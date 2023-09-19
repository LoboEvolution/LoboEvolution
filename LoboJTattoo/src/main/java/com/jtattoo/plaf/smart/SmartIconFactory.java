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
package com.jtattoo.plaf.smart;

import javax.swing.Icon;

import com.jtattoo.plaf.AbstractIconFactory;
import com.jtattoo.plaf.BaseIcons;

/**
 * <p>SmartIconFactory class.</p>
 *
 * Author Michael Hagen
 *
 */
public final class SmartIconFactory implements AbstractIconFactory {

	private static SmartIconFactory instance = null;

	/**
	 * <p>Getter for the field instance.</p>
	 *
	 * @return a {@link com.jtattoo.plaf.smart.SmartIconFactory} object.
	 */
	public static synchronized SmartIconFactory getInstance() {
		if (instance == null) {
			instance = new SmartIconFactory();
		}
		return instance;
	}

	private SmartIconFactory() {
	}

	/** {@inheritDoc} */
	@Override
	public Icon getCheckBoxIcon() {
		return BaseIcons.getCheckBoxIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getCloseIcon() {
		return SmartIcons.getCloseIcon();
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
		return SmartIcons.getIconIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getLeftArrowIcon() {
		return BaseIcons.getLeftArrowIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getMaxIcon() {
		return SmartIcons.getMaxIcon();
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
		return SmartIcons.getMinIcon();
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
		return BaseIcons.getRadioButtonIcon();
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
		return BaseIcons.getThumbHorIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbHorIconRollover() {
		return BaseIcons.getThumbHorIconRollover();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbVerIcon() {
		return BaseIcons.getThumbVerIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getThumbVerIconRollover() {
		return BaseIcons.getThumbVerIconRollover();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getTreeCloseIcon() {
		return BaseIcons.getTreeClosedIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getTreeCollapsedIcon() {
		return BaseIcons.getTreeCollapsedIcon();
	}

	/** {@inheritDoc} */
	@Override
	public Icon getTreeExpandedIcon() {
		return BaseIcons.getTreeExpandedIcon();
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

} // end of class SmartIconFactory
