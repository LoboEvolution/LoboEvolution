/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Dec 3, 2005
 */
package org.lobobrowser.html.domfilter;

import org.w3c.dom.Node;

/**
 * The Class ElementTableAttributeFilter.
 */
public final class ElementTableAttributeFilter implements NodeFilter {

	/** The element name. */
	private final String elementName;

	/**
	 * Instantiates a new element table attribute filter.
	 *
	 * @param name
	 *            the name
	 */
	public ElementTableAttributeFilter(String name) {
		super();
		elementName = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domfilter.NodeFilter#accept(org.w3c.dom.Node)
	 */
	@Override
	public final boolean accept(Node node) {
		return this.elementName.equalsIgnoreCase(node.getNodeName());
	}
}
