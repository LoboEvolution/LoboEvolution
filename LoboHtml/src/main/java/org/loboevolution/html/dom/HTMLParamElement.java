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
 * Parameters fed to the OBJECT element. See the PARAM element
 * definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLParamElement extends HTMLElement {
	/**
	 * The name of a run-time parameter. See the name attribute definition in HTML
	 * 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * Content type for the value attribute when valuetype
	 * has the value "ref". See the type attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getType();

	/**
	 * The value of a run-time parameter. See the value attribute definition in HTML
	 * 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getValue();

	/**
	 * Information about the meaning of the value attribute value. See
	 * the valuetype attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getValueType();

	/**
	 * The name of a run-time parameter. See the name attribute definition in HTML
	 * 4.01.
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	void setName(String name);

	/**
	 * Content type for the value attribute when valuetype
	 * has the value "ref". See the type attribute definition in HTML 4.01.
	 *
	 * @param type a {@link java.lang.String} object.
	 */
	void setType(String type);

	/**
	 * The value of a run-time parameter. See the value attribute definition in HTML
	 * 4.01.
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	void setValue(String value);

	/**
	 * Information about the meaning of the value attribute value. See
	 * the valuetype attribute definition in HTML 4.01.
	 *
	 * @param valueType a {@link java.lang.String} object.
	 */
	void setValueType(String valueType);

}
