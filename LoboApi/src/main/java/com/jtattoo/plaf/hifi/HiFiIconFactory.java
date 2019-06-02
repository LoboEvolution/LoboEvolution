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

    @Override
    public Icon getOptionPaneErrorIcon() {
        return HiFiIcons.getOptionPaneErrorIcon();
    }

    @Override
    public Icon getOptionPaneWarningIcon() {
        return HiFiIcons.getOptionPaneWarningIcon();
    }

    @Override
    public Icon getOptionPaneInformationIcon() {
        return HiFiIcons.getOptionPaneInformationIcon();
    }

    @Override
    public Icon getOptionPaneQuestionIcon() {
        return HiFiIcons.getOptionPaneQuestionIcon();
    }

    @Override
    public Icon getFileChooserUpFolderIcon() {
        return HiFiIcons.getFileChooserUpFolderIcon();
    }

    @Override
    public Icon getFileChooserHomeFolderIcon() {
        return HiFiIcons.getFileChooserHomeFolderIcon();
    }

    @Override
    public Icon getFileChooserNewFolderIcon() {
        return HiFiIcons.getFileChooserNewFolderIcon();
    }

    @Override
    public Icon getFileChooserListViewIcon() {
        return HiFiIcons.getFileChooserListViewIcon();
    }

    @Override
    public Icon getFileChooserDetailViewIcon() {
        return HiFiIcons.getFileChooserDetailViewIcon();
    }

    @Override
    public Icon getFileViewComputerIcon() {
        return HiFiIcons.getFileViewComputerIcon();
    }

    @Override
    public Icon getFileViewFloppyDriveIcon() {
        return HiFiIcons.getFileViewFloppyDriveIcon();
    }

    @Override
    public Icon getFileViewHardDriveIcon() {
        return HiFiIcons.getFileViewHardDriveIcon();
    }

    @Override
    public Icon getMenuIcon() {
        return HiFiIcons.getMenuIcon();
    }

    @Override
    public Icon getIconIcon() {
        return HiFiIcons.getIconIcon();
    }

    @Override
    public Icon getMaxIcon() {
        return HiFiIcons.getMaxIcon();
    }

    @Override
    public Icon getMinIcon() {
        return HiFiIcons.getMinIcon();
    }

    @Override
    public Icon getCloseIcon() {
        return HiFiIcons.getCloseIcon();
    }

    @Override
    public Icon getPaletteCloseIcon() {
        return HiFiIcons.getPaletteCloseIcon();
    }

    @Override
    public Icon getRadioButtonIcon() {
        return HiFiIcons.getRadioButtonIcon();
    }

    @Override
    public Icon getCheckBoxIcon() {
        return HiFiIcons.getCheckBoxIcon();
    }

    @Override
    public Icon getComboBoxIcon() {
        return HiFiIcons.getComboBoxIcon();
    }

    @Override
    public Icon getTreeOpenIcon() {
        return HiFiIcons.getTreeOpenedIcon();
    }

    @Override
    public Icon getTreeCloseIcon() {
        return HiFiIcons.getTreeClosedIcon();
    }

    @Override
    public Icon getTreeLeafIcon() {
        return HiFiIcons.getTreeLeafIcon();
    }

    @Override
    public Icon getTreeCollapsedIcon() {
        return HiFiIcons.getTreeCollapsedIcon();
    }

    @Override
    public Icon getTreeExpandedIcon() {
        return HiFiIcons.getTreeExpandedIcon();
    }

    @Override
    public Icon getMenuArrowIcon() {
        return HiFiIcons.getMenuArrowIcon();
    }

    @Override
    public Icon getMenuCheckBoxIcon() {
        return HiFiIcons.getMenuCheckBoxIcon();
    }

    @Override
    public Icon getMenuRadioButtonIcon() {
        return HiFiIcons.getMenuRadioButtonIcon();
    }

    @Override
    public Icon getUpArrowIcon() {
        return HiFiIcons.getUpArrowIcon();
    }

    @Override
    public Icon getDownArrowIcon() {
        return HiFiIcons.getDownArrowIcon();
    }

    @Override
    public Icon getLeftArrowIcon() {
        return HiFiIcons.getLeftArrowIcon();
    }

    @Override
    public Icon getRightArrowIcon() {
        return HiFiIcons.getRightArrowIcon();
    }

    @Override
    public Icon getSplitterDownArrowIcon() {
        return HiFiIcons.getSplitterDownArrowIcon();
    }

    @Override
    public Icon getSplitterHorBumpIcon() {
        return HiFiIcons.getSplitterHorBumpIcon();
    }

    @Override
    public Icon getSplitterLeftArrowIcon() {
        return HiFiIcons.getSplitterLeftArrowIcon();
    }

    @Override
    public Icon getSplitterRightArrowIcon() {
        return HiFiIcons.getSplitterRightArrowIcon();
    }

    @Override
    public Icon getSplitterUpArrowIcon() {
        return HiFiIcons.getSplitterUpArrowIcon();
    }

    @Override
    public Icon getSplitterVerBumpIcon() {
        return HiFiIcons.getSplitterVerBumpIcon();
    }

    @Override
    public Icon getThumbHorIcon() {
        return HiFiIcons.getThumbHorIcon();
    }

    @Override
    public Icon getThumbVerIcon() {
        return HiFiIcons.getThumbVerIcon();
    }

    @Override
    public Icon getThumbHorIconRollover() {
        return HiFiIcons.getThumbHorIconRollover();
    }

    @Override
    public Icon getThumbVerIconRollover() {
        return HiFiIcons.getThumbVerIconRollover();
    }
    
} // end of class HiFiIconFactory
