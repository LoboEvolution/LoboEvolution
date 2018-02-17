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

package org.loboevolution.html.style;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.html.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.info.SelectorInfo;
import org.loboevolution.html.info.StyleRuleInfo;
import org.loboevolution.html.style.selectors.AttributeSelector;
import org.loboevolution.html.style.selectors.SelectorMatcher;
import org.loboevolution.http.UserAgentContext;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.css.CSSImportRule;
import org.w3c.dom.css.CSSMediaRule;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.stylesheets.MediaList;

/**
 * Aggregates all style sheets in a document. Every time a new STYLE element is
 * found, it is added to the style sheet aggreagator by means of the
 * {@link #addStyleSheet(CSSStyleSheet)} method. HTML elements have a
 * <code>style</code> object that has a list of <code>CSSStyleDeclaration</code>
 * instances. The instances inserted in that list are obtained by means of the
 * getStyleDeclarations(HTMLElementImpl, String, String, String)} method.
 */
/**
 * @author Administrator
 *
 */
public class StyleSheetAggregator {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(StyleSheetAggregator.class);

	/** The attribute. */
	private String attribute;

	/** The attributeValue. */
	private String attributeValue;

	/** The attributeOperator. */
	private String attributeOperator = "";

	/** The element. */
	private String htmlElement;

	/** The document. */
	private final HTMLDocumentImpl document;

	/** The class maps by element. */
	private final Map<String, Map<String, Collection<StyleRuleInfo>>> classMapsByElement = new HashMap<String, Map<String, Collection<StyleRuleInfo>>>();

	/** The attr maps by element. */
	private final Map<String, Map<String, Collection<StyleRuleInfo>>> attrMapsByElement = new HashMap<String, Map<String, Collection<StyleRuleInfo>>>();

	/** The id maps by element. */
	private final Map<String, Map<String, Collection<StyleRuleInfo>>> idMapsByElement = new HashMap<String, Map<String, Collection<StyleRuleInfo>>>();

	/** The rules by element. */
	private final Map<String, Collection<StyleRuleInfo>> rulesByElement = new HashMap<String, Collection<StyleRuleInfo>>();

	/** The pseudo element. */
	private String pseudoElement;

	private int cnt = 1;

	/**
	 * Instantiates a new style sheet aggregator.
	 *
	 * @param document
	 *            the document
	 */
	public StyleSheetAggregator(HTMLDocumentImpl document) {
		this.document = document;
	}

	/**
	 * Adds the style sheets.
	 *
	 * @param styleSheets
	 *            the style sheets
	 * @throws MalformedURLException
	 *             the malformed url exception
	 * @throws UnsupportedEncodingException
	 */
	public final void addStyleSheets(List<CSSStyleSheet> styleSheets)
			throws MalformedURLException, UnsupportedEncodingException {
		Iterator<CSSStyleSheet> i = styleSheets.iterator();
		while (i.hasNext()) {
			CSSStyleSheet sheet = i.next();
			this.addStyleSheet(sheet);
		}
	}

	/**
	 * Adds the style sheet.
	 *
	 * @param styleSheet
	 *            the style sheet
	 * @throws MalformedURLException
	 *             the malformed url exception
	 * @throws UnsupportedEncodingException
	 */
	private final void addStyleSheet(CSSStyleSheet styleSheet)
			throws MalformedURLException, UnsupportedEncodingException {
		CSSRuleList ruleList = styleSheet.getCssRules();
		int length = ruleList.getLength();
		for (int i = 0; i < length; i++) {
			CSSRule rule = ruleList.item(i);
			this.addRule(styleSheet, rule);
		}
	}

