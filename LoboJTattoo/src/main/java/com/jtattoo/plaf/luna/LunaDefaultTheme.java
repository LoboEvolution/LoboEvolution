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
package com.jtattoo.plaf.luna;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;

import com.jtattoo.plaf.AbstractTheme;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>LunaDefaultTheme class.</p>
 *
 *
 *
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
		DEFAULT_COLORS = ColorHelper.createColorArr(controlColorLight, controlColorDark, 20);
		HIDEFAULT_COLORS = ColorHelper.createColorArr(ColorHelper.brighter(controlColorLight, 90),
				ColorHelper.brighter(controlColorDark, 30), 20);

		ACTIVE_COLORS = DEFAULT_COLORS;
		INACTIVE_COLORS = ColorHelper.createColorArr(new Color(248, 247, 241), backgroundColor, 20);

		ROLLOVER_COLORS = ColorHelper.createColorArr(ColorHelper.brighter(controlColorLight, 30),
				ColorHelper.brighter(controlColorDark, 20), 30);
		SELECTED_COLORS = DEFAULT_COLORS;
		DISABLED_COLORS = ColorHelper.createColorArr(Color.white, Color.lightGray, 20);

		// Generate the color arrays
		final Color topHi = windowTitleColorLight;
		final Color topLo = ColorHelper.darker(windowTitleColorLight, 10);
		final Color bottomHi = ColorHelper.brighter(windowTitleColorDark, 15);
		final Color bottomLo = windowTitleColorDark;

		WINDOW_TITLE_COLORS = new Color[20];
		final Color[] topColors = ColorHelper.createColorArr(topHi, topLo, 8);
		System.arraycopy(topColors, 0, WINDOW_TITLE_COLORS, 0, 8);
		final Color[] bottomColors = ColorHelper.createColorArr(bottomHi, bottomLo, 12);
		System.arraycopy(bottomColors, 0, WINDOW_TITLE_COLORS, 8, 12);

		WINDOW_INACTIVE_TITLE_COLORS = new Color[WINDOW_TITLE_COLORS.length];
		for (int i = 0; i < WINDOW_INACTIVE_TITLE_COLORS.length; i++) {
			WINDOW_INACTIVE_TITLE_COLORS[i] = ColorHelper.brighter(WINDOW_TITLE_COLORS[i], 20);
		}

		MENUBAR_COLORS = ColorHelper.createColorArr(menuColorLight, menuColorDark, 20);
		TOOLBAR_COLORS = ColorHelper.createColorArr(toolbarColorLight, toolbarColorDark, 20);

		BUTTON_COLORS = new Color[] { new Color(255, 255, 255), new Color(254, 254, 254), new Color(252, 252, 251),
				new Color(251, 251, 249), new Color(250, 250, 248), new Color(249, 249, 246), new Color(248, 248, 244),
				new Color(247, 247, 243), new Color(246, 246, 242), new Color(245, 245, 240), new Color(244, 244, 239),
				new Color(243, 243, 238), new Color(242, 242, 236), new Color(241, 241, 235), new Color(240, 240, 234),
				new Color(236, 235, 230), new Color(226, 223, 214), new Color(214, 208, 197), };
		TAB_COLORS = ColorHelper.createColorArr(Color.white, new Color(236, 235, 230), 20);
		COL_HEADER_COLORS = TAB_COLORS;
		CHECKBOX_COLORS = TAB_COLORS;
		TRACK_COLORS = ColorHelper.createColorArr(new Color(243, 241, 236), new Color(254, 254, 251), 20);
		THUMB_COLORS = ColorHelper.createColorArr(new Color(218, 230, 254), new Color(180, 197, 240), 20);
		// SLIDER_COLORS = ColorHelper.createColorArr(new Color(218, 230, 254), new
		// Color(180, 197, 240), 20);
		SLIDER_COLORS = THUMB_COLORS;// ColorHelper.createColorArr(new Color(243, 241, 236), new Color(254, 254,
										// 251), 20);
		PROGRESSBAR_COLORS = THUMB_COLORS;
	}

} // end of class LunaDefaultTheme
