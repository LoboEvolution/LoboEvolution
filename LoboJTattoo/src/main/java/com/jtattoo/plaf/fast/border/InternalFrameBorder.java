package com.jtattoo.plaf.fast.border;

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
        final Color frameColor = AbstractLookAndFeel.getFrameColor();
        Color borderColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
        if (active) {
            borderColor = AbstractLookAndFeel.getWindowBorderColor();
        }
        Color cHi = ColorHelper.brighter(frameColor, 40);
        Color cLo = frameColor;
        if (!resizable) {
            JTattooUtilities.draw3DBorder(g, cHi, cLo, x, y, w, h);
            g.setColor(borderColor);
            for (int i = 1; i < DW; i++) {
                g.drawRect(i, i, w - 2 * i - 1, h - 2 * i - 1);
            }
        } else {
            JTattooUtilities.draw3DBorder(g, cHi, cLo, x, y, w, h);
            cHi = ColorHelper.brighter(borderColor, 40);
            cLo = ColorHelper.darker(borderColor, 20);
            JTattooUtilities.draw3DBorder(g, cHi, cLo, x + 1, y + 1, w - 2, h - 2);

            g.setColor(borderColor);
            g.drawRect(x + 2, y + 2, w - 5, h - 5);
            g.drawRect(x + 3, y + 3, w - 7, h - 7);
            JTattooUtilities.draw3DBorder(g, ColorHelper.darker(borderColor, 5), ColorHelper.brighter(borderColor, 30),
                    x + 4, y + 4, w - 8, h - 8);
        }
    }
} // end of class InternalFrameBorder