	/**
	 * Adds the rule.
	 *
	 * @param styleSheet
	 *            the style sheet
	 * @param rule
	 *            the rule
	 * @throws MalformedURLException
	 *             the malformed url exception
	 * @throws UnsupportedEncodingException
	 */
	private final void addRule(CSSStyleSheet styleSheet, CSSRule rule)
			throws MalformedURLException, UnsupportedEncodingException {
		HTMLDocumentImpl document = this.document;
		if (rule instanceof CSSStyleRule) {
			CSSStyleRule sr = (CSSStyleRule) rule;
			String selectorList = sr.getSelectorText();
			String[] selectors = selectorList.split(",");
			ArrayList<SelectorMatcher> selectorMatchers = null;
			for (String commaTok : selectors) {
				selectorMatchers = new ArrayList<SelectorMatcher>();				
				String selectorPart = commaTok.toLowerCase();
				String lastSelectorText = null;
				StringTokenizer tok = new StringTokenizer(selectorPart, " \t\r\n");
				if (tok.hasMoreTokens()) {
					selectorMatchers = new ArrayList<SelectorMatcher>();
					SelectorMatcher prevSelector = null;
					while(true) {
						String token = tok.nextToken();
						if (">".equals(token)) {
							if (prevSelector != null) {
								prevSelector.setSelectorType(SelectorMatcher.PARENT);
							}
							continue;
						} else if ("+".equals(token)) {
							if (prevSelector != null) {
								prevSelector.setSelectorType(SelectorMatcher.PRECEEDING_SIBLING);
							}
							continue;
						}
						int colonIdx = token.indexOf(':');
						String selectorText = colonIdx == -1 ? token : token.substring(0, colonIdx);
						pseudoElement = colonIdx == -1 ? null : token.substring(colonIdx + 1);
						prevSelector = new SelectorMatcher(selectorText, pseudoElement);
						selectorMatchers.add(prevSelector);
						if (!tok.hasMoreTokens()) {
							lastSelectorText = selectorText;
							break;
						}
					}
				}

				if (lastSelectorText != null) {
					int dotIdx = lastSelectorText.indexOf('.');
					if (dotIdx != -1) {
						String elemtl = lastSelectorText.substring(0, dotIdx);
						String classtl = lastSelectorText.substring(dotIdx + 1);
						this.addClassRule(elemtl, classtl, sr, selectorMatchers);
					} else {
						int poundIdx = lastSelectorText.indexOf('#');
						if (poundIdx != -1) {
							String elemtl = lastSelectorText.substring(0, poundIdx);
							String idtl = lastSelectorText.substring(poundIdx + 1);
							this.addIdRule(elemtl, idtl, sr, selectorMatchers);
						} else {
							String elemtl = lastSelectorText;
							this.addElementRule(elemtl, sr, selectorMatchers);
						}
					}
				}
			}

			if (selectorList.contains("[") && selectorList.endsWith("]")) {
				SelectorMatcher sm = new SelectorMatcher();
				AttributeSelector am = new AttributeSelector();
				String selector = am.getAttributeSelector(selectorList.replace("\"", ""));
				attributeOperator = sm.getOperator(selector);
				SelectorInfo si = am.getSelector(selector, attributeOperator);
				attribute = si.getAttribute();
				attributeValue = si.getAttributeValue();
				int parenthesis = selectorList.indexOf('[');
				htmlElement = selectorList.substring(0, parenthesis);
				this.addAttributeRule(htmlElement, attributeValue, sr, selectorMatchers);
			}
		} else if (rule instanceof CSSImportRule) {
			UserAgentContext uacontext = document.getUserAgentContext();
			if (uacontext.isExternalCSSEnabled()) {
				CSSImportRule importRule = (CSSImportRule) rule;
				if (CSSUtilities.matchesMedia(importRule.getMedia(), uacontext)) {
					String href = importRule.getHref();
					try {
						CSSStyleSheet sheet = CSSUtilities.parse(href, document);
						if (sheet != null) {
							this.addStyleSheet(sheet);
						}
					} catch (Exception err) {
						logger.error("Unable to parse CSS. URI=[" + href + "]." + err);
					}
				}
			}
		} else if (rule instanceof CSSMediaRule) {
			CSSMediaRule mrule = (CSSMediaRule) rule;
			MediaList mediaList = mrule.getMedia();
			if (CSSUtilities.matchesMedia(mediaList, document.getUserAgentContext())) {
				CSSRuleList ruleList = mrule.getCssRules();
				int length = ruleList.getLength();
				for (int i = 0; i < length; i++) {
					CSSRule subRule = ruleList.item(i);
					this.addRule(styleSheet, subRule);
				}
			}
		}
	}

