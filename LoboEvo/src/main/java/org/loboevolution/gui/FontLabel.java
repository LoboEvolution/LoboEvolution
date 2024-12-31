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

package org.loboevolution.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.io.Serial;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 * <p>FontLabel class.</p>
 *
 *
 *
 */
public class FontLabel extends JLabel {

	@Serial
    private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for FontLabel.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 */
	public FontLabel(final String text) {
		super(text, SwingConstants.CENTER);
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		setBackground(Color.white);
		setForeground(Color.black);
		setOpaque(true);
		setBorder(new LineBorder(Color.black));
		setPreferredSize(new Dimension(120, 40));
	}
}
