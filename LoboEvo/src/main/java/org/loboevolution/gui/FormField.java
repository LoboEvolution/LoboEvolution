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

package org.loboevolution.gui;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import com.jtattoo.plaf.lobo.LoboLabel;
import com.jtattoo.plaf.lobo.LoboPasswordField;
import com.jtattoo.plaf.lobo.LoboTextField;

/**
 * The Class FormField.
 *
 *
 *
 */
public class FormField {

	/** The field editor. */
	private JComponent fieldEditor;

	/** The loboLabel. */
	private LoboLabel loboLabel;

	/** The type. */
	public final FieldType type;

	/**
	 * Instantiates a new form field.
	 *
	 * @param type the type
	 */
	public FormField(final FieldType type) {
		this(type, "");
	}

	/**
	 * Instantiates a new form field.
	 *
	 * @param type     the type
	 * @param editable the editable
	 */
	public FormField(final FieldType type, final boolean editable) {
		this.type = type;
		setEditable(editable);
	}

	/**
	 * Instantiates a new form field.
	 *
	 * @param type    the type
	 * @param caption the caption
	 */
	public FormField(final FieldType type, String caption) {
		this(type, caption, true);
	}

	/**
	 * Instantiates a new form field.
	 *
	 * @param type     the type
	 * @param caption  the caption
	 * @param editable the editable
	 */
	public FormField(final FieldType type, final String caption, final boolean editable) {
		this.type = type;
		createAndShowGUI(caption, editable);
	}

	private void createAndShowGUI(final String caption, final boolean editable) {
		setCaption(caption);
		setEditable(editable);
	}

	/**
	 * Gets the caption.
	 *
	 * @return the caption
	 */
	public String getCaption() {
		return getLabel().getText();
	}

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
			fe = new LoboTextField();
			break;
		case PASSWORD:
			fe = new LoboPasswordField();
			break;
		default:
			throw new IllegalArgumentException("type=" + this.type);
		}
		this.fieldEditor = fe;
		return fe;
	}

	/**
	 * Gets the loboLabel.
	 *
	 * @return the loboLabel
	 */
	public LoboLabel getLabel() {
		LoboLabel loboLabel = this.loboLabel;
		if (loboLabel != null) {
			return loboLabel;
		}
		loboLabel = new LoboLabel("");
		this.loboLabel = loboLabel;
		return loboLabel;
	}

	/**
	 * Gets the tool tip.
	 *
	 * @return the tool tip
	 */
	public String getToolTip() {
		return getFieldEditor().getToolTipText();
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public FieldType getType() {
		return this.type;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		final JComponent fe = getFieldEditor();
		if (fe instanceof JTextComponent) {
			return ((JTextComponent) fe).getText();
		} else {
			return null;
		}
	}

	/**
	 * Checks if is editable.
	 *
	 * @return true, if is editable
	 */
	public boolean isEditable() {
		final JComponent fe = getFieldEditor();
		if (fe instanceof JTextComponent) {
			return ((JTextComponent) fe).isEditable();
		} else {
			return false;
		}
	}

	/**
	 * Sets the caption.
	 *
	 * @param caption the new caption
	 */
	public void setCaption(String caption) {
		getLabel().setText(caption);
	}

	/**
	 * Sets the editable.
	 *
	 * @param editable the new editable
	 */
	public void setEditable(boolean editable) {
		final JComponent fe = getFieldEditor();
		if (fe instanceof JTextComponent) {
			((JTextComponent) fe).setEditable(editable);
		}
	}

	/**
	 * Sets the tool tip.
	 *
	 * @param tooltip the new tool tip
	 */
	public void setToolTip(String tooltip) {
		getFieldEditor().setToolTipText(tooltip);
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		final JComponent fe = getFieldEditor();
		if (fe instanceof JTextComponent) {
			((JTextComponent) fe).setText(value);
		}
	}
}
