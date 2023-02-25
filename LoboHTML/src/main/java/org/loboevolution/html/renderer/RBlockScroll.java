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

import org.loboevolution.html.renderstate.RenderState;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * <p>RBlockScroll class.</p>
 */
public class RBlockScroll {

    private final RBlock rBlock;

    private JScrollBar hScrollBar;

    private JScrollBar vScrollBar;

    private boolean resettingScrollBars;

    /**
     * <p>Constructor for RBlockScroll.</p>
     *
     * @param rBlock a {@link org.loboevolution.html.renderer.RBlock} object.
     */
    public RBlockScroll(RBlock rBlock) {
        this.rBlock = rBlock;
    }

    /**
     * <p>paintSroll.</p>
     *
     * @param g a {@link java.awt.Graphics} object.
     */
    public void paintSroll(Graphics g) {
        final JScrollBar hsb = this.hScrollBar;
        final Insets insets = rBlock.getInsetsMarginBorder(rBlock.hasHScrollBar, rBlock.hasVScrollBar);
        if (hsb != null) {
            final Graphics sbg = g.create(insets.left, rBlock.getHeight() - insets.bottom, rBlock.getWidth() - insets.left - insets.right, 16);
            try {
                hsb.paint(sbg);
            } finally {
                sbg.dispose();
            }
        }
        final JScrollBar vsb = this.vScrollBar;
        if (vsb != null) {
            final Graphics sbg = g.create(rBlock.getWidth() - insets.right, insets.top, 16, rBlock.getHeight() - insets.top - insets.bottom);
            try {
                vsb.paint(sbg);
            } finally {
                sbg.dispose();
            }
        }
    }

    /**
     * <p>getVScrollBar.</p>
     */
    protected JScrollBar getVScrollBar() {
        JScrollBar sb = this.vScrollBar;
        if (sb == null) {
            sb = new JScrollBar(JScrollBar.VERTICAL);
            sb.addAdjustmentListener(new LocalAdjustmentListener(JScrollBar.VERTICAL));
            BoundedRangeModel model = sb.getModel();
            model.addChangeListener(new BoundedChangeListener(JScrollBar.VERTICAL));
            this.vScrollBar = sb;
        }
        return sb;
    }

    /**
     * <p>getHScrollBar.</p>
     */
    protected JScrollBar getHScrollBar() {
        JScrollBar sb = this.hScrollBar;
        if (sb == null) {
            sb = new JScrollBar(JScrollBar.HORIZONTAL);
            sb.addAdjustmentListener(new LocalAdjustmentListener(JScrollBar.HORIZONTAL));
            BoundedRangeModel model = sb.getModel();
            model.addChangeListener(new BoundedChangeListener(JScrollBar.HORIZONTAL));
            this.hScrollBar = sb;
        }
        return sb;
    }

    /**
     * <p>invalidateLayoutLocal.</p>
     */
    protected void invalidateLayoutLocal() {
        final JScrollBar hScrollBar = this.hScrollBar;
        if (hScrollBar != null) {
            hScrollBar.invalidate();
        }
        final JScrollBar vScrollBar = this.vScrollBar;
        if (vScrollBar != null) {
            vScrollBar.invalidate();
        }
    }

    /**
     * <p>resetScrollBars.</p>
     * Changes scroll bar state to match viewport origin.
     */
    protected void resetScrollBars(RenderState renderState) {
        this.resettingScrollBars = true;
        try {
            final RBlockViewport bodyLayout = rBlock.bodyLayout;
            if (bodyLayout != null) {
                final Insets insets = rBlock.getInsetsMarginBorder(rBlock.hasHScrollBar, rBlock.hasVScrollBar);
                final JScrollBar vsb = this.vScrollBar;
                if (vsb != null) {
                    final int newValue = insets.top - bodyLayout.getY();
                    final int newExtent = rBlock.getHeight() - insets.top - insets.bottom;
                    final int newMin = 0;
                    final int newMax = bodyLayout.getHeight();
                    vsb.setValues(newValue, newExtent, newMin, newMax);
                    vsb.setUnitIncrement(getVUnitIncrement());
                    vsb.setBlockIncrement(newExtent);
                }
                final JScrollBar hsb = this.hScrollBar;
                if (hsb != null) {
                    final int newValue = insets.left - bodyLayout.getX();
                    final int newExtent = rBlock.getWidth() - insets.left - insets.right;
                    final int newMin = 0;
                    final int newMax = bodyLayout.getWidth();
                    hsb.setValues(newValue, newExtent, newMin, newMax);
                }
            }
        } finally {
            this.resettingScrollBars = false;
        }
    }

