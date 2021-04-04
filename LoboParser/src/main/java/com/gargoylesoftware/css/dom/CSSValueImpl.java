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
package com.gargoylesoftware.css.dom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.DOMException;

import com.gargoylesoftware.css.parser.AbstractLocatable;
import com.gargoylesoftware.css.parser.CSSOMParser;
import com.gargoylesoftware.css.parser.LexicalUnit;
import com.gargoylesoftware.css.parser.LexicalUnit.LexicalUnitType;
import com.gargoylesoftware.css.parser.LexicalUnitImpl;
import com.gargoylesoftware.css.util.LangUtils;

/**
 * The <code>CSSValueImpl</code> class can represent either a
 * <code>CSSPrimitiveValue</code> or a <code>CSSValueList</code> so that
 * the type can successfully change when using <code>setCssText</code>.
 *
 * Author Ronald Brill
 *
 */
public class CSSValueImpl extends AbstractLocatable implements Serializable {

    /**
     * CSSValueType enum.
     */
    public enum CSSValueType {
        /** CSS_VALUE_LIST. */
        CSS_VALUE_LIST,

        /** CSS_INHERIT. */
        CSS_INHERIT,

        /** CSS_PRIMITIVE_VALUE. */
        CSS_PRIMITIVE_VALUE;
    }

    /**
     * CSSPrimitiveValueType enum.
     */
    public enum CSSPrimitiveValueType {

        /** CSS_IDENT. */
        CSS_IDENT,

        /** CSS_NUMBER. */
        CSS_NUMBER,

        /** CSS_EMS. */
        CSS_EMS,

        /** CSS_EXS. */
        CSS_EXS,

        /** CSS_REM. */
        CSS_REM,

        /** CSS_CH. */
        CSS_CH,

        /** CSS_VW. */
        CSS_VW,

        /** CSS_VH. */
        CSS_VH,

        /** CSS_VMIN. */
        CSS_VMIN,

        /** CSS_VMAX. */
        CSS_VMAX,

        /** CSS_PX. */
        CSS_PX,

        /** CSS_IN. */
        CSS_IN,

        /** CSS_CM. */
        CSS_CM,

        /** CSS_MM. */
        CSS_MM,

        /** CSS_PT. */
        CSS_PT,

        /** CSS_PC. */
        CSS_PC,

        /** CSS_PERCENTAGE. */
        CSS_PERCENTAGE,

        /** CSS_URI. */
        CSS_URI,

        /** COUNTERS_FUNCTION. */
        COUNTERS_FUNCTION,

        /** CSS_RGBCOLOR. */
        CSS_RGBCOLOR,

        /** CSS_DEG. */
        CSS_DEG,

        /** CSS_GRAD. */
        CSS_GRAD,

        /** CSS_RAD. */
        CSS_RAD,

        /** CSS_MS. */
        CSS_MS,

        /** CSS_S. */
        CSS_S,

        /** CSS_HZ. */
        CSS_HZ,

        /** CSS_KHZ. */
        CSS_KHZ,

        /** CSS_ATTR. */
        CSS_ATTR,

        /** CSS_RECT. */
        CSS_RECT,

        /** CSS_STRING. */
        CSS_STRING,

        /** CSS_DIMENSION. */
        CSS_DIMENSION,

        /** CSS_COUNTER. */
        CSS_COUNTER,

        /** CSS_UNKNOWN. */
        CSS_UNKNOWN
    }

    private Object value_;

    /**
     * <p>getValue.</p>
     *
     * @return the value
     */
    public Object getValue() {
        return value_;
    }

