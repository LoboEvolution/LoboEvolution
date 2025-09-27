package com.jtattoo.plaf.bernstein.border;

import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;

public class ButtonBorder implements Border, UIResource {

    private static final Insets INSETS = new Insets(4, 8, 4, 8);

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
        final Color cHi = MetalLookAndFeel.getControlDarkShadow();
        final Color cLo = ColorHelper.darker(cHi, 8);
        JTattooUtilities.draw3DBorder(g, cHi, cLo, x, y, w, h);
    }

} // end of class ButtonBorder
