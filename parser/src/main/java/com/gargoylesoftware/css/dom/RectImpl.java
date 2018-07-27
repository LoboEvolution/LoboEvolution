/*
 * Copyright (c) 2018 Ronald Brill.
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
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.Rect;

import com.gargoylesoftware.css.parser.LexicalUnit;
import com.gargoylesoftware.css.parser.LexicalUnit.LexicalUnitType;

/**
 * Implementation of {@link Rect}.
 *
 * @author Ronald Brill
 */
public class RectImpl implements Rect, Serializable {

    private CSSPrimitiveValue top_;
    private CSSPrimitiveValue right_;
    private CSSPrimitiveValue bottom_;
    private CSSPrimitiveValue left_;

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
     * Returns the top part.
     */
    @Override
    public CSSPrimitiveValue getTop() {
        return top_;
    }

    /**
     * Sets the top part to a new value.
     * @param top the new CSSPrimitiveValue
     */
    public void setTop(final CSSPrimitiveValue top) {
        top_ = top;
    }

    /**
     * Returns the right part.
     */
    @Override
    public CSSPrimitiveValue getRight() {
        return right_;
    }

    /**
     * Sets the right part to a new value.
     * @param right the new CSSPrimitiveValue
     */
    public void setRight(final CSSPrimitiveValue right) {
        right_ = right;
    }

    /**
     * Returns the bottom part.
     */
    @Override
    public CSSPrimitiveValue getBottom() {
        return bottom_;
    }

    /**
     * Sets the bottom part to a new value.
     * @param bottom the new CSSPrimitiveValue
     */
    public void setBottom(final CSSPrimitiveValue bottom) {
        bottom_ = bottom;
    }

    /**
     * Returns the left part.
     */
    @Override
    public CSSPrimitiveValue getLeft() {
        return left_;
    }

    /**
     * Sets the left part to a new value.
     * @param left the new CSSPrimitiveValue
     */
    public void setLeft(final CSSPrimitiveValue left) {
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
