package org.lobobrowser.html.svgimpl;

import org.lobobrowser.w3c.svg.SVGLength;
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
		convertToSpecifiedUnits(SVGLengthImpl.SVG_LENGTHTYPE_NUMBER);
		return valueInSpecifiedUnits;
	}
	
	@Override
	public void setValue(float value) {
		this.valueInSpecifiedUnits = value;
		this.unitType = SVGLengthImpl.SVG_LENGTHTYPE_NUMBER;
	}

	@Override
	public String getValueAsString() {
		String suffix = getUnitTypeAsString(this.unitType);
		if (suffix.equals("unkown"))
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Unknow unit type");
		if (this.valueInSpecifiedUnits == (long) this.valueInSpecifiedUnits)
			return String.format("%f" + suffix, (long) this.valueInSpecifiedUnits);
		else
			return String.format("%s" + suffix, this.valueInSpecifiedUnits);
	}
	
	@Override
	public void setValueAsString(String valueAsString) {
		
		if(valueAsString == null)
			valueAsString = "0";

		String valueString = null;
		if (valueAsString.endsWith("cm")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 2);
			this.unitType = SVGLengthImpl.SVG_LENGTHTYPE_CM;
		} else if (valueAsString.endsWith("ems")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 3);
			this.unitType = SVGLengthImpl.SVG_LENGTHTYPE_EMS;
		} else if (valueAsString.endsWith("exs")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 3);
			this.unitType = SVGLengthImpl.SVG_LENGTHTYPE_EXS;
		} else if (valueAsString.endsWith("in")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 2);
			this.unitType = SVGLengthImpl.SVG_LENGTHTYPE_IN;
		} else if (valueAsString.endsWith("mm")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 2);
			this.unitType = SVGLengthImpl.SVG_LENGTHTYPE_MM;
		} else if (valueAsString.endsWith("pc")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 2);
			this.unitType = SVGLengthImpl.SVG_LENGTHTYPE_PC;
		} else if (valueAsString.endsWith("%")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 1);
			this.unitType = SVGLengthImpl.SVG_LENGTHTYPE_PERCENTAGE;
		} else if (valueAsString.endsWith("pt")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 2);
			this.unitType = SVGLengthImpl.SVG_LENGTHTYPE_PT;
		} else if (valueAsString.endsWith("px")) {
			valueString = valueAsString.substring(0, valueAsString.length() - 2);
			this.unitType = SVGLengthImpl.SVG_LENGTHTYPE_PX;
		} else {
			valueString = valueAsString;
			this.unitType = SVGLengthImpl.SVG_LENGTHTYPE_NUMBER;
		}

		try {
			this.valueInSpecifiedUnits = Float.parseFloat(valueString);
		} catch (NumberFormatException e) {
			throw new DOMException(DOMException.SYNTAX_ERR, "Invalid value: '" + valueString + "'!");
		}
	}
	
	@Override
	public void newValueSpecifiedUnits(short unitType, float valueInSpecifiedUnits) {
		if (unitType != SVGLengthImpl.SVG_LENGTHTYPE_CM && unitType != SVGLengthImpl.SVG_LENGTHTYPE_EMS
				&& unitType != SVGLengthImpl.SVG_LENGTHTYPE_EXS && unitType != SVGLengthImpl.SVG_LENGTHTYPE_IN
				&& unitType != SVGLengthImpl.SVG_LENGTHTYPE_MM && unitType != SVGLengthImpl.SVG_LENGTHTYPE_NUMBER
				&& unitType != SVGLengthImpl.SVG_LENGTHTYPE_PC && unitType != SVGLengthImpl.SVG_LENGTHTYPE_PERCENTAGE
				&& unitType != SVGLengthImpl.SVG_LENGTHTYPE_PT && unitType != SVGLengthImpl.SVG_LENGTHTYPE_PX) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid unit type");
		}
		this.unitType = unitType;
		this.valueInSpecifiedUnits = valueInSpecifiedUnits;
	}

	@Override
	public void convertToSpecifiedUnits(short unitType) {

		if (this.unitType == unitType)
			return;
		
		float inchValue = this.valueInSpecifiedUnits;
		switch (this.unitType) {
		case SVGLengthImpl.SVG_LENGTHTYPE_IN:
			break;
		case SVGLengthImpl.SVG_LENGTHTYPE_CM:
			inchValue = this.valueInSpecifiedUnits / 2.54f;
			break;
		case SVGLengthImpl.SVG_LENGTHTYPE_MM:
			inchValue = this.valueInSpecifiedUnits / 25.40013f;
			break;
		case SVGLengthImpl.SVG_LENGTHTYPE_PT:
			inchValue = this.valueInSpecifiedUnits / 72.26999f;
			break;
		case SVGLengthImpl.SVG_LENGTHTYPE_PX:
			inchValue = this.valueInSpecifiedUnits / 96.0f;
			break;
		case SVGLengthImpl.SVG_LENGTHTYPE_EMS:
			inchValue = this.valueInSpecifiedUnits / 7.22699f;
			break;
		case SVGLengthImpl.SVG_LENGTHTYPE_PC:
			inchValue = this.valueInSpecifiedUnits / 6.0225f;
			break;
		default:
			break;
		}

		switch (unitType) {
		case SVGLengthImpl.SVG_LENGTHTYPE_IN:
			this.valueInSpecifiedUnits = inchValue;
		case SVGLengthImpl.SVG_LENGTHTYPE_MM:
			this.valueInSpecifiedUnits = inchValue * 25.40013f;
		case SVGLengthImpl.SVG_LENGTHTYPE_CM:
			this.valueInSpecifiedUnits = inchValue * 25.4f;
		case SVGLengthImpl.SVG_LENGTHTYPE_PT:
			this.valueInSpecifiedUnits = inchValue * 72.26999f;
		case SVGLengthImpl.SVG_LENGTHTYPE_PX:
			this.valueInSpecifiedUnits = inchValue * 96.0f;
		case SVGLengthImpl.SVG_LENGTHTYPE_EMS:
			this.valueInSpecifiedUnits = inchValue * 7.22699f;
		case SVGLengthImpl.SVG_LENGTHTYPE_PC:
			this.valueInSpecifiedUnits = inchValue * 6.0225f;
		default:
			this.valueInSpecifiedUnits = inchValue;
		}
		this.unitType = unitType;
	}
	
	public float getValueInSpecifiedUnits() {
		return valueInSpecifiedUnits;
	}

	public void setValueInSpecifiedUnits(float valueInSpecifiedUnits) {
		this.valueInSpecifiedUnits = valueInSpecifiedUnits;
	}

	private String getUnitTypeAsString(short unitType) {
		String suffix = "";
		switch (unitType) {
		case SVGLengthImpl.SVG_LENGTHTYPE_CM:
			suffix = "cm";
			break;
		case SVGLengthImpl.SVG_LENGTHTYPE_EMS:
			suffix = "ems";
			break;
		case SVGLengthImpl.SVG_LENGTHTYPE_EXS:
			suffix = "exs";
			break;
		case SVGLengthImpl.SVG_LENGTHTYPE_IN:
			suffix = "in";
			break;
		case SVGLengthImpl.SVG_LENGTHTYPE_MM:
			suffix = "mm";
			break;
		case SVGLengthImpl.SVG_LENGTHTYPE_PC:
			suffix = "pc";
			break;
		case SVGLengthImpl.SVG_LENGTHTYPE_PERCENTAGE:
			suffix = "%";
			break;
		case SVGLengthImpl.SVG_LENGTHTYPE_PT:
			suffix = "pt";
			break;
		case SVGLengthImpl.SVG_LENGTHTYPE_PX:
			suffix = "px";
			break;
		case SVGLengthImpl.SVG_LENGTHTYPE_NUMBER:
			suffix = "";
			break;
		case SVGLengthImpl.SVG_LENGTHTYPE_UNKNOWN:
			suffix = "unknown";
		default:
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid unit type");
		}
		return suffix;
	}

}
