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

import java.io.Serializable;

import org.w3c.css.sac.Locator;

import com.steadystate.css.util.LangUtils;

/**
 * Implementation of {@link Locator}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David
 *         Schweinsberg</a>
 */
public class LocatorImpl implements Locator, Serializable {

	private static final long serialVersionUID = 2240824537064705530L;

	private String uri_;
	private int lineNumber_;
	private int columnNumber_;

	/**
	 * Creates new LocatorImpl
	 * 
	 * @param uri
	 *            the uri
	 * @param line
	 *            the lineNumber
	 * @param column
	 *            the columnNumber
	 */
	public LocatorImpl(final String uri, final int line, final int column) {
		uri_ = uri;
		lineNumber_ = line;
		columnNumber_ = column;
	}

	/**
	 * Return the URI for the current document event.
	 *
	 * <p>
	 * The parser must resolve the URI fully before passing it to the
	 * application.
	 * </p>
	 *
	 * @return A string containing the URI, or null if none is available.
	 */
	public String getURI() {
		return uri_;
	}

	/**
	 * @return @see #getURI()
	 */
	public String getUri() {
		return uri_;
	}

	/**
	 * Set the uri to a new value.
	 * 
	 * @see #getURI()
	 * @param uri
	 *            the new uri
	 */
	public void setUri(final String uri) {
		uri_ = uri;
	}

	/**
	 * Return the column number where the current document event ends. Note that
	 * this is the column number of the first character after the text
	 * associated with the document event. The first column in a line is
	 * position 1.
	 * 
	 * @return The column number, or -1 if none is available.
	 * @see #getLineNumber
	 */
	public int getColumnNumber() {
		return columnNumber_;
	}

	/**
	 * Set the columnNumber to a new value.
	 * 
	 * @param column
	 *            the new columnNumber
	 */
	public void setColumnNumber(final int column) {
		columnNumber_ = column;
	}

	/**
	 * Return the line number where the current document event ends. Note that
	 * this is the line position of the first character after the text
	 * associated with the document event.
	 * 
	 * @return The line number, or -1 if none is available.
	 * @see #getColumnNumber
	 */
	public int getLineNumber() {
		return lineNumber_;
	}

	/**
	 * Set the lineNumber to a new value.
	 * 
	 * @param line
	 *            the new lineNumber
	 */
	public void setLineNumber(final int line) {
		lineNumber_ = line;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Locator)) {
			return false;
		}
		final Locator l = (Locator) obj;
		return (getColumnNumber() == l.getColumnNumber())
				&& (getLineNumber() == l.getLineNumber())
				&& LangUtils.equals(getURI(), l.getURI());
	}

	@Override
	public int hashCode() {
		int hash = LangUtils.HASH_SEED;
		hash = LangUtils.hashCode(hash, columnNumber_);
		hash = LangUtils.hashCode(hash, lineNumber_);
		hash = LangUtils.hashCode(hash, uri_);
		return hash;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(getUri()).append(" (")
				.append(getLineNumber()).append(':').append(getColumnNumber())
				.append(')').toString();
	}
}
