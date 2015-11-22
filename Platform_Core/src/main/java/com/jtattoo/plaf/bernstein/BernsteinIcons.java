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
 
package com.jtattoo.plaf.bernstein;

import com.jtattoo.plaf.*;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.plaf.UIResource;

/**
 * The Class BernsteinIcons.
 *
 * @author Michael Hagen
 */
public class BernsteinIcons extends BaseIcons {

    /** Gets the radio button icon.
	 *
	 * @return the radio button icon
	 */
    public static Icon getRadioButtonIcon() {
        if (radioButtonIcon == null) {
            radioButtonIcon = new RadioButtonIcon();
        }
        return radioButtonIcon;
    }

    /** Gets the check box icon.
	 *
	 * @return the check box icon
	 */
    public static Icon getCheckBoxIcon() {
        if (checkBoxIcon == null) {
            checkBoxIcon = new CheckBoxIcon();
        }
        return checkBoxIcon;
    }

    /** Gets the thumb hor icon.
	 *
	 * @return the thumb hor icon
	 */
    public static Icon getThumbHorIcon() {
        if (thumbHorIcon == null) {
            thumbHorIcon = new LazyImageIcon("bernstein/icons/radio.gif");
        }
        return thumbHorIcon;
    }

    /** Gets the thumb ver icon.
	 *
	 * @return the thumb ver icon
	 */
    public static Icon getThumbVerIcon() {
        if (thumbVerIcon == null) {
            thumbVerIcon = new LazyImageIcon("bernstein/icons/radio.gif");
        }
        return thumbVerIcon;
    }

    /** Gets the thumb hor icon rollover.
	 *
	 * @return the thumb hor icon rollover
	 */
    public static Icon getThumbHorIconRollover() {
        if (thumbHorIconRollover == null) {
            thumbHorIconRollover = new LazyImageIcon("bernstein/icons/radio_rollover.gif");
        }
        return thumbHorIconRollover;
    }

    /** Gets the thumb ver icon rollover.
	 *
	 * @return the thumb ver icon rollover
	 */
    public static Icon getThumbVerIconRollover() {
        if (thumbVerIconRollover == null) {
            thumbVerIconRollover = new LazyImageIcon("bernstein/icons/radio_rollover.gif");
        }
        return thumbVerIconRollover;
    }

    /** Gets the splitter up arrow icon.
	 *
	 * @return the splitter up arrow icon
	 */
    public static Icon getSplitterUpArrowIcon() {
        if (splitterUpArrowIcon == null) {
            splitterUpArrowIcon = new LazyImageIcon("bernstein/icons/SplitterUpArrow.gif");
        }
        return splitterUpArrowIcon;
    }

    /** Gets the splitter down arrow icon.
	 *
	 * @return the splitter down arrow icon
	 */
    public static Icon getSplitterDownArrowIcon() {
        if (splitterDownArrowIcon == null) {
            splitterDownArrowIcon = new LazyImageIcon("bernstein/icons/SplitterDownArrow.gif");
        }
        return splitterDownArrowIcon;
    }

    /** Gets the splitter left arrow icon.
	 *
	 * @return the splitter left arrow icon
	 */
    public static Icon getSplitterLeftArrowIcon() {
        if (splitterLeftArrowIcon == null) {
            splitterLeftArrowIcon = new LazyImageIcon("bernstein/icons/SplitterLeftArrow.gif");
        }
        return splitterLeftArrowIcon;
    }

    /** Gets the splitter right arrow icon.
	 *
	 * @return the splitter right arrow icon
	 */
    public static Icon getSplitterRightArrowIcon() {
        if (splitterRightArrowIcon == null) {
            splitterRightArrowIcon = new LazyImageIcon("bernstein/icons/SplitterRightArrow.gif");
        }
        return splitterRightArrowIcon;
    }

    /** The Class CheckBoxIcon.
	 */
    //--------------------------------------------------------------------------------------------------------
    private static class CheckBoxIcon implements Icon, UIResource {

        /** The check icon. */
        private static Icon checkIcon = null;
        
        /** The check selected icon. */
        private static Icon checkSelectedIcon = null;
        
        /** The check pressed icon. */
        private static Icon checkPressedIcon = null;
        
        /** The check rollover icon. */
        private static Icon checkRolloverIcon = null;
        
        /** The check rollover selected icon. */
        private static Icon checkRolloverSelectedIcon = null;
        
        /** The check disabled icon. */
        private static Icon checkDisabledIcon = null;
        
