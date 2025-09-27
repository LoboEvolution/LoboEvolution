package com.jtattoo.plaf.base.button;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.NoFocusButton;
import com.jtattoo.plaf.base.BaseIcons;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class ArrowButton extends NoFocusButton {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void paint(final Graphics g) {
        final Dimension size = getSize();
        final Color[] colors;
        if (isEnabled()) {
            if (getModel().isArmed() && getModel().isPressed()) {
                colors = AbstractLookAndFeel.getTheme().getPressedColors();
            } else if (getModel().isRollover()) {
                colors = AbstractLookAndFeel.getTheme().getRolloverColors();
            } else {
                colors = AbstractLookAndFeel.getTheme().getButtonColors();
            }
        } else {
            colors = AbstractLookAndFeel.getTheme().getDisabledColors();
        }
        JTattooUtilities.fillHorGradient(g, colors, 0, 0, size.width, size.height);

        final boolean inverse = ColorHelper.getGrayValue(colors) < 128;

        final Icon icon = inverse ? BaseIcons.getComboBoxInverseIcon() : BaseIcons.getComboBoxIcon();
        final int x = (size.width - icon.getIconWidth()) / 2;
        final int y = (size.height - icon.getIconHeight()) / 2;

        final Graphics2D g2D = (Graphics2D) g;
        final Composite savedComposite = g2D.getComposite();
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        if (getModel().isPressed() && getModel().isArmed()) {
            icon.paintIcon(this, g, x + 2, y + 1);
        } else {
            icon.paintIcon(this, g, x + 1, y);
        }
        g2D.setComposite(savedComposite);
        paintBorder(g2D);

    }
}
