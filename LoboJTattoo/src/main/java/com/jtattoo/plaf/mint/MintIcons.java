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
package com.jtattoo.plaf.mint;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Icon;
import javax.swing.JButton;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseIcons;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.LazyImageIcon;

/**
 * <p>MintIcons class.</p>
 *
 * Author Michael Hagen
 *
 */
public class MintIcons extends BaseIcons {

	// ------------------------------------------------------------------------------
	private static class TitleButtonIcon implements Icon {

		private static final Color CLOSER_COLOR_LIGHT = new Color(241, 172, 154);
		private static final Color CLOSER_COLOR_DARK = new Color(224, 56, 2);

		private static final int ICON_ICON_TYP = 0;
		private static final int MIN_ICON_TYP = 1;
		private static final int MAX_ICON_TYP = 2;
		private static final int CLOSE_ICON_TYP = 3;

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
		public void paintIcon(final Component c, final Graphics g, final int iconX, final int y) {

			final int w = c.getWidth();
			final int h = c.getHeight();

			final JButton b = (JButton) c;
			final Graphics2D g2D = (Graphics2D) g;

			final boolean isActive = JTattooUtilities.isActive(b);
			final boolean isPressed = b.getModel().isPressed();
			final boolean isArmed = b.getModel().isArmed();
			final boolean isRollover = b.getModel().isRollover();

			Color cHi = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getWindowTitleColorLight(), 40);
			Color cLo = ColorHelper.darker(AbstractLookAndFeel.getTheme().getWindowTitleColorDark(), 10);
			if (iconTyp == CLOSE_ICON_TYP) {
				cHi = CLOSER_COLOR_LIGHT;
				cLo = CLOSER_COLOR_DARK;
			}

			Color fcHi = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getWindowTitleColorDark(), 80);
			Color fcLo = ColorHelper.darker(AbstractLookAndFeel.getTheme().getWindowTitleColorDark(), 40);

			if (!isActive) {
				cHi = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorLight(), 40);
				cLo = ColorHelper.darker(AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorDark(), 10);
				fcHi = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorLight(), 60);
				fcLo = ColorHelper.darker(AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorDark(), 10);
			}
			if (isPressed && isArmed) {
				final Color cTemp = ColorHelper.darker(cLo, 10);
				cLo = ColorHelper.darker(cHi, 10);
				cHi = cTemp;
			} else if (isRollover) {
				cHi = ColorHelper.brighter(cHi, 30);
				cLo = ColorHelper.brighter(cLo, 30);
			}

			final Shape savedClip = g.getClip();
			final Area area = new Area(new RoundRectangle2D.Double(1, 1, w - 1, h - 1, 3, 3));
			g2D.setClip(area);

			g2D.setPaint(new GradientPaint(0, 0, fcLo, w, h, fcHi));
			g.fillRect(1, 1, w - 1, h - 1);

			g2D.setPaint(new GradientPaint(0, 0, ColorHelper.brighter(cHi, 80), w, h, ColorHelper.darker(cLo, 30)));
			g.fillRect(2, 2, w - 3, h - 3);

			g2D.setPaint(new GradientPaint(0, 0, cHi, w, h, cLo));
			g.fillRect(3, 3, w - 5, h - 5);

			g2D.setClip(savedClip);

			cHi = Color.white;
			cLo = ColorHelper.darker(cLo, 30);
			Icon icon = null;
			switch (iconTyp) {
			case ICON_ICON_TYP:
				icon = new BaseIcons.IconSymbol(cHi, cLo, null);
				break;
			case MIN_ICON_TYP:
				icon = new BaseIcons.MinSymbol(cHi, cLo, null);
				break;
			case MAX_ICON_TYP:
				icon = new BaseIcons.MaxSymbol(cHi, cLo, null);
				break;
			case CLOSE_ICON_TYP:
				icon = new BaseIcons.CloseSymbol(cHi, cLo, null);
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
	 * <p>getDownArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getDownArrowIcon() {
		if (downArrowIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				downArrowIcon = new LazyImageIcon("mint/icons/small/arrow_down_7x6.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				downArrowIcon = new LazyImageIcon("mint/icons/medium/arrow_down_9x8.png");
			} else {
				downArrowIcon = new LazyImageIcon("mint/icons/large/arrow_down_11x10.png");
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
				iconIcon = new TitleButtonIcon(TitleButtonIcon.ICON_ICON_TYP);
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
				leftArrowIcon = new LazyImageIcon("mint/icons/small/arrow_left_6x7.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				leftArrowIcon = new LazyImageIcon("mint/icons/medium/arrow_left_8x9.png");
			} else {
				leftArrowIcon = new LazyImageIcon("mint/icons/large/arrow_left_10x11.png");
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
	 * <p>getRightArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getRightArrowIcon() {
		if (rightArrowIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				rightArrowIcon = new LazyImageIcon("mint/icons/small/arrow_right_6x7.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				rightArrowIcon = new LazyImageIcon("mint/icons/medium/arrow_right_8x9.png");
			} else {
				rightArrowIcon = new LazyImageIcon("mint/icons/large/arrow_right_10x11.png");
			}
		}
		return rightArrowIcon;
	}

	/**
	 * <p>getUpArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getUpArrowIcon() {
		if (upArrowIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				upArrowIcon = new LazyImageIcon("mint/icons/small/arrow_up_7x6.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				upArrowIcon = new LazyImageIcon("mint/icons/medium/arrow_up_9x8.png");
			} else {
				upArrowIcon = new LazyImageIcon("mint/icons/large/arrow_up_11x10.png");
			}
		}
		return upArrowIcon;
	}

} // end of class MintIcons
