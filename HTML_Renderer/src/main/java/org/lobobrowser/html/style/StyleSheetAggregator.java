/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.html.style;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.info.StyleRuleInfo;
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
public class StyleSheetAggregator {

    /** The Constant logger. */
    private static final Logger logger = Logger
            .getLogger(StyleSheetAggregator.class.getName());

    /** The document. */
    private final HTMLDocumentImpl document;

    /** The class maps by element. */
    private final Map<String, Map> classMapsByElement = new HashMap<String, Map>();

    /** The id maps by element. */
    private final Map<String, Map> idMapsByElement = new HashMap<String, Map>();

    /** The rules by element. */
    private final Map<String, Collection> rulesByElement = new HashMap<String, Collection>();

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
    public final void addStyleSheets(Collection styleSheets)
            throws MalformedURLException, UnsupportedEncodingException {
        Iterator i = styleSheets.iterator();
        while (i.hasNext()) {
            CSSStyleSheet sheet = (CSSStyleSheet) i.next();
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
            StringTokenizer commaTok = new StringTokenizer(selectorList, ",");
            while (commaTok.hasMoreTokens()) {
                String selectorPart = commaTok.nextToken().toLowerCase();
                ArrayList<SimpleSelector> simpleSelectors = null;
                String lastSelectorText = null;
                StringTokenizer tok = new StringTokenizer(selectorPart,
                        " \t\r\n");
                if (tok.hasMoreTokens()) {
                    simpleSelectors = new ArrayList<SimpleSelector>();
                    SimpleSelector prevSelector = null;
                    SELECTOR_FOR: for (;;) {
                        String token = tok.nextToken();
                        if (">".equals(token)) {
                            if (prevSelector != null) {
                                prevSelector
                                .setSelectorType(SimpleSelector.PARENT);
                            }
                            continue SELECTOR_FOR;
                        } else if ("+".equals(token)) {
                            if (prevSelector != null) {
                                prevSelector
                                .setSelectorType(SimpleSelector.PRECEEDING_SIBLING);
                            }
                            continue SELECTOR_FOR;
                        }
                        int colonIdx = token.indexOf(':');
                        String simpleSelectorText = colonIdx == -1 ? token
                                : token.substring(0, colonIdx);
                        String pseudoElement = colonIdx == -1 ? null : token
                                .substring(colonIdx + 1);
                        prevSelector = new SimpleSelector(simpleSelectorText,
                                pseudoElement);
                        simpleSelectors.add(prevSelector);
                        if (!tok.hasMoreTokens()) {
                            lastSelectorText = simpleSelectorText;
                            break;
                        }
                    }
                }
                if (lastSelectorText != null) {
                    int dotIdx = lastSelectorText.indexOf('.');
                    if (dotIdx != -1) {
                        String elemtl = lastSelectorText.substring(0, dotIdx);
                        String classtl = lastSelectorText.substring(dotIdx + 1);
                        this.addClassRule(elemtl, classtl, sr, simpleSelectors);
                    } else {
                        int poundIdx = lastSelectorText.indexOf('#');
                        if (poundIdx != -1) {
                            String elemtl = lastSelectorText.substring(0,
                                    poundIdx);
                            String idtl = lastSelectorText
                                    .substring(poundIdx + 1);
                            this.addIdRule(elemtl, idtl, sr, simpleSelectors);
                        } else {
                            String elemtl = lastSelectorText;
                            this.addElementRule(elemtl, sr, simpleSelectors);
                        }
                    }
                }
            }
            // TODO: Attribute selectors
        } else if (rule instanceof CSSImportRule) {
            UserAgentContext uacontext = document.getUserAgentContext();
            if (uacontext.isExternalCSSEnabled()) {
                CSSImportRule importRule = (CSSImportRule) rule;
                if (CSSUtilities.matchesMedia(importRule.getMedia(), uacontext)) {

                    String href = importRule.getHref();

                    ArrayList<String> listext = CSSUtilities.cssText(href,
                            document, document.getBaseURI());

                    String text = "";

                    for (int i = 0; i < listext.size(); i++) {
                        text = listext.get(i);

                        if ((text.length() > 0) && !text.startsWith("/*")) {

                            CSSStyleSheet sheet = null;

                            try {
                                sheet = CSSUtilities.parse(
                                        styleSheet.getOwnerNode(), href, text,
                                        document.getBaseURI(), false);

                            } catch (Throwable e) {
                                logger.log(Level.WARNING,
                                        "Unable to parse CSS: " + text);
                            }

                            if (sheet != null) {
                                this.addStyleSheet(sheet);
                            }
                        }
                    }
                }
            }
        } else if (rule instanceof CSSMediaRule) {
            CSSMediaRule mrule = (CSSMediaRule) rule;
            MediaList mediaList = mrule.getMedia();
            if (CSSUtilities.matchesMedia(mediaList,
                    document.getUserAgentContext())) {
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
    private final void addClassRule(String elemtl, String classtl,
            CSSStyleRule styleRule, ArrayList<SimpleSelector> ancestorSelectors) {
        Map<String, Collection> classMap = this.classMapsByElement.get(elemtl);
        if (classMap == null) {
            classMap = new HashMap<String, Collection>();
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
    private final void addIdRule(String elemtl, String idtl,
            CSSStyleRule styleRule, ArrayList<SimpleSelector> ancestorSelectors) {
        Map<String, Collection> idsMap = this.idMapsByElement.get(elemtl);
        if (idsMap == null) {
            idsMap = new HashMap<String, Collection>();
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
            ArrayList<SimpleSelector> ancestorSelectors) {
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
    public final Collection<CSSStyleDeclaration> getActiveStyleDeclarations(
            HTMLElementImpl element, String elementName, String elementId,
            String className, Set pseudoNames) {
        Collection<CSSStyleDeclaration> styleDeclarations = null;
        String elementTL = elementName.toLowerCase();
        Collection elementRules = this.rulesByElement.get(elementTL);
        if (elementRules != null) {
            Iterator i = elementRules.iterator();
            while (i.hasNext()) {
                StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
                if (styleRuleInfo.isSelectorMatch(element, pseudoNames)) {
                    CSSStyleRule styleRule = styleRuleInfo.getStyleRule();
                    CSSStyleSheet styleSheet = styleRule.getParentStyleSheet();
                    if ((styleSheet != null) && styleSheet.getDisabled()) {
                        continue;
                    }
                    if (styleDeclarations == null) {
                        styleDeclarations = new LinkedList<CSSStyleDeclaration>();
                    }
                    styleDeclarations.add(styleRule.getStyle());
                } else {
                }
            }
        }
        elementRules = this.rulesByElement.get("*");
        if (elementRules != null) {
            Iterator i = elementRules.iterator();
            while (i.hasNext()) {
                StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
                if (styleRuleInfo.isSelectorMatch(element, pseudoNames)) {
                    CSSStyleRule styleRule = styleRuleInfo.getStyleRule();
                    CSSStyleSheet styleSheet = styleRule.getParentStyleSheet();
                    if ((styleSheet != null) && styleSheet.getDisabled()) {
                        continue;
                    }
                    if (styleDeclarations == null) {
                        styleDeclarations = new LinkedList<CSSStyleDeclaration>();
                    }
                    styleDeclarations.add(styleRule.getStyle());
                }
            }
        }
        if (className != null) {
            String classNameTL = className.toLowerCase();
            Map<?, ?> classMaps = this.classMapsByElement.get(elementTL);
            if (classMaps != null) {
                Collection classRules = (Collection) classMaps.get(classNameTL);
                if (classRules != null) {
                    Iterator i = classRules.iterator();
                    while (i.hasNext()) {
                        StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
                        if (styleRuleInfo.isSelectorMatch(element, pseudoNames)) {
                            CSSStyleRule styleRule = styleRuleInfo.getStyleRule();
                            CSSStyleSheet styleSheet = styleRule
                                    .getParentStyleSheet();
                            if ((styleSheet != null)
                                    && styleSheet.getDisabled()) {
                                continue;
                            }
                            if (styleDeclarations == null) {
                                styleDeclarations = new LinkedList<CSSStyleDeclaration>();
                            }
                            styleDeclarations.add(styleRule.getStyle());
                        }
                    }
                }
            }
            classMaps = this.classMapsByElement.get("*");
            if (classMaps != null) {
                Collection classRules = (Collection) classMaps.get(classNameTL);
                if (classRules != null) {
                    Iterator i = classRules.iterator();
                    while (i.hasNext()) {
                        StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
                        if (styleRuleInfo.isSelectorMatch(element, pseudoNames)) {
                            CSSStyleRule styleRule = styleRuleInfo.getStyleRule();
                            CSSStyleSheet styleSheet = styleRule
                                    .getParentStyleSheet();
                            if ((styleSheet != null)
                                    && styleSheet.getDisabled()) {
                                continue;
                            }
                            if (styleDeclarations == null) {
                                styleDeclarations = new LinkedList<CSSStyleDeclaration>();
                            }
                            styleDeclarations.add(styleRule.getStyle());
                        }
                    }
                }
            }
        }
        if (elementId != null) {
            Map<?, ?> idMaps = this.idMapsByElement.get(elementTL);
            if (idMaps != null) {
                String elementIdTL = elementId.toLowerCase();
                Collection idRules = (Collection) idMaps.get(elementIdTL);
                if (idRules != null) {
                    Iterator i = idRules.iterator();
                    while (i.hasNext()) {
                        StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
                        if (styleRuleInfo.isSelectorMatch(element, pseudoNames)) {
                            CSSStyleRule styleRule = styleRuleInfo.getStyleRule();
                            CSSStyleSheet styleSheet = styleRule
                                    .getParentStyleSheet();
                            if ((styleSheet != null)
                                    && styleSheet.getDisabled()) {
                                continue;
                            }
                            if (styleDeclarations == null) {
                                styleDeclarations = new LinkedList<CSSStyleDeclaration>();
                            }
                            styleDeclarations.add(styleRule.getStyle());
                        }
                    }
                }
            }
            idMaps = this.idMapsByElement.get("*");
            if (idMaps != null) {
                String elementIdTL = elementId.toLowerCase();
                Collection idRules = (Collection) idMaps.get(elementIdTL);
                if (idRules != null) {
                    Iterator i = idRules.iterator();
                    while (i.hasNext()) {
                        StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
                        if (styleRuleInfo.isSelectorMatch(element, pseudoNames)) {
                            CSSStyleRule styleRule = styleRuleInfo.getStyleRule();
                            CSSStyleSheet styleSheet = styleRule
                                    .getParentStyleSheet();
                            if ((styleSheet != null)
                                    && styleSheet.getDisabled()) {
                                continue;
                            }
                            if (styleDeclarations == null) {
                                styleDeclarations = new LinkedList<CSSStyleDeclaration>();
                            }
                            styleDeclarations.add(styleRule.getStyle());
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
    public final boolean affectedByPseudoNameInAncestor(
            HTMLElementImpl element, HTMLElementImpl ancestor,
            String elementName, String elementId, String[] classArray,
            String pseudoName) {
        String elementTL = elementName.toLowerCase();
        Collection elementRules = this.rulesByElement.get(elementTL);
        if (elementRules != null) {
            Iterator i = elementRules.iterator();
            while (i.hasNext()) {
                StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
                CSSStyleSheet styleSheet = styleRuleInfo.getStyleRule()
                        .getParentStyleSheet();
                if ((styleSheet != null) && styleSheet.getDisabled()) {
                    continue;
                }
                if (styleRuleInfo.affectedByPseudoNameInAncestor(element,
                        ancestor, pseudoName)) {
                    return true;
                }
            }
        }
        elementRules = this.rulesByElement.get("*");
        if (elementRules != null) {
            Iterator i = elementRules.iterator();
            while (i.hasNext()) {
                StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
                CSSStyleSheet styleSheet = styleRuleInfo.getStyleRule()
                        .getParentStyleSheet();
                if ((styleSheet != null) && styleSheet.getDisabled()) {
                    continue;
                }
                if (styleRuleInfo.affectedByPseudoNameInAncestor(element,
                        ancestor, pseudoName)) {
                    return true;
                }
            }
        }
        if (classArray != null) {
            for (int cidx = 0; cidx < classArray.length; cidx++) {
                String className = classArray[cidx];
                String classNameTL = className.toLowerCase();
                Map<?, ?> classMaps = this.classMapsByElement.get(elementTL);
                if (classMaps != null) {
                    Collection classRules = (Collection) classMaps
                            .get(classNameTL);
                    if (classRules != null) {
                        Iterator i = classRules.iterator();
                        while (i.hasNext()) {
                            StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i
                                    .next();
                            CSSStyleSheet styleSheet = styleRuleInfo.getStyleRule()
                                    .getParentStyleSheet();
                            if ((styleSheet != null)
                                    && styleSheet.getDisabled()) {
                                continue;
                            }
                            if (styleRuleInfo.affectedByPseudoNameInAncestor(
                                    element, ancestor, pseudoName)) {
                                return true;
                            }
                        }
                    }
                }
                classMaps = this.classMapsByElement.get("*");
                if (classMaps != null) {
                    Collection classRules = (Collection) classMaps
                            .get(classNameTL);
                    if (classRules != null) {
                        Iterator i = classRules.iterator();
                        while (i.hasNext()) {
                            StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i
                                    .next();
                            CSSStyleSheet styleSheet = styleRuleInfo.getStyleRule()
                                    .getParentStyleSheet();
                            if ((styleSheet != null)
                                    && styleSheet.getDisabled()) {
                                continue;
                            }
                            if (styleRuleInfo.affectedByPseudoNameInAncestor(
                                    element, ancestor, pseudoName)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        if (elementId != null) {
            Map<?, ?> idMaps = this.idMapsByElement.get(elementTL);
            if (idMaps != null) {
                String elementIdTL = elementId.toLowerCase();
                Collection idRules = (Collection) idMaps.get(elementIdTL);
                if (idRules != null) {
                    Iterator i = idRules.iterator();
                    while (i.hasNext()) {
                        StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
                        CSSStyleSheet styleSheet = styleRuleInfo.getStyleRule()
                                .getParentStyleSheet();
                        if ((styleSheet != null) && styleSheet.getDisabled()) {
                            continue;
                        }
                        if (styleRuleInfo.affectedByPseudoNameInAncestor(
                                element, ancestor, pseudoName)) {
                            return true;
                        }
                    }
                }
            }
            idMaps = this.idMapsByElement.get("*");
            if (idMaps != null) {
                String elementIdTL = elementId.toLowerCase();
                Collection idRules = (Collection) idMaps.get(elementIdTL);
                if (idRules != null) {
                    Iterator i = idRules.iterator();
                    while (i.hasNext()) {
                        StyleRuleInfo styleRuleInfo = (StyleRuleInfo) i.next();
                        CSSStyleSheet styleSheet = styleRuleInfo.getStyleRule()
                                .getParentStyleSheet();
                        if ((styleSheet != null) && styleSheet.getDisabled()) {
                            continue;
                        }
                        if (styleRuleInfo.affectedByPseudoNameInAncestor(
                                element, ancestor, pseudoName)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
