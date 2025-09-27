package com.jtattoo.plaf.base.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.io.Serial;

public class PaletteBorder extends AbstractBorder implements UIResource {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Insets INSETS = new Insets(1, 1, 1, 1);

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
        if (JTattooUtilities.isFrameActive(c)) {
            g.setColor(AbstractLookAndFeel.getWindowBorderColor());
        } else {
            g.setColor(AbstractLookAndFeel.getWindowInactiveBorderColor());
        }
        g.drawRect(x, y, w - 1, h - 1);
    }

} // class PaletteBorder