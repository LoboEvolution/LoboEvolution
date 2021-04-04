/*
 * Copyright (c) 2019-2020 Ronald Brill.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.css.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.Stack;

import org.w3c.dom.DOMException;

import com.gargoylesoftware.css.dom.AbstractCSSRuleImpl;
import com.gargoylesoftware.css.dom.CSSCharsetRuleImpl;
import com.gargoylesoftware.css.dom.CSSFontFaceRuleImpl;
import com.gargoylesoftware.css.dom.CSSImportRuleImpl;
import com.gargoylesoftware.css.dom.CSSMediaRuleImpl;
import com.gargoylesoftware.css.dom.CSSPageRuleImpl;
import com.gargoylesoftware.css.dom.CSSRuleListImpl;
import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;
import com.gargoylesoftware.css.dom.CSSStyleRuleImpl;
import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;
import com.gargoylesoftware.css.dom.CSSUnknownRuleImpl;
import com.gargoylesoftware.css.dom.CSSValueImpl;
import com.gargoylesoftware.css.dom.MediaListImpl;
import com.gargoylesoftware.css.dom.Property;
import com.gargoylesoftware.css.parser.javacc.CSS3Parser;
import com.gargoylesoftware.css.parser.media.MediaQueryList;
import com.gargoylesoftware.css.parser.selector.SelectorList;

/**
 * <p>CSSOMParser class.</p>
 *
 * Author Ronald Brill
 *
 */
public class CSSOMParser {

    private CSSParser parser_;
    private CSSStyleSheetImpl parentStyleSheet_;

    /**
     * Creates new CSSOMParser.
     *
     * @param parser the parser
     */
    public CSSOMParser(final CSSParser parser) {
        parser_ = parser;
    }

    /**
     * Creates new CSSOMParser.
     */
    public CSSOMParser() {
        parser_ = new CSS3Parser();
    }

    /**
     * <p>setErrorHandler.</p>
     *
     * @param eh the error handler to be used
     */
    public void setErrorHandler(final CSSErrorHandler eh) {
        parser_.setErrorHandler(eh);
    }

    /**
     * Parses a SAC input source into a CSSOM style sheet.
     *
     * @param source the SAC input source
     * @param href the href
     * @return the CSSOM style sheet
     * @throws java.io.IOException if the underlying SAC parser throws an java.io.IOException
     */
    public CSSStyleSheetImpl parseStyleSheet(final InputSource source, final String href) throws IOException {
        final CSSOMHandler handler = new CSSOMHandler();
        handler.setHref(href);
        parser_.setDocumentHandler(handler);
        parser_.parseStyleSheet(source);
        final Object o = handler.getRoot();
        if (o instanceof CSSStyleSheetImpl) {
            return (CSSStyleSheetImpl) o;
        }
        return null;
    }

    /**
     * Parses a input string into a CSSOM style declaration.
     *
     * @param styleDecl the input string
     * @return the CSSOM style declaration
     * @throws java.io.IOException if the underlying SAC parser throws an java.io.IOException
     */
    public CSSStyleDeclarationImpl parseStyleDeclaration(final String styleDecl) throws IOException {
        final CSSStyleDeclarationImpl sd = new CSSStyleDeclarationImpl(null);
        parseStyleDeclaration(sd, styleDecl);
        return sd;
    }

    /**
     * Parses a input string into a CSSOM style declaration.
     *
     * @param styleDecl the input string
     * @param sd the CSSOM style declaration
     * @throws java.io.IOException if the underlying SAC parser throws an java.io.IOException
     */
    public void parseStyleDeclaration(final CSSStyleDeclarationImpl sd, final String styleDecl) throws IOException {
        try (InputSource source = new InputSource(new StringReader(styleDecl))) {
            final Stack<Object> nodeStack = new Stack<>();
            nodeStack.push(sd);
            final CSSOMHandler handler = new CSSOMHandler(nodeStack);
            parser_.setDocumentHandler(handler);
            parser_.parseStyleDeclaration(source);
        }
    }

    /**
     * Parses a input string into a CSSValue.
     *
     * @param propertyValue the input string
     * @return the css value
     * @throws java.io.IOException if the underlying SAC parser throws an java.io.IOException
     */
    public CSSValueImpl parsePropertyValue(final String propertyValue) throws IOException {
        try (InputSource source = new InputSource(new StringReader(propertyValue))) {
            final CSSOMHandler handler = new CSSOMHandler();
            parser_.setDocumentHandler(handler);
            final LexicalUnit lu = parser_.parsePropertyValue(source);
            if (null == lu) {
                return null;
            }
            return new CSSValueImpl(lu);
        }
    }

