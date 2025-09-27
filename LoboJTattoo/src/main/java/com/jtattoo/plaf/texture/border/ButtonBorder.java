package com.jtattoo.plaf.texture.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;


public class ButtonBorder implements Border, UIResource {

    private static final Color DEFAULT_COLOR_HI = new Color(220, 230, 245);
    private static final Color DEFAULT_COLOR_LO = new Color(212, 224, 243);
    private static final Insets INSETS = new Insets(3, 4, 3, 4);

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
        final Graphics2D g2D = (Graphics2D) g;
        final AbstractButton button = (AbstractButton) c;
        Color frameColor = AbstractLookAndFeel.getTheme().getFrameColor();
        if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
            frameColor = AbstractLookAndFeel.getTheme().getFocusFrameColor();
        }
        if (!button.isEnabled()) {
            frameColor = ColorHelper.brighter(frameColor, 20);
        } else if (button.getModel().isRollover()) {
            frameColor = ColorHelper.darker(frameColor, 20);
        }
        if (AbstractLookAndFeel.getTheme().doDrawSquareButtons()) {
            final Composite savedComposite = g2D.getComposite();
            final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            g2D.setComposite(alpha);
            g2D.setColor(Color.white);
            g2D.drawLine(x, h - 1, x + w, h - 1);
            g2D.setComposite(savedComposite);
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
                g2D.drawRect(x, y, w - 1, h - 2);
            }
        } else {

            final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            final Composite saveComposite = g2D.getComposite();
            final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
            g2D.setComposite(alpha);
            g2D.setColor(Color.white);
            g2D.drawRoundRect(x, y, w - 1, h - 1, 6, 6);
            g2D.setComposite(saveComposite);

            if (button.getRootPane() != null && button.equals(button.getRootPane().getDefaultButton())
                    && !button.hasFocus()) {
                g2D.setColor(ColorHelper.darker(frameColor, 20));
                g2D.drawRoundRect(x, y, w - 1, h - 2, 6, 6);
                if (!button.getModel().isRollover()) {
                    g2D.setColor(DEFAULT_COLOR_HI);
                    g2D.drawRoundRect(x + 1, y + 1, w - 3, h - 4, 6, 6);
                    g2D.setColor(DEFAULT_COLOR_LO);
                    g2D.drawRoundRect(x + 2, y + 2, w - 5, h - 6, 6, 6);
                }
            } else {
                g2D.setColor(frameColor);
                g2D.drawRoundRect(x, y, w - 1, h - 2, 6, 6);
                g2D.setColor(ColorHelper.brighter(frameColor, 20));
                g2D.drawLine(x + 2, y, w - 3, y);
                g2D.setColor(ColorHelper.darker(frameColor, 10));
                g2D.drawLine(x + 2, h - 2, w - 3, h - 2);
            }

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
        }
    }

} // end of class ButtonBorder