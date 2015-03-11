/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2015 David Schweinsberg.  All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * To contact the authors of the library:
 *
 * http://cssparser.sourceforge.net/
 * mailto:davidsch@users.sourceforge.net
 *
 */

package com.steadystate.css.parser;

import java.io.Serializable;

import org.w3c.css.sac.LexicalUnit;


/**
 * Implementation of {@link LexicalUnit}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class LexicalUnitImpl extends LocatableImpl implements LexicalUnit, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7260032046960116891L;

    /** The lexical unit type_. */
    private short lexicalUnitType_;
    
    /** The next lexical unit_. */
    private LexicalUnit nextLexicalUnit_;
    
    /** The previous lexical unit_. */
    private LexicalUnit previousLexicalUnit_;
    
    /** The float value_. */
    private float floatValue_;
    
    /** The dimension_. */
    private String dimension_;
    
    /** The function name_. */
    private String functionName_;
    
    /** The parameters_. */
    private LexicalUnit parameters_;
    
    /** The string value_. */
    private String stringValue_;

    /**  cache *. */
    private transient String toString_;

    /**
     * Sets the lexical unit type.
     *
     * @param type the new lexical unit type
     */
    public void setLexicalUnitType(final short type) {
        lexicalUnitType_ = type;
        toString_ = null;
    }

    /**
     * Sets the next lexical unit.
     *
     * @param next the new next lexical unit
     */
    public void setNextLexicalUnit(final LexicalUnit next) {
        nextLexicalUnit_ = next;
    }

    /**
     * Sets the previous lexical unit.
     *
     * @param prev the new previous lexical unit
     */
    public void setPreviousLexicalUnit(final LexicalUnit prev) {
        previousLexicalUnit_ = prev;
    }

    /**
     * Sets the float value.
     *
     * @param floatVal the new float value
     */
    public void setFloatValue(final float floatVal) {
        floatValue_ = floatVal;
        toString_ = null;
    }

    /**
     * Gets the dimension.
     *
     * @return the dimension
     */
    public String getDimension() {
        return dimension_;
    }

    /**
     * Sets the dimension.
     *
     * @param dimension the new dimension
     */
    public void setDimension(final String dimension) {
        dimension_ = dimension;
        toString_ = null;
    }

    /**
     * Sets the function name.
     *
     * @param function the new function name
     */
    public void setFunctionName(final String function) {
        functionName_ = function;
        toString_ = null;
    }

    /**
     * Sets the parameters.
     *
     * @param params the new parameters
     */
    public void setParameters(final LexicalUnit params) {
        parameters_ = params;
        toString_ = null;
    }

    /**
     * Sets the string value.
     *
     * @param stringVal the new string value
     */
    public void setStringValue(final String stringVal) {
        stringValue_ = stringVal;
        toString_ = null;
    }

    /**
     * Instantiates a new lexical unit impl.
     *
     * @param previous the previous
     * @param type the type
     */
    protected LexicalUnitImpl(final LexicalUnit previous, final short type) {
        this();
        lexicalUnitType_ = type;
        previousLexicalUnit_ = previous;
        if (previousLexicalUnit_ != null) {
            ((LexicalUnitImpl) previousLexicalUnit_).nextLexicalUnit_ = this;
        }
    }

    /**
     * Integer.
     *
     * @param previous the previous
     * @param value the value
     */
    protected LexicalUnitImpl(final LexicalUnit previous, final int value) {
        this(previous, SAC_INTEGER);
        floatValue_ = value;
    }

    /**
     * Dimension.
     *
     * @param previous the previous
     * @param type the type
     * @param value the value
     */
    protected LexicalUnitImpl(final LexicalUnit previous, final short type, final float value) {
        this(previous, type);
        floatValue_ = value;
    }

    /**
     * Unknown dimension.
     *
     * @param previous the previous
     * @param type the type
     * @param dimension the dimension
     * @param value the value
     */
    protected LexicalUnitImpl(
            final LexicalUnit previous,
            final short type,
            final String dimension,
            final float value) {
        this(previous, type);
        dimension_ = dimension;
        floatValue_ = value;
    }

    /**
     * String.
     *
     * @param previous the previous
     * @param type the type
     * @param value the value
     */
    protected LexicalUnitImpl(final LexicalUnit previous, final short type, final String value) {
        this(previous, type);
        stringValue_ = value;
    }

    /**
     * Function.
     *
     * @param previous the previous
     * @param type the type
     * @param name the name
     * @param params the params
     */
    protected LexicalUnitImpl(
            final LexicalUnit previous,
            final short type,
            final String name,
            final LexicalUnit params) {
        this(previous, type);
        functionName_ = name;
        parameters_ = params;
    }

    /**
     * Instantiates a new lexical unit impl.
     *
     * @param previous the previous
     * @param type the type
     * @param name the name
     * @param stringValue the string value
     */
    protected LexicalUnitImpl(final LexicalUnit previous, final short type, final String name,
            final String stringValue) {
        this(previous, type);
        functionName_ = name;
        stringValue_ = stringValue;
    }

    /**
     * Default constructor.
     */
    protected LexicalUnitImpl() {
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.LexicalUnit#getLexicalUnitType()
     */
    public short getLexicalUnitType() {
        return lexicalUnitType_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.LexicalUnit#getNextLexicalUnit()
     */
    public LexicalUnit getNextLexicalUnit() {
        return nextLexicalUnit_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.LexicalUnit#getPreviousLexicalUnit()
     */
    public LexicalUnit getPreviousLexicalUnit() {
        return previousLexicalUnit_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.LexicalUnit#getIntegerValue()
     */
    public int getIntegerValue() {
        return (int) floatValue_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.LexicalUnit#getFloatValue()
     */
    public float getFloatValue() {
        return floatValue_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.LexicalUnit#getDimensionUnitText()
     */
    public String getDimensionUnitText() {
        switch (lexicalUnitType_) {
            case SAC_EM:
                return "em";
            case SAC_EX:
                return "ex";
            case SAC_PIXEL:
                return "px";
            case SAC_INCH:
                return "in";
            case SAC_CENTIMETER:
                return "cm";
            case SAC_MILLIMETER:
                return "mm";
            case SAC_POINT:
                return "pt";
            case SAC_PICA:
                return "pc";
            case SAC_PERCENTAGE:
                return "%";
            case SAC_DEGREE:
                return "deg";
            case SAC_GRADIAN:
                return "grad";
            case SAC_RADIAN:
                return "rad";
            case SAC_MILLISECOND:
                return "ms";
            case SAC_SECOND:
                return "s";
            case SAC_HERTZ:
                return "Hz";
            case SAC_KILOHERTZ:
                return "kHz";
            case SAC_DIMENSION:
                return dimension_;
            default:
                return "";
        }
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.LexicalUnit#getFunctionName()
     */
    public String getFunctionName() {
        return functionName_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.LexicalUnit#getParameters()
     */
    public LexicalUnit getParameters() {
        return parameters_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.LexicalUnit#getStringValue()
     */
    public String getStringValue() {
        return stringValue_;
    }

    /* (non-Javadoc)
     * @see org.w3c.css.sac.LexicalUnit#getSubValues()
     */
    public LexicalUnit getSubValues() {
        return parameters_;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (null != toString_) {
            return toString_;
        }

        final StringBuilder sb = new StringBuilder();
        switch (lexicalUnitType_) {
            case SAC_OPERATOR_COMMA:
                sb.append(",");
                break;
            case SAC_OPERATOR_PLUS:
                sb.append("+");
                break;
            case SAC_OPERATOR_MINUS:
                sb.append("-");
                break;
            case SAC_OPERATOR_MULTIPLY:
                sb.append("*");
                break;
            case SAC_OPERATOR_SLASH:
                sb.append("/");
                break;
            case SAC_OPERATOR_MOD:
                sb.append("%");
                break;
            case SAC_OPERATOR_EXP:
                sb.append("^");
                break;
            case SAC_OPERATOR_LT:
                sb.append("<");
                break;
            case SAC_OPERATOR_GT:
                sb.append(">");
                break;
            case SAC_OPERATOR_LE:
                sb.append("<=");
                break;
            case SAC_OPERATOR_GE:
                sb.append(">=");
                break;
            case SAC_OPERATOR_TILDE:
                sb.append("~");
                break;
            case SAC_INHERIT:
                sb.append("inherit");
                break;
            case SAC_INTEGER:
                sb.append(String.valueOf(getIntegerValue()));
                break;
            case SAC_REAL:
                sb.append(getTrimedFloatValue());
                break;
            case SAC_EM:
            case SAC_EX:
            case SAC_PIXEL:
            case SAC_INCH:
            case SAC_CENTIMETER:
            case SAC_MILLIMETER:
            case SAC_POINT:
            case SAC_PICA:
            case SAC_PERCENTAGE:
            case SAC_DEGREE:
            case SAC_GRADIAN:
            case SAC_RADIAN:
            case SAC_MILLISECOND:
            case SAC_SECOND:
            case SAC_HERTZ:
            case SAC_KILOHERTZ:
            case SAC_DIMENSION:
                sb.append(getTrimedFloatValue());
                final String dimUnitText = getDimensionUnitText();
                if (null != dimUnitText) {
                    sb.append(dimUnitText);
                }
                break;
            case SAC_URI:
                sb.append("url(").append(getStringValue()).append(")");
                break;
            case SAC_COUNTER_FUNCTION:
                sb.append("counter(");
                appendParams(sb);
                sb.append(")");
                break;
            case SAC_COUNTERS_FUNCTION:
                sb.append("counters(");
                appendParams(sb);
                sb.append(")");
                break;
            case SAC_RGBCOLOR:
                sb.append("rgb(");
                appendParams(sb);
                sb.append(")");
                break;
            case SAC_IDENT:
                sb.append(getStringValue());
                break;
            case SAC_STRING_VALUE:
                sb.append("\"").append(getStringValue()).append("\"");
                break;
            case SAC_ATTR:
                sb.append("attr(")
                    .append(getStringValue())
                    .append(")");
                break;
            case SAC_RECT_FUNCTION:
                sb.append("rect(");
                appendParams(sb);
                sb.append(")");
                break;
            case SAC_UNICODERANGE:
                final String range = getStringValue();
                if (null != range) {
                    sb.append(range);
                }
                break;
            case SAC_SUB_EXPRESSION:
                final String subExpression = getStringValue();
                if (null != subExpression) {
                    sb.append(subExpression);
                }
                break;
            case SAC_FUNCTION:
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

    /**
     * To debug string.
     *
     * @return the string
     */
    public String toDebugString() {
        final StringBuilder sb = new StringBuilder();
        switch (lexicalUnitType_) {
            case SAC_OPERATOR_COMMA:
                sb.append("SAC_OPERATOR_COMMA");
                break;
            case SAC_OPERATOR_PLUS:
                sb.append("SAC_OPERATOR_PLUS");
                break;
            case SAC_OPERATOR_MINUS:
                sb.append("SAC_OPERATOR_MINUS");
                break;
            case SAC_OPERATOR_MULTIPLY:
                sb.append("SAC_OPERATOR_MULTIPLY");
                break;
            case SAC_OPERATOR_SLASH:
                sb.append("SAC_OPERATOR_SLASH");
                break;
            case SAC_OPERATOR_MOD:
                sb.append("SAC_OPERATOR_MOD");
                break;
            case SAC_OPERATOR_EXP:
                sb.append("SAC_OPERATOR_EXP");
                break;
            case SAC_OPERATOR_LT:
                sb.append("SAC_OPERATOR_LT");
                break;
            case SAC_OPERATOR_GT:
                sb.append("SAC_OPERATOR_GT");
                break;
            case SAC_OPERATOR_LE:
                sb.append("SAC_OPERATOR_LE");
                break;
            case SAC_OPERATOR_GE:
                sb.append("SAC_OPERATOR_GE");
                break;
            case SAC_OPERATOR_TILDE:
                sb.append("SAC_OPERATOR_TILDE");
                break;
            case SAC_INHERIT:
                sb.append("SAC_INHERIT");
                break;
            case SAC_INTEGER:
                sb.append("SAC_INTEGER(")
                    .append(String.valueOf(getIntegerValue()))
                    .append(")");
                break;
            case SAC_REAL:
                sb.append("SAC_REAL(")
                    .append(getTrimedFloatValue())
                    .append(")");
                break;
            case SAC_EM:
                sb.append("SAC_EM(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_EX:
                sb.append("SAC_EX(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_PIXEL:
                sb.append("SAC_PIXEL(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_INCH:
                sb.append("SAC_INCH(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_CENTIMETER:
                sb.append("SAC_CENTIMETER(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_MILLIMETER:
                sb.append("SAC_MILLIMETER(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_POINT:
                sb.append("SAC_POINT(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_PICA:
                sb.append("SAC_PICA(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_PERCENTAGE:
                sb.append("SAC_PERCENTAGE(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_DEGREE:
                sb.append("SAC_DEGREE(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_GRADIAN:
                sb.append("SAC_GRADIAN(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_RADIAN:
                sb.append("SAC_RADIAN(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_MILLISECOND:
                sb.append("SAC_MILLISECOND(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_SECOND:
                sb.append("SAC_SECOND(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_HERTZ:
                sb.append("SAC_HERTZ(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_KILOHERTZ:
                sb.append("SAC_KILOHERTZ(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_DIMENSION:
                sb.append("SAC_DIMENSION(")
                    .append(getTrimedFloatValue())
                    .append(getDimensionUnitText())
                    .append(")");
                break;
            case SAC_URI:
                sb.append("SAC_URI(url(")
                    .append(getStringValue())
                    .append("))");
                break;
            case SAC_COUNTER_FUNCTION:
                sb.append("SAC_COUNTER_FUNCTION(counter(");
                appendParams(sb);
                sb.append("))");
                break;
            case SAC_COUNTERS_FUNCTION:
                sb.append("SAC_COUNTERS_FUNCTION(counters(");
                appendParams(sb);
                sb.append("))");
                break;
            case SAC_RGBCOLOR:
                sb.append("SAC_RGBCOLOR(rgb(");
                appendParams(sb);
                sb.append("))");
                break;
            case SAC_IDENT:
                sb.append("SAC_IDENT(")
                    .append(getStringValue())
                    .append(")");
                break;
            case SAC_STRING_VALUE:
                sb.append("SAC_STRING_VALUE(\"")
                    .append(getStringValue())
                    .append("\")");
                break;
            case SAC_ATTR:
                sb.append("SAC_ATTR(attr(")
                    .append(getStringValue())
                    .append("))");
                break;
            case SAC_RECT_FUNCTION:
                sb.append("SAC_RECT_FUNCTION(rect(");
                appendParams(sb);
                sb.append("))");
                break;
            case SAC_UNICODERANGE:
                sb.append("SAC_UNICODERANGE(")
                    .append(getStringValue())
                    .append(")");
                break;
            case SAC_SUB_EXPRESSION:
                sb.append("SAC_SUB_EXPRESSION(")
                    .append(getStringValue())
                    .append(")");
                break;
            case SAC_FUNCTION:
                sb.append("SAC_FUNCTION(")
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

    /**
     * Append params.
     *
     * @param sb the sb
     */
    private void appendParams(final StringBuilder sb) {
        LexicalUnit l = parameters_;
        if (l != null) {
            sb.append(l.toString());
            l = l.getNextLexicalUnit();
            while (l != null) {
                if (l.getLexicalUnitType() != SAC_OPERATOR_COMMA) {
                    sb.append(" ");
                }
                sb.append(l.toString());
                l = l.getNextLexicalUnit();
            }
        }
    }

    /**
     * Gets the trimed float value.
     *
     * @return the trimed float value
     */
    private String getTrimedFloatValue() {
        final float f = getFloatValue();
        final String s = Float.toString(f);
        return (f - (int) f != 0) ? s : s.substring(0, s.length() - 2);
    }

    /**
     * Creates the number.
     *
     * @param prev the prev
     * @param i the i
     * @return the lexical unit
     */
    public static LexicalUnit createNumber(final LexicalUnit prev, final int i) {
        return new LexicalUnitImpl(prev, i);
    }

    /**
     * Creates the number.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createNumber(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_REAL, f);
    }

    /**
     * Creates the percentage.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createPercentage(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_PERCENTAGE, f);
    }

    /**
     * Creates the pixel.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createPixel(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_PIXEL, f);
    }

    /**
     * Creates the centimeter.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createCentimeter(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_CENTIMETER, f);
    }

    /**
     * Creates the millimeter.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createMillimeter(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_MILLIMETER, f);
    }

    /**
     * Creates the inch.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createInch(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_INCH, f);
    }

    /**
     * Creates the point.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createPoint(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_POINT, f);
    }

    /**
     * Creates the pica.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createPica(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_PICA, f);
    }

    /**
     * Creates the em.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createEm(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_EM, f);
    }

    /**
     * Creates the ex.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createEx(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_EX, f);
    }

    /**
     * Creates the degree.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createDegree(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_DEGREE, f);
    }

    /**
     * Creates the radian.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createRadian(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_RADIAN, f);
    }

    /**
     * Creates the gradian.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createGradian(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_GRADIAN, f);
    }

    /**
     * Creates the millisecond.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createMillisecond(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_MILLISECOND, f);
    }

    /**
     * Creates the second.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createSecond(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_SECOND, f);
    }

    /**
     * Creates the hertz.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createHertz(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_HERTZ, f);
    }

    /**
     * Creates the dimension.
     *
     * @param prev the prev
     * @param f the f
     * @param dim the dim
     * @return the lexical unit
     */
    public static LexicalUnit createDimension(final LexicalUnit prev, final float f, final String dim) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_DIMENSION, dim, f);
    }

    /**
     * Creates the kilo hertz.
     *
     * @param prev the prev
     * @param f the f
     * @return the lexical unit
     */
    public static LexicalUnit createKiloHertz(final LexicalUnit prev, final float f) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_KILOHERTZ, f);
    }

    /**
     * Creates the counter.
     *
     * @param prev the prev
     * @param params the params
     * @return the lexical unit
     */
    public static LexicalUnit createCounter(final LexicalUnit prev, final LexicalUnit params) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_COUNTER_FUNCTION, "counter", params);
    }

    /**
     * Creates the counters.
     *
     * @param prev the prev
     * @param params the params
     * @return the lexical unit
     */
    public static LexicalUnit createCounters(final LexicalUnit prev, final LexicalUnit params) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_COUNTERS_FUNCTION, "counters", params);
    }

    /**
     * Creates the attr.
     *
     * @param prev the prev
     * @param value the value
     * @return the lexical unit
     */
    public static LexicalUnit createAttr(final LexicalUnit prev, final String value) {
        // according to LexicalUnit.SAC_ATTR, LexicalUnit.getStringValue(), not
        // LexicalUnit.getParameters() is applicable
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_ATTR, "name", value);
    }

    /**
     * Creates the rect.
     *
     * @param prev the prev
     * @param params the params
     * @return the lexical unit
     */
    public static LexicalUnit createRect(final LexicalUnit prev, final LexicalUnit params) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_RECT_FUNCTION, "rect", params);
    }

    /**
     * Creates the rgb color.
     *
     * @param prev the prev
     * @param params the params
     * @return the lexical unit
     */
    public static LexicalUnit createRgbColor(final LexicalUnit prev, final LexicalUnit params) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_RGBCOLOR, "rgb", params);
    }

    /**
     * Creates the function.
     *
     * @param prev the prev
     * @param name the name
     * @param params the params
     * @return the lexical unit
     */
    public static LexicalUnit createFunction(final LexicalUnit prev, final String name, final LexicalUnit params) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_FUNCTION, name, params);
    }

    /**
     * Creates the string.
     *
     * @param prev the prev
     * @param value the value
     * @return the lexical unit
     */
    public static LexicalUnit createString(final LexicalUnit prev, final String value) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_STRING_VALUE, value);
    }

    /**
     * Creates the ident.
     *
     * @param prev the prev
     * @param value the value
     * @return the lexical unit
     */
    public static LexicalUnit createIdent(final LexicalUnit prev, final String value) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_IDENT, value);
    }

    /**
     * Creates the uri.
     *
     * @param prev the prev
     * @param value the value
     * @return the lexical unit
     */
    public static LexicalUnit createURI(final LexicalUnit prev, final String value) {
        return new LexicalUnitImpl(prev, LexicalUnit.SAC_URI, value);
    }

    /**
     * Creates the comma.
     *
     * @param prev the prev
     * @return the lexical unit
     */
    public static LexicalUnit createComma(final LexicalUnit prev) {
        return new LexicalUnitImpl(prev, SAC_OPERATOR_COMMA);
    }
}
