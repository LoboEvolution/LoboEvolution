/*
* Copyright (c) 2002 and later by MH Software-Entwicklung. All Rights Reserved.
*  
* JTattoo is multiple licensed. If your are an open source developer you can use
* it under the terms and conditions of the GNU General Public License version 2.0
* or later as published by the Free Software Foundation.
*  
* see: gpl-2.0.txt
* 
* If you pay for a license you will become a registered user who could use the
* software under the terms and conditions of the GNU Lesser General Public License
* version 2.0 or later with classpath exception as published by the Free Software
* Foundation.
* 
* see: lgpl-2.0.txt
* see: classpath-exception.txt
* 
* Registered users could also use JTattoo under the terms and conditions of the 
* Apache License, Version 2.0 as published by the Apache Software Foundation.
*  
* see: APACHE-LICENSE-2.0.txt
*/

package com.jtattoo.plaf;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.plaf.UIResource;

/**
 * The Class BaseIcons.
 *
 * @author Michael Hagen
 */
public class BaseIcons {
    
    /** The Constant PEARL_RED_SMALL. */
    public static final LazyImageIcon PEARL_RED_SMALL = new LazyImageIcon("icons/small/pearl_red_24x24.png");
    
    /** The Constant PEARL_YELLOW_SMALL. */
    public static final LazyImageIcon PEARL_YELLOW_SMALL = new LazyImageIcon("icons/small/pearl_yellow_24x24.png");
    
    /** The Constant PEARL_GREEN_SMALL. */
    public static final LazyImageIcon PEARL_GREEN_SMALL = new LazyImageIcon("icons/small/pearl_green_24x24.png");
    
    /** The Constant PEARL_GREY_SMALL. */
    public static final LazyImageIcon PEARL_GREY_SMALL = new LazyImageIcon("icons/small/pearl_grey_24x24.png");
    
    /** The Constant PEARL_RED_MEDIUM. */
    public static final LazyImageIcon PEARL_RED_MEDIUM = new LazyImageIcon("icons/medium/pearl_red_28x28.png");
    
    /** The Constant PEARL_YELLOW_MEDIUM. */
    public static final LazyImageIcon PEARL_YELLOW_MEDIUM = new LazyImageIcon("icons/medium/pearl_yellow_28x28.png");
    
    /** The Constant PEARL_GREEN_MEDIUM. */
    public static final LazyImageIcon PEARL_GREEN_MEDIUM = new LazyImageIcon("icons/medium/pearl_green_28x28.png");
    
    /** The Constant PEARL_GREY_MEDIUM. */
    public static final LazyImageIcon PEARL_GREY_MEDIUM = new LazyImageIcon("icons/medium/pearl_grey_28x28.png");
    
    /** The Constant PEARL_RED_LARGE. */
    public static final LazyImageIcon PEARL_RED_LARGE = new LazyImageIcon("icons/large/pearl_red_32x32.png");
    
    /** The Constant PEARL_YELLOW_LARGE. */
    public static final LazyImageIcon PEARL_YELLOW_LARGE = new LazyImageIcon("icons/large/pearl_yellow_32x32.png");
    
    /** The Constant PEARL_GREEN_LARGE. */
    public static final LazyImageIcon PEARL_GREEN_LARGE = new LazyImageIcon("icons/large/pearl_green_32x32.png");
    
    /** The Constant PEARL_GREY_LARGE. */
    public static final LazyImageIcon PEARL_GREY_LARGE = new LazyImageIcon("icons/large/pearl_grey_32x32.png");
    
    /** The Constant ICONIZER_SMALL. */
    public static final LazyImageIcon ICONIZER_SMALL = new LazyImageIcon("icons/small/iconizer_10x10.png");
    
    /** The Constant MINIMIZER_SMALL. */
    public static final LazyImageIcon MINIMIZER_SMALL = new LazyImageIcon("icons/small/minimizer_10x10.png");
    
    /** The Constant MAXIMIZER_SMALL. */
    public static final LazyImageIcon MAXIMIZER_SMALL = new LazyImageIcon("icons/small/maximizer_10x10.png");
    
    /** The Constant CLOSER_SMALL. */
    public static final LazyImageIcon CLOSER_SMALL = new LazyImageIcon("icons/small/closer_10x10.png");
    
    /** The Constant ICONIZER_MEDIUM. */
    public static final LazyImageIcon ICONIZER_MEDIUM = new LazyImageIcon("icons/medium/iconizer_12x12.png");
    
    /** The Constant MINIMIZER_MEDIUM. */
    public static final LazyImageIcon MINIMIZER_MEDIUM = new LazyImageIcon("icons/medium/minimizer_12x12.png");
    
    /** The Constant MAXIMIZER_MEDIUM. */
    public static final LazyImageIcon MAXIMIZER_MEDIUM = new LazyImageIcon("icons/medium/maximizer_12x12.png");
    
    /** The Constant CLOSER_MEDIUM. */
    public static final LazyImageIcon CLOSER_MEDIUM = new LazyImageIcon("icons/medium/closer_12x12.png");
    
    /** The Constant ICONIZER_LARGE. */
    public static final LazyImageIcon ICONIZER_LARGE = new LazyImageIcon("icons/large/iconizer_12x12.png");
    
    /** The Constant MINIMIZER_LARGE. */
    public static final LazyImageIcon MINIMIZER_LARGE = new LazyImageIcon("icons/large/minimizer_12x12.png");
    
    /** The Constant MAXIMIZER_LARGE. */
    public static final LazyImageIcon MAXIMIZER_LARGE = new LazyImageIcon("icons/large/maximizer_12x12.png");
    
    /** The Constant CLOSER_LARGE. */
    public static final LazyImageIcon CLOSER_LARGE = new LazyImageIcon("icons/large/closer_12x12.png");
    
    /** The Constant EMPTY_8x8. */
    public static final LazyImageIcon EMPTY_8x8 = new LazyImageIcon("icons/empty_8x8.png");

    /** The check box icon. */
    protected static Icon checkBoxIcon = null;
    
    /** The menu check box icon. */
    protected static Icon menuCheckBoxIcon = null;
    
    /** The radio button icon. */
    protected static Icon radioButtonIcon = null;
    
    /** The menu radio button icon. */
    protected static Icon menuRadioButtonIcon = null;
    
