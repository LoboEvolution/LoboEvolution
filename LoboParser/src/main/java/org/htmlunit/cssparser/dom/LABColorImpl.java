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
 * Implementation of LABColor and OKLABColor.
 *
 * @author Ronald Brill
 */
public class LABColorImpl extends AbstractColor {
    private final String function_;

    private CSSValueImpl lightness_;
    private CSSValueImpl aDistance_;
    private CSSValueImpl bDistance_;

    /**
     * Constructor that reads the values from the given
     * chain of LexicalUnits.
     * @param function the name of the function; lab or oklab
     * @param lu the values
     * @throws DOMException in case of error
     */
    public LABColorImpl(final String function, final LexicalUnit lu) throws DOMException {
        if (function == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Color space 'lab' or 'oklab' is required.");
        }
        final String functionLC = function.toLowerCase(Locale.ROOT);
        if (!"lab".equals(functionLC) && !"oklab".equals(functionLC)) {
            throw new DOMException(DOMException.SYNTAX_ERR, "Color space '" + functionLC + "' not supported.");
        }
        function_ = functionLC;

        LexicalUnit next = lu;
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "'" + function_ + "' requires at least three values.");
        }

        getNumberPercentagePart(next, this::setLightness);

        next = next.getNextLexicalUnit();
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "'" + function_ + "' requires at least three values.");
        }

        getNumberPercentagePart(next, this::setA);
        next = next.getNextLexicalUnit();
        if (next == null) {
            throw new DOMException(DOMException.SYNTAX_ERR, "'" + function_ + "' requires at least three values.");
        }

        getNumberPercentagePart(next, this::setB);
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
            throw new DOMException(DOMException.SYNTAX_ERR, "Too many parameters for '" + function_ + "' function.");
        }
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
     * <p>getA.</p>
     *
     * @return the a part.
     */
    public CSSValueImpl getA() {
        return aDistance_;
    }

    /**
     * Sets the a part to a new value.
     * @param a the new CSSValueImpl
     */
    public void setA(final CSSValueImpl a) {
        aDistance_ = a;
    }

    /**
     * <p>getB.</p>
     *
     * @return the b part.
     */
    public CSSValueImpl getB() {
        return bDistance_;
    }

    /**
     * Sets the b part to a new value.
     * @param b the new CSSValueImpl
     */
    public void setB(final CSSValueImpl b) {
        bDistance_ = b;
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
            .append(lightness_)
            .append(" ")
            .append(aDistance_)
            .append(" ")
            .append(bDistance_);

        if (null != getAlpha()) {
            sb.append(" / ").append(getAlpha());
        }

        sb.append(")");

        return sb.toString();
    }
}
