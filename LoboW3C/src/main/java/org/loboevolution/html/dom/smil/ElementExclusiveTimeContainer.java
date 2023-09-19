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
import org.loboevolution.html.node.NodeList;

/**
 * This interface defines a time container with semantics based upon par, but
 * with the additional constraint that only one child element may play at a
 * time.
 */
public interface ElementExclusiveTimeContainer extends ElementTimeContainer {
    /**
     * Controls the end of the container. Need to address thr id-ref value.
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getEndSync();

    /**
     * <p>setEndSync.</p>
     *
     * @param endSync a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setEndSync(String endSync) throws DOMException;

    /**
     * This should support another method to get the ordered collection of
     * paused elements (the paused stack) at a given point in time.
     *
     * @return All paused elements at the current time.
     */
    NodeList getPausedElements();

}
