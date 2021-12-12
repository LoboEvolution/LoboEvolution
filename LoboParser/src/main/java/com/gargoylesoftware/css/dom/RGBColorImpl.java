/*
 * Copyright (c) 2019-2021 Ronald Brill.
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
import java.util.Locale;

import org.w3c.dom.DOMException;

import com.gargoylesoftware.css.parser.LexicalUnit;
import com.gargoylesoftware.css.parser.LexicalUnit.LexicalUnitType;

/**
 * Implementation of RGBColor.
 *
 * @author Ronald Brill
 */
public class RGBColorImpl implements Serializable {
    private final String function_;

    private CSSValueImpl red_;
    private CSSValueImpl green_;
    private CSSValueImpl blue_;
    private CSSValueImpl alpha_;
    private final boolean commaSeparated_;

    /**
     * Constructor that reads the values from the given
     * chain of LexicalUnits.
     * @param function the name of the function; rgb or rgba
     * @param lu the values
     * @throws DOMException in case of error
     */
    public RGBColorImpl(final String function, final LexicalUnit lu) throws DOMException {
        if (function == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Color space rgb or rgba is required.");
        }
        final String functionLC = function.toLowerCase(Locale.ROOT);
        if (!"rgb".equals(functionLC) && !"rgba".equals(functionLC)) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Color space '" + functionLC + "' not supported.");
        }
        function_ = functionLC;

        LexicalUnit next = lu;

        final boolean percentage = LexicalUnitType.PERCENTAGE == next.getLexicalUnitType();
        red_ = getPart(next);

