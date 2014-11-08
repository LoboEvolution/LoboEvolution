/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2011 David Schweinsberg.  All rights reserved.
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
						"com.steadystate.css.parser.SACParserMessages",	getLocale());
			} catch (final MissingResourceException e) {
				//e.printStackTrace();
			}
		}
		return sacParserMessages_;
	}

	public Locator getLocator() {
		return createLocator(getToken());
	}

	protected Locator createLocator(final Token t) {
		return new LocatorImpl(getInputSource().getURI(), t == null ? 0
				: t.beginLine, t == null ? 0 : t.beginColumn);
	}

	protected String add_escapes(final String str) {
		final StringBuilder retval = new StringBuilder();
		char ch;
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			switch (ch) {
			case 0:
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
					retval.append("\\u"
							+ s.substring(s.length() - 4, s.length()));
				} else {
					retval.append(ch);
				}
				continue;
			}
		}
		return retval.toString();
	}

	protected CSSParseException toCSSParseException(final String key,
			final ParseException e) {
		final String messagePattern1 = getSACParserMessages().getString(
				"invalidExpectingOne");
		final String messagePattern2 = getSACParserMessages().getString(
				"invalidExpectingMore");
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
		} catch (final MissingResourceException ex) {
			s = key;
		}
		final StringBuilder message = new StringBuilder(s);
		message.append(" (");
		if (e.expectedTokenSequences.length == 1) {
			message.append(MessageFormat.format(messagePattern1, new Object[] {
					invalid, expected }));
		} else {
			message.append(MessageFormat.format(messagePattern2, new Object[] {
					invalid, expected }));
		}
		message.append(")");
		return new CSSParseException(message.toString(), getInputSource()
				.getURI(), e.currentToken.next.beginLine,
				e.currentToken.next.beginColumn);
	}

	protected CSSParseException toCSSParseException(final TokenMgrError e) {
		final String messagePattern = getSACParserMessages().getString(
				"tokenMgrError");
		return new CSSParseException(messagePattern, getInputSource().getURI(),
				1, 1);
	}

	protected CSSParseException toCSSParseException(final String messageKey,
			final Object[] msgParams, final Locator locator) {
		final String messagePattern = getSACParserMessages().getString(
				messageKey);
		return new CSSParseException(MessageFormat.format(messagePattern,
				msgParams), locator);
	}

	protected CSSParseException createSkipWarning(final String key,
			final CSSParseException e) {
		String s = null;
		try {
			s = getSACParserMessages().getString(key);
		} catch (final MissingResourceException ex) {
			s = key;
		}
		return new CSSParseException(s, e.getURI(), e.getLineNumber(),
				e.getColumnNumber());
	}

	public void parseStyleSheet(final InputSource source) throws IOException {
		source_ = source;
		ReInit(getCharStream(source));
		try {
			styleSheet();
		} catch (final ParseException e) {
			getErrorHandler()
					.error(toCSSParseException("invalidStyleSheet", e));
		} catch (final TokenMgrError e) {
			getErrorHandler().error(toCSSParseException(e));
		} catch (final CSSParseException e) {
			getErrorHandler().error(e);
		}
	}

	public void parseStyleSheet(final String uri) throws IOException {
		parseStyleSheet(new InputSource(uri));
	}

	public void parseStyleDeclaration(final InputSource source)
			throws IOException {
		source_ = source;
		ReInit(getCharStream(source));
		try {
			styleDeclaration();
		} catch (final ParseException e) {
			getErrorHandler().error(
					toCSSParseException("invalidStyleDeclaration", e));
		} catch (final TokenMgrError e) {
			getErrorHandler().error(toCSSParseException(e));
		} catch (final CSSParseException e) {
			getErrorHandler().error(e);
		}
	}

	public void parseRule(final InputSource source) throws IOException {
		source_ = source;
		ReInit(getCharStream(source));
		try {
			styleSheetRuleSingle();
		} catch (final ParseException e) {
			getErrorHandler().error(toCSSParseException("invalidRule", e));
		} catch (final TokenMgrError e) {
			getErrorHandler().error(toCSSParseException(e));
		} catch (final CSSParseException e) {
			getErrorHandler().error(e);
		}
	}

	public SelectorList parseSelectors(final InputSource source)
			throws IOException {
		source_ = source;
		ReInit(getCharStream(source));
		SelectorList sl = null;
		try {
			sl = parseSelectorsInternal();
		} catch (final ParseException e) {
			getErrorHandler().error(
					toCSSParseException("invalidSelectorList", e));
		} catch (final TokenMgrError e) {
			getErrorHandler().error(toCSSParseException(e));
		} catch (final CSSParseException e) {
			getErrorHandler().error(e);
		}
		return sl;
	}

	public LexicalUnit parsePropertyValue(final InputSource source)
			throws IOException {
		source_ = source;
		ReInit(getCharStream(source));
		LexicalUnit lu = null;
		try {
			lu = expr();
		} catch (final ParseException e) {
			getErrorHandler().error(toCSSParseException("invalidExpr", e));
		} catch (final TokenMgrError e) {
			getErrorHandler().error(toCSSParseException(e));
		} catch (final CSSParseException e) {
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
		} catch (final ParseException e) {
			getErrorHandler().error(toCSSParseException("invalidPrio", e));
		} catch (final TokenMgrError e) {
			getErrorHandler().error(toCSSParseException(e));
		} catch (final CSSParseException e) {
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
		} catch (final ParseException e) {
			getErrorHandler().error(toCSSParseException("invalidMediaList", e));
		} catch (final TokenMgrError e) {
			getErrorHandler().error(toCSSParseException(e));
		} catch (final CSSParseException e) {
			getErrorHandler().error(e);
		}
		return ml;
	}

	private CharStream getCharStream(final InputSource source)
			throws IOException {
		if (source.getCharacterStream() != null) {
			return new ASCII_CharStream(source.getCharacterStream(), 1, 1);
		} else if (source.getByteStream() != null) {
			return new ASCII_CharStream(new InputStreamReader(
					source.getByteStream()), 1, 1);
		} else if (source.getURI() != null) {
			return new ASCII_CharStream(new InputStreamReader(new URL(
					source.getURI()).openStream()), 1, 1);
		}
		return null;
	}

	public abstract String getParserVersion();

	protected abstract String getGrammarUri();

	protected abstract void ReInit(CharStream charStream);

	protected abstract void styleSheet() throws CSSParseException,
			ParseException;

	protected abstract void styleDeclaration() throws ParseException;

	protected abstract void styleSheetRuleSingle() throws ParseException;

	protected abstract SelectorList parseSelectorsInternal()
			throws ParseException;

	protected abstract SelectorList selectorList() throws ParseException;

	protected abstract LexicalUnit expr() throws ParseException;

	protected abstract boolean prio() throws ParseException;

	protected abstract void mediaList(SACMediaListImpl ml)
			throws ParseException;

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
		} else {
			documentHandler.ignorableAtRule(s);
		}
	}

	protected void handleCharset(final String characterEncoding,
			final Locator locator) {
		final DocumentHandler documentHandler = getDocumentHandler();
		if (documentHandler instanceof DocumentHandlerExt) {
			((DocumentHandlerExt) documentHandler).charset(characterEncoding,
					locator);
		}
	}

	protected void handleImportStyle(final String uri,
			final SACMediaList media, final String defaultNamespaceURI,
			final Locator locator) {
		final DocumentHandler documentHandler = getDocumentHandler();
		if (documentHandler instanceof DocumentHandlerExt) {
			((DocumentHandlerExt) documentHandler).importStyle(uri, media,
					defaultNamespaceURI, locator);
		} else {
			documentHandler.importStyle(uri, media, defaultNamespaceURI);
		}
	}

	protected void handleStartMedia(final SACMediaList media,
			final Locator locator) {
		final DocumentHandler documentHandler = getDocumentHandler();
		if (documentHandler instanceof DocumentHandlerExt) {
			((DocumentHandlerExt) documentHandler).startMedia(media, locator);
		} else {
			documentHandler.startMedia(media);
		}
	}

	protected void handleMedium(final String medium, final Locator locator) {
		// empty default impl
	}

	protected void handleEndMedia(final SACMediaList media) {
		getDocumentHandler().endMedia(media);
	}

	protected void handleStartPage(final String name, final String pseudoPage,
			final Locator locator) {
		final DocumentHandler documentHandler = getDocumentHandler();
		if (documentHandler instanceof DocumentHandlerExt) {
			((DocumentHandlerExt) documentHandler).startPage(name, pseudoPage,
					locator);
		} else {
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
		} else {
			documentHandler.startFontFace();
		}
	}

	protected void handleEndFontFace() {
		getDocumentHandler().endFontFace();
	}

	protected void handleSelector(final Selector selector) {
		// empty default impl
	}

	protected void handleStartSelector(final SelectorList selectors,
			final Locator locator) {
		final DocumentHandler documentHandler = getDocumentHandler();
		if (documentHandler instanceof DocumentHandlerExt) {
			((DocumentHandlerExt) documentHandler).startSelector(selectors,
					locator);
		} else {
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
			((DocumentHandlerExt) documentHandler).property(name, value,
					important, locator);
		} else {
			documentHandler.property(name, value, important);
		}
	}

	protected LexicalUnit functionInternal(final LexicalUnit prev,
			final String funct, final LexicalUnit params) {

		if ("counter(".equalsIgnoreCase(funct)) {
			return LexicalUnitImpl.createCounter(prev, params);
		} else if ("counters(".equalsIgnoreCase(funct)) {
			return LexicalUnitImpl.createCounters(prev, params);
		} else if ("attr(".equalsIgnoreCase(funct)) {
			return LexicalUnitImpl.createAttr(prev, params.getStringValue());
		} else if ("rect(".equalsIgnoreCase(funct)) {
			return LexicalUnitImpl.createRect(prev, params);
		} else if ("rgb(".equalsIgnoreCase(funct)) {
			return LexicalUnitImpl.createRgbColor(prev, params);
		}
		return LexicalUnitImpl.createFunction(prev,
				funct.substring(0, funct.length() - 1), params);
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
			} else if (len == 6) {
				r = Integer.parseInt(t.image.substring(i + 0, i + 2), 16);
				g = Integer.parseInt(t.image.substring(i + 2, i + 4), 16);
				b = Integer.parseInt(t.image.substring(i + 4, i + 6), 16);
			} else {
				final String pattern = getSACParserMessages().getString(
						"invalidColor");
				throw new CSSParseException(MessageFormat.format(pattern,
						new Object[] { t }), getInputSource().getURI(),
						t.beginLine, t.beginColumn);
			}

			// Turn into an "rgb()"
			final LexicalUnit lr = LexicalUnitImpl.createNumber(null, r);
			final LexicalUnit lc1 = LexicalUnitImpl.createComma(lr);
			final LexicalUnit lg = LexicalUnitImpl.createNumber(lc1, g);
			final LexicalUnit lc2 = LexicalUnitImpl.createComma(lg);
			LexicalUnitImpl.createNumber(lc2, b);

			return LexicalUnitImpl.createRgbColor(prev, lr);
		} catch (final NumberFormatException ex) {
			final String pattern = getSACParserMessages().getString(
					"invalidColor");
			throw new CSSParseException(MessageFormat.format(pattern,
					new Object[] { t }), getInputSource().getURI(),
					t.beginLine, t.beginColumn, ex);
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
		for (; i < s.length(); i++) {
			if (NUM_CHARS.indexOf(s.charAt(i)) < 0) {
				break;
			}
		}
		return i - 1;
	}

	/**
	 * Unescapes escaped characters in the specified string, according to the <a
	 * href="http://www.w3.org/TR/CSS21/syndata.html#escaped-characters">CSS
	 * specification</a>.
	 *
	 * This could be done directly in the parser, but portions of the lexer
	 * would have to be moved to the parser, meaning that the grammar would no
	 * longer match the standard grammar specified by the W3C. This would make
	 * the parser and lexer much less maintainable.
	 */
	String unescape(final String s, final boolean unescapeDoubleQuotes) {
		final int len = s.length();
		final StringBuilder buf = new StringBuilder(len);
		int index = 0;

		while (index < len) {
			char c = s.charAt(index);
			if (c == '\\') {
				if (++index < len) {
					c = s.charAt(index);
					switch (c) {
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
					case 'a':
					case 'b':
					case 'c':
					case 'd':
					case 'e':
					case 'f':
					case 'A':
					case 'B':
					case 'C':
					case 'D':
					case 'E':
					case 'F':
						int numValue = Character.digit(c, 16);
						final int count = 0;

						while (index + 1 < len && count < 6) {
							c = s.charAt(index + 1);

							if (Character.digit(c, 16) != -1) {
								numValue = (numValue * 16)
										+ Character.digit(c, 16);
								index++;
							} else {
								if (Character.isWhitespace(c)) {
									// skip the latest white space
									index++;
								}
								break;
							}
						}
						buf.append((char) numValue);
						break;
					case '\n':
					case '\f':
						break;
					case '\r':
						if (index + 1 < len) {
							if (s.charAt(index + 1) == '\n') {
								index++;
							}
						}
						break;
					case '\"':
						if (!unescapeDoubleQuotes) {
							buf.append('\\');
						}
						buf.append(c);
						break;
					default:
						buf.append(c);
					}
				} else {
					throw new CSSParseException("invalid string " + s,
							getLocator());
				}
			} else {
				buf.append(c);
			}
			index++;
		}

		return buf.toString();
	}
}
