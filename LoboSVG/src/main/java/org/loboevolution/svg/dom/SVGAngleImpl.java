/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.svg.dom;

import org.loboevolution.svg.SVGAngle;

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
	public SVGAngleImpl(final float value) {
		unitType = SVG_ANGLETYPE_DEG;
		setValue(value);
	}

	/**
	 * Creates a new SVGAngleImpl.
	 *
	 * @param valueAndUnit
	 *            The String representation of the angle. eg. 90deg
	 */
	public SVGAngleImpl(final String valueAndUnit) {
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
	public void setValue(final float value) {
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
	public void setValueInSpecifiedUnits(final float valueInSpecifiedUnits) {
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
	public void setValueAsString(final String valueAsString) {
		this.valueAsString = valueAsString;
		valueInSpecifiedUnits = Float.parseFloat(valueAsString);
		value = convertToDegrees(valueInSpecifiedUnits, unitType);
	}

	/** {@inheritDoc} */
	@Override
	public void newValueSpecifiedUnits(final short unitType, final float valueInSpecifiedUnits) {
		this.unitType = unitType;
		setValueInSpecifiedUnits(valueInSpecifiedUnits);
	}

	/** {@inheritDoc} */
	@Override
	public void convertToSpecifiedUnits(final short unitType) {
		this.unitType = unitType;
		setValue(value);
	}

	void setCSSText(final String cssText) {
		final String text = cssText.trim();
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

			final String unit = text.substring(index);
			unitType = getUnitTypeConst(unit);
			valueAsString = text.substring(0, index);
			valueInSpecifiedUnits = Float.parseFloat(valueAsString);
			value = convertToDegrees(valueInSpecifiedUnits, unitType);
		}
	}

	private float convertToDegrees(final float value, final short unitType) {
        return switch (unitType) {
            case SVG_ANGLETYPE_RAD -> (float) Math.toDegrees(value);
            case SVG_ANGLETYPE_GRAD -> (float) (value * 9.0 / 10.0);
            default -> value;
        };
	}

	private float convertFromDegrees(final float value, final short unitType) {
        return switch (unitType) {
            case SVG_ANGLETYPE_RAD -> (float) Math.toRadians(value);
            case SVG_ANGLETYPE_GRAD -> (float) (value * 10.0 / 9.0);
            default -> value;
        };
	}

	private static short getUnitTypeConst(final String unit) {
		if (unit == null) {
			return SVG_ANGLETYPE_UNSPECIFIED;
		}

        return switch (unit.toLowerCase()) {
            case "deg" -> SVG_ANGLETYPE_DEG;
            case "grad" -> SVG_ANGLETYPE_GRAD;
            case "rad" -> SVG_ANGLETYPE_RAD;
            case "" -> SVG_ANGLETYPE_UNSPECIFIED;
            default -> SVG_ANGLETYPE_UNKNOWN;
        };
	}
}
