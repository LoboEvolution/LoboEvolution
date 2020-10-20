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
 * Empty implementation of the DocumentHandlerExt interface.
 *
 * @author Ronald Brill
 */
public class HandlerBase implements DocumentHandler, CSSErrorHandler {

    @Override
    public void startDocument(final InputSource source) throws CSSException {
        // empty default impl
    }

    @Override
    public void endDocument(final InputSource source) throws CSSException {
        // empty default impl
    }

    @Override
    public void ignorableAtRule(final String atRule, final Locator locator) throws CSSException {
        // empty default impl
    }

    @Override
    public void importStyle(final String uri, final MediaQueryList media,
            final String defaultNamespaceURI, final Locator locator) throws CSSException {
        // empty default impl
    }

    @Override
    public void startMedia(final MediaQueryList media, final Locator locator) throws CSSException {
        // empty default impl
    }

    @Override
    public void endMedia(final MediaQueryList media) throws CSSException {
        // empty default impl
    }

    @Override
    public void startPage(final String name, final String pseudoPage, final Locator locator) throws CSSException {
        // empty default impl
    }

    @Override
    public void endPage(final String name, final String pseudoPage) throws CSSException {
        // empty default impl
    }

    @Override
    public void startFontFace(final Locator locator) throws CSSException {
        // empty default impl
    }

    @Override
    public void endFontFace() throws CSSException {
        // empty default impl
    }

    @Override
    public void startSelector(final SelectorList selectors, final Locator locator) throws CSSException {
        // empty default impl
    }

    @Override
    public void endSelector(final SelectorList selectors) throws CSSException {
        // empty default impl
    }

    @Override
    public void property(final String name, final LexicalUnit value, final boolean important, final Locator locator) {
        // empty default impl
    }

    @Override
    public void charset(final String characterEncoding, final Locator locator) throws CSSException {
        // empty default impl
    }

    @Override
    public void warning(final CSSParseException exception) throws CSSException {
        final StringBuilder sb = new StringBuilder();
        sb.append(exception.getURI())
            .append(" [")
            .append(exception.getLineNumber())
            .append(":")
            .append(exception.getColumnNumber())
            .append("] ")
            .append(exception.getMessage());
        System.err.println(sb.toString());
    }

    @Override
    public void error(final CSSParseException exception) throws CSSException {
        final StringBuilder sb = new StringBuilder();
        sb.append(exception.getURI())
            .append(" [")
            .append(exception.getLineNumber())
            .append(":")
            .append(exception.getColumnNumber())
            .append("] ")
            .append(exception.getMessage());
        System.err.println(sb.toString());
    }

    @Override
    public void fatalError(final CSSParseException exception) throws CSSException {
        final StringBuilder sb = new StringBuilder();
        sb.append(exception.getURI())
            .append(" [")
            .append(exception.getLineNumber())
            .append(":")
            .append(exception.getColumnNumber())
            .append("] ")
            .append(exception.getMessage());
        System.err.println(sb.toString());
    }
}
