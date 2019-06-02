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
package com.jtattoo.plaf.smart;

import com.jtattoo.plaf.AbstractIconFactory;
import javax.swing.Icon;

/**
 * @author Michael Hagen
 */
public class SmartIconFactory implements AbstractIconFactory {

    private static SmartIconFactory instance = null;

    private SmartIconFactory() {
    }

    public static synchronized SmartIconFactory getInstance() {
        if (instance == null) {
            instance = new SmartIconFactory();
        }
        return instance;
    }

    @Override
    public Icon getOptionPaneErrorIcon() {
        return SmartIcons.getOptionPaneErrorIcon();
    }

    @Override
    public Icon getOptionPaneWarningIcon() {
        return SmartIcons.getOptionPaneWarningIcon();
    }

    @Override
    public Icon getOptionPaneInformationIcon() {
        return SmartIcons.getOptionPaneInformationIcon();
    }

    @Override
    public Icon getOptionPaneQuestionIcon() {
        return SmartIcons.getOptionPaneQuestionIcon();
    }

    @Override
    public Icon getFileChooserUpFolderIcon() {
        return SmartIcons.getFileChooserUpFolderIcon();
    }

    @Override
    public Icon getFileChooserHomeFolderIcon() {
        return SmartIcons.getFileChooserHomeFolderIcon();
    }

    @Override
    public Icon getFileChooserNewFolderIcon() {
        return SmartIcons.getFileChooserNewFolderIcon();
    }

    @Override
    public Icon getFileChooserListViewIcon() {
        return SmartIcons.getFileChooserListViewIcon();
    }

    @Override
    public Icon getFileChooserDetailViewIcon() {
        return SmartIcons.getFileChooserDetailViewIcon();
    }

    @Override
    public Icon getFileViewComputerIcon() {
        return SmartIcons.getFileViewComputerIcon();
    }

    @Override
    public Icon getFileViewFloppyDriveIcon() {
        return SmartIcons.getFileViewFloppyDriveIcon();
    }

    @Override
    public Icon getFileViewHardDriveIcon() {
        return SmartIcons.getFileViewHardDriveIcon();
    }

    @Override
    public Icon getMenuIcon() {
        return SmartIcons.getMenuIcon();
    }

    @Override
    public Icon getIconIcon() {
        return SmartIcons.getIconIcon();
    }

    @Override
    public Icon getMaxIcon() {
        return SmartIcons.getMaxIcon();
    }

    @Override
    public Icon getMinIcon() {
        return SmartIcons.getMinIcon();
    }

    @Override
    public Icon getCloseIcon() {
        return SmartIcons.getCloseIcon();
    }

    @Override
    public Icon getPaletteCloseIcon() {
        return SmartIcons.getPaletteCloseIcon();
    }

    @Override
    public Icon getRadioButtonIcon() {
        return SmartIcons.getRadioButtonIcon();
    }

    @Override
    public Icon getCheckBoxIcon() {
        return SmartIcons.getCheckBoxIcon();
    }

    @Override
    public Icon getComboBoxIcon() {
        return SmartIcons.getComboBoxIcon();
    }

    @Override
    public Icon getTreeOpenIcon() {
        return SmartIcons.getTreeOpenedIcon();
    }

    @Override
    public Icon getTreeCloseIcon() {
        return SmartIcons.getTreeClosedIcon();
    }

    @Override
    public Icon getTreeLeafIcon() {
        return SmartIcons.getTreeLeafIcon();
    }

    @Override
    public Icon getTreeCollapsedIcon() {
        return SmartIcons.getTreeCollapsedIcon();
    }

    @Override
    public Icon getTreeExpandedIcon() {
        return SmartIcons.getTreeExpandedIcon();
    }

    @Override
    public Icon getMenuArrowIcon() {
        return SmartIcons.getMenuArrowIcon();
    }

    @Override
    public Icon getMenuCheckBoxIcon() {
        return SmartIcons.getMenuCheckBoxIcon();
    }

    @Override
    public Icon getMenuRadioButtonIcon() {
        return SmartIcons.getMenuRadioButtonIcon();
    }

    @Override
    public Icon getUpArrowIcon() {
        return SmartIcons.getUpArrowIcon();
    }

    @Override
    public Icon getDownArrowIcon() {
        return SmartIcons.getDownArrowIcon();
    }

    @Override
    public Icon getLeftArrowIcon() {
        return SmartIcons.getLeftArrowIcon();
    }

    @Override
    public Icon getRightArrowIcon() {
        return SmartIcons.getRightArrowIcon();
    }

    @Override
    public Icon getSplitterDownArrowIcon() {
        return SmartIcons.getSplitterDownArrowIcon();
    }

    @Override
    public Icon getSplitterHorBumpIcon() {
        return SmartIcons.getSplitterHorBumpIcon();
    }

    @Override
    public Icon getSplitterLeftArrowIcon() {
        return SmartIcons.getSplitterLeftArrowIcon();
    }

    @Override
    public Icon getSplitterRightArrowIcon() {
        return SmartIcons.getSplitterRightArrowIcon();
    }

    @Override
    public Icon getSplitterUpArrowIcon() {
        return SmartIcons.getSplitterUpArrowIcon();
    }

    @Override
    public Icon getSplitterVerBumpIcon() {
        return SmartIcons.getSplitterVerBumpIcon();
    }

    @Override
    public Icon getThumbHorIcon() {
        return SmartIcons.getThumbHorIcon();
    }

    @Override
    public Icon getThumbVerIcon() {
        return SmartIcons.getThumbVerIcon();
    }

    @Override
    public Icon getThumbHorIconRollover() {
        return SmartIcons.getThumbHorIconRollover();
    }

    @Override
    public Icon getThumbVerIconRollover() {
        return SmartIcons.getThumbVerIconRollover();
    }
    
} // end of class SmartIconFactory
