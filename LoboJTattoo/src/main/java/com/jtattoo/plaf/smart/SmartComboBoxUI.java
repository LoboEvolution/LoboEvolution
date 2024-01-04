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
package com.jtattoo.plaf.smart;

import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseComboBoxUI;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>SmartComboBoxUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class SmartComboBoxUI extends BaseComboBoxUI {

	// --------------------------------------------------------------------------------------------------
	static class ArrowButton extends BaseComboBoxUI.ArrowButton {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(final Graphics g) {
			super.paint(g);
			if (getModel().isRollover()) {
				g.setColor(AbstractLookAndFeel.getFocusColor());
				g.drawLine(1, 0, getWidth() - 1, 0);
				g.drawLine(1, 1, getWidth() - 1, 1);
			}
		}

	} // end of class Arrow Button

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new SmartComboBoxUI();
	}

	/** {@inheritDoc} */
	@Override
	public JButton createArrowButton() {
		final ArrowButton button = new ArrowButton();
		if (JTattooUtilities.isLeftToRight(comboBox)) {
			final Border border = BorderFactory.createMatteBorder(0, 1, 0, 0, AbstractLookAndFeel.getFrameColor());
			button.setBorder(border);
		} else {
			final Border border = BorderFactory.createMatteBorder(0, 0, 0, 1, AbstractLookAndFeel.getFrameColor());
			button.setBorder(border);
		}
		return button;
	}

} // end of class SmartComboBoxUI
