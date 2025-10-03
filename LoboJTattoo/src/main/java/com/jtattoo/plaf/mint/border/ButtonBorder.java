package com.jtattoo.plaf.mint.border;

import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;

public class ButtonBorder implements Border, UIResource {

    @Override
    public Insets getBorderInsets(final Component c) {
        return new Insets(3, 8, 5, 10);
    }

    public Insets getBorderInsets(final Component c, final Insets borderInsets) {
        Insets insets = new Insets(3, 8, 5, 10);
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
