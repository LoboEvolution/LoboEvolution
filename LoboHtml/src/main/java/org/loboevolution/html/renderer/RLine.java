/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Apr 16, 2005
 */
package org.loboevolution.html.renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.loboevolution.html.AlignValues;
import org.loboevolution.html.dom.domimpl.ModelNode;
import org.loboevolution.html.renderstate.RenderState;

/**
 * @author J. H. S.
 */
class RLine extends BaseRCollection {
	private boolean allowOverflow = false;
	private int baseLineOffset;
	private int desiredMaxWidth;

	private boolean firstAllowOverflowWord = false;

	private LineBreak lineBreak;
	private BoundableRenderable mousePressTarget;

	private final List<Renderable> renderables = new ArrayList<Renderable>(8);

	/**
	 * Offset where next renderable should be placed. This can be different to
	 * width.
	 */
	private int xoffset;

	/**
	 * <p>Constructor for RLine.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.ModelNode} object.
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param x a int.
	 * @param y a int.
	 * @param desiredMaxWidth a int.
	 * @param height a int.
	 * @param initialAllowOverflow a boolean.
	 */
	public RLine(ModelNode modelNode, RenderableContainer container, int x, int y, int desiredMaxWidth, int height,
			boolean initialAllowOverflow) {
		// Note that in the case of RLine, modelNode is the context node
		// at the beginning of the line, not a node that encloses the whole line.
		super(container, modelNode);
		this.x = x;
		this.y = y;
		this.height = height;
		this.desiredMaxWidth = desiredMaxWidth;
		// Layout here can always be "invalidated"
		this.layoutUpTreeCanBeInvalidated = true;
		this.allowOverflow = initialAllowOverflow;
	}

	/**
	 * This method adds and positions a renderable in the line, if possible. Note
	 * that RLine does not set sizes, but only origins.
	 *
	 * @throws org.loboevolution.html.renderer.OverflowException Thrown if the renderable overflows the line. All
	 *                           overflowing renderables are added to the exception.
	 * @param renderable a {@link org.loboevolution.html.renderer.Renderable} object.
	 */
	public final void add(Renderable renderable) throws OverflowException {
		if (renderable instanceof RWord) {
			addWord((RWord) renderable);
		} else if (renderable instanceof RBlank) {
			addBlank((RBlank) renderable);
		} else if (renderable instanceof RElement) {
			addElement((RElement) renderable);
		} else if (renderable instanceof RSpacing) {
			addSpacing((RSpacing) renderable);
		} else if (renderable instanceof RStyleChanger) {
			addStyleChanger((RStyleChanger) renderable);
		} else if (renderable instanceof RFloatInfo) {
			simplyAdd(renderable);
		} else {
			throw new IllegalArgumentException("Can't add " + renderable);
		}
	}

	/**
	 * <p>addBlank.</p>
	 *
	 * @param rblank a {@link org.loboevolution.html.renderer.RBlank} object.
	 */
	public final void addBlank(RBlank rblank) {
		// NOTE: Blanks may be added without concern for wrapping (?)
		final int x = this.xoffset;
		final int width = rblank.width;
		rblank.setOrigin(x, this.baseLineOffset - rblank.ascentPlusLeading);
		this.renderables.add(rblank);
		rblank.setParent(this);
		// Only move xoffset, but not width
		this.xoffset = x + width;
	}

