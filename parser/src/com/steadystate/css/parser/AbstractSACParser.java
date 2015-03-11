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
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ConditionFactory;
import org.w3c.css.sac.DocumentHandler;
import org.w3c.css.sac.ErrorHandler;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.LexicalUnit;
import org.w3c.css.sac.Locator;
import org.w3c.css.sac.Parser;
import org.w3c.css.sac.SACMediaList;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorFactory;
import org.w3c.css.sac.SelectorList;

import com.steadystate.css.parser.selectors.ConditionFactoryImpl;
import com.steadystate.css.parser.selectors.SelectorFactoryImpl;
import com.steadystate.css.sac.DocumentHandlerExt;


/**
 * Base implementation of {@link Parser}.
 *
 * @author koch
 * @author RBRi
 */
abstract class AbstractSACParser implements Parser {
    
    /** The document handler_. */
    private DocumentHandler documentHandler_;
    
    /** The error handler_. */
    private ErrorHandler errorHandler_;
    
    /** The source_. */
    private InputSource source_;
    
    /** The locale_. */
    private Locale locale_;
    
    /** The selector factory_. */
    private SelectorFactory selectorFactory_;
    
    /** The condition factory_. */
    private ConditionFactory conditionFactory_;
    
    /** The sac parser messages_. */
    private ResourceBundle sacParserMessages_;

    /** The Constant NUM_CHARS. */
    private static final String NUM_CHARS = "0123456789.";

    /**
     * Gets the token.
     *
     * @return the token
     */
    protected abstract Token getToken();

