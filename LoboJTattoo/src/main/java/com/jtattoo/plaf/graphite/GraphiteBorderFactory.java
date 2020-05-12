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
package com.jtattoo.plaf.graphite;

import javax.swing.border.Border;

import com.jtattoo.plaf.AbstractBorderFactory;
import com.jtattoo.plaf.BaseBorders;

/**
 * <p>GraphiteBorderFactory class.</p>
 *
 * @author Michael Hagen
 * @version $Id: $Id
 */
public class GraphiteBorderFactory implements AbstractBorderFactory {

	private static GraphiteBorderFactory instance = null;

	/**
	 * <p>Getter for the field instance.</p>
	 *
	 * @return a {@link com.jtattoo.plaf.graphite.GraphiteBorderFactory} object.
	 */
	public static synchronized GraphiteBorderFactory getInstance() {
		if (instance == null) {
			instance = new GraphiteBorderFactory();
		}
		return instance;
	}

	private GraphiteBorderFactory() {
	}

	/** {@inheritDoc} */
	@Override
	public Border getButtonBorder() {
		return GraphiteBorders.getButtonBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getComboBoxBorder() {
		return BaseBorders.getComboBoxBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getDesktopIconBorder() {
		return BaseBorders.getDesktopIconBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getFocusFrameBorder() {
		return BaseBorders.getFocusFrameBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getInternalFrameBorder() {
		return GraphiteBorders.getInternalFrameBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getMenuBarBorder() {
		return BaseBorders.getMenuBarBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getMenuItemBorder() {
		return GraphiteBorders.getMenuItemBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getPaletteBorder() {
		return BaseBorders.getPaletteBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getPopupMenuBorder() {
		return GraphiteBorders.getPopupMenuBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getProgressBarBorder() {
		return BaseBorders.getProgressBarBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getScrollPaneBorder() {
		return BaseBorders.getScrollPaneBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getSpinnerBorder() {
		return BaseBorders.getSpinnerBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getTabbedPaneBorder() {
		return BaseBorders.getTabbedPaneBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getTableHeaderBorder() {
		return BaseBorders.getTableHeaderBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getTableScrollPaneBorder() {
		return BaseBorders.getTableScrollPaneBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getTextBorder() {
		return BaseBorders.getTextBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getTextFieldBorder() {
		return BaseBorders.getTextFieldBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getToggleButtonBorder() {
		return GraphiteBorders.getToggleButtonBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getToolBarBorder() {
		return BaseBorders.getToolBarBorder();
	}

} // end of class GraphiteBorderFactory
