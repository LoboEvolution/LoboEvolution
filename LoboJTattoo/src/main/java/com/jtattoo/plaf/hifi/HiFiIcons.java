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
package com.jtattoo.plaf.hifi;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
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
import com.jtattoo.plaf.base.BaseIcons;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.LazyImageIcon;
import com.jtattoo.plaf.LazyMenuArrowImageIcon;
import com.jtattoo.plaf.base.icon.*;

/**
 * <p>HiFiIcons class.</p>
 *
 * Author Michael Hagen
 *
 */
public class HiFiIcons extends BaseIcons {

	// ----------------------------------------------------------------------------------------------------------------------
// inner classes
//----------------------------------------------------------------------------------------------------------------------
	private final static class CheckBoxIcon implements Icon, UIResource, Serializable {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final int GAP = 2;
		private static final Icon SMALL_CHECK_ICON = new LazyImageIcon("hifi/icons/small/check_symbol_12x11.png");
		private static final Icon SMALL_CHECK_INVERSE_ICON = new LazyImageIcon("icons/small/check_symbol_10x10.png");
		private static final Icon SMALL_CHECK_DISABLED_ICON = new LazyImageIcon(
				"icons/small/check_symbol_disabled_10x10.png");
		private static final Icon MEDIUM_CHECK_ICON = new LazyImageIcon("hifi/icons/medium/check_symbol_14x13.png");
		private static final Icon MEDIUM_CHECK_INVERSE_ICON = new LazyImageIcon("icons/medium/check_symbol_12x12.png");
		private static final Icon MEDIUM_CHECK_DISABLED_ICON = new LazyImageIcon(
				"icons/medium/check_symbol_disabled_12x12.png");
		private static final Icon LARGE_CHECK_ICON = new LazyImageIcon("hifi/icons/large/check_symbol_16x15.png");
		private static final Icon LARGE_CHECK_INVERSE_ICON = new LazyImageIcon("icons/large/check_symbol_14x14.png");
		private static final Icon LARGE_CHECK_DISABLED_ICON = new LazyImageIcon(
				"icons/large/check_symbol_disabled_14x14.png");

		@Override
		public int getIconHeight() {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				return 15;
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				return 17;
			} else {
				return 19;
			}
		}

