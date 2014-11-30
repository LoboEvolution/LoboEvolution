/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.ext;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/*
 * Created on Jun 6, 2005
 */
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author J. H. S.
 */
public class TextFieldComboBoxEditor implements ComboBoxEditor {
	private final JTextField textField;
	private boolean inNotification = false;

	public TextFieldComboBoxEditor() {
		this.textField = new JTextField();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ComboBoxEditor#getEditorComponent()
	 */
	public Component getEditorComponent() {
		return this.textField;
	}

	// private Object item;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ComboBoxEditor#setItem(java.lang.Object)
	 */
	public void setItem(Object arg0) {
		// this.item = arg0;
		if (!this.inNotification) {
			this.textField.setText(arg0 == null ? "" : String.valueOf(arg0));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ComboBoxEditor#getItem()
	 */
	public Object getItem() {
		return this.textField.getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ComboBoxEditor#selectAll()
	 */
	public void selectAll() {
		this.textField.selectAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.ComboBoxEditor#addActionListener(java.awt.event.ActionListener
	 * )
	 */
	public void addActionListener(ActionListener arg0) {
		this.textField.addActionListener(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.ComboBoxEditor#removeActionListener(java.awt.event.ActionListener
	 * )
	 */
	public void removeActionListener(ActionListener arg0) {
		this.textField.removeActionListener(arg0);
	}

	public void addKeyListener(java.awt.event.KeyListener listener) {
		this.textField.addKeyListener(listener);
	}

	public void removeKeyListener(java.awt.event.KeyListener listener) {
		this.textField.removeKeyListener(listener);
	}

	public void addChangeListener(final ChangeListener listener) {
		this.textField.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {
						TextFieldComboBoxEditor.this.inNotification = true;
						try {
							listener.stateChanged(new ChangeEvent(
									TextFieldComboBoxEditor.this));
						} finally {
							TextFieldComboBoxEditor.this.inNotification = false;
						}
					}

					public void insertUpdate(DocumentEvent e) {
						TextFieldComboBoxEditor.this.inNotification = true;
						try {
							listener.stateChanged(new ChangeEvent(
									TextFieldComboBoxEditor.this));
						} finally {
							TextFieldComboBoxEditor.this.inNotification = false;
						}
					}

					public void removeUpdate(DocumentEvent e) {
						TextFieldComboBoxEditor.this.inNotification = true;
						try {
							listener.stateChanged(new ChangeEvent(
									TextFieldComboBoxEditor.this));
						} finally {
							TextFieldComboBoxEditor.this.inNotification = false;
						}
					}
				});
	}

}
