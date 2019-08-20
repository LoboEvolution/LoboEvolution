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

package org.lobobrowser.html.style;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.lobobrowser.html.dom.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.dom.domimpl.HTMLElementImpl;
import org.lobobrowser.http.UserAgentContext;
import org.w3c.dom.css.CSSImportRule;
import org.w3c.dom.css.CSSMediaRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.stylesheets.MediaList;

import com.gargoylesoftware.css.dom.AbstractCSSRuleImpl;
import com.gargoylesoftware.css.dom.CSSRuleListImpl;
import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;
import com.gargoylesoftware.css.dom.CSSStyleRuleImpl;
import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;

/**
 * Aggregates all style sheets in a document. Every time a new STYLE element is
 * found, it is added to the style sheet aggreagator by means of the
 * {@link #addStyleSheet(CSSStyleSheet)} method. HTML elements have a
 * <code>style</code> object that has a list of <code>CSSStyleDeclaration</code>
 * instances. The instances inserted in that list are obtained by means of the
 * {@link #getStyleDeclarations(HTMLElementImpl, String, String, String)}
 * method.
 */
public class StyleSheetAggregator {
	static class SimpleSelector {
		public static final int ANCESTOR = 0;
		public static final int PARENT = 1;
		public static final int PRECEEDING_SIBLING = 2;

		public final String pseudoElement;
		public int selectorType;
		public final String simpleSelectorText;

		/**
		 * 
		 * @param simpleSelectorText Simple selector text in lower case.
		 * @param pseudoElement      The pseudo-element if any.
		 */
		public SimpleSelector(String simpleSelectorText, String pseudoElement) {
			super();
			this.simpleSelectorText = simpleSelectorText;
			this.pseudoElement = pseudoElement;
			this.selectorType = ANCESTOR;
		}

		public int getSelectorType() {
			return this.selectorType;
		}

		public final boolean hasPseudoName(String pseudoName) {
			return pseudoName.equals(this.pseudoElement);
		}

		public final boolean matches(HTMLElementImpl element) {
			final Set names = element.getPseudoNames();
			if (names == null) {
				return this.pseudoElement == null;
			} else {
				final String pe = this.pseudoElement;
				return pe == null || names.contains(pe);
			}
		}

		public final boolean matches(Set names) {
			if (names == null) {
				return this.pseudoElement == null;
			} else {
				final String pe = this.pseudoElement;
				return pe == null || names.contains(pe);
			}
		}

		public final boolean matches(String pseudoName) {
			if (pseudoName == null) {
				return this.pseudoElement == null;
			} else {
				final String pe = this.pseudoElement;
				return pe == null || pseudoName.equals(pe);
			}
		}

		public void setSelectorType(int selectorType) {
			this.selectorType = selectorType;
		}
	}

	private static class StyleRuleInfo {
		private final ArrayList ancestorSelectors;
		private final CSSStyleRuleImpl styleRule;

		/**
		 * @param selectors A collection of SimpleSelector's.
		 * @param rule      A CSS rule.
		 */
		public StyleRuleInfo(ArrayList simpleSelectors, CSSStyleRuleImpl rule) {
			super();
			this.ancestorSelectors = simpleSelectors;
			this.styleRule = rule;
		}

