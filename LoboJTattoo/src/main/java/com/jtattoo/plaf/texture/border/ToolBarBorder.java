package com.jtattoo.plaf.texture.border;

import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.LazyImageIcon;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.io.Serial;

public class ToolBarBorder extends AbstractBorder implements UIResource, SwingConstants {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final LazyImageIcon HOR_RUBBER_ICON = new LazyImageIcon("texture/icons/HorRubber.gif");
    private static final LazyImageIcon VER_RUBBER_ICON = new LazyImageIcon("texture/icons/VerRubber.gif");

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
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
        if (((JToolBar) c).isFloatable()) {
            if (((JToolBar) c).getOrientation() == HORIZONTAL) {
                final int x1 = 4;
                final int y1 = (h - HOR_RUBBER_ICON.getIconHeight()) / 2;
                HOR_RUBBER_ICON.paintIcon(c, g, x1, y1);
            } else {
                final int x1 = (w - VER_RUBBER_ICON.getIconWidth()) / 2 + 2;
                final int y1 = 4;
                VER_RUBBER_ICON.paintIcon(c, g, x1, y1);
            }
        }
    }
} // end of class ToolBarBorder
