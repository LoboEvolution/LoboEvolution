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

package org.loboevolution.html.xpath;

import org.loboevolution.html.node.Node;
import org.w3c.dom.DOMException;
import org.w3c.dom.xpath.XPathException;

/**
 * The XPathResult interface represents the result of the
 * evaluation of an XPath 1.0 expression within the context of a particular
 * node. Since evaluation of an XPath expression can result in various
 * result types, this object makes it possible to discover and manipulate
 * the type and value of the result.
 * <p>See also the <a href='http://www.w3.org/2002/08/WD-DOM-Level-3-XPath-20020820'>Document Object Model (DOM) Level 3 XPath Specification</a>.
 *
 *
 *
 */
public interface XPathResult {
    // XPathResultType
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
    public static final short ANY_TYPE                  = 0;
    /**
     * The result is a number as defined by . Document modification does not
     * invalidate the number, but may mean that reevaluation would not yield
     * the same number.
     */
    public static final short NUMBER_TYPE               = 1;
    /**
     * The result is a string as defined by . Document modification does not
     * invalidate the string, but may mean that the string no longer
     * corresponds to the current document.
     */
    public static final short STRING_TYPE               = 2;
    /**
     * The result is a boolean as defined by . Document modification does not
     * invalidate the boolean, but may mean that reevaluation would not
     * yield the same boolean.
     */
    public static final short BOOLEAN_TYPE              = 3;
    /**
     * The result is a node set as defined by  that will be accessed
     * iteratively, which may not produce nodes in a particular order.
     * Document modification invalidates the iteration.
     * <br>This is the default type returned if the result is a node set and
     * ANY_TYPE is requested.
     */
    public static final short UNORDERED_NODE_ITERATOR_TYPE = 4;
    /**
     * The result is a node set as defined by  that will be accessed
     * iteratively, which will produce document-ordered nodes. Document
     * modification invalidates the iteration.
     */
    public static final short ORDERED_NODE_ITERATOR_TYPE = 5;
    /**
     * The result is a node set as defined by  that will be accessed as a
     * snapshot list of nodes that may not be in a particular order.
     * Document modification does not invalidate the snapshot but may mean
     * that reevaluation would not yield the same snapshot and nodes in the
     * snapshot may have been altered, moved, or removed from the document.
     */
    public static final short UNORDERED_NODE_SNAPSHOT_TYPE = 6;
    /**
     * The result is a node set as defined by  that will be accessed as a
     * snapshot list of nodes that will be in original document order.
     * Document modification does not invalidate the snapshot but may mean
     * that reevaluation would not yield the same snapshot and nodes in the
     * snapshot may have been altered, moved, or removed from the document.
     */
    public static final short ORDERED_NODE_SNAPSHOT_TYPE = 7;
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
    public static final short ANY_UNORDERED_NODE_TYPE   = 8;
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
    public static final short FIRST_ORDERED_NODE_TYPE   = 9;

    /**
     * A code representing the type of this result, as defined by the type
     * constants.
     *
     * @return a short.
     */
    public short getResultType();

    /**
     * The value of this number result. If the native double type of the DOM
     * binding does not directly support the exact IEEE 754 result of the
     * XPath expression, then it is up to the definition of the binding
     * binding to specify how the XPath number is converted to the native
     * binding number.
     *
     * @exception XPathException
     *   TYPE_ERR: raised if resultType is not
     *   NUMBER_TYPE.
     * @return a double.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     * @throws org.w3c.dom.xpath.XPathException if any.
     * @throws org.w3c.dom.xpath.XPathException if any.
     */
    public double getNumberValue()
                             throws XPathException;

    /**
     * The value of this string result.
     *
     * @exception XPathException
     *   TYPE_ERR: raised if resultType is not
     *   STRING_TYPE.
     * @return a {@link java.lang.String} object.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     * @throws org.w3c.dom.xpath.XPathException if any.
     * @throws org.w3c.dom.xpath.XPathException if any.
     */
    public String getStringValue()
                             throws XPathException;

    /**
     * The value of this boolean result.
     *
     * @exception XPathException
     *   TYPE_ERR: raised if resultType is not
     *   BOOLEAN_TYPE.
     * @return a boolean.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     * @throws org.w3c.dom.xpath.XPathException if any.
     * @throws org.w3c.dom.xpath.XPathException if any.
     */
    public boolean getBooleanValue()
                             throws XPathException;

    /**
     * The value of this single node result, which may be null.
     *
     * @exception XPathException
     *   TYPE_ERR: raised if resultType is not
     *   ANY_UNORDERED_NODE_TYPE or
     *   FIRST_ORDERED_NODE_TYPE.
     * @return a {@link org.loboevolution.html.node.Node} object.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     * @throws org.w3c.dom.xpath.XPathException if any.
     * @throws org.w3c.dom.xpath.XPathException if any.
     */
    public Node getSingleNodeValue()
                             throws XPathException;

    /**
     * Signifies that the iterator has become invalid. True if
     * resultType is UNORDERED_NODE_ITERATOR_TYPE
     * or ORDERED_NODE_ITERATOR_TYPE and the document has been
     * modified since this result was returned.
     *
     * @return a boolean.
     */
    public boolean getInvalidIteratorState();

    /**
     * The number of nodes in the result snapshot. Valid values for
     * snapshotItem indices are 0 to
     * snapshotLength-1 inclusive.
     *
     * @exception XPathException
     *   TYPE_ERR: raised if resultType is not
     *   UNORDERED_NODE_SNAPSHOT_TYPE or
     *   ORDERED_NODE_SNAPSHOT_TYPE.
     * @return a int.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     * @throws org.w3c.dom.xpath.XPathException if any.
     * @throws org.w3c.dom.xpath.XPathException if any.
     */
    public int getSnapshotLength()
                             throws XPathException;

    /**
     * Iterates and returns the next node from the node set or
     * nullif there are no more nodes.
     *
     * @return Returns the next node.
     * @exception XPathException
     *   TYPE_ERR: raised if resultType is not
     *   UNORDERED_NODE_ITERATOR_TYPE or
     *   ORDERED_NODE_ITERATOR_TYPE.
     * @exception DOMException
     *   INVALID_STATE_ERR: The document has been mutated since the result was
     *   returned.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     * @throws org.w3c.dom.DOMException if any.
     * @throws org.w3c.dom.DOMException if any.
     * @throws org.w3c.dom.xpath.XPathException if any.
     * @throws org.w3c.dom.DOMException if any.
     * @throws org.w3c.dom.DOMException if any.
     * @throws org.w3c.dom.xpath.XPathException if any.
     * @throws org.w3c.dom.DOMException if any.
     */
    public Node iterateNext()
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
     *   NodeList, or null if that is not a valid
     *   index.
     * @exception XPathException
     *   TYPE_ERR: raised if resultType is not
     *   UNORDERED_NODE_SNAPSHOT_TYPE or
     *   ORDERED_NODE_SNAPSHOT_TYPE.
     * @throws org.loboevolution.html.xpath.XPathException if any.
     * @throws org.w3c.dom.xpath.XPathException if any.
     * @throws org.w3c.dom.xpath.XPathException if any.
     */
    public Node snapshotItem(int index)
                             throws XPathException;

}