    /**
     * Parses a string into a CSSRule.
     *
     * @param rule the input string
     * @return the css rule
     * @throws java.io.IOException if the underlying SAC parser throws an java.io.IOException
     */
    public AbstractCSSRuleImpl parseRule(final String rule) throws IOException {
        try (InputSource source = new InputSource(new StringReader(rule))) {
            final CSSOMHandler handler = new CSSOMHandler();
            parser_.setDocumentHandler(handler);
            parser_.parseRule(source);
            return (AbstractCSSRuleImpl) handler.getRoot();
        }
    }

    /**
     * Parses a string into a CSSSelectorList.
     *
     * @param selectors the input string
     * @return the css selector list
     * @throws java.io.IOException if the underlying SAC parser throws an java.io.IOException
     */
    public SelectorList parseSelectors(final String selectors) throws IOException {
        try (InputSource source = new InputSource(new StringReader(selectors))) {
            final HandlerBase handler = new HandlerBase();
            parser_.setDocumentHandler(handler);
            return parser_.parseSelectors(source);
        }
    }

    /**
     * Parses a string into a MediaQueryList.
     *
     * @param media the input string
     * @return the css media query list
     * @throws java.io.IOException if the underlying SAC parser throws an java.io.IOException
     */
    public MediaQueryList parseMedia(final String media) throws IOException {
        try (InputSource source = new InputSource(new StringReader(media))) {
            final HandlerBase handler = new HandlerBase();
            parser_.setDocumentHandler(handler);
            if (parser_ instanceof AbstractCSSParser) {
                return ((AbstractCSSParser) parser_).parseMedia(source);
            }
            return null;
        }
    }

    /**
     * <p>setParentStyleSheet.</p>
     *
     * @param parentStyleSheet the new parent stylesheet
     */
    public void setParentStyleSheet(final CSSStyleSheetImpl parentStyleSheet) {
        parentStyleSheet_ = parentStyleSheet;
    }

    /**
     * <p>getParentStyleSheet.</p>
     *
     * @return the parent style sheet
     */
    protected CSSStyleSheetImpl getParentStyleSheet() {
        return parentStyleSheet_;
    }

    class CSSOMHandler implements DocumentHandler {
        private Stack<Object> nodeStack_;
        private Object root_;
        private String href_;

        private String getHref() {
            return href_;
        }

        private void setHref(final String href) {
            href_ = href;
        }

        CSSOMHandler(final Stack<Object> nodeStack) {
            nodeStack_ = nodeStack;
        }

        CSSOMHandler() {
            nodeStack_ = new Stack<>();
        }

        Object getRoot() {
            return root_;
        }

        @Override
        public void startDocument(final InputSource source) throws CSSException {
            if (nodeStack_.empty()) {
                final CSSStyleSheetImpl ss = new CSSStyleSheetImpl();
                CSSOMParser.this.setParentStyleSheet(ss);
                ss.setHref(getHref());
                ss.setMediaText(source.getMedia());
                ss.setTitle(source.getTitle());
                // Create the rule list
                final CSSRuleListImpl rules = new CSSRuleListImpl();
                ss.setCssRules(rules);
                nodeStack_.push(ss);
                nodeStack_.push(rules);
            }
        }

        @Override
        public void endDocument(final InputSource source) throws CSSException {
            // Pop the rule list and style sheet nodes
            nodeStack_.pop();
            root_ = nodeStack_.pop();
        }

        @Override
        public void ignorableAtRule(final String atRule, final Locator locator) throws CSSException {
            // Create the unknown rule and add it to the rule list
            final CSSUnknownRuleImpl ir = new CSSUnknownRuleImpl(
                CSSOMParser.this.getParentStyleSheet(),
                getParentRule(),
                atRule);
            ir.setLocator(locator);
            if (!nodeStack_.empty()) {
                ((CSSRuleListImpl) nodeStack_.peek()).add(ir);
            }
            else {
                root_ = ir;
            }
        }

        @Override
        public void charset(final String characterEncoding, final Locator locator)
            throws CSSException {
            final CSSCharsetRuleImpl cr = new CSSCharsetRuleImpl(
                    CSSOMParser.this.getParentStyleSheet(),
                    getParentRule(),
                    characterEncoding);
            cr.setLocator(locator);
            if (!nodeStack_.empty()) {
                ((CSSRuleListImpl) nodeStack_.peek()).add(cr);
            }
            else {
                root_ = cr;
            }
        }

        @Override
        public void importStyle(final String uri, final MediaQueryList media,
            final String defaultNamespaceURI, final Locator locator) throws CSSException {
            // Create the import rule and add it to the rule list
            final CSSImportRuleImpl ir = new CSSImportRuleImpl(
                CSSOMParser.this.getParentStyleSheet(),
                getParentRule(),
                uri,
                new MediaListImpl(media));
            ir.setLocator(locator);
            if (!nodeStack_.empty()) {
                ((CSSRuleListImpl) nodeStack_.peek()).add(ir);
            }
            else {
                root_ = ir;
            }
        }

