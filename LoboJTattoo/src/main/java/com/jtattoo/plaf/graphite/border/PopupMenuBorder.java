package com.jtattoo.plaf.graphite.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.base.border.BasePopupMenuBorder;

import java.awt.*;
import java.io.Serial;

public class PopupMenuBorder extends BasePopupMenuBorder {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
        final Color borderColor = ColorHelper.darker(AbstractLookAndFeel.getMenuSelectionBackgroundColor(), 20);
        if (JTattooUtilities.isLeftToRight(c)) {
            final int dx = getBorderInsets(c).left;
            final Color logoColorHi = AbstractLookAndFeel.getTheme().getMenuSelectionBackgroundColorDark();
            final Color logoColorLo = AbstractLookAndFeel.getTheme().getMenuSelectionBackgroundColor();
            final Color[] colors = ColorHelper.createColorArr(logoColorHi, logoColorLo, 32);
            JTattooUtilities.fillHorGradient(g, colors, x, y, dx - 1, h - 1);
            paintLogo(c, g, x, y, w, h);
            // - highlight
            g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getMenuBackgroundColor(), 40));
            g.drawLine(x + dx, y + 1, x + w - 2, y + 1);
            // - outer frame
            g.setColor(borderColor);
            if (isMenuBarPopup(c)) {
                // top
                g.drawLine(x + dx - 1, y, x + w, y);
                // left
                g.drawLine(x, y, x, y + h - 1);
                // bottom
                g.drawLine(x, y + h - 1, x + w, y + h - 1);
                // right
                g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
            } else {
                g.drawRect(x, y, w - 1, h - 1);
            }
            // - logo separator
            g.drawLine(x + dx - 1, y + 1, x + dx - 1, y + h - 1);
        } else {
            final int dx = getBorderInsets(c).right;
            final Color logoColorHi = AbstractLookAndFeel.getTheme().getMenuSelectionBackgroundColorDark();
            final Color logoColorLo = AbstractLookAndFeel.getTheme().getMenuSelectionBackgroundColor();
            final Color[] colors = ColorHelper.createColorArr(logoColorHi, logoColorLo, 32);
            JTattooUtilities.fillHorGradient(g, colors, x + w - dx, y, dx, h - 1);
            paintLogo(c, g, x, y, w, h);
            // - highlight
            g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getMenuBackgroundColor(), 40));
            g.drawLine(x + 1, y + 1, x + w - dx - 1, y + 1);
            g.drawLine(x + 1, y + 1, x + 1, y + h - 2);
            // - outer frame
            g.setColor(borderColor);
            if (isMenuBarPopup(c)) {
                // top
                g.drawLine(x, y, x + w - dx, y);
                // left
                g.drawLine(x, y, x, y + h - 1);
                // bottom
                g.drawLine(x, y + h - 1, x + w, y + h - 1);
                // right
                g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
            } else {
                g.drawRect(x, y, w - 1, h - 1);
            }
            // - logo separator
            g.drawLine(x + w - dx, y + 1, x + w - dx, y + h - 1);
        }
    }

} // end of class PopupMenuBorder
