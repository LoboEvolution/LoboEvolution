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
 
package com.jtattoo.plaf.acryl;

import com.jtattoo.plaf.AbstractIconFactory;
import javax.swing.Icon;

/**
 * @author Michael Hagen
 */
public class AcrylIconFactory implements AbstractIconFactory {

    private static AcrylIconFactory instance = null;

    private AcrylIconFactory() {
    }

    public static synchronized AcrylIconFactory getInstance() {
        if (instance == null) {
            instance = new AcrylIconFactory();
        }
        return instance;
    }

    @Override
    public Icon getOptionPaneErrorIcon() {
        return AcrylIcons.getOptionPaneErrorIcon();
    }

    @Override
    public Icon getOptionPaneWarningIcon() {
        return AcrylIcons.getOptionPaneWarningIcon();
    }

    @Override
    public Icon getOptionPaneInformationIcon() {
        return AcrylIcons.getOptionPaneInformationIcon();
    }

    @Override
    public Icon getOptionPaneQuestionIcon() {
        return AcrylIcons.getOptionPaneQuestionIcon();
    }

    @Override
    public Icon getFileChooserUpFolderIcon() {
        return AcrylIcons.getFileChooserUpFolderIcon();
    }

    @Override
    public Icon getFileChooserHomeFolderIcon() {
        return AcrylIcons.getFileChooserHomeFolderIcon();
    }

    @Override
    public Icon getFileChooserNewFolderIcon() {
        return AcrylIcons.getFileChooserNewFolderIcon();
    }

    @Override
    public Icon getFileChooserListViewIcon() {
        return AcrylIcons.getFileChooserListViewIcon();
    }

    @Override
    public Icon getFileChooserDetailViewIcon() {
        return AcrylIcons.getFileChooserDetailViewIcon();
    }

    @Override
    public Icon getFileViewComputerIcon() {
        return AcrylIcons.getFileViewComputerIcon();
    }

    @Override
    public Icon getFileViewFloppyDriveIcon() {
        return AcrylIcons.getFileViewFloppyDriveIcon();
    }

    @Override
    public Icon getFileViewHardDriveIcon() {
        return AcrylIcons.getFileViewHardDriveIcon();
    }

    @Override
    public Icon getMenuIcon() {
        return AcrylIcons.getMenuIcon();
    }

    @Override
    public Icon getIconIcon() {
        return AcrylIcons.getIconIcon();
    }

    @Override
    public Icon getMaxIcon() {
        return AcrylIcons.getMaxIcon();
    }

    @Override
    public Icon getMinIcon() {
        return AcrylIcons.getMinIcon();
    }

    @Override
    public Icon getCloseIcon() {
        return AcrylIcons.getCloseIcon();
    }

    @Override
    public Icon getPaletteCloseIcon() {
        return AcrylIcons.getPaletteCloseIcon();
    }

    @Override
    public Icon getRadioButtonIcon() {
        return AcrylIcons.getRadioButtonIcon();
    }

    @Override
    public Icon getCheckBoxIcon() {
        return AcrylIcons.getCheckBoxIcon();
    }

    @Override
    public Icon getComboBoxIcon() {
        return AcrylIcons.getComboBoxIcon();
    }

    @Override
    public Icon getTreeOpenIcon() {
        return AcrylIcons.getTreeOpenedIcon();
    }

    @Override
    public Icon getTreeCloseIcon() {
        return AcrylIcons.getTreeClosedIcon();
    }
    
    @Override
    public Icon getTreeLeafIcon() {
        return AcrylIcons.getTreeLeafIcon();
    }

    @Override
    public Icon getTreeCollapsedIcon() {
        return AcrylIcons.getTreeCollapsedIcon();
    }

    @Override
    public Icon getTreeExpandedIcon() {
        return AcrylIcons.getTreeExpandedIcon();
    }

    @Override
    public Icon getMenuArrowIcon() {
        return AcrylIcons.getMenuArrowIcon();
    }

    @Override
    public Icon getMenuCheckBoxIcon() {
        return AcrylIcons.getMenuCheckBoxIcon();
    }

    @Override
    public Icon getMenuRadioButtonIcon() {
        return AcrylIcons.getMenuRadioButtonIcon();
    }

    @Override
    public Icon getUpArrowIcon() {
        return AcrylIcons.getUpArrowIcon();
    }

    @Override
    public Icon getDownArrowIcon() {
        return AcrylIcons.getDownArrowIcon();
    }

    @Override
    public Icon getLeftArrowIcon() {
        return AcrylIcons.getLeftArrowIcon();
    }

    @Override
    public Icon getRightArrowIcon() {
        return AcrylIcons.getRightArrowIcon();
    }

    @Override
    public Icon getSplitterDownArrowIcon() {
        return AcrylIcons.getSplitterDownArrowIcon();
    }

    @Override
    public Icon getSplitterHorBumpIcon() {
        return AcrylIcons.getSplitterHorBumpIcon();
    }

    @Override
    public Icon getSplitterLeftArrowIcon() {
        return AcrylIcons.getSplitterLeftArrowIcon();
    }

    @Override
    public Icon getSplitterRightArrowIcon() {
        return AcrylIcons.getSplitterRightArrowIcon();
    }

    @Override
    public Icon getSplitterUpArrowIcon() {
        return AcrylIcons.getSplitterUpArrowIcon();
    }

    @Override
    public Icon getSplitterVerBumpIcon() {
        return AcrylIcons.getSplitterVerBumpIcon();
    }

    @Override
    public Icon getThumbHorIcon() {
        return AcrylIcons.getThumbHorIcon();
    }

    @Override
    public Icon getThumbVerIcon() {
        return AcrylIcons.getThumbVerIcon();
    }

    @Override
    public Icon getThumbHorIconRollover() {
        return AcrylIcons.getThumbHorIconRollover();
    }

    @Override
    public Icon getThumbVerIconRollover() {
        return AcrylIcons.getThumbVerIconRollover();
    }

} // end of class AcrylIconFactory

