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
package com.jtattoo.plaf.graphite;

import com.jtattoo.plaf.AbstractIconFactory;
import javax.swing.Icon;

/**
 * @author Michael Hagen
 */
public class GraphiteIconFactory implements AbstractIconFactory {

    private static GraphiteIconFactory instance = null;

    private GraphiteIconFactory() {
    }

    public static synchronized GraphiteIconFactory getInstance() {
        if (instance == null) {
            instance = new GraphiteIconFactory();
        }
        return instance;
    }

    @Override
    public Icon getOptionPaneErrorIcon() {
        return GraphiteIcons.getOptionPaneErrorIcon();
    }

    @Override
    public Icon getOptionPaneWarningIcon() {
        return GraphiteIcons.getOptionPaneWarningIcon();
    }

    @Override
    public Icon getOptionPaneInformationIcon() {
        return GraphiteIcons.getOptionPaneInformationIcon();
    }

    @Override
    public Icon getOptionPaneQuestionIcon() {
        return GraphiteIcons.getOptionPaneQuestionIcon();
    }

    @Override
    public Icon getFileChooserUpFolderIcon() {
        return GraphiteIcons.getFileChooserUpFolderIcon();
    }

    @Override
    public Icon getFileChooserHomeFolderIcon() {
        return GraphiteIcons.getFileChooserHomeFolderIcon();
    }

    @Override
    public Icon getFileChooserNewFolderIcon() {
        return GraphiteIcons.getFileChooserNewFolderIcon();
    }

    @Override
    public Icon getFileChooserListViewIcon() {
        return GraphiteIcons.getFileChooserListViewIcon();
    }

    @Override
    public Icon getFileChooserDetailViewIcon() {
        return GraphiteIcons.getFileChooserDetailViewIcon();
    }

    @Override
    public Icon getFileViewComputerIcon() {
        return GraphiteIcons.getFileViewComputerIcon();
    }

    @Override
    public Icon getFileViewFloppyDriveIcon() {
        return GraphiteIcons.getFileViewFloppyDriveIcon();
    }

    @Override
    public Icon getFileViewHardDriveIcon() {
        return GraphiteIcons.getFileViewHardDriveIcon();
    }

    @Override
    public Icon getMenuIcon() {
        return GraphiteIcons.getMenuIcon();
    }

    @Override
    public Icon getIconIcon() {
        return GraphiteIcons.getIconIcon();
    }

    @Override
    public Icon getMaxIcon() {
        return GraphiteIcons.getMaxIcon();
    }

    @Override
    public Icon getMinIcon() {
        return GraphiteIcons.getMinIcon();
    }

    @Override
    public Icon getCloseIcon() {
        return GraphiteIcons.getCloseIcon();
    }

    @Override
    public Icon getPaletteCloseIcon() {
        return GraphiteIcons.getPaletteCloseIcon();
    }

    @Override
    public Icon getRadioButtonIcon() {
        return GraphiteIcons.getRadioButtonIcon();
    }

    @Override
    public Icon getCheckBoxIcon() {
        return GraphiteIcons.getCheckBoxIcon();
    }

    @Override
    public Icon getComboBoxIcon() {
        return GraphiteIcons.getComboBoxIcon();
    }

    @Override
    public Icon getTreeOpenIcon() {
        return GraphiteIcons.getTreeOpenedIcon();
    }

    @Override
    public Icon getTreeCloseIcon() {
        return GraphiteIcons.getTreeClosedIcon();
    }

    @Override
    public Icon getTreeLeafIcon() {
        return GraphiteIcons.getTreeLeafIcon();
    }

    @Override
    public Icon getTreeCollapsedIcon() {
        return GraphiteIcons.getTreeCollapsedIcon();
    }

    @Override
    public Icon getTreeExpandedIcon() {
        return GraphiteIcons.getTreeExpandedIcon();
    }

    @Override
    public Icon getMenuArrowIcon() {
        return GraphiteIcons.getMenuArrowIcon();
    }

    @Override
    public Icon getMenuCheckBoxIcon() {
        return GraphiteIcons.getMenuCheckBoxIcon();
    }

    @Override
    public Icon getMenuRadioButtonIcon() {
        return GraphiteIcons.getMenuRadioButtonIcon();
    }

    @Override
    public Icon getUpArrowIcon() {
        return GraphiteIcons.getUpArrowIcon();
    }

    @Override
    public Icon getDownArrowIcon() {
        return GraphiteIcons.getDownArrowIcon();
    }

    @Override
    public Icon getLeftArrowIcon() {
        return GraphiteIcons.getLeftArrowIcon();
    }

    @Override
    public Icon getRightArrowIcon() {
        return GraphiteIcons.getRightArrowIcon();
    }

    @Override
    public Icon getSplitterDownArrowIcon() {
        return GraphiteIcons.getSplitterDownArrowIcon();
    }

    @Override
    public Icon getSplitterHorBumpIcon() {
        return GraphiteIcons.getSplitterHorBumpIcon();
    }

    @Override
    public Icon getSplitterLeftArrowIcon() {
        return GraphiteIcons.getSplitterLeftArrowIcon();
    }

    @Override
    public Icon getSplitterRightArrowIcon() {
        return GraphiteIcons.getSplitterRightArrowIcon();
    }

    @Override
    public Icon getSplitterUpArrowIcon() {
        return GraphiteIcons.getSplitterUpArrowIcon();
    }

    @Override
    public Icon getSplitterVerBumpIcon() {
        return GraphiteIcons.getSplitterVerBumpIcon();
    }

    @Override
    public Icon getThumbHorIcon() {
        return GraphiteIcons.getThumbHorIcon();
    }

    @Override
    public Icon getThumbVerIcon() {
        return GraphiteIcons.getThumbVerIcon();
    }

    @Override
    public Icon getThumbHorIconRollover() {
        return GraphiteIcons.getThumbHorIconRollover();
    }

    @Override
    public Icon getThumbVerIconRollover() {
        return GraphiteIcons.getThumbVerIconRollover();
    }
    
} // end of class GraphiteIconFactory