	/**
	 * Adds the class rule.
	 *
	 * @param elemtl
	 *            the elemtl
	 * @param classtl
	 *            the classtl
	 * @param styleRule
	 *            the style rule
	 * @param ancestorSelectors
	 *            the ancestor selectors
	 */
	private final void addClassRule(String elemtl, String classtl, CSSStyleRule styleRule,
			ArrayList<SelectorMatcher> ancestorSelectors) {

		if (elemtl == null || "".equals(elemtl)) {
			elemtl = "*";
		}

		Map<String, Collection<StyleRuleInfo>> classMap = this.classMapsByElement.get(elemtl);
		if (classMap == null) {
			classMap = new HashMap<String, Collection<StyleRuleInfo>>();
			this.classMapsByElement.put(elemtl, classMap);
		}
		Collection<StyleRuleInfo> rules = classMap.get(classtl);
		if (rules == null) {
			rules = new LinkedList<StyleRuleInfo>();
			classMap.put(classtl, rules);
		}
		rules.add(new StyleRuleInfo(ancestorSelectors, styleRule));
	}

	/**
	 * Adds the attribute rule.
	 *
	 * @param elemtl
	 *            the elemtl
	 * @param classtl
	 *            the classtl
	 * @param styleRule
	 *            the style rule
	 * @param ancestorSelectors
	 *            the ancestor selectors
	 */
	private final void addAttributeRule(String elemtl, String attrtl, CSSStyleRule styleRule,
			ArrayList<SelectorMatcher> ancestorSelectors) {

		if (elemtl == null || "".equals(elemtl)) {
			elemtl = "*";
		}

		Map<String, Collection<StyleRuleInfo>> attrMap = this.attrMapsByElement.get(elemtl);
		if (attrMap == null) {
			attrMap = new HashMap<String, Collection<StyleRuleInfo>>();
			this.attrMapsByElement.put(elemtl, attrMap);
		}
		Collection<StyleRuleInfo> rules = attrMap.get(attrtl);
		if (rules == null) {
			rules = new LinkedList<StyleRuleInfo>();
			attrMap.put(attrtl, rules);
		}
		rules.add(new StyleRuleInfo(ancestorSelectors, styleRule));
	}

	/**
	 * Adds the id rule.
	 *
	 * @param elemtl
	 *            the elemtl
	 * @param idtl
	 *            the idtl
	 * @param styleRule
	 *            the style rule
	 * @param ancestorSelectors
	 *            the ancestor selectors
	 */
	private final void addIdRule(String elemtl, String idtl, CSSStyleRule styleRule,
			ArrayList<SelectorMatcher> ancestorSelectors) {

		if (elemtl == null || "".equals(elemtl)) {
			elemtl = "*";
		}

		Map<String, Collection<StyleRuleInfo>> idsMap = this.idMapsByElement.get(elemtl);
		if (idsMap == null) {
			idsMap = new HashMap<String, Collection<StyleRuleInfo>>();
			this.idMapsByElement.put(elemtl, idsMap);
		}
		Collection<StyleRuleInfo> rules = idsMap.get(idtl);
		if (rules == null) {
			rules = new LinkedList<StyleRuleInfo>();
			idsMap.put(idtl, rules);
		}
		rules.add(new StyleRuleInfo(ancestorSelectors, styleRule));
	}

