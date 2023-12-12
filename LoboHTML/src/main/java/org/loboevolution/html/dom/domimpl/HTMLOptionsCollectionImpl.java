/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.domimpl;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class HTMLOptionsCollectionImpl extends HTMLCollectionImpl implements HTMLOptionsCollection {

	private final NodeImpl rootNode;

	private Integer selectedIndex = null;

	/**
	 * <p>Constructor for HTMLOptionsCollectionImpl.</p>
	 *
	 * @param rootNode a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 * @param filter a {@link org.loboevolution.html.node.traversal.NodeFilter} object.
	 */
	public HTMLOptionsCollectionImpl(final NodeImpl rootNode, final NodeFilter filter) {
		super(rootNode, filter);
		this.rootNode = rootNode;
	}

	/** {@inheritDoc} */
	@Override
	public void setLength(final int length) {
		if (length == 0) clear();
	}

	/** {@inheritDoc} */
	@Override
	public int getSelectedIndex() {
		if (selectedIndex != null) return this.selectedIndex;
		if (this.getLength() == 0) return -1;

		final HTMLSelectElementImpl selctElement = (HTMLSelectElementImpl) this.rootNode;
		int index = selctElement.isMultiple() ? -1 : 0;
		for (int i = 0; i < this.getLength(); i++) {
			final Node n = item(i);
			final HTMLOptionElement element = (HTMLOptionElement) n;
			if ((element.isSelected() != null && element.isSelected()) || element.hasAttribute("selected")) {
				index = i;
				break;
			}
		}
		return index;
	}

	/** {@inheritDoc} */
	@Override
	public void setSelectedIndex(final int selectedIndex) {
		if (getLength() <= selectedIndex || selectedIndex < 0) {
			this.selectedIndex = null;
		} else {
			this.selectedIndex = selectedIndex;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void add(final Object element, final Object before) throws DOMException {
		if (element instanceof HTMLOptionElement) {
			if (before instanceof HTMLElement) {
				addElements((HTMLOptionElement) element, (HTMLOptionElement) before);
			}

			if (before instanceof Double) {
				final HTMLSelectElementImpl selctElement = (HTMLSelectElementImpl) rootNode;
				final double d = (double) before;

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
					log.info("not found");
				}
			}

			if (before == null) {
				addElementIndex((HTMLOptionElementImpl) element, getLength());
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void add(final HTMLOptionElement element) {
		final HTMLSelectElementImpl selctElement = (HTMLSelectElementImpl) rootNode;
		final List<Node> nodeList = getList();
		if (nodeList.size() == 0 && !selctElement.isMultiple()) element.setSelected(true);
		nodeList.add(element);
	}

	/** {@inheritDoc} */
	@Override
	public boolean remove(final Object element) {
		try {
			final HTMLSelectElementImpl selctElement = (HTMLSelectElementImpl) rootNode;

			if (element instanceof HTMLOptionElementImpl) {
				getList().remove(element);
			}

			if (element instanceof Double) {
				final double d = (Double) element;
				if (d > -1 && d < getList().size())
					getList().remove((int) d);
			}

			if (selctElement == null || !selctElement.isMultiple()) {
				final List<Node> list = getList();
				for (int i = 0; i < list.size(); i++) {
					final HTMLOptionElementImpl opt = (HTMLOptionElementImpl) list.get(i);
					opt.setSelected(i == 0);
				}
			}
		} catch (final Exception ex) {
			log.error(ex.getMessage(), ex);
		}

		return false;
	}

    @Override
	public void setItem(final Integer index, final Node node) {
		final HTMLSelectElementImpl selctElement = (HTMLSelectElementImpl) rootNode;
		if (node != null && index > -1) {
			final List<Node> nodeList = getList();
			if (nodeList.size() < index) {
				for (int i = nodeList.size(); i < index; i++) {
					final HTMLOptionElementImpl opt = new HTMLOptionElementImpl("", "");
					opt.setParentImpl(selctElement);
					super.setItem(i, opt);
				}
				final List<Node> list = new ArrayList<>(nodeList);
				((NodeImpl) node).setParentImpl(selctElement);
				list.add(node);
				setList(list);
			} else {
				final List<Node> list;
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
				final List<Node> list = getList();
				for (int i = 0; i < list.size(); i++) {
					final HTMLOptionElementImpl opt = (HTMLOptionElementImpl) list.get(i);
					opt.setSelected(i == 0);
				}
			}
		} else {
			remove(index.doubleValue());
		}
	}

	@Override
	public int getLength() {
		return this.size();
	}

	private void addElementIndex(final HTMLOptionElement element, final double before) {
		final List<Node> nodeList = getList();
		if (before > nodeList.size() || before < 0) {
			add(element);
		} else {
			final HTMLSelectElementImpl selctElement = (HTMLSelectElementImpl) rootNode;
			if (nodeList.size() == 0 && !selctElement.isMultiple()) {
				element.setSelected(true);
				((NodeImpl) element).setParentImpl(selctElement);
				nodeList.add(element);
			} else {
				((NodeImpl) element).setParentImpl(selctElement);
				nodeList.add(before < 0 ? 0 : (int) before, element);
			}
		}
	}

	private void addElements(final HTMLOptionElement element, final HTMLOptionElement before) throws DOMException {
		final List<Node> nodeList = getList();
		final HTMLSelectElementImpl selctElement = (HTMLSelectElementImpl) rootNode;
		boolean found = false;
		for (int i = 0; i < nodeList.size(); i++) {
			final HTMLOptionElement elem = (HTMLOptionElement) nodeList.get(i);
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
