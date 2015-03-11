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
/*
 * Copyright (c) 2003 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 */

package org.lobobrowser.html.w3c;

import java.io.File;

import org.w3c.dom.NodeList;


/**
 * Form control.Depending upon the environment in which the page is being
 * viewed, the value property may be read-only for the file upload input type.
 * For the "password" input type, the actual value returned may be masked to
 * prevent unauthorized use. See the INPUT element definition in [<a
 * href='http://www.w3.org/TR/1999/REC-html401-19991224'>HTML 4.01</a>].
 * <p>
 * See also the
 * Object Model (DOM) Level 2 HTML Specification</p>.
 */
public interface HTMLInputElement extends HTMLElement {
	
	/**
	 * When the <code>type</code> attribute of the element has the value "text",
	 * "file" or "password", this represents the HTML value attribute of the
	 * element. The value of this attribute does not change if the contents of
	 * the corresponding form control, in an interactive user agent, changes.
	 * See the value attribute definition in HTML 4.01.
	 *
	 * @return the default value
	 */
	public String getDefaultValue();

	/**
	 * When the <code>type</code> attribute of the element has the value "text",
	 * "file" or "password", this represents the HTML value attribute of the
	 * element. The value of this attribute does not change if the contents of
	 * the corresponding form control, in an interactive user agent, changes.
	 * See the value attribute definition in HTML 4.01.
	 *
	 * @param defaultValue the new default value
	 */
	public void setDefaultValue(String defaultValue);

	/**
	 * When <code>type</code> has the value "radio" or "checkbox", this
	 * represents the HTML checked attribute of the element. The value of this
	 * attribute does not change if the state of the corresponding form control,
	 * in an interactive user agent, changes. See the checked attribute
	 * definition in HTML 4.01.
	 *
	 * @return the default checked
	 */
	public boolean getDefaultChecked();

	/**
	 * When <code>type</code> has the value "radio" or "checkbox", this
	 * represents the HTML checked attribute of the element. The value of this
	 * attribute does not change if the state of the corresponding form control,
	 * in an interactive user agent, changes. See the checked attribute
	 * definition in HTML 4.01.
	 *
	 * @param defaultChecked the new default checked
	 */
	public void setDefaultChecked(boolean defaultChecked);

	/**
	 * Returns the <code>FORM</code> element containing this control. Returns
	 * <code>null</code> if this control is not within the context of a form.
	 *
	 * @return the form
	 */
	public HTMLFormElement getForm();

	/**
	 * A comma-separated list of content types that a server processing this
	 * form will handle correctly. See the accept attribute definition in HTML
	 * 4.01.
	 *
	 * @return the accept
	 */
	public String getAccept();

	/**
	 * A comma-separated list of content types that a server processing this
	 * form will handle correctly. See the accept attribute definition in HTML
	 * 4.01.
	 *
	 * @param accept the new accept
	 */
	public void setAccept(String accept);

	/**
	 * A single character access key to give access to the form control. See the
	 * accesskey attribute definition in HTML 4.01.
	 *
	 * @return the access key
	 */
	public String getAccessKey();

	/**
	 * A single character access key to give access to the form control. See the
	 * accesskey attribute definition in HTML 4.01.
	 *
	 * @param accessKey the new access key
	 */
	public void setAccessKey(String accessKey);

	/**
	 * Aligns this object (vertically or horizontally) with respect to its
	 * surrounding text. See the align attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @return the align
	 */
	public String getAlign();

	/**
	 * Aligns this object (vertically or horizontally) with respect to its
	 * surrounding text. See the align attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @param align the new align
	 */
	public void setAlign(String align);

	/**
	 * Alternate text for user agents not rendering the normal content of this
	 * element. See the alt attribute definition in HTML 4.01.
	 *
	 * @return the alt
	 */
	public String getAlt();

	/**
	 * Alternate text for user agents not rendering the normal content of this
	 * element. See the alt attribute definition in HTML 4.01.
	 *
	 * @param alt the new alt
	 */
	public void setAlt(String alt);

	/**
	 * When the <code>type</code> attribute of the element has the value "radio"
	 * or "checkbox", this represents the current state of the form control, in
	 * an interactive user agent. Changes to this attribute change the state of
	 * the form control, but do not change the value of the HTML checked
	 * attribute of the INPUT element.During the handling of a click event on an
	 * input element with a type attribute that has the value "radio" or
	 * "checkbox", some implementations may change the value of this property
	 * before the event is being dispatched in the document. If the default
	 * action of the event is canceled, the value of the property may be changed
	 * back to its original value. This means that the value of this property
	 * during the handling of click events is implementation dependent.
	 *
	 * @return the checked
	 */
	public boolean getChecked();

