package com.jtattoo.plaf.luna.border;

import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.io.Serial;

public class TextFieldBorder extends AbstractBorder implements UIResource {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Color BORDER_COLOR = new Color(127, 157, 185);
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
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
        int width = w;
        int height = h;
        width--;
        height--;
        g.setColor(BORDER_COLOR);
        g.drawRect(x, y, width, height);
    }

} // end of class TextFieldBorder