        next = next.getNextLexicalUnit();
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, function_ + " requires at least three values.");
        }

        commaSeparated_ = next.getLexicalUnitType() == LexicalUnitType.OPERATOR_COMMA;
        if (commaSeparated_) {
            next = next.getNextLexicalUnit();
            if (next == null) {
                throw new DOMException(DOMException.SYNTAX_ERR, function_ + " requires at least three values.");
            }

            if (percentage && LexicalUnitType.PERCENTAGE != next.getLexicalUnitType()) {
                throw new DOMException(DOMException.SYNTAX_ERR, function_ + " mixing numbers and percentages.");
            }
            if (!percentage && LexicalUnitType.PERCENTAGE == next.getLexicalUnitType()) {
                throw new DOMException(DOMException.SYNTAX_ERR, function_ + " mixing numbers and percentages.");
            }
            green_ = getPart(next);
            next = next.getNextLexicalUnit();
            if (next == null) {
                throw new DOMException(DOMException.SYNTAX_ERR, function_ + " requires at least three values.");
            }

            if (next.getLexicalUnitType() != LexicalUnitType.OPERATOR_COMMA) {
                throw new DOMException(DOMException.SYNTAX_ERR, function_ + " parameters must be separated by ','.");
            }

            next = next.getNextLexicalUnit();
            if (next == null) {
                throw new DOMException(DOMException.SYNTAX_ERR, function_ + "b requires at least three values.");
            }

            if (percentage && LexicalUnitType.PERCENTAGE != next.getLexicalUnitType()) {
                throw new DOMException(DOMException.SYNTAX_ERR, function_ + " mixing numbers and percentages.");
            }
            if (!percentage && LexicalUnitType.PERCENTAGE == next.getLexicalUnitType()) {
                throw new DOMException(DOMException.SYNTAX_ERR, function_ + " mixing numbers and percentages.");
            }
            blue_ = getPart(next);

            next = next.getNextLexicalUnit();
            if (next == null) {
                return;
            }

            if (next.getLexicalUnitType() != LexicalUnitType.OPERATOR_COMMA) {
                throw new DOMException(DOMException.SYNTAX_ERR, function_ + " parameters must be separated by ','.");
            }
            next = next.getNextLexicalUnit();
            if (next == null) {
                throw new DOMException(DOMException.SYNTAX_ERR, "Missing alpha value");
            }

            alpha_ = getPart(next);
            next = next.getNextLexicalUnit();
            if (next != null) {
                throw new DOMException(DOMException.SYNTAX_ERR, "Too many parameters for " + function_ +  " function.");
            }
            return;
        }

        if (percentage && LexicalUnitType.PERCENTAGE != next.getLexicalUnitType()) {
            throw new DOMException(DOMException.SYNTAX_ERR, function_ + " mixing numbers and percentages.");
        }
        if (!percentage && LexicalUnitType.PERCENTAGE == next.getLexicalUnitType()) {
            throw new DOMException(DOMException.SYNTAX_ERR, function_ + " mixing numbers and percentages.");
        }
        green_ = getPart(next);
        next = next.getNextLexicalUnit();
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, function_ + " requires at least three values.");
        }
        if (next.getLexicalUnitType() == LexicalUnitType.OPERATOR_COMMA) {
            throw new DOMException(DOMException.SYNTAX_ERR,
                    function_ + " requires consitent separators (blank or comma).");
        }

        if (percentage && LexicalUnitType.PERCENTAGE != next.getLexicalUnitType()) {
            throw new DOMException(DOMException.SYNTAX_ERR, function_ + " mixing numbers and percentages.");
        }
        if (!percentage && LexicalUnitType.PERCENTAGE == next.getLexicalUnitType()) {
            throw new DOMException(DOMException.SYNTAX_ERR, function_ + " mixing numbers and percentages.");
        }
        blue_ = getPart(next);
        next = next.getNextLexicalUnit();
        if (next == null) {
            return;
        }

        if (next.getLexicalUnitType() != LexicalUnitType.OPERATOR_SLASH) {
            throw new DOMException(DOMException.SYNTAX_ERR, function_ + " alpha value must be separated by '/'.");
        }
        next = next.getNextLexicalUnit();
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Missing alpha value.");
        }

        alpha_ = getPart(next);
        next = next.getNextLexicalUnit();
        if (next != null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Too many parameters for " + function_ +  " function.");
        }
    }

    private static CSSValueImpl getPart(final LexicalUnit next) {
        if (LexicalUnitType.PERCENTAGE == next.getLexicalUnitType()
                || LexicalUnitType.INTEGER == next.getLexicalUnitType()
                || LexicalUnitType.REAL == next.getLexicalUnitType()) {
            return new CSSValueImpl(next, true);
        }

        throw new DOMException(DOMException.SYNTAX_ERR, "Color part has to be numeric or percentage.");
    }

    /**
     * <p>getRed.</p>
     *
     * @return the red part.
     */
    public CSSValueImpl getRed() {
        return red_;
    }

    /**
     * Sets the red part to a new value.
     *
     * @param red the new CSSPrimitiveValue
     */
    public void setRed(final CSSValueImpl red) {
        red_ = red;
    }

    /**
     * <p>getGreen.</p>
     *
     * @return the green part.
     */
    public CSSValueImpl getGreen() {
        return green_;
    }

    /**
     * Sets the green part to a new value.
     *
     * @param green the new CSSPrimitiveValue
     */
    public void setGreen(final CSSValueImpl green) {
        green_ = green;
    }

    /**
     * <p>getBlue.</p>
     *
     * @return the blue part.
     */
    public CSSValueImpl getBlue() {
        return blue_;
    }

    /**
     * Sets the blue part to a new value.
     * @param blue the new CSSPrimitiveValue
     */
    public void setBlue(final CSSValueImpl blue) {
        blue_ = blue;
    }

    /**
     * @return the alpha part.
     */
    public CSSValueImpl getAlpha() {
        return alpha_;
    }

    /**
     * Sets the alpha part to a new value.
     * @param alpha the new CSSPrimitiveValue
     */
    public void setAlpha(final CSSValueImpl alpha) {
        alpha_ = alpha;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        sb
            .append(function_)
            .append("(")
            .append(red_);
        if (commaSeparated_) {
            sb
                .append(", ")
                .append(green_)
                .append(", ")
                .append(blue_);

            if (null != alpha_) {
                sb.append(", ").append(alpha_);
            }
        }
        else {
            sb
                .append(" ")
                .append(green_)
                .append(" ")
                .append(blue_);

            if (null != alpha_) {
                sb.append(" / ").append(alpha_);
            }
        }

        sb.append(")");

        return sb.toString();
    }
}
