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
 * A parallel container defines a simple parallel time grouping in
 * which multiple elements can play back at the same time. It may have to
 * specify a repeat iteration. (?)
 */
public interface ElementParallelTimeContainer extends ElementTimeContainer {
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
     * This method returns the implicit duration in seconds.
     *
     * @return The implicit duration in seconds or -1 if the implicit is unknown
     *         (indefinite?).
     */
    float getImplicitDuration();

}
