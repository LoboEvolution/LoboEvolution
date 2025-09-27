package com.jtattoo.plaf.smart.button;

import com.jtattoo.plaf.AbstractLookAndFeel;

import java.awt.*;
import java.io.Serial;

public class ArrowButton extends com.jtattoo.plaf.base.button.ArrowButton {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void paint(final Graphics g) {
        super.paint(g);
        if (getModel().isRollover()) {
            g.setColor(AbstractLookAndFeel.getFocusColor());
            g.drawLine(1, 0, getWidth() - 1, 0);
            g.drawLine(1, 1, getWidth() - 1, 1);
        }
    }

} // end of class Arrow Button
