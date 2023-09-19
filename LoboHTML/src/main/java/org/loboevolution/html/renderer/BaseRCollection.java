/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.html.renderer;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLLinkElementImpl;
import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Iterator;

abstract class BaseRCollection extends BaseBoundableRenderable implements RCollection {

	private BoundableRenderable renderableWithMouse = null;

	/**
	 * <p>Constructor for BaseRCollection.</p>
	 *
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param modelNode a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	public BaseRCollection(RenderableContainer container, ModelNode modelNode) {
		super(container, modelNode);
	}

	/** {@inheritDoc} */
	@Override
	public void blur() {
		final RCollection parent = this.parent;
		if (parent != null) {
			parent.focus();
		}
	}
	
	  /** {@inheritDoc} */
	  @Override
	  public Rectangle getClipBoundsWithoutInsets() {
	    return getClipBounds();
	  }

	/** {@inheritDoc} */
	@Override
	public boolean extractSelectionText(StringBuilder buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint) {
		Point checkPoint1 = null;
		Point checkPoint2 = null;
		if (!inSelection) {
			final boolean isStart = startPoint.renderable == this;
			final boolean isEnd = endPoint.renderable == this;
			if (isStart && isEnd) {
				checkPoint1 = startPoint.getPoint();
				checkPoint2 = endPoint.getPoint();
			} else if (isStart) {
				checkPoint1 = startPoint.getPoint();
			} else if (isEnd) {
				checkPoint1 = endPoint.getPoint();
			}
		} else {
			if (startPoint.renderable == this) {
				checkPoint1 = startPoint.getPoint();
			} else if (endPoint.renderable == this) {
				checkPoint1 = endPoint.getPoint();
			}
		}
		final Iterator<Renderable> i = getRenderables();
		if (i != null) {
			while (i.hasNext()) {
		        final Renderable rn = i.next();
		        final Renderable robj = (rn instanceof PositionedRenderable) ? ((PositionedRenderable) rn).getRenderable() : rn;
				if (robj instanceof BoundableRenderable) {
					final BoundableRenderable renderable = (BoundableRenderable) robj;
					if (!inSelection) {
						final Rectangle bounds = renderable.getVisualBounds();
						if (checkPoint1 != null && checkStartSelection(bounds, checkPoint1)) {
							if (checkPoint2 != null) {
								checkPoint1 = checkPoint2;
								checkPoint2 = null;
							} else {
								checkPoint1 = null;
							}
							inSelection = true;
						} else if (checkPoint2 != null && checkStartSelection(bounds, checkPoint2)) {
							checkPoint1 = null;
							checkPoint2 = null;
							inSelection = true;
						}
					} else if (inSelection && checkPoint1 != null
							&& checkEndSelection(renderable.getVisualBounds(), checkPoint1)) {
						return false;
					}
					final boolean newInSelection = renderable.extractSelectionText(buffer, inSelection, startPoint,
							endPoint);
					if (inSelection && !newInSelection) {
						return false;
					}
					inSelection = newInSelection;
				}
			}
		}
		if (inSelection && checkPoint1 != null) {
			return false;
		} else if (!inSelection && (checkPoint1 != null || checkPoint2 != null)
				&& !(checkPoint1 != null && checkPoint2 != null)) {
			// Has to have started not being in selection,
			// but we must start selecting without having
			// selected anything in the block then.
			return true;
		}
		return inSelection;
	}

	/** {@inheritDoc} */
	@Override
	public void focus() {
		this.container.focus();
	}

