package com.jtattoo.plaf.luna.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;

import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.io.Serial;

public class TableHeaderBorder extends AbstractBorder implements UIResource {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Insets INSETS = new Insets(0, 1, 1, 1);

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
        g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getControlBackgroundColor(), 40));
        g.drawLine(0, 0, 0, h - 1);
        g.setColor(ColorHelper.darker(AbstractLookAndFeel.getControlBackgroundColor(), 20));
        g.drawLine(w - 1, 0, w - 1, h - 1);
        g.setColor(ColorHelper.darker(AbstractLookAndFeel.getControlBackgroundColor(), 10));
        g.drawLine(0, h - 1, w - 1, h - 1);
    }

} // end of class TableHeaderBorder

