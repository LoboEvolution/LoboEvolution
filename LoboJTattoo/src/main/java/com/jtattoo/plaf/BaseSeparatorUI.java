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

package com.jtattoo.plaf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSeparatorUI;

/**
 * <p>BaseSeparatorUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseSeparatorUI extends BasicSeparatorUI {

	private static final Dimension size = new Dimension(2, 3);

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BaseSeparatorUI();
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize(JComponent c) {
		return size;
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		boolean horizontal = true;
		if (c instanceof JSeparator) {
			horizontal = ((JSeparator) c).getOrientation() == SwingConstants.HORIZONTAL;
		}
		Color background = c instanceof JPopupMenu.Separator ? AbstractLookAndFeel.getMenuBackgroundColor()
				: AbstractLookAndFeel.getBackgroundColor();
		if (horizontal) {
			int w = c.getWidth();
			g.setColor(background);
			g.drawLine(0, 0, w, 0);
			g.setColor(ColorHelper.darker(background, 30));
			g.drawLine(0, 1, w, 1);
			g.setColor(ColorHelper.brighter(background, 50));
			g.drawLine(0, 2, w, 2);
		} else {
			int h = c.getHeight();
			g.setColor(ColorHelper.darker(background, 30));
			g.drawLine(0, 0, 0, h);
			g.setColor(ColorHelper.brighter(background, 50));
			g.drawLine(1, 0, 1, h);
		}
	}

} // end of class BaseSeparatorUI
