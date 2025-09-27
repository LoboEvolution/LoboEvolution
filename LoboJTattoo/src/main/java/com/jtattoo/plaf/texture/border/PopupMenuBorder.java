package com.jtattoo.plaf.texture.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.base.border.BasePopupMenuBorder;
import com.jtattoo.plaf.texture.TextureUtils;

import java.awt.*;
import java.io.Serial;

public class PopupMenuBorder extends BasePopupMenuBorder {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final float[] SHOW_ALPHA = {0.6f, 0.4f, 0.2f, 0.1f};

    public PopupMenuBorder() {
        shadowSize = 4;
        leftLogoInsets = new Insets(1, 18, 1, 1);
        rightLogoInsets = new Insets(1, 1, 1, 18);
        insets = new Insets(1, 1, 1, 1);
    }

    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
        final Graphics2D g2D = (Graphics2D) g;
        final Color frameColor = AbstractLookAndFeel.getFrameColor();
        g.setColor(frameColor);
        if (JTattooUtilities.isLeftToRight(c)) {
            final int dx = getBorderInsets(c).left;
            // Top

            if (hasLogo(c)) {
                TextureUtils.fillComponent(g, c, x, y, dx, h - 1 - shadowSize, TextureUtils.ROLLOVER_TEXTURE_TYPE);
                paintLogo(c, g, x, y, w, h);
            }
            g.setColor(frameColor);
            if (isMenuBarPopup(c)) {
                g.drawLine(x + dx, y, x + w - shadowSize - 1, y);
            } else {
                g.drawLine(x, y, x + w - shadowSize - 1, y);
            }
            // Left
            g.drawLine(x, y, x, y + h - shadowSize - 1);
            // Bottom
            g.drawLine(x, y + h - shadowSize - 1, x + w - shadowSize - 1, y + h - shadowSize - 1);
            // Right
            g.drawLine(x + w - shadowSize - 1, y, x + w - shadowSize - 1, y + h - shadowSize - 1);
        } else {
            final int dx = getBorderInsets(c).right - shadowSize;
            // Top
            if (hasLogo(c)) {
                TextureUtils.fillComponent(g, c, x + w - dx - shadowSize, y, dx - 1, h - 1 - shadowSize,
                        TextureUtils.ROLLOVER_TEXTURE_TYPE);
                paintLogo(c, g, x, y, w, h);
            }
            g.setColor(frameColor);
            if (isMenuBarPopup(c)) {
                g.drawLine(x, y, x + w - dx - shadowSize - 1, y);
            } else {
                g.drawLine(x, y, x + w - shadowSize - 1, y);
            }

            // Left
            g.drawLine(x, y, x, y + h - shadowSize - 1);
            // Bottom
            g.drawLine(x, y + h - shadowSize - 1, x + w - shadowSize - 1, y + h - shadowSize - 1);
            // Right
            g.drawLine(x + w - shadowSize - 1, y, x + w - shadowSize - 1, y + h - shadowSize - 1);
        }

        // paint the shadow
        final Composite savedComposite = g2D.getComposite();
        g2D.setColor(Color.black);
        for (int i = 0; i < shadowSize; i++) {
            final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, SHOW_ALPHA[i]);
            g2D.setComposite(alpha);
            // bottom
            g.drawLine(x + shadowSize, y + h - shadowSize + i, x + w - shadowSize + i, y + h - shadowSize + i);
            // right
            g.drawLine(x + w - shadowSize + i, y + shadowSize, x + w - shadowSize + i, y + h - shadowSize - 1 + i);
        }
        g2D.setComposite(savedComposite);
    }

} // end of class PopupMenuTextureBorder
