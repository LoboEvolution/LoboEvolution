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
package com.gargoylesoftware.css.dom;

import java.io.Serializable;

import org.w3c.dom.DOMException;

import com.gargoylesoftware.css.parser.LexicalUnit;
import com.gargoylesoftware.css.parser.LexicalUnit.LexicalUnitType;

/**
 * Implementation of Rect.
 *
 * @author Ronald Brill
 */
public class RectImpl implements Serializable {

    private CSSValueImpl top_;
    private CSSValueImpl right_;
    private CSSValueImpl bottom_;
    private CSSValueImpl left_;

    /**
     * Constructor that reads the values from the given
     * chain of LexicalUnits.
     * @param lu the values
     * @throws DOMException in case of error
     */
    public RectImpl(final LexicalUnit lu) throws DOMException {
        // top
        if (lu == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Rect misses first parameter.");
        }
        top_ = new CSSValueImpl(lu, true);

        // right
        LexicalUnit next = lu.getNextLexicalUnit();  // ,
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Rect misses second parameter.");
        }

        boolean isCommaSeparated = false;
        if (next.getLexicalUnitType() == LexicalUnitType.OPERATOR_COMMA) {
            isCommaSeparated = true;
            next = next.getNextLexicalUnit();
            if (next == null) {
                throw new DOMException(DOMException.SYNTAX_ERR, "Rect misses second parameter.");
            }
        }
        right_ = new CSSValueImpl(next, true);

        // bottom
        next = next.getNextLexicalUnit();
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Rect misses third parameter.");
        }
        if (isCommaSeparated) {
            if (next.getLexicalUnitType() != LexicalUnitType.OPERATOR_COMMA) {
                throw new DOMException(DOMException.SYNTAX_ERR,
                        "All or none rect parameters must be separated by ','.");
            }
            next = next.getNextLexicalUnit();
            if (next == null) {
                throw new DOMException(DOMException.SYNTAX_ERR, "Rect misses third parameter.");
            }
        }
        else {
            if (next.getLexicalUnitType() == LexicalUnitType.OPERATOR_COMMA) {
                throw new DOMException(DOMException.SYNTAX_ERR,
                        "All or none rect parameters must be separated by ','.");
            }
        }
        bottom_ = new CSSValueImpl(next, true);

        // left
        next = next.getNextLexicalUnit();
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Rect misses fourth parameter.");
        }
        if (isCommaSeparated) {
            if (next.getLexicalUnitType() != LexicalUnitType.OPERATOR_COMMA) {
                throw new DOMException(DOMException.SYNTAX_ERR,
                        "All or none rect parameters must be separated by ','.");
            }
            next = next.getNextLexicalUnit();
            if (next == null) {
                throw new DOMException(DOMException.SYNTAX_ERR, "Rect misses fourth parameter.");
            }
        }
        else {
            if (next.getLexicalUnitType() == LexicalUnitType.OPERATOR_COMMA) {
                throw new DOMException(DOMException.SYNTAX_ERR,
                        "All or none rect parameters must be separated by ','.");
            }
        }
        left_ = new CSSValueImpl(next, true);

        // too many
        next = next.getNextLexicalUnit();
        if (next != null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Too many parameters for rect function.");
        }
    }

    /**
     * @return the top part.
     */
    public CSSValueImpl getTop() {
        return top_;
    }

    /**
     * Sets the top part to a new value.
     * @param top the new CSSPrimitiveValue
     */
    public void setTop(final CSSValueImpl top) {
        top_ = top;
    }

    /**
     * @return the right part.
     */
    public CSSValueImpl getRight() {
        return right_;
    }

    /**
     * Sets the right part to a new value.
     * @param right the new CSSPrimitiveValue
     */
    public void setRight(final CSSValueImpl right) {
        right_ = right;
    }

    /**
     * @return the bottom part.
     */
    public CSSValueImpl getBottom() {
        return bottom_;
    }

    /**
     * Sets the bottom part to a new value.
     * @param bottom the new CSSPrimitiveValue
     */
    public void setBottom(final CSSValueImpl bottom) {
        bottom_ = bottom;
    }

    /**
     * @return the left part.
     */
    public CSSValueImpl getLeft() {
        return left_;
    }

    /**
     * Sets the left part to a new value.
     * @param left the new CSSPrimitiveValue
     */
    public void setLeft(final CSSValueImpl left) {
        left_ = left;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new StringBuilder("rect(")
        .append(top_).append(", ")
        .append(right_).append(", ")
        .append(bottom_).append(", ")
        .append(left_).append(")").toString();
    }
}
