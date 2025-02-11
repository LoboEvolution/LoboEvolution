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
package com.jtattoo.plaf.mint;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;

import com.jtattoo.plaf.AbstractTheme;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>MintDefaultTheme class.</p>
 *
 *
 *
 */
public class MintDefaultTheme extends AbstractTheme {

	/**
	 * <p>Constructor for MintDefaultTheme.</p>
	 */
	public MintDefaultTheme() {
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
		return "MintTheme.properties";
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColor() {
		super.setUpColor();

		// Defaults for MintLookAndFeel
		backgroundColor = new ColorUIResource(236, 242, 242);
		backgroundColorLight = new ColorUIResource(236, 242, 242);
		backgroundColorDark = new ColorUIResource(220, 228, 228);
		alterBackgroundColor = new ColorUIResource(220, 228, 228);
		selectionBackgroundColor = new ColorUIResource(220, 228, 228);
		selectionForegroundColor = BLACK;
		frameColor = new ColorUIResource(140, 140, 140);
		focusCellColor = focusColor;

		rolloverColor = new ColorUIResource(255, 230, 170);
		rolloverColorLight = new ColorUIResource(255, 230, 170);
		rolloverColorDark = new ColorUIResource(255, 191, 43);

		pressedBackgroundColor = new ColorUIResource(192, 211, 211);
		pressedBackgroundColorLight = new ColorUIResource(ColorHelper.brighter(pressedBackgroundColor, 20));
		pressedBackgroundColorDark = new ColorUIResource(ColorHelper.darker(pressedBackgroundColor, 4));

		buttonBackgroundColor = new ColorUIResource(244, 248, 246);
		buttonColorLight = WHITE;
		buttonColorDark = new ColorUIResource(220, 228, 228);

		controlBackgroundColor = backgroundColor; // netbeans use this for selected tab in the toolbar
		controlColorLight = new ColorUIResource(223, 234, 234);
		controlColorDark = new ColorUIResource(180, 203, 203);
		controlHighlightColor = WHITE;
		controlShadowColor = new ColorUIResource(172, 186, 186);
		controlDarkShadowColor = new ColorUIResource(160, 164, 164);

		windowTitleForegroundColor = BLACK;
		windowTitleBackgroundColor = new ColorUIResource(180, 203, 203);// new ColorUIResource(180, 203, 203);
		windowTitleColorLight = new ColorUIResource(223, 234, 234);
		windowTitleColorDark = new ColorUIResource(180, 203, 203);
		windowBorderColor = new ColorUIResource(154, 186, 186);

		windowInactiveTitleBackgroundColor = new ColorUIResource(220, 228, 228);// new ColorUIResource(220, 220, 220);
		windowInactiveTitleColorLight = backgroundColor;
		windowInactiveTitleColorDark = new ColorUIResource(220, 228, 228);
		windowInactiveBorderColor = new ColorUIResource(192, 211, 211);

		menuBackgroundColor = WHITE;// backgroundColor;
		menuSelectionBackgroundColor = selectionBackgroundColor;
		menuSelectionForegroundColor = selectionForegroundColor;
		menuColorLight = backgroundColor;
		menuColorDark = new ColorUIResource(220, 228, 228);

		toolbarBackgroundColor = backgroundColor;
		toolbarColorLight = menuColorLight;
		toolbarColorDark = menuColorDark;

		tabAreaBackgroundColor = backgroundColor;
		desktopColor = new ColorUIResource(220, 228, 228);
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColorArrs() {
		super.setUpColorArrs();

		// Generate the color arrays
		DEFAULT_COLORS = ColorHelper.createColorArr(controlColorLight, controlColorDark, 20);
		HIDEFAULT_COLORS = ColorHelper.createColorArr(ColorHelper.brighter(controlColorLight, 40),
				ColorHelper.brighter(controlColorDark, 40), 20);
		ACTIVE_COLORS = DEFAULT_COLORS;
		ROLLOVER_COLORS = ColorHelper.createColorArr(rolloverColorLight, rolloverColorDark, 20);
		SELECTED_COLORS = DEFAULT_COLORS;
		DISABLED_COLORS = ColorHelper.createColorArr(new Color(248, 248, 248), new Color(224, 224, 224), 20);
		WINDOW_TITLE_COLORS = ColorHelper.createColorArr(windowTitleColorLight, windowTitleColorDark, 20);
		WINDOW_INACTIVE_TITLE_COLORS = ColorHelper.createColorArr(windowInactiveTitleColorLight,
				windowInactiveTitleColorDark, 20);
		MENUBAR_COLORS = ColorHelper.createColorArr(menuColorLight, menuColorDark, 20);
		TOOLBAR_COLORS = ColorHelper.createColorArr(toolbarColorLight, toolbarColorDark, 20);
		BUTTON_COLORS = new Color[] { new Color(255, 255, 255), new Color(254, 255, 254), new Color(253, 255, 254),
				new Color(252, 255, 254), new Color(251, 254, 253), new Color(250, 253, 252), new Color(250, 253, 252),
				new Color(248, 250, 249), new Color(244, 248, 246), new Color(240, 245, 243), new Color(238, 242, 240),
				new Color(232, 237, 235), new Color(232, 237, 235), new Color(224, 230, 227), new Color(214, 221, 217),
				new Color(204, 212, 208), new Color(160, 164, 162), };
		TAB_COLORS = ColorHelper.createColorArr(buttonColorLight, buttonColorDark, 20);
		CHECKBOX_COLORS = TAB_COLORS;
		COL_HEADER_COLORS = ColorHelper.createColorArr(buttonColorLight, buttonColorDark, 20);
		TRACK_COLORS = ColorHelper.createColorArr(new Color(238, 238, 238), Color.white, 20);
		THUMB_COLORS = DEFAULT_COLORS;
		SLIDER_COLORS = DEFAULT_COLORS;
		PROGRESSBAR_COLORS = DEFAULT_COLORS;
		INACTIVE_COLORS = ColorHelper.createColorArr(buttonColorLight, buttonColorDark, 20);
	}

} // end of class MintDefaultTheme
