package com.jtattoo.plaf.base.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.io.Serial;

public class MenuItemBorder extends AbstractBorder implements UIResource {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Insets INSETS = new Insets(2, 2, 2, 2);

    @Override
    public Insets getBorderInsets(final Component c) {
        return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
    }

    @Override
    public Insets getBorderInsets(final Component c, final Insets borderInsets) {
        borderInsets.left = INSETS.left;
        borderInsets.top = INSETS.top;
        borderInsets.right = INSETS.right;
        borderInsets.bottom = INSETS.bottom;
        return borderInsets;
    }

    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
        final JMenuItem b = (JMenuItem) c;
        final ButtonModel model = b.getModel();
        final Color borderColorLo = AbstractLookAndFeel.getFrameColor();
        final Color borderColorHi = ColorHelper.brighter(AbstractLookAndFeel.getMenuSelectionBackgroundColor(), 40);
        if (c.getParent() instanceof JMenuBar) {
            if (model.isArmed() || model.isSelected()) {
                g.setColor(borderColorLo);
                g.drawLine(x, y, x + w - 1, y);
                g.drawLine(x, y, x, y + h - 1);
                g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
                g.setColor(borderColorHi);
                g.drawLine(x + 1, y + 1, x + w - 2, y + 1);
                g.drawLine(x + 1, y + 1, x + 1, y + h - 1);
            }
        } else {
            if (model.isArmed() || c instanceof JMenu && model.isSelected()) {
                g.setColor(borderColorLo);
                g.drawLine(x, y, x + w - 1, y);
                g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
                g.setColor(borderColorHi);
                g.drawLine(x, y + 1, x + w - 2, y + 1);
            }
        }
    }

} // end of class MenuItemBorder