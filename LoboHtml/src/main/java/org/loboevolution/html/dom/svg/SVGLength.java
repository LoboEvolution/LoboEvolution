/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.svg;

import org.w3c.dom.DOMException;

/**
 * <p>SVGLength interface.</p>
 *
 * @author utente
 * @version $Id: $Id
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
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setValue(float value) throws DOMException;

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
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setValueInSpecifiedUnits(float valueInSpecifiedUnits) throws DOMException;

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
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setValueAsString(String valueAsString) throws DOMException;

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
