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
package org.lobobrowser.html.style.selectors;

import java.util.Set;

import org.lobobrowser.html.domimpl.DOMNodeImpl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;

/**
 * The Class SelectorMatcher.
 */
public class SelectorMatcher {
	
	/** The op equal. */
	public final static String OP_EQUAL = "=";
	
	/** The op tilde equal. */
	public final static String OP_TILDE_EQUAL = "~=";
	
	/** The op pipe equal. */
	public final static String OP_PIPE_EQUAL = "|=";
	
	/** The op dollar equal. */
	public final static String OP_DOLLAR_EQUAL = "$=";
	
	/** The op circumflex equal. */
	public final static String OP_CIRCUMFLEX_EQUAL = "^=";
	
	/** The op star equal. */
	public final static String OP_STAR_EQUAL = "*=";
	
	/** The op all. */
	public final static String OP_ALL = "ALL";

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
	public final static String ROOT ="root";
	
	/** The empty. */
	public final static String EMPTY = "empty";
	
	/** The lang. */
	public final static String LANG = "lang";
	
	/** The Constant ANCESTOR. */
	public final static int ANCESTOR = 0;

	/** The Constant PARENT. */
	public final static int PARENT = 1;

	/** The Constant PRECEEDING_SIBLING. */
	public final static int PRECEEDING_SIBLING = 2;
	
	/** The Constant ODD. */
	private String ODD = "odd";
	
	/** The Constant ODD. */
	private String EVEN = "even";

	/** The simple selector text. */
	private String simpleSelectorText;

	/** The pseudo element. */
	private String pseudoElement;

	/** The selector type. */
	private int selectorType;

	public SelectorMatcher() {}
	
	/**
	 * Instantiates a new simple selector.
	 *
	 * @param simpleSelectorText
	 *            Simple selector text in lower case.
	 * @param pseudoElement
	 *            The pseudo-element if any.
	 */
	public SelectorMatcher(String simpleSelectorText, String pseudoElement) {
		this.simpleSelectorText = simpleSelectorText;
		this.pseudoElement = pseudoElement;
		this.selectorType = ANCESTOR;
	}

	/**
	 * Matches.
	 *
	 * @param element
	 *            the element
	 * @return true, if successful
	 */
	public final boolean matches(HTMLElementImpl element) {
		Set<String> names = element.getPseudoNames();
		if (names == null) {
			return this.pseudoElement == null;
		} else {
			String pe = this.pseudoElement;
			return (pe == null) || names.contains(pe);
		}
	}

	/**
	 * Matches.
	 *
	 * @param names
	 *            the names
	 * @return true, if successful
	 */
	public final boolean matches(Set names) {
		if (names == null) {
			return this.pseudoElement == null;
		} else {
			String pe = this.pseudoElement;
			
			if (pe != null && pe.contains("(")) 
				pe = pe.substring(0, pe.indexOf("("));
			
			return (pe == null) || names.contains(pe);
		}
	}

	/**
	 * Matches.
	 *
	 * @param pseudoName
	 *            the pseudo name
	 * @return true, if successful
	 */
	public final boolean matches(String pseudoName) {
		if (pseudoName == null) {
			return this.pseudoElement == null;
		} else {
			String pe = this.pseudoElement;
			return (pe == null) || pseudoName.equals(pe);
		}
	}

