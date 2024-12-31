/*
 * Copyright (c) 2019-2024 Ronald Brill.
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
package org.htmlunit.cssparser.dom;

import java.util.Locale;

import org.htmlunit.cssparser.parser.LexicalUnit;
import org.htmlunit.cssparser.parser.LexicalUnit.LexicalUnitType;
import org.w3c.dom.DOMException;

/**
 * Implementation of HSLColor.
 *
 * @author Ronald Brill
 */
public class HSLColorImpl extends AbstractColor {
    private final String function_;

    private CSSValueImpl hue_;
    private CSSValueImpl saturation_;
    private CSSValueImpl lightness_;
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
            throw new DOMException(DOMException.SYNTAX_ERR, "Color space 'hsl' or 'hsla' is required.");
        }
        final String functionLC = function.toLowerCase(Locale.ROOT);
        if (!"hsl".equals(functionLC) && !"hsla".equals(functionLC)) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Color space '" + functionLC + "' not supported.");
        }
        function_ = functionLC;

        LexicalUnit next = lu;
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "'" + function_ + "' requires at least three values.");
        }
        hue_ = getHuePart(next);

        next = next.getNextLexicalUnit();
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "'" + function_ + "' requires at least three values.");
        }

        commaSeparated_ = LexicalUnitType.OPERATOR_COMMA == next.getLexicalUnitType();
        if (commaSeparated_) {
            if (hue_.getLexicalUnitType() == LexicalUnitType.NONE) {
                throw new DOMException(DOMException.SYNTAX_ERR,
                        "'" + function_ + "' has to use blank as separator if none is used.");
            }

            next = next.getNextLexicalUnit();
            if (next == null) {
                throw new DOMException(DOMException.SYNTAX_ERR, "'" + function_ + "' requires at least three values.");
            }

            if (LexicalUnitType.NONE == next.getLexicalUnitType()) {
                throw new DOMException(DOMException.SYNTAX_ERR,
                        "'" + function_ + "' has to use blank as separator if none is used.");
            }
            if (LexicalUnitType.PERCENTAGE != next.getLexicalUnitType()) {
                throw new DOMException(DOMException.SYNTAX_ERR, "Saturation part has to be percentage.");
            }
            saturation_ = new CSSValueImpl(next, true);

            next = next.getNextLexicalUnit();
            if (next == null) {
                throw new DOMException(DOMException.SYNTAX_ERR, "'" + function_ + "' requires at least three values.");
            }

            if (LexicalUnitType.OPERATOR_COMMA != next.getLexicalUnitType()) {
                throw new DOMException(DOMException.SYNTAX_ERR,
                        "'" + function_ + "' parameters must be separated by ','.");
            }

            next = next.getNextLexicalUnit();
            if (next == null) {
                throw new DOMException(DOMException.SYNTAX_ERR, "'" + function_ + "' requires at least three values.");
            }

            if (LexicalUnitType.NONE == next.getLexicalUnitType()) {
                throw new DOMException(DOMException.SYNTAX_ERR,
                        "'" + function_ + "' has to use blank as separator if none is used.");
            }
            if (LexicalUnitType.PERCENTAGE != next.getLexicalUnitType()) {
                throw new DOMException(DOMException.SYNTAX_ERR, "Lightness part has to be percentage.");
            }
            lightness_ = new CSSValueImpl(next, true);

            next = next.getNextLexicalUnit();
            if (next == null) {
                return;
            }

            if (LexicalUnitType.OPERATOR_COMMA != next.getLexicalUnitType()) {
                throw new DOMException(DOMException.SYNTAX_ERR,
                        "'" + function_ + "' parameters must be separated by ','.");
            }

            next = next.getNextLexicalUnit();
            if (next == null) {
                throw new DOMException(DOMException.SYNTAX_ERR, "Missing alpha value.");
            }

            if (LexicalUnitType.NONE == next.getLexicalUnitType()) {
                throw new DOMException(DOMException.SYNTAX_ERR,
                        "'" + function_ + "' has to use blank as separator if none is used.");
            }

            getAlphaPart(next);

            next = next.getNextLexicalUnit();
            if (next != null) {
                throw new DOMException(DOMException.SYNTAX_ERR,
                        "Too many parameters for '" + function_ +  "' function.");
            }
            return;
        }

        if (LexicalUnitType.NONE != next.getLexicalUnitType()
                && LexicalUnitType.PERCENTAGE != next.getLexicalUnitType()) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Saturation part has to be percentage.");
        }
        saturation_ = new CSSValueImpl(next, true);

        next = next.getNextLexicalUnit();
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "'" + function_ + "' requires at least three values.");
        }
        if (next.getLexicalUnitType() == LexicalUnitType.OPERATOR_COMMA) {
            throw new DOMException(DOMException.SYNTAX_ERR,
                    "'" + function_ + "' requires consitent separators (blank or comma).");
        }

        if (LexicalUnitType.NONE != next.getLexicalUnitType()
                && LexicalUnitType.PERCENTAGE != next.getLexicalUnitType()) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Lightness part has to be percentage.");
        }
        lightness_ = new CSSValueImpl(next, true);
        next = next.getNextLexicalUnit();
        if (next == null) {
            return;
        }

        if (next.getLexicalUnitType() != LexicalUnitType.OPERATOR_SLASH) {
            throw new DOMException(DOMException.SYNTAX_ERR,
                    "'" + function_ + "' alpha value must be separated by '/'.");
        }
        next = next.getNextLexicalUnit();
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Missing alpha value.");
        }

        getAlphaPart(next);

        next = next.getNextLexicalUnit();
        if (next != null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Too many parameters for '" + function_ +  "' function.");
        }
    }

    private static CSSValueImpl getHuePart(final LexicalUnit next) {
        if (LexicalUnitType.DEGREE == next.getLexicalUnitType()
                || LexicalUnitType.RADIAN == next.getLexicalUnitType()
                || LexicalUnitType.GRADIAN == next.getLexicalUnitType()
                || LexicalUnitType.TURN == next.getLexicalUnitType()

                || LexicalUnitType.INTEGER == next.getLexicalUnitType()
                || LexicalUnitType.REAL == next.getLexicalUnitType()

                || LexicalUnitType.NONE == next.getLexicalUnitType()) {
            return new CSSValueImpl(next, true);
        }

        throw new DOMException(DOMException.SYNTAX_ERR, "Color hue part has to be numeric or an angle.");
    }

    /**
     * @return the hue part.
     */
    public CSSValueImpl getHue() {
        return hue_;
    }

    /**
     * Sets the hue part to a new value.
     * @param hue the new CSSValueImpl
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
     * @param saturation the new CSSValueImpl
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
     * @param lightness the new CSSValueImpl
     */
    public void setLightness(final CSSValueImpl lightness) {
        lightness_ = lightness;
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
            if (null != getAlpha()) {
                sb.append(", ").append(getAlpha());
            }
        }
        else {
            sb
                .append(" ")
                .append(saturation_)
                .append(" ")
                .append(lightness_);
            if (null != getAlpha()) {
                sb.append(" / ").append(getAlpha());
            }
        }

        sb.append(")");

        return sb.toString();
    }
}
