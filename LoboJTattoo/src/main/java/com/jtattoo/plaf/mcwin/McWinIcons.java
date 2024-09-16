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
package com.jtattoo.plaf.mcwin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.plaf.UIResource;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseIcons;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.LazyImageIcon;

/**
 * <p>McWinIcons class.</p>
 *
 * Author Michael Hagen
 *
 */
public class McWinIcons extends BaseIcons {

	// --------------------------------------------------------------------------------------------------------
	private static class CheckBoxIcon implements Icon, UIResource, Serializable {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final int GAP = 4;
		private static final Icon SMALL_CHECK_ICON = new LazyImageIcon("mcwin/icons/small/check_symbol_16x11.png");
		private static final Icon SMALL_CHECK_DISABLED_ICON = new LazyImageIcon(
				"mcwin/icons/small/check_symbol_disabled_16x11.png");
		private static final Icon MEDIUM_CHECK_ICON = new LazyImageIcon("mcwin/icons/medium/check_symbol_18x13.png");
		private static final Icon MEDIUM_CHECK_DISABLED_ICON = new LazyImageIcon(
				"mcwin/icons/medium/check_symbol_disabled_18x13.png");
		private static final Icon LARGE_CHECK_ICON = new LazyImageIcon("mcwin/icons/large/check_symbol_20x15.png");
		private static final Icon LARGE_CHECK_DISABLED_ICON = new LazyImageIcon(
				"mcwin/icons/large/check_symbol_disabled_20x15.png");

		@Override
		public int getIconHeight() {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				return 13;
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				return 15;
			} else {
				return 17;
			}
		}

