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

package org.loboevolution.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 * <p>ColorComboBox class.</p>
 *
 *
 *
 */
public class ColorComboBox extends JComboBox<Object> {

	static class ColorComboRenderer extends JPanel implements ListCellRenderer<Object> {
		private static final long serialVersionUID = 1L;
		protected Color m_c = Color.black;

		public ColorComboRenderer() {
			super();
			setBorder(new CompoundBorder(new MatteBorder(2, 10, 2, 10, Color.white), new LineBorder(Color.black)));
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object obj, int row, boolean sel,
				boolean hasFocus) {
			if (obj instanceof Color) {
				this.m_c = (Color) obj;
			}
			return this;
		}

		@Override
		public void paint(final Graphics g) {
			setBackground(this.m_c);
			super.paint(g);
		}

	}

	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for ColorComboBox.</p>
	 */
	public ColorComboBox() {
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		final int[] values = new int[] { 0, 128, 192, 255 };
		for (final int value : values) {
			for (final int value2 : values) {
				for (final int value3 : values) {
					final Color c = new Color(value, value2, value3);
					addItem(c);
				}
			}
		}
		setRenderer(new ColorComboRenderer());
	}

}
