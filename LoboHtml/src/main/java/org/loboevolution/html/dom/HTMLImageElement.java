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
 * Embedded image. See the IMG element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLImageElement extends HTMLElement {
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
	 * element. See the alt attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAlt();

	/**
	 * Width of border around image. See the border attribute definition in HTML
	 * 4.01. This attribute is deprecated in HTML 4.01. Note that the type of this
	 * attribute was DOMString in DOM Level 1 HTML
	 * [<a href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
	 * 1</a>] .
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getBorder();

	/**
	 * Height of the image in pixels. See the height attribute definition in HTML
	 * 4.01. Note that the type of this attribute was DOMString in DOM
	 * Level 1 HTML
	 * [<a href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
	 * 1</a>].
	 *
	 * @version DOM Level 2
	 * @return a int.
	 */
	int getHeight();

	/**
	 * Horizontal space to the left and right of this image in pixels. See the
	 * hspace attribute definition in HTML 4.01. This attribute is deprecated in
	 * HTML 4.01. Note that the type of this attribute was DOMString in
	 * DOM Level 1 HTML
	 * [<a href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
	 * 1</a>].
	 *
	 * @version DOM Level 2
	 * @return a int.
	 */
	int getHspace();

	/**
	 * Use server-side image map. See the ismap attribute definition in HTML 4.01.
	 *
	 * @return a boolean.
	 */
	boolean getIsMap();

	/**
	 * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating a long description of this image or frame. See the longdesc
	 * attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getLongDesc();

	/**
	 * The name of the element (for backwards compatibility).
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating the source of this image. See the src attribute definition in
	 * HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getSrc();

	/**
	 * Use client-side image map. See the usemap attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getUseMap();

	/**
	 * Vertical space above and below this image in pixels. See the vspace attribute
	 * definition in HTML 4.01. This attribute is deprecated in HTML 4.01. Note that
	 * the type of this attribute was "DOMString" in DOM Level 1 HTML
	 * [<a href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
	 * 1</a>].
	 *
	 * @version DOM Level 2
	 * @return a int.
	 */
	int getVspace();

	/**
	 * The width of the image in pixels. See the width attribute definition in HTML
	 * 4.01. Note that the type of this attribute was DOMString in DOM
	 * Level 1 HTML
	 * [<a href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
	 * 1</a>].
	 *
	 * @version DOM Level 2
	 * @return a int.
	 */
	int getWidth();

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
	 * element. See the alt attribute definition in HTML 4.01.
	 *
	 * @param alt a {@link java.lang.String} object.
	 */
	void setAlt(String alt);

	/**
	 * Width of border around image. See the border attribute definition in HTML
	 * 4.01. This attribute is deprecated in HTML 4.01. Note that the type of this
	 * attribute was DOMString in DOM Level 1 HTML
	 * [<a href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
	 * 1</a>] .
	 *
	 * @param border a {@link java.lang.String} object.
	 */
	void setBorder(String border);

	/**
	 * Height of the image in pixels. See the height attribute definition in HTML
	 * 4.01. Note that the type of this attribute was DOMString in DOM
	 * Level 1 HTML
	 * [<a href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
	 * 1</a>].
	 *
	 * @version DOM Level 2
	 * @param height a int.
	 */
	void setHeight(int height);

	/**
	 * Horizontal space to the left and right of this image in pixels. See the
	 * hspace attribute definition in HTML 4.01. This attribute is deprecated in
	 * HTML 4.01. Note that the type of this attribute was DOMString in
	 * DOM Level 1 HTML
	 * [<a href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
	 * 1</a>].
	 *
	 * @version DOM Level 2
	 * @param hspace a int.
	 */
	void setHspace(int hspace);

	/**
	 * Use server-side image map. See the ismap attribute definition in HTML 4.01.
	 *
	 * @param isMap a boolean.
	 */
	void setIsMap(boolean isMap);

	/**
	 * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating a long description of this image or frame. See the longdesc
	 * attribute definition in HTML 4.01.
	 *
	 * @param longDesc a {@link java.lang.String} object.
	 */
	void setLongDesc(String longDesc);

	/**
	 * The name of the element (for backwards compatibility).
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	void setName(String name);

	/**
	 * URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]
	 * designating the source of this image. See the src attribute definition in
	 * HTML 4.01.
	 *
	 * @param src a {@link java.lang.String} object.
	 */
	void setSrc(String src);

	/**
	 * Use client-side image map. See the usemap attribute definition in HTML 4.01.
	 *
	 * @param useMap a {@link java.lang.String} object.
	 */
	void setUseMap(String useMap);

	/**
	 * Vertical space above and below this image in pixels. See the vspace attribute
	 * definition in HTML 4.01. This attribute is deprecated in HTML 4.01. Note that
	 * the type of this attribute was "DOMString" in DOM Level 1 HTML
	 * [<a href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
	 * 1</a>].
	 *
	 * @version DOM Level 2
	 * @param vspace a int.
	 */
	void setVspace(int vspace);

	/**
	 * The width of the image in pixels. See the width attribute definition in HTML
	 * 4.01. Note that the type of this attribute was DOMString in DOM
	 * Level 1 HTML
	 * [<a href='http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001'>DOM Level
	 * 1</a>].
	 *
	 * @version DOM Level 2
	 * @param width a int.
	 */
	void setWidth(int width);

}
