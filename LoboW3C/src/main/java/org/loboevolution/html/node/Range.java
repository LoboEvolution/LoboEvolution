/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.node;

import org.loboevolution.jsenum.Mode;

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
     * @param how a {@link org.loboevolution.jsenum.Mode} object.
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
