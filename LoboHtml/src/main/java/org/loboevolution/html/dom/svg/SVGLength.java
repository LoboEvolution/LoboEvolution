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

public interface SVGLength {
	// Length Unit Types
	static final short SVG_LENGTHTYPE_UNKNOWN = 0;
	static final short SVG_LENGTHTYPE_NUMBER = 1;
	static final short SVG_LENGTHTYPE_PERCENTAGE = 2;
	static final short SVG_LENGTHTYPE_EMS = 3;
	static final short SVG_LENGTHTYPE_EXS = 4;
	static final short SVG_LENGTHTYPE_PX = 5;
	static final short SVG_LENGTHTYPE_CM = 6;
	static final short SVG_LENGTHTYPE_MM = 7;
	static final short SVG_LENGTHTYPE_IN = 8;
	static final short SVG_LENGTHTYPE_PT = 9;
	static final short SVG_LENGTHTYPE_PC = 10;

	short getUnitType();

	float getValue();

	void setValue(float value) throws DOMException;

	float getValueInSpecifiedUnits();

	void setValueInSpecifiedUnits(float valueInSpecifiedUnits) throws DOMException;

	String getValueAsString();

	void setValueAsString(String valueAsString) throws DOMException;

	void newValueSpecifiedUnits(short unitType, float valueInSpecifiedUnits);

	void convertToSpecifiedUnits(short unitType);
}
