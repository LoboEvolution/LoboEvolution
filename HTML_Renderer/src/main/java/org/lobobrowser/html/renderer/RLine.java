/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Apr 16, 2005
 */
package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.renderstate.RenderState;

/**
 * The Class RLine.
 *
 * @author J. H. S.
 */
public class RLine extends BaseRCollection {

	/** The renderables. */
	private final ArrayList<Renderable> renderables = new ArrayList<Renderable>(8);
	
	/** The base line offset. */
	private int baseLineOffset;

	/** The desired max width. */
	private int desiredMaxWidth;
	
	/** The mouse press target. */
	private BoundableRenderable mousePressTarget;

	/** The line break. */
	private LineBreak lineBreak;

	/** The line xoffset. */
	private int xoffset;

	/** The allow overflow. */
	private boolean allowOverflow = false;

	/** The first allow overflow word. */
	private boolean firstAllowOverflowWord = false;

	/**
	 * Instantiates a new r line.
	 *
	 * @param modelNode
	 *            the model node
	 * @param container
	 *            the container
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param desiredMaxWidth
	 *            the desired max width
	 * @param height
	 *            the height
	 * @param initialAllowOverflow
	 *            the initial allow overflow
	 */
	public RLine(ModelNode modelNode, RenderableContainer container, int x, int y, int desiredMaxWidth, int height,
			boolean initialAllowOverflow) {
		// Note that in the case of RLine, modelNode is the context node
		// at the beginning of the line, not a node that encloses the whole
		// line.
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
	 * Sets the allow overflow.
	 *
	 * @param flag
	 *            the new allow overflow
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
	 * Checks if is allow overflow.
	 *
	 * @return the allow overflow
	 */
	public boolean isAllowOverflow() {
		return this.allowOverflow;
	}

	/**
	 * This method should only be invoked when the line has no items yet.
	 *
	 * @param x
	 *            the x
	 * @param desiredMaxWidth
	 *            the desired max width
	 */
	public void changeLimits(int x, int desiredMaxWidth) {
		this.x = x;
		this.desiredMaxWidth = desiredMaxWidth;
	}

	/**
	 * Gets the baseline offset.
	 *
	 * @return the baseline offset
	 */
	public int getBaselineOffset() {
		return this.baseLineOffset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.BaseBoundableRenderable#
	 * invalidateLayoutLocal()
	 */
	@Override
	protected void invalidateLayoutLocal() {
		// Workaround for fact that RBlockViewport does not
		// get validated or invalidated.
		this.layoutUpTreeCanBeInvalidated = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.xamj.domimpl.markup.Renderable#paint(java.awt.Graphics)
	 */

	@Override
	public void paint(Graphics g) {
		// Paint according to render state of the start of line first.
		RenderState rs = this.modelNode.getRenderState();
		if (rs != null) {
			Color textColor = rs.getColor();
			g.setColor(textColor);
			Font font = rs.getFont();
			g.setFont(font);
		}
		// Note that partial paints of the line can only be done
		// if all RStyleChanger's are applied first.
		Iterator<Renderable> i = this.renderables.iterator();
		if (i != null) {
			while (i.hasNext()) {
				Object r = i.next();
				if (r instanceof RElement) {
					// RElement's should be clipped.
					RElement relement = (RElement) r;
					Graphics newG = g.create(relement.getX(), relement.getY(), relement.getWidth(),
							relement.getHeight());
					try {
						relement.paint(newG);
					} finally {
						newG.dispose();
					}
				} else if (r instanceof BoundableRenderable) {
					BoundableRenderable br = (BoundableRenderable) r;
					br.paintTranslated(g);
				} else {
					((Renderable) r).paint(g);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BaseRCollection#extractSelectionText(java.
	 * lang .StringBuffer, boolean,
	 * org.lobobrowser.html.renderer.RenderableSpot,
	 * org.lobobrowser.html.renderer.RenderableSpot)
	 */
	@Override
	public boolean extractSelectionText(StringBuffer buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint) {
		boolean result = super.extractSelectionText(buffer, inSelection, startPoint, endPoint);
		if (result) {
			LineBreak br = this.lineBreak;
			if (br != null) {
				buffer.append(System.getProperty("line.separator"));
			} else {
				ArrayList<Renderable> renderables = this.renderables;
				int size = renderables.size();
				if (size > 0 && !(renderables.get(size - 1) instanceof RBlank)) {
					buffer.append(" ");
				}
			}
		}
		return result;
	}

	/**
	 * Adds the style changer.
	 *
	 * @param sc
	 *            the sc
	 */
	public final void addStyleChanger(RStyleChanger sc) {
		this.renderables.add(sc);
	}

	/**
	 * Simply add.
	 *
	 * @param r
	 *            the r
	 */
	public final void simplyAdd(Renderable r) {
		this.renderables.add(r);
	}

	/**
	 * This method adds and positions a renderable in the line, if possible.
	 * Note that RLine does not set sizes, but only origins.
	 *
	 * @param renderable
	 *            the renderable
	 * @throws OverflowException
	 *             Thrown if the renderable overflows the line. All overflowing
	 *             renderables are added to the exception.
	 */
	public final void add(Renderable renderable) throws OverflowException {
		if (renderable instanceof RWord) {
			this.addWord((RWord) renderable);
		} else if (renderable instanceof RBlank) {
			this.addBlank((RBlank) renderable);
		} else if (renderable instanceof RElement) {
			this.addElement((RElement) renderable);
		} else if (renderable instanceof RSpacing) {
			this.addSpacing((RSpacing) renderable);
		} else if (renderable instanceof RStyleChanger) {
			this.addStyleChanger((RStyleChanger) renderable);
		} else if (renderable instanceof RFloatInfo) {
			this.simplyAdd(renderable);
		} else {
			throw new IllegalArgumentException("Can't add " + renderable);
		}
	}

	/**
	 * Adds the word.
	 *
	 * @param rword
	 *            the rword
	 * @throws OverflowException
	 *             the overflow exception
	 */
	public final void addWord(RWord rword) throws OverflowException {
		// Check if it fits horzizontally
		int offset = this.xoffset;
		int wiwidth = rword.width;
		boolean allowOverflow = this.allowOverflow;
		boolean firstAllowOverflowWord = this.firstAllowOverflowWord;
		if (allowOverflow && firstAllowOverflowWord) {
			this.firstAllowOverflowWord = false;
		}
		if ((!allowOverflow || firstAllowOverflowWord) && offset != 0 && offset + wiwidth > this.desiredMaxWidth) {
			ArrayList<Renderable> renderables = this.renderables;
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
				Renderable renderable = renderables.get(i);
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
						RBlank rblank = (RBlank) renderable;
						newWidth = rblank.getX();
						newOffset = newWidth + rblank.getWidth();
					} else {
						BoundableRenderable br = (BoundableRenderable) renderable;
						newWidth = newOffset = br.getX() + br.getWidth();
					}
					break;
				}
			}
			if (cancel) {
				// Oops. Need to undo overflow.
				if (overflow != null) {
					Iterator<Renderable> i = overflow.iterator();
					while (i.hasNext()) {
						renderables.add(i.next());
					}
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

		// Add it

		int extraHeight = 0;
		int maxDescent = this.height - this.baseLineOffset;
		if (rword.getDescent() > maxDescent) {
			extraHeight += rword.getDescent() - maxDescent;
		}
		int maxAscentPlusLeading = this.baseLineOffset;
		if (rword.getAscentPlusLeading() > maxAscentPlusLeading) {
			extraHeight += rword.getAscentPlusLeading() - maxAscentPlusLeading;
		}
		if (extraHeight > 0) {
			int newHeight = this.height + extraHeight;
			this.adjustHeight(newHeight, newHeight, RElement.VALIGN_ABSBOTTOM);
		}
		this.renderables.add(rword);
		rword.setParent(this);
		int x = offset;
		offset += wiwidth;
		this.width = this.xoffset = offset;
		rword.setOrigin(x, this.baseLineOffset - rword.getAscentPlusLeading());
	}

	/**
	 * Adds the blank.
	 *
	 * @param rblank
	 *            the rblank
	 */
	public final void addBlank(RBlank rblank) {
		// NOTE: Blanks may be added without concern for wrapping (?)
		int x = this.xoffset;
		int width = rblank.width;
		rblank.setOrigin(x, this.baseLineOffset - rblank.getAscentPlusLeading());
		this.renderables.add(rblank);
		rblank.setParent(this);
		// Only move xoffset, but not width
		this.xoffset = x + width;
	}

	/**
	 * Adds the spacing.
	 *
	 * @param rblank
	 *            the rblank
	 */
	public final void addSpacing(RSpacing rblank) {
		// NOTE: Spacing may be added without concern for wrapping (?)
		int x = this.xoffset;
		int width = rblank.width;
		rblank.setOrigin(x, (this.height - rblank.height) / 2);
		this.renderables.add(rblank);
		rblank.setParent(this);
		this.width = this.xoffset = x + width;
	}

	/**
	 * Sets the element y.
	 *
	 * @param relement
	 *            the relement
	 * @param elementHeight
	 *            The required new line height.
	 * @param valign
	 *            the valign
	 */
	private final void setElementY(RElement relement, int elementHeight, int valign) {
		// At this point height should be more than what's needed.
		int yoffset;
		switch (valign) {
		case RElement.VALIGN_ABSBOTTOM:
			yoffset = this.height - elementHeight;
			break;
		case RElement.VALIGN_ABSMIDDLE:
			yoffset = (this.height - elementHeight) / 2;
			break;
		case RElement.VALIGN_BASELINE:
		case RElement.VALIGN_BOTTOM:
			yoffset = this.baseLineOffset - elementHeight;
			break;
		case RElement.VALIGN_MIDDLE:
			yoffset = this.baseLineOffset - elementHeight / 2;
			break;
		case RElement.VALIGN_TOP:
			yoffset = 0;
			break;
		default:
			yoffset = this.baseLineOffset - elementHeight;
			break;
		}
		// RLine only sets origins, not sizes.
		// relement.setBounds(x, yoffset, width, height);
		relement.setY(yoffset);
	}

	/**
	 * Adds the element.
	 *
	 * @param relement
	 *            the relement
	 * @throws OverflowException
	 *             the overflow exception
	 */
	private final void addElement(RElement relement) throws OverflowException {
		// Check if it fits horizontally
		int origXOffset = this.xoffset;
		int desiredMaxWidth = this.desiredMaxWidth;
		int pw = relement.getWidth();
		boolean allowOverflow = this.allowOverflow;
		boolean firstAllowOverflowWord = this.firstAllowOverflowWord;
		if (allowOverflow && firstAllowOverflowWord) {
			this.firstAllowOverflowWord = false;
		}
		if ((!allowOverflow || firstAllowOverflowWord) && origXOffset != 0 && origXOffset + pw > desiredMaxWidth) {
			throw new OverflowException(Collections.singleton(relement));
		}
		// Note: Renderable for widget doesn't paint the widget, but
		// it's needed for height readjustment.
		int boundsh = this.height;
		int ph = relement.getHeight();
		int requiredHeight;
		int valign = relement.getVAlign();
		switch (valign) {
		case RElement.VALIGN_BASELINE:
		case RElement.VALIGN_BOTTOM:
			requiredHeight = ph + boundsh - this.baseLineOffset;
			break;
		case RElement.VALIGN_MIDDLE:
			requiredHeight = Math.max(ph, ph / 2 + boundsh - this.baseLineOffset);
			break;
		default:
			requiredHeight = ph;
			break;
		}
		if (requiredHeight > boundsh) {
			// Height adjustment depends on bounds being already set.
			this.adjustHeight(requiredHeight, ph, valign);
		}
		this.renderables.add(relement);
		relement.setParent(this);
		relement.setX(origXOffset);
		this.setElementY(relement, ph, valign);
		int newX = origXOffset + pw;
		this.width = this.xoffset = newX;
	}

	/**
	 * Rearrange line elements based on a new line height and alignment
	 * provided. All line elements are expected to have bounds preset.
	 *
	 * @param newHeight
	 *            the new height
	 * @param elementHeight
	 *            the element height
	 * @param valign
	 *            the valign
	 */
	private void adjustHeight(int newHeight, int elementHeight, int valign) {
		// Set new line height
		// int oldHeight = this.height;
		this.height = newHeight;
		ArrayList<Renderable> renderables = this.renderables;
		// Find max baseline
		FontMetrics firstFm = this.modelNode.getRenderState().getFontMetrics();
		int maxDescent = firstFm.getDescent();
		int maxAscentPlusLeading = firstFm.getAscent() + firstFm.getLeading();
		for (Renderable renderable : renderables) {
			Object r = renderable;
			if (r instanceof RStyleChanger) {
				RStyleChanger rstyleChanger = (RStyleChanger) r;
				FontMetrics fm = rstyleChanger.getModelNode().getRenderState().getFontMetrics();
				int descent = fm.getDescent();
				if (descent > maxDescent) {
					maxDescent = descent;
				}
				int ascentPlusLeading = fm.getAscent() + fm.getLeading();
				if (ascentPlusLeading > maxAscentPlusLeading) {
					maxAscentPlusLeading = ascentPlusLeading;
				}
			}
		}
		int textHeight = maxDescent + maxAscentPlusLeading;

		// TODO: Need to take into account previous RElement's and
		// their alignments?

		int baseline;
		switch (valign) {
		case RElement.VALIGN_ABSBOTTOM:
			baseline = newHeight - maxDescent;
			break;
		case RElement.VALIGN_ABSMIDDLE:
			baseline = (newHeight + textHeight) / 2 - maxDescent;
			break;
		case RElement.VALIGN_BASELINE:
		case RElement.VALIGN_BOTTOM:
			baseline = elementHeight;
			break;
		case RElement.VALIGN_MIDDLE:
			baseline = newHeight / 2;
			break;
		case RElement.VALIGN_TOP:
			baseline = maxAscentPlusLeading;
			break;
		default:
			baseline = elementHeight;
			break;
		}
		this.baseLineOffset = baseline;

		// Change bounds of renderables accordingly
		for (Renderable renderable : renderables) {
			Object r = renderable;
			if (r instanceof RWord) {
				RWord rword = (RWord) r;
				rword.setY(baseline - rword.getAscentPlusLeading());
			} else if (r instanceof RBlank) {
				RBlank rblank = (RBlank) r;
				rblank.setY(baseline - rblank.getAscentPlusLeading());
			} else if (r instanceof RElement) {
				RElement relement = (RElement) r;
				// int w = relement.getWidth();
				this.setElementY(relement, relement.getHeight(), relement.getVAlign());
			}
		}
		// TODO: Could throw OverflowException when we add floating widgets
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#onMouseClick(java.awt.
	 * event .MouseEvent, int, int)
	 */
	@Override
	public boolean onMouseClick(MouseEvent event, int x, int y) {
		Renderable[] rarray = this.renderables.toArray(Renderable.EMPTY_ARRAY);
		BoundableRenderable r = MarkupUtilities.findRenderable(rarray, x, y, false);
		if (r != null) {
			Rectangle rbounds = r.getBounds();
			return r.onMouseClick(event, x - rbounds.x, y - rbounds.y);
		} else {
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#onDoubleClick(java.awt.
	 * event.MouseEvent, int, int)
	 */
	@Override
	public boolean onDoubleClick(MouseEvent event, int x, int y) {
		Renderable[] rarray = this.renderables.toArray(Renderable.EMPTY_ARRAY);
		BoundableRenderable r = MarkupUtilities.findRenderable(rarray, x, y, false);
		if (r != null) {
			Rectangle rbounds = r.getBounds();
			return r.onDoubleClick(event, x - rbounds.x, y - rbounds.y);
		} else {
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#onMousePressed(java.awt
	 * .event.MouseEvent, int, int)
	 */
	@Override
	public boolean onMousePressed(MouseEvent event, int x, int y) {
		Renderable[] rarray = this.renderables.toArray(Renderable.EMPTY_ARRAY);
		BoundableRenderable r = MarkupUtilities.findRenderable(rarray, x, y, false);
		if (r != null) {
			this.mousePressTarget = r;
			Rectangle rbounds = r.getBounds();
			return r.onMousePressed(event, x - rbounds.x, y - rbounds.y);
		} else {
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#getLowestRenderableSpot
	 * (int, int)
	 */
	@Override
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		Renderable[] rarray = this.renderables.toArray(Renderable.EMPTY_ARRAY);
		BoundableRenderable br = MarkupUtilities.findRenderable(rarray, x, y, false);
		if (br != null) {
			Rectangle rbounds = br.getBounds();
			return br.getLowestRenderableSpot(x - rbounds.x, y - rbounds.y);
		} else {
			return new RenderableSpot(this, x, y);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#onMouseReleased(java.
	 * awt .event.MouseEvent, int, int)
	 */
	@Override
	public boolean onMouseReleased(MouseEvent event, int x, int y) {
		Renderable[] rarray = this.renderables.toArray(Renderable.EMPTY_ARRAY);
		BoundableRenderable r = MarkupUtilities.findRenderable(rarray, x, y, false);
		if (r != null) {
			Rectangle rbounds = r.getBounds();
			BoundableRenderable oldArmedRenderable = this.mousePressTarget;
			if (oldArmedRenderable != null && r != oldArmedRenderable) {
				oldArmedRenderable.onMouseDisarmed(event);
				this.mousePressTarget = null;
			}
			return r.onMouseReleased(event, x - rbounds.x, y - rbounds.y);
		} else {
			BoundableRenderable oldArmedRenderable = this.mousePressTarget;
			if (oldArmedRenderable != null) {
				oldArmedRenderable.onMouseDisarmed(event);
				this.mousePressTarget = null;
			}
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#onMouseDisarmed(java.
	 * awt .event.MouseEvent)
	 */
	@Override
	public boolean onMouseDisarmed(MouseEvent event) {
		BoundableRenderable target = this.mousePressTarget;
		if (target != null) {
			this.mousePressTarget = null;
			return target.onMouseDisarmed(event);
		} else {
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.BaseBoundableRenderable#
	 * getBlockBackgroundColor ()
	 */
	@Override
	public Color getBlockBackgroundColor() {
		return this.container.getPaintedBackgroundColor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.render.RCollection#getRenderables()
	 */
	@Override
	public Iterator<Renderable> getRenderables() {
		return this.renderables.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#isContainedByNode()
	 */
	@Override
	public boolean isContainedByNode() {
		return false;
	}
	
	/**
	 * Gets the line break.
	 *
	 * @return the line break
	 */
	public LineBreak getLineBreak() {
		return lineBreak;
	}

	/**
	 * Sets the line break.
	 *
	 * @param lineBreak
	 *            the new line break
	 */
	public void setLineBreak(LineBreak lineBreak) {
		this.lineBreak = lineBreak;
	}

	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return this.xoffset == 0;
	}

}