		@Override
		public int getIconWidth() {
			final int w;
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				w = 13;
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				w = 15;
			} else {
				w = 17;
			}
			return w + GAP;
		}

		@Override
		public void paintIcon(final Component c, final Graphics g, final int iconX, final int y) {
int x = iconX;
			if (!JTattooUtilities.isLeftToRight(c)) {
				x += GAP;
			}
			final int w = getIconWidth() - GAP;
			final int h = getIconHeight();
			final AbstractButton button = (AbstractButton) c;
			final ButtonModel model = button.getModel();
			final Color[] colors;
			Color frameColor = AbstractLookAndFeel.getFrameColor();
			if (button.isEnabled()) {
				if (button.isRolloverEnabled() && model.isRollover()) {
					colors = AbstractLookAndFeel.getTheme().getRolloverColors();
				} else if (!JTattooUtilities.isFrameActive(button)) {
					colors = AbstractLookAndFeel.getTheme().getInActiveColors();
				} else if (button.isSelected()) {
					colors = AbstractLookAndFeel.getTheme().getDefaultColors();
				} else {
					colors = AbstractLookAndFeel.getTheme().getCheckBoxColors();
				}
			} else {
				colors = AbstractLookAndFeel.getTheme().getDisabledColors();
				frameColor = ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 20);
			}
			JTattooUtilities.fillHorGradient(g, colors, x + 1, y + 1, w - 1, h - 1);
			g.setColor(frameColor);
			g.drawRect(x, y, w, h);

			if (button.isEnabled() && !model.isRollover() && !model.isPressed() && !model.isSelected()) {
				g.setColor(Color.white);
				g.drawLine(x + 1, y + 1, x + 1, y + h - 2);
				g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 2);
			}

			final Icon checkIcon;
			final Icon checkDisabledIcon;
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				checkIcon = SMALL_CHECK_ICON;
				checkDisabledIcon = SMALL_CHECK_DISABLED_ICON;
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				checkIcon = MEDIUM_CHECK_ICON;
				checkDisabledIcon = MEDIUM_CHECK_DISABLED_ICON;
			} else {
				checkIcon = LARGE_CHECK_ICON;
				checkDisabledIcon = LARGE_CHECK_DISABLED_ICON;
			}
			if (model.isPressed() && model.isArmed()) {
				final Color bc = AbstractLookAndFeel.getTheme().getSelectionBackgroundColor();
				final Color fc = ColorHelper.darker(bc, 40);
				g.setColor(fc);
				g.drawRect(x + 3, y + 3, w - 6, h - 6);
				g.setColor(bc);
				g.fillRect(x + 4, y + 4, w - 7, h - 7);
			} else if (model.isSelected()) {
				final int xi = x + (w - checkIcon.getIconWidth()) / 2 + 4;
				final int yi = y + (h - checkIcon.getIconHeight()) / 2;
				if (model.isEnabled()) {
					checkIcon.paintIcon(c, g, xi, yi);
				} else {
					checkDisabledIcon.paintIcon(c, g, xi, yi);
				}
			}
		}
	}

	private static class RadioButtonIcon implements Icon, UIResource, Serializable {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final int GAP = 2;

		@Override
		public int getIconHeight() {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				return 14;
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				return 16;
			} else {
				return 18;
			}
		}

		@Override
		public int getIconWidth() {
			final int w;
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				w = 14;
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				w = 16;
			} else {
				w = 18;
			}
			return w + GAP;
		}

		@Override
		public void paintIcon(final Component c, final Graphics g, final int iconX, final int y) {
int x = iconX;
			if (!JTattooUtilities.isLeftToRight(c)) {
				x += GAP;
			}
			final int w = getIconWidth() - GAP;
			final int h = getIconHeight();
			final Graphics2D g2D = (Graphics2D) g;
			final AbstractButton button = (AbstractButton) c;
			final ButtonModel model = button.getModel();
			final Color[] colors;
			if (button.isEnabled()) {
				if (button.isRolloverEnabled() && model.isRollover() || model.isPressed() && model.isArmed()) {
					colors = AbstractLookAndFeel.getTheme().getRolloverColors();
				} else if (!JTattooUtilities.isFrameActive(button)) {
					colors = AbstractLookAndFeel.getTheme().getInActiveColors();
				} else if (button.isSelected()) {
					colors = AbstractLookAndFeel.getTheme().getDefaultColors();
				} else {
					colors = AbstractLookAndFeel.getTheme().getCheckBoxColors();
				}
			} else {
				colors = AbstractLookAndFeel.getTheme().getDisabledColors();
			}

			final Shape savedClip = g.getClip();
			final Area clipArea = new Area(new Ellipse2D.Double(x, y, w + 1, h + 1));
			if (savedClip != null) {
				clipArea.intersect(new Area(savedClip));
			}
			g2D.setClip(clipArea);
			JTattooUtilities.fillHorGradient(g, colors, x, y, w, h);
			g2D.setClip(savedClip);

			if (button.isEnabled()) {
				g2D.setColor(AbstractLookAndFeel.getFrameColor());
			} else {
				g2D.setColor(ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 20));
			}
			final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.drawOval(x, y, w, h);

			if (model.isSelected()) {
				if (model.isEnabled()) {
					final int gv = ColorHelper.getGrayValue(colors[0]);
					if (gv > 128) {
						g.setColor(Color.black);
					} else {
						g.setColor(Color.white);
					}
				} else {
					g.setColor(AbstractLookAndFeel.getTheme().getDisabledForegroundColor());
				}
				if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
					g2D.fillOval(x + 4, y + 4, w - 7, h - 7);
				} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
					g2D.fillOval(x + 4, y + 4, w - 7, h - 7);
				} else {
					g2D.fillOval(x + 5, y + 5, w - 9, h - 9);
				}
			}
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
		}
	}

	private static class ThumbIcon implements Icon, UIResource, Serializable {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final  int WIDTH = 15;
		private static final  int HEIGHT = 15;
		private boolean isRollover = false;

		public ThumbIcon(final boolean isRollover) {
			this.isRollover = isRollover;
		}

		@Override
		public int getIconHeight() {
			return HEIGHT + 2;
		}

		@Override
		public int getIconWidth() {
			return WIDTH + 2;
		}

		@Override
		public void paintIcon(final Component c, final Graphics g, final int iconX, final int y) {
			final int x = iconX;
			final Graphics2D g2D = (Graphics2D) g;
			final Color[] colors;
			if (isRollover) {
				colors = AbstractLookAndFeel.getTheme().getRolloverColors();
			} else {
				if (AbstractLookAndFeel.getTheme().isBrightMode()) {
					colors = AbstractLookAndFeel.getTheme().getButtonColors();
				} else {
					colors = AbstractLookAndFeel.getTheme().getSelectedColors();
				}
			}

			final Shape savedClip = g2D.getClip();
			if (savedClip != null) {
				final Area clipArea = new Area(new Ellipse2D.Double(x + 1, y + 1, WIDTH, HEIGHT));
				clipArea.intersect(new Area(savedClip));
				g2D.setClip(clipArea);
				JTattooUtilities.fillHorGradient(g, colors, x + 1, y + 1, WIDTH, HEIGHT);
				g2D.setClip(savedClip);
			} else {
				final Area ellipseArea = new Area(new Ellipse2D.Double(x + 1, y + 1, WIDTH, HEIGHT));
				g2D.setClip(ellipseArea);
				JTattooUtilities.fillHorGradient(g, colors, x, y, WIDTH, HEIGHT);
				g2D.setClip(null);
			}
			g2D.setColor(AbstractLookAndFeel.getFrameColor());
			final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.drawOval(x + 1, y + 1, WIDTH - 1, HEIGHT - 1);
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
		}
	}

	/**
	 * <p>getCheckBoxIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getCheckBoxIcon() {
		if (checkBoxIcon == null) {
			checkBoxIcon = new CheckBoxIcon();
		}
		return checkBoxIcon;
	}

	/**
	 * <p>getCloseIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getCloseIcon() {
		if (closeIcon == null) {
			closeIcon = new MacCloseIcon();
		}
		return closeIcon;
	}

	/**
	 * <p>getIconIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getIconIcon() {
		if (iconIcon == null) {
			iconIcon = new MacIconIcon();
		}
		return iconIcon;
	}

	/**
	 * <p>getMaxIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getMaxIcon() {
		if (maxIcon == null) {
			maxIcon = new MacMaxIcon();
		}
		return maxIcon;
	}

	/**
	 * <p>getMinIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getMinIcon() {
		if (minIcon == null) {
			minIcon = new MacMinIcon();
		}
		return minIcon;
	}

	/**
	 * <p>getRadioButtonIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getRadioButtonIcon() {
		if (radioButtonIcon == null) {
			radioButtonIcon = new RadioButtonIcon();
		}
		return radioButtonIcon;
	}

	/**
	 * <p>getThumbHorIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbHorIcon() {
		if (thumbHorIcon == null) {
			thumbHorIcon = new ThumbIcon(false);
		}
		return thumbHorIcon;
	}

	/**
	 * <p>getThumbHorIconRollover.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbHorIconRollover() {
		if (thumbHorIconRollover == null) {
			thumbHorIconRollover = new ThumbIcon(true);
		}
		return thumbHorIconRollover;
	}

	/**
	 * <p>getThumbVerIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbVerIcon() {
		if (thumbVerIcon == null) {
			thumbVerIcon = new ThumbIcon(false);
		}
		return thumbVerIcon;
	}

	/**
	 * <p>getThumbVerIconRollover.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbVerIconRollover() {
		if (thumbVerIconRollover == null) {
			thumbVerIconRollover = new ThumbIcon(true);
		}
		return thumbVerIconRollover;
	}

} // end of class McWinIcons
