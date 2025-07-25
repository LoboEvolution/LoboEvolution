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
package org.loboevolution.html.renderer;

import lombok.Getter;
import lombok.Setter;
import org.loboevolution.html.dom.HTMLBodyElement;
import org.loboevolution.html.dom.HTMLHtmlElement;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.ModelNode;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.renderer.info.RBlockInfo;
import org.loboevolution.html.renderer.info.RBlockLayoutInfo;
import org.loboevolution.html.renderer.info.RLayoutInfo;
import org.loboevolution.html.renderstate.BlockRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.RenderThreadState;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.info.FloatingInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a HTML block in a rendered document, typically a DIV. The root
 * renderer node is of this type as well.
 * <p>
 * Immediately below an RBlock you will find a node of type
 * {@link org.loboevolution.html.renderer.RBlockViewport}.
 */
public class RBlock extends BaseElementRenderable {

	private BoundableRenderable armedRenderable;
	protected final RBlockViewport bodyLayout;
	@Getter
	private final RBlockScroll scroll;

	@Setter
	protected int defaultOverflowX = RenderState.OVERFLOW_NONE;

	@Setter
	protected int defaultOverflowY = RenderState.OVERFLOW_NONE;
	protected RenderableSpot endSelection;
	protected final FrameContext frameContext;
	protected boolean hasHScrollBar = false;

	protected boolean hasVScrollBar = false;
	protected final int listNesting;

	protected final HtmlRendererContext rendererContext;

	protected RenderableSpot startSelection;

	/**
	 * <p>Constructor for RBlock.</p>
	 *
	 * @param info a {@link org.loboevolution.html.renderer.info.RBlockInfo} object.
	 */
	public RBlock(final RBlockInfo info) {
		super(info.getParentContainer(), info.getModelNode(), info.getPcontext());
		this.listNesting = info.getListNesting();
		this.frameContext = info.getFrameContext();
		this.rendererContext = info.getRcontext();
		this.scroll = new RBlockScroll(this);
		this.bodyLayout = new RBlockViewport(info, this, this);
		this.bodyLayout.setOriginalParent(this);
		this.bodyLayout.setX(Short.MAX_VALUE);
		this.bodyLayout.setY(Short.MAX_VALUE);
	}

