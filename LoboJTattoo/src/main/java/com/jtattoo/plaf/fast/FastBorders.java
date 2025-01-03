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
package com.jtattoo.plaf.fast;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseBorders;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>FastBorders class.</p>
 *
 * Author Michael Hagen
 *
 */
public class FastBorders extends BaseBorders {

//------------------------------------------------------------------------------------
// Implementation of border classes
//------------------------------------------------------------------------------------
	public static class ButtonBorder implements Border, UIResource {

		private static final Color DEFAULT_FRAME_COLOR = new Color(0, 64, 255);
		private static final Insets INSETS = new Insets(4, 8, 4, 8);

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
			final ButtonModel model = button.getModel();
			final Color frameColor = ColorHelper.darker(button.getBackground(), 30);
			if (model.isEnabled()) {
				if (model.isPressed() && model.isArmed() || model.isSelected()) {
					g.setColor(frameColor);
					g.drawRect(x, y, w - 1, h - 1);
				} else {
					g.setColor(frameColor);
					g.drawRect(x, y, w - 1, h - 1);
					g.setColor(ColorHelper.brighter(button.getBackground(), 40));
					g.drawLine(x + 1, y + 1, x + w - 2, y + 1);
					g.drawLine(x + 1, y + 1, x + 1, y + h - 2);
				}
				if (c instanceof JButton) {
					final JButton b = (JButton) c;
					if (b.getRootPane() != null && b.equals(b.getRootPane().getDefaultButton())) {
						g.setColor(DEFAULT_FRAME_COLOR);
						g.drawRect(x, y, w - 1, h - 1);
					}
				}
			} else {
				g.setColor(AbstractLookAndFeel.getDisabledForegroundColor());
				g.drawRect(x, y, w - 1, h - 1);
			}
		}

	} // end of class ButtonBorder

	// ------------------------------------------------------------------------------
	public static class InternalFrameBorder extends BaseInternalFrameBorder {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final boolean active = isActive(c);
			final boolean resizable = isResizable(c);
			final Color frameColor = AbstractLookAndFeel.getFrameColor();
			Color borderColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
			if (active) {
				borderColor = AbstractLookAndFeel.getWindowBorderColor();
			}
			Color cHi = ColorHelper.brighter(frameColor, 40);
			Color cLo = frameColor;
			if (!resizable) {
				JTattooUtilities.draw3DBorder(g, cHi, cLo, x, y, w, h);
				g.setColor(borderColor);
				for (int i = 1; i < DW; i++) {
					g.drawRect(i, i, w - 2 * i - 1, h - 2 * i - 1);
				}
			} else {
				JTattooUtilities.draw3DBorder(g, cHi, cLo, x, y, w, h);
				cHi = ColorHelper.brighter(borderColor, 40);
				cLo = ColorHelper.darker(borderColor, 20);
				JTattooUtilities.draw3DBorder(g, cHi, cLo, x + 1, y + 1, w - 2, h - 2);

				g.setColor(borderColor);
				g.drawRect(x + 2, y + 2, w - 5, h - 5);
				g.drawRect(x + 3, y + 3, w - 7, h - 7);
				JTattooUtilities.draw3DBorder(g, ColorHelper.darker(borderColor, 5), ColorHelper.brighter(borderColor, 30),
						x + 4, y + 4, w - 8, h - 8);
			}
		}
	} // end of class InternalFrameBorder

	// ------------------------------------------------------------------------------
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
					g.setColor(Color.white);
					g.fillRect(x + 1, y + 1, w - 2, h - 2);
					g2D.setComposite(composite);
				}
			}
		}

	} // end of class RolloverToolButtonBorder

	// ------------------------------------------------------------------------------
	public static class ToolButtonBorder implements Border, UIResource {

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
			final Color hiColor = ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 90);
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
				} else {
					JTattooUtilities.draw3DBorder(g, hiColor, loColor, 0, 0, w, h);
				}
			}
		}

	} // end of class ToolButtonBorder

	// ------------------------------------------------------------------------------------
// Lazy access methods
//------------------------------------------------------------------------------------
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

	/**
	 * <p>getToolButtonBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getToolButtonBorder() {
		if (toolButtonBorder == null) {
			toolButtonBorder = new ToolButtonBorder();
		}
		return toolButtonBorder;
	}

} // end of class FastBorders
