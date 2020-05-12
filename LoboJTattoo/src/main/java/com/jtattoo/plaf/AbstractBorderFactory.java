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
	public Border getButtonBorder();

	/**
	 * <p>getComboBoxBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getComboBoxBorder();

	/**
	 * <p>getDesktopIconBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getDesktopIconBorder();

	/**
	 * <p>getFocusFrameBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getFocusFrameBorder();

	/**
	 * <p>getInternalFrameBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getInternalFrameBorder();

	/**
	 * <p>getMenuBarBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getMenuBarBorder();

	/**
	 * <p>getMenuItemBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getMenuItemBorder();

	/**
	 * <p>getPaletteBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getPaletteBorder();

	/**
	 * <p>getPopupMenuBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getPopupMenuBorder();

	/**
	 * <p>getProgressBarBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getProgressBarBorder();

	/**
	 * <p>getScrollPaneBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getScrollPaneBorder();

	/**
	 * <p>getSpinnerBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getSpinnerBorder();

	/**
	 * <p>getTabbedPaneBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getTabbedPaneBorder();

	/**
	 * <p>getTableHeaderBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getTableHeaderBorder();

	/**
	 * <p>getTableScrollPaneBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getTableScrollPaneBorder();

	/**
	 * <p>getTextBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getTextBorder();

	/**
	 * <p>getTextFieldBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getTextFieldBorder();

	/**
	 * <p>getToggleButtonBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getToggleButtonBorder();

	/**
	 * <p>getToolBarBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getToolBarBorder();

} // end of interface AbstractBorderFactory
