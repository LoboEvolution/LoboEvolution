/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.html.w3c.xpath;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/**
 * The Interface XPathResult.
 */
public interface XPathResult {

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
	 * @throws XPathException
	 *             the x path exception
	 * @throws DOMException
	 *             the DOM exception
	 */
	public Node iterateNext() throws XPathException, DOMException;

	/**
	 * Snapshot item.
	 *
	 * @param index
	 *            the index
	 * @return the node
	 * @throws XPathException
	 *             the x path exception
	 */
	public Node snapshotItem(int index) throws XPathException;
}