    /** The option pane error icon. */
    protected static Icon optionPaneErrorIcon = null;
    
    /** The option pane warning icon. */
    protected static Icon optionPaneWarningIcon = null;
    
    /** The option pane information icon. */
    protected static Icon optionPaneInformationIcon = null;
    
    /** The option pane question icon. */
    protected static Icon optionPaneQuestionIcon = null;
    
    /** The file chooser up folder icon. */
    protected static Icon fileChooserUpFolderIcon = null;
    
    /** The file chooser home folder icon. */
    protected static Icon fileChooserHomeFolderIcon = null;
    
    /** The file chooser new folder icon. */
    protected static Icon fileChooserNewFolderIcon = null;
    
    /** The file chooser list view icon. */
    protected static Icon fileChooserListViewIcon = null;
    
    /** The file chooser detail view icon. */
    protected static Icon fileChooserDetailViewIcon = null;
    
    /** The file view computer icon. */
    protected static Icon fileViewComputerIcon = null;
    
    /** The file view floppy drive icon. */
    protected static Icon fileViewFloppyDriveIcon = null;
    
    /** The file view hard drive icon. */
    protected static Icon fileViewHardDriveIcon = null;
    
    /** The tree opened icon. */
    protected static Icon treeOpenedIcon = null;
    
    /** The tree closed icon. */
    protected static Icon treeClosedIcon = null;
    
    /** The tree leaf icon. */
    protected static Icon treeLeafIcon = null;
    
    /** The tree expanded icon. */
    protected static Icon treeExpandedIcon = null;
    
    /** The tree collapsed icon. */
    protected static Icon treeCollapsedIcon = null;
    
    /** The palette close icon. */
    protected static Icon paletteCloseIcon = null;
    
    /** The menu icon. */
    protected static Icon menuIcon = null;
    
    /** The icon icon. */
    protected static Icon iconIcon = null;
    
    /** The max icon. */
    protected static Icon maxIcon = null;
    
    /** The min icon. */
    protected static Icon minIcon = null;
    
    /** The close icon. */
    protected static Icon closeIcon = null;
    
    /** The up arrow icon. */
    protected static Icon upArrowIcon = null;
    
    /** The up arrow inverse icon. */
    protected static Icon upArrowInverseIcon = null;
    
    /** The down arrow icon. */
    protected static Icon downArrowIcon = null;
    
    /** The down arrow inverse icon. */
    protected static Icon downArrowInverseIcon = null;
    
    /** The left arrow icon. */
    protected static Icon leftArrowIcon = null;
    
    /** The left arrow inverse icon. */
    protected static Icon leftArrowInverseIcon = null;
    
    /** The right arrow icon. */
    protected static Icon rightArrowIcon = null;
    
    /** The right arrow inverse icon. */
    protected static Icon rightArrowInverseIcon = null;
    
    /** The menu arrow icon. */
    protected static Icon menuArrowIcon = null;
    
    /** The splitter up arrow icon. */
    protected static Icon splitterUpArrowIcon = null;
    
    /** The splitter down arrow icon. */
    protected static Icon splitterDownArrowIcon = null;
    
    /** The splitter left arrow icon. */
    protected static Icon splitterLeftArrowIcon = null;
    
    /** The splitter right arrow icon. */
    protected static Icon splitterRightArrowIcon = null;
    
    /** The splitter hor bump icon. */
    protected static Icon splitterHorBumpIcon = null;
    
    /** The splitter ver bump icon. */
    protected static Icon splitterVerBumpIcon = null;
    
    /** The thumb hor icon. */
    protected static Icon thumbHorIcon = null;
    
    /** The thumb ver icon. */
    protected static Icon thumbVerIcon = null;
    
    /** The thumb hor icon rollover. */
    protected static Icon thumbHorIconRollover = null;
    
    /** The thumb ver icon rollover. */
    protected static Icon thumbVerIconRollover = null;

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

    /** Gets the radio button icon.
	 *
	 * @return the radio button icon
	 */
    public static Icon getRadioButtonIcon() {
        if (radioButtonIcon == null) {
            radioButtonIcon = new RadioButtonIcon();
        }
        return radioButtonIcon;
    }

    /** Gets the check box icon.
	 *
	 * @return the check box icon
	 */
    public static Icon getCheckBoxIcon() {
        if (checkBoxIcon == null) {
            checkBoxIcon = new CheckBoxIcon();
        }
        return checkBoxIcon;
    }

    /** Gets the combo box icon.
	 *
	 * @return the combo box icon
	 */
    // ComboBox
    public static Icon getComboBoxIcon() {
        return getDownArrowIcon();
    }

    /** Gets the combo box inverse icon.
	 *
	 * @return the combo box inverse icon
	 */
    public static Icon getComboBoxInverseIcon() {
        return getDownArrowInverseIcon();
    }

    /** Gets the option pane error icon.
	 *
	 * @return the option pane error icon
	 */
    // OptionPane
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

    /** Gets the option pane warning icon.
	 *
	 * @return the option pane warning icon
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

    /** Gets the option pane information icon.
	 *
	 * @return the option pane information icon
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

    /** Gets the option pane question icon.
	 *
	 * @return the option pane question icon
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

    /** Gets the file chooser up folder icon.
	 *
	 * @return the file chooser up folder icon
	 */
    // FileChooser
    public static Icon getFileChooserUpFolderIcon() {
        if (fileChooserUpFolderIcon == null) {
            fileChooserUpFolderIcon = new LazyImageIcon("icons/folder_up_22x22.png");
        }
        return fileChooserUpFolderIcon;
    }

    /** Gets the file chooser home folder icon.
	 *
	 * @return the file chooser home folder icon
	 */
    public static Icon getFileChooserHomeFolderIcon() {
        if (fileChooserHomeFolderIcon == null) {
            fileChooserHomeFolderIcon = new LazyImageIcon("icons/home_22x22.png");
        }
        return fileChooserHomeFolderIcon;
    }

    /** Gets the file chooser new folder icon.
	 *
	 * @return the file chooser new folder icon
	 */
    public static Icon getFileChooserNewFolderIcon() {
        if (fileChooserNewFolderIcon == null) {
            fileChooserNewFolderIcon = new LazyImageIcon("icons/folder_new_22x22.png");
        }
        return fileChooserNewFolderIcon;
    }

