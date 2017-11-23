/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.control;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.lobobrowser.html.domimpl.HTMLBaseInputElement;

/**
 * The Class InputFileControl.
 */
public class InputFileControl extends BaseInputControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The text field. */
	private final JTextField textField = new JTextField();

	/** The browse button. */
	private final JButton browseButton = new JButton();

	/** The files value. */
	private File[] filesValue;

	/**
	 * Instantiates a new input file control.
	 *
	 * @param modelNode
	 *            the model node
	 */
	public InputFileControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JButton browseButton = this.browseButton;
		browseButton.setAction(new BrowseAction());
		browseButton.setText("Browse");
		Dimension ps = this.textField.getPreferredSize();
		this.textField.setPreferredSize(new Dimension(128, ps.height));
		this.textField.setEditable(false);
		if (modelNode.getTitle() != null) {
			this.textField.setToolTipText(modelNode.getTitle());
		}
		textField.setVisible(modelNode.getHidden());
		textField.applyComponentOrientation(direction(modelNode.getDir()));
		textField.setEditable(Boolean.valueOf(modelNode.getContentEditable() == null ? "true" : modelNode.getContentEditable()));
		textField.setEnabled(!modelNode.getDisabled());
		this.add(this.textField);
		this.add(Box.createHorizontalStrut(4));
		this.add(browseButton);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.control.BaseInputControl#getValue()
	 */
	@Override
	public String getValue() {
		// This is the way browsers behave, even
		// though this value is not submitted.
		return this.textField.getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.control.BaseInputControl#setDisabled(boolean)
	 */
	@Override
	public void setDisabled(boolean disabled) {
		this.browseButton.setEnabled(!disabled);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.control.BaseInputControl#setValue(java.lang.String)
	 */
	@Override
	public void setValue(String value) {
		// nop - security
	}

	/**
	 * Sets the file value.
	 *
	 * @param files
	 *            the new file value
	 */
	private void setFileValue(File[] files) {
		this.filesValue = files;
		if (files == null) {
			this.textField.setText("");
		} else {
			String paths = "";
			for (File f : files) {
				paths += paths + ";" + f.getAbsolutePath();
			}

			this.textField.setText(paths);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.control.BaseInputControl#getFileValue()
	 */
	@Override
	public File[] getFileValue() {
		return this.filesValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#resetInput()
	 */
	@Override
	public void resetInput() {
		this.setFileValue(null);
	}

	/**
	 * The Class BrowseAction.
	 */
	private class BrowseAction extends AbstractAction {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(true);
			if (chooser.showOpenDialog(InputFileControl.this) == JFileChooser.APPROVE_OPTION) {
				setFileValue(chooser.getSelectedFiles());
			} else {
				setFileValue(null);
			}
		}
	}

	/**
	 * Direction.
	 *
	 * @param dir
	 *            the dir
	 * @return the component orientation
	 */
	private ComponentOrientation direction(String dir) {

		if ("ltr".equalsIgnoreCase(dir)) {
			return ComponentOrientation.LEFT_TO_RIGHT;
		} else if ("rtl".equalsIgnoreCase(dir)) {
			return ComponentOrientation.RIGHT_TO_LEFT;
		} else {
			return ComponentOrientation.UNKNOWN;
		}
	}
}
