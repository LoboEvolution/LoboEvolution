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
package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import javax.swing.JScrollBar;

import org.lobobrowser.html.domimpl.ModelNode;
import org.lobobrowser.html.domimpl.NodeImpl;
import org.lobobrowser.html.style.BlockRenderState;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.html.style.RenderThreadState;
import org.lobobrowser.http.HtmlRendererContext;
import org.lobobrowser.http.UserAgentContext;
import java.util.Objects;

/**
 * Represents a HTML block in a rendered document, typically a DIV. The root
 * renderer node is of this type as well.
 * <p>
 * Immediately below an <code>RBlock</code> you will find a node of type
 * {@link RBlockViewport}.
 */
public class RBlock extends BaseElementRenderable {
	private static class LayoutKey {
		public final int availHeight;
		public final int availWidth;
		public final int defaultOverflowX;
		public final int defaultOverflowY;
		public final boolean expandHeight;
		public final boolean expandWidth;
		public final FloatingBoundsSource floatBoundsSource;
		public final Font font;
		public final boolean useDeclaredSize;
		public final int whitespace;

		public LayoutKey(int availWidth, int availHeight, boolean expandWidth, boolean expandHeight,
				FloatingBoundsSource floatBoundsSource, int defaultOverflowX, int defaultOverflowY, int whitespace,
				Font font) {
			super();
			this.availWidth = availWidth;
			this.availHeight = availHeight;
			this.floatBoundsSource = floatBoundsSource;
			this.defaultOverflowX = defaultOverflowX;
			this.defaultOverflowY = defaultOverflowY;
			this.whitespace = whitespace;
			this.font = font;
			this.expandWidth = expandWidth;
			this.expandHeight = expandHeight;
			this.useDeclaredSize = true;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof LayoutKey)) {
				return false;
			}
			final LayoutKey other = (LayoutKey) obj;
			return other.availWidth == this.availWidth && other.availHeight == this.availHeight
					&& other.defaultOverflowX == this.defaultOverflowX
					&& other.defaultOverflowY == this.defaultOverflowY && other.whitespace == this.whitespace
					&& other.expandWidth == this.expandWidth && other.expandHeight == this.expandHeight
					&& other.useDeclaredSize == this.useDeclaredSize && Objects.equals(other.font, this.font)
					&& Objects.equals(other.floatBoundsSource, this.floatBoundsSource);
		}

		@Override
		public int hashCode() {
			final Font font = this.font;
			return this.availWidth * 1000 + this.availHeight ^ (font == null ? 0 : font.hashCode())
					^ (this.expandWidth ? 2 : 0) ^ (this.expandHeight ? 1 : 0);
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
				// fall through
			case AdjustmentEvent.UNIT_DECREMENT:
				// fall through
			case AdjustmentEvent.BLOCK_INCREMENT:
				// fall through
			case AdjustmentEvent.BLOCK_DECREMENT:
				// fall through
			case AdjustmentEvent.TRACK: {
				final int value = e.getValue();
				scrollToSBValue(this.orientation, value);
				break;
			}
			}
		}
	}

	protected static final Logger logger = Logger.getLogger(RBlock.class.getName());
	private static final int MAX_CACHE_SIZE = 10;
	private BoundableRenderable armedRenderable;
	protected final RBlockViewport bodyLayout;

	protected final Map cachedLayout = new Hashtable(5);
	protected int defaultOverflowX = RenderState.OVERFLOW_NONE;
	protected int defaultOverflowY = RenderState.OVERFLOW_NONE;
	protected RenderableSpot endSelection;
	protected final FrameContext frameContext;
	protected boolean hasHScrollBar = false;

	// Validation-dependent variables...
	// private Dimension layoutSize = null;

	protected boolean hasVScrollBar = false;
	protected JScrollBar hScrollBar;

	private LayoutKey lastLayoutKey = null;
	private LayoutValue lastLayoutValue = null;

	protected final int listNesting;

	protected final HtmlRendererContext rendererContext;

	private boolean resettingScrollBars = false;

	protected RenderableSpot startSelection;

	protected JScrollBar vScrollBar;

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
		// Initialize origin of RBlockViewport to be as far top-left as
		// possible.
		// This will be corrected on first layout.
		bl.setX(Short.MAX_VALUE);
		bl.setY(Short.MAX_VALUE);
	}

	@Override
	protected void clearStyle(boolean isRootBlock) {
		super.clearStyle(isRootBlock);
		this.overflowX = this.defaultOverflowX;
		this.overflowY = this.defaultOverflowY;
	}

	private final boolean correctViewportOrigin(Insets insets, int blockWidth, int blockHeight) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		final int viewPortX = bodyLayout.x;
		final int viewPortY = bodyLayout.y;
		boolean corrected = false;
		if (viewPortX > insets.left) {
			bodyLayout.x = insets.left;
			corrected = true;
		} else if (viewPortX < blockWidth - insets.right - bodyLayout.width) {
			bodyLayout.x = Math.min(insets.left, blockWidth - insets.right - bodyLayout.width);
			corrected = true;
		}
		if (viewPortY > insets.top) {
			bodyLayout.y = insets.top;
			corrected = true;
		} else if (viewPortY < blockHeight - insets.bottom - bodyLayout.height) {
			bodyLayout.y = Math.min(insets.top, blockHeight - insets.bottom - bodyLayout.height);
			corrected = true;
		}
		return corrected;
	}

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
	 * @param availWidth
	 * @param availHeight
	 */
	public void doLayout(int availWidth, int availHeight, boolean expandWidth, boolean expandHeight,
			FloatingBoundsSource floatBoundsSource, int defaultOverflowX, int defaultOverflowY, boolean sizeOnly) {
		// Expected to be invoked in the GUI thread.
		final RenderState renderState = this.modelNode.getRenderState();
		final Font font = renderState == null ? null : renderState.getFont();
		final int whiteSpace = renderState == null ? RenderState.WS_NORMAL : renderState.getWhiteSpace();
		final LayoutKey key = new LayoutKey(availWidth, availHeight, expandWidth, expandHeight, floatBoundsSource,
				defaultOverflowX, defaultOverflowY, whiteSpace, font);
		final Map cachedLayout = this.cachedLayout;
		LayoutValue value;
		if (sizeOnly) {
			value = (LayoutValue) cachedLayout.get(key);
		} else {
			if (Objects.equals(key, this.lastLayoutKey)) {
				value = this.lastLayoutValue;
			} else {
				value = null;
			}
		}
		if (value == null) {
			value = forceLayout(renderState, availWidth, availHeight, expandWidth, expandHeight, floatBoundsSource,
					defaultOverflowX, defaultOverflowY, sizeOnly);
			if (sizeOnly) {
				this.lastLayoutKey = null;
				this.lastLayoutValue = null;
				if (cachedLayout.size() > MAX_CACHE_SIZE) {
					// Unlikely, but we should keep it bounded.
					cachedLayout.clear();
				}
				cachedLayout.put(key, value);
			} else {
				this.lastLayoutKey = key;
				this.lastLayoutValue = value;
			}
		}
		this.width = value.width;
		this.height = value.height;
		this.hasHScrollBar = value.hasHScrollBar;
		this.hasVScrollBar = value.hasVScrollBar;

		// Even if we didn't do layout, the parent is
		// expected to have removed its GUI components.
		sendGUIComponentsToParent();

		// Even if we didn't do layout, the parent is
		// expected to have removed its delayed pairs.
		sendDelayedPairsToParent();
	}

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

	@Override
	public boolean extractSelectionText(StringBuffer buffer, boolean inSelection, RenderableSpot startPoint,
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

	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Lays out the block without checking for prior dimensions.
	 * 
	 * @param declaredWidth   The declared width of the block.
	 * @param declaredHeight  The declared height of the block.
	 * @param tentativeWidth  Presumed width of the whole block (with margins).
	 * @param tentativeHeight
	 * @return
	 */
	private final LayoutValue forceLayout(RenderState renderState, int availWidth, int availHeight, boolean expandWidth,
			boolean expandHeight, FloatingBoundsSource blockFloatBoundsSource, int defaultOverflowX,
			int defaultOverflowY, boolean sizeOnly) {
		// Expected to be invoked in the GUI thread.
		// TODO: Not necessary to do full layout if only expandWidth or
		// expandHeight change (specifically in tables).
		RenderState rs = renderState;
		if (rs == null) {
			rs = new BlockRenderState(null);
		}

//		// Clear adjust() cache.
//		this.cachedAdjust.clear();

		// We reprocess the rendering state.
		// Probably doesn't need to be done in its entirety every time.
		applyStyle(availWidth, availHeight);

		final RBlockViewport bodyLayout = this.bodyLayout;
		final NodeImpl node = (NodeImpl) this.modelNode;
		if (node == null || bodyLayout == null) {
			final Insets insets = getInsetsMarginBorder(false, false);
			return new LayoutValue(insets.left + insets.right, insets.bottom + insets.top, false, false);
		}

		Insets paddingInsets = this.paddingInsets;
		if (paddingInsets == null) {
			paddingInsets = RBlockViewport.ZERO_INSETS;
		}
		Insets borderInsets = this.borderInsets;
		if (borderInsets == null) {
			borderInsets = RBlockViewport.ZERO_INSETS;
		}
		Insets marginInsets = this.marginInsets;
		if (marginInsets == null) {
			marginInsets = RBlockViewport.ZERO_INSETS;
		}
		final int paddingTotalWidth = paddingInsets.left + paddingInsets.right;
		final int paddingTotalHeight = paddingInsets.top + paddingInsets.bottom;

		int overflowX = this.overflowX;
		if (overflowX == RenderState.OVERFLOW_NONE) {
			overflowX = defaultOverflowX;
		}
		int overflowY = this.overflowY;
		if (overflowY == RenderState.OVERFLOW_NONE) {
			overflowY = defaultOverflowY;
		}
		final boolean vauto = overflowY == RenderState.OVERFLOW_AUTO;
		boolean hscroll = overflowX == RenderState.OVERFLOW_SCROLL;
		final boolean hauto = overflowX == RenderState.OVERFLOW_AUTO;
		boolean vscroll = overflowY == RenderState.OVERFLOW_SCROLL;

		Insets insets = getInsetsMarginBorder(hscroll, vscroll);
		int insetsTotalWidth = insets.left + insets.right;
		int insetsTotalHeight = insets.top + insets.bottom;
		int actualAvailWidth = availWidth - paddingTotalWidth - insetsTotalWidth;
		final int actualAvailHeight = availWidth - paddingTotalHeight - insetsTotalHeight;
		Integer dw = getDeclaredWidth(renderState, actualAvailWidth);
		final Integer dh = getDeclaredHeight(renderState, actualAvailHeight);
		int declaredWidth = -1;
		int declaredHeight = -1;
		if (dw != null) {
			declaredWidth = dw.intValue();
		}
		if (dh != null) {
			declaredHeight = dh.intValue();
		}

		// Remove all GUI components previously added by descendents
		// The RBlockViewport.layout() method is expected to add all of them
		// back.
		clearGUIComponents();

		int tentativeWidth;
		int tentativeHeight;

		// Step # 1: If there's no declared width and no width
		// expansion has been requested, do a preliminary layout
		// assuming that the scrollable region has width=0 and
		// there's no wrapping.
		tentativeWidth = declaredWidth == -1 ? availWidth : declaredWidth + insetsTotalWidth + paddingTotalWidth;
		tentativeHeight = declaredHeight == -1 ? availHeight : declaredHeight + insetsTotalHeight + paddingTotalHeight;
		if (declaredWidth == -1 && !expandWidth && availWidth > insetsTotalWidth + paddingTotalWidth) {
			final RenderThreadState state = RenderThreadState.getState();
			final boolean prevOverrideNoWrap = state.overrideNoWrap;
			if (!prevOverrideNoWrap) {
				state.overrideNoWrap = true;
				try {
					final int desiredViewportWidth = paddingTotalWidth;
					final int desiredViewportHeight = paddingTotalHeight;
					bodyLayout.layout(desiredViewportWidth, desiredViewportHeight, paddingInsets, -1, null, true);
					// If we find that the viewport is not as wide as we
					// presumed, then we'll use that as a new tentative width.
					if (bodyLayout.width + insetsTotalWidth < tentativeWidth) {
						tentativeWidth = bodyLayout.width + insetsTotalWidth;
						tentativeHeight = bodyLayout.height + insetsTotalHeight;
					}
				} finally {
					state.overrideNoWrap = false;
				}
			}
		}

		// Step # 2: Do a layout with the tentativeWidth (adjusted if Step # 1 was
		// done),
		// but in case overflow-y is "auto", then we check for possible overflow.
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
			// Getting this exception means that we need to add a vertical scrollbar.
			// Wee need to relayout and adjust insets and widths for scrollbar.
			vscroll = true;
			insets = getInsetsMarginBorder(hscroll, vscroll);
			insetsTotalWidth = insets.left + insets.right;
			actualAvailWidth = availWidth - paddingTotalWidth - insetsTotalWidth;
			dw = getDeclaredWidth(renderState, actualAvailWidth);
			declaredWidth = dw == null ? -1 : dw.intValue();
			desiredViewportWidth = tentativeWidth - insetsTotalWidth;
			if (blockFloatBounds != null) {
				viewportFloatBounds = new ShiftedFloatingBounds(blockFloatBounds, -insets.left, -insets.right,
						-insets.top);
			}
			bodyLayout.layout(desiredViewportWidth, desiredViewportHeight, paddingInsets, -1, viewportFloatBounds,
					sizeOnly);
		}

		final int bodyWidth = bodyLayout.width;
		final int bodyHeight = bodyLayout.height;
		final int prelimBlockWidth = bodyWidth + insetsTotalWidth;
		int prelimBlockHeight = bodyHeight + insetsTotalHeight;
		final int adjDeclaredWidth = declaredWidth == -1 ? -1
				: declaredWidth + insets.left + insets.right + paddingInsets.left + paddingInsets.right;
		final int adjDeclaredHeight = declaredHeight == -1 ? -1
				: declaredHeight + insets.top + insets.bottom + paddingInsets.top + paddingInsets.bottom;

		// Adjust insets and other dimensions base on overflow-y=auto.
		if (hauto && (adjDeclaredWidth != -1 && prelimBlockWidth > adjDeclaredWidth
				|| prelimBlockWidth > tentativeWidth)) {
			hscroll = true;
			insets = getInsetsMarginBorder(hscroll, vscroll);
			insetsTotalHeight = insets.top + insets.bottom;
			prelimBlockHeight = bodyHeight + insetsTotalHeight;
		}

		final boolean visibleX = overflowX == RenderState.OVERFLOW_VISIBLE || overflowX == RenderState.OVERFLOW_NONE;
		final boolean visibleY = overflowY == RenderState.OVERFLOW_VISIBLE || overflowY == RenderState.OVERFLOW_NONE;
		int resultingWidth;
		int resultingHeight;
		if (adjDeclaredWidth == -1) {
			resultingWidth = expandWidth ? Math.max(prelimBlockWidth, tentativeWidth) : prelimBlockWidth;
			if (hscroll && resultingWidth > tentativeWidth) {
				resultingWidth = Math.max(tentativeWidth, SCROLL_BAR_THICKNESS);
			}
		} else {
			resultingWidth = visibleX ? Math.max(prelimBlockWidth, adjDeclaredWidth) : adjDeclaredWidth;
		}
		if (!sizeOnly) {
			// Align horizontally now. This may change canvas height.
			final int alignmentXPercent = rs.getAlignXPercent();
			if (alignmentXPercent > 0) {
				// TODO: OPTIMIZATION: alignment should not be done in table cell
				// sizing determination.
				final int canvasWidth = Math.max(bodyLayout.width, resultingWidth - insets.left - insets.right);
				// Alignment is done afterwards because canvas dimensions might have
				// changed.
				bodyLayout.alignX(alignmentXPercent, canvasWidth, paddingInsets);
			}
		}
		if (adjDeclaredHeight == -1) {
			resultingHeight = expandHeight ? Math.max(prelimBlockHeight, tentativeHeight) : prelimBlockHeight;
			if (vscroll && resultingHeight > tentativeHeight) {
				resultingHeight = Math.max(tentativeHeight, SCROLL_BAR_THICKNESS);
			}
		} else {
			resultingHeight = visibleY ? Math.max(prelimBlockHeight, adjDeclaredHeight) : adjDeclaredHeight;
		}
		if (!sizeOnly) {
			// Align vertically now
			final int alignmentYPercent = rs.getAlignYPercent();
			if (alignmentYPercent > 0) {
				// TODO: OPTIMIZATION: alignment should not be done in table cell
				// sizing determination.
				final int canvasHeight = Math.max(bodyLayout.height, resultingHeight - insets.top - insets.bottom);
				// Alignment is done afterwards because canvas dimensions might have
				// changed.
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

	public int getDefaultOverflowX() {
		return this.defaultOverflowX;
	}

	public int getDefaultOverflowY() {
		return this.defaultOverflowY;
	}

	public FloatingInfo getExportableFloatingInfo() {
		final FloatingInfo info = this.bodyLayout.getExportableFloatingInfo();
		if (info == null) {
			return null;
		}
		final Insets insets = getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
		return new FloatingInfo(info.shiftX + insets.left, info.shiftY + insets.top, info.floats);
	}

	public int getFirstBaselineOffset() {
		return this.bodyLayout.getFirstBaselineOffset();
	}

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
	 * @see org.xamjwg.html.renderer.BoundableRenderable#getRenderablePoint(int,
	 * int)
	 */
	@Override
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			final Insets insets = getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
			if (x > insets.left && x < this.width - insets.right && y > insets.top && y < this.height - insets.bottom) {
				return bodyLayout.getLowestRenderableSpot(x - bodyLayout.x, y - bodyLayout.y);
			} else {
				return new RenderableSpot(this, x, y);
			}
		} else {
			return new RenderableSpot(this, x, y);
		}
	}

	@Override
	public Color getPaintedBackgroundColor() {
		return this.backgroundColor;
	}