    /**
     * Constructor.
     *
     * @param value the lexical unit value
     * @param forcePrimitive true or false
     */
    public CSSValueImpl(final LexicalUnit value, final boolean forcePrimitive) {
        LexicalUnit parameters = null;
        if (value != null) {
            parameters = value.getParameters();
        }

        if (!forcePrimitive && value != null && (value.getNextLexicalUnit() != null)) {
            value_ = getValues(value);
        }
        else if (parameters != null) {
            if (value.getLexicalUnitType() == LexicalUnitType.RECT_FUNCTION) {
                // Rect
                value_ = new RectImpl(value.getParameters());
            }
            else if (value.getLexicalUnitType() == LexicalUnitType.RGBCOLOR) {
                // RGBColor
                value_ = new RGBColorImpl(value.getParameters());
            }
            else if (value.getLexicalUnitType() == LexicalUnitType.COUNTER_FUNCTION) {
                // Counter
                value_ = new CounterImpl(false, value.getParameters());
            }
            else if (value.getLexicalUnitType() == LexicalUnitType.COUNTERS_FUNCTION) {
                // Counter
                value_ = new CounterImpl(true, value.getParameters());
            }
            else {
                value_ = value;
            }
        }
        else {
            // We need to be a CSSPrimitiveValue
            value_ = value;
        }

        if (value != null) {
            setLocator(value.getLocator());
        }
    }

    private static List<CSSValueImpl> getValues(final LexicalUnit value) {
        final List<CSSValueImpl> values = new ArrayList<>();
        LexicalUnit lu = value;
        while (lu != null) {
            values.add(new CSSValueImpl(lu, true));
            lu = lu.getNextLexicalUnit();
        }
        return values;
    }

    /**
     * Ctor.
     *
     * @param value the value
     */
    public CSSValueImpl(final LexicalUnit value) {
        this(value, false);
    }

    /**
     * <p>getCssText.</p>
     *
     * @return the css text
     */
    public String getCssText() {
        if (getCssValueType() == CSSValueType.CSS_VALUE_LIST) {

            // Create the string from the LexicalUnits so we include the correct
            // operators in the string
            final StringBuilder sb = new StringBuilder();
            final List<?> list = (List<?>) value_;
            final Iterator<?> it = list.iterator();

            boolean separate = false;
            while (it.hasNext()) {
                final Object o = it.next();

                final CSSValueImpl cssValue = (CSSValueImpl) o;
                if (separate) {
                    if (cssValue.value_ instanceof LexicalUnit) {
                        final LexicalUnit lu = (LexicalUnit) cssValue.value_;
                        if (lu.getLexicalUnitType() != LexicalUnitType.OPERATOR_COMMA) {
                            sb.append(" ");
                        }
                    }
                    else {
                        sb.append(" ");
                    }
                }

                sb.append(o.toString());
                separate = true;
            }
            return sb.toString();
        }
        return value_ != null ? value_.toString() : "";
    }

    /**
     * Sets the css text.
     *
     * @param cssText the new css text
     * @throws org.w3c.dom.DOMException in case of error
     */
    public void setCssText(final String cssText) throws DOMException {
        try {
            final CSSOMParser parser = new CSSOMParser();
            final CSSValueImpl v2 = parser.parsePropertyValue(cssText);
            value_ = v2.value_;
        }
        catch (final Exception e) {
            throw new DOMExceptionImpl(
                DOMException.SYNTAX_ERR,
                DOMExceptionImpl.SYNTAX_ERROR,
                e.getMessage());
        }
    }

    /**
     * <p>getCssValueType.</p>
     *
     * @return the css value type
     */
    public CSSValueType getCssValueType() {
        if (value_ instanceof List) {
            return CSSValueType.CSS_VALUE_LIST;
        }
        if ((value_ instanceof LexicalUnit)
                && (((LexicalUnit) value_).getLexicalUnitType() == LexicalUnitType.INHERIT)) {
            return CSSValueType.CSS_INHERIT;
        }
        return CSSValueType.CSS_PRIMITIVE_VALUE;
    }

