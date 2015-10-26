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
 
package com.jtattoo.plaf.noire;

import com.jtattoo.plaf.AbstractIconFactory;
import javax.swing.Icon;

/**
 * @author Michael Hagen
 */
public class NoireIconFactory implements AbstractIconFactory {

    private static NoireIconFactory instance = null;

    private NoireIconFactory() {
    }

    public static synchronized NoireIconFactory getInstance() {
        if (instance == null) {
            instance = new NoireIconFactory();
        }
        return instance;
    }

    public Icon getOptionPaneErrorIcon() {
        return NoireIcons.getOptionPaneErrorIcon();
    }

    public Icon getOptionPaneWarningIcon() {
        return NoireIcons.getOptionPaneWarningIcon();
    }

    public Icon getOptionPaneInformationIcon() {
        return NoireIcons.getOptionPaneInformationIcon();
    }

    public Icon getOptionPaneQuestionIcon() {
        return NoireIcons.getOptionPaneQuestionIcon();
    }

    public Icon getFileChooserUpFolderIcon() {
        return NoireIcons.getFileChooserUpFolderIcon();
    }

    public Icon getFileChooserHomeFolderIcon() {
        return NoireIcons.getFileChooserHomeFolderIcon();
    }

    public Icon getFileChooserNewFolderIcon() {
        return NoireIcons.getFileChooserNewFolderIcon();
    }

    public Icon getFileChooserListViewIcon() {
        return NoireIcons.getFileChooserListViewIcon();
    }

    public Icon getFileChooserDetailViewIcon() {
        return NoireIcons.getFileChooserDetailViewIcon();
    }

    public Icon getFileViewComputerIcon() {
        return NoireIcons.getFileViewComputerIcon();
    }

    public Icon getFileViewFloppyDriveIcon() {
        return NoireIcons.getFileViewFloppyDriveIcon();
    }

    public Icon getFileViewHardDriveIcon() {
        return NoireIcons.getFileViewHardDriveIcon();
    }

    public Icon getMenuIcon() {
        return NoireIcons.getMenuIcon();
    }

    public Icon getIconIcon() {
        return NoireIcons.getIconIcon();
    }

    public Icon getMaxIcon() {
        return NoireIcons.getMaxIcon();
    }

    public Icon getMinIcon() {
        return NoireIcons.getMinIcon();
    }

    public Icon getCloseIcon() {
        return NoireIcons.getCloseIcon();
    }

    public Icon getPaletteCloseIcon() {
        return NoireIcons.getPaletteCloseIcon();
    }

    public Icon getRadioButtonIcon() {
        return NoireIcons.getRadioButtonIcon();
    }

    public Icon getCheckBoxIcon() {
        return NoireIcons.getCheckBoxIcon();
    }

    public Icon getComboBoxIcon() {
        return NoireIcons.getComboBoxIcon();
    }

    public Icon getTreeOpenIcon() {
        return NoireIcons.getTreeOpenedIcon();
    }

    public Icon getTreeCloseIcon() {
        return NoireIcons.getTreeClosedIcon();
    }

    public Icon getTreeLeafIcon() {
        return NoireIcons.getTreeLeafIcon();
    }

    public Icon getTreeCollapsedIcon() {
        return NoireIcons.getTreeCollapsedIcon();
    }

    public Icon getTreeExpandedIcon() {
        return NoireIcons.getTreeExpandedIcon();
    }

    public Icon getMenuArrowIcon() {
        return NoireIcons.getMenuArrowIcon();
    }

    public Icon getMenuCheckBoxIcon() {
        return NoireIcons.getMenuCheckBoxIcon();
    }

    public Icon getMenuRadioButtonIcon() {
        return NoireIcons.getMenuRadioButtonIcon();
    }

    public Icon getUpArrowIcon() {
        return NoireIcons.getUpArrowIcon();
    }

    public Icon getDownArrowIcon() {
        return NoireIcons.getDownArrowIcon();
    }

    public Icon getLeftArrowIcon() {
        return NoireIcons.getLeftArrowIcon();
    }

    public Icon getRightArrowIcon() {
        return NoireIcons.getRightArrowIcon();
    }

    public Icon getSplitterDownArrowIcon() {
        return NoireIcons.getSplitterDownArrowIcon();
    }

    public Icon getSplitterHorBumpIcon() {
        return NoireIcons.getSplitterHorBumpIcon();
    }

    public Icon getSplitterLeftArrowIcon() {
        return NoireIcons.getSplitterLeftArrowIcon();
    }

    public Icon getSplitterRightArrowIcon() {
        return NoireIcons.getSplitterRightArrowIcon();
    }

    public Icon getSplitterUpArrowIcon() {
        return NoireIcons.getSplitterUpArrowIcon();
    }

    public Icon getSplitterVerBumpIcon() {
        return NoireIcons.getSplitterVerBumpIcon();
    }

    public Icon getThumbHorIcon() {
        return NoireIcons.getThumbHorIcon();
    }

    public Icon getThumbVerIcon() {
        return NoireIcons.getThumbVerIcon();
    }

    public Icon getThumbHorIconRollover() {
        return NoireIcons.getThumbHorIconRollover();
    }

    public Icon getThumbVerIconRollover() {
        return NoireIcons.getThumbVerIconRollover();
    }
}
