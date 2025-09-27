package com.jtattoo.plaf.smart.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.base.border.BaseInternalFrameBorder;

import java.awt.*;
import java.io.Serial;

public class InternalFrameBorder extends BaseInternalFrameBorder {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
        final Graphics2D g2D = (Graphics2D) g;
        final boolean active = isActive(c);
        final boolean resizable = isResizable(c);
        final int th = getTitleHeight(c);
        Color frameColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
        Color titleColor = AbstractLookAndFeel.getWindowInactiveTitleColorLight();
        if (active) {
            titleColor = AbstractLookAndFeel.getWindowTitleColorLight();
            frameColor = AbstractLookAndFeel.getWindowBorderColor();
        }

        if (!resizable) {
            final Insets bi = getBorderInsets(c);
            g.setColor(frameColor);
            g.drawRect(x, y, w - 1, h - 1);
            if (active) {
                g.setColor(AbstractLookAndFeel.getWindowTitleColorDark());
            } else {
                g.setColor(AbstractLookAndFeel.getWindowInactiveTitleColorDark());
            }
            for (int i = 1; i < bi.left; i++) {
                g.drawRect(i, i, w - 2 * i - 1, h - 2 * i - 1);
            }
            g.setColor(frameColor);
            g.drawLine(bi.left - 1, y + th + bi.top, bi.left - 1, y + h - bi.bottom);
            g.drawLine(w - bi.right, y + th + bi.top, w - bi.right, y + h - bi.bottom);
            g.drawLine(bi.left - 1, y + h - bi.bottom, w - bi.right, y + h - bi.bottom);
        } else {
            g.setColor(titleColor);
            g.fillRect(x, y + 1, w, DW - 1);
            g.fillRect(x + 1, y + h - DW, w - 2, DW - 1);
            final Color color;
            if (active) {
                JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 1, DW, DW, th + 1);
                JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), w - DW, DW, DW, th + 1);

                final Color c1 = AbstractLookAndFeel.getTheme().getWindowTitleColorDark();
                final Color c2 = AbstractLookAndFeel.getTheme().getWindowTitleColorLight();
                g2D.setPaint(new GradientPaint(0, DW + th + 1, c1, 0, h - th - 2 * DW, c2));
                g.fillRect(1, DW + th + 1, DW - 1, h - th - 2 * DW);
                g.fillRect(w - DW, DW + th + 1, DW - 1, h - th - 2 * DW);
                g2D.setPaint(null);
            } else {
                JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 1, DW, DW, th + 1);
                JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), w - DW, DW, DW, th + 1);

                final Color c1 = AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorDark();
                final Color c2 = AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorLight();
                g2D.setPaint(new GradientPaint(0, DW + th + 1, c1, 0, h - th - 2 * DW, c2));
                g.fillRect(1, DW + th + 1, DW - 1, h - th - 2 * DW);
                g.fillRect(w - DW, DW + th + 1, DW - 1, h - th - 2 * DW);
                g2D.setPaint(null);
            }
            if (active && resizable) {
                final int d = DW + 12;
                // unten
                color = AbstractLookAndFeel.getWindowTitleColorDark();
                final Color cHi = ColorHelper.brighter(color, 30);
                final Color cLo = ColorHelper.darker(color, 20);

                // links
                g.setColor(color);
                g.fillRect(x + 1, y + h - d, DW - 1, d - 1);
                g.fillRect(x + DW, y + h - DW, d - DW - 1, d - DW - 1);

                g.setColor(cLo);
                g.drawLine(x + 1, y + h - d - 2, x + DW - 2, y + h - d - 2);
                g.drawLine(x + DW - 2, y + h - d - 2, x + DW - 2, y + h - DW);
                g.drawLine(x + DW - 2, y + h - DW, x + d - 1, y + h - DW);
                g.drawLine(x + d - 1, y + h - DW, x + d - 1, y + h - 1);

                g.setColor(cHi);
                g.drawLine(x + 1, y + h - d - 1, x + DW - 3, y + h - d - 1);
                g.drawLine(x + DW - 1, y + h - d - 1, x + DW - 1, y + h - DW - 1);
                g.drawLine(x + DW - 1, y + h - DW + 1, x + d - 2, y + h - DW + 1);
                g.drawLine(x + d - 2, y + h - DW + 1, x + d - 2, y + h - 1);

                // rechts
                g.setColor(color);
                g.fillRect(x + w - d - 1, y + h - DW, d, DW - 1);
                g.fillRect(x + w - DW, y + h - d - 1, DW - 1, d);

                g.setColor(cLo);
                g.drawLine(x + w - DW - 1, y + h - d - 2, x + w - 1, y + h - d - 2);
                g.drawLine(x + w - DW, y + h - d - 2, x + w - DW, y + h - DW);
                g.drawLine(x + w - d - 1, y + h - DW, x + w - DW, y + h - DW);
                g.drawLine(x + w - d - 1, y + h - DW, x + w - d - 1, y + h - 1);

                g.setColor(cHi);
                g.drawLine(x + w - DW + 1, y + h - d - 1, x + w - 1, y + h - d - 1);
                g.drawLine(x + w - DW + 1, y + h - d - 1, x + w - DW + 1, y + h - DW);
                g.drawLine(x + w - d, y + h - DW + 1, x + w - DW + 1, y + h - DW + 1);
                g.drawLine(x + w - d, y + h - DW + 1, x + w - d, y + h - 1);
            }
            g.setColor(frameColor);
            g.drawRect(x, y, w - 1, h - 1);
            g.drawLine(x + DW - 1, y + DW + th, x + DW - 1, y + h - DW);
            g.drawLine(x + w - DW, y + DW + th, x + w - DW, y + h - DW);
            g.drawLine(x + DW - 1, y + h - DW, x + w - DW, y + h - DW);
        }

    }
} // end of class InternalFrameBorder

