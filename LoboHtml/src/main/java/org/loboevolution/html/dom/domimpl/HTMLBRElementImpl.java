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

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLBRElement;

/**
 * <p>HTMLBRElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLBRElementImpl extends HTMLElementImpl implements HTMLBRElement {
	/**
	 * <p>Constructor for HTMLBRElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLBRElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	protected void appendInnerTextImpl(StringBuilder buffer) {
		buffer.append("\r\n");
		super.appendInnerTextImpl(buffer);
	}

	/** {@inheritDoc} */
	@Override
	public String getClear() {
		return getAttribute("clear");
	}

	/** {@inheritDoc} */
	@Override
	public void setClear(String clear) {
		setAttribute("clear", clear);
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLBRElement]";
	}
}
