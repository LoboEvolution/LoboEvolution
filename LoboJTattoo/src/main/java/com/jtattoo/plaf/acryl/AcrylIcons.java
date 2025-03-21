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

package com.jtattoo.plaf.acryl;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.plaf.ColorUIResource;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseIcons;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.LazyImageIcon;

/**
 * <p>AcrylIcons class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AcrylIcons extends BaseIcons {

	// ----------------------------------------------------------------------------------------------------------------------
	private final static class CheckBoxIcon implements Icon {

		private static final int GAP = 2;

		private static final Icon SMALL_CHECK_ICON = new LazyImageIcon("acryl/icons/small/check_symbol_12x11.png");
		private static final Icon SMALL_CHECK_INVERSE_ICON = new LazyImageIcon("icons/small/check_symbol_10x10.png");
		// private static final Icon SMALL_CHECK_PRESSED_ICON = new
		// LazyImageIcon("acryl/icons/small/check_symbol_pressed_10x10.png");
		private static final Icon SMALL_CHECK_DISABLED_ICON = new LazyImageIcon(
				"icons/small/check_symbol_disabled_10x10.png");
		private static final Icon MEDIUM_CHECK_ICON = new LazyImageIcon("acryl/icons/medium/check_symbol_14x13.png");
		private static final Icon MEDIUM_CHECK_INVERSE_ICON = new LazyImageIcon("icons/medium/check_symbol_12x12.png");
		// private static final Icon MEDIUM_CHECK_PRESSED_ICON = new
		// LazyImageIcon("acryl/icons/medium/check_symbol_pressed_12x12.png");
		private static final Icon MEDIUM_CHECK_DISABLED_ICON = new LazyImageIcon(
				"icons/medium/check_symbol_disabled_12x12.png");
		private static final Icon LARGE_CHECK_ICON = new LazyImageIcon("acryl/icons/large/check_symbol_16x15.png");
		private static final Icon LARGE_CHECK_INVERSE_ICON = new LazyImageIcon("icons/large/check_symbol_14x14.png");
		// private static final Icon LARGE_CHECK_PRESSED_ICON = new
		// LazyImageIcon("acryl/icons/large/check_symbol_pressed_14x14.png");
		private static final Icon LARGE_CHECK_DISABLED_ICON = new LazyImageIcon(
				"icons/large/check_symbol_disabled_14x14.png");

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
			final AbstractButton button = (AbstractButton) c;
			final ButtonModel model = button.getModel();
			Color frameColor = AbstractLookAndFeel.getFrameColor();
			if (button.isEnabled()) {
				if (button.isRolloverEnabled() && model.isRollover()) {
					JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getRolloverColors(), x + 1,
							y + 1, w - 2, h - 2);
					frameColor = ColorHelper.brighter(frameColor, 30);
				} else {
					if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
						JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getFocusColors(), x + 1,
								y + 1, w - 2, h - 2);
					} else {
						JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getCheckBoxColors(), x + 1,
								y + 1, w - 2, h - 2);
					}
				}
			} else {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getDisabledColors(), x + 1, y + 1,
						w - 2, h - 2);
				frameColor = ColorHelper.brighter(frameColor, 40);
			}

			g.setColor(AbstractLookAndFeel.getTheme().getControlShadowColor());
			g.drawRect(x, y, w - 1, h - 1);
			g.setColor(frameColor);
			g.drawLine(x + 1, y, x + w - 2, y);
			g.drawLine(x + 1, y + h - 1, x + w - 2, y + h - 1);
			g.drawLine(x, y + 1, x, y + h - 2);
			g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 2);

			final Icon checkIcon;
			final Icon checkDisabledIcon;
			final Icon checkInverseIcon;
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				checkIcon = SMALL_CHECK_ICON;
				checkDisabledIcon = SMALL_CHECK_DISABLED_ICON;
				checkInverseIcon = SMALL_CHECK_INVERSE_ICON;
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				checkIcon = MEDIUM_CHECK_ICON;
				checkDisabledIcon = MEDIUM_CHECK_DISABLED_ICON;
				checkInverseIcon = MEDIUM_CHECK_INVERSE_ICON;
			} else {
				checkIcon = LARGE_CHECK_ICON;
				checkDisabledIcon = LARGE_CHECK_DISABLED_ICON;
				checkInverseIcon = LARGE_CHECK_INVERSE_ICON;
			}
			final int gv = model.isRollover()
					? ColorHelper.getGrayValue(AbstractLookAndFeel.getTheme().getRolloverColorDark())
					: ColorHelper.getGrayValue(AbstractLookAndFeel.getTheme().getControlColorDark());
			final int xi = x + (w - checkIcon.getIconWidth()) / 2;
			final int yi = y + (h - checkIcon.getIconHeight()) / 2;
			if (model.isPressed() && model.isArmed()) {
				final Color bc = gv > 128 ? AbstractLookAndFeel.getTheme().getSelectionBackgroundColor()
						: AbstractLookAndFeel.getTheme().getSelectionForegroundColor();
				final Color fc = gv > 128 ? ColorHelper.darker(bc, 40) : ColorHelper.brighter(bc, 20);
				g.setColor(fc);
				g.drawRect(x + 4, y + 4, w - 9, h - 9);
				g.setColor(bc);
				g.fillRect(x + 5, y + 5, w - 10, h - 10);
			} else if (model.isSelected()) {
				if (!model.isEnabled()) {
					checkDisabledIcon.paintIcon(c, g, xi + 1, yi + 1);
				} else {
					if (gv > 128) {
						checkInverseIcon.paintIcon(c, g, xi + 1, yi + 1);
					} else {
						checkIcon.paintIcon(c, g, xi, yi);
					}
				}
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------------------------
	private final static class RadioButtonIcon implements Icon {

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
			Color frameColor = AbstractLookAndFeel.getFrameColor();
			final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
			final Shape savedClip = g.getClip();
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			final Area clipArea = new Area(new Ellipse2D.Double(x, y, w + 1, h + 1));
			if (savedClip != null) {
				clipArea.intersect(new Area(savedClip));
			}
			g2D.setClip(clipArea);
			if (button.isEnabled()) {
				if (button.isRolloverEnabled() && model.isRollover() || model.isPressed() && model.isArmed()) {
					JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getRolloverColors(), x, y, w, h);
					frameColor = ColorHelper.brighter(frameColor, 30);
				} else {
					if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
						JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getFocusColors(), x, y, w,
								h);
					} else {
						JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getCheckBoxColors(), x, y, w,
								h);
					}
				}
			} else {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getDisabledColors(), x, y, w, h);
				frameColor = ColorHelper.brighter(frameColor, 40);
			}
			g2D.setClip(savedClip);
			g2D.setColor(frameColor);
			g2D.drawOval(x, y, w, h);
			if (model.isSelected()) {
				if (!model.isEnabled()) {
					g.setColor(AbstractLookAndFeel.getTheme().getDisabledForegroundColor());
					g.fillOval(x + w / 2 - 2, y + h / 2 - 2, 5, 5);
				} else {
					final int gv;
					if (model.isRollover()) {
						gv = ColorHelper.getGrayValue(AbstractLookAndFeel.getTheme().getRolloverColorDark());
					} else {
						gv = ColorHelper.getGrayValue(AbstractLookAndFeel.getTheme().getControlColorDark());
					}
					if (gv > 128) {
						g.setColor(Color.black);
					} else {
						g.setColor(Color.white);
					}
					if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
						g2D.fillOval(x + 4, y + 4, w - 7, h - 7);
					} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
						g2D.fillOval(x + 4, y + 4, w - 7, h - 7);
					} else {
						g2D.fillOval(x + 5, y + 5, w - 9, h - 9);
					}
				}
			}
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
		}
	}

	// ----------------------------------------------------------------------------------------------------------------------
// inner classes
//----------------------------------------------------------------------------------------------------------------------
	private static class TitleButtonIcon implements Icon {

		private static final Color EXTRA_LIGHT_GRAY = new Color(240, 240, 240);
		private static final Color CLOSER_COLOR_LIGHT = new Color(241, 172, 154);
		private static final Color CLOSER_COLOR_DARK = new Color(224, 56, 2);

		public static final int ICON_ICON_TYP = 0;
		public static final int MIN_ICON_TYP = 1;
		public static final int MAX_ICON_TYP = 2;
		public static final int CLOSE_ICON_TYP = 3;
		private int iconTyp = ICON_ICON_TYP;

		public TitleButtonIcon(final int typ) {
			iconTyp = typ;
		}

		@Override
		public int getIconHeight() {
			return 20;
		}

		@Override
		public int getIconWidth() {
			return 20;
		}

		@Override
		public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
			final int w = c.getWidth();
			final int h = c.getHeight();

			final JButton b = (JButton) c;
			final Graphics2D g2D = (Graphics2D) g;

			final boolean isPressed = b.getModel().isPressed();
			final boolean isArmed = b.getModel().isArmed();
			final boolean isRollover = b.getModel().isRollover();

			final Color cFrame = AbstractLookAndFeel.getTheme().getWindowBorderColor();
			Color cFrameInner = ColorHelper.brighter(cFrame, 60);
			Color cHi = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getWindowTitleColorLight(), 40);
			Color cLo = ColorHelper.darker(AbstractLookAndFeel.getTheme().getWindowTitleColorDark(), 10);
			Color cShadow = Color.black;
			if (iconTyp == CLOSE_ICON_TYP) {
				cHi = CLOSER_COLOR_LIGHT;
				cLo = CLOSER_COLOR_DARK;
			}

			if (isPressed && isArmed) {
				final Color cTemp = ColorHelper.darker(cLo, 10);
				cLo = ColorHelper.darker(cHi, 10);
				cHi = cTemp;
				g2D.setPaint(new GradientPaint(0, 0, cHi, w, h, cLo));
				g.fillRect(2, 2, w - 3, h - 3);
			} else if (isRollover) {
				cFrameInner = ColorHelper.brighter(cFrameInner, 50);
				if (iconTyp == CLOSE_ICON_TYP) {
					cHi = CLOSER_COLOR_LIGHT;
					cLo = CLOSER_COLOR_DARK;
					cShadow = cLo;
					g2D.setPaint(new GradientPaint(0, 0, cHi, w, h, cLo));
					g2D.fillRect(2, 2, w - 3, h - 3);
				} else {
					JTattooUtilities.fillHorGradient(g2D, AbstractLookAndFeel.getTheme().getRolloverColors(), 2, 2,
							w - 3, h - 3);
				}
			}

			g2D.setColor(cFrame);
			g2D.drawLine(1, 0, w - 2, 0);
			g2D.drawLine(1, h - 1, w - 2, h - 1);
			g2D.drawLine(0, 1, 0, h - 2);
			g2D.drawLine(w - 1, 1, w - 1, h - 2);

			g2D.setColor(cFrameInner);
			g2D.drawRect(1, 1, w - 3, h - 3);

			Icon icon = null;
			switch (iconTyp) {
			case ICON_ICON_TYP:
				icon = new BaseIcons.IconSymbol(EXTRA_LIGHT_GRAY, cShadow, null, new Insets(0, 5, 0, 5));
				break;
			case MIN_ICON_TYP:
				icon = new BaseIcons.MinSymbol(EXTRA_LIGHT_GRAY, cShadow, null, new Insets(0, 4, 0, 4));
				break;
			case MAX_ICON_TYP:
				icon = new BaseIcons.MaxSymbol(EXTRA_LIGHT_GRAY, cShadow, null, new Insets(0, 4, 0, 4));
				break;
			case CLOSE_ICON_TYP:
				icon = new BaseIcons.CloseSymbol(Color.white, ColorHelper.darker(cShadow, 50), null,
						new Insets(0, 5, 0, 5));
				break;
			default:
				break;
			}
			if (icon != null) {
				icon.paintIcon(c, g, 0, 0);
			}
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
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				closeIcon = new MacCloseIcon();
			} else {
				closeIcon = new TitleButtonIcon(TitleButtonIcon.CLOSE_ICON_TYP);
			}
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
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				iconIcon = new MacIconIcon();
			} else {
				iconIcon = new TitleButtonIcon(TitleButtonIcon.ICON_ICON_TYP);
			}
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
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				maxIcon = new MacMaxIcon();
			} else {
				maxIcon = new TitleButtonIcon(TitleButtonIcon.MAX_ICON_TYP);
			}
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
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				minIcon = new MacMinIcon();
			} else {
				minIcon = new TitleButtonIcon(TitleButtonIcon.MIN_ICON_TYP);
			}
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
		if (!AbstractLookAndFeel.getControlColorLight().equals(new ColorUIResource(96, 98, 100))) {
			return BaseIcons.getThumbHorIcon();
		}

		if (thumbHorIcon == null) {
			thumbHorIcon = new LazyImageIcon("acryl/icons/thumb_hor.gif");
		}
		return thumbHorIcon;
	}

	/**
	 * <p>getThumbHorIconRollover.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbHorIconRollover() {
		if (!AbstractLookAndFeel.getControlColorLight().equals(new ColorUIResource(96, 98, 100))) {
			return BaseIcons.getThumbHorIconRollover();
		}

		if (thumbHorIconRollover == null) {
			thumbHorIconRollover = new LazyImageIcon("acryl/icons/thumb_hor_rollover.gif");
		}
		return thumbHorIconRollover;
	}

	/**
	 * <p>getThumbVerIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbVerIcon() {
		if (!AbstractLookAndFeel.getControlColorLight().equals(new ColorUIResource(96, 98, 100))) {
			return BaseIcons.getThumbVerIcon();
		}

		if (thumbVerIcon == null) {
			thumbVerIcon = new LazyImageIcon("acryl/icons/thumb_ver.gif");
		}
		return thumbVerIcon;
	}

	/**
	 * <p>getThumbVerIconRollover.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbVerIconRollover() {
		if (!AbstractLookAndFeel.getControlColorLight().equals(new ColorUIResource(96, 98, 100))) {
			return BaseIcons.getThumbVerIconRollover();
		}

		if (thumbVerIconRollover == null) {
			thumbVerIconRollover = new LazyImageIcon("acryl/icons/thumb_ver_rollover.gif");
		}
		return thumbVerIconRollover;
	}

	/**
	 * <p>getTreeCollapsedIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getTreeCollapsedIcon() {
		if (!AbstractLookAndFeel.getControlColorLight().equals(new ColorUIResource(96, 98, 100))) {
			return BaseIcons.getTreeCollapsedIcon();
		} else {
			if (treeCollapsedIcon == null) {
				if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
					treeCollapsedIcon = new LazyImageIcon("acryl/icons/small/tree_collapsed_11x11.png");
				} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
					treeCollapsedIcon = new LazyImageIcon("acryl/icons/medium/tree_collapsed_13x13.png");
				} else {
					treeCollapsedIcon = new LazyImageIcon("acryl/icons/large/tree_collapsed_15x15.png");
				}
			}
			return treeCollapsedIcon;
		}
	}

	/**
	 * <p>getTreeExpandedIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getTreeExpandedIcon() {
		if (!AbstractLookAndFeel.getControlColorLight().equals(new ColorUIResource(96, 98, 100))) {
			return BaseIcons.getTreeExpandedIcon();
		} else {
			if (treeExpandedIcon == null) {
				if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
					treeExpandedIcon = new LazyImageIcon("acryl/icons/small/tree_expanded_11x11.png");
				} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
					treeExpandedIcon = new LazyImageIcon("acryl/icons/medium/tree_expanded_13x13.png");
				} else {
					treeExpandedIcon = new LazyImageIcon("acryl/icons/large/tree_expanded_15x15.png");
				}
			}
			return treeExpandedIcon;
		}
	}

} // end of class AcrylIcons
