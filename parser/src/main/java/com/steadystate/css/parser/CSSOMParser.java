/*
 * Copyright (C) 1999-2016 David Schweinsberg.  All rights reserved.
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

package com.steadystate.css.parser;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.ErrorHandler;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.LexicalUnit;
import org.w3c.css.sac.Locator;
import org.w3c.css.sac.Parser;
import org.w3c.css.sac.SACMediaList;
import org.w3c.css.sac.SelectorList;
import org.w3c.css.sac.helpers.ParserFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.CSSValue;

import com.steadystate.css.dom.CSSCharsetRuleImpl;
import com.steadystate.css.dom.CSSFontFaceRuleImpl;
import com.steadystate.css.dom.CSSImportRuleImpl;
import com.steadystate.css.dom.CSSMediaRuleImpl;
import com.steadystate.css.dom.CSSOMObject;
import com.steadystate.css.dom.CSSPageRuleImpl;
import com.steadystate.css.dom.CSSRuleListImpl;
import com.steadystate.css.dom.CSSStyleDeclarationImpl;
import com.steadystate.css.dom.CSSStyleRuleImpl;
import com.steadystate.css.dom.CSSStyleSheetImpl;
import com.steadystate.css.dom.CSSUnknownRuleImpl;
import com.steadystate.css.dom.CSSValueImpl;
import com.steadystate.css.dom.MediaListImpl;
import com.steadystate.css.dom.Property;
import com.steadystate.css.sac.DocumentHandlerExt;
import com.steadystate.css.userdata.UserDataConstants;

/**
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 */
public class CSSOMParser {

    private static final String DEFAULT_PARSER = "com.steadystate.css.parser.SACParserCSS21";

    private static String LastFailed_;

    private Parser parser_;
    private CSSStyleSheetImpl parentStyleSheet_;

    /** Creates new CSSOMParser */
    public CSSOMParser() {
        this (null);
    }

    /**
     * Creates new CSSOMParser.
     *
     * @param parser the SAC Parser
     */
    public CSSOMParser(final Parser parser) {
        synchronized (DEFAULT_PARSER) {
            if (null != parser) {
                System.setProperty("org.w3c.css.sac.parser", parser.getClass().getCanonicalName());
                parser_ = parser;
                return;
            }

            // no parser provided, determine the correct one
            String currentParser = System.getProperty("org.w3c.css.sac.parser");
            try {
                // use the direct method if we already failed once before
                if (null != LastFailed_ && LastFailed_.equals(currentParser)) {
                    parser_ = new SACParserCSS21();
                }
                else {
                    if (null == currentParser) {
                        System.setProperty("org.w3c.css.sac.parser", DEFAULT_PARSER);
                        currentParser = DEFAULT_PARSER;
                    }
                    final ParserFactory factory = new ParserFactory();
                    parser_ = factory.makeParser();
                }
            }
            catch (final Exception e) {
                final Logger log = LogManager.getLogger("com.steadystate.css");
                log.warn(e.toString());
                log.warn("using the default 'SACParserCSS21' instead");
                log.error("CSSOMParser", "consturctor", e);
                LastFailed_ = currentParser;
                parser_ = new SACParserCSS21();
            }
        }
    }

    public void setErrorHandler(final ErrorHandler eh) {
        parser_.setErrorHandler(eh);
    }

    /**
     * Parses a SAC input source into a CSSOM style sheet.
     *
     * @param source the SAC input source
     * @param ownerNode the owner node (see the definition of
     *   <code>ownerNode</code> in org.w3c.dom.css.StyleSheet)
     * @param href the href (see the definition of <code>href</code> in
     *   org.w3c.dom.css.StyleSheet)
     * @return the CSSOM style sheet
     * @throws IOException if the underlying SAC parser throws an IOException
     */
    public CSSStyleSheet parseStyleSheet(final InputSource source,
            final Node ownerNode, final String href) throws IOException {
        final CSSOMHandler handler = new CSSOMHandler();
        handler.setOwnerNode(ownerNode);
        handler.setHref(href);
        parser_.setDocumentHandler(handler);
        parser_.parseStyleSheet(source);
        final Object o = handler.getRoot();
        if (o instanceof CSSStyleSheet) {
            return (CSSStyleSheet) o;
        }
        return null;
    }

