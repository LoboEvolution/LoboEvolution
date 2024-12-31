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

package com.jtattoo.plaf.aero;

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
 * <p>AeroBorders class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AeroBorders extends BaseBorders {

	// ------------------------------------------------------------------------------------
	// Implementation of border classes
	// ------------------------------------------------------------------------------------
	public static class ButtonBorder implements Border, UIResource {

		private static final Insets insets = new Insets(4, 8, 4, 8);

		@Override
		public Insets getBorderInsets(final Component c) {
			return insets;
		}

		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
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
			final Graphics2D g2D = (Graphics2D) g;
			final AbstractButton button = (AbstractButton) c;
			final ButtonModel model = button.getModel();
			if (model.isEnabled()) {
				g.setColor(AbstractLookAndFeel.getFrameColor());
			} else {
				g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 30));
			}
			g.drawRect(x, y, w - 2, h - 2);
			final Composite composite = g2D.getComposite();
			final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			g2D.setComposite(alpha);
			g.setColor(Color.white);
			g.drawLine(x + w - 1, y + 1, x + w - 1, y + h);
			g.drawLine(x + 1, y + h - 1, x + w, y + h - 1);
			g2D.setComposite(composite);
		}

	} // class ButtonBorder

	public static class InternalFrameBorder extends BaseInternalFrameBorder {

		private static final long serialVersionUID = 1L;

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int borderW, final int borderH) {
			int w = borderW;
			int h = borderH;

			Color borderColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
			if (isActive(c)) {
				borderColor = AbstractLookAndFeel.getWindowBorderColor();
			}
			if (!isResizable(c)) {
				Color cHi = ColorHelper.brighter(borderColor, 40);
				Color cLo = ColorHelper.darker(borderColor, 40);
				JTattooUtilities.draw3DBorder(g, cHi, cLo, x, y, w, h);
				cHi = ColorHelper.darker(cHi, 20);
				cLo = ColorHelper.brighter(cLo, 20);
				JTattooUtilities.draw3DBorder(g, cHi, cLo, x + 1, y + 1, w - 2, h - 2);
				g.setColor(borderColor);
				for (int i = 2; i < DW; i++) {
					g.drawRect(i, i, w - 2 * i - 1, h - 2 * i - 1);
				}
			} else {
				final int dt = w / 3;
				final int db = w * 2 / 3;
				h--;
				w--;

				final Color cl = ColorHelper.brighter(borderColor, 10);
				final Color cr = AbstractLookAndFeel.getWindowInactiveBorderColor();
				g.setColor(cl);
				g.drawLine(x, y, x, y + h);
				g.setColor(ColorHelper.brighter(cl, 60));
				g.drawLine(x + 1, y + 1, x + 1, y + h - 1);
				g.setColor(ColorHelper.brighter(cl, 40));
				g.drawLine(x + 2, y + 2, x + 2, y + h - 2);
				g.setColor(ColorHelper.brighter(cl, 20));
				g.drawLine(x + 3, y + 3, x + 3, y + h - 3);
				g.setColor(cl);
				g.drawLine(x + 4, y + 4, x + 4, y + h - 4);

				// rechts
				g.setColor(cr);
				g.drawLine(x + w, y, x + w, y + h);
				g.setColor(ColorHelper.brighter(cr, 30));
				g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
				g.setColor(ColorHelper.brighter(cr, 60));
				g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 2);
				g.setColor(ColorHelper.brighter(cr, 90));
				g.drawLine(x + w - 3, y + 3, x + w - 3, y + h - 3);
				g.setColor(cr);
				g.drawLine(x + w - 4, y + 4, x + w - 4, y + h - 4);

				g.setColor(cl);
				g.drawLine(x + w, y, x + w, y + TRACK_WIDTH);
				g.setColor(ColorHelper.brighter(cl, 20));
				g.drawLine(x + w - 1, y + 1, x + w - 1, y + TRACK_WIDTH);
				g.setColor(ColorHelper.brighter(cl, 40));
				g.drawLine(x + w - 2, y + 2, x + w - 2, y + TRACK_WIDTH);
				g.setColor(ColorHelper.brighter(cl, 60));
				g.drawLine(x + w - 3, y + 3, x + w - 3, y + TRACK_WIDTH);
				g.setColor(cl);
				g.drawLine(x + w - 4, y + 4, x + w - 4, y + TRACK_WIDTH);

				g.setColor(cl);
				g.drawLine(x + w, y + h - TRACK_WIDTH, x + w, y + h);
				g.setColor(ColorHelper.brighter(cl, 20));
				g.drawLine(x + w - 1, y + h - TRACK_WIDTH, x + w - 1, y + h - 1);
				g.setColor(ColorHelper.brighter(cl, 40));
				g.drawLine(x + w - 2, y + h - TRACK_WIDTH, x + w - 2, y + h - 2);
				g.setColor(ColorHelper.brighter(cl, 60));
				g.drawLine(x + w - 3, y + h - TRACK_WIDTH, x + w - 3, y + h - 3);
				g.setColor(cl);
				g.drawLine(x + w - 4, y + h - TRACK_WIDTH, x + w - 4, y + h - 4);

				// oben
				g.setColor(cl);
				g.drawLine(x, y, x + dt, y);
				g.setColor(ColorHelper.brighter(cl, 60));
				g.drawLine(x + 1, y + 1, x + dt, y + 1);
				g.setColor(ColorHelper.brighter(cl, 40));
				g.drawLine(x + 2, y + 2, x + dt, y + 2);
				g.setColor(ColorHelper.brighter(cl, 20));
				g.drawLine(x + 3, y + 3, x + dt, y + 3);
				g.setColor(cl);
				g.drawLine(x + 4, y + 4, x + dt, y + 4);

				g.setColor(cr);
				g.drawLine(x + dt, y, x + w, y);
				g.setColor(ColorHelper.brighter(cr, 90));
				g.drawLine(x + dt, y + 1, x + w - 1, y + 1);
				g.setColor(ColorHelper.brighter(cr, 60));
				g.drawLine(x + dt, y + 2, x + w - 2, y + 2);
				g.setColor(ColorHelper.brighter(cr, 30));
				g.drawLine(x + dt, y + 3, x + w - 3, y + 3);
				if (isActive(c)) {
					g.setColor(ColorHelper.darker(cr, 15));
				} else {
					g.setColor(cr);
				}
				g.drawLine(x + dt, y + 4, x + w - 4, y + 4);

				g.setColor(cl);
				g.drawLine(x + w - TRACK_WIDTH, y, x + w, y);
				g.setColor(ColorHelper.brighter(cl, 60));
				g.drawLine(x + w - TRACK_WIDTH, y + 1, x + w - 1, y + 1);
				g.setColor(ColorHelper.brighter(cl, 40));
				g.drawLine(x + w - TRACK_WIDTH, y + 2, x + w - 2, y + 2);
				g.setColor(ColorHelper.brighter(cl, 20));
				g.drawLine(x + w - TRACK_WIDTH, y + 3, x + w - 3, y + 3);
				g.setColor(cl);
				g.drawLine(x + w - TRACK_WIDTH, y + 4, x + w - 4, y + 4);

				// unten
				g.setColor(cl);
				g.drawLine(x, y + h, x + db, y + h);
				g.setColor(ColorHelper.brighter(cl, 20));
				g.drawLine(x + 1, y + h - 1, x + db, y + h - 1);
				g.setColor(ColorHelper.brighter(cl, 40));
				g.drawLine(x + 2, y + h - 2, x + db, y + h - 2);
				g.setColor(ColorHelper.brighter(cl, 60));
				g.drawLine(x + 3, y + h - 3, x + db, y + h - 3);
				g.setColor(cl);
				g.drawLine(x + 4, y + h - 4, x + db, y + h - 4);

				g.setColor(cr);
				g.drawLine(x + db, y + h, x + w, y + h);
				g.setColor(ColorHelper.brighter(cr, 30));
				g.drawLine(x + db, y + h - 1, x + w - 1, y + h - 1);
				g.setColor(ColorHelper.brighter(cr, 60));
				g.drawLine(x + db, y + h - 2, x + w - 2, y + h - 2);
				g.setColor(ColorHelper.brighter(cr, 90));
				g.drawLine(x + db, y + h - 3, x + w - 3, y + h - 3);
				g.setColor(cr);
				g.drawLine(x + db, y + h - 4, x + w - 4, y + h - 4);

				g.setColor(cl);
				g.drawLine(x + w - TRACK_WIDTH, y + h, x + w, y + h);
				g.setColor(ColorHelper.brighter(cl, 20));
				g.drawLine(x + w - TRACK_WIDTH, y + h - 1, x + w - 1, y + h - 1);
				g.setColor(ColorHelper.brighter(cl, 40));
				g.drawLine(x + w - TRACK_WIDTH, y + h - 2, x + w - 2, y + h - 2);
				g.setColor(ColorHelper.brighter(cl, 60));
				g.drawLine(x + w - TRACK_WIDTH, y + h - 3, x + w - 3, y + h - 3);
				g.setColor(cl);
				g.drawLine(x + w - TRACK_WIDTH, y + h - 4, x + w - 4, y + h - 4);
			} // paintBorder
		}
	} // end of class InternalFrameBorder

	public static class RolloverToolButtonBorder implements Border, UIResource {

		private static final Insets insets = new Insets(1, 1, 1, 1);

		@Override
		public Insets getBorderInsets(final Component c) {
			return new Insets(insets.top, insets.left, insets.bottom, insets.right);
		}

		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
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

	} // class RolloverToolButtonBorder

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

} // end of class AeroBorders
