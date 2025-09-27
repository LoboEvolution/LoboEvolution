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

package com.jtattoo.plaf.base;

import com.jtattoo.plaf.*;
import com.jtattoo.plaf.base.icon.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;

/**
 * <p>BaseIcons class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseIcons {

	// -----------------------------------------------------------------------------------------------------------
	private final static class CheckBoxIcon implements Icon {

		private static final int GAP = 2;
		private static final Icon SMALL_CHECK_ICON = new LazyImageIcon("icons/small/check_symbol_10x10.png");
		private static final Icon SMALL_CHECK_DISABLED_ICON = new LazyImageIcon(
				"icons/small/check_symbol_disabled_10x10.png");
		private static final Icon MEDIUM_CHECK_ICON = new LazyImageIcon("icons/medium/check_symbol_12x12.png");
		private static final Icon MEDIUM_CHECK_DISABLED_ICON = new LazyImageIcon(
				"icons/medium/check_symbol_disabled_12x12.png");
		private static final Icon LARGE_CHECK_ICON = new LazyImageIcon("icons/large/check_symbol_14x14.png");
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
			if (c instanceof JCheckBoxMenuItem) {
				g.setColor(Color.white);
				g.fillRect(x, y, w, h);
				if (button.isEnabled()) {
					g.setColor(AbstractLookAndFeel.getFrameColor());
				} else {
					g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 40));
				}
				g.drawRect(x, y, w, h);
			} else {
				if (button.isEnabled()) {
					if (button.isRolloverEnabled() && model.isRollover()) {
						JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getRolloverColors(), x, y, w,
								h);
					} else {
						if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
							JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getFocusColors(), x, y,
									w, h);
						} else {
							JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getCheckBoxColors(), x,
									y, w, h);
						}
						if (!model.isPressed()) {
							g.setColor(Color.white);
							g.drawLine(x + 1, y + 1, x + 1, y + h - 2);
							g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 2);
						}
					}
					if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
						final Color hiColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFocusFrameColor(), 30);
						final Color loColor = ColorHelper.darker(AbstractLookAndFeel.getTheme().getFocusFrameColor(), 20);
						g.setColor(hiColor);
						g.drawRect(x - 1, y - 1, w + 2, h + 2);
						g.setColor(loColor);
						g.drawRect(x, y, w, h);
					} else {
						g.setColor(AbstractLookAndFeel.getFrameColor());
						g.drawRect(x, y, w, h);
					}
				} else {
					JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getDisabledColors(), x, y, w, h);
					g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 40));
					g.drawRect(x, y, w, h);
				}
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
			final int xi = x + (w - checkIcon.getIconWidth()) / 2 + 1;
			final int yi = y + (h - checkIcon.getIconHeight()) / 2 + 1;
			if (model.isPressed() && model.isArmed()) {
				final Color bc = AbstractLookAndFeel.getTheme().getSelectionBackgroundColor();
				final Color fc = ColorHelper.darker(bc, 40);
				g.setColor(fc);
				g.drawRect(x + 3, y + 3, w - 6, h - 6);
				g.setColor(bc);
				g.fillRect(x + 4, y + 4, w - 7, h - 7);
			} else if (model.isSelected()) {
				if (button.isEnabled()) {
					checkIcon.paintIcon(c, g, xi, yi);
				} else {
					checkDisabledIcon.paintIcon(c, g, xi, yi);
				}
			}
		}
	}

	/** Constant PEARL_RED_SMALL */
	public static final LazyImageIcon PEARL_RED_SMALL = new LazyImageIcon("icons/small/pearl_red_24x24.png");
	/** Constant PEARL_YELLOW_SMALL */
	public static final LazyImageIcon PEARL_YELLOW_SMALL = new LazyImageIcon("icons/small/pearl_yellow_24x24.png");

	/** Constant PEARL_GREEN_SMALL */
	public static final LazyImageIcon PEARL_GREEN_SMALL = new LazyImageIcon("icons/small/pearl_green_24x24.png");
	/** Constant PEARL_GREY_SMALL */
	public static final LazyImageIcon PEARL_GREY_SMALL = new LazyImageIcon("icons/small/pearl_grey_24x24.png");
	/** Constant PEARL_RED_MEDIUM */
	public static final LazyImageIcon PEARL_RED_MEDIUM = new LazyImageIcon("icons/medium/pearl_red_28x28.png");
	/** Constant PEARL_YELLOW_MEDIUM */
	public static final LazyImageIcon PEARL_YELLOW_MEDIUM = new LazyImageIcon("icons/medium/pearl_yellow_28x28.png");
	/** Constant PEARL_GREEN_MEDIUM */
	public static final LazyImageIcon PEARL_GREEN_MEDIUM = new LazyImageIcon("icons/medium/pearl_green_28x28.png");
	/** Constant PEARL_GREY_MEDIUM */
	public static final LazyImageIcon PEARL_GREY_MEDIUM = new LazyImageIcon("icons/medium/pearl_grey_28x28.png");
	/** Constant PEARL_RED_LARGE */
	public static final LazyImageIcon PEARL_RED_LARGE = new LazyImageIcon("icons/large/pearl_red_32x32.png");
	/** Constant PEARL_YELLOW_LARGE */
	public static final LazyImageIcon PEARL_YELLOW_LARGE = new LazyImageIcon("icons/large/pearl_yellow_32x32.png");
	/** Constant PEARL_GREEN_LARGE */
	public static final LazyImageIcon PEARL_GREEN_LARGE = new LazyImageIcon("icons/large/pearl_green_32x32.png");
	/** Constant PEARL_GREY_LARGE */
	public static final LazyImageIcon PEARL_GREY_LARGE = new LazyImageIcon("icons/large/pearl_grey_32x32.png");
	/** Constant ICONIZER_SMALL */
	public static final LazyImageIcon ICONIZER_SMALL = new LazyImageIcon("icons/small/iconizer_10x10.png");
	/** Constant MINIMIZER_SMALL */
	public static final LazyImageIcon MINIMIZER_SMALL = new LazyImageIcon("icons/small/minimizer_10x10.png");

	/** Constant MAXIMIZER_SMALL */
	public static final LazyImageIcon MAXIMIZER_SMALL = new LazyImageIcon("icons/small/maximizer_10x10.png");

	/** Constant CLOSER_SMALL */
	public static final LazyImageIcon CLOSER_SMALL = new LazyImageIcon("icons/small/closer_10x10.png");
	/** Constant ICONIZER_MEDIUM */
	public static final LazyImageIcon ICONIZER_MEDIUM = new LazyImageIcon("icons/medium/iconizer_12x12.png");
	/** Constant MINIMIZER_MEDIUM */
	public static final LazyImageIcon MINIMIZER_MEDIUM = new LazyImageIcon("icons/medium/minimizer_12x12.png");
	/** Constant MAXIMIZER_MEDIUM */
	public static final LazyImageIcon MAXIMIZER_MEDIUM = new LazyImageIcon("icons/medium/maximizer_12x12.png");
	/** Constant CLOSER_MEDIUM */
	public static final LazyImageIcon CLOSER_MEDIUM = new LazyImageIcon("icons/medium/closer_12x12.png");
	/** Constant ICONIZER_LARGE */
	public static final LazyImageIcon ICONIZER_LARGE = new LazyImageIcon("icons/large/iconizer_12x12.png");
	/** Constant MINIMIZER_LARGE */
	public static final LazyImageIcon MINIMIZER_LARGE = new LazyImageIcon("icons/large/minimizer_12x12.png");
	/** Constant MAXIMIZER_LARGE */
	public static final LazyImageIcon MAXIMIZER_LARGE = new LazyImageIcon("icons/large/maximizer_12x12.png");

	/** Constant CLOSER_LARGE */
	public static final LazyImageIcon CLOSER_LARGE = new LazyImageIcon("icons/large/closer_12x12.png");
	/** Constant EMPTY_8x8 */
	public static final LazyImageIcon EMPTY_8x8 = new LazyImageIcon("icons/empty_8x8.png");
	/** Constant checkBoxIcon */
	protected static Icon checkBoxIcon = null;
	/** Constant menuCheckBoxIcon */
	protected static Icon menuCheckBoxIcon = null;
	/** Constant radioButtonIcon */
	protected static Icon radioButtonIcon = null;
	/** Constant menuRadioButtonIcon */
	protected static Icon menuRadioButtonIcon = null;
	/** Constant optionPaneErrorIcon */
	protected static Icon optionPaneErrorIcon = null;
	/** Constant optionPaneWarningIcon */
	protected static Icon optionPaneWarningIcon = null;

	/** Constant optionPaneInformationIcon */
	protected static Icon optionPaneInformationIcon = null;
	/** Constant optionPaneQuestionIcon */
	protected static Icon optionPaneQuestionIcon = null;
	/** Constant fileChooserUpFolderIcon */
	protected static Icon fileChooserUpFolderIcon = null;
	/** Constant fileChooserHomeFolderIcon */
	protected static Icon fileChooserHomeFolderIcon = null;
	/** Constant fileChooserNewFolderIcon */
	protected static Icon fileChooserNewFolderIcon = null;

	/** Constant fileChooserListViewIcon */
	protected static Icon fileChooserListViewIcon = null;
	/** Constant fileChooserDetailViewIcon */
	protected static Icon fileChooserDetailViewIcon = null;

	/** Constant fileViewComputerIcon */
	protected static Icon fileViewComputerIcon = null;
	/** Constant fileViewFloppyDriveIcon */
	protected static Icon fileViewFloppyDriveIcon = null;
	/** Constant fileViewHardDriveIcon */
	protected static Icon fileViewHardDriveIcon = null;
	/** Constant treeOpenedIcon */
	protected static Icon treeOpenedIcon = null;

	/** Constant treeClosedIcon */
	protected static Icon treeClosedIcon = null;
	/** Constant treeLeafIcon */
	protected static Icon treeLeafIcon = null;
	/** Constant treeExpandedIcon */
	protected static Icon treeExpandedIcon = null;
	/** Constant treeCollapsedIcon */
	protected static Icon treeCollapsedIcon = null;
	/** Constant paletteCloseIcon */
	protected static Icon paletteCloseIcon = null;
	/** Constant menuIcon */
	protected static Icon menuIcon = null;
	/** Constant iconIcon */
	protected static Icon iconIcon = null;
	/** Constant maxIcon */
	protected static Icon maxIcon = null;
	/** Constant minIcon */
	protected static Icon minIcon = null;
	/** Constant closeIcon */
	protected static Icon closeIcon = null;
	/** Constant upArrowIcon */
	protected static Icon upArrowIcon = null;
	/** Constant upArrowInverseIcon */
	protected static Icon upArrowInverseIcon = null;
	/** Constant downArrowIcon */
	protected static Icon downArrowIcon = null;
	/** Constant downArrowInverseIcon */
	protected static Icon downArrowInverseIcon = null;
	/** Constant leftArrowIcon */
	protected static Icon leftArrowIcon = null;
	/** Constant leftArrowInverseIcon */
	protected static Icon leftArrowInverseIcon = null;
	/** Constant rightArrowIcon */
	protected static Icon rightArrowIcon = null;
	/** Constant rightArrowInverseIcon */
	protected static Icon rightArrowInverseIcon = null;
	/** Constant menuArrowIcon */
	protected static Icon menuArrowIcon = null;

	/** Constant splitterUpArrowIcon */
	protected static Icon splitterUpArrowIcon = null;

	/** Constant splitterDownArrowIcon */
	protected static Icon splitterDownArrowIcon = null;

	/** Constant splitterLeftArrowIcon */
	protected static Icon splitterLeftArrowIcon = null;

	/** Constant splitterRightArrowIcon */
	protected static Icon splitterRightArrowIcon = null;

	/** Constant splitterHorBumpIcon */
	protected static Icon splitterHorBumpIcon = null;

	/** Constant splitterVerBumpIcon */
	protected static Icon splitterVerBumpIcon = null;

	/** Constant thumbHorIcon */
	protected static Icon thumbHorIcon = null;

	/** Constant thumbVerIcon */
	protected static Icon thumbVerIcon = null;

	/** Constant thumbHorIconRollover */
	protected static Icon thumbHorIconRollover = null;

	/** Constant thumbVerIconRollover */
	protected static Icon thumbVerIconRollover = null;

	/**
	 * <p>Getter for the field checkBoxIcon.</p>
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
	 * <p>Getter for the field closeIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getCloseIcon() {
		if (closeIcon == null) {
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				closeIcon = new MacCloseIcon();
			} else {
				final Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				final Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				closeIcon = new CloseSymbol(iconColor, null, iconRolloverColor);
			}
		}
		return closeIcon;
	}

	// ComboBox
	/**
	 * <p>getComboBoxIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getComboBoxIcon() {
		return getDownArrowIcon();
	}

	/**
	 * <p>getComboBoxInverseIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getComboBoxInverseIcon() {
		return getDownArrowInverseIcon();
	}

	/**
	 * <p>Getter for the field downArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getDownArrowIcon() {
		if (downArrowIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				downArrowIcon = new LazyImageIcon("icons/small/arrow_down_7x4.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				downArrowIcon = new LazyImageIcon("icons/medium/arrow_down_9x6.png");
			} else {
				downArrowIcon = new LazyImageIcon("icons/large/arrow_down_11x8.png");
			}
		}
		return downArrowIcon;
	}

	/**
	 * <p>Getter for the field downArrowInverseIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getDownArrowInverseIcon() {
		if (downArrowInverseIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				downArrowInverseIcon = new LazyImageIcon("icons/small/arrow_down_inverse_7x4.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				downArrowInverseIcon = new LazyImageIcon("icons/medium/arrow_down_inverse_9x6.png");
			} else {
				downArrowInverseIcon = new LazyImageIcon("icons/large/arrow_down_inverse_11x8.png");
			}
		}
		return downArrowInverseIcon;
	}

	/**
	 * <p>Getter for the field fileChooserDetailViewIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getFileChooserDetailViewIcon() {
		if (fileChooserDetailViewIcon == null) {
			fileChooserDetailViewIcon = new LazyImageIcon("icons/view_detail_22x22.png");
		}
		return fileChooserDetailViewIcon;
	}

	/**
	 * <p>Getter for the field fileChooserHomeFolderIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getFileChooserHomeFolderIcon() {
		if (fileChooserHomeFolderIcon == null) {
			fileChooserHomeFolderIcon = new LazyImageIcon("icons/home_22x22.png");
		}
		return fileChooserHomeFolderIcon;
	}

	/**
	 * <p>Getter for the field fileChooserListViewIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getFileChooserListViewIcon() {
		if (fileChooserListViewIcon == null) {
			fileChooserListViewIcon = new LazyImageIcon("icons/view_list_22x22.png");
		}
		return fileChooserListViewIcon;
	}

	/**
	 * <p>Getter for the field fileChooserNewFolderIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getFileChooserNewFolderIcon() {
		if (fileChooserNewFolderIcon == null) {
			fileChooserNewFolderIcon = new LazyImageIcon("icons/folder_new_22x22.png");
		}
		return fileChooserNewFolderIcon;
	}

	// FileChooser
	/**
	 * <p>Getter for the field fileChooserUpFolderIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getFileChooserUpFolderIcon() {
		if (fileChooserUpFolderIcon == null) {
			fileChooserUpFolderIcon = new LazyImageIcon("icons/folder_up_22x22.png");
		}
		return fileChooserUpFolderIcon;
	}

	/**
	 * <p>Getter for the field fileViewComputerIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getFileViewComputerIcon() {
		if (fileViewComputerIcon == null) {
			fileViewComputerIcon = new LazyImageIcon("icons/computer_16x16.png");
		}
		return fileViewComputerIcon;
	}

	/**
	 * <p>Getter for the field fileViewFloppyDriveIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getFileViewFloppyDriveIcon() {
		if (fileViewFloppyDriveIcon == null) {
			fileViewFloppyDriveIcon = new LazyImageIcon("icons/floppy_drive_16x16.png");
		}
		return fileViewFloppyDriveIcon;
	}

	/**
	 * <p>Getter for the field fileViewHardDriveIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getFileViewHardDriveIcon() {
		if (fileViewHardDriveIcon == null) {
			fileViewHardDriveIcon = new LazyImageIcon("icons/hard_drive_16x16.png");
		}
		return fileViewHardDriveIcon;
	}

	/**
	 * <p>Getter for the field iconIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getIconIcon() {
		if (iconIcon == null) {
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				iconIcon = new MacIconIcon();
			} else {
				final Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				final Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				iconIcon = new IconSymbol(iconColor, null, iconRolloverColor);
			}
		}
		return iconIcon;
	}

	/**
	 * <p>Getter for the field leftArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getLeftArrowIcon() {
		if (leftArrowIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				leftArrowIcon = new LazyImageIcon("icons/small/arrow_left_4x7.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				leftArrowIcon = new LazyImageIcon("icons/medium/arrow_left_6x9.png");
			} else {
				leftArrowIcon = new LazyImageIcon("icons/large/arrow_left_8x11.png");
			}
		}
		return leftArrowIcon;
	}

	/**
	 * <p>Getter for the field leftArrowInverseIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getLeftArrowInverseIcon() {
		if (leftArrowInverseIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				leftArrowInverseIcon = new LazyImageIcon("icons/small/arrow_left_inverse_4x7.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				leftArrowInverseIcon = new LazyImageIcon("icons/medium/arrow_left_inverse_6x9.png");
			} else {
				leftArrowInverseIcon = new LazyImageIcon("icons/large/arrow_left_inverse_8x11.png");
			}
		}
		return leftArrowInverseIcon;
	}

	/**
	 * <p>Getter for the field maxIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getMaxIcon() {
		if (maxIcon == null) {
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				maxIcon = new MacMaxIcon();
			} else {
				final Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				final Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				maxIcon = new MaxSymbol(iconColor, null, iconRolloverColor);
			}
		}
		return maxIcon;
	}

	// MenuIcons
	/**
	 * <p>Getter for the field menuArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getMenuArrowIcon() {
		if (menuArrowIcon == null) {
			menuArrowIcon = new LazyMenuArrowImageIcon("icons/MenuRightArrow.gif", "icons/MenuLeftArrow.gif");
		}
		return menuArrowIcon;
	}

	/**
	 * <p>Getter for the field menuCheckBoxIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getMenuCheckBoxIcon() {
		if (menuCheckBoxIcon == null) {
			menuCheckBoxIcon = new CheckBoxIcon();
		}
		return menuCheckBoxIcon;
	}

	// TitlePane icons
	/**
	 * <p>Getter for the field menuIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getMenuIcon() {
		if (menuIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				menuIcon = new LazyImageIcon("icons/small/cup_16x16.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				menuIcon = new LazyImageIcon("icons/medium/cup_20x20.png");
			} else {
				menuIcon = new LazyImageIcon("icons/large/cup_24x24.png");
			}
		}
		return menuIcon;
	}

	/**
	 * <p>Getter for the field menuRadioButtonIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getMenuRadioButtonIcon() {
		if (menuRadioButtonIcon == null) {
			menuRadioButtonIcon = new RadioButtonIcon();
		}
		return menuRadioButtonIcon;
	}

	/**
	 * <p>Getter for the field minIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getMinIcon() {
		if (minIcon == null) {
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				minIcon = new MacMinIcon();
			} else {
				final Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				final Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				minIcon = new MinSymbol(iconColor, null, iconRolloverColor);
			}
		}
		return minIcon;
	}

	// OptionPane
	/**
	 * <p>Getter for the field optionPaneErrorIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getOptionPaneErrorIcon() {
		if (optionPaneErrorIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				optionPaneErrorIcon = new LazyImageIcon("icons/medium/error_32x32.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				optionPaneErrorIcon = new LazyImageIcon("icons/medium/error_32x32.png");
			} else {
				optionPaneErrorIcon = new LazyImageIcon("icons/large/error_48x48.png");
			}
		}
		return optionPaneErrorIcon;
	}

	/**
	 * <p>Getter for the field optionPaneInformationIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getOptionPaneInformationIcon() {
		if (optionPaneInformationIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				optionPaneInformationIcon = new LazyImageIcon("icons/medium/information_32x32.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				optionPaneInformationIcon = new LazyImageIcon("icons/medium/information_32x32.png");
			} else {
				optionPaneInformationIcon = new LazyImageIcon("icons/large/information_48x48.png");
			}
		}
		return optionPaneInformationIcon;
	}

	/**
	 * <p>Getter for the field optionPaneQuestionIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getOptionPaneQuestionIcon() {
		if (optionPaneQuestionIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				optionPaneQuestionIcon = new LazyImageIcon("icons/medium/question_32x32.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				optionPaneQuestionIcon = new LazyImageIcon("icons/medium/question_32x32.png");
			} else {
				optionPaneQuestionIcon = new LazyImageIcon("icons/large/question_48x48.png");
			}
		}
		return optionPaneQuestionIcon;
	}

	/**
	 * <p>Getter for the field optionPaneWarningIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getOptionPaneWarningIcon() {
		if (optionPaneWarningIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				optionPaneWarningIcon = new LazyImageIcon("icons/medium/warning_32x32.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				optionPaneWarningIcon = new LazyImageIcon("icons/medium/warning_32x32.png");
			} else {
				optionPaneWarningIcon = new LazyImageIcon("icons/large/warning_48x48.png");
			}
		}
		return optionPaneWarningIcon;
	}

	/**
	 * <p>Getter for the field paletteCloseIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getPaletteCloseIcon() {
		if (paletteCloseIcon == null) {
			paletteCloseIcon = new CloseSymbol(Color.black, null, Color.red);
		}
		return paletteCloseIcon;
	}

	/**
	 * <p>Getter for the field radioButtonIcon.</p>
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
	 * <p>Getter for the field rightArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getRightArrowIcon() {
		if (rightArrowIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				rightArrowIcon = new LazyImageIcon("icons/small/arrow_right_4x7.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				rightArrowIcon = new LazyImageIcon("icons/medium/arrow_right_6x9.png");
			} else {
				rightArrowIcon = new LazyImageIcon("icons/large/arrow_right_8x11.png");
			}
		}
		return rightArrowIcon;
	}

	/**
	 * <p>Getter for the field rightArrowInverseIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getRightArrowInverseIcon() {
		if (rightArrowInverseIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				rightArrowInverseIcon = new LazyImageIcon("icons/small/arrow_right_inverse_4x7.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				rightArrowInverseIcon = new LazyImageIcon("icons/medium/arrow_right_inverse_6x9.png");
			} else {
				rightArrowInverseIcon = new LazyImageIcon("icons/large/arrow_right_inverse_8x11.png");
			}
		}
		return rightArrowInverseIcon;
	}

	/**
	 * <p>Getter for the field splitterDownArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterDownArrowIcon() {
		if (splitterDownArrowIcon == null) {
			splitterDownArrowIcon = new LazyImageIcon("icons/SplitterDownArrow.gif");
		}
		return splitterDownArrowIcon;
	}

	/**
	 * <p>Getter for the field splitterHorBumpIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterHorBumpIcon() {
		if (splitterHorBumpIcon == null) {
			splitterHorBumpIcon = new LazyImageIcon("icons/SplitterHorBumps.gif");
		}
		return splitterHorBumpIcon;
	}

	/**
	 * <p>Getter for the field splitterLeftArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterLeftArrowIcon() {
		if (splitterLeftArrowIcon == null) {
			splitterLeftArrowIcon = new LazyImageIcon("icons/SplitterLeftArrow.gif");
		}
		return splitterLeftArrowIcon;
	}

	/**
	 * <p>Getter for the field splitterRightArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterRightArrowIcon() {
		if (splitterRightArrowIcon == null) {
			splitterRightArrowIcon = new LazyImageIcon("icons/SplitterRightArrow.gif");
		}
		return splitterRightArrowIcon;
	}

	// Splitter
	/**
	 * <p>Getter for the field splitterUpArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterUpArrowIcon() {
		if (splitterUpArrowIcon == null) {
			splitterUpArrowIcon = new LazyImageIcon("icons/SplitterUpArrow.gif");
		}
		return splitterUpArrowIcon;
	}

	/**
	 * <p>Getter for the field splitterVerBumpIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getSplitterVerBumpIcon() {
		if (splitterVerBumpIcon == null) {
			splitterVerBumpIcon = new LazyImageIcon("icons/SplitterVerBumps.gif");
		}
		return splitterVerBumpIcon;
	}

	// Slider
	/**
	 * <p>Getter for the field thumbHorIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbHorIcon() {
		if (thumbHorIcon == null) {
			thumbHorIcon = new LazyImageIcon("icons/thumb_hor.gif");
		}
		return thumbHorIcon;
	}

	/**
	 * <p>Getter for the field thumbHorIconRollover.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbHorIconRollover() {
		if (thumbHorIconRollover == null) {
			thumbHorIconRollover = new LazyImageIcon("icons/thumb_hor_rollover.gif");
		}
		return thumbHorIconRollover;
	}

	/**
	 * <p>Getter for the field thumbVerIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbVerIcon() {
		if (thumbVerIcon == null) {
			thumbVerIcon = new LazyImageIcon("icons/thumb_ver.gif");
		}
		return thumbVerIcon;
	}

	/**
	 * <p>Getter for the field thumbVerIconRollover.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getThumbVerIconRollover() {
		if (thumbVerIconRollover == null) {
			thumbVerIconRollover = new LazyImageIcon("icons/thumb_ver_rollover.gif");
		}
		return thumbVerIconRollover;
	}

	/**
	 * <p>Getter for the field treeClosedIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getTreeClosedIcon() {
		if (treeClosedIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				treeClosedIcon = new LazyImageIcon("icons/small/folder_closed_16x16.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				treeClosedIcon = new LazyImageIcon("icons/medium/folder_closed_20x20.png");
			} else {
				treeClosedIcon = new LazyImageIcon("icons/large/folder_closed_24x24.png");
			}
		}
		return treeClosedIcon;
	}

	/**
	 * <p>Getter for the field treeCollapsedIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getTreeCollapsedIcon() {
		if (treeCollapsedIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				treeCollapsedIcon = new LazyImageIcon("icons/small/tree_collapsed_9x9.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				treeCollapsedIcon = new LazyImageIcon("icons/medium/tree_collapsed_11x11.png");
			} else {
				treeCollapsedIcon = new LazyImageIcon("icons/large/tree_collapsed_14x14.png");
			}
		}
		return treeCollapsedIcon;
	}

	/**
	 * <p>Getter for the field treeExpandedIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getTreeExpandedIcon() {
		if (treeExpandedIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				treeExpandedIcon = new LazyImageIcon("icons/small/tree_expanded_9x9.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				treeExpandedIcon = new LazyImageIcon("icons/medium/tree_expanded_11x11.png");
			} else {
				treeExpandedIcon = new LazyImageIcon("icons/large/tree_expanded_14x14.png");
			}
		}
		return treeExpandedIcon;
	}

	/**
	 * <p>Getter for the field treeLeafIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getTreeLeafIcon() {
		if (treeLeafIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				treeLeafIcon = new LazyImageIcon("icons/small/document_16x16.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				treeLeafIcon = new LazyImageIcon("icons/medium/document_20x20.png");
			} else {
				treeLeafIcon = new LazyImageIcon("icons/large/document_24x24.png");
			}
		}
		return treeLeafIcon;
	}

// Tree
	/**
	 * <p>Getter for the field treeOpenedIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getTreeOpenedIcon() {
		if (treeOpenedIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				treeOpenedIcon = new LazyImageIcon("icons/small/folder_opened_16x16.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				treeOpenedIcon = new LazyImageIcon("icons/medium/folder_opened_20x20.png");
			} else {
				treeOpenedIcon = new LazyImageIcon("icons/large/folder_opened_24x24.png");
			}
		}
		return treeOpenedIcon;
	}

// ArrowIcons
	/**
	 * <p>Getter for the field upArrowIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getUpArrowIcon() {
		if (upArrowIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				upArrowIcon = new LazyImageIcon("icons/small/arrow_up_7x4.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				upArrowIcon = new LazyImageIcon("icons/medium/arrow_up_9x6.png");
			} else {
				upArrowIcon = new LazyImageIcon("icons/large/arrow_up_11x8.png");
			}
		}
		return upArrowIcon;
	}

	/**
	 * <p>Getter for the field upArrowInverseIcon.</p>
	 *
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getUpArrowInverseIcon() {
		if (upArrowInverseIcon == null) {
			if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
				upArrowInverseIcon = new LazyImageIcon("icons/small/arrow_up_inverse_7x4.png");
			} else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
				upArrowInverseIcon = new LazyImageIcon("icons/medium/arrow_up_inverse_9x6.png");
			} else {
				upArrowInverseIcon = new LazyImageIcon("icons/large/arrow_up_inverse_11x8.png");
			}
		}
		return upArrowInverseIcon;
	}

	/**
	 * <p>initDefaults.</p>
	 */
	public static void initDefaults() {
		checkBoxIcon = null;
		menuCheckBoxIcon = null;
		radioButtonIcon = null;
		menuRadioButtonIcon = null;
		optionPaneErrorIcon = null;
		optionPaneWarningIcon = null;
		optionPaneInformationIcon = null;
		optionPaneQuestionIcon = null;
		fileChooserUpFolderIcon = null;
		fileChooserHomeFolderIcon = null;
		fileChooserNewFolderIcon = null;
		fileChooserListViewIcon = null;
		fileChooserDetailViewIcon = null;
		fileViewComputerIcon = null;
		fileViewFloppyDriveIcon = null;
		fileViewHardDriveIcon = null;
		treeOpenedIcon = null;
		treeClosedIcon = null;
		treeLeafIcon = null;
		treeExpandedIcon = null;
		treeCollapsedIcon = null;
		paletteCloseIcon = null;
		menuIcon = null;
		iconIcon = null;
		maxIcon = null;
		minIcon = null;
		closeIcon = null;
		upArrowIcon = null;
		upArrowInverseIcon = null;
		downArrowIcon = null;
		downArrowInverseIcon = null;
		leftArrowIcon = null;
		leftArrowInverseIcon = null;
		rightArrowIcon = null;
		rightArrowInverseIcon = null;
		menuArrowIcon = null;
		splitterUpArrowIcon = null;
		splitterDownArrowIcon = null;
		splitterLeftArrowIcon = null;
		splitterRightArrowIcon = null;
		splitterHorBumpIcon = null;
		splitterVerBumpIcon = null;
		thumbHorIcon = null;
		thumbVerIcon = null;
		thumbHorIconRollover = null;
		thumbVerIconRollover = null;
	}

} // end of class BaseIcons