    /**
     * Parses a SAC input source into a CSSOM style declaration.
     *
     * @param source the SAC input source
     * @return the CSSOM style declaration
     * @throws IOException if the underlying SAC parser throws an IOException
     */
    public CSSStyleDeclaration parseStyleDeclaration(final InputSource source) throws IOException {
        final CSSStyleDeclarationImpl sd = new CSSStyleDeclarationImpl(null);
        parseStyleDeclaration(sd, source);
        return sd;
    }

    public void parseStyleDeclaration(final CSSStyleDeclaration sd, final InputSource source) throws IOException {
        final Stack<Object> nodeStack = new Stack<Object>();
        nodeStack.push(sd);
        final CSSOMHandler handler = new CSSOMHandler(nodeStack);
        parser_.setDocumentHandler(handler);
        parser_.parseStyleDeclaration(source);
    }

    public CSSValue parsePropertyValue(final InputSource source) throws IOException {
        final CSSOMHandler handler = new CSSOMHandler();
        parser_.setDocumentHandler(handler);
        final LexicalUnit lu = parser_.parsePropertyValue(source);
        if (null == lu) {
            return null;
        }
        return new CSSValueImpl(lu);
    }

    public CSSRule parseRule(final InputSource source) throws IOException {
        final CSSOMHandler handler = new CSSOMHandler();
        parser_.setDocumentHandler(handler);
        parser_.parseRule(source);
        return (CSSRule) handler.getRoot();
    }

    public SelectorList parseSelectors(final InputSource source) throws IOException {
        final HandlerBase handler = new HandlerBase();
        parser_.setDocumentHandler(handler);
        return parser_.parseSelectors(source);
    }

    public SACMediaList parseMedia(final InputSource source) throws IOException {
        final HandlerBase handler = new HandlerBase();
        parser_.setDocumentHandler(handler);
        if (parser_ instanceof AbstractSACParser) {
            return ((AbstractSACParser) parser_).parseMedia(source);
        }
        return null;
    }

    public void setParentStyleSheet(final CSSStyleSheetImpl parentStyleSheet) {
        parentStyleSheet_ = parentStyleSheet;
    }

    protected CSSStyleSheetImpl getParentStyleSheet() {
        return parentStyleSheet_;
    }

    class CSSOMHandler implements DocumentHandlerExt {
        private Stack<Object> nodeStack_;
        private Object root_;
        private Node ownerNode_;
        private String href_;

        private Node getOwnerNode() {
            return ownerNode_;
        }

        private void setOwnerNode(final Node ownerNode) {
            ownerNode_ = ownerNode;
        }

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
            nodeStack_ = new Stack<Object>();
        }

        Object getRoot() {
            return root_;
        }

        public void startDocument(final InputSource source) throws CSSException {
            if (nodeStack_.empty()) {
                final CSSStyleSheetImpl ss = new CSSStyleSheetImpl();
                CSSOMParser.this.setParentStyleSheet(ss);
                ss.setOwnerNode(getOwnerNode());
                ss.setBaseUri(source.getURI());
                ss.setHref(getHref());
                ss.setMediaText(source.getMedia());
                ss.setTitle(source.getTitle());
                // Create the rule list
                final CSSRuleListImpl rules = new CSSRuleListImpl();
                ss.setCssRules(rules);
                nodeStack_.push(ss);
                nodeStack_.push(rules);
            }
            else {
                // Error
            }
        }

