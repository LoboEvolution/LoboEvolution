/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.smil;

import org.htmlunit.cssparser.dom.DOMException;

/**
 * <p>ElementTimeControl interface.</p>
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
     * @throws DOMException if any.
     */
    boolean beginElement() throws DOMException;

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
     * @throws DOMException if any.
     */
    boolean beginElementAt(float offset) throws DOMException;

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
     * @throws DOMException if any.
     */
    boolean endElement() throws DOMException;

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
     * @throws DOMException if any.
     */
    boolean endElementAt(float offset) throws DOMException;

}