        /** The check disabled selected icon. */
        private static Icon checkDisabledSelectedIcon = null;

        public CheckBoxIcon() {
            checkIcon = new LazyImageIcon("bernstein/icons/check.gif");
            checkSelectedIcon = new LazyImageIcon("bernstein/icons/check_selected.gif");
            checkPressedIcon = new LazyImageIcon("bernstein/icons/check_pressed.gif");
            checkRolloverIcon = new LazyImageIcon("bernstein/icons/check_rollover.gif");
            checkRolloverSelectedIcon = new LazyImageIcon("bernstein/icons/check_rollover_selected.gif");
            checkDisabledIcon = new LazyImageIcon("bernstein/icons/check_disabled.gif");
            checkDisabledSelectedIcon = new LazyImageIcon("bernstein/icons/check_disabled_selected.gif");
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            if (!JTattooUtilities.isLeftToRight(c)) {
                x += 2;
            }
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();
            if (button.isEnabled()) {
                if (model.isPressed() && model.isArmed()) {
                    checkPressedIcon.paintIcon(c, g, x, y);
                } else if (model.isSelected()) {
                    if (button.isRolloverEnabled() && model.isRollover() && !model.isArmed()) {
                        checkRolloverSelectedIcon.paintIcon(c, g, x, y);
                    } else {
                        checkSelectedIcon.paintIcon(c, g, x, y);
                    }
                } else {
                    if (button.isRolloverEnabled() && model.isRollover() && !model.isArmed()) {
                        checkRolloverIcon.paintIcon(c, g, x, y);
                    } else {
                        checkIcon.paintIcon(c, g, x, y);
                    }
                }
            } else {
                if (model.isPressed() && model.isArmed()) {
                    checkPressedIcon.paintIcon(c, g, x, y);
                } else if (model.isSelected()) {
                    checkDisabledSelectedIcon.paintIcon(c, g, x, y);
                } else {
                    checkDisabledIcon.paintIcon(c, g, x, y);
                }
            }
        }

        public int getIconWidth() {
            return checkIcon.getIconWidth() + 2;
        }

        public int getIconHeight() {
            return checkIcon.getIconHeight();
        }
    }

    /** The Class RadioButtonIcon.
	 */
    private static class RadioButtonIcon implements Icon, UIResource {

        /** The radio icon. */
        private Icon radioIcon = null;
        
        /** The radio selected icon. */
        private Icon radioSelectedIcon = null;
        
        /** The radio rollover icon. */
        private Icon radioRolloverIcon = null;
        
        /** The radio rollover selected icon. */
        private Icon radioRolloverSelectedIcon = null;
        
        /** The radio disabled icon. */
        private Icon radioDisabledIcon = null;
        
        /** The radio disabled selected icon. */
        private Icon radioDisabledSelectedIcon = null;

        public RadioButtonIcon() {
            radioIcon = new LazyImageIcon("bernstein/icons/radio.gif");
            radioSelectedIcon = new LazyImageIcon("bernstein/icons/radio_selected.gif");
            radioRolloverIcon = new LazyImageIcon("bernstein/icons/radio_rollover.gif");
            radioRolloverSelectedIcon = new LazyImageIcon("bernstein/icons/radio_rollover_selected.gif");
            radioDisabledIcon = new LazyImageIcon("bernstein/icons/radio_disabled.gif");
            radioDisabledSelectedIcon = new LazyImageIcon("bernstein/icons/radio_disabled_selected.gif");
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            if (!JTattooUtilities.isLeftToRight(c)) {
                x += 2;
            }
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();
            if (button.isEnabled()) {
                if (model.isSelected()) {
                    if (button.isRolloverEnabled() && model.isRollover()) {
                        radioRolloverSelectedIcon.paintIcon(c, g, x, y);
                    } else {
                        radioSelectedIcon.paintIcon(c, g, x, y);
                    }
                } else {
                    if (button.isRolloverEnabled() && model.isRollover()) {
                        radioRolloverIcon.paintIcon(c, g, x, y);
                    } else {
                        radioIcon.paintIcon(c, g, x, y);
                    }
                }
            } else {
                if (model.isSelected()) {
                    radioDisabledSelectedIcon.paintIcon(c, g, x, y);
                } else {
                    radioDisabledIcon.paintIcon(c, g, x, y);
                }
            }
        }

        public int getIconWidth() {
            return radioIcon.getIconWidth() + 2;
        }

        public int getIconHeight() {
            return radioIcon.getIconHeight();
        }
    }
}
