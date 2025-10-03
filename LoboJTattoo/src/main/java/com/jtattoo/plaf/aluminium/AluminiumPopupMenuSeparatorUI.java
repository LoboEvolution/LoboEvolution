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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSeparatorUI;

/**
 * <p>AluminiumPopupMenuSeparatorUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AluminiumPopupMenuSeparatorUI extends BasicSeparatorUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new AluminiumPopupMenuSeparatorUI();
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize(final JComponent c) {
		return new Dimension(1, 1);
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		if (c != null) {
			boolean horizontal = true;
			if (c instanceof JSeparator) {
				final JSeparator sep = (JSeparator) c;
				horizontal = sep.getOrientation() == SwingConstants.HORIZONTAL;
			}
			if (horizontal) {
				g.setColor(Color.lightGray);
				g.drawLine(0, 0, c.getWidth(), 0);
			} else {
				g.setColor(Color.lightGray);
				g.drawLine(0, 0, 0, c.getHeight());
			}
		}
	}

} // end of class AluminiumPopupMenuSeparatorUI
