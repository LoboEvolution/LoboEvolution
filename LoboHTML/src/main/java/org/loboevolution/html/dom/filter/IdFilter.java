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
package org.loboevolution.html.dom.filter;

import org.loboevolution.html.dom.NodeFilter;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

/**
 * <p>IdFilter class.</p>
 *
 *
 *
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
	public boolean acceptNode(Node node) {
		return (node instanceof Element) && this._id != null && 
				this._id.equals(((Element) node).getAttribute("id"));
	}
}