    /**
     * <p>getPrimitiveType.</p>
     *
     * @return the primitive type
     */
    public CSSPrimitiveValueType getPrimitiveType() {
        if (value_ instanceof LexicalUnit) {
            final LexicalUnit lu = (LexicalUnit) value_;
            switch (lu.getLexicalUnitType()) {
                case INHERIT:
                    return CSSPrimitiveValueType.CSS_IDENT;
                case INTEGER:
                case REAL:
                    return CSSPrimitiveValueType.CSS_NUMBER;
                case EM:
                    return CSSPrimitiveValueType.CSS_EMS;
                case REM:
                    return CSSPrimitiveValueType.CSS_REM;
                case EX:
                    return CSSPrimitiveValueType.CSS_EXS;
                case CH:
                    return CSSPrimitiveValueType.CSS_CH;
                case VW:
                    return CSSPrimitiveValueType.CSS_VW;
                case VH:
                    return CSSPrimitiveValueType.CSS_VH;
                case VMIN:
                    return CSSPrimitiveValueType.CSS_VMIN;
                case VMAX:
                    return CSSPrimitiveValueType.CSS_VMAX;
                case PIXEL:
                    return CSSPrimitiveValueType.CSS_PX;
                case INCH:
                    return CSSPrimitiveValueType.CSS_IN;
                case CENTIMETER:
                    return CSSPrimitiveValueType.CSS_CM;
                case MILLIMETER:
                    return CSSPrimitiveValueType.CSS_MM;
                case POINT:
                    return CSSPrimitiveValueType.CSS_PT;
                case PICA:
                    return CSSPrimitiveValueType.CSS_PC;
                case PERCENTAGE:
                    return CSSPrimitiveValueType.CSS_PERCENTAGE;
                case URI:
                    return CSSPrimitiveValueType.CSS_URI;
                case COUNTER_FUNCTION:
    //            case COUNTERS_FUNCTION:
                    return CSSPrimitiveValueType.CSS_COUNTER;
    //            case RGBCOLOR:
    //                return CSS_RGBCOLOR;
                case DEGREE:
                    return CSSPrimitiveValueType.CSS_DEG;
                case GRADIAN:
                    return CSSPrimitiveValueType.CSS_GRAD;
                case RADIAN:
                    return CSSPrimitiveValueType.CSS_RAD;
                case MILLISECOND:
                    return CSSPrimitiveValueType.CSS_MS;
                case SECOND:
                    return CSSPrimitiveValueType.CSS_S;
                case HERTZ:
                    return CSSPrimitiveValueType.CSS_HZ;
                case KILOHERTZ:
                    return CSSPrimitiveValueType.CSS_KHZ;
                case IDENT:
                    return CSSPrimitiveValueType.CSS_IDENT;
                case STRING_VALUE:
                    return CSSPrimitiveValueType.CSS_STRING;
                case ATTR:
                    return CSSPrimitiveValueType.CSS_ATTR;
    //            case RECT_FUNCTION:
    //                return CSSPrimitiveValueType.CSS_RECT;
                case UNICODERANGE:
                case SUB_EXPRESSION:
                case FUNCTION:
                    return CSSPrimitiveValueType.CSS_STRING;
                case DIMENSION:
                    return CSSPrimitiveValueType.CSS_DIMENSION;
                default:
                    return CSSPrimitiveValueType.CSS_UNKNOWN;
            }
        }
        else if (value_ instanceof RectImpl) {
            return CSSPrimitiveValueType.CSS_RECT;
        }
        else if (value_ instanceof RGBColorImpl) {
            return CSSPrimitiveValueType.CSS_RGBCOLOR;
        }
        else if (value_ instanceof CounterImpl) {
            return CSSPrimitiveValueType.CSS_COUNTER;
        }
        return CSSPrimitiveValueType.CSS_UNKNOWN;
    }

    /**
     * <p>getLexicalUnitType.</p>
     *
     * @return the lexical unit type
     */
    public LexicalUnit.LexicalUnitType getLexicalUnitType() {
        if (value_ instanceof LexicalUnit) {
            return ((LexicalUnit) value_).getLexicalUnitType();
        }
        return null;
    }

    /**
     * Sets the double value to a new value.
     *
     * @param doubleValue the new value
     * @throws org.w3c.dom.DOMException in case of error
     */
    public void setDoubleValue(final double doubleValue) throws DOMException {
        value_ = LexicalUnitImpl.createNumber(null, doubleValue);
    }