//	/**
//	 * Adjustment step which must be done after layout. This will expand blocks
//	 * that need to be expanded and relayout blocks with relative sizes
//	 * accordingly.
//	 * 
//	 * @param availWidth
//	 * @param availHeight
//	 * @param expandWidth
//	 * @param expandHeight
//	 */
//	public void adjust(int availWidth, int availHeight, boolean expandWidth,
//			boolean expandHeight, FloatingBoundsSource floatBoundsSource, boolean useDeclaredSize) {
//        RenderState renderState = this.modelNode.getRenderState();
//        Font font = renderState == null ? null : renderState.getFont();
//        int whiteSpace = renderState == null ? RenderState.WS_NORMAL : renderState.getWhiteSpace();
//        int tentativeWidth;
//        if(useDeclaredSize && floatBoundsSource != null) {
//            Integer declaredWidth = this.getDeclaredWidth(renderState, availWidth);
//            Insets insets = this.getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar); 
//            Insets paddingInsets = this.paddingInsets;
//            int hinsets = insets.left + insets.right + (paddingInsets == null ? 0 : paddingInsets.left + paddingInsets.right); 
//            tentativeWidth = declaredWidth == null ? availWidth : declaredWidth.intValue() + hinsets;
//        }
//        else {
//            // Assuming that we don't care about this if
//            // floatBoundsSource == null.
//            tentativeWidth = availWidth;
//        }
//        FloatingBounds blockFloatBounds = floatBoundsSource == null ? null : floatBoundsSource.getChildBlockFloatingBounds(tentativeWidth);
//	    LayoutKey layoutKey = new LayoutKey(availWidth, availHeight, blockFloatBounds, this.defaultOverflowX, this.defaultOverflowY, whiteSpace, font, expandWidth, expandHeight, useDeclaredSize);
//	    LayoutValue layoutValue = (LayoutValue) this.cachedAdjust.get(layoutKey);
//		// Expected to be invoked in the GUI thread.
//		if (layoutValue == null) {
//			layoutValue = this.forceAdjust(renderState, availWidth, availHeight,
//					expandWidth, expandHeight, blockFloatBounds, this.defaultOverflowX, this.defaultOverflowY, useDeclaredSize);
//			this.cachedAdjust.put(layoutKey, layoutValue);			
//		}
//
//		// We send GUI components up in adjust() in case new ones were added.
//		this.sendGUIComponentsToParent();
//		// No sending delayed pairs here.
//		this.width = layoutValue.width;
//		this.height = layoutValue.height;
//		this.hasHScrollBar = layoutValue.hasHScrollBar;
//		this.hasVScrollBar = layoutValue.hasVScrollBar;
//	}
//
//	/**
//	 * This adjustment step needs to be performed after layout. In this case,
//	 * the dimensions previously obtained in the layout are assumed to be the
//	 * desired dimensions of the block.
//	 * <p>
//	 * When we first layout a block, we don't know its final width and height. 
//	 * It could be wider or narrower than originally assumed.
//	 * Consider elements embedded in the block that have widths and heights
//	 * specified by a percentage. 
//	 */
//	public void adjust() {
//		// Expected to be invoked in the GUI thread.
//	    this.adjust(this.width, this.height, true, true, null, false);
//	}
//
//	/**
//	 * 
//	 * @param renderState
//	 * @param tentativeWidth
//	 *            The tentative or max width that will be tried.
//	 * @param tentativeHeight
//	 *            The tentative or max height that will be tried.
//	 * @param adjDeclaredWidth
//	 *            The declared width plus margins.
//	 * @param adjDeclaredHeight
//	 *            The declared height plus margins.
//	 * @param floatBounds
//	 *            Float bounds that need to be passed to the viewport.
//	 * @param defaultOverflow
//	 */
//	private final LayoutValue forceAdjust(RenderState renderState, int availWidth, int availHeight, 
//			boolean expandWidth, boolean expandHeight,
//			FloatingBounds blockFloatBounds, int defaultOverflowX, int defaultOverflowY, boolean useDeclaredSize) {
//		// Expected to be invoked in the GUI thread.
//		RenderState rs = renderState;
//		if (rs == null) {
//			rs = new BlockRenderState(null);
//		}
//		RBlockViewport bodyLayout = this.bodyLayout;
//		NodeImpl node = (NodeImpl) this.modelNode;
//		if (node == null || bodyLayout == null) {
//			Insets insets = this.getInsetsMarginBorder(false, false);
//			return new LayoutValue(insets.left + insets.right, insets.bottom + insets.top, false, false);
//		}
//
//		// No clearing of GUI components here
//
//        int overflowX = this.overflowX;
//        if (overflowX == RenderState.OVERFLOW_NONE) {
//            overflowX = defaultOverflowX;
//        }
//        int overflowY = this.overflowY;
//        if (overflowY == RenderState.OVERFLOW_NONE) {
//            overflowY = defaultOverflowY;
//        }
//        boolean autoY = overflowY == RenderState.OVERFLOW_AUTO;
//        boolean hscroll = overflowX == RenderState.OVERFLOW_SCROLL;
//        boolean hauto = overflowX == RenderState.OVERFLOW_AUTO;
//        boolean vscroll = overflowY == RenderState.OVERFLOW_SCROLL;
//		Insets paddingInsets = this.paddingInsets;
//		if (paddingInsets == null) {
//			paddingInsets = RBlockViewport.ZERO_INSETS;
//		}
//        Insets borderInsets = this.borderInsets;
//        if(borderInsets == null) {
//            borderInsets = RBlockViewport.ZERO_INSETS;
//        }
//        Insets marginInsets = this.marginInsets;
//        if(marginInsets == null) {
//            marginInsets = RBlockViewport.ZERO_INSETS;
//        }
//        
//        // Calculate presumed size of block.
//        int tentativeWidth;
//        int tentativeHeight;
//        int declaredWidth = -1;
//        int declaredHeight = -1;
//        if(useDeclaredSize) {
//            Integer dw = this.getDeclaredWidth(renderState, availWidth);
//            Integer dh = this.getDeclaredHeight(renderState, availHeight);
//            if (dw != null) {
//                declaredWidth = dw.intValue();
//            }
//            if (dh != null) {
//                declaredHeight = dh.intValue();
//            }
//        }
//        if(declaredWidth == -1) {
//            tentativeWidth = availWidth;            
//        }
//        else {
//            tentativeWidth = declaredWidth + paddingInsets.left + paddingInsets.right + borderInsets.left + borderInsets.right + marginInsets.left + marginInsets.right; 
//        }
//        if(declaredHeight == -1) {
//            tentativeHeight = availHeight;
//        }
//        else {
//            tentativeHeight = declaredHeight + paddingInsets.top + paddingInsets.bottom + borderInsets.top + borderInsets.bottom + marginInsets.top + marginInsets.bottom;
//        }
//		Insets insets = null;
//		for (int tries = (autoY ? 0 : 1); tries < 2; tries++) {
//			try {
//				insets = this.getInsetsMarginBorder(hscroll, vscroll);
//				int desiredViewportWidth = tentativeWidth - insets.left
//						- insets.right;
//				int desiredViewportHeight = tentativeHeight - insets.top
//						- insets.bottom;
//				FloatingBounds viewportFloatBounds = null;
//				if (blockFloatBounds != null) {
//					viewportFloatBounds = new ShiftedFloatingBounds(
//							blockFloatBounds, -insets.left, -insets.right,
//							-insets.top);
//				}
//				bodyLayout.adjust(desiredViewportWidth, desiredViewportHeight,
//						paddingInsets, viewportFloatBounds);
//				break;
//			} catch (SizeExceededException hee) {
//				if (tries != 0) {
//					throw new IllegalStateException("tries=" + tries + ",autoY="
//							+ autoY);
//				}
//				vscroll = true;
//			}
//		}
//		// Dimension size = bodyLayout.getSize();
//		// Dimension rblockSize = new Dimension(size.width + insets.left +
//		// insets.right, size.height + insets.top + insets.bottom);
//		int rblockWidth = bodyLayout.width + insets.left + insets.right;
//        int adjDeclaredWidth = declaredWidth == -1 ? -1 : declaredWidth + insets.left + insets.right + paddingInsets.left + paddingInsets.right; 
//        int adjDeclaredHeight = declaredHeight == -1 ? -1 : declaredHeight + insets.top + insets.bottom + paddingInsets.top + paddingInsets.bottom; 
//		if (hauto
//				&& !hscroll
//				&& ((adjDeclaredWidth != -1 && rblockWidth > adjDeclaredWidth) || (rblockWidth > tentativeWidth))) {
//			hscroll = true;
//			insets = this.getInsetsMarginBorder(hscroll, vscroll);
//			rblockWidth = bodyLayout.width + insets.left + insets.right;
//		}
//		// Calculate resulting width.
//		boolean visibleX = overflowX == RenderState.OVERFLOW_VISIBLE || overflowX == RenderState.OVERFLOW_NONE; 
//        boolean visibleY = overflowY == RenderState.OVERFLOW_VISIBLE || overflowY == RenderState.OVERFLOW_NONE; 
//		int resultingWidth;
//		if (adjDeclaredWidth == -1) {
//			resultingWidth = rblockWidth;
//			if (hscroll && resultingWidth > tentativeWidth) {
//				resultingWidth = Math.max(tentativeWidth, SCROLL_BAR_THICKNESS);
//			} else if (expandWidth && resultingWidth < tentativeWidth) {
//				resultingWidth = tentativeWidth;
//			}
//		} else {
//			resultingWidth = visibleX ? Math.max(rblockWidth, adjDeclaredWidth)
//					: adjDeclaredWidth;
//		}
//		// Align horizontally now. This may change canvas height.
//		int alignmentXPercent = rs.getAlignXPercent();
//		if (alignmentXPercent > 0) {
//			// TODO: OPTIMIZATION: alignment should not be done in table cell
//			// sizing determination.
//			int canvasWidth = Math.max(bodyLayout.width, resultingWidth
//					- insets.left - insets.right);
//			// Alignment is done afterwards because canvas dimensions might have
//			// changed.
//			bodyLayout.alignX(alignmentXPercent, canvasWidth, paddingInsets);
//		}
//
//		int resultingHeight;
//		int rblockHeight = bodyLayout.height + insets.top + insets.bottom;
//		if (autoY
//				&& !vscroll
//				&& ((adjDeclaredHeight != -1 && rblockHeight > adjDeclaredHeight) || (rblockHeight > tentativeHeight))) {
//			vscroll = true;
//			insets = this.getInsetsMarginBorder(hscroll, vscroll);
//			rblockHeight = bodyLayout.height + insets.top + insets.bottom;
//		}
//		if (adjDeclaredHeight == -1) {
//			resultingHeight = rblockHeight;
//			if (vscroll && resultingHeight > tentativeHeight) {
//				resultingHeight = Math.max(tentativeHeight,
//						SCROLL_BAR_THICKNESS);
//			} else if (expandHeight && resultingHeight < tentativeHeight) {
//				resultingHeight = tentativeHeight;
//			}
//		} else {
//			resultingHeight = visibleY ? Math.max(rblockHeight,
//					adjDeclaredHeight) : adjDeclaredHeight;
//		}
//
//		// Align vertically now
//		int alignmentYPercent = rs.getAlignYPercent();
//		if (alignmentYPercent > 0) {
//			// TODO: OPTIMIZATION: alignment should not be done in table cell
//			// sizing determination.
//			int canvasHeight = Math.max(bodyLayout.height, resultingHeight
//					- insets.top - insets.bottom);
//			// Alignment is done afterwards because canvas dimensions might have
//			// changed.
//			bodyLayout.alignY(alignmentYPercent, canvasHeight, paddingInsets);
//		}
//
//		if (vscroll) {
//			JScrollBar sb = this.getVScrollBar();
//			this.addComponent(sb);
//			// Bounds set by updateWidgetBounds
//		}
//		if (hscroll) {
//			JScrollBar sb = this.getHScrollBar();
//			this.addComponent(sb);
//			// Bounds set by updateWidgetBounds
//		}
//
//		if (hscroll || vscroll) {
//			// In this case, viewport origin should not be changed.
//			// We don't want to cause the document to scroll back
//			// up while rendering.
//			this.correctViewportOrigin(insets, resultingWidth, resultingHeight);
//			// Depends on width, height and origin
//			this.resetScrollBars(rs);
//		} else {
//			bodyLayout.x = insets.left;
//			bodyLayout.y = insets.top;
//		}
//		return new LayoutValue(resultingWidth, resultingHeight, hscroll, vscroll);
//	}

	public RBlockViewport getRBlockViewport() {
		return this.bodyLayout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.RCollection#getRenderables()
	 */
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

	@Override
	public int getVAlign() {
		// Not used
		return VALIGN_BASELINE;
	}

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
	 * RBlocks should only be invalidated if one of their properties change, or if a
	 * descendent changes, or if a style property of an ancestor is such that it
	 * could produce layout changes in this RBlock.
	 */
	@Override
	public void invalidateLayoutLocal() {
		super.invalidateLayoutLocal();
		this.cachedLayout.clear();
		this.lastLayoutKey = null;
		this.lastLayoutValue = null;
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

	// public final boolean couldBeScrollable() {
//		int overflow = this.getOverflow();
//		return overflow != OVERFLOW_NONE
//				&& (overflow == OVERFLOW_SCROLL
//						|| overflow == OVERFLOW_VERTICAL || overflow == OVERFLOW_AUTO);
//	}
//
	public final boolean isOverflowVisibleX() {
		final int overflow = this.overflowX;
		return overflow == RenderState.OVERFLOW_NONE || overflow == RenderState.OVERFLOW_VISIBLE;
	}

	public final boolean isOverflowVisibleY() {
		final int overflow = this.overflowY;
		return overflow == RenderState.OVERFLOW_NONE || overflow == RenderState.OVERFLOW_VISIBLE;
	}

	public final void layout(int availWidth, int availHeight, boolean expandWidth, boolean expandHeight,
			FloatingBoundsSource floatBoundsSource, boolean sizeOnly) {
		this.layout(availWidth, availHeight, expandWidth, expandHeight, floatBoundsSource, this.defaultOverflowX,
				this.defaultOverflowY, sizeOnly);
	}

	public final void layout(int availWidth, int availHeight, boolean expandWidth, boolean expandHeight,
			FloatingBoundsSource floatBoundsSource, int defaultOverflowX, int defaultOverflowY, boolean sizeOnly) {
		try {
			this.doLayout(availWidth, availHeight, expandWidth, expandHeight, floatBoundsSource, defaultOverflowX,
					defaultOverflowY, sizeOnly);
		} finally {
			this.layoutUpTreeCanBeInvalidated = true;
			this.layoutDeepCanBeInvalidated = true;
			// this.renderStyleCanBeInvalidated = true;
		}
	}

	public final void layout(int availWidth, int availHeight, boolean expandWidth, boolean expandHeight,
			int defaultOverflowX, int defaultOverflowY, boolean sizeOnly) {
		this.layout(availWidth, availHeight, expandWidth, expandHeight, null, defaultOverflowX, defaultOverflowY,
				sizeOnly);
	}

	@Override
	public boolean onDoubleClick(MouseEvent event, int x, int y) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			if (!bodyLayout.onDoubleClick(event, x - bodyLayout.x, y - bodyLayout.y)) {
				return false;
			}
		}
		if (this.backgroundColor != null) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xamjwg.html.renderer.BoundableRenderable#onMouseClick(java.awt.event.
	 * MouseEvent, int, int)
	 */
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
		if (this.backgroundColor != null) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xamjwg.html.renderer.BoundableRenderable#onMouseDisarmed(java.awt.event.
	 * MouseEvent)
	 */
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

	// public boolean extractSelectionText(StringBuffer buffer, boolean
	// inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
	// RBlockViewport bodyLayout = this.bodyLayout;
	// if(bodyLayout != null) {
	// inSelection = inSelection ? endPoint.renderable != this :
	// startPoint.renderable == this;
	// return bodyLayout.extractSelectionText(buffer, inSelection, startPoint,
	// endPoint);
	// }
	// else {
	// return inSelection;
	// }
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xamjwg.html.renderer.BoundableRenderable#onMousePressed(java.awt.event.
	 * MouseEvent, int, int)
	 */
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
		if (this.backgroundColor != null) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xamjwg.html.renderer.BoundableRenderable#onMouseReleased(java.awt.event.
	 * MouseEvent, int, int)
	 */
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
		if (this.backgroundColor != null) {
			return false;
		}
		return true;
	}

	@Override
	public void paint(Graphics g) {
		final RenderState rs = this.modelNode.getRenderState();
		if (rs != null && rs.getVisibility() != RenderState.VISIBILITY_VISIBLE) {
			// Just don't paint it.
			return;
		}
		prePaint(g);
		try {
			final Insets insets = getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
			final RBlockViewport bodyLayout = this.bodyLayout;
			if (bodyLayout != null) {
				final int overflowX = this.overflowX;
				final int overflowY = this.overflowY;
				if ((overflowX == RenderState.OVERFLOW_NONE || overflowX == RenderState.OVERFLOW_VISIBLE)
						&& (overflowY == RenderState.OVERFLOW_NONE || overflowY == RenderState.OVERFLOW_VISIBLE)) {
					// Simply translate.
					final int bx = bodyLayout.x;
					final int by = bodyLayout.y;
					g.translate(bx, by);
					try {
						bodyLayout.paint(g);
					} finally {
						g.translate(-bx, -by);
					}
				} else {
					// Clip when there potential scrolling or hidden overflow
					// was requested.
					final Graphics newG = g.create(insets.left, insets.top, this.width - insets.left - insets.right,
							this.height - insets.top - insets.bottom);
					try {
						// Second, translate
						newG.translate(bodyLayout.x - insets.left, bodyLayout.y - insets.top);
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
			// Must always call super implementation
			super.paint(g);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.UIControl#paintSelection(java.awt.Graphics,
	 * boolean, org.xamjwg.html.renderer.RenderablePoint,
	 * org.xamjwg.html.renderer.RenderablePoint)
	 */
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
		// boolean endSelectionLater = false;
		// if(inSelection) {
		// if(startPoint.renderable == this || endPoint.renderable == this) {
		// return false;
		// }
		// }
		// else {
		// if(startPoint.renderable == this || endPoint.renderable == this) {
		// // This can only occur if the selection point
		// // is on the margin or border or the block.
		// inSelection = true;
		// if(startPoint.renderable == this && endPoint.renderable == this) {
		// // Start and end selection points on margin or border.
		// endSelectionLater = true;
		// }
		// }
		// }
		// RBlockViewport bodyLayout = this.bodyLayout;
		// if(bodyLayout != null) {
		// Insets insets = this.getInsetsMarginBorder(this.hasHScrollBar,
		// this.hasVScrollBar);
		// Graphics newG = g.create(insets.left, insets.top, this.width -
		// insets.left - insets.right, this.height - insets.top -
		// insets.bottom);
		// try {
		// newG.translate(bodyLayout.x - insets.left, bodyLayout.y -
		// insets.top);
		// boolean newInSelection = bodyLayout.paintSelection(newG, inSelection,
		// startPoint, endPoint);
		// if(endSelectionLater) {
		// return false;
		// }
		// return newInSelection;
		// } finally {
		// newG.dispose();
		// }
		// }
		// else {
		// return inSelection;
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.ContainingBlockContext#repaint(org.xamjwg.html.
	 * domimpl.RenderableContext)
	 */
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

	public void scrollBy(int orientation, int offset) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			switch (orientation) {
			case JScrollBar.HORIZONTAL:
				scrollHorizontalTo(bodyLayout.x - offset);
				break;
			case JScrollBar.VERTICAL:
				scrollVerticalTo(bodyLayout.y - offset);
				break;
			}
		}
	}

	public void scrollByUnits(int orientation, int units) {
		final int offset = orientation == JScrollBar.VERTICAL ? getVUnitIncrement(null) * units : units;
		scrollBy(orientation, offset);
	}

	public void scrollHorizontalTo(int newX) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			final Insets insets = getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
			final int viewPortX = newX;
			if (viewPortX > insets.left) {
				bodyLayout.x = insets.left;
			} else if (viewPortX < this.width - insets.right - bodyLayout.width) {
				bodyLayout.x = Math.min(insets.left, this.width - insets.right - bodyLayout.width);
			} else {
				bodyLayout.x = viewPortX;
			}
			resetScrollBars(null);
			this.updateWidgetBounds();
			this.repaint();
		}
	}

	/**
	 * Scrolls the viewport's origin to the given location, or as close to it as
	 * possible.
	 * <p>
	 * This method should be invoked in the GUI thread.
	 * 
	 * @param bounds    The bounds of the scrollable area that should become
	 *                  visible.
	 * @param xIfNeeded If this parameter is <code>true</code> the x coordinate is
	 *                  changed only if the horizontal bounds are not currently
	 *                  visible.
	 * @param yIfNeeded If this parameter is <code>true</code> the y coordinate is
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
			}
		}
	}

//	public FloatingBounds getExportableFloatingBounds() {
//		RBlockViewport viewport = this.bodyLayout;
//		FloatingBounds viewportBounds = viewport.getExportableFloatingBounds();
//		if (viewportBounds == null) {
//			return null;
//		}
//		Insets insets = this.getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
//		return new ShiftedFloatingBounds(viewportBounds, insets.left,
//				insets.right, viewport.y);
//	}

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

	public void scrollVerticalTo(int newY) {
		final RBlockViewport bodyLayout = this.bodyLayout;
		if (bodyLayout != null) {
			final Insets insets = getInsetsMarginBorder(this.hasHScrollBar, this.hasVScrollBar);
			final int viewPortY = newY;
			if (viewPortY > insets.top) {
				bodyLayout.y = insets.top;
			} else if (viewPortY < this.height - insets.bottom - bodyLayout.height) {
				bodyLayout.y = Math.min(insets.top, this.height - insets.bottom - bodyLayout.height);
			} else {
				bodyLayout.y = viewPortY;
			}
			resetScrollBars(null);
			this.updateWidgetBounds();
			this.repaint();
		}
	}

	public void setDefaultOverflowX(int defaultOverflowX) {
		this.defaultOverflowX = defaultOverflowX;
	}

	public void setDefaultOverflowY(int defaultOverflowY) {
		this.defaultOverflowY = defaultOverflowY;
	}

	public void setSelectionEnd(RenderableSpot rpoint) {
		this.endSelection = rpoint;
	}

	public void setSelectionStart(RenderableSpot rpoint) {
		this.startSelection = rpoint;
	}

	@Override
	public String toString() {
		return "RBlock[node=" + this.modelNode + "]";
	}

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
}