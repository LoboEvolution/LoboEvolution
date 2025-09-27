package com.jtattoo.plaf.graphite.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.base.border.BaseInternalFrameBorder;

import java.awt.*;
import java.io.Serial;

public class InternalFrameBorder extends BaseInternalFrameBorder {

    @Serial
    private static final long serialVersionUID = 1L;

    public InternalFrameBorder() {
    }

    @Override
    public Insets getBorderInsets(final Component c) {
        return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
    }

    @Override
    public Insets getBorderInsets(final Component c, final Insets borderInsets) {
        borderInsets.left = INSETS.left;
        borderInsets.top = INSETS.top;
        borderInsets.right = INSETS.right;
        borderInsets.bottom = INSETS.bottom;
        return borderInsets;
    }

    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
        final int th = getTitleHeight(c);
        Color titleColor = AbstractLookAndFeel.getWindowTitleColorLight();
        Color borderColor = AbstractLookAndFeel.getWindowTitleColorDark();
        Color frameColor = AbstractLookAndFeel.getWindowBorderColor();
        if (!isActive(c)) {
            titleColor = AbstractLookAndFeel.getWindowInactiveTitleColorLight();
            borderColor = AbstractLookAndFeel.getWindowInactiveTitleColorDark();
            frameColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
        }

        g.setColor(titleColor);
        g.fillRect(x, y + 1, w, INSETS.top - 1);
        g.setColor(titleColor);
        g.fillRect(x + 1, y + h - DW, w - 2, DW - 1);

        if (isActive(c)) {
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 1,
                    INSETS.top, DW, th + 1);
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), w - DW - 1,
                    INSETS.top, DW, th + 1);
            g.setColor(borderColor);
            JTattooUtilities.fillInverseHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 1,
                    INSETS.top + th + 1, DW - 1, h - th - DW);
            JTattooUtilities.fillInverseHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(),
                    w - DW, INSETS.top + th + 1, DW - 1, h - th - DW);
        } else {
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 1,
                    INSETS.top, DW, th + 1);
            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(),
                    w - DW - 1, INSETS.top, DW, th + 1);
            g.setColor(borderColor);
            JTattooUtilities.fillInverseHorGradient(g,
                    AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 1, INSETS.top + th + 1, DW - 1,
                    h - th - DW);
            JTattooUtilities.fillInverseHorGradient(g,
                    AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), w - DW, INSETS.top + th + 1,
                    DW - 1, h - th - DW);
        }

        g.setColor(frameColor);
        g.drawRect(x, y, w - 1, h - 1);
        g.drawLine(x + DW - 1, y + INSETS.top + th, x + DW - 1, y + h - DW);
        g.drawLine(x + w - DW, y + INSETS.top + th, x + w - DW, y + h - DW);
        g.drawLine(x + DW - 1, y + h - DW, x + w - DW, y + h - DW);
    }

} // end of class InternalFrameBorder
