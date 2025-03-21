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

package com.jtattoo.plaf.aero;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;

import com.jtattoo.plaf.AbstractTheme;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>AeroDefaultTheme class.</p>
 */
public class AeroDefaultTheme extends AbstractTheme {

	/**
	 * <p>Constructor for AeroDefaultTheme.</p>
	 */
	public AeroDefaultTheme() {
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
		return "AeroTheme.properties";
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColor() {
		super.setUpColor();
		backgroundColor = new ColorUIResource(236, 236, 236);
		backgroundColorLight = new ColorUIResource(255, 255, 255);
		backgroundColorDark = new ColorUIResource(228, 228, 228);
		alterBackgroundColor = new ColorUIResource(228, 228, 228);
		frameColor = new ColorUIResource(160, 164, 168);

		selectionForegroundColor = BLACK;
		selectionBackgroundColor = new ColorUIResource(176, 196, 222);
		rolloverColor = new ColorUIResource(192, 212, 230);

		pressedBackgroundColor = new ColorUIResource(176, 196, 222);
		pressedBackgroundColorLight = new ColorUIResource(ColorHelper.brighter(pressedBackgroundColor, 20));
		pressedBackgroundColorDark = new ColorUIResource(ColorHelper.darker(pressedBackgroundColor, 5));

		buttonBackgroundColor = new ColorUIResource(220, 220, 220);
		buttonColorLight = new ColorUIResource(240, 240, 240);
		buttonColorDark = new ColorUIResource(120, 120, 120);

		controlBackgroundColor = backgroundColor;
		controlColorLight = new ColorUIResource(150, 176, 211);
		controlColorDark = new ColorUIResource(60, 95, 142);
		controlHighlightColor = WHITE;
		controlShadowColor = new ColorUIResource(180, 186, 190);
		controlDarkShadowColor = frameColor;

		windowTitleForegroundColor = WHITE;
		windowTitleBackgroundColor = new ColorUIResource(176, 196, 222);
		windowTitleColorLight = new ColorUIResource(ColorHelper.brighter(controlColorLight, 20));
		windowTitleColorDark = new ColorUIResource(ColorHelper.brighter(controlColorDark, 20));
		windowBorderColor = controlColorDark;

		windowInactiveTitleBackgroundColor = new ColorUIResource(236, 236, 236);
		windowInactiveTitleColorLight = new ColorUIResource(240, 240, 240);
		windowInactiveTitleColorDark = new ColorUIResource(220, 220, 220);
		windowInactiveBorderColor = new ColorUIResource(210, 210, 210);

		menuBackgroundColor = backgroundColor;
		menuSelectionForegroundColor = selectionForegroundColor;
		menuSelectionBackgroundColor = selectionBackgroundColor;
		menuColorLight = controlColorLight;
		menuColorDark = controlColorDark;

		toolbarBackgroundColor = backgroundColor;
		toolbarColorLight = new ColorUIResource(240, 240, 240);
		toolbarColorDark = new ColorUIResource(200, 200, 200);

		tabAreaBackgroundColor = backgroundColor;
		tabSelectionForegroundColor = WHITE;

		desktopColor = new ColorUIResource(240, 240, 240);
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColorArrs() {
		super.setUpColorArrs();
		Color[] color1 = ColorHelper.createColorArr(controlColorLight, controlColorDark, 6);
		Color[] color2 = ColorHelper.createColorArr(ColorHelper.brighter(controlColorDark, 10), controlColorLight, 15);
		System.arraycopy(color1, 0, DEFAULT_COLORS, 0, 6);
		System.arraycopy(color2, 0, DEFAULT_COLORS, 5, 15);
		for (int i = 0; i < 20; i++) {
			HIDEFAULT_COLORS[i] = ColorHelper.brighter(DEFAULT_COLORS[i], 60);
		}

		ROLLOVER_COLORS = HIDEFAULT_COLORS;
		ACTIVE_COLORS = DEFAULT_COLORS;

		DISABLED_COLORS = ColorHelper.createColorArr(new Color(240, 240, 240), new Color(220, 220, 220), 20);

		color1 = ColorHelper.createColorArr(windowTitleColorLight, windowTitleColorDark, 6);
		color2 = ColorHelper.createColorArr(ColorHelper.brighter(windowTitleColorDark, 10), windowTitleColorLight, 15);
		System.arraycopy(color1, 0, WINDOW_TITLE_COLORS, 0, 6);
		System.arraycopy(color2, 0, WINDOW_TITLE_COLORS, 5, 15);

		MENUBAR_COLORS = ColorHelper.createColorArr(menuColorLight, menuColorDark, 20);
		TOOLBAR_COLORS = ColorHelper.createColorArr(toolbarColorLight, toolbarColorDark, 20);

		BUTTON_COLORS = new Color[] { new Color(247, 247, 247), new Color(243, 243, 243), new Color(240, 240, 240),
				new Color(230, 230, 230), new Color(220, 220, 220), new Color(214, 214, 214), new Color(218, 218, 218),
				new Color(222, 222, 222), new Color(226, 226, 226), new Color(230, 230, 230), new Color(234, 234, 234),
				new Color(237, 237, 237), new Color(240, 240, 240), new Color(242, 242, 242), new Color(244, 244, 244),
				new Color(246, 246, 246), new Color(248, 248, 248), new Color(250, 250, 250), new Color(252, 252, 252),
				new Color(254, 254, 254), new Color(255, 255, 255), };
		CHECKBOX_COLORS = BUTTON_COLORS;

		SELECTED_COLORS = new Color[20];
		for (int i = 0; i < 20; i++) {
			SELECTED_COLORS[i] = ColorHelper.brighter(DEFAULT_COLORS[i], 40);
		}
		TAB_COLORS = BUTTON_COLORS;
		COL_HEADER_COLORS = BUTTON_COLORS;
		THUMB_COLORS = SELECTED_COLORS;
		SLIDER_COLORS = THUMB_COLORS;
		PROGRESSBAR_COLORS = THUMB_COLORS;
		INACTIVE_COLORS = BUTTON_COLORS;

		WINDOW_INACTIVE_TITLE_COLORS = INACTIVE_COLORS;
	}

} // end of class AeroDefaultTheme