	/** {@inheritDoc} */
	public BoundableRenderable getRenderable(int x, int y) {
		final Iterator<Renderable> i = getRenderables();
		if (i != null) {
			while (i.hasNext()) {
				final Renderable rn = i.next();
				final Renderable r = (rn instanceof PositionedRenderable) ? ((PositionedRenderable) rn).getRenderable() : rn;
				if (r instanceof BoundableRenderable) {
					final BoundableRenderable br = (BoundableRenderable) r;
					if (br instanceof RBlockViewport) {
						return br;
					}
					if ((!br.isDelegated()) && br.contains(x, y)) {
						return br;
					}
				}
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void invalidateLayoutDeep() {
		// TODO: May be pretty inefficient in RLine's
		// if it's true that non-layable components
		// are not in RLine's anymore.
		invalidateLayoutLocal();
		final Iterator<Renderable> renderables = getRenderables();
		if (renderables != null) {
			while (renderables.hasNext()) {
		        final Renderable rn = renderables.next();
		        final Renderable r = (rn instanceof PositionedRenderable) ? ((PositionedRenderable) rn).getRenderable() : rn;
				if (r instanceof RCollection) {
					((RCollection) r).invalidateLayoutDeep();
				}
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void onMouseMoved(final MouseEvent event, int x, int y, boolean triggerEvent, ModelNode limit) {
		super.onMouseMoved(event, x, y, triggerEvent, limit);
		final BoundableRenderable oldRenderable = this.renderableWithMouse;
		final BoundableRenderable newRenderable = getRenderable(x, y);
		ModelNode newLimit;
		if (isContainedByNode()) {
			newLimit = this.modelNode;
		} else {
			newLimit = limit;
		}
		final boolean changed = oldRenderable != newRenderable;
		if (changed) {
			if (oldRenderable != null) {
				oldRenderable.onMouseOut(event, x - oldRenderable.getX(), y - oldRenderable.getY(), newLimit);
			}
			this.renderableWithMouse = newRenderable;
		}
		// Must recurse always
		if (newRenderable != null) {
			newRenderable.onMouseMoved(event, x - newRenderable.getX(), y - newRenderable.getY(), changed, newLimit);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void onMouseOut(final MouseEvent event, int x, int y, ModelNode limit) {
		super.onMouseOut(event, x, y, limit);
		final BoundableRenderable oldRenderable = this.renderableWithMouse;
		if (oldRenderable != null) {
			this.renderableWithMouse = null;
			ModelNode newLimit;
			if (isContainedByNode()) {
				newLimit = this.modelNode;
			} else {
				newLimit = limit;
			}
			oldRenderable.onMouseOut(event, x - oldRenderable.getX(), y - oldRenderable.getY(), newLimit);
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean onRightClick(final MouseEvent event, int x, int y) {
		final BoundableRenderable br = getRenderable(x, y);
		if (br == null) {
			return HtmlController.getInstance().onContextMenu(this.modelNode, event, x, y);
		} else {
			return br.onRightClick(event, x - br.getX(), y - br.getY());
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		// TODO: Does this work with renderables that are absolutely positioned?
		Point checkPoint1 = null;
		Point checkPoint2 = null;
		if (!inSelection) {
			final boolean isStart = startPoint.renderable == this;
			final boolean isEnd = endPoint.renderable == this;
			if (isStart && isEnd) {
				checkPoint1 = startPoint.getPoint();
				checkPoint2 = endPoint.getPoint();
			} else if (isStart) {
				checkPoint1 = startPoint.getPoint();
			} else if (isEnd) {
				checkPoint1 = endPoint.getPoint();
			}
		} else {
			if (startPoint.renderable == this) {
				checkPoint1 = startPoint.getPoint();
			} else if (endPoint.renderable == this) {
				checkPoint1 = endPoint.getPoint();
			}
		}
		final Iterator<Renderable> i = getRenderables();
		if (i != null) {
			while (i.hasNext()) {
		        final Renderable rn = i.next();
		        final Renderable robj = (rn instanceof PositionedRenderable) ? ((PositionedRenderable) rn).getRenderable() : rn;
				if (robj instanceof BoundableRenderable) {
					final BoundableRenderable renderable = (BoundableRenderable) robj;
					final Rectangle bounds = renderable.getVisualBounds();
					if (!inSelection) {
						if (checkPoint1 != null && checkStartSelection(bounds, checkPoint1)) {
							if (checkPoint2 != null) {
								checkPoint1 = checkPoint2;
								checkPoint2 = null;
							} else {
								checkPoint1 = null;
							}
							inSelection = true;
						} else if (checkPoint2 != null && checkStartSelection(bounds, checkPoint2)) {
							checkPoint1 = null;
							checkPoint2 = null;
							inSelection = true;
						}
					} else if (inSelection && checkPoint1 != null && checkEndSelection(bounds, checkPoint1)) {
						return false;
					}
					final int offsetX = bounds.x;
					final int offsetY = bounds.y;
					g.translate(offsetX, offsetY);
					try {
						final boolean newInSelection = renderable.paintSelection(g, inSelection, startPoint, endPoint);
						if (inSelection && !newInSelection) {
							return false;
						}
						inSelection = newInSelection;
					} finally {
						g.translate(-offsetX, -offsetY);
					}
				}
			}
		}
		if (inSelection && checkPoint1 != null) {
			return false;
		} else if (!inSelection && (checkPoint1 != null || checkPoint2 != null)
				&& !(checkPoint1 != null && checkPoint2 != null)) {
			// Has to have started not being in selection,
			// but we must start selecting without having
			// selected anything in the block then.
			return true;
		}
		return inSelection;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Updates bounds of all descendent's GUI components, based on root bounds.
	 */
	@Override
	public void updateWidgetBounds(int guiX, int guiY) {
		final Iterator<Renderable> i = getRenderables();
		if (i != null) {
			while (i.hasNext()) {
		        final Renderable rn = i.next();
		        final Renderable r = (rn instanceof PositionedRenderable) ? ((PositionedRenderable) rn).getRenderable() : rn;
				if (r instanceof RCollection) {
					// RUIControl is a RCollection too.
					final RCollection rc = (RCollection) r;
					rc.updateWidgetBounds(guiX + rc.getX(), guiY + rc.getY());
				}
			}
		}
	}


	/**
	 * <p>getDeclaredWidthImpl.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param availWidth a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getDeclaredWidthImpl(HTMLElementImpl element, int availWidth) {
		HTMLDocumentImpl doc = (HTMLDocumentImpl) element.getDocumentNode();
		CSSStyleDeclaration props = element.getCurrentStyle();
		RenderState renderState = element.getRenderState();
		if (props == null) {
			return -1;
		}
		String widthText = props.getWidth();
		final String textContent = element.getTextContent();

		if ("inherit".equalsIgnoreCase(widthText)) {
			widthText = element.getParentStyle().getWidth();
		} else if ("initial".equalsIgnoreCase(widthText)) {
			widthText = "100%";
		}

		int width = -1;

		if(element instanceof HTMLLinkElementImpl && Strings.isNotBlank(textContent)) {
			width = (int) (Strings.texMeasure(textContent,  renderState.getFont()).getWidth()) + 5;
		}

		if (Strings.isNotBlank(widthText)) {
			width = HtmlValues.getPixelSize(widthText, renderState, doc.getDefaultView(), -1, availWidth);
		}

		if (width == -1 && Strings.isNotBlank(textContent) && (renderState.getPosition() == RenderState.POSITION_ABSOLUTE || renderState.getDisplay() == RenderState.DISPLAY_INLINE_BLOCK)) {
			HtmlInsets paddingInsets = renderState.getPaddingInsets();
			HtmlInsets marginInsets = renderState.getMarginInsets();
			int right = 0;
			int left = 0;

			if (paddingInsets != null) {
				right = right + paddingInsets.getRight();
				left = left + paddingInsets.getLeft();
			}

			if (marginInsets != null) {
				right = right + marginInsets.getRight();
				left = left + marginInsets.getLeft();
			}
			width = (int) (Strings.texMeasure(textContent,  renderState.getFont()).getWidth() + right + left);
		}

		if (Strings.isNotBlank(props.getMaxWidth())) {
			int maxWidth = HtmlValues.getPixelSize(props.getMaxWidth(), renderState, doc.getDefaultView(), -1, availWidth);

			if (width == -1 || width > maxWidth) {
				width = maxWidth;
			}
		}

		if (Strings.isNotBlank(props.getMinWidth())) {
			if (width == -1 && "100%".equals(props.getMinWidth())) {width = element.getClientHeight();}
			int minWidth = HtmlValues.getPixelSize(props.getMinWidth(), element.getRenderState(), doc.getDefaultView(), 0, availWidth);
			if (width == 0 || width < minWidth) {
				width = minWidth;
			}
		}
		return width;
	}

	/**
	 * <p>getDeclaredHeightImpl.</p>
	 *
	 @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param availHeight a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getDeclaredHeightImpl(HTMLElementImpl element, int availHeight) {
		CSSStyleDeclaration props = element.getCurrentStyle();
		RenderState renderState = element.getRenderState();
		if (props == null) {
			return -1;
		}
		HTMLDocumentImpl doc = (HTMLDocumentImpl) element.getDocumentNode();
		String heightText = props.getHeight() != null ? props.getHeight() : "";

		switch (heightText.trim()) {
			case "inherit":
				heightText = element.getParentStyle().getHeight();
				break;
			case "initial":
				heightText = "100%";
				break;
			case "auto":
				if (renderState.getPosition() == RenderState.POSITION_ABSOLUTE) {
					heightText = "100%";
				}
				break;
			default:
				break;
		}

		int height = -1;

		if (Strings.isNotBlank(heightText)) {
			height = HtmlValues.getPixelSize(heightText, renderState, doc.getDefaultView(), -1, availHeight);
		}

		final String textContent = element.getTextContent();
		if (height == -1 && Strings.isNotBlank(textContent) && renderState.getPosition() == RenderState.POSITION_ABSOLUTE) {
			height = (int) Strings.texMeasure(textContent, renderState.getFont()).getHeight();
		}

		if (props.getMaxHeight() != null) {
			int maxHeight = HtmlValues.getPixelSize(props.getMaxHeight(), renderState, doc.getDefaultView(), -1, availHeight);
			if (height == 0 || height > maxHeight) {
				height = maxHeight;
			}
		}

		if (props.getMinHeight() != null) {
			if (height == -1 && "100%".equals(props.getMinHeight())) {height = element.getClientHeight();}
			int minHeight = HtmlValues.getPixelSize(props.getMinHeight(), renderState, doc.getDefaultView(), -1, availHeight);
			if (height == 0 || height < minHeight) {
				height = minHeight;
			}
		}
		return height;
	}
	
	private boolean checkEndSelection(Rectangle bounds, Point selectionPoint) {
		if (bounds.y > selectionPoint.y) {
			return true;
		} else if (selectionPoint.y >= bounds.y && selectionPoint.y < bounds.y + bounds.height
				&& selectionPoint.x < bounds.x) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkStartSelection(Rectangle bounds, Point selectionPoint) {
		if (bounds.y > selectionPoint.y) {
			return true;
		} else return selectionPoint.y >= bounds.y && selectionPoint.y < bounds.y + bounds.height
				&& bounds.x > selectionPoint.x;
	}
}
