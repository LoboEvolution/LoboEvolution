package com.jtattoo.plaf.hifi.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;

import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;

public class ButtonBorder implements Border, UIResource {

    private static final Insets INSETS = new Insets(4, 8, 4, 8);

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
        g.translate(x, y);

        final Color hiFrameColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getButtonBackgroundColor(), 14);
        final Color frameColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getButtonBackgroundColor(), 6);
        final Color loFrameColor = ColorHelper.darker(AbstractLookAndFeel.getTheme().getButtonBackgroundColor(), 50);

        g.setColor(hiFrameColor);
        g.drawLine(1, 0, w - 3, 0);
        g.drawLine(0, 1, 0, h - 3);
        g.setColor(frameColor);
        g.drawLine(w - 2, 0, w - 2, h - 2);
        g.drawLine(1, h - 2, w - 3, h - 2);

        final Composite composite = g2D.getComposite();
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        g2D.setComposite(alpha);
        g2D.setColor(loFrameColor);
        g.drawLine(1, 1, w - 3, 1);
        g.drawLine(1, 2, 1, h - 3);
        g.setColor(Color.black);
        g.drawLine(w - 1, 1, w - 1, h - 1);
        g.drawLine(1, h - 1, w - 1, h - 1);
        alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
        g2D.setComposite(alpha);
        g.drawLine(1, h - 2, 2, h - 1);
        g2D.setComposite(composite);

        g.translate(-x, -y);
    }

} // end of class ButtonBorder