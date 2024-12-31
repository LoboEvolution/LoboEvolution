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
package com.jtattoo.plaf.aluminium;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseBorders;
import com.jtattoo.plaf.BaseMenuBarUI;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>AluminiumMenuBarUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AluminiumMenuBarUI extends BaseMenuBarUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new AluminiumMenuBarUI();
	}

	/** {@inheritDoc} */
	@Override
	public void installUI(final JComponent c) {
		super.installUI(c);
		if (c instanceof JMenuBar) {
			c.setBorder(BaseBorders.getMenuBarBorder());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		if (JTattooUtilities.isMac() || !AbstractLookAndFeel.getTheme().isBackgroundPatternOn()) {
			super.paint(g, c);
		} else {
			AluminiumUtils.fillComponent(g, c);
		}
	}

} // end of class AluminiumMenuBarUI
