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

package com.jtattoo.plaf.texture;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.base.BaseComboBoxUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.NoFocusButton;
import com.jtattoo.plaf.luna.LunaIcons;

/**
 * <p>TextureComboBoxUI class.</p>
 */
public class TextureComboBoxUI extends BaseComboBoxUI {

	// --------------------------------------------------------------------------------------------------
	static class ArrowButton extends NoFocusButton {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public ArrowButton() {
			setBorder(BorderFactory.createEmptyBorder());
			setBorderPainted(false);
			setContentAreaFilled(false);
		}

		@Override
		public void paint(final Graphics g) {
			final Graphics2D g2D = (Graphics2D) g;

			final boolean isPressed = getModel().isPressed();
			final boolean isRollover = getModel().isRollover();

			final int width = getWidth();
			final int height = getHeight();

			final Color[] tc = AbstractLookAndFeel.getTheme().getThumbColors();
			Color c1 = tc[0];
			Color c2 = tc[tc.length - 1];
			if (isPressed) {
				c1 = ColorHelper.darker(c1, 5);
				c2 = ColorHelper.darker(c2, 5);
			} else if (isRollover) {
				c1 = ColorHelper.brighter(c1, 20);
				c2 = ColorHelper.brighter(c2, 20);
			}

			g2D.setPaint(new GradientPaint(0, 0, c1, width, height, c2));
			g2D.fillRect(0, 0, width, height);
			g2D.setPaint(null);
			g2D.setColor(Color.white);
			if (JTattooUtilities.isLeftToRight(this)) {
				g2D.drawRect(1, 0, width - 2, height - 1);
			} else {
				g2D.drawRect(0, 0, width - 2, height - 1);
			}

			final Composite composite = g2D.getComposite();
			final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
			g2D.setComposite(alpha);
			g2D.setColor(c2);
			if (JTattooUtilities.isLeftToRight(this)) {
				g.drawLine(2, 1, width - 2, 1);
				g.drawLine(2, 2, 2, height - 2);
			} else {
				g.drawLine(1, 1, width - 3, 1);
				g.drawLine(1, 2, 1, height - 2);
			}
			g2D.setComposite(composite);

			// paint the icon
			final Icon icon = LunaIcons.getComboBoxIcon();
			final int x = (width - icon.getIconWidth()) / 2;
			final int y = (height - icon.getIconHeight()) / 2;
			final int dx = JTattooUtilities.isLeftToRight(this) ? 0 : -1;
			if (getModel().isPressed() && getModel().isArmed()) {
				icon.paintIcon(this, g, x + dx + 2, y + 1);
			} else {
				icon.paintIcon(this, g, x + dx + 1, y);
			}
		}
	} // end of class ArrowButton

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new TextureComboBoxUI();
	}

	/** {@inheritDoc} */
	@Override
	public JButton createArrowButton() {
		return new ArrowButton();
	}

	/** {@inheritDoc} */
	@Override
	protected void setButtonBorder() {
	}

} // end of class TextureCheckBoxUI
