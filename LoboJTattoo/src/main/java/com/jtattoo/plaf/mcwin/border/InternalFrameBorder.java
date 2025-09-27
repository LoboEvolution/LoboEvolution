package com.jtattoo.plaf.mcwin.border;

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
        final boolean active = isActive(c);
        final boolean resizable = isResizable(c);
        final int th = getTitleHeight(c);
        Color frameColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
        Color titleColor = AbstractLookAndFeel.getWindowInactiveTitleColorLight();
        Color borderColor = AbstractLookAndFeel.getWindowInactiveTitleColorDark();
        if (active) {
            frameColor = AbstractLookAndFeel.getWindowBorderColor();
            titleColor = AbstractLookAndFeel.getWindowTitleColorLight();
            borderColor = AbstractLookAndFeel.getWindowTitleColorDark();
        }
        if (!resizable) {
            final Insets borderInsets = getBorderInsets(c);
            g.setColor(frameColor);
            g.drawRect(x, y, w - 1, h - 1);
            if (active) {
                g.setColor(AbstractLookAndFeel.getWindowTitleColorDark());
            } else {
                g.setColor(AbstractLookAndFeel.getWindowInactiveTitleColorDark());
            }
            for (int i = 1; i < borderInsets.left; i++) {
                g.drawRect(i, i, w - 2 * i - 1, h - 2 * i - 1);
            }
            g.setColor(ColorHelper.brighter(frameColor, 20));
            g.drawLine(borderInsets.left - 1, y + th + borderInsets.top, borderInsets.left - 1,
                    y + h - borderInsets.bottom);
            g.drawLine(w - borderInsets.right, y + th + borderInsets.top, w - borderInsets.right,
                    y + h - borderInsets.bottom);
            g.drawLine(borderInsets.left - 1, y + h - borderInsets.bottom, w - borderInsets.right,
                    y + h - borderInsets.bottom);
        } else {
            g.setColor(titleColor);
            g.fillRect(x, y + 1, w, DW - 1);
            g.setColor(borderColor);
            g.fillRect(x + 1, y + h - DW, w - 2, DW - 1);
            if (active) {
                JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 1, DW, DW,th + 1);
                JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), w - DW, DW, DW, th + 1);
            } else {
                JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 1, DW, DW, th + 1);
                JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), w - DW, DW, DW, th + 1);
            }
            g.setColor(borderColor);
            g.fillRect(1, INSETS.top + th + 1, DW - 1, h - th - DW);
            g.fillRect(w - DW, INSETS.top + th + 1, DW - 1, h - th - DW);

            g.setColor(ColorHelper.darker(frameColor, 10));
            g.drawRect(x, y, w - 1, h - 1);
            g.setColor(frameColor);
            g.drawLine(x + DW - 1, y + DW + th, x + DW - 1, y + h - DW);
            g.drawLine(x + w - DW, y + DW + th, x + w - DW, y + h - DW);
            g.drawLine(x + DW - 1, y + h - DW, x + w - DW, y + h - DW);
        }
    }
}// end of class InternalFrameBorder
