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
package org.lobobrowser.html.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.style.selectors.SelectorMatcher;
import org.w3c.dom.css.CSSStyleRule;

/**
 * The Class StyleRuleInfo.
 */
public class StyleRuleInfo implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 9165715430607111555L;

	/** The style rule. */
	private CSSStyleRule styleRule;

	/** The ancestor selectors. */
	private final ArrayList<SelectorMatcher> ancestorSelectors;

	/**
	 * Instantiates a new style rule info.
	 *
	 * @param SelectorMatchers
	 *            A collection of SelectorMatcher's.
	 * @param rule
	 *            A CSS rule.
	 */
	public StyleRuleInfo(ArrayList<SelectorMatcher> SelectorMatchers, CSSStyleRule rule) {
		super();
		ancestorSelectors = SelectorMatchers;
		setStyleRule(rule);
	}

	/**
	 * Affected by pseudo name in ancestor.
	 *
	 * @param element
	 *            the element
	 * @param ancestor
	 *            the ancestor
	 * @param pseudoName
	 *            the pseudo name
	 * @return true, if successful
	 */
	public final boolean affectedByPseudoNameInAncestor(HTMLElementImpl element, HTMLElementImpl ancestor,
			String pseudoName) {
		ArrayList<SelectorMatcher> as = this.ancestorSelectors;
		HTMLElementImpl currentElement = element;
		int size = as.size();
		boolean first = true;
		for (int i = size; --i >= 0;) {
			SelectorMatcher selectorMatcher = as.get(i);
			if (first) {
				if (ancestor == element) {
					return selectorMatcher.hasPseudoName(pseudoName);
				}
				first = false;
				continue;
			}
			String selectorText = selectorMatcher.getSimpleSelectorText();
			int dotIdx = selectorText.indexOf('.');
			HTMLElementImpl newElement;
			if (dotIdx != -1) {
				String elemtl = selectorText.substring(0, dotIdx);
				String classtl = selectorText.substring(dotIdx + 1);
				newElement = currentElement.getAncestorWithClass(elemtl, classtl);
			} else {
				int poundIdx = selectorText.indexOf('#');
				if (poundIdx != -1) {
					String elemtl = selectorText.substring(0, poundIdx);
					String idtl = selectorText.substring(poundIdx + 1);
					newElement = currentElement.getAncestorWithId(elemtl, idtl);
				} else {
					String elemtl = selectorText;
					newElement = currentElement.getAncestor(elemtl);
				}
			}
			if (newElement == null) {
				return false;
			}
			currentElement = newElement;
			if (currentElement == ancestor) {
				return selectorMatcher.hasPseudoName(pseudoName);
			}
		}
		return false;
	}

	/**
	 * Checks if is selector match.
	 *
	 * @param element
	 *            The element to test for a match.
	 * @param pseudoNames
	 *            A set of pseudo-names in lowercase.
	 * @return true, if is selector match
	 */
	public final boolean isSelectorMatch(HTMLElementImpl element, Set<?> pseudoNames) {
		ArrayList<SelectorMatcher> as = this.ancestorSelectors;
		HTMLElementImpl currentElement = element;
		int size = as.size();
		boolean first = true;
		for (int i = size; --i >= 0;) {
			SelectorMatcher selectorMatcher = as.get(i);
			
			if (first) {
				if (!selectorMatcher.matches(pseudoNames)) {
					return false;
				}
				first = false;
				continue;
			}
			
			String selectorText = selectorMatcher.getSimpleSelectorText();
			int dotIdx = selectorText.indexOf('.');
			int selectorType = selectorMatcher.getSelectorType();
			HTMLElementImpl priorElement;
			
			if (dotIdx != -1) {
				String elemtl = selectorText.substring(0, dotIdx);
				String classtl = selectorText.substring(dotIdx + 1);
				
				if (elemtl == null || "".equals(elemtl)) {
					elemtl = "*";
				}
				
				switch (selectorType) {
				case SelectorMatcher.ANCESTOR:
					priorElement = currentElement.getAncestorWithClass(elemtl, classtl);
					break;
				case SelectorMatcher.PARENT:
					priorElement = currentElement.getParentWithClass(elemtl, classtl);
					break;
				case SelectorMatcher.PRECEEDING_SIBLING:
					priorElement = currentElement.getPreceedingSiblingWithClass(elemtl, classtl);
					break;
				default:
					throw new IllegalStateException("selectorType=" + selectorType);
				}
				
			} else {
				int poundIdx = selectorText.indexOf('#');
				if (poundIdx != -1) {
					String elemtl = selectorText.substring(0, poundIdx);
					String idtl = selectorText.substring(poundIdx + 1);
					
					if (elemtl == null || "".equals(elemtl)) {
						elemtl = "*";
					}
					
					switch (selectorType) {
					case SelectorMatcher.ANCESTOR:
						priorElement = currentElement.getAncestorWithId(elemtl, idtl);
						break;
					case SelectorMatcher.PARENT:
						priorElement = currentElement.getParentWithId(elemtl, idtl);
						break;
					case SelectorMatcher.PRECEEDING_SIBLING:
						priorElement = currentElement.getPreceedingSiblingWithId(elemtl, idtl);
						break;
					default:
						throw new IllegalStateException("selectorType=" + selectorType);
					}

				} else {
					String elemtl = selectorText;
					
					if (elemtl == null || "".equals(elemtl)) {
						elemtl = "*";
					}
					
					if (elemtl.contains("[") && elemtl.endsWith("]")) {
						String selector = elemtl.replace("\"", "");
						elemtl = selector.substring(0, selector.indexOf("["));
					}
										
					switch (selectorType) {
					case SelectorMatcher.ANCESTOR:
						priorElement = currentElement.getAncestor(elemtl);
						break;
					case SelectorMatcher.PARENT:
						priorElement = currentElement.getParent(elemtl);
						break;
					case SelectorMatcher.PRECEEDING_SIBLING:
						priorElement = currentElement.getPreceedingSibling(elemtl);
						break;
					default:
						throw new IllegalStateException("selectorType=" + selectorType);
					}
					
				}
			}
			if (priorElement == null) {
				return false;
			}
			if (!selectorMatcher.matches(priorElement)) {
				return false;
			}
			currentElement = priorElement;
		}
		return true;
	}

	/**
	 * Gets the style rule.
	 *
	 * @return the style rule
	 */
	public CSSStyleRule getStyleRule() {
		return styleRule;
	}

	/**
	 * Sets the style rule.
	 *
	 * @param styleRule
	 *            the new style rule
	 */
	public void setStyleRule(CSSStyleRule styleRule) {
		this.styleRule = styleRule;
	}

	/**
	 * get Ancestor Selectors.
	 */
	public ArrayList<SelectorMatcher> getAncestorSelectors() {
		return ancestorSelectors;
	}
}