	/**
	 * Checks for pseudo name.
	 *
	 * @param pseudoName
	 *            the pseudo name
	 * @return true, if successful
	 */
	public final boolean hasPseudoName(String pseudoName) {
		return pseudoName.equals(this.pseudoElement);
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
	public boolean matchesPseudoClassSelector(String selector, DOMNodeImpl node, int c) {
		
		String cnt = "";
		String select = selector;
		if (selector != null && selector.contains("(")) {
			selector = selector.substring(0, selector.indexOf("("));
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
			return parentDOMNodeImpl != null && parentDOMNodeImpl.getNodeType() == DOMNodeImpl.DOCUMENT_TYPE_NODE;
		case EMPTY: 
			return isEmpty(node);	
		case NTH_CHILD:
			cnt = getValue(NTH_CHILD, select);
			if(EVEN.equals(cnt) || ODD.equals(cnt)){
				if(EVEN.equals(cnt) && isEven(c)){
					return matchesChild(node, 0, Integer.valueOf(c).intValue(), true, false);
				} else if(ODD.equals(cnt) && !isEven(c)){
					return matchesChild(node, 0, Integer.valueOf(c).intValue(), true, false);
				} else{
					return false;
				}	
			} else{
				return matchesChild(node, 0, Integer.valueOf(cnt).intValue(), true, false);
			}
		case NTH_LAST_CHILD:
			cnt= getValue(NTH_LAST_CHILD, select);
			if(EVEN.equals(cnt) || ODD.equals(cnt)){
				if(EVEN.equals(cnt) && isEven(c)){
					return matchesChild(node, 0, Integer.valueOf(c).intValue(), true, false);
				} else if(ODD.equals(cnt) && !isEven(c)){
					return matchesChild(node, 0, Integer.valueOf(c).intValue(), true, false);
				} else{
					return false;
				}	
			} else{
				return matchesChild(node, 0, Integer.valueOf(cnt).intValue(), true, false);
			}
		case NTH_OF_TYPE:
			cnt= getValue(NTH_OF_TYPE, select);
			if(EVEN.equals(cnt) || ODD.equals(cnt)){
				if(EVEN.equals(cnt) && isEven(c)){
					return matchesChild(node, 0, Integer.valueOf(c).intValue(), true, false);
				} else if(ODD.equals(cnt) && !isEven(c)){
					return matchesChild(node, 0, Integer.valueOf(c).intValue(), true, false);
				} else{
					return false;
				}	
			} else{
				return matchesChild(node, 0, Integer.valueOf(cnt).intValue(), true, false);
			}
		case NTH_LAST_OF_TYPE:
			cnt= getValue(NTH_LAST_OF_TYPE, select);
			if(EVEN.equals(cnt) || ODD.equals(cnt)){
				if(EVEN.equals(cnt) && isEven(c)){
					return matchesChild(node, 0, Integer.valueOf(c).intValue(), true, false);
				} else if(ODD.equals(cnt) && !isEven(c)){
					return matchesChild(node, 0, Integer.valueOf(c).intValue(), true, false);
				} else{
					return false;
				}	
			} else{
				return matchesChild(node, 0, Integer.valueOf(cnt).intValue(), true, false);
			}
		case LANG:
			String value = select.replace(LANG, "").replace("(","").replace(")","").trim();
			NamedNodeMap attributes = node.getAttributes();
			for (int s = 0; s < attributes.getLength(); s++) {
                Attr attr = (Attr) attributes.item(s);
                if(LANG.equals(attr.getName()) && value.equals(attr.getValue())){
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
	private boolean matchesChild(DOMNodeImpl node, int a, int b, boolean isOfType, boolean fromEnd) {
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
	
	/**
	 *  Matches a empty.
	 *
	 * @param node
	 *            The root node.
	 *            
	 * @return {@code true} or {@code false}
	 */
	private static boolean isEmpty(DOMNodeImpl node) {
		for (DOMNodeImpl child = (DOMNodeImpl) node.getFirstChild(); child != null; child = (DOMNodeImpl) child.getNextSibling()) {
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
	}
	
	
	private boolean isEven(int number){
		 if(number%2 == 0){
             return true;
		 }
      return false;
	}
	
	private String getValue(String selector, String select){
		return select.replace(selector, "").replace("(","").replace(")","").trim();
	}
	
	/**
	 * Gets the selector type.
	 *
	 * @return the selector type
	 */
	public int getSelectorType() {
		return selectorType;
	}

	/**
	 * Sets the selector type.
	 *
	 * @param selectorType
	 *            the new selector type
	 */
	public void setSelectorType(int selectorType) {
		this.selectorType = selectorType;
	}

	/**
	 * Gets the simple selector text.
	 *
	 * @return the simple selector text
	 */
	public String getSimpleSelectorText() {
		return simpleSelectorText;
	}

	/**
	 * Sets the simple selector text.
	 *
	 * @param simpleSelectorText
	 *            the new simple selector text
	 */
	public void setSimpleSelectorText(String simpleSelectorText) {
		this.simpleSelectorText = simpleSelectorText;
	}

	/**
	 * Gets the pseudo element.
	 *
	 * @return the pseudo element
	 */
	public String getPseudoElement() {
		return pseudoElement;
	}

	/**
	 * Sets the pseudo element.
	 *
	 * @param pseudoElement
	 *            the new pseudo element
	 */
	public void setPseudoElement(String pseudoElement) {
		this.pseudoElement = pseudoElement;
	}
}
