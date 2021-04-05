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
/*
 * Created on Oct 29, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLHeadElement;

/**
 * <p>HTMLHeadElementImpl class.</p>
 *
 *
 *
 */
public class HTMLHeadElementImpl extends HTMLElementImpl implements HTMLHeadElement {

	/**
	 * <p>Constructor for HTMLHeadElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLHeadElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public String getProfile() {
		return this.getAttribute("profile");
	}

	/** {@inheritDoc} */
	@Override
	public void setProfile(String profile) {
		this.setAttribute("profile", profile);
	}	
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLHeadElement]";
	}
}

