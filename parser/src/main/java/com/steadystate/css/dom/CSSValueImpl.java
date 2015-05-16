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

package com.steadystate.css.dom;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.LexicalUnit;
import org.w3c.css.sac.Locator;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSValueList;
import org.w3c.dom.css.Counter;
import org.w3c.dom.css.RGBColor;
import org.w3c.dom.css.Rect;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.LexicalUnitImpl;
import com.steadystate.css.userdata.UserDataConstants;
import com.steadystate.css.util.LangUtils;

/**
 * The <code>CSSValueImpl</code> class can represent either a
 * <code>CSSPrimitiveValue</code> or a <code>CSSValueList</code> so that
 * the type can successfully change when using <code>setCssText</code>.
 *
 * TODO:
 * Float unit conversions,
 * A means of checking valid primitive types for properties
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class CSSValueImpl extends CSSOMObjectImpl implements CSSPrimitiveValue, CSSValueList {

    private static final long serialVersionUID = 406281136418322579L;

    private Object value_;

    public Object getValue() {
        return value_;
    }

    public void setValue(final Object value) {
        value_ = value;
    }

    /**
     * Constructor
     */
    public CSSValueImpl(final LexicalUnit value, final boolean forcePrimitive) {
        LexicalUnit parameters = null;
        try {
            parameters = value.getParameters();
        }
        catch (final IllegalStateException e) {
            // Batik SAC parser throws IllegalStateException in some cases
        }

        if (!forcePrimitive && (value.getNextLexicalUnit() != null)) {
            value_ = getValues(value);
        }
        else if (parameters != null) {
            if (value.getLexicalUnitType() == LexicalUnit.SAC_RECT_FUNCTION) {
                // Rect
                value_ = new RectImpl(value.getParameters());
            }
            else if (value.getLexicalUnitType() == LexicalUnit.SAC_RGBCOLOR) {
                // RGBColor
                value_ = new RGBColorImpl(value.getParameters());
            }
            else if (value.getLexicalUnitType() == LexicalUnit.SAC_COUNTER_FUNCTION) {
                // Counter
                value_ = new CounterImpl(false, value.getParameters());
            }
            else if (value.getLexicalUnitType() == LexicalUnit.SAC_COUNTERS_FUNCTION) {
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

        if (value instanceof LexicalUnitImpl) {
            final Locator locator = ((LexicalUnitImpl) value).getLocator();
            if (locator != null) {
                setUserData(UserDataConstants.KEY_LOCATOR, locator);
            }
        }
    }

    public CSSValueImpl() {
        super();
    }

    private List<CSSValueImpl> getValues(final LexicalUnit value) {
        // We need to be a CSSValueList
        // Values in an "expr" can be seperated by "operator"s, which are
        // either '/' or ',' - ignore these operators
        final List<CSSValueImpl> values = new ArrayList<CSSValueImpl>();
        LexicalUnit lu = value;
        while (lu != null) {
            if ((lu.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_COMMA)
                && (lu.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_SLASH)) {
                values.add(new CSSValueImpl(lu, true));
            }
            lu = lu.getNextLexicalUnit();
        }
        return values;
    }

    public CSSValueImpl(final LexicalUnit value) {
        this(value, false);
    }

    public String getCssText() {
        return getCssText(null);
    }

    /**
     * Returns a string representation of the rule based on the given format.
     * If provided format is null, the result is the same as getCssText()
     *
     * @param format the formating rules
     * @return the formated string
     */
    public String getCssText(final CSSFormat format) {
        if (getCssValueType() == CSS_VALUE_LIST) {

            // Create the string from the LexicalUnits so we include the correct
            // operators in the string
            final StringBuilder sb = new StringBuilder();
            final List<?> list = (List<?>) value_;
            final Iterator<?> it = list.iterator();
            while (it.hasNext()) {
                final Object o = it.next();
                final CSSValueImpl cssValue = (CSSValueImpl) o;
                if (cssValue.value_ instanceof LexicalUnit) {
                    LexicalUnit lu = (LexicalUnit) cssValue.value_;
                    sb.append(lu.toString());

                    // Step to the next lexical unit, determining what spacing we
                    // need to put around the operators
                    final LexicalUnit prev = lu;
                    lu = lu.getNextLexicalUnit();
                    if ((lu != null)
                            && ((lu.getLexicalUnitType() == LexicalUnit.SAC_OPERATOR_COMMA)
                            || (lu.getLexicalUnitType() == LexicalUnit.SAC_OPERATOR_SLASH)
                            || (prev.getLexicalUnitType() == LexicalUnit.SAC_OPERATOR_SLASH)
                        )) {
                        sb.append(lu.toString());
                    }
                }
                else {
                    sb.append(o);
                }
                if (it.hasNext()) {
                    sb.append(" ");
                }
            }
            return sb.toString();
        }
        return value_ != null ? value_.toString() : "";
    }

    public void setCssText(final String cssText) throws DOMException {
        try {
            final InputSource is = new InputSource(new StringReader(cssText));
            final CSSOMParser parser = new CSSOMParser();
            final CSSValueImpl v2 = (CSSValueImpl) parser.parsePropertyValue(is);
            value_ = v2.value_;
            setUserDataMap(v2.getUserDataMap());
        }
        catch (final Exception e) {
            throw new DOMExceptionImpl(
                DOMException.SYNTAX_ERR,
                DOMExceptionImpl.SYNTAX_ERROR,
                e.getMessage());
        }
    }

    public short getCssValueType() {
        if (value_ instanceof List) {
            return CSS_VALUE_LIST;
        }
        if ((value_ instanceof LexicalUnit)
                && (((LexicalUnit) value_).getLexicalUnitType() == LexicalUnit.SAC_INHERIT)) {
            return CSS_INHERIT;
        }
        return CSS_PRIMITIVE_VALUE;
    }

    public short getPrimitiveType() {
        if (value_ instanceof LexicalUnit) {
            final LexicalUnit lu = (LexicalUnit) value_;
            switch (lu.getLexicalUnitType()) {
                case LexicalUnit.SAC_INHERIT:
                    return CSS_IDENT;
                case LexicalUnit.SAC_INTEGER:
                case LexicalUnit.SAC_REAL:
                    return CSS_NUMBER;
                case LexicalUnit.SAC_EM:
                    return CSS_EMS;
                case LexicalUnit.SAC_EX:
                    return CSS_EXS;
                case LexicalUnit.SAC_PIXEL:
                    return CSS_PX;
                case LexicalUnit.SAC_INCH:
                    return CSS_IN;
                case LexicalUnit.SAC_CENTIMETER:
                    return CSS_CM;
                case LexicalUnit.SAC_MILLIMETER:
                    return CSS_MM;
                case LexicalUnit.SAC_POINT:
                    return CSS_PT;
                case LexicalUnit.SAC_PICA:
                    return CSS_PC;
                case LexicalUnit.SAC_PERCENTAGE:
                    return CSS_PERCENTAGE;
                case LexicalUnit.SAC_URI:
                    return CSS_URI;
                case LexicalUnit.SAC_COUNTER_FUNCTION:
    //            case LexicalUnit.SAC_COUNTERS_FUNCTION:
                    return CSS_COUNTER;
    //            case LexicalUnit.SAC_RGBCOLOR:
    //                return CSS_RGBCOLOR;
                case LexicalUnit.SAC_DEGREE:
                    return CSS_DEG;
                case LexicalUnit.SAC_GRADIAN:
                    return CSS_GRAD;
                case LexicalUnit.SAC_RADIAN:
                    return CSS_RAD;
                case LexicalUnit.SAC_MILLISECOND:
                    return CSS_MS;
                case LexicalUnit.SAC_SECOND:
                    return CSS_S;
                case LexicalUnit.SAC_HERTZ:
                    return CSS_HZ;
                case LexicalUnit.SAC_KILOHERTZ:
                    return CSS_KHZ;
                case LexicalUnit.SAC_IDENT:
                    return CSS_IDENT;
                case LexicalUnit.SAC_STRING_VALUE:
                    return CSS_STRING;
                case LexicalUnit.SAC_ATTR:
                    return CSS_ATTR;
    //            case LexicalUnit.SAC_RECT_FUNCTION:
    //                return CSS_RECT;
                case LexicalUnit.SAC_UNICODERANGE:
                case LexicalUnit.SAC_SUB_EXPRESSION:
                case LexicalUnit.SAC_FUNCTION:
                    return CSS_STRING;
                case LexicalUnit.SAC_DIMENSION:
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

    public void setFloatValue(final short unitType, final float floatValue) throws DOMException {
        value_ = LexicalUnitImpl.createNumber(null, floatValue);
    }

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
     * TODO: return a value for a list type
     */
    public String getStringValue() throws DOMException {
        if (value_ instanceof LexicalUnit) {
            final LexicalUnit lu = (LexicalUnit) value_;
            if ((lu.getLexicalUnitType() == LexicalUnit.SAC_IDENT)
                || (lu.getLexicalUnitType() == LexicalUnit.SAC_STRING_VALUE)
                || (lu.getLexicalUnitType() == LexicalUnit.SAC_URI)
                || (lu.getLexicalUnitType() == LexicalUnit.SAC_INHERIT)
                || (lu.getLexicalUnitType() == LexicalUnit.SAC_ATTR)) {
                return lu.getStringValue();
            }
        }
        else if (value_ instanceof List) {
            return null;
        }

        throw new DOMExceptionImpl(
            DOMException.INVALID_ACCESS_ERR,
            DOMExceptionImpl.STRING_ERROR);
    }

    public Counter getCounterValue() throws DOMException {
        if (value_ instanceof Counter) {
            return (Counter) value_;
        }

        throw new DOMExceptionImpl(
                DOMException.INVALID_ACCESS_ERR,
                DOMExceptionImpl.COUNTER_ERROR);
    }

    public Rect getRectValue() throws DOMException {
        if (value_ instanceof Rect) {
            return (Rect) value_;
        }

        throw new DOMExceptionImpl(
                DOMException.INVALID_ACCESS_ERR,
                DOMExceptionImpl.RECT_ERROR);
    }

    public RGBColor getRGBColorValue() throws DOMException {
        if (value_ instanceof RGBColor) {
            return (RGBColor) value_;
        }

        throw new DOMExceptionImpl(
            DOMException.INVALID_ACCESS_ERR,
            DOMExceptionImpl.RGBCOLOR_ERROR);
    }

    @SuppressWarnings("unchecked")
    public int getLength() {
        if (value_ instanceof List) {
            return ((List<CSSValue>) value_).size();
        }
        return 0;
    }

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
        return getCssText(null);
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
