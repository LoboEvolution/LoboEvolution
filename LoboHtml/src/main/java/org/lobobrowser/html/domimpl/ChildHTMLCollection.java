/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Dec 3, 2005
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.dom.HTMLCollection;
import org.lobobrowser.js.AbstractScriptableDelegate;
import org.w3c.dom.Node;

public class ChildHTMLCollection extends AbstractScriptableDelegate implements HTMLCollection {
	private final NodeImpl rootNode;

	/**
	 * @param node
	 */
	public ChildHTMLCollection(NodeImpl node) {
		super();
		this.rootNode = node;
	}

	@Override
	public int getLength() {
		return this.rootNode.getChildCount();
	}

	public int indexOf(Node node) {
		return this.rootNode.getChildIndex(node);
	}

	@Override
	public Node item(int index) {
		return this.rootNode.getChildAtIndex(index);
	}

	@Override
	public Node namedItem(String name) {
		final org.w3c.dom.Document doc = this.rootNode.getOwnerDocument();
		if (doc == null) {
			return null;
		}
		// TODO: This might get elements that are not descendents.
		final Node node = doc.getElementById(name);
		if (node != null && node.getParentNode() == this.rootNode) {
			return node;
		}
		return null;
	}
}
