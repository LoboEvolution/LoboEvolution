/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.html.control;

import java.awt.ComponentOrientation;
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

	private static final long serialVersionUID = 1L;
	private final JTextField textField = new JTextField();
	private final JButton browseButton = new JButton();
	private File[] filesValue;

	public InputFileControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JButton browseButton = this.browseButton;
		browseButton.setAction(new BrowseAction());
		browseButton.setText("Browse");
		java.awt.Dimension ps = this.textField.getPreferredSize();
		this.textField.setPreferredSize(new java.awt.Dimension(128, ps.height));
		this.textField.setEditable(false);
		if(modelNode.getTitle() != null)
			this.textField.setToolTipText(modelNode.getTitle());
		textField.setVisible(modelNode.getHidden());
		textField.applyComponentOrientation(direction(modelNode.getDir()));
		textField.setEditable(new Boolean(modelNode.getContentEditable() == null ? "true" : modelNode.getContentEditable()));
		textField.setEnabled(!modelNode.getDisabled());
		this.add(this.textField);
		this.add(Box.createHorizontalStrut(4));
		this.add(browseButton);
	}

	public String getValue() {
		// This is the way browsers behave, even
		// though this value is not submitted.
		return this.textField.getText();
	}

	public void setDisabled(boolean disabled) {
		this.browseButton.setEnabled(!disabled);
	}

	public void setValue(String value) {
		// nop - security
	}

	private void setFileValue(File[] files) {
		this.filesValue = files;
		if (files == null) {
			this.textField.setText("");
		} else {
			String paths = "";
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				paths += paths + ";" + f.getAbsolutePath();
			}

			this.textField.setText(paths);
		}
	}

	public File[] getFileValue() {
		return this.filesValue;
	}

	public void resetInput() {
		this.setFileValue(null);
	}

	private class BrowseAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

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