    /** Gets the file chooser list view icon.
	 *
	 * @return the file chooser list view icon
	 */
    public static Icon getFileChooserListViewIcon() {
        if (fileChooserListViewIcon == null) {
            fileChooserListViewIcon = new LazyImageIcon("icons/view_list_22x22.png");
        }
        return fileChooserListViewIcon;
    }

    /** Gets the file chooser detail view icon.
	 *
	 * @return the file chooser detail view icon
	 */
    public static Icon getFileChooserDetailViewIcon() {
        if (fileChooserDetailViewIcon == null) {
            fileChooserDetailViewIcon = new LazyImageIcon("icons/view_detail_22x22.png");
        }
        return fileChooserDetailViewIcon;
    }

    /** Gets the file view computer icon.
	 *
	 * @return the file view computer icon
	 */
    public static Icon getFileViewComputerIcon() {
        if (fileViewComputerIcon == null) {
            fileViewComputerIcon = new LazyImageIcon("icons/computer_16x16.png");
        }
        return fileViewComputerIcon;
    }

    /** Gets the file view floppy drive icon.
	 *
	 * @return the file view floppy drive icon
	 */
    public static Icon getFileViewFloppyDriveIcon() {
        if (fileViewFloppyDriveIcon == null) {
            fileViewFloppyDriveIcon = new LazyImageIcon("icons/floppy_drive_16x16.png");
        }
        return fileViewFloppyDriveIcon;
    }

    /** Gets the file view hard drive icon.
	 *
	 * @return the file view hard drive icon
	 */
    public static Icon getFileViewHardDriveIcon() {
        if (fileViewHardDriveIcon == null) {
            fileViewHardDriveIcon = new LazyImageIcon("icons/hard_drive_16x16.png");
        }
        return fileViewHardDriveIcon;
    }

    /** Gets the tree opened icon.
	 *
	 * @return the tree opened icon
	 */
    // Tree
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
    
    /** Gets the tree closed icon.
	 *
	 * @return the tree closed icon
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

    /** Gets the tree leaf icon.
	 *
	 * @return the tree leaf icon
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

    /** Gets the tree collapsed icon.
	 *
	 * @return the tree collapsed icon
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
    
    /** Gets the tree expanded icon.
	 *
	 * @return the tree expanded icon
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

    /** Gets the menu icon.
	 *
	 * @return the menu icon
	 */
    // TitlePane icons
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

    /** Gets the icon icon.
	 *
	 * @return the icon icon
	 */
    public static Icon getIconIcon() {
        if (iconIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                iconIcon = new MacIconIcon();
            } else {
                Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
                Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
                iconIcon = new IconSymbol(iconColor, null, iconRolloverColor);
            }
        }
        return iconIcon;
    }

    /** Gets the max icon.
	 *
	 * @return the max icon
	 */
    public static Icon getMaxIcon() {
        if (maxIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                maxIcon = new MacMaxIcon();
            } else {
                Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
                Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
                maxIcon = new MaxSymbol(iconColor, null, iconRolloverColor);
            }
        }
        return maxIcon;
    }

    /** Gets the min icon.
	 *
	 * @return the min icon
	 */
    public static Icon getMinIcon() {
        if (minIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                minIcon = new MacMinIcon();
            } else {
                Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
                Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
                minIcon = new MinSymbol(iconColor, null, iconRolloverColor);
            }
        }
        return minIcon;
    }

    /** Gets the close icon.
	 *
	 * @return the close icon
	 */
    public static Icon getCloseIcon() {
        if (closeIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                closeIcon = new MacCloseIcon();
            } else {
                Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
                Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
                closeIcon = new CloseSymbol(iconColor, null, iconRolloverColor);
            }
        }
        return closeIcon;
    }

    /** Gets the palette close icon.
	 *
	 * @return the palette close icon
	 */
    public static Icon getPaletteCloseIcon() {
        if (paletteCloseIcon == null) {
            paletteCloseIcon = new CloseSymbol(Color.black, null, Color.red);
        }
        return paletteCloseIcon;
    }

    /** Gets the menu arrow icon.
	 *
	 * @return the menu arrow icon
	 */
    // MenuIcons
    public static Icon getMenuArrowIcon() {
        if (menuArrowIcon == null) {
            menuArrowIcon = new LazyMenuArrowImageIcon("icons/MenuRightArrow.gif", "icons/MenuLeftArrow.gif");
        }
        return menuArrowIcon;
    }

    /** Gets the menu check box icon.
	 *
	 * @return the menu check box icon
	 */
    public static Icon getMenuCheckBoxIcon() {
        if (menuCheckBoxIcon == null) {
            menuCheckBoxIcon = new CheckBoxIcon();
        }
        return menuCheckBoxIcon;
    }

    /** Gets the menu radio button icon.
	 *
	 * @return the menu radio button icon
	 */
    public static Icon getMenuRadioButtonIcon() {
        if (menuRadioButtonIcon == null) {
            menuRadioButtonIcon = new RadioButtonIcon();
        }
        return menuRadioButtonIcon;
    }

    /** Gets the up arrow icon.
	 *
	 * @return the up arrow icon
	 */
    // ArrowIcons
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

    /** Gets the up arrow inverse icon.
	 *
	 * @return the up arrow inverse icon
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
    
    /** Gets the down arrow icon.
	 *
	 * @return the down arrow icon
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

    /** Gets the down arrow inverse icon.
	 *
	 * @return the down arrow inverse icon
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
    
    /** Gets the left arrow icon.
	 *
	 * @return the left arrow icon
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

    /** Gets the left arrow inverse icon.
	 *
	 * @return the left arrow inverse icon
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
    
    /** Gets the right arrow icon.
	 *
	 * @return the right arrow icon
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

    /** Gets the right arrow inverse icon.
	 *
	 * @return the right arrow inverse icon
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
    
    /** Gets the splitter up arrow icon.
	 *
	 * @return the splitter up arrow icon
	 */
    // Splitter
    public static Icon getSplitterUpArrowIcon() {
        if (splitterUpArrowIcon == null) {
            splitterUpArrowIcon = new LazyImageIcon("icons/SplitterUpArrow.gif");
        }
        return splitterUpArrowIcon;
    }

    /** Gets the splitter down arrow icon.
	 *
	 * @return the splitter down arrow icon
	 */
    public static Icon getSplitterDownArrowIcon() {
        if (splitterDownArrowIcon == null) {
            splitterDownArrowIcon = new LazyImageIcon("icons/SplitterDownArrow.gif");
        }
        return splitterDownArrowIcon;
    }

    /** Gets the splitter left arrow icon.
	 *
	 * @return the splitter left arrow icon
	 */
    public static Icon getSplitterLeftArrowIcon() {
        if (splitterLeftArrowIcon == null) {
            splitterLeftArrowIcon = new LazyImageIcon("icons/SplitterLeftArrow.gif");
        }
        return splitterLeftArrowIcon;
    }

    /** Gets the splitter right arrow icon.
	 *
	 * @return the splitter right arrow icon
	 */
    public static Icon getSplitterRightArrowIcon() {
        if (splitterRightArrowIcon == null) {
            splitterRightArrowIcon = new LazyImageIcon("icons/SplitterRightArrow.gif");
        }
        return splitterRightArrowIcon;
    }

    /** Gets the splitter hor bump icon.
	 *
	 * @return the splitter hor bump icon
	 */
    public static Icon getSplitterHorBumpIcon() {
        if (splitterHorBumpIcon == null) {
            splitterHorBumpIcon = new LazyImageIcon("icons/SplitterHorBumps.gif");
        }
        return splitterHorBumpIcon;
    }

    /** Gets the splitter ver bump icon.
	 *
	 * @return the splitter ver bump icon
	 */
    public static Icon getSplitterVerBumpIcon() {
        if (splitterVerBumpIcon == null) {
            splitterVerBumpIcon = new LazyImageIcon("icons/SplitterVerBumps.gif");
        }
        return splitterVerBumpIcon;
    }

    /** Gets the thumb hor icon.
	 *
	 * @return the thumb hor icon
	 */
    // Slider
    public static Icon getThumbHorIcon() {
        if (thumbHorIcon == null) {
            thumbHorIcon = new LazyImageIcon("icons/thumb_hor.gif");
        }
        return thumbHorIcon;
    }

    /** Gets the thumb ver icon.
	 *
	 * @return the thumb ver icon
	 */
    public static Icon getThumbVerIcon() {
        if (thumbVerIcon == null) {
            thumbVerIcon = new LazyImageIcon("icons/thumb_ver.gif");
        }
        return thumbVerIcon;
    }

    /** Gets the thumb hor icon rollover.
	 *
	 * @return the thumb hor icon rollover
	 */
    public static Icon getThumbHorIconRollover() {
        if (thumbHorIconRollover == null) {
            thumbHorIconRollover = new LazyImageIcon("icons/thumb_hor_rollover.gif");
        }
        return thumbHorIconRollover;
    }

    /** Gets the thumb ver icon rollover.
	 *
	 * @return the thumb ver icon rollover
	 */
    public static Icon getThumbVerIconRollover() {
        if (thumbVerIconRollover == null) {
            thumbVerIconRollover = new LazyImageIcon("icons/thumb_ver_rollover.gif");
        }
        return thumbVerIconRollover;
    }

