/*
* Copyright (c) 2002 and later by MH Software-Entwicklung. All Rights Reserved.
*  
* JTattoo is multiple licensed. If your are an open source developer you can use
* it under the terms and conditions of the GNU General Public License version 2.0
* or later as published by the Free Software Foundation.
*  
* see: gpl-2.0.txt
* 
* If you pay for a license you will become a registered user who could use the
* software under the terms and conditions of the GNU Lesser General Public License
* version 2.0 or later with classpath exception as published by the Free Software
* Foundation.
* 
* see: lgpl-2.0.txt
* see: classpath-exception.txt
* 
* Registered users could also use JTattoo under the terms and conditions of the 
* Apache License, Version 2.0 as published by the Apache Software Foundation.
*  
* see: APACHE-LICENSE-2.0.txt
 */
package com.jtattoo.plaf.hifi;

import com.jtattoo.plaf.BaseComboBoxUI;
import com.jtattoo.plaf.NoFocusButton;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class HiFiComboBoxUI extends BaseComboBoxUI {

    public static ComponentUI createUI(JComponent c) {
        return new HiFiComboBoxUI();
    }

    @Override
    public JButton createArrowButton() {
        JButton button = new NoFocusButton(HiFiIcons.getComboBoxIcon());
        button.setBorder(new ArrowButtonBorder());
        return button;
    }

    protected void setButtonBorder() {
    }

//--------------------------------------------------------------------------------------------------    
    static class ArrowButtonBorder extends AbstractBorder {

        private static final Insets INSETS = new Insets(1, 3, 1, 2);
        private static final Color FRAME_LO_COLOR = new Color(120, 120, 120);
        private static final Color FRAME_LOWER_COLOR = new Color(104, 104, 104);
        private static final Color FRAME_LOWER_LO_COLOR = new Color(64, 64, 64);
        private static final Color FRAME_LOWEST_COLOR = new Color(32, 32, 32);

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2D = (Graphics2D) g;
            g.translate(x, y);

            g.setColor(FRAME_LO_COLOR);
            g.drawLine(1, 0, w - 1, 0);
            g.drawLine(1, 1, 1, h - 2);
            g.setColor(FRAME_LOWER_COLOR);
            g.drawLine(w - 1, 1, w - 1, h - 2);
            g.drawLine(2, h - 1, w - 2, h - 1);

            Composite composite = g2D.getComposite();
            AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            g2D.setComposite(alpha);
            g.setColor(FRAME_LOWEST_COLOR);
            g.drawLine(2, 1, w - 2, 1);
            g.drawLine(2, 2, 2, h - 3);
            g.setColor(FRAME_LOWER_LO_COLOR);
            g.drawLine(0, 0, 0, h);
            g2D.setComposite(composite);

            g.translate(-x, -y);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
        }

        public Insets getBorderInsets(Component c, Insets borderInsets) {
            borderInsets.left = INSETS.left;
            borderInsets.top = INSETS.top;
            borderInsets.right = INSETS.right;
            borderInsets.bottom = INSETS.bottom;
            return borderInsets;
        }

    } // end of class ArrowButtonBorder
    
} // end of class HiFiComboBoxUI
