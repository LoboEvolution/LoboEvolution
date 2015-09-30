/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.xpath;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;


/**
 * The Interface XPathResult.
 */
public interface XPathResult {
	
	/** The Constant ANY_TYPE. */
	// XPathResult
	public static final short ANY_TYPE = 0;
	
	/** The Constant NUMBER_TYPE. */
	public static final short NUMBER_TYPE = 1;
	
	/** The Constant STRING_TYPE. */
	public static final short STRING_TYPE = 2;
	
	/** The Constant BOOLEAN_TYPE. */
	public static final short BOOLEAN_TYPE = 3;
	
	/** The Constant UNORDERED_NODE_ITERATOR_TYPE. */
	public static final short UNORDERED_NODE_ITERATOR_TYPE = 4;
	
	/** The Constant ORDERED_NODE_ITERATOR_TYPE. */
	public static final short ORDERED_NODE_ITERATOR_TYPE = 5;
	
	/** The Constant UNORDERED_NODE_SNAPSHOT_TYPE. */
	public static final short UNORDERED_NODE_SNAPSHOT_TYPE = 6;
	
	/** The Constant ORDERED_NODE_SNAPSHOT_TYPE. */
	public static final short ORDERED_NODE_SNAPSHOT_TYPE = 7;
	
	/** The Constant ANY_UNORDERED_NODE_TYPE. */
	public static final short ANY_UNORDERED_NODE_TYPE = 8;
	
	/** The Constant FIRST_ORDERED_NODE_TYPE. */
	public static final short FIRST_ORDERED_NODE_TYPE = 9;

	/**
	 * Gets the result type.
	 *
	 * @return the result type
	 */
	public short getResultType();

	/**
	 * Gets the number value.
	 *
	 * @return the number value
	 * @throws XPathException
	 *             the x path exception
	 */
	public double getNumberValue() throws XPathException;

	/**
	 * Gets the string value.
	 *
	 * @return the string value
	 * @throws XPathException
	 *             the x path exception
	 */
	public String getStringValue() throws XPathException;

	/**
	 * Gets the boolean value.
	 *
	 * @return the boolean value
	 * @throws XPathException
	 *             the x path exception
	 */
	public boolean getBooleanValue() throws XPathException;

	/**
	 * Gets the single node value.
	 *
	 * @return the single node value
	 * @throws XPathException
	 *             the x path exception
	 */
	public Node getSingleNodeValue() throws XPathException;

	/**
	 * Gets the invalid iterator state.
	 *
	 * @return the invalid iterator state
	 */
	public boolean getInvalidIteratorState();

	/**
	 * Gets the snapshot length.
	 *
	 * @return the snapshot length
	 * @throws XPathException
	 *             the x path exception
	 */
	public int getSnapshotLength() throws XPathException;

	/**
	 * Iterate next.
	 *
	 * @return the node
	 * @throws XPathException the x path exception
	 * @throws DOMException the DOM exception
	 */
	public Node iterateNext() throws XPathException, DOMException;

	/**
	 * Snapshot item.
	 *
	 * @param index the index
	 * @return the node
	 * @throws XPathException the x path exception
	 */
	public Node snapshotItem(int index) throws XPathException;
}
