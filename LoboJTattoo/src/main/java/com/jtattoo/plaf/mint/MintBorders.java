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
package com.jtattoo.plaf.mint;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseBorders;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>MintBorders class.</p>
 *
 * Author Michael Hagen
 *
 */
public class MintBorders extends BaseBorders {

	// ------------------------------------------------------------------------------------
	// Implementation of border classes
	// ------------------------------------------------------------------------------------
	public static class ButtonBorder implements Border, UIResource {

		private static final Insets INSETS = new Insets(3, 8, 5, 10);

		@Override
		public Insets getBorderInsets(final Component c) {
			return INSETS;
		}

		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
			borderInsets.left = INSETS.left;
			borderInsets.top = INSETS.top;
			borderInsets.right = INSETS.right;
			borderInsets.bottom = INSETS.bottom;
			return borderInsets;
		}

		@Override
		public boolean isBorderOpaque() {
			return true;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
		}

	} // end of class ButtonBorder

	public static class InternalFrameBorder extends BaseInternalFrameBorder {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
			boolean active = isActive(c);
			boolean resizable = isResizable(c);
			if (!resizable) {
				Color frameColor = AbstractLookAndFeel.getFrameColor();
				Color borderColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
				if (active) {
					borderColor = AbstractLookAndFeel.getWindowBorderColor();
				}
				Color cHi = ColorHelper.brighter(frameColor, 40);
				Color cLo = frameColor;
				JTattooUtilities.draw3DBorder(g, cHi, cLo, x, y, w, h);
				g.setColor(borderColor);
				for (int i = 1; i < DW; i++) {
					g.drawRect(i, i, w - 2 * i - 1, h - 2 * i - 1);
				}
				return;
			}
			h--;
			w--;
			Color color = AbstractLookAndFeel.getWindowInactiveBorderColor();
			if (active) {
				color = AbstractLookAndFeel.getWindowBorderColor();
			}

			// left
			g.setColor(color);
			g.drawLine(x, y, x, y + h);
			g.setColor(ColorHelper.brighter(color, 60));
			g.drawLine(x + 1, y + 1, x + 1, y + h - 1);
			g.setColor(ColorHelper.brighter(color, 40));
			g.drawLine(x + 2, y + 2, x + 2, y + h - 2);
			g.setColor(ColorHelper.brighter(color, 20));
			g.drawLine(x + 3, y + 3, x + 3, y + h - 3);
			g.setColor(color);
			g.drawLine(x + 4, y + 4, x + 4, y + h - 4);

			// right
			g.setColor(color);
			g.drawLine(x + w, y, x + w, y + h);
			g.setColor(ColorHelper.brighter(color, 20));
			g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
			g.setColor(ColorHelper.brighter(color, 40));
			g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 2);
			g.setColor(ColorHelper.brighter(color, 60));
			g.drawLine(x + w - 3, y + 3, x + w - 3, y + h - 3);
			g.setColor(color);
			g.drawLine(x + w - 4, y + 4, x + w - 4, y + h - 4);

			// top
			g.setColor(color);
			g.drawLine(x, y, x + w, y);
			g.setColor(ColorHelper.brighter(color, 60));
			g.drawLine(x + 1, y + 1, x + w - 1, y + 1);
			g.setColor(ColorHelper.brighter(color, 40));
			g.drawLine(x + 2, y + 2, x + w - 2, y + 2);
			g.setColor(ColorHelper.brighter(color, 20));
			g.drawLine(x + 3, y + 3, x + w - 3, y + 3);
			g.setColor(color);
			g.drawLine(x + 4, y + 4, x + w - 4, y + 4);

			// bottom
			g.setColor(color);
			g.drawLine(x, y + h, x + w, y + h);
			g.setColor(ColorHelper.brighter(color, 20));
			g.drawLine(x + 1, y + h - 1, x + w - 1, y + h - 1);
			g.setColor(ColorHelper.brighter(color, 40));
			g.drawLine(x + 2, y + h - 2, x + w - 2, y + h - 2);
			g.setColor(ColorHelper.brighter(color, 60));
			g.drawLine(x + 3, y + h - 3, x + w - 3, y + h - 3);
			g.setColor(color);
			g.drawLine(x + 4, y + h - 4, x + w - 4, y + h - 4);
		}

	} // end of class InternalFrameBorder

	public static class RolloverToolButtonBorder implements Border, UIResource {

		private static final Insets INSETS = new Insets(1, 1, 1, 1);

		@Override
		public Insets getBorderInsets(final Component c) {
			return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
		}

		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
			borderInsets.left = INSETS.left;
			borderInsets.top = INSETS.top;
			borderInsets.right = INSETS.right;
			borderInsets.bottom = INSETS.bottom;
			return borderInsets;
		}

		@Override
		public boolean isBorderOpaque() {
			return true;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
			AbstractButton button = (AbstractButton) c;
			ButtonModel model = button.getModel();
			Color frameColor = ColorHelper.darker(AbstractLookAndFeel.getToolbarBackgroundColor(), 20);
			if (model.isEnabled()) {
				if (model.isPressed() && model.isArmed() || model.isSelected()) {
					Graphics2D g2D = (Graphics2D) g;
					Composite composite = g2D.getComposite();
					g.setColor(frameColor);
					g.drawRect(x, y, w - 1, h - 1);
					AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f);
					g2D.setComposite(alpha);
					g.setColor(Color.black);
					g.fillRect(x + 1, y + 1, w - 2, h - 2);
					g2D.setComposite(composite);
				} else if (model.isRollover()) {
					Graphics2D g2D = (Graphics2D) g;
					Composite composite = g2D.getComposite();
					g.setColor(frameColor);
					g.drawRect(x, y, w - 1, h - 1);
					AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
					g2D.setComposite(alpha);
					g.setColor(Color.white);
					g.fillRect(x + 1, y + 1, w - 2, h - 2);
					g2D.setComposite(composite);
				}
			}
		}

	} // end of class RolloverToolButtonBorder

	// ------------------------------------------------------------------------------------
	// Lazy access methods
	// ------------------------------------------------------------------------------------
	/**
	 * <p>getButtonBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getButtonBorder() {
		if (buttonBorder == null) {
			buttonBorder = new ButtonBorder();
		}
		return buttonBorder;
	}

	/**
	 * <p>getInternalFrameBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getInternalFrameBorder() {
		if (internalFrameBorder == null) {
			internalFrameBorder = new InternalFrameBorder();
		}
		return internalFrameBorder;
	}

	/**
	 * <p>getRolloverToolButtonBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getRolloverToolButtonBorder() {
		if (rolloverToolButtonBorder == null) {
			rolloverToolButtonBorder = new RolloverToolButtonBorder();
		}
		return rolloverToolButtonBorder;
	}

	/**
	 * <p>getToggleButtonBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getToggleButtonBorder() {
		if (buttonBorder == null) {
			buttonBorder = new ButtonBorder();
		}
		return buttonBorder;
	}

} // end of class MintBorders
