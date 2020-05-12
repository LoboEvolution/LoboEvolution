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

import org.w3c.dom.Document;

/**
 * Generic embedded object.In principle, all properties on the object element
 * are read-write but in some environments some properties may be read-only once
 * the underlying object is instantiated. See the OBJECT element definition in
 * [<a href='http://www.w3.org/TR/1999/REC-html401-19991224'>HTML 4.01</a>].
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLObjectElement extends HTMLElement {
	/**
	 * Aligns this object (vertically or horizontally) with respect to its
	 * surrounding text. See the align attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAlign();

	/**
	 * Space-separated list of archives. See the archive attribute definition in
	 * HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getArchive();

	/**
	 * Width of border around the object. See the border attribute definition in
	 * HTML 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getBorder();

	/**
	 * Applet class file. See the code attribute for HTMLAppletElement.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCode();

	/**
	 * Base URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * for classid, data, and archive
	 * attributes. See the codebase attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCodeBase();

	/**
	 * Content type for data downloaded via classid attribute. See the
	 * codetype attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCodeType();

	/**
	 * The document this object contains, if there is any and it is available, or
	 * null otherwise.
	 *
	 * @since DOM Level 2
	 * @return a {@link org.w3c.dom.Document} object.
	 */
	Document getContentDocument();

	/**
	 * A URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * specifying the location of the object's data. See the data attribute
	 * definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getData();

	/**
	 * Declare (for future reference), but do not instantiate, this object. See the
	 * declare attribute definition in HTML 4.01.
	 *
	 * @return a boolean.
	 */
	boolean getDeclare();

	/**
	 * Returns the FORM element containing this control. Returns
	 * null if this control is not within the context of a form.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLFormElement} object.
	 */
	HTMLFormElement getForm();

	/**
	 * Override height. See the height attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getHeight();

	/**
	 * Horizontal space, in pixels, to the left and right of this image, applet, or
	 * object. See the hspace attribute definition in HTML 4.01. This attribute is
	 * deprecated in HTML 4.01.
	 *
	 * @return a int.
	 */
	int getHspace();

	/**
	 * Form control or object name when submitted with a form. See the name
	 * attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * Message to render while loading the object. See the standby attribute
	 * definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getStandby();

	/**
	 * Index that represents the element's position in the tabbing order. See the
	 * tabindex attribute definition in HTML 4.01.
	 *
	 * @return a int.
	 */
	int getTabIndex();

	/**
	 * Content type for data downloaded via data attribute. See the
	 * type attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getType();

	/**
	 * Use client-side image map. See the usemap attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getUseMap();

	/**
	 * Vertical space, in pixels, above and below this image, applet, or object. See
	 * the vspace attribute definition in HTML 4.01. This attribute is deprecated in
	 * HTML 4.01.
	 *
	 * @return a int.
	 */
	int getVspace();

	/**
	 * Override width. See the width attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getWidth();

	/**
	 * Aligns this object (vertically or horizontally) with respect to its
	 * surrounding text. See the align attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @param align a {@link java.lang.String} object.
	 */
	void setAlign(String align);

	/**
	 * Space-separated list of archives. See the archive attribute definition in
	 * HTML 4.01.
	 *
	 * @param archive a {@link java.lang.String} object.
	 */
	void setArchive(String archive);

	/**
	 * Width of border around the object. See the border attribute definition in
	 * HTML 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @param border a {@link java.lang.String} object.
	 */
	void setBorder(String border);

	/**
	 * Applet class file. See the code attribute for HTMLAppletElement.
	 *
	 * @param code a {@link java.lang.String} object.
	 */
	void setCode(String code);

	/**
	 * Base URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * for classid, data, and archive
	 * attributes. See the codebase attribute definition in HTML 4.01.
	 *
	 * @param codeBase a {@link java.lang.String} object.
	 */
	void setCodeBase(String codeBase);

	/**
	 * Content type for data downloaded via classid attribute. See the
	 * codetype attribute definition in HTML 4.01.
	 *
	 * @param codeType a {@link java.lang.String} object.
	 */
	void setCodeType(String codeType);

	/**
	 * A URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * specifying the location of the object's data. See the data attribute
	 * definition in HTML 4.01.
	 *
	 * @param data a {@link java.lang.String} object.
	 */
	void setData(String data);

	/**
	 * Declare (for future reference), but do not instantiate, this object. See the
	 * declare attribute definition in HTML 4.01.
	 *
	 * @param declare a boolean.
	 */
	void setDeclare(boolean declare);

	/**
	 * Override height. See the height attribute definition in HTML 4.01.
	 *
	 * @param height a {@link java.lang.String} object.
	 */
	void setHeight(String height);

	/**
	 * Horizontal space, in pixels, to the left and right of this image, applet, or
	 * object. See the hspace attribute definition in HTML 4.01. This attribute is
	 * deprecated in HTML 4.01.
	 *
	 * @param hspace a int.
	 */
	void setHspace(int hspace);

	/**
	 * Form control or object name when submitted with a form. See the name
	 * attribute definition in HTML 4.01.
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	void setName(String name);

	/**
	 * Message to render while loading the object. See the standby attribute
	 * definition in HTML 4.01.
	 *
	 * @param standby a {@link java.lang.String} object.
	 */
	void setStandby(String standby);

	/**
	 * Index that represents the element's position in the tabbing order. See the
	 * tabindex attribute definition in HTML 4.01.
	 *
	 * @param tabIndex a int.
	 */
	void setTabIndex(int tabIndex);

	/**
	 * Content type for data downloaded via data attribute. See the
	 * type attribute definition in HTML 4.01.
	 *
	 * @param type a {@link java.lang.String} object.
	 */
	void setType(String type);

	/**
	 * Use client-side image map. See the usemap attribute definition in HTML 4.01.
	 *
	 * @param useMap a {@link java.lang.String} object.
	 */
	void setUseMap(String useMap);

	/**
	 * Vertical space, in pixels, above and below this image, applet, or object. See
	 * the vspace attribute definition in HTML 4.01. This attribute is deprecated in
	 * HTML 4.01.
	 *
	 * @param vspace a int.
	 */
	void setVspace(int vspace);

	/**
	 * Override width. See the width attribute definition in HTML 4.01.
	 *
	 * @param width a {@link java.lang.String} object.
	 */
	void setWidth(String width);

}
