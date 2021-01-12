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
package org.loboevolution.html.dom.filter;

import org.loboevolution.html.dom.NodeFilter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <p>ClassNameFilter class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ClassNameFilter implements NodeFilter {
	private final String _class;

	/**
	 * <p>Constructor for ClassNameFilter.</p>
	 *
	 * @param _class a {@link java.lang.String} object.
	 */
	public ClassNameFilter(String _class) {
		this._class = _class;
	}

	/** {@inheritDoc} */
	public boolean accept(Node node) {
		return (node instanceof Element)
				&& this._class.equals(((Element) node).getAttribute("class"));
	}
}
