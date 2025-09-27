package com.jtattoo.plaf.mint.border;

import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;

public class ButtonBorder implements Border, UIResource {

    private static final Insets INSETS = new Insets(3, 8, 5, 10);

    @Override
    public Insets getBorderInsets(final Component c) {
        return INSETS;
    }

    public Insets getBorderInsets(final Component c, final Insets borderInsets) {
        borderInsets.left = INSETS.left;
        borderInsets.top = INSETS.top;
        borderInsets.right = INSETS.right;
        borderInsets.bottom = INSETS.bottom;
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
