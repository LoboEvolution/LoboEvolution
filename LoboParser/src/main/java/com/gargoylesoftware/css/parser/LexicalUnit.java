/*
 * Copyright (c) 2019-2020 Ronald Brill.
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
 * @author Ronald brill
 */
public interface LexicalUnit {

    /**
     * Enum for the various unit types.
     */
    enum LexicalUnitType {
        OPERATOR_COMMA,
        OPERATOR_PLUS,
        OPERATOR_MINUS,
        OPERATOR_MULTIPLY,
        OPERATOR_SLASH,
        OPERATOR_MOD,
        OPERATOR_EXP,
        OPERATOR_LT,
        OPERATOR_GT,
        OPERATOR_LE,
        OPERATOR_GE,
        OPERATOR_TILDE,
        INHERIT,
        INTEGER,
        REAL,
        EM,
        REM,
        EX,
        CH,
        VW,
        VH,
        VMIN,
        VMAX,
        PIXEL,
        INCH,
        CENTIMETER,
        MILLIMETER,
        POINT,
        PICA,
        PERCENTAGE,
        URI,
        COUNTER_FUNCTION,
        COUNTERS_FUNCTION,
        RGBCOLOR,
        DEGREE,
        GRADIAN,
        RADIAN,
        MILLISECOND,
        SECOND,
        HERTZ,
        KILOHERTZ,
        IDENT,
        STRING_VALUE,
        ATTR,
        RECT_FUNCTION,
        UNICODERANGE,
        SUB_EXPRESSION,
        FUNCTION,
        DIMENSION
    }

    /**
     * @return an integer indicating the type of <code>LexicalUnit</code>.
     */
    LexicalUnitType getLexicalUnitType();

    /**
     * @return the next value or <code>null</code> if any.
     */
    LexicalUnit getNextLexicalUnit();

    /**
     * @return the previous value or <code>null</code> if any.
     */
    LexicalUnit getPreviousLexicalUnit();

    /**
     * @return the integer value.
     */
    int getIntegerValue();

    /**
     * @return the double value.
     */
    double getDoubleValue();

    /**
     * @return the string representation of the unit.
     */
    String getDimensionUnitText();

    /**
     * @return the name of the function.
     */
    String getFunctionName();

    /**
     * @return the function parameters including operators (like the comma).
     */
    LexicalUnit getParameters();

    /**
     * @return the string value.
     */
    String getStringValue();

    /**
     * @return a list of values inside the sub expression.
     */
    LexicalUnit getSubValues();

    /**
     * @return the locator
     */
    Locator getLocator();

    /**
     * @param locator the new locator
     */
    void setLocator(Locator locator);
}
