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

package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAngle;

/**
 * <p>SVGAngleImpl class.</p>
 */
public class SVGAngleImpl implements SVGAngle {

	private short unitType;
	private float value;
	private float valueInSpecifiedUnits;
	private String valueAsString;

	/**
	 * Creates and new SVGAngleImpl. Initialized to 0 degrees.
	 */
	public SVGAngleImpl() {
		unitType = SVG_ANGLETYPE_DEG;
		setValue(0);
	}

	/**
	 * Creates and new SVGAngleImpl. Initialized to value degrees.
	 */
	public SVGAngleImpl(float value) {
		unitType = SVG_ANGLETYPE_DEG;
		setValue(value);
	}

	/**
	 * Creates a new SVGAngleImpl.
	 *
	 * @param valueAndUnit
	 *            The String representation of the angle. eg. 90deg
	 */
	public SVGAngleImpl(String valueAndUnit) {
		setCSSText(valueAndUnit);
	}


	/** {@inheritDoc} */
	@Override
	public short getUnitType() {
		return unitType;
	}

	/** {@inheritDoc} */
	@Override
	public float getValue() {
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(float value) {
		this.value = value;
		valueInSpecifiedUnits = convertFromDegrees(value, unitType);
		valueAsString = String.valueOf(valueInSpecifiedUnits);

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
		valueAsString = String.valueOf(valueInSpecifiedUnits);
		value = convertToDegrees(valueInSpecifiedUnits, unitType);
	}

	/** {@inheritDoc} */
	@Override
	public String getValueAsString() {
		return valueAsString;
	}

	/** {@inheritDoc} */
	@Override
	public void setValueAsString(String valueAsString) {
		this.valueAsString = valueAsString;
		valueInSpecifiedUnits = Float.parseFloat(valueAsString);
		value = convertToDegrees(valueInSpecifiedUnits, unitType);
	}

	/** {@inheritDoc} */
	@Override
	public void newValueSpecifiedUnits(short unitType, float valueInSpecifiedUnits) {
		this.unitType = unitType;
		setValueInSpecifiedUnits(valueInSpecifiedUnits);
	}

	/** {@inheritDoc} */
	@Override
	public void convertToSpecifiedUnits(short unitType) {
		this.unitType = unitType;
		setValue(value);
	}

	void setCSSText(String cssText) {
		String text = cssText.trim();
		int index = -1;
		for (int i = 0; i < text.length(); i++) {
			if (!Character.isDigit(text.charAt(i)) && text.charAt(i) != '.' && text.charAt(i) != '-'
					&& text.charAt(i) != '+') {
				index = i;
				break;
			}
		}
		if (index == -1) {
			value = Float.parseFloat(text);
			unitType = SVG_ANGLETYPE_UNSPECIFIED;
			valueAsString = text;
			valueInSpecifiedUnits = value;

		} else { // there was a unit

			String unit = text.substring(index);
			unitType = getUnitTypeConst(unit);
			valueAsString = text.substring(0, index);
			valueInSpecifiedUnits = Float.parseFloat(valueAsString);
			value = convertToDegrees(valueInSpecifiedUnits, unitType);
		}
	}

	private float convertToDegrees(float value, short unitType) {
		switch (unitType) {
			case SVG_ANGLETYPE_RAD:
				return (float) Math.toDegrees(value);
			case SVG_ANGLETYPE_GRAD:
				return (float) (value * 9.0 / 10.0);
			case SVG_ANGLETYPE_DEG:
			case SVG_ANGLETYPE_UNSPECIFIED:
			default:
				return value;
		}
	}

	private float convertFromDegrees(float value, short unitType) {
		switch (unitType) {
			case SVG_ANGLETYPE_RAD:
				return (float) Math.toRadians(value);
			case SVG_ANGLETYPE_GRAD:
				return (float) (value * 10.0 / 9.0);
			case SVG_ANGLETYPE_DEG:
			case SVG_ANGLETYPE_UNSPECIFIED:
			default:
				return value;
		}
	}

	private static short getUnitTypeConst(String unit) {
		if (unit == null) {
			return SVG_ANGLETYPE_UNSPECIFIED;
		}

		switch (unit.toLowerCase()) {
			case "deg":
				return SVG_ANGLETYPE_DEG;
			case "grad":
				return SVG_ANGLETYPE_GRAD;
			case "rad":
				return SVG_ANGLETYPE_RAD;
			case "":
				return SVG_ANGLETYPE_UNSPECIFIED;
			default:
				return SVG_ANGLETYPE_UNKNOWN;
		}
	}
}
