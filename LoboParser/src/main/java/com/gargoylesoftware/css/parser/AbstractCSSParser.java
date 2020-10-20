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
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;

import org.w3c.dom.DOMException;

import com.gargoylesoftware.css.parser.javacc.CharStream;
import com.gargoylesoftware.css.parser.javacc.ParseException;
import com.gargoylesoftware.css.parser.javacc.Token;
import com.gargoylesoftware.css.parser.javacc.TokenMgrError;
import com.gargoylesoftware.css.parser.media.MediaQueryList;
import com.gargoylesoftware.css.parser.selector.SelectorList;

/**
 * Base implementation of {@link CSSParser}.
 *
 * @author Ronald Brill
 */
public abstract class AbstractCSSParser implements CSSParser {
    private DocumentHandler documentHandler_;
    private CSSErrorHandler errorHandler_;
    private InputSource source_;

    private static final HashMap<String, String> parserMessages_ = new HashMap<>();

    static {
        parserMessages_.put("invalidExpectingOne", "Invalid token \"{0}\". Was expecting: {1}.");
        parserMessages_.put("invalidExpectingMore", "Invalid token \"{0}\". Was expecting one of: {1}.");
        parserMessages_.put("invalidColor", "Invalid color \"{0}\".");
        parserMessages_.put("invalidStyleSheet", "Error in style sheet.");
        parserMessages_.put("invalidRule", "Error in rule.");
        parserMessages_.put("invalidUnknownRule", "Error in unknown at-rule.");
        parserMessages_.put("invalidCharsetRule", "Error in @charset rule.");
        parserMessages_.put("misplacedCharsetRule", "The @charset must be the first element in the style sheet.");
        parserMessages_.put("invalidImportRule", "Error in @import rule.");
        parserMessages_.put("invalidImportRuleIgnored", "@import rule must occur before all other rules.");
        parserMessages_.put("invalidImportRuleIgnored2",
                                "@import rule must occur before all other rules, except the @charset rule.");
        parserMessages_.put("invalidPageRule", "Error in @page rule.");
        parserMessages_.put("invalidFontFaceRule", "Error in @font-face rule.");
        parserMessages_.put("invalidMediaList", "Error in media list.");
        parserMessages_.put("invalidMediaRule", "Error in @media rule.");
        parserMessages_.put("invalidStyleRule", "Error in style rule.");
        parserMessages_.put("invalidStyleDeclaration", "Error in style declaration.");
        parserMessages_.put("invalidDeclaration", "Error in declaration.");
        parserMessages_.put("invalidDeclarationInvalidChar", "Error in declaration; invalid character \"{0}\" found.");
        parserMessages_.put("invalidDeclarationStarHack",
                                    "Error in declaration. ''*'' is not allowed as first char of a property.");
        parserMessages_.put("invalidSelectorList", "Error in selector list.");
        parserMessages_.put("invalidSelector", "Error in selector.");
        parserMessages_.put("invalidSimpleSelector", "Error in simple selector.");
        parserMessages_.put("invalidClassSelector", "Error in class selector.");
        parserMessages_.put("invalidElementName", "Error in element name.");
        parserMessages_.put("invalidAttrib", "Error in attribute selector.");
        parserMessages_.put("invalidPseudo", "Error in pseudo class or element.");
        parserMessages_.put("duplicatePseudo", "Duplicate pseudo class \":{0}\" or pseudo class \":{0}\" not at end.");
        parserMessages_.put("invalidHash", "Error in hash.");
        parserMessages_.put("invalidExpr", "Error in expression.");
        parserMessages_.put("invalidExprColon", "Error in expression; '':'' found after identifier \"{0}\".");
        parserMessages_.put("invalidPrio", "Error in priority.");

        parserMessages_.put("ignoringRule", "Ignoring the whole rule.");
        parserMessages_.put("ignoringFollowingDeclarations", "Ignoring the following declarations in this rule.");

        parserMessages_.put("tokenMgrError", "Lexical error.");
        parserMessages_.put("domException", "DOM exception: ''{0}''");
    }

    private boolean ieStarHackAccepted_;

    private static final String NUM_CHARS = "0123456789.";

    /**
     * @return the document handler
     */
    protected DocumentHandler getDocumentHandler() {
        if (documentHandler_ == null) {
            setDocumentHandler(new HandlerBase());
        }
        return documentHandler_;
    }

    @Override
    public void setDocumentHandler(final DocumentHandler handler) {
        documentHandler_ = handler;
    }

    /**
     * @return the error handler
     */
    protected CSSErrorHandler getErrorHandler() {
        if (errorHandler_ == null) {
            setErrorHandler(new HandlerBase());
        }
        return errorHandler_;
    }

    @Override
    public void setErrorHandler(final CSSErrorHandler eh) {
        errorHandler_ = eh;
    }

    /**
     * @return the input source
     */
    protected InputSource getInputSource() {
        return source_;
    }