/**
 * The Class CheckBoxIcon.
 */
//-----------------------------------------------------------------------------------------------------------
    private static class CheckBoxIcon implements Icon {

        /** The gap. */
        private static int GAP = 2;
        
        /** The Constant SMALL_CHECK_ICON. */
        private static final Icon SMALL_CHECK_ICON = new LazyImageIcon("icons/small/check_symbol_10x10.png");
        
        /** The Constant SMALL_CHECK_DISABLED_ICON. */
        private static final Icon SMALL_CHECK_DISABLED_ICON = new LazyImageIcon("icons/small/check_symbol_disabled_10x10.png");
        
        /** The Constant MEDIUM_CHECK_ICON. */
        private static final Icon MEDIUM_CHECK_ICON = new LazyImageIcon("icons/medium/check_symbol_12x12.png");
        
        /** The Constant MEDIUM_CHECK_DISABLED_ICON. */
        private static final Icon MEDIUM_CHECK_DISABLED_ICON = new LazyImageIcon("icons/medium/check_symbol_disabled_12x12.png");
        
        /** The Constant LARGE_CHECK_ICON. */
        private static final Icon LARGE_CHECK_ICON = new LazyImageIcon("icons/large/check_symbol_14x14.png");
        
        /** The Constant LARGE_CHECK_DISABLED_ICON. */
        private static final Icon LARGE_CHECK_DISABLED_ICON = new LazyImageIcon("icons/large/check_symbol_disabled_14x14.png");

        public void paintIcon(Component c, Graphics g, int x, int y) {
            if (!JTattooUtilities.isLeftToRight(c)) {
                x += GAP;
            }
            int w = getIconWidth() - GAP;
            int h = getIconHeight();
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();
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
                    if ((button.isRolloverEnabled() && model.isRollover())) {
                        JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getRolloverColors(), x, y, w, h);
                    } else {
                        if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
                            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getFocusColors(), x, y, w, h);
                        } else {
                            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getCheckBoxColors(), x, y, w, h);
                        }
                        if (!model.isPressed()) {
                            g.setColor(Color.white);
                            g.drawLine(x + 1, y + 1, x + 1, y + h - 2);
                            g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 2);
                        }
                    }
                    if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
                        Color hiColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFocusFrameColor(), 30);
                        Color loColor = ColorHelper.darker(AbstractLookAndFeel.getTheme().getFocusFrameColor(), 20);
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
            
            Icon checkIcon;
            Icon checkDisabledIcon;
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
            int xi = x + ((w - checkIcon.getIconWidth()) / 2) + 1;
            int yi = y + ((h - checkIcon.getIconHeight()) / 2) + 1;
            if (model.isPressed() && model.isArmed()) {
                Color bc = AbstractLookAndFeel.getTheme().getSelectionBackgroundColor();
                Color fc = ColorHelper.darker(bc, 40);
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

        public int getIconWidth() {
            int w;
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                w = 15;
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                w = 17;
            } else {
                w = 19;
            }
            return w + GAP;
        }

        public int getIconHeight() {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                return 15;
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                return 17;
            } else {
                return 19;
            }
        }
    }

/**
 * The Class RadioButtonIcon.
 */
