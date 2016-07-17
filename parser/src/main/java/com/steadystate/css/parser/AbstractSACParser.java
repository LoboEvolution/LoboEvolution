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
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
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
import org.w3c.dom.DOMException;

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
    private DocumentHandler documentHandler_;
    private ErrorHandler errorHandler_;
    private InputSource source_;
    private Locale locale_;
    private SelectorFactory selectorFactory_;
    private ConditionFactory conditionFactory_;
    private ResourceBundle sacParserMessages_;

    private static final String NUM_CHARS = "0123456789.";

    protected abstract Token getToken();

    protected DocumentHandler getDocumentHandler() {
        if (documentHandler_ == null) {
            setDocumentHandler(new HandlerBase());
        }
        return documentHandler_;
    }

    public void setDocumentHandler(final DocumentHandler handler) {
        documentHandler_ = handler;
    }

    protected ErrorHandler getErrorHandler() {
        if (errorHandler_ == null) {
            setErrorHandler(new HandlerBase());
        }
        return errorHandler_;
    }

    public void setErrorHandler(final ErrorHandler eh) {
        errorHandler_ = eh;
    }

    protected InputSource getInputSource() {
        return source_;
    }

    public void setLocale(final Locale locale) {
        if (locale_ != locale) {
            sacParserMessages_ = null;
        }
        locale_ = locale;
    }

    protected Locale getLocale() {
        if (locale_ == null) {
            setLocale(Locale.getDefault());
        }
        return locale_;
    }

    protected SelectorFactory getSelectorFactory() {
        if (selectorFactory_ == null) {
            selectorFactory_ = new SelectorFactoryImpl();
        }
        return selectorFactory_;
    }

    public void setSelectorFactory(final SelectorFactory selectorFactory) {
        selectorFactory_ = selectorFactory;
    }

    protected ConditionFactory getConditionFactory() {
        if (conditionFactory_ == null) {
            conditionFactory_ = new ConditionFactoryImpl();
        }
        return conditionFactory_;
    }

    public void setConditionFactory(final ConditionFactory conditionFactory) {
        conditionFactory_ = conditionFactory;
    }

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

    public Locator getLocator() {
        return createLocator(getToken());
    }

    protected Locator createLocator(final Token t) {
        return new LocatorImpl(getInputSource().getURI(),
            t == null ? 0 : t.beginLine,
            t == null ? 0 : t.beginColumn);
    }

    protected String add_escapes(final String str) {
        final StringBuilder sb = new StringBuilder();
        char ch;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            switch (ch) {
                case 0 :
                    continue;
                case '\b':
                    sb.append("\\b");
                    continue;
                case '\t':
                    sb.append("\\t");
                    continue;
                case '\n':
                    sb.append("\\n");
                    continue;
                case '\f':
                    sb.append("\\f");
                    continue;
                case '\r':
                    sb.append("\\r");
                    continue;
                case '\"':
                    sb.append("\\\"");
                    continue;
                case '\'':
                    sb.append("\\\'");
                    continue;
                case '\\':
                    sb.append("\\\\");
                    continue;
                default:
                    if (ch < 0x20 || ch > 0x7e) {
                        final String s = "0000" + Integer.toString(ch, 16);
                        sb.append("\\u" + s.substring(s.length() - 4, s.length()));
                    }
                    else {
                        sb.append(ch);
                    }
                    continue;
            }
        }
        return sb.toString();
    }

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

    protected CSSParseException toCSSParseException(final DOMException e) {
        final String messagePattern = getSACParserMessages().getString("domException");
        return new CSSParseException(
                MessageFormat.format(messagePattern, e.getMessage()), getInputSource().getURI(), 1, 1);
    }

    protected CSSParseException toCSSParseException(final TokenMgrError e) {
        final String messagePattern = getSACParserMessages().getString("tokenMgrError");
        return new CSSParseException(messagePattern, getInputSource().getURI(), 1, 1);
    }

    protected CSSParseException toCSSParseException(final String messageKey,
            final Object[] msgParams, final Locator locator) {
        final String messagePattern = getSACParserMessages().getString(messageKey);
        return new CSSParseException(MessageFormat.format(messagePattern, msgParams), locator);
    }

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

    public void parseStyleSheet(final String uri) throws IOException {
        parseStyleSheet(new InputSource(uri));
    }

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

    private CharStream getCharStream(final InputSource source) throws IOException {
        if (source.getCharacterStream() != null) {
            return new CssCharStream(source.getCharacterStream(), 1, 1);
        }
        if (source.getByteStream() != null) {
            final InputStreamReader reader;
            final String encoding = source.getEncoding();
            if (encoding == null || encoding.length() < 1) {
                reader = new InputStreamReader(source.getByteStream(), Charset.defaultCharset());
            }
            else {
                reader = new InputStreamReader(source.getByteStream(), encoding);
            }
            return new CssCharStream(reader, 1, 1);
        }
        if (source.getURI() != null) {
            final InputStreamReader reader = new InputStreamReader(new URL(source.getURI()).openStream());
            return new CssCharStream(reader, 1, 1);
        }
        return null;
    }

    public abstract String getParserVersion();
    protected abstract String getGrammarUri();
    protected abstract void ReInit(CharStream charStream);
    protected abstract void styleSheet() throws CSSParseException, ParseException;
    protected abstract void styleDeclaration() throws ParseException;
    protected abstract void styleSheetRuleSingle() throws ParseException;
    protected abstract SelectorList parseSelectorsInternal() throws ParseException;
    protected abstract SelectorList selectorList() throws ParseException;
    protected abstract LexicalUnit expr() throws ParseException;
    protected abstract boolean prio() throws ParseException;
    protected abstract void mediaList(SACMediaListImpl ml) throws ParseException;

    protected void handleStartDocument() {
        getDocumentHandler().startDocument(getInputSource());
    }

    protected void handleEndDocument() {
        getDocumentHandler().endDocument(getInputSource());
    }

    protected void handleIgnorableAtRule(final String s, final Locator locator) {
        final DocumentHandler documentHandler = getDocumentHandler();
        if (documentHandler instanceof DocumentHandlerExt) {
            ((DocumentHandlerExt) documentHandler).ignorableAtRule(s, locator);
        }
        else {
            documentHandler.ignorableAtRule(s);
        }
    }

    protected void handleCharset(final String characterEncoding, final Locator locator) {
        final DocumentHandler documentHandler = getDocumentHandler();
        if (documentHandler instanceof DocumentHandlerExt) {
            ((DocumentHandlerExt) documentHandler).charset(characterEncoding, locator);
        }
    }

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

    protected void handleStartMedia(final SACMediaList media, final Locator locator) {
        final DocumentHandler documentHandler = getDocumentHandler();
        if (documentHandler instanceof DocumentHandlerExt) {
            ((DocumentHandlerExt) documentHandler).startMedia(media, locator);
        }
        else {
            documentHandler.startMedia(media);
        }
    }

    protected void handleMedium(final String medium, final Locator locator) {
        // empty default impl
    }

    protected void handleEndMedia(final SACMediaList media) {
        getDocumentHandler().endMedia(media);
    }

    protected void handleStartPage(final String name, final String pseudoPage, final Locator locator) {
        final DocumentHandler documentHandler = getDocumentHandler();
        if (documentHandler instanceof DocumentHandlerExt) {
            ((DocumentHandlerExt) documentHandler).startPage(name, pseudoPage, locator);
        }
        else {
            documentHandler.startPage(name, pseudoPage);
        }
    }

    protected void handleEndPage(final String name, final String pseudoPage) {
        getDocumentHandler().endPage(name, pseudoPage);
    }

    protected void handleStartFontFace(final Locator locator) {
        final DocumentHandler documentHandler = getDocumentHandler();
        if (documentHandler instanceof DocumentHandlerExt) {
            ((DocumentHandlerExt) documentHandler).startFontFace(locator);
        }
        else {
            documentHandler.startFontFace();
        }
    }

    protected void handleEndFontFace() {
        getDocumentHandler().endFontFace();
    }

    protected void handleSelector(final Selector selector) {
        // empty default impl
    }

    protected void handleStartSelector(final SelectorList selectors, final Locator locator) {
        final DocumentHandler documentHandler = getDocumentHandler();
        if (documentHandler instanceof DocumentHandlerExt) {
            ((DocumentHandlerExt) documentHandler).startSelector(selectors, locator);
        }
        else {
            documentHandler.startSelector(selectors);
        }
    }

    protected void handleEndSelector(final SelectorList selectors) {
        getDocumentHandler().endSelector(selectors);
    }

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

    int intValue(final char op, final String s) {
        final int result = Integer.parseInt(s);
        if (op == '-') {
            return -1 * result;
        }
        return result;
    }

    float floatValue(final char op, final String s) {
        final float result = Float.parseFloat(s);
        if (op == '-') {
            return -1 * result;
        }
        return result;
    }

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

    private static int hexval(final char c) {
        switch (c) {
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
