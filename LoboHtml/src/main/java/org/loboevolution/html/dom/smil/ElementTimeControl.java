/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.smil;

import org.w3c.dom.DOMException;

/**
 * <p>ElementTimeControl interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface ElementTimeControl {
	/**
	 * Causes this element to begin the local timeline (subject to sync
	 * constraints).
	 *
	 * @return true if the method call was successful and the
	 *         element was begun. false if the method call failed.
	 *         Possible reasons for failure include: The element doesn't support
	 *         the beginElement method. (the begin
	 *         attribute is not set to "indefinite" ) The element
	 *         is already active and can't be restart when it is active. (the
	 *         restart attribute is set to
	 *         "whenNotActive" ) The element is active or has been
	 *         active and can't be restart. (the restart attribute
	 *         is set to "never" ).
	 * @exception DOMException
	 *                SYNTAX_ERR: The element was not defined with the
	 *                appropriate syntax to allow beginElement
	 *                calls.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public boolean beginElement() throws DOMException;

	/**
	 * Causes this element to begin the local timeline (subject to sync
	 * constraints), at the passed offset from the current time when the method
	 * is called. If the offset is &gt;= 0, the semantics are equivalent to an
	 * event-base begin with the specified offset. If the offset is &lt; 0, the
	 * semantics are equivalent to beginElement(), but the element active
	 * duration is evaluated as though the element had begun at the passed
	 * (negative) offset from the current time when the method is called.
	 *
	 * @param offset
	 *            The offset in seconds at which to begin the element.
	 * @return true if the method call was successful and the
	 *         element was begun. false if the method call failed.
	 *         Possible reasons for failure include: The element doesn't support
	 *         the beginElementAt method. (the begin
	 *         attribute is not set to "indefinite" ) The element
	 *         is already active and can't be restart when it is active. (the
	 *         restart attribute is set to
	 *         "whenNotActive" ) The element is active or has been
	 *         active and can't be restart. (the restart attribute
	 *         is set to "never" ).
	 * @exception DOMException
	 *                SYNTAX_ERR: The element was not defined with the
	 *                appropriate syntax to allow beginElementAt
	 *                calls.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public boolean beginElementAt(float offset) throws DOMException;

	/**
	 * Causes this element to end the local timeline (subject to sync
	 * constraints).
	 *
	 * @return true if the method call was successful and the
	 *         element was ended. false if method call failed.
	 *         Possible reasons for failure include: The element doesn't support
	 *         the endElement method. (the end
	 *         attribute is not set to "indefinite" ) The element
	 *         is not active.
	 * @exception DOMException
	 *                SYNTAX_ERR: The element was not defined with the
	 *                appropriate syntax to allow endElement calls.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public boolean endElement() throws DOMException;

	/**
	 * Causes this element to end the local timeline (subject to sync
	 * constraints) at the specified offset from the current time when the
	 * method is called.
	 *
	 * @param offset
	 *            The offset in seconds at which to end the element. Must be
	 *            &gt;= 0.
	 * @return true if the method call was successful and the
	 *         element was ended. false if method call failed.
	 *         Possible reasons for failure include: The element doesn't support
	 *         the endElementAt method. (the end
	 *         attribute is not set to "indefinite" ) The element
	 *         is not active.
	 * @exception DOMException
	 *                SYNTAX_ERR: The element was not defined with the
	 *                appropriate syntax to allow endElementAt
	 *                calls.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public boolean endElementAt(float offset) throws DOMException;

}
