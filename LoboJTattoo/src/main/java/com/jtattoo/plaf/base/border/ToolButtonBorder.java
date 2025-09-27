package com.jtattoo.plaf.base.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;

public class ToolButtonBorder implements Border, UIResource {

    private static final Insets INSETS = new Insets(2, 2, 2, 2);

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
        final AbstractButton button = (AbstractButton) c;
        final ButtonModel model = button.getModel();
        final Color frameColor = AbstractLookAndFeel.getToolbarBackgroundColor();
        final Color frameHiColor = ColorHelper.brighter(frameColor, 10);
        final Color frameLoColor = ColorHelper.darker(frameColor, 30);
        JTattooUtilities.draw3DBorder(g, frameHiColor, frameLoColor, x, y, w, h);
        if (model.isPressed() && model.isArmed() || model.isSelected()) {
            JTattooUtilities.draw3DBorder(g, frameLoColor, frameHiColor, x, y, w, h);
        } else {
            JTattooUtilities.draw3DBorder(g, frameLoColor, frameHiColor, x, y, w, h);
            JTattooUtilities.draw3DBorder(g, frameHiColor, frameLoColor, x + 1, y + 1, w - 2, h - 2);
        }
    }

} // end of class ToolButtonBorder
