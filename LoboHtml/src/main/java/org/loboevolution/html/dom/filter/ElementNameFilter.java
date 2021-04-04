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
 * The Class ElementNameFilter.
 *
 *
 *
 */
public class ElementNameFilter implements NodeFilter {

	/** The name. */
	private final String name;

	/**
	 * Instantiates a new element name filter.
	 *
	 * @param name
	 *            the name
	 */
	public ElementNameFilter(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domfilter.NodeFilter#accept(org.w3c.dom.Node)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean acceptNode(Node node) {
		if(node instanceof Element) {
			Element elm = (Element)node;
			if(elm != null && this.name != null) {
				return this.name.equals(elm.getAttribute("name"));
			}
		}		
		return  false;
	}
}
