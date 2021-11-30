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
public class HSLColorImpl implements Serializable {
    private String function_;

    private CSSValueImpl hue_;
    private CSSValueImpl saturation_;
    private CSSValueImpl lightness_;
    private CSSValueImpl alpha_;
    private final boolean commaSeparated_;

    /**
     * Constructor that reads the values from the given
     * chain of LexicalUnits.
     * @param function the name of the function; rgb or rgba
     * @param lu the values
     * @throws DOMException in case of error
     */
    public HSLColorImpl(final String function, final LexicalUnit lu) throws DOMException {
        if (function == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Color space rgb or rgba is requiredc");
        }
        final String functionLC = function.toLowerCase(Locale.ROOT);
        if (!"hsl".equals(functionLC) && !"hsla".equals(functionLC)) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Color space '" + functionLC + "' not supported.");
        }
        function_ = functionLC;

        LexicalUnit next = lu;
        hue_ = new CSSValueImpl(next, true);

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

            if (LexicalUnitType.PERCENTAGE != next.getLexicalUnitType()) {
                throw new DOMException(DOMException.SYNTAX_ERR, "Saturation part has to be percentage.");
            }
            saturation_ = new CSSValueImpl(next, true);

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

            if (LexicalUnitType.PERCENTAGE != next.getLexicalUnitType()) {
                throw new DOMException(DOMException.SYNTAX_ERR, "Lightness part has to be percentage.");
            }
            lightness_ = new CSSValueImpl(next, true);

            next = next.getNextLexicalUnit();
            if (next == null) {
                return;
            }

            if (next.getLexicalUnitType() != LexicalUnitType.OPERATOR_COMMA) {
                throw new DOMException(DOMException.SYNTAX_ERR, function_ + " parameters must be separated by ','.");
            }
            next = next.getNextLexicalUnit();
            if (next == null) {
                throw new DOMException(DOMException.SYNTAX_ERR, "Missing alpha value.");
            }

            if (LexicalUnitType.INTEGER != next.getLexicalUnitType()
                    && LexicalUnitType.REAL != next.getLexicalUnitType()
                    && LexicalUnitType.PERCENTAGE != next.getLexicalUnitType()) {
                new DOMException(DOMException.SYNTAX_ERR, "Alpha part has to be numeric or percentage.");
            }
            alpha_ = new CSSValueImpl(next, true);
            next = next.getNextLexicalUnit();
            if (next != null) {
                throw new DOMException(DOMException.SYNTAX_ERR, "Too many parameters for " + function_ +  " function.");
            }
            return;
        }

        if (LexicalUnitType.PERCENTAGE != next.getLexicalUnitType()) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Saturation part has to be percentage.");
        }
        saturation_ = new CSSValueImpl(next, true);

        next = next.getNextLexicalUnit();
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, function_ + " requires at least three values.");
        }
        if (next.getLexicalUnitType() == LexicalUnitType.OPERATOR_COMMA) {
            throw new DOMException(DOMException.SYNTAX_ERR,
                    function_ + " requires consitent separators (blank or comma).");
        }

        if (LexicalUnitType.PERCENTAGE != next.getLexicalUnitType()) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Lightness part has to be percentage.");
        }
        lightness_ = new CSSValueImpl(next, true);
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

        if (LexicalUnitType.INTEGER != next.getLexicalUnitType()
                && LexicalUnitType.REAL != next.getLexicalUnitType()
                && LexicalUnitType.PERCENTAGE != next.getLexicalUnitType()) {
            new DOMException(DOMException.SYNTAX_ERR, "Alpha part has to be numeric or percentage.");
        }
        alpha_ = new CSSValueImpl(next, true);

        next = next.getNextLexicalUnit();
        if (next != null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Too many parameters for " + function_ +  " function.");
        }
    }

    /**
     * @return the red part.
     */
    public CSSValueImpl getRed() {
        return hue_;
    }

    /**
     * Sets the red part to a new value.
     * @param red the new CSSPrimitiveValue
     */
    public void setRed(final CSSValueImpl red) {
        hue_ = red;
    }

    /**
     * @return the green part.
     */
    public CSSValueImpl getGreen() {
        return saturation_;
    }

    /**
     * Sets the green part to a new value.
     * @param green the new CSSPrimitiveValue
     */
    public void setGreen(final CSSValueImpl green) {
        saturation_ = green;
    }

    /**
     * @return the blue part.
     */
    public CSSValueImpl getBlue() {
        return lightness_;
    }

    /**
     * Sets the blue part to a new value.
     * @param blue the new CSSPrimitiveValue
     */
    public void setBlue(final CSSValueImpl blue) {
        lightness_ = blue;
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
            .append(hue_);
        if (commaSeparated_) {
            sb
                .append(", ")
                .append(saturation_)
                .append(", ")
                .append(lightness_);

            if (null != alpha_) {
                sb.append(", ").append(alpha_);
            }
        }
        else {
            sb
                .append(" ")
                .append(saturation_)
                .append(" ")
                .append(lightness_);

            if (null != alpha_) {
                sb.append(" / ").append(alpha_);
            }
        }

        sb.append(")");

        return sb.toString();
    }
}
