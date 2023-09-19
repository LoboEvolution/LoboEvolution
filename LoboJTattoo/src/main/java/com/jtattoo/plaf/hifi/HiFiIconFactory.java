/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package com.jtattoo.plaf.hifi;

import javax.swing.Icon;

import com.jtattoo.plaf.AbstractIconFactory;
import com.jtattoo.plaf.BaseIcons;

/**
 * <p>HiFiIconFactory class.</p>
 *
 * Author Michael Hagen
 *
 */
public final class HiFiIconFactory implements AbstractIconFactory {

	private static HiFiIconFactory instance = null;

	/**
	 * <p>Getter for the field instance.</p>
	 *
	 * @return a {@link com.jtattoo.plaf.hifi.HiFiIconFactory} object.
	 */
	public static synchronized HiFiIconFactory getInstance() {
		if (instance == null) {
			instance = new HiFiIconFactory();
		}
		return instance;
	}

	private HiFiIconFactory() {
	}

	/** {@inheritDoc} */
	@Override
	public Icon getCheckBoxIcon() {
		return HiFiIcons.getCheckBoxIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getCloseIcon() {
		return HiFiIcons.getCloseIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getComboBoxIcon() {
		return HiFiIcons.getComboBoxIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getDownArrowIcon() {
		return HiFiIcons.getDownArrowIcon();
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
		return HiFiIcons.getIconIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getLeftArrowIcon() {
		return HiFiIcons.getLeftArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getMaxIcon() {
		return HiFiIcons.getMaxIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getMenuArrowIcon() {
		return HiFiIcons.getMenuArrowIcon();
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
		return HiFiIcons.getMinIcon();
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
		return HiFiIcons.getRadioButtonIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getRightArrowIcon() {
		return HiFiIcons.getRightArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getSplitterDownArrowIcon() {
		return HiFiIcons.getSplitterDownArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getSplitterHorBumpIcon() {
		return HiFiIcons.getSplitterHorBumpIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getSplitterLeftArrowIcon() {
		return HiFiIcons.getSplitterLeftArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getSplitterRightArrowIcon() {
		return HiFiIcons.getSplitterRightArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getSplitterUpArrowIcon() {
		return HiFiIcons.getSplitterUpArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getSplitterVerBumpIcon() {
		return HiFiIcons.getSplitterVerBumpIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbHorIcon() {
		return HiFiIcons.getThumbHorIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbHorIconRollover() {
		return HiFiIcons.getThumbHorIconRollover();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbVerIcon() {
		return HiFiIcons.getThumbVerIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbVerIconRollover() {
		return HiFiIcons.getThumbVerIconRollover();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getTreeCloseIcon() {
		return BaseIcons.getTreeClosedIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getTreeCollapsedIcon() {
		return HiFiIcons.getTreeCollapsedIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getTreeExpandedIcon() {
		return HiFiIcons.getTreeExpandedIcon();
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
		return HiFiIcons.getUpArrowIcon();
	}

} // end of class HiFiIconFactory
