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
package com.jtattoo.plaf.luna;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
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
 * <p>LunaBorders class.</p>
 *
 * Author Michael Hagen
 *
 */
public class LunaBorders extends BaseBorders {

	// ------------------------------------------------------------------------------------
	// Implementation of border classes
	// ------------------------------------------------------------------------------------
	public static class ButtonBorder implements Border, UIResource {

		private static final Color DEFAUULT_COLOR_HI = new Color(220, 230, 245);
		private static final Color DEFAULT_COLOR_MED = new Color(212, 224, 243);
		private static final Color DEFAULT_COLOR_LO = new Color(200, 215, 240);
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
				if (button.getRootPane() != null && button.equals(button.getRootPane().getDefaultButton())) {
					if (!button.getModel().isRollover()) {
						g2D.setColor(DEFAUULT_COLOR_HI);
						g2D.drawRect(x + 1, y + 1, w - 4, h - 5);
						g2D.setColor(DEFAULT_COLOR_MED);
						g2D.drawRect(x + 2, y + 2, w - 6, h - 6);
						g2D.setColor(DEFAULT_COLOR_LO);
						g2D.drawLine(x + 3, h - 3, w - 3, h - 3);
						g2D.drawLine(w - 3, y + 4, w - 3, h - 4);
					}
				}

				g2D.setColor(Color.white);
				g2D.drawRect(x, y, w - 1, h - 1);

