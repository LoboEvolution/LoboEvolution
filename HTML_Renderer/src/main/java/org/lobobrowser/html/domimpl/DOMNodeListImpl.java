/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
 * Created on Sep 3, 2005
 */
package org.lobobrowser.html.domimpl;

import java.util.ArrayList;
import java.util.Collection;

import org.lobobrowser.js.AbstractScriptableDelegate;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class DOMNodeListImpl.
 */
public class DOMNodeListImpl extends AbstractScriptableDelegate implements NodeList {
	// Note: class must be public for reflection to work.
	/** The node list. */
	private final ArrayList nodeList = new ArrayList();

	/**
	 * Instantiates a new DOM node list impl.
	 *
	 * @param collection
	 *            the collection
	 */
	public DOMNodeListImpl(Collection collection) {
		super();
		nodeList.addAll(collection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.NodeList#getLength()
	 */
	@Override
	public int getLength() {
		return this.nodeList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.NodeList#item(int)
	 */
	@Override
	public Node item(int index) {
		try {
			return (Node) this.nodeList.get(index);
		} catch (IndexOutOfBoundsException iob) {
			return null;
		}
	}
}
