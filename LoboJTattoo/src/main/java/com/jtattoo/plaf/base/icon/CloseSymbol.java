package com.jtattoo.plaf.base.icon;

import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.*;
import java.awt.*;

public class CloseSymbol implements Icon {

    private Color foregroundColor = null;
    private Color shadowColor = null;
    private Color rolloverColor = null;
    private Color inactiveForegroundColor = null;
    private Color inactiveShadowColor = null;
    private Insets insets = new Insets(0, 0, 0, 0);

    public CloseSymbol(final Color foregroundColor, final Color shadowColor, final Color rolloverColor) {
        this.foregroundColor = foregroundColor;
        this.shadowColor = shadowColor;
        this.rolloverColor = rolloverColor;
        this.inactiveForegroundColor = foregroundColor;
        this.inactiveShadowColor = shadowColor;
    }

    public CloseSymbol(final Color foregroundColor, final Color shadowColor, final Color rolloverColor, final Color inactiveForegroundColor,
                       final Color inactiveShadowColor, final Insets insets) {
        this.foregroundColor = foregroundColor;
        this.shadowColor = shadowColor;
        this.rolloverColor = rolloverColor;
        this.inactiveForegroundColor = inactiveForegroundColor;
        this.inactiveShadowColor = inactiveShadowColor;
        this.insets = insets;
    }

    public CloseSymbol(final Color foregroundColor, final Color shadowColor, final Color rolloverColor, final Insets insets) {
        this.foregroundColor = foregroundColor;
        this.shadowColor = shadowColor;
        this.rolloverColor = rolloverColor;
        this.insets = insets;
        this.inactiveForegroundColor = foregroundColor;
        this.inactiveShadowColor = shadowColor;
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
        final int w = c.getWidth() - insets.left - insets.right;
        final int h = c.getHeight() - insets.top - insets.bottom;
        final boolean active = JTattooUtilities.isActive((JComponent) c);
        Color color = foregroundColor;
        if (!active) {
            color = inactiveForegroundColor;
        }
        if (c instanceof AbstractButton) {
            if (((AbstractButton) c).getModel().isRollover() && rolloverColor != null) {
                color = rolloverColor;
            }
        }
        // int lw = (w / 12) + 1;
        final int lw = h > 22 ? 3 : 2;
        final int dx = w / 5 + 2;
        final int dy = dx;

        final Stroke savedStroke = g2D.getStroke();
        final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.setStroke(new BasicStroke(lw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        if (shadowColor != null) {
            if (!active) {
                g2D.setColor(inactiveShadowColor);
            } else {
                g2D.setColor(shadowColor);
            }
            g2D.drawLine(dx + 1, dy + 1, w - dx + 1, h - dy + 1);
            g2D.drawLine(w - dx + 1, dy + 1, dx + 1, h - dy + 1);
        }
        g2D.setColor(color);
        g2D.drawLine(dx, dy, w - dx, h - dy);
        g2D.drawLine(w - dx, dy, dx, h - dy);

        g2D.setStroke(savedStroke);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
        g2D.translate(-insets.left, -insets.top);
    }

} // end of class CloseSymbol
