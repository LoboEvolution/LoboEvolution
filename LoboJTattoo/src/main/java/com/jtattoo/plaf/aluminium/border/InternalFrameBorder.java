package com.jtattoo.plaf.aluminium.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
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
        Color titleColor = AbstractLookAndFeel.getWindowInactiveTitleBackgroundColor();
        if (isActive(c)) {
            titleColor = AbstractLookAndFeel.getWindowTitleBackgroundColor();
        }
        final int th = getTitleHeight(c);

        g.setColor(titleColor);
        g.fillRect(1, 1, w, DW);
        g.fillRect(1, h - DW, w, DW - 1);

        if (isActive(c)) {
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 1, DW, DW,
                    th + 1);
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), w - DW, DW,
                    DW, th + 1);
            final Color c1 = AbstractLookAndFeel.getTheme().getWindowTitleColorDark();
            final Color c2 = AbstractLookAndFeel.getTheme().getWindowTitleColorLight();
            g2D.setPaint(new GradientPaint(0, DW + th + 1, c1, 0, h - th - 2 * DW, c2));
            g.fillRect(1, DW + th + 1, DW - 1, h - th - 2 * DW);
            g.fillRect(w - DW, DW + th + 1, DW - 1, h - th - 2 * DW);
            g2D.setPaint(null);
        } else {
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 1,
                    DW, DW, th + 1);
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(),
                    w - DW, DW, DW, th + 1);
            final Color c1 = AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorDark();
            final Color c2 = AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorLight();
            g2D.setPaint(new GradientPaint(0, DW + th + 1, c1, 0, h - th - 2 * DW, c2));
            g.fillRect(1, DW + th + 1, DW - 1, h - th - 2 * DW);
            g.fillRect(w - DW, DW + th + 1, DW - 1, h - th - 2 * DW);
            g2D.setPaint(null);
        }

        Color borderColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
        if (isActive(c)) {
            borderColor = AbstractLookAndFeel.getWindowBorderColor();
        }
        g.setColor(borderColor);
        g.drawRect(0, 0, w - 1, h - 1);
        g.drawLine(x + DW - 1, y + INSETS.top + th, x + DW - 1, y + h - DW);
        g.drawLine(x + w - DW, y + INSETS.top + th, x + w - DW, y + h - DW);
        g.drawLine(x + DW - 1, y + h - DW, x + w - DW, y + h - DW);

        g.setColor(new Color(220, 220, 220));
        g.drawLine(1, 1, w - 3, 1);
        g.drawLine(1, 1, 1, h - 2);
    }

} // end of class InternalFrameBorder
