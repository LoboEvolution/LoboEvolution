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
 * Style information. See the STYLE element definition in HTML 4.01, the CSS
 * module
 * [<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>DOM Level
 * 2 Style Sheets and CSS</a>] and the LinkStyle interface in the
 * StyleSheets module
 * [<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>DOM Level
 * 2 Style Sheets and CSS</a>].
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLStyleElement extends HTMLElement {
	/**
	 * Enables/disables the style sheet.
	 *
	 * @return a boolean.
	 */
	boolean getDisabled();

	/**
	 * Designed for use with one or more target media. See the media attribute
	 * definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getMedia();

	/**
	 * The content type of the style sheet language. See the type attribute
	 * definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getType();

	/**
	 * Enables/disables the style sheet.
	 *
	 * @param disabled a boolean.
	 */
	void setDisabled(boolean disabled);

	/**
	 * Designed for use with one or more target media. See the media attribute
	 * definition in HTML 4.01.
	 *
	 * @param media a {@link java.lang.String} object.
	 */
	void setMedia(String media);

	/**
	 * The content type of the style sheet language. See the type attribute
	 * definition in HTML 4.01.
	 *
	 * @param type a {@link java.lang.String} object.
	 */
	void setType(String type);

}
