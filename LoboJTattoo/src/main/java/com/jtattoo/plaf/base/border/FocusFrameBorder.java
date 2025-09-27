package com.jtattoo.plaf.base.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;

import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.io.Serial;

public class FocusFrameBorder extends AbstractBorder implements UIResource {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Insets INSETS = new Insets(2, 2, 2, 2);

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
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
        final Color hiColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFocusFrameColor(), 60);
        final Color loColor = AbstractLookAndFeel.getTheme().getFocusFrameColor();
        g.setColor(loColor);
        g.drawRect(x, y, width - 1, height - 1);
        g.setColor(hiColor);
        g.drawRect(x + 1, y + 1, width - 3, height - 3);
    }

} // end of class FocusFrameBorder
