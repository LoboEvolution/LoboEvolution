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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSValueList;
import org.w3c.dom.css.Counter;
import org.w3c.dom.css.RGBColor;
import org.w3c.dom.css.Rect;

import com.gargoylesoftware.css.parser.AbstractLocatable;
import com.gargoylesoftware.css.parser.CSSOMParser;
import com.gargoylesoftware.css.parser.InputSource;
import com.gargoylesoftware.css.parser.LexicalUnit;
import com.gargoylesoftware.css.parser.LexicalUnitImpl;
import com.gargoylesoftware.css.parser.LexicalUnit.LexicalUnitType;
import com.gargoylesoftware.css.util.LangUtils;

/**
 * The <code>CSSValueImpl</code> class can represent either a
 * <code>CSSPrimitiveValue</code> or a <code>CSSValueList</code> so that
 * the type can successfully change when using <code>setCssText</code>.
 *
 * TODO:
 * Float unit conversions,
 * A means of checking valid primitive types for properties
 *
 * @author Ronald Brill
 */
public class CSSValueImpl extends AbstractLocatable implements CSSPrimitiveValue, CSSValueList, Serializable {

    private Object value_;

    /**
     * @return the value
     */
    public Object getValue() {
        return value_;
    }

    /**
     * Constructor.
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

    private List<CSSValueImpl> getValues(final LexicalUnit value) {
        final List<CSSValueImpl> values = new ArrayList<CSSValueImpl>();
        LexicalUnit lu = value;
        while (lu != null) {
            values.add(new CSSValueImpl(lu, true));
            lu = lu.getNextLexicalUnit();
        }
        return values;
    }

    /**
     * Ctor.
     * @param value the value
     */
    public CSSValueImpl(final LexicalUnit value) {
        this(value, false);
    }

