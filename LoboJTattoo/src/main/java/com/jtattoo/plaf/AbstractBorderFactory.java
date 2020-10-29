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
 * <p>AbstractBorderFactory interface.</p>
 *
 * @author Michael Hagen
 * @version $Id: $Id
 */
public interface AbstractBorderFactory {

	/**
	 * <p>getButtonBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getButtonBorder();

	/**
	 * <p>getComboBoxBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getComboBoxBorder();

	/**
	 * <p>getDesktopIconBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getDesktopIconBorder();

	/**
	 * <p>getFocusFrameBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getFocusFrameBorder();

	/**
	 * <p>getInternalFrameBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getInternalFrameBorder();

	/**
	 * <p>getMenuBarBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getMenuBarBorder();

	/**
	 * <p>getMenuItemBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getMenuItemBorder();

	/**
	 * <p>getPaletteBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getPaletteBorder();

	/**
	 * <p>getPopupMenuBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getPopupMenuBorder();

	/**
	 * <p>getProgressBarBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getProgressBarBorder();

	/**
	 * <p>getScrollPaneBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getScrollPaneBorder();

	/**
	 * <p>getSpinnerBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getSpinnerBorder();

	/**
	 * <p>getTabbedPaneBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getTabbedPaneBorder();

	/**
	 * <p>getTableHeaderBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getTableHeaderBorder();

	/**
	 * <p>getTableScrollPaneBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getTableScrollPaneBorder();

	/**
	 * <p>getTextBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getTextBorder();

	/**
	 * <p>getTextFieldBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getTextFieldBorder();

	/**
	 * <p>getToggleButtonBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getToggleButtonBorder();

	/**
	 * <p>getToolBarBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
    Border getToolBarBorder();

} // end of interface AbstractBorderFactory
