package com.jtattoo.plaf.texture.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;

public class RolloverToolButtonBorder implements Border, UIResource {

    private static final Insets INSETS = new Insets(1, 1, 1, 1);

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
        final AbstractButton button = (AbstractButton) c;
        final ButtonModel model = button.getModel();
        final Color loColor = ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 50);
        if (model.isEnabled()) {
            if (model.isPressed() && model.isArmed() || model.isSelected()) {
                final Graphics2D g2D = (Graphics2D) g;
                final Composite composite = g2D.getComposite();
                g.setColor(loColor);
                g.drawRect(x, y, w - 1, h - 1);
                final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f);
                g2D.setComposite(alpha);
                g.setColor(Color.black);
                g.fillRect(x + 1, y + 1, w - 2, h - 2);
                g2D.setComposite(composite);
            } else if (model.isRollover()) {
                final Graphics2D g2D = (Graphics2D) g;
                final Composite composite = g2D.getComposite();
                g.setColor(loColor);
                g.drawRect(x, y, w - 1, h - 1);
                final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
                g2D.setComposite(alpha);
                g.setColor(Color.white);
                g.fillRect(x + 1, y + 1, w - 2, h - 2);
                g2D.setComposite(composite);
            }
        }
    }

} // end of class RolloverToolButtonBorder