	/**
	 * Adds the element rule.
	 *
	 * @param elemtl
	 *            the elemtl
	 * @param styleRule
	 *            the style rule
	 * @param ancestorSelectors
	 *            the ancestor selectors
	 */
	private final void addElementRule(String elemtl, CSSStyleRule styleRule,
			ArrayList<SelectorMatcher> ancestorSelectors) {

		if (elemtl == null || "".equals(elemtl)) {
			elemtl = "*";
		}

		Collection<StyleRuleInfo> rules = this.rulesByElement.get(elemtl);
		if (rules == null) {
			rules = new LinkedList<StyleRuleInfo>();
			this.rulesByElement.put(elemtl, rules);
		}
		rules.add(new StyleRuleInfo(ancestorSelectors, styleRule));
	}

	/**
	 * Gets the active style declarations.
	 *
	 * @param element
	 *            the element
	 * @param elementName
	 *            the element name
	 * @param elementId
	 *            the element id
	 * @param className
	 *            the class name
	 * @param pseudoNames
	 *            the pseudo names
	 * @return the active style declarations
	 */
	public final Collection<CSSStyleDeclaration> getActiveStyleDeclarations(HTMLElementImpl element, String elementName,
			String elementId, String className, Set pseudoNames, NamedNodeMap attributes) {

		Collection<CSSStyleDeclaration> styleDeclarations = null;
		String elementTL = elementName.toLowerCase();
		Collection<StyleRuleInfo> elementRules = this.rulesByElement.get(elementTL);
		if (elementRules != null) {
			AttributeSelector am = new AttributeSelector();
			String psElement = pseudoElement;
			if (psElement != null && psElement.contains("(")) {
				psElement = psElement.substring(0, psElement.indexOf('('));
			}

			int c = cnt++;

			Iterator<StyleRuleInfo> i = elementRules.iterator();
			while (i.hasNext()) {
				StyleRuleInfo styleRuleInfo = i.next();
				for (int a = 0; a < styleRuleInfo.getAncestorSelectors().size(); a++) {
					SelectorMatcher selectorMatcher = styleRuleInfo.getAncestorSelectors().get(a);
					if (am.matchesPseudoClassSelector(selectorMatcher.getPseudoElement(), element, c)
							&& element.getPseudoNames().contains(psElement)) {
						styleDeclarations = putStyleDeclarations(elementRules, styleDeclarations, element, pseudoNames);
					} else if (!am.matchesPseudoClassSelector(selectorMatcher.getPseudoElement(), element, c)
							&& !element.getPseudoNames().contains(psElement)) {
						styleDeclarations = putStyleDeclarations(elementRules, styleDeclarations, element, pseudoNames);
					}
				}
			}
		}
		elementRules = this.rulesByElement.get("*");
		if (elementRules != null) {
			styleDeclarations = putStyleDeclarations(elementRules, styleDeclarations, element, pseudoNames);
		}

		if (className != null) {
			String classNameTL = className.toLowerCase();
			Map<String, Collection<StyleRuleInfo>> classMaps = this.classMapsByElement.get(elementTL);
			if (classMaps != null) {
				Collection<StyleRuleInfo> classRules = classMaps.get(classNameTL);
				if (classRules != null) {
					styleDeclarations = putStyleDeclarations(classRules, styleDeclarations, element, pseudoNames);
				}
			}
			classMaps = this.classMapsByElement.get("*");
			if (classMaps != null) {
				Collection<StyleRuleInfo> classRules = classMaps.get(classNameTL);
				if (classRules != null) {
					styleDeclarations = putStyleDeclarations(classRules, styleDeclarations, element, pseudoNames);
				}
			}
		}
		if (elementId != null) {
			Map<String, Collection<StyleRuleInfo>> idMaps = this.idMapsByElement.get(elementTL);
			if (idMaps != null) {
				String elementIdTL = elementId.toLowerCase();
				Collection<StyleRuleInfo> idRules = idMaps.get(elementIdTL);
				if (idRules != null) {
					styleDeclarations = putStyleDeclarations(idRules, styleDeclarations, element, pseudoNames);
				}
			}
			idMaps = this.idMapsByElement.get("*");
			if (idMaps != null) {
				String elementIdTL = elementId.toLowerCase();
				Collection<StyleRuleInfo> idRules = idMaps.get(elementIdTL);
				if (idRules != null) {
					styleDeclarations = putStyleDeclarations(idRules, styleDeclarations, element, pseudoNames);
				}
			}
		}

		if (attributes != null && attributes.getLength() > 0) {
			for (int i1 = 0; i1 < attributes.getLength(); i1++) {
				Attr attr = (Attr) attributes.item(i1);
				if (isAttributeOperator(attr, element)) {
					Map<String, Collection<StyleRuleInfo>> classMaps = this.attrMapsByElement.get(htmlElement);
					if (classMaps != null) {
						Collection<StyleRuleInfo> attrRules = classMaps.get(attributeValue);
						if (attrRules != null) {
							styleDeclarations = putStyleDeclarations(attrRules, styleDeclarations, element,
									pseudoNames);
						}
					}
				}
			}
		}
		return styleDeclarations;
	}

