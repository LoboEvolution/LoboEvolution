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
package com.jtattoo.plaf.mcwin;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;

import com.jtattoo.plaf.AbstractTheme;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>McWinDefaultTheme class.</p>
 *
 * Author Michael Hagen
 *
 */
public class McWinDefaultTheme extends AbstractTheme {

	/**
	 * <p>Constructor for McWinDefaultTheme.</p>
	 */
	public McWinDefaultTheme() {
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
		return "McWinTheme.properties";
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColor() {
		super.setUpColor();

		// Defaults for McWinLookAndFeel
		menuOpaque = false;
		menuAlpha = 0.85f;

		backgroundColor = SUPER_LIGHT_GRAY;
		backgroundColorLight = WHITE;
		backgroundColorDark = new ColorUIResource(240, 240, 240);
		alterBackgroundColor = new ColorUIResource(240, 240, 240);

		selectionBackgroundColor = new ColorUIResource(212, 224, 243);

		frameColor = new ColorUIResource(140, 144, 148);
		focusCellColor = ORANGE;

		buttonBackgroundColor = SUPER_LIGHT_GRAY;

		controlBackgroundColor = SUPER_LIGHT_GRAY;
		controlColorLight = new ColorUIResource(106, 150, 192);
		controlColorDark = LIGHT_GRAY;

		rolloverColor = new ColorUIResource(164, 217, 190);
		rolloverColorLight = new ColorUIResource(182, 224, 203);
		rolloverColorDark = new ColorUIResource(106, 192, 150);

		pressedBackgroundColor = new ColorUIResource(220, 220, 220);
		pressedBackgroundColorLight = new ColorUIResource(ColorHelper.brighter(pressedBackgroundColor, 20));
		pressedBackgroundColorDark = new ColorUIResource(ColorHelper.darker(pressedBackgroundColor, 4));

		windowTitleForegroundColor = new ColorUIResource(22, 34, 44);
		windowTitleBackgroundColor = new ColorUIResource(212, 224, 243);
		windowTitleColorLight = new ColorUIResource(231, 235, 248);
		windowTitleColorDark = new ColorUIResource(193, 211, 236);
		windowBorderColor = new ColorUIResource(154, 168, 182);

		windowInactiveTitleForegroundColor = EXTRA_DARK_GRAY;
		windowInactiveTitleBackgroundColor = backgroundColor;
		windowInactiveTitleColorLight = WHITE;
		windowInactiveTitleColorDark = new ColorUIResource(236, 236, 236);
		windowInactiveBorderColor = LIGHT_GRAY;

		menuBackgroundColor = backgroundColor;
		menuColorLight = WHITE;
		menuColorDark = backgroundColor;
		menuSelectionBackgroundColor = selectionBackgroundColor;

		toolbarBackgroundColor = backgroundColor;

		tabAreaBackgroundColor = backgroundColor;
		tabSelectionForegroundColor = BLACK;
		desktopColor = new ColorUIResource(232, 232, 232);
	}

	/** {@inheritDoc} */
	@Override
	public void setUpColorArrs() {
		super.setUpColorArrs();

		if (controlColorLight.equals(new ColorUIResource(106, 150, 192))) {
			// Generate the color arrays
			setDefaultColors(new Color[]{new Color(106, 150, 192), new Color(154, 190, 209), new Color(182, 208, 231),
                    new Color(200, 223, 255), new Color(189, 218, 246), new Color(167, 204, 231),
                    new Color(148, 191, 226), new Color(144, 181, 225), new Color(145, 182, 226),
                    new Color(151, 188, 230), new Color(160, 198, 235), new Color(168, 206, 242),
                    new Color(174, 213, 244), new Color(183, 222, 251), new Color(191, 230, 255),
                    new Color(202, 237, 255), new Color(206, 247, 253), new Color(211, 255, 254),
                    new Color(208, 255, 254), new Color(206, 249, 255), new Color(202, 237, 255),});
		} else {
			final Color[] color1 = ColorHelper.createColorArr(controlColorLight, controlColorDark, 6);
			color1[0] = controlColorDark;
			final Color[] color2 = ColorHelper.createColorArr(ColorHelper.brighter(controlColorDark, 10), controlColorLight,
					15);
			System.arraycopy(color1, 0, getDefaultColors(), 0, 6);
            System.arraycopy(color2, 0, getDefaultColors(), 5, 15);
		}
		if (rolloverColorDark.equals(new ColorUIResource(106, 192, 150))) {
			setRolloverColors(new Color[]{new Color(106, 192, 150), new Color(154, 209, 190),
                    new Color(173, 220, 198), new Color(182, 232, 203), new Color(180, 234, 207),
                    new Color(167, 231, 204), new Color(148, 226, 191), new Color(144, 225, 181),
                    new Color(145, 226, 182), new Color(151, 230, 188), new Color(160, 235, 198),
                    new Color(168, 242, 206), new Color(174, 244, 213), new Color(183, 251, 222),
                    new Color(191, 255, 230), new Color(202, 255, 237), new Color(206, 253, 247),
                    new Color(211, 254, 255), new Color(208, 254, 255), new Color(206, 249, 255),
                    new Color(196, 247, 227),});
		} else {
			final Color[] color1 = ColorHelper.createColorArr(rolloverColorLight, rolloverColorDark, 6);
			color1[0] = rolloverColorDark;
			final Color[] color2 = ColorHelper.createColorArr(ColorHelper.brighter(rolloverColorDark, 10), rolloverColorLight,
					15);
			System.arraycopy(color1, 0, getRolloverColors(), 0, 6);
            System.arraycopy(color2, 0, getRolloverColors(), 5, 15);
		}

		setHidefaultColors(new Color[]{new Color(250, 250, 250), new Color(250, 250, 250), new Color(240, 240, 240),
                new Color(230, 230, 230), new Color(220, 220, 220), new Color(214, 214, 214), new Color(218, 218, 218),
                new Color(222, 222, 222), new Color(226, 226, 226), new Color(230, 230, 230), new Color(234, 234, 234),
                new Color(237, 237, 237), new Color(240, 240, 240), new Color(242, 242, 242), new Color(244, 244, 244),
                new Color(246, 246, 246), new Color(248, 248, 248), new Color(250, 250, 250), new Color(252, 252, 252),
                new Color(254, 254, 254), new Color(255, 255, 255),});

		setActiveColors(getDefaultColors());
		setInactiveColors(getDisabledColors());
		setSelectedColors(getDefaultColors());

		setDisabledColors(new Color[getDisabledColors().length]);
		for (int i = 0; i < getDisabledColors().length; i++) {
			getDisabledColors()[i] = ColorHelper.brighter(getDisabledColors()[i], 40.0);
		}

		setWindowTitleColors(ColorHelper.createColorArr(windowTitleColorLight, windowTitleColorDark, 20));
		setWindowInactiveTitleColors(ColorHelper.createColorArr(windowInactiveTitleColorLight,
                windowInactiveTitleColorDark, 20));
		setMenubarColors(ColorHelper.createColorArr(menuColorLight, menuColorDark, 20));
		setToolbarColors(getMenubarColors());

		setButtonColors(getDisabledColors());
		setCheckboxColors(getButtonColors());
		setTabColors(getButtonColors());
		if (isBrightMode()) {
			setColHeaderColors(getDisabledColors());
		} else {
			setColHeaderColors(getDefaultColors());
		}
		if (isBrightMode()) {
			if (controlColorLight.equals(new ColorUIResource(106, 150, 192))) {
				setThumbColors(getHiDefaultColors());
			} else {
				final Color cHi = ColorHelper.brighter(controlColorLight, 10);
				final Color cLo = ColorHelper.brighter(controlColorDark, 10);
				final Color[] color1 = ColorHelper.createColorArr(cHi, cLo, 6);
				color1[0] = cLo;
				final Color[] color2 = ColorHelper.createColorArr(ColorHelper.brighter(cLo, 10), cHi, 15);
				System.arraycopy(color1, 0, getThumbColors(), 0, 6);
                System.arraycopy(color2, 0, getThumbColors(), 5, 15);

			}
		} else {
			setThumbColors(getDefaultColors());
		}
		setTrackColors(ColorHelper.createColorArr(new Color(220, 220, 220), Color.white, 20));
		setSliderColors(getDefaultColors());
		setProgressbarColors(getDefaultColors());
	}

} // end of class McWinDefaultTheme
