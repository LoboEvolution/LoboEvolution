/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Apr 16, 2005
 */
package org.loboevolution.html.renderer;

import lombok.Getter;
import lombok.Setter;
import org.loboevolution.html.AlignValues;
import org.loboevolution.html.node.ModelNode;
import org.loboevolution.html.renderstate.RenderState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author J. H. S.
 */
class RLine extends BaseRCollection {

	@Getter
	private boolean allowOverflow;
	private int baseLineOffset;
	private int desiredMaxWidth;

	private boolean firstAllowOverflowWord = false;

	@Getter
	@Setter
	private LineBreak lineBreak;
	private BoundableRenderable mousePressTarget;

	private final List<Renderable> renderables = new ArrayList<>(8);

	/**
	 * Offset where next renderable should be placed. This can be different to
	 * width.
	 */
	private int xoffset;

	/**
	 * <p>Constructor for RLine.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.node.ModelNode} object.
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param desiredMaxWidth a {@link java.lang.Integer} object.
	 * @param height a {@link java.lang.Integer} object.
	 * @param initialAllowOverflow a boolean.
	 */
	public RLine(final ModelNode modelNode, final RenderableContainer container, final int x, final int y, final int desiredMaxWidth, final int height,
				 final boolean initialAllowOverflow) {
		// Note that in the case of RLine, modelNode is the context node
		// at the beginning of the line, not a node that encloses the whole line.
		super(container, modelNode);
		this.setX(x);
		this.setY(y);
		this.setHeight(height);
		this.desiredMaxWidth = desiredMaxWidth;
		// Layout here can always be "invalidated"
		this.layoutUpTreeCanBeInvalidated = true;
		this.allowOverflow = initialAllowOverflow;
	}

	/**
	 * This method adds and positions a renderable in the line, if possible. Note
	 * that RLine does not set sizes, but only origins.
	 *
	 * @param renderable a {@link org.loboevolution.html.renderer.Renderable} object.
	 * @throws org.loboevolution.html.renderer.OverflowException if any.
	 */
	public final void add(final Renderable renderable) throws OverflowException {
        switch (renderable) {
            case RWord rWord -> addWord(rWord);
            case RBlank rBlank -> addBlank(rBlank);
            case RElement rElement -> addElement(rElement);
            case RSpacing rSpacing -> addSpacing(rSpacing);
            case RStyleChanger rStyleChanger -> addStyleChanger(rStyleChanger);
            case RFloatInfo rFloatInfo -> simplyAdd(renderable);
            case null, default -> throw new IllegalArgumentException("Can't add " + renderable);
        }
	}

	/**
	 * <p>addBlank.</p>
	 *
	 * @param rblank a {@link org.loboevolution.html.renderer.RBlank} object.
	 */
	public final void addBlank(final RBlank rblank) {
		// NOTE: Blanks may be added without concern for wrapping (?)
		final int x = this.xoffset;
		final int width = rblank.getWidth();
		rblank.setOrigin(x, this.baseLineOffset - rblank.ascentPlusLeading);
		this.renderables.add(rblank);
		rblank.setParent(this);
		// Only move xoffset, but not width
		this.xoffset = x + width;
	}

	private void addElement(final RElement relement) throws OverflowException {
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
		final int boundsh = this.getHeight();
		final int ph = relement.getHeight();
		final int requiredHeight;
		final int valign = relement.getVAlign();
		final AlignValues key = AlignValues.get(valign);
        requiredHeight = switch (key) {
            case BASELINE, BOTTOM -> ph + boundsh - this.baseLineOffset;
            case MIDDLE -> Math.max(ph, ph / 2 + boundsh - this.baseLineOffset);
            default -> ph;
        };
		if (requiredHeight > boundsh) {
			// Height adjustment depends on bounds being already set.
			adjustHeight(requiredHeight, ph, valign);
		}
		this.renderables.add(relement);
		relement.setParent(this);
		relement.setX(origXOffset);
		setElementY(relement, ph, valign);
		final int newX = origXOffset + pw;
		this.setWidth(this.xoffset = newX);
	}

	/**
	 * <p>addSpacing.</p>
	 *
	 * @param rblank a {@link org.loboevolution.html.renderer.RSpacing} object.
	 */
	public final void addSpacing(final RSpacing rblank) {
		final int x = this.xoffset;
		final int width = rblank.getWidth();
		rblank.setOrigin(x, (this.getHeight() - rblank.getHeight()) / 2);
		this.renderables.add(rblank);
		rblank.setParent(this);
		this.setWidth(this.xoffset = x + width);
	}

