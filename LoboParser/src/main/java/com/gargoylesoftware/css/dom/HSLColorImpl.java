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
 * Implementation of HSLColor.
 *
 * @author Ronald Brill
 */
public class HSLColorImpl implements Serializable {
    private final String function_;

    private CSSValueImpl hue_;
    private CSSValueImpl saturation_;
    private CSSValueImpl lightness_;
    private CSSValueImpl alpha_;
    private final boolean commaSeparated_;

    /**
     * Constructor that reads the values from the given
     * chain of LexicalUnits.
     * @param function the name of the function; hsl or hsla
     * @param lu the values
     * @throws DOMException in case of error
     */
    public HSLColorImpl(final String function, final LexicalUnit lu) throws DOMException {
        if (function == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Color space hsl or hsla is required.");
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
                throw new DOMException(DOMException.SYNTAX_ERR, "Alpha part has to be numeric or percentage.");
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
            throw new DOMException(DOMException.SYNTAX_ERR, "Alpha part has to be numeric or percentage.");
        }
        alpha_ = new CSSValueImpl(next, true);

        next = next.getNextLexicalUnit();
        if (next != null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Too many parameters for " + function_ +  " function.");
        }
    }

    /**
     * @return the hue part.
     */
    public CSSValueImpl getHue() {
        return hue_;
    }

    /**
     * Sets the hue part to a new value.
     * @param hue the new CSSPrimitiveValue
     */
    public void setHue(final CSSValueImpl hue) {
        hue_ = hue;
    }

    /**
     * @return the saturation part.
     */
    public CSSValueImpl getSaturation() {
        return saturation_;
    }

    /**
     * Sets the saturation part to a new value.
     * @param saturation the new CSSPrimitiveValue
     */
    public void setSaturation(final CSSValueImpl saturation) {
        saturation_ = saturation;
    }

    /**
     * @return the lightness part.
     */
    public CSSValueImpl getLightness() {
        return lightness_;
    }

    /**
     * Sets the lightness part to a new value.
     * @param lightness the new CSSPrimitiveValue
     */
    public void setLightness(final CSSValueImpl lightness) {
        lightness_ = lightness;
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
