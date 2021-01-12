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
 * Client-side image map area definition. See the AREA element definition in
 * HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLAreaElement extends HTMLElement {
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
	 * Comma-separated list of lengths, defining an active region geometry. See also
	 * shape for the shape of the region. See the coords attribute
	 * definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCoords();

	/**
	 * The URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>] of
	 * the linked resource. See the href attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getHref();

	/**
	 * Specifies that this area is inactive, i.e., has no associated action. See the
	 * nohref attribute definition in HTML 4.01.
	 *
	 * @return a boolean.
	 */
	boolean getNoHref();

	/**
	 * The shape of the active area. The coordinates are given by
	 * coords. See the shape attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getShape();

	/**
	 * Index that represents the element's position in the tabbing order. See the
	 * tabindex attribute definition in HTML 4.01.
	 *
	 * @return a int.
	 */
	int getTabIndex();

	/**
	 * Frame to render the resource in. See the target attribute definition in HTML
	 * 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getTarget();

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
	 * Comma-separated list of lengths, defining an active region geometry. See also
	 * shape for the shape of the region. See the coords attribute
	 * definition in HTML 4.01.
	 *
	 * @param coords a {@link java.lang.String} object.
	 */
	void setCoords(String coords);

	/**
	 * The URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>] of
	 * the linked resource. See the href attribute definition in HTML 4.01.
	 *
	 * @param href a {@link java.lang.String} object.
	 */
	void setHref(String href);

	/**
	 * Specifies that this area is inactive, i.e., has no associated action. See the
	 * nohref attribute definition in HTML 4.01.
	 *
	 * @param noHref a boolean.
	 */
	void setNoHref(boolean noHref);

	/**
	 * The shape of the active area. The coordinates are given by
	 * coords. See the shape attribute definition in HTML 4.01.
	 *
	 * @param shape a {@link java.lang.String} object.
	 */
	void setShape(String shape);

	/**
	 * Index that represents the element's position in the tabbing order. See the
	 * tabindex attribute definition in HTML 4.01.
	 *
	 * @param tabIndex a int.
	 */
	void setTabIndex(int tabIndex);

	/**
	 * Frame to render the resource in. See the target attribute definition in HTML
	 * 4.01.
	 *
	 * @param target a {@link java.lang.String} object.
	 */
	void setTarget(String target);

}
