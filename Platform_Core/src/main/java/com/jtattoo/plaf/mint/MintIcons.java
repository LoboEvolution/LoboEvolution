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
 
package com.jtattoo.plaf.mint;

import com.jtattoo.plaf.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * @author Michael Hagen
 */
public class MintIcons extends BaseIcons {

    public static Icon getUpArrowIcon() {
        if (upArrowIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                upArrowIcon = new LazyImageIcon("mint/icons/small/arrow_up_7x6.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                upArrowIcon = new LazyImageIcon("mint/icons/medium/arrow_up_9x8.png");
            } else {
                upArrowIcon = new LazyImageIcon("mint/icons/large/arrow_up_11x10.png");
            }
        }
        return upArrowIcon;
    }

    public static Icon getDownArrowIcon() {
        if (downArrowIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                downArrowIcon = new LazyImageIcon("mint/icons/small/arrow_down_7x6.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                downArrowIcon = new LazyImageIcon("mint/icons/medium/arrow_down_9x8.png");
            } else {
                downArrowIcon = new LazyImageIcon("mint/icons/large/arrow_down_11x10.png");
            }
        }
        return downArrowIcon;
    }

    public static Icon getLeftArrowIcon() {
        if (leftArrowIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                leftArrowIcon = new LazyImageIcon("mint/icons/small/arrow_left_6x7.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                leftArrowIcon = new LazyImageIcon("mint/icons/medium/arrow_left_8x9.png");
            } else {
                leftArrowIcon = new LazyImageIcon("mint/icons/large/arrow_left_10x11.png");
            }
        }
        return leftArrowIcon;
    }

    public static Icon getRightArrowIcon() {
        if (rightArrowIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                rightArrowIcon = new LazyImageIcon("mint/icons/small/arrow_right_6x7.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                rightArrowIcon = new LazyImageIcon("mint/icons/medium/arrow_right_8x9.png");
            } else {
                rightArrowIcon = new LazyImageIcon("mint/icons/large/arrow_right_10x11.png");
            }
        }
        return rightArrowIcon;
    }
    
    public static Icon getIconIcon() {
        if (iconIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                iconIcon = new MacIconIcon();
            } else {
                iconIcon = new TitleButtonIcon(TitleButtonIcon.ICON_ICON_TYP);
            }
        }
        return iconIcon;
    }

    public static Icon getMinIcon() {
        if (minIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                minIcon = new MacMinIcon();
            } else {
                minIcon = new TitleButtonIcon(TitleButtonIcon.MIN_ICON_TYP);
            }
        }
        return minIcon;
    }

    public static Icon getMaxIcon() {
        if (maxIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                maxIcon = new MacMaxIcon();
            } else {
                maxIcon = new TitleButtonIcon(TitleButtonIcon.MAX_ICON_TYP);
            }
        }
        return maxIcon;
    }

    public static Icon getCloseIcon() {
        if (closeIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                closeIcon = new MacCloseIcon();
            } else {
                closeIcon = new TitleButtonIcon(TitleButtonIcon.CLOSE_ICON_TYP);
            }
        }
        return closeIcon;
    }

//------------------------------------------------------------------------------    
    private static class TitleButtonIcon implements Icon {

        private static final Color closerColorLight = new Color(241, 172, 154);
        private static final Color closerColorDark = new Color(224, 56, 2);
        
        private static final int ICON_ICON_TYP = 0;
        private static final int MIN_ICON_TYP = 1;
        private static final int MAX_ICON_TYP = 2;
        private static final int CLOSE_ICON_TYP = 3;
        
        private int iconTyp = ICON_ICON_TYP;

        public TitleButtonIcon(int typ) {
            iconTyp = typ;
        }

        public int getIconHeight() {
            return 20;
        }

        public int getIconWidth() {
            return 20;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            int w = c.getWidth();
            int h = c.getHeight();

            JButton b = (JButton) c;
            Graphics2D g2D = (Graphics2D) g;

            boolean isActive = JTattooUtilities.isActive(b);
            boolean isPressed = b.getModel().isPressed();
            boolean isArmed = b.getModel().isArmed();
            boolean isRollover = b.getModel().isRollover();

            Color cHi = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getWindowTitleColorLight(), 40);
            Color cLo = ColorHelper.darker(AbstractLookAndFeel.getTheme().getWindowTitleColorDark(), 10);
            if (iconTyp == CLOSE_ICON_TYP) {
                cHi = closerColorLight;
                cLo = closerColorDark;
            }

            Color fcHi = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getWindowTitleColorDark(), 80);
            Color fcLo = ColorHelper.darker(AbstractLookAndFeel.getTheme().getWindowTitleColorDark(), 40);

            if (!isActive) {
                cHi = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorLight(), 40);
                cLo = ColorHelper.darker(AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorDark(), 10);
                fcHi = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorLight(), 60);
                fcLo = ColorHelper.darker(AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorDark(), 10);
            }
            if (isPressed && isArmed) {
                Color cTemp = ColorHelper.darker(cLo, 10);
                cLo = ColorHelper.darker(cHi, 10);
                cHi = cTemp;
            } else if (isRollover) {
                cHi = ColorHelper.brighter(cHi, 30);
                cLo = ColorHelper.brighter(cLo, 30);
            }

            Shape savedClip = g.getClip();
            Area area = new Area(new RoundRectangle2D.Double(1, 1, w - 1, h - 1, 3, 3));
            g2D.setClip(area);

            g2D.setPaint(new GradientPaint(0, 0, fcLo, w, h, fcHi));
            g.fillRect(1, 1, w - 1, h - 1);

            g2D.setPaint(new GradientPaint(0, 0, ColorHelper.brighter(cHi, 80), w, h, ColorHelper.darker(cLo, 30)));
            g.fillRect(2, 2, w - 3, h - 3);

            g2D.setPaint(new GradientPaint(0, 0, cHi, w, h, cLo));
            g.fillRect(3, 3, w - 5, h - 5);

            g2D.setClip(savedClip);

            cHi = Color.white;
            cLo = ColorHelper.darker(cLo, 30);
            Icon icon = null;
            if (iconTyp == ICON_ICON_TYP) {
                icon = new BaseIcons.IconSymbol(cHi, cLo, null);
            } else if (iconTyp == MIN_ICON_TYP) {
                icon = new BaseIcons.MinSymbol(cHi, cLo, null);
            } else if (iconTyp == MAX_ICON_TYP) {
                icon = new BaseIcons.MaxSymbol(cHi, cLo, null);
            } else if (iconTyp == CLOSE_ICON_TYP) {
                icon = new BaseIcons.CloseSymbol(cHi, cLo, null);
            }
            if (icon != null) {
                icon.paintIcon(c, g, 0, 0);
            }
        }
    }
}
