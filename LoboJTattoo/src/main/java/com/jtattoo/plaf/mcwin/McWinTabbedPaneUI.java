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
package com.jtattoo.plaf.mcwin;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseTabbedPaneUI;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>McWinTabbedPaneUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class McWinTabbedPaneUI extends BaseTabbedPaneUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new McWinTabbedPaneUI();
	}

	private Color[] sepColors = null;

	private Color[] altSepColors = null;

	/** {@inheritDoc} */
	@Override
	protected Color[] getContentBorderColors(int tabPlacement) {
		Color controlColorLight = AbstractLookAndFeel.getTheme().getControlColorLight();
		if (!controlColorLight.equals(new ColorUIResource(106, 150, 192))) {
			controlColorLight = ColorHelper.brighter(controlColorLight, 6);
			Color controlColorDark = AbstractLookAndFeel.getTheme().getControlColorDark();
			if (sepColors == null) {
				sepColors = new Color[5];
				sepColors[0] = controlColorDark;
				sepColors[1] = controlColorLight;
				sepColors[2] = controlColorLight;
				sepColors[3] = controlColorLight;
				sepColors[4] = controlColorDark;
			}
			return sepColors;
		} else {
			if (tabPlacement == TOP || tabPlacement == LEFT) {
				if (sepColors == null) {
					int len = AbstractLookAndFeel.getTheme().getDefaultColors().length;
					sepColors = new Color[5];
					sepColors[0] = AbstractLookAndFeel.getTheme().getDefaultColors()[0];
					sepColors[1] = AbstractLookAndFeel.getTheme().getDefaultColors()[len - 6];
					sepColors[2] = AbstractLookAndFeel.getTheme().getDefaultColors()[2];
					sepColors[3] = AbstractLookAndFeel.getTheme().getDefaultColors()[1];
					sepColors[4] = AbstractLookAndFeel.getTheme().getDefaultColors()[0];
				}
				return sepColors;
			} else {
				if (altSepColors == null) {
					altSepColors = new Color[5];
					altSepColors[0] = AbstractLookAndFeel.getTheme().getDefaultColors()[9];
					altSepColors[1] = AbstractLookAndFeel.getTheme().getDefaultColors()[8];
					altSepColors[2] = AbstractLookAndFeel.getTheme().getDefaultColors()[7];
					altSepColors[3] = AbstractLookAndFeel.getTheme().getDefaultColors()[6];
					altSepColors[4] = AbstractLookAndFeel.getTheme().getDefaultColors()[0];
				}
				return altSepColors;
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void installDefaults() {
		super.installDefaults();
		tabAreaInsets.bottom = 5;
	}

} // end of class McWinTabbedPaneUI
