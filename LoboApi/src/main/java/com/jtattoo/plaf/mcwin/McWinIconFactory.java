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
package com.jtattoo.plaf.mcwin;

import com.jtattoo.plaf.AbstractIconFactory;
import javax.swing.Icon;

/**
 * @author Michael Hagen
 */
public class McWinIconFactory implements AbstractIconFactory {

    private static McWinIconFactory instance = null;

    private McWinIconFactory() {
    }

    public static synchronized McWinIconFactory getInstance() {
        if (instance == null) {
            instance = new McWinIconFactory();
        }
        return instance;
    }

    @Override
    public Icon getOptionPaneErrorIcon() {
        return McWinIcons.getOptionPaneErrorIcon();
    }

    @Override
    public Icon getOptionPaneWarningIcon() {
        return McWinIcons.getOptionPaneWarningIcon();
    }

    @Override
    public Icon getOptionPaneInformationIcon() {
        return McWinIcons.getOptionPaneInformationIcon();
    }

    @Override
    public Icon getOptionPaneQuestionIcon() {
        return McWinIcons.getOptionPaneQuestionIcon();
    }

    @Override
    public Icon getFileChooserUpFolderIcon() {
        return McWinIcons.getFileChooserUpFolderIcon();
    }

    @Override
    public Icon getFileChooserHomeFolderIcon() {
        return McWinIcons.getFileChooserHomeFolderIcon();
    }

    @Override
    public Icon getFileChooserNewFolderIcon() {
        return McWinIcons.getFileChooserNewFolderIcon();
    }

    @Override
    public Icon getFileChooserListViewIcon() {
        return McWinIcons.getFileChooserListViewIcon();
    }

    @Override
    public Icon getFileChooserDetailViewIcon() {
        return McWinIcons.getFileChooserDetailViewIcon();
    }

    @Override
    public Icon getFileViewComputerIcon() {
        return McWinIcons.getFileViewComputerIcon();
    }

    @Override
    public Icon getFileViewFloppyDriveIcon() {
        return McWinIcons.getFileViewFloppyDriveIcon();
    }

    @Override
    public Icon getFileViewHardDriveIcon() {
        return McWinIcons.getFileViewHardDriveIcon();
    }

    @Override
    public Icon getMenuIcon() {
        return McWinIcons.getMenuIcon();
    }

    @Override
    public Icon getIconIcon() {
        return McWinIcons.getIconIcon();
    }

    @Override
    public Icon getMaxIcon() {
        return McWinIcons.getMaxIcon();
    }

    @Override
    public Icon getMinIcon() {
        return McWinIcons.getMinIcon();
    }

    @Override
    public Icon getCloseIcon() {
        return McWinIcons.getCloseIcon();
    }

    @Override
    public Icon getPaletteCloseIcon() {
        return McWinIcons.getPaletteCloseIcon();
    }

    @Override
    public Icon getRadioButtonIcon() {
        return McWinIcons.getRadioButtonIcon();
    }

    @Override
    public Icon getCheckBoxIcon() {
        return McWinIcons.getCheckBoxIcon();
    }

    @Override
    public Icon getComboBoxIcon() {
        return McWinIcons.getComboBoxIcon();
    }

    @Override
    public Icon getTreeOpenIcon() {
        return McWinIcons.getTreeOpenedIcon();
    }

    @Override
    public Icon getTreeCloseIcon() {
        return McWinIcons.getTreeClosedIcon();
    }

    @Override
    public Icon getTreeLeafIcon() {
        return McWinIcons.getTreeLeafIcon();
    }

    @Override
    public Icon getTreeCollapsedIcon() {
        return McWinIcons.getTreeCollapsedIcon();
    }

    @Override
    public Icon getTreeExpandedIcon() {
        return McWinIcons.getTreeExpandedIcon();
    }

    @Override
    public Icon getMenuArrowIcon() {
        return McWinIcons.getMenuArrowIcon();
    }

    @Override
    public Icon getMenuCheckBoxIcon() {
        return McWinIcons.getMenuCheckBoxIcon();
    }

    @Override
    public Icon getMenuRadioButtonIcon() {
        return McWinIcons.getMenuRadioButtonIcon();
    }

    @Override
    public Icon getUpArrowIcon() {
        return McWinIcons.getUpArrowIcon();
    }

    @Override
    public Icon getDownArrowIcon() {
        return McWinIcons.getDownArrowIcon();
    }

    @Override
    public Icon getLeftArrowIcon() {
        return McWinIcons.getLeftArrowIcon();
    }

    @Override
    public Icon getRightArrowIcon() {
        return McWinIcons.getRightArrowIcon();
    }

    public Icon getSplitterDownArrowIcon() {
        return McWinIcons.getSplitterDownArrowIcon();
    }

    @Override
    public Icon getSplitterHorBumpIcon() {
        return McWinIcons.getSplitterHorBumpIcon();
    }

    @Override
    public Icon getSplitterLeftArrowIcon() {
        return McWinIcons.getSplitterLeftArrowIcon();
    }

    @Override
    public Icon getSplitterRightArrowIcon() {
        return McWinIcons.getSplitterRightArrowIcon();
    }

    @Override
    public Icon getSplitterUpArrowIcon() {
        return McWinIcons.getSplitterUpArrowIcon();
    }

    @Override
    public Icon getSplitterVerBumpIcon() {
        return McWinIcons.getSplitterVerBumpIcon();
    }

    @Override
    public Icon getThumbHorIcon() {
        return McWinIcons.getThumbHorIcon();
    }

    @Override
    public Icon getThumbVerIcon() {
        return McWinIcons.getThumbVerIcon();
    }

    @Override
    public Icon getThumbHorIconRollover() {
        return McWinIcons.getThumbHorIconRollover();
    }

    @Override
    public Icon getThumbVerIconRollover() {
        return McWinIcons.getThumbVerIconRollover();
    }
    
} // end of class McWinIconFactory
