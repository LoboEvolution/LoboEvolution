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

package org.loboevolution.html.xpath;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.node.Node;

/**
 * The XPathResult interface represents the result of the
 * evaluation of an XPath 1.0 expression within the context of a particular
 * node. Since evaluation of an XPath expression can result in various
 * result types, this object makes it possible to discover and manipulate
 * the type and value of the result.
 * <p>See also the <a href='http://www.w3.org/2002/08/WD-DOM-Level-3-XPath-20020820'>Document Object Model (DOM) Level 3 XPath Specification</a>.
 */
public interface XPathResult {
    /**
     * This code does not represent a specific type. An evaluation of an XPath
     * expression will never produce this type. If this type is requested,
     * then the evaluation returns whatever type naturally results from
     * evaluation of the expression.
     * <br>If the natural result is a node set when ANY_TYPE was
     * requested, then UNORDERED_NODE_ITERATOR_TYPE is always
     * the resulting type. Any other representation of a node set must be
     * explicitly requested.
     */
    short ANY_TYPE = 0;
    /**
     * The result is a number as defined by . Document modification does not
     * invalidate the number, but may mean that reevaluation would not yield
     * the same number.
     */
    short NUMBER_TYPE = 1;
    /**
     * The result is a string as defined by . Document modification does not
     * invalidate the string, but may mean that the string no longer
     * corresponds to the current document.
     */
    short STRING_TYPE = 2;
    /**
     * The result is a boolean as defined by . Document modification does not
     * invalidate the boolean, but may mean that reevaluation would not
     * yield the same boolean.
     */
    short BOOLEAN_TYPE = 3;
    /**
     * The result is a node set as defined by  that will be accessed
     * iteratively, which may not produce nodes in a particular order.
     * Document modification invalidates the iteration.
     * <br>This is the default type returned if the result is a node set and
     * ANY_TYPE is requested.
     */
    short UNORDERED_NODE_ITERATOR_TYPE = 4;
    /**
     * The result is a node set as defined by  that will be accessed
     * iteratively, which will produce document-ordered nodes. Document
     * modification invalidates the iteration.
     */
    short ORDERED_NODE_ITERATOR_TYPE = 5;
    /**
     * The result is a node set as defined by  that will be accessed as a
     * snapshot list of nodes that may not be in a particular order.
     * Document modification does not invalidate the snapshot but may mean
     * that reevaluation would not yield the same snapshot and nodes in the
     * snapshot may have been altered, moved, or removed from the document.
     */
    short UNORDERED_NODE_SNAPSHOT_TYPE = 6;
    /**
     * The result is a node set as defined by  that will be accessed as a
     * snapshot list of nodes that will be in original document order.
     * Document modification does not invalidate the snapshot but may mean
     * that reevaluation would not yield the same snapshot and nodes in the
     * snapshot may have been altered, moved, or removed from the document.
     */
    short ORDERED_NODE_SNAPSHOT_TYPE = 7;
    /**
     * The result is a node set as defined by  and will be accessed as a
     * single node, which may be nullif the node set is empty.
     * Document modification does not invalidate the node, but may mean that
     * the result node no longer corresponds to the current document. This
     * is a convenience that permits optimization since the implementation
     * can stop once any node in the in the resulting set has been found.
     * <br>If there are more than one node in the actual result, the single
     * node returned might not be the first in document order.
     */
    short ANY_UNORDERED_NODE_TYPE = 8;
    /**
     * The result is a node set as defined by  and will be accessed as a
     * single node, which may be null if the node set is empty.
     * Document modification does not invalidate the node, but may mean that
     * the result node no longer corresponds to the current document. This
     * is a convenience that permits optimization since the implementation
     * can stop once the first node in document order of the resulting set
     * has been found.
     * <br>If there are more than one node in the actual result, the single
     * node returned will be the first in document order.
     */
    short FIRST_ORDERED_NODE_TYPE = 9;

    /**
     * A code representing the type of this result, as defined by the type
     * constants.
     *
     * @return a short.
     */
    short getResultType();

    /**
     * The value of this number result. If the native double type of the DOM
     * binding does not directly support the exact IEEE 754 result of the
     * XPath expression, then it is up to the definition of the binding
     * binding to specify how the XPath number is converted to the native
     * binding number.
     *
     * @return a double.
     * @throws XPathException                              TYPE_ERR: raised if resultType is not
     *                                                     NUMBER_TYPE.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     */
    double getNumberValue()
            throws XPathException;

    /**
     * The value of this string result.
     *
     * @return a {@link java.lang.String} object.
     * @throws XPathException                              TYPE_ERR: raised if resultType is not
     *                                                     STRING_TYPE.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     */
    String getStringValue()
            throws XPathException;

    /**
     * The value of this boolean result.
     *
     * @return a boolean.
     * @throws XPathException                              TYPE_ERR: raised if resultType is not
     *                                                     BOOLEAN_TYPE.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     */
    boolean getBooleanValue()
            throws XPathException;

    /**
     * The value of this single node result, which may be null.
     *
     * @return a {@link org.loboevolution.html.node.Node} object.
     * @throws XPathException                              TYPE_ERR: raised if resultType is not
     *                                                     ANY_UNORDERED_NODE_TYPE or
     *                                                     FIRST_ORDERED_NODE_TYPE.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     */
    Node getSingleNodeValue()
            throws XPathException;

    /**
     * Signifies that the iterator has become invalid. True if
     * resultType is UNORDERED_NODE_ITERATOR_TYPE
     * or ORDERED_NODE_ITERATOR_TYPE and the document has been
     * modified since this result was returned.
     *
     * @return a boolean.
     */
    boolean getInvalidIteratorState();

    /**
     * The number of nodes in the result snapshot. Valid values for
     * snapshotItem indices are 0 to
     * snapshotLength-1 inclusive.
     *
     * @return a int.
     * @throws XPathException                              TYPE_ERR: raised if resultType is not
     *                                                     UNORDERED_NODE_SNAPSHOT_TYPE or
     *                                                     ORDERED_NODE_SNAPSHOT_TYPE.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     */
    int getSnapshotLength()
            throws XPathException;

    /**
     * Iterates and returns the next node from the node set or
     * nullif there are no more nodes.
     *
     * @return Returns the next node.
     * @throws XPathException                              TYPE_ERR: raised if resultType is not
     *                                                     UNORDERED_NODE_ITERATOR_TYPE or
     *                                                     ORDERED_NODE_ITERATOR_TYPE.
     * @throws DOMException                                INVALID_STATE_ERR: The document has been mutated since the result was
     *                                                     returned.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     * @throws DOMException   if any.
     */
    Node iterateNext()
            throws XPathException, DOMException;

    /**
     * Returns the indexth item in the snapshot collection. If
     * index is greater than or equal to the number of nodes in
     * the list, this method returns null. Unlike the iterator
     * result, the snapshot does not become invalid, but may not correspond
     * to the current document if it is mutated.
     *
     * @param index Index into the snapshot collection.
     * @return The node at the indexth position in the
     * NodeList, or null if that is not a valid
     * index.
     * @throws XPathException                              TYPE_ERR: raised if resultType is not
     *                                                     UNORDERED_NODE_SNAPSHOT_TYPE or
     *                                                     ORDERED_NODE_SNAPSHOT_TYPE.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     */
    Node snapshotItem(int index)
            throws XPathException;

}