//-----------------------------------------------------------------------------------------------------------
    private static class RadioButtonIcon implements Icon {
        
        /** The gap. */
        private static int GAP = 2;

        public void paintIcon(Component c, Graphics g, int x, int y) {
            if (!JTattooUtilities.isLeftToRight(c)) {
                x += GAP;
            }
            int w = getIconWidth() - GAP;
            int h = getIconHeight();
            Graphics2D g2D = (Graphics2D) g;
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();
            Color cHi = Color.white;
            Color cLo = Color.white;
            if (!(c instanceof JRadioButtonMenuItem)) {
                Color colors[];
                if (button.isEnabled()) {
                    if ((button.isRolloverEnabled() && model.isRollover()) || (model.isPressed() && model.isArmed())) {
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
                cHi = colors[0];
                cLo = colors[colors.length - 1];
            }
            Paint savedPaint = g2D.getPaint();
            g2D.setPaint(new GradientPaint(0, 0, cHi, 0, h, cLo));
            g2D.fillOval(x, y, w, h);
            g2D.setPaint(savedPaint);
            
            Shape savedClip = g.getClip();
            //Area clipArea = new Area(new Ellipse2D.Double(x + 1, y + 1, w - 1, h - 1));
            Area clipArea = new Area(new Ellipse2D.Double(x, y, w + 1, h + 1));
            if (savedClip != null) {
                clipArea.intersect(new Area(savedClip));
            }
            g2D.setClip(clipArea);
            if (c instanceof JRadioButtonMenuItem) {
                g.setColor(Color.white);
                g.fillRect(x, y, w, h);
            } else {
                if (button.isEnabled()) {
                    if ((button.isRolloverEnabled() && model.isRollover()) || (model.isPressed() && model.isArmed())) {
                        JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getRolloverColors(), x, y, w, h);
                    } else {
                        if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
                            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getFocusColors(), x, y, w, h);
                        } else {
                            JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getCheckBoxColors(), x, y, w, h);
                        }
                    }
                } else {
                    JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getDisabledColors(), x, y, w, h);
                }
            }
            g2D.setClip(savedClip);
            
            Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (!model.isRollover()) {
                Composite savedComposite = g2D.getComposite();
                AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
                g2D.setComposite(alpha);
                g2D.setColor(Color.white);
                g2D.drawOval(x + 1, y + 1, w - 2, h - 2);
                g2D.setComposite(savedComposite);
            }
            if (button.isEnabled()) {
                if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
                    Color hiColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFocusFrameColor(), 30);
                    Color loColor = ColorHelper.darker(AbstractLookAndFeel.getTheme().getFocusFrameColor(), 20);
                    g.setColor(hiColor);
                    g.drawOval(x - 1, y - 1, w + 2, h + 2);
                    g.setColor(loColor);
                    g2D.drawOval(x, y, w, h);
                } else {
                    g.setColor(AbstractLookAndFeel.getFrameColor());
                    g2D.drawOval(x, y, w, h);
                }
            } else {
                g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 40));
                g2D.drawOval(x, y, w, h);
            }

            if (model.isSelected()) {
                if (button.isEnabled()) {
                    Color fc = AbstractLookAndFeel.getForegroundColor();
                    if (ColorHelper.getGrayValue(cLo) < 128) {
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

        public int getIconWidth() {
            int w;
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                w = 14;
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                w = 16;
            } else {
                w = 18;
            }
            return w + GAP;
        }

        public int getIconHeight() {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                return 14;
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                return 16;
            } else {
                return 18;
            }
        }
    }

/**
 * The Class MacCloseIcon.
 */
