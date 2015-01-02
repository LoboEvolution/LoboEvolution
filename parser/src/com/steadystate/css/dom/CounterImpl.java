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

package com.steadystate.css.dom;

import java.io.Serializable;

import org.w3c.css.sac.LexicalUnit;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.Counter;

/**
 * Implementation of {@link Counter}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class CounterImpl implements Counter, Serializable {

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
        next = next.getNextLexicalUnit();   // ','
        if (next != null) {
            if (next.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_COMMA) {
                // error
                throw new DOMException(DOMException.SYNTAX_ERR,
                    "Counter parameters must be separated by ','.");
            }
            next = next.getNextLexicalUnit();
            if (separatorSpecified && (next != null)) {
                separator_ = next.getStringValue();
                next = next.getNextLexicalUnit();   // ','
                if (next != null) {
                    if (next.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_COMMA) {
                        // error
                        throw new DOMException(DOMException.SYNTAX_ERR,
                            "Counter parameters must be separated by ','.");
                    }
                    next = next.getNextLexicalUnit();
                }
            }
            if (next != null) {
                listStyle_ = next.getStringValue();
                next = next.getNextLexicalUnit();
                if (next != null) {
                    // error
                    throw new DOMException(DOMException.SYNTAX_ERR,
                        "Too many parameters for counter function.");
                }
            }
        }
    }

    public CounterImpl() {
        super();
    }

    public String getIdentifier() {
        return identifier_;
    }

    public String getListStyle() {
        return listStyle_;
    }

    public String getSeparator() {
        return separator_;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (separator_ == null) {
            // This is a 'counter()' function
            sb.append("counter(");
        }
        else {
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
}