    @Override
    public void setIeStarHackAccepted(final boolean accepted) {
        ieStarHackAccepted_ = accepted;
    }

    @Override
    public boolean isIeStarHackAccepted() {
        return ieStarHackAccepted_;
    }

    /**
     * @param key the lookup key
     * @return the parser message
     */
    protected String getParserMessage(final String key) {
        final String msg = parserMessages_.get(key);
        if (msg == null) {
            return "[[" + key + "]]";
        }
        return msg;
    }

    /**
     * Returns a new locator for the given token.
     * @param t the token to generate the locator for
     * @return a new locator
     */
    protected Locator createLocator(final Token t) {
        return new Locator(getInputSource().getURI(),
            t == null ? 0 : t.beginLine,
            t == null ? 0 : t.beginColumn);
    }

    /**
     * Escapes some chars in the given string.
     * @param str the input
     * @return a new string with the escaped values
     */
    protected String addEscapes(final String str) {
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

    /**
     *
     * @param key the message lookup key
     * @param e the parse exception
     * @return a new CSSParseException
     */
    protected CSSParseException toCSSParseException(final String key, final ParseException e) {
        final String messagePattern1 = getParserMessage("invalidExpectingOne");
        final String messagePattern2 = getParserMessage("invalidExpectingMore");
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
            invalid.append(addEscapes(tok.image));
            tok = tok.next;
        }
        final StringBuilder message = new StringBuilder(getParserMessage(key));
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
     * @param e the DOMException
     * @return a new CSSParseException
     */
    protected CSSParseException toCSSParseException(final DOMException e) {
        final String messagePattern = getParserMessage("domException");
        return new CSSParseException(
                MessageFormat.format(messagePattern, e.getMessage()), getInputSource().getURI(), 1, 1);
    }

    /**
     * @param e the TokenMgrError
     * @return a new CSSParseException
     */
    protected CSSParseException toCSSParseException(final TokenMgrError e) {
        final String messagePattern = getParserMessage("tokenMgrError");
        return new CSSParseException(messagePattern, getInputSource().getURI(), 1, 1);
    }

    /**
     * @param messageKey the message key
     * @param msgParams the params
     * @param locator the locator
     * @return a new CSSParseException
     */
    protected CSSParseException toCSSParseException(final String messageKey,
            final Object[] msgParams, final Locator locator) {
        final String messagePattern = getParserMessage(messageKey);
        return new CSSParseException(MessageFormat.format(messagePattern, msgParams), locator);
    }

    /**
     * @param messageKey the message key
     * @param e a CSSParseException
     * @return a new CSSParseException
     */
    protected CSSParseException createSkipWarning(final String messageKey, final CSSParseException e) {
        return new CSSParseException(getParserMessage(messageKey), e.getURI(), e.getLineNumber(), e.getColumnNumber());
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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
     * Parse the given input source and return the media list.
     * @param source the input source
     * @return new media list
     * @throws IOException in case of errors
     */
    public MediaQueryList parseMedia(final InputSource source) throws IOException {
        source_ = source;
        ReInit(getCharStream(source));
        final MediaQueryList ml = new MediaQueryList();
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

    private static CharStream getCharStream(final InputSource source) throws IOException {
        if (source.getReader() != null) {
            return new CssCharStream(source.getReader(), 1, 1);
        }
        if (source.getURI() != null) {
            final InputStreamReader reader = new InputStreamReader(new URL(source.getURI()).openStream());
            return new CssCharStream(reader, 1, 1);
        }
        return null;
    }

    @Override
    public abstract String getParserVersion();

    /**
     * Re intit the stream.
     * @param charStream the stream
     */
    protected abstract void ReInit(CharStream charStream);

    /**
     * Process a style sheet.
     *
     * @throws CSSParseException in case of error
     * @throws ParseException in case of error
     */
    protected abstract void styleSheet() throws CSSParseException, ParseException;

    /**
     * Process a style sheet declaration.
     *
     * @throws ParseException in case of error
     */
    protected abstract void styleDeclaration() throws ParseException;

    /**
     * Process a style sheet rule.
     *
     * @throws ParseException in case of error
     */
    protected abstract void styleSheetRuleSingle() throws ParseException;

    /**
     * Process a selector list.
     *
     * @return the selector list
     * @throws ParseException in case of error
     */
    protected abstract SelectorList parseSelectorsInternal() throws ParseException;

    /**
     * Process an expression.
     *
     * @return the lexical unit
     * @throws ParseException in case of error
     */
    protected abstract LexicalUnit expr() throws ParseException;

    /**
     * Process a prio.
     *
     * @return true or false
     * @throws ParseException in case of error
     */
    protected abstract boolean prio() throws ParseException;

    /**
     * Process a media list.
     *
     * @param ml the media list
     * @throws ParseException in case of error
     */
    protected abstract void mediaList(MediaQueryList ml) throws ParseException;

    /**
     * start document handler.
     */
    protected void handleStartDocument() {
        getDocumentHandler().startDocument(getInputSource());
    }

    /**
     * end document handler.
     */
    protected void handleEndDocument() {
        getDocumentHandler().endDocument(getInputSource());
    }

    /**
     * ignorable at rule handler.
     *
     * @param s the rule
     * @param locator the locator
     */
    protected void handleIgnorableAtRule(final String s, final Locator locator) {
        getDocumentHandler().ignorableAtRule(s, locator);
    }

    /**
     * charset handler.
     *
     * @param characterEncoding the encoding
     * @param locator the locator
     */
    protected void handleCharset(final String characterEncoding, final Locator locator) {
        getDocumentHandler().charset(characterEncoding, locator);
    }

    /**
     * import style handler.
     *
     * @param uri the uri
     * @param media the media query list
     * @param defaultNamespaceURI the namespace uri
     * @param locator the locator
     */
    protected void handleImportStyle(final String uri, final MediaQueryList media,
            final String defaultNamespaceURI, final Locator locator) {
        getDocumentHandler().importStyle(uri, media, defaultNamespaceURI, locator);
    }

    /**
     * start media handler.
     *
     * @param media the media query list
     * @param locator the locator
     */
    protected void handleStartMedia(final MediaQueryList media, final Locator locator) {
        getDocumentHandler().startMedia(media, locator);
    }

    /**
     * medium handler.
     *
     * @param medium the medium
     * @param locator the locator
     */
    protected void handleMedium(final String medium, final Locator locator) {
        // empty default impl
    }

    /**
     * end media handler.
     *
     * @param media the media query list
     */
    protected void handleEndMedia(final MediaQueryList media) {
        getDocumentHandler().endMedia(media);
    }

    /**
     * start page handler.
     *
     * @param name the name
     * @param pseudoPage the pseudo page
     * @param locator the locator
     */
    protected void handleStartPage(final String name, final String pseudoPage, final Locator locator) {
        getDocumentHandler().startPage(name, pseudoPage, locator);
    }

    /**
     * end page handler.
     *
     * @param name the name
     * @param pseudoPage the pseudo page
     */
    protected void handleEndPage(final String name, final String pseudoPage) {
        getDocumentHandler().endPage(name, pseudoPage);
    }

    /**
     * start font face handler.
     *
     * @param locator the locator
     */
    protected void handleStartFontFace(final Locator locator) {
        getDocumentHandler().startFontFace(locator);
    }

    /**
     * end font face handler.
     */
    protected void handleEndFontFace() {
        getDocumentHandler().endFontFace();
    }

    /**
     * selector start handler.
     *
     * @param selectors the selector list
     * @param locator the locator
     */
    protected void handleStartSelector(final SelectorList selectors, final Locator locator) {
        getDocumentHandler().startSelector(selectors, locator);
    }

    /**
     * selector end handler.
     *
     * @param selectors the selector list
     */
    protected void handleEndSelector(final SelectorList selectors) {
        getDocumentHandler().endSelector(selectors);
    }

    /**
     * property handler.
     *
     * @param name the name
     * @param value the value
     * @param important important flag
     * @param locator the locator
     */
    protected void handleProperty(final String name, final LexicalUnit value,
            final boolean important, final Locator locator) {
        getDocumentHandler().property(name, value, important, locator);
    }

    /**
     * Process a function decl.
     *
     * @param prev the previous lexical unit
     * @param funct the function
     * @param params the params
     * @return a lexical unit
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
     * Processes a hexadecimal color definition.
     *
     * @param prev the previous lexical unit
     * @param t the token
     * @return a new lexical unit
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
                final String pattern = getParserMessage("invalidColor");
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
            final String pattern = getParserMessage("invalidColor");
            throw new CSSParseException(MessageFormat.format(
                pattern, new Object[] {t}),
                getInputSource().getURI(), t.beginLine,
                t.beginColumn, ex);
        }
    }

    /**
     * Parses the sting into an integer.
     *
     * @param op the sign char
     * @param s the string to parse
     * @return the int value
     */
    protected int intValue(final char op, final String s) {
        final int result = Integer.parseInt(s);
        if (op == '-') {
            return -1 * result;
        }
        return result;
    }

    /**
     * Parses the sting into an double.
     *
     * @param op the sign char
     * @param s the string to parse
     * @return the double value
     */
    protected double doubleValue(final char op, final String s) {
        final double result = Double.parseDouble(s);
        if (op == '-') {
            return -1 * result;
        }
        return result;
    }

    /**
     * Returns the pos of the last numeric char in the given string.
     *
     * @param s the string to parse
     * @return the pos
     */
    protected int getLastNumPos(final String s) {
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
     * @param s the string to unescape
     * @param unescapeDoubleQuotes if true unescape double quotes also
     * @return the unescaped string
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
