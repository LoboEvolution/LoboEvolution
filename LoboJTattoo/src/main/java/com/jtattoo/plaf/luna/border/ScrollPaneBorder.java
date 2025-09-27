package com.jtattoo.plaf.luna.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;

import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.io.Serial;

public class ScrollPaneBorder extends AbstractBorder implements UIResource {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Color BORDER_COLOR = new Color(127, 157, 185);
    private static final Insets INSETS = new Insets(2, 2, 2, 2);
    private static final Insets TABLE_INSETS = new Insets(1, 1, 1, 1);
    private boolean tableBorder = false;

    public ScrollPaneBorder(final boolean tableBorder) {
        this.tableBorder = tableBorder;
    }

    @Override
    public Insets getBorderInsets(final Component c) {
        if (tableBorder) {
            return new Insets(TABLE_INSETS.top, TABLE_INSETS.left, TABLE_INSETS.bottom, TABLE_INSETS.right);
        } else {
            return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
        }
    }

    @Override
    public Insets getBorderInsets(final Component c, final Insets borderInsets) {
        final Insets ins = getBorderInsets(c);
        borderInsets.left = ins.left;
        borderInsets.top = ins.top;
        borderInsets.right = ins.right;
        borderInsets.bottom = ins.bottom;
        return borderInsets;
    }

    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
        g.setColor(BORDER_COLOR);
        g.drawRect(x, y, w - 1, h - 1);
        g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getTheme().getBackgroundColor(), 50));
        g.drawRect(x + 1, y + 1, w - 3, h - 3);
    }

} // end of class ScrollPaneBorder
