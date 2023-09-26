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
package com.jtattoo.plaf.hifi;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.BaseComboBoxUI;
import com.jtattoo.plaf.NoFocusButton;

/**
 * <p>HiFiComboBoxUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class HiFiComboBoxUI extends BaseComboBoxUI {

	// --------------------------------------------------------------------------------------------------
	static class ArrowButtonBorder extends AbstractBorder {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Insets INSETS = new Insets(1, 3, 1, 2);
		private static final Color FRAME_LO_COLOR = new Color(120, 120, 120);
		private static final Color FRAME_LOWER_COLOR = new Color(104, 104, 104);
		private static final Color FRAME_LOWER_LO_COLOR = new Color(64, 64, 64);
		private static final Color FRAME_LOWEST_COLOR = new Color(32, 32, 32);

		@Override
		public Insets getBorderInsets(final Component c) {
			return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
		}

		@Override
		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
			borderInsets.left = INSETS.left;
			borderInsets.top = INSETS.top;
			borderInsets.right = INSETS.right;
			borderInsets.bottom = INSETS.bottom;
			return borderInsets;
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final Graphics2D g2D = (Graphics2D) g;
			g.translate(x, y);

			g.setColor(FRAME_LO_COLOR);
			g.drawLine(1, 0, w - 1, 0);
			g.drawLine(1, 1, 1, h - 2);
			g.setColor(FRAME_LOWER_COLOR);
			g.drawLine(w - 1, 1, w - 1, h - 2);
			g.drawLine(2, h - 1, w - 2, h - 1);

			final Composite composite = g2D.getComposite();
			final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			g2D.setComposite(alpha);
			g.setColor(FRAME_LOWEST_COLOR);
			g.drawLine(2, 1, w - 2, 1);
			g.drawLine(2, 2, 2, h - 3);
			g.setColor(FRAME_LOWER_LO_COLOR);
			g.drawLine(0, 0, 0, h);
			g2D.setComposite(composite);

			g.translate(-x, -y);
		}

	} // end of class ArrowButtonBorder

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new HiFiComboBoxUI();
	}

	/** {@inheritDoc} */
	@Override
	public JButton createArrowButton() {
		final JButton button = new NoFocusButton(HiFiIcons.getComboBoxIcon());
		button.setBorder(new ArrowButtonBorder());
		return button;
	}

	/** {@inheritDoc} */
	@Override
	protected void setButtonBorder() {
	}

} // end of class HiFiComboBoxUI
