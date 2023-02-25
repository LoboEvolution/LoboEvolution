/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
