/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Apr 16, 2005
 */
package org.lobobrowser.html.renderer;

import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JScrollBar;

import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.domimpl.DOMNodeImpl;
import org.lobobrowser.html.info.FloatingInfo;
import org.lobobrowser.html.layout.LayoutValue;
import org.lobobrowser.html.renderstate.BlockRenderState;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.style.RenderThreadState;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.util.Objects;

/**
 * Represents a HTML block in a rendered document, typically a DIV. The root
 * renderer node is of this type as well.
 * <p>
 * Immediately below an <code>RBlock</code> you will find a node of type
 * {@link RBlockViewport}.
 */
public class RBlock extends BaseElementRenderable implements
        RenderableContainer, ImageObserver {

    /** The Constant logger. */
    protected static final Logger logger = Logger.getLogger(RBlock.class
            .getName());

    /** The Constant loggableInfo. */
    private static final boolean loggableInfo = logger.isLoggable(Level.INFO);

    /** The Constant MAX_CACHE_SIZE. */
    private static final int MAX_CACHE_SIZE = 10;

    /** The frame context. */
    protected final FrameContext frameContext;

    /** The list nesting. */
    protected final int listNesting;

    /** The renderer context. */
    protected final HtmlRendererContext rendererContext;

    /** The body layout. */
    protected final RBlockViewport bodyLayout;

    /** The cached layout. */
    protected final Map<LayoutKey, LayoutValue> cachedLayout = new Hashtable<LayoutKey, LayoutValue>(
            5);

    /** The start selection. */
    protected RenderableSpot startSelection;

    /** The end selection. */
    protected RenderableSpot endSelection;

    /** The v scroll bar. */
    protected JScrollBar vScrollBar;

    /** The h scroll bar. */
    protected JScrollBar hScrollBar;

    /** The has h scroll bar. */
    protected boolean hasHScrollBar = false;

    /** The has v scroll bar. */
    protected boolean hasVScrollBar = false;

    // Validation-dependent variables...
    // private Dimension layoutSize = null;

    /** The default overflow x. */
    protected int defaultOverflowX = RenderState.OVERFLOW_NONE;

    /** The default overflow y. */
    protected int defaultOverflowY = RenderState.OVERFLOW_NONE;

    /** The last layout value. */
    private LayoutValue lastLayoutValue = null;

    /** The last layout key. */
    private LayoutKey lastLayoutKey = null;

    /**
     * Instantiates a new r block.
     *
     * @param modelNode
     *            the model node
     * @param listNesting
     *            the list nesting
     * @param pcontext
     *            the pcontext
     * @param rcontext
     *            the rcontext
     * @param frameContext
     *            the frame context
     * @param parentContainer
     *            the parent container
     */
    public RBlock(DOMNodeImpl modelNode, int listNesting,
            UserAgentContext pcontext, HtmlRendererContext rcontext,
            FrameContext frameContext, RenderableContainer parentContainer) {
        super(parentContainer, modelNode, pcontext);
        this.listNesting = listNesting;
        this.frameContext = frameContext;
        this.rendererContext = rcontext;
        RBlockViewport bl = new RBlockViewport(modelNode, this,
                this.getViewportListNesting(listNesting), pcontext, rcontext,
                frameContext, this);
        this.bodyLayout = bl;
        bl.setOriginalParent(this);
        // Initialize origin of RBlockViewport to be as far top-left as
        // possible.
        // This will be corrected on first layout.
        bl.setX(Short.MAX_VALUE);
        bl.setY(Short.MAX_VALUE);
    }

    /**
     * Gets the width the vertical scrollbar has when shown.
     *
     * @return the v scroll bar width
     */
    public int getVScrollBarWidth() {
        return SCROLL_BAR_THICKNESS;
    }

    /*
 * (non-Javadoc)
 * @see java.lang.Object#finalize()
 */
    @Override
    public void finalize() throws Throwable {
        super.finalize();
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.html.renderer.RElement#getVAlign()
 */
    @Override
    public int getVAlign() {
        // Not used
        return VALIGN_BASELINE;
    }

    /**
     * Ensure visible.
     *
     * @param point
     *            the point
     */
    public void ensureVisible(Point point) {
        RBlockViewport bodyLayout = this.bodyLayout;
        if (bodyLayout != null) {
            boolean hscroll = this.hasHScrollBar;
            boolean vscroll = this.hasVScrollBar;
            int origX = bodyLayout.x;
            int origY = bodyLayout.y;
            Insets insets = this.getInsets(hscroll, vscroll);
            if (hscroll) {
                if (point.x < insets.left) {
                    bodyLayout.x += (insets.left - point.x);
                } else if (point.x > (this.width - insets.right)) {
                    bodyLayout.x -= ((point.x - this.width) + insets.right);
                }
            }
            if (vscroll) {
                if (point.y < insets.top) {
                    bodyLayout.y += (insets.top - point.y);
                } else if (point.y > (this.height - insets.bottom)) {
                    bodyLayout.y -= ((point.y - this.height) + insets.bottom);
                }
            }
            if (hscroll || vscroll) {
                this.correctViewportOrigin(insets, this.width, this.height);
                if ((origX != bodyLayout.x) || (origY != bodyLayout.y)) {
                    this.resetScrollBars(null);
                    // TODO: This could be paintImmediately.
                    this.repaint();
                }
            }
        }
    }

    /**
     * Gets the h scroll bar.
     *
     * @return the h scroll bar
     */
    private JScrollBar getHScrollBar() {
        JScrollBar sb = this.hScrollBar;
        if (sb == null) {
            // Should never go back to null
            sb = new JScrollBar(Adjustable.HORIZONTAL);
            sb.addAdjustmentListener(new LocalAdjustmentListener(
                    Adjustable.HORIZONTAL));
            this.hScrollBar = sb;
        }
        return sb;
    }

    /**
     * Gets the v scroll bar.
     *
     * @return the v scroll bar
     */
    private JScrollBar getVScrollBar() {
        JScrollBar sb = this.vScrollBar;
        if (sb == null) {
            // Should never go back to null
            sb = new JScrollBar(Adjustable.VERTICAL);
            sb.addAdjustmentListener(new LocalAdjustmentListener(
                    Adjustable.VERTICAL));
            this.vScrollBar = sb;
        }
        return sb;
    }

    /**
     * Checks if is overflow visible x.
     *
     * @return true, if is overflow visible x
     */
    public final boolean isOverflowVisibleX() {
        int overflow = this.overflowX;
        return (overflow == RenderState.OVERFLOW_NONE)
                || (overflow == RenderState.OVERFLOW_VISIBLE);
    }

    /**
     * Checks if is overflow visible y.
     *
     * @return true, if is overflow visible y
     */
    public final boolean isOverflowVisibleY() {
        int overflow = this.overflowY;
        return (overflow == RenderState.OVERFLOW_NONE)
                || (overflow == RenderState.OVERFLOW_VISIBLE);
    }

    /**
     * Gets the first line height.
     *
     * @return the first line height
     */
    public int getFirstLineHeight() {
        return this.bodyLayout.getFirstLineHeight();
    }

    /**
     * Gets the first baseline offset.
     *
     * @return the first baseline offset
     */
    public int getFirstBaselineOffset() {
        return this.bodyLayout.getFirstBaselineOffset();
    }

    /**
     * Sets the selection end.
     *
     * @param rpoint
     *            the new selection end
     */
    public void setSelectionEnd(RenderableSpot rpoint) {
        this.endSelection = rpoint;
    }

    /**
     * Sets the selection start.
     *
     * @param rpoint
     *            the new selection start
     */
    public void setSelectionStart(RenderableSpot rpoint) {
        this.startSelection = rpoint;
    }

    /**
     * Gets the viewport list nesting.
     *
     * @param blockNesting
     *            the block nesting
     * @return the viewport list nesting
     */
    public int getViewportListNesting(int blockNesting) {
        return blockNesting;
    }

    /*
 * (non-Javadoc)
 * @see
 * org.lobobrowser.html.renderer.BaseElementRenderable#paint(java.awt.Graphics)
 */
    @Override
    public void paint(Graphics g) {
        RenderState rs = this.modelNode.getRenderState();
        if ((rs != null)
                && (rs.getVisibility() != RenderState.VISIBILITY_VISIBLE)) {
            // Just don't paint it.
            return;
        }
        boolean linfo = loggableInfo;
        long time1 = linfo ? System.currentTimeMillis() : 0;
        this.prePaint(g);
        long time2 = linfo ? System.currentTimeMillis() : 0;
        long time3 = 0;
        try {
            Insets insets = this.getInsets(this.hasHScrollBar,
                    this.hasVScrollBar);
            RBlockViewport bodyLayout = this.bodyLayout;
            if (bodyLayout != null) {
                int overflowX = this.overflowX;
                int overflowY = this.overflowY;
                if (((overflowX == RenderState.OVERFLOW_NONE) || (overflowX == RenderState.OVERFLOW_VISIBLE))
                        && ((overflowY == RenderState.OVERFLOW_NONE) || (overflowY == RenderState.OVERFLOW_VISIBLE))) {
                    // Simply translate.
                    int bx = bodyLayout.x;
                    int by = bodyLayout.y;
                    g.translate(bx, by);
                    try {
                        bodyLayout.paint(g);
                    } finally {
                        g.translate(-bx, -by);
                    }
                } else {
                    // Clip when there potential scrolling or hidden overflow
                    // was requested.
                    Graphics newG = g.create(insets.left, insets.top,
                            this.width - insets.left - insets.right,
                            this.height - insets.top - insets.bottom);
                    try {
                        // Second, translate
                        newG.translate(bodyLayout.x - insets.left, bodyLayout.y
                                - insets.top);
                        // Third, paint in clipped + translated region.
                        bodyLayout.paint(newG);
                    } finally {
                        newG.dispose();
                    }
                }

                if (linfo) {
                    time3 = System.currentTimeMillis();
                }
            } else {
                // nop
            }

            // Paint FrameContext selection.
            // This is only done by root RBlock.

            RenderableSpot start = this.startSelection;
            RenderableSpot end = this.endSelection;
            boolean inSelection = false;
            if ((start != null) && (end != null) && !start.equals(end)) {
                this.paintSelection(g, inSelection, start, end);
            }
            // Must paint scrollbars too.
            JScrollBar hsb = this.hScrollBar;
            if (hsb != null) {
                Graphics sbg = g.create(insets.left, this.height
                        - insets.bottom, this.width - insets.left
                        - insets.right, SCROLL_BAR_THICKNESS);
                try {
                    hsb.paint(sbg);
                } finally {
                    sbg.dispose();
                }
            }
            JScrollBar vsb = this.vScrollBar;
            if (vsb != null) {
                Graphics sbg = g.create(this.width - insets.right, insets.top,
                        SCROLL_BAR_THICKNESS, this.height - insets.top
                                - insets.bottom);
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
        if (linfo) {
            long time4 = System.currentTimeMillis();
            if ((time4 - time1) > 100) {
                logger.info("paint(): Elapsed: " + (time4 - time1)
                        + " ms. Prepaint: " + (time2 - time1)
                        + " ms. Viewport: " + (time3 - time2) + " ms. RBlock: "
                        + this + ".");
            }
        }
    }

    /**
     * Layout.
     *
     * @param availWidth
     *            the avail width
     * @param availHeight
     *            the avail height
     * @param expandWidth
     *            the expand width
     * @param expandHeight
     *            the expand height
     * @param defaultOverflowX
     *            the default overflow x
     * @param defaultOverflowY
     *            the default overflow y
     * @param sizeOnly
     *            the size only
     */
    public final void layout(int availWidth, int availHeight,
            boolean expandWidth, boolean expandHeight, int defaultOverflowX,
            int defaultOverflowY, boolean sizeOnly) {
        this.layout(availWidth, availHeight, expandWidth, expandHeight, null,
                defaultOverflowX, defaultOverflowY, sizeOnly);
    }

    /**
     * Layout.
     *
     * @param availWidth
     *            the avail width
     * @param availHeight
     *            the avail height
     * @param expandWidth
     *            the expand width
     * @param expandHeight
     *            the expand height
     * @param floatBoundsSource
     *            the float bounds source
     * @param sizeOnly
     *            the size only
     */
    public final void layout(int availWidth, int availHeight,
            boolean expandWidth, boolean expandHeight,
            FloatingBoundsSource floatBoundsSource, boolean sizeOnly) {
        this.layout(availWidth, availHeight, expandWidth, expandHeight,
                floatBoundsSource, this.defaultOverflowX,
                this.defaultOverflowY, sizeOnly);
    }

    /**
     * Layout.
     *
     * @param availWidth
     *            the avail width
     * @param availHeight
     *            the avail height
     * @param expandWidth
     *            the expand width
     * @param expandHeight
     *            the expand height
     * @param floatBoundsSource
     *            the float bounds source
     * @param defaultOverflowX
     *            the default overflow x
     * @param defaultOverflowY
     *            the default overflow y
     * @param sizeOnly
     *            the size only
     */
    public final void layout(int availWidth, int availHeight,
            boolean expandWidth, boolean expandHeight,
            FloatingBoundsSource floatBoundsSource, int defaultOverflowX,
            int defaultOverflowY, boolean sizeOnly) {
        try {
            this.doLayout(availWidth, availHeight, expandWidth, expandHeight,
                    floatBoundsSource, defaultOverflowX, defaultOverflowY,
                    sizeOnly);
        } finally {
            this.layoutUpTreeCanBeInvalidated = true;
            this.layoutDeepCanBeInvalidated = true;
            // this.renderStyleCanBeInvalidated = true;
        }
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.html.renderer.BaseElementRenderable#doLayout(int, int,
 * boolean)
 */
    @Override
    public final void doLayout(int availWidth, int availHeight, boolean sizeOnly) {
        // This is an override of an abstract method.
        this.doLayout(availWidth, availHeight, true, false, null,
                this.defaultOverflowX, this.defaultOverflowY, sizeOnly);
    }

    /**
     * Do layout.
     *
     * @param availWidth
     *            the avail width
     * @param availHeight
     *            the avail height
     * @param expandWidth
     *            the expand width
     * @param expandHeight
     *            the expand height
     * @param floatBoundsSource
     *            the float bounds source
     * @param defaultOverflowX
     *            the default overflow x
     * @param defaultOverflowY
     *            the default overflow y
     * @param sizeOnly
     *            the size only
     */
    public void doLayout(int availWidth, int availHeight, boolean expandWidth,
            boolean expandHeight, FloatingBoundsSource floatBoundsSource,
            int defaultOverflowX, int defaultOverflowY, boolean sizeOnly) {
        this.doLayout(availWidth, availHeight, expandWidth, expandHeight,
                floatBoundsSource, defaultOverflowX, defaultOverflowY,
                sizeOnly, true);
    }

    /**
     * Lays out and sets dimensions only if RBlock is invalid (or never before
     * layed out), if the parameters passed differ from the last layout, or if
     * the current font differs from the font for the last layout.
     *
     * @param availWidth
     *            the avail width
     * @param availHeight
     *            the avail height
     * @param expandWidth
     *            the expand width
     * @param expandHeight
     *            the expand height
     * @param floatBoundsSource
     *            the float bounds source
     * @param defaultOverflowX
     *            the default overflow x
     * @param defaultOverflowY
     *            the default overflow y
     * @param sizeOnly
     *            the size only
     * @param useCache
     *            For testing. Should always be true.
     */
    public void doLayout(int availWidth, int availHeight, boolean expandWidth,
            boolean expandHeight, FloatingBoundsSource floatBoundsSource,
            int defaultOverflowX, int defaultOverflowY, boolean sizeOnly,
            boolean useCache) {
        // Expected to be invoked in the GUI thread.
        RenderState renderState = this.modelNode.getRenderState();
        Font font = renderState == null ? null : renderState.getFont();
        int whiteSpace = renderState == null ? RenderState.WS_NORMAL
                : renderState.getWhiteSpace();
        // Having whiteSpace == NOWRAP and having a NOWRAP override
        // are not exactly the same thing.
        boolean overrideNoWrap = RenderThreadState.getState().overrideNoWrap;
        LayoutKey key = new LayoutKey(availWidth, availHeight, expandWidth,
                expandHeight, floatBoundsSource, defaultOverflowX,
                defaultOverflowY, whiteSpace, font, overrideNoWrap);
        Map<LayoutKey, LayoutValue> cachedLayout = this.cachedLayout;
        LayoutValue value;
        if (sizeOnly) {
            value = useCache ? (LayoutValue) cachedLayout.get(key) : null;
        } else {
            if (Objects.equals(key, this.lastLayoutKey)) {
                value = this.lastLayoutValue;
            } else {
                value = null;
            }
        }
        if (value == null) {
            value = this.forceLayout(renderState, availWidth, availHeight,
                    expandWidth, expandHeight, floatBoundsSource,
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
        this.sendGUIComponentsToParent();

        // Even if we didn't do layout, the parent is
        // expected to have removed its delayed pairs.
        this.sendDelayedPairsToParent();
    }

    /**
     * Correct viewport origin.
     *
     * @param insets
     *            the insets
     * @param blockWidth
     *            the block width
     * @param blockHeight
     *            the block height
     * @return true, if successful
     */
    private final boolean correctViewportOrigin(Insets insets, int blockWidth,
            int blockHeight) {
        RBlockViewport bodyLayout = this.bodyLayout;
        int viewPortX = bodyLayout.x;
        int viewPortY = bodyLayout.y;
        boolean corrected = false;
        if (viewPortX > insets.left) {
            bodyLayout.x = insets.left;
            corrected = true;
        } else if (viewPortX < (blockWidth - insets.right - bodyLayout.width)) {
            bodyLayout.x = Math.min(insets.left, blockWidth - insets.right
                    - bodyLayout.width);
            corrected = true;
        }
        if (viewPortY > insets.top) {
            bodyLayout.y = insets.top;
            corrected = true;
        } else if (viewPortY < (blockHeight - insets.bottom - bodyLayout.height)) {
            bodyLayout.y = Math.min(insets.top, blockHeight - insets.bottom
                    - bodyLayout.height);
            corrected = true;
        }
        return corrected;
    }

    /**
     * Lays out the block without checking for prior dimensions.
     *
     * @param renderState
     *            the render state
     * @param availWidth
     *            the avail width
     * @param availHeight
     *            the avail height
     * @param expandWidth
     *            the expand width
     * @param expandHeight
     *            the expand height
     * @param blockFloatBoundsSource
     *            the block float bounds source
     * @param defaultOverflowX
     *            the default overflow x
     * @param defaultOverflowY
     *            the default overflow y
     * @param sizeOnly
     *            the size only
     * @return the layout value
     */
    private final LayoutValue forceLayout(RenderState renderState,
            int availWidth, int availHeight, boolean expandWidth,
            boolean expandHeight, FloatingBoundsSource blockFloatBoundsSource,
            int defaultOverflowX, int defaultOverflowY, boolean sizeOnly) {
        // Expected to be invoked in the GUI thread.
        // TODO: Not necessary to do full layout if only expandWidth or
        // expandHeight change (specifically in tables).
        RenderState rs = renderState;
        if (rs == null) {
            rs = new BlockRenderState(null);
        }

        // // Clear adjust() cache.
        // this.cachedAdjust.clear();

        // We reprocess the rendering state.
        // Probably doesn't need to be done in its entirety every time.
        this.applyStyle(availWidth, availHeight);

        RBlockViewport bodyLayout = this.bodyLayout;
        DOMNodeImpl node = (DOMNodeImpl) this.modelNode;
        if ((node == null) || (bodyLayout == null)) {
            Insets insets = this.getInsets(false, false);
            return new LayoutValue(insets.left + insets.right, insets.bottom
                    + insets.top, false, false);
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
        int paddingTotalWidth = paddingInsets.left + paddingInsets.right;
        int paddingTotalHeight = paddingInsets.top + paddingInsets.bottom;

        int overflowX = this.overflowX;
        if (overflowX == RenderState.OVERFLOW_NONE) {
            overflowX = defaultOverflowX;
        }
        int overflowY = this.overflowY;
        if (overflowY == RenderState.OVERFLOW_NONE) {
            overflowY = defaultOverflowY;
        }
        boolean vauto = overflowY == RenderState.OVERFLOW_AUTO;
        boolean hscroll = overflowX == RenderState.OVERFLOW_SCROLL;
        boolean hauto = overflowX == RenderState.OVERFLOW_AUTO;
        boolean vscroll = overflowY == RenderState.OVERFLOW_SCROLL;

        Insets insets = this.getInsets(hscroll, vscroll);
        int insetsTotalWidth = insets.left + insets.right;
        int insetsTotalHeight = insets.top + insets.bottom;
        int actualAvailWidth = availWidth - paddingTotalWidth
                - insetsTotalWidth;
        int actualAvailHeight = availHeight - paddingTotalHeight
                - insetsTotalHeight;
        Integer dw = this.getDeclaredWidth(renderState, actualAvailWidth);
        Integer dh = this.getDeclaredHeight(renderState, actualAvailHeight);
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
        this.clearGUIComponents();

        int tentativeWidth;
        int tentativeHeight;

        // Step # 1: If there's no declared width and no width
        // expansion has been requested, do a preliminary layout
        // assuming that the scrollable region has width=0 and
        // there's no wrapping.
        tentativeWidth = declaredWidth == -1 ? availWidth : declaredWidth
                + insetsTotalWidth + paddingTotalWidth;
        tentativeHeight = declaredHeight == -1 ? availHeight : declaredHeight
                + insetsTotalHeight + paddingTotalHeight;
        if ((declaredWidth == -1) && !expandWidth
                && (availWidth > (insetsTotalWidth + paddingTotalWidth))) {
            RenderThreadState state = RenderThreadState.getState();
            boolean prevOverrideNoWrap = state.overrideNoWrap;
            if (!prevOverrideNoWrap) {
                state.overrideNoWrap = true;
                try {
                    int desiredViewportWidth = paddingTotalWidth;
                    int desiredViewportHeight = paddingTotalHeight;
                    bodyLayout.layout(desiredViewportWidth,
                            desiredViewportHeight, paddingInsets, -1, null,
                            true);
                    // If we find that the viewport is not as wide as we
                    // presumed, then we'll use that as a new tentative width.
                    if ((bodyLayout.width + insetsTotalWidth) < tentativeWidth) {
                        tentativeWidth = bodyLayout.width + insetsTotalWidth;
                        tentativeHeight = bodyLayout.height + insetsTotalHeight;
                    }
                } finally {
                    state.overrideNoWrap = false;
                }
            }
        }

        // Step # 2: Do a layout with the tentativeWidth (adjusted if Step # 1
        // was done),
        // but in case overflow-y is "auto", then we check for possible
        // overflow.
        FloatingBounds viewportFloatBounds = null;
        FloatingBounds blockFloatBounds = null;
        if (blockFloatBoundsSource != null) {
            blockFloatBounds = blockFloatBoundsSource
                    .getChildBlockFloatingBounds(tentativeWidth);
            viewportFloatBounds = new ShiftedFloatingBounds(blockFloatBounds,
                    -insets.left, -insets.right, -insets.top);
        }
        int desiredViewportWidth = tentativeWidth - insetsTotalWidth;
        int desiredViewportHeight = tentativeHeight - insets.top
                - insets.bottom;
        int maxY = vauto ? (declaredHeight == -1 ? -1 : declaredHeight
                + paddingInsets.top) : -1;
        try {
            bodyLayout.layout(desiredViewportWidth, desiredViewportHeight,
                    paddingInsets, maxY, viewportFloatBounds, sizeOnly);
        } catch (SizeExceededException see) {
            // Getting this exception means that we need to add a vertical
            // scrollbar.
            // Wee need to relayout and adjust insets and widths for scrollbar.
            vscroll = true;
            insets = this.getInsets(hscroll, vscroll);
            insetsTotalWidth = insets.left + insets.right;
            actualAvailWidth = availWidth - paddingTotalWidth
                    - insetsTotalWidth;
            dw = this.getDeclaredWidth(renderState, actualAvailWidth);
            declaredWidth = dw == null ? -1 : dw.intValue();
            desiredViewportWidth = tentativeWidth - insetsTotalWidth;
            if (blockFloatBounds != null) {
                viewportFloatBounds = new ShiftedFloatingBounds(
                        blockFloatBounds, -insets.left, -insets.right,
                        -insets.top);
            }
            bodyLayout.layout(desiredViewportWidth, desiredViewportHeight,
                    paddingInsets, -1, viewportFloatBounds, sizeOnly);
        }

        int bodyWidth = bodyLayout.width;
        int bodyHeight = bodyLayout.height;
        int prelimBlockWidth = bodyWidth + insetsTotalWidth;
        int prelimBlockHeight = bodyHeight + insetsTotalHeight;
        int adjDeclaredWidth = declaredWidth == -1 ? -1 : declaredWidth
                + insets.left + insets.right + paddingInsets.left
                + paddingInsets.right;
        int adjDeclaredHeight = declaredHeight == -1 ? -1 : declaredHeight
                + insets.top + insets.bottom + paddingInsets.top
                + paddingInsets.bottom;

        // Adjust insets and other dimensions base on overflow-y=auto.
        if (hauto
                && (((adjDeclaredWidth != -1) && (prelimBlockWidth > adjDeclaredWidth)) || (prelimBlockWidth > tentativeWidth))) {
            hscroll = true;
            insets = this.getInsets(hscroll, vscroll);
            insetsTotalHeight = insets.top + insets.bottom;
            prelimBlockHeight = bodyHeight + insetsTotalHeight;
        }

        boolean visibleX = (overflowX == RenderState.OVERFLOW_VISIBLE)
                || (overflowX == RenderState.OVERFLOW_NONE);
        boolean visibleY = (overflowY == RenderState.OVERFLOW_VISIBLE)
                || (overflowY == RenderState.OVERFLOW_NONE);
        int resultingWidth;
        int resultingHeight;
        if (adjDeclaredWidth == -1) {
            resultingWidth = expandWidth ? Math.max(prelimBlockWidth,
                    tentativeWidth) : prelimBlockWidth;
            if (hscroll && (resultingWidth > tentativeWidth)) {
                resultingWidth = Math.max(tentativeWidth, SCROLL_BAR_THICKNESS);
            }
        } else {
            resultingWidth = visibleX ? Math.max(prelimBlockWidth,
                    adjDeclaredWidth) : adjDeclaredWidth;
        }
        if (!sizeOnly) {
            // Align horizontally now. This may change canvas height.
            int alignmentXPercent = rs.getAlignXPercent();
            if (alignmentXPercent > 0) {
                // TODO: OPTIMIZATION: alignment should not be done in table
                // cell
                // sizing determination.
                int canvasWidth = Math.max(bodyLayout.width, resultingWidth
                        - insets.left - insets.right);
                // Alignment is done afterwards because canvas dimensions might
                // have
                // changed.
                bodyLayout
                        .alignX(alignmentXPercent, canvasWidth, paddingInsets);
            }
        }
        if (adjDeclaredHeight == -1) {
            resultingHeight = expandHeight ? Math.max(prelimBlockHeight,
                    tentativeHeight) : prelimBlockHeight;
            if (vscroll && (resultingHeight > tentativeHeight)) {
                resultingHeight = Math.max(tentativeHeight,
                        SCROLL_BAR_THICKNESS);
            }
        } else {
            resultingHeight = visibleY ? Math.max(prelimBlockHeight,
                    adjDeclaredHeight) : adjDeclaredHeight;
        }
        if (!sizeOnly) {
            // Align vertically now
            int alignmentYPercent = rs.getAlignYPercent();
            if (alignmentYPercent > 0) {
                // TODO: OPTIMIZATION: alignment should not be done in table
                // cell
                // sizing determination.
                int canvasHeight = Math.max(bodyLayout.height, resultingHeight
                        - insets.top - insets.bottom);
                // Alignment is done afterwards because canvas dimensions might
                // have
                // changed.
                bodyLayout.alignY(alignmentYPercent, canvasHeight,
                        paddingInsets);
            }
        }
        if (vscroll) {
            JScrollBar sb = this.getVScrollBar();
            this.addComponent(sb);
            // Bounds set by updateWidgetBounds
        }
        if (hscroll) {
            JScrollBar sb = this.getHScrollBar();
            this.addComponent(sb);
            // Bounds set by updateWidgetBounds
        }

        if (hscroll || vscroll) {
            // In this case, viewport origin should not be reset.
            // We don't want to cause the document to scroll back
            // up while rendering.
            this.correctViewportOrigin(insets, resultingWidth, resultingHeight);
            // Now reset the scrollbar state. Depends
            // on block width and height.
            this.width = resultingWidth;
            this.height = resultingHeight;
            this.resetScrollBars(rs);
        } else {
            bodyLayout.x = insets.left;
            bodyLayout.y = insets.top;
        }

        return new LayoutValue(resultingWidth, resultingHeight, hscroll,
                vscroll);
    }

    /**
     * Gets the v unit increment.
     *
     * @param renderState
     *            the render state
     * @return the v unit increment
     */
    private int getVUnitIncrement(RenderState renderState) {
        if (renderState != null) {
            return renderState.getFontMetrics().getHeight();
        } else {
            return new BlockRenderState(null).getFontMetrics().getHeight();
        }
    }

    /** The resetting scroll bars. */
    private boolean resettingScrollBars = false;

    /**
     * Changes scroll bar state to match viewport origin.
     *
     * @param renderState
     *            the render state
     */
    private void resetScrollBars(RenderState renderState) {
        // Expected to be called only in the GUI thread.
        this.resettingScrollBars = true;
        try {
            RBlockViewport bodyLayout = this.bodyLayout;
            if (bodyLayout != null) {
                Insets insets = this.getInsets(this.hasHScrollBar,
                        this.hasVScrollBar);
                JScrollBar vsb = this.vScrollBar;
                if (vsb != null) {
                    int newValue = insets.top - bodyLayout.y;
                    int newExtent = this.height - insets.top - insets.bottom;
                    int newMin = 0;
                    int newMax = bodyLayout.height;
                    vsb.setValues(newValue, newExtent, newMin, newMax);
                    vsb.setUnitIncrement(this.getVUnitIncrement(renderState));
                    vsb.setBlockIncrement(newExtent);
                }
                JScrollBar hsb = this.hScrollBar;
                if (hsb != null) {
                    int newValue = insets.left - bodyLayout.x;
                    int newExtent = this.width - insets.left - insets.right;
                    int newMin = 0;
                    int newMax = bodyLayout.width;
                    hsb.setValues(newValue, newExtent, newMin, newMax);
                }
            }
        } finally {
            this.resettingScrollBars = false;
        }
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.html.render.UIControl#paintSelection(java.awt.Graphics,
 * boolean, org.lobobrowser.html.render.RenderablePoint,
 * org.lobobrowser.html.render.RenderablePoint)
 */
    @Override
    public boolean paintSelection(Graphics g, boolean inSelection,
            RenderableSpot startPoint, RenderableSpot endPoint) {
        Graphics newG = g.create();
        try {
            Insets insets = this.getInsets(this.hasHScrollBar,
                    this.hasVScrollBar);
            // Just clip, don't translate.
            newG.clipRect(insets.left, insets.top, this.width - insets.left
                    - insets.right, this.height - insets.top - insets.bottom);
            return super
                    .paintSelection(newG, inSelection, startPoint, endPoint);
        } finally {
            newG.dispose();
        }
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.html.render.BoundableRenderable#getRenderablePoint(int,
 * int)
 */
    @Override
    public RenderableSpot getLowestRenderableSpot(int x, int y) {
        RBlockViewport bodyLayout = this.bodyLayout;
        if (bodyLayout != null) {
            Insets insets = this.getInsets(this.hasHScrollBar,
                    this.hasVScrollBar);
            if ((x > insets.left) && (x < (this.width - insets.right))
                    && (y > insets.top) && (y < (this.height - insets.bottom))) {
                return bodyLayout.getLowestRenderableSpot(x - bodyLayout.x, y
                        - bodyLayout.y);
            } else {
                return new RenderableSpot(this, x, y);
            }
        } else {
            return new RenderableSpot(this, x, y);
        }
    }

    /**
     * RBlocks should only be invalidated if one of their properties change, or
     * if a descendent changes, or if a style property of an ancestor is such
     * that it could produce layout changes in this RBlock.
     */
    @Override
    public void invalidateLayoutLocal() {
        super.invalidateLayoutLocal();
        this.cachedLayout.clear();
        this.lastLayoutKey = null;
        this.lastLayoutValue = null;
        JScrollBar hScrollBar = this.hScrollBar;
        if (hScrollBar != null) {
            // Necessary
            hScrollBar.invalidate();
        }
        JScrollBar vScrollBar = this.vScrollBar;
        if (vScrollBar != null) {
            // Necessary
            vScrollBar.invalidate();
        }
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.html.renderer.BaseElementRenderable#clearStyle(boolean)
 */
    @Override
    protected void clearStyle(boolean isRootBlock) {
        super.clearStyle(isRootBlock);
        this.overflowX = this.defaultOverflowX;
        this.overflowY = this.defaultOverflowY;
    }

    /*
 * (non-Javadoc)
 * @see
 * org.lobobrowser.html.render.BoundableRenderable#onMouseClick(java.awt.event
 * .MouseEvent, int, int)
 */
    @Override
    public boolean onMouseClick(MouseEvent event, int x, int y) {
        RBlockViewport bodyLayout = this.bodyLayout;

        if (!HtmlController.getInstance().onMouseClick(this.modelNode, event,
                bodyLayout, x, y)) {
            return false;
        }

        if (this.backgroundColor != null) {
            return false;
        }
        return true;
    }

    /*
 * (non-Javadoc)
 * @see
 * org.lobobrowser.html.renderer.BoundableRenderable#onDoubleClick(java.awt.
 * event.MouseEvent, int, int)
 */
    @Override
    public boolean onDoubleClick(MouseEvent event, int x, int y) {
        RBlockViewport bodyLayout = this.bodyLayout;

        if (!HtmlController.getInstance().onDoubleClick(this.modelNode, event,
                bodyLayout, x, y)) {
            return false;
        }

        if (this.backgroundColor != null) {
            return false;
        }
        return true;
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.html.render.BoundableRenderable#onMouseDisarmed(java.awt
 * .event.MouseEvent)
 */
    @Override
    public boolean onMouseDisarmed(MouseEvent event) {
        BoundableRenderable br = this.armedRenderable;
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

    /** The armed renderable. */
    private BoundableRenderable armedRenderable;

    /**
     * @return the armedRenderable
     */
    public BoundableRenderable getArmedRenderable() {
        return armedRenderable;
    }

    /**
     * @param armedRenderable
     *            the armedRenderable to set
     */
    public void setArmedRenderable(BoundableRenderable armedRenderable) {
        this.armedRenderable = armedRenderable;
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.html.render.BoundableRenderable#onMousePressed(java.awt.
 * event.MouseEvent, int, int)
 */
    @Override
    public boolean onMousePressed(MouseEvent event, int x, int y) {

        if (!HtmlController.getInstance().onMouseDown(this.modelNode, event,
                this, x, y)) {
            return false;
        }
        if (this.backgroundColor != null) {
            return false;
        }
        return true;
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.html.render.BoundableRenderable#onMouseReleased(java.awt
 * .event.MouseEvent, int, int)
 */
    @Override
    public boolean onMouseReleased(MouseEvent event, int x, int y) {

        if (!HtmlController.getInstance().onMouseUp(this.modelNode, event,
                this, x, y)) {
            return false;
        }

        if (this.backgroundColor != null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onKeyPressed(KeyEvent event) {

        if (!HtmlController.getInstance().onKeyPress(this.modelNode, event)) {
            return false;
        }

        return true;

    }

    @Override
    public boolean onKeyUp(KeyEvent event) {
        if (!HtmlController.getInstance().onKeyUp(this.modelNode, event)) {
            return false;
        }

        return true;

    }

    @Override
    public boolean onKeyDown(KeyEvent event) {
        if (!HtmlController.getInstance().onKeyDown(this.modelNode, event)) {
            return false;
        }

        return true;

    }

    /*
 * (non-Javadoc)
 * @see
 * org.lobobrowser.html.renderer.RenderableContainer#getPaintedBackgroundColor()
 */
    @Override
    public Color getPaintedBackgroundColor() {
        return this.backgroundColor;
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.html.render.RCollection#getRenderables()
 */
    @Override
    public Iterator<?> getRenderables() {
        final RBlockViewport bodyLayout = this.bodyLayout;
        return new Iterator<Object>() {
            private RBlockViewport bl = bodyLayout;

            @Override
            public boolean hasNext() {
                return bl != null;
            }

            @Override
            public Object next() {
                if (bl == null) {
                    throw new NoSuchElementException();
                }
                try {
                    return bl;
                } finally {
                    bl = null;
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.html.dombl.UINode(org.lobobrowser.html.dombl.ModelNode
 * modelNode);
 */
    @Override
    public void repaint(ModelNode modelNode) {
        // this.invalidateRenderStyle();
        this.repaint();
    }

    /*
 * (non-Javadoc)
 * @see org.lobobrowser.html.renderer.BaseRCollection#updateWidgetBounds(int,
 * int)
 */
    @Override
    public void updateWidgetBounds(int guiX, int guiY) {
        super.updateWidgetBounds(guiX, guiY);
        boolean hscroll = this.hasHScrollBar;
        boolean vscroll = this.hasVScrollBar;
        if (hscroll || vscroll) {
            Insets insets = this.getInsets(hscroll, vscroll);
            if (hscroll) {
                JScrollBar hsb = this.hScrollBar;
                if (hsb != null) {
                    hsb.setBounds(guiX + insets.left, (guiY + this.height)
                            - insets.bottom, this.width - insets.left
                            - insets.right, SCROLL_BAR_THICKNESS);
                }
            }
            if (vscroll) {
                JScrollBar vsb = this.vScrollBar;
                if (vsb != null) {
                    vsb.setBounds((guiX + this.width) - insets.right, guiY
                            + insets.top, SCROLL_BAR_THICKNESS, this.height
                            - insets.top - insets.bottom);
                }
            }
        }
    }

    /**
     * Scroll horizontal to.
     *
     * @param newX
     *            the new x
     */
    public void scrollHorizontalTo(int newX) {
        RBlockViewport bodyLayout = this.bodyLayout;
        if (bodyLayout != null) {
            Insets insets = this.getInsets(this.hasHScrollBar,
                    this.hasVScrollBar);
            int viewPortX = newX;
            if (viewPortX > insets.left) {
                bodyLayout.x = insets.left;
            } else if (viewPortX < (this.width - insets.right - bodyLayout.width)) {
                bodyLayout.x = Math.min(insets.left, this.width - insets.right
                        - bodyLayout.width);
            } else {
                bodyLayout.x = viewPortX;
            }
            this.resetScrollBars(null);
            this.updateWidgetBounds();
            this.repaint();
        }
    }

    /**
     * Scroll vertical to.
     *
     * @param newY
     *            the new y
     */
    public void scrollVerticalTo(int newY) {
        RBlockViewport bodyLayout = this.bodyLayout;
        if (bodyLayout != null) {
            Insets insets = this.getInsets(this.hasHScrollBar,
                    this.hasVScrollBar);
            int viewPortY = newY;
            if (viewPortY > insets.top) {
                bodyLayout.y = insets.top;
            } else if (viewPortY < (this.height - insets.bottom - bodyLayout.height)) {
                bodyLayout.y = Math.min(insets.top, this.height - insets.bottom
                        - bodyLayout.height);
            } else {
                bodyLayout.y = viewPortY;
            }
            this.resetScrollBars(null);
            this.updateWidgetBounds();
            this.repaint();
        }
    }

    /**
     * Scroll by units.
     *
     * @param orientation
     *            the orientation
     * @param units
     *            the units
     */
    public void scrollByUnits(int orientation, int units) {
        int offset = orientation == Adjustable.VERTICAL ? this
                .getVUnitIncrement(null) * units : units;
        this.scrollBy(orientation, offset);
    }

    /**
     * Scroll by.
     *
     * @param orientation
     *            the orientation
     * @param offset
     *            the offset
     */
    public void scrollBy(int orientation, int offset) {
        RBlockViewport bodyLayout = this.bodyLayout;
        if (bodyLayout != null) {
            switch (orientation) {
            case Adjustable.HORIZONTAL:
                this.scrollHorizontalTo(bodyLayout.x - offset);
                break;
            case Adjustable.VERTICAL:
                this.scrollVerticalTo(bodyLayout.y - offset);
                break;
            }
        }
    }

    /**
     * Scrolls the viewport's origin to the given location, or as close to it as
     * possible.
     * <p>
     * This method should be invoked in the GUI thread.
     *
     * @param bounds
     *            The bounds of the scrollable area that should become visible.
     * @param xIfNeeded
     *            If this parameter is <code>true</code> the x coordinate is
     *            changed only if the horizontal bounds are not currently
     *            visible.
     * @param yIfNeeded
     *            If this parameter is <code>true</code> the y coordinate is
     *            changed only if the vertical bounds are not currently visible.
     */
    public void scrollTo(Rectangle bounds, boolean xIfNeeded, boolean yIfNeeded) {
        boolean hscroll = this.hasHScrollBar;
        boolean vscroll = this.hasVScrollBar;
        if (hscroll || vscroll) {
            RBlockViewport bv = this.bodyLayout;
            Insets insets = this.getInsets(hscroll, vscroll);
            int vpheight = this.height - insets.top - insets.bottom;
            int vpwidth = this.width - insets.left - insets.right;
            int tentativeX = insets.left - bounds.x;
            int tentativeY = insets.top - bounds.y;
            boolean needCorrection = false;
            if (!(xIfNeeded && (tentativeX <= bv.x) && ((-tentativeX + bv.x + bounds.width) <= vpwidth))) {
                bv.setX(tentativeX);
                needCorrection = true;
            }
            if (!(yIfNeeded && (tentativeY <= bv.y) && ((-tentativeY + bv.y + bounds.height) <= vpheight))) {
                bv.setY(tentativeY);
                needCorrection = true;
            }
            if (needCorrection) {
                this.correctViewportOrigin(insets, this.width, this.height);
            }
        }
    }

    /**
     * Scroll to sb value.
     *
     * @param orientation
     *            the orientation
     * @param value
     *            the value
     */
    private void scrollToSBValue(int orientation, int value) {
        Insets insets = this.getInsets(this.hasHScrollBar, this.hasVScrollBar);
        switch (orientation) {
        case Adjustable.HORIZONTAL:
            int xOrigin = insets.left - value;
            this.scrollHorizontalTo(xOrigin);
            break;
        case Adjustable.VERTICAL:
            int yOrigin = insets.top - value;
            this.scrollVerticalTo(yOrigin);
            break;
        }
    }

    /**
     * Gets the r block viewport.
     *
     * @return the r block viewport
     */
    public RBlockViewport getRBlockViewport() {
        return this.bodyLayout;
    }

    /*
 * (non-Javadoc)
 * @see
 * org.lobobrowser.html.renderer.BaseRCollection#extractSelectionText(java.lang
 * .StringBuffer, boolean, org.lobobrowser.html.renderer.RenderableSpot,
 * org.lobobrowser.html.renderer.RenderableSpot)
 */
    @Override
    public boolean extractSelectionText(StringBuffer buffer,
            boolean inSelection, RenderableSpot startPoint,
            RenderableSpot endPoint) {
        boolean result = super.extractSelectionText(buffer, inSelection,
                startPoint, endPoint);
        String br = System.getProperty("line.separator");
        if (inSelection) {
            buffer.insert(0, br);
        }
        if (result) {
            buffer.append(br);
        }
        return result;
    }

    /*
 * (non-Javadoc)
 * @see java.lang.Object#toString()
 */
    @Override
    public String toString() {
        return "RBlock[node=" + this.modelNode + "]";
    }

    /**
     * Gets the exportable floating info.
     *
     * @return the exportable floating info
     */
    public FloatingInfo getExportableFloatingInfo() {
        FloatingInfo info = this.bodyLayout.getExportableFloatingInfo();
        if (info == null) {
            return null;
        }
        Insets insets = this.getInsets(this.hasHScrollBar, this.hasVScrollBar);
        return new FloatingInfo(info.getShiftX() + insets.left, info.getShiftY()
                + insets.top, info.getFloats());
    }

    /**
     * The listener interface for receiving localAdjustment events. The class
     * that is interested in processing a localAdjustment event implements this
     * interface, and the object created with that class is registered with a
     * component using the component's <code>addLocalAdjustmentListener</code>
     * method. When the localAdjustment event occurs, that object's appropriate
     * method is invoked.
     *
     * @see LocalAdjustmentEvent
     */
    private class LocalAdjustmentListener implements AdjustmentListener {

        /** The orientation. */
        private final int orientation;

        /**
         * Instantiates a new local adjustment listener.
         *
         * @param orientation
         *            the orientation
         */
        public LocalAdjustmentListener(int orientation) {
            this.orientation = orientation;
        }

        /*
 * (non-Javadoc)
 * @see java.awt.event.AdjustmentListener#adjustmentValueChanged(java.awt.event.
 * AdjustmentEvent)
 */
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
                int value = e.getValue();
                RBlock.this.scrollToSBValue(this.orientation, value);
                break;
            }
            }
        }
    }

    /**
     * Gets the default overflow x.
     *
     * @return the default overflow x
     */
    public int getDefaultOverflowX() {
        return defaultOverflowX;
    }

    /**
     * Gets the default overflow y.
     *
     * @return the default overflow y
     */
    public int getDefaultOverflowY() {
        return defaultOverflowY;
    }

    /**
     * Sets the default overflow x.
     *
     * @param defaultOverflowX
     *            the new default overflow x
     */
    public void setDefaultOverflowX(int defaultOverflowX) {
        this.defaultOverflowX = defaultOverflowX;
    }

    /**
     * Sets the default overflow y.
     *
     * @param defaultOverflowY
     *            the new default overflow y
     */
    public void setDefaultOverflowY(int defaultOverflowY) {
        this.defaultOverflowY = defaultOverflowY;
    }

    /**
     * The Class LayoutKey.
     */
    public static class LayoutKey {

        /** The avail width. */
        public final int availWidth;

        /** The avail height. */
        public final int availHeight;

        /** The float bounds source. */
        public final FloatingBoundsSource floatBoundsSource;

        /** The default overflow x. */
        public final int defaultOverflowX;

        /** The default overflow y. */
        public final int defaultOverflowY;

        /** The whitespace. */
        public final int whitespace;

        /** The font. */
        public final Font font;

        /** The expand width. */
        public final boolean expandWidth;

        /** The expand height. */
        public final boolean expandHeight;

        /** The use declared size. */
        public final boolean useDeclaredSize;

        /** The override no wrap. */
        public final boolean overrideNoWrap;

        /**
         * Instantiates a new layout key.
         *
         * @param availWidth
         *            the avail width
         * @param availHeight
         *            the avail height
         * @param expandWidth
         *            the expand width
         * @param expandHeight
         *            the expand height
         * @param floatBoundsSource
         *            the float bounds source
         * @param defaultOverflowX
         *            the default overflow x
         * @param defaultOverflowY
         *            the default overflow y
         * @param whitespace
         *            the whitespace
         * @param font
         *            the font
         * @param overrideNoWrap
         *            the override no wrap
         */
        public LayoutKey(int availWidth, int availHeight, boolean expandWidth,
                boolean expandHeight, FloatingBoundsSource floatBoundsSource,
                int defaultOverflowX, int defaultOverflowY, int whitespace,
                Font font, boolean overrideNoWrap) {
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
            this.overrideNoWrap = overrideNoWrap;
        }

        /*
 * (non-Javadoc)
 * @see java.lang.Object#equals(java.lang.Object)
 */
        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof LayoutKey)) {
                return false;
            }
            LayoutKey other = (LayoutKey) obj;
            return (other.availWidth == this.availWidth)
                    && (other.availHeight == this.availHeight)
                    && (other.defaultOverflowX == this.defaultOverflowX)
                    && (other.defaultOverflowY == this.defaultOverflowY)
                    && (other.whitespace == this.whitespace)
                    && (other.expandWidth == this.expandWidth)
                    && (other.expandHeight == this.expandHeight)
                    && (other.useDeclaredSize == this.useDeclaredSize)
                    && (other.overrideNoWrap == this.overrideNoWrap)
                    && Objects.equals(other.font, this.font)
                    && Objects.equals(other.floatBoundsSource,
                            this.floatBoundsSource);
        }

        /*
 * (non-Javadoc)
 * @see java.lang.Object#hashCode()
 */
        @Override
        public int hashCode() {
            Font font = this.font;
            return ((this.availWidth * 1000) + this.availHeight)
                    ^ (font == null ? 0 : font.hashCode())
                    ^ (this.expandWidth ? 2 : 0) ^ (this.expandHeight ? 1 : 0)
                    ^ (this.whitespace << 2);
        }
    }

}
