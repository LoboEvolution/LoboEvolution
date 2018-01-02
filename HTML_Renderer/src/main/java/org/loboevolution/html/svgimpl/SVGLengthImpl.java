/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.loboevolution.html.svgimpl;

import org.loboevolution.w3c.svg.SVGLength;
import org.w3c.dom.DOMException;

public class SVGLengthImpl implements SVGLength {

	private short unitType;
	private float valueInSpecifiedUnits;

	public SVGLengthImpl() {
		this.unitType = SVGLength.SVG_LENGTHTYPE_UNKNOWN;
		this.valueInSpecifiedUnits = 0.0f;
	}

	public SVGLengthImpl(short unitType) {
		this.unitType = unitType;
		this.valueInSpecifiedUnits = 0.0f;
	}

	public SVGLengthImpl(short unitType, float valueInSpecifiedUnits) {
		this.unitType = unitType;
		this.valueInSpecifiedUnits = valueInSpecifiedUnits;
	}

	public SVGLengthImpl(String valueAsString) {
		setValueAsString(valueAsString);
	}

	public SVGLengthImpl(float valueInSpecifiedUnits) {
		this.unitType = SVGLength.SVG_LENGTHTYPE_NUMBER;
		this.valueInSpecifiedUnits = valueInSpecifiedUnits;
	}

	@Override
	public short getUnitType() {
		return this.unitType;
	}

