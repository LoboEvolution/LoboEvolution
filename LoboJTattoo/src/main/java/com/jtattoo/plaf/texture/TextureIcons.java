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
package com.jtattoo.plaf.texture;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.Icon;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.base.BaseIcons;
import com.jtattoo.plaf.LazyImageIcon;
import com.jtattoo.plaf.base.icon.*;

/**
 * <p>TextureIcons class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TextureIcons extends BaseIcons {

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
						new Insets(1, 1, 1, 1));
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
				downArrowIcon = new LazyImageIcon("texture/icons/small/arrow_down_7x6.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				downArrowIcon = new LazyImageIcon("texture/icons/medium/arrow_down_9x8.png");
			} else {
				downArrowIcon = new LazyImageIcon("texture/icons/large/arrow_down_11x10.png");
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
						new Insets(1, 1, 1, 1));
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
				leftArrowIcon = new LazyImageIcon("texture/icons/small/arrow_left_6x7.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				leftArrowIcon = new LazyImageIcon("texture/icons/medium/arrow_left_8x9.png");
			} else {
				leftArrowIcon = new LazyImageIcon("texture/icons/large/arrow_left_10x11.png");
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
						new Insets(1, 1, 1, 1));
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
				final Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				final Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
				final Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				minIcon = new MinSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(1, 1, 1, 1));
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
				rightArrowIcon = new LazyImageIcon("texture/icons/small/arrow_right_6x7.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				rightArrowIcon = new LazyImageIcon("texture/icons/medium/arrow_right_8x9.png");
			} else {
				rightArrowIcon = new LazyImageIcon("texture/icons/large/arrow_right_10x11.png");
			}
		}
		return rightArrowIcon;
	}

	/**
	 * <p>getSplitterHorBumpIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterHorBumpIcon() {
		if (splitterHorBumpIcon == null) {
			splitterHorBumpIcon = new LazyImageIcon("texture/icons/SplitterHorBumps.gif");
		}
		return splitterHorBumpIcon;
	}

	/**
	 * <p>getSplitterVerBumpIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterVerBumpIcon() {
		if (splitterVerBumpIcon == null) {
			splitterVerBumpIcon = new LazyImageIcon("texture/icons/SplitterVerBumps.gif");
		}
		return splitterVerBumpIcon;
	}

	/**
	 * <p>getThumbHorIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbHorIcon() {
		if ("Default".equals(AbstractLookAndFeel.getTheme().getName())) {
			if (thumbHorIcon == null) {
				thumbHorIcon = new LazyImageIcon("texture/icons/thumb_hor.gif");
			}
			return thumbHorIcon;
		} else {
			return BaseIcons.getThumbHorIcon();
		}
	}

	/**
	 * <p>getThumbHorIconRollover.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbHorIconRollover() {
		if ("Default".equals(AbstractLookAndFeel.getTheme().getName())) {
			if (thumbHorIconRollover == null) {
				thumbHorIconRollover = new LazyImageIcon("texture/icons/thumb_hor_rollover.gif");
			}
			return thumbHorIconRollover;
		} else {
			return BaseIcons.getThumbHorIconRollover();
		}
	}

	/**
	 * <p>getThumbVerIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbVerIcon() {
		if ("Default".equals(AbstractLookAndFeel.getTheme().getName())) {
			if (thumbVerIcon == null) {
				thumbVerIcon = new LazyImageIcon("texture/icons/thumb_ver.gif");
			}
			return thumbVerIcon;
		} else {
			return BaseIcons.getThumbVerIcon();
		}
	}

	/**
	 * <p>getThumbVerIconRollover.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbVerIconRollover() {
		if ("Default".equals(AbstractLookAndFeel.getTheme().getName())) {
			if (thumbVerIconRollover == null) {
				thumbVerIconRollover = new LazyImageIcon("texture/icons/thumb_ver_rollover.gif");
			}
			return thumbVerIconRollover;
		} else {
			return BaseIcons.getThumbVerIconRollover();
		}
	}

	/**
	 * <p>getUpArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getUpArrowIcon() {
		if (upArrowIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				upArrowIcon = new LazyImageIcon("texture/icons/small/arrow_up_7x6.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				upArrowIcon = new LazyImageIcon("texture/icons/medium/arrow_up_9x8.png");
			} else {
				upArrowIcon = new LazyImageIcon("texture/icons/large/arrow_up_11x10.png");
			}
		}
		return upArrowIcon;
	}

} // end of class TextureIcons
