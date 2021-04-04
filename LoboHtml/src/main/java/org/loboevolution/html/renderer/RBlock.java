/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.renderer;

import org.loboevolution.html.dom.HTMLHtmlElement;
import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.renderstate.BlockRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.RenderThreadState;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.info.FloatingInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

/**
 * Represents a HTML block in a rendered document, typically a DIV. The root
 * renderer node is of this type as well.
 * <p>
 * Immediately below an RBlock you will find a node of type
 * {@link org.loboevolution.html.renderer.RBlockViewport}.
 *
 *
 *
 */
public class RBlock extends BaseElementRenderable {

	/** Constant logger */
	protected static final Logger logger = Logger.getLogger(RBlock.class.getName());
	private BoundableRenderable armedRenderable;
	protected final RBlockViewport bodyLayout;

	protected int defaultOverflowX = RenderState.OVERFLOW_NONE;
	protected int defaultOverflowY = RenderState.OVERFLOW_NONE;
	protected RenderableSpot endSelection;
	protected final FrameContext frameContext;
	protected boolean hasHScrollBar = false;

	protected boolean hasVScrollBar = false;
	protected JScrollBar hScrollBar;

	protected final int listNesting;

	protected final HtmlRendererContext rendererContext;

	private boolean resettingScrollBars = false;

	protected RenderableSpot startSelection;

	protected JScrollBar vScrollBar;

	/**
	 * <p>Constructor for RBlock.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 * @param listNesting a int.
	 * @param pcontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @param rcontext a {@link org.loboevolution.http.HtmlRendererContext} object.
	 * @param frameContext a {@link org.loboevolution.html.renderer.FrameContext} object.
	 * @param parentContainer a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 */
	public RBlock(NodeImpl modelNode, int listNesting, UserAgentContext pcontext, HtmlRendererContext rcontext,
			FrameContext frameContext, RenderableContainer parentContainer) {
		super(parentContainer, modelNode, pcontext);
		this.listNesting = listNesting;
		this.frameContext = frameContext;
		this.rendererContext = rcontext;
		final RBlockViewport bl = new RBlockViewport(modelNode, this, getViewportListNesting(listNesting), pcontext,
				rcontext, frameContext, this);
		this.bodyLayout = bl;
		bl.setOriginalParent(this);
		bl.setX(Short.MAX_VALUE);
		bl.setY(Short.MAX_VALUE);
	}

	/** {@inheritDoc} */
	@Override
	protected void clearStyle(boolean isRootBlock) {
		super.clearStyle(isRootBlock);
		this.overflowX = this.defaultOverflowX;
		this.overflowY = this.defaultOverflowY;
	}

	/** {@inheritDoc} */
	@Override
	public final void doLayout(int availWidth, int availHeight, boolean sizeOnly) {
		// This is an override of an abstract method.
		this.doLayout(availWidth, availHeight, true, false, null, this.defaultOverflowX, this.defaultOverflowY,
				sizeOnly);
	}

	/**
	 * Lays out and sets dimensions only if RBlock is invalid (or never before layed
	 * out), if the parameters passed differ from the last layout, or if the current
	 * font differs from the font for the last layout.
	 *
	 * @param availWidth a int.
	 * @param availHeight a int.
	 * @param expandWidth a boolean.
	 * @param expandHeight a boolean.
	 * @param floatBoundsSource a {@link org.loboevolution.html.renderer.FloatingBoundsSource} object.
	 * @param defaultOverflowX a int.
	 * @param defaultOverflowY a int.
	 * @param sizeOnly a boolean.
	 */
	public void doLayout(int availWidth, int availHeight, boolean expandWidth, boolean expandHeight,
			FloatingBoundsSource floatBoundsSource, int defaultOverflowX, int defaultOverflowY, boolean sizeOnly) {

		final RenderState renderState = this.modelNode.getRenderState();
		LayoutValue value = forceLayout(renderState, availWidth, availHeight, expandWidth, expandHeight, floatBoundsSource, defaultOverflowX, defaultOverflowY, sizeOnly);
		this.width = value.width;
		this.height = value.height;
		this.hasHScrollBar = value.hasHScrollBar;
		this.hasVScrollBar = value.hasVScrollBar;

		bodyLayout.positionDelayed();

		sendGUIComponentsToParent();

		sendDelayedPairsToParent();
	}

