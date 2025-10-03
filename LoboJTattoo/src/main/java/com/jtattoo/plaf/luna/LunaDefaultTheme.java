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
package com.jtattoo.plaf.luna;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;

import com.jtattoo.plaf.AbstractTheme;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>LunaDefaultTheme class.</p>
 */
public class LunaDefaultTheme extends AbstractTheme {

	/**
	 * <p>Constructor for LunaDefaultTheme.</p>
	 */
	public LunaDefaultTheme() {
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
		return "LunaTheme.properties";
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColor() {
		super.setUpColor();

		// Defaults for AbstractLookAndFeel
		backgroundColor = new ColorUIResource(240, 238, 225);
		backgroundColorLight = new ColorUIResource(255, 255, 255);
		backgroundColorDark = new ColorUIResource(232, 228, 208);
		alterBackgroundColor = new ColorUIResource(232, 228, 208);

		selectionForegroundColor = BLACK;
		selectionBackgroundColor = new ColorUIResource(194, 208, 243);// new ColorUIResource(200, 210, 240);

		frameColor = new ColorUIResource(0, 88, 168); // new ColorUIResource(0, 60, 116);
		focusCellColor = new ColorUIResource(0, 60, 116);

		buttonBackgroundColor = new ColorUIResource(236, 233, 216);
		buttonColorLight = WHITE;
		buttonColorDark = new ColorUIResource(214, 208, 197);

		rolloverColor = LIGHT_ORANGE;

		pressedBackgroundColor = new ColorUIResource(232, 228, 208);
		pressedBackgroundColorLight = new ColorUIResource(ColorHelper.brighter(pressedBackgroundColor, 20));
		pressedBackgroundColorDark = new ColorUIResource(ColorHelper.darker(pressedBackgroundColor, 4));

		controlForegroundColor = BLACK;
		controlBackgroundColor = new ColorUIResource(236, 233, 216);
		controlColorLight = WHITE;
		controlColorDark = new ColorUIResource(214, 208, 197);

		windowTitleForegroundColor = WHITE;
		windowTitleBackgroundColor = new ColorUIResource(2, 100, 247);
		windowTitleColorLight = new ColorUIResource(139, 185, 254);
		windowTitleColorDark = new ColorUIResource(2, 80, 196);
		windowBorderColor = new ColorUIResource(2, 80, 196);

		windowInactiveTitleForegroundColor = WHITE;
		windowInactiveTitleBackgroundColor = new ColorUIResource(39, 106, 204);
		windowInactiveTitleColorLight = new ColorUIResource(141, 186, 253);
		windowInactiveTitleColorDark = new ColorUIResource(39, 106, 204);
		windowInactiveBorderColor = new ColorUIResource(39, 106, 204);

		menuBackgroundColor = backgroundColor;
		menuSelectionForegroundColor = WHITE;
		menuSelectionBackgroundColor = new ColorUIResource(49, 106, 197);
		menuColorLight = new ColorUIResource(248, 247, 241);
		menuColorDark = backgroundColor;

		toolbarBackgroundColor = backgroundColor;
		toolbarColorLight = menuColorLight;
		toolbarColorDark = backgroundColor;

		tabAreaBackgroundColor = backgroundColor;
		desktopColor = backgroundColor;
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColorArrs() {
		super.setUpColorArrs();

		// Generate the color arrays
		setDefaultColors(ColorHelper.createColorArr(controlColorLight, controlColorDark, 20));
		setHidefaultColors(ColorHelper.createColorArr(ColorHelper.brighter(controlColorLight, 90),
                ColorHelper.brighter(controlColorDark, 30), 20));

		setActiveColors(getDefaultColors());
		setInactiveColors(ColorHelper.createColorArr(new Color(248, 247, 241), backgroundColor, 20));

		setRolloverColors(ColorHelper.createColorArr(ColorHelper.brighter(controlColorLight, 30),
                ColorHelper.brighter(controlColorDark, 20), 30));
		setSelectedColors(getDefaultColors());
		setDisabledColors(ColorHelper.createColorArr(Color.white, Color.lightGray, 20));

		// Generate the color arrays
		final Color topHi = windowTitleColorLight;
		final Color topLo = ColorHelper.darker(windowTitleColorLight, 10);
		final Color bottomHi = ColorHelper.brighter(windowTitleColorDark, 15);
		final Color bottomLo = windowTitleColorDark;

		setWindowTitleColors(new Color[20]);
		final Color[] topColors = ColorHelper.createColorArr(topHi, topLo, 8);
		System.arraycopy(topColors, 0, getWindowTitleColors(), 0, 8);
		final Color[] bottomColors = ColorHelper.createColorArr(bottomHi, bottomLo, 12);
		System.arraycopy(bottomColors, 0, getWindowTitleColors(), 8, 12);

		setWindowInactiveTitleColors(new Color[getWindowTitleColors().length]);
		for (int i = 0; i < getWindowInactiveTitleColors().length; i++) {
			getWindowInactiveTitleColors()[i] = ColorHelper.brighter(getWindowTitleColors()[i], 20);
		}

		setMenubarColors(ColorHelper.createColorArr(menuColorLight, menuColorDark, 20));
		setToolbarColors(ColorHelper.createColorArr(toolbarColorLight, toolbarColorDark, 20));

		setButtonColors(new Color[]{new Color(255, 255, 255), new Color(254, 254, 254), new Color(252, 252, 251),
                new Color(251, 251, 249), new Color(250, 250, 248), new Color(249, 249, 246), new Color(248, 248, 244),
                new Color(247, 247, 243), new Color(246, 246, 242), new Color(245, 245, 240), new Color(244, 244, 239),
                new Color(243, 243, 238), new Color(242, 242, 236), new Color(241, 241, 235), new Color(240, 240, 234),
                new Color(236, 235, 230), new Color(226, 223, 214), new Color(214, 208, 197),});
		setTabColors(ColorHelper.createColorArr(Color.white, new Color(236, 235, 230), 20));
		setColHeaderColors(getTabColors());
		setCheckboxColors(getTabColors());
		setTrackColors(ColorHelper.createColorArr(new Color(243, 241, 236), new Color(254, 254, 251), 20));
		setThumbColors(ColorHelper.createColorArr(new Color(218, 230, 254), new Color(180, 197, 240), 20));
		setSliderColors(getThumbColors());
		setProgressbarColors(getThumbColors());
	}

} // end of class LunaDefaultTheme
