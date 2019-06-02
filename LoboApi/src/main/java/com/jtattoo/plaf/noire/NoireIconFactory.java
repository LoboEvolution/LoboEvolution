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

    @Override
    public Icon getOptionPaneErrorIcon() {
        return NoireIcons.getOptionPaneErrorIcon();
    }

    @Override
    public Icon getOptionPaneWarningIcon() {
        return NoireIcons.getOptionPaneWarningIcon();
    }

    @Override
    public Icon getOptionPaneInformationIcon() {
        return NoireIcons.getOptionPaneInformationIcon();
    }

    @Override
    public Icon getOptionPaneQuestionIcon() {
        return NoireIcons.getOptionPaneQuestionIcon();
    }

    @Override
    public Icon getFileChooserUpFolderIcon() {
        return NoireIcons.getFileChooserUpFolderIcon();
    }

    @Override
    public Icon getFileChooserHomeFolderIcon() {
        return NoireIcons.getFileChooserHomeFolderIcon();
    }

    @Override
    public Icon getFileChooserNewFolderIcon() {
        return NoireIcons.getFileChooserNewFolderIcon();
    }

    @Override
    public Icon getFileChooserListViewIcon() {
        return NoireIcons.getFileChooserListViewIcon();
    }

    @Override
    public Icon getFileChooserDetailViewIcon() {
        return NoireIcons.getFileChooserDetailViewIcon();
    }

    @Override
    public Icon getFileViewComputerIcon() {
        return NoireIcons.getFileViewComputerIcon();
    }

    @Override
    public Icon getFileViewFloppyDriveIcon() {
        return NoireIcons.getFileViewFloppyDriveIcon();
    }

    @Override
    public Icon getFileViewHardDriveIcon() {
        return NoireIcons.getFileViewHardDriveIcon();
    }

    @Override
    public Icon getMenuIcon() {
        return NoireIcons.getMenuIcon();
    }

    @Override
    public Icon getIconIcon() {
        return NoireIcons.getIconIcon();
    }

    @Override
    public Icon getMaxIcon() {
        return NoireIcons.getMaxIcon();
    }

    @Override
    public Icon getMinIcon() {
        return NoireIcons.getMinIcon();
    }

    @Override
    public Icon getCloseIcon() {
        return NoireIcons.getCloseIcon();
    }

    @Override
    public Icon getPaletteCloseIcon() {
        return NoireIcons.getPaletteCloseIcon();
    }

    @Override
    public Icon getRadioButtonIcon() {
        return NoireIcons.getRadioButtonIcon();
    }

    @Override
    public Icon getCheckBoxIcon() {
        return NoireIcons.getCheckBoxIcon();
    }

    @Override
    public Icon getComboBoxIcon() {
        return NoireIcons.getComboBoxIcon();
    }

    @Override
    public Icon getTreeOpenIcon() {
        return NoireIcons.getTreeOpenedIcon();
    }

    @Override
    public Icon getTreeCloseIcon() {
        return NoireIcons.getTreeClosedIcon();
    }

    @Override
    public Icon getTreeLeafIcon() {
        return NoireIcons.getTreeLeafIcon();
    }

    @Override
    public Icon getTreeCollapsedIcon() {
        return NoireIcons.getTreeCollapsedIcon();
    }

    @Override
    public Icon getTreeExpandedIcon() {
        return NoireIcons.getTreeExpandedIcon();
    }

    @Override
    public Icon getMenuArrowIcon() {
        return NoireIcons.getMenuArrowIcon();
    }

    @Override
    public Icon getMenuCheckBoxIcon() {
        return NoireIcons.getMenuCheckBoxIcon();
    }

    @Override
    public Icon getMenuRadioButtonIcon() {
        return NoireIcons.getMenuRadioButtonIcon();
    }

    @Override
    public Icon getUpArrowIcon() {
        return NoireIcons.getUpArrowIcon();
    }

    @Override
    public Icon getDownArrowIcon() {
        return NoireIcons.getDownArrowIcon();
    }

    @Override
    public Icon getLeftArrowIcon() {
        return NoireIcons.getLeftArrowIcon();
    }

    @Override
    public Icon getRightArrowIcon() {
        return NoireIcons.getRightArrowIcon();
    }

    @Override
    public Icon getSplitterDownArrowIcon() {
        return NoireIcons.getSplitterDownArrowIcon();
    }

    @Override
    public Icon getSplitterHorBumpIcon() {
        return NoireIcons.getSplitterHorBumpIcon();
    }

    @Override
    public Icon getSplitterLeftArrowIcon() {
        return NoireIcons.getSplitterLeftArrowIcon();
    }

    @Override
    public Icon getSplitterRightArrowIcon() {
        return NoireIcons.getSplitterRightArrowIcon();
    }

    @Override
    public Icon getSplitterUpArrowIcon() {
        return NoireIcons.getSplitterUpArrowIcon();
    }

    @Override
    public Icon getSplitterVerBumpIcon() {
        return NoireIcons.getSplitterVerBumpIcon();
    }

    @Override
    public Icon getThumbHorIcon() {
        return NoireIcons.getThumbHorIcon();
    }

    @Override
    public Icon getThumbVerIcon() {
        return NoireIcons.getThumbVerIcon();
    }

    @Override
    public Icon getThumbHorIconRollover() {
        return NoireIcons.getThumbHorIconRollover();
    }

    @Override
    public Icon getThumbVerIconRollover() {
        return NoireIcons.getThumbVerIconRollover();
    }
    
} // end of class NoireIconFactory