	/** {@inheritDoc} */
	@Override
	protected void clearStyle(final boolean isRootBlock) {
		super.clearStyle(isRootBlock);
		this.overflowX = this.defaultOverflowX;
		this.overflowY = this.defaultOverflowY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void doLayout(final int availWidth, final int availHeight, final boolean sizeOnly) {
		this.doLayout(RLayoutInfo.builder()
				.availWidth(availWidth)
				.availHeight(availHeight)
				.expandWidth(true)
				.expandHeight(false)
				.blockFloatBoundsSource(null)
				.defaultOverflowX(defaultOverflowX)
				.defaultOverflowY(defaultOverflowY)
				.sizeOnly(sizeOnly)
				.build());
	}

	/**
	 * Lays out and sets dimensions only if RBlock is invalid (or never before layed
	 * out), if the parameters passed differ from the last layout, or if the current
	 * font differs from the font for the last layout.
	 *
	 * @param info a {@link org.loboevolution.html.renderer.info.RLayoutInfo} object.
	 */
	public void doLayout(final RLayoutInfo info) {

		final RenderState renderState = (RenderState)this.modelNode.getRenderState();
        final RBlockLayoutInfo value = forceLayout(renderState, info);
		this.setWidth(value.getWidth());
		this.setHeight(value.getHeight());
		this.hasHScrollBar = value.isHasHScrollBar();
		this.hasVScrollBar = value.isHasVScrollBar();

		sendGUIComponentsToParent();

		sendDelayedPairsToParent();
	}

	/**
	 * <p>layout.</p>
	 *
	 * @param info a {@link org.loboevolution.html.renderer.info.RLayoutInfo} object.
	 */
	public void layout(final RLayoutInfo info) {
		try {
			this.doLayout(info);
		} finally {
			this.layoutUpTreeCanBeInvalidated = true;
			this.layoutDeepCanBeInvalidated = true;
		}
	}

	/**
	 * Lays out the block without checking for prior dimensions.
	 */
	private RBlockLayoutInfo forceLayout(final RenderState renderState, final RLayoutInfo info) {

		final int availWidth = info.getAvailWidth();
		final int availHeight = info.getAvailHeight();
		final boolean expandWidth = info.isExpandWidth();
		final boolean expandHeight = modelNode instanceof HTMLHtmlElement || modelNode instanceof HTMLBodyElement || info.isExpandHeight();
		final boolean sizeOnly = info.isSizeOnly();
		final FloatingBoundsSource blockFloatBoundsSource = info.getBlockFloatBoundsSource();

		RenderState rs = renderState;
		if (rs == null) {
			rs = new BlockRenderState(null);
		}

		this.applyStyle(availWidth, availHeight);

		final RBlockViewport bodyLayout = this.bodyLayout;
		final NodeImpl node = (NodeImpl) this.modelNode;
		if (node == null) {
			final Insets insets = this.getInsetsMarginBorder(false, false);
			return RBlockLayoutInfo.builder().
					width(insets.left + insets.right).
					height(insets.bottom + insets.top).
					hasHScrollBar(false).
					hasVScrollBar(false).
					build();
		}

        final Insets paddingInsets = (this.paddingInsets == null) ? RBlockViewport.ZERO_INSETS : this.paddingInsets;
        final Insets borderInsets = (this.borderInsets == null) ? RBlockViewport.ZERO_INSETS : this.borderInsets;
        final Insets marginInsets = (this.marginInsets == null) ? RBlockViewport.ZERO_INSETS : this.marginInsets;

		final int paddingTotalWidth = paddingInsets.left + paddingInsets.right;
		final int paddingTotalHeight = paddingInsets.top + paddingInsets.bottom;

        final int overflowX = (this.overflowX == RenderState.OVERFLOW_NONE) ? defaultOverflowX : this.overflowX;
        final int overflowY = (this.overflowY == RenderState.OVERFLOW_NONE) ? defaultOverflowY : this.overflowY;

		final boolean vauto = overflowY == RenderState.OVERFLOW_AUTO;
		boolean hscroll = overflowX == RenderState.OVERFLOW_SCROLL;
		final boolean hauto = overflowX == RenderState.OVERFLOW_AUTO;
		boolean vscroll = overflowY == RenderState.OVERFLOW_SCROLL;

		Insets insets = this.getInsetsMarginBorder(hscroll, vscroll);
		int insetsTotalWidth = insets.left + insets.right;
		int insetsTotalHeight = insets.top + insets.bottom;
		int tentativeAvailWidth = availWidth - paddingTotalWidth - insetsTotalWidth;
		int tentativeAvailHeight = availHeight - paddingTotalHeight - insetsTotalHeight;

		final Integer declaredMaxWidth = getDeclaredMaxWidth(renderState, tentativeAvailWidth);
		final Integer declaredMaxHeight = getDeclaredMaxHeight(renderState, tentativeAvailHeight);
		if (declaredMaxWidth != null) {
			tentativeAvailWidth = Math.min(tentativeAvailWidth, declaredMaxWidth);
		}

		final boolean isHtmlElem = getModelNode() instanceof HTMLHtmlElement;
		int actualAvailWidth = tentativeAvailWidth;

        final Integer dw = this.getDeclaredWidth(actualAvailWidth);
		final Integer dh = this.getDeclaredHeight(tentativeAvailHeight);
		int declaredWidth = dw == null ? -1 : dw;
		int declaredHeight = dh == null ? -1 : dh;

		final Integer declaredMinWidth = getDeclaredMinWidth(renderState, tentativeAvailWidth);
		if ((declaredMinWidth != null) && declaredMinWidth > 0) {
			declaredWidth = dw == null ? declaredMinWidth : Math.max(declaredWidth, declaredMinWidth);
		}

		final Integer declaredMinHeight = getDeclaredMinHeight(renderState, tentativeAvailHeight);
		if ((declaredMinHeight != null) && declaredMinHeight > 0) {
			declaredHeight = dh == null ? declaredMinHeight : Math.max(declaredHeight, declaredMinHeight);
		}

		this.clearGUIComponents();

		int tentativeWidth;
		int tentativeHeight;


        if ("border-box".equals(rs.getBoxSizing())) {
            tentativeWidth = declaredWidth == -1 ? availWidth : declaredWidth;
            tentativeHeight = declaredHeight == -1 ? availHeight : declaredHeight;
        } else {
            tentativeWidth = declaredWidth == -1 ? availWidth : declaredWidth + insetsTotalWidth + paddingTotalWidth;
            tentativeHeight = declaredHeight == -1 ? availHeight : declaredHeight + insetsTotalHeight + paddingTotalHeight;
        }

		if ((declaredWidth == -1) && !expandWidth && (availWidth > (insetsTotalWidth + paddingTotalWidth))) {
			final RenderThreadState state = RenderThreadState.getState();
			final boolean prevOverrideNoWrap = state.overrideNoWrap;
			if (!prevOverrideNoWrap) {
				state.overrideNoWrap = true;
				try {
                    bodyLayout.layout(paddingTotalWidth, paddingTotalHeight, paddingInsets, -1, null, true);
					if ((bodyLayout.getWidth() + insetsTotalWidth) < tentativeWidth) {
						tentativeWidth = bodyLayout.getWidth() + insetsTotalWidth;
						tentativeHeight = bodyLayout.getHeight() + insetsTotalHeight;
					}
				} finally {
					state.overrideNoWrap = false;
				}
			}
		}

		if (declaredMinWidth != null) {
			tentativeWidth = Math.max(tentativeWidth, declaredMinWidth);
		}

		if (declaredMinHeight != null) {
			tentativeHeight = Math.max(tentativeHeight, declaredMinHeight);
		}

		FloatingBounds viewportFloatBounds = null;
		FloatingBounds blockFloatBounds = null;
		if (blockFloatBoundsSource != null) {
			blockFloatBounds = blockFloatBoundsSource.getChildBlockFloatingBounds(tentativeWidth);
			viewportFloatBounds = new ShiftedFloatingBounds(blockFloatBounds, -insets.left, -insets.right, -insets.top);
		}
		if (declaredMaxWidth != null) {
			tentativeWidth = Math.min(tentativeWidth, declaredMaxWidth + insetsTotalWidth + paddingTotalWidth);
		}
		int desiredViewportWidth = tentativeWidth - insetsTotalWidth;
		final int desiredViewportHeight = tentativeHeight - insets.top - insets.bottom;
		final int maxY = vauto ? (declaredHeight == -1 ? availHeight : declaredHeight + paddingInsets.top) : -1;
		try {
			bodyLayout.layout(desiredViewportWidth, desiredViewportHeight, paddingInsets, maxY, viewportFloatBounds, sizeOnly);
		} catch (final SizeExceededException see) {
			vscroll = true;
			insets = this.getInsetsMarginBorder(hscroll, vscroll);
			insetsTotalWidth = insets.left + insets.right;
			actualAvailWidth = availWidth - paddingTotalWidth - insetsTotalWidth;
			final Integer dwNew = this.getDeclaredWidth(actualAvailWidth);
			declaredWidth = dwNew == null ? -1 : dwNew;
			desiredViewportWidth = tentativeWidth - insetsTotalWidth;
			if (blockFloatBounds != null) {
				viewportFloatBounds = new ShiftedFloatingBounds(blockFloatBounds, -insets.left, -insets.right, -insets.top);
			}
			bodyLayout.layout(desiredViewportWidth, desiredViewportHeight, paddingInsets, -1, viewportFloatBounds, sizeOnly);
		}

		if (marginInsets != this.marginInsets) {
			// Can happen because of margin top being absorbed from child
			insets = this.getInsetsMarginBorder(hscroll, vscroll);
			insetsTotalHeight = insets.top + insets.bottom;
		}

		final int bodyWidth = bodyLayout.getWidth();
		final int bodyHeight = bodyLayout.getHeight();

		if ((declaredHeight == -1) && (bodyHeight == 0)) {
			if ((paddingInsets.top == 0) && (paddingInsets.bottom == 0) && (borderInsets.top == 0) && (borderInsets.bottom == 0)) {
				final Insets mi = this.marginInsets;
				if (mi != null) {
					mi.top = Math.max(mi.top, mi.bottom);
					mi.bottom = 0;
					insets = this.getInsetsMarginBorder(hscroll, vscroll);
					insetsTotalHeight = insets.top + insets.bottom;
				}
			}
		}

		int prelimBlockWidth = bodyWidth + insetsTotalWidth;
		int prelimBlockHeight = bodyHeight + insetsTotalHeight;

        final int adjDeclaredWidth;
        final int adjDeclaredHeight;

        if ("border-box".equals(rs.getBoxSizing())) {
            adjDeclaredWidth = declaredWidth;
            adjDeclaredHeight = declaredHeight;
        } else {
            adjDeclaredWidth = declaredWidth == -1 ? -1 : declaredWidth + insets.left + insets.right + paddingInsets.left + paddingInsets.right;
            adjDeclaredHeight = declaredHeight == -1 ? -1 : declaredHeight + insets.top + insets.bottom + paddingInsets.top + paddingInsets.bottom;
        }

		// Adjust insets and other dimensions base on overflow-y=auto.
		if (hauto && (((adjDeclaredWidth != -1) && (prelimBlockWidth > adjDeclaredWidth)) || (prelimBlockWidth > tentativeWidth))) {
			hscroll = true;
			insets = this.getInsetsMarginBorder(hscroll, vscroll);
			insetsTotalHeight = insets.top + insets.bottom;
			prelimBlockHeight = bodyHeight + insetsTotalHeight;
		}

		if ((vauto || vscroll) && ((prelimBlockHeight - insetsTotalHeight) < bodyLayout.getVisualHeight())) {
			if (isHtmlElem) {
				prelimBlockHeight = bodyLayout.getVisualHeight() + insetsTotalHeight;
			} else {
				vscroll = true;
				insets = this.getInsetsMarginBorder(hscroll, vscroll);
				insetsTotalWidth = insets.left + insets.right;
			}
		}

		int resultingWidth;
		int resultingHeight;
		if (adjDeclaredWidth == -1) {
			resultingWidth = expandWidth ? Math.max(prelimBlockWidth, tentativeWidth) : prelimBlockWidth;
			if ((tentativeWidth > 0) && hscroll && (resultingWidth > tentativeWidth)) {
				resultingWidth = Math.max(tentativeWidth, SCROLL_BAR_THICKNESS);
			}
		} else {
			resultingWidth = adjDeclaredWidth;
		}
		if (!sizeOnly) {
			final int alignmentXPercent = rs.getAlignXPercent();
			if (alignmentXPercent > 0) {
				final int canvasWidth = Math.max(bodyLayout.getWidth(), resultingWidth - insets.left - insets.right);
				bodyLayout.alignX(alignmentXPercent, canvasWidth, paddingInsets);
			}
		}

		if (adjDeclaredHeight == -1) {
			resultingHeight = expandHeight ? Math.max(prelimBlockHeight, tentativeHeight) : prelimBlockHeight;
			if (vscroll && (resultingHeight > tentativeHeight)) {
				resultingHeight = Math.max(tentativeHeight, SCROLL_BAR_THICKNESS);
			}
		} else {
			resultingHeight = adjDeclaredHeight;
		}
		if (!sizeOnly) {
			final int alignmentYPercent = rs.getAlignYPercent();
			if (alignmentYPercent > 0) {
				final int canvasHeight = Math.max(bodyLayout.getHeight(), resultingHeight - insets.top - insets.bottom);
				bodyLayout.alignY(alignmentYPercent, canvasHeight, paddingInsets);
			}
		}

		final int scrollWidth = vscroll ? SCROLL_BAR_THICKNESS : 0;
		if (declaredWidth >= 0) {
			resultingWidth = Math.min(resultingWidth, declaredWidth + paddingTotalWidth + insetsTotalWidth - scrollWidth);
		}

		if (declaredMaxWidth != null) {
			resultingWidth = Math.min(resultingWidth, declaredMaxWidth + paddingTotalWidth + insetsTotalWidth - scrollWidth);
		}

		final int scrollHeight = hscroll ? SCROLL_BAR_THICKNESS : 0;
		if (declaredHeight >= 0) {
			resultingHeight = Math.min(resultingHeight, declaredHeight + paddingTotalHeight + insetsTotalHeight - scrollHeight);
		}

		if (declaredMaxHeight != null) {
			resultingHeight = Math.min(resultingHeight, declaredMaxHeight + paddingTotalHeight + insetsTotalHeight - scrollHeight);
		}

		if (renderState.getPosition() == RenderState.POSITION_STATIC || renderState.getPosition() == RenderState.POSITION_RELATIVE) {
			final Dimension changes = this.applyAutoStyles(availWidth - resultingWidth);
			if (changes != null) {
				resultingWidth += changes.width;
				resultingHeight += changes.height;
			}
		}

		insets = getInsetsMarginBorder(hscroll, vscroll);

		if (hscroll || vscroll) {
			this.correctViewportOrigin(insets, resultingWidth, resultingHeight);
			this.setWidth(resultingWidth);
			this.setHeight(resultingHeight);
			scroll.resetScrollBars(rs);
		} else {
			bodyLayout.setX(insets.left);
			bodyLayout.setY(insets.top);
		}

		return RBlockLayoutInfo.builder().
				width(resultingWidth).
				height(resultingHeight).
				hasHScrollBar(hscroll).
				hasVScrollBar(vscroll).
				build();
	}

	private Dimension applyAutoStyles(final int availWidth) {
		final Object rootNode = this.modelNode;
		final HTMLElementImpl rootElement;
		if (rootNode instanceof HTMLDocumentImpl doc) {
            rootElement = (HTMLElementImpl) doc.getBody();
		} else {
			if (rootNode instanceof HTMLElementImpl) {
				rootElement = (HTMLElementImpl) rootNode;
			} else {
				rootElement = null;
			}
		}
		if (rootElement == null) {
			return null;
		}
		final RenderState rs = rootElement.getRenderState();
		final Dimension changes = new Dimension();
		final HtmlInsets minsets = rs.getMarginInsets();
		if (minsets != null) {
			if (availWidth > 1) {
                if (minsets.getLeftType() == HtmlInsets.TYPE_AUTO) {
					this.marginInsets.left = availWidth;
					changes.width += availWidth;
				}
				if (minsets.getRightType() == HtmlInsets.TYPE_AUTO) {
					this.marginInsets.right = availWidth;
					changes.width += availWidth;
				}
			}
		}
		return changes;
	}

	/**
	 * <p>ensureVisible.</p>
	 *
	 * @param point a {@link java.awt.Point} object.
	 */
	public void ensureVisible(final Point point) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			final boolean hscroll = this.hasHScrollBar;
			final boolean vscroll = this.hasVScrollBar;
			final int origX = bodyLayout.getX();
			final int origY = bodyLayout.getY();
			final Insets insets = getInsetsMarginBorder(hscroll, vscroll);

			if (hscroll) {
				if (point.x < insets.left) {
					bodyLayout.setX(bodyLayout.getX() + insets.left - point.x);
				} else if (point.x > this.getWidth() - insets.right) {
					bodyLayout.setX(bodyLayout.getX() + point.x - this.getWidth() + insets.right);
				}
			}
			if (vscroll) {
				if (point.y < insets.top) {
					bodyLayout.setY(bodyLayout.getY() + insets.top - point.y);
				} else if (point.y > this.getHeight() - insets.bottom) {
					bodyLayout.setY(bodyLayout.getY() - point.y - this.getHeight() + insets.bottom);
				}
			}
			if (hscroll || vscroll) {
				correctViewportOrigin(insets, this.getWidth(), this.getHeight());
				if (origX != bodyLayout.getX() || origY != bodyLayout.getY()) {
					scroll.resetScrollBars(null);
					this.repaint();
				}
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean extractSelectionText(final StringBuilder buffer, final boolean inSelection, final RenderableSpot startPoint,
										final RenderableSpot endPoint) {
		final boolean result = super.extractSelectionText(buffer, inSelection, startPoint, endPoint);
		final String br = System.lineSeparator();
		if (inSelection) {
			buffer.insert(0, br);
		}
		if (result) {
			buffer.append(br);
		}
		return result;
	}

	/**
	 * <p>getExportableFloatingInfo.</p>
	 *
	 * @return a {@link org.loboevolution.info.FloatingInfo} object.
	 */
	public FloatingInfo getExportableFloatingInfo() {
		final FloatingInfo info = this.bodyLayout.getExportableFloatingInfo();
		if (info == null) {
			return null;
		}
		final Insets insets = getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
		final FloatingInfo fInfo = new FloatingInfo();
		fInfo.setShiftX(info.getShiftX() + insets.left);
		fInfo.setShiftY(info.getShiftY() + insets.top);
		fInfo.setFloats(info.getFloats());
		return fInfo;
	}

	/**
	 * <p>getFirstBaselineOffset.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getFirstBaselineOffset() {
		return this.bodyLayout.getFirstBaselineOffset();
	}

	/**
	 * <p>getFirstLineHeight.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getFirstLineHeight() {
		return this.bodyLayout.getFirstLineHeight();
	}


	/** {@inheritDoc} */
	@Override
	public RenderableSpot getLowestRenderableSpot(final int x, final int y) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			final Insets insets = getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
			if ((x - relativeOffsetX > insets.left) && (x - relativeOffsetX < (this.getWidth() - insets.right))
					&& (y - relativeOffsetY > insets.top) && (y - relativeOffsetY < (this.getHeight() - insets.bottom))) {
				return bodyLayout.getLowestRenderableSpot(x - relativeOffsetX - bodyLayout.getX(),
						y - relativeOffsetY - bodyLayout.getY());
			} else {
				return new RenderableSpot(this, x - relativeOffsetX, y - relativeOffsetY);
			}
		} else {
			return new RenderableSpot(this, x - relativeOffsetX, y - relativeOffsetY);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Color getPaintedBackgroundColor() {
		return this.backgroundColor;
	}

	/**
	 * <p>getRBlockViewport.</p>
	 *
	 * @return a {@link org.loboevolution.html.renderer.RBlockViewport} object.
	 */
	public RBlockViewport getRBlockViewport() {
		return this.bodyLayout;
	}

	/** {@inheritDoc} */
	@Override
	public List<Renderable> getRenderables() {
		final RBlockViewport bodyLayout = this.bodyLayout;
		final List<Renderable> renderables = new ArrayList<>();
		renderables.add(bodyLayout);
		return renderables;
	}

	/**
	 * <p>getViewportListNesting.</p>
	 *
	 * @param blockNesting a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getViewportListNesting(final int blockNesting) {
		return blockNesting;
	}


	/**
	 * Gets the width the vertical scrollbar has when shown.
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getVScrollBarWidth() {
		return SCROLL_BAR_THICKNESS;
	}

	/**
	 * {@inheritDoc}
	 *
	 * RBlocks should only be invalidated if one of their properties change, or if a
	 * descendent changes, or if a style property of an ancestor is such that it
	 * could produce layout changes in this RBlock.
	 */
	@Override
	public void invalidateLayoutLocal() {
		super.invalidateLayoutLocal();
		scroll.invalidateLayoutLocal();
	}

	/** {@inheritDoc} */
	@Override
	public int getX() {
		return super.getX() + relativeOffsetX;
	}

	/** {@inheritDoc} */
	@Override
	public int getY() {
		return super.getY() + relativeOffsetY;
	}

	/** {@inheritDoc} */
	@Override
	public int getVisualWidth() {
		return Math.max(super.getVisualWidth(), bodyLayout.getVisualWidth());
	}

	/** {@inheritDoc} */
	@Override
	public int getVisualHeight() {
		return Math.max(super.getVisualHeight(), bodyLayout.getVisualHeight());
	}

	/**
	 * <p>isOverflowVisibleX.</p>
	 *
	 * @return a boolean.
	 */
	public final boolean isOverflowVisibleX() {
		final int overflow = this.overflowX;
		return overflow == RenderState.OVERFLOW_NONE || overflow == RenderState.OVERFLOW_VISIBLE;
	}

	/**
	 * <p>isOverflowVisibleY.</p>
	 *
	 * @return a boolean.
	 */
	public final boolean isOverflowVisibleY() {
		final int overflow = this.overflowY;
		return overflow == RenderState.OVERFLOW_NONE || overflow == RenderState.OVERFLOW_VISIBLE;
	}

	/** {@inheritDoc} */
	@Override
	public boolean onDoubleClick(final MouseEvent event, final int x, final int y) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			if (!bodyLayout.onDoubleClick(event, x - bodyLayout.getX(), y - bodyLayout.getY())) {
				return false;
			}
		}
		return this.backgroundColor == null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean onMouseClick(final MouseEvent event, final int x, final int y) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			if (!bodyLayout.onMouseClick(event, x - bodyLayout.getX(), y - bodyLayout.getY())) {
				return false;
			}
		}
		if (!HtmlController.getInstance().onMouseClick(this.modelNode, event, x, y)) {
			return false;
		}
		return this.backgroundColor == null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean onMouseDisarmed(final MouseEvent event) {
		final BoundableRenderable br = this.armedRenderable;
		if (br != null) {
			try {
				return br.onMouseDisarmed(event);
			} finally {
				this.armedRenderable = null;
			}
		} else {
			return true;
		}
	}


