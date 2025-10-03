package com.jtattoo.plaf.base.button;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.NoFocusButton;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class SpinButton extends NoFocusButton {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Dimension minSize = new Dimension(14, 12);
    private int direction = SwingConstants.NORTH;

    public SpinButton(final int aDirection) {
        super();
        setInheritsPopupMenu(true);
        direction = aDirection;
    }

    @Override
    public Dimension getPreferredSize() {
        final Dimension size = super.getPreferredSize();
        size.width = Math.max(size.width, minSize.width);
        size.height = Math.max(size.height, minSize.height);
        return size;
    }

    @Override
    public void paint(final Graphics g) {
        final Color[] colors;
        if (isEnabled()) {
            if (getModel().isPressed() && getModel().isArmed()) {
                colors = AbstractLookAndFeel.getTheme().getPressedColors();
            } else {
                if (getModel().isRollover()) {
                    colors = AbstractLookAndFeel.getTheme().getRolloverColors();
                } else if (JTattooUtilities.isFrameActive(this)) {
                    colors = AbstractLookAndFeel.getTheme().getButtonColors();
                } else {
                    colors = AbstractLookAndFeel.getTheme().getInactiveColors();
                }
            }
        } else {
            colors = AbstractLookAndFeel.getTheme().getDisabledColors();
        }
        JTattooUtilities.fillHorGradient(g, colors, 0, 0, getWidth(), getHeight());
        paintBorder(g);
        g.setColor(getForeground());
        final int w = 4;
        final int h = 3;
        final int x = (getWidth() - w) / 2;
        final int y = (getHeight() - h) / 2;
        if (direction == SwingConstants.NORTH) {
            for (int i = 0; i < h; i++) {
                g.drawLine(x + h - i - 1, y + i, x + w - (h - i) + 1, y + i);
            }
        } else {
            for (int i = 0; i < h; i++) {
                g.drawLine(x + i, y + i, x + w - i, y + i);
            }
        }
    }

}