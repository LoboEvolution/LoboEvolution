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
 
package com.jtattoo.plaf.aluminium;

import com.jtattoo.plaf.*;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.Icon;

/**
 * The Class AluminiumIcons.
 *
 * @author Michael Hagen
 */
public class AluminiumIcons extends BaseIcons {

    /** Gets the icon icon.
	 *
	 * @return the icon icon
	 */
    public static Icon getIconIcon() {
        if (iconIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                iconIcon = new MacIconIcon();
            } else {
                Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
                Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
                Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
                iconIcon = new BaseIcons.IconSymbol(iconColor, iconShadowColor, iconRolloverColor, new Insets(0, 0, 1, 0));
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
                Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
                Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
                Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
                minIcon = new BaseIcons.MinSymbol(iconColor, iconShadowColor, iconRolloverColor, new Insets(0, 0, 1, 0));
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
                Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
                Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
                Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
                maxIcon = new BaseIcons.MaxSymbol(iconColor, iconShadowColor, iconRolloverColor, new Insets(0, 0, 1, 0));
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
                Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
                Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
                Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
                closeIcon = new BaseIcons.CloseSymbol(iconColor, iconShadowColor, iconRolloverColor, new Insets(0, 0, 1, 0));
            }
        }
        return closeIcon;
    }

    /** Gets the thumb hor icon.
	 *
	 * @return the thumb hor icon
	 */
    public static Icon getThumbHorIcon() {
        if (thumbHorIcon == null) {
            thumbHorIcon = new LazyImageIcon("aluminium/icons/thumb_hor.gif");
        }
        return thumbHorIcon;
    }

    /** Gets the thumb hor icon rollover.
	 *
	 * @return the thumb hor icon rollover
	 */
    public static Icon getThumbHorIconRollover() {
        if (thumbHorIconRollover == null) {
            thumbHorIconRollover = new LazyImageIcon("aluminium/icons/thumb_hor_rollover.gif");
        }
        return thumbHorIconRollover;
    }

    /** Gets the thumb ver icon.
	 *
	 * @return the thumb ver icon
	 */
    public static Icon getThumbVerIcon() {
        if (thumbVerIcon == null) {
            thumbVerIcon = new LazyImageIcon("aluminium/icons/thumb_ver.gif");
        }
        return thumbVerIcon;
    }

    /** Gets the thumb ver icon rollover.
	 *
	 * @return the thumb ver icon rollover
	 */
    public static Icon getThumbVerIconRollover() {
        if (thumbVerIconRollover == null) {
            thumbVerIconRollover = new LazyImageIcon("aluminium/icons/thumb_ver_rollover.gif");
        }
        return thumbVerIconRollover;
    }

    /** Gets the splitter up arrow icon.
	 *
	 * @return the splitter up arrow icon
	 */
    public static Icon getSplitterUpArrowIcon() {
        if (splitterUpArrowIcon == null) {
            splitterUpArrowIcon = new LazyImageIcon("aluminium/icons/SplitterUpArrow.gif");
        }
        return splitterUpArrowIcon;
    }

    /** Gets the splitter down arrow icon.
	 *
	 * @return the splitter down arrow icon
	 */
    public static Icon getSplitterDownArrowIcon() {
        if (splitterDownArrowIcon == null) {
            splitterDownArrowIcon = new LazyImageIcon("aluminium/icons/SplitterDownArrow.gif");
        }
        return splitterDownArrowIcon;
    }

    /** Gets the splitter left arrow icon.
	 *
	 * @return the splitter left arrow icon
	 */
    public static Icon getSplitterLeftArrowIcon() {
        if (splitterLeftArrowIcon == null) {
            splitterLeftArrowIcon = new LazyImageIcon("aluminium/icons/SplitterLeftArrow.gif");
        }
        return splitterLeftArrowIcon;
    }

    /** Gets the splitter right arrow icon.
	 *
	 * @return the splitter right arrow icon
	 */
    public static Icon getSplitterRightArrowIcon() {
        if (splitterRightArrowIcon == null) {
            splitterRightArrowIcon = new LazyImageIcon("aluminium/icons/SplitterRightArrow.gif");
        }
        return splitterRightArrowIcon;
    }

}
