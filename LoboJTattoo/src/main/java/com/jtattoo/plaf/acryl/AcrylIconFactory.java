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

package com.jtattoo.plaf.acryl;

import javax.swing.Icon;

import com.jtattoo.plaf.AbstractIconFactory;
import com.jtattoo.plaf.BaseIcons;

/**
 * <p>AcrylIconFactory class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AcrylIconFactory implements AbstractIconFactory {

	private static AcrylIconFactory instance = null;

	/**
	 * <p>Getter for the field instance.</p>
	 *
	 * @return a {@link com.jtattoo.plaf.acryl.AcrylIconFactory} object.
	 */
	public static synchronized AcrylIconFactory getInstance() {
		if (instance == null) {
			instance = new AcrylIconFactory();
		}
		return instance;
	}

	private AcrylIconFactory() {
	}

	/** {@inheritDoc} */
	@Override
	public Icon getCheckBoxIcon() {
		return AcrylIcons.getCheckBoxIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getCloseIcon() {
		return AcrylIcons.getCloseIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getComboBoxIcon() {
		return BaseIcons.getComboBoxIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getDownArrowIcon() {
		return BaseIcons.getDownArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getFileChooserDetailViewIcon() {
		return BaseIcons.getFileChooserDetailViewIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getFileChooserHomeFolderIcon() {
		return BaseIcons.getFileChooserHomeFolderIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getFileChooserListViewIcon() {
		return BaseIcons.getFileChooserListViewIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getFileChooserNewFolderIcon() {
		return BaseIcons.getFileChooserNewFolderIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getFileChooserUpFolderIcon() {
		return BaseIcons.getFileChooserUpFolderIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getFileViewComputerIcon() {
		return BaseIcons.getFileViewComputerIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getFileViewFloppyDriveIcon() {
		return BaseIcons.getFileViewFloppyDriveIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getFileViewHardDriveIcon() {
		return BaseIcons.getFileViewHardDriveIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getIconIcon() {
		return AcrylIcons.getIconIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getLeftArrowIcon() {
		return BaseIcons.getLeftArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getMaxIcon() {
		return AcrylIcons.getMaxIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getMenuArrowIcon() {
		return BaseIcons.getMenuArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getMenuCheckBoxIcon() {
		return BaseIcons.getMenuCheckBoxIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getMenuIcon() {
		return BaseIcons.getMenuIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getMenuRadioButtonIcon() {
		return BaseIcons.getMenuRadioButtonIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getMinIcon() {
		return AcrylIcons.getMinIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getOptionPaneErrorIcon() {
		return BaseIcons.getOptionPaneErrorIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getOptionPaneInformationIcon() {
		return BaseIcons.getOptionPaneInformationIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getOptionPaneQuestionIcon() {
		return BaseIcons.getOptionPaneQuestionIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getOptionPaneWarningIcon() {
		return BaseIcons.getOptionPaneWarningIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getPaletteCloseIcon() {
		return BaseIcons.getPaletteCloseIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getRadioButtonIcon() {
		return AcrylIcons.getRadioButtonIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getRightArrowIcon() {
		return BaseIcons.getRightArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getSplitterDownArrowIcon() {
		return BaseIcons.getSplitterDownArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getSplitterHorBumpIcon() {
		return BaseIcons.getSplitterHorBumpIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getSplitterLeftArrowIcon() {
		return BaseIcons.getSplitterLeftArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getSplitterRightArrowIcon() {
		return BaseIcons.getSplitterRightArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getSplitterUpArrowIcon() {
		return BaseIcons.getSplitterUpArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getSplitterVerBumpIcon() {
		return BaseIcons.getSplitterVerBumpIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbHorIcon() {
		return AcrylIcons.getThumbHorIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbHorIconRollover() {
		return AcrylIcons.getThumbHorIconRollover();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbVerIcon() {
		return AcrylIcons.getThumbVerIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbVerIconRollover() {
		return AcrylIcons.getThumbVerIconRollover();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getTreeCloseIcon() {
		return BaseIcons.getTreeClosedIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getTreeCollapsedIcon() {
		return AcrylIcons.getTreeCollapsedIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getTreeExpandedIcon() {
		return AcrylIcons.getTreeExpandedIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getTreeLeafIcon() {
		return BaseIcons.getTreeLeafIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getTreeOpenIcon() {
		return BaseIcons.getTreeOpenedIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getUpArrowIcon() {
		return BaseIcons.getUpArrowIcon();
	}

} // end of class AcrylIconFactory
