/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.html;

import java.io.File;

import org.w3c.dom.NodeList;


/**
 * The Interface HTMLInputElement.
 */
public interface HTMLInputElement extends HTMLElement {
	
	/**
	 * Gets the accept.
	 *
	 * @return the accept
	 */
	// HTMLInputElement
	public String getAccept();

	/**
	 * Sets the accept.
	 *
	 * @param accept
	 *            the new accept
	 */
	public void setAccept(String accept);

	/**
	 * Gets the alt.
	 *
	 * @return the alt
	 */
	public String getAlt();

	/**
	 * Sets the alt.
	 *
	 * @param alt
	 *            the new alt
	 */
	public void setAlt(String alt);

	/**
	 * Gets the autocomplete.
	 *
	 * @return the autocomplete
	 */
	public boolean getAutocomplete();

	/**
	 * Sets the autocomplete.
	 *
	 * @param autocomplete
	 *            the new autocomplete
	 */
	public void setAutocomplete(String autocomplete);

	/**
	 * Gets the autofocus.
	 *
	 * @return the autofocus
	 */
	public boolean getAutofocus();

	/**
	 * Sets the autofocus.
	 *
	 * @param autofocus
	 *            the new autofocus
	 */
	public void setAutofocus(boolean autofocus);

	/**
	 * Gets the default checked.
	 *
	 * @return the default checked
	 */
	public boolean getDefaultChecked();

	/**
	 * Sets the default checked.
	 *
	 * @param defaultChecked
	 *            the new default checked
	 */
	public void setDefaultChecked(boolean defaultChecked);

	/* (non-Javadoc)
	 * @see org.lobobrowser.w3c.html.HTMLElement#getChecked()
	 */
	public boolean getChecked();

	/**
	 * Sets the checked.
	 *
	 * @param checked
	 *            the new checked
	 */
	public void setChecked(boolean checked);

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

	/* (non-Javadoc)
	 * @see org.lobobrowser.w3c.html.HTMLElement#getDisabled()
	 */
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
	 * Gets the files.
	 *
	 * @return the files
	 */
	public File[] getFiles();

	/**
	 * Gets the form action.
	 *
	 * @return the form action
	 */
	public String getFormAction();

	/**
	 * Sets the form action.
	 *
	 * @param formAction
	 *            the new form action
	 */
	public void setFormAction(String formAction);

	/**
	 * Gets the form enctype.
	 *
	 * @return the form enctype
	 */
	public String getFormEnctype();

	/**
	 * Sets the form enctype.
	 *
	 * @param formEnctype
	 *            the new form enctype
	 */
	public void setFormEnctype(String formEnctype);

	/**
	 * Gets the form method.
	 *
	 * @return the form method
	 */
	public String getFormMethod();

	/**
	 * Sets the form method.
	 *
	 * @param formMethod
	 *            the new form method
	 */
	public void setFormMethod(String formMethod);

	/**
	 * Gets the form no validate.
	 *
	 * @return the form no validate
	 */
	public boolean getFormNoValidate();

	/**
	 * Sets the form no validate.
	 *
	 * @param formNoValidate
	 *            the new form no validate
	 */
	public void setFormNoValidate(boolean formNoValidate);

	/**
	 * Gets the form target.
	 *
	 * @return the form target
	 */
	public String getFormTarget();

	/**
	 * Sets the form target.
	 *
	 * @param formTarget
	 *            the new form target
	 */
	public void setFormTarget(String formTarget);

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public String getHeight();

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the new height
	 */
	public void setHeight(String height);

	/**
	 * Gets the indeterminate.
	 *
	 * @return the indeterminate
	 */
	public boolean getIndeterminate();

	/**
	 * Sets the indeterminate.
	 *
	 * @param indeterminate
	 *            the new indeterminate
	 */
	public void setIndeterminate(boolean indeterminate);

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public HTMLElement getList();

	/**
	 * Gets the max.
	 *
	 * @return the max
	 */
	public String getMax();

	/**
	 * Sets the max.
	 *
	 * @param max
	 *            the new max
	 */
	public void setMax(String max);

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
	 * Gets the min.
	 *
	 * @return the min
	 */
	public String getMin();

	/**
	 * Sets the min.
	 *
	 * @param min
	 *            the new min
	 */
	public void setMin(String min);

	/**
	 * Gets the multiple.
	 *
	 * @return the multiple
	 */
	public boolean getMultiple();

	/**
	 * Sets the multiple.
	 *
	 * @param multiple
	 *            the new multiple
	 */
	public void setMultiple(boolean multiple);

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
	 * Gets the pattern.
	 *
	 * @return the pattern
	 */
	public String getPattern();

	/**
	 * Sets the pattern.
	 *
	 * @param pattern
	 *            the new pattern
	 */
	public void setPattern(String pattern);

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
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize();

	/**
	 * Sets the size.
	 *
	 * @param size
	 *            the new size
	 */
	public void setSize(int size);

	/**
	 * Gets the src.
	 *
	 * @return the src
	 */
	public String getSrc();

	/**
	 * Sets the src.
	 *
	 * @param src
	 *            the new src
	 */
	public void setSrc(String src);

	/**
	 * Gets the step.
	 *
	 * @return the step
	 */
	public String getStep();

	/**
	 * Sets the step.
	 *
	 * @param step
	 *            the new step
	 */
	public void setStep(String step);

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType();

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type);

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
	 * Gets the value as date.
	 *
	 * @return the value as date
	 */
	public long getValueAsDate();

	/**
	 * Sets the value as date.
	 *
	 * @param valueAsDate
	 *            the new value as date
	 */
	public void setValueAsDate(long valueAsDate);

	/**
	 * Gets the value as number.
	 *
	 * @return the value as number
	 */
	public double getValueAsNumber();

	/**
	 * Sets the value as number.
	 *
	 * @param valueAsNumber
	 *            the new value as number
	 */
	public void setValueAsNumber(double valueAsNumber);

	/**
	 * Gets the selected option.
	 *
	 * @return the selected option
	 */
	public HTMLOptionElement getSelectedOption();

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public String getWidth();

	/**
	 * Sets the width.
	 *
	 * @param width
	 *            the new width
	 */
	public void setWidth(String width);

	/**
	 * Step up.
	 */
	public void stepUp();

	/**
	 * Step up.
	 *
	 * @param n the n
	 */
	public void stepUp(int n);

	/**
	 * Step down.
	 */
	public void stepDown();

	/**
	 * Step down.
	 *
	 * @param n the n
	 */
	public void stepDown(int n);

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
	 * @param start the start
	 * @param end the end
	 */
	public void setSelectionRange(int start, int end);

	/**
	 * Sets the selection range.
	 *
	 * @param start the start
	 * @param end the end
	 * @param direction the direction
	 */
	public void setSelectionRange(int start, int end, String direction);

	/**
	 * Gets the align.
	 *
	 * @return the align
	 */
	// HTMLInputElement-17
	public String getAlign();

	/**
	 * Sets the align.
	 *
	 * @param align
	 *            the new align
	 */
	public void setAlign(String align);

	/**
	 * Gets the use map.
	 *
	 * @return the use map
	 */
	public String getUseMap();

	/**
	 * Sets the use map.
	 *
	 * @param useMap
	 *            the new use map
	 */
	public void setUseMap(String useMap);

	/**
	 * Sets the autocomplete.
	 *
	 * @param autocomplete
	 *            the new autocomplete
	 */
	void setAutocomplete(boolean autocomplete);
}
