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
package com.jtattoo.plaf.texture;

import com.jtattoo.plaf.AbstractIconFactory;
import javax.swing.Icon;

/**
 * @author Michael Hagen
 */
public class TextureIconFactory implements AbstractIconFactory {

    private static TextureIconFactory instance = null;

    private TextureIconFactory() {
    }

    public static synchronized TextureIconFactory getInstance() {
        if (instance == null) {
            instance = new TextureIconFactory();
        }
        return instance;
    }

    @Override
    public Icon getOptionPaneErrorIcon() {
        return TextureIcons.getOptionPaneErrorIcon();
    }

    @Override
    public Icon getOptionPaneWarningIcon() {
        return TextureIcons.getOptionPaneWarningIcon();
    }

    @Override
    public Icon getOptionPaneInformationIcon() {
        return TextureIcons.getOptionPaneInformationIcon();
    }

    @Override
    public Icon getOptionPaneQuestionIcon() {
        return TextureIcons.getOptionPaneQuestionIcon();
    }

    @Override
    public Icon getFileChooserUpFolderIcon() {
        return TextureIcons.getFileChooserUpFolderIcon();
    }

    @Override
    public Icon getFileChooserHomeFolderIcon() {
        return TextureIcons.getFileChooserHomeFolderIcon();
    }

    @Override
    public Icon getFileChooserNewFolderIcon() {
        return TextureIcons.getFileChooserNewFolderIcon();
    }

    @Override
    public Icon getFileChooserListViewIcon() {
        return TextureIcons.getFileChooserListViewIcon();
    }

    @Override
    public Icon getFileChooserDetailViewIcon() {
        return TextureIcons.getFileChooserDetailViewIcon();
    }

    @Override
    public Icon getFileViewComputerIcon() {
        return TextureIcons.getFileViewComputerIcon();
    }

    @Override
    public Icon getFileViewFloppyDriveIcon() {
        return TextureIcons.getFileViewFloppyDriveIcon();
    }

    @Override
    public Icon getFileViewHardDriveIcon() {
        return TextureIcons.getFileViewHardDriveIcon();
    }

    @Override
    public Icon getMenuIcon() {
        return TextureIcons.getMenuIcon();
    }

    @Override
    public Icon getIconIcon() {
        return TextureIcons.getIconIcon();
    }

    @Override
    public Icon getMaxIcon() {
        return TextureIcons.getMaxIcon();
    }

    @Override
    public Icon getMinIcon() {
        return TextureIcons.getMinIcon();
    }

    @Override
    public Icon getCloseIcon() {
        return TextureIcons.getCloseIcon();
    }

    @Override
    public Icon getPaletteCloseIcon() {
        return TextureIcons.getPaletteCloseIcon();
    }

    @Override
    public Icon getRadioButtonIcon() {
        return TextureIcons.getRadioButtonIcon();
    }

    @Override
    public Icon getCheckBoxIcon() {
        return TextureIcons.getCheckBoxIcon();
    }

    @Override
    public Icon getComboBoxIcon() {
        return TextureIcons.getComboBoxIcon();
    }

    @Override
    public Icon getTreeOpenIcon() {
        return TextureIcons.getTreeOpenedIcon();
    }

    @Override
    public Icon getTreeCloseIcon() {
        return TextureIcons.getTreeClosedIcon();
    }

    @Override
    public Icon getTreeLeafIcon() {
        return TextureIcons.getTreeLeafIcon();
    }

    @Override
    public Icon getTreeCollapsedIcon() {
        return TextureIcons.getTreeCollapsedIcon();
    }

    @Override
    public Icon getTreeExpandedIcon() {
        return TextureIcons.getTreeExpandedIcon();
    }

    @Override
    public Icon getMenuArrowIcon() {
        return TextureIcons.getMenuArrowIcon();
    }

    @Override
    public Icon getMenuCheckBoxIcon() {
        return TextureIcons.getMenuCheckBoxIcon();
    }

    @Override
    public Icon getMenuRadioButtonIcon() {
        return TextureIcons.getMenuRadioButtonIcon();
    }

    @Override
    public Icon getUpArrowIcon() {
        return TextureIcons.getUpArrowIcon();
    }

    @Override
    public Icon getDownArrowIcon() {
        return TextureIcons.getDownArrowIcon();
    }

    @Override
    public Icon getLeftArrowIcon() {
        return TextureIcons.getLeftArrowIcon();
    }

    @Override
    public Icon getRightArrowIcon() {
        return TextureIcons.getRightArrowIcon();
    }

    @Override
    public Icon getSplitterDownArrowIcon() {
        return TextureIcons.getSplitterDownArrowIcon();
    }

    @Override
    public Icon getSplitterHorBumpIcon() {
        return TextureIcons.getSplitterHorBumpIcon();
    }

    @Override
    public Icon getSplitterLeftArrowIcon() {
        return TextureIcons.getSplitterLeftArrowIcon();
    }

    @Override
    public Icon getSplitterRightArrowIcon() {
        return TextureIcons.getSplitterRightArrowIcon();
    }

    @Override
    public Icon getSplitterUpArrowIcon() {
        return TextureIcons.getSplitterUpArrowIcon();
    }

    @Override
    public Icon getSplitterVerBumpIcon() {
        return TextureIcons.getSplitterVerBumpIcon();
    }

    @Override
    public Icon getThumbHorIcon() {
        return TextureIcons.getThumbHorIcon();
    }

    @Override
    public Icon getThumbVerIcon() {
        return TextureIcons.getThumbVerIcon();
    }

    @Override
    public Icon getThumbHorIconRollover() {
        return TextureIcons.getThumbHorIconRollover();
    }

    @Override
    public Icon getThumbVerIconRollover() {
        return TextureIcons.getThumbVerIconRollover();
    }
    
} // end of class TextureIconFactory
