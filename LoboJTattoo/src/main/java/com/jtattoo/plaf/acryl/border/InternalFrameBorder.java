package com.jtattoo.plaf.acryl.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.base.border.BaseInternalFrameBorder;

import java.awt.*;
import java.io.Serial;

public class InternalFrameBorder extends BaseInternalFrameBorder {

    @Serial
    private static final long serialVersionUID = 1L;

    public InternalFrameBorder() {
        INSETS.top = 3;
    }

    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
        final boolean active = isActive(c);
        final int th = getTitleHeight(c);
        Color titleColor = ColorHelper.brighter(AbstractLookAndFeel.getWindowInactiveTitleColorLight(), 10);
        Color borderColor = AbstractLookAndFeel.getWindowInactiveTitleColorLight();
        Color frameColor = ColorHelper.darker(AbstractLookAndFeel.getWindowInactiveBorderColor(), 10);
        if (active) {
            titleColor = AbstractLookAndFeel.getWindowTitleColorLight();
            borderColor = AbstractLookAndFeel.getWindowTitleColorLight();
            frameColor = ColorHelper.darker(AbstractLookAndFeel.getWindowBorderColor(), 10);
        }
        g.setColor(titleColor);
        g.fillRect(x, y + 1, w, INSETS.top - 1);
        g.setColor(borderColor);
        g.fillRect(x + 1, y + h - DW, w - 2, DW - 1);

        if (active) {
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 1,
                    INSETS.top, DW, th + 1);
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), w - DW,
                    INSETS.top, DW, th + 1);
        } else {
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 1,
                    INSETS.top, DW - 1, th + 1);
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(),
                    w - DW, INSETS.top, DW - 1, th + 1);
        }
        g.setColor(borderColor);
        g.fillRect(1, INSETS.top + th + 1, DW - 1, h - th - DW);
        g.fillRect(w - DW, INSETS.top + th + 1, DW - 1, h - th - DW);

        g.setColor(frameColor);
        g.drawRect(x, y, w - 1, h - 1);
        g.drawLine(x + DW - 1, y + INSETS.top + th, x + DW - 1, y + h - DW);
        g.drawLine(x + w - DW, y + INSETS.top + th, x + w - DW, y + h - DW);
        g.drawLine(x + DW - 1, y + h - DW, x + w - DW, y + h - DW);
    }

} // class InternalFrameBorder
