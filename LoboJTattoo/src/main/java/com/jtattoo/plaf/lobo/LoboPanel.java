/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * <p>LoboPanel class.</p>
 */
public class LoboPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for LoboPanel.</p>
	 */
	public LoboPanel() {
		final LoboBackground lb = new LoboBackground();
		setBackground(lb.getBackground());
	}
	
	/**
	 * <p>Constructor for LoboPanel.</p>
	 *
	 * @param title a {@link java.lang.String} object.
	 */
	public LoboPanel(final String title) {
		final LoboBackground lb = new LoboBackground();
		setBackground(lb.getBackground());
		final TitledBorder border = new TitledBorder(new LineBorder(lb.getForeground()), title);
		border.setTitleColor(lb.getForeground());
		setBorder(border);
	}

	/**
	 * <p>Constructor for LoboPanel.</p>
	 *
	 * @param layout a {@link java.awt.LayoutManager} object.
	 * @param title a {@link java.lang.String} object.
	 */
	public LoboPanel(final LayoutManager layout, final String title) {
		super(layout);
		final LoboBackground lb = new LoboBackground();
		setBackground(lb.getBackground());
		final TitledBorder border = new TitledBorder(new LineBorder(lb.getForeground()), title);
		border.setTitleColor(lb.getForeground());
		setBorder(border);
	}
}
