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
package com.gargoylesoftware.css.parser;

/**
 * Lexical unit of css values.
 *
 * @author Ronald Brill
 */
public interface LexicalUnit {

    /**
     * Enum for the various unit types.
     */
    enum LexicalUnitType {
        /** OPERATOR_COMMA. */
        OPERATOR_COMMA,
        /** OPERATOR_PLUS. */
        OPERATOR_PLUS,
        /** OPERATOR_MINUS. */
        OPERATOR_MINUS,
        /** OPERATOR_MULTIPLY. */
        OPERATOR_MULTIPLY,
        /** OPERATOR_SLASH. */
        OPERATOR_SLASH,
        /** OPERATOR_MOD. */
        OPERATOR_MOD,
        /** OPERATOR_EXP. */
        OPERATOR_EXP,
        /** OPERATOR_LT. */
        OPERATOR_LT,
        /** OPERATOR_GT. */
        OPERATOR_GT,
        /** OPERATOR_LE. */
        OPERATOR_LE,
        /** OPERATOR_GE. */
        OPERATOR_GE,
        /** OPERATOR_TILDE. */
        OPERATOR_TILDE,
        /** INHERIT. */
        INHERIT,
        /** INTEGER. */
        INTEGER,
        /** REAL. */
        REAL,
        /** EM. */
        EM,
        /** REM. */
        REM,
        /** EX. */
        EX,
        /** CH. */
        CH,
        /** VW. */
        VW,
        /** VH. */
        VH,
        /** VMIN. */
        VMIN,
        /** VMAX. */
        VMAX,
        /** PIXEL. */
        PIXEL,
        /** INCH. */
        INCH,
        /** CENTIMETER. */
        CENTIMETER,
        /** MILLIMETER. */
        MILLIMETER,
        /** POINT. */
        POINT,
        /** PICA. */
        PICA,
        /** QUATER. */
        QUATER,
        /** PERCENTAGE. */
        PERCENTAGE,
        /** URI. */
        URI,
        /** COUNTER_FUNCTION. */
        COUNTER_FUNCTION,
        /** COUNTERS_FUNCTION. */
        COUNTERS_FUNCTION,
        /** RGBCOLOR. */
        RGBCOLOR,
        /** HSLCOLOR. */
        HSLCOLOR,
        /** DEGREE. */
        DEGREE,
        /** GRADIAN. */
        GRADIAN,
        /** RADIAN. */
        RADIAN,
        /** TURN. */
        TURN,
        /** MILLISECOND. */
        MILLISECOND,
        /** SECOND. */
        SECOND,
        /** HERTZ. */
        HERTZ,
        /** KILOHERTZ. */
        KILOHERTZ,
        /** IDENT. */
        IDENT,
        /** STRING_VALUE. */
        STRING_VALUE,
        /** ATTR. */
        ATTR,
        /** RECT_FUNCTION. */
        RECT_FUNCTION,
        /** UNICODERANGE. */
        UNICODERANGE,
        /** FUNCTION. */
        FUNCTION,
        /** FUNCTION_CALC. */
        FUNCTION_CALC,
        /** DIMENSION. */
        DIMENSION
    }

    /**
     * <p>getLexicalUnitType.</p>
     *
     * @return an integer indicating the type of <code>LexicalUnit</code>.
     */
    LexicalUnitType getLexicalUnitType();

    /**
     * <p>getNextLexicalUnit.</p>
     *
     * @return the next value or <code>null</code> if any.
     */
    LexicalUnit getNextLexicalUnit();

    /**
     * <p>getPreviousLexicalUnit.</p>
     *
     * @return the previous value or <code>null</code> if any.
     */
    LexicalUnit getPreviousLexicalUnit();

    /**
     * <p>getIntegerValue.</p>
     *
     * @return the integer value.
     */
    int getIntegerValue();

    /**
     * <p>getDoubleValue.</p>
     *
     * @return the double value.
     */
    double getDoubleValue();

    /**
     * <p>getDimensionUnitText.</p>
     *
     * @return the string representation of the unit.
     */
    String getDimensionUnitText();

    /**
     * <p>getFunctionName.</p>
     *
     * @return the name of the function.
     */
    String getFunctionName();

    /**
     * <p>getParameters.</p>
     *
     * @return the function parameters including operators (like the comma).
     */
    LexicalUnit getParameters();

    /**
     * <p>getStringValue.</p>
     *
     * @return the string value.
     */
    String getStringValue();

    /**
     * <p>getSubValues.</p>
     *
     * @return a list of values inside the sub expression.
     */
    LexicalUnit getSubValues();

    /**
     * <p>getLocator.</p>
     *
     * @return the locator
     */
    Locator getLocator();

    /**
     * <p>setLocator.</p>
     *
     * @param locator the new locator
     */
    void setLocator(Locator locator);
}
