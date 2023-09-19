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
import org.loboevolution.html.node.Element;

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
     * {@inheritDoc}
     *
     * <p>setId.</p>
     */
    void setId(String id) throws DOMException;

}
