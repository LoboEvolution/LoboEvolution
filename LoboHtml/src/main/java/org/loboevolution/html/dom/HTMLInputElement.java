/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.dom;

/**
 * Form control.Depending upon the environment in which the page is being
 * viewed, the value property may be read-only for the file upload input type.
 * For the "password" input type, the actual value returned may be masked to
 * prevent unauthorized use. See the INPUT element definition in
 * [<a href='http://www.w3.org/TR/1999/REC-html401-19991224'>HTML 4.01</a>].
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLInputElement extends HTMLElement {
	/**
	 * Removes keyboard focus from this element.
	 */
	void blur();

	/**
	 * Simulate a mouse-click. For INPUT elements whose
	 * type attribute has one of the following values: "button",
	 * "checkbox", "radio", "reset", or "submit".
	 */
	void click();

	/**
	 * Gives keyboard focus to this element.
	 */
	void focus();
	
	/**
	 * <p>setSelectionRange.</p>
	 *
	 * @param start a int.
	 * @param end a int.
	 */
	void setSelectionRange(int start, int end);
	
	
	void setSelectionStart(int start);
	
    void setSelectionEnd(int end);

	
	/**
	 * <p>setRangeText.</p>
	 *
	 * @param select a {@link java.lang.String} object.
	 * @param start a int.
	 * @param end a int.
	 * @param preserve a {@link java.lang.String} object.
	 */
	void setRangeText(String select, int start, int end, String preserve);

	/**
	 * A comma-separated list of content types that a server processing this form
	 * will handle correctly. See the accept attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAccept();

	/**
	 * A single character access key to give access to the form control. See the
	 * accesskey attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAccessKey();

	/**
	 * Alternate text for user agents not rendering the normal content of this
	 * element. See the alt attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAlt();

	/**
	 * When the type attribute of the element has the value "radio" or
	 * "checkbox", this represents the current state of the form control, in an
	 * interactive user agent. Changes to this attribute change the state of the
	 * form control, but do not change the value of the HTML checked attribute of
	 * the INPUT element.During the handling of a click event on an input element
	 * with a type attribute that has the value "radio" or "checkbox", some
	 * implementations may change the value of this property before the event is
	 * being dispatched in the document. If the default action of the event is
	 * canceled, the value of the property may be changed back to its original
	 * value. This means that the value of this property during the handling of
	 * click events is implementation dependent.
	 *
	 * @return a boolean.
	 */
	boolean getChecked();


	/**
	 * The control is unavailable in this context. See the disabled attribute
	 * definition in HTML 4.01.
	 *
	 * @return a boolean.
	 */
	boolean getDisabled();

	/**
	 * Returns the FORM element containing this control. Returns
	 * null if this control is not within the context of a form.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLFormElement} object.
	 */
	HTMLFormElement getForm();

	/**
	 * Maximum number of characters for text fields, when type has the
	 * value "text" or "password". See the maxlength attribute definition in HTML
	 * 4.01.
	 *
	 * @return a int.
	 */
	int getMaxLength();

	/**
	 * Form control or object name when submitted with a form. See the name
	 * attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * This control is read-only. Relevant only when type has the value
	 * "text" or "password". See the readonly attribute definition in HTML 4.01.
	 *
	 * @return a boolean.
	 */
	boolean getReadOnly();

	/**
	 * Size information. The precise meaning is specific to each type of field. See
	 * the size attribute definition in HTML 4.01.
	 *
	 * @version DOM Level 2
	 * @return a int.
	 */
	int getSize();

	/**
	 * When the type attribute has the value "image", this attribute
	 * specifies the location of the image to be used to decorate the graphical
	 * submit button. See the src attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getSrc();

	/**
	 * The type of control created (all lower case). See the type attribute
	 * definition in HTML 4.01.
	 *
	 * @version DOM Level 2
	 * @return a {@link java.lang.String} object.
	 */
	String getType();

	/**
	 * When the type attribute of the element has the value "text",
	 * "file" or "password", this represents the current contents of the
	 * corresponding form control, in an interactive user agent. Changing this
	 * attribute changes the contents of the form control, but does not change the
	 * value of the HTML value attribute of the element. When the type
	 * attribute of the element has the value "button", "hidden", "submit", "reset",
	 * "image", "checkbox" or "radio", this represents the HTML value attribute of
	 * the element. See the value attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getValue();
	
	/**
	 * <p>getAutocomplete.</p>
	 *
	 * @return a boolean.
	 */
	boolean getAutocomplete();

	/**
	 * Select the contents of the text area. For INPUT elements whose
	 * type attribute has one of the following values: "text", "file",
	 * or "password".
	 */
	void select();
	
	int getSelectionStart();

    int getSelectionEnd();	

	/**
	 * A comma-separated list of content types that a server processing this form
	 * will handle correctly. See the accept attribute definition in HTML 4.01.
	 *
	 * @param accept a {@link java.lang.String} object.
	 */
	void setAccept(String accept);

	/**
	 * A single character access key to give access to the form control. See the
	 * accesskey attribute definition in HTML 4.01.
	 *
	 * @param accessKey a {@link java.lang.String} object.
	 */
	void setAccessKey(String accessKey);


	/**
	 * Alternate text for user agents not rendering the normal content of this
	 * element. See the alt attribute definition in HTML 4.01.
	 *
	 * @param alt a {@link java.lang.String} object.
	 */
	void setAlt(String alt);

	/**
	 * When the type attribute of the element has the value "radio" or
	 * "checkbox", this represents the current state of the form control, in an
	 * interactive user agent. Changes to this attribute change the state of the
	 * form control, but do not change the value of the HTML checked attribute of
	 * the INPUT element.During the handling of a click event on an input element
	 * with a type attribute that has the value "radio" or "checkbox", some
	 * implementations may change the value of this property before the event is
	 * being dispatched in the document. If the default action of the event is
	 * canceled, the value of the property may be changed back to its original
	 * value. This means that the value of this property during the handling of
	 * click events is implementation dependent.
	 *
	 * @param checked a boolean.
	 */
	void setChecked(boolean checked);


	/**
	 * The control is unavailable in this context. See the disabled attribute
	 * definition in HTML 4.01.
	 *
	 * @param disabled a boolean.
	 */
	void setDisabled(boolean disabled);

	/**
	 * Maximum number of characters for text fields, when type has the
	 * value "text" or "password". See the maxlength attribute definition in HTML
	 * 4.01.
	 *
	 * @param maxLength a int.
	 */
	void setMaxLength(int maxLength);

	/**
	 * Form control or object name when submitted with a form. See the name
	 * attribute definition in HTML 4.01.
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	void setName(String name);

	/**
	 * This control is read-only. Relevant only when type has the value
	 * "text" or "password". See the readonly attribute definition in HTML 4.01.
	 *
	 * @param readOnly a boolean.
	 */
	void setReadOnly(boolean readOnly);

	/**
	 * Size information. The precise meaning is specific to each type of field. See
	 * the size attribute definition in HTML 4.01.
	 *
	 * @version DOM Level 2
	 * @param size a int.
	 */
	void setSize(int size);

	/**
	 * When the type attribute has the value "image", this attribute
	 * specifies the location of the image to be used to decorate the graphical
	 * submit button. See the src attribute definition in HTML 4.01.
	 *
	 * @param src a {@link java.lang.String} object.
	 */
	void setSrc(String src);

	/**
	 * The type of control created (all lower case). See the type attribute
	 * definition in HTML 4.01.
	 *
	 * @version DOM Level 2
	 * @param type a {@link java.lang.String} object.
	 */
	void setType(String type);

	/**
	 * When the type attribute of the element has the value "text",
	 * "file" or "password", this represents the current contents of the
	 * corresponding form control, in an interactive user agent. Changing this
	 * attribute changes the contents of the form control, but does not change the
	 * value of the HTML value attribute of the element. When the type
	 * attribute of the element has the value "button", "hidden", "submit", "reset",
	 * "image", "checkbox" or "radio", this represents the HTML value attribute of
	 * the element. See the value attribute definition in HTML 4.01.
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	void setValue(String value);

}
