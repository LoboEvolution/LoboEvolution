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

import javax.swing.Icon;

import com.jtattoo.plaf.AbstractIconFactory;
import com.jtattoo.plaf.BaseIcons;
import com.jtattoo.plaf.hifi.HiFiIcons;

/**
 * @author Michael Hagen
 */
public class NoireIconFactory implements AbstractIconFactory {

	private static NoireIconFactory instance = null;

	public static synchronized NoireIconFactory getInstance() {
		if (instance == null) {
			instance = new NoireIconFactory();
		}
		return instance;
	}

	private NoireIconFactory() {
	}

	@Override
	public Icon getCheckBoxIcon() {
		return HiFiIcons.getCheckBoxIcon();
	}

	@Override
	public Icon getCloseIcon() {
		return HiFiIcons.getCloseIcon();
	}

	@Override
	public Icon getComboBoxIcon() {
		return HiFiIcons.getComboBoxIcon();
	}

	@Override
	public Icon getDownArrowIcon() {
		return NoireIcons.getDownArrowIcon();
	}

	@Override
	public Icon getFileChooserDetailViewIcon() {
		return BaseIcons.getFileChooserDetailViewIcon();
	}

	@Override
	public Icon getFileChooserHomeFolderIcon() {
		return BaseIcons.getFileChooserHomeFolderIcon();
	}

	@Override
	public Icon getFileChooserListViewIcon() {
		return BaseIcons.getFileChooserListViewIcon();
	}

	@Override
	public Icon getFileChooserNewFolderIcon() {
		return BaseIcons.getFileChooserNewFolderIcon();
	}

	@Override
	public Icon getFileChooserUpFolderIcon() {
		return BaseIcons.getFileChooserUpFolderIcon();
	}

	@Override
	public Icon getFileViewComputerIcon() {
		return BaseIcons.getFileViewComputerIcon();
	}

	@Override
	public Icon getFileViewFloppyDriveIcon() {
		return BaseIcons.getFileViewFloppyDriveIcon();
	}

	@Override
	public Icon getFileViewHardDriveIcon() {
		return BaseIcons.getFileViewHardDriveIcon();
	}

	@Override
	public Icon getIconIcon() {
		return HiFiIcons.getIconIcon();
	}

	@Override
	public Icon getLeftArrowIcon() {
		return NoireIcons.getLeftArrowIcon();
	}

	@Override
	public Icon getMaxIcon() {
		return HiFiIcons.getMaxIcon();
	}

	@Override
	public Icon getMenuArrowIcon() {
		return HiFiIcons.getMenuArrowIcon();
	}

	@Override
	public Icon getMenuCheckBoxIcon() {
		return BaseIcons.getMenuCheckBoxIcon();
	}

	@Override
	public Icon getMenuIcon() {
		return BaseIcons.getMenuIcon();
	}

	@Override
	public Icon getMenuRadioButtonIcon() {
		return BaseIcons.getMenuRadioButtonIcon();
	}

	@Override
	public Icon getMinIcon() {
		return HiFiIcons.getMinIcon();
	}

	@Override
	public Icon getOptionPaneErrorIcon() {
		return BaseIcons.getOptionPaneErrorIcon();
	}

	@Override
	public Icon getOptionPaneInformationIcon() {
		return BaseIcons.getOptionPaneInformationIcon();
	}

	@Override
	public Icon getOptionPaneQuestionIcon() {
		return BaseIcons.getOptionPaneQuestionIcon();
	}

	@Override
	public Icon getOptionPaneWarningIcon() {
		return BaseIcons.getOptionPaneWarningIcon();
	}

	@Override
	public Icon getPaletteCloseIcon() {
		return BaseIcons.getPaletteCloseIcon();
	}

	@Override
	public Icon getRadioButtonIcon() {
		return HiFiIcons.getRadioButtonIcon();
	}

	@Override
	public Icon getRightArrowIcon() {
		return NoireIcons.getRightArrowIcon();
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
	public Icon getThumbHorIconRollover() {
		return HiFiIcons.getThumbHorIconRollover();
	}

	@Override
	public Icon getThumbVerIcon() {
		return HiFiIcons.getThumbVerIcon();
	}

	@Override
	public Icon getThumbVerIconRollover() {
		return HiFiIcons.getThumbVerIconRollover();
	}

	@Override
	public Icon getTreeCloseIcon() {
		return BaseIcons.getTreeClosedIcon();
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
	public Icon getTreeLeafIcon() {
		return BaseIcons.getTreeLeafIcon();
	}

	@Override
	public Icon getTreeOpenIcon() {
		return BaseIcons.getTreeOpenedIcon();
	}

	@Override
	public Icon getUpArrowIcon() {
		return NoireIcons.getUpArrowIcon();
	}

} // end of class NoireIconFactory
