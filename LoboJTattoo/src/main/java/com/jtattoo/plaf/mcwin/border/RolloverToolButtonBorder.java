package com.jtattoo.plaf.mcwin.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;

public class RolloverToolButtonBorder implements Border, UIResource {

    private static final Insets INSETS = new Insets(1, 1, 1, 1);

    @Override
    public Insets getBorderInsets(final Component c) {
        return INSETS;
    }

    public Insets getBorderInsets(final Component c, final Insets borderInsets) {
        borderInsets.top = 1;
        borderInsets.left = 1;
        borderInsets.bottom = 1;
        borderInsets.right = 1;
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
        Color frameColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFrameColor(), 40);
        if (model.isPressed() && model.isArmed() || model.isSelected()) {
            frameColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFrameColor(), 20);
        } else if (model.isRollover()) {
            frameColor = AbstractLookAndFeel.getTheme().getRolloverColor();
        }
        g.setColor(frameColor);
        g.drawRect(x, y, w - 2, h - 1);
        g.setColor(AbstractLookAndFeel.getTheme().getToolbarBackgroundColor());
        g.drawLine(w - 1, 0, w - 1, h - 1);
    }

} // end of class RolloverToolButtonBorder