		@Override
		public int getIconWidth() {
			final int w;
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				w = 15;
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				w = 17;
			} else {
				w = 19;
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
			final Graphics2D g2D = (Graphics2D) g;
			if (button.isEnabled()) {
				if (button.isRolloverEnabled() && model.isRollover()) {
					JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getRolloverColors(), x + 1,
							y + 1, w - 1, h - 1);
				} else {
					if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
						JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getFocusColors(), x + 1,
								y + 1, w - 1, h - 1);
					} else {
						JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getCheckBoxColors(), x + 1,
								y + 1, w - 1, h - 1);
					}
				}
			} else {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getDisabledColors(), x + 1, y + 1,
						w - 1, h - 1);
			}

			final Color frameColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getButtonBackgroundColor(), 6);
			final Color loFrameColor = ColorHelper.darker(AbstractLookAndFeel.getTheme().getButtonBackgroundColor(), 50);

			g.setColor(frameColor);
			g.drawRect(x, y, w, h);
			final Composite savedComposite = g2D.getComposite();
			final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			g2D.setComposite(alpha);
			g.setColor(loFrameColor);
			g.drawLine(x + 1, y + 1, x + w - 1, y + 1);
			g.drawLine(x + 1, y + 1, x + 1, y + h - 1);
			g2D.setComposite(savedComposite);

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
			final int xi = x + (w - checkIcon.getIconWidth()) / 2 + 1;
			final int yi = y + (h - checkIcon.getIconHeight()) / 2;
			final int gv = ColorHelper.getGrayValue(AbstractLookAndFeel.getButtonForegroundColor());
			if (model.isPressed() && model.isArmed()) {
				final Color bc = gv > 128 ? AbstractLookAndFeel.getTheme().getSelectionForegroundColor()
						: AbstractLookAndFeel.getTheme().getSelectionBackgroundColor();
				final Color fc = gv > 128 ? ColorHelper.brighter(bc, 20) : ColorHelper.darker(bc, 40);
				g.setColor(fc);
				g.drawRect(x + 4, y + 4, w - 8, h - 8);
				g.setColor(bc);
				g.fillRect(x + 5, y + 5, w - 9, h - 9);
			} else if (model.isSelected()) {
				if (!model.isEnabled()) {
					checkDisabledIcon.paintIcon(c, g, xi + 1, yi);
				} else {
					if (gv > 128) {
						checkIcon.paintIcon(c, g, xi, yi);
					} else {
						checkInverseIcon.paintIcon(c, g, xi + 1, yi + 1);
					}
				}
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------------------------
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
				} else {
					if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
						colors = AbstractLookAndFeel.getTheme().getFocusColors();
					} else {
						colors = AbstractLookAndFeel.getTheme().getCheckBoxColors();
					}
				}
			} else {
				colors = AbstractLookAndFeel.getTheme().getDisabledColors();
			}

			final Color frameColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getButtonBackgroundColor(), 6);
			final Shape savedClip = g.getClip();
			final Area clipArea = new Area(new Ellipse2D.Double(x, y, w + 1, h + 1));
			if (savedClip != null) {
				clipArea.intersect(new Area(savedClip));
			}
			g2D.setClip(clipArea);
			JTattooUtilities.fillHorGradient(g, colors, x, y, w, h);
			g2D.setClip(savedClip);

			final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(frameColor);
			g.drawOval(x, y, w, h);

			if (model.isSelected()) {
				if (model.isEnabled()) {
					final Color fc = AbstractLookAndFeel.getForegroundColor();
					if (ColorHelper.getGrayValue(colors[0]) < 128) {
						if (ColorHelper.getGrayValue(fc) < 128) {
							g2D.setColor(Color.white);
						} else {
							g2D.setColor(fc);
						}
					} else {
						if (ColorHelper.getGrayValue(fc) > 128) {
							g2D.setColor(Color.black);
						} else {
							g2D.setColor(fc);
						}
					}
				} else {
					g.setColor(AbstractLookAndFeel.getDisabledForegroundColor());
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
				final Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				final Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
				final Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				closeIcon = new CloseSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(-1, -1, 0, 0));
			}
		}
		return closeIcon;
	}

	/**
	 * <p>getComboBoxIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getComboBoxIcon() {
		return getDownArrowIcon();
	}

	/**
	 * <p>getDownArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getDownArrowIcon() {
		if (downArrowIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				downArrowIcon = new LazyImageIcon("hifi/icons/small/arrow_down_7x6.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				downArrowIcon = new LazyImageIcon("hifi/icons/medium/arrow_down_9x8.png");
			} else {
				downArrowIcon = new LazyImageIcon("hifi/icons/large/arrow_down_11x10.png");
			}
		}
		return downArrowIcon;
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
				final Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				final Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
				final Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				iconIcon = new IconSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(-1, -1, 0, 0));
			}
		}
		return iconIcon;
	}

	/**
	 * <p>getLeftArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getLeftArrowIcon() {
		if (leftArrowIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				leftArrowIcon = new LazyImageIcon("hifi/icons/small/arrow_left_6x7.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				leftArrowIcon = new LazyImageIcon("hifi/icons/medium/arrow_left_8x9.png");
			} else {
				leftArrowIcon = new LazyImageIcon("hifi/icons/large/arrow_left_10x11.png");
			}
		}
		return leftArrowIcon;
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
				final Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				final Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
				final Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				maxIcon = new MaxSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(-1, -1, 0, 0));
			}
		}
		return maxIcon;
	}

	/**
	 * <p>getMenuArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getMenuArrowIcon() {
		if (menuArrowIcon == null) {
			menuArrowIcon = new LazyMenuArrowImageIcon("hifi/icons/medium/arrow_right_8x9.png",
					"hifi/icons/medium/arrow_left8x9.png");
		}
		return menuArrowIcon;
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
				final Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				final Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
				final Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				minIcon = new MinSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(-1, -1, 0, 0));
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
	 * <p>getRightArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getRightArrowIcon() {
		if (rightArrowIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				rightArrowIcon = new LazyImageIcon("hifi/icons/small/arrow_right_6x7.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				rightArrowIcon = new LazyImageIcon("hifi/icons/medium/arrow_right_8x9.png");
			} else {
				rightArrowIcon = new LazyImageIcon("hifi/icons/large/arrow_right_10x11.png");
			}
		}
		return rightArrowIcon;
	}

	/**
	 * <p>getSplitterDownArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterDownArrowIcon() {
		if (splitterDownArrowIcon == null) {
			splitterDownArrowIcon = new LazyImageIcon("hifi/icons/SplitterDownArrow.gif");
		}
		return splitterDownArrowIcon;
	}

	/**
	 * <p>getSplitterHorBumpIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterHorBumpIcon() {
		if (splitterHorBumpIcon == null) {
			splitterHorBumpIcon = new LazyImageIcon("hifi/icons/SplitterHorBumps.gif");
		}
		return splitterHorBumpIcon;
	}

	/**
	 * <p>getSplitterLeftArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterLeftArrowIcon() {
		if (splitterLeftArrowIcon == null) {
			splitterLeftArrowIcon = new LazyImageIcon("hifi/icons/SplitterLeftArrow.gif");
		}
		return splitterLeftArrowIcon;
	}

	/**
	 * <p>getSplitterRightArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterRightArrowIcon() {
		if (splitterRightArrowIcon == null) {
			splitterRightArrowIcon = new LazyImageIcon("hifi/icons/SplitterRightArrow.gif");
		}
		return splitterRightArrowIcon;
	}

	/**
	 * <p>getSplitterUpArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterUpArrowIcon() {
		if (splitterUpArrowIcon == null) {
			splitterUpArrowIcon = new LazyImageIcon("hifi/icons/SplitterUpArrow.gif");
		}
		return splitterUpArrowIcon;
	}

	/**
	 * <p>getSplitterVerBumpIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterVerBumpIcon() {
		if (splitterVerBumpIcon == null) {
			splitterVerBumpIcon = new LazyImageIcon("hifi/icons/SplitterVerBumps.gif");
		}
		return splitterVerBumpIcon;
	}

	/**
	 * <p>getThumbHorIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbHorIcon() {
		if (thumbHorIcon == null) {
			thumbHorIcon = new LazyImageIcon("hifi/icons/thumb_hor.gif");
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
			thumbHorIconRollover = new LazyImageIcon("hifi/icons/thumb_hor_rollover.gif");
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
			thumbVerIcon = new LazyImageIcon("hifi/icons/thumb_ver.gif");
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
			thumbVerIconRollover = new LazyImageIcon("hifi/icons/thumb_ver_rollover.gif");
		}
		return thumbVerIconRollover;
	}

	/**
	 * <p>getTreeCollapsedIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getTreeCollapsedIcon() {
		if (treeCollapsedIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				treeCollapsedIcon = new LazyImageIcon("hifi/icons/small/tree_collapsed_9x9.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				treeCollapsedIcon = new LazyImageIcon("hifi/icons/medium/tree_collapsed_11x11.png");
			} else {
				treeCollapsedIcon = new LazyImageIcon("hifi/icons/large/tree_collapsed_14x14.png");
			}
		}
		return treeCollapsedIcon;
	}

	/**
	 * <p>getTreeExpandedIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getTreeExpandedIcon() {
		if (treeExpandedIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				treeExpandedIcon = new LazyImageIcon("hifi/icons/small/tree_expanded_9x9.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				treeExpandedIcon = new LazyImageIcon("hifi/icons/medium/tree_expanded_11x11.png");
			} else {
				treeExpandedIcon = new LazyImageIcon("hifi/icons/large/tree_expanded_14x14.png");
			}
		}
		return treeExpandedIcon;
	}

	/**
	 * <p>getUpArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getUpArrowIcon() {
		if (upArrowIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				upArrowIcon = new LazyImageIcon("hifi/icons/small/arrow_up_7x6.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				upArrowIcon = new LazyImageIcon("hifi/icons/medium/arrow_up_9x8.png");
			} else {
				upArrowIcon = new LazyImageIcon("hifi/icons/large/arrow_up_11x10.png");
			}
		}
		return upArrowIcon;
	}

} // end of class HiFiIcons
