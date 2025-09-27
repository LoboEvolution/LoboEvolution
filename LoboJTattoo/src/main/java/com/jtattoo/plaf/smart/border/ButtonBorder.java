package com.jtattoo.plaf.smart.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;

public class ButtonBorder implements Border, UIResource {

    private static final Color DEFAULT_COLOR_HI = new Color(220, 230, 245);
    private static final Color DEFAULT_COLOR_LO = new Color(212, 224, 243);
    private static final Insets INSETS = new Insets(3, 6, 3, 6);

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
        final Graphics2D g2D = (Graphics2D) g;
        Color frameColor = AbstractLookAndFeel.getTheme().getFrameColor();
        if (!JTattooUtilities.isFrameActive(button)) {
            frameColor = ColorHelper.brighter(frameColor, 40);
        }

        if (AbstractLookAndFeel.getTheme().doDrawSquareButtons()) {
            g2D.setColor(Color.white);
            g2D.drawRect(x, y, w - 1, h - 1);

            if (button.getRootPane() != null && button.equals(button.getRootPane().getDefaultButton())
                    && !button.hasFocus()) {
                g2D.setColor(ColorHelper.darker(frameColor, 20));
                g2D.drawRect(x, y, w - 1, h - 2);
                if (!button.getModel().isRollover()) {
                    g2D.setColor(DEFAULT_COLOR_HI);
                    g2D.drawRect(x + 1, y + 1, w - 3, h - 4);
                    g2D.setColor(DEFAULT_COLOR_LO);
                    g2D.drawRect(x + 2, y + 2, w - 5, h - 6);
                }
            } else {
                g2D.setColor(frameColor);
                g2D.drawRect(x, y, w - 2, h - 2);
            }
        } else {
            final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (button.getRootPane() != null && button.equals(button.getRootPane().getDefaultButton())) {
                if (!button.getModel().isRollover()) {
                    g2D.setColor(DEFAULT_COLOR_HI);
                    g2D.drawRoundRect(x + 1, y + 1, w - 4, h - 2, 6, 6);
                    g2D.setColor(DEFAULT_COLOR_LO);
                    g2D.drawRoundRect(x + 2, y + 2, w - 6, h - 6, 6, 6);
                }
            }

            g2D.setColor(Color.white);
            g2D.drawRoundRect(x, y, w - 1, h - 1, 6, 6);

            g2D.setColor(frameColor);
            g2D.drawRoundRect(x, y, w - 2, h - 2, 6, 6);

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
        }
    }

} // end of class ButtonBorder
