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

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLOptionElement;
import org.loboevolution.html.dom.HTMLOptionsCollection;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.traversal.NodeFilter;

import java.util.ArrayList;
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
	 * @param filter a {@link org.loboevolution.html.node.traversal.NodeFilter} object.
	 */
	public HTMLOptionsCollectionImpl(NodeImpl rootNode, NodeFilter filter) {
		super(rootNode, filter);
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
			HTMLOptionElement element = (HTMLOptionElement) n;
			if ((element.isSelected() != null && element.isSelected()) || element.hasAttribute("selected")) {
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
			this.selectedIndex = null;
		} else {
			this.selectedIndex = selectedIndex;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void add(Object element, Object before) throws DOMException {
		if (element instanceof HTMLOptionElement){
			if (before instanceof HTMLElement) {
				addElements((HTMLOptionElement)element, (HTMLOptionElement)before);
			}

			if (before instanceof Double) {
				HTMLSelectElementImpl selctElement = (HTMLSelectElementImpl) rootNode;
				double d = (double) before;

				if (d > -1 && d < getLength()) {
					addElementIndex((HTMLOptionElementImpl) element, d);
				} else if (d < getLength() && selctElement.isMultiple()) {
					addElementIndex((HTMLOptionElementImpl) element, d);
				} else if (d == getLength()) {
					addElementIndex((HTMLOptionElementImpl) element, d);
				} else if (d > getLength()) {
					addElementIndex((HTMLOptionElementImpl) element, getLength());
				} else if (d < getLength() && !selctElement.isMultiple()) {
					addElementIndex((HTMLOptionElementImpl) element, getLength());
				} else {
					System.out.println("not found");
				}
			}

			if (before == null) {
				addElementIndex((HTMLOptionElementImpl)element, getLength());
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void add(HTMLOptionElement element) {
		HTMLSelectElementImpl selctElement = (HTMLSelectElementImpl) rootNode;
		List<Node> nodeList = getList();
		if (nodeList.size() == 0 && !selctElement.isMultiple()) element.setSelected(true);
		nodeList.add(element);
	}

	/** {@inheritDoc} */
	@Override
	public boolean remove(Object element) {
		try {
			HTMLSelectElementImpl selctElement = (HTMLSelectElementImpl) rootNode;

			if (element instanceof HTMLOptionElementImpl) {
				getList().remove(element);
			}

			if (element instanceof Double) {
				double d = (Double) element;
				if (d > -1 && d < getList().size())
					getList().remove((int) d);
			}

			if (selctElement == null || !selctElement.isMultiple()) {
				List<Node> list = getList();
				for (int i = 0; i < list.size(); i++) {
					HTMLOptionElementImpl opt = (HTMLOptionElementImpl) list.get(i);
					opt.setSelected(i == 0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

    @Override
	public void setItem(Integer index, Node node) {
		HTMLSelectElementImpl selctElement = (HTMLSelectElementImpl) rootNode;
		if (node != null && index > -1) {
			List<Node> nodeList = getList();
			if (nodeList.size() < index) {
				for (int i = nodeList.size(); i < index; i++) {
					HTMLOptionElementImpl opt = new HTMLOptionElementImpl("", "");
					opt.setParentImpl(selctElement);
					super.setItem(i, opt);
				}
				List<Node> list = new ArrayList<>(nodeList);
				((NodeImpl) node).setParentImpl(selctElement);
				list.add(node);
				setList(list);
			} else {
				List<Node> list;
				if (index == 0 && nodeList.size() == 0) {
					list = new ArrayList<>();
					((NodeImpl) node).setParentImpl(selctElement);
					list.add(node);
				} else if (nodeList.size() == index) {
					list = new ArrayList<>(nodeList);
					((NodeImpl) node).setParentImpl(selctElement);
					list.add(node);
				} else {
					list = new ArrayList<>(nodeList);
					((NodeImpl) node).setParentImpl(selctElement);
					list.set(index, node);
				}
				setList(list);
			}
		}

		if (node != null) {
			if (!selctElement.isMultiple() && index < 2) {
				List<Node> list = getList();
				for (int i = 0; i < list.size(); i++) {
					HTMLOptionElementImpl opt = (HTMLOptionElementImpl) list.get(i);
					opt.setSelected(i == 0);
				}
			}
		} else{
			remove(index.doubleValue());
		}
	}

	@Override
	public int getLength() {
		return this.size();
	}

	private void addElementIndex(HTMLOptionElement element, double before) {
		List<Node> nodeList = getList();
		if (before > nodeList.size() || before < 0) {
			add(element);
		} else {
			HTMLSelectElementImpl selctElement = (HTMLSelectElementImpl) rootNode;
			if (nodeList.size() == 0 && !selctElement.isMultiple()) {
				element.setSelected(true);
				((NodeImpl)element).setParentImpl(selctElement);
				nodeList.add(element);
			} else	{
				((NodeImpl)element).setParentImpl(selctElement);
				nodeList.add(before < 0 ? 0 : (int) before, element);
			}
		}
	}

	private void addElements(HTMLOptionElement element, HTMLOptionElement before) throws DOMException {
		List<Node> nodeList = getList();
		HTMLSelectElementImpl selctElement = (HTMLSelectElementImpl) rootNode;
		boolean found = false;
		for (int i = 0; i < nodeList.size(); i++) {
			HTMLOptionElement elem = (HTMLOptionElement) nodeList.get(i);
			if (elem.getText().equals(before.getText())) {
				((NodeImpl) element).setParentImpl(selctElement);
				nodeList.add(i, element);
				found = true;
				break;
			}
		}
		if (!found)
			throw new DOMException(DOMException.NOT_FOUND_ERR, "Record not found");

	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLOptionsCollection]";
	}
}
