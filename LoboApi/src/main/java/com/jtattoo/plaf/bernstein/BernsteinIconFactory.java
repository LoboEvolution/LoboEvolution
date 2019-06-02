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
package com.jtattoo.plaf.bernstein;

import com.jtattoo.plaf.AbstractIconFactory;
import javax.swing.Icon;

/**
 * @author Michael Hagen
 */
public class BernsteinIconFactory implements AbstractIconFactory {

    private static BernsteinIconFactory instance = null;

    private BernsteinIconFactory() {
    }

    public static synchronized BernsteinIconFactory getInstance() {
        if (instance == null) {
            instance = new BernsteinIconFactory();
        }
        return instance;
    }

    @Override
    public Icon getOptionPaneErrorIcon() {
        return BernsteinIcons.getOptionPaneErrorIcon();
    }

    @Override
    public Icon getOptionPaneWarningIcon() {
        return BernsteinIcons.getOptionPaneWarningIcon();
    }

    @Override
    public Icon getOptionPaneInformationIcon() {
        return BernsteinIcons.getOptionPaneInformationIcon();
    }

    @Override
    public Icon getOptionPaneQuestionIcon() {
        return BernsteinIcons.getOptionPaneQuestionIcon();
    }

    @Override
    public Icon getFileChooserUpFolderIcon() {
        return BernsteinIcons.getFileChooserUpFolderIcon();
    }

    @Override
    public Icon getFileChooserHomeFolderIcon() {
        return BernsteinIcons.getFileChooserHomeFolderIcon();
    }

    @Override
    public Icon getFileChooserNewFolderIcon() {
        return BernsteinIcons.getFileChooserNewFolderIcon();
    }

    @Override
    public Icon getFileChooserListViewIcon() {
        return BernsteinIcons.getFileChooserListViewIcon();
    }

    @Override
    public Icon getFileChooserDetailViewIcon() {
        return BernsteinIcons.getFileChooserDetailViewIcon();
    }

    public Icon getFileViewComputerIcon() {
        return BernsteinIcons.getFileViewComputerIcon();
    }

    @Override
    public Icon getFileViewFloppyDriveIcon() {
        return BernsteinIcons.getFileViewFloppyDriveIcon();
    }

    @Override
    public Icon getFileViewHardDriveIcon() {
        return BernsteinIcons.getFileViewHardDriveIcon();
    }

    @Override
    public Icon getMenuIcon() {
        return BernsteinIcons.getMenuIcon();
    }

    @Override
    public Icon getIconIcon() {
        return BernsteinIcons.getIconIcon();
    }

    @Override
    public Icon getMaxIcon() {
        return BernsteinIcons.getMaxIcon();
    }

    @Override
    public Icon getMinIcon() {
        return BernsteinIcons.getMinIcon();
    }

    @Override
    public Icon getCloseIcon() {
        return BernsteinIcons.getCloseIcon();
    }

    @Override
    public Icon getPaletteCloseIcon() {
        return BernsteinIcons.getPaletteCloseIcon();
    }

    @Override
    public Icon getRadioButtonIcon() {
        return BernsteinIcons.getRadioButtonIcon();
    }

    @Override
    public Icon getCheckBoxIcon() {
        return BernsteinIcons.getCheckBoxIcon();
    }

    @Override
    public Icon getComboBoxIcon() {
        return BernsteinIcons.getComboBoxIcon();
    }

    @Override
    public Icon getTreeOpenIcon() {
        return BernsteinIcons.getTreeOpenedIcon();
    }

    @Override
    public Icon getTreeCloseIcon() {
        return BernsteinIcons.getTreeClosedIcon();
    }

    @Override
    public Icon getTreeLeafIcon() {
        return BernsteinIcons.getTreeLeafIcon();
    }

    @Override
    public Icon getTreeCollapsedIcon() {
        return BernsteinIcons.getTreeCollapsedIcon();
    }

    @Override
    public Icon getTreeExpandedIcon() {
        return BernsteinIcons.getTreeExpandedIcon();
    }

    @Override
    public Icon getMenuArrowIcon() {
        return BernsteinIcons.getMenuArrowIcon();
    }

    @Override
    public Icon getMenuCheckBoxIcon() {
        return BernsteinIcons.getMenuCheckBoxIcon();
    }

    @Override
    public Icon getMenuRadioButtonIcon() {
        return BernsteinIcons.getMenuRadioButtonIcon();
    }

    public Icon getUpArrowIcon() {
        return BernsteinIcons.getUpArrowIcon();
    }

    @Override
    public Icon getDownArrowIcon() {
        return BernsteinIcons.getDownArrowIcon();
    }

    @Override
    public Icon getLeftArrowIcon() {
        return BernsteinIcons.getLeftArrowIcon();
    }

    @Override
    public Icon getRightArrowIcon() {
        return BernsteinIcons.getRightArrowIcon();
    }

    @Override
    public Icon getSplitterDownArrowIcon() {
        return BernsteinIcons.getSplitterDownArrowIcon();
    }

    @Override
    public Icon getSplitterHorBumpIcon() {
        return BernsteinIcons.getSplitterHorBumpIcon();
    }

    @Override
    public Icon getSplitterLeftArrowIcon() {
        return BernsteinIcons.getSplitterLeftArrowIcon();
    }

    @Override
    public Icon getSplitterRightArrowIcon() {
        return BernsteinIcons.getSplitterRightArrowIcon();
    }

    @Override
    public Icon getSplitterUpArrowIcon() {
        return BernsteinIcons.getSplitterUpArrowIcon();
    }

    @Override
    public Icon getSplitterVerBumpIcon() {
        return BernsteinIcons.getSplitterVerBumpIcon();
    }

    @Override
    public Icon getThumbHorIcon() {
        return BernsteinIcons.getThumbHorIcon();
    }

    @Override
    public Icon getThumbVerIcon() {
        return BernsteinIcons.getThumbVerIcon();
    }

    @Override
    public Icon getThumbHorIconRollover() {
        return BernsteinIcons.getThumbHorIconRollover();
    }

    @Override
    public Icon getThumbVerIconRollover() {
        return BernsteinIcons.getThumbVerIconRollover();
    }
    
} // end of class BernsteinIconFactory
