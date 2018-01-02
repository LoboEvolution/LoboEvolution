/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.w3c.svg;

import org.w3c.dom.DOMException;

public interface SVGLength {
	// Length Unit Types
	public static final short SVG_LENGTHTYPE_UNKNOWN = 0;
	public static final short SVG_LENGTHTYPE_NUMBER = 1;
	public static final short SVG_LENGTHTYPE_PERCENTAGE = 2;
	public static final short SVG_LENGTHTYPE_EMS = 3;
	public static final short SVG_LENGTHTYPE_EXS = 4;
	public static final short SVG_LENGTHTYPE_PX = 5;
	public static final short SVG_LENGTHTYPE_CM = 6;
	public static final short SVG_LENGTHTYPE_MM = 7;
	public static final short SVG_LENGTHTYPE_IN = 8;
	public static final short SVG_LENGTHTYPE_PT = 9;
	public static final short SVG_LENGTHTYPE_PC = 10;

	public short getUnitType();

	public float getValue();

	public void setValue(float value) throws DOMException;

	public float getValueInSpecifiedUnits();

	public void setValueInSpecifiedUnits(float valueInSpecifiedUnits) throws DOMException;

	public String getValueAsString();

	public void setValueAsString(String valueAsString) throws DOMException;

	public void newValueSpecifiedUnits(short unitType, float valueInSpecifiedUnits);

	public void convertToSpecifiedUnits(short unitType);
}
