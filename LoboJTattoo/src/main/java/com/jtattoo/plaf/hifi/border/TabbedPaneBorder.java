package com.jtattoo.plaf.hifi.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;

public class TabbedPaneBorder implements Border, UIResource {

    private static final Insets INSETS = new Insets(1, 1, 1, 1);

    @Override
    public Insets getBorderInsets(final Component c) {
        return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
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
        final Color frameColor = AbstractLookAndFeel.getTheme().getFrameColor();
        JTattooUtilities.draw3DBorder(g, frameColor, ColorHelper.brighter(frameColor, 10), x, y, w, h);
    }

} // end of class TabbedPaneBorder
