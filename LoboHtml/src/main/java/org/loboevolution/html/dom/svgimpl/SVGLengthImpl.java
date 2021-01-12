/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.dom.svgimpl;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import org.loboevolution.html.dom.svg.SVGLength;
import org.w3c.dom.DOMException;

/**
 * <p>SVGLengthImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGLengthImpl implements SVGLength {

	private short unitType;
	private float valueInSpecifiedUnits;

	/**
	 * <p>Constructor for SVGLengthImpl.</p>
	 */
	public SVGLengthImpl() {
		this.unitType = SVGLength.SVG_LENGTHTYPE_UNKNOWN;
		this.valueInSpecifiedUnits = 0.0f;
	}

	/**
	 * <p>Constructor for SVGLengthImpl.</p>
	 *
	 * @param unitType a short.
	 */
	public SVGLengthImpl(short unitType) {
		this.unitType = unitType;
		this.valueInSpecifiedUnits = 0.0f;
	}

	/**
	 * <p>Constructor for SVGLengthImpl.</p>
	 *
	 * @param unitType a short.
	 * @param valueInSpecifiedUnits a float.
	 */
	public SVGLengthImpl(short unitType, float valueInSpecifiedUnits) {
		this.unitType = unitType;
		this.valueInSpecifiedUnits = valueInSpecifiedUnits;
	}

	/**
	 * <p>Constructor for SVGLengthImpl.</p>
	 *
	 * @param valueAsString a {@link java.lang.String} object.
	 */
	public SVGLengthImpl(String valueAsString) {
		setValueAsString(valueAsString);
	}

	/**
	 * <p>Constructor for SVGLengthImpl.</p>
	 *
	 * @param valueInSpecifiedUnits a float.
	 */
	public SVGLengthImpl(float valueInSpecifiedUnits) {
		this.unitType = SVGLength.SVG_LENGTHTYPE_NUMBER;
		this.valueInSpecifiedUnits = valueInSpecifiedUnits;
	}

	/** {@inheritDoc} */
	@Override
	public short getUnitType() {
		return this.unitType;
	}

	/** {@inheritDoc} */
	@Override
	public float getValue() {
		convertToSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER);
		return valueInSpecifiedUnits;
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(float value) {
		this.valueInSpecifiedUnits = value;
		this.unitType = SVGLength.SVG_LENGTHTYPE_NUMBER;
	}

	/** {@inheritDoc} */
	@Override
	public String getValueAsString() {
		String suffix = getUnitTypeAsString(this.unitType);
		if ("unkown".equals(suffix)) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Unknow unit type");
		}
		if (this.valueInSpecifiedUnits == (long) this.valueInSpecifiedUnits) {
			return String.format("%f" + suffix, (long) this.valueInSpecifiedUnits);
		} else {
			return String.format("%s" + suffix, this.valueInSpecifiedUnits);
		}
	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	@Override
	public void newValueSpecifiedUnits(short unitType, float valueInSpecifiedUnits) {

		switch (unitType) {
		case SVGLength.SVG_LENGTHTYPE_CM:
		case SVGLength.SVG_LENGTHTYPE_EMS:
		case SVGLength.SVG_LENGTHTYPE_EXS:
		case SVGLength.SVG_LENGTHTYPE_IN:
		case SVGLength.SVG_LENGTHTYPE_MM:
		case SVGLength.SVG_LENGTHTYPE_NUMBER:
		case SVGLength.SVG_LENGTHTYPE_PC:
		case SVGLength.SVG_LENGTHTYPE_PERCENTAGE:
		case SVGLength.SVG_LENGTHTYPE_PT:
		case SVGLength.SVG_LENGTHTYPE_PX:
			this.unitType = unitType;
			this.valueInSpecifiedUnits = valueInSpecifiedUnits;
			break;
		default:
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid unit type");
		}
	}

	/** {@inheritDoc} */
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
			break;
		case SVGLength.SVG_LENGTHTYPE_MM:
			this.valueInSpecifiedUnits = inchValue * 25.40013f;
			break;
		case SVGLength.SVG_LENGTHTYPE_CM:
			this.valueInSpecifiedUnits = inchValue * 25.4f;
			break;
		case SVGLength.SVG_LENGTHTYPE_PT:
			this.valueInSpecifiedUnits = inchValue * 72.26999f;
			break;
		case SVGLength.SVG_LENGTHTYPE_PX:
			this.valueInSpecifiedUnits = inchValue * 96.0f;
			break;
		case SVGLength.SVG_LENGTHTYPE_EMS:
			this.valueInSpecifiedUnits = inchValue * 7.22699f;
			break;
		case SVGLength.SVG_LENGTHTYPE_PC:
			this.valueInSpecifiedUnits = inchValue * 6.0225f;
			break;
		default:
			this.valueInSpecifiedUnits = inchValue;
			break;
		}
		this.unitType = unitType;
	}

	/** {@inheritDoc} */
	@Override
	public float getValueInSpecifiedUnits() {
		return valueInSpecifiedUnits;
	}

	/** {@inheritDoc} */
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
			break;
		default:
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid unit type");
		}
		return suffix;
	}
	
	
	/**
	 * <p>getTransformedLength.</p>
	 *
	 * @param transform a {@link java.awt.geom.AffineTransform} object.
	 * @return a float.
	 */
	protected float getTransformedLength(AffineTransform transform) {

		if (unitType == SVG_LENGTHTYPE_NUMBER || transform == null || transform != null && transform.isIdentity()) {
			return getValue();
		}

		Point2D q1 = new Point2D.Double(0, 0);
		float val = getValue();
		Point2D q2 = new Point2D.Double(0.7071068 * val, 0.7071068 * val);

		Point2D transQ1 = new Point2D.Double();
		Point2D transQ2 = new Point2D.Double();

		transform.transform(q1, transQ1);
		transform.transform(q2, transQ2);

		double diffX = transQ2.getX() - transQ1.getX();
		double diffY = transQ2.getY() - transQ1.getY();

		float dist = (float) Math.sqrt(diffX * diffX + diffY * diffY);
		return dist;

	}
}
