/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package com.jtattoo.plaf.hifi;

import javax.swing.border.Border;

import com.jtattoo.plaf.AbstractBorderFactory;
import com.jtattoo.plaf.BaseBorders;

/**
 * <p>HiFiBorderFactory class.</p>
 *
 * Author Michael Hagen
 *
 */
public final class HiFiBorderFactory implements AbstractBorderFactory {

	private static HiFiBorderFactory instance = null;

	/**
	 * <p>Getter for the field instance.</p>
	 *
	 * @return a {@link com.jtattoo.plaf.hifi.HiFiBorderFactory} object.
	 */
	public static synchronized HiFiBorderFactory getInstance() {
		if (instance == null) {
			instance = new HiFiBorderFactory();
		}
		return instance;
	}

	private HiFiBorderFactory() {
	}

	/** {@inheritDoc} */
	@Override
	public Border getButtonBorder() {
		return HiFiBorders.getButtonBorder();
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
		return HiFiBorders.getInternalFrameBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getMenuBarBorder() {
		return BaseBorders.getMenuBarBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getMenuItemBorder() {
		return BaseBorders.getMenuItemBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getPaletteBorder() {
		return BaseBorders.getPaletteBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getPopupMenuBorder() {
		return BaseBorders.getPopupMenuBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getProgressBarBorder() {
		return BaseBorders.getProgressBarBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getScrollPaneBorder() {
		return HiFiBorders.getScrollPaneBorder();
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
		return HiFiBorders.getTableScrollPaneBorder();
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
		return HiFiBorders.getToggleButtonBorder();
	}

	/** {@inheritDoc} */
	@Override
	public Border getToolBarBorder() {
		return HiFiBorders.getToolBarBorder();
	}

} // end of class HiFiBorderFactory