    /**
     * <p>updateWidgetBounds.</p>
     */
    protected void updateWidgetBounds(int guiX, int guiY) {
        final boolean hscroll = rBlock.hasHScrollBar;
        final boolean vscroll = rBlock.hasVScrollBar;
        if (hscroll || vscroll) {
            final Insets insets = rBlock.getInsetsMarginBorder(hscroll, vscroll);
            if (hscroll) {
                final JScrollBar hsb = this.hScrollBar;
                if (hsb != null) {
                    hsb.setBounds(guiX + insets.left, guiY + rBlock.getHeight() - insets.bottom,
                            rBlock.getWidth() - insets.left - insets.right, 16);
                }
            }
            if (vscroll) {
                final JScrollBar vsb = this.vScrollBar;
                if (vsb != null) {
                    vsb.setBounds(guiX + rBlock.getWidth() - insets.right, guiY + insets.top, 16,
                            rBlock.getHeight() - insets.top - insets.bottom);
                }
            }
        }
    }

    /**
     * <p>getVUnitIncrement.</p>
     */
    protected int getVUnitIncrement() {
        return 4;
    }

    /**
     * <p>scrollBy.</p>
     *
     * @param orientation a {@link java.lang.Integer} object.
     * @param offset a {@link java.lang.Integer} object.
     * @return a a {@link java.lang.Boolean} object.
     */
    public boolean scrollBy(int orientation, double offset) {
        switch (orientation) {
            case JScrollBar.HORIZONTAL:
                return scrollHorizontalTo(- offset);
            case JScrollBar.VERTICAL:
                return scrollVerticalTo(- offset);
            default:
                break;
        }
        return false;
    }

    private void scrollToSBValue(int orientation, double value) {
        final Insets insets = rBlock.getInsetsMarginBorder(rBlock.hasHScrollBar, rBlock.hasVScrollBar);
        switch (orientation) {
            case JScrollBar.HORIZONTAL:
                final double xOrigin = insets.left - value;
                scrollHorizontalTo(xOrigin);
                break;
            case JScrollBar.VERTICAL:
                final double yOrigin = insets.top - value;
                scrollVerticalTo(yOrigin);
                break;
        }
    }

    private boolean scrollVerticalTo(double newY) {
        final RBlockViewport bodyLayout = rBlock.bodyLayout;
        if (bodyLayout != null) {
            final Insets insets = rBlock.getInsetsMarginBorder(rBlock.hasHScrollBar, rBlock.hasVScrollBar);
            final int prevY = bodyLayout.getY();
            final int prevHeight = rBlock.getHeight() - insets.bottom - bodyLayout.getHeight();
            final int min = Math.min(insets.top, prevHeight);

            if (newY > insets.top && bodyLayout.getY() <= 0) {
                bodyLayout.setY(bodyLayout.getY() + (int) newY);
            } else if (newY < prevHeight) {
                bodyLayout.setY(min);
            } else if (newY < insets.top && bodyLayout.getY() > 0) {
                bodyLayout.setY(bodyLayout.getY() + (int) newY);

            } else {
                if (bodyLayout.getY() <= 1 && min < bodyLayout.getY()) {
                    bodyLayout.setY(bodyLayout.getY() + (int) newY);
                }
            }

            resetScrollBars(null);
            rBlock.updateWidgetBounds();
            rBlock.repaint();
            return bodyLayout.getY() != prevY;
        }
        return false;
    }

    private boolean scrollHorizontalTo(double newX) {
        final RBlockViewport bodyLayout = rBlock.bodyLayout;
        if (bodyLayout != null) {
            final Insets insets = rBlock.getInsetsMarginBorder(rBlock.hasHScrollBar, rBlock.hasVScrollBar);
            final int prevX = bodyLayout.getX();
            final int prevWidth = rBlock.getWidth() - insets.right - bodyLayout.getWidth();

            if (newX == 0) {
                bodyLayout.setX(0);
            } else if (newX < 0 && newX > prevWidth) {
                bodyLayout.setX((int) newX - 10);
            } else if (newX > 0 && newX < prevWidth) {
                bodyLayout.setX((int) newX + 10);
            }

            resetScrollBars(null);
            rBlock.updateWidgetBounds();
            rBlock.repaint();
            return bodyLayout.getX() != prevX;
        }
        return false;
    }


    private class BoundedChangeListener implements ChangeListener {

        private final int orientation;

        BoundedChangeListener(int orientation) {
            this.orientation = orientation;
        }

        public void stateChanged(ChangeEvent changeEvent) {
            Object source = changeEvent.getSource();
            if (source instanceof BoundedRangeModel) {
                BoundedRangeModel aModel = (BoundedRangeModel) source;
                if (!aModel.getValueIsAdjusting()) {
                    if (orientation == JScrollBar.HORIZONTAL) rBlock.rendererContext.setScrollx(aModel.getValue());
                    if (orientation == JScrollBar.VERTICAL) rBlock.rendererContext.setScrolly(aModel.getValue());
                }
            }
        }
    }

    private class LocalAdjustmentListener implements AdjustmentListener {
        private final int orientation;

        public LocalAdjustmentListener(int orientation) {
            this.orientation = orientation;
        }

        @Override
        public void adjustmentValueChanged(AdjustmentEvent evt) {
            if (evt.getValueIsAdjusting()) {
                return;
            }

            if (!resettingScrollBars) {
                final int value = evt.getValue();
                scrollToSBValue(this.orientation, value);
                rBlock.onMouseScroll();
            }
        }
    }
}
