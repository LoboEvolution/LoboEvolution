package com.jtattoo.plaf.bernstein.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;

public class RolloverToolButtonBorder implements Border, UIResource {

    private static final Color FRAME_HI_COLOR = ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 60);
    private static final Color FRAME_LO_COLOR = AbstractLookAndFeel.getFrameColor();
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
        if (model.isEnabled()) {
            if (model.isRollover()) {
                JTattooUtilities.draw3DBorder(g, FRAME_HI_COLOR, FRAME_LO_COLOR, x, y, w - 1, h);
                JTattooUtilities.draw3DBorder(g, Color.white, FRAME_HI_COLOR, x + 1, y + 1, w - 2, h - 2);
            } else if (model.isPressed() && model.isArmed() || model.isSelected()) {
                g.setColor(FRAME_HI_COLOR);
                g.drawRect(x, y, w - 2, h - 1);
            } else {
                g.setColor(AbstractLookAndFeel.getFrameColor());
                g.drawRect(x, y, w - 2, h - 1);
            }
        } else {
            g.setColor(FRAME_HI_COLOR);
            g.drawRect(x, y, w - 2, h - 1);
        }
        g.setColor(Color.white);
        g.drawLine(w - 1, 0, w - 1, h - 1);
    }

} // end of class RolloverToolButtonBorder