        @Override
        public void startMedia(final MediaQueryList media, final Locator locator) throws CSSException {
            final MediaListImpl ml = new MediaListImpl(media);
            // Create the media rule and add it to the rule list
            final CSSMediaRuleImpl mr = new CSSMediaRuleImpl(
                CSSOMParser.this.getParentStyleSheet(),
                getParentRule(),
                ml);
            mr.setLocator(locator);
            if (!nodeStack_.empty()) {
                ((CSSRuleListImpl) nodeStack_.peek()).add(mr);
            }

            // Create the rule list
            final CSSRuleListImpl rules = new CSSRuleListImpl();
            mr.setRuleList(rules);
            nodeStack_.push(mr);
            nodeStack_.push(rules);
        }

        @Override
        public void endMedia(final MediaQueryList media) throws CSSException {
            // Pop the rule list and media rule nodes
            nodeStack_.pop();
            root_ = nodeStack_.pop();
        }

        @Override
        public void startPage(final String name, final String pseudoPage, final Locator locator)
            throws CSSException {
            // Create the page rule and add it to the rule list
            final CSSPageRuleImpl pr = new CSSPageRuleImpl(
                CSSOMParser.this.getParentStyleSheet(),
                getParentRule(), pseudoPage);
            pr.setLocator(locator);
            if (!nodeStack_.empty()) {
                ((CSSRuleListImpl) nodeStack_.peek()).add(pr);
            }

            // Create the style declaration
            final CSSStyleDeclarationImpl decl = new CSSStyleDeclarationImpl(pr);
            pr.setStyle(decl);
            nodeStack_.push(pr);
            nodeStack_.push(decl);
        }

        @Override
        public void endPage(final String name, final String pseudoPage) throws CSSException {
            // Pop both the style declaration and the page rule nodes
            nodeStack_.pop();
            root_ = nodeStack_.pop();
        }

        @Override
        public void startFontFace(final Locator locator) throws CSSException {
            // Create the font face rule and add it to the rule list
            final CSSFontFaceRuleImpl ffr = new CSSFontFaceRuleImpl(
                CSSOMParser.this.getParentStyleSheet(),
                getParentRule());
            ffr.setLocator(locator);
            if (!nodeStack_.empty()) {
                ((CSSRuleListImpl) nodeStack_.peek()).add(ffr);
            }

            // Create the style declaration
            final CSSStyleDeclarationImpl decl = new CSSStyleDeclarationImpl(ffr);
            ffr.setStyle(decl);
            nodeStack_.push(ffr);
            nodeStack_.push(decl);
        }

        @Override
        public void endFontFace() throws CSSException {
            // Pop both the style declaration and the font face rule nodes
            nodeStack_.pop();
            root_ = nodeStack_.pop();
        }

        @Override
        public void startSelector(final SelectorList selectors, final Locator locator) throws CSSException {
            // Create the style rule and add it to the rule list
            final CSSStyleRuleImpl sr = new CSSStyleRuleImpl(
                CSSOMParser.this.getParentStyleSheet(),
                getParentRule(), selectors);
            sr.setLocator(locator);
            if (!nodeStack_.empty()) {
                final Object o = nodeStack_.peek();
                ((CSSRuleListImpl) o).add(sr);
            }

            // Create the style declaration
            final CSSStyleDeclarationImpl decl = new CSSStyleDeclarationImpl(sr);
            sr.setStyle(decl);
            nodeStack_.push(sr);
            nodeStack_.push(decl);
        }

        @Override
        public void endSelector(final SelectorList selectors) throws CSSException {
            // Pop both the style declaration and the style rule nodes
            nodeStack_.pop();
            root_ = nodeStack_.pop();
        }

        @Override
        public void property(final String name, final LexicalUnit value, final boolean important,
                                final Locator locator) {
            final CSSStyleDeclarationImpl decl = (CSSStyleDeclarationImpl) nodeStack_.peek();
            try {
                final Property property = new Property(name, new CSSValueImpl(value), important);
                property.setLocator(locator);
                decl.addProperty(property);
            }
            catch (final DOMException e) {
                if (parser_ instanceof AbstractCSSParser) {
                    final AbstractCSSParser parser = (AbstractCSSParser) parser_;
                    parser.getErrorHandler().error(parser.toCSSParseException(e));

                }
                // call ErrorHandler?
            }
        }

        private AbstractCSSRuleImpl getParentRule() {
            if (!nodeStack_.empty() && nodeStack_.size() > 1) {
                final Object node = nodeStack_.get(nodeStack_.size() - 2);
                if (node instanceof AbstractCSSRuleImpl) {
                    return (AbstractCSSRuleImpl) node;
                }
            }
            return null;
        }
    }
}
