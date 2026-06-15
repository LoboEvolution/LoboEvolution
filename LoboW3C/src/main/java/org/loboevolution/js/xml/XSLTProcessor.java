package org.loboevolution.js.xml;

/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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

import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

/**
 * <p>XSLTProcessor interface.</p>
 *
 * <p>XSLTProcessor is an interface for performing XSLT transformations. It allows
 * you to import an XSLT stylesheet and transform XML documents.</p>
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/XSLTProcessor">MDN XSLTProcessor</a>
 */
public interface XSLTProcessor {

    /**
     * Imports the XSLT stylesheet into this processor for use in subsequent transformations.
     *
     * @param stylesheet the XSLT stylesheet document
     */
    void importStylesheet(Node stylesheet);

    /**
     * Transforms the given XML source document into a Document object.
     *
     * @param source the XML source document to transform
     * @return the transformed Document
     */
    Document transformToDocument(Node source);

    /**
     * Transforms the given XML source node into a DocumentFragment.
     *
     * @param source the XML source node to transform
     * @param owner  the owner document for the resulting fragment (can be null)
     * @return the transformed DocumentFragment
     */
    Node transformToFragment(Node source, Document owner);
}