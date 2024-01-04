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
package com.jtattoo.plaf.bernstein;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;

import com.jtattoo.plaf.AbstractTheme;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>BernsteinDefaultTheme class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BernsteinDefaultTheme extends AbstractTheme {

	/**
	 * <p>Constructor for BernsteinDefaultTheme.</p>
	 */
	public BernsteinDefaultTheme() {
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
		return "BernsteinTheme.properties";
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColor() {
		super.setUpColor();
		// Defaults for BernsteinLookAndFeel
		menuOpaque = false;
		menuAlpha = 0.85f;

		backgroundColor = new ColorUIResource(253, 249, 204);
		backgroundColorLight = WHITE;
		backgroundColorDark = new ColorUIResource(253, 241, 176);
		alterBackgroundColor = new ColorUIResource(253, 241, 176);

		selectionBackgroundColor = new ColorUIResource(250, 216, 32);
		disabledForegroundColor = new ColorUIResource(164, 164, 164);
		frameColor = new ColorUIResource(233, 199, 5);
		focusCellColor = new ColorUIResource(139, 92, 0);
		rolloverColor = new ColorUIResource(251, 220, 64);

		buttonBackgroundColor = new ColorUIResource(253, 249, 204);

		pressedBackgroundColor = buttonBackgroundColor;
		pressedBackgroundColorLight = new ColorUIResource(ColorHelper.brighter(pressedBackgroundColor, 20));
		pressedBackgroundColorDark = new ColorUIResource(ColorHelper.darker(pressedBackgroundColor, 5));

		controlBackgroundColor = new ColorUIResource(253, 249, 204);
		controlHighlightColor = WHITE;
		controlShadowColor = new ColorUIResource(247, 231, 34);
		controlDarkShadowColor = frameColor;
		controlColorLight = new ColorUIResource(252, 218, 0);
		controlColorDark = new ColorUIResource(183, 142, 0);

		windowTitleBackgroundColor = selectionBackgroundColor;
		windowTitleColorLight = new ColorUIResource(253, 249, 204);
		windowTitleColorDark = new ColorUIResource(251, 241, 153);
		windowBorderColor = new ColorUIResource(254, 240, 0);

		windowInactiveTitleBackgroundColor = backgroundColor;
		windowInactiveTitleColorLight = WHITE;
		windowInactiveTitleColorDark = new ColorUIResource(236, 236, 236);
		windowInactiveBorderColor = new ColorUIResource(254, 240, 0);

		menuBackgroundColor = backgroundColor;
		menuSelectionBackgroundColor = selectionBackgroundColor;

		toolbarBackgroundColor = backgroundColor;

		tabAreaBackgroundColor = backgroundColor;
		desktopColor = new ColorUIResource(253, 249, 204);

		tooltipForegroundColor = BLACK;
		tooltipBackgroundColor = new ColorUIResource(254, 240, 80);
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColorArrs() {
		super.setUpColorArrs();
		// Generate the color arrays
		DEFAULT_COLORS = new Color[] { new Color(247, 225, 0), new Color(251, 232, 0), new Color(243, 216, 0),
				new Color(237, 204, 0), new Color(239, 209, 0), new Color(242, 215, 0), new Color(243, 216, 0),
				new Color(245, 221, 0), new Color(246, 222, 0), new Color(247, 225, 0), new Color(248, 227, 0),
				new Color(249, 230, 0), new Color(251, 232, 0), new Color(252, 235, 0), new Color(253, 237, 0),
				new Color(253, 237, 0), new Color(254, 240, 0), };
		HIDEFAULT_COLORS = new Color[DEFAULT_COLORS.length];
		for (int i = 0; i < DEFAULT_COLORS.length; i++) {
			HIDEFAULT_COLORS[i] = ColorHelper.brighter(DEFAULT_COLORS[i], 50.0);
		}

		ACTIVE_COLORS = DEFAULT_COLORS;
		INACTIVE_COLORS = HIDEFAULT_COLORS;
		SELECTED_COLORS = DEFAULT_COLORS;
//        PRESSED_COLORS = new Color[DEFAULT_COLORS.length];
//        for (int i = 0; i < DEFAULT_COLORS.length; i++) {
//            PRESSED_COLORS[i] = backgroundColor;
//        }

		ROLLOVER_COLORS = new Color[DEFAULT_COLORS.length];
		for (int i = 0; i < DEFAULT_COLORS.length; i++) {
			ROLLOVER_COLORS[i] = ColorHelper.brighter(DEFAULT_COLORS[i], 70.0);
		}

		DISABLED_COLORS = new Color[HIDEFAULT_COLORS.length];
		for (int i = 0; i < HIDEFAULT_COLORS.length; i++) {
			DISABLED_COLORS[i] = ColorHelper.brighter(HIDEFAULT_COLORS[i], 40.0);
		}

		WINDOW_TITLE_COLORS = ColorHelper.createColorArr(windowTitleColorLight, windowTitleColorDark, 20);
		WINDOW_INACTIVE_TITLE_COLORS = ColorHelper.createColorArr(windowInactiveTitleColorLight,
				windowInactiveTitleColorDark, 20);
		MENUBAR_COLORS = ColorHelper.createColorArr(menuColorLight, menuColorDark, 20);
		TOOLBAR_COLORS = MENUBAR_COLORS;

		BUTTON_COLORS = HIDEFAULT_COLORS;
		TAB_COLORS = BUTTON_COLORS;
		COL_HEADER_COLORS = HIDEFAULT_COLORS;
		THUMB_COLORS = HIDEFAULT_COLORS;
		TRACK_COLORS = ColorHelper.createColorArr(new Color(255, 245, 200), Color.white, 20);
		SLIDER_COLORS = DEFAULT_COLORS;
		PROGRESSBAR_COLORS = DEFAULT_COLORS;
	}

} // end of class BernsteinDefaultTheme
