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
package com.jtattoo.plaf.bernstein;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.plaf.UIResource;

import com.jtattoo.plaf.BaseIcons;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.LazyImageIcon;

/**
 * <p>BernsteinIcons class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BernsteinIcons extends BaseIcons {

	// --------------------------------------------------------------------------------------------------------
	private static class CheckBoxIcon implements Icon, UIResource {

		private static Icon checkIcon = null;
		private static Icon checkSelectedIcon = null;
		private static Icon checkPressedIcon = null;
		private static Icon checkRolloverIcon = null;
		private static Icon checkRolloverSelectedIcon = null;
		private static Icon checkDisabledIcon = null;
		private static Icon checkDisabledSelectedIcon = null;

		public CheckBoxIcon() {
			checkIcon = new LazyImageIcon("bernstein/icons/check.gif");
			checkSelectedIcon = new LazyImageIcon("bernstein/icons/check_selected.gif");
			checkPressedIcon = new LazyImageIcon("bernstein/icons/check_pressed.gif");
			checkRolloverIcon = new LazyImageIcon("bernstein/icons/check_rollover.gif");
			checkRolloverSelectedIcon = new LazyImageIcon("bernstein/icons/check_rollover_selected.gif");
			checkDisabledIcon = new LazyImageIcon("bernstein/icons/check_disabled.gif");
			checkDisabledSelectedIcon = new LazyImageIcon("bernstein/icons/check_disabled_selected.gif");
		}

		@Override
		public int getIconHeight() {
			return checkIcon.getIconHeight();
		}

		@Override
		public int getIconWidth() {
			return checkIcon.getIconWidth() + 2;
		}

		@Override
		public void paintIcon(final Component c, final Graphics g, final int iconX, final int y) {
int x = iconX;
			if (!JTattooUtilities.isLeftToRight(c)) {
				x += 2;
			}
			final AbstractButton button = (AbstractButton) c;
			final ButtonModel model = button.getModel();
			if (button.isEnabled()) {
				if (model.isPressed() && model.isArmed()) {
					checkPressedIcon.paintIcon(c, g, x, y);
				} else if (model.isSelected()) {
					if (button.isRolloverEnabled() && model.isRollover() && !model.isArmed()) {
						checkRolloverSelectedIcon.paintIcon(c, g, x, y);
					} else {
						checkSelectedIcon.paintIcon(c, g, x, y);
					}
				} else {
					if (button.isRolloverEnabled() && model.isRollover() && !model.isArmed()) {
						checkRolloverIcon.paintIcon(c, g, x, y);
					} else {
						checkIcon.paintIcon(c, g, x, y);
					}
				}
			} else {
				if (model.isPressed() && model.isArmed()) {
					checkPressedIcon.paintIcon(c, g, x, y);
				} else if (model.isSelected()) {
					checkDisabledSelectedIcon.paintIcon(c, g, x, y);
				} else {
					checkDisabledIcon.paintIcon(c, g, x, y);
				}
			}
		}
	}

	private static class RadioButtonIcon implements Icon, UIResource {

		private Icon radioIcon = null;
		private Icon radioSelectedIcon = null;
		private Icon radioRolloverIcon = null;
		private Icon radioRolloverSelectedIcon = null;
		private Icon radioDisabledIcon = null;
		private Icon radioDisabledSelectedIcon = null;

		public RadioButtonIcon() {
			radioIcon = new LazyImageIcon("bernstein/icons/radio.gif");
			radioSelectedIcon = new LazyImageIcon("bernstein/icons/radio_selected.gif");
			radioRolloverIcon = new LazyImageIcon("bernstein/icons/radio_rollover.gif");
			radioRolloverSelectedIcon = new LazyImageIcon("bernstein/icons/radio_rollover_selected.gif");
			radioDisabledIcon = new LazyImageIcon("bernstein/icons/radio_disabled.gif");
			radioDisabledSelectedIcon = new LazyImageIcon("bernstein/icons/radio_disabled_selected.gif");
		}

		@Override
		public int getIconHeight() {
			return radioIcon.getIconHeight();
		}

		@Override
		public int getIconWidth() {
			return radioIcon.getIconWidth() + 2;
		}

		@Override
		public void paintIcon(final Component c, final Graphics g, final int iconX, final int y) {
int x = iconX;
			if (!JTattooUtilities.isLeftToRight(c)) {
				x += 2;
			}
			final AbstractButton button = (AbstractButton) c;
			final ButtonModel model = button.getModel();
			if (button.isEnabled()) {
				if (model.isSelected()) {
					if (button.isRolloverEnabled() && model.isRollover()) {
						radioRolloverSelectedIcon.paintIcon(c, g, x, y);
					} else {
						radioSelectedIcon.paintIcon(c, g, x, y);
					}
				} else {
					if (button.isRolloverEnabled() && model.isRollover()) {
						radioRolloverIcon.paintIcon(c, g, x, y);
					} else {
						radioIcon.paintIcon(c, g, x, y);
					}
				}
			} else {
				if (model.isSelected()) {
					radioDisabledSelectedIcon.paintIcon(c, g, x, y);
				} else {
					radioDisabledIcon.paintIcon(c, g, x, y);
				}
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
	 * <p>getSplitterDownArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterDownArrowIcon() {
		if (splitterDownArrowIcon == null) {
			splitterDownArrowIcon = new LazyImageIcon("bernstein/icons/SplitterDownArrow.gif");
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
			splitterLeftArrowIcon = new LazyImageIcon("bernstein/icons/SplitterLeftArrow.gif");
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
			splitterRightArrowIcon = new LazyImageIcon("bernstein/icons/SplitterRightArrow.gif");
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
			splitterUpArrowIcon = new LazyImageIcon("bernstein/icons/SplitterUpArrow.gif");
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
			thumbHorIcon = new LazyImageIcon("bernstein/icons/radio.gif");
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
			thumbHorIconRollover = new LazyImageIcon("bernstein/icons/radio_rollover.gif");
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
			thumbVerIcon = new LazyImageIcon("bernstein/icons/radio.gif");
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
			thumbVerIconRollover = new LazyImageIcon("bernstein/icons/radio_rollover.gif");
		}
		return thumbVerIconRollover;
	}

} // end of class BernsteinIcons