	/**
	 * When the <code>type</code> attribute of the element has the value "radio"
	 * or "checkbox", this represents the current state of the form control, in
	 * an interactive user agent. Changes to this attribute change the state of
	 * the form control, but do not change the value of the HTML checked
	 * attribute of the INPUT element.During the handling of a click event on an
	 * input element with a type attribute that has the value "radio" or
	 * "checkbox", some implementations may change the value of this property
	 * before the event is being dispatched in the document. If the default
	 * action of the event is canceled, the value of the property may be changed
	 * back to its original value. This means that the value of this property
	 * during the handling of click events is implementation dependent.
	 *
	 * @param checked the new checked
	 */
	public void setChecked(boolean checked);

	/**
	 * The control is unavailable in this context. See the disabled attribute
	 * definition in HTML 4.01.
	 *
	 * @return the disabled
	 */
	public boolean getDisabled();

	/**
	 * The control is unavailable in this context. See the disabled attribute
	 * definition in HTML 4.01.
	 *
	 * @param disabled the new disabled
	 */
	public void setDisabled(boolean disabled);

	/**
	 * Maximum number of characters for text fields, when <code>type</code> has
	 * the value "text" or "password". See the maxlength attribute definition in
	 * HTML 4.01.
	 *
	 * @return the max length
	 */
	public int getMaxLength();

	/**
	 * Maximum number of characters for text fields, when <code>type</code> has
	 * the value "text" or "password". See the maxlength attribute definition in
	 * HTML 4.01.
	 *
	 * @param maxLength the new max length
	 */
	public void setMaxLength(int maxLength);

	/**
	 * Form control or object name when submitted with a form. See the name
	 * attribute definition in HTML 4.01.
	 *
	 * @return the name
	 */
	public String getName();

	/**
	 * Form control or object name when submitted with a form. See the name
	 * attribute definition in HTML 4.01.
	 *
	 * @param name the new name
	 */
	public void setName(String name);

	/**
	 * This control is read-only. Relevant only when <code>type</code> has the
	 * value "text" or "password". See the readonly attribute definition in HTML
	 * 4.01.
	 *
	 * @return the read only
	 */
	public boolean getReadOnly();

	/**
	 * This control is read-only. Relevant only when <code>type</code> has the
	 * value "text" or "password". See the readonly attribute definition in HTML
	 * 4.01.
	 *
	 * @param readOnly the new read only
	 */
	public void setReadOnly(boolean readOnly);

	/**
	 * Size information. The precise meaning is specific to each type of field.
	 * See the size attribute definition in HTML 4.01.
	 *
	 * @version DOM Level 2
	 * @return the size
	 */
	public int getSize();

	/**
	 * Size information. The precise meaning is specific to each type of field.
	 * See the size attribute definition in HTML 4.01.
	 *
	 * @version DOM Level 2
	 * @param size the new size
	 */
	public void setSize(int size);

	/**
	 * When the <code>type</code> attribute has the value "image", this
	 * attribute specifies the location of the image to be used to decorate the
	 * graphical submit button. See the src attribute definition in HTML 4.01.
	 *
	 * @return the src
	 */
	public String getSrc();

	/**
	 * When the <code>type</code> attribute has the value "image", this
	 * attribute specifies the location of the image to be used to decorate the
	 * graphical submit button. See the src attribute definition in HTML 4.01.
	 *
	 * @param src the new src
	 */
	public void setSrc(String src);

	/**
	 * Index that represents the element's position in the tabbing order. See
	 * the tabindex attribute definition in HTML 4.01.
	 *
	 * @return the tab index
	 */
	public int getTabIndex();

	/**
	 * Index that represents the element's position in the tabbing order. See
	 * the tabindex attribute definition in HTML 4.01.
	 *
	 * @param tabIndex the new tab index
	 */
	public void setTabIndex(int tabIndex);

	/**
	 * The type of control created (all lower case). See the type attribute
	 * definition in HTML 4.01.
	 *
	 * @version DOM Level 2
	 * @return the type
	 */
	public String getType();

	/**
	 * The type of control created (all lower case). See the type attribute
	 * definition in HTML 4.01.
	 *
	 * @version DOM Level 2
	 * @param type the new type
	 */
	public void setType(String type);

	/**
	 * Use client-side image map. See the usemap attribute definition in HTML
	 * 4.01.
	 *
	 * @return the use map
	 */
	public String getUseMap();

	/**
	 * Use client-side image map. See the usemap attribute definition in HTML
	 * 4.01.
	 *
	 * @param useMap the new use map
	 */
	public void setUseMap(String useMap);

