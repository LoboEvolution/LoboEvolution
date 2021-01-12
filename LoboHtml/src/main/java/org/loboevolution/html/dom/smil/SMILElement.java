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

package org.loboevolution.html.dom.smil;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

/**
 * The SMILElement interface is the base for all SMIL element
 * types. It follows the model of the HTMLElement in the HTML DOM,
 * extending the base Element class to denote SMIL-specific
 * elements.
 * <p>
 * Note that the SMILElement interface overlaps with the
 * HTMLElement interface. In practice, an integrated document
 * profile that include HTML and SMIL modules will effectively implement both
 * interfaces (see also the DOM documentation discussion of Inheritance vs
 * Flattened Views of the API ). // etc. This needs attention
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SMILElement extends Element {
	/**
	 * The unique id.
	 *
	 * @exception DOMException
	 *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
	 *                readonly.
	 * @return a {@link java.lang.String} object.
	 */
    String getId();

	/**
	 * <p>setId.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
    void setId(String id) throws DOMException;

}