//-----------------------------------------------------------------------------------------------------------
    public static class MacCloseIcon implements Icon, UIResource {

        public void paintIcon(Component c, Graphics g, int x, int y) {
            AbstractButton btn = (AbstractButton) c;
            ButtonModel model = btn.getModel();
            int w = c.getWidth();
            int h = c.getHeight();
            Icon closerIcon;
            Icon pearlIcon;
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                closerIcon = CLOSER_SMALL;
                pearlIcon = PEARL_RED_SMALL;
                if (!JTattooUtilities.isActive(btn)) {
                    pearlIcon = PEARL_GREY_SMALL;
                }
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                closerIcon = CLOSER_MEDIUM;
                pearlIcon = PEARL_RED_MEDIUM;
                if (!JTattooUtilities.isActive(btn)) {
                    pearlIcon = PEARL_GREY_MEDIUM;
                }
            } else {
                closerIcon = CLOSER_LARGE;
                pearlIcon = PEARL_RED_LARGE;
                if (!JTattooUtilities.isActive(btn)) {
                    pearlIcon = PEARL_GREY_LARGE;
                }
            }
            x = (w - pearlIcon.getIconWidth()) / 2;
            y = (h - pearlIcon.getIconHeight()) / 2;
            pearlIcon.paintIcon(c, g, x, y);
            if (model.isRollover()) {
                x += (pearlIcon.getIconWidth() - closerIcon.getIconWidth()) / 2;
                y += (pearlIcon.getIconHeight() - closerIcon.getIconHeight()) / 2;
                closerIcon.paintIcon(c, g, x, y);
            }
        }

        public int getIconHeight() {
            return 24;
        }

        public int getIconWidth() {
            return 24;
        }
    }
    
    /** The Class MacIconIcon.
	 */
    public static class MacIconIcon implements Icon, UIResource {

        public void paintIcon(Component c, Graphics g, int x, int y) {
            AbstractButton btn = (AbstractButton) c;
            ButtonModel model = btn.getModel();
            int w = c.getWidth();
            int h = c.getHeight();
            Icon iconizerIcon;
            Icon pearlIcon;
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                    iconizerIcon = ICONIZER_SMALL;
                    pearlIcon = PEARL_YELLOW_SMALL;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_SMALL;
                    }
                } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                    iconizerIcon = ICONIZER_MEDIUM;
                    pearlIcon = PEARL_YELLOW_MEDIUM;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_MEDIUM;
                    }
                } else {
                    iconizerIcon = ICONIZER_LARGE;
                    pearlIcon = PEARL_YELLOW_LARGE;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_LARGE;
                    }
                }
                
            } else {
                if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                    iconizerIcon = ICONIZER_SMALL;
                    pearlIcon = PEARL_GREEN_SMALL;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_SMALL;
                    }
                } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                    iconizerIcon = ICONIZER_MEDIUM;
                    pearlIcon = PEARL_GREEN_MEDIUM;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_MEDIUM;
                    }
                } else {
                    iconizerIcon = ICONIZER_LARGE;
                    pearlIcon = PEARL_GREEN_LARGE;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_LARGE;
                    }
                }
            }
            x = (w - pearlIcon.getIconWidth()) / 2;
            y = (h - pearlIcon.getIconHeight()) / 2;
            pearlIcon.paintIcon(c, g, x, y);
            if (model.isRollover()) {
                x += (pearlIcon.getIconWidth() - iconizerIcon.getIconWidth()) / 2;
                y += (pearlIcon.getIconHeight() - iconizerIcon.getIconHeight()) / 2;
                iconizerIcon.paintIcon(c, g, x, y);
            }
        }

        public int getIconHeight() {
            return 24;
        }

        public int getIconWidth() {
            return 24;
        }
    }

    /** The Class MacMaxIcon.
	 */
    public static class MacMaxIcon implements Icon, UIResource {

        public void paintIcon(Component c, Graphics g, int x, int y) {
            AbstractButton btn = (AbstractButton) c;
            ButtonModel model = btn.getModel();
            int w = c.getWidth();
            int h = c.getHeight();
            Icon maximizerIcon;
            Icon pearlIcon;
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                    maximizerIcon = MAXIMIZER_SMALL;
                    pearlIcon = PEARL_GREEN_SMALL;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_SMALL;
                    }
                } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                    maximizerIcon = MAXIMIZER_MEDIUM;
                    pearlIcon = PEARL_GREEN_MEDIUM;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_MEDIUM;
                    }
                } else {
                    maximizerIcon = MAXIMIZER_LARGE;
                    pearlIcon = PEARL_GREEN_LARGE;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_LARGE;
                    }
                }
            } else {
                if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                    maximizerIcon = MAXIMIZER_SMALL;
                    pearlIcon = PEARL_YELLOW_SMALL;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_SMALL;
                    }
                } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                    maximizerIcon = MAXIMIZER_MEDIUM;
                    pearlIcon = PEARL_YELLOW_MEDIUM;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_MEDIUM;
                    }
                } else {
                    maximizerIcon = MAXIMIZER_LARGE;
                    pearlIcon = PEARL_YELLOW_LARGE;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_LARGE;
                    }
                }
            }
            x = (w - pearlIcon.getIconWidth()) / 2;
            y = (h - pearlIcon.getIconHeight()) / 2;
            pearlIcon.paintIcon(c, g, x, y);
            if (model.isRollover()) {
                x += (pearlIcon.getIconWidth() - maximizerIcon.getIconWidth()) / 2;
                y += (pearlIcon.getIconHeight() - maximizerIcon.getIconHeight()) / 2;
                maximizerIcon.paintIcon(c, g, x, y);
            }
        }

        public int getIconHeight() {
            return 24;
        }

        public int getIconWidth() {
            return 24;
        }
    }
    
    /** The Class MacMinIcon.
	 */
    public static class MacMinIcon implements Icon, UIResource {

        public void paintIcon(Component c, Graphics g, int x, int y) {
            AbstractButton btn = (AbstractButton) c;
            ButtonModel model = btn.getModel();
            int w = c.getWidth();
            int h = c.getHeight();
            Icon minimizerIcon;
            Icon pearlIcon;
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                    minimizerIcon = MINIMIZER_SMALL;
                    pearlIcon = PEARL_GREEN_SMALL;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_SMALL;
                    }
                } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                    minimizerIcon = MINIMIZER_MEDIUM;
                    pearlIcon = PEARL_GREEN_MEDIUM;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_MEDIUM;
                    }
                } else {
                    minimizerIcon = MINIMIZER_LARGE;
                    pearlIcon = PEARL_GREEN_LARGE;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_LARGE;
                    }
                }
            } else {
                if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                    minimizerIcon = MINIMIZER_SMALL;
                    pearlIcon = PEARL_YELLOW_SMALL;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_SMALL;
                    }
                } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                    minimizerIcon = MINIMIZER_MEDIUM;
                    pearlIcon = PEARL_YELLOW_MEDIUM;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_MEDIUM;
                    }
                } else {
                    minimizerIcon = MINIMIZER_LARGE;
                    pearlIcon = PEARL_YELLOW_LARGE;
                    if (!JTattooUtilities.isActive(btn)) {
                        pearlIcon = PEARL_GREY_LARGE;
                    }
                }
            }
            x = (w - pearlIcon.getIconWidth()) / 2;
            y = (h - pearlIcon.getIconHeight()) / 2;
            pearlIcon.paintIcon(c, g, x, y);
            if (model.isRollover()) {
                x += (pearlIcon.getIconWidth() - minimizerIcon.getIconWidth()) / 2;
                y += (pearlIcon.getIconHeight() - minimizerIcon.getIconHeight()) / 2;
                minimizerIcon.paintIcon(c, g, x, y);
            }
        }

        public int getIconHeight() {
            return 24;
        }

        public int getIconWidth() {
            return 24;
        }
    }
    
/**
 * The Class IconSymbol.
 */
