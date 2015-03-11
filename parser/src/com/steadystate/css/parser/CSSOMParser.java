/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2015 David Schweinsberg.  All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * To contact the authors of the library:
 *
 * http://cssparser.sourceforge.net/
 * mailto:davidsch@users.sourceforge.net
 *
 */

package com.steadystate.css.parser;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;
import java.util.logging.Logger;

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
 * The Class CSSOMParser.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 */
public class CSSOMParser {

    /** The Constant DEFAULT_PARSER. */
    private static final String DEFAULT_PARSER = "com.steadystate.css.parser.SACParserCSS21";

    /** The Last failed_. */
    private static String LastFailed_;

    /** The parser_. */
    private Parser parser_;
    
    /** The parent style sheet_. */
    private CSSStyleSheetImpl parentStyleSheet_;

    /**
     *  Creates new CSSOMParser.
     */
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
                final Logger log = Logger.getLogger("com.steadystate.css");
                log.warning(e.toString());
                log.warning("using the default 'SACParserCSS21' instead");
                log.throwing("CSSOMParser", "consturctor", e);
                LastFailed_ = currentParser;
                parser_ = new SACParserCSS21();
            }
        }
    }

    /**
     * Sets the error handler.
     *
     * @param eh the new error handler
     */
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

    /**
     * Parses the style declaration.
     *
     * @param sd the sd
     * @param source the source
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void parseStyleDeclaration(final CSSStyleDeclaration sd, final InputSource source) throws IOException {
        final Stack<Object> nodeStack = new Stack<Object>();
        nodeStack.push(sd);
        final CSSOMHandler handler = new CSSOMHandler(nodeStack);
        parser_.setDocumentHandler(handler);
        parser_.parseStyleDeclaration(source);
    }

    /**
     * Parses the property value.
     *
     * @param source the source
     * @return the CSS value
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public CSSValue parsePropertyValue(final InputSource source) throws IOException {
        final CSSOMHandler handler = new CSSOMHandler();
        parser_.setDocumentHandler(handler);
        final LexicalUnit lu = parser_.parsePropertyValue(source);
        if (null == lu) {
            return null;
        }
        return new CSSValueImpl(lu);
    }

    /**
     * Parses the rule.
     *
     * @param source the source
     * @return the CSS rule
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public CSSRule parseRule(final InputSource source) throws IOException {
        final CSSOMHandler handler = new CSSOMHandler();
        parser_.setDocumentHandler(handler);
        parser_.parseRule(source);
        return (CSSRule) handler.getRoot();
    }

    /**
     * Parses the selectors.
     *
     * @param source the source
     * @return the selector list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public SelectorList parseSelectors(final InputSource source) throws IOException {
        final HandlerBase handler = new HandlerBase();
        parser_.setDocumentHandler(handler);
        return parser_.parseSelectors(source);
    }

    /**
     * Parses the media.
     *
     * @param source the source
     * @return the SAC media list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public SACMediaList parseMedia(final InputSource source) throws IOException {
        final HandlerBase handler = new HandlerBase();
        parser_.setDocumentHandler(handler);
        if (parser_ instanceof AbstractSACParser) {
            return ((AbstractSACParser) parser_).parseMedia(source);
        }
        return null;
    }

    /**
     * Sets the parent style sheet.
     *
     * @param parentStyleSheet the new parent style sheet
     */
    public void setParentStyleSheet(final CSSStyleSheetImpl parentStyleSheet) {
        parentStyleSheet_ = parentStyleSheet;
    }

    /**
     * Gets the parent style sheet.
     *
     * @return the parent style sheet
     */
    protected CSSStyleSheetImpl getParentStyleSheet() {
        return parentStyleSheet_;
    }

    /**
     * The Class CSSOMHandler.
     */
    class CSSOMHandler implements DocumentHandlerExt {
        
        /** The node stack_. */
        private Stack<Object> nodeStack_;
        
        /** The root_. */
        private Object root_;
        
        /** The owner node_. */
        private Node ownerNode_;
        
        /** The href_. */
        private String href_;

        /**
         * Gets the owner node.
         *
         * @return the owner node
         */
        private Node getOwnerNode() {
            return ownerNode_;
        }

        /**
         * Sets the owner node.
         *
         * @param ownerNode the new owner node
         */
        private void setOwnerNode(final Node ownerNode) {
            ownerNode_ = ownerNode;
        }

        /**
         * Gets the href.
         *
         * @return the href
         */
        private String getHref() {
            return href_;
        }

        /**
         * Sets the href.
         *
         * @param href the new href
         */
        private void setHref(final String href) {
            href_ = href;
        }

        /**
         * Instantiates a new CSSOM handler.
         *
         * @param nodeStack the node stack
         */
        public CSSOMHandler(final Stack<Object> nodeStack) {
            nodeStack_ = nodeStack;
        }

        /**
         * Instantiates a new CSSOM handler.
         */
        public CSSOMHandler() {
            nodeStack_ = new Stack<Object>();
        }

        /**
         * Gets the root.
         *
         * @return the root
         */
        public Object getRoot() {
            return root_;
        }

        /* (non-Javadoc)
         * @see org.w3c.css.sac.DocumentHandler#startDocument(org.w3c.css.sac.InputSource)
         */
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

        /* (non-Javadoc)
         * @see org.w3c.css.sac.DocumentHandler#endDocument(org.w3c.css.sac.InputSource)
         */
        public void endDocument(final InputSource source) throws CSSException {
            // Pop the rule list and style sheet nodes
            nodeStack_.pop();
            root_ = nodeStack_.pop();
        }

        /* (non-Javadoc)
         * @see org.w3c.css.sac.DocumentHandler#comment(java.lang.String)
         */
        public void comment(final String text) throws CSSException {
            // empty default impl
        }

        /* (non-Javadoc)
         * @see org.w3c.css.sac.DocumentHandler#ignorableAtRule(java.lang.String)
         */
        public void ignorableAtRule(final String atRule) throws CSSException {
            ignorableAtRule(atRule, null);
        }

        /* (non-Javadoc)
         * @see com.steadystate.css.sac.DocumentHandlerExt#ignorableAtRule(java.lang.String, org.w3c.css.sac.Locator)
         */
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

        /* (non-Javadoc)
         * @see org.w3c.css.sac.DocumentHandler#namespaceDeclaration(java.lang.String, java.lang.String)
         */
        public void namespaceDeclaration(final String prefix, final String uri) throws CSSException {
            // empty default impl
        }

        /* (non-Javadoc)
         * @see com.steadystate.css.sac.DocumentHandlerExt#charset(java.lang.String, org.w3c.css.sac.Locator)
         */
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

        /* (non-Javadoc)
         * @see org.w3c.css.sac.DocumentHandler#importStyle(java.lang.String, org.w3c.css.sac.SACMediaList, java.lang.String)
         */
        public void importStyle(
                final String uri,
                final SACMediaList media,
                final String defaultNamespaceURI) throws CSSException {
            importStyle(uri, media, defaultNamespaceURI, null);
        }

        /* (non-Javadoc)
         * @see com.steadystate.css.sac.DocumentHandlerExt#importStyle(java.lang.String, org.w3c.css.sac.SACMediaList, java.lang.String, org.w3c.css.sac.Locator)
         */
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

        /* (non-Javadoc)
         * @see org.w3c.css.sac.DocumentHandler#startMedia(org.w3c.css.sac.SACMediaList)
         */
        public void startMedia(final SACMediaList media) throws CSSException {
            startMedia(media, null);
        }

        /* (non-Javadoc)
         * @see com.steadystate.css.sac.DocumentHandlerExt#startMedia(org.w3c.css.sac.SACMediaList, org.w3c.css.sac.Locator)
         */
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

        /* (non-Javadoc)
         * @see org.w3c.css.sac.DocumentHandler#endMedia(org.w3c.css.sac.SACMediaList)
         */
        public void endMedia(final SACMediaList media) throws CSSException {
            // Pop the rule list and media rule nodes
            nodeStack_.pop();
            root_ = nodeStack_.pop();
        }

        /* (non-Javadoc)
         * @see org.w3c.css.sac.DocumentHandler#startPage(java.lang.String, java.lang.String)
         */
        public void startPage(final String name, final String pseudoPage) throws CSSException {
            startPage(name, pseudoPage, null);
        }

        /* (non-Javadoc)
         * @see com.steadystate.css.sac.DocumentHandlerExt#startPage(java.lang.String, java.lang.String, org.w3c.css.sac.Locator)
         */
        public void startPage(final String name, final String pseudoPage, final Locator locator)
            throws CSSException {
            // Create the page rule and add it to the rule list
            final CSSPageRuleImpl pr = new CSSPageRuleImpl(
                CSSOMParser.this.getParentStyleSheet(),
                getParentRule(), name, pseudoPage);
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

        /* (non-Javadoc)
         * @see org.w3c.css.sac.DocumentHandler#endPage(java.lang.String, java.lang.String)
         */
        public void endPage(final String name, final String pseudoPage) throws CSSException {
            // Pop both the style declaration and the page rule nodes
            nodeStack_.pop();
            root_ = nodeStack_.pop();
        }

        /* (non-Javadoc)
         * @see org.w3c.css.sac.DocumentHandler#startFontFace()
         */
        public void startFontFace() throws CSSException {
            startFontFace(null);
        }

        /* (non-Javadoc)
         * @see com.steadystate.css.sac.DocumentHandlerExt#startFontFace(org.w3c.css.sac.Locator)
         */
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

        /* (non-Javadoc)
         * @see org.w3c.css.sac.DocumentHandler#endFontFace()
         */
        public void endFontFace() throws CSSException {
            // Pop both the style declaration and the font face rule nodes
            nodeStack_.pop();
            root_ = nodeStack_.pop();
        }

        /* (non-Javadoc)
         * @see org.w3c.css.sac.DocumentHandler#startSelector(org.w3c.css.sac.SelectorList)
         */
        public void startSelector(final SelectorList selectors) throws CSSException {
            startSelector(selectors, null);
        }

        /* (non-Javadoc)
         * @see com.steadystate.css.sac.DocumentHandlerExt#startSelector(org.w3c.css.sac.SelectorList, org.w3c.css.sac.Locator)
         */
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

        /* (non-Javadoc)
         * @see org.w3c.css.sac.DocumentHandler#endSelector(org.w3c.css.sac.SelectorList)
         */
        public void endSelector(final SelectorList selectors) throws CSSException {
            // Pop both the style declaration and the style rule nodes
            nodeStack_.pop();
            root_ = nodeStack_.pop();
        }

        /* (non-Javadoc)
         * @see org.w3c.css.sac.DocumentHandler#property(java.lang.String, org.w3c.css.sac.LexicalUnit, boolean)
         */
        public void property(final String name, final LexicalUnit value, final boolean important) throws CSSException {
            property(name, value, important, null);
        }

        /* (non-Javadoc)
         * @see com.steadystate.css.sac.DocumentHandlerExt#property(java.lang.String, org.w3c.css.sac.LexicalUnit, boolean, org.w3c.css.sac.Locator)
         */
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
                // call ErrorHandler?
            }
        }

        /**
         * Gets the parent rule.
         *
         * @return the parent rule
         */
        private CSSRule getParentRule() {
            if (!nodeStack_.empty() && nodeStack_.size() > 1) {
                final Object node = nodeStack_.get(nodeStack_.size() - 2);
                if (node instanceof CSSRule) {
                    return (CSSRule) node;
                }
            }
            return null;
        }

        /**
         * Adds the locator.
         *
         * @param locator the locator
         * @param cssomObject the cssom object
         */
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