				g2D.setColor(frameColor);
				g2D.drawRect(x, y, w - 2, h - 2);
			} else {
				final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				if (button.getRootPane() != null && button.equals(button.getRootPane().getDefaultButton())) {
					if (!button.getModel().isRollover()) {
						g2D.setColor(DEFAUULT_COLOR_HI);
						g2D.drawRoundRect(x + 1, y + 1, w - 4, h - 5, 6, 6);
						g2D.setColor(DEFAULT_COLOR_MED);
						g2D.drawRoundRect(x + 2, y + 2, w - 6, h - 6, 6, 6);
						g2D.setColor(DEFAULT_COLOR_LO);
						g2D.drawLine(x + 3, h - 3, w - 3, h - 3);
						g2D.drawLine(w - 3, y + 4, w - 3, h - 4);
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

	public static class ComboBoxBorder extends AbstractBorder implements UIResource {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Color BORDER_COLOR = new Color(127, 157, 185);
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
			int width = w;
			int height = h;
			width--;
			height--;
			g.setColor(BORDER_COLOR);
			g.drawRect(x, y, width, height);
		}

	} // end of class ComboBoxBorder

	public static class InternalFrameBorder extends BaseInternalFrameBorder {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public InternalFrameBorder() {
			INSETS.top = 3;
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final boolean active = isActive(c);
			final int th = getTitleHeight(c);
			Color titleColor = AbstractLookAndFeel.getWindowTitleColorLight();
			Color borderColor = AbstractLookAndFeel.getWindowBorderColor();
			if (!active) {
				titleColor = ColorHelper.brighter(titleColor, 20);
				borderColor = ColorHelper.brighter(borderColor, 20);
			}
			g.setColor(titleColor);
			g.fillRect(x, y + 1, w, INSETS.top - 1);
			g.setColor(borderColor);
			g.fillRect(x + 1, y + h - DW, w - 2, DW - 1);
			g.fillRect(1, INSETS.top + th + 1, DW - 1, h - th - DW);
			g.fillRect(w - DW, INSETS.top + th + 1, DW - 1, h - th - DW);

			if (active) {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 1,
						INSETS.top, DW, th + 1);
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), w - DW,
						INSETS.top, DW, th + 1);
			} else {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 1,
						INSETS.top, DW, th + 1);
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(),
						w - DW, INSETS.top, DW, th + 1);
			}

			g.setColor(ColorHelper.darker(borderColor, 15));
			g.drawRect(x, y, w - 1, h - 1);
			g.drawLine(x + DW - 1, y + INSETS.top + th, x + DW - 1, y + h - DW);
			g.drawLine(x + w - DW, y + INSETS.top + th, x + w - DW, y + h - DW);
			g.drawLine(x + DW - 1, y + h - DW, x + w - DW, y + h - DW);
		}
	} // end of class InternalFrameBorder

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
					final Color frameColor = ColorHelper.darker(AbstractLookAndFeel.getToolbarBackgroundColor(), 20);
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
					Color frameLoColor = ColorHelper.darker(frameColor, 30);
					JTattooUtilities.draw3DBorder(g, frameHiColor, frameLoColor, x, y, w, h);
					frameHiColor = Color.white;
					frameLoColor = ColorHelper.brighter(frameLoColor, 60);
					JTattooUtilities.draw3DBorder(g, frameHiColor, frameLoColor, x + 1, y + 1, w - 2, h - 2);

					final Graphics2D g2D = (Graphics2D) g;
					final Composite composite = g2D.getComposite();
					final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
					g2D.setComposite(alpha);
					g.setColor(Color.white);
					g.fillRect(x + 2, y + 2, w - 4, h - 4);
					g2D.setComposite(composite);
				} else if (model.isSelected()) {
					final Color frameColor = AbstractLookAndFeel.getToolbarBackgroundColor();
					final Color frameHiColor = Color.white;
					final Color frameLoColor = ColorHelper.darker(frameColor, 30);
					JTattooUtilities.draw3DBorder(g, frameLoColor, frameHiColor, x, y, w, h);
				}
			}
		}

	} // end of class RolloverToolButtonBorder

	public static class ScrollPaneBorder extends AbstractBorder implements UIResource {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Color BORDER_COLOR = new Color(127, 157, 185);
		private static final Insets INSETS = new Insets(2, 2, 2, 2);
		private static final Insets TABLE_INSETS = new Insets(1, 1, 1, 1);
		private boolean tableBorder = false;

		public ScrollPaneBorder(final boolean tableBorder) {
			this.tableBorder = tableBorder;
		}

		@Override
		public Insets getBorderInsets(final Component c) {
			if (tableBorder) {
				return new Insets(TABLE_INSETS.top, TABLE_INSETS.left, TABLE_INSETS.bottom, TABLE_INSETS.right);
			} else {
				return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
			}
		}

		@Override
		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
			final Insets ins = getBorderInsets(c);
			borderInsets.left = ins.left;
			borderInsets.top = ins.top;
			borderInsets.right = ins.right;
			borderInsets.bottom = ins.bottom;
			return borderInsets;
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			g.setColor(BORDER_COLOR);
			g.drawRect(x, y, w - 1, h - 1);
			g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getTheme().getBackgroundColor(), 50));
			g.drawRect(x + 1, y + 1, w - 3, h - 3);
		}

	} // end of class ScrollPaneBorder

	public static class TableHeaderBorder extends AbstractBorder implements UIResource {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Insets INSETS = new Insets(0, 1, 1, 1);

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
			g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getControlBackgroundColor(), 40));
			g.drawLine(0, 0, 0, h - 1);
			g.setColor(ColorHelper.darker(AbstractLookAndFeel.getControlBackgroundColor(), 20));
			g.drawLine(w - 1, 0, w - 1, h - 1);
			g.setColor(ColorHelper.darker(AbstractLookAndFeel.getControlBackgroundColor(), 10));
			g.drawLine(0, h - 1, w - 1, h - 1);
		}

	} // end of class TableHeaderBorder

	public static class TextFieldBorder extends AbstractBorder implements UIResource {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Color BORDER_COLOR = new Color(127, 157, 185);
		private static final Insets INSETS = new Insets(2, 2, 2, 2);

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
			int width = w;
			int height = h;
			width--;
			height--;
			g.setColor(BORDER_COLOR);
			g.drawRect(x, y, width, height);
		}

	} // end of class TextFieldBorder

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
	 * <p>getComboBoxBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getComboBoxBorder() {
		if (comboBoxBorder == null) {
			comboBoxBorder = new ComboBoxBorder();
		}
		return comboBoxBorder;
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
	 * <p>getScrollPaneBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getScrollPaneBorder() {
		if (scrollPaneBorder == null) {
			scrollPaneBorder = new ScrollPaneBorder(false);
		}
		return scrollPaneBorder;
	}

	/**
	 * <p>getTableHeaderBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getTableHeaderBorder() {
		if (tableHeaderBorder == null) {
			tableHeaderBorder = new TableHeaderBorder();
		}
		return tableHeaderBorder;
	}

	/**
	 * <p>getTableScrollPaneBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getTableScrollPaneBorder() {
		if (tableScrollPaneBorder == null) {
			tableScrollPaneBorder = new ScrollPaneBorder(true);
		}
		return tableScrollPaneBorder;
	}

	// ------------------------------------------------------------------------------------
	// Lazy access methods
	// ------------------------------------------------------------------------------------
	/**
	 * <p>getTextBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getTextBorder() {
		if (textFieldBorder == null) {
			textFieldBorder = new TextFieldBorder();
		}
		return textFieldBorder;
	}

	/**
	 * <p>getTextFieldBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getTextFieldBorder() {
		return getTextBorder();
	}

	/**
	 * <p>getToggleButtonBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getToggleButtonBorder() {
		return getButtonBorder();
	}

} // end of class LunaBorders
