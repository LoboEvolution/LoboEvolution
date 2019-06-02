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
package com.jtattoo.plaf.fast;

import com.jtattoo.plaf.AbstractIconFactory;
import javax.swing.Icon;

/**
 * @author Michael Hagen
 */
public class FastIconFactory implements AbstractIconFactory {

    private static FastIconFactory instance = null;

    private FastIconFactory() {
    }

    public static synchronized FastIconFactory getInstance() {
        if (instance == null) {
            instance = new FastIconFactory();
        }
        return instance;
    }

    @Override
    public Icon getOptionPaneErrorIcon() {
        return FastIcons.getOptionPaneErrorIcon();
    }

    @Override
    public Icon getOptionPaneWarningIcon() {
        return FastIcons.getOptionPaneWarningIcon();
    }

    @Override
    public Icon getOptionPaneInformationIcon() {
        return FastIcons.getOptionPaneInformationIcon();
    }

    @Override
    public Icon getOptionPaneQuestionIcon() {
        return FastIcons.getOptionPaneQuestionIcon();
    }

    @Override
    public Icon getFileChooserUpFolderIcon() {
        return FastIcons.getFileChooserUpFolderIcon();
    }

    @Override
    public Icon getFileChooserHomeFolderIcon() {
        return FastIcons.getFileChooserHomeFolderIcon();
    }

    @Override
    public Icon getFileChooserNewFolderIcon() {
        return FastIcons.getFileChooserNewFolderIcon();
    }

    @Override
    public Icon getFileChooserListViewIcon() {
        return FastIcons.getFileChooserListViewIcon();
    }

    @Override
    public Icon getFileChooserDetailViewIcon() {
        return FastIcons.getFileChooserDetailViewIcon();
    }

    @Override
    public Icon getFileViewComputerIcon() {
        return FastIcons.getFileViewComputerIcon();
    }

    @Override
    public Icon getFileViewFloppyDriveIcon() {
        return FastIcons.getFileViewFloppyDriveIcon();
    }

    @Override
    public Icon getFileViewHardDriveIcon() {
        return FastIcons.getFileViewHardDriveIcon();
    }

    @Override
    public Icon getMenuIcon() {
        return FastIcons.getMenuIcon();
    }

    @Override
    public Icon getIconIcon() {
        return FastIcons.getIconIcon();
    }

    @Override
    public Icon getMaxIcon() {
        return FastIcons.getMaxIcon();
    }

    @Override
    public Icon getMinIcon() {
        return FastIcons.getMinIcon();
    }

    @Override
    public Icon getCloseIcon() {
        return FastIcons.getCloseIcon();
    }

    @Override
    public Icon getPaletteCloseIcon() {
        return FastIcons.getPaletteCloseIcon();
    }

    @Override
    public Icon getRadioButtonIcon() {
        return FastIcons.getRadioButtonIcon();
    }

    @Override
    public Icon getCheckBoxIcon() {
        return FastIcons.getCheckBoxIcon();
    }

    @Override
    public Icon getComboBoxIcon() {
        return FastIcons.getComboBoxIcon();
    }

    @Override
    public Icon getTreeOpenIcon() {
        return FastIcons.getTreeOpenedIcon();
    }

    @Override
    public Icon getTreeCloseIcon() {
        return FastIcons.getTreeClosedIcon();
    }

    @Override
    public Icon getTreeLeafIcon() {
        return FastIcons.getTreeLeafIcon();
    }

    @Override
    public Icon getTreeCollapsedIcon() {
        return FastIcons.getTreeCollapsedIcon();
    }

    @Override
    public Icon getTreeExpandedIcon() {
        return FastIcons.getTreeExpandedIcon();
    }

    @Override
    public Icon getMenuArrowIcon() {
        return FastIcons.getMenuArrowIcon();
    }

    @Override
    public Icon getMenuCheckBoxIcon() {
        return FastIcons.getMenuCheckBoxIcon();
    }

    @Override
    public Icon getMenuRadioButtonIcon() {
        return FastIcons.getMenuRadioButtonIcon();
    }

    @Override
    public Icon getUpArrowIcon() {
        return FastIcons.getUpArrowIcon();
    }

    @Override
    public Icon getDownArrowIcon() {
        return FastIcons.getDownArrowIcon();
    }

    @Override
    public Icon getLeftArrowIcon() {
        return FastIcons.getLeftArrowIcon();
    }

    @Override
    public Icon getRightArrowIcon() {
        return FastIcons.getRightArrowIcon();
    }

    @Override
    public Icon getSplitterDownArrowIcon() {
        return FastIcons.getSplitterDownArrowIcon();
    }

    @Override
    public Icon getSplitterHorBumpIcon() {
        return FastIcons.getSplitterHorBumpIcon();
    }

    @Override
    public Icon getSplitterLeftArrowIcon() {
        return FastIcons.getSplitterLeftArrowIcon();
    }

    @Override
    public Icon getSplitterRightArrowIcon() {
        return FastIcons.getSplitterRightArrowIcon();
    }

    @Override
    public Icon getSplitterUpArrowIcon() {
        return FastIcons.getSplitterUpArrowIcon();
    }

    @Override
    public Icon getSplitterVerBumpIcon() {
        return FastIcons.getSplitterVerBumpIcon();
    }

    @Override
    public Icon getThumbHorIcon() {
        return FastIcons.getThumbHorIcon();
    }

    @Override
    public Icon getThumbVerIcon() {
        return FastIcons.getThumbVerIcon();
    }

    @Override
    public Icon getThumbHorIconRollover() {
        return FastIcons.getThumbHorIconRollover();
    }

    @Override
    public Icon getThumbVerIconRollover() {
        return FastIcons.getThumbVerIconRollover();
    }
    
} // end of class FastIconFactory
