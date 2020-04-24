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

public interface SVGAngle {
	// Angle Unit Types
	static final short SVG_ANGLETYPE_UNKNOWN = 0;
	static final short SVG_ANGLETYPE_UNSPECIFIED = 1;
	static final short SVG_ANGLETYPE_DEG = 2;
	static final short SVG_ANGLETYPE_RAD = 3;
	static final short SVG_ANGLETYPE_GRAD = 4;

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
