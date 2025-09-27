package com.jtattoo.plaf.bernstein.border;

import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.base.border.BaseInternalFrameBorder;

import java.awt.*;
import java.io.Serial;

public class InternalFrameBorder extends BaseInternalFrameBorder {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Color BORDER_COLOR = new Color(255, 244, 128);
    private static final Color[] FRAME_COLORS = { new Color(229, 187, 0), new Color(251, 232, 0),
            new Color(247, 225, 0), new Color(243, 216, 0), new Color(229, 187, 0), };

    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int wBorder, final int hBorder) {
        int w = wBorder;
        int h = hBorder;

        if (!isResizable(c)) {
            Color cHi = ColorHelper.brighter(BORDER_COLOR, 40);
            Color cLo = ColorHelper.darker(BORDER_COLOR, 20);
            JTattooUtilities.draw3DBorder(g, cHi, cLo, x, y, w, h);
            cHi = ColorHelper.darker(cHi, 20);
            cLo = ColorHelper.brighter(cLo, 20);
            JTattooUtilities.draw3DBorder(g, cHi, cLo, x + 1, y + 1, w - 2, h - 2);
            g.setColor(BORDER_COLOR);
            for (int i = 2; i < DW; i++) {
                g.drawRect(i, i, w - 2 * i - 1, h - 2 * i - 1);
            }
            return;
        }
        final int dt = w / 3;
        final int db = w * 2 / 3;
        h--;
        w--;

        final Color cr = BORDER_COLOR;
        g.setColor(FRAME_COLORS[0]);
        g.drawLine(x, y, x, y + h);
        g.setColor(FRAME_COLORS[1]);
        g.drawLine(x + 1, y + 1, x + 1, y + h - 1);
        g.setColor(FRAME_COLORS[2]);
        g.drawLine(x + 2, y + 2, x + 2, y + h - 2);
        g.setColor(FRAME_COLORS[3]);
        g.drawLine(x + 3, y + 3, x + 3, y + h - 3);
        g.setColor(FRAME_COLORS[4]);
        g.drawLine(x + 4, y + 4, x + 4, y + h - 4);

        // rechts
        g.setColor(cr);
        g.drawLine(x + w, y, x + w, y + h);
        g.setColor(ColorHelper.brighter(cr, 30));
        g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
        g.setColor(ColorHelper.brighter(cr, 60));
        g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 2);
        g.setColor(ColorHelper.brighter(cr, 90));
        g.drawLine(x + w - 3, y + 3, x + w - 3, y + h - 3);
        g.setColor(cr);
        g.drawLine(x + w - 4, y + 4, x + w - 4, y + h - 4);

        g.setColor(FRAME_COLORS[0]);
        g.drawLine(x + w, y, x + w, y + TRACK_WIDTH);
        g.setColor(FRAME_COLORS[1]);
        g.drawLine(x + w - 1, y + 1, x + w - 1, y + TRACK_WIDTH);
        g.setColor(FRAME_COLORS[2]);
        g.drawLine(x + w - 2, y + 2, x + w - 2, y + TRACK_WIDTH);
        g.setColor(FRAME_COLORS[3]);
        g.drawLine(x + w - 3, y + 3, x + w - 3, y + TRACK_WIDTH);
        g.setColor(FRAME_COLORS[4]);
        g.drawLine(x + w - 4, y + 4, x + w - 4, y + TRACK_WIDTH);

        g.setColor(FRAME_COLORS[0]);
        g.drawLine(x + w, y + h - TRACK_WIDTH, x + w, y + h);
        g.setColor(FRAME_COLORS[1]);
        g.drawLine(x + w - 1, y + h - TRACK_WIDTH, x + w - 1, y + h - 1);
        g.setColor(FRAME_COLORS[2]);
        g.drawLine(x + w - 2, y + h - TRACK_WIDTH, x + w - 2, y + h - 2);
        g.setColor(FRAME_COLORS[3]);
        g.drawLine(x + w - 3, y + h - TRACK_WIDTH, x + w - 3, y + h - 3);
        g.setColor(FRAME_COLORS[4]);
        g.drawLine(x + w - 4, y + h - TRACK_WIDTH, x + w - 4, y + h - 4);
        // oben
        g.setColor(FRAME_COLORS[0]);
        g.drawLine(x, y, x + dt, y);
        g.setColor(FRAME_COLORS[1]);
        g.drawLine(x + 1, y + 1, x + dt, y + 1);
        g.setColor(FRAME_COLORS[2]);
        g.drawLine(x + 2, y + 2, x + dt, y + 2);
        g.setColor(FRAME_COLORS[3]);
        g.drawLine(x + 3, y + 3, x + dt, y + 3);
        g.setColor(FRAME_COLORS[4]);
        g.drawLine(x + 4, y + 4, x + dt, y + 4);

        g.setColor(cr);
        g.drawLine(x + dt, y, x + w, y);
        g.setColor(ColorHelper.brighter(cr, 90));
        g.drawLine(x + dt, y + 1, x + w - 1, y + 1);
        g.setColor(ColorHelper.brighter(cr, 60));
        g.drawLine(x + dt, y + 2, x + w - 2, y + 2);
        g.setColor(ColorHelper.brighter(cr, 30));
        g.drawLine(x + dt, y + 3, x + w - 3, y + 3);
        g.setColor(cr);
        g.drawLine(x + dt, y + 4, x + w - 4, y + 4);

        g.setColor(FRAME_COLORS[0]);
        g.drawLine(x + w - TRACK_WIDTH, y, x + w, y);
        g.setColor(FRAME_COLORS[1]);
        g.drawLine(x + w - TRACK_WIDTH, y + 1, x + w - 1, y + 1);
        g.setColor(FRAME_COLORS[2]);
        g.drawLine(x + w - TRACK_WIDTH, y + 2, x + w - 2, y + 2);
        g.setColor(FRAME_COLORS[3]);
        g.drawLine(x + w - TRACK_WIDTH, y + 3, x + w - 3, y + 3);
        g.setColor(FRAME_COLORS[4]);
        g.drawLine(x + w - TRACK_WIDTH, y + 4, x + w - 4, y + 4);

        // unten
        g.setColor(FRAME_COLORS[0]);
        g.drawLine(x, y + h, x + db, y + h);
        g.setColor(FRAME_COLORS[1]);
        g.drawLine(x + 1, y + h - 1, x + db, y + h - 1);
        g.setColor(FRAME_COLORS[2]);
        g.drawLine(x + 2, y + h - 2, x + db, y + h - 2);
        g.setColor(FRAME_COLORS[3]);
        g.drawLine(x + 3, y + h - 3, x + db, y + h - 3);
        g.setColor(FRAME_COLORS[4]);
        g.drawLine(x + 4, y + h - 4, x + db, y + h - 4);

        g.setColor(cr);
        g.drawLine(x + db, y + h, x + w, y + h);
        g.setColor(ColorHelper.brighter(cr, 30));
        g.drawLine(x + db, y + h - 1, x + w - 1, y + h - 1);
        g.setColor(ColorHelper.brighter(cr, 60));
        g.drawLine(x + db, y + h - 2, x + w - 2, y + h - 2);
        g.setColor(ColorHelper.brighter(cr, 90));
        g.drawLine(x + db, y + h - 3, x + w - 3, y + h - 3);
        g.setColor(cr);
        g.drawLine(x + db, y + h - 4, x + w - 4, y + h - 4);

        g.setColor(FRAME_COLORS[0]);
        g.drawLine(x + w - TRACK_WIDTH, y + h, x + w, y + h);
        g.setColor(FRAME_COLORS[1]);
        g.drawLine(x + w - TRACK_WIDTH, y + h - 1, x + w - 1, y + h - 1);
        g.setColor(FRAME_COLORS[2]);
        g.drawLine(x + w - TRACK_WIDTH, y + h - 2, x + w - 2, y + h - 2);
        g.setColor(FRAME_COLORS[3]);
        g.drawLine(x + w - TRACK_WIDTH, y + h - 3, x + w - 3, y + h - 3);
        g.setColor(FRAME_COLORS[4]);
        g.drawLine(x + w - TRACK_WIDTH, y + h - 4, x + w - 4, y + h - 4);
    }
} // end of class InternalFrameBorder
