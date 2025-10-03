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
package com.jtattoo.plaf.hifi;

import java.awt.Color;
import java.awt.Font;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

import com.jtattoo.plaf.AbstractTheme;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>HiFiDefaultTheme class.</p>
 *
 * Author Michael Hagen
 *
 */
public class HiFiDefaultTheme extends AbstractTheme {

	/**
	 * <p>Constructor for HiFiDefaultTheme.</p>
	 */
	public HiFiDefaultTheme() {
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
		return "HiFiTheme.properties";
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColor() {
		super.setUpColor();

		// Defaults for HiFiLookAndFeel
		textShadow = true;
		foregroundColor = EXTRA_LIGHT_GRAY;
		disabledForegroundColor = GRAY;
		disabledBackgroundColor = new ColorUIResource(48, 48, 48);

		backgroundColor = new ColorUIResource(48, 48, 48);
		backgroundColorLight = new ColorUIResource(48, 48, 48);
		backgroundColorDark = new ColorUIResource(16, 16, 16);
		alterBackgroundColor = new ColorUIResource(64, 64, 64);
		selectionForegroundColor = WHITE;
		selectionBackgroundColor = new ColorUIResource(40, 40, 40);
		frameColor = BLACK;
		gridColor = BLACK;
		focusCellColor = ORANGE;

		inputBackgroundColor = new ColorUIResource(80, 80, 80);
		inputForegroundColor = foregroundColor;

		rolloverForegroundColor = EXTRA_LIGHT_GRAY;
		rolloverColor = new ColorUIResource(112, 112, 112);
		rolloverColorLight = new ColorUIResource(128, 128, 128);
		rolloverColorDark = new ColorUIResource(96, 96, 96);

		pressedForegroundColor = foregroundColor;
		pressedBackgroundColor = new ColorUIResource(24, 24, 24);
		pressedBackgroundColorLight = new ColorUIResource(ColorHelper.brighter(pressedBackgroundColor, 4));
		pressedBackgroundColorDark = new ColorUIResource(ColorHelper.darker(pressedBackgroundColor, 40));

		buttonForegroundColor = foregroundColor;
		buttonBackgroundColor = new ColorUIResource(96, 96, 96);
		buttonColorLight = new ColorUIResource(96, 96, 96);
		buttonColorDark = new ColorUIResource(32, 32, 32);

		controlForegroundColor = foregroundColor;
		controlBackgroundColor = new ColorUIResource(64, 64, 64); // netbeans use this for selected tab in the toolbar
		controlColorLight = new ColorUIResource(96, 96, 96);
		controlColorDark = new ColorUIResource(32, 32, 32);
		controlHighlightColor = new ColorUIResource(96, 96, 96);
		controlShadowColor = new ColorUIResource(32, 32, 32);
		controlDarkShadowColor = BLACK;

		windowTitleForegroundColor = foregroundColor;
		windowTitleBackgroundColor = new ColorUIResource(32, 32, 32);
		windowTitleColorLight = new ColorUIResource(96, 96, 96);
		windowTitleColorDark = new ColorUIResource(32, 32, 32);// new ColorUIResource(16, 16, 16);
		windowBorderColor = BLACK;
		windowIconColor = LIGHT_GRAY;
		windowIconShadowColor = BLACK;
		windowIconRolloverColor = ORANGE;

		windowInactiveTitleForegroundColor = new ColorUIResource(196, 196, 196);
		windowInactiveTitleBackgroundColor = new ColorUIResource(64, 64, 64);
		windowInactiveTitleColorLight = new ColorUIResource(64, 64, 64);
		windowInactiveTitleColorDark = new ColorUIResource(32, 32, 32);
		windowInactiveBorderColor = BLACK;

		menuForegroundColor = foregroundColor;
		menuBackgroundColor = new ColorUIResource(32, 32, 32);
		menuSelectionForegroundColor = WHITE;
		menuSelectionBackgroundColor = new ColorUIResource(96, 96, 96);
		menuColorLight = new ColorUIResource(96, 96, 96);
		menuColorDark = new ColorUIResource(32, 32, 32);

		toolbarBackgroundColor = new ColorUIResource(48, 48, 48);
		toolbarColorLight = new ColorUIResource(96, 96, 96);
		toolbarColorDark = new ColorUIResource(32, 32, 32);

		tabAreaBackgroundColor = backgroundColor;
		tabSelectionForegroundColor = selectionForegroundColor;

		desktopColor = new ColorUIResource(64, 64, 64);

		tooltipForegroundColor = WHITE;
		tooltipBackgroundColor = new ColorUIResource(24, 24, 24);

		controlFont = new FontUIResource("Dialog", Font.BOLD, 12);
		systemFont = new FontUIResource("Dialog", Font.BOLD, 12);
		userFont = new FontUIResource("Dialog", Font.BOLD, 12);
		menuFont = new FontUIResource("Dialog", Font.BOLD, 12);
		windowTitleFont = new FontUIResource("Dialog", Font.BOLD, 12);
		smallFont = new FontUIResource("Dialog", Font.PLAIN, 10);
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColorArrs() {
		super.setUpColorArrs();
		setDefaultColors(ColorHelper.createColorArr(controlColorLight, controlColorDark, 20));
		setHidefaultColors(ColorHelper.createColorArr(ColorHelper.brighter(controlColorLight, 15),
                ColorHelper.brighter(controlColorDark, 15), 20));
		setActiveColors(getDefaultColors());
		setInactiveColors(ColorHelper.createColorArr(new Color(64, 64, 64), new Color(32, 32, 32), 20));
		setSelectedColors(getHiDefaultColors());
		setButtonColors(ColorHelper.createColorArr(buttonColorLight, buttonColorDark, 20));
		setRolloverColors(getHiDefaultColors());
		setDisabledColors(ColorHelper.createColorArr(ColorHelper.brighter(controlColorDark, 5),
				ColorHelper.darker(controlColorDark, 10), 20));

		setWindowTitleColors(ColorHelper.createColorArr(windowTitleColorLight, windowTitleColorDark, 20));
		setWindowInactiveTitleColors(ColorHelper.createColorArr(windowInactiveTitleColorLight,
                windowInactiveTitleColorDark, 20));
		setMenubarColors(getDefaultColors());
		setToolbarColors(getMenubarColors());
		setTrackColors(ColorHelper.createColorArr(ColorHelper.darker(backgroundColor, 10),
                ColorHelper.brighter(backgroundColor, 5), 20));
		setSliderColors(getDefaultColors());
		setProgressbarColors(getDefaultColors());
		setThumbColors(getDefaultColors());
		setTabColors(getDefaultColors());
		setColHeaderColors(getDefaultColors());
	}

} // end of class HiFiDefaultTheme
