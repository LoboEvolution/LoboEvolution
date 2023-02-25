/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

import com.gargoylesoftware.css.dom.DOMException;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLOptionElement;
import org.loboevolution.html.dom.HTMLOptionsCollection;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.node.Node;

import java.util.List;

/**
 * <p>HTMLOptionsCollectionImpl class.</p>
 */
public class HTMLOptionsCollectionImpl extends HTMLCollectionImpl implements HTMLOptionsCollection {

	private final NodeImpl rootNode;

	private Integer selectedIndex = null;

	/**
	 * <p>Constructor for HTMLOptionsCollectionImpl.</p>
	 *
	 * @param rootNode a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 * @param nodeList a {@link java.util.List} object.
	 */
	public HTMLOptionsCollectionImpl(NodeImpl rootNode, List<Node> nodeList) {
		super(rootNode, nodeList);
		this.rootNode = rootNode;
	}

	/** {@inheritDoc} */
	@Override
	public void setLength(int length) {
		if (length == 0) clear();
	}

	/** {@inheritDoc} */
	@Override
	public int getSelectedIndex() {
		if (selectedIndex != null) return this.selectedIndex;
		if (this.getLength() == 0) return -1;

		HTMLSelectElementImpl selctElement = (HTMLSelectElementImpl) this.rootNode;
		int index = selctElement.isMultiple() ? -1 : 0;
		for (int i = 0; i < this.getLength(); i++) {
			Node n = item(i);
			HTMLElementImpl element = (HTMLElementImpl) n;
			if (element.getAttributeAsBoolean("selected")) {
				index = i;
				break;
			}
		}
		return index;
	}

	/** {@inheritDoc} */
	@Override
	public void setSelectedIndex(int selectedIndex) {
		if (getLength() <= selectedIndex || selectedIndex < 0) {
			this.selectedIndex = -1;
		} else {
			this.selectedIndex = selectedIndex;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void add(Object element, Object before) {

		if (element instanceof HTMLOptionElementImpl && before instanceof HTMLElement) {
			addElements((HTMLOptionElementImpl)element, (HTMLElement)before);
		}

		if (element instanceof HTMLOptionElementImpl && before instanceof Double) {
			double d = (double) before;
			addElementIndex((HTMLOptionElementImpl)element, d);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void add(HTMLOptionElement element) {
		List<Node> nodeList = getList();
		if (nodeList.size() == 0) element.setSelected(true);
		nodeList.add(element);
	}

	/** {@inheritDoc} */
	@Override
	public boolean remove(Object element) {
		try{
		HTMLSelectElementImpl selctElement = (HTMLSelectElementImpl)rootNode;

		if (element instanceof HTMLOptionElementImpl) {
			getList().remove(element);
		}

		if (element instanceof Double) {
			double d = (Double) element;
			if (d < getList().size())
				getList().remove((int) d);
		}

		if (getList().size() == 1 && (selctElement == null || !selctElement.isMultiple())) {
			List<Node> list = getList();
			for (int i = 0; i < list.size(); i++) {
				HTMLOptionElementImpl opt = (HTMLOptionElementImpl) list.get(i);
				opt.setSelected(i == 0);
			}
		}
		}catch (Exception e) {e.printStackTrace();}

		return false;
	}



	private void addElementIndex(HTMLOptionElement element, double before) {
		List<Node> nodeList = getList();
		if (before > nodeList.size() || before < 0) {
			add(element);
		} else {
			if (nodeList.size() == 0) {
				element.setSelected(true);
				nodeList.add(element);
			} else	{
				nodeList.add(before < 0 ? 0 : (int) before, element);
			}
		}
	}

	private void addElements(HTMLOptionElement element, HTMLElement before) {
		List<Node> nodeList = getList();
		if (nodeList.size() == 0) {
			nodeList.add(0, element);
		} else {
			boolean found = false;
			HTMLOptionElement bef = (HTMLOptionElement) before;
			for (int i = 0; i < nodeList.size(); i++) {
				HTMLOptionElement elem = (HTMLOptionElement) nodeList.get(i);
				if (elem.getText().equals(bef.getText())) {
					nodeList.add(i, element);
					found = true;
					break;
				}
			}
			if (!found)
				throw new DOMException(DOMException.NOT_FOUND_ERR, "Record not found");
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLOptionsCollection]";
	}
}
