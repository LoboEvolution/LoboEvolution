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

import org.w3c.dom.Element;

/**
 * All HTML element interfaces derive from this class. Elements that only expose
 * the HTML core attributes are represented by the base HTMLElement
 * interface. These elements are as follows: special: SUB, SUP, SPAN, BDOfont:
 * TT, I, B, U, S, STRIKE, BIG, SMALL phrase: EM, STRONG, DFN, CODE, SAMP, KBD,
 * VAR, CITE, ACRONYM, ABBRlist: DD, DTNOFRAMES, NOSCRIPTADDRESS, CENTERThe
 * style attribute of an HTML element is accessible through the
 * ElementCSSInlineStyle interface which is defined in the CSS
 * module
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
public interface HTMLElement extends Element {
	/**
	 * The class attribute of the element. This attribute has been renamed due to
	 * conflicts with the "class" keyword exposed by many languages. See the class
	 * attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getClassName();

	/**
	 * Specifies the base direction of directionally neutral text and the
	 * directionality of tables. See the dir attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getDir();

	/**
	 * The element's identifier. See the id attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getId();

	/**
	 * Language code defined in RFC 1766. See the lang attribute definition in HTML
	 * 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getLang();

	/**
	 * The element's advisory title. See the title attribute definition in HTML
	 * 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getTitle();
	
    /**
     * <p>getContentEditable.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getContentEditable();

    /**
     * <p>setContentEditable.</p>
     *
     * @param contenteditable a {@link java.lang.String} object.
     */
    void setContentEditable(String contenteditable);

	/**
	 * The class attribute of the element. This attribute has been renamed due to
	 * conflicts with the "class" keyword exposed by many languages. See the class
	 * attribute definition in HTML 4.01.
	 *
	 * @param className a {@link java.lang.String} object.
	 */
	void setClassName(String className);

	/**
	 * Specifies the base direction of directionally neutral text and the
	 * directionality of tables. See the dir attribute definition in HTML 4.01.
	 *
	 * @param dir a {@link java.lang.String} object.
	 */
	void setDir(String dir);

	/**
	 * The element's identifier. See the id attribute definition in HTML 4.01.
	 *
	 * @param id a {@link java.lang.String} object.
	 */
	void setId(String id);

	/**
	 * Language code defined in RFC 1766. See the lang attribute definition in HTML
	 * 4.01.
	 *
	 * @param lang a {@link java.lang.String} object.
	 */
	void setLang(String lang);

	/**
	 * The element's advisory title. See the title attribute definition in HTML
	 * 4.01.
	 *
	 * @param title a {@link java.lang.String} object.
	 */
	void setTitle(String title);

}
