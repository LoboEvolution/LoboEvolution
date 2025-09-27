package com.jtattoo.plaf.mcwin.border;

import com.jtattoo.plaf.AbstractLookAndFeel;

import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;

public class ButtonBorder implements Border, UIResource {
    private static final Insets INSETS = new Insets(2, 12, 2, 12);
    private static final Insets SQUARE_INSETS = new Insets(3, 4, 3, 4);

    @Override
    public Insets getBorderInsets(final Component c) {
        if (AbstractLookAndFeel.getTheme().doDrawSquareButtons()) {
            return SQUARE_INSETS;
        } else {
            return INSETS;
        }
    }

    public Insets getBorderInsets(final Component c, final Insets borderInsets) {
        final Insets insets = getBorderInsets(c);
        borderInsets.left = insets.left;
        borderInsets.top = insets.top;
        borderInsets.right = insets.right;
        borderInsets.bottom = insets.bottom;
        return borderInsets;
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
    }

} // end of class ButtonBorder
