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
package com.jtattoo.plaf.luna;

import com.jtattoo.plaf.AbstractIconFactory;
import javax.swing.Icon;

/**
 * @author Michael Hagen
 */
public class LunaIconFactory implements AbstractIconFactory {

    private static LunaIconFactory instance = null;

    private LunaIconFactory() {
    }

    public static synchronized LunaIconFactory getInstance() {
        if (instance == null) {
            instance = new LunaIconFactory();
        }
        return instance;
    }

    @Override
    public Icon getOptionPaneErrorIcon() {
        return LunaIcons.getOptionPaneErrorIcon();
    }

    @Override
    public Icon getOptionPaneWarningIcon() {
        return LunaIcons.getOptionPaneWarningIcon();
    }

    @Override
    public Icon getOptionPaneInformationIcon() {
        return LunaIcons.getOptionPaneInformationIcon();
    }

    @Override
    public Icon getOptionPaneQuestionIcon() {
        return LunaIcons.getOptionPaneQuestionIcon();
    }

    @Override
    public Icon getFileChooserUpFolderIcon() {
        return LunaIcons.getFileChooserUpFolderIcon();
    }

    @Override
    public Icon getFileChooserHomeFolderIcon() {
        return LunaIcons.getFileChooserHomeFolderIcon();
    }

    @Override
    public Icon getFileChooserNewFolderIcon() {
        return LunaIcons.getFileChooserNewFolderIcon();
    }

    @Override
    public Icon getFileChooserListViewIcon() {
        return LunaIcons.getFileChooserListViewIcon();
    }

    /**
     *
     * @return
     */
    @Override
    public Icon getFileChooserDetailViewIcon() {
        return LunaIcons.getFileChooserDetailViewIcon();
    }

    @Override
    public Icon getFileViewComputerIcon() {
        return LunaIcons.getFileViewComputerIcon();
    }

    @Override
    public Icon getFileViewFloppyDriveIcon() {
        return LunaIcons.getFileViewFloppyDriveIcon();
    }

    @Override
    public Icon getFileViewHardDriveIcon() {
        return LunaIcons.getFileViewHardDriveIcon();
    }

    @Override
    public Icon getMenuIcon() {
        return LunaIcons.getMenuIcon();
    }

    @Override
    public Icon getIconIcon() {
        return LunaIcons.getIconIcon();
    }

    @Override
    public Icon getMaxIcon() {
        return LunaIcons.getMaxIcon();
    }

    @Override
    public Icon getMinIcon() {
        return LunaIcons.getMinIcon();
    }

    @Override
    public Icon getCloseIcon() {
        return LunaIcons.getCloseIcon();
    }

    @Override
    public Icon getPaletteCloseIcon() {
        return LunaIcons.getPaletteCloseIcon();
    }

    @Override
    public Icon getRadioButtonIcon() {
        return LunaIcons.getRadioButtonIcon();
    }

    @Override
    public Icon getCheckBoxIcon() {
        return LunaIcons.getCheckBoxIcon();
    }

    @Override
    public Icon getComboBoxIcon() {
        return LunaIcons.getComboBoxIcon();
    }

    @Override
    public Icon getTreeOpenIcon() {
        return LunaIcons.getTreeOpenedIcon();
    }

    @Override
    public Icon getTreeCloseIcon() {
        return LunaIcons.getTreeClosedIcon();
    }

    @Override
    public Icon getTreeLeafIcon() {
        return LunaIcons.getTreeLeafIcon();
    }

    @Override
    public Icon getTreeCollapsedIcon() {
        return LunaIcons.getTreeCollapsedIcon();
    }

    @Override
    public Icon getTreeExpandedIcon() {
        return LunaIcons.getTreeExpandedIcon();
    }

    @Override
    public Icon getMenuArrowIcon() {
        return LunaIcons.getMenuArrowIcon();
    }

    @Override
    public Icon getMenuCheckBoxIcon() {
        return LunaIcons.getMenuCheckBoxIcon();
    }

    @Override
    public Icon getMenuRadioButtonIcon() {
        return LunaIcons.getMenuRadioButtonIcon();
    }

    @Override
    public Icon getUpArrowIcon() {
        return LunaIcons.getUpArrowIcon();
    }

    @Override
    public Icon getDownArrowIcon() {
        return LunaIcons.getDownArrowIcon();
    }

    @Override
    public Icon getLeftArrowIcon() {
        return LunaIcons.getLeftArrowIcon();
    }

    @Override
    public Icon getRightArrowIcon() {
        return LunaIcons.getRightArrowIcon();
    }

    @Override
    public Icon getSplitterDownArrowIcon() {
        return LunaIcons.getSplitterDownArrowIcon();
    }

    @Override
    public Icon getSplitterHorBumpIcon() {
        return LunaIcons.getSplitterHorBumpIcon();
    }

    @Override
    public Icon getSplitterLeftArrowIcon() {
        return LunaIcons.getSplitterLeftArrowIcon();
    }

    @Override
    public Icon getSplitterRightArrowIcon() {
        return LunaIcons.getSplitterRightArrowIcon();
    }

    @Override
    public Icon getSplitterUpArrowIcon() {
        return LunaIcons.getSplitterUpArrowIcon();
    }

    @Override
    public Icon getSplitterVerBumpIcon() {
        return LunaIcons.getSplitterVerBumpIcon();
    }

    @Override
    public Icon getThumbHorIcon() {
        return LunaIcons.getThumbHorIcon();
    }

    @Override
    public Icon getThumbVerIcon() {
        return LunaIcons.getThumbVerIcon();
    }

    @Override
    public Icon getThumbHorIconRollover() {
        return LunaIcons.getThumbHorIconRollover();
    }

    @Override
    public Icon getThumbVerIconRollover() {
        return LunaIcons.getThumbVerIconRollover();
    }
    
} // end of class LunaIconFactory
