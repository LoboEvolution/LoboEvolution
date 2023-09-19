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
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JButton;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseIcons;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.LazyImageIcon;

/**
 * <p>LunaIcons class.</p>
 *
 * Author Michael Hagen
 *
 */
public class LunaIcons extends BaseIcons {

	// ------------------------------------------------------------------------------
	private static class TitleButtonIcon implements Icon {

		private static final Color BLUE_FRAME_COLOR = Color.white;
		private static final Color BLUE_COLOR_LIGHT = new Color(154, 183, 250);
		private static final Color BLUE_COLOR_DARK = new Color(0, 69, 211);
		private static final Color CLOSER_COLOR_LIGHT = new Color(241, 172, 154);
		private static final Color CLOSER_COLOR_DARK = new Color(224, 56, 2);

		private static final int ICON_ICON_TYP = 0;
		private static final int MIN_ICON_TYP = 1;
		private static final int MAX_ICON_TYP = 2;
		private static final int CLOSE_ICON_TYP = 3;

		private int iconTyp = ICON_ICON_TYP;

		public TitleButtonIcon(int typ) {
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
		public void paintIcon(Component c, Graphics g, int x, int y) {
			int w = c.getWidth();
			int h = c.getHeight();

			JButton b = (JButton) c;
			Graphics2D g2D = (Graphics2D) g;

			boolean isActive = JTattooUtilities.isActive(b);
			boolean isPressed = b.getModel().isPressed();
			boolean isArmed = b.getModel().isArmed();
			boolean isRollover = b.getModel().isRollover();

			Color fc = BLUE_FRAME_COLOR;
			Color cHi = BLUE_COLOR_LIGHT;
			Color cLo = BLUE_COLOR_DARK;
			if (iconTyp == CLOSE_ICON_TYP) {
				cHi = CLOSER_COLOR_LIGHT;
				cLo = CLOSER_COLOR_DARK;
			}

			if (!isActive) {
				cHi = ColorHelper.brighter(cHi, 20);
				cLo = ColorHelper.brighter(cLo, 10);
			}
			if (isPressed && isArmed) {
				Color cTemp = ColorHelper.darker(cLo, 10);
				cLo = ColorHelper.darker(cHi, 10);
				cHi = cTemp;
			} else if (isRollover) {
				cHi = ColorHelper.brighter(cHi, 30);
				cLo = ColorHelper.brighter(cLo, 30);
			}

			g2D.setPaint(new GradientPaint(0, 0, cHi, w, h, cLo));
			g.fillRect(1, 1, w - 2, h - 2);

			g.setColor(fc);
			g.drawLine(1, 0, w - 2, 0);
			g.drawLine(0, 1, 0, h - 2);
			g.drawLine(1, h - 1, w - 2, h - 1);
			g.drawLine(w - 1, 1, w - 1, h - 2);
			Composite composite = g2D.getComposite();
			AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
			g2D.setComposite(alpha);
			g2D.setColor(cLo);
			g.drawLine(2, 1, w - 2, 1);
			g.drawLine(1, 2, 1, h - 2);
			g2D.setColor(ColorHelper.darker(cLo, 40));
			g.drawLine(2, h - 2, w - 2, h - 2);
			g.drawLine(w - 2, 2, w - 2, h - 2);

			g2D.setComposite(composite);

			// Paint the icon
			cHi = Color.white;
			cLo = ColorHelper.darker(cLo, 30);
			Icon icon = null;
			switch (iconTyp) {
			case ICON_ICON_TYP:
				icon = new BaseIcons.IconSymbol(cHi, cLo, null, new Insets(0, 0, 0, 1));
				break;
			case MIN_ICON_TYP:
				icon = new BaseIcons.MinSymbol(cHi, cLo, null, new Insets(0, 0, 0, 0));
				break;
			case MAX_ICON_TYP:
				icon = new BaseIcons.MaxSymbol(cHi, cLo, null, new Insets(0, 0, 0, 1));
				break;
			case CLOSE_ICON_TYP:
				icon = new BaseIcons.CloseSymbol(cHi, cLo, null, new Insets(0, 0, 0, 1));
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
				downArrowIcon = new LazyImageIcon("luna/icons/small/arrow_down_7x6.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				downArrowIcon = new LazyImageIcon("luna/icons/medium/arrow_down_9x8.png");
			} else {
				downArrowIcon = new LazyImageIcon("luna/icons/large/arrow_down_11x10.png");
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
				leftArrowIcon = new LazyImageIcon("luna/icons/small/arrow_left_6x7.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				leftArrowIcon = new LazyImageIcon("luna/icons/medium/arrow_left_8x9.png");
			} else {
				leftArrowIcon = new LazyImageIcon("luna/icons/large/arrow_left_10x11.png");
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
				rightArrowIcon = new LazyImageIcon("luna/icons/small/arrow_right_6x7.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				rightArrowIcon = new LazyImageIcon("luna/icons/medium/arrow_right_8x9.png");
			} else {
				rightArrowIcon = new LazyImageIcon("luna/icons/large/arrow_right_10x11.png");
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
				upArrowIcon = new LazyImageIcon("luna/icons/small/arrow_up_7x6.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				upArrowIcon = new LazyImageIcon("luna/icons/medium/arrow_up_9x8.png");
			} else {
				upArrowIcon = new LazyImageIcon("luna/icons/large/arrow_up_11x10.png");
			}
		}
		return upArrowIcon;
	}

} // end of class LunaIcons
