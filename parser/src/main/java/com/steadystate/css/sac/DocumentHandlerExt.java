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

package com.steadystate.css.sac;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.DocumentHandler;
import org.w3c.css.sac.LexicalUnit;
import org.w3c.css.sac.Locator;
import org.w3c.css.sac.SACMediaList;
import org.w3c.css.sac.SelectorList;

/**
 * Extension of the DocumentHanlder interface. This was added to support the
 * locator parameter to inform about the code position.
 */
public interface DocumentHandlerExt extends DocumentHandler {

	/**
	 * Receive notification of a charset at-rule.
	 *
	 * @param characterEncoding
	 *            the character encoding
	 * @param locator
	 *            the SAC locator
	 * @throws CSSException
	 *             Any CSS exception, possibly wrapping another exception.
	 */
	void charset(String characterEncoding, Locator locator) throws CSSException;

	/**
	 * Receive notification of a import statement in the style sheet.
	 *
	 * @param uri
	 *            The URI of the imported style sheet.
	 * @param media
	 *            The intended destination media for style information.
	 * @param defaultNamespaceURI
	 *            The default namespace URI for the imported style sheet.
	 * @param locator
	 *            the SAC locator
	 * @exception CSSException
	 *                Any CSS exception, possibly wrapping another exception.
	 */
	void importStyle(String uri, SACMediaList media,
			String defaultNamespaceURI, Locator locator) throws CSSException;

	/**
	 * Receive notification of an unknown rule t-rule not supported by this
	 * parser.
	 *
	 * @param atRule
	 *            The complete ignored at-rule.
	 * @param locator
	 *            the SAC locator
	 * @exception CSSException
	 *                Any CSS exception, possibly wrapping another exception.
	 */
	void ignorableAtRule(String atRule, Locator locator) throws CSSException;

	/**
	 * Receive notification of the beginning of a font face statement.
	 *
	 * The Parser will invoke this method at the beginning of every font face
	 * statement in the style sheet. there will be a corresponding endFontFace()
	 * event for every startFontFace() event.
	 *
	 * @param locator
	 *            the SAC locator
	 * @exception CSSException
	 *                Any CSS exception, possibly wrapping another exception.
	 */
	void startFontFace(Locator locator) throws CSSException;

	/**
	 * Receive notification of the beginning of a page statement.
	 *
	 * The Parser will invoke this method at the beginning of every page
	 * statement in the style sheet. there will be a corresponding endPage()
	 * event for every startPage() event.
	 *
	 * @param name
	 *            the name of the page (if any, null otherwise)
	 * @param pseudoPage
	 *            the pseudo page (if any, null otherwise)
	 * @param locator
	 *            the SAC locator
	 * @exception CSSException
	 *                Any CSS exception, possibly wrapping another exception.
	 */
	void startPage(String name, String pseudoPage, Locator locator)
			throws CSSException;

	/**
	 * Receive notification of the beginning of a media statement.
	 *
	 * The Parser will invoke this method at the beginning of every media
	 * statement in the style sheet. there will be a corresponding endMedia()
	 * event for every startElement() event.
	 *
	 * @param media
	 *            The intended destination media for style information.
	 * @param locator
	 *            the SAC locator
	 * @exception CSSException
	 *                Any CSS exception, possibly wrapping another exception.
	 */
	void startMedia(SACMediaList media, Locator locator) throws CSSException;

	/**
	 * Receive notification of the beginning of a rule statement.
	 *
	 * @param selectors
	 *            All intended selectors for all declarations.
	 * @param locator
	 *            the SAC locator
	 * @exception CSSException
	 *                Any CSS exception, possibly wrapping another exception.
	 */
	void startSelector(SelectorList selectors, Locator locator)
			throws CSSException;

	/**
	 * Receive notification of a declaration.
	 *
	 * @param name
	 *            the name of the property.
	 * @param value
	 *            the value of the property. All whitespace are stripped.
	 * @param important
	 *            is this property important ?
	 * @param locator
	 *            the SAC locator
	 */
	void property(String name, LexicalUnit value, boolean important,
			Locator locator);
}
