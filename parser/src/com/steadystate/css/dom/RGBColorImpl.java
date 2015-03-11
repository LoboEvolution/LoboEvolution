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

import java.io.Serializable;

import org.w3c.css.sac.LexicalUnit;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.RGBColor;


/**
 * Implementation of {@link RGBColor}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 */
public class RGBColorImpl implements RGBColor, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8152675334081993160L;
    
    /** The red_. */
    private CSSPrimitiveValue red_;
    
    /** The green_. */
    private CSSPrimitiveValue green_;
    
    /** The blue_. */
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
            if (next.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_COMMA) {
                // error
                throw new DOMException(DOMException.SYNTAX_ERR,
                    "rgb parameters must be separated by ','.");
            }
            next = next.getNextLexicalUnit();
            if (next != null) {
                green_ = new CSSValueImpl(next, true);
                next = next.getNextLexicalUnit();   // ,
                if (next != null) {
                    if (next.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_COMMA) {
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
     * Constructor.
     * The values for the colors are null.
     */
    public RGBColorImpl() {
        super();
    }

    /**
     * Returns the red part.
     *
     * @return the red
     */
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
     *
     * @return the green
     */
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
     *
     * @return the blue
     */
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
        return new StringBuilder("rgb(")
            .append(red_).append(", ")
            .append(green_).append(", ")
            .append(blue_).append(")").toString();
    }
}
