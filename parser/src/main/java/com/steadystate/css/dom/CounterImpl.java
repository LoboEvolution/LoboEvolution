/*
 * Copyright (C) 1999-2017 David Schweinsberg.
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

package com.steadystate.css.dom;

import java.io.Serializable;

import org.w3c.css.sac.LexicalUnit;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.Counter;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;

/**
 * Implementation of {@link Counter}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David
 *         Schweinsberg</a>
 * @author rbri
 */
public class CounterImpl implements Counter, CSSFormatable, Serializable {

	private static final long serialVersionUID = 7996279151817598904L;

	private String identifier_;
	private String listStyle_;
	private String separator_;

	public void setIdentifier(final String identifier) {
		identifier_ = identifier;
	}

	public void setListStyle(final String listStyle) {
		listStyle_ = listStyle;
	}

	public void setSeparator(final String separator) {
		separator_ = separator;
	}

	/**
	 * Creates new CounterImpl
	 */
	public CounterImpl(final boolean separatorSpecified, final LexicalUnit lu) throws DOMException {
		LexicalUnit next = lu;
		identifier_ = next.getStringValue();
		next = next.getNextLexicalUnit(); // ','
		if (next != null) {
			if (next.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_COMMA) {
				// error
				throw new DOMException(DOMException.SYNTAX_ERR, "Counter parameters must be separated by ','.");
			}
			next = next.getNextLexicalUnit();
			if (separatorSpecified && (next != null)) {
				separator_ = next.getStringValue();
				next = next.getNextLexicalUnit(); // ','
				if (next != null) {
					if (next.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_COMMA) {
						// error
						throw new DOMException(DOMException.SYNTAX_ERR, "Counter parameters must be separated by ','.");
					}
					next = next.getNextLexicalUnit();
				}
			}
			if (next != null) {
				listStyle_ = next.getStringValue();
				next = next.getNextLexicalUnit();
				if (next != null) {
					// error
					throw new DOMException(DOMException.SYNTAX_ERR, "Too many parameters for counter function.");
				}
			}
		}
	}

	public CounterImpl() {
		super();
	}

	@Override
	public String getIdentifier() {
		return identifier_;
	}

	@Override
	public String getListStyle() {
		return listStyle_;
	}

	@Override
	public String getSeparator() {
		return separator_;
	}

	/**
	 * Same as {@link #getCssText(CSSFormat)} but using the default format.
	 *
	 * @return the formated string
	 */
	public String getCssText() {
		return getCssText(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCssText(final CSSFormat format) {
		final StringBuilder sb = new StringBuilder();
		if (separator_ == null) {
			// This is a 'counter()' function
			sb.append("counter(");
		} else {
			// This is a 'counters()' function
			sb.append("counters(");
		}
		sb.append(identifier_);
		if (separator_ != null) {
			sb.append(", \"").append(separator_).append("\"");
		}
		if (listStyle_ != null) {
			sb.append(", ").append(listStyle_);
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public String toString() {
		return getCssText(null);
	}
}
