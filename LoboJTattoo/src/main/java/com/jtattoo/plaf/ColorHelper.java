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

package com.jtattoo.plaf;

import java.awt.Color;

/**
 * A helper class for handling color values.
 *
 * Author Michael Hagen
 *
 */
public final class ColorHelper {

	/**
	 * Creates a color that is the brighter version of the color parameter c.
	 *
	 * @param c the color
	 * @param p the factor of the brightness in percent from 0 to 100
	 * @return a new color value that is a brighter version of the color parameter c
	 */
	public static Color brighter(final Color c, final double p) {
		if (c == null) {
			return null;
		}

		double r = c.getRed();
		double g = c.getGreen();
		double b = c.getBlue();

		final double rd = 255.0 - r;
		final double gd = 255.0 - g;
		final double bd = 255.0 - b;

		r += rd * p / 100.0;
		g += gd * p / 100.0;
		b += bd * p / 100.0;
		return createColor((int) r, (int) g, (int) b);
	}

	/**
	 * Creates a color object.
	 *
	 * @param r the Red component
	 * @param g the Green component
	 * @param b the Blue component
	 * @return a color object
	 */
	public static Color createColor(final int r, final int g, final int b) {
		return new Color((r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF);
	}

	/**
	 * Creates an array of color values. The colors created will be a gradient from
	 * color c1 to color c1 with a count of steps values.
	 *
	 * @param c1    the starting color
	 * @param c2    the ending color
	 * @param steps the number of steps between c1 and c2 (the size of the created
	 *              array)
	 * @return the array of color values
	 */
	public static Color[] createColorArr(final Color c1, final Color c2, final int steps) {
		if (c1 == null || c2 == null) {
			return null;
		}

		final Color[] colors = new Color[steps];
		double r = c1.getRed();
		double g = c1.getGreen();
		double b = c1.getBlue();
		final double dr = (c2.getRed() - r) / steps;
		final double dg = (c2.getGreen() - g) / steps;
		final double db = (c2.getBlue() - b) / steps;
		colors[0] = c1;
		for (int i = 1; i < steps - 1; i++) {
			r += dr;
			g += dg;
			b += db;
			colors[i] = createColor((int) r, (int) g, (int) b);
		}
		colors[steps - 1] = c2;
		return colors;
	}

	/**
	 * Creates a color that is the darker version of the color parameter c.
	 *
	 * @param c the color
	 * @param p the factor to shade the color c in percent from 0 to 100
	 * @return a new color value that is a darker version of the color parameter c
	 */
	public static Color darker(final Color c, final double p) {
		if (c == null) {
			return null;
		}

		double r = c.getRed();
		double g = c.getGreen();
		double b = c.getBlue();

		r -= r * p / 100.0;
		g -= g * p / 100.0;
		b -= b * p / 100.0;

		return createColor((int) r, (int) g, (int) b);
	}

	/**
	 * Returns a value between 0 and 255 which represents the gray value of the
	 * color parameter.
	 *
	 * @param c the color you want to calculate the gray value
	 * @return the gray value
	 */
	public static int getGrayValue(final Color c) {
		if (c == null) {
			return 0;
		}

		final double r = c.getRed();
		final double g = c.getGreen();
		final double b = c.getBlue();
		return Math.min(255, (int) (r * 0.28 + g * 0.59 + b * 0.13));
	}

	/**
	 * Returns a value between 0 and 255 which represents the median gray value of
	 * the color array.
	 *
	 * @param ca the color array you want to calculate the gray value
	 * @return the gray value
	 */
	public static int getGrayValue(final Color[] ca) {
		int sum = 0;
		for (final Color color : ca) {
			sum += getGrayValue(color);
		}
		return sum / ca.length;
	}

	/**
	 * Returns a color value which is the media between the colors c1 and c1
	 *
	 * @param c1 the first color
	 * @param c2 the second color
	 * @return the median color value of the two colors c1 and c1
	 */
	public static Color median(final Color c1, final Color c2) {
		if (c1 == null || c2 == null) {
			return null;
		}

		final int r = (c1.getRed() + c2.getRed()) / 2;
		final int g = (c1.getGreen() + c2.getGreen()) / 2;
		final int b = (c1.getBlue() + c2.getBlue()) / 2;
		return createColor(r, g, b);
	}

	/**
	 * Returns a gray version of the color parameter c, which means all parts
	 * (r,g,b) do have the same value.
	 *
	 * @param c the color
	 * @return a gray version of the color parameter c.
	 */
	public static Color toGray(final Color c) {
		if (c == null) {
			return null;
		}

		final int gray = getGrayValue(c);
		return new Color(gray, gray, gray, c.getAlpha());
	}

	/**
	 * Avoid creation of this class. This class only contains static helper methods,
	 * so creation of an object is not necessary.
	 */
	private ColorHelper() {
	}

} // end of class ColorHelper
