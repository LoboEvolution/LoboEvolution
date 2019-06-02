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
package com.jtattoo.plaf.mint;

import com.jtattoo.plaf.AbstractIconFactory;
import javax.swing.Icon;

/**
 * @author Michael Hagen
 */
public class MintIconFactory implements AbstractIconFactory {

    private static MintIconFactory instance = null;

    private MintIconFactory() {
    }

    public static synchronized MintIconFactory getInstance() {
        if (instance == null) {
            instance = new MintIconFactory();
        }
        return instance;
    }

    @Override
    public Icon getOptionPaneErrorIcon() {
        return MintIcons.getOptionPaneErrorIcon();
    }

    @Override
    public Icon getOptionPaneWarningIcon() {
        return MintIcons.getOptionPaneWarningIcon();
    }

    @Override
    public Icon getOptionPaneInformationIcon() {
        return MintIcons.getOptionPaneInformationIcon();
    }

    @Override
    public Icon getOptionPaneQuestionIcon() {
        return MintIcons.getOptionPaneQuestionIcon();
    }

    @Override
    public Icon getFileChooserUpFolderIcon() {
        return MintIcons.getFileChooserUpFolderIcon();
    }

    @Override
    public Icon getFileChooserHomeFolderIcon() {
        return MintIcons.getFileChooserHomeFolderIcon();
    }

    @Override
    public Icon getFileChooserNewFolderIcon() {
        return MintIcons.getFileChooserNewFolderIcon();
    }

    @Override
    public Icon getFileChooserListViewIcon() {
        return MintIcons.getFileChooserListViewIcon();
    }

    @Override
    public Icon getFileChooserDetailViewIcon() {
        return MintIcons.getFileChooserDetailViewIcon();
    }

    @Override
    public Icon getFileViewComputerIcon() {
        return MintIcons.getFileViewComputerIcon();
    }

    @Override
    public Icon getFileViewFloppyDriveIcon() {
        return MintIcons.getFileViewFloppyDriveIcon();
    }

    @Override
    public Icon getFileViewHardDriveIcon() {
        return MintIcons.getFileViewHardDriveIcon();
    }

    @Override
    public Icon getMenuIcon() {
        return MintIcons.getMenuIcon();
    }

    @Override
    public Icon getIconIcon() {
        return MintIcons.getIconIcon();
    }

    @Override
    public Icon getMaxIcon() {
        return MintIcons.getMaxIcon();
    }

    @Override
    public Icon getMinIcon() {
        return MintIcons.getMinIcon();
    }

    @Override
    public Icon getCloseIcon() {
        return MintIcons.getCloseIcon();
    }

    @Override
    public Icon getPaletteCloseIcon() {
        return MintIcons.getPaletteCloseIcon();
    }

    @Override
    public Icon getRadioButtonIcon() {
        return MintIcons.getRadioButtonIcon();
    }

    @Override
    public Icon getCheckBoxIcon() {
        return MintIcons.getCheckBoxIcon();
    }

    @Override
    public Icon getComboBoxIcon() {
        return MintIcons.getComboBoxIcon();
    }

    @Override
    public Icon getTreeOpenIcon() {
        return MintIcons.getTreeOpenedIcon();
    }

    @Override
    public Icon getTreeCloseIcon() {
        return MintIcons.getTreeClosedIcon();
    }

    @Override
    public Icon getTreeLeafIcon() {
        return MintIcons.getTreeLeafIcon();
    }

    @Override
    public Icon getTreeCollapsedIcon() {
        return MintIcons.getTreeCollapsedIcon();
    }

    @Override
    public Icon getTreeExpandedIcon() {
        return MintIcons.getTreeExpandedIcon();
    }

    @Override
    public Icon getMenuArrowIcon() {
        return MintIcons.getMenuArrowIcon();
    }

    @Override
    public Icon getMenuCheckBoxIcon() {
        return MintIcons.getMenuCheckBoxIcon();
    }

    @Override
    public Icon getMenuRadioButtonIcon() {
        return MintIcons.getMenuRadioButtonIcon();
    }

    @Override
    public Icon getUpArrowIcon() {
        return MintIcons.getUpArrowIcon();
    }

    @Override
    public Icon getDownArrowIcon() {
        return MintIcons.getDownArrowIcon();
    }

    @Override
    public Icon getLeftArrowIcon() {
        return MintIcons.getLeftArrowIcon();
    }

    @Override
    public Icon getRightArrowIcon() {
        return MintIcons.getRightArrowIcon();
    }

    @Override
    public Icon getSplitterDownArrowIcon() {
        return MintIcons.getSplitterDownArrowIcon();
    }

    @Override
    public Icon getSplitterHorBumpIcon() {
        return MintIcons.getSplitterHorBumpIcon();
    }

    @Override
    public Icon getSplitterLeftArrowIcon() {
        return MintIcons.getSplitterLeftArrowIcon();
    }

    @Override
    public Icon getSplitterRightArrowIcon() {
        return MintIcons.getSplitterRightArrowIcon();
    }

    @Override
    public Icon getSplitterUpArrowIcon() {
        return MintIcons.getSplitterUpArrowIcon();
    }

    @Override
    public Icon getSplitterVerBumpIcon() {
        return MintIcons.getSplitterVerBumpIcon();
    }

    @Override
    public Icon getThumbHorIcon() {
        return MintIcons.getThumbHorIcon();
    }

    @Override
    public Icon getThumbVerIcon() {
        return MintIcons.getThumbVerIcon();
    }

    @Override
    public Icon getThumbHorIconRollover() {
        return MintIcons.getThumbHorIconRollover();
    }

    @Override
    public Icon getThumbVerIconRollover() {
        return MintIcons.getThumbVerIconRollover();
    }
    
} // end of class MintIconFactory
