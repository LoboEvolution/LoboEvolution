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

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTreeUI;

/**
 * author Michael Hagen
 *
 *
 *
 */
public class BaseTreeUI extends BasicTreeUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BaseTreeUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintHorizontalLine(final Graphics g, final JComponent c, final int y, final int left, final int right) {
		drawDashedHorizontalLine(g, y, left, right);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintVerticalLine(final Graphics g, final JComponent c, final int x, final int top, final int bottom) {
		drawDashedVerticalLine(g, x, top, bottom);
	}

} // end of class BaseTreeUI
