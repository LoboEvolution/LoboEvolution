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

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Implementation of {@link LexicalUnit}.
 *
 * @author Ronald Brill
 */
public class LexicalUnitImpl extends AbstractLocatable implements LexicalUnit, Serializable {

    private LexicalUnitType lexicalUnitType_;
    private LexicalUnit nextLexicalUnit_;
    private LexicalUnit previousLexicalUnit_;
    private double doubleValue_;
    private String dimension_;
    private String functionName_;
    private LexicalUnit parameters_;
    private String stringValue_;

    /** cache */
    private transient String toString_;

    /**
     * @param next next LexicalUnit
     */
    public void setNextLexicalUnit(final LexicalUnit next) {
        nextLexicalUnit_ = next;
    }

    /**
     * @param prev previous LexicalUnit
     */
    public void setPreviousLexicalUnit(final LexicalUnit prev) {
        previousLexicalUnit_ = prev;
    }

    /**
     * @param doubleVal the double value
     */
    public void setDoubleValue(final double doubleVal) {
        doubleValue_ = doubleVal;
        toString_ = null;
    }

    /**
     * @return the dimension
     */
    public String getDimension() {
        return dimension_;
    }

    /**
     * @param dimension the new dimension
     */
    public void setDimension(final String dimension) {
        dimension_ = dimension;
        toString_ = null;
    }

    /**
     * @param function the function name
     */
    public void setFunctionName(final String function) {
        functionName_ = function;
        toString_ = null;
    }

    /**
     * @param params the parameter LexicalUnit
     */
    public void setParameters(final LexicalUnit params) {
        parameters_ = params;
        toString_ = null;
    }

    /**
     * @param stringVal the string value
     */
    public void setStringValue(final String stringVal) {
        stringValue_ = stringVal;
        toString_ = null;
    }

    /**
     * Ctor.
     *
     * @param previous the previous LexicalUnit
     * @param type the LexicalUnitType
     */
    public LexicalUnitImpl(final LexicalUnit previous, final LexicalUnitType type) {
        lexicalUnitType_ = type;
        previousLexicalUnit_ = previous;
        if (previousLexicalUnit_ != null) {
            ((LexicalUnitImpl) previousLexicalUnit_).nextLexicalUnit_ = this;
        }
    }

    /**
     * Integer.
     *
     * @param previous the previous LexicalUnit
     * @param value the int value
     */
    protected LexicalUnitImpl(final LexicalUnit previous, final int value) {
        this(previous, LexicalUnitType.INTEGER);
        doubleValue_ = value;
    }

    /**
     * Dimension.
     *
     * @param previous the previous LexicalUnit
     * @param type the LexicalUnitType
     * @param value the double value
     */
    protected LexicalUnitImpl(final LexicalUnit previous, final LexicalUnitType type, final double value) {
        this(previous, type);
        doubleValue_ = value;
    }

    /**
     * Unknown dimension.
     *
     * @param previous the previous LexicalUnit
     * @param type the LexicalUnitType
     * @param dimension the dimension
     * @param value the double value
     */
    protected LexicalUnitImpl(
            final LexicalUnit previous,
            final LexicalUnitType type,
            final String dimension,
            final double value) {
        this(previous, type);
        dimension_ = dimension;
        doubleValue_ = value;
    }

    /**
     * String.
     *
     * @param previous the previous LexicalUnit
     * @param type the LexicalUnitType
     * @param value the string value
     */
    public LexicalUnitImpl(final LexicalUnit previous, final LexicalUnitType type, final String value) {
        this(previous, type);
        stringValue_ = value;
    }

    /**
     * Function.
     * @param previous the previous LexicalUnit
     * @param type the LexicalUnitType
     * @param name the name
     * @param params the parameter LexicalUnit
     */
    protected LexicalUnitImpl(
            final LexicalUnit previous,
            final LexicalUnitType type,
            final String name,
            final LexicalUnit params) {
        this(previous, type);
        functionName_ = name;
        parameters_ = params;
    }

    /**
     * Function.
     * @param previous the previous LexicalUnit
     * @param type the LexicalUnitType
     * @param name the name
     * @param stringValue the string value
     */
    protected LexicalUnitImpl(final LexicalUnit previous, final LexicalUnitType type, final String name,
            final String stringValue) {
        this(previous, type);
        functionName_ = name;
        stringValue_ = stringValue;
    }