		public final boolean affectedByPseudoNameInAncestor(HTMLElementImpl element, HTMLElementImpl ancestor,
				String pseudoName) {
			final ArrayList as = this.ancestorSelectors;
			HTMLElementImpl currentElement = element;
			final int size = as.size();
			boolean first = true;
			for (int i = size; --i >= 0;) {
				final SimpleSelector simpleSelector = (SimpleSelector) as.get(i);
				if (first) {
					if (ancestor == element) {
						return simpleSelector.hasPseudoName(pseudoName);
					}
					first = false;
					continue;
				}
				final String selectorText = simpleSelector.simpleSelectorText;
				final int dotIdx = selectorText.indexOf('.');
				HTMLElementImpl newElement;
				if (dotIdx != -1) {
					final String elemtl = selectorText.substring(0, dotIdx);
					final String classtl = selectorText.substring(dotIdx + 1);
					newElement = currentElement.getAncestorWithClass(elemtl, classtl);
				} else {
					final int poundIdx = selectorText.indexOf('#');
					if (poundIdx != -1) {
						final String elemtl = selectorText.substring(0, poundIdx);
						final String idtl = selectorText.substring(poundIdx + 1);
						newElement = currentElement.getAncestorWithId(elemtl, idtl);
					} else {
						final String elemtl = selectorText;
						newElement = currentElement.getAncestor(elemtl);
					}
				}
				if (newElement == null) {
					return false;
				}
				currentElement = newElement;
				if (currentElement == ancestor) {
					return simpleSelector.hasPseudoName(pseudoName);
				}
			}
			return false;
		}

		/**
		 * 
		 * @param element     The element to test for a match.
		 * @param pseudoNames A set of pseudo-names in lowercase.
		 */
		private final boolean isSelectorMatch(HTMLElementImpl element, Set pseudoNames) {
			final ArrayList as = this.ancestorSelectors;
			HTMLElementImpl currentElement = element;
			final int size = as.size();
			boolean first = true;
			for (int i = size; --i >= 0;) {
				final SimpleSelector simpleSelector = (SimpleSelector) as.get(i);
				if (first) {
					if (!simpleSelector.matches(pseudoNames)) {
						return false;
					}
					first = false;
					continue;
				}
				final String selectorText = simpleSelector.simpleSelectorText;
				final int dotIdx = selectorText.indexOf('.');
				final int selectorType = simpleSelector.selectorType;
				HTMLElementImpl priorElement;
				if (dotIdx != -1) {
					final String elemtl = selectorText.substring(0, dotIdx);
					final String classtl = selectorText.substring(dotIdx + 1);
					if (selectorType == SimpleSelector.ANCESTOR) {
						priorElement = currentElement.getAncestorWithClass(elemtl, classtl);
					} else if (selectorType == SimpleSelector.PARENT) {
						priorElement = currentElement.getParentWithClass(elemtl, classtl);
					} else if (selectorType == SimpleSelector.PRECEEDING_SIBLING) {
						priorElement = currentElement.getPreceedingSiblingWithClass(elemtl, classtl);
					} else {
						throw new IllegalStateException("selectorType=" + selectorType);
					}
				} else {
					final int poundIdx = selectorText.indexOf('#');
					if (poundIdx != -1) {
						final String elemtl = selectorText.substring(0, poundIdx);
						final String idtl = selectorText.substring(poundIdx + 1);
						if (selectorType == SimpleSelector.ANCESTOR) {
							priorElement = currentElement.getAncestorWithId(elemtl, idtl);
						} else if (selectorType == SimpleSelector.PARENT) {
							priorElement = currentElement.getParentWithId(elemtl, idtl);
						} else if (selectorType == SimpleSelector.PRECEEDING_SIBLING) {
							priorElement = currentElement.getPreceedingSiblingWithId(elemtl, idtl);
						} else {
							throw new IllegalStateException("selectorType=" + selectorType);
						}
					} else {
						final String elemtl = selectorText;
						if (selectorType == SimpleSelector.ANCESTOR) {
							priorElement = currentElement.getAncestor(elemtl);
						} else if (selectorType == SimpleSelector.PARENT) {
							priorElement = currentElement.getParent(elemtl);
						} else if (selectorType == SimpleSelector.PRECEEDING_SIBLING) {
							priorElement = currentElement.getPreceedingSibling(elemtl);
						} else {
							throw new IllegalStateException("selectorType=" + selectorType);
						}
					}
				}
				if (priorElement == null) {
					return false;
				}
				if (!simpleSelector.matches(priorElement)) {
					return false;
				}
				currentElement = priorElement;
			}
			return true;
		}
	}

