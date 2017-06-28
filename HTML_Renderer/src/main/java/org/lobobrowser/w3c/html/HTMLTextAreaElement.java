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

package org.lobobrowser.w3c.html;

import org.w3c.dom.NodeList;

/**
 * The Interface HTMLTextAreaElement.
 */
public interface HTMLTextAreaElement extends HTMLElement {

	/**
	 * Gets the autofocus.
	 *
	 * @return the autofocus
	 */
	// HTMLTextAreaElement
	public boolean getAutofocus();

	/**
	 * Sets the autofocus.
	 *
	 * @param autofocus
	 *            the new autofocus
	 */
	public void setAutofocus(boolean autofocus);

	/**
	 * Gets the cols.
	 *
	 * @return the cols
	 */
	public int getCols();

	/**
	 * Sets the cols.
	 *
	 * @param cols
	 *            the new cols
	 */
	public void setCols(int cols);

	/**
	 * Gets the dir name.
	 *
	 * @return the dir name
	 */
	public String getDirName();

	/**
	 * Sets the dir name.
	 *
	 * @param dirName
	 *            the new dir name
	 */
	public void setDirName(String dirName);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLElement#getDisabled()
	 */
	@Override
	public boolean getDisabled();

	/**
	 * Sets the disabled.
	 *
	 * @param disabled
	 *            the new disabled
	 */
	public void setDisabled(boolean disabled);

	/**
	 * Gets the form.
	 *
	 * @return the form
	 */
	public HTMLFormElement getForm();

	/**
	 * Gets the max length.
	 *
	 * @return the max length
	 */
	public int getMaxLength();

	/**
	 * Sets the max length.
	 *
	 * @param maxLength
	 *            the new max length
	 */
	public void setMaxLength(int maxLength);

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name);

	/**
	 * Gets the placeholder.
	 *
	 * @return the placeholder
	 */
	public String getPlaceholder();

	/**
	 * Sets the placeholder.
	 *
	 * @param placeholder
	 *            the new placeholder
	 */
	public void setPlaceholder(String placeholder);

	/**
	 * Gets the read only.
	 *
	 * @return the read only
	 */
	public boolean getReadOnly();

	/**
	 * Sets the read only.
	 *
	 * @param readOnly
	 *            the new read only
	 */
	public void setReadOnly(boolean readOnly);

	/**
	 * Gets the required.
	 *
	 * @return the required
	 */
	public boolean getRequired();

	/**
	 * Sets the required.
	 *
	 * @param required
	 *            the new required
	 */
	public void setRequired(boolean required);

	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	public int getRows();

	/**
	 * Sets the rows.
	 *
	 * @param rows
	 *            the new rows
	 */
	public void setRows(int rows);

	/**
	 * Gets the wrap.
	 *
	 * @return the wrap
	 */
	public String getWrap();

	/**
	 * Sets the wrap.
	 *
	 * @param wrap
	 *            the new wrap
	 */
	public void setWrap(String wrap);

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType();

	/**
	 * Gets the default value.
	 *
	 * @return the default value
	 */
	public String getDefaultValue();

	/**
	 * Sets the default value.
	 *
	 * @param defaultValue
	 *            the new default value
	 */
	public void setDefaultValue(String defaultValue);

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue();

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(String value);

	/**
	 * Gets the text length.
	 *
	 * @return the text length
	 */
	public int getTextLength();

	/**
	 * Gets the will validate.
	 *
	 * @return the will validate
	 */
	public boolean getWillValidate();

	/**
	 * Gets the validity.
	 *
	 * @return the validity
	 */
	public ValidityState getValidity();

	/**
	 * Gets the validation message.
	 *
	 * @return the validation message
	 */
	public String getValidationMessage();

	/**
	 * Check validity.
	 *
	 * @return true, if successful
	 */
	public boolean checkValidity();

	/**
	 * Sets the custom validity.
	 *
	 * @param error
	 *            the new custom validity
	 */
	public void setCustomValidity(String error);

	/**
	 * Gets the labels.
	 *
	 * @return the labels
	 */
	public NodeList getLabels();

	/**
	 * Select.
	 */
	public void select();

	/**
	 * Gets the selection start.
	 *
	 * @return the selection start
	 */
	public int getSelectionStart();

	/**
	 * Sets the selection start.
	 *
	 * @param selectionStart
	 *            the new selection start
	 */
	public void setSelectionStart(int selectionStart);

	/**
	 * Gets the selection end.
	 *
	 * @return the selection end
	 */
	public int getSelectionEnd();

	/**
	 * Sets the selection end.
	 *
	 * @param selectionEnd
	 *            the new selection end
	 */
	public void setSelectionEnd(int selectionEnd);

	/**
	 * Gets the selection direction.
	 *
	 * @return the selection direction
	 */
	public String getSelectionDirection();

	/**
	 * Sets the selection direction.
	 *
	 * @param selectionDirection
	 *            the new selection direction
	 */
	public void setSelectionDirection(String selectionDirection);

	/**
	 * Sets the selection range.
	 *
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 */
	public void setSelectionRange(int start, int end);

	/**
	 * Sets the selection range.
	 *
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @param direction
	 *            the direction
	 */
	public void setSelectionRange(int start, int end, String direction);
}
