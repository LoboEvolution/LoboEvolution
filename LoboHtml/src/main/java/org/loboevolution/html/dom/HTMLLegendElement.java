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
 * Provides a caption for a FIELDSET grouping. See the LEGEND
 * element definition in HTML 4.01.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLLegendElement extends HTMLElement {
	/**
	 * A single character access key to give access to the form control. See the
	 * accesskey attribute definition in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAccessKey();

	/**
	 * Text alignment relative to FIELDSET. See the align attribute
	 * definition in HTML 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAlign();

	/**
	 * Returns the FORM element containing this control. Returns
	 * null if this control is not within the context of a form.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLFormElement} object.
	 */
	HTMLFormElement getForm();

	/**
	 * A single character access key to give access to the form control. See the
	 * accesskey attribute definition in HTML 4.01.
	 *
	 * @param accessKey a {@link java.lang.String} object.
	 */
	void setAccessKey(String accessKey);

	/**
	 * Text alignment relative to FIELDSET. See the align attribute
	 * definition in HTML 4.01. This attribute is deprecated in HTML 4.01.
	 *
	 * @param align a {@link java.lang.String} object.
	 */
	void setAlign(String align);

}