    @Override
    public LexicalUnitType getLexicalUnitType() {
        return lexicalUnitType_;
    }

    @Override
    public LexicalUnit getNextLexicalUnit() {
        return nextLexicalUnit_;
    }

    @Override
    public LexicalUnit getPreviousLexicalUnit() {
        return previousLexicalUnit_;
    }

    @Override
    public int getIntegerValue() {
        return (int) doubleValue_;
    }

    @Override
    public double getDoubleValue() {
        return doubleValue_;
    }

    @Override
    public String getDimensionUnitText() {
        switch (lexicalUnitType_) {
            case EM:
                return "em";
            case REM:
                return "rem";
            case EX:
                return "ex";
            case CH:
                return "ch";
            case VW:
                return "vw";
            case VH:
                return "vh";
            case VMIN:
                return "vmin";
            case VMAX:
                return "vmax";
            case PIXEL:
                return "px";
            case INCH:
                return "in";
            case CENTIMETER:
                return "cm";
            case MILLIMETER:
                return "mm";
            case POINT:
                return "pt";
            case PICA:
                return "pc";
            case PERCENTAGE:
                return "%";
            case DEGREE:
                return "deg";
            case GRADIAN:
                return "grad";
            case RADIAN:
                return "rad";
            case MILLISECOND:
                return "ms";
            case SECOND:
                return "s";
            case HERTZ:
                return "Hz";
            case KILOHERTZ:
                return "kHz";
            case DIMENSION:
                return dimension_;
            default:
                return "";
        }
    }

    @Override
    public String getFunctionName() {
        return functionName_;
    }

    @Override
    public LexicalUnit getParameters() {
        return parameters_;
    }

    @Override
    public String getStringValue() {
        return stringValue_;
    }

    @Override
    public LexicalUnit getSubValues() {
        return parameters_;
    }

    /**
     * @return the current css text
     */
    public String getCssText() {
        if (null != toString_) {
            return toString_;
        }

        final StringBuilder sb = new StringBuilder();
        switch (lexicalUnitType_) {
            case OPERATOR_COMMA:
                sb.append(",");
                break;
            case OPERATOR_PLUS:
                sb.append("+");
                break;
            case OPERATOR_MINUS:
                sb.append("-");
                break;
            case OPERATOR_MULTIPLY:
                sb.append("*");
                break;
            case OPERATOR_SLASH:
                sb.append("/");
                break;
            case OPERATOR_MOD:
                sb.append("%");
                break;
            case OPERATOR_EXP:
                sb.append("^");
                break;
            case OPERATOR_LT:
                sb.append("<");
                break;
            case OPERATOR_GT:
                sb.append(">");
                break;
            case OPERATOR_LE:
                sb.append("<=");
                break;
            case OPERATOR_GE:
                sb.append(">=");
                break;
            case OPERATOR_TILDE:
                sb.append("~");
                break;
            case INHERIT:
                sb.append("inherit");
                break;
            case INTEGER:
                sb.append(String.valueOf(getIntegerValue()));
                break;
            case REAL:
                sb.append(getTrimedDoubleValue());
                break;
            case EM:
            case REM:
            case EX:
            case CH:
            case VW:
            case VH:
            case VMIN:
            case VMAX:
            case PIXEL:
            case INCH:
            case CENTIMETER:
            case MILLIMETER:
            case POINT:
            case PICA:
            case PERCENTAGE:
            case DEGREE:
            case GRADIAN:
            case RADIAN:
            case MILLISECOND:
            case SECOND:
            case HERTZ:
            case KILOHERTZ:
            case DIMENSION:
                sb.append(getTrimedDoubleValue());
                final String dimUnitText = getDimensionUnitText();
                if (null != dimUnitText) {
                    sb.append(dimUnitText);
                }
                break;
            case URI:
                sb.append("url(").append(getStringValue()).append(")");
                break;
            case COUNTER_FUNCTION:
                sb.append("counter(");
                appendParams(sb);
                sb.append(")");
                break;
            case COUNTERS_FUNCTION:
                sb.append("counters(");
                appendParams(sb);
                sb.append(")");
                break;
            case RGBCOLOR:
                sb.append("rgb(");
                appendParams(sb);
                sb.append(")");
                break;
            case IDENT:
                sb.append(getStringValue());
                break;
            case STRING_VALUE:
                sb.append("\"");

                String value = getStringValue();
                // replace line breaks
                value = value.replace("\n", "\\A ").replace("\r", "\\D ");
                sb.append(value);

                sb.append("\"");
                break;
            case ATTR:
                sb.append("attr(")
                    .append(getStringValue())
                    .append(")");
                break;
            case RECT_FUNCTION:
                sb.append("rect(");
                appendParams(sb);
                sb.append(")");
                break;
            case UNICODERANGE:
                final String range = getStringValue();
                if (null != range) {
                    sb.append(range);
                }
                break;
            case SUB_EXPRESSION:
                final String subExpression = getStringValue();
                if (null != subExpression) {
                    sb.append(subExpression);
                }
                break;
            case FUNCTION:
                final String functName = getFunctionName();
                if (null != functName) {
                    sb.append(functName);
                }
                sb.append('(');
                appendParams(sb);
                sb.append(")");
                break;
            default:
                break;
        }
        toString_ = sb.toString();
        return toString_;
    }

