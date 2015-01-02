/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2014 David Schweinsberg.  All rights reserved.
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
        System.err.println(sb.toString());
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
        System.err.println(sb.toString());
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
        System.err.println(sb.toString());
    }
}
