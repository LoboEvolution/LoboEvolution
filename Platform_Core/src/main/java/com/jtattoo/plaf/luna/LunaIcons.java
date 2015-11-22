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
 * The Class LunaIcons.
 *
 * @author Michael Hagen
 */
public class LunaIcons extends BaseIcons {

    /** Gets the combo box icon.
	 *
	 * @return the combo box icon
	 */
    public static Icon getComboBoxIcon() {
        return getDownArrowIcon();
    }

    /** Gets the up arrow icon.
	 *
	 * @return the up arrow icon
	 */
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

    /** Gets the down arrow icon.
	 *
	 * @return the down arrow icon
	 */
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

    /** Gets the left arrow icon.
	 *
	 * @return the left arrow icon
	 */
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

    /** Gets the right arrow icon.
	 *
	 * @return the right arrow icon
	 */
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
    
    /** Gets the icon icon.
	 *
	 * @return the icon icon
	 */
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

    /** Gets the min icon.
	 *
	 * @return the min icon
	 */
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

    /** Gets the max icon.
	 *
	 * @return the max icon
	 */
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

    /** Gets the close icon.
	 *
	 * @return the close icon
	 */
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

/**
 * The Class TitleButtonIcon.
 */
//------------------------------------------------------------------------------
    private static class TitleButtonIcon implements Icon {

        /** The Constant blueFrameColor. */
        private static final Color blueFrameColor = Color.white;
        
        /** The Constant blueColorLight. */
        private static final Color blueColorLight = new Color(154, 183, 250);
        
        /** The Constant blueColorDark. */
        private static final Color blueColorDark = new Color(0, 69, 211);
        
        /** The Constant closerColorLight. */
        private static final Color closerColorLight = new Color(241, 172, 154);
        
        /** The Constant closerColorDark. */
        private static final Color closerColorDark = new Color(224, 56, 2);
        
        /** The Constant ICON_ICON_TYP. */
        private static final int ICON_ICON_TYP = 0;
        
        /** The Constant MIN_ICON_TYP. */
        private static final int MIN_ICON_TYP = 1;
        
        /** The Constant MAX_ICON_TYP. */
        private static final int MAX_ICON_TYP = 2;
        
        /** The Constant CLOSE_ICON_TYP. */
        private static final int CLOSE_ICON_TYP = 3;
        
        /** The icon typ. */
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

            Color fc = blueFrameColor;
            Color cHi = blueColorLight;
            Color cLo = blueColorDark;
            if (iconTyp == CLOSE_ICON_TYP) {
                cHi = closerColorLight;
                cLo = closerColorDark;
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
            if (iconTyp == ICON_ICON_TYP) {
                icon = new BaseIcons.IconSymbol(cHi, cLo, null, new Insets(0, 0, 0, 1));
            } else if (iconTyp == MIN_ICON_TYP) {
                icon = new BaseIcons.MinSymbol(cHi, cLo, null, new Insets(0, 0, 0, 0));
            } else if (iconTyp == MAX_ICON_TYP) {
                icon = new BaseIcons.MaxSymbol(cHi, cLo, null, new Insets(0, 0, 0, 1));
            } else if (iconTyp == CLOSE_ICON_TYP) {
                icon = new BaseIcons.CloseSymbol(cHi, cLo, null, new Insets(0, 0, 0, 1));
            }
            if (icon != null) {
                icon.paintIcon(c, g, 0, 0);
            }
        }
    }
}
