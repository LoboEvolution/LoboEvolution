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

package com.jtattoo.plaf.lobo;

import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

/**
 * <p>LoboButton class.</p>
 */
public class LoboButton extends JButton {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for LoboButton.</p>
	 */
	public LoboButton() {
		final LoboBackground lb = new LoboBackground();
		setForeground(lb.getForeground());
		setFont(new Font("Tahoma", Font.BOLD, 14));
		setFocusPainted(false);
		setContentAreaFilled(false);
		setBorder(new LineBorder(lb.getForeground()));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
}