	/**
	 * When the <code>type</code> attribute of the element has the value "text",
	 * "file" or "password", this represents the current contents of the
	 * corresponding form control, in an interactive user agent. Changing this
	 * attribute changes the contents of the form control, but does not change
	 * the value of the HTML value attribute of the element. When the
	 * <code>type</code> attribute of the element has the value "button",
	 * "hidden", "submit", "reset", "image", "checkbox" or "radio", this
	 * represents the HTML value attribute of the element. See the value
	 * attribute definition in HTML 4.01.
	 *
	 * @return the value
	 */
	public String getValue();

	/**
	 * When the <code>type</code> attribute of the element has the value "text",
	 * "file" or "password", this represents the current contents of the
	 * corresponding form control, in an interactive user agent. Changing this
	 * attribute changes the contents of the form control, but does not change
	 * the value of the HTML value attribute of the element. When the
	 * <code>type</code> attribute of the element has the value "button",
	 * "hidden", "submit", "reset", "image", "checkbox" or "radio", this
	 * represents the HTML value attribute of the element. See the value
	 * attribute definition in HTML 4.01.
	 *
	 * @param value the new value
	 */
	public void setValue(String value);

	/**
	 * Removes keyboard focus from this element.
	 */
	public void blur();

	/**
	 * Gives keyboard focus to this element.
	 */
	public void focus();

	/**
	 * Select the contents of the text area. For <code>INPUT</code> elements
	 * whose <code>type</code> attribute has one of the following values:
	 * "text", "file", or "password".
	 */
	public void select();

	/**
	 * Simulate a mouse-click. For <code>INPUT</code> elements whose
	 * <code>type</code> attribute has one of the following values: "button",
	 * "checkbox", "radio", "reset", or "submit".
	 */
	public void click();

	/**
	 * Gets the autocomplete.
	 *
	 * @return the autocomplete
	 */
	public boolean getAutocomplete();

	/**
	 * Sets the autocomplete.
	 *
	 * @param autocomplete the new autocomplete
	 */
	public void setAutocomplete(boolean autocomplete);

	/**
	 * Gets the autofocus.
	 *
	 * @return the autofocus
	 */
	public boolean getAutofocus();

	/**
	 * Sets the autofocus.
	 *
	 * @param autofocus the new autofocus
	 */
	public void setAutofocus(boolean autofocus);

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
	 * @param formAction the new form action
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
	 * @param formEnctype the new form enctype
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
	 * @param formMethod the new form method
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
	 * @param formNoValidate the new form no validate
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
	 * @param formTarget the new form target
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
	 * @param height the new height
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
	 * @param indeterminate the new indeterminate
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
	 * @param max the new max
	 */
	public void setMax(String max);

	/**
	 * Gets the min.
	 *
	 * @return the min
	 */
	public String getMin();

	/**
	 * Sets the min.
	 *
	 * @param min the new min
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
	 * @param multiple the new multiple
	 */
	public void setMultiple(boolean multiple);

	/**
	 * Gets the pattern.
	 *
	 * @return the pattern
	 */
	public String getPattern();

	/**
	 * Sets the pattern.
	 *
	 * @param pattern the new pattern
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
	 * @param placeholder the new placeholder
	 */
	public void setPlaceholder(String placeholder);

	/**
	 * Gets the required.
	 *
	 * @return the required
	 */
	public boolean getRequired();

	/**
	 * Sets the required.
	 *
	 * @param required the new required
	 */
	public void setRequired(boolean required);

	/**
	 * Gets the step.
	 *
	 * @return the step
	 */
	public String getStep();

	/**
	 * Sets the step.
	 *
	 * @param step the new step
	 */
	public void setStep(String step);
	
	/**
	 * Gets the value as date.
	 *
	 * @return the value as date
	 */
	public long getValueAsDate();

	/**
	 * Sets the value as date.
	 *
	 * @param valueAsDate the new value as date
	 */
	public void setValueAsDate(long valueAsDate);

	/**
	 * Gets the value as number.
	 *
	 * @return the value as number
	 */
	public float getValueAsNumber();

	/**
	 * Sets the value as number.
	 *
	 * @param valueAsNumber the new value as number
	 */
	public void setValueAsNumber(float valueAsNumber);

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
	 * @param width the new width
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
	 * @param error the new custom validity
	 */
	public void setCustomValidity(String error);

	/**
	 * Gets the labels.
	 *
	 * @return the labels
	 */
	public NodeList getLabels();

	/**
	 * Gets the selection start.
	 *
	 * @return the selection start
	 */
	public int getSelectionStart();

	/**
	 * Sets the selection start.
	 *
	 * @param selectionStart the new selection start
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
	 * @param selectionEnd the new selection end
	 */
	public void setSelectionEnd(int selectionEnd);

	/**
	 * Sets the selection range.
	 *
	 * @param start the start
	 * @param end the end
	 */
	public void setSelectionRange(int start, int end);
}