	/**
	 * Affected by pseudo name in ancestor.
	 *
	 * @param element
	 *            the element
	 * @param ancestor
	 *            the ancestor
	 * @param elementName
	 *            the element name
	 * @param elementId
	 *            the element id
	 * @param classArray
	 *            the class array
	 * @param pseudoName
	 *            the pseudo name
	 * @return true, if successful
	 */
	public final boolean affectedByPseudoNameInAncestor(HTMLElementImpl element, HTMLElementImpl ancestor,
			String elementName, String elementId, String[] classArray, String pseudoName) {
		String elementTL = elementName.toLowerCase();
		Collection<StyleRuleInfo> elementRules = this.rulesByElement.get(elementTL);
		if (elementRules != null) {
			return isAffectedByPseudoNameInAncestor(elementRules, ancestor, element, pseudoName);
		}
		elementRules = this.rulesByElement.get("*");
		if (elementRules != null) {
			return isAffectedByPseudoNameInAncestor(elementRules, ancestor, element, pseudoName);
		}
		if (classArray != null) {
			for (String className : classArray) {
				String classNameTL = className.toLowerCase();
				Map<String, Collection<StyleRuleInfo>> classMaps = this.classMapsByElement.get(elementTL);
				if (classMaps != null) {
					Collection<StyleRuleInfo> classRules = classMaps.get(classNameTL);
					if (classRules != null) {
						return isAffectedByPseudoNameInAncestor(elementRules, ancestor, element, pseudoName);
					}
				}
				classMaps = this.classMapsByElement.get("*");
				if (classMaps != null) {
					Collection<StyleRuleInfo> classRules = classMaps.get(classNameTL);
					if (classRules != null) {
						return isAffectedByPseudoNameInAncestor(elementRules, ancestor, element, pseudoName);
					}
				}
			}
		}
		if (elementId != null) {
			Map<String, Collection<StyleRuleInfo>> idMaps = this.idMapsByElement.get(elementTL);
			if (idMaps != null) {
				String elementIdTL = elementId.toLowerCase();
				Collection<StyleRuleInfo> idRules = idMaps.get(elementIdTL);
				if (idRules != null) {
					return isAffectedByPseudoNameInAncestor(elementRules, ancestor, element, pseudoName);
				}
			}
			idMaps = this.idMapsByElement.get("*");
			if (idMaps != null) {
				String elementIdTL = elementId.toLowerCase();
				Collection<StyleRuleInfo> idRules = idMaps.get(elementIdTL);
				if (idRules != null) {
					return isAffectedByPseudoNameInAncestor(elementRules, ancestor, element, pseudoName);
				}
			}
		}
		return false;
	}

