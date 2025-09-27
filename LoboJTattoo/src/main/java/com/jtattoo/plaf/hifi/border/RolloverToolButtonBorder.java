package com.jtattoo.plaf.hifi.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;

public class RolloverToolButtonBorder implements Border, UIResource {

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
        final Graphics2D g2D = (Graphics2D) g;
        final Composite composite = g2D.getComposite();
        final Color c1;
        final Color c2;
        if (JTattooUtilities.isActive((JComponent) c)) {
            c1 = ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 60);
            c2 = AbstractLookAndFeel.getFrameColor();
        } else {
            c1 = AbstractLookAndFeel.getFrameColor();
            c2 = ColorHelper.darker(AbstractLookAndFeel.getFrameColor(), 20);
        }
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
        g2D.setComposite(alpha);
        JTattooUtilities.draw3DBorder(g, c1, c2, 0, 0, w, h);
        alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
        g2D.setComposite(alpha);
        JTattooUtilities.draw3DBorder(g, c2, c1, 1, 1, w - 2, h - 2);
        g2D.setComposite(composite);
    }

} // end of class RolloverToolButtonBorder
