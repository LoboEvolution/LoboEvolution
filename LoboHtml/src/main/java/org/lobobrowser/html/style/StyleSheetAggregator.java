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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import org.lobo.common.Strings;
import org.lobobrowser.html.dom.HTMLElement;
import org.lobobrowser.html.dom.HTMLInputElement;
import org.lobobrowser.html.dom.HTMLSelectElement;
import org.lobobrowser.html.dom.HTMLTextAreaElement;
import org.lobobrowser.html.dom.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.dom.domimpl.HTMLElementImpl;
import org.lobobrowser.html.dom.domimpl.NodeImpl;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.css.CSSStyleSheet;
import com.gargoylesoftware.css.dom.AbstractCSSRuleImpl;
import com.gargoylesoftware.css.dom.CSSMediaRuleImpl;
import com.gargoylesoftware.css.dom.CSSRuleListImpl;
import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;
import com.gargoylesoftware.css.dom.CSSStyleRuleImpl;
import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;
import com.gargoylesoftware.css.dom.MediaListImpl;
import com.gargoylesoftware.css.parser.CSSErrorHandler;
import com.gargoylesoftware.css.parser.CSSException;
import com.gargoylesoftware.css.parser.CSSOMParser;
import com.gargoylesoftware.css.parser.CSSParseException;
import com.gargoylesoftware.css.parser.condition.Condition;
import com.gargoylesoftware.css.parser.condition.Condition.ConditionType;
import com.gargoylesoftware.css.parser.javacc.CSS3Parser;
import com.gargoylesoftware.css.parser.selector.ChildSelector;
import com.gargoylesoftware.css.parser.selector.DescendantSelector;
import com.gargoylesoftware.css.parser.selector.DirectAdjacentSelector;
import com.gargoylesoftware.css.parser.selector.ElementSelector;
import com.gargoylesoftware.css.parser.selector.GeneralAdjacentSelector;
import com.gargoylesoftware.css.parser.selector.Selector;
import com.gargoylesoftware.css.parser.selector.PseudoElementSelector;
import com.gargoylesoftware.css.parser.selector.Selector.SelectorType;
import com.gargoylesoftware.css.parser.selector.SelectorList;
import com.gargoylesoftware.css.parser.selector.SimpleSelector;

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

	private static final Pattern NTH_NUMERIC = Pattern.compile("\\d+");
	private static final Pattern NTH_COMPLEX = Pattern.compile("[+-]?\\d*n\\w*([+-]\\w\\d*)?");
	private static final Pattern UNESCAPE_SELECTOR = Pattern.compile("\\\\([\\[\\]\\.:])");

	private static final Set<String> CSS2_PSEUDO_CLASSES = new HashSet<>(
			Arrays.asList("link", "visited", "hover", "active", "focus", "lang", "first-child"));

	private static final Set<String> CSS3_PSEUDO_CLASSES = new HashSet<>(
			Arrays.asList("checked", "disabled", "enabled", "indeterminated", "root", "target", "not()", "nth-child()",
					"nth-last-child()", "nth-of-type()", "nth-last-of-type()", "last-child", "first-of-type",
					"last-of-type", "only-child", "only-of-type", "empty", "optional", "required"));

	private final HTMLDocumentImpl document;
	
	CSSStyleSheetImpl.CSSStyleSheetRuleIndex index = null;

	public StyleSheetAggregator(HTMLDocumentImpl document) {
		this.document = document;
	}

	public final void addStyleSheets(List<CSSStyleSheetImpl> styleSheets) throws Exception {
		for (CSSStyleSheetImpl sheet : styleSheets) {
			addStyleSheet(sheet);
		}
	}

	public final boolean affectedByPseudoNameInAncestor(HTMLElementImpl element, HTMLElementImpl ancestor,
			String elementName, String elementId, String[] classArray, String pseudoName) {
		return false;
	}

	public final List<CSSStyleDeclarationImpl> getActiveStyleDeclarations(HTMLElementImpl element, String elementName,
			String elementId, final String[] classes) {

		List<CSSStyleDeclarationImpl> declaration = new ArrayList<CSSStyleDeclarationImpl>();
		final List<CSSStyleSheetImpl.SelectorEntry> matchingRules = selects(getRuleIndex(), element, elementName, false, classes);

		for (CSSStyleSheetImpl.SelectorEntry entry : matchingRules) {
			declaration.add(entry.getRule().getStyle());
		}

		return declaration;
	}

	private CSSStyleSheetImpl.CSSStyleSheetRuleIndex getRuleIndex() {
		if (index == null) {
			index = new CSSStyleSheetImpl.CSSStyleSheetRuleIndex();
		}
		return index;
	}

	private List<CSSStyleSheetImpl.SelectorEntry> selects(final CSSStyleSheetImpl.CSSStyleSheetRuleIndex index,
			final HTMLElement element, final String pseudoElement, final boolean fromQuerySelectorAll, final String[] classes) {

		final List<CSSStyleSheetImpl.SelectorEntry> matchingRules = new ArrayList<>();

		final String elementName = element.getNodeName().toLowerCase();
		final Iterator<CSSStyleSheetImpl.SelectorEntry> iter = index.getSelectorEntriesIteratorFor(elementName, classes);

		CSSStyleSheetImpl.SelectorEntry entry = iter.next();
		while (null != entry) {
			if (selects(entry.getSelector(), element, pseudoElement, fromQuerySelectorAll)) {
				matchingRules.add(entry);
			}
			entry = iter.next();
		}

		for (CSSStyleSheetImpl.CSSStyleSheetRuleIndex child : index.getChildren()) {
			matchingRules.addAll(selects(child, element, pseudoElement, fromQuerySelectorAll, classes));
		}

		return matchingRules;
	}

	private static boolean selects(final Selector selector, final HTMLElement element, final String pseudoElement,
			final boolean fromQuerySelectorAll) {
		switch (selector.getSelectorType()) {
		case ELEMENT_NODE_SELECTOR:
			final ElementSelector es = (ElementSelector) selector;
			final String name = es.getLocalNameLowerCase();
			if (name == null || name.equals(element.getNodeName().toLowerCase())) {
				final List<Condition> conditions = es.getConditions();
				if (conditions != null) {
					for (Condition condition : conditions) {
						if (!selects(condition, element, fromQuerySelectorAll)) {
							return false;
						}
					}
				}
				return true;
			}
			return false;

		case CHILD_SELECTOR:
			final Node parentNode = element.getParentNode();
			if (!(parentNode instanceof HTMLElement)) {
				return false; // for instance parent is a DocumentFragment
			}
			final ChildSelector cs = (ChildSelector) selector;
			return selects(cs.getSimpleSelector(), element, pseudoElement, fromQuerySelectorAll)
					&& selects(cs.getAncestorSelector(), (HTMLElement) parentNode, pseudoElement, fromQuerySelectorAll);

		case DESCENDANT_SELECTOR:
			final DescendantSelector ds = (DescendantSelector) selector;
			final SimpleSelector simpleSelector = ds.getSimpleSelector();
			if (selects(simpleSelector, element, pseudoElement, fromQuerySelectorAll)) {
				Node ancestor = element;
				if (simpleSelector.getSelectorType() != SelectorType.PSEUDO_ELEMENT_SELECTOR) {
					ancestor = ancestor.getParentNode();
				}
				final Selector dsAncestorSelector = ds.getAncestorSelector();
				while (ancestor instanceof HTMLElement) {
					if (selects(dsAncestorSelector, (HTMLElement) ancestor, pseudoElement, fromQuerySelectorAll)) {
						return true;
					}
					ancestor = ancestor.getParentNode();
				}
			}
			return false;

		case DIRECT_ADJACENT_SELECTOR:
			final DirectAdjacentSelector das = (DirectAdjacentSelector) selector;
			if (selects(das.getSimpleSelector(), element, pseudoElement, fromQuerySelectorAll)) {
				Node prev = element.getPreviousSibling();
				while (prev != null && !(prev instanceof HTMLElement)) {
					prev = prev.getPreviousSibling();
				}
				return prev != null
						&& selects(das.getSelector(), (HTMLElement) prev, pseudoElement, fromQuerySelectorAll);
			}
			return false;

		case GENERAL_ADJACENT_SELECTOR:
			final GeneralAdjacentSelector gas = (GeneralAdjacentSelector) selector;
			if (selects(gas.getSimpleSelector(), element, pseudoElement, fromQuerySelectorAll)) {
				for (Node prev1 = element.getPreviousSibling(); prev1 != null; prev1 = prev1.getPreviousSibling()) {
					if (prev1 instanceof HTMLElement
							&& selects(gas.getSelector(), (HTMLElement) prev1, pseudoElement, fromQuerySelectorAll)) {
						return true;
					}
				}
			}
			return false;
		case PSEUDO_ELEMENT_SELECTOR:
			if (pseudoElement != null && pseudoElement.length() != 0 && pseudoElement.charAt(0) == ':') {
				final String pseudoName = ((PseudoElementSelector) selector).getLocalName();
				return pseudoName.equals(pseudoElement.substring(1));
			}
			return false;

		default:
			return false;
		}
	}

	private static boolean selects(final Condition condition, final HTMLElement element, final boolean fromQuerySelectorAll) {

		switch (condition.getConditionType()) {
		case ID_CONDITION:
			return condition.getValue().equals(element.getId());

		case CLASS_CONDITION:
			String v3 = condition.getValue();
			if (v3.indexOf('\\') > -1) {
				v3 = UNESCAPE_SELECTOR.matcher(v3).replaceAll("$1");
			}
			final String a3 = element.getAttribute("class");
			return selectsWhitespaceSeparated(v3, a3);

		case ATTRIBUTE_CONDITION:
			String value = condition.getValue();
			if (value != null) {
				if (value.indexOf('\\') > -1) {
					value = UNESCAPE_SELECTOR.matcher(value).replaceAll("$1");
				}
				final String attrValue = element.getAttribute(condition.getLocalName());
				return Strings.isNotBlank(attrValue) && attrValue.equals(value);
			}
			return element.hasAttribute(condition.getLocalName());

		case PREFIX_ATTRIBUTE_CONDITION:
			final String prefixValue = condition.getValue();
			String attr = element.getAttribute(condition.getLocalName());
			return !"".equals(prefixValue) && Strings.isNotBlank(attr) && element.getAttribute(condition.getLocalName()).startsWith(prefixValue);

		case SUFFIX_ATTRIBUTE_CONDITION:
			final String suffixValue = condition.getValue();
			String attrib = element.getAttribute(condition.getLocalName());
			return !"".equals(suffixValue) && Strings.isNotBlank(attrib) && element.getAttribute(condition.getLocalName()).endsWith(suffixValue);

		case SUBSTRING_ATTRIBUTE_CONDITION:
			final String substringValue = condition.getValue();
			return !"".equals(substringValue)
					&& element.getAttribute(condition.getLocalName()).contains(substringValue);

		case BEGIN_HYPHEN_ATTRIBUTE_CONDITION:
			final String v = condition.getValue();
			final String a = element.getAttribute(condition.getLocalName());
			return selects(v, a, '-');

		case ONE_OF_ATTRIBUTE_CONDITION:
			final String v2 = condition.getValue();
			final String a2 = element.getAttribute(condition.getLocalName());
			return selects(v2, a2, ' ');

		case LANG_CONDITION:
			final String lcLang = condition.getValue();
			final int lcLangLength = lcLang.length();
			for (Node node = element; node instanceof HTMLElement; node = node.getParentNode()) {
				final String nodeLang = ((HTMLElement) node).getAttribute("lang");
				if (Strings.isNotBlank(nodeLang)) {
					return nodeLang.startsWith(lcLang)
							&& (nodeLang.length() == lcLangLength || '-' == nodeLang.charAt(lcLangLength));
				}
			}
			return false;

		case PSEUDO_CLASS_CONDITION:
			return selectsPseudoClass(condition, element, fromQuerySelectorAll);

		default:
			return false;
		}
	}

	private static boolean selects(final String condition, final String attribute, final char separator) {
		final int conditionLength = condition.length();
		if (conditionLength < 1) {
			return false;
		}

		final int attribLength = attribute.length();
		if (attribLength < conditionLength) {
			return false;
		}
		if (attribLength > conditionLength) {
			if (separator == attribute.charAt(conditionLength) && attribute.startsWith(condition)) {
				return true;
			}
			if (separator == attribute.charAt(attribLength - conditionLength - 1) && attribute.endsWith(condition)) {
				return true;
			}
			if (attribLength + 1 > conditionLength) {
				final StringBuilder tmp = new StringBuilder(conditionLength + 2);
				tmp.append(separator).append(condition).append(separator);
				return attribute.contains(tmp);
			}
			return false;
		}
		return attribute.equals(condition);
	}

	private static boolean selectsWhitespaceSeparated(final String condition, final String attribute) {
		final int conditionLength = condition.length();
		if (conditionLength < 1 || attribute == null) {
			return false;
		}

		final int attribLength = attribute.length();
		if (attribLength < conditionLength) {
			return false;
		}

		int pos = attribute.indexOf(condition);
		while (pos != -1) {
			if (pos > 0 && !Character.isWhitespace(attribute.charAt(pos - 1))) {
				pos = attribute.indexOf(condition, pos + 1);
			} else {
				final int lastPos = pos + condition.length();
				if (lastPos >= attribLength || Character.isWhitespace(attribute.charAt(lastPos))) {
					return true;
				}
				pos = attribute.indexOf(condition, pos + 1);
			}
		}

		return false;
	}

	private static boolean selectsPseudoClass(final Condition condition, final HTMLElement element,
			final boolean fromQuerySelectorAll) {

		final String value = condition.getValue();
		switch (value) {
		case "root":
			NodeImpl parentDOMNodeImpl = (NodeImpl) element.getParentNode();
			return parentDOMNodeImpl != null && parentDOMNodeImpl.getNodeType() == Node.DOCUMENT_TYPE_NODE;

		case "enabled":
			return element.hasAttribute("enabled");

		case "disabled":
			return element.hasAttribute("disabled");

		case "checked":
			return (element instanceof HTMLInputElement && ((HTMLInputElement) element).getChecked());

		case "required":
			return (element instanceof HTMLInputElement || element instanceof HTMLSelectElement
					|| element instanceof HTMLTextAreaElement) && element.hasAttribute("required");

		case "optional":
			return (element instanceof HTMLInputElement || element instanceof HTMLSelectElement
					|| element instanceof HTMLTextAreaElement) && !element.hasAttribute("required");

		case "first-child":
			for (Node n = element.getPreviousSibling(); n != null; n = n.getPreviousSibling()) {
				if (n instanceof HTMLElement) {
					return false;
				}
			}
			return true;

		case "last-child":
			for (Node n = element.getNextSibling(); n != null; n = n.getNextSibling()) {
				if (n instanceof HTMLElement) {
					return false;
				}
			}
			return true;

		case "first-of-type":
			final String firstType = element.getNodeName();
			for (Node n = element.getPreviousSibling(); n != null; n = n.getPreviousSibling()) {
				if (n instanceof HTMLElement && n.getNodeName().equals(firstType)) {
					return false;
				}
			}
			return true;

		case "last-of-type":
			final String lastType = element.getNodeName();
			for (Node n = element.getNextSibling(); n != null; n = n.getNextSibling()) {
				if (n instanceof HTMLElement && n.getNodeName().equals(lastType)) {
					return false;
				}
			}
			return true;

		case "only-child":
			for (Node n = element.getPreviousSibling(); n != null; n = n.getPreviousSibling()) {
				if (n instanceof HTMLElement) {
					return false;
				}
			}
			for (Node n = element.getNextSibling(); n != null; n = n.getNextSibling()) {
				if (n instanceof HTMLElement) {
					return false;
				}
			}
			return true;

		case "only-of-type":
			final String type = element.getNodeName();
			for (Node n = element.getPreviousSibling(); n != null; n = n.getPreviousSibling()) {
				if (n instanceof HTMLElement && n.getNodeName().equals(type)) {
					return false;
				}
			}
			for (Node n = element.getNextSibling(); n != null; n = n.getNextSibling()) {
				if (n instanceof HTMLElement && n.getNodeName().equals(type)) {
					return false;
				}
			}
			return true;

		case "empty":
			return isEmpty(element);

		default:
			if (value.startsWith("nth-child(")) {
				final String nth = value.substring(value.indexOf('(') + 1, value.length() - 1);
				int index = 0;
				for (Node n = element; n != null; n = n.getPreviousSibling()) {
					if (n instanceof HTMLElement) {
						index++;
					}
				}
				return getNth(nth, index);
			} else if (value.startsWith("nth-last-child(")) {
				final String nth = value.substring(value.indexOf('(') + 1, value.length() - 1);
				int index = 0;
				for (Node n = element; n != null; n = n.getNextSibling()) {
					if (n instanceof HTMLElement) {
						index++;
					}
				}
				return getNth(nth, index);
			} else if (value.startsWith("nth-of-type(")) {
				final String nthType = element.getNodeName();
				final String nth = value.substring(value.indexOf('(') + 1, value.length() - 1);
				int index = 0;
				for (Node n = element; n != null; n = n.getPreviousSibling()) {
					if (n instanceof HTMLElement && n.getNodeName().equals(nthType)) {
						index++;
					}
				}
				return getNth(nth, index);
			} else if (value.startsWith("nth-last-of-type(")) {
				final String nthLastType = element.getNodeName();
				final String nth = value.substring(value.indexOf('(') + 1, value.length() - 1);
				int index = 0;
				for (Node n = element; n != null; n = n.getNextSibling()) {
					if (n instanceof HTMLElement && n.getNodeName().equals(nthLastType)) {
						index++;
					}
				}
				return getNth(nth, index);
			} else if (value.startsWith("not(")) {
				final String selectors = value.substring(value.indexOf('(') + 1, value.length() - 1);
				final AtomicBoolean errorOccured = new AtomicBoolean(false);
				final CSSErrorHandler errorHandler = new CSSErrorHandler() {
					@Override
					public void warning(final CSSParseException exception) throws CSSException {
						// ignore
					}

					@Override
					public void fatalError(final CSSParseException exception) throws CSSException {
						errorOccured.set(true);
					}

					@Override
					public void error(final CSSParseException exception) throws CSSException {
						errorOccured.set(true);
					}
				};
				final CSSOMParser parser = new CSSOMParser(new CSS3Parser());
				parser.setErrorHandler(errorHandler);
				try {
					final SelectorList selectorList = parser.parseSelectors(selectors);
					if (errorOccured.get() || selectorList == null || selectorList.size() != 1) {
						throw new CSSException("Invalid selectors: " + selectors);
					}

					validateSelectors(selectorList, 9, element);

					return !selects(selectorList.get(0), element, null, fromQuerySelectorAll);
				} catch (final IOException e) {
					throw new CSSException("Error parsing CSS selectors from '" + selectors + "': " + e.getMessage());
				}
			}
			return false;
		}
	}

	private static void validateSelectors(final SelectorList selectorList, final int documentMode, final Node domNode)
			throws CSSException {
		for (Selector selector : selectorList) {
			if (!isValidSelector(selector, documentMode, domNode)) {
				throw new CSSException("Invalid selector: " + selector);
			}
		}
	}

	private static boolean isValidSelector(final Selector selector, final int documentMode, final Node domNode) {
		switch (selector.getSelectorType()) {
		case ELEMENT_NODE_SELECTOR:
			final List<Condition> conditions = ((ElementSelector) selector).getConditions();
			if (conditions != null) {
				for (Condition condition : conditions) {
					if (!isValidCondition(condition, documentMode, domNode)) {
						return false;
					}
				}
			}
			return true;
		case DESCENDANT_SELECTOR:
			final DescendantSelector ds = (DescendantSelector) selector;
			return isValidSelector(ds.getAncestorSelector(), documentMode, domNode)
					&& isValidSelector(ds.getSimpleSelector(), documentMode, domNode);
		case CHILD_SELECTOR:
			final ChildSelector cs = (ChildSelector) selector;
			return isValidSelector(cs.getAncestorSelector(), documentMode, domNode)
					&& isValidSelector(cs.getSimpleSelector(), documentMode, domNode);
		case DIRECT_ADJACENT_SELECTOR:
			final DirectAdjacentSelector das = (DirectAdjacentSelector) selector;
			return isValidSelector(das.getSelector(), documentMode, domNode)
					&& isValidSelector(das.getSimpleSelector(), documentMode, domNode);
		case GENERAL_ADJACENT_SELECTOR:
			final GeneralAdjacentSelector gas = (GeneralAdjacentSelector) selector;
			return isValidSelector(gas.getSelector(), documentMode, domNode)
					&& isValidSelector(gas.getSimpleSelector(), documentMode, domNode);
		default:
			return true; // at least in a first time to break less stuff
		}
	}

	private static boolean isValidCondition(final Condition condition, final int documentMode, final Node domNode) {
		switch (condition.getConditionType()) {
		case ATTRIBUTE_CONDITION:
		case ID_CONDITION:
		case LANG_CONDITION:
		case ONE_OF_ATTRIBUTE_CONDITION:
		case BEGIN_HYPHEN_ATTRIBUTE_CONDITION:
		case CLASS_CONDITION:
		case PREFIX_ATTRIBUTE_CONDITION:
		case SUBSTRING_ATTRIBUTE_CONDITION:
		case SUFFIX_ATTRIBUTE_CONDITION:
			return true;
		case PSEUDO_CLASS_CONDITION:
			String value = condition.getValue();
			if (value.endsWith(")")) {
				if (value.endsWith("()")) {
					return false;
				}
				value = value.substring(0, value.indexOf('(') + 1) + ')';
			}
			if (documentMode < 9) {
				return CSS2_PSEUDO_CLASSES.contains(value);
			}

			if (!CSS2_PSEUDO_CLASSES.contains(value) && !domNode.hasChildNodes()) {
				throw new CSSException("Syntax Error");
			}

			if ("nth-child()".equals(value)) {
				final String arg = Strings.substringBetween(condition.getValue(), "(", ")").trim();
				return "even".equalsIgnoreCase(arg) || "odd".equalsIgnoreCase(arg) || NTH_NUMERIC.matcher(arg).matches()
						|| NTH_COMPLEX.matcher(arg).matches();
			}
			return CSS3_PSEUDO_CLASSES.contains(value);
		default:
			return true;
		}
	}

	private static boolean isEmpty(final HTMLElement element) {
		for (Node n = element.getFirstChild(); n != null; n = n.getNextSibling()) {
			if (n instanceof HTMLElement || n instanceof Text) {
				return false;
			}
		}
		return true;
	}

	private static boolean getNth(final String nth, final int index) {
		if ("odd".equalsIgnoreCase(nth)) {
			return index % 2 != 0;
		}

		if ("even".equalsIgnoreCase(nth)) {
			return index % 2 == 0;
		}

		final int nIndex = nth.indexOf('n');
		int a = 0;
		if (nIndex != -1) {
			String value = nth.substring(0, nIndex).trim();
			if ("-".equals(value)) {
				a = -1;
			} else {
				if (value.length() > 0 && value.charAt(0) == '+') {
					value = value.substring(1);
				}
				a = HtmlValues.getPixelSize(value, null, 1);
			}
		}

		String value = nth.substring(nIndex + 1).trim();
		if (value.length() > 0 && value.charAt(0) == '+') {
			value = value.substring(1);
		}
		final int b = HtmlValues.getPixelSize(value, null, 0);
		if (a == 0) {
			return index == b && b > 0;
		}

		final double n = (index - b) / (double) a;
		return n >= 0 && n % 1 == 0;
	}

	private final void addStyleSheet(CSSStyleSheetImpl styleSheet) throws Exception {
		CSSRuleListImpl ruleList = styleSheet.getCssRules();
		index = styleSheet.getRuleIndex();
		if (index == null) {
			index = new CSSStyleSheetImpl.CSSStyleSheetRuleIndex();
		}
		index(index, ruleList, new HashSet<String>());
	}

	private void index(final CSSStyleSheetImpl.CSSStyleSheetRuleIndex index, final CSSRuleListImpl ruleList,
			final Set<String> alreadyProcessing) {
		for (AbstractCSSRuleImpl rule : ruleList.getRules()) {
			if (rule instanceof CSSStyleRuleImpl) {
				final CSSStyleRuleImpl styleRule = (CSSStyleRuleImpl) rule;
				final SelectorList selectors = styleRule.getSelectors();
				for (Selector selector : selectors) {
					final SimpleSelector simpleSel = selector.getSimpleSelector();
					if (SelectorType.ELEMENT_NODE_SELECTOR == simpleSel.getSelectorType()) {
						final ElementSelector es = (ElementSelector) simpleSel;
						boolean wasClass = false;
						final List<Condition> conds = es.getConditions();
						if (conds != null && conds.size() == 1) {
							final Condition c = conds.get(0);
							if (ConditionType.CLASS_CONDITION == c.getConditionType()) {
								index.addClassSelector(es, c.getValue(), selector, styleRule);
								wasClass = true;
							}
						}
						if (!wasClass) {
							index.addElementSelector(es, selector, styleRule);
						}
					} else {
						index.addOtherSelector(selector, styleRule);
					}
				}
			} else if (rule instanceof CSSMediaRuleImpl) {
				final CSSMediaRuleImpl mediaRule = (CSSMediaRuleImpl) rule;
				final MediaListImpl mediaList = mediaRule.getMediaList();
				if (mediaList.getLength() == 0 && index.getMediaList().getLength() == 0) {
					index(index, mediaRule.getCssRules(), alreadyProcessing);
				} else {
					index(index.addMedia(mediaList), mediaRule.getCssRules(), alreadyProcessing);
				}
			}
		}
	}

}