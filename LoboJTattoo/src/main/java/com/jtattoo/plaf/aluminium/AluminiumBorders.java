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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseBorders;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>AluminiumBorders class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AluminiumBorders extends BaseBorders {

	// ------------------------------------------------------------------------------------
	// Implementation of border classes
	// ------------------------------------------------------------------------------------
	public static class ButtonBorder implements Border, UIResource {

		@Override
		public Insets getBorderInsets(final Component c) {
			if (AbstractLookAndFeel.getTheme().doDrawSquareButtons()) {
				return new Insets(3, 4, 3, 4);
			} else {
				return new Insets(2, 12, 2, 12);
			}
		}

		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
			final Insets insets = getBorderInsets(c);
			borderInsets.left = insets.left;
			borderInsets.top = insets.top;
			borderInsets.right = insets.right;
			borderInsets.bottom = insets.bottom;
			return borderInsets;
		}

		@Override
		public boolean isBorderOpaque() {
			return true;
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
		}

	} // end of class ButtonBorder

	public static class InternalFrameBorder extends BaseInternalFrameBorder {

		private static final long serialVersionUID = 1L;

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final Graphics2D g2D = (Graphics2D) g;
			Color titleColor = AbstractLookAndFeel.getWindowInactiveTitleBackgroundColor();
			if (isActive(c)) {
				titleColor = AbstractLookAndFeel.getWindowTitleBackgroundColor();
			}
			final int th = getTitleHeight(c);

			g.setColor(titleColor);
			g.fillRect(1, 1, w, DW);
			g.fillRect(1, h - DW, w, DW - 1);

			if (isActive(c)) {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 1, DW, DW,
						th + 1);
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), w - DW, DW,
						DW, th + 1);
				final Color c1 = AbstractLookAndFeel.getTheme().getWindowTitleColorDark();
				final Color c2 = AbstractLookAndFeel.getTheme().getWindowTitleColorLight();
				g2D.setPaint(new GradientPaint(0, DW + th + 1, c1, 0, h - th - 2 * DW, c2));
				g.fillRect(1, DW + th + 1, DW - 1, h - th - 2 * DW);
				g.fillRect(w - DW, DW + th + 1, DW - 1, h - th - 2 * DW);
				g2D.setPaint(null);
			} else {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 1,
						DW, DW, th + 1);
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(),
						w - DW, DW, DW, th + 1);
				final Color c1 = AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorDark();
				final Color c2 = AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorLight();
				g2D.setPaint(new GradientPaint(0, DW + th + 1, c1, 0, h - th - 2 * DW, c2));
				g.fillRect(1, DW + th + 1, DW - 1, h - th - 2 * DW);
				g.fillRect(w - DW, DW + th + 1, DW - 1, h - th - 2 * DW);
				g2D.setPaint(null);
			}

			Color borderColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
			if (isActive(c)) {
				borderColor = AbstractLookAndFeel.getWindowBorderColor();
			}
			g.setColor(borderColor);
			g.drawRect(0, 0, w - 1, h - 1);
			g.drawLine(x + DW - 1, y + INSETS.top + th, x + DW - 1, y + h - DW);
			g.drawLine(x + w - DW, y + INSETS.top + th, x + w - DW, y + h - DW);
			g.drawLine(x + DW - 1, y + h - DW, x + w - DW, y + h - DW);

			g.setColor(new Color(220, 220, 220));
			g.drawLine(1, 1, w - 3, 1);
			g.drawLine(1, 1, 1, h - 2);
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
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final AbstractButton button = (AbstractButton) c;
			final ButtonModel model = button.getModel();
			final Color loColor = AbstractLookAndFeel.getFrameColor();
			if (model.isEnabled()) {
				if (model.isPressed() && model.isArmed() || model.isSelected()) {
					final Graphics2D g2D = (Graphics2D) g;
					final Composite composite = g2D.getComposite();
					g.setColor(loColor);
					g.drawRect(x, y, w - 1, h - 1);
					final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f);
					g2D.setComposite(alpha);
					g.setColor(Color.black);
					g.fillRect(x + 1, y + 1, w - 2, h - 2);
					g2D.setComposite(composite);
				} else if (model.isRollover()) {
					final Graphics2D g2D = (Graphics2D) g;
					final Composite composite = g2D.getComposite();
					g.setColor(loColor);
					g.drawRect(x, y, w - 1, h - 1);
					final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
					g2D.setComposite(alpha);
					g.setColor(AbstractLookAndFeel.getTheme().getSelectionBackgroundColor());
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
		return getButtonBorder();
	}

} // end of class AluminiumBorders
