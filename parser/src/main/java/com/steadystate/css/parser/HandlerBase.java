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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.LexicalUnit;
import org.w3c.css.sac.Locator;
import org.w3c.css.sac.SACMediaList;
import org.w3c.css.sac.SelectorList;

import com.steadystate.css.sac.DocumentHandlerExt;

/**
 * Empty implementation of the DocumentHandlerExt interface.
 */
public class HandlerBase implements DocumentHandlerExt, ErrorHandler {
	
	/** The Constant logger. */
    private static final Logger logger = LogManager.getLogger(HandlerBase.class);

    public void startDocument(final InputSource source) throws CSSException {
        // empty default impl
    }

    public void endDocument(final InputSource source) throws CSSException {
        // empty default impl
    }

    public void comment(final String text) throws CSSException {
        // empty default impl
    }

    public void ignorableAtRule(final String atRule) throws CSSException {
        // empty default impl
    }

    public void ignorableAtRule(final String atRule, final Locator locator) throws CSSException {
        // empty default impl
    }

    public void namespaceDeclaration(final String prefix, final String uri) throws CSSException {
        // empty default impl
    }

    public void importStyle(final String uri, final SACMediaList media,
            final String defaultNamespaceURI) throws CSSException {
        // empty default impl
    }

    public void importStyle(final String uri, final SACMediaList media,
            final String defaultNamespaceURI, final Locator locator) throws CSSException {
        // empty default impl
    }

    public void startMedia(final SACMediaList media) throws CSSException {
        // empty default impl
    }

    public void startMedia(final SACMediaList media, final Locator locator) throws CSSException {
        // empty default impl
    }

    public void endMedia(final SACMediaList media) throws CSSException {
        // empty default impl
    }

    public void startPage(final String name, final String pseudoPage) throws CSSException {
        // empty default impl
    }

    public void startPage(final String name, final String pseudoPage, final Locator locator) throws CSSException {
        // empty default impl
    }

    public void endPage(final String name, final String pseudoPage) throws CSSException {
        // empty default impl
    }

    public void startFontFace() throws CSSException {
        // empty default impl
    }

    public void startFontFace(final Locator locator) throws CSSException {
        // empty default impl
    }

    public void endFontFace() throws CSSException {
        // empty default impl
    }

    public void startSelector(final SelectorList selectors) throws CSSException {
        // empty default impl
    }

    public void startSelector(final SelectorList selectors, final Locator locator) throws CSSException {
        // empty default impl
    }

    public void endSelector(final SelectorList selectors) throws CSSException {
        // empty default impl
    }

    public void property(final String name, final LexicalUnit value, final boolean important) throws CSSException {
        // empty default impl
    }

    public void property(final String name, final LexicalUnit value, final boolean important, final Locator locator) {
        // empty default impl
    }

    public void charset(final String characterEncoding, final Locator locator) throws CSSException {
        // empty default impl
    }

    public void warning(final CSSParseException exception) throws CSSException {
        final StringBuilder sb = new StringBuilder();
        sb.append(exception.getURI())
            .append(" [")
            .append(exception.getLineNumber())
            .append(":")
            .append(exception.getColumnNumber())
            .append("] ")
            .append(exception.getMessage());
        logger.log(Level.ERROR,sb.toString());
    }

    public void error(final CSSParseException exception) throws CSSException {
        final StringBuilder sb = new StringBuilder();
        sb.append(exception.getURI())
            .append(" [")
            .append(exception.getLineNumber())
            .append(":")
            .append(exception.getColumnNumber())
            .append("] ")
            .append(exception.getMessage());
        logger.log(Level.ERROR,sb.toString());
    }

    public void fatalError(final CSSParseException exception) throws CSSException {
        final StringBuilder sb = new StringBuilder();
        sb.append(exception.getURI())
            .append(" [")
            .append(exception.getLineNumber())
            .append(":")
            .append(exception.getColumnNumber())
            .append("] ")
            .append(exception.getMessage());
        logger.log(Level.ERROR,sb.toString());
    }
}