	private final void addElement(RElement relement) throws OverflowException {
		// Check if it fits horizontally
		final int origXOffset = this.xoffset;
		final int desiredMaxWidth = this.desiredMaxWidth;
		final int pw = relement.getWidth();
		final boolean allowOverflow = this.allowOverflow;
		final boolean firstAllowOverflowWord = this.firstAllowOverflowWord;
		if (allowOverflow && firstAllowOverflowWord) {
			this.firstAllowOverflowWord = false;
		}
		if ((!allowOverflow || firstAllowOverflowWord) && origXOffset != 0 && origXOffset + pw > desiredMaxWidth) {
			throw new OverflowException(Collections.singleton(relement));
		}
		// Note: Renderable for widget doesn't paint the widget, but
		// it's needed for height readjustment.
		final int boundsh = this.height;
		final int ph = relement.getHeight();
		int requiredHeight;
		final int valign = relement.getVAlign();
		final AlignValues key = AlignValues.get(valign);
		switch (key) {
		case BASELINE:
		case BOTTOM:
			requiredHeight = ph + boundsh - this.baseLineOffset;
			break;
		case MIDDLE:
			requiredHeight = Math.max(ph, ph / 2 + boundsh - this.baseLineOffset);
			break;
		default:
			requiredHeight = ph;
			break;
		}
		if (requiredHeight > boundsh) {
			// Height adjustment depends on bounds being already set.
			adjustHeight(requiredHeight, ph, valign);
		}
		this.renderables.add(relement);
		relement.setParent(this);
		relement.setX(origXOffset);
		setElementY(relement, ph, valign);
		final int newX = origXOffset + pw;
		this.width = this.xoffset = newX;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sourceforge.xamj.domimpl.markup.Renderable#paint(java.awt.Graphics)
	 */

	/**
	 * <p>addSpacing.</p>
	 *
	 * @param rblank a {@link org.loboevolution.html.renderer.RSpacing} object.
	 */
	public final void addSpacing(RSpacing rblank) {
		// NOTE: Spacing may be added without concern for wrapping (?)
		final int x = this.xoffset;
		final int width = rblank.width;
		rblank.setOrigin(x, (this.height - rblank.height) / 2);
		this.renderables.add(rblank);
		rblank.setParent(this);
		this.width = this.xoffset = x + width;
	}

	/**
	 * <p>addStyleChanger.</p>
	 *
	 * @param sc a {@link org.loboevolution.html.renderer.RStyleChanger} object.
	 */
	public final void addStyleChanger(RStyleChanger sc) {
		this.renderables.add(sc);
	}

	/**
	 * <p>addWord.</p>
	 *
	 * @param rword a {@link org.loboevolution.html.renderer.RWord} object.
	 * @throws org.loboevolution.html.renderer.OverflowException if any.
	 */
	public final void addWord(RWord rword) throws OverflowException {
		// Check if it fits horzizontally
		int offset = this.xoffset;
		final int wiwidth = rword.width;
		final boolean allowOverflow = this.allowOverflow;
		final boolean firstAllowOverflowWord = this.firstAllowOverflowWord;
		if (allowOverflow && firstAllowOverflowWord) {
			this.firstAllowOverflowWord = false;
		}
		if ((!allowOverflow || firstAllowOverflowWord) && offset != 0 && offset + wiwidth > this.desiredMaxWidth) {
			final List<Renderable> renderables = this.renderables;
			ArrayList<Renderable> overflow = null;
			boolean cancel = false;
			// Check if other words need to be overflown (for example,
			// a word just before a markup tag adjacent to the word
			// we're trying to add). An RBlank between words prevents
			// a word from being overflown to the next line (and this
			// is the usefulness of RBlank.)
			int newOffset = offset;
			int newWidth = offset;
			for (int i = renderables.size(); --i >= 0;) {
				final Renderable renderable = (Renderable) renderables.get(i);
				if (renderable instanceof RWord || !(renderable instanceof BoundableRenderable)) {
					if (overflow == null) {
						overflow = new ArrayList<Renderable>();
					}
					if (renderable != rword && renderable instanceof RWord && ((RWord) renderable).getX() == 0) {
						// Can't overflow words starting at offset zero.
						// Note that all or none should be overflown.
						cancel = true;
						// No need to set offset - set later.
						break;
					}
					overflow.add(0, renderable);
					renderables.remove(i);
				} else {
					if (renderable instanceof RBlank) {
						final RBlank rblank = (RBlank) renderable;
						newWidth = rblank.getX();
						newOffset = newWidth + rblank.getWidth();
					} else {
						final BoundableRenderable br = (BoundableRenderable) renderable;
						newWidth = newOffset = br.getX() + br.getWidth();
					}
					break;
				}
			}
			if (cancel) {
				// Oops. Need to undo overflow.
				if (overflow != null) {
					renderables.addAll(overflow);
				}
			} else {
				this.xoffset = newOffset;
				this.width = newWidth;
				if (overflow == null) {
					throw new OverflowException(Collections.singleton(rword));
				} else {
					overflow.add(rword);
					throw new OverflowException(overflow);
				}
			}
		}

		

	    int extraHeight = 0;
	    final int maxAscentPlusLeading = this.baseLineOffset;
	    if (rword.ascentPlusLeading > maxAscentPlusLeading) {
	      extraHeight += (rword.ascentPlusLeading - maxAscentPlusLeading) + rword.descent;
	    }
	    
	    if (extraHeight > 0) {
	      final int newHeight = (this.height + extraHeight);
	      this.adjustHeight(newHeight, newHeight, AlignValues.BOTTOM.getValue());
	    }
	    this.renderables.add(rword);
	    rword.setParent(this);
	    final int x = offset;
	    offset += wiwidth;
	    this.width = this.xoffset = offset;
	    rword.setOrigin(x, this.baseLineOffset - rword.ascentPlusLeading);
	}

	/**
	 * Rearrange line elements based on a new line height and alignment provided.
	 * All line elements are expected to have bounds preset.
	 * 
	 * @param newHeight
	 * @param alignmentY
	 */
	private void adjustHeight(int newHeight, int elementHeight, int valign) {
		// Set new line height
		// int oldHeight = this.height;
		this.height = newHeight;
		final List<Renderable> renderables = this.renderables;
		// Find max baseline
		final FontMetrics firstFm = this.modelNode.getRenderState().getFontMetrics();
		int maxDescent = firstFm.getDescent();
		int maxAscentPlusLeading = firstFm.getAscent() + firstFm.getLeading();
		for (final Object r : renderables) {
			if (r instanceof RStyleChanger) {
				final RStyleChanger rstyleChanger = (RStyleChanger) r;
				final FontMetrics fm = rstyleChanger.getModelNode().getRenderState().getFontMetrics();
				final int descent = fm.getDescent();
				if (descent > maxDescent) {
					maxDescent = descent;
				}
				final int ascentPlusLeading = fm.getAscent() + fm.getLeading();
				if (ascentPlusLeading > maxAscentPlusLeading) {
					maxAscentPlusLeading = ascentPlusLeading;
				}
			}
		}
		final int textHeight = maxDescent + maxAscentPlusLeading;
		int baseline;
		AlignValues key = AlignValues.get(valign);
		switch (key) {
		case ABSBOTTOM:
			baseline = newHeight - maxDescent;
			break;
		case ABSMIDDLE:
			baseline = (newHeight + textHeight) / 2 - maxDescent;
			break;
		case BASELINE:
		case BOTTOM:
			baseline = elementHeight;
			break;
		case MIDDLE:
			baseline = newHeight / 2;
			break;
		case TOP:
			baseline = maxAscentPlusLeading;
			break;
		default:
			baseline = elementHeight;
			break;
		}
		this.baseLineOffset = baseline;

		// Change bounds of renderables accordingly
		for (final Object r : renderables) {
			if (r instanceof RWord) {
				final RWord rword = (RWord) r;
				rword.setY(baseline - rword.ascentPlusLeading);
			} else if (r instanceof RBlank) {
				final RBlank rblank = (RBlank) r;
				rblank.setY(baseline - rblank.ascentPlusLeading);
			} else if (r instanceof RElement) {
				final RElement relement = (RElement) r;
				// int w = relement.getWidth();
				setElementY(relement, relement.getHeight(), relement.getVAlign());
			} else {
				// RSpacing and RStyleChanger don't matter?
			}
		}
		// TODO: Could throw OverflowException when we add floating widgets
	}

	/**
	 * This method should only be invoked when the line has no items yet.
	 *
	 * @param x a int.
	 * @param desiredMaxWidth a int.
	 */
	public void changeLimits(int x, int desiredMaxWidth) {
		this.x = x;
		this.desiredMaxWidth = desiredMaxWidth;
	}

	/** {@inheritDoc} */
	@Override
	public boolean extractSelectionText(StringBuilder buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint) {
		final boolean result = super.extractSelectionText(buffer, inSelection, startPoint, endPoint);
		if (result) {
			final LineBreak br = this.lineBreak;
			if (br != null) {
				buffer.append(System.getProperty("line.separator"));
			} else {
				final List<Renderable> renderables = this.renderables;
				final int size = renderables.size();
				if (size > 0 && !(renderables.get(size - 1) instanceof RBlank)) {
					buffer.append(" ");
				}
			}
		}
		return result;
	}

	/**
	 * <p>getBaselineOffset.</p>
	 *
	 * @return a int.
	 */
	public int getBaselineOffset() {
		return this.baseLineOffset;
	}

	/** {@inheritDoc} */
	@Override
	public Color getBlockBackgroundColor() {
		return this.container.getPaintedBackgroundColor();
	}

	/**
	 * <p>Getter for the field lineBreak.</p>
	 *
	 * @return a {@link org.loboevolution.html.renderer.LineBreak} object.
	 */
	public LineBreak getLineBreak() {
		return this.lineBreak;
	}

	/** {@inheritDoc} */
	@Override
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		final Renderable[] rarray = (Renderable[]) this.renderables.toArray(Renderable.EMPTY_ARRAY);
		final BoundableRenderable br = MarkupUtilities.findRenderable(rarray, x, y, false);
		if (br != null) {
			final Rectangle rbounds = br.getVisualBounds();
			return br.getLowestRenderableSpot(x - rbounds.x, y - rbounds.y);
		} else {
			return new RenderableSpot(this, x, y);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.RCollection#getRenderables()
	 */
	/** {@inheritDoc} */
	@Override
	public Iterator<Renderable> getRenderables() {
		return this.renderables.iterator();
	}

	/** {@inheritDoc} */
	@Override
	protected void invalidateLayoutLocal() {
		// Workaround for fact that RBlockViewport does not
		// get validated or invalidated.
		this.layoutUpTreeCanBeInvalidated = true;
	}

	/**
	 * <p>isAllowOverflow.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isAllowOverflow() {
		return this.allowOverflow;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isContainedByNode() {
		return false;
	}

	/**
	 * <p>isEmpty.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isEmpty() {
		return this.xoffset == 0;
	}

	/** {@inheritDoc} */
	@Override
	public boolean onDoubleClick(MouseEvent event, int x, int y) {
		final Renderable[] rarray = (Renderable[]) this.renderables.toArray(Renderable.EMPTY_ARRAY);
		final BoundableRenderable r = MarkupUtilities.findRenderable(rarray, x, y, false);
		if (r != null) {
			final Rectangle rbounds = r.getVisualBounds();
			return r.onDoubleClick(event, x - rbounds.x, y - rbounds.y);
		} else {
			return true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean onMouseClick(MouseEvent event, int x, int y) {
		final Renderable[] rarray = (Renderable[]) this.renderables.toArray(Renderable.EMPTY_ARRAY);
		final BoundableRenderable r = MarkupUtilities.findRenderable(rarray, x, y, false);
		if (r != null) {
			final Rectangle rbounds = r.getVisualBounds();
			return r.onMouseClick(event, x - rbounds.x, y - rbounds.y);
		} else {
			return true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean onMouseDisarmed(MouseEvent event) {
		final BoundableRenderable target = this.mousePressTarget;
		if (target != null) {
			this.mousePressTarget = null;
			return target.onMouseDisarmed(event);
		} else {
			return true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean onMousePressed(MouseEvent event, int x, int y) {
		final Renderable[] rarray = (Renderable[]) this.renderables.toArray(Renderable.EMPTY_ARRAY);
		final BoundableRenderable r = MarkupUtilities.findRenderable(rarray, x, y, false);
		if (r != null) {
			this.mousePressTarget = r;
			final Rectangle rbounds = r.getVisualBounds();
			return r.onMousePressed(event, x - rbounds.x, y - rbounds.y);
		} else {
			return true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean onMouseReleased(MouseEvent event, int x, int y) {
		final Renderable[] rarray = (Renderable[]) this.renderables.toArray(Renderable.EMPTY_ARRAY);
		final BoundableRenderable r = MarkupUtilities.findRenderable(rarray, x, y, false);
		if (r != null) {
			final Rectangle rbounds = r.getVisualBounds();
			final BoundableRenderable oldArmedRenderable = this.mousePressTarget;
			if (oldArmedRenderable != null && r != oldArmedRenderable) {
				oldArmedRenderable.onMouseDisarmed(event);
				this.mousePressTarget = null;
			}
			return r.onMouseReleased(event, x - rbounds.x, y - rbounds.y);
		} else {
			final BoundableRenderable oldArmedRenderable = this.mousePressTarget;
			if (oldArmedRenderable != null) {
				oldArmedRenderable.onMouseDisarmed(event);
				this.mousePressTarget = null;
			}
			return true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void paint(Graphics g) {
		final RenderState rs = this.modelNode.getRenderState();
        
		if (rs != null) {
			if (rs.getVisibility() != RenderState.VISIBILITY_VISIBLE) {
	            return;
	        }
			
			final Color textColor = rs.getColor();
			g.setColor(textColor);
			final Font font = rs.getFont();
			g.setFont(font);
		}

		final Iterator<Renderable> i = this.renderables.iterator();
		if (i != null) {
			while (i.hasNext()) {
				final Object r = i.next();
				if (r instanceof RElement) {
					// RElements should be translated.
					final RElement relement = (RElement) r;
					final Graphics newG = g.create();
					newG.translate(relement.getX(), relement.getY());
					try {
						relement.paint(newG);
					} finally {
						newG.dispose();
					}
				} else if (r instanceof BoundableRenderable) {
					final BoundableRenderable br = (BoundableRenderable) r;
					br.paintTranslated(g);
				} else {
					((Renderable) r).paint(g);
				}
			}
		}
	}

	/**
	 * <p>Setter for the field allowOverflow.</p>
	 *
	 * @param flag a boolean.
	 */
	public void setAllowOverflow(boolean flag) {
		if (flag != this.allowOverflow) {
			this.allowOverflow = flag;
			if (flag) {
				// Set to true only if allowOverflow was
				// previously false.
				this.firstAllowOverflowWord = true;
			}
		}
	}

	/**
	 * 
	 * @param relement
	 * @param x
	 * @param elementHeight The required new line height.
	 * @param valign
	 */
	private final void setElementY(RElement relement, int elementHeight, int valign) {
		int yoffset;
		AlignValues key = AlignValues.get(valign);
		switch (key) {
		case BOTTOM:
			yoffset = this.height - elementHeight;
			break;
		case MIDDLE:
			yoffset = (this.height - elementHeight) / 2;
			break;
		case BASELINE:
			yoffset = this.baseLineOffset - elementHeight;
			break;
		case TOP:
			yoffset = 0;
			break;
		default:
			yoffset = this.baseLineOffset - elementHeight;
		}
		relement.setY(yoffset);
	}

	/**
	 * <p>Setter for the field lineBreak.</p>
	 *
	 * @param lineBreak a {@link org.loboevolution.html.renderer.LineBreak} object.
	 */
	public void setLineBreak(LineBreak lineBreak) {
		this.lineBreak = lineBreak;
	}

	/**
	 * <p>simplyAdd.</p>
	 *
	 * @param r a {@link org.loboevolution.html.renderer.Renderable} object.
	 */
	public final void simplyAdd(Renderable r) {
		this.renderables.add(r);
	}

	/** {@inheritDoc} */
	@Override
	public Rectangle getClipBounds() {
		// TODO Auto-generated method stub
		return null;
	}
}
