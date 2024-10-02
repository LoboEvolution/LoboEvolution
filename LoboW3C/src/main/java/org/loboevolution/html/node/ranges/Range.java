/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

package org.loboevolution.html.node.ranges;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.Node;
import org.loboevolution.js.geom.DOMRect;
import org.loboevolution.js.geom.DOMRectList;

/**
 * A fragment of a document that can contain nodes and parts of text nodes.
 */
public interface Range {


    short START_TO_START = 0;
    short START_TO_END = 1;
    short END_TO_END = 2;
    short END_TO_START = 3;

    /**
     * <p>getStartContainer.</p>
     *
     * @return a {@link org.loboevolution.html.node.Node} object.
     */
    Node getStartContainer() throws DOMException;

    int getStartOffset() throws DOMException;

    /**
     * <p>getEndContainer.</p>
     *
     * @return a {@link org.loboevolution.html.node.Node} object.
     */
    Node getEndContainer() throws DOMException;

    int getEndOffset() throws DOMException;

    boolean getCollapsed() throws DOMException;

    DocumentFragment createContextualFragment(String text);

    /**
     * <p>getCommonAncestorContainer.</p>
     *
     * @return a {@link org.loboevolution.html.node.Node} object.
     */
    Node getCommonAncestorContainer() throws DOMException;

    void setStart(Node refNode, int offset) throws RangeException, DOMException;

    void setEnd(Node refNode, int offset) throws RangeException, DOMException;

    void setStartBefore(Node refNode) throws RangeException, DOMException;

    void setStartAfter(Node refNode) throws RangeException, DOMException;

    void setEndBefore(Node refNode) throws RangeException, DOMException;

    void setEndAfter(Node refNode) throws RangeException, DOMException;

    void collapse(boolean toStart) throws DOMException;

    void selectNode(Node refNode) throws RangeException, DOMException;

    void selectNodeContents(Node refNode) throws RangeException, DOMException;

    /**
     * <p>compareBoundaryPoints.</p>
     *
     * @param how
     * @param sourceRange a {@link org.loboevolution.html.node.ranges.Range} object.
     * @return a {@link java.lang.Integer} object.
     */
    int getCompareBoundaryPoints(int how, Range sourceRange) throws DOMException;

    void deleteContents() throws DOMException;

    /**
     * <p>extractContents.</p>
     *
     * @return a {@link org.loboevolution.html.node.DocumentFragment} object.
     */
    DocumentFragment extractContents() throws DOMException;

    /**
     * <p>cloneContents.</p>
     *
     * @return a {@link org.loboevolution.html.node.DocumentFragment} object.
     */
    DocumentFragment cloneContents() throws DOMException;

    void insertNode(Node newNode) throws DOMException, RangeException;

    void surroundContents(Node newParent) throws DOMException, RangeException;

    Range cloneRange() throws DOMException;

    String toString() throws DOMException;

    void detach() throws DOMException;

    DOMRectList getClientRects();

    DOMRect getBoundingClientRect();
}
