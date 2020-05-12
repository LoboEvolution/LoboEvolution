/*
 * Copyright (c) 2019 Ronald Brill.
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

import com.gargoylesoftware.css.parser.selector.SelectorList;

/**
 * Basic interface of the CSS parser.
 *
 * @author Ronald Brill
 * @version $Id: $Id
 */
public interface CSSParser {

    /**
     * Allow an application to register a document event handler.
     *
     * <p>If the application does not register a document handler, all
     * document events reported by the CSS parser will be silently
     * ignored (this is the default behaviour implemented by
     * HandlerBase).</p>
     *
     * <p>Applications may register a new or different handler in the
     * middle of a parse, and the CSS parser must begin using the new
     * handler immediately.</p>
     *
     * @param handler The document handler.
     * @see DocumentHandler
     */
    void setDocumentHandler(DocumentHandler handler);

    /**
     * Allow an application to register an error event handler.
     *
     * <p>If the application does not register an error event handler,
     * all error events reported by the CSS parser will be silently
     * ignored, except for fatalError, which will throw a CSSException
     * (this is the default behaviour implemented by HandlerBase).</p>
     *
     * <p>Applications may register a new or different handler in the
     * middle of a parse, and the CSS parser must begin using the new
     * handler immediately.</p>
     *
     * @param handler The error handler.
     * @see CSSErrorHandler
     * @see CSSException
     */
    void setErrorHandler(CSSErrorHandler handler);

    /**
     * Parse a CSS document.
     *
     * <p>The application can use this method to instruct the CSS parser
     * to begin parsing an CSS document from any valid input
     * source (a character stream, a byte stream, or a URI).</p>
     *
     * <p>Applications may not invoke this method while a parse is in
     * progress (they should create a new Parser instead for each
     * additional CSS document).  Once a parse is complete, an
     * application may reuse the same Parser object, possibly with a
     * different input source.</p>
     *
     * @param source The input source for the top-level of the
     *        CSS document.
     * @exception CSSException Any CSS exception, possibly
     *            wrapping another exception.
     * @exception java.io.IOException An IO exception from the parser,
     *            possibly from a byte stream or character stream
     *            supplied by the application.
     * @see InputSource
     * @see #setDocumentHandler
     * @see #setErrorHandler
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     * @throws java.io.IOException if any.
     */
    void parseStyleSheet(InputSource source) throws CSSException, IOException;

    /**
     * Parse a CSS style declaration (without '{' and '}').
     *
     * @param source source to be parsed
     * @exception CSSException Any CSS exception, possibly
     *            wrapping another exception.
     * @exception java.io.IOException An IO exception from the parser,
     *            possibly from a byte stream or character stream
     *            supplied by the application.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     * @throws java.io.IOException if any.
     */
    void parseStyleDeclaration(InputSource source) throws CSSException, IOException;

    /**
     * Parse a CSS rule.
     *
     * @param source source to be parsed
     * @exception CSSException Any CSS exception, possibly
     *            wrapping another exception.
     * @exception java.io.IOException An IO exception from the parser,
     *            possibly from a byte stream or character stream
     *            supplied by the application.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     * @throws java.io.IOException if any.
     */
    void parseRule(InputSource source) throws CSSException, IOException;

    /**
     * <p>getParserVersion.</p>
     *
     * @return a string about which CSS language is supported by this
     * parser. For CSS Level 1, it returns "http://www.w3.org/TR/REC-CSS1", for
     * CSS Level 2, it returns "http://www.w3.org/TR/REC-CSS2". Note that a
     * "CSSx" parser can return lexical unit other than those allowed by CSS
     * Level x but this usage is not recommended.
     */
    String getParserVersion();

    /**
     * Parse a comma separated list of selectors.
     *
     * @param source source to be parsed
     * @return a selector list
     * @exception CSSException Any CSS exception, possibly
     *            wrapping another exception.
     * @exception java.io.IOException An IO exception from the parser,
     *            possibly from a byte stream or character stream
     *            supplied by the application.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     * @throws java.io.IOException if any.
     */
    SelectorList parseSelectors(InputSource source) throws CSSException, IOException;

    /**
     * Parse a CSS property value.
     *
     * @param source source to be parsed
     * @return a lexical unit
     * @exception CSSException Any CSS exception, possibly
     *            wrapping another exception.
     * @exception java.io.IOException An IO exception from the parser,
     *            possibly from a byte stream or character stream
     *            supplied by the application.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     * @throws java.io.IOException if any.
     */
    LexicalUnit parsePropertyValue(InputSource source) throws CSSException, IOException;

    /**
     * Parse a CSS priority value (e.g. "!important").
     *
     * @param source source to be parsed
     * @return true or flase
     * @exception CSSException Any CSS exception, possibly
     *            wrapping another exception.
     * @exception java.io.IOException An IO exception from the parser,
     *            possibly from a byte stream or character stream
     *            supplied by the application.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     * @throws java.io.IOException if any.
     */
    boolean parsePriority(InputSource source) throws CSSException, IOException;

    /**
     * <p>setIeStarHackAccepted.</p>
     *
     * @param accepted trur if the parser should accept ie star hack
     */
    void setIeStarHackAccepted(boolean accepted);

    /**
     * <p>isIeStarHackAccepted.</p>
     *
     * @return accepted trur if the parser should accept ie star hack
     */
    boolean isIeStarHackAccepted();
}
