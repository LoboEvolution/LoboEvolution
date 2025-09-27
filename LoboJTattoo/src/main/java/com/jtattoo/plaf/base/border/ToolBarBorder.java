package com.jtattoo.plaf.base.border;

import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.io.Serial;

public class ToolBarBorder extends AbstractBorder implements UIResource, SwingConstants {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Color SHADOW_COLOR = new Color(160, 160, 160);

    @Override
    public Insets getBorderInsets(final Component c) {
        final Insets insets = new Insets(2, 2, 2, 2);
        if (((JToolBar) c).isFloatable()) {
            if (((JToolBar) c).getOrientation() == HORIZONTAL) {
                if (JTattooUtilities.isLeftToRight(c)) {
                    insets.left = 15;
                } else {
                    insets.right = 15;
                }
            } else {
                insets.top = 15;
            }
        }
        final Insets margin = ((JToolBar) c).getMargin();
        if (margin != null) {
            insets.left += margin.left;
            insets.top += margin.top;
            insets.right += margin.right;
            insets.bottom += margin.bottom;
        }
        return insets;
    }

    @Override
    public Insets getBorderInsets(final Component c, final Insets borderInsets) {
        final Insets insets = getBorderInsets(c);
        borderInsets.left = insets.left;
        borderInsets.top = insets.top;
        borderInsets.right = insets.right;
        borderInsets.bottom = insets.bottom;
        return borderInsets;
    }

    @Override
    public void paintBorder(final Component c, final Graphics g, final int borderX, final int y, final int w, final int h) {
        int x = borderX;
        if (((JToolBar) c).isFloatable()) {
            final Graphics2D g2D = (Graphics2D) g;
            final Composite savedComposite = g2D.getComposite();
            final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            g2D.setComposite(alpha);
            if (((JToolBar) c).getOrientation() == HORIZONTAL) {
                if (!JTattooUtilities.isLeftToRight(c)) {
                    x += w - 15;
                }
                g.setColor(Color.WHITE);
                g.drawLine(x + 3, y + 4, x + 3, h - 5);
                g.drawLine(x + 6, y + 3, x + 6, h - 4);
                g.drawLine(x + 9, y + 4, x + 9, h - 5);
                g.setColor(SHADOW_COLOR);
                g.drawLine(x + 4, y + 4, x + 4, h - 5);
                g.drawLine(x + 7, y + 3, x + 7, h - 4);
                g.drawLine(x + 10, y + 4, x + 10, h - 5);
            } else {
                // vertical
                g.setColor(Color.WHITE);
                g.drawLine(x + 3, y + 3, w - 4, y + 3);
                g.drawLine(x + 3, y + 6, w - 4, y + 6);
                g.drawLine(x + 3, y + 9, w - 4, y + 9);
                g.setColor(SHADOW_COLOR);
                g.drawLine(x + 3, y + 4, w - 4, y + 4);
                g.drawLine(x + 3, y + 7, w - 4, y + 7);
                g.drawLine(x + 3, y + 10, w - 4, y + 10);
            }
            g2D.setComposite(savedComposite);
        }
    }

} // end of class ToolBarBorder