    @Override
    public String toString() {
        return getCssText();
    }

    /**
     * @return a string helping to debug
     */
    public String toDebugString() {
        final StringBuilder sb = new StringBuilder();
        switch (lexicalUnitType_) {
            case OPERATOR_COMMA:
                sb.append("OPERATOR_COMMA");
                break;
            case OPERATOR_PLUS:
                sb.append("OPERATOR_PLUS");
                break;
            case OPERATOR_MINUS:
                sb.append("OPERATOR_MINUS");
                break;
            case OPERATOR_MULTIPLY:
                sb.append("OPERATOR_MULTIPLY");
                break;
            case OPERATOR_SLASH:
                sb.append("OPERATOR_SLASH");
                break;
            case OPERATOR_MOD:
                sb.append("OPERATOR_MOD");
                break;
            case OPERATOR_EXP:
                sb.append("OPERATOR_EXP");
                break;
            case OPERATOR_LT:
                sb.append("OPERATOR_LT");
                break;
            case OPERATOR_GT:
                sb.append("OPERATOR_GT");
                break;
            case OPERATOR_LE:
                sb.append("OPERATOR_LE");
                break;
            case OPERATOR_GE:
                sb.append("OPERATOR_GE");
                break;
            case OPERATOR_TILDE:
                sb.append("OPERATOR_TILDE");
                break;
            case INHERIT:
                sb.append("INHERIT");
                break;
            case INTEGER:
                sb.append("INTEGER(")
                    .append(String.valueOf(getIntegerValue()))
                    .append(")");
                break;
            case REAL:
                sb.append("REAL(")
                    .append(getTrimedDoubleValue())
                    .append(")");
                break;
            case EM:
                sb.append("EM(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case REM:
                sb.append("REM(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case EX:
                sb.append("EX(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case CH:
                sb.append("CH(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case VW:
                sb.append("VW(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case VH:
                sb.append("VH(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case VMIN:
                sb.append("VMIN(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case VMAX:
                sb.append("VMAX(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case PIXEL:
                sb.append("PIXEL(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case INCH:
                sb.append("INCH(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case CENTIMETER:
                sb.append("CENTIMETER(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case MILLIMETER:
                sb.append("MILLIMETER(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case POINT:
                sb.append("POINT(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case PICA:
                sb.append("PICA(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case PERCENTAGE:
                sb.append("PERCENTAGE(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case DEGREE:
                sb.append("DEGREE(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case GRADIAN:
                sb.append("GRADIAN(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case RADIAN:
                sb.append("RADIAN(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case MILLISECOND:
                sb.append("MILLISECOND(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SECOND:
                sb.append("SECOND(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case HERTZ:
                sb.append("HERTZ(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case KILOHERTZ:
                sb.append("KILOHERTZ(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case DIMENSION:
                sb.append("DIMENSION(")
                    .append(getTrimedDoubleValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case URI:
                sb.append("URI(url(")
                    .append(getStringValue())
                    .append("))");
                break;
            case COUNTER_FUNCTION:
                sb.append("COUNTER_FUNCTION(counter(");
                appendParams(sb);
                sb.append("))");
                break;
            case COUNTERS_FUNCTION:
                sb.append("COUNTERS_FUNCTION(counters(");
                appendParams(sb);
                sb.append("))");
                break;
            case RGBCOLOR:
                sb.append("RGBCOLOR(rgb(");
                appendParams(sb);
                sb.append("))");
                break;
            case IDENT:
                sb.append("IDENT(")
                    .append(getStringValue())
                    .append(")");
                break;
            case STRING_VALUE:
                sb.append("STRING_VALUE(\"")
                    .append(getStringValue())
                    .append("\")");
                break;
            case ATTR:
                sb.append("ATTR(attr(")
                    .append(getStringValue())
                    .append("))");
                break;
            case RECT_FUNCTION:
                sb.append("RECT_FUNCTION(rect(");
                appendParams(sb);
                sb.append("))");
                break;
            case UNICODERANGE:
                sb.append("UNICODERANGE(")
                    .append(getStringValue())
                    .append(")");
                break;
            case SUB_EXPRESSION:
                sb.append("SUB_EXPRESSION(")
                    .append(getStringValue())
                    .append(")");
                break;
            case FUNCTION:
                sb.append("FUNCTION(")
                    .append(getFunctionName())
                    .append("(");
                LexicalUnit l = parameters_;
                while (l != null) {
                    sb.append(l.toString());
                    l = l.getNextLexicalUnit();
                }
                sb.append("))");
                break;
            default:
                break;
        }
        return sb.toString();
    }

    private void appendParams(final StringBuilder sb) {
        LexicalUnit l = parameters_;
        if (l != null) {
            sb.append(l.toString());
            l = l.getNextLexicalUnit();
            while (l != null) {
                if (l.getLexicalUnitType() != LexicalUnitType.OPERATOR_COMMA) {
                    sb.append(" ");
                }
                sb.append(l.toString());
                l = l.getNextLexicalUnit();
            }
        }
    }

    private String getTrimedDoubleValue() {
        final double f = getDoubleValue();
        final int i = (int) f;

        if (f - i == 0) {
            return Integer.toString((int) f);
        }

        final DecimalFormat decimalFormat = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        decimalFormat.setGroupingUsed(false);
        decimalFormat.setMaximumFractionDigits(4);
        return decimalFormat.format(f);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param i the integer value
     * @return lexical unit with type integer
     */
    public static LexicalUnit createNumber(final LexicalUnit prev, final int i) {
        return new LexicalUnitImpl(prev, LexicalUnitType.INTEGER, i);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type real
     */
    public static LexicalUnit createNumber(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.REAL, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type percent
     */
    public static LexicalUnit createPercentage(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.PERCENTAGE, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type pixel
     */
    public static LexicalUnit createPixel(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.PIXEL, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type centimeter
     */
    public static LexicalUnit createCentimeter(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.CENTIMETER, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type millimeter
     */
    public static LexicalUnit createMillimeter(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.MILLIMETER, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type inch
     */
    public static LexicalUnit createInch(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.INCH, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type point
     */
    public static LexicalUnit createPoint(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.POINT, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type pica
     */
    public static LexicalUnit createPica(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.PICA, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type em
     */
    public static LexicalUnit createEm(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.EM, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type rem
     */
    public static LexicalUnit createRem(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.REM, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type ex
     */
    public static LexicalUnit createEx(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.EX, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type ch
     */
    public static LexicalUnit createCh(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.CH, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type vw
     */
    public static LexicalUnit createVw(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.VW, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type vh
     */
    public static LexicalUnit createVh(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.VH, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type vmin
     */
    public static LexicalUnit createVMin(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.VMIN, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type vmax
     */
    public static LexicalUnit createVMax(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.VMAX, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type degree
     */
    public static LexicalUnit createDegree(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.DEGREE, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type radian
     */
    public static LexicalUnit createRadian(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.RADIAN, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type gradian
     */
    public static LexicalUnit createGradian(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.GRADIAN, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type millisecond
     */
    public static LexicalUnit createMillisecond(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.MILLISECOND, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type second
     */
    public static LexicalUnit createSecond(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.SECOND, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type hertz
     */
    public static LexicalUnit createHertz(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.HERTZ, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type dimension
     * @param dim the dimension
     */
    public static LexicalUnit createDimension(final LexicalUnit prev, final double d, final String dim) {
        return new LexicalUnitImpl(prev, LexicalUnitType.DIMENSION, dim, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param d the double value
     * @return lexical unit with type kilohertz
     */
    public static LexicalUnit createKiloHertz(final LexicalUnit prev, final double d) {
        return new LexicalUnitImpl(prev, LexicalUnitType.KILOHERTZ, d);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param params the params
     * @return lexical unit with type counter
     */
    public static LexicalUnit createCounter(final LexicalUnit prev, final LexicalUnit params) {
        return new LexicalUnitImpl(prev, LexicalUnitType.COUNTER_FUNCTION, "counter", params);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param params the params
     * @return lexical unit with type counters
     */
    public static LexicalUnit createCounters(final LexicalUnit prev, final LexicalUnit params) {
        return new LexicalUnitImpl(prev, LexicalUnitType.COUNTERS_FUNCTION, "counters", params);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param value the value
     * @return lexical unit with type attr
     */
    public static LexicalUnit createAttr(final LexicalUnit prev, final String value) {
        // according to LexicalUnit.ATTR, LexicalUnit.getStringValue(), not
        // LexicalUnit.getParameters() is applicable
        return new LexicalUnitImpl(prev, LexicalUnitType.ATTR, "name", value);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param params the params
     * @return lexical unit with type rect
     */
    public static LexicalUnit createRect(final LexicalUnit prev, final LexicalUnit params) {
        return new LexicalUnitImpl(prev, LexicalUnitType.RECT_FUNCTION, "rect", params);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param params the params
     * @return lexical unit with type rgb color
     */
    public static LexicalUnit createRgbColor(final LexicalUnit prev, final LexicalUnit params) {
        return new LexicalUnitImpl(prev, LexicalUnitType.RGBCOLOR, "rgb", params);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param name the name
     * @param params the params
     * @return lexical unit with type function
     */
    public static LexicalUnit createFunction(final LexicalUnit prev, final String name, final LexicalUnit params) {
        return new LexicalUnitImpl(prev, LexicalUnitType.FUNCTION, name, params);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param value the value
     * @return lexical unit with type string
     */
    public static LexicalUnit createString(final LexicalUnit prev, final String value) {
        return new LexicalUnitImpl(prev, LexicalUnitType.STRING_VALUE, value);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param value the value
     * @return lexical unit with type ident
     */
    public static LexicalUnit createIdent(final LexicalUnit prev, final String value) {
        return new LexicalUnitImpl(prev, LexicalUnitType.IDENT, value);
    }

    /**
     * @param prev the previous LexicalUnit
     * @param value the value
     * @return lexical unit with type uri
     */
    public static LexicalUnit createURI(final LexicalUnit prev, final String value) {
        return new LexicalUnitImpl(prev, LexicalUnitType.URI, value);
    }

    /**
     * @param prev the previous LexicalUnit
     * @return lexical unit with type comma
     */
    public static LexicalUnit createComma(final LexicalUnit prev) {
        return new LexicalUnitImpl(prev, LexicalUnitType.OPERATOR_COMMA);
    }

    /**
     * @param prev the previous LexicalUnit
     * @return lexical unit with type plus
     */
    public static LexicalUnit createPlus(final LexicalUnit prev) {
        return new LexicalUnitImpl(prev, LexicalUnitType.OPERATOR_PLUS);
    }

    /**
     * @param prev the previous LexicalUnit
     * @return lexical unit with type minus
     */
    public static LexicalUnit createMinus(final LexicalUnit prev) {
        return new LexicalUnitImpl(prev, LexicalUnitType.OPERATOR_MINUS);
    }

    /**
     * @param prev the previous LexicalUnit
     * @return lexical unit with type multiply
     */
    public static LexicalUnit createMultiply(final LexicalUnit prev) {
        return new LexicalUnitImpl(prev, LexicalUnitType.OPERATOR_MULTIPLY);
    }

    /**
     * @param prev the previous LexicalUnit
     * @return lexical unit with type slash
     */
    public static LexicalUnit createDivide(final LexicalUnit prev) {
        return new LexicalUnitImpl(prev, LexicalUnitType.OPERATOR_SLASH);
    }
}
