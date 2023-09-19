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

package org.loboevolution.html.style;

import org.htmlunit.cssparser.parser.CSSErrorHandler;
import org.htmlunit.cssparser.parser.CSSException;
import org.htmlunit.cssparser.parser.CSSOMParser;
import org.htmlunit.cssparser.parser.CSSParseException;
import org.htmlunit.cssparser.parser.condition.AttributeCondition;
import org.htmlunit.cssparser.parser.condition.Condition;
import org.htmlunit.cssparser.parser.condition.Condition.ConditionType;
import org.htmlunit.cssparser.parser.condition.NotPseudoClassCondition;
import org.htmlunit.cssparser.parser.javacc.CSS3Parser;
import org.htmlunit.cssparser.parser.media.MediaQuery;
import org.htmlunit.cssparser.parser.selector.*;
import org.htmlunit.cssparser.parser.selector.Selector.SelectorType;
import org.htmlunit.cssparser.dom.*;
import org.loboevolution.common.Strings;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.html.dom.*;
import org.loboevolution.html.dom.domimpl.*;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.js.css.StyleSheetListImpl;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.Text;
import org.loboevolution.html.node.css.CSSStyleSheet;
import org.loboevolution.html.node.js.Window;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * Aggregates all style sheets in a document. Every time a new STYLE element is
 * found, it is added to the style sheet aggreagator by means of the
 * {@link #addStyleSheet(CSSStyleSheetImpl)} method.
 */
public class StyleSheetAggregator {

	private static final Pattern UNESCAPE_SELECTOR = Pattern.compile("\\\\([\\[\\]\\.:])");

	private static final Set<String> CSS2_PSEUDO_CLASSES = new HashSet<>(Arrays.asList(
			"link", "visited", "hover", "active",
			"focus", "lang", "first-child"));

	private static final Set<String> CSS3_PSEUDO_CLASSES = new HashSet<>(Arrays.asList(
			"checked", "disabled", "enabled", "indeterminated", "root", "target", "not()",
			"nth-child()", "nth-last-child()", "nth-of-type()", "nth-last-of-type()",
			"last-child", "first-of-type", "last-of-type", "only-child", "only-of-type", "empty",
			"optional", "required", "valid", "invalid"));

	private static final Set<String> CSS4_PSEUDO_CLASSES = new HashSet<>(Arrays.asList("focus-within", "focus-visible"));

	private final List<CSSStyleSheetImpl> styleSheets;

	private boolean mouseOver = false;

	private HTMLDocumentImpl doc;


	static {
		CSS3_PSEUDO_CLASSES.addAll(CSS2_PSEUDO_CLASSES);
		CSS4_PSEUDO_CLASSES.addAll(CSS3_PSEUDO_CLASSES);
	}
	
	/**
	 * <p>Constructor for StyleSheetAggregator.</p>
	 */
	public StyleSheetAggregator() {
		styleSheets = new ArrayList<>();
	}

	/**
	 * <p>addStyleSheets.</p>
	 *
	 * @param styleSheets a {@link org.loboevolution.html.js.css.StyleSheetListImpl} object.
	 */
	public final void addStyleSheets(StyleSheetListImpl styleSheets) {
		for (CSSStyleSheet sheet : styleSheets) {
			org.loboevolution.html.js.css.CSSStyleSheetImpl impl = (org.loboevolution.html.js.css.CSSStyleSheetImpl)sheet;
			addStyleSheet(impl.getCssStyleSheet());
		}
	}
	
	/**
	 * <p>getActiveStyleDeclarations.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param elementName a {@link java.lang.String} object.
	 * @param classes an array of {@link java.lang.String} objects.
	 * @param mouseOver a {@link java.lang.Boolean } object.
	 * @return a {@link java.util.List} object.
	 */
	public final List<CSSStyleSheetImpl.SelectorEntry> getActiveStyleDeclarations(HTMLElementImpl element, String elementName, final String[] classes, boolean mouseOver) {
		List<CSSStyleSheetImpl.SelectorEntry> matchingRules = new ArrayList<>();
		for (CSSStyleSheetImpl sheet : styleSheets) {
			if (matchingRules.size() == 0) {
				matchingRules = selects(sheet.getRuleIndex(), element, elementName, mouseOver, classes);
			} else {
				final List<CSSStyleSheetImpl.SelectorEntry> _matchingRules = selects(sheet.getRuleIndex(), element, elementName, mouseOver, classes);
				matchingRules.addAll(_matchingRules);
			}
		}
		return matchingRules;
	}
	
	/**
	 * <p>selects.</p>
	 *
	 * @param selector a {@link org.htmlunit.cssparser.parser.selector.Selector} object.
	 * @param node a {@link org.loboevolution.html.node.Node} object.
	 * @param pseudoElement a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean selects(final Selector selector, final Node node, final String pseudoElement) {
		return new StyleSheetAggregator().selects(selector, (HTMLElement)node, pseudoElement, false);
	}

	private List<CSSStyleSheetImpl.SelectorEntry> selects(final CSSStyleSheetImpl.CSSStyleSheetRuleIndex index,
			final HTMLElement element, final String pseudoElement, final boolean mouseOver,
			final String[] classes) {

		final List<CSSStyleSheetImpl.SelectorEntry> matchingRules = new ArrayList<>();
		if (isActive(element, index.getMediaList())) {
			final String elementName = element.getNodeName().toLowerCase();
			final Iterator<CSSStyleSheetImpl.SelectorEntry> iter = index.getSelectorEntriesIteratorFor(elementName, classes);
			CSSStyleSheetImpl.SelectorEntry entry = iter.next();
			while (null != entry) {
				if (selects(entry.getSelector(), element, pseudoElement, mouseOver)) {
					matchingRules.add(entry);
				}
				entry = iter.next();
			}

			for (CSSStyleSheetImpl.CSSStyleSheetRuleIndex child : index.getChildren()) {
				matchingRules.addAll(selects(child, element, pseudoElement, mouseOver, classes));
			}
		}
		return matchingRules;
	}

	private boolean selects(final Selector selector, final HTMLElement element, final String pseudoElement, final boolean mouseOver) {
		switch (selector.getSelectorType()) {
		case ELEMENT_NODE_SELECTOR:
			final ElementSelector es = (ElementSelector) selector;
			final String name = es.getLocalNameLowerCase();
			if (name == null || name.equals(element.getNodeName().toLowerCase())) {
				final List<Condition> conditions = es.getConditions();
				if (conditions != null) {
					for (Condition condition : conditions) {
						if (!selects(condition, element, mouseOver)) {
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
			return selects(cs.getSimpleSelector(), element, pseudoElement, mouseOver)
					&& selects(cs.getAncestorSelector(), (HTMLElement) parentNode, pseudoElement, mouseOver);

		case DESCENDANT_SELECTOR:
			final DescendantSelector ds = (DescendantSelector) selector;
			final SimpleSelector simpleSelector = ds.getSimpleSelector();
			if (selects(simpleSelector, element, pseudoElement, mouseOver)) {
				Node ancestor = element;
				if (simpleSelector.getSelectorType() != SelectorType.PSEUDO_ELEMENT_SELECTOR) {
					ancestor = ancestor.getParentNode();
				}
				final Selector dsAncestorSelector = ds.getAncestorSelector();
				while (ancestor instanceof HTMLElement) {
					if (selects(dsAncestorSelector, (HTMLElement) ancestor, pseudoElement, mouseOver)) {
						return true;
					}
					ancestor = ancestor.getParentNode();
				}
			}
			return false;

		case DIRECT_ADJACENT_SELECTOR:
			final DirectAdjacentSelector das = (DirectAdjacentSelector) selector;
			if (selects(das.getSimpleSelector(), element, pseudoElement, mouseOver)) {
				Node prev = element.getPreviousSibling();
				while (prev != null && !(prev instanceof HTMLElement)) {
					prev = prev.getPreviousSibling();
				}
				return prev != null
						&& selects(das.getSelector(), (HTMLElement) prev, pseudoElement, mouseOver);
			}
			return false;

		case GENERAL_ADJACENT_SELECTOR:
			final GeneralAdjacentSelector gas = (GeneralAdjacentSelector) selector;
			if (selects(gas.getSimpleSelector(), element, pseudoElement, mouseOver)) {
				for (Node prev1 = element.getPreviousSibling(); prev1 != null; prev1 = prev1.getPreviousSibling()) {
					if (prev1 instanceof HTMLElement
							&& selects(gas.getSelector(), (HTMLElement) prev1, pseudoElement, mouseOver)) {
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

	private boolean selects(final Condition condition, final HTMLElement element, final boolean mouseOver) {
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
			final AttributeCondition prefixAttributeCondition = (AttributeCondition) condition;
			final String prefixValue = prefixAttributeCondition.getValue();
			final String attribute = element.getAttribute(prefixAttributeCondition.getLocalName());
			if (prefixAttributeCondition.isCaseInSensitive()) {
				return Strings.isNotBlank(prefixValue)
						&& Strings.isNotBlank(attribute)
						&& Strings.startsWithIgnoreCase(attribute, prefixValue);
			}
			return Strings.isNotBlank(prefixValue)
					&& Strings.isNotBlank(attribute)
					&& attribute.startsWith(prefixValue);

		case SUFFIX_ATTRIBUTE_CONDITION:
			final AttributeCondition suffixAttributeCondition = (AttributeCondition) condition;
			final String suffixValue = suffixAttributeCondition.getValue();
			final String attribute2 = element.getAttribute(suffixAttributeCondition.getLocalName());
			if (suffixAttributeCondition.isCaseInSensitive()) {
				return Strings.isNotBlank(suffixValue)
						&& Strings.isNotBlank(attribute2)
						&& Strings.endsWithIgnoreCase(attribute2, suffixValue);
			}
			return Strings.isNotBlank(suffixValue)
					&& Strings.isNotBlank(attribute2)
					&& attribute2.endsWith(suffixValue);

		case SUBSTRING_ATTRIBUTE_CONDITION:
			final AttributeCondition substringAttributeCondition = (AttributeCondition) condition;
			final String substringValue = substringAttributeCondition.getValue();
			final String attribute3 = element.getAttribute(substringAttributeCondition.getLocalName());
			if (substringAttributeCondition.isCaseInSensitive()) {
				return Strings.isNotBlank(substringValue)
						&& Strings.isNotBlank(attribute3)
						&& Strings.containsIgnoreCase(attribute3, substringValue);
			}
			return Strings.isNotBlank(substringValue)
					&& Strings.isNotBlank(attribute3)
					&& attribute3.contains(substringValue);

		case BEGIN_HYPHEN_ATTRIBUTE_CONDITION:
			final AttributeCondition beginHyphenAttributeCondition = (AttributeCondition) condition;
			final String v = beginHyphenAttributeCondition.getValue();
			final String a = element.getAttribute(beginHyphenAttributeCondition.getLocalName());
			if (beginHyphenAttributeCondition.isCaseInSensitive()) {
				return  Strings.isNotBlank(v)
						&& Strings.isNotBlank(a)
						&& selectsHyphenSeparated(
						v.toLowerCase(Locale.ROOT),
						a.toLowerCase(Locale.ROOT));
			}
			return Strings.isNotBlank(v)
					&& Strings.isNotBlank(a)
					&& selectsHyphenSeparated(v, a);

		case ONE_OF_ATTRIBUTE_CONDITION:
			final AttributeCondition oneOfAttributeCondition = (AttributeCondition) condition;
			final String v2 = oneOfAttributeCondition.getValue();
			final String a2 = element.getAttribute(oneOfAttributeCondition.getLocalName());
			if (oneOfAttributeCondition.isCaseInSensitive()) {
				return Strings.isNotBlank(v2)
						&& Strings.isNotBlank(a2)
						&& selectsOneOf(
						v2.toLowerCase(Locale.ROOT),
						a2.toLowerCase(Locale.ROOT));
			}
			return Strings.isNotBlank(v2)
					&& Strings.isNotBlank(a2)
					&& selectsOneOf(v2, a2);

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

		case NOT_PSEUDO_CLASS_CONDITION:
			final NotPseudoClassCondition notPseudoCondition = (NotPseudoClassCondition) condition;
			final SelectorList selectorList = notPseudoCondition.getSelectors();
			for (final Selector selector : selectorList) {
				if (selects(selector, element, null)) {
					return false;
				}
			}
			return true;

		case PSEUDO_CLASS_CONDITION:
			return selectsPseudoClass(condition, element, mouseOver);

		default:
			return false;
		}
	}

	private boolean selectsPseudoClass(final Condition condition, final HTMLElement element, final boolean mouseOver) {
		final String value = condition.getValue();
		switch (value) {
			case "hover":
				setMouseOver(true);
				return mouseOver;

			case "root":
				NodeImpl parentDOMNodeImpl = (NodeImpl) element.getParentNode();
				return parentDOMNodeImpl != null && parentDOMNodeImpl.getNodeType() == Node.DOCUMENT_NODE;

			case "enabled":
				return ((element instanceof HTMLInputElement) || (element instanceof HTMLButtonElement) ||
						(element instanceof HTMLSelectElement) || (element instanceof HTMLTextAreaElement)) &&
						(element.hasAttribute("enabled") ||  !element.hasAttribute("enabled") && !element.hasAttribute("disabled"));

			case "disabled":
				return ((element instanceof HTMLInputElement) || (element instanceof HTMLButtonElement) ||
						(element instanceof HTMLSelectElement) || (element instanceof HTMLTextAreaElement)) && element.hasAttribute("disabled");

			case "placeholder":
				return element.hasAttribute("placeholder");

			case "read-only":
				return element.hasAttribute("readonly");

			case "read-write":
				return !element.hasAttribute("readonly");

			case "out-of-range":
				if (element instanceof HTMLInputElement) {
					HTMLInputElementImpl input = (HTMLInputElementImpl)element;
					if ("number".equals(input.getType())) {
						String minTxt = input.getAttribute("min");
						String maxTxt = input.getAttribute("max");

						int min = minTxt == null ? 0 : Integer.parseInt(input.getAttribute("min"));
						int max = maxTxt == null ? Integer.MAX_VALUE : Integer.parseInt(input.getAttribute("max"));
						int valueNumber = Integer.parseInt(input.getValue());
						return (valueNumber < min || valueNumber > max);
					}
				}

			case "checked":
				if (element instanceof HTMLInputElement) {
					return ((HTMLInputElement) element).isChecked();
				}

				if (element instanceof HTMLOptionElement) {
					AtomicInteger selected = new AtomicInteger(0);
					AtomicInteger isSelected = new AtomicInteger(0);
					HTMLSelectElement selectElement = (HTMLSelectElement) element.getParentNode();
					HTMLOptionsCollectionImpl optionsCollection = (HTMLOptionsCollectionImpl) selectElement.getOptions();
					optionsCollection.forEach(opt -> {
						HTMLOptionElement optionElement = (HTMLOptionElement) opt;
						if (optionElement.hasAttribute("selected")) {
							selected.incrementAndGet();
						}

						if (optionElement.isSelected()) {
							isSelected.incrementAndGet();
						}
					});

					if (isSelected.get() > 0) {
						return ((HTMLOptionElement) element).isSelected();
					}

					if (selected.get() > 0) {
						return element.hasAttribute("selected");
					}
				}
			case "required":
				return (element instanceof HTMLInputElement || element instanceof HTMLSelectElement
						|| element instanceof HTMLTextAreaElement) && element.hasAttribute("required");

			case "optional":
				return (element instanceof HTMLInputElement || element instanceof HTMLSelectElement
						|| element instanceof HTMLTextAreaElement) && !element.hasAttribute("required");

			case "link":
				return (element instanceof HTMLLinkElement);

			case "visited":
				if (element instanceof HTMLLinkElement) {
					HTMLLinkElementImpl elem = (HTMLLinkElementImpl)element;
					final HtmlRendererConfig config = elem.getHtmlRendererConfig();
					return config.isVisited(elem.getHref());
				} else{
					return false;
				}

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

			case "target":
				HTMLElementImpl impl = (HTMLElementImpl) element;
				final HTMLDocumentImpl document = (HTMLDocumentImpl) impl.getDocumentNode();
				final String ref = document.getBaseURI();
				return Strings.isNotBlank(ref) && ref.contains("#") && ref.split("#")[1].equals(element.getId());

			default:
				if (value.startsWith("nth-child(")) {
					final String nth = value.substring(value.indexOf('(') + 1, value.length() - 1);
					int index = 0;
					for (Node n = element; n != null; n = n.getPreviousSibling()) {
						if (n instanceof HTMLElement) {
							index++;
						}
					}
					return getNth(element, nth, index);
				} else if (value.startsWith("nth-last-child(")) {
					final String nth = value.substring(value.indexOf('(') + 1, value.length() - 1);
					int index = 0;
					for (Node n = element; n != null; n = n.getNextSibling()) {
						if (n instanceof HTMLElement) {
							index++;
						}
					}
					return getNth(element, nth, index);
				} else if (value.startsWith("nth-of-type(")) {
					final String nthType = element.getNodeName();
					final String nth = value.substring(value.indexOf('(') + 1, value.length() - 1);
					int index = 0;
					for (Node n = element; n != null; n = n.getPreviousSibling()) {
						if (n instanceof HTMLElement && n.getNodeName().equals(nthType)) {
							index++;
						}
					}
					return getNth(element, nth, index);
				} else if (value.startsWith("nth-last-of-type(")) {
					final String nthLastType = element.getNodeName();
					final String nth = value.substring(value.indexOf('(') + 1, value.length() - 1);
					int index = 0;
					for (Node n = element; n != null; n = n.getNextSibling()) {
						if (n instanceof HTMLElement && n.getNodeName().equals(nthLastType)) {
							index++;
						}
					}
					return getNth(element, nth, index);
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
							return false;
						}

						for (final Selector selector : selectorList) {
							if (!isValidSelector(selector, 9, element)) {
								return false;
							}
						}

						return !selects(selectorList.get(0), element, null, mouseOver);
					} catch (final IOException e) {
						throw new CSSException("Error parsing CSS selectors from '" + selectors + "': " + e.getMessage());
					}
				}
				return false;
		}
	}

	private boolean selectsWhitespaceSeparated(final String condition, final String attribute) {
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

	private static boolean selectsHyphenSeparated(final String condition, final String attribute) {
		final int conditionLength = condition.length();
		if (conditionLength < 1) {
			if (Strings.isNotBlank(attribute)) {
				final int attribLength = attribute.length();
				return attribLength == 0 || '-' == attribute.charAt(0);
			}
			return false;
		}

		final int attribLength = attribute.length();
		if (attribLength < conditionLength) {
			return false;
		}
		if (attribLength > conditionLength) {
			return '-' == attribute.charAt(conditionLength)
					&& attribute.startsWith(condition);
		}
		return attribute.equals(condition);
	}

	private static boolean selectsOneOf(final String condition, final String attribute) {

		final int conditionLength = condition.length();
		if (conditionLength < 1) {
			return false;
		}

		final int attribLength = attribute.length();
		if (attribLength < conditionLength) {
			return false;
		}
		if (attribLength > conditionLength) {
			if (' ' == attribute.charAt(conditionLength)
					&& attribute.startsWith(condition)) {
				return true;
			}
			if (' ' == attribute.charAt(attribLength - conditionLength - 1)
					&& attribute.endsWith(condition)) {
				return true;
			}
			if (attribLength + 1 > conditionLength) {
				final StringBuilder tmp = new StringBuilder(conditionLength + 2);
				tmp.append(' ').append(condition).append(' ');
				return attribute.contains(tmp);
			}
			return false;
		}
		return attribute.equals(condition);
	}

	private static boolean isValidSelector(final Selector selector, final int documentMode, final Node domNode) {
		switch (selector.getSelectorType()) {
			case ELEMENT_NODE_SELECTOR:
				final List<Condition> conditions = ((ElementSelector) selector).getConditions();
				if (conditions != null) {
					for (final Condition condition : conditions) {
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
				return true;
		}
	}

	private static boolean isValidCondition(final Condition condition, final int documentMode, final Node domNode) {
		if (Objects.requireNonNull(condition.getConditionType()) == ConditionType.PSEUDO_CLASS_CONDITION) {
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

			if (!CSS2_PSEUDO_CLASSES.contains(value)
					&& !domNode.hasChildNodes()) {
				throw new CSSException("Syntax Error");
			}

			if ("nth-child(".equals(value)) {
				final String arg = value.substring(value.indexOf('(') + 1, value.length() - 1);
				return "even".equalsIgnoreCase(arg) || "odd".equalsIgnoreCase(arg);
			}

			return CSS4_PSEUDO_CLASSES.contains(value);
		}
		return true;
	}


	/**
	 * <p>isActive.</p>
	 *
	 * @param window a {@link org.loboevolution.html.node.js.Window} object.
	 * @param mediaList a {@link MediaListImpl} object.
	 * @return a boolean.
	 */
	public static boolean isActive(Window window, final MediaListImpl mediaList) {
		if (mediaList.getLength() == 0) {
			return true;
		}

		for (int i = 0; i < mediaList.getLength(); i++) {
			final MediaQuery mediaQuery = mediaList.mediaQuery(i);
			boolean isActive = isActive(window, mediaQuery);
			if (mediaQuery.isNot()) {
				isActive = !isActive;
			}
			if (isActive) {
				return true;
			}
		}
		return false;
	}

	private static boolean isActive(HTMLElement element, final MediaListImpl mediaList) {
        HTMLElementImpl impl = (HTMLElementImpl) element;
		final HTMLDocumentImpl document = (HTMLDocumentImpl) impl.getDocumentNode();
		return isActive(document.getDefaultView(), mediaList);
    }

	private static boolean isActive(Window window, final MediaQuery mediaQuery) {
		final String mediaType = mediaQuery.getMedia();
		if ("screen".equalsIgnoreCase(mediaType) || "all".equalsIgnoreCase(mediaType) || "print".equalsIgnoreCase(mediaType)) {

			for (final Property property : mediaQuery.getProperties()) {
				int val = -1;
				if(property.getValue() == null) return "resolution".equals(property.getName()) || "orientation".equals(property.getName());
				String value = property.getValue().getCssText();
				switch (property.getName()) {
					case "max-width":
						if (HtmlValues.isUnits(value)) {
							val = HtmlValues.getPixelSize(value, null, window, -1);
						}
						if (val == -1 || val < window.getInnerWidth()) {
							return false;
						}
						break;
					case "max-height":
						if (HtmlValues.isUnits(value)) {
							val = HtmlValues.getPixelSize(value, null, window, -1);
						}
						if (val == -1 || val < window.getInnerHeight()) {
							return false;
						}
						break;

					case "min-width":
						if (HtmlValues.isUnits(value)) {
							val = HtmlValues.getPixelSize(value, null, window, -1);
						}
						if (val == -1 || val > window.getInnerWidth()) {
							return false;
						}
						break;

					case "min-height":
						if (HtmlValues.isUnits(value)) {
							val = HtmlValues.getPixelSize(value, null, window, -1);
						}
						if (val == -1 || val > window.getInnerHeight()) {
							return false;
						}
						break;

					case "max-device-width":
						if (HtmlValues.isUnits(value)) {
							val = HtmlValues.getPixelSize(value, null, window, -1);
						}
						if (val == -1 || val < window.getScreen().getWidth()) {
							return false;
						}
						break;
					case "max-device-height":
						if (HtmlValues.isUnits(value)) {
							val = HtmlValues.getPixelSize(value, null, window, -1);
						}
						if (val == -1 || val < window.getScreen().getHeight()) {
							return false;
						}
						break;

					case "min-device-width":
						if (HtmlValues.isUnits(value)) {
							val = HtmlValues.getPixelSize(value, null, window, -1);
						}
						if (val == -1 || val > window.getScreen().getWidth()) {
							return false;
						}
						break;
					case "min-device-height":
						if (HtmlValues.isUnits(value)) {
							val = HtmlValues.getPixelSize(value, null, window, -1);
						}
						if (val == -1 || val > window.getScreen().getHeight()) {
							return false;
						}
						break;
					case "resolution":
						val = HtmlValues.resolutionValue(property.getValue());
						if (val == -1 || Math.round(val) != window.getScreen().getPixelDepth()) {
							return false;
						}
						break;
					case "max-resolution":
						val = HtmlValues.resolutionValue(property.getValue());
						if (val == -1 || val < window.getScreen().getPixelDepth()) {
							return false;
						}
						break;

					case "min-resolution":
						val = HtmlValues.resolutionValue(property.getValue());
						if (val == -1 || val > window.getScreen().getPixelDepth()) {
							return false;
						}
						break;

					case "orientation":
						if ("portrait".equals(value)) {
							if (window.getInnerWidth() > window.getInnerHeight()) {
								return false;
							}
						} else if ("landscape".equals(value)) {
							if (window.getInnerWidth() < window.getInnerHeight()) {
								return false;
							}
						} else {
							return false;
						}
						break;
					default:
				}
			}
		}

		return mediaQuery.getProperties().size() != 0 || !"print".equalsIgnoreCase(mediaType);
	}
	
	private boolean isEmpty(final HTMLElement element) {
		for (Node n = element.getFirstChild(); n != null; n = n.getNextSibling()) {
			if (n instanceof HTMLElement || n instanceof Text) {
				return false;
			}
		}
		return true;
	}

	private boolean getNth(HTMLElement element, final String nth, final int index) {
		HTMLDocumentImpl doc =  (HTMLDocumentImpl)element.getOwnerDocument();

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
				a = HtmlValues.getPixelSize(value, null, doc.getDefaultView(), 1);
			}
		}

		String value = nth.substring(nIndex + 1).trim();
		if (value.length() > 0 && value.charAt(0) == '+') {
			value = value.substring(1);
		}
		final int b = HtmlValues.getPixelSize(value, null, doc.getDefaultView(), 0);
		if (a == 0) {
			return index == b && b > 0;
		}

		final double n = (index - b) / (double) a;
		return n >= 0 && n % 1 == 0;
	}

	private void addStyleSheet(CSSStyleSheetImpl styleSheet) {
		CSSRuleListImpl ruleList = styleSheet.getCssRules();
		CSSStyleSheetImpl.CSSStyleSheetRuleIndex index = styleSheet.getRuleIndex();
		if (index == null) {
			index = new CSSStyleSheetImpl.CSSStyleSheetRuleIndex();
		}
		index(index, ruleList);
		styleSheet.setRuleIndex(index);
		styleSheets.add(styleSheet);
	}

	private void index(final CSSStyleSheetImpl.CSSStyleSheetRuleIndex index, final CSSRuleListImpl ruleList) {
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
					index(index, mediaRule.getCssRules());
				} else {
					index(index.addMedia(mediaList), mediaRule.getCssRules());
				}
			} else if (rule instanceof CSSPageRuleImpl) {
				final CSSPageRuleImpl pageRule = (CSSPageRuleImpl) rule;
				index(index, pageRule.getParentStyleSheet().getCssRules());
			} else if (rule instanceof CSSImportRuleImpl) {
				try {
					final CSSImportRuleImpl importRule = (CSSImportRuleImpl) rule;
					final MediaListImpl mediaList = importRule.getMedia();
					HTMLDocumentImpl doc = getDoc();
					String uri = doc.getFullURL(importRule.getHref(), doc.getBaseURI()).toString();
					final CSSStyleSheetImpl sheet = CSSUtilities.parseCssExternal(getDoc().getHtmlRendererConfig(), uri, null, doc.getBaseURI(), false);
					if (mediaList.getLength() == 0 && index.getMediaList().getLength() == 0) {
						index(index, sheet.getCssRules());
					} else {
						index(index.addMedia(mediaList), sheet.getCssRules());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public HTMLDocumentImpl getDoc() {
		return doc;
	}

	public void setDoc(HTMLDocumentImpl doc) {
		this.doc = doc;
	}
}
