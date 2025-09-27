package com.jtattoo.plaf.base.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

import java.awt.*;
import java.io.Serial;

public class BasePopupMenuShadowBorder extends BasePopupMenuBorder {

    @Serial
    private static final long serialVersionUID = 1L;

    public BasePopupMenuShadowBorder() {
        shadowSize = 4;
    }

    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
        final Graphics2D g2D = (Graphics2D) g;
        final Composite savedComposite = g2D.getComposite();
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                AbstractLookAndFeel.getTheme().getMenuAlpha());
        g2D.setComposite(alpha);
        final Color logoColor = AbstractLookAndFeel.getTheme().getMenuSelectionBackgroundColor();
        final Color borderColorLo = AbstractLookAndFeel.getFrameColor();
        final Color borderColorHi = ColorHelper.brighter(AbstractLookAndFeel.getMenuSelectionBackgroundColor(), 40);
        g.setColor(logoColor);
        if (JTattooUtilities.isLeftToRight(c)) {
            final int dx = getBorderInsets(c).left;
            g.fillRect(x, y, dx - 1, h - 1 - shadowSize);
            paintLogo(c, g, x, y, w, h);
            // - highlight
            g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getMenuBackgroundColor(), 40));
            g.drawLine(x + dx, y + 1, x + w - shadowSize - 2, y + 1);
            g.setColor(borderColorHi);
            g.drawLine(x + 1, y, x + 1, y + h - shadowSize - 2);
            // - outer frame
            g.setColor(borderColorLo);
            if (isMenuBarPopup(c)) {
                // top
                g.drawLine(x + dx - 1, y, x + w - shadowSize - 1, y);
                // left
                g.drawLine(x, y, x, y + h - shadowSize - 1);
                // bottom
                g.drawLine(x, y + h - shadowSize - 1, x + w - shadowSize - 1, y + h - shadowSize - 1);
                // right
                g.drawLine(x + w - shadowSize - 1, y + 1, x + w - shadowSize - 1, y + h - shadowSize - 1);
            } else {
                g.drawRect(x, y, w - shadowSize - 1, h - shadowSize - 1);
            }
            // - logo separator
            g.drawLine(x + dx - 1, y + 1, x + dx - 1, y + h - shadowSize - 1);
        } else {
            final int dx = getBorderInsets(c).right - shadowSize;
            g.fillRect(x + w - dx - shadowSize, y, dx - 1, h - 1 - shadowSize);
            paintLogo(c, g, x, y, w, h);
            // - highlight
            g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getMenuBackgroundColor(), 40));
            g.drawLine(x + 1, y + 1, x + w - dx - shadowSize - 1, y + 1);
            g.drawLine(x + 1, y + 1, x + 1, y + h - shadowSize - 2);
            // - outer frame
            g.setColor(borderColorLo);
            if (isMenuBarPopup(c)) {
                // top
                g.drawLine(x, y, x + w - dx - shadowSize, y);
                // left
                g.drawLine(x, y, x, y + h - shadowSize - 1);
                // bottom
                g.drawLine(x, y + h - shadowSize - 1, x + w - shadowSize - 1, y + h - shadowSize - 1);
                // right
                g.drawLine(x + w - shadowSize - 1, y, x + w - shadowSize - 1, y + h - shadowSize - 1);
            } else {
                g.drawRect(x, y, w - shadowSize - 1, h - shadowSize - 1);
            }
            // - logo separator
            g.drawLine(x + w - dx - shadowSize, y + 1, x + w - dx - shadowSize, y + h - shadowSize - 1);
        }

        // paint the shadow
        g2D.setColor(AbstractLookAndFeel.getTheme().getShadowColor());
        float alphaValue = 0.4f;
        for (int i = 0; i < shadowSize; i++) {
            alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue);
            g2D.setComposite(alpha);
            g.drawLine(x + w - shadowSize + i, y + shadowSize, x + w - shadowSize + i, y + h - shadowSize - 1 + i);
            g.drawLine(x + shadowSize, y + h - shadowSize + i, x + w - shadowSize + i, y + h - shadowSize + i);
            alphaValue -= alphaValue / 2;
        }

        g2D.setComposite(savedComposite);
    }

} // end of class PopupMenuShadowBorder
