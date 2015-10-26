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
 
package com.jtattoo.plaf.texture;

import com.jtattoo.plaf.*;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.Icon;

/**
 * @author Michael Hagen
 */
public class TextureIcons extends BaseIcons {

    public static Icon getUpArrowIcon() {
        if (upArrowIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                upArrowIcon = new LazyImageIcon("texture/icons/small/arrow_up_7x6.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                upArrowIcon = new LazyImageIcon("texture/icons/medium/arrow_up_9x8.png");
            } else {
                upArrowIcon = new LazyImageIcon("texture/icons/large/arrow_up_11x10.png");
            }
        }
        return upArrowIcon;
    }

    public static Icon getDownArrowIcon() {
        if (downArrowIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                downArrowIcon = new LazyImageIcon("texture/icons/small/arrow_down_7x6.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                downArrowIcon = new LazyImageIcon("texture/icons/medium/arrow_down_9x8.png");
            } else {
                downArrowIcon = new LazyImageIcon("texture/icons/large/arrow_down_11x10.png");
            }
        }
        return downArrowIcon;
    }

    public static Icon getLeftArrowIcon() {
        if (leftArrowIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                leftArrowIcon = new LazyImageIcon("texture/icons/small/arrow_left_6x7.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                leftArrowIcon = new LazyImageIcon("texture/icons/medium/arrow_left_8x9.png");
            } else {
                leftArrowIcon = new LazyImageIcon("texture/icons/large/arrow_left_10x11.png");
            }
        }
        return leftArrowIcon;
    }

    public static Icon getRightArrowIcon() {
        if (rightArrowIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                rightArrowIcon = new LazyImageIcon("texture/icons/small/arrow_right_6x7.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                rightArrowIcon = new LazyImageIcon("texture/icons/medium/arrow_right_8x9.png");
            } else {
                rightArrowIcon = new LazyImageIcon("texture/icons/large/arrow_right_10x11.png");
            }
        }
        return rightArrowIcon;
    }
    
    public static Icon getIconIcon() {
        if (iconIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                iconIcon = new MacIconIcon();
            } else {
                Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
                Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
                Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
                iconIcon = new BaseIcons.IconSymbol(iconColor, iconShadowColor, iconRolloverColor, new Insets(1, 1, 1, 1));
            }
        }
        return iconIcon;
    }

    public static Icon getMinIcon() {
        if (minIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                minIcon = new MacMinIcon();
            } else {
                Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
                Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
                Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
                minIcon = new BaseIcons.MinSymbol(iconColor, iconShadowColor, iconRolloverColor, new Insets(1, 1, 1, 1));
            }
        }
        return minIcon;
    }

    public static Icon getMaxIcon() {
        if (maxIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                maxIcon = new MacMaxIcon();
            } else {
                Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
                Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
                Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
                maxIcon = new BaseIcons.MaxSymbol(iconColor, iconShadowColor, iconRolloverColor, new Insets(1, 1, 1, 1));
            }
        }
        return maxIcon;
    }

    public static Icon getCloseIcon() {
        if (closeIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                closeIcon = new MacCloseIcon();
            } else {
                Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
                Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
                Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
                closeIcon = new BaseIcons.CloseSymbol(iconColor, iconShadowColor, iconRolloverColor, new Insets(1, 1, 1, 1));
            }
        }
        return closeIcon;
    }

    public static Icon getSplitterHorBumpIcon() {
        if (splitterHorBumpIcon == null) {
            splitterHorBumpIcon = new LazyImageIcon("texture/icons/SplitterHorBumps.gif");
        }
        return splitterHorBumpIcon;
    }

    public static Icon getSplitterVerBumpIcon() {
        if (splitterVerBumpIcon == null) {
            splitterVerBumpIcon = new LazyImageIcon("texture/icons/SplitterVerBumps.gif");
        }
        return splitterVerBumpIcon;
    }

    public static Icon getThumbHorIcon() {
        if ("Default".equals(AbstractLookAndFeel.getTheme().getName())) {
            if (thumbHorIcon == null) {
                thumbHorIcon = new LazyImageIcon("texture/icons/thumb_hor.gif");
            }
            return thumbHorIcon;
        } else {
            return BaseIcons.getThumbHorIcon();
        }
    }

    public static Icon getThumbVerIcon() {
        if ("Default".equals(AbstractLookAndFeel.getTheme().getName())) {
            if (thumbVerIcon == null) {
                thumbVerIcon = new LazyImageIcon("texture/icons/thumb_ver.gif");
            }
            return thumbVerIcon;
        } else {
            return BaseIcons.getThumbVerIcon();
        }
    }

    public static Icon getThumbHorIconRollover() {
        if ("Default".equals(AbstractLookAndFeel.getTheme().getName())) {
            if (thumbHorIconRollover == null) {
                thumbHorIconRollover = new LazyImageIcon("texture/icons/thumb_hor_rollover.gif");
            }
            return thumbHorIconRollover;
        } else {
            return BaseIcons.getThumbHorIconRollover();
        }
    }

    public static Icon getThumbVerIconRollover() {
        if ("Default".equals(AbstractLookAndFeel.getTheme().getName())) {
            if (thumbVerIconRollover == null) {
                thumbVerIconRollover = new LazyImageIcon("texture/icons/thumb_ver_rollover.gif");
            }
            return thumbVerIconRollover;
        } else {
            return BaseIcons.getThumbVerIconRollover();
        }
    }
}
