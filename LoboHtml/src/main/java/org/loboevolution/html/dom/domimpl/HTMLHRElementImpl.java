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

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLHRElement;
import org.loboevolution.html.node.Element;

/**
 * <p>HTMLHRElementImpl class.</p>
 */
public class HTMLHRElementImpl extends HTMLElementImpl implements HTMLHRElement {
	/**
	 * <p>Constructor for HTMLHRElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLHRElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNoShade() {
		return "noshade".equalsIgnoreCase(getAttribute("noshade"));
	}

	/** {@inheritDoc} */
	@Override
	public String getSize() {
		return getAttribute("size");
	}

	/** {@inheritDoc} */
	@Override
	public String getWidth() {
		return getAttribute("width");
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	@Override
	public String getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColor(String color) {
		// TODO Auto-generated method stub
	}

	/** {@inheritDoc} */
	@Override
	public void setNoShade(boolean noShade) {
		setAttribute("noshade", noShade ? "noshade" : null);
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(String size) {
		setAttribute("size", size);
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLHRElement]";
	}
}
