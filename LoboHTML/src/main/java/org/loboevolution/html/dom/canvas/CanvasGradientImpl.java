/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

import lombok.Data;
import org.loboevolution.html.dom.CanvasGradient;
import org.loboevolution.laf.ColorFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The Class CanvasGradientImpl.
 */
@Data
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
	public CanvasGradientImpl(final Object x0, final Object y0, final Object x1, final Object y1) {
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
	public CanvasGradientImpl(final Object x0, final Object y0, final Object x1, final Object y1, final Object r1, final Object r2) {
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
	public void addColorStop(final String offset, final String color) {
		fractions.add(Float.valueOf(offset));
		colors.add(ColorFactory.getInstance().getColor(color));
	}
	
	
	/**
	 * gradient.
	 *
	 * @return gradient paint
	 */
	protected Paint gradient() {

        final float[] floatArray = new float[fractions.size()];
		int i = 0;

		for (final Float f : fractions) {
			floatArray[i++] = f != null ? f : Float.NaN;
		}

		final Color[] colorArray = new Color[colors.size()];
		int a = 0;

		for (final Color c : colors) {
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
}
