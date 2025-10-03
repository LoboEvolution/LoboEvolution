package com.jtattoo.plaf.base.icon;

import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.*;
import java.awt.*;

public class IconSymbol implements Icon {

    private final Color foregroundColor;
    private final Color shadowColor;
    private final Color inactiveForegroundColor;
    private final Color inactiveShadowColor;
    private final Color rolloverColor;
    private Insets insets = new Insets(0, 0, 0, 0);

    public IconSymbol(final Color foregroundColor, final Color shadowColor, final Color rolloverColor) {
        this.foregroundColor = foregroundColor;
        this.shadowColor = shadowColor;
        this.rolloverColor = rolloverColor;
        this.inactiveForegroundColor = foregroundColor;
        this.inactiveShadowColor = shadowColor;
    }

    public IconSymbol(final Color foregroundColor, final Color shadowColor, final Color rolloverColor, final Color inactiveForegroundColor,
                      final Color inactiveShadowColor, final Insets insets) {
        this.foregroundColor = foregroundColor;
        this.shadowColor = shadowColor;
        this.rolloverColor = rolloverColor;
        this.inactiveForegroundColor = inactiveForegroundColor;
        this.inactiveShadowColor = inactiveShadowColor;
        this.insets = insets;
    }

    public IconSymbol(final Color foregroundColor, final Color shadowColor, final Color rolloverColor, final Insets insets) {
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
        g2D.setStroke(new BasicStroke(lw, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        if (shadowColor != null) {
            if (!active) {
                g2D.setColor(inactiveShadowColor);
            } else {
                g2D.setColor(shadowColor);
            }
            g2D.drawLine(dx + 1, h - dy, w - dx + 1, h - dy);
        }
        g2D.setColor(color);
        g2D.drawLine(dx, h - dy - 1, w - dx, h - dy - 1);
        g2D.setStroke(savedStroke);
        g2D.translate(-insets.left, -insets.top);
    }

} // end of class IconSymbol
