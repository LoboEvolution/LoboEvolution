/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
package com.jtattoo.plaf.fast;

import javax.swing.plaf.ColorUIResource;

import com.jtattoo.plaf.AbstractTheme;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>FastDefaultTheme class.</p>
 *
 * Author Michael Hagen
 *
 */
public class FastDefaultTheme extends AbstractTheme {

	/**
	 * <p>Constructor for FastDefaultTheme.</p>
	 */
	public FastDefaultTheme() {
		super();
		// Setup theme with defaults
		setUpColor();
		// Overwrite defaults with user props
		loadProperties();
		// Setup the color arrays
		setUpColorArrs();
	}

	/** {@inheritDoc} */
	@Override
	public String getPropertyFileName() {
		return "FastTheme.properties";
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColor() {
		super.setUpColor();
		// Defaults for FastLookAndFeel
		backgroundColor = new ColorUIResource(244, 244, 244);
		backgroundColorLight = new ColorUIResource(255, 255, 255);
		backgroundColorDark = new ColorUIResource(232, 232, 232);
		alterBackgroundColor = new ColorUIResource(232, 232, 232);
		selectionBackgroundColor = new ColorUIResource(210, 210, 210);
		frameColor = GRAY;
		focusColor = new ColorUIResource(160, 160, 200);
		focusCellColor = new ColorUIResource(160, 160, 200);
		buttonBackgroundColor = EXTRA_LIGHT_GRAY;
		controlBackgroundColor = EXTRA_LIGHT_GRAY;

		windowTitleBackgroundColor = new ColorUIResource(210, 210, 210);
		windowBorderColor = new ColorUIResource(210, 210, 210);

		windowInactiveTitleBackgroundColor = new ColorUIResource(230, 230, 230);
		windowInactiveBorderColor = new ColorUIResource(230, 230, 230);

		menuBackgroundColor = new ColorUIResource(240, 240, 240);
		menuSelectionBackgroundColor = LIGHT_GRAY;

		toolbarBackgroundColor = new ColorUIResource(240, 240, 240);

		tabAreaBackgroundColor = backgroundColor;
		desktopColor = new ColorUIResource(128, 128, 148);
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColorArrs() {
		super.setUpColorArrs();
		// Generate the color arrays
		setDefaultColors(ColorHelper.createColorArr(controlBackgroundColor, controlBackgroundColor, 2));
		setHidefaultColors(ColorHelper.createColorArr(backgroundColor, backgroundColor, 2));

		setActiveColors(getDefaultColors());
		setInactiveColors(getHiDefaultColors());

		setRolloverColors(ColorHelper.createColorArr(SUPER_LIGHT_GRAY, SUPER_LIGHT_GRAY, 2));
		setSelectedColors(ColorHelper.createColorArr(backgroundColor, backgroundColor, 2));
		setDisabledColors(ColorHelper.createColorArr(controlColorLight, controlColorLight, 2));

		setButtonColors(ColorHelper.createColorArr(buttonBackgroundColor, buttonBackgroundColor, 2));
		setColHeaderColors(ColorHelper.createColorArr(SUPER_LIGHT_GRAY, SUPER_LIGHT_GRAY, 2));
		setCheckboxColors(ColorHelper.createColorArr(SUPER_LIGHT_GRAY, SUPER_LIGHT_GRAY, 2));

		setTabColors(getDefaultColors());
	}

} // end of class FastDefaultTheme
