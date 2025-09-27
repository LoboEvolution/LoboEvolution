package com.jtattoo.plaf.acryl.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;

public class ButtonBorder implements Border, UIResource {

    private static final Insets insets = new Insets(3, 6, 3, 6);

    @Override
    public Insets getBorderInsets(final Component c) {
        return insets;
    }

    public Insets getBorderInsets(final Component c, final Insets borderInsets) {
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
        final AbstractButton button = (AbstractButton) c;
        final ButtonModel model = button.getModel();
        final Graphics2D g2D = (Graphics2D) g;
        Color frameColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFrameColor(), 50);
        if (!JTattooUtilities.isFrameActive(button)) {
            frameColor = ColorHelper.brighter(frameColor, 30);
        }
        if (model.isRollover() && !model.isPressed() && !model.isArmed()) {
            frameColor = AbstractLookAndFeel.getTheme().getRolloverColorDark();
        }
        if (AbstractLookAndFeel.getTheme().doDrawSquareButtons()) {
            g2D.setColor(Color.white);
            g2D.drawRect(x, y, w - 1, h - 1);
            g2D.setColor(frameColor);
            g2D.drawRect(x, y, w - 2, h - 2);
        } else {
            final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2D.setColor(Color.white);
            g2D.drawRoundRect(x, y, w - 1, h - 1, 6, 6);

            g2D.setColor(frameColor);
            g2D.drawRoundRect(x, y, w - 2, h - 2, 6, 6);

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
        }
    }
} // class ButtonBorder