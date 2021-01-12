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
 * An embedded Java applet. See the APPLET element definition in HTML 4.01. This
 * element is deprecated in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLAppletElement extends HTMLElement {
	/**
	 * Aligns this object (vertically or horizontally) with respect to its
	 * surrounding text. See the align attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAlign();

	/**
	 * Alternate text for user agents not rendering the normal content of this
	 * element. See the alt attribute definition in HTML 4.01. This attribute is
	 * deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAlt();

	/**
	 * Comma-separated archive list. See the archive attribute definition in HTML
	 * 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getArchive();

	/**
	 * Applet class file. See the code attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCode();

	/**
	 * Optional base URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC
	 * 2396</a>] for applet. See the codebase attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCodeBase();

	/**
	 * Override height. See the height attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getHeight();

	/**
	 * Horizontal space, in pixels, to the left and right of this image, applet, or
	 * object. See the hspace attribute definition in HTML 4.01. This attribute is
	 * deprecated in HTML 4.01.
	 *
	 * @version DOM Level 2
	 * @return a int.
	 */
	int getHspace();

	/**
	 * The name of the applet. See the name attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * The value of the "object" attribute. See the object attribute definition in
	 * HTML 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @version DOM Level 2
	 * @return a {@link java.lang.String} object.
	 */
	String getObject();

	/**
	 * Vertical space, in pixels, above and below this image, applet, or object. See
	 * the vspace attribute definition in HTML 4.01. This attribute is deprecated in
	 * HTML 4.01.
	 *
	 * @version DOM Level 2
	 * @return a int.
	 */
	int getVspace();

	/**
	 * Override width. See the width attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
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
	 * Alternate text for user agents not rendering the normal content of this
	 * element. See the alt attribute definition in HTML 4.01. This attribute is
	 * deprecated in HTML 4.01.
	 *
	 * @param alt a {@link java.lang.String} object.
	 */
	void setAlt(String alt);

	/**
	 * Comma-separated archive list. See the archive attribute definition in HTML
	 * 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @param archive a {@link java.lang.String} object.
	 */
	void setArchive(String archive);

	/**
	 * Applet class file. See the code attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @param code a {@link java.lang.String} object.
	 */
	void setCode(String code);

	/**
	 * Optional base URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC
	 * 2396</a>] for applet. See the codebase attribute definition in HTML 4.01.
	 * This attribute is deprecated in HTML 4.01.
	 *
	 * @param codeBase a {@link java.lang.String} object.
	 */
	void setCodeBase(String codeBase);

	/**
	 * Override height. See the height attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @param height a {@link java.lang.String} object.
	 */
	void setHeight(String height);

	/**
	 * Horizontal space, in pixels, to the left and right of this image, applet, or
	 * object. See the hspace attribute definition in HTML 4.01. This attribute is
	 * deprecated in HTML 4.01.
	 *
	 * @version DOM Level 2
	 * @param hspace a int.
	 */
	void setHspace(int hspace);

	/**
	 * The name of the applet. See the name attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	void setName(String name);

	/**
	 * The value of the "object" attribute. See the object attribute definition in
	 * HTML 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @version DOM Level 2
	 * @param object a {@link java.lang.String} object.
	 */
	void setObject(String object);

	/**
	 * Vertical space, in pixels, above and below this image, applet, or object. See
	 * the vspace attribute definition in HTML 4.01. This attribute is deprecated in
	 * HTML 4.01.
	 *
	 * @version DOM Level 2
	 * @param vspace a int.
	 */
	void setVspace(int vspace);

	/**
	 * Override width. See the width attribute definition in HTML 4.01. This
	 * attribute is deprecated in HTML 4.01.
	 *
	 * @param width a {@link java.lang.String} object.
	 */
	void setWidth(String width);

}
