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
 * Implementation of HWBColor.
 *
 * @author Ronald Brill
 */
public class HWBColorImpl extends AbstractColor {
    private CSSValueImpl hue_;
    private CSSValueImpl whiteness_;
    private CSSValueImpl blackness_;

    /**
     * Constructor that reads the values from the given
     * chain of LexicalUnits.
     * @param function the name of the function; hwb
     * @param lu the values
     * @throws DOMException in case of error
     */
    public HWBColorImpl(final String function, final LexicalUnit lu) throws DOMException {
        if (function == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Color space 'hwb' is required.");
        }
        final String functionLC = function.toLowerCase(Locale.ROOT);
        if (!"hwb".equals(functionLC)) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Color space '" + functionLC + "' not supported.");
        }

        LexicalUnit next = lu;
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "'hwb' requires at least three values.");
        }
        hue_ = getHuePart(next);

        next = next.getNextLexicalUnit();
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "'hwb' requires at least three values.");
        }

        if (LexicalUnitType.NONE != next.getLexicalUnitType()
                && LexicalUnitType.PERCENTAGE != next.getLexicalUnitType()) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Whiteness part has to be percentage.");
        }
        whiteness_ = new CSSValueImpl(next, true);

        next = next.getNextLexicalUnit();
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "'hwb' requires at least three values.");
        }

        if (LexicalUnitType.NONE != next.getLexicalUnitType()
                && LexicalUnitType.PERCENTAGE != next.getLexicalUnitType()) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Blackness part has to be percentage.");
        }
        blackness_ = new CSSValueImpl(next, true);
        next = next.getNextLexicalUnit();
        if (next == null) {
            return;
        }

        if (next.getLexicalUnitType() != LexicalUnitType.OPERATOR_SLASH) {
            throw new DOMException(DOMException.SYNTAX_ERR, "'hwb' alpha value must be separated by '/'.");
        }
        next = next.getNextLexicalUnit();
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Missing alpha value.");
        }

        getAlphaPart(next);

        next = next.getNextLexicalUnit();
        if (next != null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Too many parameters for 'hwb' function.");
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
     * @return the whiteness part.
     */
    public CSSValueImpl getWhiteness() {
        return whiteness_;
    }

    /**
     * Sets the whiteness part to a new value.
     * @param whiteness the new CSSValueImpl
     */
    public void setWhiteness(final CSSValueImpl whiteness) {
        whiteness_ = whiteness;
    }

    /**
     * @return the blackness part.
     */
    public CSSValueImpl getBlackness() {
        return blackness_;
    }

    /**
     * Sets the blackness part to a new value.
     * @param blackness the new CSSValueImpl
     */
    public void setBlackness(final CSSValueImpl blackness) {
        blackness_ = blackness;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        sb
            .append("hwb(")
            .append(hue_)
            .append(" ")
            .append(whiteness_)
            .append(" ")
            .append(blackness_);

        if (null != getAlpha()) {
            sb.append(" / ").append(getAlpha());
        }

        sb.append(")");

        return sb.toString();
    }
}
