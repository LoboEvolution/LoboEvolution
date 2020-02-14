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
 */
public interface HTMLInputElement extends HTMLElement {
	/**
	 * Removes keyboard focus from this element.
	 */
	void blur();

	/**
	 * Simulate a mouse-click. For <code>INPUT</code> elements whose
	 * <code>type</code> attribute has one of the following values: "button",
	 * "checkbox", "radio", "reset", or "submit".
	 */
	void click();

	/**
	 * Gives keyboard focus to this element.
	 */
	void focus();
	
	void setSelectionRange(int start, int end); 
	
	void setRangeText(String select, int start, int end, String preserve);

	/**
	 * A comma-separated list of content types that a server processing this form
	 * will handle correctly. See the accept attribute definition in HTML 4.01.
	 */
	String getAccept();

	/**
	 * A single character access key to give access to the form control. See the
	 * accesskey attribute definition in HTML 4.01.
	 */
	String getAccessKey();

	/**
	 * Alternate text for user agents not rendering the normal content of this
	 * element. See the alt attribute definition in HTML 4.01.
	 */
	String getAlt();

	/**
	 * When the <code>type</code> attribute of the element has the value "radio" or
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
	 */
	boolean getChecked();


	/**
	 * The control is unavailable in this context. See the disabled attribute
	 * definition in HTML 4.01.
	 */
	boolean getDisabled();

	/**
	 * Returns the <code>FORM</code> element containing this control. Returns
	 * <code>null</code> if this control is not within the context of a form.
	 */
	HTMLFormElement getForm();

	/**
	 * Maximum number of characters for text fields, when <code>type</code> has the
	 * value "text" or "password". See the maxlength attribute definition in HTML
	 * 4.01.
	 */
	int getMaxLength();

	/**
	 * Form control or object name when submitted with a form. See the name
	 * attribute definition in HTML 4.01.
	 */
	String getName();

	/**
	 * This control is read-only. Relevant only when <code>type</code> has the value
	 * "text" or "password". See the readonly attribute definition in HTML 4.01.
	 */
	boolean getReadOnly();

	/**
	 * Size information. The precise meaning is specific to each type of field. See
	 * the size attribute definition in HTML 4.01.
	 * 
	 * @version DOM Level 2
	 */
	int getSize();

	/**
	 * When the <code>type</code> attribute has the value "image", this attribute
	 * specifies the location of the image to be used to decorate the graphical
	 * submit button. See the src attribute definition in HTML 4.01.
	 */
	String getSrc();

	/**
	 * The type of control created (all lower case). See the type attribute
	 * definition in HTML 4.01.
	 * 
	 * @version DOM Level 2
	 */
	String getType();

	/**
	 * When the <code>type</code> attribute of the element has the value "text",
	 * "file" or "password", this represents the current contents of the
	 * corresponding form control, in an interactive user agent. Changing this
	 * attribute changes the contents of the form control, but does not change the
	 * value of the HTML value attribute of the element. When the <code>type</code>
	 * attribute of the element has the value "button", "hidden", "submit", "reset",
	 * "image", "checkbox" or "radio", this represents the HTML value attribute of
	 * the element. See the value attribute definition in HTML 4.01.
	 */
	String getValue();
	
	boolean getAutocomplete();

	/**
	 * Select the contents of the text area. For <code>INPUT</code> elements whose
	 * <code>type</code> attribute has one of the following values: "text", "file",
	 * or "password".
	 */
	void select();

	/**
	 * A comma-separated list of content types that a server processing this form
	 * will handle correctly. See the accept attribute definition in HTML 4.01.
	 */
	void setAccept(String accept);

	/**
	 * A single character access key to give access to the form control. See the
	 * accesskey attribute definition in HTML 4.01.
	 */
	void setAccessKey(String accessKey);


	/**
	 * Alternate text for user agents not rendering the normal content of this
	 * element. See the alt attribute definition in HTML 4.01.
	 */
	void setAlt(String alt);

	/**
	 * When the <code>type</code> attribute of the element has the value "radio" or
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
	 */
	void setChecked(boolean checked);


	/**
	 * The control is unavailable in this context. See the disabled attribute
	 * definition in HTML 4.01.
	 */
	void setDisabled(boolean disabled);

	/**
	 * Maximum number of characters for text fields, when <code>type</code> has the
	 * value "text" or "password". See the maxlength attribute definition in HTML
	 * 4.01.
	 */
	void setMaxLength(int maxLength);

	/**
	 * Form control or object name when submitted with a form. See the name
	 * attribute definition in HTML 4.01.
	 */
	void setName(String name);

	/**
	 * This control is read-only. Relevant only when <code>type</code> has the value
	 * "text" or "password". See the readonly attribute definition in HTML 4.01.
	 */
	void setReadOnly(boolean readOnly);

	/**
	 * Size information. The precise meaning is specific to each type of field. See
	 * the size attribute definition in HTML 4.01.
	 * 
	 * @version DOM Level 2
	 */
	void setSize(int size);

	/**
	 * When the <code>type</code> attribute has the value "image", this attribute
	 * specifies the location of the image to be used to decorate the graphical
	 * submit button. See the src attribute definition in HTML 4.01.
	 */
	void setSrc(String src);

	/**
	 * The type of control created (all lower case). See the type attribute
	 * definition in HTML 4.01.
	 * 
	 * @version DOM Level 2
	 */
	void setType(String type);

	/**
	 * When the <code>type</code> attribute of the element has the value "text",
	 * "file" or "password", this represents the current contents of the
	 * corresponding form control, in an interactive user agent. Changing this
	 * attribute changes the contents of the form control, but does not change the
	 * value of the HTML value attribute of the element. When the <code>type</code>
	 * attribute of the element has the value "button", "hidden", "submit", "reset",
	 * "image", "checkbox" or "radio", this represents the HTML value attribute of
	 * the element. See the value attribute definition in HTML 4.01.
	 */
	void setValue(String value);

}
