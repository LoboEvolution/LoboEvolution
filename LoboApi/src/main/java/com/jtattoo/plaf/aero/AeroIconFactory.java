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
 
package com.jtattoo.plaf.aero;

import com.jtattoo.plaf.AbstractIconFactory;
import javax.swing.Icon;

/**
 * @author Michael Hagen
 */
public class AeroIconFactory implements AbstractIconFactory {

    private static AeroIconFactory instance = null;

    private AeroIconFactory() {
    }

    public static synchronized AeroIconFactory getInstance() {
        if (instance == null) {
            instance = new AeroIconFactory();
        }
        return instance;
    }

    @Override
    public Icon getOptionPaneErrorIcon() {
        return AeroIcons.getOptionPaneErrorIcon();
    }

    @Override
    public Icon getOptionPaneWarningIcon() {
        return AeroIcons.getOptionPaneWarningIcon();
    }

    @Override
    public Icon getOptionPaneInformationIcon() {
        return AeroIcons.getOptionPaneInformationIcon();
    }

    @Override
    public Icon getOptionPaneQuestionIcon() {
        return AeroIcons.getOptionPaneQuestionIcon();
    }

    @Override
    public Icon getFileChooserUpFolderIcon() {
        return AeroIcons.getFileChooserUpFolderIcon();
    }

    @Override
    public Icon getFileChooserHomeFolderIcon() {
        return AeroIcons.getFileChooserHomeFolderIcon();
    }

    @Override
    public Icon getFileChooserNewFolderIcon() {
        return AeroIcons.getFileChooserNewFolderIcon();
    }

    @Override
    public Icon getFileChooserListViewIcon() {
        return AeroIcons.getFileChooserListViewIcon();
    }

    @Override
    public Icon getFileChooserDetailViewIcon() {
        return AeroIcons.getFileChooserDetailViewIcon();
    }

    @Override
    public Icon getFileViewComputerIcon() {
        return AeroIcons.getFileViewComputerIcon();
    }

    @Override
    public Icon getFileViewFloppyDriveIcon() {
        return AeroIcons.getFileViewFloppyDriveIcon();
    }

    @Override
    public Icon getFileViewHardDriveIcon() {
        return AeroIcons.getFileViewHardDriveIcon();
    }

    @Override
    public Icon getMenuIcon() {
        return AeroIcons.getMenuIcon();
    }

    @Override
    public Icon getIconIcon() {
        return AeroIcons.getIconIcon();
    }

    @Override
    public Icon getMaxIcon() {
        return AeroIcons.getMaxIcon();
    }

    @Override
    public Icon getMinIcon() {
        return AeroIcons.getMinIcon();
    }

    @Override
    public Icon getCloseIcon() {
        return AeroIcons.getCloseIcon();
    }

    @Override
    public Icon getPaletteCloseIcon() {
        return AeroIcons.getPaletteCloseIcon();
    }

    @Override
    public Icon getRadioButtonIcon() {
        return AeroIcons.getRadioButtonIcon();
    }

    @Override
    public Icon getCheckBoxIcon() {
        return AeroIcons.getCheckBoxIcon();
    }

    @Override
    public Icon getComboBoxIcon() {
        return AeroIcons.getComboBoxIcon();
    }

    @Override
    public Icon getTreeOpenIcon() {
        return AeroIcons.getTreeOpenedIcon();
    }

    @Override
    public Icon getTreeCloseIcon() {
        return AeroIcons.getTreeClosedIcon();
    }

    @Override
    public Icon getTreeLeafIcon() {
        return AeroIcons.getTreeLeafIcon();
    }

    @Override
    public Icon getTreeCollapsedIcon() {
        return AeroIcons.getTreeCollapsedIcon();
    }

    @Override
    public Icon getTreeExpandedIcon() {
        return AeroIcons.getTreeExpandedIcon();
    }

    @Override
    public Icon getMenuArrowIcon() {
        return AeroIcons.getMenuArrowIcon();
    }

    @Override
    public Icon getMenuCheckBoxIcon() {
        return AeroIcons.getMenuCheckBoxIcon();
    }

    @Override
    public Icon getMenuRadioButtonIcon() {
        return AeroIcons.getMenuRadioButtonIcon();
    }

    @Override
    public Icon getUpArrowIcon() {
        return AeroIcons.getUpArrowIcon();
    }

    @Override
    public Icon getDownArrowIcon() {
        return AeroIcons.getDownArrowIcon();
    }

    @Override
    public Icon getLeftArrowIcon() {
        return AeroIcons.getLeftArrowIcon();
    }

    @Override
    public Icon getRightArrowIcon() {
        return AeroIcons.getRightArrowIcon();
    }

    @Override
    public Icon getSplitterDownArrowIcon() {
        return AeroIcons.getSplitterDownArrowIcon();
    }

    @Override
    public Icon getSplitterHorBumpIcon() {
        return AeroIcons.getSplitterHorBumpIcon();
    }

    @Override
    public Icon getSplitterLeftArrowIcon() {
        return AeroIcons.getSplitterLeftArrowIcon();
    }

    @Override
    public Icon getSplitterRightArrowIcon() {
        return AeroIcons.getSplitterRightArrowIcon();
    }

    @Override
    public Icon getSplitterUpArrowIcon() {
        return AeroIcons.getSplitterUpArrowIcon();
    }

    @Override
    public Icon getSplitterVerBumpIcon() {
        return AeroIcons.getSplitterVerBumpIcon();
    }

    @Override
    public Icon getThumbHorIcon() {
        return AeroIcons.getThumbHorIcon();
    }

    @Override
    public Icon getThumbVerIcon() {
        return AeroIcons.getThumbVerIcon();
    }

    @Override
    public Icon getThumbHorIconRollover() {
        return AeroIcons.getThumbHorIconRollover();
    }

    @Override
    public Icon getThumbVerIconRollover() {
        return AeroIcons.getThumbVerIconRollover();
    }

} // end of class AeroIconFactory
