/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.svg.SVGLength;
import org.loboevolution.svg.style.SvgValues;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;


/**
 * <p>SVGLengthImpl class.</p>
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
	public SVGLengthImpl(final short unitType) {
		this.unitType = unitType;
		this.valueInSpecifiedUnits = 0.0f;
	}

	/**
	 * <p>Constructor for SVGLengthImpl.</p>
	 *
	 * @param unitType a short.
	 * @param valueInSpecifiedUnits a float.
	 */
	public SVGLengthImpl(final short unitType, final float valueInSpecifiedUnits) {
		this.unitType = unitType;
		this.valueInSpecifiedUnits = valueInSpecifiedUnits;
	}

	/**
	 * <p>Constructor for SVGLengthImpl.</p>
	 *
	 * @param valueAsString a {@link java.lang.String} object.
	 */
	public SVGLengthImpl(final String valueAsString) {
		setValueAsString(valueAsString);
	}

	/**
	 * <p>Constructor for SVGLengthImpl.</p>
	 *
	 * @param valueInSpecifiedUnits a float.
	 */
	public SVGLengthImpl(final float valueInSpecifiedUnits) {
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
	public void setValue(final float value) {
		this.valueInSpecifiedUnits = value;
		this.unitType = SVGLength.SVG_LENGTHTYPE_NUMBER;
	}

	/** {@inheritDoc} */
	@Override
	public String getValueAsString() {
		final String suffix = SvgValues.getUnitTypeAsString(this.unitType);
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
	public void setValueAsString(final String valueAsStr) {

		if (valueAsStr == null) {
			this.valueInSpecifiedUnits = 0;
			this.unitType = SVGLength.SVG_LENGTHTYPE_NUMBER;
			return;
		}

		final String lcSpec = valueAsStr.toLowerCase();
		String unit = "";
		String text = lcSpec;

		if (SvgValues.isUnits(lcSpec)) {

			if (lcSpec.endsWith("%") || lcSpec.endsWith("q")) {
				unit = lcSpec.substring(lcSpec.length() - 1);
				text = lcSpec.substring(0, lcSpec.length() - 1);

			} else if (lcSpec.endsWith("rem") || lcSpec.endsWith("exs")) {
				unit = lcSpec.substring(lcSpec.length() - 3);
				text = lcSpec.substring(0, lcSpec.length() - 3);

			} else {
				unit = lcSpec.substring(lcSpec.length() - 2);
				text = lcSpec.substring(0, lcSpec.length() - 2);
			}
		}

		switch (unit) {
			case "cm":
				this.unitType = SVGLength.SVG_LENGTHTYPE_CM;
				break;
			case "em", "rem":
				this.unitType = SVGLength.SVG_LENGTHTYPE_EMS;
				break;
            case "ex":
			case "exs":
				this.unitType = SVGLength.SVG_LENGTHTYPE_EXS;
				break;
			case "in":
				this.unitType = SVGLength.SVG_LENGTHTYPE_IN;
				break;
			case "mm":
				this.unitType = SVGLength.SVG_LENGTHTYPE_MM;
				break;
			case "pc":
				this.unitType = SVGLength.SVG_LENGTHTYPE_PC;
				break;
			case "%":
				this.unitType = SVGLength.SVG_LENGTHTYPE_PERCENTAGE;
				break;
			case "pt":
				this.unitType = SVGLength.SVG_LENGTHTYPE_PT;
				break;
			case "px":
				this.unitType = SVGLength.SVG_LENGTHTYPE_PX;
				break;
			default:
				this.unitType = SVGLength.SVG_LENGTHTYPE_NUMBER;
		}

		try {
			this.valueInSpecifiedUnits = Float.parseFloat(text);
		} catch (NumberFormatException e) {
			throw new DOMException(DOMException.SYNTAX_ERR, "Invalid value: " + text);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void newValueSpecifiedUnits(final short unitType, final float valueInSpecifiedUnits) {

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
	public void convertToSpecifiedUnits(final short unitType) {

		if (this.unitType == unitType) {
			return;
		}

		// 1) Converti tutto in pollici
		float inchValue = this.valueInSpecifiedUnits;

		switch (this.unitType) {
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
			case SVGLength.SVG_LENGTHTYPE_MM:
				this.valueInSpecifiedUnits = inchValue * 25.40013f;
				break;
			case SVGLength.SVG_LENGTHTYPE_CM:
				this.valueInSpecifiedUnits = inchValue * 2.54f;
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
	public void setValueInSpecifiedUnits(final float valueInSpecifiedUnits) {
		this.valueInSpecifiedUnits = valueInSpecifiedUnits;
	}
	
	/**
	 * <p>getTransformedLength.</p>
	 *
	 * @param transform a {@link java.awt.geom.AffineTransform} object.
	 * @return a float.
	 */
	protected float getTransformedLength(final AffineTransform transform) {

		if (unitType == SVG_LENGTHTYPE_NUMBER || transform == null || transform.isIdentity()) {
			return getValue();
		}

		final Point2D q1 = new Point2D.Double(0, 0);
		final float val = getValue();
		final Point2D q2 = new Point2D.Double(0.7071068 * val, 0.7071068 * val);

		final Point2D transQ1 = new Point2D.Double();
		final Point2D transQ2 = new Point2D.Double();

		transform.transform(q1, transQ1);
		transform.transform(q2, transQ2);

		final double diffX = transQ2.getX() - transQ1.getX();
		final double diffY = transQ2.getY() - transQ1.getY();

        return (float) Math.sqrt(diffX * diffX + diffY * diffY);

	}
}