	private final Map classMapsByElement = new HashMap();
	private final HTMLDocumentImpl document;

	private final Map idMapsByElement = new HashMap();

	private final Map rulesByElement = new HashMap();

	public StyleSheetAggregator(HTMLDocumentImpl document) {
		this.document = document;
	}

	private final void addClassRule(String elemtl, String classtl, CSSStyleRuleImpl styleRule,
			ArrayList ancestorSelectors) {
		Map classMap = (Map) this.classMapsByElement.get(elemtl);
		if (classMap == null) {
			classMap = new HashMap();
			this.classMapsByElement.put(elemtl, classMap);
		}
		Collection rules = (Collection) classMap.get(classtl);
		if (rules == null) {
			rules = new LinkedList();
			classMap.put(classtl, rules);
		}
		rules.add(new StyleRuleInfo(ancestorSelectors, styleRule));
	}

	private final void addElementRule(String elemtl, CSSStyleRuleImpl styleRule, ArrayList ancestorSelectors) {
		Collection rules = (Collection) this.rulesByElement.get(elemtl);
		if (rules == null) {
			rules = new LinkedList();
			this.rulesByElement.put(elemtl, rules);
		}
		rules.add(new StyleRuleInfo(ancestorSelectors, styleRule));
	}

	private final void addIdRule(String elemtl, String idtl, CSSStyleRuleImpl styleRule, ArrayList ancestorSelectors) {
		Map idsMap = (Map) this.idMapsByElement.get(elemtl);
		if (idsMap == null) {
			idsMap = new HashMap();
			this.idMapsByElement.put(elemtl, idsMap);
		}
		Collection rules = (Collection) idsMap.get(idtl);
		if (rules == null) {
			rules = new LinkedList();
			idsMap.put(idtl, rules);
		}
		rules.add(new StyleRuleInfo(ancestorSelectors, styleRule));
	}

	private final void addRule(CSSStyleSheetImpl styleSheet, AbstractCSSRuleImpl rule) throws Exception {
		final HTMLDocumentImpl document = this.document;
		if (rule instanceof CSSStyleRuleImpl) {
			CSSStyleRuleImpl sr = (CSSStyleRuleImpl) rule;
			final String selectorList = sr.getSelectorText();
			final StringTokenizer commaTok = new StringTokenizer(selectorList, ",");
			while (commaTok.hasMoreTokens()) {
				final String selectorPart = commaTok.nextToken().toLowerCase();
				ArrayList simpleSelectors = null;
				String lastSelectorText = null;
				final StringTokenizer tok = new StringTokenizer(selectorPart, " \t\r\n");
				if (tok.hasMoreTokens()) {
					simpleSelectors = new ArrayList();
					SimpleSelector prevSelector = null;
					SELECTOR_FOR: for (;;) {
						final String token = tok.nextToken();
						if (">".equals(token)) {
							if (prevSelector != null) {
								prevSelector.setSelectorType(SimpleSelector.PARENT);
							}
							continue SELECTOR_FOR;
						} else if ("+".equals(token)) {
							if (prevSelector != null) {
								prevSelector.setSelectorType(SimpleSelector.PRECEEDING_SIBLING);
							}
							continue SELECTOR_FOR;
						}
						final int colonIdx = token.indexOf(':');
						final String simpleSelectorText = colonIdx == -1 ? token : token.substring(0, colonIdx);
						final String pseudoElement = colonIdx == -1 ? null : token.substring(colonIdx + 1);
						prevSelector = new SimpleSelector(simpleSelectorText, pseudoElement);
						simpleSelectors.add(prevSelector);
						if (!tok.hasMoreTokens()) {
							lastSelectorText = simpleSelectorText;
							break;
						}
					}
				}
				if (lastSelectorText != null) {
					final int dotIdx = lastSelectorText.indexOf('.');
					if (dotIdx != -1) {
						final String elemtl = lastSelectorText.substring(0, dotIdx);
						final String classtl = lastSelectorText.substring(dotIdx + 1);
						addClassRule(elemtl, classtl, sr, simpleSelectors);
					} else {
						final int poundIdx = lastSelectorText.indexOf('#');
						if (poundIdx != -1) {
							final String elemtl = lastSelectorText.substring(0, poundIdx);
							final String idtl = lastSelectorText.substring(poundIdx + 1);
							addIdRule(elemtl, idtl, sr, simpleSelectors);
						} else {
							final String elemtl = lastSelectorText;
							addElementRule(elemtl, sr, simpleSelectors);
						}
					}
				}
			}
			// TODO: Attribute selectors
		} else if (rule instanceof CSSImportRule) {
			final UserAgentContext uacontext = document.getUserAgentContext();
			if (uacontext.isExternalCSSEnabled()) {
				final CSSImportRule importRule = (CSSImportRule) rule;
				if (CSSUtilities.matchesMedia(importRule.getMedia(), uacontext)) {
					final String href = importRule.getHref();
					final String styleHref = styleSheet.getHref();
					final String baseHref = styleHref == null ? document.getBaseURI() : styleHref;
					final CSSStyleSheetImpl sheet = CSSUtilities.parse(styleSheet.getOwnerNode(), href, document, baseHref,
							false);
					if (sheet != null) {
						addStyleSheet(sheet);
					}
				}
			}
		} else if (rule instanceof CSSMediaRule) {
			final CSSMediaRule mrule = (CSSMediaRule) rule;
			final MediaList mediaList = mrule.getMedia();
			if (CSSUtilities.matchesMedia(mediaList, document.getUserAgentContext())) {
				final CSSRuleListImpl ruleList = (CSSRuleListImpl) mrule.getCssRules();
				for (AbstractCSSRuleImpl subRule : ruleList.getRules()) {
					addRule(styleSheet, subRule);
				}
			}
		}
	}

