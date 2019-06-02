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

package com.jtattoo.plaf;

import java.awt.*;
import javax.swing.Icon;
import javax.swing.UIManager;

/**
 * @author  Michael Hagen
 */
public abstract class XPScrollButton extends BaseScrollButton {

    public XPScrollButton(int direction, int width) {
        super(direction, width);
    }

    private Icon getUpArrowIcon() {
        if (UIManager.getLookAndFeel() instanceof AbstractLookAndFeel) {
            AbstractLookAndFeel laf = (AbstractLookAndFeel)UIManager.getLookAndFeel();
            return laf.getIconFactory().getUpArrowIcon();
        }
        return null;
    }
    
    private Icon getDownArrowIcon() {
        if (UIManager.getLookAndFeel() instanceof AbstractLookAndFeel) {
            AbstractLookAndFeel laf = (AbstractLookAndFeel)UIManager.getLookAndFeel();
            return laf.getIconFactory().getDownArrowIcon();
        }
        return null;
    }
    
    private Icon getLeftArrowIcon() {
        if (UIManager.getLookAndFeel() instanceof AbstractLookAndFeel) {
            AbstractLookAndFeel laf = (AbstractLookAndFeel)UIManager.getLookAndFeel();
            return laf.getIconFactory().getLeftArrowIcon();
        }
        return null;
    }
    
    private Icon getRightArrowIcon() {
        if (UIManager.getLookAndFeel() instanceof AbstractLookAndFeel) {
            AbstractLookAndFeel laf = (AbstractLookAndFeel)UIManager.getLookAndFeel();
            return laf.getIconFactory().getRightArrowIcon();
        }
        return null;
    }
    
    public Color getFrameColor() {
        return Color.white;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        Composite savedComposite = g2D.getComposite();
        Paint savedPaint = g2D.getPaint();

        boolean isPressed = getModel().isPressed();
        boolean isRollover = getModel().isRollover();

        int width = getWidth();
        int height = getHeight();

        Color[] tc = AbstractLookAndFeel.getTheme().getThumbColors();
        Color c1 = tc[0];
        Color c2 = tc[tc.length - 1];
        if (isPressed) {
            c1 = ColorHelper.darker(c1, 5);
            c2 = ColorHelper.darker(c2, 5);
        } else if (isRollover) {
            c1 = ColorHelper.brighter(c1, 20);
            c2 = ColorHelper.brighter(c2, 20);
        }

        g2D.setPaint(new GradientPaint(0, 0, c1, width, height, c2));
        g.fillRect(0, 0, width, height);
        g2D.setPaint(savedPaint);

        g.setColor(getFrameColor());
        g.drawLine(1, 1, width - 2, 1);
        g.drawLine(1, 1, 1, height - 3);
        g.drawLine(width - 2, 1, width - 2, height - 3);
        g.drawLine(2, height - 2, width - 3, height - 2);

        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        g2D.setComposite(alpha);
        g2D.setColor(c2);
        g.drawLine(2, 2, width - 3, 2);
        g.drawLine(2, 3, 2, height - 3);

        g.setColor(ColorHelper.darker(c2, 40));
        g.drawLine(width - 1, 2, width - 1, height - 3);
        g.drawLine(3, height - 1, width - 3, height - 1);
        alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
        g2D.setComposite(alpha);
        g.drawLine(1, height - 2, 2, height - 1);
        g.drawLine(width - 1, height - 2, width - 2, height - 1);

        g2D.setComposite(savedComposite);

        int x;
        int y;
        Icon icon;
        // paint the icon
        switch (getDirection()) {
            case NORTH:
                icon = getUpArrowIcon();
                x = (width / 2) - (icon.getIconWidth() / 2);
                y = (height / 2) - (icon.getIconHeight() / 2);
                break;
            case SOUTH:
                icon = getDownArrowIcon();
                x = (width / 2) - (icon.getIconWidth() / 2);
                y = (height / 2) - (icon.getIconHeight() / 2) + 1;
                break;
            case WEST:
                icon = getLeftArrowIcon();
                x = (width / 2) - (icon.getIconWidth() / 2);
                y = (height / 2) - (icon.getIconHeight() / 2);
                break;
            default:
                icon = getRightArrowIcon();
                x = (width / 2) - (icon.getIconWidth() / 2) + 1;
                y = (height / 2) - (icon.getIconHeight() / 2);
                break;
        }
        icon.paintIcon(this, g, x, y);
    }

    @Override
    public Dimension getPreferredSize() {
        switch (getDirection()) {
            case NORTH:
                return new Dimension(buttonWidth, buttonWidth);
            case SOUTH:
                return new Dimension(buttonWidth, buttonWidth);
            case EAST:
                return new Dimension(buttonWidth, buttonWidth);
            case WEST:
                return new Dimension(buttonWidth, buttonWidth);
            default:
                return new Dimension(0, 0);
        }
    }
    
} // end of class XPScrollButton
