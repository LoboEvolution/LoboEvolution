package com.jtattoo.plaf.fast.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;

public class ButtonBorder implements Border, UIResource {

    private static final Color DEFAULT_FRAME_COLOR = new Color(0, 64, 255);
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
        final AbstractButton button = (AbstractButton) c;
        final ButtonModel model = button.getModel();
        final Color frameColor = ColorHelper.darker(button.getBackground(), 30);
        if (model.isEnabled()) {
            if (model.isPressed() && model.isArmed() || model.isSelected()) {
                g.setColor(frameColor);
                g.drawRect(x, y, w - 1, h - 1);
            } else {
                g.setColor(frameColor);
                g.drawRect(x, y, w - 1, h - 1);
                g.setColor(ColorHelper.brighter(button.getBackground(), 40));
                g.drawLine(x + 1, y + 1, x + w - 2, y + 1);
                g.drawLine(x + 1, y + 1, x + 1, y + h - 2);
            }
            if (c instanceof JButton) {
                final JButton b = (JButton) c;
                if (b.getRootPane() != null && b.equals(b.getRootPane().getDefaultButton())) {
                    g.setColor(DEFAULT_FRAME_COLOR);
                    g.drawRect(x, y, w - 1, h - 1);
                }
            }
        } else {
            g.setColor(AbstractLookAndFeel.getDisabledForegroundColor());
            g.drawRect(x, y, w - 1, h - 1);
        }
    }

} // end of class ButtonBorder