	@Override
	public float getValue() {
		convertToSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER);
		return valueInSpecifiedUnits;
	}

	@Override
	public void setValue(float value) {
		this.valueInSpecifiedUnits = value;
		this.unitType = SVGLength.SVG_LENGTHTYPE_NUMBER;
	}

	@Override
	public String getValueAsString() {
		String suffix = getUnitTypeAsString(this.unitType);
		if (suffix.equals("unkown")) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Unknow unit type");
		}
		if (this.valueInSpecifiedUnits == (long) this.valueInSpecifiedUnits) {
			return String.format("%f" + suffix, (long) this.valueInSpecifiedUnits);
		} else {
			return String.format("%s" + suffix, this.valueInSpecifiedUnits);
		}
	}

	@Override
	public void setValueAsString(String valueAsString) {

		if (valueAsString == null) {
			valueAsString = "0";
		}

		String valueString = null;
		if (valueAsString.endsWith("cm")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 2);
			this.unitType = SVGLength.SVG_LENGTHTYPE_CM;
		} else if (valueAsString.endsWith("ems")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 3);
			this.unitType = SVGLength.SVG_LENGTHTYPE_EMS;
		} else if (valueAsString.endsWith("exs")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 3);
			this.unitType = SVGLength.SVG_LENGTHTYPE_EXS;
		} else if (valueAsString.endsWith("in")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 2);
			this.unitType = SVGLength.SVG_LENGTHTYPE_IN;
		} else if (valueAsString.endsWith("mm")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 2);
			this.unitType = SVGLength.SVG_LENGTHTYPE_MM;
		} else if (valueAsString.endsWith("pc")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 2);
			this.unitType = SVGLength.SVG_LENGTHTYPE_PC;
		} else if (valueAsString.endsWith("%")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 1);
			this.unitType = SVGLength.SVG_LENGTHTYPE_PERCENTAGE;
		} else if (valueAsString.endsWith("pt")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 2);
			this.unitType = SVGLength.SVG_LENGTHTYPE_PT;
		} else if (valueAsString.endsWith("px")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 2);
			this.unitType = SVGLength.SVG_LENGTHTYPE_PX;
		} else {
			valueString = valueAsString;
			this.unitType = SVGLength.SVG_LENGTHTYPE_NUMBER;
		}

		try {
			this.valueInSpecifiedUnits = Float.parseFloat(valueString);
		} catch (NumberFormatException e) {
			throw new DOMException(DOMException.SYNTAX_ERR, "Invalid value: '" + valueString + "'!");
		}
	}

	@Override
	public void newValueSpecifiedUnits(short unitType, float valueInSpecifiedUnits) {
		if (unitType != SVGLength.SVG_LENGTHTYPE_CM && unitType != SVGLength.SVG_LENGTHTYPE_EMS
				&& unitType != SVGLength.SVG_LENGTHTYPE_EXS && unitType != SVGLength.SVG_LENGTHTYPE_IN
				&& unitType != SVGLength.SVG_LENGTHTYPE_MM && unitType != SVGLength.SVG_LENGTHTYPE_NUMBER
				&& unitType != SVGLength.SVG_LENGTHTYPE_PC && unitType != SVGLength.SVG_LENGTHTYPE_PERCENTAGE
				&& unitType != SVGLength.SVG_LENGTHTYPE_PT && unitType != SVGLength.SVG_LENGTHTYPE_PX) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid unit type");
		}
		this.unitType = unitType;
		this.valueInSpecifiedUnits = valueInSpecifiedUnits;
	}

	@Override
	public void convertToSpecifiedUnits(short unitType) {

		if (this.unitType == unitType) {
			return;
		}

		float inchValue = this.valueInSpecifiedUnits;
		switch (this.unitType) {
		case SVGLength.SVG_LENGTHTYPE_IN:
			break;
		case SVGLength.SVG_LENGTHTYPE_CM:
			inchValue = this.valueInSpecifiedUnits / 2.54f;
			break;
		case SVGLength.SVG_LENGTHTYPE_MM:
			inchValue = this.valueInSpecifiedUnits / 25.40013f;
			break;
		case SVGLength.SVG_LENGTHTYPE_PT:
			inchValue = this.valueInSpecifiedUnits / 72.26999f;
			break;
		case SVGLength.SVG_LENGTHTYPE_PX:
			inchValue = this.valueInSpecifiedUnits / 96.0f;
			break;
		case SVGLength.SVG_LENGTHTYPE_EMS:
			inchValue = this.valueInSpecifiedUnits / 7.22699f;
			break;
		case SVGLength.SVG_LENGTHTYPE_PC:
			inchValue = this.valueInSpecifiedUnits / 6.0225f;
			break;
		default:
			break;
		}

		switch (unitType) {
		case SVGLength.SVG_LENGTHTYPE_IN:
			this.valueInSpecifiedUnits = inchValue;
		case SVGLength.SVG_LENGTHTYPE_MM:
			this.valueInSpecifiedUnits = inchValue * 25.40013f;
		case SVGLength.SVG_LENGTHTYPE_CM:
			this.valueInSpecifiedUnits = inchValue * 25.4f;
		case SVGLength.SVG_LENGTHTYPE_PT:
			this.valueInSpecifiedUnits = inchValue * 72.26999f;
		case SVGLength.SVG_LENGTHTYPE_PX:
			this.valueInSpecifiedUnits = inchValue * 96.0f;
		case SVGLength.SVG_LENGTHTYPE_EMS:
			this.valueInSpecifiedUnits = inchValue * 7.22699f;
		case SVGLength.SVG_LENGTHTYPE_PC:
			this.valueInSpecifiedUnits = inchValue * 6.0225f;
		default:
			this.valueInSpecifiedUnits = inchValue;
		}
		this.unitType = unitType;
	}

	@Override
	public float getValueInSpecifiedUnits() {
		return valueInSpecifiedUnits;
	}

	@Override
	public void setValueInSpecifiedUnits(float valueInSpecifiedUnits) {
		this.valueInSpecifiedUnits = valueInSpecifiedUnits;
	}

	private String getUnitTypeAsString(short unitType) {
		String suffix = "";
		switch (unitType) {
		case SVGLength.SVG_LENGTHTYPE_CM:
			suffix = "cm";
			break;
		case SVGLength.SVG_LENGTHTYPE_EMS:
			suffix = "ems";
			break;
		case SVGLength.SVG_LENGTHTYPE_EXS:
			suffix = "exs";
			break;
		case SVGLength.SVG_LENGTHTYPE_IN:
			suffix = "in";
			break;
		case SVGLength.SVG_LENGTHTYPE_MM:
			suffix = "mm";
			break;
		case SVGLength.SVG_LENGTHTYPE_PC:
			suffix = "pc";
			break;
		case SVGLength.SVG_LENGTHTYPE_PERCENTAGE:
			suffix = "%";
			break;
		case SVGLength.SVG_LENGTHTYPE_PT:
			suffix = "pt";
			break;
		case SVGLength.SVG_LENGTHTYPE_PX:
			suffix = "px";
			break;
		case SVGLength.SVG_LENGTHTYPE_NUMBER:
			suffix = "";
			break;
		case SVGLength.SVG_LENGTHTYPE_UNKNOWN:
			suffix = "unknown";
		default:
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid unit type");
		}
		return suffix;
	}

}