        public void endDocument(final InputSource source) throws CSSException {
            // Pop the rule list and style sheet nodes
            nodeStack_.pop();
            root_ = nodeStack_.pop();
        }

        public void comment(final String text) throws CSSException {
            // empty default impl
        }

        public void ignorableAtRule(final String atRule) throws CSSException {
            ignorableAtRule(atRule, null);
        }

        public void ignorableAtRule(final String atRule, final Locator locator) throws CSSException {
            // Create the unknown rule and add it to the rule list
            final CSSUnknownRuleImpl ir = new CSSUnknownRuleImpl(
                CSSOMParser.this.getParentStyleSheet(),
                getParentRule(),
                atRule);
            addLocator(locator, ir);
            if (!nodeStack_.empty()) {
                ((CSSRuleListImpl) nodeStack_.peek()).add(ir);
            }
            else {
                root_ = ir;
            }
        }

        public void namespaceDeclaration(final String prefix, final String uri) throws CSSException {
            // empty default impl
        }

        public void charset(final String characterEncoding, final Locator locator)
            throws CSSException {
            final CSSCharsetRuleImpl cr = new CSSCharsetRuleImpl(
                    CSSOMParser.this.getParentStyleSheet(),
                    getParentRule(),
                    characterEncoding);
            addLocator(locator, cr);
            if (!nodeStack_.empty()) {
                ((CSSRuleListImpl) nodeStack_.peek()).add(cr);
            }
            else {
                root_ = cr;
            }
        }

        public void importStyle(
                final String uri,
                final SACMediaList media,
                final String defaultNamespaceURI) throws CSSException {
            importStyle(uri, media, defaultNamespaceURI, null);
        }

        public void importStyle(final String uri, final SACMediaList media,
            final String defaultNamespaceURI, final Locator locator) throws CSSException {
            // Create the import rule and add it to the rule list
            final CSSImportRuleImpl ir = new CSSImportRuleImpl(
                CSSOMParser.this.getParentStyleSheet(),
                getParentRule(),
                uri,
                new MediaListImpl(media));
            addLocator(locator, ir);
            if (!nodeStack_.empty()) {
                ((CSSRuleListImpl) nodeStack_.peek()).add(ir);
            }
            else {
                root_ = ir;
            }
        }

        public void startMedia(final SACMediaList media) throws CSSException {
            startMedia(media, null);
        }

        public void startMedia(final SACMediaList media, final Locator locator) throws CSSException {
            final MediaListImpl ml = new MediaListImpl(media);
            // Create the media rule and add it to the rule list
            final CSSMediaRuleImpl mr = new CSSMediaRuleImpl(
                CSSOMParser.this.getParentStyleSheet(),
                getParentRule(),
                ml);
            addLocator(locator, mr);
            if (!nodeStack_.empty()) {
                ((CSSRuleListImpl) nodeStack_.peek()).add(mr);
            }

            // Create the rule list
            final CSSRuleListImpl rules = new CSSRuleListImpl();
            mr.setRuleList(rules);
            nodeStack_.push(mr);
            nodeStack_.push(rules);
        }

        public void endMedia(final SACMediaList media) throws CSSException {
            // Pop the rule list and media rule nodes
            nodeStack_.pop();
            root_ = nodeStack_.pop();
        }

        public void startPage(final String name, final String pseudoPage) throws CSSException {
            startPage(name, pseudoPage, null);
        }

        public void startPage(final String name, final String pseudoPage, final Locator locator)
            throws CSSException {
            // Create the page rule and add it to the rule list
            final CSSPageRuleImpl pr = new CSSPageRuleImpl(
                CSSOMParser.this.getParentStyleSheet(),
                getParentRule(), pseudoPage);
            addLocator(locator, pr);
            if (!nodeStack_.empty()) {
                ((CSSRuleListImpl) nodeStack_.peek()).add(pr);
            }

            // Create the style declaration
            final CSSStyleDeclarationImpl decl = new CSSStyleDeclarationImpl(pr);
            pr.setStyle(decl);
            nodeStack_.push(pr);
            nodeStack_.push(decl);
        }

