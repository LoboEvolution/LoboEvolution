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

import javax.swing.border.Border;

import com.jtattoo.plaf.AbstractBorderFactory;
import com.jtattoo.plaf.BaseBorders;

/**
 * @author Michael Hagen
 */
public class LunaBorderFactory implements AbstractBorderFactory {

	private static LunaBorderFactory instance = null;

	public static synchronized LunaBorderFactory getInstance() {
		if (instance == null) {
			instance = new LunaBorderFactory();
		}
		return instance;
	}

	private LunaBorderFactory() {
	}

	@Override
	public Border getButtonBorder() {
		return LunaBorders.getButtonBorder();
	}

	@Override
	public Border getComboBoxBorder() {
		return LunaBorders.getComboBoxBorder();
	}

	@Override
	public Border getDesktopIconBorder() {
		return BaseBorders.getDesktopIconBorder();
	}

	@Override
	public Border getFocusFrameBorder() {
		return BaseBorders.getFocusFrameBorder();
	}

	@Override
	public Border getInternalFrameBorder() {
		return LunaBorders.getInternalFrameBorder();
	}

	@Override
	public Border getMenuBarBorder() {
		return BaseBorders.getMenuBarBorder();
	}

	@Override
	public Border getMenuItemBorder() {
		return BaseBorders.getMenuItemBorder();
	}

	@Override
	public Border getPaletteBorder() {
		return BaseBorders.getPaletteBorder();
	}

	@Override
	public Border getPopupMenuBorder() {
		return BaseBorders.getPopupMenuBorder();
	}

	@Override
	public Border getProgressBarBorder() {
		return BaseBorders.getProgressBarBorder();
	}

	@Override
	public Border getScrollPaneBorder() {
		return LunaBorders.getScrollPaneBorder();
	}

	@Override
	public Border getSpinnerBorder() {
		return BaseBorders.getSpinnerBorder();
	}

	@Override
	public Border getTabbedPaneBorder() {
		return BaseBorders.getTabbedPaneBorder();
	}

	@Override
	public Border getTableHeaderBorder() {
		return LunaBorders.getTableHeaderBorder();
	}

	@Override
	public Border getTableScrollPaneBorder() {
		return LunaBorders.getTableScrollPaneBorder();
	}

	@Override
	public Border getTextBorder() {
		return LunaBorders.getTextBorder();
	}

	@Override
	public Border getTextFieldBorder() {
		return LunaBorders.getTextFieldBorder();
	}

	@Override
	public Border getToggleButtonBorder() {
		return LunaBorders.getToggleButtonBorder();
	}

	@Override
	public Border getToolBarBorder() {
		return BaseBorders.getToolBarBorder();
	}

} // end of class LunaBorderFactory
