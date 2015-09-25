/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.gui;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 * The Class FormField.
 */
public class FormField {

	/** The type. */
	public final FieldType type;

	/**
	 * Instantiates a new form field.
	 *
	 * @param type
	 *            the type
	 */
	public FormField(final FieldType type) {
		this(FieldType.TEXT, "");
	}

	/**
	 * Instantiates a new form field.
	 *
	 * @param type
	 *            the type
	 * @param caption
	 *            the caption
	 */
	public FormField(final FieldType type, String caption) {
		this(type, caption, true);
	}

	/**
	 * Instantiates a new form field.
	 *
	 * @param type
	 *            the type
	 * @param caption
	 *            the caption
	 * @param editable
	 *            the editable
	 */
	public FormField(final FieldType type, final String caption, final boolean editable) {
		this.type = type;
		this.setCaption(caption);
		this.setEditable(editable);
	}

	/**
	 * Instantiates a new form field.
	 *
	 * @param type
	 *            the type
	 * @param editable
	 *            the editable
	 */
	public FormField(final FieldType type, final boolean editable) {
		this.type = type;
		this.setEditable(editable);
	}

	/**
	 * Gets the caption.
	 *
	 * @return the caption
	 */
	public String getCaption() {
		return this.getLabel().getText();
	}

	/**
	 * Checks if is editable.
	 *
	 * @return true, if is editable
	 */
	public boolean isEditable() {
		JComponent fe = this.getFieldEditor();
		if (fe instanceof JTextComponent) {
			return ((JTextComponent) fe).isEditable();
		} else {
			return false;
		}
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public FieldType getType() {
		return type;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		JComponent fe = this.getFieldEditor();
		if (fe instanceof JTextComponent) {
			return ((JTextComponent) fe).getText();
		} else {
			return null;
		}
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(String value) {
		JComponent fe = this.getFieldEditor();
		if (fe instanceof JTextComponent) {
			((JTextComponent) fe).setText(value);
		}
	}

	/**
	 * Gets the tool tip.
	 *
	 * @return the tool tip
	 */
	public String getToolTip() {
		return this.getFieldEditor().getToolTipText();
	}

	/**
	 * Sets the tool tip.
	 *
	 * @param tooltip
	 *            the new tool tip
	 */
	public void setToolTip(String tooltip) {
		this.getFieldEditor().setToolTipText(tooltip);
	}

	/**
	 * Sets the editable.
	 *
	 * @param editable
	 *            the new editable
	 */
	public void setEditable(boolean editable) {
		JComponent fe = this.getFieldEditor();
		if (fe instanceof JTextComponent) {
			((JTextComponent) fe).setEditable(editable);
		}
	}

	/**
	 * Sets the caption.
	 *
	 * @param caption
	 *            the new caption
	 */
	public void setCaption(String caption) {
		this.getLabel().setText(caption);
	}

	/** The label. */
	private JLabel label;

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public JLabel getLabel() {
		JLabel label = this.label;
		if (label != null) {
			return label;
		}
		label = new JLabel();
		this.label = label;
		return label;
	}

	/** The field editor. */
	private JComponent fieldEditor;

	/**
	 * Gets the field editor.
	 *
	 * @return the field editor
	 */
	public JComponent getFieldEditor() {
		JComponent fe = this.fieldEditor;
		if (fe != null) {
			return fe;
		}
		switch (this.type) {
		case TEXT:
			final JTextField textField = new JTextField();
			fe = textField;
			break;
		case PASSWORD:
			final JPasswordField pwdField = new JPasswordField();
			pwdField.setEchoChar('*');
			fe = pwdField;
			break;
		default:
			throw new IllegalArgumentException("type=" + this.type);
		}
		this.fieldEditor = fe;
		return fe;
	}
}
