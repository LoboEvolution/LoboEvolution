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
package com.jtattoo.plaf.aluminium;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.Icon;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseIcons;
import com.jtattoo.plaf.LazyImageIcon;

/**
 * <p>AluminiumIcons class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AluminiumIcons extends BaseIcons {

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
				closeIcon = new BaseIcons.CloseSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(0, 0, 1, 0));
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
				final Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				final Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
				final Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				iconIcon = new BaseIcons.IconSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(0, 0, 1, 0));
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
				final Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				final Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
				final Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				maxIcon = new BaseIcons.MaxSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(0, 0, 1, 0));
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
				minIcon = new BaseIcons.MinSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(0, 0, 1, 0));
			}
		}
		return minIcon;
	}

	/**
	 * <p>getSplitterDownArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterDownArrowIcon() {
		if (splitterDownArrowIcon == null) {
			splitterDownArrowIcon = new LazyImageIcon("aluminium/icons/SplitterDownArrow.gif");
		}
		return splitterDownArrowIcon;
	}

	/**
	 * <p>getSplitterLeftArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterLeftArrowIcon() {
		if (splitterLeftArrowIcon == null) {
			splitterLeftArrowIcon = new LazyImageIcon("aluminium/icons/SplitterLeftArrow.gif");
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
			splitterRightArrowIcon = new LazyImageIcon("aluminium/icons/SplitterRightArrow.gif");
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
			splitterUpArrowIcon = new LazyImageIcon("aluminium/icons/SplitterUpArrow.gif");
		}
		return splitterUpArrowIcon;
	}

	/**
	 * <p>getThumbHorIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbHorIcon() {
		if (thumbHorIcon == null) {
			thumbHorIcon = new LazyImageIcon("aluminium/icons/thumb_hor.gif");
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
			thumbHorIconRollover = new LazyImageIcon("aluminium/icons/thumb_hor_rollover.gif");
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
			thumbVerIcon = new LazyImageIcon("aluminium/icons/thumb_ver.gif");
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
			thumbVerIconRollover = new LazyImageIcon("aluminium/icons/thumb_ver_rollover.gif");
		}
		return thumbVerIconRollover;
	}

} // end of class AluminiumIcons
