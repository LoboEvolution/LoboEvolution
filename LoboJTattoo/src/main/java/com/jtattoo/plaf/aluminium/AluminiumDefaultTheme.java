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
package com.jtattoo.plaf.aluminium;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;

import com.jtattoo.plaf.AbstractTheme;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>AluminiumDefaultTheme class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AluminiumDefaultTheme extends AbstractTheme {

	/**
	 * <p>Constructor for AluminiumDefaultTheme.</p>
	 */
	public AluminiumDefaultTheme() {
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
		return "AluminiumTheme.properties";
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColor() {
		super.setUpColor();
		// Defaults for AluminiumLookAndFeel
		backgroundColor = new ColorUIResource(200, 200, 200);
		backgroundColorLight = new ColorUIResource(240, 240, 240);
		backgroundColorDark = new ColorUIResource(200, 200, 200);
		alterBackgroundColor = new ColorUIResource(220, 220, 220);

		frameColor = new ColorUIResource(140, 140, 140);
		backgroundPattern = true;
		selectionForegroundColor = BLACK;
		selectionBackgroundColor = new ColorUIResource(224, 227, 206);

		pressedBackgroundColor = selectionBackgroundColor;
		pressedBackgroundColorLight = new ColorUIResource(ColorHelper.brighter(pressedBackgroundColor, 20));
		pressedBackgroundColorDark = new ColorUIResource(ColorHelper.darker(pressedBackgroundColor, 5));

		focusColor = new ColorUIResource(255, 128, 96);
		focusCellColor = focusColor;

		rolloverColor = new ColorUIResource(196, 203, 163);
		rolloverColorLight = new ColorUIResource(220, 224, 201);
		rolloverColorDark = new ColorUIResource(196, 203, 163);

		buttonBackgroundColor = EXTRA_LIGHT_GRAY;
		buttonColorLight = WHITE;
		buttonColorDark = new ColorUIResource(210, 212, 214);

		controlBackgroundColor = EXTRA_LIGHT_GRAY;
		controlColorLight = new ColorUIResource(244, 244, 244);
		controlColorDark = new ColorUIResource(224, 224, 224);
		controlHighlightColor = new ColorUIResource(240, 240, 240);
		controlShadowColor = new ColorUIResource(180, 180, 180);

		windowTitleForegroundColor = new ColorUIResource(32, 32, 32);
		windowTitleBackgroundColor = new ColorUIResource(200, 200, 200);
		windowTitleColorLight = new ColorUIResource(200, 200, 200);
		windowTitleColorDark = new ColorUIResource(160, 160, 160);
		windowBorderColor = new ColorUIResource(120, 120, 120);
		windowIconColor = new ColorUIResource(32, 32, 32);
		windowIconShadowColor = new ColorUIResource(208, 208, 208);
		windowIconRolloverColor = new ColorUIResource(196, 0, 0);

		windowInactiveTitleForegroundColor = BLACK;
		windowInactiveTitleBackgroundColor = new ColorUIResource(220, 220, 220);
		windowInactiveTitleColorLight = new ColorUIResource(220, 220, 220);
		windowInactiveTitleColorDark = new ColorUIResource(200, 200, 200);
		windowInactiveBorderColor = new ColorUIResource(140, 140, 140);

		menuBackgroundColor = EXTRA_LIGHT_GRAY;
		menuSelectionForegroundColor = selectionForegroundColor;
		menuSelectionBackgroundColor = new ColorUIResource(202, 208, 172);

		menuColorLight = new ColorUIResource(210, 210, 210);// controlColorLight;
		menuColorDark = new ColorUIResource(200, 200, 200);// controlColorDark;

		toolbarBackgroundColor = backgroundColor;
		toolbarColorLight = new ColorUIResource(240, 240, 240);
		toolbarColorDark = new ColorUIResource(200, 200, 200);

		tabAreaBackgroundColor = backgroundColor;
		desktopColor = backgroundColor;

		tooltipForegroundColor = BLACK;
		tooltipBackgroundColor = new ColorUIResource(ColorHelper.brighter(selectionBackgroundColor, 40));
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColorArrs() {
		super.setUpColorArrs();
		DEFAULT_COLORS = ColorHelper.createColorArr(controlColorLight, controlColorDark, 20);
		HIDEFAULT_COLORS = new Color[DEFAULT_COLORS.length];
		for (int i = 0; i < DEFAULT_COLORS.length; i++) {
			HIDEFAULT_COLORS[i] = ColorHelper.brighter(DEFAULT_COLORS[i], 20);
		}

		ACTIVE_COLORS = DEFAULT_COLORS;
		INACTIVE_COLORS = ColorHelper.createColorArr(new Color(240, 240, 240), new Color(220, 220, 220), 20);

		// PRESSED_COLORS =
		// ColorHelper.createColorArr(ColorHelper.darker(selectionBackgroundColor, 5),
		// ColorHelper.brighter(selectionBackgroundColor, 20), 20);
		DISABLED_COLORS = ColorHelper.createColorArr(Color.white, Color.lightGray, 20);
		BUTTON_COLORS = new Color[] { new Color(240, 240, 240), new Color(235, 235, 235), new Color(232, 232, 232),
				new Color(230, 230, 230), new Color(228, 228, 228), new Color(225, 225, 225), new Color(220, 220, 220),
				new Color(215, 215, 215), new Color(210, 210, 210), new Color(205, 205, 205), new Color(210, 210, 210),
				new Color(215, 215, 215), new Color(220, 220, 220), new Color(225, 225, 225), new Color(228, 228, 228),
				new Color(230, 230, 230), new Color(232, 232, 232), new Color(235, 235, 235), };
		ROLLOVER_COLORS = ColorHelper.createColorArr(rolloverColorLight, rolloverColorDark, 20);
		WINDOW_TITLE_COLORS = ColorHelper.createColorArr(windowTitleColorLight, windowTitleColorDark, 20);
		WINDOW_INACTIVE_TITLE_COLORS = ColorHelper.createColorArr(windowInactiveTitleColorLight,
				windowInactiveTitleColorDark, 20);
		MENUBAR_COLORS = ColorHelper.createColorArr(menuColorLight, menuColorDark, 20);
		TOOLBAR_COLORS = ColorHelper.createColorArr(toolbarColorLight, toolbarColorDark, 20);
		TAB_COLORS = DEFAULT_COLORS;
		COL_HEADER_COLORS = BUTTON_COLORS;

		SELECTED_COLORS = ColorHelper.createColorArr(ColorHelper.brighter(selectionBackgroundColor, 40),
				selectionBackgroundColor, 20);

		TRACK_COLORS = ColorHelper.createColorArr(new Color(210, 210, 210), new Color(230, 230, 230), 20);
		// THUMB_COLORS = ColorHelper.createColorArr(new Color(202, 208, 172), new
		// Color(180, 188, 137), 20);
		THUMB_COLORS = ColorHelper.createColorArr(new Color(200, 200, 200), new Color(170, 170, 170), 20);
		// SLIDER_COLORS = DEFAULT_COLORS;
		SLIDER_COLORS = ColorHelper.createColorArr(new Color(180, 180, 180), new Color(150, 150, 150), 10);
		PROGRESSBAR_COLORS = SLIDER_COLORS;
	}

} // end of class AluminiumDefaultTheme