    /**
     * <p>getDoubleValue.</p>
     *
     * @return the double value.
     * @throws org.w3c.dom.DOMException in case of error
     */
    public double getDoubleValue() throws DOMException {
        if (value_ instanceof LexicalUnit) {
            final LexicalUnit lu = (LexicalUnit) value_;
            return lu.getDoubleValue();
        }
        throw new DOMExceptionImpl(
            DOMException.INVALID_ACCESS_ERR,
            DOMExceptionImpl.FLOAT_ERROR);

        // We need to attempt a conversion
        // return 0;
    }

    /**
     * <p>getStringValue.</p>
     *
     * @return the string value.
     * @throws org.w3c.dom.DOMException case of error
     */
    public String getStringValue() throws DOMException {
        if (value_ instanceof LexicalUnit) {
            final LexicalUnit lu = (LexicalUnit) value_;
            if ((lu.getLexicalUnitType() == LexicalUnitType.IDENT)
                || (lu.getLexicalUnitType() == LexicalUnitType.STRING_VALUE)
                || (lu.getLexicalUnitType() == LexicalUnitType.URI)
                || (lu.getLexicalUnitType() == LexicalUnitType.INHERIT)
                || (lu.getLexicalUnitType() == LexicalUnitType.ATTR)) {
                return lu.getStringValue();
            }

            // for rgba values we are using this type
            if (lu.getLexicalUnitType() == LexicalUnitType.FUNCTION) {
                return lu.toString();
            }
        }
        else if (value_ instanceof List) {
            return null;
        }

        throw new DOMExceptionImpl(
            DOMException.INVALID_ACCESS_ERR,
            DOMExceptionImpl.STRING_ERROR);
    }

    /**
     * <p>getCounterValue.</p>
     *
     * @return the counter value
     * @throws org.w3c.dom.DOMException in case of error
     */
    public CounterImpl getCounterValue() throws DOMException {
        if (value_ instanceof CounterImpl) {
            return (CounterImpl) value_;
        }

        throw new DOMExceptionImpl(
                DOMException.INVALID_ACCESS_ERR,
                DOMExceptionImpl.COUNTER_ERROR);
    }

    /**
     * <p>getRectValue.</p>
     *
     * @return the rect
     * @throws org.w3c.dom.DOMException in case of error
     */
    public RectImpl getRectValue() throws DOMException {
        if (value_ instanceof RectImpl) {
            return (RectImpl) value_;
        }

        throw new DOMExceptionImpl(
                DOMException.INVALID_ACCESS_ERR,
                DOMExceptionImpl.RECT_ERROR);
    }

    /**
     * <p>getRGBColorValue.</p>
     *
     * @return the rgb
     * @throws org.w3c.dom.DOMException in case of error
     */
    public RGBColorImpl getRGBColorValue() throws DOMException {
        if (value_ instanceof RGBColorImpl) {
            return (RGBColorImpl) value_;
        }

        throw new DOMExceptionImpl(
            DOMException.INVALID_ACCESS_ERR,
            DOMExceptionImpl.RGBCOLOR_ERROR);
    }

    /**
     * <p>getLength.</p>
     *
     * @return the length
     */
    @SuppressWarnings("unchecked")
    public int getLength() {
        if (value_ instanceof List) {
            return ((List<CSSValueImpl>) value_).size();
        }
        return 0;
    }

    /**
     * <p>item.</p>
     *
     * @param index the position
     * @return the value at the position
     */
    @SuppressWarnings("unchecked")
    public CSSValueImpl item(final int index) {
        if (value_ instanceof List) {
            final List<CSSValueImpl> list = (List<CSSValueImpl>) value_;
            return list.get(index);
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getCssText();
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSValueImpl)) {
            return false;
        }
        final CSSValueImpl cv = (CSSValueImpl) obj;
        // TODO to be improved!
        return super.equals(obj)
            && (getCssValueType() == cv.getCssValueType())
            && LangUtils.equals(getCssText(), cv.getCssText());
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = LangUtils.hashCode(hash, value_);
        return hash;
    }

}
