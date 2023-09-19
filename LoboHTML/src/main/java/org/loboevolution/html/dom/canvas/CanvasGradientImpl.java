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
package org.loboevolution.html.dom.canvas;

import org.loboevolution.html.dom.CanvasGradient;
import org.loboevolution.laf.ColorFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The Class CanvasGradientImpl.
 *
 *
 *
 */
public class CanvasGradientImpl implements CanvasGradient {

	/** The fractions. */
	private List<Float> fractions;

	/** The colors. */
	private List<Color> colors;

	/** The linear x. */
	private Double linearX;

	/** The linear x1. */
	private Double linearX1;

	/** The linear y. */
	private Double linearY;

	/** The linear y1. */
	private Double linearY1;

	/** The r1. */
	private Double r1;

	/** The r2. */
	private Double r2;

	/**
	 * <p>Constructor for CanvasGradientImpl.</p>
	 *
	 * @param x0 a {@link java.lang.Object} object.
	 * @param y0 a {@link java.lang.Object} object.
	 * @param x1 a {@link java.lang.Object} object.
	 * @param y1 a {@link java.lang.Object} object.
	 */
	public CanvasGradientImpl(Object x0, Object y0, Object x1, Object y1) {
		fractions = new ArrayList<>();
		colors = new ArrayList<>();
		this.linearX = Double.valueOf(x0.toString());
		this.linearX1 = Double.valueOf(y0.toString());
		this.linearY = Double.valueOf(x1.toString());
		this.linearY1 = Double.valueOf(y1.toString());
	}

	/**
	 * <p>Constructor for CanvasGradientImpl.</p>
	 *
	 * @param x0 a {@link java.lang.Object} object.
	 * @param y0 a {@link java.lang.Object} object.
	 * @param x1 a {@link java.lang.Object} object.
	 * @param y1 a {@link java.lang.Object} object.
	 * @param r1 a {@link java.lang.Object} object.
	 * @param r2 a {@link java.lang.Object} object.
	 */
	public CanvasGradientImpl(Object x0, Object y0, Object x1, Object y1, Object r1, Object r2) {
		fractions = new ArrayList<>();
		colors = new ArrayList<>();
		this.linearX = Double.valueOf(x0.toString());
		this.linearX1 = Double.valueOf(y0.toString());
		this.linearY = Double.valueOf(x1.toString());
		this.linearY1 = Double.valueOf(y1.toString());
		this.r1 = Double.valueOf(r1.toString());
		this.r2 = Double.valueOf(r2.toString());
	}

	/** {@inheritDoc} */
	@Override
	public void addColorStop(String offset, String color) {
		fractions.add(Float.valueOf(offset));
		colors.add(ColorFactory.getInstance().getColor(color));
	}
	
	
	/**
	 * gradient.
	 *
	 * @return gradient paint
	 */
	protected Paint gradient() {

        float[] floatArray = new float[fractions.size()];
		int i = 0;

		for (Float f : fractions) {
			floatArray[i++] = f != null ? f : Float.NaN;
		}

		Color[] colorArray = new Color[colors.size()];
		int a = 0;

		for (Color c : colors) {
			colorArray[a++] = c;
		}

		Arrays.sort(floatArray);

		if (r2 != null) {
			return new RadialGradientPaint(linearX.floatValue(), linearY.floatValue(), r2.floatValue(),
					linearX1.floatValue(), linearY1.floatValue(), floatArray, colorArray,
					RadialGradientPaint.CycleMethod.NO_CYCLE);

		} else {
			return new LinearGradientPaint(linearX.floatValue(), linearX1.floatValue(), linearY.floatValue(),
					linearY1.floatValue(), floatArray, colorArray);
		}
	}

	/**
	 * Gets the fractions.
	 *
	 * @return the fractions
	 */
	public List<Float> getFractions() {
		return fractions;
	}

	/**
	 * Sets the fractions.
	 *
	 * @param fractions
	 *            the new fractions
	 */
	public void setFractions(List<Float> fractions) {
		this.fractions = fractions;
	}

	/**
	 * Gets the colors.
	 *
	 * @return the colors
	 */
	public List<Color> getColors() {
		return colors;
	}

	/**
	 * Sets the colors.
	 *
	 * @param colors
	 *            the new colors
	 */
	public void setColors(List<Color> colors) {
		this.colors = colors;
	}

	/**
	 * Gets the linear x.
	 *
	 * @return the linear x
	 */
	public Double getLinearX() {
		return linearX;
	}

	/**
	 * Sets the linear x.
	 *
	 * @param linearX
	 *            the new linear x
	 */
	public void setLinearX(Double linearX) {
		this.linearX = linearX;
	}

	/**
	 * Gets the linear x1.
	 *
	 * @return the linear x1
	 */
	public Double getLinearX1() {
		return linearX1;
	}

	/**
	 * Sets the linear x1.
	 *
	 * @param linearX1
	 *            the new linear x1
	 */
	public void setLinearX1(Double linearX1) {
		this.linearX1 = linearX1;
	}

	/**
	 * Gets the linear y.
	 *
	 * @return the linear y
	 */
	public Double getLinearY() {
		return linearY;
	}

	/**
	 * Sets the linear y.
	 *
	 * @param linearY
	 *            the new linear y
	 */
	public void setLinearY(Double linearY) {
		this.linearY = linearY;
	}

	/**
	 * Gets the linear y1.
	 *
	 * @return the linear y1
	 */
	public Double getLinearY1() {
		return linearY1;
	}

	/**
	 * Sets the linear y1.
	 *
	 * @param linearY1
	 *            the new linear y1
	 */
	public void setLinearY1(Double linearY1) {
		this.linearY1 = linearY1;
	}

	/**
	 * <p>Getter for the field r1.</p>
	 *
	 * @return the r1
	 */
	public Double getR1() {
		return r1;
	}

	/**
	 * <p>Setter for the field r1.</p>
	 *
	 * @param r1
	 *            the r1 to set
	 */
	public void setR1(Double r1) {
		this.r1 = r1;
	}

	/**
	 * <p>Getter for the field r2.</p>
	 *
	 * @return the r2
	 */
	public Double getR2() {
		return r2;
	}

	/**
	 * <p>Setter for the field r2.</p>
	 *
	 * @param r2
	 *            the r2 to set
	 */
	public void setR2(Double r2) {
		this.r2 = r2;
	}
}
