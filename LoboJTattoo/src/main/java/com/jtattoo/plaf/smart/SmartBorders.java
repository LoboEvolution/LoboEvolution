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
package com.jtattoo.plaf.smart;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseBorders;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>SmartBorders class.</p>
 *
 * Author Michael Hagen
 *
 */
public class SmartBorders extends BaseBorders {

	// ------------------------------------------------------------------------------------
	// Implementation of border classes
	// ------------------------------------------------------------------------------------
	public static class ButtonBorder implements Border, UIResource {

		private static final Color DEFAULT_COLOR_HI = new Color(220, 230, 245);
		private static final Color DEFAULT_COLOR_LO = new Color(212, 224, 243);
		private static final Insets INSETS = new Insets(3, 6, 3, 6);

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
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final AbstractButton button = (AbstractButton) c;
			final Graphics2D g2D = (Graphics2D) g;
			Color frameColor = AbstractLookAndFeel.getTheme().getFrameColor();
			if (!JTattooUtilities.isFrameActive(button)) {
				frameColor = ColorHelper.brighter(frameColor, 40);
			}

			if (AbstractLookAndFeel.getTheme().doDrawSquareButtons()) {
				g2D.setColor(Color.white);
				g2D.drawRect(x, y, w - 1, h - 1);

				if (button.getRootPane() != null && button.equals(button.getRootPane().getDefaultButton())
						&& !button.hasFocus()) {
					g2D.setColor(ColorHelper.darker(frameColor, 20));
					g2D.drawRect(x, y, w - 1, h - 2);
					if (!button.getModel().isRollover()) {
						g2D.setColor(DEFAULT_COLOR_HI);
						g2D.drawRect(x + 1, y + 1, w - 3, h - 4);
						g2D.setColor(DEFAULT_COLOR_LO);
						g2D.drawRect(x + 2, y + 2, w - 5, h - 6);
					}
				} else {
					g2D.setColor(frameColor);
					g2D.drawRect(x, y, w - 2, h - 2);
				}
			} else {
				final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				if (button.getRootPane() != null && button.equals(button.getRootPane().getDefaultButton())) {
					if (!button.getModel().isRollover()) {
						g2D.setColor(DEFAULT_COLOR_HI);
						g2D.drawRoundRect(x + 1, y + 1, w - 4, h - 2, 6, 6);
						g2D.setColor(DEFAULT_COLOR_LO);
						g2D.drawRoundRect(x + 2, y + 2, w - 6, h - 6, 6, 6);
					}
				}

				g2D.setColor(Color.white);
				g2D.drawRoundRect(x, y, w - 1, h - 1, 6, 6);

				g2D.setColor(frameColor);
				g2D.drawRoundRect(x, y, w - 2, h - 2, 6, 6);

				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
			}
		}

	} // end of class ButtonBorder

	public static class InternalFrameBorder extends BaseInternalFrameBorder {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final Graphics2D g2D = (Graphics2D) g;
			final boolean active = isActive(c);
			final boolean resizable = isResizable(c);
			final int th = getTitleHeight(c);
			Color frameColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
			Color titleColor = AbstractLookAndFeel.getWindowInactiveTitleColorLight();
			if (active) {
				titleColor = AbstractLookAndFeel.getWindowTitleColorLight();
				frameColor = AbstractLookAndFeel.getWindowBorderColor();
			}

			if (!resizable) {
				final Insets bi = getBorderInsets(c);
				g.setColor(frameColor);
				g.drawRect(x, y, w - 1, h - 1);
				if (active) {
					g.setColor(AbstractLookAndFeel.getWindowTitleColorDark());
				} else {
					g.setColor(AbstractLookAndFeel.getWindowInactiveTitleColorDark());
				}
				for (int i = 1; i < bi.left; i++) {
					g.drawRect(i, i, w - 2 * i - 1, h - 2 * i - 1);
				}
				g.setColor(frameColor);
				g.drawLine(bi.left - 1, y + th + bi.top, bi.left - 1, y + h - bi.bottom);
				g.drawLine(w - bi.right, y + th + bi.top, w - bi.right, y + h - bi.bottom);
				g.drawLine(bi.left - 1, y + h - bi.bottom, w - bi.right, y + h - bi.bottom);
			} else {
				g.setColor(titleColor);
				g.fillRect(x, y + 1, w, DW - 1);
				g.fillRect(x + 1, y + h - DW, w - 2, DW - 1);
				final Color color;
				if (active) {
					JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 1, DW, DW, th + 1);
					JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), w - DW, DW, DW, th + 1);

					final Color c1 = AbstractLookAndFeel.getTheme().getWindowTitleColorDark();
					final Color c2 = AbstractLookAndFeel.getTheme().getWindowTitleColorLight();
					g2D.setPaint(new GradientPaint(0, DW + th + 1, c1, 0, h - th - 2 * DW, c2));
					g.fillRect(1, DW + th + 1, DW - 1, h - th - 2 * DW);
					g.fillRect(w - DW, DW + th + 1, DW - 1, h - th - 2 * DW);
					g2D.setPaint(null);
				} else {
					JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 1, DW, DW, th + 1);
					JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), w - DW, DW, DW, th + 1);

					final Color c1 = AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorDark();
					final Color c2 = AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorLight();
					g2D.setPaint(new GradientPaint(0, DW + th + 1, c1, 0, h - th - 2 * DW, c2));
					g.fillRect(1, DW + th + 1, DW - 1, h - th - 2 * DW);
					g.fillRect(w - DW, DW + th + 1, DW - 1, h - th - 2 * DW);
					g2D.setPaint(null);
				}
				if (active && resizable) {
					final int d = DW + 12;
					// unten
					color = AbstractLookAndFeel.getWindowTitleColorDark();
					final Color cHi = ColorHelper.brighter(color, 30);
					final Color cLo = ColorHelper.darker(color, 20);

					// links
					g.setColor(color);
					g.fillRect(x + 1, y + h - d, DW - 1, d - 1);
					g.fillRect(x + DW, y + h - DW, d - DW - 1, d - DW - 1);

					g.setColor(cLo);
					g.drawLine(x + 1, y + h - d - 2, x + DW - 2, y + h - d - 2);
					g.drawLine(x + DW - 2, y + h - d - 2, x + DW - 2, y + h - DW);
					g.drawLine(x + DW - 2, y + h - DW, x + d - 1, y + h - DW);
					g.drawLine(x + d - 1, y + h - DW, x + d - 1, y + h - 1);

					g.setColor(cHi);
					g.drawLine(x + 1, y + h - d - 1, x + DW - 3, y + h - d - 1);
					g.drawLine(x + DW - 1, y + h - d - 1, x + DW - 1, y + h - DW - 1);
					g.drawLine(x + DW - 1, y + h - DW + 1, x + d - 2, y + h - DW + 1);
					g.drawLine(x + d - 2, y + h - DW + 1, x + d - 2, y + h - 1);

					// rechts
					g.setColor(color);
					g.fillRect(x + w - d - 1, y + h - DW, d, DW - 1);
					g.fillRect(x + w - DW, y + h - d - 1, DW - 1, d);

					g.setColor(cLo);
					g.drawLine(x + w - DW - 1, y + h - d - 2, x + w - 1, y + h - d - 2);
					g.drawLine(x + w - DW, y + h - d - 2, x + w - DW, y + h - DW);
					g.drawLine(x + w - d - 1, y + h - DW, x + w - DW, y + h - DW);
					g.drawLine(x + w - d - 1, y + h - DW, x + w - d - 1, y + h - 1);

					g.setColor(cHi);
					g.drawLine(x + w - DW + 1, y + h - d - 1, x + w - 1, y + h - d - 1);
					g.drawLine(x + w - DW + 1, y + h - d - 1, x + w - DW + 1, y + h - DW);
					g.drawLine(x + w - d, y + h - DW + 1, x + w - DW + 1, y + h - DW + 1);
					g.drawLine(x + w - d, y + h - DW + 1, x + w - d, y + h - 1);
				}
				g.setColor(frameColor);
				g.drawRect(x, y, w - 1, h - 1);
				g.drawLine(x + DW - 1, y + DW + th, x + DW - 1, y + h - DW);
				g.drawLine(x + w - DW, y + DW + th, x + w - DW, y + h - DW);
				g.drawLine(x + DW - 1, y + h - DW, x + w - DW, y + h - DW);
			}

		}
	} // end of class InternalFrameBorder

	public static class PaletteBorder extends AbstractBorder implements UIResource {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Insets INSETS = new Insets(1, 1, 1, 1);

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
			if (JTattooUtilities.isFrameActive(c)) {
				g.setColor(AbstractLookAndFeel.getFrameColor());
			} else {
				g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 40));
			}
			g.drawRect(x, y, w - 1, h - 1);
		}

	} // end of class PaletteBorder

	public static class RolloverToolButtonBorder implements Border, UIResource {

		private static final Insets INSETS = new Insets(2, 2, 2, 2);

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
			if (model.isEnabled()) {
				if (model.isPressed() && model.isArmed() || model.isSelected()) {
					final Color frameColor = ColorHelper.darker(AbstractLookAndFeel.getToolbarBackgroundColor(), 30);
					g.setColor(frameColor);
					g.drawRect(x, y, w - 1, h - 1);

					final Graphics2D g2D = (Graphics2D) g;
					final Composite composite = g2D.getComposite();
					final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
					g2D.setComposite(alpha);
					g.setColor(Color.black);
					g.fillRect(x + 1, y + 1, w - 2, h - 2);
					g2D.setComposite(composite);
				} else if (model.isRollover()) {
					final Color frameColor = AbstractLookAndFeel.getToolbarBackgroundColor();
					Color frameHiColor = ColorHelper.darker(frameColor, 5);
					Color frameLoColor = ColorHelper.darker(frameColor, 20);
					JTattooUtilities.draw3DBorder(g, frameHiColor, frameLoColor, x, y, w, h);
					frameHiColor = Color.white;
					frameLoColor = ColorHelper.brighter(frameLoColor, 60);
					JTattooUtilities.draw3DBorder(g, frameHiColor, frameLoColor, x + 1, y + 1, w - 2, h - 2);

					final Graphics2D g2D = (Graphics2D) g;
					final Composite composite = g2D.getComposite();
					final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
					g2D.setComposite(alpha);
					g.setColor(Color.white);
					g.fillRect(x + 2, y + 2, w - 4, h - 4);
					g2D.setComposite(composite);

					g.setColor(AbstractLookAndFeel.getFocusColor());
					g.drawLine(x + 1, y + 1, x + w - 1, y + 1);
					g.drawLine(x + 1, y + 2, x + w - 2, y + 2);
				} else if (model.isSelected()) {
					final Color frameColor = AbstractLookAndFeel.getToolbarBackgroundColor();
					final Color frameHiColor = Color.white;
					final Color frameLoColor = ColorHelper.darker(frameColor, 30);
					JTattooUtilities.draw3DBorder(g, frameLoColor, frameHiColor, x, y, w, h);
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
	 * <p>getPaletteBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getPaletteBorder() {
		if (paletteBorder == null) {
			paletteBorder = new PaletteBorder();
		}
		return paletteBorder;
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

} // end of class SmartBorders
