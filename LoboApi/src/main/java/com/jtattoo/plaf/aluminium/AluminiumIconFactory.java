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
package com.jtattoo.plaf.aluminium;

import com.jtattoo.plaf.AbstractIconFactory;
import javax.swing.Icon;

/**
 * @author Michael Hagen
 */
public class AluminiumIconFactory implements AbstractIconFactory {

    private static AluminiumIconFactory instance = null;

    private AluminiumIconFactory() {
    }

    public static synchronized AluminiumIconFactory getInstance() {
        if (instance == null) {
            instance = new AluminiumIconFactory();
        }
        return instance;
    }

    @Override
    public Icon getOptionPaneErrorIcon() {
        return AluminiumIcons.getOptionPaneErrorIcon();
    }

    @Override
    public Icon getOptionPaneWarningIcon() {
        return AluminiumIcons.getOptionPaneWarningIcon();
    }

    @Override
    public Icon getOptionPaneInformationIcon() {
        return AluminiumIcons.getOptionPaneInformationIcon();
    }

    @Override
    public Icon getOptionPaneQuestionIcon() {
        return AluminiumIcons.getOptionPaneQuestionIcon();
    }

    @Override
    public Icon getFileChooserUpFolderIcon() {
        return AluminiumIcons.getFileChooserUpFolderIcon();
    }

    @Override
    public Icon getFileChooserHomeFolderIcon() {
        return AluminiumIcons.getFileChooserHomeFolderIcon();
    }

    @Override
    public Icon getFileChooserNewFolderIcon() {
        return AluminiumIcons.getFileChooserNewFolderIcon();
    }

    @Override
    public Icon getFileChooserListViewIcon() {
        return AluminiumIcons.getFileChooserListViewIcon();
    }

    @Override
    public Icon getFileChooserDetailViewIcon() {
        return AluminiumIcons.getFileChooserDetailViewIcon();
    }

    @Override
    public Icon getFileViewComputerIcon() {
        return AluminiumIcons.getFileViewComputerIcon();
    }

    @Override
    public Icon getFileViewFloppyDriveIcon() {
        return AluminiumIcons.getFileViewFloppyDriveIcon();
    }

    @Override
    public Icon getFileViewHardDriveIcon() {
        return AluminiumIcons.getFileViewHardDriveIcon();
    }

    @Override
    public Icon getMenuIcon() {
        return AluminiumIcons.getMenuIcon();
    }

    @Override
    public Icon getIconIcon() {
        return AluminiumIcons.getIconIcon();
    }

    @Override
    public Icon getMaxIcon() {
        return AluminiumIcons.getMaxIcon();
    }

    @Override
    public Icon getMinIcon() {
        return AluminiumIcons.getMinIcon();
    }

    @Override
    public Icon getCloseIcon() {
        return AluminiumIcons.getCloseIcon();
    }

    @Override
    public Icon getPaletteCloseIcon() {
        return AluminiumIcons.getPaletteCloseIcon();
    }

    @Override
    public Icon getRadioButtonIcon() {
        return AluminiumIcons.getRadioButtonIcon();
    }

    @Override
    public Icon getCheckBoxIcon() {
        return AluminiumIcons.getCheckBoxIcon();
    }

    @Override
    public Icon getComboBoxIcon() {
        return AluminiumIcons.getComboBoxIcon();
    }

    @Override
    public Icon getTreeOpenIcon() {
        return AluminiumIcons.getTreeOpenedIcon();
    }

    @Override
    public Icon getTreeCloseIcon() {
        return AluminiumIcons.getTreeClosedIcon();
    }

    @Override
    public Icon getTreeLeafIcon() {
        return AluminiumIcons.getTreeLeafIcon();
    }

    @Override
    public Icon getTreeCollapsedIcon() {
        return AluminiumIcons.getTreeCollapsedIcon();
    }

    @Override
    public Icon getTreeExpandedIcon() {
        return AluminiumIcons.getTreeExpandedIcon();
    }

    @Override
    public Icon getMenuArrowIcon() {
        return AluminiumIcons.getMenuArrowIcon();
    }

    @Override
    public Icon getMenuCheckBoxIcon() {
        return AluminiumIcons.getMenuCheckBoxIcon();
    }

    @Override
    public Icon getMenuRadioButtonIcon() {
        return AluminiumIcons.getMenuRadioButtonIcon();
    }

    @Override
    public Icon getUpArrowIcon() {
        return AluminiumIcons.getUpArrowIcon();
    }

    @Override
    public Icon getDownArrowIcon() {
        return AluminiumIcons.getDownArrowIcon();
    }

    @Override
    public Icon getLeftArrowIcon() {
        return AluminiumIcons.getLeftArrowIcon();
    }

    @Override
    public Icon getRightArrowIcon() {
        return AluminiumIcons.getRightArrowIcon();
    }

    public Icon getSplitterDownArrowIcon() {
        return AluminiumIcons.getSplitterDownArrowIcon();
    }

    @Override
    public Icon getSplitterHorBumpIcon() {
        return AluminiumIcons.getSplitterHorBumpIcon();
    }

    @Override
    public Icon getSplitterLeftArrowIcon() {
        return AluminiumIcons.getSplitterLeftArrowIcon();
    }

    @Override
    public Icon getSplitterRightArrowIcon() {
        return AluminiumIcons.getSplitterRightArrowIcon();
    }

    @Override
    public Icon getSplitterUpArrowIcon() {
        return AluminiumIcons.getSplitterUpArrowIcon();
    }

    @Override
    public Icon getSplitterVerBumpIcon() {
        return AluminiumIcons.getSplitterVerBumpIcon();
    }

    @Override
    public Icon getThumbHorIcon() {
        return AluminiumIcons.getThumbHorIcon();
    }

    @Override
    public Icon getThumbVerIcon() {
        return AluminiumIcons.getThumbVerIcon();
    }

    @Override
    public Icon getThumbHorIconRollover() {
        return AluminiumIcons.getThumbHorIconRollover();
    }

    @Override
    public Icon getThumbVerIconRollover() {
        return AluminiumIcons.getThumbVerIconRollover();
    }
    
} // end of class AluminiumIconFactory
