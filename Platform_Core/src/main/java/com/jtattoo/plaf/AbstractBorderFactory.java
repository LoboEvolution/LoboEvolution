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

import javax.swing.border.Border;

/**
 * A factory for creating AbstractBorder objects.
 *
 * @author Michael Hagen
 */
public interface AbstractBorderFactory {

    /** Gets the focus frame border.
	 *
	 * @return the focus frame border
	 */
    public Border getFocusFrameBorder();

    /** Gets the button border.
	 *
	 * @return the button border
	 */
    public Border getButtonBorder();

    /** Gets the toggle button border.
	 *
	 * @return the toggle button border
	 */
    public Border getToggleButtonBorder();

    /** Gets the text border.
	 *
	 * @return the text border
	 */
    public Border getTextBorder();

    /** Gets the spinner border.
	 *
	 * @return the spinner border
	 */
    public Border getSpinnerBorder();

    /** Gets the text field border.
	 *
	 * @return the text field border
	 */
    public Border getTextFieldBorder();

    /** Gets the combo box border.
	 *
	 * @return the combo box border
	 */
    public Border getComboBoxBorder();

    /** Gets the table header border.
	 *
	 * @return the table header border
	 */
    public Border getTableHeaderBorder();

    /** Gets the table scroll pane border.
	 *
	 * @return the table scroll pane border
	 */
    public Border getTableScrollPaneBorder();

    /** Gets the scroll pane border.
	 *
	 * @return the scroll pane border
	 */
    public Border getScrollPaneBorder();

    /** Gets the tabbed pane border.
	 *
	 * @return the tabbed pane border
	 */
    public Border getTabbedPaneBorder();

    /** Gets the menu bar border.
	 *
	 * @return the menu bar border
	 */
    public Border getMenuBarBorder();

    /** Gets the menu item border.
	 *
	 * @return the menu item border
	 */
    public Border getMenuItemBorder();

    /** Gets the popup menu border.
	 *
	 * @return the popup menu border
	 */
    public Border getPopupMenuBorder();

    /** Gets the internal frame border.
	 *
	 * @return the internal frame border
	 */
    public Border getInternalFrameBorder();

    /** Gets the palette border.
	 *
	 * @return the palette border
	 */
    public Border getPaletteBorder();

    /** Gets the tool bar border.
	 *
	 * @return the tool bar border
	 */
    public Border getToolBarBorder();

    /** Gets the desktop icon border.
	 *
	 * @return the desktop icon border
	 */
    public Border getDesktopIconBorder();

    /** Gets the progress bar border.
	 *
	 * @return the progress bar border
	 */
    public Border getProgressBarBorder();
}

