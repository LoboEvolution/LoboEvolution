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

/**
 * Implementation of RGBColor.
 *
 * @author Ronald Brill
 */
public class RGBColorImpl extends AbstractColor {
    private final String function_;

    private CSSValueImpl red_;
    private CSSValueImpl green_;
    private CSSValueImpl blue_;
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
            throw new DOMException(DOMException.SYNTAX_ERR, "Color space 'rgb' or 'rgba' is required.");
        }
        final String functionLC = function.toLowerCase(Locale.ROOT);
        if (!"rgb".equals(functionLC) && !"rgba".equals(functionLC)) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Color space '" + functionLC + "' not supported.");
        }
        function_ = functionLC;

        LexicalUnit next = lu;
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "'" + function_ + "' requires at least three values.");
        }

        getNumberPercentagePart(next, this::setRed);

        next = next.getNextLexicalUnit();
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "'" + function_ + "' requires at least three values.");
        }

        commaSeparated_ = LexicalUnitType.OPERATOR_COMMA == next.getLexicalUnitType();
        if (commaSeparated_) {
            if (LexicalUnitType.NONE == red_.getLexicalUnitType()) {
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
            getNumberPercentagePart(next, this::setGreen);

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
                throw new DOMException(DOMException.SYNTAX_ERR, function_ + "b requires at least three values.");
            }

            if (LexicalUnitType.NONE == next.getLexicalUnitType()) {
                throw new DOMException(DOMException.SYNTAX_ERR,
                        "'" + function_ + "' has to use blank as separator if none is used.");
            }
            getNumberPercentagePart(next, this::setBlue);

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
                throw new DOMException(DOMException.SYNTAX_ERR, "Missing alpha value");
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

        getNumberPercentagePart(next, this::setGreen);
        next = next.getNextLexicalUnit();
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "'" + function_ + "' requires at least three values.");
        }
        if (next.getLexicalUnitType() == LexicalUnitType.OPERATOR_COMMA) {
            throw new DOMException(DOMException.SYNTAX_ERR,
                    "'" + function_ + "' requires consitent separators (blank or comma).");
        }

        getNumberPercentagePart(next, this::setBlue);
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
     * @param red the new CSSValueImpl
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
     * @param green the new CSSValueImpl
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
     * @param blue the new CSSValueImpl
     */
    public void setBlue(final CSSValueImpl blue) {
        blue_ = blue;
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

            if (null != getAlpha()) {
                sb.append(", ").append(getAlpha());
            }

        }
        else {
            sb
                .append(" ")
                .append(green_)
                .append(" ")
                .append(blue_);

            if (null != getAlpha()) {
                sb.append(" / ").append(getAlpha());
            }
        }

        sb.append(")");

        return sb.toString();
    }
}
