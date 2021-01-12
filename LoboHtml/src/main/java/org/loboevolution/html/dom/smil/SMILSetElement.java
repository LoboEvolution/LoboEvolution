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


/**
 * This interface represents the set element.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SMILSetElement extends ElementTimeControl, ElementTime, ElementTargetAttributes, SMILElement {
	/**
	 * Specifies the value for the attribute during the duration of this
	 * element.
	 *
	 * @return a {@link java.lang.String} object.
	 */
    String getTo();

	/**
	 * <p>setTo.</p>
	 *
	 * @param to a {@link java.lang.String} object.
	 */
    void setTo(String to);

}