    @Override
    public String getCssText() {
        if (getCssValueType() == CSS_VALUE_LIST) {

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

    @Override
    public void setCssText(final String cssText) throws DOMException {
        try {
            final InputSource is = new InputSource(new StringReader(cssText));
            final CSSOMParser parser = new CSSOMParser();
            final CSSValueImpl v2 = (CSSValueImpl) parser.parsePropertyValue(is);
            value_ = v2.value_;
        }
        catch (final Exception e) {
            throw new DOMExceptionImpl(
                DOMException.SYNTAX_ERR,
                DOMExceptionImpl.SYNTAX_ERROR,
                e.getMessage());
        }
    }

    @Override
    public short getCssValueType() {
        if (value_ instanceof List) {
            return CSS_VALUE_LIST;
        }
        if ((value_ instanceof LexicalUnit)
                && (((LexicalUnit) value_).getLexicalUnitType() == LexicalUnitType.INHERIT)) {
            return CSS_INHERIT;
        }
        return CSS_PRIMITIVE_VALUE;
    }

    @Override
    public short getPrimitiveType() {
        if (value_ instanceof LexicalUnit) {
            final LexicalUnit lu = (LexicalUnit) value_;
            switch (lu.getLexicalUnitType()) {
                case INHERIT:
                    return CSS_IDENT;
                case INTEGER:
                case REAL:
                    return CSS_NUMBER;
                case EM:
                    return CSS_EMS;
                case EX:
                    return CSS_EXS;
                case PIXEL:
                    return CSS_PX;
                case INCH:
                    return CSS_IN;
                case CENTIMETER:
                    return CSS_CM;
                case MILLIMETER:
                    return CSS_MM;
                case POINT:
                    return CSS_PT;
                case PICA:
                    return CSS_PC;
                case PERCENTAGE:
                    return CSS_PERCENTAGE;
                case URI:
                    return CSS_URI;
                case COUNTER_FUNCTION:
    //            case COUNTERS_FUNCTION:
                    return CSS_COUNTER;
    //            case RGBCOLOR:
    //                return CSS_RGBCOLOR;
                case DEGREE:
                    return CSS_DEG;
                case GRADIAN:
                    return CSS_GRAD;
                case RADIAN:
                    return CSS_RAD;
                case MILLISECOND:
                    return CSS_MS;
                case SECOND:
                    return CSS_S;
                case HERTZ:
                    return CSS_HZ;
                case KILOHERTZ:
                    return CSS_KHZ;
                case IDENT:
                    return CSS_IDENT;
                case STRING_VALUE:
                    return CSS_STRING;
                case ATTR:
                    return CSS_ATTR;
    //            case RECT_FUNCTION:
    //                return CSS_RECT;
                case UNICODERANGE:
                case SUB_EXPRESSION:
                case FUNCTION:
                    return CSS_STRING;
                case DIMENSION:
                    return CSS_DIMENSION;
                default:
                    return CSS_UNKNOWN;
            }
        }
        else if (value_ instanceof RectImpl) {
            return CSS_RECT;
        }
        else if (value_ instanceof RGBColorImpl) {
            return CSS_RGBCOLOR;
        }
        else if (value_ instanceof CounterImpl) {
            return CSS_COUNTER;
        }
        return CSS_UNKNOWN;
    }

    @Override
    public void setFloatValue(final short unitType, final float floatValue) throws DOMException {
        value_ = LexicalUnitImpl.createNumber(null, floatValue);
    }

    @Override
    public float getFloatValue(final short unitType) throws DOMException {
        if (value_ instanceof LexicalUnit) {
            final LexicalUnit lu = (LexicalUnit) value_;
            return lu.getFloatValue();
        }
        throw new DOMExceptionImpl(
            DOMException.INVALID_ACCESS_ERR,
            DOMExceptionImpl.FLOAT_ERROR);

        // We need to attempt a conversion
        // return 0;
    }

    @Override
    public void setStringValue(final short stringType, final String stringValue) throws DOMException {
        switch (stringType) {
            case CSS_STRING:
                value_ = LexicalUnitImpl.createString(null, stringValue);
                break;
            case CSS_URI:
                value_ = LexicalUnitImpl.createURI(null, stringValue);
                break;
            case CSS_IDENT:
                value_ = LexicalUnitImpl.createIdent(null, stringValue);
                break;
            case CSS_ATTR:
    //            _value = LexicalUnitImpl.createAttr(null, stringValue);
    //            break;
                throw new DOMExceptionImpl(
                    DOMException.NOT_SUPPORTED_ERR,
                    DOMExceptionImpl.NOT_IMPLEMENTED);
            default:
                throw new DOMExceptionImpl(
                    DOMException.INVALID_ACCESS_ERR,
                    DOMExceptionImpl.STRING_ERROR);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
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

    @Override
    public Counter getCounterValue() throws DOMException {
        if (value_ instanceof Counter) {
            return (Counter) value_;
        }

        throw new DOMExceptionImpl(
                DOMException.INVALID_ACCESS_ERR,
                DOMExceptionImpl.COUNTER_ERROR);
    }

    @Override
    public Rect getRectValue() throws DOMException {
        if (value_ instanceof Rect) {
            return (Rect) value_;
        }

        throw new DOMExceptionImpl(
                DOMException.INVALID_ACCESS_ERR,
                DOMExceptionImpl.RECT_ERROR);
    }

    @Override
    public RGBColor getRGBColorValue() throws DOMException {
        if (value_ instanceof RGBColor) {
            return (RGBColor) value_;
        }

        throw new DOMExceptionImpl(
            DOMException.INVALID_ACCESS_ERR,
            DOMExceptionImpl.RGBCOLOR_ERROR);
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getLength() {
        if (value_ instanceof List) {
            return ((List<CSSValue>) value_).size();
        }
        return 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CSSValue item(final int index) {
        if (value_ instanceof List) {
            final List<CSSValue> list = (List<CSSValue>) value_;
            return list.get(index);
        }
        return null;
    }

    @Override
    public String toString() {
        return getCssText();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSValue)) {
            return false;
        }
        final CSSValue cv = (CSSValue) obj;
        // TODO to be improved!
        return super.equals(obj)
            && (getCssValueType() == cv.getCssValueType())
            && LangUtils.equals(getCssText(), cv.getCssText());
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = LangUtils.hashCode(hash, value_);
        return hash;
    }

}
