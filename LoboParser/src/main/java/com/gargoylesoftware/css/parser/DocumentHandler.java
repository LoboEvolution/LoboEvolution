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

import com.gargoylesoftware.css.parser.media.MediaQueryList;
import com.gargoylesoftware.css.parser.selector.SelectorList;

/**
 * This is the main interface that most CSS applications implement: if the
 * application needs to be informed of basic parsing events, it implements this
 * interface and registers an instance with the CSS parser using the
 * setCSSHandler method.
 *
 * Author Ronald Brill
 *
 */
public interface DocumentHandler {

    /**
     * Receive notification of the beginning of a style sheet.
     *
     * The CSS parser will invoke this method only once, before any other
     * methods in this interface.
     *
     * @param source the input source to parse
     * @exception CSSException Any CSS exception, possibly wrapping another
     *                         exception.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     */
    void startDocument(InputSource source) throws CSSException;

    /**
     * Receive notification of the end of a document.
     *
     * The CSS parser will invoke this method only once, and it will be the
     * last method invoked during the parse. The parser shall not invoke this
     * method until it has either abandoned parsing (because of an
     * unrecoverable error) or reached the end of input.
     *
     * @param source the input source to parse
     * @exception CSSException Any CSS exception, possibly wrapping another
     *                         exception.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     */
    void endDocument(InputSource source) throws CSSException;

    /**
     * Receive notification of the end of a media statement.
     *
     * @param media The intended destination media for style information.
     * @exception CSSException Any CSS exception, possibly wrapping another
     *                         exception.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     */
    void endMedia(MediaQueryList media) throws CSSException;

    /**
     * Receive notification of the end of a media statement.
     *
     * @param name the name
     * @param pseudoPage the pseudo page (if any, null otherwise)
     * @exception CSSException Any CSS exception, possibly wrapping another
     *                         exception.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     */
    void endPage(String name, String pseudoPage) throws CSSException;

    /**
     * Receive notification of the end of a font face statement.
     *
     * @exception CSSException Any CSS exception, possibly wrapping another
     *                         exception.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     */
    void endFontFace() throws CSSException;

    /**
     * Receive notification of the end of a rule statement.
     *
     * @param selectors All intended selectors for all declarations.
     * @exception CSSException Any CSS exception, possibly wrapping another
     *                         exception.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     */
    void endSelector(SelectorList selectors) throws CSSException;

    /**
     * Receive notification of a charset at-rule.
     *
     * @param characterEncoding the character encoding
     * @param locator the locator
     * @throws com.gargoylesoftware.css.parser.CSSException Any CSS exception, possibly wrapping another
     *  exception.
     */
    void charset(String characterEncoding, Locator locator) throws CSSException;

    /**
     * Receive notification of a import statement in the style sheet.
     *
     * @param uri The URI of the imported style sheet.
     * @param media The intended destination media for style information.
     * @param defaultNamespaceURI The default namespace URI for the imported
     *  style sheet.
     * @param locator the locator
     * @exception CSSException Any CSS exception, possibly wrapping another
     *  exception.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     */
    void importStyle(String uri, MediaQueryList media,
        String defaultNamespaceURI, Locator locator) throws CSSException;

    /**
     * Receive notification of an unknown rule t-rule not supported by this
     * parser.
     *
     * @param atRule The complete ignored at-rule.
     * @param locator the locator
     * @exception CSSException Any CSS exception, possibly wrapping another
     *  exception.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     */
    void ignorableAtRule(String atRule, Locator locator) throws CSSException;

    /**
     * Receive notification of the beginning of a font face statement.
     *
     * The Parser will invoke this method at the beginning of every font face
     * statement in the style sheet. there will be a corresponding endFontFace()
     * event for every startFontFace() event.
     *
     * @param locator the locator
     * @exception CSSException Any CSS exception, possibly wrapping another
     *  exception.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     */
    void startFontFace(Locator locator) throws CSSException;

    /**
     * Receive notification of the beginning of a page statement.
     *
     * The Parser will invoke this method at the beginning of every page
     * statement in the style sheet. there will be a corresponding endPage()
     * event for every startPage() event.
     *
     * @param name the name of the page (if any, null otherwise)
     * @param pseudoPage the pseudo page (if any, null otherwise)
     * @param locator the locator
     * @exception CSSException Any CSS exception, possibly wrapping another
     *  exception.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     */
    void startPage(String name, String pseudoPage, Locator locator) throws CSSException;

    /**
     * Receive notification of the beginning of a media statement.
     *
     * The Parser will invoke this method at the beginning of every media
     * statement in the style sheet. there will be a corresponding endMedia()
     * event for every startElement() event.
     *
     * @param media The intended destination media for style information.
     * @param locator the locator
     * @exception CSSException Any CSS exception, possibly wrapping another
     *  exception.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     */
    void startMedia(MediaQueryList media, Locator locator) throws CSSException;

    /**
     * Receive notification of the beginning of a rule statement.
     *
     * @param selectors All intended selectors for all declarations.
     * @param locator the locator
     * @exception CSSException Any CSS exception, possibly wrapping another
     *  exception.
     * @throws com.gargoylesoftware.css.parser.CSSException if any.
     */
    void startSelector(SelectorList selectors, Locator locator) throws CSSException;

    /**
     * Receive notification of a declaration.
     *
     * @param name the name of the property.
     * @param value the value of the property. All whitespace are stripped.
     * @param important is this property important ?
     * @param locator the locator
     */
    void property(String name, LexicalUnit value, boolean important, Locator locator);
}
