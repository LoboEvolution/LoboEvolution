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
package com.jtattoo.plaf.fast;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JRootPane;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.base.BaseRootPaneUI;
import com.jtattoo.plaf.base.BaseTitlePane;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>FastTitlePane class.</p>
 *
 * Author Michael Hagen
 *
 */
public class FastTitlePane extends BaseTitlePane {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for FastTitlePane.</p>
	 *
	 * @param root a {@link javax.swing.JRootPane} object.
	 * @param ui a {@link com.jtattoo.plaf.base.BaseRootPaneUI} object.
	 */
	public FastTitlePane(final JRootPane root, final BaseRootPaneUI ui) {
		super(root, ui);
	}

	/** {@inheritDoc} */
	@Override
	public void paintBackground(final Graphics g) {
		if (isActive()) {
			g.setColor(AbstractLookAndFeel.getWindowTitleBackgroundColor());
			g.fillRect(0, 0, getWidth(), getHeight());
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveTitleBackgroundColor());
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(final Graphics g) {
		Color borderColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
		if (isActive()) {
			borderColor = AbstractLookAndFeel.getWindowBorderColor();
		}
		JTattooUtilities.draw3DBorder(g, ColorHelper.brighter(borderColor, 30), ColorHelper.darker(borderColor, 5), 0,
				0, getWidth(), getHeight());
	}

} // end of class FastTitlePane
