/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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
package com.jtattoo.plaf.graphite;

import java.awt.Color;
import java.awt.Font;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

import com.jtattoo.plaf.AbstractTheme;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>GraphiteDefaultTheme class.</p>
 *
 *
 *
 */
public class GraphiteDefaultTheme extends AbstractTheme {

	/**
	 * <p>Constructor for GraphiteDefaultTheme.</p>
	 */
	public GraphiteDefaultTheme() {
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
		return "GraphiteTheme.properties";
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColor() {
		super.setUpColor();

//        showFocusFrame = true;
		foregroundColor = BLACK;
		backgroundColor = new ColorUIResource(224, 224, 224);
		backgroundColorLight = WHITE;
		backgroundColorDark = LIGHT_GRAY;
		alterBackgroundColor = new ColorUIResource(220, 220, 220);
		disabledForegroundColor = new ColorUIResource(128, 128, 128);
		disabledBackgroundColor = new ColorUIResource(240, 240, 240);
		inputBackgroundColor = WHITE;
		inputForegroundColor = BLACK;

		selectionForegroundColor = WHITE;
		selectionBackgroundColor = new ColorUIResource(80, 80, 80);
		selectionBackgroundColorLight = new ColorUIResource(96, 96, 96);
		selectionBackgroundColorDark = new ColorUIResource(64, 64, 64);

		focusColor = ORANGE;
		focusCellColor = ORANGE;
		frameColor = new ColorUIResource(144, 144, 144);
		gridColor = new ColorUIResource(200, 200, 200);

		rolloverColor = ORANGE;
		rolloverColorLight = new ColorUIResource(255, 213, 113);
		rolloverColorDark = new ColorUIResource(240, 168, 0);

		pressedBackgroundColor = new ColorUIResource(230, 230, 230);
		pressedBackgroundColorLight = new ColorUIResource(240, 240, 240);
		pressedBackgroundColorDark = new ColorUIResource(220, 220, 220);

		buttonForegroundColor = BLACK;
		buttonBackgroundColor = EXTRA_LIGHT_GRAY;
		buttonColorLight = WHITE;
		buttonColorDark = EXTRA_LIGHT_GRAY;

		controlForegroundColor = BLACK;
		controlBackgroundColor = EXTRA_LIGHT_GRAY;
		controlShadowColor = new ColorUIResource(164, 164, 164);
		controlDarkShadowColor = new ColorUIResource(64, 64, 64);
		controlColorLight = new ColorUIResource(96, 96, 96);
		controlColorDark = new ColorUIResource(40, 40, 40);

		windowTitleForegroundColor = WHITE;
		windowTitleBackgroundColor = new ColorUIResource(88, 88, 88);
		windowTitleColorLight = new ColorUIResource(88, 88, 88);
		windowTitleColorDark = new ColorUIResource(24, 24, 24);
		windowBorderColor = BLACK;
		windowIconColor = WHITE;
		windowIconShadowColor = BLACK;
		windowIconRolloverColor = ORANGE;

		windowInactiveTitleForegroundColor = WHITE;
		windowInactiveTitleBackgroundColor = new ColorUIResource(224, 224, 224);
		windowInactiveTitleColorLight = new ColorUIResource(64, 64, 64);
		windowInactiveTitleColorDark = new ColorUIResource(0, 0, 0);
		windowInactiveBorderColor = BLACK;

		menuForegroundColor = BLACK;
		menuBackgroundColor = new ColorUIResource(240, 240, 240);
		menuSelectionForegroundColor = BLACK;
		menuSelectionBackgroundColor = new ColorUIResource(32, 32, 32);
		menuSelectionBackgroundColorLight = new ColorUIResource(255, 213, 113);
		menuSelectionBackgroundColorDark = new ColorUIResource(240, 168, 0);
		menuColorLight = new ColorUIResource(240, 240, 240);
		menuColorDark = new ColorUIResource(220, 220, 220);

		toolbarForegroundColor = BLACK;
		toolbarBackgroundColor = backgroundColor;
		toolbarColorLight = menuColorLight;
		toolbarColorDark = menuColorDark;

		tabAreaBackgroundColor = backgroundColor;
		tabSelectionForegroundColor = WHITE;
		desktopColor = backgroundColor;
		tooltipForegroundColor = BLACK;
		tooltipBackgroundColor = YELLOW;

		controlFont = new FontUIResource("Dialog", Font.PLAIN, 13);
		systemFont = new FontUIResource("Dialog", Font.PLAIN, 13);
		userFont = new FontUIResource("Dialog", Font.PLAIN, 13);
		smallFont = new FontUIResource("Dialog", Font.PLAIN, 12);
		menuFont = new FontUIResource("Dialog", Font.PLAIN, 13);
		windowTitleFont = new FontUIResource("Dialog", Font.BOLD, 13);
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColorArrs() {
		super.setUpColorArrs();
		BUTTON_COLORS = ColorHelper.createColorArr(buttonColorLight, buttonColorDark, 24);
		CHECKBOX_COLORS = BUTTON_COLORS;
		DEFAULT_COLORS = ColorHelper.createColorArr(controlColorLight, controlColorDark, 24);
		HIDEFAULT_COLORS = new Color[20];
		for (int i = 0; i < 20; i++) {
			HIDEFAULT_COLORS[i] = ColorHelper.brighter(DEFAULT_COLORS[i], 40);
		}
		SELECTED_COLORS = DEFAULT_COLORS;
		SELECTION_COLORS = ColorHelper.createColorArr(selectionBackgroundColorLight, selectionBackgroundColorDark, 20);
		MENU_SELECTION_COLORS = ColorHelper.createColorArr(menuSelectionBackgroundColorLight,
				menuSelectionBackgroundColorDark, 20);
		ROLLOVER_COLORS = ColorHelper.createColorArr(rolloverColorLight, rolloverColorDark, 24);
		// PRESSED_COLORS = ColorHelper.createColorArr(menuColorDark, menuColorLight,
		// 24);
		DISABLED_COLORS = ColorHelper.createColorArr(SUPER_LIGHT_GRAY, EXTRA_LIGHT_GRAY, 24);
		ACTIVE_COLORS = DEFAULT_COLORS;
		INACTIVE_COLORS = BUTTON_COLORS;

		WINDOW_TITLE_COLORS = ColorHelper.createColorArr(windowTitleColorLight, windowTitleColorDark, 24);
		WINDOW_INACTIVE_TITLE_COLORS = ColorHelper.createColorArr(windowInactiveTitleColorLight,
				windowInactiveTitleColorDark, 24);

		TOOLBAR_COLORS = ColorHelper.createColorArr(toolbarColorLight, toolbarColorDark, 24);
		MENUBAR_COLORS = ColorHelper.createColorArr(menuColorLight, menuColorDark, 24);

		TAB_COLORS = BUTTON_COLORS;
		COL_HEADER_COLORS = BUTTON_COLORS;
		Color hiColor = backgroundColorLight;
		Color loColor = ColorHelper.darker(backgroundColor, 4);
		TRACK_COLORS = ColorHelper.createColorArr(loColor, hiColor, 24);
		if ("true".equals(System.getProperty("graphiteBrightThumb"))) {
			hiColor = backgroundColorDark;
			loColor = ColorHelper.darker(backgroundColorDark, 10);
			THUMB_COLORS = ColorHelper.createColorArr(hiColor, loColor, 24);
		} else {
			THUMB_COLORS = DEFAULT_COLORS;
		}

		SLIDER_COLORS = DEFAULT_COLORS;
		PROGRESSBAR_COLORS = DEFAULT_COLORS;
	}

} // end of class GraphiteDefaultTheme
