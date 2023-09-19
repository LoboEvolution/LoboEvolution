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
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseBorders;
import com.jtattoo.plaf.BaseMenuBarUI;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>McWinMenuBarUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class McWinMenuBarUI extends BaseMenuBarUI {

	private static final Color[] SHADOW_COLORS = ColorHelper.createColorArr(Color.white, new Color(240, 240, 240), 8);

	/** {@inheritDoc} */
	public static ComponentUI createUI(JComponent x) {
		return new McWinMenuBarUI();
	}

	/** {@inheritDoc} */
	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		if (c != null && c instanceof JMenuBar) {
			c.setBorder(BaseBorders.getMenuBarBorder());
			((JMenuBar) c).setBorderPainted(true);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		if (AbstractLookAndFeel.getTheme().isBackgroundPatternOn()) {
			McWinUtils.fillComponent(g, c, SHADOW_COLORS);
		} else {
			super.paint(g, c);
		}
	}

} // end of class
