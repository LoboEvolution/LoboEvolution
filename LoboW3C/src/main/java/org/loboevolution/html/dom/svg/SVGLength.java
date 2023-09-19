/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.svg;


import org.htmlunit.cssparser.dom.DOMException;

/**
 * <p>SVGLength interface.</p>
 *
 *
 *
 */
public interface SVGLength {
	// Length Unit Types
	/** Constant SVG_LENGTHTYPE_UNKNOWN=0 */
    short SVG_LENGTHTYPE_UNKNOWN = 0;
	/** Constant SVG_LENGTHTYPE_NUMBER=1 */
    short SVG_LENGTHTYPE_NUMBER = 1;
	/** Constant SVG_LENGTHTYPE_PERCENTAGE=2 */
    short SVG_LENGTHTYPE_PERCENTAGE = 2;
	/** Constant SVG_LENGTHTYPE_EMS=3 */
    short SVG_LENGTHTYPE_EMS = 3;
	/** Constant SVG_LENGTHTYPE_EXS=4 */
    short SVG_LENGTHTYPE_EXS = 4;
	/** Constant SVG_LENGTHTYPE_PX=5 */
    short SVG_LENGTHTYPE_PX = 5;
	/** Constant SVG_LENGTHTYPE_CM=6 */
    short SVG_LENGTHTYPE_CM = 6;
	/** Constant SVG_LENGTHTYPE_MM=7 */
    short SVG_LENGTHTYPE_MM = 7;
	/** Constant SVG_LENGTHTYPE_IN=8 */
    short SVG_LENGTHTYPE_IN = 8;
	/** Constant SVG_LENGTHTYPE_PT=9 */
    short SVG_LENGTHTYPE_PT = 9;
	/** Constant SVG_LENGTHTYPE_PC=10 */
    short SVG_LENGTHTYPE_PC = 10;

	/**
	 * <p>getUnitType.</p>
	 *
	 * @return a short.
	 */
	short getUnitType();

	/**
	 * <p>getValue.</p>
	 *
	 * @return a float.
	 */
	float getValue();

	/**
	 * <p>setValue.</p>
	 *
	 * @param value a float.
	 * @throws DOMException if any.
	 */
	void setValue(float value);

	/**
	 * <p>getValueInSpecifiedUnits.</p>
	 *
	 * @return a float.
	 */
	float getValueInSpecifiedUnits();

	/**
	 * <p>setValueInSpecifiedUnits.</p>
	 *
	 * @param valueInSpecifiedUnits a float.
	 * @throws DOMException if any.
	 */
	void setValueInSpecifiedUnits(float valueInSpecifiedUnits);

	/**
	 * <p>getValueAsString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getValueAsString();

	/**
	 * <p>setValueAsString.</p>
	 *
	 * @param valueAsString a {@link java.lang.String} object.
	 * @throws DOMException if any.
	 */
	void setValueAsString(String valueAsString);

	/**
	 * <p>newValueSpecifiedUnits.</p>
	 *
	 * @param unitType a short.
	 * @param valueInSpecifiedUnits a float.
	 */
	void newValueSpecifiedUnits(short unitType, float valueInSpecifiedUnits);

	/**
	 * <p>convertToSpecifiedUnits.</p>
	 *
	 * @param unitType a short.
	 */
	void convertToSpecifiedUnits(short unitType);
}