	/**
	 * is affected by pseudo name in ancestor
	 *
	 * @param elementRules
	 * @param ancestor
	 * @param element
	 * @param pseudoName
	 * @return
	 */
	private boolean isAffectedByPseudoNameInAncestor(Collection<StyleRuleInfo> elementRules, HTMLElementImpl ancestor,
			HTMLElementImpl element, String pseudoName) {
		if (elementRules != null) {
			Iterator<StyleRuleInfo> i = elementRules.iterator();
			while (i.hasNext()) {
				StyleRuleInfo styleRuleInfo = i.next();
				CSSStyleSheet styleSheet = styleRuleInfo.getStyleRule().getParentStyleSheet();
				if (styleSheet != null && styleSheet.getDisabled()) {
					continue;
				}
				if (styleRuleInfo.affectedByPseudoNameInAncestor(element, ancestor, pseudoName)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * put style declarations
	 *
	 * @param elementRules
	 * @param styleDeclarations
	 * @param element
	 * @param pseudoNames
	 * @return
	 */
	private Collection<CSSStyleDeclaration> putStyleDeclarations(Collection<StyleRuleInfo> elementRules,
			Collection<CSSStyleDeclaration> styleDeclarations, HTMLElementImpl element, Set pseudoNames) {
		Iterator<StyleRuleInfo> i = elementRules.iterator();
		while (i.hasNext()) {
			StyleRuleInfo styleRuleInfo = i.next();
			if (styleRuleInfo.isSelectorMatch(element, pseudoNames)) {
				CSSStyleRule styleRule = styleRuleInfo.getStyleRule();
				CSSStyleSheet styleSheet = styleRule.getParentStyleSheet();
				if (styleSheet != null && styleSheet.getDisabled()) {
					continue;
				}
				if (styleDeclarations == null) {
					styleDeclarations = new LinkedList<CSSStyleDeclaration>();
				}
				styleDeclarations.add(styleRule.getStyle());
			}
		}
		return styleDeclarations;
	}

	/**
	 * is attribute operator
	 *
	 * @param attr
	 * @return
	 */
	private boolean isAttributeOperator(Attr attr, HTMLElementImpl element) {

		String name = attr.getName();
		String value = attr.getValue();
		String nodeName = element.getNodeName();

		switch (attributeOperator) {
		case SelectorMatcher.OP_EQUAL:
			if (name.equals(attribute) && value.equals(attributeValue) && "*".equals(htmlElement)) {
				return true;
			} else if (name.equals(attribute) && value.equals(attributeValue)
					&& nodeName.equalsIgnoreCase(htmlElement)) {
				return true;
			}
			break;
		case SelectorMatcher.OP_TILDE_EQUAL:
		case SelectorMatcher.OP_STAR_EQUAL:
			if (name.equals(attribute) && value.contains(attributeValue) && "*".equals(htmlElement)) {
				return true;
			} else if (name.equals(attribute) && value.contains(attributeValue)
					&& nodeName.equalsIgnoreCase(htmlElement)) {
				return true;
			}
			break;
		case SelectorMatcher.OP_PIPE_EQUAL:
		case SelectorMatcher.OP_CIRCUMFLEX_EQUAL:
			if (name.equals(attribute) && value.startsWith(attributeValue) && "*".equals(htmlElement)) {
				return true;
			} else if (name.equals(attribute) && value.startsWith(attributeValue)
					&& nodeName.equalsIgnoreCase(htmlElement)) {
				return true;
			}
			break;
		case SelectorMatcher.OP_DOLLAR_EQUAL:
			if (name.equals(attribute) && value.endsWith(attributeValue) && "*".equals(htmlElement)) {
				return true;
			} else if (name.equals(attribute) && value.endsWith(attributeValue)
					&& nodeName.equalsIgnoreCase(htmlElement)) {
				return true;
			}
			break;
		case SelectorMatcher.OP_ALL:
			if (name.equals(attribute) && "*".equals(htmlElement)) {
				return true;
			} else if (name.equals(attribute) && nodeName.equalsIgnoreCase(htmlElement)) {
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}
}
