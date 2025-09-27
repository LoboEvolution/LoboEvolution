package com.jtattoo.plaf.base.icon;

import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class MinSymbol implements Icon {

    private Color foregroundColor = null;
    private Color shadowColor = null;
    private Color rolloverColor = null;
    private Color inactiveForegroundColor = null;
    private Color inactiveShadowColor = null;
    private Insets insets = new Insets(0, 0, 0, 0);

    public MinSymbol(final Color foregroundColor, final Color shadowColor, final Color rolloverColor) {
        this.foregroundColor = foregroundColor;
        this.shadowColor = shadowColor;
        this.rolloverColor = rolloverColor;
        this.inactiveForegroundColor = foregroundColor;
        this.inactiveShadowColor = shadowColor;
    }

    public MinSymbol(final Color foregroundColor, final Color shadowColor, final Color rolloverColor, final Color inactiveForegroundColor,
                     final Color inactiveShadowColor, final Insets insets) {
        this.foregroundColor = foregroundColor;
        this.shadowColor = shadowColor;
        this.rolloverColor = rolloverColor;
        this.inactiveForegroundColor = inactiveForegroundColor;
        this.inactiveShadowColor = inactiveShadowColor;
        this.insets = insets;
    }

    public MinSymbol(final Color foregroundColor, final Color shadowColor, final Color rolloverColor, final Insets insets) {
        this.foregroundColor = foregroundColor;
        this.shadowColor = shadowColor;
        this.rolloverColor = rolloverColor;
        this.inactiveForegroundColor = foregroundColor;
        this.inactiveShadowColor = shadowColor;
        this.insets = insets;
    }

    @Override
    public int getIconHeight() {
        return 16;
    }

    @Override
    public int getIconWidth() {
        return 16;
    }

    @Override
    public void paintIcon(final Component c, final Graphics g, final int iconX, final int iconY) {
        final Graphics2D g2D = (Graphics2D) g;
        g2D.translate(insets.left, insets.top);
        int w = c.getWidth() - insets.left - insets.right;
        int h = c.getHeight() - insets.top - insets.bottom;

        final int lw = h > 22 ? 2 : 1;
        final int delta = w / 4;

        w = Math.min(w, h) - 6;
        h = w;

        final int x1 = 3;
        final int y1 = 3;
        final int w1 = w - delta;
        final int h1 = h - delta;

        final int x2 = delta + 2;
        final int y2 = Math.max(delta + 2, y1 + 2 * lw + 1);
        final int w2 = w - delta;
        final int h2 = h - delta;

        final boolean active = JTattooUtilities.isActive((JComponent) c);
        Color ic = foregroundColor;
        Color sc = shadowColor;
        if (!active) {
            ic = inactiveForegroundColor;
            if (sc != null) {
                sc = inactiveShadowColor;
            }
        }
        if (c instanceof AbstractButton) {
            if (((AbstractButton) c).getModel().isRollover() && rolloverColor != null) {
                ic = rolloverColor;
            }
        }

        final Shape savedClip = g2D.getClip();
        final Stroke savedStroke = g2D.getStroke();
        g2D.setStroke(new BasicStroke(lw, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        final Area clipArea = new Area(savedClip);
        clipArea.subtract(new Area(new Rectangle2D.Double(x2, y2, w2, h2)));
        g2D.setClip(clipArea);
        paintRect(g2D, x1, y1, w1, h1, lw, ic, sc);
        g2D.setClip(savedClip);
        paintRect(g2D, x2, y2, w2, h2, lw, ic, sc);

        g2D.setStroke(savedStroke);
        g2D.translate(-insets.left, -insets.top);
    }

    private void paintRect(final Graphics2D g2D, final int x, final int y, final int w, final int h, final int lw, final Color iconColor, final Color shadowColor) {
        if (shadowColor != null) {
            g2D.setColor(shadowColor);
            g2D.drawRect(x + 1, y + 1, w, h);
            g2D.drawLine(x + 1, y + lw + 1, x + w + 1, y + lw + 1);
        }
        g2D.setColor(iconColor);
        g2D.drawRect(x, y, w, h);
        g2D.drawLine(x, y + lw, x + w, y + lw);

    }

} // end of class MinSymbol
