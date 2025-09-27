package com.jtattoo.plaf.smart.border;

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
        final AbstractButton button = (AbstractButton) c;
        final ButtonModel model = button.getModel();
        if (model.isEnabled()) {
            if (model.isPressed() && model.isArmed() || model.isSelected()) {
                final Color frameColor = ColorHelper.darker(AbstractLookAndFeel.getToolbarBackgroundColor(), 30);
                g.setColor(frameColor);
                g.drawRect(x, y, w - 1, h - 1);

                final Graphics2D g2D = (Graphics2D) g;
                final Composite composite = g2D.getComposite();
                final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
                g2D.setComposite(alpha);
                g.setColor(Color.black);
                g.fillRect(x + 1, y + 1, w - 2, h - 2);
                g2D.setComposite(composite);
            } else if (model.isRollover()) {
                final Color frameColor = AbstractLookAndFeel.getToolbarBackgroundColor();
                Color frameHiColor = ColorHelper.darker(frameColor, 5);
                Color frameLoColor = ColorHelper.darker(frameColor, 20);
                JTattooUtilities.draw3DBorder(g, frameHiColor, frameLoColor, x, y, w, h);
                frameHiColor = Color.white;
                frameLoColor = ColorHelper.brighter(frameLoColor, 60);
                JTattooUtilities.draw3DBorder(g, frameHiColor, frameLoColor, x + 1, y + 1, w - 2, h - 2);

                final Graphics2D g2D = (Graphics2D) g;
                final Composite composite = g2D.getComposite();
                final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
                g2D.setComposite(alpha);
                g.setColor(Color.white);
                g.fillRect(x + 2, y + 2, w - 4, h - 4);
                g2D.setComposite(composite);

                g.setColor(AbstractLookAndFeel.getFocusColor());
                g.drawLine(x + 1, y + 1, x + w - 1, y + 1);
                g.drawLine(x + 1, y + 2, x + w - 2, y + 2);
            } else if (model.isSelected()) {
                final Color frameColor = AbstractLookAndFeel.getToolbarBackgroundColor();
                final Color frameHiColor = Color.white;
                final Color frameLoColor = ColorHelper.darker(frameColor, 30);
                JTattooUtilities.draw3DBorder(g, frameLoColor, frameHiColor, x, y, w, h);
            }
        }
    }

} // end of class RolloverToolButtonBorder
