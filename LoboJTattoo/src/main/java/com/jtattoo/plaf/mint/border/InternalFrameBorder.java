package com.jtattoo.plaf.mint.border;

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
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
        int w = width;
        int h = height;
        final boolean active = isActive(c);
        final boolean resizable = isResizable(c);
        if (!resizable) {
            final Color frameColor = AbstractLookAndFeel.getFrameColor();
            Color borderColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
            if (active) {
                borderColor = AbstractLookAndFeel.getWindowBorderColor();
            }
            final Color cHi = ColorHelper.brighter(frameColor, 40);
            final Color cLo = frameColor;
            JTattooUtilities.draw3DBorder(g, cHi, cLo, x, y, w, h);
            g.setColor(borderColor);
            for (int i = 1; i < DW; i++) {
                g.drawRect(i, i, w - 2 * i - 1, h - 2 * i - 1);
            }
            return;
        }
        h--;
        w--;
        Color color = AbstractLookAndFeel.getWindowInactiveBorderColor();
        if (active) {
            color = AbstractLookAndFeel.getWindowBorderColor();
        }

        // left
        g.setColor(color);
        g.drawLine(x, y, x, y + h);
        g.setColor(ColorHelper.brighter(color, 60));
        g.drawLine(x + 1, y + 1, x + 1, y + h - 1);
        g.setColor(ColorHelper.brighter(color, 40));
        g.drawLine(x + 2, y + 2, x + 2, y + h - 2);
        g.setColor(ColorHelper.brighter(color, 20));
        g.drawLine(x + 3, y + 3, x + 3, y + h - 3);
        g.setColor(color);
        g.drawLine(x + 4, y + 4, x + 4, y + h - 4);

        // right
        g.setColor(color);
        g.drawLine(x + w, y, x + w, y + h);
        g.setColor(ColorHelper.brighter(color, 20));
        g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
        g.setColor(ColorHelper.brighter(color, 40));
        g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 2);
        g.setColor(ColorHelper.brighter(color, 60));
        g.drawLine(x + w - 3, y + 3, x + w - 3, y + h - 3);
        g.setColor(color);
        g.drawLine(x + w - 4, y + 4, x + w - 4, y + h - 4);

        // top
        g.setColor(color);
        g.drawLine(x, y, x + w, y);
        g.setColor(ColorHelper.brighter(color, 60));
        g.drawLine(x + 1, y + 1, x + w - 1, y + 1);
        g.setColor(ColorHelper.brighter(color, 40));
        g.drawLine(x + 2, y + 2, x + w - 2, y + 2);
        g.setColor(ColorHelper.brighter(color, 20));
        g.drawLine(x + 3, y + 3, x + w - 3, y + 3);
        g.setColor(color);
        g.drawLine(x + 4, y + 4, x + w - 4, y + 4);

        // bottom
        g.setColor(color);
        g.drawLine(x, y + h, x + w, y + h);
        g.setColor(ColorHelper.brighter(color, 20));
        g.drawLine(x + 1, y + h - 1, x + w - 1, y + h - 1);
        g.setColor(ColorHelper.brighter(color, 40));
        g.drawLine(x + 2, y + h - 2, x + w - 2, y + h - 2);
        g.setColor(ColorHelper.brighter(color, 60));
        g.drawLine(x + 3, y + h - 3, x + w - 3, y + h - 3);
        g.setColor(color);
        g.drawLine(x + 4, y + h - 4, x + w - 4, y + h - 4);
    }

} // end of class InternalFrameBorder
