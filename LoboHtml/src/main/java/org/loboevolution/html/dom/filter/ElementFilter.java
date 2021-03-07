/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
/*
 * Created on Dec 3, 2005
 */
package org.loboevolution.html.dom.filter;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.NodeFilter;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

/**
 * <p>ElementFilter class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public final class ElementFilter implements NodeFilter {
	private final String elementName;

	/**
	 * <p>Constructor for ElementFilter.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public ElementFilter(String name) {
		this.elementName = name;
	}

	/** {@inheritDoc} */
	@Override
	public boolean acceptNode(Node node) {
		return Strings.isNotBlank(elementName) ? (this.elementName.equalsIgnoreCase(node.getNodeName())) : (node instanceof Element);
	}
}