	private final void addStyleSheet(CSSStyleSheetImpl styleSheet) throws Exception {
		CSSRuleListImpl cssRules = styleSheet.getCssRules();
		for (AbstractCSSRuleImpl rule : cssRules.getRules()) {
			addRule(styleSheet, rule);
		}
	}

	public final void addStyleSheets(List<CSSStyleSheetImpl> styleSheets) throws Exception {
		for (CSSStyleSheetImpl sheet : styleSheets) {
			addStyleSheet(sheet);
		}
	}

	public final boolean affectedByPseudoNameInAncestor(HTMLElementImpl element, HTMLElementImpl ancestor,
			String elementName, String elementId, String[] classArray, String pseudoName) {
		final String elementTL = elementName.toLowerCase();
		Collection elementRules = (Collection) this.rulesByElement.get(elementTL);
		if (elementRules != null) {
			final Iterator i = elementRules.iterator();
			while (i.hasNext()) {
				final StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
				final CSSStyleSheetImpl styleSheet = styleRuleInfo.styleRule.getParentStyleSheet();
				if (styleSheet != null && styleSheet.getDisabled()) {
					continue;
				}
				if (styleRuleInfo.affectedByPseudoNameInAncestor(element, ancestor, pseudoName)) {
					return true;
				}
			}
		}
		elementRules = (Collection) this.rulesByElement.get("*");
		if (elementRules != null) {
			final Iterator i = elementRules.iterator();
			while (i.hasNext()) {
				final StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
				final CSSStyleSheetImpl styleSheet = styleRuleInfo.styleRule.getParentStyleSheet();
				if (styleSheet != null && styleSheet.getDisabled()) {
					continue;
				}
				if (styleRuleInfo.affectedByPseudoNameInAncestor(element, ancestor, pseudoName)) {
					return true;
				}
			}
		}
		if (classArray != null) {
			for (final String className : classArray) {
				final String classNameTL = className.toLowerCase();
				Map classMaps = (Map) this.classMapsByElement.get(elementTL);
				if (classMaps != null) {
					final Collection classRules = (Collection) classMaps.get(classNameTL);
					if (classRules != null) {
						final Iterator i = classRules.iterator();
						while (i.hasNext()) {
							final StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
							final CSSStyleSheetImpl styleSheet = styleRuleInfo.styleRule.getParentStyleSheet();
							if (styleSheet != null && styleSheet.getDisabled()) {
								continue;
							}
							if (styleRuleInfo.affectedByPseudoNameInAncestor(element, ancestor, pseudoName)) {
								return true;
							}
						}
					}
				}
				classMaps = (Map) this.classMapsByElement.get("*");
				if (classMaps != null) {
					final Collection classRules = (Collection) classMaps.get(classNameTL);
					if (classRules != null) {
						final Iterator i = classRules.iterator();
						while (i.hasNext()) {
							final StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
							final CSSStyleSheetImpl styleSheet = styleRuleInfo.styleRule.getParentStyleSheet();
							if (styleSheet != null && styleSheet.getDisabled()) {
								continue;
							}
							if (styleRuleInfo.affectedByPseudoNameInAncestor(element, ancestor, pseudoName)) {
								return true;
							}
						}
					}
				}
			}
		}
		if (elementId != null) {
			Map idMaps = (Map) this.idMapsByElement.get(elementTL);
			if (idMaps != null) {
				final String elementIdTL = elementId.toLowerCase();
				final Collection idRules = (Collection) idMaps.get(elementIdTL);
				if (idRules != null) {
					final Iterator i = idRules.iterator();
					while (i.hasNext()) {
						final StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
						final CSSStyleSheetImpl styleSheet = styleRuleInfo.styleRule.getParentStyleSheet();
						if (styleSheet != null && styleSheet.getDisabled()) {
							continue;
						}
						if (styleRuleInfo.affectedByPseudoNameInAncestor(element, ancestor, pseudoName)) {
							return true;
						}
					}
				}
			}
			idMaps = (Map) this.idMapsByElement.get("*");
			if (idMaps != null) {
				final String elementIdTL = elementId.toLowerCase();
				final Collection idRules = (Collection) idMaps.get(elementIdTL);
				if (idRules != null) {
					final Iterator i = idRules.iterator();
					while (i.hasNext()) {
						final StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
						final CSSStyleSheetImpl styleSheet = styleRuleInfo.styleRule.getParentStyleSheet();
						if (styleSheet != null && styleSheet.getDisabled()) {
							continue;
						}
						if (styleRuleInfo.affectedByPseudoNameInAncestor(element, ancestor, pseudoName)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public final Collection<CSSStyleDeclarationImpl> getActiveStyleDeclarations(HTMLElementImpl element, String elementName, String elementId,
			String className, Set pseudoNames) {
		Collection styleDeclarations = null;
		final String elementTL = elementName.toLowerCase();
		Collection elementRules = (Collection) this.rulesByElement.get(elementTL);
		if (elementRules != null) {
			final Iterator i = elementRules.iterator();
			while (i.hasNext()) {
				final StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
				if (styleRuleInfo.isSelectorMatch(element, pseudoNames)) {
					final CSSStyleRuleImpl styleRule = styleRuleInfo.styleRule;
					final CSSStyleSheetImpl styleSheet = styleRule.getParentStyleSheet();
					if (styleSheet != null && styleSheet.getDisabled()) {
						continue;
					}
					if (styleDeclarations == null) {
						styleDeclarations = new LinkedList();
					}
					styleDeclarations.add(styleRule.getStyle());
				} else {
				}
			}
		}
		elementRules = (Collection) this.rulesByElement.get("*");
		if (elementRules != null) {
			final Iterator i = elementRules.iterator();
			while (i.hasNext()) {
				final StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
				if (styleRuleInfo.isSelectorMatch(element, pseudoNames)) {
					final CSSStyleRuleImpl styleRule = styleRuleInfo.styleRule;
					final CSSStyleSheetImpl styleSheet = styleRule.getParentStyleSheet();
					if (styleSheet != null && styleSheet.getDisabled()) {
						continue;
					}
					if (styleDeclarations == null) {
						styleDeclarations = new LinkedList();
					}
					styleDeclarations.add(styleRule.getStyle());
				}
			}
		}
		if (className != null) {
			final String classNameTL = className.toLowerCase();
			Map classMaps = (Map) this.classMapsByElement.get(elementTL);
			if (classMaps != null) {
				final Collection classRules = (Collection) classMaps.get(classNameTL);
				if (classRules != null) {
					final Iterator i = classRules.iterator();
					while (i.hasNext()) {
						final StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
						if (styleRuleInfo.isSelectorMatch(element, pseudoNames)) {
							final CSSStyleRuleImpl styleRule = styleRuleInfo.styleRule;
							final CSSStyleSheetImpl styleSheet = styleRule.getParentStyleSheet();
							if (styleSheet != null && styleSheet.getDisabled()) {
								continue;
							}
							if (styleDeclarations == null) {
								styleDeclarations = new LinkedList();
							}
							styleDeclarations.add(styleRule.getStyle());
						}
					}
				}
			}
			classMaps = (Map) this.classMapsByElement.get("*");
			if (classMaps != null) {
				final Collection classRules = (Collection) classMaps.get(classNameTL);
				if (classRules != null) {
					final Iterator i = classRules.iterator();
					while (i.hasNext()) {
						final StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
						if (styleRuleInfo.isSelectorMatch(element, pseudoNames)) {
							final CSSStyleRuleImpl styleRule = styleRuleInfo.styleRule;
							final CSSStyleSheetImpl styleSheet = styleRule.getParentStyleSheet();
							if (styleSheet != null && styleSheet.getDisabled()) {
								continue;
							}
							if (styleDeclarations == null) {
								styleDeclarations = new LinkedList();
							}
							styleDeclarations.add(styleRule.getStyle());
						}
					}
				}
			}
		}
		if (elementId != null) {
			Map idMaps = (Map) this.idMapsByElement.get(elementTL);
			if (idMaps != null) {
				final String elementIdTL = elementId.toLowerCase();
				final Collection idRules = (Collection) idMaps.get(elementIdTL);
				if (idRules != null) {
					final Iterator i = idRules.iterator();
					while (i.hasNext()) {
						final StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
						if (styleRuleInfo.isSelectorMatch(element, pseudoNames)) {
							final CSSStyleRuleImpl styleRule = styleRuleInfo.styleRule;
							final CSSStyleSheetImpl styleSheet = styleRule.getParentStyleSheet();
							if (styleSheet != null && styleSheet.getDisabled()) {
								continue;
							}
							if (styleDeclarations == null) {
								styleDeclarations = new LinkedList();
							}
							styleDeclarations.add(styleRule.getStyle());
						}
					}
				}
			}
			idMaps = (Map) this.idMapsByElement.get("*");
			if (idMaps != null) {
				final String elementIdTL = elementId.toLowerCase();
				final Collection idRules = (Collection) idMaps.get(elementIdTL);
				if (idRules != null) {
					final Iterator i = idRules.iterator();
					while (i.hasNext()) {
						final StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
						if (styleRuleInfo.isSelectorMatch(element, pseudoNames)) {
							final CSSStyleRuleImpl styleRule = styleRuleInfo.styleRule;
							final CSSStyleSheetImpl styleSheet = styleRule.getParentStyleSheet();
							if (styleSheet != null && styleSheet.getDisabled()) {
								continue;
							}
							if (styleDeclarations == null) {
								styleDeclarations = new LinkedList();
							}
							styleDeclarations.add(styleRule.getStyle());
						}
					}
				}
			}
		}
		return styleDeclarations;
	}
}
