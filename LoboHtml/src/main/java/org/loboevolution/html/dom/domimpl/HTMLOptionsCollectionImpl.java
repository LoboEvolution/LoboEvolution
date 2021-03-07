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

import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLOptGroupElement;
import org.loboevolution.html.dom.HTMLOptionElement;
import org.loboevolution.html.dom.HTMLOptionsCollection;
import org.loboevolution.html.dom.filter.OptionFilter;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

/**
 * <p>HTMLOptionsCollectionImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLOptionsCollectionImpl implements HTMLOptionsCollection {

	private final NodeImpl rootNode;

	private NodeListImpl rootList = null;

	/**
	 * <p>Constructor for HTMLOptionsCollectionImpl.</p>
	 *
	 * @param rootNode a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	public HTMLOptionsCollectionImpl(NodeImpl rootNode) {
		this.rootNode = rootNode;
		rootList = (NodeListImpl) rootNode.getNodeList(new OptionFilter());
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		if (rootList == null) {
			return this.rootNode.getChildCount();
		} else {
			return this.rootList.getLength();
		}
	}

	/** {@inheritDoc} */
	@Override
	public Node item(int index) {
		if (rootList == null) {
			return this.rootNode.getChildAtIndex(index);
		} else {
			return this.rootList.get(index);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Element namedItem(String name) {
		final Document doc = this.rootNode.getOwnerDocument();
		if (doc == null) {
			return null;
		}
		final Element node = doc.getElementById(name);
		if (node != null && node.getParentNode() == this.rootNode) {
			return node;
		}
		return null;
	}
	
	/**
	 * <p>indexOf.</p>
	 *
	 * @param node a {@link org.w3c.dom.Node} object.
	 * @return a int.
	 */
	public int indexOf(Node node) {
		if (rootList == null) {
			return this.rootNode.getChildIndex(node);
		} else {
			return this.rootList.indexOf(node);
		}
	}
	
	/**
	 * <p>Getter for the field rootList.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.nodeimpl.NodeListImpl} object.
	 */
	protected NodeListImpl getRootList() {
		return rootList;
	}

	@Override
	public void setLength(int length) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSelectedIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSelectedIndex(int selectedIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(HTMLOptGroupElement element, HTMLElement before) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(HTMLOptGroupElement element, int before) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(HTMLOptGroupElement element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(HTMLOptionElement element, HTMLElement before) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(HTMLOptionElement element, int before) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(HTMLOptionElement element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(int index) {
		// TODO Auto-generated method stub
		
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLOptionsCollection]";
	}
}