    /**
     * Gets the document handler.
     *
     * @return the document handler
     */
    protected DocumentHandler getDocumentHandler() {
        if (documentHandler_ == null) {
            setDocumentHandler(new HandlerBase());
        }
        return documentHandler_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Parser#setDocumentHandler(org.w3c.css.sac.DocumentHandler)
     */
    public void setDocumentHandler(final DocumentHandler handler) {
        documentHandler_ = handler;
    }

    /**
     * Gets the error handler.
     *
     * @return the error handler
     */
    protected ErrorHandler getErrorHandler() {
        if (errorHandler_ == null) {
            setErrorHandler(new HandlerBase());
        }
        return errorHandler_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Parser#setErrorHandler(org.w3c.css.sac.ErrorHandler)
     */
    public void setErrorHandler(final ErrorHandler eh) {
        errorHandler_ = eh;
    }

    /**
     * Gets the input source.
     *
     * @return the input source
     */
    protected InputSource getInputSource() {
        return source_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Parser#setLocale(java.util.Locale)
     */
    public void setLocale(final Locale locale) {
        if (locale_ != locale) {
            sacParserMessages_ = null;
        }
        locale_ = locale;
    }

    /**
     * Gets the locale.
     *
     * @return the locale
     */
    protected Locale getLocale() {
        if (locale_ == null) {
            setLocale(Locale.getDefault());
        }
        return locale_;
    }

    /**
     * Gets the selector factory.
     *
     * @return the selector factory
     */
    protected SelectorFactory getSelectorFactory() {
        if (selectorFactory_ == null) {
            selectorFactory_ = new SelectorFactoryImpl();
        }
        return selectorFactory_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Parser#setSelectorFactory(org.w3c.css.sac.SelectorFactory)
     */
    public void setSelectorFactory(final SelectorFactory selectorFactory) {
        selectorFactory_ = selectorFactory;
    }

    /**
     * Gets the condition factory.
     *
     * @return the condition factory
     */
    protected ConditionFactory getConditionFactory() {
        if (conditionFactory_ == null) {
            conditionFactory_ = new ConditionFactoryImpl();
        }
        return conditionFactory_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Parser#setConditionFactory(org.w3c.css.sac.ConditionFactory)
     */
    public void setConditionFactory(final ConditionFactory conditionFactory) {
        conditionFactory_ = conditionFactory;
    }

    /**
     * Gets the SAC parser messages.
     *
     * @return the SAC parser messages
     */
    protected ResourceBundle getSACParserMessages() {
        if (sacParserMessages_ == null) {
            try {
                sacParserMessages_ = ResourceBundle.getBundle(
                    "com.steadystate.css.parser.SACParserMessages",
                    getLocale());
            }
            catch (final MissingResourceException e) {
                e.printStackTrace();
            }
        }
        return sacParserMessages_;
    }

    /**
     * Gets the locator.
     *
     * @return the locator
     */
    public Locator getLocator() {
        return createLocator(getToken());
    }

    /**
     * Creates the locator.
     *
     * @param t the t
     * @return the locator
     */
    protected Locator createLocator(final Token t) {
        return new LocatorImpl(getInputSource().getURI(),
            t == null ? 0 : t.beginLine,
            t == null ? 0 : t.beginColumn);
    }

    /**
     * Add_escapes.
     *
     * @param str the str
     * @return the string
     */
    protected String add_escapes(final String str) {
        final StringBuilder retval = new StringBuilder();
        char ch;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            switch (ch) {
                case 0 :
                    continue;
                case '\b':
                    retval.append("\\b");
                    continue;
                case '\t':
                    retval.append("\\t");
                    continue;
                case '\n':
                    retval.append("\\n");
                    continue;
                case '\f':
                    retval.append("\\f");
                    continue;
                case '\r':
                    retval.append("\\r");
                    continue;
                case '\"':
                    retval.append("\\\"");
                    continue;
                case '\'':
                    retval.append("\\\'");
                    continue;
                case '\\':
                    retval.append("\\\\");
                    continue;
                default:
                    if (ch < 0x20 || ch > 0x7e) {
                        final String s = "0000" + Integer.toString(ch, 16);
                        retval.append("\\u" + s.substring(s.length() - 4, s.length()));
                    }
                    else {
                        retval.append(ch);
                    }
                    continue;
            }
        }
        return retval.toString();
    }

    /**
     * To css parse exception.
     *
     * @param key the key
     * @param e the e
     * @return the CSS parse exception
     */
    protected CSSParseException toCSSParseException(final String key, final ParseException e) {
        final String messagePattern1 = getSACParserMessages().getString("invalidExpectingOne");
        final String messagePattern2 = getSACParserMessages().getString("invalidExpectingMore");
        int maxSize = 0;
        final StringBuilder expected = new StringBuilder();
        for (int i = 0; i < e.expectedTokenSequences.length; i++) {
            if (maxSize < e.expectedTokenSequences[i].length) {
                maxSize = e.expectedTokenSequences[i].length;
            }
            for (int j = 0; j < e.expectedTokenSequences[i].length; j++) {
                expected.append(e.tokenImage[e.expectedTokenSequences[i][j]]);
            }
            if (i < e.expectedTokenSequences.length - 1) {
                expected.append(", ");
            }
        }
        final StringBuilder invalid = new StringBuilder();
        Token tok = e.currentToken.next;
        for (int i = 0; i < maxSize; i++) {
            if (i != 0) {
                invalid.append(" ");
            }
            if (tok.kind == 0) {
                invalid.append(e.tokenImage[0]);
                break;
            }
            invalid.append(add_escapes(tok.image));
            tok = tok.next;
        }
        String s = null;
        try {
            s = getSACParserMessages().getString(key);
        }
        catch (final MissingResourceException ex) {
            s = key;
        }
        final StringBuilder message = new StringBuilder(s);
        message.append(" (");
        if (e.expectedTokenSequences.length == 1) {
            message.append(MessageFormat.format(messagePattern1, new Object[] {invalid, expected}));
        }
        else {
            message.append(MessageFormat.format(messagePattern2, new Object[] {invalid, expected}));
        }
        message.append(")");
        return new CSSParseException(message.toString(),
            getInputSource().getURI(), e.currentToken.next.beginLine,
            e.currentToken.next.beginColumn);
    }

    /**
     * To css parse exception.
     *
     * @param e the e
     * @return the CSS parse exception
     */
    protected CSSParseException toCSSParseException(final TokenMgrError e) {
        final String messagePattern = getSACParserMessages().getString("tokenMgrError");
        return new CSSParseException(messagePattern, getInputSource().getURI(), 1, 1);
    }

    /**
     * To css parse exception.
     *
     * @param messageKey the message key
     * @param msgParams the msg params
     * @param locator the locator
     * @return the CSS parse exception
     */
    protected CSSParseException toCSSParseException(final String messageKey,
            final Object[] msgParams, final Locator locator) {
        final String messagePattern = getSACParserMessages().getString(messageKey);
        return new CSSParseException(MessageFormat.format(messagePattern, msgParams), locator);
    }

    /**
     * Creates the skip warning.
     *
     * @param key the key
     * @param e the e
     * @return the CSS parse exception
     */
    protected CSSParseException createSkipWarning(final String key, final CSSParseException e) {
        String s = null;
        try {
            s = getSACParserMessages().getString(key);
        }
        catch (final MissingResourceException ex) {
            s = key;
        }
        return new CSSParseException(s, e.getURI(), e.getLineNumber(), e.getColumnNumber());
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Parser#parseStyleSheet(org.w3c.css.sac.InputSource)
     */
    public void parseStyleSheet(final InputSource source) throws IOException {
        source_ = source;
        ReInit(getCharStream(source));
        try {
            styleSheet();
        }
        catch (final ParseException e) {
            getErrorHandler().error(toCSSParseException("invalidStyleSheet", e));
        }
        catch (final TokenMgrError e) {
            getErrorHandler().error(toCSSParseException(e));
        }
        catch (final CSSParseException e) {
            getErrorHandler().error(e);
        }
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Parser#parseStyleSheet(java.lang.String)
     */
    public void parseStyleSheet(final String uri) throws IOException {
        parseStyleSheet(new InputSource(uri));
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Parser#parseStyleDeclaration(org.w3c.css.sac.InputSource)
     */
    public void parseStyleDeclaration(final InputSource source) throws IOException {
        source_ = source;
        ReInit(getCharStream(source));
        try {
            styleDeclaration();
        }
        catch (final ParseException e) {
            getErrorHandler().error(toCSSParseException("invalidStyleDeclaration", e));
        }
        catch (final TokenMgrError e) {
            getErrorHandler().error(toCSSParseException(e));
        }
        catch (final CSSParseException e) {
            getErrorHandler().error(e);
        }
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Parser#parseRule(org.w3c.css.sac.InputSource)
     */
    public void parseRule(final InputSource source) throws IOException {
        source_ = source;
        ReInit(getCharStream(source));
        try {
            styleSheetRuleSingle();
        }
        catch (final ParseException e) {
            getErrorHandler().error(toCSSParseException("invalidRule", e));
        }
        catch (final TokenMgrError e) {
            getErrorHandler().error(toCSSParseException(e));
        }
        catch (final CSSParseException e) {
            getErrorHandler().error(e);
        }
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Parser#parseSelectors(org.w3c.css.sac.InputSource)
     */
    public SelectorList parseSelectors(final InputSource source) throws IOException {
        source_ = source;
        ReInit(getCharStream(source));
        SelectorList sl = null;
        try {
            sl = parseSelectorsInternal();
        }
        catch (final ParseException e) {
            getErrorHandler().error(toCSSParseException("invalidSelectorList", e));
        }
        catch (final TokenMgrError e) {
            getErrorHandler().error(toCSSParseException(e));
        }
        catch (final CSSParseException e) {
            getErrorHandler().error(e);
        }
        return sl;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Parser#parsePropertyValue(org.w3c.css.sac.InputSource)
     */
    public LexicalUnit parsePropertyValue(final InputSource source) throws IOException {
        source_ = source;
        ReInit(getCharStream(source));
        LexicalUnit lu = null;
        try {
            lu = expr();
        }
        catch (final ParseException e) {
            getErrorHandler().error(toCSSParseException("invalidExpr", e));
        }
        catch (final TokenMgrError e) {
            getErrorHandler().error(toCSSParseException(e));
        }
        catch (final CSSParseException e) {
            getErrorHandler().error(e);
        }
        return lu;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Parser#parsePriority(org.w3c.css.sac.InputSource)
     */
    public boolean parsePriority(final InputSource source) throws IOException {
        source_ = source;
        ReInit(getCharStream(source));
        boolean b = false;
        try {
            b = prio();
        }
        catch (final ParseException e) {
            getErrorHandler().error(toCSSParseException("invalidPrio", e));
        }
        catch (final TokenMgrError e) {
            getErrorHandler().error(toCSSParseException(e));
        }
        catch (final CSSParseException e) {
            getErrorHandler().error(e);
        }
        return b;
    }

    /**
     * Parses the media.
     *
     * @param source the source
     * @return the SAC media list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public SACMediaList parseMedia(final InputSource source) throws IOException {
        source_ = source;
        ReInit(getCharStream(source));
        final SACMediaListImpl ml = new SACMediaListImpl();
        try {
            mediaList(ml);
        }
        catch (final ParseException e) {
            getErrorHandler().error(toCSSParseException("invalidMediaList", e));
        }
        catch (final TokenMgrError e) {
            getErrorHandler().error(toCSSParseException(e));
        }
        catch (final CSSParseException e) {
            getErrorHandler().error(e);
        }
        return ml;
    }

    /**
     * Gets the char stream.
     *
     * @param source the source
     * @return the char stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private CharStream getCharStream(final InputSource source) throws IOException {
        if (source.getCharacterStream() != null) {
            return new CssCharStream(source.getCharacterStream(), 1, 1);
        }
        if (source.getByteStream() != null) {
            return new CssCharStream(new InputStreamReader(source.getByteStream()), 1, 1);
        }
        if (source.getURI() != null) {
            return new CssCharStream(new InputStreamReader(new URL(source.getURI()).openStream()), 1, 1);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.Parser#getParserVersion()
     */
    public abstract String getParserVersion();
    
    /**
     * Gets the grammar uri.
     *
     * @return the grammar uri
     */
    protected abstract String getGrammarUri();
    
    /**
     * Re init.
     *
     * @param charStream the char stream
     */
    protected abstract void ReInit(CharStream charStream);
    
    /**
     * Style sheet.
     *
     * @throws CSSParseException the CSS parse exception
     * @throws ParseException the parse exception
     */
    protected abstract void styleSheet() throws CSSParseException, ParseException;
    
    /**
     * Style declaration.
     *
     * @throws ParseException the parse exception
     */
    protected abstract void styleDeclaration() throws ParseException;
    
    /**
     * Style sheet rule single.
     *
     * @throws ParseException the parse exception
     */
    protected abstract void styleSheetRuleSingle() throws ParseException;
    
    /**
     * Parses the selectors internal.
     *
     * @return the selector list
     * @throws ParseException the parse exception
     */
    protected abstract SelectorList parseSelectorsInternal() throws ParseException;
    
    /**
     * Selector list.
     *
     * @return the selector list
     * @throws ParseException the parse exception
     */
    protected abstract SelectorList selectorList() throws ParseException;
    
    /**
     * Expr.
     *
     * @return the lexical unit
     * @throws ParseException the parse exception
     */
    protected abstract LexicalUnit expr() throws ParseException;
    
    /**
     * Prio.
     *
     * @return true, if successful
     * @throws ParseException the parse exception
     */
    protected abstract boolean prio() throws ParseException;
    
    /**
     * Media list.
     *
     * @param ml the ml
     * @throws ParseException the parse exception
     */
    protected abstract void mediaList(SACMediaListImpl ml) throws ParseException;

    /**
     * Handle start document.
     */
    protected void handleStartDocument() {
        getDocumentHandler().startDocument(getInputSource());
    }

    /**
     * Handle end document.
     */
    protected void handleEndDocument() {
        getDocumentHandler().endDocument(getInputSource());
    }

    /**
     * Handle ignorable at rule.
     *
     * @param s the s
     * @param locator the locator
     */
    protected void handleIgnorableAtRule(final String s, final Locator locator) {
        final DocumentHandler documentHandler = getDocumentHandler();
        if (documentHandler instanceof DocumentHandlerExt) {
            ((DocumentHandlerExt) documentHandler).ignorableAtRule(s, locator);
        }
        else {
            documentHandler.ignorableAtRule(s);
        }
    }

    /**
     * Handle charset.
     *
     * @param characterEncoding the character encoding
     * @param locator the locator
     */
    protected void handleCharset(final String characterEncoding, final Locator locator) {
        final DocumentHandler documentHandler = getDocumentHandler();
        if (documentHandler instanceof DocumentHandlerExt) {
            ((DocumentHandlerExt) documentHandler).charset(characterEncoding, locator);
        }
    }

    /**
     * Handle import style.
     *
     * @param uri the uri
     * @param media the media
     * @param defaultNamespaceURI the default namespace uri
     * @param locator the locator
     */
    protected void handleImportStyle(final String uri, final SACMediaList media,
            final String defaultNamespaceURI, final Locator locator) {
        final DocumentHandler documentHandler = getDocumentHandler();
        if (documentHandler instanceof DocumentHandlerExt) {
            ((DocumentHandlerExt) documentHandler).importStyle(uri, media, defaultNamespaceURI, locator);
        }
        else {
            documentHandler.importStyle(uri, media, defaultNamespaceURI);
        }
    }

    /**
     * Handle start media.
     *
     * @param media the media
     * @param locator the locator
     */
    protected void handleStartMedia(final SACMediaList media, final Locator locator) {
        final DocumentHandler documentHandler = getDocumentHandler();
        if (documentHandler instanceof DocumentHandlerExt) {
            ((DocumentHandlerExt) documentHandler).startMedia(media, locator);
        }
        else {
            documentHandler.startMedia(media);
        }
    }

    /**
     * Handle medium.
     *
     * @param medium the medium
     * @param locator the locator
     */
    protected void handleMedium(final String medium, final Locator locator) {
        // empty default impl
    }

    /**
     * Handle end media.
     *
     * @param media the media
     */
    protected void handleEndMedia(final SACMediaList media) {
        getDocumentHandler().endMedia(media);
    }

    /**
     * Handle start page.
     *
     * @param name the name
     * @param pseudoPage the pseudo page
     * @param locator the locator
     */
    protected void handleStartPage(final String name, final String pseudoPage, final Locator locator) {
        final DocumentHandler documentHandler = getDocumentHandler();
        if (documentHandler instanceof DocumentHandlerExt) {
            ((DocumentHandlerExt) documentHandler).startPage(name, pseudoPage, locator);
        }
        else {
            documentHandler.startPage(name, pseudoPage);
        }
    }

    /**
     * Handle end page.
     *
     * @param name the name
     * @param pseudoPage the pseudo page
     */
    protected void handleEndPage(final String name, final String pseudoPage) {
        getDocumentHandler().endPage(name, pseudoPage);
    }

    /**
     * Handle start font face.
     *
     * @param locator the locator
     */
    protected void handleStartFontFace(final Locator locator) {
        final DocumentHandler documentHandler = getDocumentHandler();
        if (documentHandler instanceof DocumentHandlerExt) {
            ((DocumentHandlerExt) documentHandler).startFontFace(locator);
        }
        else {
            documentHandler.startFontFace();
        }
    }

    /**
     * Handle end font face.
     */
    protected void handleEndFontFace() {
        getDocumentHandler().endFontFace();
    }

    /**
     * Handle selector.
     *
     * @param selector the selector
     */
    protected void handleSelector(final Selector selector) {
        // empty default impl
    }

    /**
     * Handle start selector.
     *
     * @param selectors the selectors
     * @param locator the locator
     */
    protected void handleStartSelector(final SelectorList selectors, final Locator locator) {
        final DocumentHandler documentHandler = getDocumentHandler();
        if (documentHandler instanceof DocumentHandlerExt) {
            ((DocumentHandlerExt) documentHandler).startSelector(selectors, locator);
        }
        else {
            documentHandler.startSelector(selectors);
        }
    }

    /**
     * Handle end selector.
     *
     * @param selectors the selectors
     */
    protected void handleEndSelector(final SelectorList selectors) {
        getDocumentHandler().endSelector(selectors);
    }

    /**
     * Handle property.
     *
     * @param name the name
     * @param value the value
     * @param important the important
     * @param locator the locator
     */
    protected void handleProperty(final String name, final LexicalUnit value,
            final boolean important, final Locator locator) {
        final DocumentHandler documentHandler = getDocumentHandler();
        if (documentHandler instanceof DocumentHandlerExt) {
            ((DocumentHandlerExt) documentHandler).property(name, value, important, locator);
        }
        else {
            documentHandler.property(name, value, important);
        }
    }

    /**
     * Function internal.
     *
     * @param prev the prev
     * @param funct the funct
     * @param params the params
     * @return the lexical unit
     */
    protected LexicalUnit functionInternal(final LexicalUnit prev, final String funct,
            final LexicalUnit params) {

        if ("counter(".equalsIgnoreCase(funct)) {
            return LexicalUnitImpl.createCounter(prev, params);
        }
        else if ("counters(".equalsIgnoreCase(funct)) {
            return LexicalUnitImpl.createCounters(prev, params);
        }
        else if ("attr(".equalsIgnoreCase(funct)) {
            return LexicalUnitImpl.createAttr(prev, params.getStringValue());
        }
        else if ("rect(".equalsIgnoreCase(funct)) {
            return LexicalUnitImpl.createRect(prev, params);
        }
        else if ("rgb(".equalsIgnoreCase(funct)) {
            return LexicalUnitImpl.createRgbColor(prev, params);
        }
        return LexicalUnitImpl.createFunction(
            prev,
            funct.substring(0, funct.length() - 1),
            params);
    }

    /**
     * Hexcolor internal.
     *
     * @param prev the prev
     * @param t the t
     * @return the lexical unit
     */
    protected LexicalUnit hexcolorInternal(final LexicalUnit prev, final Token t) {
        // Step past the hash at the beginning
        final int i = 1;
        int r = 0;
        int g = 0;
        int b = 0;
        final int len = t.image.length() - 1;
        try {
            if (len == 3) {
                r = Integer.parseInt(t.image.substring(i + 0, i + 1), 16);
                g = Integer.parseInt(t.image.substring(i + 1, i + 2), 16);
                b = Integer.parseInt(t.image.substring(i + 2, i + 3), 16);
                r = (r << 4) | r;
                g = (g << 4) | g;
                b = (b << 4) | b;
            }
            else if (len == 6) {
                r = Integer.parseInt(t.image.substring(i + 0, i + 2), 16);
                g = Integer.parseInt(t.image.substring(i + 2, i + 4), 16);
                b = Integer.parseInt(t.image.substring(i + 4, i + 6), 16);
            }
            else {
                final String pattern = getSACParserMessages().getString("invalidColor");
                throw new CSSParseException(MessageFormat.format(
                    pattern, new Object[] {t}),
                    getInputSource().getURI(), t.beginLine,
                    t.beginColumn);
            }

            // Turn into an "rgb()"
            final LexicalUnit lr = LexicalUnitImpl.createNumber(null, r);
            final LexicalUnit lc1 = LexicalUnitImpl.createComma(lr);
            final LexicalUnit lg = LexicalUnitImpl.createNumber(lc1, g);
            final LexicalUnit lc2 = LexicalUnitImpl.createComma(lg);
            LexicalUnitImpl.createNumber(lc2, b);

            return LexicalUnitImpl.createRgbColor(prev, lr);
        }
        catch (final NumberFormatException ex) {
            final String pattern = getSACParserMessages().getString("invalidColor");
            throw new CSSParseException(MessageFormat.format(
                pattern, new Object[] {t}),
                getInputSource().getURI(), t.beginLine,
                t.beginColumn, ex);
        }
    }

    /**
     * Int value.
     *
     * @param op the op
     * @param s the s
     * @return the int
     */
    int intValue(final char op, final String s) {
        final int result = Integer.parseInt(s);
        if (op == '-') {
            return -1 * result;
        }
        return result;
    }

    /**
     * Float value.
     *
     * @param op the op
     * @param s the s
     * @return the float
     */
    float floatValue(final char op, final String s) {
        final float result = Float.parseFloat(s);
        if (op == '-') {
            return -1 * result;
        }
        return result;
    }

    /**
     * Gets the last num pos.
     *
     * @param s the s
     * @return the last num pos
     */
    int getLastNumPos(final String s) {
        int i = 0;
        for ( ; i < s.length(); i++) {
            if (NUM_CHARS.indexOf(s.charAt(i)) < 0) {
                break;
            }
        }
        return i - 1;
    }

    /**
     * Unescapes escaped characters in the specified string, according to the
     * <a href="http://www.w3.org/TR/CSS21/syndata.html#escaped-characters">CSS specification</a>.
     * 
     * This could be done directly in the parser, but portions of the lexer would have to be moved
     * to the parser, meaning that the grammar would no longer match the standard grammar specified
     * by the W3C. This would make the parser and lexer much less maintainable.
     *
     * @param s the s
     * @param unescapeDoubleQuotes the unescape double quotes
     * @return the string
     */
    public String unescape(final String s, final boolean unescapeDoubleQuotes) {
        if (s == null) {
            return s;
        }

        // avoid creation of new string if possible
        StringBuilder buf = null;
        int index = -1;
        int len = s.length();
        len--;
        if (unescapeDoubleQuotes) {
            while (index < len) {
                final char c = s.charAt(++index);

                if (c == '\\' || (c == '\"')) {
                    buf = new StringBuilder(len);
                    buf.append(s.substring(0, index));
                    index--;
                    break;
                }
            }
        }
        else {
            while (index < len) {
                if ('\\' == s.charAt(++index)) {
                    buf = new StringBuilder(len);
                    buf.append(s.substring(0, index));
                    index--;
                    break;
                }
            }
        }

        if (null == buf) {
            return s;
        }

        // ok, we have to construct a new string
        int numValue = -1;
        int hexval;
        int digitCount = 0;

        while (index < len) {
            final char c = s.charAt(++index);

            if (numValue > -1) {
                hexval = hexval(c);
                if (hexval != -1) {
                    numValue = (numValue * 16) + hexval;
                    if (++digitCount < 6) {
                        continue;
                    }

                    if (numValue > 0xFFFF || numValue == 0) {
                        numValue = 0xFFFD;
                    }
                    buf.append((char) numValue);
                    numValue = -1;
                    continue;
                }

                if (digitCount > 0) {
                    if (numValue > 0xFFFF || numValue == 0) {
                        numValue = 0xFFFD;
                    }

                    buf.append((char) numValue);

                    if (c == ' ' || c == '\t') {
                        numValue = -1;
                        continue;
                    }
                }

                numValue = -1;
                if (digitCount == 0 && c == '\\') {
                    buf.append('\\');
                    continue;
                }

                if (c == '\n' || c == '\f') {
                    continue;
                }
                if (c == '\r') {
                    if (index < len) {
                        if (s.charAt(index + 1) == '\n') {
                            index++;
                        }
                    }
                    continue;
                }
            }

            if (c == '\\') {
                numValue = 0;
                digitCount = 0;
                continue;
            }

            if (c == '\"' && !unescapeDoubleQuotes) {
                buf.append('\\');
            }

            buf.append(c);
        }

        if (numValue > -1) {
            if (digitCount == 0) {
                buf.append('\\');
            }
            else {
                if (numValue > 0xFFFF || numValue == 0) {
                    numValue = 0xFFFD;
                }
                buf.append((char) numValue);
            }
        }

        return buf.toString();
    }

    /**
     * Hexval.
     *
     * @param c the c
     * @return the int
     */
    private static int hexval(final char c) {
        switch(c) {
            case '0' :
                return 0;
            case '1' :
                return 1;
            case '2' :
                return 2;
            case '3' :
                return 3;
            case '4' :
                return 4;
            case '5' :
                return 5;
            case '6' :
                return 6;
            case '7' :
                return 7;
            case '8' :
                return 8;
            case '9' :
                return 9;

            case 'a' :
            case 'A' :
                return 10;
            case 'b' :
            case 'B' :
                return 11;
            case 'c' :
            case 'C' :
                return 12;
            case 'd' :
            case 'D' :
                return 13;
            case 'e' :
            case 'E' :
                return 14;
            case 'f' :
            case 'F' :
                return 15;
            default :
                return -1;
        }
    }
}
