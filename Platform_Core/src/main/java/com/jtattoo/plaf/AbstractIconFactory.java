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

import javax.swing.Icon;

/**
 * A factory for creating AbstractIcon objects.
 *
 * @author Michael Hagen
 */
public interface AbstractIconFactory {

    /** Gets the option pane error icon.
	 *
	 * @return the option pane error icon
	 */
    public Icon getOptionPaneErrorIcon();

    /** Gets the option pane warning icon.
	 *
	 * @return the option pane warning icon
	 */
    public Icon getOptionPaneWarningIcon();

    /** Gets the option pane information icon.
	 *
	 * @return the option pane information icon
	 */
    public Icon getOptionPaneInformationIcon();

    /** Gets the option pane question icon.
	 *
	 * @return the option pane question icon
	 */
    public Icon getOptionPaneQuestionIcon();

    /** Gets the file chooser up folder icon.
	 *
	 * @return the file chooser up folder icon
	 */
    public Icon getFileChooserUpFolderIcon();

    /** Gets the file chooser home folder icon.
	 *
	 * @return the file chooser home folder icon
	 */
    public Icon getFileChooserHomeFolderIcon();

    /** Gets the file chooser new folder icon.
	 *
	 * @return the file chooser new folder icon
	 */
    public Icon getFileChooserNewFolderIcon();

    /** Gets the file chooser detail view icon.
	 *
	 * @return the file chooser detail view icon
	 */
    public Icon getFileChooserDetailViewIcon();

    /** Gets the file chooser list view icon.
	 *
	 * @return the file chooser list view icon
	 */
    public Icon getFileChooserListViewIcon();
    
    /** Gets the file view computer icon.
	 *
	 * @return the file view computer icon
	 */
    public Icon getFileViewComputerIcon();

    /** Gets the file view floppy drive icon.
	 *
	 * @return the file view floppy drive icon
	 */
    public Icon getFileViewFloppyDriveIcon();

    /** Gets the file view hard drive icon.
	 *
	 * @return the file view hard drive icon
	 */
    public Icon getFileViewHardDriveIcon();

    /** Gets the menu icon.
	 *
	 * @return the menu icon
	 */
    public Icon getMenuIcon();

    /** Gets the icon icon.
	 *
	 * @return the icon icon
	 */
    public Icon getIconIcon();

    /** Gets the max icon.
	 *
	 * @return the max icon
	 */
    public Icon getMaxIcon();

    /** Gets the min icon.
	 *
	 * @return the min icon
	 */
    public Icon getMinIcon();

    /** Gets the close icon.
	 *
	 * @return the close icon
	 */
    public Icon getCloseIcon();

    /** Gets the palette close icon.
	 *
	 * @return the palette close icon
	 */
    public Icon getPaletteCloseIcon();

    /** Gets the radio button icon.
	 *
	 * @return the radio button icon
	 */
    public Icon getRadioButtonIcon();

    /** Gets the check box icon.
	 *
	 * @return the check box icon
	 */
    public Icon getCheckBoxIcon();

    /** Gets the combo box icon.
	 *
	 * @return the combo box icon
	 */
    public Icon getComboBoxIcon();

    /** Gets the tree open icon.
	 *
	 * @return the tree open icon
	 */
    public Icon getTreeOpenIcon();
    
    /** Gets the tree close icon.
	 *
	 * @return the tree close icon
	 */
    public Icon getTreeCloseIcon();

    /** Gets the tree leaf icon.
	 *
	 * @return the tree leaf icon
	 */
    public Icon getTreeLeafIcon();

    /** Gets the tree collapsed icon.
	 *
	 * @return the tree collapsed icon
	 */
    public Icon getTreeCollapsedIcon();

    /** Gets the tree expanded icon.
	 *
	 * @return the tree expanded icon
	 */
    public Icon getTreeExpandedIcon();

    /** Gets the menu arrow icon.
	 *
	 * @return the menu arrow icon
	 */
    public Icon getMenuArrowIcon();

    /** Gets the menu check box icon.
	 *
	 * @return the menu check box icon
	 */
    public Icon getMenuCheckBoxIcon();

    /** Gets the menu radio button icon.
	 *
	 * @return the menu radio button icon
	 */
    public Icon getMenuRadioButtonIcon();

    /** Gets the up arrow icon.
	 *
	 * @return the up arrow icon
	 */
    public Icon getUpArrowIcon();

    /** Gets the down arrow icon.
	 *
	 * @return the down arrow icon
	 */
    public Icon getDownArrowIcon();

    /** Gets the left arrow icon.
	 *
	 * @return the left arrow icon
	 */
    public Icon getLeftArrowIcon();

    /** Gets the right arrow icon.
	 *
	 * @return the right arrow icon
	 */
    public Icon getRightArrowIcon();

    /** Gets the splitter up arrow icon.
	 *
	 * @return the splitter up arrow icon
	 */
    public Icon getSplitterUpArrowIcon();

    /** Gets the splitter down arrow icon.
	 *
	 * @return the splitter down arrow icon
	 */
    public Icon getSplitterDownArrowIcon();

    /** Gets the splitter left arrow icon.
	 *
	 * @return the splitter left arrow icon
	 */
    public Icon getSplitterLeftArrowIcon();

    /** Gets the splitter right arrow icon.
	 *
	 * @return the splitter right arrow icon
	 */
    public Icon getSplitterRightArrowIcon();

    /** Gets the splitter hor bump icon.
	 *
	 * @return the splitter hor bump icon
	 */
    public Icon getSplitterHorBumpIcon();

    /** Gets the splitter ver bump icon.
	 *
	 * @return the splitter ver bump icon
	 */
    public Icon getSplitterVerBumpIcon();

    /** Gets the thumb hor icon.
	 *
	 * @return the thumb hor icon
	 */
    public Icon getThumbHorIcon();

    /** Gets the thumb ver icon.
	 *
	 * @return the thumb ver icon
	 */
    public Icon getThumbVerIcon();

    /** Gets the thumb hor icon rollover.
	 *
	 * @return the thumb hor icon rollover
	 */
    public Icon getThumbHorIconRollover();

    /** Gets the thumb ver icon rollover.
	 *
	 * @return the thumb ver icon rollover
	 */
    public Icon getThumbVerIconRollover();
}
