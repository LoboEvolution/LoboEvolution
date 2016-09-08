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
package org.lobobrowser.html.style.selectors;

import org.lobobrowser.html.domimpl.DOMNodeImpl;

/**
 * The Class SelectorMatcher.
 */
public class SelectorMatcher {

	public SelectorMatcher() {
	}

	/**
	 * Matches the given pseudo class selector against the given node.
	 *
	 * @param selector
	 *            The pseudo class selector.
	 * @param node
	 *            The root node.
	 * @return {@code true} or {@code false}
	 */
	public boolean matchesPseudoClassSelector(String selector, DOMNodeImpl node) {

		if (selector != null && selector.contains("(")) {
			selector = selector.substring(0, selector.indexOf("("));
		} else if (selector == null) {
			return false;
		}

		switch (selector) {
		case "first-child":
			return matchesFirstOrLastChild(node, true);
		case "last-child":
			return matchesFirstOrLastChild(node, false);
		case "only-child":
			return matchesFirstOrLastChild(node, true) && matchesFirstOrLastChild(node, false);
		case "first-of-type":
			return matchesNthChild(node, 0, 1, true, false);
		case "last-of-type":
			return matchesNthChild(node, 0, 1, true, true);
		case "only-of-type":
			return matchesNthChild(node, 0, 1, true, false) && matchesNthChild(node, 0, 1, true, true);
		case "root":
			DOMNodeImpl parentDOMNodeImpl = (DOMNodeImpl) node.getParentNode();
			return parentDOMNodeImpl != null && parentDOMNodeImpl.getNodeType() == DOMNodeImpl.DOCUMENT_TYPE_NODE;
		case "empty":
			for (DOMNodeImpl child = (DOMNodeImpl) node.getFirstChild(); child != null; child = (DOMNodeImpl) child
					.getNextSibling()) {
				switch (child.getNodeType()) {
				case DOMNodeImpl.ELEMENT_NODE:
					return false;
				case DOMNodeImpl.TEXT_NODE:
					String data = child.getNodeName();
					if (data != null && !data.isEmpty()) {
						return false;
					}
				}
			}

			return true;
		case "nth-child":
		case "nth-last-child":
		case "nth-of-type":
		case "nth-last-of-type":
			return false;
		default:
			return false;
		}
	}

	/**
	 * Matches a first or last child.
	 *
	 * @param node
	 *            The root node.
	 * @param first
	 *            If matching is performed against the first child.
	 * @return {@code true} or {@code false}
	 */
	private boolean matchesFirstOrLastChild(DOMNodeImpl node, boolean first) {
		while (true) {
			DOMNodeImpl n;
			if (first) {
				n = (DOMNodeImpl) node.getPreviousSibling();
			} else {
				n = (DOMNodeImpl) node.getNextSibling();
			}

			if (n == null) {
				n = (DOMNodeImpl) node.getParentNode();
				return n != null && n.getNodeType() != DOMNodeImpl.DOCUMENT_TYPE_NODE;
			} else {
				if (n.getNodeType() == DOMNodeImpl.ELEMENT_NODE) {
					return false;
				}

				node = n;
			}
		}
	}

	/**
	 * Matches the <i>nth</i> child.
	 *
	 * @param node
	 *            The root node.
	 * @param a
	 *            The <i>A</i> argument.
	 * @param b
	 *            The <i>B</i> argument.
	 * @param isOfType
	 *            If the matching is performed for a {@code -of-type} selector.
	 * @param fromEnd
	 *            If matching is performed from the end.
	 * @return {@code true} or {@code false}
	 */
	private boolean matchesNthChild(DOMNodeImpl node, int a, int b, boolean isOfType, boolean fromEnd) {
		DOMNodeImpl parentDOMNodeImpl = (DOMNodeImpl) node.getParentNode();
		if (parentDOMNodeImpl == null || parentDOMNodeImpl.getNodeType() == DOMNodeImpl.DOCUMENT_TYPE_NODE) {
			return false;
		}

		DOMNodeImpl n = node;
		int i = 1;
		while (true) {
			DOMNodeImpl sibling;
			if (fromEnd) {
				sibling = (DOMNodeImpl) n.getNextSibling();
			} else {
				sibling = (DOMNodeImpl) n.getPreviousSibling();
			}

			if (sibling == null) {
				break;
			}

			n = sibling;
			if (n.getNodeType() == DOMNodeImpl.ELEMENT_NODE) {
				if (isOfType) {
					if (node.getNodeName().equals(n.getNodeName())) {
						++i;
					}
				} else {
					++i;
				}
			}
		}

		if (a == 0) {
			return b == i;
		}

		return ((i - b) / a) >= 0 && ((i - b) % a) == 0;
	}
}
