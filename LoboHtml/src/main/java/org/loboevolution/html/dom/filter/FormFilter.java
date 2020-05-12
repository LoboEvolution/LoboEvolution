/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.filter;

import org.loboevolution.html.dom.NodeFilter;
import org.w3c.dom.Node;

/**
 * The Class FormFilter.
 *
 * @author utente
 * @version $Id: $Id
 */
public class FormFilter implements NodeFilter {

	/** {@inheritDoc} */
	@Override
	public boolean accept(Node node) {
		String nodeName = node.getNodeName();
		return "form".equalsIgnoreCase(nodeName);
	}
}
