/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
package org.loboevolution.html.dom.filter;

import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.traversal.NodeFilter;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

/**
 * <p>IdFilter class.</p>
 */
public class IdFilter implements NodeFilter {
	private final String _id;

	/**
	 * <p>Constructor for IdFilter.</p>
	 *
	 * @param _id a {@link java.lang.String} object.
	 */
	public IdFilter(String _id) {
		this._id = _id;
	}

	/** {@inheritDoc} */
	@Override
	public short acceptNode(Node node) {
		if ((node instanceof Element) && this._id != null) {
			NamedNodeMap attributes = node.getAttributes();
			for (Node attribute : Nodes.iterable(attributes)) {
				Attr attr = (Attr) attribute;
				if (Strings.isNotBlank(attr.getNodeValue())) {
					if (this._id.equals(attr.getNodeValue()) && attr.isId()) {
						return NodeFilter.FILTER_ACCEPT;
					}
				}
			}
		}

		return NodeFilter.FILTER_REJECT;
	}
}