	/** {@inheritDoc} */
	@Override
	public boolean onMousePressed(final MouseEvent event, final int x, final int y) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			final int newX = x - bodyLayout.getX();
			final int newY = y - bodyLayout.getY();
			if (bodyLayout.contains(newX, newY)) {
				this.armedRenderable = bodyLayout;
				if (!bodyLayout.onMousePressed(event, newX, newY)) {
					return false;
				}
			} else {
				this.armedRenderable = null;
			}
		} else {
			this.armedRenderable = null;
		}
		if (!HtmlController.getInstance().onMouseDown(this.modelNode, event, x, y)) {
			return false;
		}
		return this.backgroundColor == null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean onMouseReleased(final MouseEvent event, final int x, final int y) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			final int newX = x - bodyLayout.getX();
			final int newY = y - bodyLayout.getY();
			if (bodyLayout.contains(newX, newY)) {
				this.armedRenderable = null;
				if (!bodyLayout.onMouseReleased(event, newX, newY)) {
					return false;
				}
			} else {
				final BoundableRenderable br = this.armedRenderable;
				if (br != null) {
					br.onMouseDisarmed(event);
				}
			}
		}
		if (!HtmlController.getInstance().onMouseUp(this.modelNode, event, x, y)) {
			return false;
		}
		return this.backgroundColor == null;
	}

	@Override
	public void onMouseScroll() {
		HtmlController.getInstance().onMouseScroll(this.modelNode);
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics gIn) {
		final RenderState rs = (RenderState)this.modelNode.getRenderState();
		if (rs != null && rs.getVisibility() == RenderState.VISIBILITY_VISIBLE) {
			final boolean isRelative = (relativeOffsetX | relativeOffsetY) != 0;
			final Graphics g = isRelative ? gIn.create() : gIn;
			if (isRelative) {
				g.translate(relativeOffsetX, relativeOffsetY);
			}

			try {
				this.prePaint(g);
				final RBlockViewport bodyLayout = this.bodyLayout;
				if (bodyLayout != null) {
					final int overflowX = this.overflowX;
					final int overflowY = this.overflowY;
					if ((overflowX == RenderState.OVERFLOW_NONE || overflowX == RenderState.OVERFLOW_VISIBLE)
							&& (overflowY == RenderState.OVERFLOW_NONE || overflowY == RenderState.OVERFLOW_VISIBLE)) {
						bodyLayout.paint(g);
					} else {
						final Insets insets = getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
						final Graphics newG = g.create(insets.left, insets.top, this.getWidth() - insets.left - insets.right,
								this.getHeight() - insets.top - insets.bottom);
						try {
							// Second, translate
							newG.translate(-insets.left, -insets.top);
							// Third, paint in clipped + translated region.
							bodyLayout.paint(newG);
						} finally {
							newG.dispose();
						}
					}
				}

				// Paint FrameContext selection.
				// This is only done by root RBlock.

				final RenderableSpot start = this.startSelection;
				final RenderableSpot end = this.endSelection;
				final boolean inSelection = false;
				if (start != null && end != null && !start.equals(end)) {
					paintSelection(g, inSelection, start, end);
				}
				scroll.paintSroll(g);
			} finally {
				if (isRelative) {
					g.dispose();
				}
				super.paint(gIn);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean paintSelection(final Graphics g, final boolean inSelection, final RenderableSpot startPoint, final RenderableSpot endPoint) {
		final Graphics newG = g.create();
		try {
			final Insets insets = getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
			final int widht = this.getWidth() - insets.left - insets.right;
			final int height = this.getHeight() - insets.top - insets.bottom;
			newG.clipRect(insets.left, insets.top, widht, height);
			return super.paintSelection(newG, inSelection, startPoint, endPoint);
		} finally {
			newG.dispose();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void repaint(final ModelNode modelNode) {
		this.repaint();
	}

	/**
	 * <p>scrollByUnits.</p>
	 *
	 * @param orientation a {@link java.lang.Integer} object.
	 * @param units a {@link java.lang.Integer} object.
	 * @return a boolean.
	 */
	public boolean scrollByUnits(final int orientation, final int units) {
		final int offset = orientation == JScrollBar.VERTICAL ? scroll.getVUnitIncrement() * units : units;
		return scroll.scrollBy(orientation, offset);
	}

	/**
	 * Scrolls the viewport's origin to the given location, or as close to it as
	 * possible.
	 * <p>
	 * This method should be invoked in the GUI thread.
	 *
	 * @param bounds    The bounds of the scrollable area that should become
	 *                  visible.
	 * @param xIfNeeded If this parameter is true the x coordinate is
	 *                  changed only if the horizontal bounds are not currently
	 *                  visible.
	 * @param yIfNeeded If this parameter is true the y coordinate is
	 *                  changed only if the vertical bounds are not currently
	 *                  visible.
	 */
	public void scrollTo(final Rectangle bounds, final boolean xIfNeeded, final boolean yIfNeeded) {
		final boolean hscroll = this.hasHScrollBar;
		final boolean vscroll = this.hasVScrollBar;
		if (hscroll || vscroll) {
			final RBlockViewport bv = this.bodyLayout;
			final Insets insets = getInsetsMarginBorder(hscroll, vscroll);
			final int vpheight = this.getHeight() - insets.top - insets.bottom;
			final int vpwidth = this.getWidth() - insets.left - insets.right;
			final int tentativeX = insets.left - bounds.x;
			final int tentativeY = insets.top - bounds.y;
			boolean needCorrection = false;
			if (!(xIfNeeded && tentativeX <= bv.getX() && -tentativeX + bv.getX() + bounds.width <= vpwidth)) {
				bv.setX(tentativeX);
				needCorrection = true;
			}
			if (!(yIfNeeded && tentativeY <= bv.getY() && -tentativeY + bv.getY() + bounds.height <= vpheight)) {
				bv.setY(tentativeY);
				needCorrection = true;
			}
			if (needCorrection) {
				correctViewportOrigin(insets, this.getWidth(), this.getHeight());
				scroll.resetScrollBars(null);
			}
		}
	}


	/** {@inheritDoc} */
	@Override
	public Rectangle getClipBoundsWithoutInsets() {
		final int hInset = this.hasVScrollBar ? SCROLL_BAR_THICKNESS : 0;
		final int vInset = this.hasHScrollBar ? SCROLL_BAR_THICKNESS : 0;
		if (!(this.hasHScrollBar || this.hasVScrollBar)) {
			return null;
		} else {
			return new Rectangle(-relativeOffsetX, -relativeOffsetY, this.getWidth() - hInset, this.getHeight() - vInset);
		}
	}

	/**
	 * <p>setSelectionEnd.</p>
	 *
	 * @param rpoint a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 */
	public void setSelectionEnd(final RenderableSpot rpoint) {
		this.endSelection = rpoint;
	}

	/**
	 * <p>setSelectionStart.</p>
	 *
	 * @param rpoint a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 */
	public void setSelectionStart(final RenderableSpot rpoint) {
		this.startSelection = rpoint;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "RBlock[node=" + this.modelNode + "]";
	}

	/** {@inheritDoc} */
	@Override
	public void updateWidgetBounds(final int guiX, final int guiY) {
		super.updateWidgetBounds(guiX, guiY);
		scroll.updateWidgetBounds(guiX, guiY);
	}

	private void correctViewportOrigin(final Insets insets, final int blockWidth, final int blockHeight) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		final int viewPortX = bodyLayout.getX();
		final int viewPortY = bodyLayout.getY();

		if (viewPortX > insets.left) {
			bodyLayout.setX(insets.left);
		} else if (viewPortX < blockWidth - insets.right - bodyLayout.getWidth()) {
			bodyLayout.setX(Math.min(insets.left, blockWidth - insets.right - bodyLayout.getWidth()));
		}
		if (viewPortY > insets.top) {
			bodyLayout.setY(insets.top);
		} else if (viewPortY < blockHeight - insets.bottom - bodyLayout.getHeight()) {
			bodyLayout.setY(Math.min(insets.top, blockHeight - insets.bottom - bodyLayout.getHeight()));
		}
	}
}
