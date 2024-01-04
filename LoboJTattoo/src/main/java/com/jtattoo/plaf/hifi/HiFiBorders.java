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
package com.jtattoo.plaf.hifi;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseBorders;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.LazyImageIcon;

/**
 * <p>HiFiBorders class.</p>
 *
 * Author Michael Hagen
 *
 */
public class HiFiBorders extends BaseBorders {

	// ------------------------------------------------------------------------------------
// Implementation of border classes
//------------------------------------------------------------------------------------
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
			final Graphics2D g2D = (Graphics2D) g;
			g.translate(x, y);

			final Color hiFrameColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getButtonBackgroundColor(), 14);
			final Color frameColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getButtonBackgroundColor(), 6);
			final Color loFrameColor = ColorHelper.darker(AbstractLookAndFeel.getTheme().getButtonBackgroundColor(), 50);

			g.setColor(hiFrameColor);
			g.drawLine(1, 0, w - 3, 0);
			g.drawLine(0, 1, 0, h - 3);
			g.setColor(frameColor);
			g.drawLine(w - 2, 0, w - 2, h - 2);
			g.drawLine(1, h - 2, w - 3, h - 2);

			final Composite composite = g2D.getComposite();
			AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			g2D.setComposite(alpha);
			g2D.setColor(loFrameColor);
			g.drawLine(1, 1, w - 3, 1);
			g.drawLine(1, 2, 1, h - 3);
			g.setColor(Color.black);
			g.drawLine(w - 1, 1, w - 1, h - 1);
			g.drawLine(1, h - 1, w - 1, h - 1);
			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
			g2D.setComposite(alpha);
			g.drawLine(1, h - 2, 2, h - 1);
			g2D.setComposite(composite);

			g.translate(-x, -y);
		}

	} // end of class ButtonBorder

	// -------------------------------------------------------------------------------------------------
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
			Color titleColor = AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors()[0];
			Color borderColor = AbstractLookAndFeel.getWindowInactiveTitleColorDark();
			Color frameColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
			if (active) {
				titleColor = AbstractLookAndFeel.getTheme().getWindowTitleColors()[0];
				borderColor = AbstractLookAndFeel.getWindowTitleColorDark();
				frameColor = AbstractLookAndFeel.getWindowBorderColor();
			}
			g.setColor(titleColor);
			g.fillRect(x, y + 1, w, INSETS.top - 1);
			g.setColor(borderColor);
			g.fillRect(x + 1, y + h - DW, w - 2, DW - 1);
			if (active) {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 1,
						INSETS.top, DW, th + 1);
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), w - DW,
						INSETS.top, DW, th + 1);
			} else {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 1,
						INSETS.top, DW - 1, th + 1);
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(),
						w - DW, INSETS.top, DW - 1, th + 1);
			}
			g.setColor(borderColor);
			g.fillRect(1, INSETS.top + th + 1, DW - 1, h - th - DW);
			g.fillRect(w - DW, INSETS.top + th + 1, DW - 1, h - th - DW);
			g.setColor(frameColor);
			g.drawRect(x, y, w - 1, h - 1);
		}

	} // end of class InternalFrameBorder

	// -------------------------------------------------------------------------------------------------
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
			final Graphics2D g2D = (Graphics2D) g;
			final Composite composite = g2D.getComposite();
			final Color c1;
			final Color c2;
			if (JTattooUtilities.isActive((JComponent) c)) {
				c1 = ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 60);
				c2 = AbstractLookAndFeel.getFrameColor();
			} else {
				c1 = AbstractLookAndFeel.getFrameColor();
				c2 = ColorHelper.darker(AbstractLookAndFeel.getFrameColor(), 20);
			}
			AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
			g2D.setComposite(alpha);
			JTattooUtilities.draw3DBorder(g, c1, c2, 0, 0, w, h);
			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
			g2D.setComposite(alpha);
			JTattooUtilities.draw3DBorder(g, c2, c1, 1, 1, w - 2, h - 2);
			g2D.setComposite(composite);
		}

	} // end of class RolloverToolButtonBorder

	// -------------------------------------------------------------------------------------------------
	public static class ScrollPaneBorder implements Border, UIResource {

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
			final Color frameColor = AbstractLookAndFeel.getTheme().getFrameColor();
			JTattooUtilities.draw3DBorder(g, frameColor, ColorHelper.brighter(frameColor, 10), x, y, w, h);
		}

	} // end of class ScrollPaneBorder

	// -------------------------------------------------------------------------------------------------
	public static class TabbedPaneBorder implements Border, UIResource {

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
			final Color frameColor = AbstractLookAndFeel.getTheme().getFrameColor();
			JTattooUtilities.draw3DBorder(g, frameColor, ColorHelper.brighter(frameColor, 10), x, y, w, h);
		}

	} // end of class TabbedPaneBorder

	public static class ToolBarBorder extends AbstractBorder implements UIResource, SwingConstants {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final LazyImageIcon HOR_RUBBER_ICON = new LazyImageIcon("hifi/icons/HorRubber.gif");
		private static final LazyImageIcon VER_RUBBER_ICON = new LazyImageIcon("hifi/icons/VerRubber.gif");

		@Override
		public Insets getBorderInsets(final Component c) {
			final Insets insets = new Insets(2, 2, 2, 2);
			if (((JToolBar) c).isFloatable()) {
				if (((JToolBar) c).getOrientation() == HORIZONTAL) {
					if (JTattooUtilities.isLeftToRight(c)) {
						insets.left = 15;
					} else {
						insets.right = 15;
					}
				} else {
					insets.top = 15;
				}
			}
			final Insets margin = ((JToolBar) c).getMargin();
			if (margin != null) {
				insets.left += margin.left;
				insets.top += margin.top;
				insets.right += margin.right;
				insets.bottom += margin.bottom;
			}
			return insets;
		}

		@Override
		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
			final Insets insets = getBorderInsets(c);
			borderInsets.left = insets.left;
			borderInsets.top = insets.top;
			borderInsets.right = insets.right;
			borderInsets.bottom = insets.bottom;
			return borderInsets;
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			if (((JToolBar) c).isFloatable()) {
				if (((JToolBar) c).getOrientation() == HORIZONTAL) {
					final int x1 = 4;
					final int y1 = (h - HOR_RUBBER_ICON.getIconHeight()) / 2;
					HOR_RUBBER_ICON.paintIcon(c, g, x1, y1);
				} else {
					final int x1 = (w - VER_RUBBER_ICON.getIconWidth()) / 2 + 2;
					final int y1 = 4;
					VER_RUBBER_ICON.paintIcon(c, g, x1, y1);
				}
			}
		}
	} // end of class ToolBarBorder

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
	 * <p>getScrollPaneBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getScrollPaneBorder() {
		if (scrollPaneBorder == null) {
			scrollPaneBorder = new ScrollPaneBorder();
		}
		return scrollPaneBorder;
	}

	/**
	 * <p>getTableScrollPaneBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getTableScrollPaneBorder() {
		if (tableScrollPaneBorder == null) {
			tableScrollPaneBorder = new ScrollPaneBorder();
		}
		return tableScrollPaneBorder;
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
	 * <p>getToolBarBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getToolBarBorder() {
		if (toolBarBorder == null) {
			toolBarBorder = new ToolBarBorder();
		}
		return toolBarBorder;
	}

} // end of class HiFiBorders
