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
import org.w3c.dom.css.RGBColor;

import com.gargoylesoftware.css.parser.LexicalUnit;
import com.gargoylesoftware.css.parser.LexicalUnit.LexicalUnitType;

/**
 * Implementation of {@link RGBColor}.
 *
 * @author Ronald Brill
 */
public class RGBColorImpl implements RGBColor, Serializable {

    private CSSPrimitiveValue red_;
    private CSSPrimitiveValue green_;
    private CSSPrimitiveValue blue_;

    /**
     * Constructor that reads the values from the given
     * chain of LexicalUnits.
     * @param lu the values
     * @throws DOMException in case of error
     */
    public RGBColorImpl(final LexicalUnit lu) throws DOMException {
        LexicalUnit next = lu;
        red_ = new CSSValueImpl(next, true);
        next = next.getNextLexicalUnit();   // ,
        if (next != null) {
            if (next.getLexicalUnitType() != LexicalUnitType.OPERATOR_COMMA) {
                // error
                throw new DOMException(DOMException.SYNTAX_ERR,
                    "rgb parameters must be separated by ','.");
            }
            next = next.getNextLexicalUnit();
            if (next != null) {
                green_ = new CSSValueImpl(next, true);
                next = next.getNextLexicalUnit();   // ,
                if (next != null) {
                    if (next.getLexicalUnitType() != LexicalUnitType.OPERATOR_COMMA) {
                        // error
                        throw new DOMException(DOMException.SYNTAX_ERR,
                            "rgb parameters must be separated by ','.");
                    }
                    next = next.getNextLexicalUnit();
                    blue_ = new CSSValueImpl(next, true);
                    next = next.getNextLexicalUnit();
                    if (next != null) {
                        // error
                        throw new DOMException(DOMException.SYNTAX_ERR,
                            "Too many parameters for rgb function.");
                    }
                }
            }
        }
    }

    /**
     * Returns the red part.
     */
    @Override
    public CSSPrimitiveValue getRed() {
        return red_;
    }

    /**
     * Sets the red part to a new value.
     * @param red the new CSSPrimitiveValue
     */
    public void setRed(final CSSPrimitiveValue red) {
        red_ = red;
    }

    /**
     * Returns the green part.
     */
    @Override
    public CSSPrimitiveValue getGreen() {
        return green_;
    }

    /**
     * Sets the green part to a new value.
     * @param green the new CSSPrimitiveValue
     */
    public void setGreen(final CSSPrimitiveValue green) {
        green_ = green;
    }

    /**
     * Returns the blue part.
     */
    @Override
    public CSSPrimitiveValue getBlue() {
        return blue_;
    }

    /**
     * Sets the blue part to a new value.
     * @param blue the new CSSPrimitiveValue
     */
    public void setBlue(final CSSPrimitiveValue blue) {
        blue_ = blue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        sb
            .append("rgb(")
            .append(red_)
            .append(", ")
            .append(green_)
            .append(", ")
            .append(blue_)
            .append(")");
        return sb.toString();
    }
}
