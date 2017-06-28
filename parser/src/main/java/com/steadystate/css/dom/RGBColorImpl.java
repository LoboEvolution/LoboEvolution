/*
 * Copyright (C) 1999-2017 David Schweinsberg.
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

package com.steadystate.css.dom;

import java.io.Serializable;

import org.w3c.css.sac.LexicalUnit;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.RGBColor;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;

/**
 * Implementation of {@link RGBColor}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David
 *         Schweinsberg</a>
 * @author rbri
 */
public class RGBColorImpl implements RGBColor, CSSFormatable, Serializable {

	private static final long serialVersionUID = 8152675334081993160L;
	private CSSPrimitiveValue red_;
	private CSSPrimitiveValue green_;
	private CSSPrimitiveValue blue_;

	/**
	 * Constructor that reads the values from the given chain of LexicalUnits.
	 * 
	 * @param lu
	 *            the values
	 * @throws DOMException
	 *             in case of error
	 */
	public RGBColorImpl(final LexicalUnit lu) throws DOMException {
		LexicalUnit next = lu;
		red_ = new CSSValueImpl(next, true);
		next = next.getNextLexicalUnit(); // ,
		if (next != null) {
			if (next.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_COMMA) {
				// error
				throw new DOMException(DOMException.SYNTAX_ERR, "rgb parameters must be separated by ','.");
			}
			next = next.getNextLexicalUnit();
			if (next != null) {
				green_ = new CSSValueImpl(next, true);
				next = next.getNextLexicalUnit(); // ,
				if (next != null) {
					if (next.getLexicalUnitType() != LexicalUnit.SAC_OPERATOR_COMMA) {
						// error
						throw new DOMException(DOMException.SYNTAX_ERR, "rgb parameters must be separated by ','.");
					}
					next = next.getNextLexicalUnit();
					blue_ = new CSSValueImpl(next, true);
					next = next.getNextLexicalUnit();
					if (next != null) {
						// error
						throw new DOMException(DOMException.SYNTAX_ERR, "Too many parameters for rgb function.");
					}
				}
			}
		}
	}

	/**
	 * Constructor. The values for the colors are null.
	 */
	public RGBColorImpl() {
		super();
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
	 * 
	 * @param red
	 *            the new CSSPrimitiveValue
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
	 * 
	 * @param green
	 *            the new CSSPrimitiveValue
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
	 * 
	 * @param blue
	 *            the new CSSPrimitiveValue
	 */
	public void setBlue(final CSSPrimitiveValue blue) {
		blue_ = blue;
	}

	/**
	 * Same as {@link #getCssText(CSSFormat)} but using the default format.
	 *
	 * @return the formated string
	 */
	public String getCssText() {
		return getCssText(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCssText(final CSSFormat format) {
		final StringBuilder sb = new StringBuilder();
		if (null != format && format.isRgbAsHex()) {
			sb.append("#").append(getColorAsHex(red_)).append(getColorAsHex(green_)).append(getColorAsHex(blue_));
			return sb.toString();
		}

		sb.append("rgb(").append(red_).append(", ").append(green_).append(", ").append(blue_).append(")");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getCssText(null);
	}

	private String getColorAsHex(final CSSPrimitiveValue color) {
		return String.format("%02x", Math.round(color.getFloatValue(LexicalUnit.SAC_INTEGER)));
	}
}