//-----------------------------------------------------------------------------------------------------------
    public static class IconSymbol implements Icon {

        /** The foreground color. */
        private Color foregroundColor = null;
        
        /** The shadow color. */
        private Color shadowColor = null;
        
        /** The inactive foreground color. */
        private Color inactiveForegroundColor = null;
        
        /** The inactive shadow color. */
        private Color inactiveShadowColor = null;
        
        /** The rollover color. */
        private Color rolloverColor = null;
        
        /** The insets. */
        private Insets insets = new Insets(0, 0, 0, 0);

        public IconSymbol(Color foregroundColor, Color shadowColor, Color rolloverColor) {
            this.foregroundColor = foregroundColor;
            this.shadowColor = shadowColor;
            this.rolloverColor = rolloverColor;
            this.inactiveForegroundColor = foregroundColor;
            this.inactiveShadowColor = shadowColor;
        }

        public IconSymbol(Color foregroundColor, Color shadowColor, Color rolloverColor, Insets insets) {
            this.foregroundColor = foregroundColor;
            this.shadowColor = shadowColor;
            this.rolloverColor = rolloverColor;
            this.inactiveForegroundColor = foregroundColor;
            this.inactiveShadowColor = shadowColor;
            this.insets = insets;
        }

        public IconSymbol(Color foregroundColor, Color shadowColor, Color rolloverColor, Color inactiveForegroundColor, Color inactiveShadowColor, Insets insets) {
            this.foregroundColor = foregroundColor;
            this.shadowColor = shadowColor;
            this.rolloverColor = rolloverColor;
            this.inactiveForegroundColor = inactiveForegroundColor;
            this.inactiveShadowColor = inactiveShadowColor;
            this.insets = insets;
        }

        public int getIconHeight() {
            return 16;
        }

        public int getIconWidth() {
            return 16;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.translate(insets.left, insets.top);
            int w = c.getWidth() - insets.left - insets.right;
            int h = c.getHeight() - insets.top - insets.bottom;
            boolean active = JTattooUtilities.isActive((JComponent) c);
            Color color = foregroundColor;
            if (!active) {
                color = inactiveForegroundColor;
            }
            if (c instanceof AbstractButton) {
                if (((AbstractButton) c).getModel().isRollover() && (rolloverColor != null)) {
                    color = rolloverColor;
                }
            }
            //int lw = (w / 12) + 1;
            int lw = (h > 22) ? 3 : 2;
            
            int dx = (w / 5) + 2;
            int dy = dx;

            Stroke savedStroke = g2D.getStroke();
            g2D.setStroke(new BasicStroke(lw, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
            if (shadowColor != null) {
                if (!active) {
                    g2D.setColor(inactiveShadowColor);
                } else {
                    g2D.setColor(shadowColor);
                }
                g2D.drawLine(dx + 1, h - dy, w - dx + 1, h - dy);
            }
            g2D.setColor(color);
            g2D.drawLine(dx, h - dy - 1, w - dx, h - dy - 1);
            g2D.setStroke(savedStroke);
            g2D.translate(-insets.left, -insets.top);
        }
    }

/**
 * The Class MaxSymbol.
 */
//-----------------------------------------------------------------------------------------------------------
    public static class MaxSymbol implements Icon {

        /** The foreground color. */
        private Color foregroundColor = null;
        
        /** The shadow color. */
        private Color shadowColor = null;
        
        /** The rollover color. */
        private Color rolloverColor = null;
        
        /** The inactive foreground color. */
        private Color inactiveForegroundColor = null;
        
        /** The inactive shadow color. */
        private Color inactiveShadowColor = null;
        
        /** The insets. */
        private Insets insets = new Insets(0, 0, 0, 0);

        public MaxSymbol(Color foregroundColor, Color shadowColor, Color rolloverColor) {
            this.foregroundColor = foregroundColor;
            this.shadowColor = shadowColor;
            this.rolloverColor = rolloverColor;
            this.inactiveForegroundColor = foregroundColor;
            this.inactiveShadowColor = shadowColor;
        }

        public MaxSymbol(Color foregroundColor, Color shadowColor, Color rolloverColor, Insets insets) {
            this.foregroundColor = foregroundColor;
            this.shadowColor = shadowColor;
            this.rolloverColor = rolloverColor;
            this.inactiveForegroundColor = foregroundColor;
            this.inactiveShadowColor = shadowColor;
            this.insets = insets;
        }

        public MaxSymbol(Color foregroundColor, Color shadowColor, Color rolloverColor, Color inactiveForegroundColor, Color inactiveShadowColor, Insets insets) {
            this.foregroundColor = foregroundColor;
            this.shadowColor = shadowColor;
            this.rolloverColor = rolloverColor;
            this.inactiveForegroundColor = inactiveForegroundColor;
            this.inactiveShadowColor = inactiveShadowColor;
            this.insets = insets;
        }

        public int getIconHeight() {
            return 16;
        }

        public int getIconWidth() {
            return 16;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.translate(insets.left, insets.top);
            int w = c.getWidth() - insets.left - insets.right;
            int h = c.getHeight() - insets.top - insets.bottom;
            boolean active = JTattooUtilities.isActive((JComponent) c);
            Color color = foregroundColor;
            if (!active) {
                color = inactiveForegroundColor;
            }
            if (c instanceof AbstractButton) {
                if (((AbstractButton) c).getModel().isRollover() && (rolloverColor != null)) {
                    color = rolloverColor;
                }
            }
            int lw = (h > 22) ? 2 : 1;
            
            int dx = (w / 5) + 1;
            int dy = (h / 5) + 2;

            Stroke savedStroke = g2D.getStroke();
            g2D.setStroke(new BasicStroke(lw, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
            if (shadowColor != null) {
                if (!active) {
                    g2D.setColor(inactiveShadowColor);
                } else {
                    g2D.setColor(shadowColor);
                }
                g2D.drawRect(dx + 1, dy + 1, w - (2 * dx), h - (2 * dy));
                g2D.drawLine(dx + 1, dy + lw + 1, w - dx, dy + lw + 1);
            }
            g2D.setColor(color);
            g2D.drawRect(dx, dy, w - (2 * dx), h - (2 * dy));
            g2D.drawLine(dx + 1, dy + lw, w - dx, dy + lw);

            g2D.setStroke(savedStroke);
            g2D.translate(-insets.left, -insets.top);
        }
    }

/**
 * The Class MinSymbol.
 */
//-----------------------------------------------------------------------------------------------------------
    public static class MinSymbol implements Icon {

        /** The foreground color. */
        private Color foregroundColor = null;
        
        /** The shadow color. */
        private Color shadowColor = null;
        
        /** The rollover color. */
        private Color rolloverColor = null;
        
        /** The inactive foreground color. */
        private Color inactiveForegroundColor = null;
        
        /** The inactive shadow color. */
        private Color inactiveShadowColor = null;
        
        /** The insets. */
        private Insets insets = new Insets(0, 0, 0, 0);

        public MinSymbol(Color foregroundColor, Color shadowColor, Color rolloverColor) {
            this.foregroundColor = foregroundColor;
            this.shadowColor = shadowColor;
            this.rolloverColor = rolloverColor;
            this.inactiveForegroundColor = foregroundColor;
            this.inactiveShadowColor = shadowColor;
        }

        public MinSymbol(Color foregroundColor, Color shadowColor, Color rolloverColor, Insets insets) {
            this.foregroundColor = foregroundColor;
            this.shadowColor = shadowColor;
            this.rolloverColor = rolloverColor;
            this.inactiveForegroundColor = foregroundColor;
            this.inactiveShadowColor = shadowColor;
            this.insets = insets;
        }

        public MinSymbol(Color foregroundColor, Color shadowColor, Color rolloverColor, Color inactiveForegroundColor, Color inactiveShadowColor, Insets insets) {
            this.foregroundColor = foregroundColor;
            this.shadowColor = shadowColor;
            this.rolloverColor = rolloverColor;
            this.inactiveForegroundColor = inactiveForegroundColor;
            this.inactiveShadowColor = inactiveShadowColor;
            this.insets = insets;
        }

        public int getIconHeight() {
            return 16;
        }

        public int getIconWidth() {
            return 16;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.translate(insets.left, insets.top);
            int w = c.getWidth() - insets.left - insets.right;
            int h = c.getHeight() - insets.top - insets.bottom;

            int lw = (h > 22) ? 2 : 1;
            int delta = w / 4;

            w = Math.min(w, h) - 6;
            h = w;

            int x1 = 3;
            int y1 = 3;
            int w1 = w - delta;
            int h1 = h - delta;

            int x2 = delta + 2;
            int y2 = Math.max(delta + 2, y1 + (2 * lw) + 1);
            int w2 = w - delta;
            int h2 = h - delta;

            boolean active = JTattooUtilities.isActive((JComponent) c);
            Color ic = foregroundColor;
            Color sc = shadowColor;
            if (!active) {
                ic = inactiveForegroundColor;
                if (sc != null) {
                    sc = inactiveShadowColor;
                }
            }
            if (c instanceof AbstractButton) {
                if (((AbstractButton) c).getModel().isRollover() && (rolloverColor != null)) {
                    ic = rolloverColor;
                }
            }

            Shape savedClip = g2D.getClip();
            Stroke savedStroke = g2D.getStroke();
            g2D.setStroke(new BasicStroke(lw, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
            Area clipArea = new Area(savedClip);
            clipArea.subtract(new Area(new Rectangle2D.Double(x2, y2, w2, h2)));
            g2D.setClip(clipArea);
            paintRect(g2D, x1, y1, w1, h1, lw, ic, sc);
            g2D.setClip(savedClip);
            paintRect(g2D, x2, y2, w2, h2, lw, ic, sc);

            g2D.setStroke(savedStroke);
            g2D.translate(-insets.left, -insets.top);
        }

        private void paintRect(Graphics2D g2D, int x, int y, int w, int h, int lw, Color iconColor, Color shadowColor) {
            if (shadowColor != null) {
                g2D.setColor(shadowColor);
                g2D.drawRect(x + 1, y + 1, w, h);
                g2D.drawLine(x + 1, y + lw + 1, x + w + 1, y + lw + 1);
            }
            g2D.setColor(iconColor);
            g2D.drawRect(x, y, w, h);
            g2D.drawLine(x, y + lw, x + w, y + lw);

        }
    }

/**
 * The Class CloseSymbol.
 */
//-----------------------------------------------------------------------------------------------------------
    public static class CloseSymbol implements Icon {

        /** The foreground color. */
        private Color foregroundColor = null;
        
        /** The shadow color. */
        private Color shadowColor = null;
        
        /** The rollover color. */
        private Color rolloverColor = null;
        
        /** The inactive foreground color. */
        private Color inactiveForegroundColor = null;
        
        /** The inactive shadow color. */
        private Color inactiveShadowColor = null;
        
        /** The insets. */
        private Insets insets = new Insets(0, 0, 0, 0);

        public CloseSymbol(Color foregroundColor, Color shadowColor, Color rolloverColor) {
            this.foregroundColor = foregroundColor;
            this.shadowColor = shadowColor;
            this.rolloverColor = rolloverColor;
            this.inactiveForegroundColor = foregroundColor;
            this.inactiveShadowColor = shadowColor;
        }

        public CloseSymbol(Color foregroundColor, Color shadowColor, Color rolloverColor, Insets insets) {
            this.foregroundColor = foregroundColor;
            this.shadowColor = shadowColor;
            this.rolloverColor = rolloverColor;
            this.insets = insets;
            this.inactiveForegroundColor = foregroundColor;
            this.inactiveShadowColor = shadowColor;
        }

        public CloseSymbol(Color foregroundColor, Color shadowColor, Color rolloverColor, Color inactiveForegroundColor, Color inactiveShadowColor, Insets insets) {
            this.foregroundColor = foregroundColor;
            this.shadowColor = shadowColor;
            this.rolloverColor = rolloverColor;
            this.inactiveForegroundColor = inactiveForegroundColor;
            this.inactiveShadowColor = inactiveShadowColor;
            this.insets = insets;
        }

        public int getIconHeight() {
            return 16;
        }

        public int getIconWidth() {
            return 16;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.translate(insets.left, insets.top);
            int w = c.getWidth() - insets.left - insets.right;
            int h = c.getHeight() - insets.top - insets.bottom;
            boolean active = JTattooUtilities.isActive((JComponent) c);
            Color color = foregroundColor;
            if (!active) {
                color = inactiveForegroundColor;
            }
            if (c instanceof AbstractButton) {
                if (((AbstractButton) c).getModel().isRollover() && (rolloverColor != null)) {
                    color = rolloverColor;
                }
            }
            //int lw = (w / 12) + 1;
            int lw = (h > 22) ? 3 : 2;
            int dx = (w / 5) + 2;
            int dy = dx;

            Stroke savedStroke = g2D.getStroke();
            Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2D.setStroke(new BasicStroke(lw, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
            if (shadowColor != null) {
                if (!active) {
                    g2D.setColor(inactiveShadowColor);
                } else {
                    g2D.setColor(shadowColor);
                }
                g2D.drawLine(dx + 1, dy + 1, w - dx + 1, h - dy + 1);
                g2D.drawLine(w - dx + 1, dy + 1, dx + 1, h - dy + 1);
            }
            g2D.setColor(color);
            g2D.drawLine(dx, dy, w - dx, h - dy);
            g2D.drawLine(w - dx, dy, dx, h - dy);

            g2D.setStroke(savedStroke);
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
            g2D.translate(-insets.left, -insets.top);
        }
    }
}