	/**
	 * <p>ensureVisible.</p>
	 *
	 * @param point a {@link java.awt.Point} object.
	 */
	public void ensureVisible(Point point) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			final boolean hscroll = this.hasHScrollBar;
			final boolean vscroll = this.hasVScrollBar;
			final int origX = bodyLayout.x;
			final int origY = bodyLayout.y;
			final Insets insets = getInsetsMarginBorder(hscroll, vscroll);

			if (hscroll) {
				if (point.x < insets.left) {
					bodyLayout.x += insets.left - point.x;
				} else if (point.x > this.width - insets.right) {
					bodyLayout.x -= point.x - this.width + insets.right;
				}
			}
			if (vscroll) {
				if (point.y < insets.top) {
					bodyLayout.y += insets.top - point.y;
				} else if (point.y > this.height - insets.bottom) {
					bodyLayout.y -= point.y - this.height + insets.bottom;
				}
			}
			if (hscroll || vscroll) {
				correctViewportOrigin(insets, this.width, this.height);
				if (origX != bodyLayout.x || origY != bodyLayout.y) {
					resetScrollBars(null);
					// TODO: This could be paintImmediately.
					this.repaint();
				}
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean extractSelectionText(StringBuilder buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint) {
		final boolean result = super.extractSelectionText(buffer, inSelection, startPoint, endPoint);
		final String br = System.getProperty("line.separator");
		if (inSelection) {
			buffer.insert(0, br);
		}
		if (result) {
			buffer.append(br);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Lays out the block without checking for prior dimensions.
	 *
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param availWidth a int.
	 * @param availHeight a int.
	 * @param expandWidth a boolean.
	 * @param expandHeight a boolean.
	 * @param blockFloatBoundsSource a {@link org.loboevolution.html.renderer.FloatingBoundsSource} object.
	 * @param defaultOverflowX a int.
	 * @param defaultOverflowY a int.
	 * @param sizeOnly a boolean.
	 * @return a {@link org.loboevolution.html.renderer.RBlock.LayoutValue} object.
	 */
	private LayoutValue forceLayout(RenderState renderState, int availWidth, int availHeight, boolean expandWidth,
			boolean expandHeight, FloatingBoundsSource blockFloatBoundsSource, int defaultOverflowX,
			int defaultOverflowY, boolean sizeOnly) {

		RenderState rs = renderState;
		if (rs == null) {
			rs = new BlockRenderState(null);
		}

		applyStyle(availWidth, availHeight);

		final RBlockViewport bodyLayout = this.bodyLayout;
		final NodeImpl node = (NodeImpl) this.modelNode;
		if (node == null || bodyLayout == null) {
			final Insets insets = getInsetsMarginBorder(false, false);
			return new LayoutValue(insets.left + insets.right, insets.bottom + insets.top, false, false);
		}

		Insets paddingInsets = (this.paddingInsets == null) ? RBlockViewport.ZERO_INSETS : this.paddingInsets;

		final int paddingTotalWidth = paddingInsets.left + paddingInsets.right;
		final int paddingTotalHeight = paddingInsets.top + paddingInsets.bottom;

		int overflowX = (this.overflowX == RenderState.OVERFLOW_NONE) ? defaultOverflowX : this.overflowX;
		int overflowY = (this.overflowY == RenderState.OVERFLOW_NONE) ? defaultOverflowY : this.overflowY;

		final boolean vauto = overflowY == RenderState.OVERFLOW_AUTO;
		boolean hscroll = overflowX == RenderState.OVERFLOW_SCROLL;
		final boolean hauto = overflowX == RenderState.OVERFLOW_AUTO;
		boolean vscroll = overflowY == RenderState.OVERFLOW_SCROLL;

		Insets insets = getInsetsMarginBorder(hscroll, vscroll);
		int insetsTotalWidth = insets.left + insets.right;
		int insetsTotalHeight = insets.top + insets.bottom;
		int actualAvailWidth = availWidth - paddingTotalWidth - insetsTotalWidth;
		final int actualAvailHeight = availHeight - paddingTotalHeight - insetsTotalHeight;
		final Integer dw = getDeclaredWidth(renderState, actualAvailWidth);
		final Integer dh = getDeclaredHeight(renderState, actualAvailHeight);
		int declaredWidth = dw != null ? dw : -1;
		int declaredHeight = dh != null ? dh : -1;

		clearGUIComponents();

		int tentativeWidth;
		int tentativeHeight;

		if ("border-box".equals(rs.getBoxSizing())) {
			tentativeWidth = declaredWidth == -1 ? availWidth : declaredWidth;
			tentativeHeight = declaredHeight == -1 ? availHeight : declaredHeight;
		} else {
			tentativeWidth = declaredWidth == -1 ? availWidth : declaredWidth + insetsTotalWidth + paddingTotalWidth;
			tentativeHeight = declaredHeight == -1 ? availHeight : declaredHeight + insetsTotalHeight + paddingTotalHeight;
		}

		if (declaredWidth == -1 && !expandWidth && availWidth > insetsTotalWidth + paddingTotalWidth) {
			final RenderThreadState state = RenderThreadState.getState();
			final boolean prevOverrideNoWrap = state.overrideNoWrap;
			if (!prevOverrideNoWrap) {
				state.overrideNoWrap = true;
				try {
					bodyLayout.layout(paddingTotalWidth, paddingTotalHeight, paddingInsets, -1, null, true);
					if (bodyLayout.width + insetsTotalWidth < tentativeWidth) {
						tentativeWidth = bodyLayout.width + insetsTotalWidth;
						tentativeHeight = bodyLayout.height + insetsTotalHeight;
					}
				} finally {
					state.overrideNoWrap = false;
				}
			}
		}

		FloatingBounds viewportFloatBounds = null;
		FloatingBounds blockFloatBounds = null;
		if (blockFloatBoundsSource != null) {
			blockFloatBounds = blockFloatBoundsSource.getChildBlockFloatingBounds(tentativeWidth);
			viewportFloatBounds = new ShiftedFloatingBounds(blockFloatBounds, -insets.left, -insets.right, -insets.top);
		}
		int desiredViewportWidth = tentativeWidth - insetsTotalWidth;
		final int desiredViewportHeight = tentativeHeight - insets.top - insets.bottom;
		final int maxY = vauto ? (declaredHeight == -1 ? availHeight : declaredHeight + paddingInsets.top) : -1;
		try {
			bodyLayout.layout(desiredViewportWidth, desiredViewportHeight, paddingInsets, maxY, viewportFloatBounds, sizeOnly);
		} catch (final SizeExceededException see) {
			vscroll = true;
			insets = getInsetsMarginBorder(hscroll, vscroll);
			insetsTotalWidth = insets.left + insets.right;
			actualAvailWidth = availWidth - paddingTotalWidth - insetsTotalWidth;
			Integer newdw = getDeclaredWidth(renderState, actualAvailWidth);
			declaredWidth = newdw == null ? -1 : newdw;
			desiredViewportWidth = tentativeWidth - insetsTotalWidth;
			if (blockFloatBounds != null) {
				viewportFloatBounds = new ShiftedFloatingBounds(blockFloatBounds, -insets.left, -insets.right, -insets.top);
			}
			bodyLayout.layout(desiredViewportWidth, desiredViewportHeight, paddingInsets, -1, viewportFloatBounds, sizeOnly);
		}

		final int bodyWidth = bodyLayout.width;
		final int bodyHeight = bodyLayout.height;
		final int prelimBlockWidth = bodyWidth + insetsTotalWidth;
		int prelimBlockHeight = bodyHeight + insetsTotalHeight;

		if ((vauto || vscroll) && ((prelimBlockHeight - insetsTotalHeight) < bodyLayout.getHeight())) {
			final boolean isHtmlElem = getModelNode() instanceof HTMLHtmlElement;
			if (isHtmlElem) {
				prelimBlockHeight = bodyLayout.getHeight() + insetsTotalHeight;
			} else {
				vscroll = true;
				insets = this.getInsetsMarginBorder(hscroll, vscroll);
				insetsTotalWidth = insets.left + insets.right;
			}
		}

		int adjDeclaredWidth;
		int adjDeclaredHeight;

		if ("border-box".equals(rs.getBoxSizing())) {
			adjDeclaredWidth = declaredWidth;
			adjDeclaredHeight = declaredHeight;
		} else {
			adjDeclaredWidth = declaredWidth == -1 ? -1 : declaredWidth + insets.left + insets.right + paddingInsets.left + paddingInsets.right;
			adjDeclaredHeight = declaredHeight == -1 ? -1 : declaredHeight + insets.top + insets.bottom + paddingInsets.top + paddingInsets.bottom;
		}

		// Adjust insets and other dimensions base on overflow-y=auto.
		if (hauto && (adjDeclaredWidth != -1 && prelimBlockWidth > adjDeclaredWidth || prelimBlockWidth > tentativeWidth)) {
			hscroll = true;
			insets = getInsetsMarginBorder(hscroll, vscroll);
			insetsTotalHeight = insets.top + insets.bottom;
			prelimBlockHeight = bodyHeight + insetsTotalHeight;
		}

		int resultingWidth;
		int resultingHeight;

		if (adjDeclaredWidth == -1) {
			resultingWidth = expandWidth ? Math.max(prelimBlockWidth, tentativeWidth) : prelimBlockWidth;
			if (hscroll && resultingWidth > tentativeWidth) {
				resultingWidth = Math.max(tentativeWidth, SCROLL_BAR_THICKNESS);
			}
		} else {
			resultingWidth = adjDeclaredWidth;
		}

		if (adjDeclaredHeight == -1) {
			resultingHeight = expandHeight ? Math.max(prelimBlockHeight, tentativeHeight) : prelimBlockHeight;
			if (vscroll && resultingHeight > tentativeHeight) {
				resultingHeight = Math.max(tentativeHeight, SCROLL_BAR_THICKNESS);
			}
		} else {
			resultingHeight = adjDeclaredHeight;
		}

		if (!sizeOnly) {
			final int alignmentYPercent = rs.getAlignYPercent();
			final int alignmentXPercent = rs.getAlignXPercent();

			if (alignmentXPercent > 0) {
				final int canvasWidth = Math.max(bodyLayout.width, resultingWidth - insets.left - insets.right);
				bodyLayout.alignX(alignmentXPercent, canvasWidth, paddingInsets);
			}

			if (alignmentYPercent > 0) {
				final int canvasHeight = Math.max(bodyLayout.height, resultingHeight - insets.top - insets.bottom);
				bodyLayout.alignY(alignmentYPercent, canvasHeight, paddingInsets);
			}
		}

		if (vscroll) {
			final JScrollBar sb = getVScrollBar();
			addComponent(sb);
			// Bounds set by updateWidgetBounds
		}
		if (hscroll) {
			final JScrollBar sb = getHScrollBar();
			addComponent(sb);
			// Bounds set by updateWidgetBounds
		}

		if (hscroll || vscroll) {
			// In this case, viewport origin should not be reset.
			// We don't want to cause the document to scroll back
			// up while rendering.
			correctViewportOrigin(insets, resultingWidth, resultingHeight);
			// Now reset the scrollbar state. Depends
			// on block width and height.
			this.width = resultingWidth;
			this.height = resultingHeight;
			resetScrollBars(rs);
		} else {
			bodyLayout.x = insets.left;
			bodyLayout.y = insets.top;
		}
		return new LayoutValue(resultingWidth, resultingHeight, hscroll, vscroll);
	}

	/**
	 * <p>Getter for the field defaultOverflowX.</p>
	 *
	 * @return a int.
	 */
	public int getDefaultOverflowX() {
		return this.defaultOverflowX;
	}

	/**
	 * <p>Getter for the field defaultOverflowY.</p>
	 *
	 * @return a int.
	 */
	public int getDefaultOverflowY() {
		return this.defaultOverflowY;
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
	 * @return a int.
	 */
	public int getFirstBaselineOffset() {
		return this.bodyLayout.getFirstBaselineOffset();
	}

	/**
	 * <p>getFirstLineHeight.</p>
	 *
	 * @return a int.
	 */
	public int getFirstLineHeight() {
		return this.bodyLayout.getFirstLineHeight();
	}

	private JScrollBar getHScrollBar() {
		JScrollBar sb = this.hScrollBar;
		if (sb == null) {
			// Should never go back to null
			sb = new JScrollBar(JScrollBar.HORIZONTAL);
			sb.addAdjustmentListener(new LocalAdjustmentListener(JScrollBar.HORIZONTAL));
			this.hScrollBar = sb;
		}
		return sb;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.BoundableRenderable#getRenderablePoint(int,
	 * int)
	 */
	/** {@inheritDoc} */
	@Override
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			final Insets insets = getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
			if ((x - relativeOffsetX > insets.left) && (x - relativeOffsetX < (this.width - insets.right))
					&& (y - relativeOffsetY > insets.top) && (y - relativeOffsetY < (this.height - insets.bottom))) {
				return bodyLayout.getLowestRenderableSpot(x - relativeOffsetX - bodyLayout.x,
						y - relativeOffsetY - bodyLayout.y);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.RCollection#getRenderables()
	 */
	/** {@inheritDoc} */
	@Override
	public Iterator getRenderables() {
		final RBlockViewport bodyLayout = this.bodyLayout;
		return new Iterator() {
			private RBlockViewport bl = bodyLayout;

			@Override
			public boolean hasNext() {
				return this.bl != null;
			}

			@Override
			public Object next() {
				if (this.bl == null) {
					throw new NoSuchElementException();
				}
				try {
					return this.bl;
				} finally {
					this.bl = null;
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	/**
	 * <p>getViewportListNesting.</p>
	 *
	 * @param blockNesting a int.
	 * @return a int.
	 */
	public int getViewportListNesting(int blockNesting) {
		return blockNesting;
	}

	private JScrollBar getVScrollBar() {
		JScrollBar sb = this.vScrollBar;
		if (sb == null) {
			// Should never go back to null
			sb = new JScrollBar(JScrollBar.VERTICAL);
			sb.addAdjustmentListener(new LocalAdjustmentListener(JScrollBar.VERTICAL));
			this.vScrollBar = sb;
		}
		return sb;
	}

	/**
	 * Gets the width the vertical scrollbar has when shown.
	 *
	 * @return a int.
	 */
	public int getVScrollBarWidth() {
		return SCROLL_BAR_THICKNESS;
	}

	private int getVUnitIncrement(RenderState renderState) {
		if (renderState != null) {
			return renderState.getFontMetrics().getHeight();
		} else {
			return new BlockRenderState(null).getFontMetrics().getHeight();
		}
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
		final JScrollBar hScrollBar = this.hScrollBar;
		if (hScrollBar != null) {
			// Necessary
			hScrollBar.invalidate();
		}
		final JScrollBar vScrollBar = this.vScrollBar;
		if (vScrollBar != null) {
			// Necessary
			vScrollBar.invalidate();
		}
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

	/**
	 * <p>layout.</p>
	 *
	 * @param availWidth a int.
	 * @param availHeight a int.
	 * @param expandWidth a boolean.
	 * @param expandHeight a boolean.
	 * @param floatBoundsSource a {@link org.loboevolution.html.renderer.FloatingBoundsSource} object.
	 * @param sizeOnly a boolean.
	 */
	public final void layout(int availWidth, int availHeight, boolean expandWidth, boolean expandHeight,
			FloatingBoundsSource floatBoundsSource, boolean sizeOnly) {
		this.layout(availWidth, availHeight, expandWidth, expandHeight, floatBoundsSource, this.defaultOverflowX,
				this.defaultOverflowY, sizeOnly);
	}

	/**
	 * <p>layout.</p>
	 *
	 * @param availWidth a int.
	 * @param availHeight a int.
	 * @param expandWidth a boolean.
	 * @param expandHeight a boolean.
	 * @param floatBoundsSource a {@link org.loboevolution.html.renderer.FloatingBoundsSource} object.
	 * @param defaultOverflowX a int.
	 * @param defaultOverflowY a int.
	 * @param sizeOnly a boolean.
	 */
	public final void layout(int availWidth, int availHeight, boolean expandWidth, boolean expandHeight,
			FloatingBoundsSource floatBoundsSource, int defaultOverflowX, int defaultOverflowY, boolean sizeOnly) {
		try {
			this.doLayout(availWidth, availHeight, expandWidth, expandHeight, floatBoundsSource, defaultOverflowX,
					defaultOverflowY, sizeOnly);
		} finally {
			this.layoutUpTreeCanBeInvalidated = true;
			this.layoutDeepCanBeInvalidated = true;
		}
	}

	/**
	 * <p>layout.</p>
	 *
	 * @param availWidth a int.
	 * @param availHeight a int.
	 * @param expandWidth a boolean.
	 * @param expandHeight a boolean.
	 * @param defaultOverflowX a int.
	 * @param defaultOverflowY a int.
	 * @param sizeOnly a boolean.
	 */
	public final void layout(int availWidth, int availHeight, boolean expandWidth, boolean expandHeight,
			int defaultOverflowX, int defaultOverflowY, boolean sizeOnly) {
		this.layout(availWidth, availHeight, expandWidth, expandHeight, null, defaultOverflowX, defaultOverflowY,
				sizeOnly);
	}

	/** {@inheritDoc} */
	@Override
	public boolean onDoubleClick(MouseEvent event, int x, int y) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			if (!bodyLayout.onDoubleClick(event, x - bodyLayout.x, y - bodyLayout.y)) {
				return false;
			}
		}
		return this.backgroundColor == null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean onMouseClick(MouseEvent event, int x, int y) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			if (!bodyLayout.onMouseClick(event, x - bodyLayout.x, y - bodyLayout.y)) {
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
	public boolean onMouseDisarmed(MouseEvent event) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.BoundableRenderable#onMousePressed(java.awt.event.
	 * MouseEvent, int, int)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean onMousePressed(MouseEvent event, int x, int y) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			final int newX = x - bodyLayout.x;
			final int newY = y - bodyLayout.y;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.BoundableRenderable#onMouseReleased(java.awt.event.
	 * MouseEvent, int, int)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean onMouseReleased(MouseEvent event, int x, int y) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			final int newX = x - bodyLayout.x;
			final int newY = y - bodyLayout.y;
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

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics gIn) {
		final RenderState rs = this.modelNode.getRenderState();
		if (rs != null && rs.getVisibility() != RenderState.VISIBILITY_VISIBLE) {
			return;
		}

		final boolean isRelative = (relativeOffsetX | relativeOffsetY) != 0;
		final Graphics g = isRelative ? gIn.create() : gIn;
		if (isRelative) {
			g.translate(relativeOffsetX, relativeOffsetY);
		}

		try {
			this.prePaint(g);
			final Insets insets = getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
			final RBlockViewport bodyLayout = this.bodyLayout;
			if (bodyLayout != null) {
				final int overflowX = this.overflowX;
				final int overflowY = this.overflowY;
				if ((overflowX == RenderState.OVERFLOW_NONE || overflowX == RenderState.OVERFLOW_VISIBLE)
						&& (overflowY == RenderState.OVERFLOW_NONE || overflowY == RenderState.OVERFLOW_VISIBLE)) {
			          bodyLayout.paint(g);
				} else {
					// Clip when there potential scrolling or hidden overflow
					// was requested.
					final Graphics newG = g.create(insets.left, insets.top, this.width - insets.left - insets.right,
							this.height - insets.top - insets.bottom);
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
			// Must paint scrollbars too.
			final JScrollBar hsb = this.hScrollBar;
			if (hsb != null) {
				final Graphics sbg = g.create(insets.left, this.height - insets.bottom,
						this.width - insets.left - insets.right, SCROLL_BAR_THICKNESS);
				try {
					hsb.paint(sbg);
				} finally {
					sbg.dispose();
				}
			}
			final JScrollBar vsb = this.vScrollBar;
			if (vsb != null) {
				final Graphics sbg = g.create(this.width - insets.right, insets.top, SCROLL_BAR_THICKNESS,
						this.height - insets.top - insets.bottom);
				try {
					vsb.paint(sbg);
				} finally {
					sbg.dispose();
				}
			}

		} finally {
			if (isRelative) {
				g.dispose();
			}
			super.paint(gIn);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.UIControl#paintSelection(java.awt.Graphics,
	 * boolean, org.loboevolution.html.rendered.RenderablePoint,
	 * org.loboevolution.html.rendered.RenderablePoint)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		final Graphics newG = g.create();
		try {
			final Insets insets = getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
			// Just clip, don't translate.
			newG.clipRect(insets.left, insets.top, this.width - insets.left - insets.right,
					this.height - insets.top - insets.bottom);
			return super.paintSelection(newG, inSelection, startPoint, endPoint);
		} finally {
			newG.dispose();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void repaint(ModelNode modelNode) {
		// this.invalidateRenderStyle();
		this.repaint();
	}

	/**
	 * Changes scroll bar state to match viewport origin.
	 */
	private void resetScrollBars(RenderState renderState) {
		// Expected to be called only in the GUI thread.
		this.resettingScrollBars = true;
		try {
			final RBlockViewport bodyLayout = this.bodyLayout;
			if (bodyLayout != null) {
				final Insets insets = getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
				final JScrollBar vsb = this.vScrollBar;
				if (vsb != null) {
					final int newValue = insets.top - bodyLayout.y;
					final int newExtent = this.height - insets.top - insets.bottom;
					final int newMin = 0;
					final int newMax = bodyLayout.height;
					vsb.setValues(newValue, newExtent, newMin, newMax);
					vsb.setUnitIncrement(getVUnitIncrement(renderState));
					vsb.setBlockIncrement(newExtent);
				}
				final JScrollBar hsb = this.hScrollBar;
				if (hsb != null) {
					final int newValue = insets.left - bodyLayout.x;
					final int newExtent = this.width - insets.left - insets.right;
					final int newMin = 0;
					final int newMax = bodyLayout.width;
					hsb.setValues(newValue, newExtent, newMin, newMax);
				}
			}
		} finally {
			this.resettingScrollBars = false;
		}
	}

	/**
	 * <p>scrollBy.</p>
	 *
	 * @param orientation a int.
	 * @param offset a int.
	 * @return a boolean.
	 */
	public boolean scrollBy(int orientation, int offset) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			switch (orientation) {
			case JScrollBar.HORIZONTAL:
				return scrollHorizontalTo(bodyLayout.x - offset);
			case JScrollBar.VERTICAL:
				return scrollVerticalTo(bodyLayout.y - offset);
			default:
				break;
			}
		}
		return false;
	}

	/**
	 * <p>scrollByUnits.</p>
	 *
	 * @param orientation a int.
	 * @param units a int.
	 * @return a boolean.
	 */
	public boolean scrollByUnits(int orientation, int units) {
		final int offset = orientation == JScrollBar.VERTICAL ? getVUnitIncrement(null) * units : units;
		return this.scrollBy(orientation, offset);
	}

	/**
	 * <p>scrollHorizontalTo.</p>
	 *
	 * @param newX a int.
	 * @return a boolean.
	 */
	public boolean scrollHorizontalTo(int newX) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			final Insets insets = getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
			final int prevX = bodyLayout.x;
			if (newX > insets.left) {
				bodyLayout.x = insets.left;
			} else if (newX < this.width - insets.right - bodyLayout.width) {
				bodyLayout.x = Math.min(insets.left, this.width - insets.right - bodyLayout.width);
			} else {
				bodyLayout.x = newX;
			}
			resetScrollBars(null);
			this.updateWidgetBounds();
			this.repaint();
			return bodyLayout.x != prevX;
		}
		return false;
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
	public void scrollTo(Rectangle bounds, boolean xIfNeeded, boolean yIfNeeded) {
		final boolean hscroll = this.hasHScrollBar;
		final boolean vscroll = this.hasVScrollBar;
		if (hscroll || vscroll) {
			final RBlockViewport bv = this.bodyLayout;
			final Insets insets = getInsetsMarginBorder(hscroll, vscroll);
			final int vpheight = this.height - insets.top - insets.bottom;
			final int vpwidth = this.width - insets.left - insets.right;
			final int tentativeX = insets.left - bounds.x;
			final int tentativeY = insets.top - bounds.y;
			boolean needCorrection = false;
			if (!(xIfNeeded && tentativeX <= bv.x && -tentativeX + bv.x + bounds.width <= vpwidth)) {
				bv.setX(tentativeX);
				needCorrection = true;
			}
			if (!(yIfNeeded && tentativeY <= bv.y && -tentativeY + bv.y + bounds.height <= vpheight)) {
				bv.setY(tentativeY);
				needCorrection = true;
			}
			if (needCorrection) {
				correctViewportOrigin(insets, this.width, this.height);
		        this.resetScrollBars(null);
			}
		}
	}

	private void scrollToSBValue(int orientation, int value) {
		final Insets insets = getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
		switch (orientation) {
		case JScrollBar.HORIZONTAL:
			final int xOrigin = insets.left - value;
			scrollHorizontalTo(xOrigin);
			break;
		case JScrollBar.VERTICAL:
			final int yOrigin = insets.top - value;
			scrollVerticalTo(yOrigin);
			break;
		}
	}

	/**
	 * <p>scrollVerticalTo.</p>
	 *
	 * @param newY a int.
	 * @return a boolean.
	 */
	public boolean scrollVerticalTo(int newY) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			final Insets insets = getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
			final int prevY = bodyLayout.y;
			if (newY > insets.top) {
				bodyLayout.y = insets.top;
			} else if (newY < this.height - insets.bottom - bodyLayout.height) {
				bodyLayout.y = Math.min(insets.top, this.height - insets.bottom - bodyLayout.height);
			} else {
				bodyLayout.y = newY;
			}
			resetScrollBars(null);
			this.updateWidgetBounds();
			this.repaint();
			return bodyLayout.y != prevY;
		}
		return false;
	}
	
	/** {@inheritDoc} */
	@Override
	public Rectangle getClipBoundsWithoutInsets() {
		final int hInset = this.hasVScrollBar ? SCROLL_BAR_THICKNESS : 0;
		final int vInset = this.hasHScrollBar ? SCROLL_BAR_THICKNESS : 0;
		if (!(this.hasHScrollBar || this.hasVScrollBar)) {
			return null;
		} else {
			return new Rectangle(-relativeOffsetX, -relativeOffsetY, this.width - hInset, this.height - vInset);
		}
	}

	/**
	 * <p>Setter for the field defaultOverflowX.</p>
	 *
	 * @param defaultOverflowX a int.
	 */
	public void setDefaultOverflowX(int defaultOverflowX) {
		this.defaultOverflowX = defaultOverflowX;
	}

	/**
	 * <p>Setter for the field defaultOverflowY.</p>
	 *
	 * @param defaultOverflowY a int.
	 */
	public void setDefaultOverflowY(int defaultOverflowY) {
		this.defaultOverflowY = defaultOverflowY;
	}

	/**
	 * <p>setSelectionEnd.</p>
	 *
	 * @param rpoint a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 */
	public void setSelectionEnd(RenderableSpot rpoint) {
		this.endSelection = rpoint;
	}

	/**
	 * <p>setSelectionStart.</p>
	 *
	 * @param rpoint a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 */
	public void setSelectionStart(RenderableSpot rpoint) {
		this.startSelection = rpoint;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "RBlock[node=" + this.modelNode + "]";
	}

	/** {@inheritDoc} */
	@Override
	public void updateWidgetBounds(int guiX, int guiY) {
		super.updateWidgetBounds(guiX, guiY);
		final boolean hscroll = this.hasHScrollBar;
		final boolean vscroll = this.hasVScrollBar;
		if (hscroll || vscroll) {
			final Insets insets = getInsetsMarginBorder(hscroll, vscroll);
			if (hscroll) {
				final JScrollBar hsb = this.hScrollBar;
				if (hsb != null) {
					hsb.setBounds(guiX + insets.left, guiY + this.height - insets.bottom,
							this.width - insets.left - insets.right, SCROLL_BAR_THICKNESS);
				}
			}
			if (vscroll) {
				final JScrollBar vsb = this.vScrollBar;
				if (vsb != null) {
					vsb.setBounds(guiX + this.width - insets.right, guiY + insets.top, SCROLL_BAR_THICKNESS,
							this.height - insets.top - insets.bottom);
				}
			}
		}
	}

	private void correctViewportOrigin(Insets insets, int blockWidth, int blockHeight) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		final int viewPortX = bodyLayout.x;
		final int viewPortY = bodyLayout.y;

		if (viewPortX > insets.left) {
			bodyLayout.x = insets.left;
		} else if (viewPortX < blockWidth - insets.right - bodyLayout.width) {
			bodyLayout.x = Math.min(insets.left, blockWidth - insets.right - bodyLayout.width);
		}
		if (viewPortY > insets.top) {
			bodyLayout.y = insets.top;
		} else if (viewPortY < blockHeight - insets.bottom - bodyLayout.height) {
			bodyLayout.y = Math.min(insets.top, blockHeight - insets.bottom - bodyLayout.height);
		}
	}

	private static class LayoutValue {
		public final boolean hasHScrollBar;
		public final boolean hasVScrollBar;
		public final int height;
		public final int width;

		public LayoutValue(int width, int height, boolean hasHScrollBar, boolean hasVScrollBar) {
			this.width = width;
			this.height = height;
			this.hasHScrollBar = hasHScrollBar;
			this.hasVScrollBar = hasVScrollBar;
		}
	}

	private class LocalAdjustmentListener implements AdjustmentListener {
		private final int orientation;

		public LocalAdjustmentListener(int orientation) {
			this.orientation = orientation;
		}

		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			if (RBlock.this.resettingScrollBars) {
				return;
			}
			switch (e.getAdjustmentType()) {
				case AdjustmentEvent.UNIT_INCREMENT:
				case AdjustmentEvent.UNIT_DECREMENT:
				case AdjustmentEvent.BLOCK_INCREMENT:
				case AdjustmentEvent.BLOCK_DECREMENT:
				case AdjustmentEvent.TRACK: {
					final int value = e.getValue();
					scrollToSBValue(this.orientation, value);
					break;
				}
				default:
					break;
			}
		}
	}
}
