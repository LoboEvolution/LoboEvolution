package com.jtattoo.plaf.acryl.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;

import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.io.Serial;

public class ScrollPaneBorder extends AbstractBorder implements UIResource {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Insets insets = new Insets(2, 2, 2, 2);
    private static final Insets tableInsets = new Insets(1, 1, 1, 1);

    private boolean tableBorder = false;

    public ScrollPaneBorder(final boolean tableBorder) {
        this.tableBorder = tableBorder;
    }

    @Override
    public Insets getBorderInsets(final Component c) {
        if (tableBorder) {
            return new Insets(tableInsets.top, tableInsets.left, tableInsets.bottom, tableInsets.right);
        } else {
            return new Insets(insets.top, insets.left, insets.bottom, insets.right);
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
        g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFrameColor(), 50));
        g.drawRect(x, y, w - 1, h - 1);
        g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getTheme().getBackgroundColor(), 50));
        g.drawRect(x + 1, y + 1, w - 3, h - 3);
    }

} // class ScrollPaneBorder

