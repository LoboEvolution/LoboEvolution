/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
import org.loboevolution.html.renderer.info.RBlockInfo;
import org.loboevolution.html.renderer.info.RBlockLayoutInfo;
import org.loboevolution.html.renderer.info.RLayoutInfo;
import org.loboevolution.html.renderstate.BlockRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.RenderThreadState;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.info.FloatingInfo;

import javax.swing.*;
import java.awt.*;
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
 */
public class RBlock extends BaseElementRenderable {

	/** Constant logger */
	protected static final Logger logger = Logger.getLogger(RBlock.class.getName());
	private BoundableRenderable armedRenderable;
	protected final RBlockViewport bodyLayout;
	private final RBlockScroll scroll;
	protected int defaultOverflowX = RenderState.OVERFLOW_NONE;
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
	public RBlock(RBlockInfo info) {
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
	protected void clearStyle(boolean isRootBlock) {
		super.clearStyle(isRootBlock);
		this.overflowX = this.defaultOverflowX;
		this.overflowY = this.defaultOverflowY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void doLayout(int availWidth, int availHeight, boolean sizeOnly) {
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
	public void doLayout(RLayoutInfo info) {

		final RenderState renderState = this.modelNode.getRenderState();
        RBlockLayoutInfo value = forceLayout(renderState, info);
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
	public void layout(RLayoutInfo info) {
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
	private RBlockLayoutInfo forceLayout(RenderState renderState, RLayoutInfo info) {

		final int availWidth = info.getAvailWidth();
		final int availHeight = info.getAvailHeight();
		final boolean expandWidth = info.isExpandWidth();
		final boolean expandHeight = info.isExpandHeight();
		final boolean sizeOnly = info.isSizeOnly();
		final FloatingBoundsSource blockFloatBoundsSource = info.getBlockFloatBoundsSource();

		RenderState rs = renderState;
		if (rs == null) {
			rs = new BlockRenderState(null);
		}

		applyStyle(availWidth, availHeight);

		final RBlockViewport bodyLayout = this.bodyLayout;
		final NodeImpl node = (NodeImpl) this.modelNode;
        if (node == null || bodyLayout == null) {
            final Insets insets = getInsetsMarginBorder(false, false);
            return RBlockLayoutInfo.builder().
                    width(insets.left + insets.right).
                    height(insets.bottom + insets.top).
                    hasHScrollBar(false).
                    hasVScrollBar(false).
                    build();
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
					if (bodyLayout.getWidth() + insetsTotalWidth < tentativeWidth) {
						tentativeWidth = bodyLayout.getWidth() + insetsTotalWidth;
						tentativeHeight = bodyLayout.getHeight() + insetsTotalHeight;
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

		final int bodyWidth = bodyLayout.getWidth();
		final int bodyHeight = bodyLayout.getHeight();
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
				final int canvasWidth = Math.max(bodyLayout.getWidth(), resultingWidth - insets.left - insets.right);
				bodyLayout.alignX(alignmentXPercent, canvasWidth, paddingInsets);
			}

			if (alignmentYPercent > 0) {
				final int canvasHeight = Math.max(bodyLayout.getHeight(), resultingHeight - insets.top - insets.bottom);
				bodyLayout.alignY(alignmentYPercent, canvasHeight, paddingInsets);
			}
		}

		if (hscroll || vscroll) {

			if (vscroll) {
				addComponent(scroll.getVScrollBar());
			}
			if (hscroll) {
				addComponent(scroll.getHScrollBar());
			}


			correctViewportOrigin(insets, resultingWidth, resultingHeight);
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


	/** {@inheritDoc} */
	@Override
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
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


	/**
	 * Gets the width the vertical scrollbar has when shown.
	 *
	 * @return a int.
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
	public boolean onDoubleClick(final MouseEvent event, int x, int y) {
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
	public boolean onMouseClick(final MouseEvent event, int x, int y) {
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
	public boolean onMousePressed(final MouseEvent event, int x, int y) {
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
	public boolean onMouseReleased(final MouseEvent event, int x, int y) {
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
		final RenderState rs = this.modelNode.getRenderState();
		if (rs != null && rs.getVisibility() == RenderState.VISIBILITY_VISIBLE) {
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
	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
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
	public void repaint(ModelNode modelNode) {
		this.repaint();
	}

	/**
	 * <p>scrollByUnits.</p>
	 *
	 * @param orientation a int.
	 * @param units a int.
	 * @return a boolean.
	 */
	public boolean scrollByUnits(int orientation, int units) {
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
	public void scrollTo(Rectangle bounds, boolean xIfNeeded, boolean yIfNeeded) {
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
		scroll.updateWidgetBounds(guiX, guiY);
	}

	public RBlockScroll getScroll() {
		return scroll;
	}

	private void correctViewportOrigin(Insets insets, int blockWidth, int blockHeight) {
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
