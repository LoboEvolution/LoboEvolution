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

package com.jtattoo.plaf.acryl;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;

import com.jtattoo.plaf.AbstractTheme;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>AcrylDefaultTheme class.</p>
 */
public class AcrylDefaultTheme extends AbstractTheme {

	/**
	 * <p>Constructor for AcrylDefaultTheme.</p>
	 */
	public AcrylDefaultTheme() {
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
		return "AcrylTheme.properties";
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColor() {
		super.setUpColor();
		// Defaults for AcrylLookAndFeel
		menuOpaque = false;
		menuAlpha = 0.90f;

		backgroundColor = new ColorUIResource(244, 244, 244);
		backgroundColorLight = new ColorUIResource(255, 255, 255);
		backgroundColorDark = new ColorUIResource(232, 232, 232);
		alterBackgroundColor = new ColorUIResource(232, 232, 232);

		selectionForegroundColor = WHITE;
		selectionBackgroundColor = EXTRA_DARK_GRAY;
		frameColor = new ColorUIResource(32, 32, 32);
		focusCellColor = focusColor;

		buttonBackgroundColor = EXTRA_LIGHT_GRAY;
		buttonColorLight = new ColorUIResource(244, 244, 244);
		buttonColorDark = new ColorUIResource(220, 220, 220);

		rolloverColor = new ColorUIResource(152, 191, 231);
		rolloverColorLight = new ColorUIResource(188, 252, 255);
		rolloverColorDark = new ColorUIResource(61, 134, 209);

		pressedBackgroundColor = new ColorUIResource(210, 210, 210);
		pressedBackgroundColorLight = new ColorUIResource(ColorHelper.brighter(pressedBackgroundColor, 28));
		pressedBackgroundColorDark = new ColorUIResource(ColorHelper.darker(pressedBackgroundColor, 4));

		controlForegroundColor = BLACK;
		controlBackgroundColor = backgroundColor;
		controlColorLight = new ColorUIResource(96, 98, 100);
		controlColorDark = new ColorUIResource(64, 65, 66);// new ColorUIResource(48, 49, 50);

		controlShadowColor = GRAY;
		controlDarkShadowColor = DARK_GRAY;

		windowTitleForegroundColor = WHITE;
		windowTitleBackgroundColor = controlColorLight;
		windowTitleColorLight = controlColorLight;
		windowTitleColorDark = controlColorDark;
		windowBorderColor = new ColorUIResource(0, 0, 0);

		windowInactiveTitleForegroundColor = new ColorUIResource(ColorHelper.brighter(windowTitleForegroundColor, 10));
		windowInactiveTitleBackgroundColor = new ColorUIResource(244, 244, 244); // new
																					// ColorUIResource(ColorHelper.brighter(windowTitleBackgroundColor,
																					// 10));
		windowInactiveTitleColorLight = new ColorUIResource(ColorHelper.brighter(windowTitleColorLight, 10));
		windowInactiveTitleColorDark = new ColorUIResource(ColorHelper.brighter(windowTitleColorDark, 10));
		windowInactiveBorderColor = new ColorUIResource(ColorHelper.brighter(windowBorderColor, 10));

		menuBackgroundColor = backgroundColor;
		menuSelectionForegroundColor = WHITE;
		menuSelectionBackgroundColor = EXTRA_DARK_GRAY;
		menuColorLight = WHITE;
		menuColorDark = backgroundColor;

		toolbarBackgroundColor = backgroundColor;
		toolbarColorLight = menuColorLight;
		toolbarColorDark = menuColorDark;

		tabAreaBackgroundColor = backgroundColor;
		tabSelectionForegroundColor = selectionForegroundColor;

		desktopColor = backgroundColor;
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColorArrs() {
		super.setUpColorArrs();

		// Generate the color arrays
		Color topHi = ColorHelper.brighter(controlColorLight, 10);
		Color topLo = ColorHelper.brighter(controlColorLight, 20);// ColorHelper.brighter(controlColorLight, 30);
		Color bottomHi = controlColorDark;
		Color bottomLo = controlColorLight;

		Color[] topColors = ColorHelper.createColorArr(topHi, topLo, 10);
		Color[] bottomColors = ColorHelper.createColorArr(bottomHi, bottomLo, 10);
		DEFAULT_COLORS = new Color[20];
		for (int i = 0; i < 10; i++) {
			DEFAULT_COLORS[i] = topColors[i];
			DEFAULT_COLORS[i + 10] = bottomColors[i];
		}

		ACTIVE_COLORS = DEFAULT_COLORS;
		if (controlColorLight.equals(new ColorUIResource(96, 98, 100))) {
			ROLLOVER_COLORS = new Color[]{new Color(194, 207, 233), new Color(185, 201, 231),
					new Color(176, 195, 228), new Color(168, 189, 226), new Color(158, 182, 223),
					new Color(148, 176, 220), new Color(138, 169, 217), new Color(132, 169, 217),
					new Color(124, 169, 218), new Color(116, 167, 218), new Color(104, 160, 218),
					new Color(86, 150, 214), new Color(64, 136, 210), new Color(72, 144, 214), new Color(79, 150, 219),
					new Color(89, 157, 224), new Color(100, 165, 230), new Color(110, 172, 235),
					new Color(120, 180, 240), new Color(127, 186, 247), new Color(134, 193, 254),
					new Color(142, 202, 254), new Color(151, 211, 255), new Color(158, 218, 255),
					new Color(166, 226, 255), new Color(177, 239, 255), new Color(188, 252, 255),};
		} else {
			topHi = ColorHelper.brighter(rolloverColorLight, 20);
			topLo = ColorHelper.brighter(rolloverColorLight, 30);
			bottomHi = rolloverColorDark;
			bottomLo = rolloverColorLight;
			topColors = ColorHelper.createColorArr(topHi, topLo, 10);
			bottomColors = ColorHelper.createColorArr(bottomHi, bottomLo, 10);
			ROLLOVER_COLORS = new Color[20];
			for (int i = 0; i < 10; i++) {
				ROLLOVER_COLORS[i] = topColors[i];
				ROLLOVER_COLORS[i + 10] = bottomColors[i];
			}
		}

		SELECTED_COLORS = ColorHelper.createColorArr(new Color(200, 200, 200), new Color(240, 240, 240), 20);
		DISABLED_COLORS = ColorHelper.createColorArr(Color.white, new Color(230, 230, 230), 20);

		topHi = windowTitleColorLight;
		topLo = ColorHelper.brighter(windowTitleColorLight, 20);
		bottomHi = windowTitleColorDark;
		bottomLo = windowTitleColorLight;
		topColors = ColorHelper.createColorArr(topHi, topLo, 10);
		bottomColors = ColorHelper.createColorArr(bottomHi, bottomLo, 10);
		WINDOW_TITLE_COLORS = new Color[20];
		for (int i = 0; i < 10; i++) {
			WINDOW_TITLE_COLORS[i] = topColors[i];
			WINDOW_TITLE_COLORS[i + 10] = bottomColors[i];
		}

		topHi = windowInactiveTitleColorLight;
		topLo = ColorHelper.brighter(windowInactiveTitleColorLight, 20);
		bottomHi = windowInactiveTitleColorDark;
		bottomLo = windowInactiveTitleColorLight;
		topColors = ColorHelper.createColorArr(topHi, topLo, 10);
		bottomColors = ColorHelper.createColorArr(bottomHi, bottomLo, 10);
		WINDOW_INACTIVE_TITLE_COLORS = new Color[20];
		for (int i = 0; i < 10; i++) {
			WINDOW_INACTIVE_TITLE_COLORS[i] = ColorHelper.brighter(topColors[i], 10);
			WINDOW_INACTIVE_TITLE_COLORS[i + 10] = ColorHelper.brighter(bottomColors[i], 10);
		}

		MENUBAR_COLORS = ColorHelper.createColorArr(menuColorLight, menuColorDark, 20);
		TOOLBAR_COLORS = ColorHelper.createColorArr(toolbarColorLight, toolbarColorDark, 20);

		topHi = ColorHelper.brighter(buttonColorLight, 20);
		topLo = ColorHelper.brighter(buttonColorLight, 80);
		bottomHi = buttonColorDark;
		bottomLo = buttonColorLight;
		topColors = ColorHelper.createColorArr(topHi, topLo, 10);
		bottomColors = ColorHelper.createColorArr(bottomHi, bottomLo, 10);
		BUTTON_COLORS = new Color[20];
		for (int i = 0; i < 10; i++) {
			BUTTON_COLORS[i] = topColors[i];
			BUTTON_COLORS[i + 10] = bottomColors[i];
		}
		CHECKBOX_COLORS = DEFAULT_COLORS;
		INACTIVE_COLORS = BUTTON_COLORS;
		TAB_COLORS = BUTTON_COLORS;
		COL_HEADER_COLORS = BUTTON_COLORS;

		TRACK_COLORS = ColorHelper.createColorArr(backgroundColor, Color.white, 16);
		THUMB_COLORS = DEFAULT_COLORS;
		SLIDER_COLORS = DEFAULT_COLORS;
		PROGRESSBAR_COLORS = DEFAULT_COLORS;
	}

} // end of class AcrylDefaultTheme