        public void endPage(final String name, final String pseudoPage) throws CSSException {
            // Pop both the style declaration and the page rule nodes
            nodeStack_.pop();
            root_ = nodeStack_.pop();
        }

        public void startFontFace() throws CSSException {
            startFontFace(null);
        }

        public void startFontFace(final Locator locator) throws CSSException {
            // Create the font face rule and add it to the rule list
            final CSSFontFaceRuleImpl ffr = new CSSFontFaceRuleImpl(
                CSSOMParser.this.getParentStyleSheet(),
                getParentRule());
            addLocator(locator, ffr);
            if (!nodeStack_.empty()) {
                ((CSSRuleListImpl) nodeStack_.peek()).add(ffr);
            }

            // Create the style declaration
            final CSSStyleDeclarationImpl decl = new CSSStyleDeclarationImpl(ffr);
            ffr.setStyle(decl);
            nodeStack_.push(ffr);
            nodeStack_.push(decl);
        }

        public void endFontFace() throws CSSException {
            // Pop both the style declaration and the font face rule nodes
            nodeStack_.pop();
            root_ = nodeStack_.pop();
        }

        public void startSelector(final SelectorList selectors) throws CSSException {
            startSelector(selectors, null);
        }

        public void startSelector(final SelectorList selectors, final Locator locator) throws CSSException {
            // Create the style rule and add it to the rule list
            final CSSStyleRuleImpl sr = new CSSStyleRuleImpl(
                CSSOMParser.this.getParentStyleSheet(),
                getParentRule(), selectors);
            addLocator(locator, sr);
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

        public void endSelector(final SelectorList selectors) throws CSSException {
            // Pop both the style declaration and the style rule nodes
            nodeStack_.pop();
            root_ = nodeStack_.pop();
        }

        public void property(final String name, final LexicalUnit value, final boolean important) throws CSSException {
            property(name, value, important, null);
        }

        public void property(final String name, final LexicalUnit value, final boolean important,
                                final Locator locator) {
            final CSSStyleDeclarationImpl decl =
                (CSSStyleDeclarationImpl) nodeStack_.peek();
            try {
                final Property property = new Property(name, new CSSValueImpl(value), important);
                addLocator(locator, property);
                decl.addProperty(property);
            }
            catch (final DOMException e) {
                if (parser_ instanceof AbstractSACParser) {
                    final AbstractSACParser parser = (AbstractSACParser) parser_;
                    parser.getErrorHandler().error(parser.toCSSParseException(e));

                }
                // call ErrorHandler?
            }
        }

        private CSSRule getParentRule() {
            if (!nodeStack_.empty() && nodeStack_.size() > 1) {
                final Object node = nodeStack_.get(nodeStack_.size() - 2);
                if (node instanceof CSSRule) {
                    return (CSSRule) node;
                }
            }
            return null;
        }

        private void addLocator(Locator locator, final CSSOMObject cssomObject) {
            if (locator == null) {
                final Parser parser = CSSOMParser.this.parser_;
                try {
                    final Method getLocatorMethod = parser.getClass().getMethod(
                        "getLocator", (Class[]) null);
                    locator = (Locator) getLocatorMethod.invoke(
                        parser, (Object[]) null);
                }
                catch (final SecurityException e) {
                    // TODO
                }
                catch (final NoSuchMethodException e) {
                    // TODO
                }
                catch (final IllegalArgumentException e) {
                    // TODO
                }
                catch (final IllegalAccessException e) {
                    // TODO
                }
                catch (final InvocationTargetException e) {
                    // TODO
                }
            }
            if (locator != null) {
                cssomObject.setUserData(UserDataConstants.KEY_LOCATOR, locator);
            }
        }

    }
}
