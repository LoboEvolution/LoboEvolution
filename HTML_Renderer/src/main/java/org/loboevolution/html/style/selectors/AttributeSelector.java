/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.style.selectors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.loboevolution.html.domimpl.DOMNodeImpl;
import org.loboevolution.html.info.SelectorInfo;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class AttributeSelector {
	
	/** The last child. */
	public final static String LAST_CHILD = "last-child";

	/** The last of type . */
	public final static String LAST_OF_TYPE = "last-of-type";

	/** The first child. */
	public final static String FIRST_CHILD = "first-child";

	/** The first of type. */
	public final static String FIRST_OF_TYPE = "first-of-type";

	/** The only child. */
	public final static String ONLY_CHILD = "only-child";

	/** The only of type. */
	public final static String ONLY_OF_TYPE = "only-of-type";

	/** The nth child. */
	public final static String NTH_CHILD = "nth-child";

	/** The nth last child. */
	public final static String NTH_LAST_CHILD = "nth-last-child";

	/** The nth of type. */
	public final static String NTH_OF_TYPE = "nth-of-type";

	/** The nth last of type. */
	public final static String NTH_LAST_OF_TYPE = "nth-last-of-type";

	/** The hover. */
	public final static String HOVER = "hover";

	/** The root. */
	public final static String ROOT = "root";

	/** The empty. */
	public final static String EMPTY = "empty";

	/** The lang. */
	public final static String LANG = "lang";
	
	/** The Constant ODD. */
	private String ODD = "odd";

	/** The Constant ODD. */
	private String EVEN = "even";

	
	/**
	 * Matches the given pseudo class selector against the given node.
	 *
	 * @param selector
	 *            The pseudo class selector.
	 * @param node
	 *            The root node.
	 * @return {@code true} or {@code false}
	 */
	public boolean matchesPseudoClassSelector(String selector, DOMNodeImpl node, int c) {

		String cnt = "";
		String select = selector;
		if (selector != null && selector.contains("(")) {
			selector = selector.substring(0, selector.indexOf('('));
		} else if (selector == null) {
			return false;
		}

		switch (selector) {
		case FIRST_CHILD:
			return matchesFirstOrLastChild(node, true);
		case LAST_CHILD:
			return matchesFirstOrLastChild(node, false);
		case ONLY_CHILD:
			return matchesFirstOrLastChild(node, true) && matchesFirstOrLastChild(node, false);
		case FIRST_OF_TYPE:
			return matchesChild(node, 0, 1, true, false);
		case LAST_OF_TYPE:
			return matchesChild(node, 0, 1, true, true);
		case ONLY_OF_TYPE:
			return matchesChild(node, 0, 1, true, false) && matchesChild(node, 0, 1, true, true);
		case ROOT:
			DOMNodeImpl parentDOMNodeImpl = (DOMNodeImpl) node.getParentNode();
			return parentDOMNodeImpl != null && parentDOMNodeImpl.getNodeType() == Node.DOCUMENT_TYPE_NODE;
		case EMPTY:
			return isEmpty(node);
		case NTH_CHILD:
			cnt = getValue(NTH_CHILD, select);
			if (EVEN.equals(cnt) || ODD.equals(cnt)) {
				if (EVEN.equals(cnt) && isEven(c)) {
					return matchesChild(node, 0, Integer.valueOf(c).intValue(), true, false);
				} else if (ODD.equals(cnt) && !isEven(c)) {
					return matchesChild(node, 0, Integer.valueOf(c).intValue(), true, false);
				} else {
					return false;
				}
			} else {
				if (cnt.contains("n+")) {
					String[] split = cnt.split("n\\+");
					int a = Integer.valueOf(split[0]).intValue();
					int b = Integer.valueOf(split[1]).intValue();
					return matchesChild(node, a, b, true, false);
				} else if (cnt.contains("n-")) {
					String[] split = cnt.split("n\\-");
					int a = Integer.valueOf(split[0]).intValue();
					int b = Integer.valueOf(split[1]).intValue();
					return matchesChild(node, a, a - b, true, false);
				} else if (cnt.contains("n")) {
					String[] split = cnt.split("n");
					int a = Integer.valueOf(split[0]).intValue();
					return matchesChild(node, a, a, true, false);
				} else {
					return matchesChild(node, 0, Integer.valueOf(cnt).intValue(), true, false);
				}
			}
		case NTH_LAST_CHILD:
			cnt = getValue(NTH_LAST_CHILD, select);
			if (EVEN.equals(cnt) || ODD.equals(cnt)) {
				if (EVEN.equals(cnt) && isEven(c)) {
					return matchesChild(node, 0, Integer.valueOf(c).intValue(), true, true);
				} else if (ODD.equals(cnt) && !isEven(c)) {
					return matchesChild(node, 0, Integer.valueOf(c).intValue(), true, true);
				} else {
					return false;
				}
			} else {
				if (cnt.contains("n+")) {
					String[] split = cnt.split("n\\+");
					int a = Integer.valueOf(split[0]).intValue();
					int b = Integer.valueOf(split[1]).intValue();
					return matchesChild(node, a, b, true, true);
				} else if (cnt.contains("n-")) {
					String[] split = cnt.split("n\\-");
					int a = Integer.valueOf(split[0]).intValue();
					int b = Integer.valueOf(split[1]).intValue();
					return matchesChild(node, a, a - b, true, true);
				} else if (cnt.contains("n")) {
					String[] split = cnt.split("n");
					int a = Integer.valueOf(split[0]).intValue();
					return matchesChild(node, a, a, true, true);
				} else {
					return matchesChild(node, 0, Integer.valueOf(cnt).intValue(), true, true);
				}
			}
		case NTH_OF_TYPE:
			cnt = getValue(NTH_OF_TYPE, select);
			if (EVEN.equals(cnt) || ODD.equals(cnt)) {
				if (EVEN.equals(cnt) && isEven(c)) {
					return matchesChild(node, 0, Integer.valueOf(c).intValue(), true, false);
				} else if (ODD.equals(cnt) && !isEven(c)) {
					return matchesChild(node, 0, Integer.valueOf(c).intValue(), true, false);
				} else {
					return false;
				}
			} else {
				if (cnt.contains("n+")) {
					String[] split = cnt.split("n\\+");
					int a = Integer.valueOf(split[0]).intValue();
					int b = Integer.valueOf(split[1]).intValue();
					return matchesChild(node, a, b, true, false);
				} else if (cnt.contains("n-")) {
					String[] split = cnt.split("n\\-");
					int a = Integer.valueOf(split[0]).intValue();
					int b = Integer.valueOf(split[1]).intValue();
					return matchesChild(node, a, a - b, true, false);
				} else if (cnt.contains("n")) {
					String[] split = cnt.split("n");
					int a = Integer.valueOf(split[0]).intValue();
					return matchesChild(node, a, a, true, false);
				} else {
					return matchesChild(node, 0, Integer.valueOf(cnt).intValue(), true, false);
				}
			}
		case NTH_LAST_OF_TYPE:
			cnt = getValue(NTH_LAST_OF_TYPE, select);
			if (EVEN.equals(cnt) || ODD.equals(cnt)) {
				if (EVEN.equals(cnt) && isEven(c)) {
					return matchesChild(node, 0, Integer.valueOf(c).intValue(), true, true);
				} else if (ODD.equals(cnt) && !isEven(c)) {
					return matchesChild(node, 0, Integer.valueOf(c).intValue(), true, true);
				} else {
					return false;
				}
			} else {
				if (cnt.contains("n+")) {
					String[] split = cnt.split("n\\+");
					int a = Integer.valueOf(split[0]).intValue();
					int b = Integer.valueOf(split[1]).intValue();
					return matchesChild(node, a, b, true, true);
				} else if (cnt.contains("n-")) {
					String[] split = cnt.split("n\\-");
					int a = Integer.valueOf(split[0]).intValue();
					int b = Integer.valueOf(split[1]).intValue();
					return matchesChild(node, a, a - b, true, true);
				} else if (cnt.contains("n")) {
					String[] split = cnt.split("n");
					int a = Integer.valueOf(split[0]).intValue();
					return matchesChild(node, a, a, true, true);
				} else {
					return matchesChild(node, 0, Integer.valueOf(cnt).intValue(), true, true);
				}
			}
		case LANG:
			String value = getValue(LANG, select);
			NamedNodeMap attributes = node.getAttributes();
			for (int s = 0; s < attributes.getLength(); s++) {
				Attr attr = (Attr) attributes.item(s);
				if (LANG.equals(attr.getName()) && value.equals(attr.getValue())) {
					return true;
				}
			}
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
				return n != null && n.getNodeType() != Node.DOCUMENT_TYPE_NODE;
			} else {
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					return false;
				}

				node = n;
			}
		}
	}
    
    public String getAttributeSelector(String x) {
         Matcher m = Pattern.compile("\\[(.*?)\\]").matcher(x);
        while(m.find()) {
            return m.group(1);
        }
        return "";
    }
    
    public SelectorInfo getSelector(String str, String selector) {
        SelectorInfo si = new SelectorInfo();
        if(!selector.equals(SelectorMatcher.OP_ALL)) {
            Pattern SPACE = Pattern.compile("\\"+selector);
            String[] arr = SPACE.split(str);
            si.setAttribute(arr[0]);
            si.setAttributeValue(arr[1]);
        } else {
            si.setAttribute(str);
            si.setAttributeValue("-");
        }
        return si;
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
	private boolean matchesChild(DOMNodeImpl node, int a, int b, boolean isOfType, boolean fromEnd) {
		DOMNodeImpl parentDOMNodeImpl = (DOMNodeImpl) node.getParentNode();
		if (parentDOMNodeImpl == null || parentDOMNodeImpl.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
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
			if (n.getNodeType() == Node.ELEMENT_NODE) {
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

		return (i - b) / a >= 0 && (i - b) % a == 0;
	}

	/**
	 * Matches a empty.
	 *
	 * @param node
	 *            The root node.
	 * 
	 * @return {@code true} or {@code false}
	 */
	private static boolean isEmpty(DOMNodeImpl node) {
		for (DOMNodeImpl child = (DOMNodeImpl) node.getFirstChild(); child != null; child = (DOMNodeImpl) child
				.getNextSibling()) {
			switch (child.getNodeType()) {
			case Node.ELEMENT_NODE:
				return false;
			case Node.TEXT_NODE:
				String data = child.getNodeName();
				if (data != null && !data.isEmpty()) {
					return false;
				}
			default:
				break;
			}
		}
		return true;
	}

	private boolean isEven(int number) {
		return (number % 2 == 0);
	}

	private String getValue(String selector, String select) {
		return select.replace(selector, "").replace("(", "").replace(")", "").trim();
	}

}
