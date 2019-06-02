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
package com.jtattoo.plaf.luna;

import com.jtattoo.plaf.*;
import java.awt.*;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * @author Michael Hagen
 */
public class LunaIcons extends BaseIcons {

    public static Icon getComboBoxIcon() {
        return getDownArrowIcon();
    }

    public static Icon getUpArrowIcon() {
        if (upArrowIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                upArrowIcon = new LazyImageIcon("luna/icons/small/arrow_up_7x6.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                upArrowIcon = new LazyImageIcon("luna/icons/medium/arrow_up_9x8.png");
            } else {
                upArrowIcon = new LazyImageIcon("luna/icons/large/arrow_up_11x10.png");
            }
        }
        return upArrowIcon;
    }

    public static Icon getDownArrowIcon() {
        if (downArrowIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                downArrowIcon = new LazyImageIcon("luna/icons/small/arrow_down_7x6.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                downArrowIcon = new LazyImageIcon("luna/icons/medium/arrow_down_9x8.png");
            } else {
                downArrowIcon = new LazyImageIcon("luna/icons/large/arrow_down_11x10.png");
            }
        }
        return downArrowIcon;
    }

    public static Icon getLeftArrowIcon() {
        if (leftArrowIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                leftArrowIcon = new LazyImageIcon("luna/icons/small/arrow_left_6x7.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                leftArrowIcon = new LazyImageIcon("luna/icons/medium/arrow_left_8x9.png");
            } else {
                leftArrowIcon = new LazyImageIcon("luna/icons/large/arrow_left_10x11.png");
            }
        }
        return leftArrowIcon;
    }

    public static Icon getRightArrowIcon() {
        if (rightArrowIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                rightArrowIcon = new LazyImageIcon("luna/icons/small/arrow_right_6x7.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                rightArrowIcon = new LazyImageIcon("luna/icons/medium/arrow_right_8x9.png");
            } else {
                rightArrowIcon = new LazyImageIcon("luna/icons/large/arrow_right_10x11.png");
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

        private static final Color BLUE_FRAME_COLOR = Color.white;
        private static final Color BLUE_COLOR_LIGHT = new Color(154, 183, 250);
        private static final Color BLUE_COLOR_DARK = new Color(0, 69, 211);
        private static final Color CLOSER_COLOR_LIGHT = new Color(241, 172, 154);
        private static final Color CLOSER_COLOR_DARK = new Color(224, 56, 2);

        private static final int ICON_ICON_TYP = 0;
        private static final int MIN_ICON_TYP = 1;
        private static final int MAX_ICON_TYP = 2;
        private static final int CLOSE_ICON_TYP = 3;

        private int iconTyp = ICON_ICON_TYP;

        public TitleButtonIcon(int typ) {
            iconTyp = typ;
        }

        @Override
        public int getIconHeight() {
            return 20;
        }

        @Override
        public int getIconWidth() {
            return 20;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            int w = c.getWidth();
            int h = c.getHeight();

            JButton b = (JButton) c;
            Graphics2D g2D = (Graphics2D) g;

            boolean isActive = JTattooUtilities.isActive(b);
            boolean isPressed = b.getModel().isPressed();
            boolean isArmed = b.getModel().isArmed();
            boolean isRollover = b.getModel().isRollover();

            Color fc = BLUE_FRAME_COLOR;
            Color cHi = BLUE_COLOR_LIGHT;
            Color cLo = BLUE_COLOR_DARK;
            if (iconTyp == CLOSE_ICON_TYP) {
                cHi = CLOSER_COLOR_LIGHT;
                cLo = CLOSER_COLOR_DARK;
            }

            if (!isActive) {
                cHi = ColorHelper.brighter(cHi, 20);
                cLo = ColorHelper.brighter(cLo, 10);
            }
            if (isPressed && isArmed) {
                Color cTemp = ColorHelper.darker(cLo, 10);
                cLo = ColorHelper.darker(cHi, 10);
                cHi = cTemp;
            } else if (isRollover) {
                cHi = ColorHelper.brighter(cHi, 30);
                cLo = ColorHelper.brighter(cLo, 30);
            }

            g2D.setPaint(new GradientPaint(0, 0, cHi, w, h, cLo));
            g.fillRect(1, 1, w - 2, h - 2);

            g.setColor(fc);
            g.drawLine(1, 0, w - 2, 0);
            g.drawLine(0, 1, 0, h - 2);
            g.drawLine(1, h - 1, w - 2, h - 1);
            g.drawLine(w - 1, 1, w - 1, h - 2);
            Composite composite = g2D.getComposite();
            AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
            g2D.setComposite(alpha);
            g2D.setColor(cLo);
            g.drawLine(2, 1, w - 2, 1);
            g.drawLine(1, 2, 1, h - 2);
            g2D.setColor(ColorHelper.darker(cLo, 40));
            g.drawLine(2, h - 2, w - 2, h - 2);
            g.drawLine(w - 2, 2, w - 2, h - 2);

            g2D.setComposite(composite);

            // Paint the icon
            cHi = Color.white;
            cLo = ColorHelper.darker(cLo, 30);
            Icon icon = null;
            switch (iconTyp) {
                case ICON_ICON_TYP:
                    icon = new BaseIcons.IconSymbol(cHi, cLo, null, new Insets(0, 0, 0, 1));
                    break;
                case MIN_ICON_TYP:
                    icon = new BaseIcons.MinSymbol(cHi, cLo, null, new Insets(0, 0, 0, 0));
                    break;
                case MAX_ICON_TYP:
                    icon = new BaseIcons.MaxSymbol(cHi, cLo, null, new Insets(0, 0, 0, 1));
                    break;
                case CLOSE_ICON_TYP:
                    icon = new BaseIcons.CloseSymbol(cHi, cLo, null, new Insets(0, 0, 0, 1));
                    break;
                default:
                    break;
            }
            if (icon != null) {
                icon.paintIcon(c, g, 0, 0);
            }
        }
    }
    
} // end of class LunaIcons
