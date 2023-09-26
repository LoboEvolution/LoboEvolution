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

package com.jtattoo.plaf.bernstein;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.BaseTabbedPaneUI;

/**
 * <p>BernsteinTabbedPaneUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BernsteinTabbedPaneUI extends BaseTabbedPaneUI {

	private static final Color[] SEP_COLORS = { new Color(229, 187, 0), new Color(254, 240, 0), new Color(251, 232, 0),
			new Color(247, 225, 0), new Color(243, 216, 0), new Color(229, 187, 0), };

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BernsteinTabbedPaneUI();
	}

	/** {@inheritDoc} */
	@Override
	protected Color[] getContentBorderColors(final int tabPlacement) {
		return SEP_COLORS;
	}

	/** {@inheritDoc} */
	@Override
	public void installDefaults() {
		super.installDefaults();
		tabAreaInsets.bottom = 6;
	}

} // end of class BernsteinTabbedPaneUI
