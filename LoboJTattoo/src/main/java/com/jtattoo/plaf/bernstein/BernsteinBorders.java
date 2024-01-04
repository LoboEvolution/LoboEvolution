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
package com.jtattoo.plaf.bernstein;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseBorders;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>BernsteinBorders class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BernsteinBorders extends BaseBorders {

	// ------------------------------------------------------------------------------------
	// Implementation of border classes
	// ------------------------------------------------------------------------------------
	public static class ButtonBorder implements Border, UIResource {

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
			final Color cHi = MetalLookAndFeel.getControlDarkShadow();
			final Color cLo = ColorHelper.darker(cHi, 8);
			JTattooUtilities.draw3DBorder(g, cHi, cLo, x, y, w, h);
		}

	} // end of class ButtonBorder

	public static class InternalFrameBorder extends BaseInternalFrameBorder {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Color BORDER_COLOR = new Color(255, 244, 128);
		private static final Color[] FRAME_COLORS = { new Color(229, 187, 0), new Color(251, 232, 0),
				new Color(247, 225, 0), new Color(243, 216, 0), new Color(229, 187, 0), };

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int wBorder, final int hBorder) {
			int w = wBorder;
			int h = hBorder;

			if (!isResizable(c)) {
				Color cHi = ColorHelper.brighter(BORDER_COLOR, 40);
				Color cLo = ColorHelper.darker(BORDER_COLOR, 20);
				JTattooUtilities.draw3DBorder(g, cHi, cLo, x, y, w, h);
				cHi = ColorHelper.darker(cHi, 20);
				cLo = ColorHelper.brighter(cLo, 20);
				JTattooUtilities.draw3DBorder(g, cHi, cLo, x + 1, y + 1, w - 2, h - 2);
				g.setColor(BORDER_COLOR);
				for (int i = 2; i < DW; i++) {
					g.drawRect(i, i, w - 2 * i - 1, h - 2 * i - 1);
				}
				return;
			}
			final int dt = w / 3;
			final int db = w * 2 / 3;
			h--;
			w--;

			final Color cr = BORDER_COLOR;
			g.setColor(FRAME_COLORS[0]);
			g.drawLine(x, y, x, y + h);
			g.setColor(FRAME_COLORS[1]);
			g.drawLine(x + 1, y + 1, x + 1, y + h - 1);
			g.setColor(FRAME_COLORS[2]);
			g.drawLine(x + 2, y + 2, x + 2, y + h - 2);
			g.setColor(FRAME_COLORS[3]);
			g.drawLine(x + 3, y + 3, x + 3, y + h - 3);
			g.setColor(FRAME_COLORS[4]);
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

			g.setColor(FRAME_COLORS[0]);
			g.drawLine(x + w, y, x + w, y + TRACK_WIDTH);
			g.setColor(FRAME_COLORS[1]);
			g.drawLine(x + w - 1, y + 1, x + w - 1, y + TRACK_WIDTH);
			g.setColor(FRAME_COLORS[2]);
			g.drawLine(x + w - 2, y + 2, x + w - 2, y + TRACK_WIDTH);
			g.setColor(FRAME_COLORS[3]);
			g.drawLine(x + w - 3, y + 3, x + w - 3, y + TRACK_WIDTH);
			g.setColor(FRAME_COLORS[4]);
			g.drawLine(x + w - 4, y + 4, x + w - 4, y + TRACK_WIDTH);

			g.setColor(FRAME_COLORS[0]);
			g.drawLine(x + w, y + h - TRACK_WIDTH, x + w, y + h);
			g.setColor(FRAME_COLORS[1]);
			g.drawLine(x + w - 1, y + h - TRACK_WIDTH, x + w - 1, y + h - 1);
			g.setColor(FRAME_COLORS[2]);
			g.drawLine(x + w - 2, y + h - TRACK_WIDTH, x + w - 2, y + h - 2);
			g.setColor(FRAME_COLORS[3]);
			g.drawLine(x + w - 3, y + h - TRACK_WIDTH, x + w - 3, y + h - 3);
			g.setColor(FRAME_COLORS[4]);
			g.drawLine(x + w - 4, y + h - TRACK_WIDTH, x + w - 4, y + h - 4);
			// oben
			g.setColor(FRAME_COLORS[0]);
			g.drawLine(x, y, x + dt, y);
			g.setColor(FRAME_COLORS[1]);
			g.drawLine(x + 1, y + 1, x + dt, y + 1);
			g.setColor(FRAME_COLORS[2]);
			g.drawLine(x + 2, y + 2, x + dt, y + 2);
			g.setColor(FRAME_COLORS[3]);
			g.drawLine(x + 3, y + 3, x + dt, y + 3);
			g.setColor(FRAME_COLORS[4]);
			g.drawLine(x + 4, y + 4, x + dt, y + 4);

			g.setColor(cr);
			g.drawLine(x + dt, y, x + w, y);
			g.setColor(ColorHelper.brighter(cr, 90));
			g.drawLine(x + dt, y + 1, x + w - 1, y + 1);
			g.setColor(ColorHelper.brighter(cr, 60));
			g.drawLine(x + dt, y + 2, x + w - 2, y + 2);
			g.setColor(ColorHelper.brighter(cr, 30));
			g.drawLine(x + dt, y + 3, x + w - 3, y + 3);
			g.setColor(cr);
			g.drawLine(x + dt, y + 4, x + w - 4, y + 4);

			g.setColor(FRAME_COLORS[0]);
			g.drawLine(x + w - TRACK_WIDTH, y, x + w, y);
			g.setColor(FRAME_COLORS[1]);
			g.drawLine(x + w - TRACK_WIDTH, y + 1, x + w - 1, y + 1);
			g.setColor(FRAME_COLORS[2]);
			g.drawLine(x + w - TRACK_WIDTH, y + 2, x + w - 2, y + 2);
			g.setColor(FRAME_COLORS[3]);
			g.drawLine(x + w - TRACK_WIDTH, y + 3, x + w - 3, y + 3);
			g.setColor(FRAME_COLORS[4]);
			g.drawLine(x + w - TRACK_WIDTH, y + 4, x + w - 4, y + 4);

			// unten
			g.setColor(FRAME_COLORS[0]);
			g.drawLine(x, y + h, x + db, y + h);
			g.setColor(FRAME_COLORS[1]);
			g.drawLine(x + 1, y + h - 1, x + db, y + h - 1);
			g.setColor(FRAME_COLORS[2]);
			g.drawLine(x + 2, y + h - 2, x + db, y + h - 2);
			g.setColor(FRAME_COLORS[3]);
			g.drawLine(x + 3, y + h - 3, x + db, y + h - 3);
			g.setColor(FRAME_COLORS[4]);
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

			g.setColor(FRAME_COLORS[0]);
			g.drawLine(x + w - TRACK_WIDTH, y + h, x + w, y + h);
			g.setColor(FRAME_COLORS[1]);
			g.drawLine(x + w - TRACK_WIDTH, y + h - 1, x + w - 1, y + h - 1);
			g.setColor(FRAME_COLORS[2]);
			g.drawLine(x + w - TRACK_WIDTH, y + h - 2, x + w - 2, y + h - 2);
			g.setColor(FRAME_COLORS[3]);
			g.drawLine(x + w - TRACK_WIDTH, y + h - 3, x + w - 3, y + h - 3);
			g.setColor(FRAME_COLORS[4]);
			g.drawLine(x + w - TRACK_WIDTH, y + h - 4, x + w - 4, y + h - 4);
		}
	} // end of class InternalFrameBorder

	public static class RolloverToolButtonBorder implements Border, UIResource {

		private static final Color FRAME_HI_COLOR = ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 60);
		private static final Color FRAME_LO_COLOR = AbstractLookAndFeel.getFrameColor();
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
				if (model.isRollover()) {
					JTattooUtilities.draw3DBorder(g, FRAME_HI_COLOR, FRAME_LO_COLOR, x, y, w - 1, h);
					JTattooUtilities.draw3DBorder(g, Color.white, FRAME_HI_COLOR, x + 1, y + 1, w - 2, h - 2);
				} else if (model.isPressed() && model.isArmed() || model.isSelected()) {
					g.setColor(FRAME_HI_COLOR);
					g.drawRect(x, y, w - 2, h - 1);
				} else {
					g.setColor(AbstractLookAndFeel.getFrameColor());
					g.drawRect(x, y, w - 2, h - 1);
				}
			} else {
				g.setColor(FRAME_HI_COLOR);
				g.drawRect(x, y, w - 2, h - 1);
			}
			g.setColor(Color.white);
			g.drawLine(w - 1, 0, w - 1, h - 1);
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

} // end of class BernsteinBorders