	/**
	 * <p>addStyleChanger.</p>
	 *
	 * @param sc a {@link org.loboevolution.html.renderer.RStyleChanger} object.
	 */
	public final void addStyleChanger(final RStyleChanger sc) {
		this.renderables.add(sc);
	}

	/**
	 * <p>addWord.</p>
	 *
	 * @param rword a {@link org.loboevolution.html.renderer.RWord} object.
	 * @throws org.loboevolution.html.renderer.OverflowException if any.
	 */
	public final void addWord(final RWord rword) throws OverflowException {
		// Check if it fits horzizontally
		int offset = this.xoffset;
		final int wiwidth = rword.getWidth();
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
				final Renderable renderable = renderables.get(i);
				if (renderable instanceof RWord || !(renderable instanceof BoundableRenderable)) {
					if (overflow == null) {
						overflow = new ArrayList<>();
					}
					if (renderable != rword && renderable instanceof RWord && ((RWord) renderable).getX() == 0) {
						// Can't overflow words starting at offset zero.
						// Note that all or none should be overflown.
						cancel = true;
						// No need to set offset - set later.
						break;
					}
					overflow.addFirst(renderable);
					renderables.remove(i);
				} else {
					if (renderable instanceof RBlank rblank) {
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
				this.setWidth(newWidth);
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
	      final int newHeight = (this.getHeight() + extraHeight);
	      this.adjustHeight(newHeight, newHeight, AlignValues.BOTTOM.getValue());
	    }
	    this.renderables.add(rword);
	    rword.setParent(this);
	    final int x = offset;
	    offset += wiwidth;
	    this.setWidth(this.xoffset = offset);
	    rword.setOrigin(x, this.baseLineOffset - rword.ascentPlusLeading);
	}

	/**
	 * Rearrange line elements based on a new line height and alignment provided.
	 * All line elements are expected to have bounds preset.
	 *
	 * @param newHeight a {@link java.lang.Integer} object.
	 * @param elementHeight a {@link java.lang.Integer} object.
	 * @param valign a {@link java.lang.Integer} object.
	 */
	private void adjustHeight(final int newHeight, final int elementHeight, final int valign) {
		// Set new line height
		// int oldHeight = this.height;
		this.setHeight(newHeight);
		final List<Renderable> renderables = this.renderables;
		// Find max baseline
		final FontMetrics firstFm = ((RenderState)this.modelNode.getRenderState()).getFontMetrics();
		int maxDescent = firstFm.getDescent();
		int maxAscentPlusLeading = firstFm.getAscent() + firstFm.getLeading();
		for (final Object r : renderables) {
			if (r instanceof RStyleChanger rstyleChanger) {
                final FontMetrics fm = ((RenderState)rstyleChanger.getModelNode().getRenderState()).getFontMetrics();
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
		final int baseline;
		final AlignValues key = AlignValues.get(valign);
        baseline = switch (key) {
            case ABSBOTTOM -> newHeight - maxDescent;
            case ABSMIDDLE -> (newHeight + textHeight) / 2 - maxDescent;
            case MIDDLE -> newHeight / 2;
            case TOP -> maxAscentPlusLeading;
            default -> elementHeight;
        };
		this.baseLineOffset = baseline;

		// Change bounds of renderables accordingly
		for (final Object r : renderables) {
            switch (r) {
                case RWord rword -> rword.setY(baseline - rword.ascentPlusLeading);
                case RBlank rblank -> rblank.setY(baseline - rblank.ascentPlusLeading);
                case RElement relement -> setElementY(relement, relement.getHeight(), relement.getVAlign());
                case null, default -> {
                    // RSpacing and RStyleChanger don't matter?
                }
            }
		}
		// TODO: Could throw OverflowException when we add floating widgets
	}

	/**
	 * This method should only be invoked when the line has no items yet.
	 *
	 * @param x a {@link java.lang.Integer} object.
	 * @param desiredMaxWidth a {@link java.lang.Integer} object.
	 */
	public void changeLimits(final int x, final int desiredMaxWidth) {
		this.setX(x);
		this.desiredMaxWidth = desiredMaxWidth;
	}

	/** {@inheritDoc} */
	@Override
	public boolean extractSelectionText(final StringBuilder buffer, final boolean inSelection, final RenderableSpot startPoint,
										final RenderableSpot endPoint) {
		final boolean result = super.extractSelectionText(buffer, inSelection, startPoint, endPoint);
		if (result) {
			final LineBreak br = this.lineBreak;
			if (br != null) {
				buffer.append(System.lineSeparator());
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
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getBaselineOffset() {
		return this.baseLineOffset;
	}

	/** {@inheritDoc} */
	@Override
	public Color getBlockBackgroundColor() {
		return this.container.getPaintedBackgroundColor();
	}

	/** {@inheritDoc} */
	@Override
	public RenderableSpot getLowestRenderableSpot(final int x, final int y) {
		final Renderable[] rarray = this.renderables.toArray(Renderable.EMPTY_ARRAY);
		final BoundableRenderable br = MarkupUtilities.findRenderable(rarray, x, y, false);
		if (br != null) {
			final Rectangle rbounds = br.getVisualBounds();
			return br.getLowestRenderableSpot(x - rbounds.x, y - rbounds.y);
		} else {
			return new RenderableSpot(this, x, y);
		}
	}

	/** {@inheritDoc} */
	@Override
	public List<Renderable> getRenderables() {
		return this.renderables;
	}

	/** {@inheritDoc} */
	@Override
	protected void invalidateLayoutLocal() {
		// Workaround for fact that RBlockViewport does not
		// get validated or invalidated.
		this.layoutUpTreeCanBeInvalidated = true;
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
	public boolean onDoubleClick(final MouseEvent event, final int x, final int y) {
		final Renderable[] rarray = this.renderables.toArray(Renderable.EMPTY_ARRAY);
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
	public boolean onMouseClick(final MouseEvent event, final int x, final int y) {
		final Renderable[] rarray = this.renderables.toArray(Renderable.EMPTY_ARRAY);
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
	public boolean onMouseDisarmed(final MouseEvent event) {
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
	public boolean onMousePressed(final MouseEvent event, final int x, final int y) {
		final Renderable[] rarray = this.renderables.toArray(Renderable.EMPTY_ARRAY);
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
	public boolean onMouseReleased(final MouseEvent event, final int x, final int y) {
		final Renderable[] rarray = this.renderables.toArray(Renderable.EMPTY_ARRAY);
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
	public void paint(final Graphics g) {
		final RenderState rs = (RenderState)this.modelNode.getRenderState();
		if (rs != null && rs.getVisibility() == RenderState.VISIBILITY_VISIBLE) {
			final Color textColor = rs.getColor();
			g.setColor(textColor);
			final Font font = rs.getFont();
			g.setFont(font);
			final List<Renderable> renderables = this.renderables;
			renderables.forEach(r -> {
				if (r instanceof RElement relement) {
                    if (!relement.isDelegated()) {
						final Graphics newG = g.create();
						newG.translate(relement.getX(), relement.getY());
						try {
							relement.paint(newG);
						} finally {
							newG.dispose();
						}
					}

				} else if (r instanceof BoundableRenderable br) {
                    if (!br.isDelegated()) br.paintTranslated(g);
				} else {
					r.paint(g);
				}
			});

		}
	}

	/**
	 * <p>Setter for the field allowOverflow.</p>
	 *
	 * @param flag a boolean.
	 */
	public void setAllowOverflow(final boolean flag) {
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
	 * @param relement a {@link RElement} object.
	 * @param elementHeight a {@link java.lang.Integer} object.
	 * @param valign a {@link java.lang.Integer} object.
	 */
	private void setElementY(final RElement relement, final int elementHeight, final int valign) {
		final int yoffset;
		final AlignValues key = AlignValues.get(valign);
        yoffset = switch (key) {
            case BOTTOM -> this.getHeight() - elementHeight;
            case MIDDLE -> (this.getHeight() - elementHeight) / 2;
            case TOP -> 0;
            default -> this.baseLineOffset - elementHeight;
        };
		relement.setY(yoffset);
	}

	/**
	 * <p>simplyAdd.</p>
	 *
	 * @param r a {@link org.loboevolution.html.renderer.Renderable} object.
	 */
	public final void simplyAdd(final Renderable r) {
		this.renderables.add(r);
	}

	/** {@inheritDoc} */
	@Override
	public Rectangle getClipBounds() {
		// TODO Auto-generated method stub
		return null;
	}
}
