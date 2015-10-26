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
 
package com.jtattoo.plaf.hifi;

import com.jtattoo.plaf.AbstractIconFactory;
import javax.swing.Icon;

/**
 * @author Michael Hagen
 */
public class HiFiIconFactory implements AbstractIconFactory {

    private static HiFiIconFactory instance = null;

    private HiFiIconFactory() {
    }

    public static synchronized HiFiIconFactory getInstance() {
        if (instance == null) {
            instance = new HiFiIconFactory();
        }
        return instance;
    }

    public Icon getOptionPaneErrorIcon() {
        return HiFiIcons.getOptionPaneErrorIcon();
    }

    public Icon getOptionPaneWarningIcon() {
        return HiFiIcons.getOptionPaneWarningIcon();
    }

    public Icon getOptionPaneInformationIcon() {
        return HiFiIcons.getOptionPaneInformationIcon();
    }

    public Icon getOptionPaneQuestionIcon() {
        return HiFiIcons.getOptionPaneQuestionIcon();
    }

    public Icon getFileChooserUpFolderIcon() {
        return HiFiIcons.getFileChooserUpFolderIcon();
    }

    public Icon getFileChooserHomeFolderIcon() {
        return HiFiIcons.getFileChooserHomeFolderIcon();
    }

    public Icon getFileChooserNewFolderIcon() {
        return HiFiIcons.getFileChooserNewFolderIcon();
    }

    public Icon getFileChooserListViewIcon() {
        return HiFiIcons.getFileChooserListViewIcon();
    }

    public Icon getFileChooserDetailViewIcon() {
        return HiFiIcons.getFileChooserDetailViewIcon();
    }

    public Icon getFileViewComputerIcon() {
        return HiFiIcons.getFileViewComputerIcon();
    }

    public Icon getFileViewFloppyDriveIcon() {
        return HiFiIcons.getFileViewFloppyDriveIcon();
    }

    public Icon getFileViewHardDriveIcon() {
        return HiFiIcons.getFileViewHardDriveIcon();
    }

    public Icon getMenuIcon() {
        return HiFiIcons.getMenuIcon();
    }

    public Icon getIconIcon() {
        return HiFiIcons.getIconIcon();
    }

    public Icon getMaxIcon() {
        return HiFiIcons.getMaxIcon();
    }

    public Icon getMinIcon() {
        return HiFiIcons.getMinIcon();
    }

    public Icon getCloseIcon() {
        return HiFiIcons.getCloseIcon();
    }

    public Icon getPaletteCloseIcon() {
        return HiFiIcons.getPaletteCloseIcon();
    }

    public Icon getRadioButtonIcon() {
        return HiFiIcons.getRadioButtonIcon();
    }

    public Icon getCheckBoxIcon() {
        return HiFiIcons.getCheckBoxIcon();
    }

    public Icon getComboBoxIcon() {
        return HiFiIcons.getComboBoxIcon();
    }

    public Icon getTreeOpenIcon() {
        return HiFiIcons.getTreeOpenedIcon();
    }

    public Icon getTreeCloseIcon() {
        return HiFiIcons.getTreeClosedIcon();
    }

    public Icon getTreeLeafIcon() {
        return HiFiIcons.getTreeLeafIcon();
    }

    public Icon getTreeCollapsedIcon() {
        return HiFiIcons.getTreeCollapsedIcon();
    }

    public Icon getTreeExpandedIcon() {
        return HiFiIcons.getTreeExpandedIcon();
    }

    public Icon getMenuArrowIcon() {
        return HiFiIcons.getMenuArrowIcon();
    }

    public Icon getMenuCheckBoxIcon() {
        return HiFiIcons.getMenuCheckBoxIcon();
    }

    public Icon getMenuRadioButtonIcon() {
        return HiFiIcons.getMenuRadioButtonIcon();
    }

    public Icon getUpArrowIcon() {
        return HiFiIcons.getUpArrowIcon();
    }

    public Icon getDownArrowIcon() {
        return HiFiIcons.getDownArrowIcon();
    }

    public Icon getLeftArrowIcon() {
        return HiFiIcons.getLeftArrowIcon();
    }

    public Icon getRightArrowIcon() {
        return HiFiIcons.getRightArrowIcon();
    }

    public Icon getSplitterDownArrowIcon() {
        return HiFiIcons.getSplitterDownArrowIcon();
    }

    public Icon getSplitterHorBumpIcon() {
        return HiFiIcons.getSplitterHorBumpIcon();
    }

    public Icon getSplitterLeftArrowIcon() {
        return HiFiIcons.getSplitterLeftArrowIcon();
    }

    public Icon getSplitterRightArrowIcon() {
        return HiFiIcons.getSplitterRightArrowIcon();
    }

    public Icon getSplitterUpArrowIcon() {
        return HiFiIcons.getSplitterUpArrowIcon();
    }

    public Icon getSplitterVerBumpIcon() {
        return HiFiIcons.getSplitterVerBumpIcon();
    }

    public Icon getThumbHorIcon() {
        return HiFiIcons.getThumbHorIcon();
    }

    public Icon getThumbVerIcon() {
        return HiFiIcons.getThumbVerIcon();
    }

    public Icon getThumbHorIconRollover() {
        return HiFiIcons.getThumbHorIconRollover();
    }

    public Icon getThumbVerIconRollover() {
        return HiFiIcons.getThumbVerIconRollover();
    }
}
