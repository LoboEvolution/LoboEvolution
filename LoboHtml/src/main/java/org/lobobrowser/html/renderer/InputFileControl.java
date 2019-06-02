/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
package org.lobobrowser.html.renderer;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.lobobrowser.html.domimpl.HTMLBaseInputElement;

public class InputFileControl extends BaseInputControl {
	private class BrowseAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final JFileChooser chooser = new JFileChooser();
			if (chooser.showOpenDialog(InputFileControl.this) == JFileChooser.APPROVE_OPTION) {
				setFileValue(chooser.getSelectedFile());
			} else {
				setFileValue(null);
			}
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JButton browseButton = new JButton();

	private File fileValue;

	private final JTextField textField = new JTextField();

	public InputFileControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		final JButton browseButton = this.browseButton;
		browseButton.setAction(new BrowseAction());
		browseButton.setText("Browse");
		final java.awt.Dimension ps = this.textField.getPreferredSize();
		this.textField.setPreferredSize(new java.awt.Dimension(128, ps.height));
		this.textField.setEditable(false);
		this.add(this.textField);
		this.add(Box.createHorizontalStrut(4));
		this.add(browseButton);
	}

	@Override
	public File getFileValue() {
		return this.fileValue;
	}

	@Override
	public String getValue() {
		// This is the way browsers behave, even
		// though this value is not submitted.
		return this.textField.getText();
	}

	@Override
	public void resetInput() {
		setFileValue(null);
	}

	@Override
	public void setDisabled(boolean disabled) {
		this.browseButton.setEnabled(!disabled);
	}

	private void setFileValue(File file) {
		this.fileValue = file;
		if (file == null) {
			this.textField.setText("");
		} else {
			this.textField.setText(file.getAbsolutePath());
		}
	}

	@Override
	public void setValue(String value) {
		// nop - security
	}
}
