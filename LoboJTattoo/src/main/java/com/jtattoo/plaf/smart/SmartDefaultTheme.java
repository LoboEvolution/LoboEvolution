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
package com.jtattoo.plaf.smart;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;

import com.jtattoo.plaf.AbstractTheme;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>SmartDefaultTheme class.</p>
 *
 *
 *
 */
public class SmartDefaultTheme extends AbstractTheme {

	/**
	 * <p>Constructor for SmartDefaultTheme.</p>
	 */
	public SmartDefaultTheme() {
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
		return "SmartTheme.properties";
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColor() {
		super.setUpColor();

		// Defaults for SmartLookAndFeel
		backgroundColor = new ColorUIResource(244, 242, 232);
		backgroundColorLight = new ColorUIResource(255, 255, 255);
		backgroundColorDark = new ColorUIResource(236, 233, 215);
		alterBackgroundColor = new ColorUIResource(236, 233, 215);

		selectionBackgroundColor = new ColorUIResource(201, 218, 254);
		selectionBackgroundColorLight = new ColorUIResource(201, 218, 254);
		selectionBackgroundColorDark = new ColorUIResource(201, 218, 254);
		frameColor = new ColorUIResource(128, 124, 112);
		focusCellColor = new ColorUIResource(255, 230, 120);

		buttonBackgroundColor = new ColorUIResource(218, 230, 254);
		buttonColorLight = WHITE;
		buttonColorDark = backgroundColor;

		rolloverColor = new ColorUIResource(218, 230, 254);
		rolloverColorLight = new ColorUIResource(236, 242, 255);
		rolloverColorDark = new ColorUIResource(191, 211, 253);

		pressedBackgroundColor = new ColorUIResource(180, 197, 240);
		pressedBackgroundColorLight = new ColorUIResource(218, 230, 254);
		pressedBackgroundColorDark = new ColorUIResource(180, 197, 240);

		controlForegroundColor = BLACK;
		controlBackgroundColor = backgroundColor;
		controlColorLight = new ColorUIResource(218, 230, 254);
		controlColorDark = new ColorUIResource(180, 197, 240);
		controlShadowColor = new ColorUIResource(164, 164, 164);
		controlDarkShadowColor = new ColorUIResource(148, 148, 148);

		windowTitleBackgroundColor = new ColorUIResource(180, 197, 240);
		windowTitleColorLight = new ColorUIResource(218, 230, 254);
		windowTitleColorDark = new ColorUIResource(180, 197, 240);
		windowBorderColor = new ColorUIResource(128, 129, 132);

		windowInactiveTitleBackgroundColor = backgroundColor;
		windowInactiveTitleColorLight = new ColorUIResource(244, 242, 234);
		windowInactiveTitleColorDark = new ColorUIResource(230, 224, 202);
		windowInactiveBorderColor = new ColorUIResource(164, 165, 169);

		menuBackgroundColor = new ColorUIResource(248, 247, 239);
		menuSelectionForegroundColor = BLACK;
		menuSelectionBackgroundColor = selectionBackgroundColor;
		menuColorLight = new ColorUIResource(248, 245, 235);
		menuColorDark = backgroundColor;

		toolbarBackgroundColor = backgroundColor;
		toolbarColorLight = menuColorLight;
		toolbarColorDark = menuColorDark;

		tabAreaBackgroundColor = backgroundColor;
		desktopColor = backgroundColor;
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColorArrs() {
		super.setUpColorArrs();

		// Generate the color arrays
		DEFAULT_COLORS = ColorHelper.createColorArr(controlColorLight, controlColorDark, 20);
		HIDEFAULT_COLORS = ColorHelper.createColorArr(ColorHelper.brighter(controlColorLight, 90),
				ColorHelper.brighter(controlColorDark, 30), 20);

		ACTIVE_COLORS = DEFAULT_COLORS;
		INACTIVE_COLORS = ColorHelper.createColorArr(Color.white, backgroundColor, 20);

		ROLLOVER_COLORS = ColorHelper.createColorArr(rolloverColorLight, rolloverColorDark, 20);
		SELECTED_COLORS = DEFAULT_COLORS;
		DISABLED_COLORS = ColorHelper.createColorArr(Color.white, new Color(230, 230, 230), 20);

		WINDOW_TITLE_COLORS = ColorHelper.createColorArr(windowTitleColorLight, windowTitleColorDark, 20);
		WINDOW_INACTIVE_TITLE_COLORS = ColorHelper.createColorArr(windowInactiveTitleColorLight,
				windowInactiveTitleColorDark, 20);
		MENUBAR_COLORS = ColorHelper.createColorArr(menuColorLight, menuColorDark, 20);
		TOOLBAR_COLORS = ColorHelper.createColorArr(toolbarColorLight, toolbarColorDark, 20);

		BUTTON_COLORS = ColorHelper.createColorArr(buttonColorLight, buttonColorDark, 20);
		TAB_COLORS = ColorHelper.createColorArr(Color.white, backgroundColor, 20);
		CHECKBOX_COLORS = TAB_COLORS;
		COL_HEADER_COLORS = MENUBAR_COLORS;
		TRACK_COLORS = ColorHelper.createColorArr(backgroundColor, Color.white, 16);
		THUMB_COLORS = DEFAULT_COLORS;
		SLIDER_COLORS = DEFAULT_COLORS;
		PROGRESSBAR_COLORS = DEFAULT_COLORS;
	}

} // end of class SmartDefaultTheme
