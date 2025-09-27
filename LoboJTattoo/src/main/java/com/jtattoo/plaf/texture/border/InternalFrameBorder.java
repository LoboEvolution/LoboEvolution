package com.jtattoo.plaf.texture.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.base.border.BaseInternalFrameBorder;
import com.jtattoo.plaf.texture.TextureUtils;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class InternalFrameBorder extends BaseInternalFrameBorder {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Color FRAME_BORDER_COLOR = new Color(128, 128, 128);
    private static final Color[] FRAME_COLORS = new Color[]{new Color(144, 144, 144), new Color(180, 180, 180),
            new Color(216, 216, 216), new Color(236, 236, 236), new Color(164, 164, 164), new Color(196, 196, 196),
            new Color(184, 184, 184), new Color(172, 172, 172)};

    @Override
    public Insets getBorderInsets(final Component c) {
        if (isResizable(c)) {
            return new Insets(5, 8, 6, 8);
        } else {
            return new Insets(PALETTE_INSETS.top, PALETTE_INSETS.left, PALETTE_INSETS.bottom, PALETTE_INSETS.right);
        }
    }

    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
        boolean isJFrameBorder = false;
        if (c instanceof JRootPane) {
            final JRootPane jp = (JRootPane) c;
            if (jp.getParent() instanceof JFrame) {
                isJFrameBorder = true;
            }
        }
        final Graphics2D g2D = (Graphics2D) g;
        final Composite savedComposite = g2D.getComposite();
        if (!isJFrameBorder) {
            TextureUtils.fillComponent(g, c, 1, 1, w - 1, h - 1, TextureUtils.WINDOW_TEXTURE_TYPE);
            g.setColor(AbstractLookAndFeel.getTheme().getWindowBorderColor());
            g.drawRect(0, 0, w - 1, h - 1);
        } else {
            final Insets bi = getBorderInsets(c);
            final Color frameColor = AbstractLookAndFeel.getTheme().getWindowBorderColor();
            // top
            g.setColor(frameColor);
            g.drawLine(x, y, w, y);
            TextureUtils.fillComponent(g, c, 1, 1, w, bi.top - 1, TextureUtils.WINDOW_TEXTURE_TYPE);
            // bottom
            g.setColor(frameColor);
            g.drawLine(x, y + h - 1, w, y + h - 1);
            TextureUtils.fillComponent(g, c, 1, h - bi.bottom, w, bi.bottom - 1, TextureUtils.WINDOW_TEXTURE_TYPE);

            g.setColor(FRAME_BORDER_COLOR);
            g.drawLine(x, y, x, y + h);
            g.drawLine(x + w - 1, y, x + w - 1, y + h);
            // left
            for (int i = 1; i < FRAME_COLORS.length; i++) {
                g2D.setColor(FRAME_COLORS[i]);
                g2D.drawLine(i, 0, i, h);
            }
            // right
            for (int i = 0; i < FRAME_COLORS.length - 1; i++) {
                g2D.setColor(FRAME_COLORS[i]);
                g2D.drawLine(w - 8 + i, 0, w - 8 + i, h);
            }
            g.setColor(ColorHelper.brighter(FRAME_BORDER_COLOR, 20));
            g.drawLine(x, y, x + bi.left - 1, y);
            g.drawLine(x + w - bi.right, y, x + w - 1, y);

        } // JFrame border
        if (isResizable(c)) {
            // top
            float alphaValue = 0.4f;
            float alphaDelta = 0.1f;
            g2D.setColor(Color.white);
            if (!AbstractLookAndFeel.getTheme().isDarkTexture()) {
                alphaValue = 0.8f;
                alphaDelta = 0.2f;
            }
            for (int i = 1; i < 5; i++) {
                g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
                g2D.drawLine(1, i, w - 2, i);
                alphaValue -= alphaDelta;
            }
            // bottom
            alphaValue = 0.3f;
            alphaDelta = 0.05f;
            g2D.setColor(Color.black);
            if (!AbstractLookAndFeel.getTheme().isDarkTexture()) {
                alphaValue = 0.14f;
                alphaDelta = 0.02f;
            }
            for (int i = 1; i < 6; i++) {
                g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
                g2D.drawLine(1, h - i, w - 2, h - i);
                alphaValue -= alphaDelta;
            }
        }
        g2D.setComposite(savedComposite);
    }

} // end of class InternalFrameBorder
