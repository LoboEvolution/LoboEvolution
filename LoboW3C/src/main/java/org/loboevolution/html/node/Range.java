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

package org.loboevolution.html.node;

import org.loboevolution.type.Mode;

/**
 * A fragment of a document that can contain nodes and parts of text nodes.
 *
 *
 *
 */
public interface Range {
   
    /**
     * <p>getCommonAncestorContainer.</p>
     *
     * @return a {@link org.loboevolution.html.node.Node} object.
     */
    Node getCommonAncestorContainer();

    /**
     * <p>cloneContents.</p>
     *
     * @return a {@link org.loboevolution.html.node.DocumentFragment} object.
     */
    DocumentFragment cloneContents();

    /**
     * <p>cloneRange.</p>
     *
     * @return a {@link org.loboevolution.html.node.Range} object.
     */
    Range cloneRange();

    /**
     * <p>collapse.</p>
     *
     * @param toStart a boolean.
     */
    void collapse(boolean toStart);

    /**
     * <p>collapse.</p>
     */
    void collapse();

    /**
     * <p>compareBoundaryPoints.</p>
     *
     * @param how a {@link org.loboevolution.type.Mode} object.
     * @param sourceRange a {@link org.loboevolution.html.node.Range} object.
     * @return a int.
     */
    int compareBoundaryPoints(Mode how, Range sourceRange);

    /**
     * Returns −1 if the point is before the range, 0 if the point is in the range, and 1 if the point is after the range.
     *
     * @param node a {@link org.loboevolution.html.node.Node} object.
     * @param offset a int.
     * @return a int.
     */
    int comparePoint(Node node, int offset);

    /**
     * <p>createContextualFragment.</p>
     *
     * @param fragment a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.node.DocumentFragment} object.
     */
    DocumentFragment createContextualFragment(String fragment);

    /**
     * <p>deleteContents.</p>
     */
    void deleteContents();

    /**
     * <p>detach.</p>
     */
    void detach();

    /**
     * <p>extractContents.</p>
     *
     * @return a {@link org.loboevolution.html.node.DocumentFragment} object.
     */
    DocumentFragment extractContents();

    /**
     * <p>insertNode.</p>
     *
     * @param node a {@link org.loboevolution.html.node.Node} object.
     */
    void insertNode(Node node);

    /**
     * Returns whether range intersects node.
     *
     * @param node a {@link org.loboevolution.html.node.Node} object.
     * @return a boolean.
     */
    boolean intersectsNode(Node node);

    /**
     * <p>isPointInRange.</p>
     *
     * @param node a {@link org.loboevolution.html.node.Node} object.
     * @param offset a int.
     * @return a boolean.
     */
    boolean isPointInRange(Node node, int offset);

    /**
     * <p>selectNode.</p>
     *
     * @param node a {@link org.loboevolution.html.node.Node} object.
     */
    void selectNode(Node node);

    /**
     * <p>selectNodeContents.</p>
     *
     * @param node a {@link org.loboevolution.html.node.Node} object.
     */
    void selectNodeContents(Node node);

    /**
     * <p>setEnd.</p>
     *
     * @param node a {@link org.loboevolution.html.node.Node} object.
     * @param offset a int.
     */
    void setEnd(Node node, int offset);

    /**
     * <p>setEndAfter.</p>
     *
     * @param node a {@link org.loboevolution.html.node.Node} object.
     */
    void setEndAfter(Node node);

    /**
     * <p>setEndBefore.</p>
     *
     * @param node a {@link org.loboevolution.html.node.Node} object.
     */
    void setEndBefore(Node node);

    /**
     * <p>setStart.</p>
     *
     * @param node a {@link org.loboevolution.html.node.Node} object.
     * @param offset a int.
     */
    void setStart(Node node, int offset);

    /**
     * <p>setStartAfter.</p>
     *
     * @param node a {@link org.loboevolution.html.node.Node} object.
     */
    void setStartAfter(Node node);

    /**
     * <p>setStartBefore.</p>
     *
     * @param node a {@link org.loboevolution.html.node.Node} object.
     */
    void setStartBefore(Node node);

    /**
     * <p>surroundContents.</p>
     *
     * @param newParent a {@link org.loboevolution.html.node.Node} object.
     */
    void surroundContents(Node newParent);

}
