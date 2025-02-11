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

package org.loboevolution.laf;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLElement;

import java.awt.*;
import java.util.Map;

/**
 * A factory for creating Color objects.
 */
public final class ColorFactory {

	/** The instance. */
	private static ColorFactory instance;
	
	/** The color map. */
	private final Map<String, Color> colorMap;

	/** The Constant HSL_START. */
	private final String HSL_START = "hsl(";

	/** The Constant HSLA_START. */
	private final String HSLA_START = "hsla(";

	/** The Constant RGB_START. */
	private final String RGB_START = "rgb(";

	/** The Constant RGBA_START. */
	private final String RGBA_START = "rgba(";

	/**
	 * Gets the instance.
	 *
	 * @return the instance
	 */
	public static synchronized ColorFactory getInstance() {
		if (instance == null) {
			instance = new ColorFactory();
		}
		return instance;
	}
	
	/**
	 * Instantiates a new color factory.
	 */
	private ColorFactory() {
		synchronized (this) {
			this.colorMap = ColorMap.colorMap();
		}
	}

	/**
	 * Gets the color.
	 *
	 * @param colorSpec the color spec
	 * @return the color
	 */
	public Color getColor(final LAFColorType colorSpec) {
		return getColor(colorSpec.getValue());

	}

	/**
	 * Gets the color.
	 *
	 * @param colorSpec the color spec
	 * @return the color
	 */
	public Color getColor(final String colorSpec) {

		if (colorSpec == null) {
			return null;
		}

		final String normalSpec = colorSpec.toLowerCase().trim();
		synchronized (this) {
			Color color = this.colorMap.get(normalSpec);
			if (color == null) {
				color = getRGB(color, normalSpec, RGBA_START);
				color = getRGB(color, normalSpec, RGB_START);
				color = getHex(color, normalSpec);
				color = getHSL(color, normalSpec, HSLA_START);
				color = getHSL(color, normalSpec, HSL_START);
			}
			return color;
		}
	}

	/**
	 * Gets the color string.
	 *
	 * @param element a {@link org.loboevolution.html.dom.HTMLElement} object.
	 * @param colorSpec a {@link java.lang.String} object.
	 * @param defaultValue a {@link java.lang.String} object.
	 * @return the color
	 */
	public String getColorString(final HTMLElement element, final String colorSpec, String defaultValue) {

	if (Strings.isBlank(colorSpec)) {
		return element.getParentNode() == null ? null : defaultValue;
	}
	final Color c = ColorFactory.getInstance().getColor(colorSpec);
        if (c != null) {
		if (c.getRed() == 0 && c.getGreen() == 0 && c.getBlue() == 0) {
			return element.getParentNode() == null ? null : defaultValue;
		}
		final float alpha = (float) (c.getAlpha()) / 255.0f;
		if (alpha > 0 && alpha < 1) {
			return element.getParentNode() == null ? null : "rgba(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ", " + alpha + ")";
		} else {
			return element.getParentNode() == null ? null : "rgb(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")";
		}
	} else {
		return element.getParentNode() == null ? null : defaultValue;
	}

		}

	/**
	 * Get HEX color.
	 *
	 * @param c          the color
	 * @param normalSpec the color spec
	 * @return the color
	 */
	private Color getHex(final Color c, final String normalSpec) {
		final Color color;
		if (normalSpec.startsWith("#")) {
			color = HexToColor(normalSpec);
			this.colorMap.put(normalSpec, color);
		} else {
			color = c;
		}
		return color;
	}

	private Color getHSL(final Color c, final String normalSpec, final String colorStart) {
		final Color color;
		if (c == null && normalSpec.startsWith(colorStart)) {
			final String commaValues = getCommaValues(normalSpec, colorStart);
			final String[] splitComma = commaValues.contains(",")  ? commaValues.split(",") : commaValues.split(" ");
			final float h = parseValue(splitComma[0].trim(), 360);
			final float s = parsePercent(splitComma[1].trim());
			final float l = parsePercent(splitComma[2].trim());
			float alpha = 1;
			if(splitComma.length > 3) {
				alpha = parseAlpha(splitComma[splitComma.length -1].trim());
			}
			color = toRGB(h, s, l, alpha);
			this.colorMap.put(normalSpec, color);
		} else {
			color = c;
		}
		return color;
	}

	/**
	 * Get RGB color.
	 *
	 * @param c          the color
	 * @param normalSpec the color spec
	 * @return the color
	 */
	private Color getRGB(final Color c, final String normalSpec, final String colorStart) {
		final Color color;
		if (c == null && normalSpec.startsWith(colorStart) && normalSpec.endsWith(")")) {
			final String commaValues = getCommaValues(normalSpec,colorStart);
			final String[] splitComma = Strings.splitUsingTokenizer(commaValues, ",");
			final int red = (int) parseValue(splitComma[0].trim(), 255);
			final int green =  (int) parseValue(splitComma[1].trim(), 255);
			final int blue =  (int) parseValue(splitComma[2].trim(), 255);
			if (splitComma.length > 3) {
				final float alpha = parseAlpha(splitComma[splitComma.length - 1].trim());
				color = new Color(normalize(red), normalize(green), normalize(blue), Math.round(alpha  * 255));
			} else {
				color = new Color(normalize(red), normalize(green), normalize(blue));
			}
			this.colorMap.put(normalSpec, color);
		} else {
			color = c;
		}
		return color;
	}

	/** Helper method that converts hue to rgb */
	private float hueToRgb(final float p, final float q, final float h1) {
		float h = h1;

		if (h < 0) {
			h += 1;
		}

		if (h > 1) {
			h -= 1;
		}

		if (6 * h < 1) {
			return p + (q - p) * 6 * h;
		}

		if (2 * h < 1) {
			return q;
		}

		if (3 * h < 2) {
			return p + (q - p) * 6 * (2.0f / 3.0f - h);
		}
		return p;
	}

	/**
	 * Checks if is color.
	 *
	 * @param colorSpec the color spec
	 * @return true, if is color
	 */
	public boolean isColor(final String colorSpec) {
		final String normalSpec = colorSpec.toLowerCase();
		final boolean starts = normalSpec.startsWith("#") ||
				normalSpec.startsWith(RGBA_START) || normalSpec.startsWith(RGB_START) ||
				normalSpec.startsWith(HSLA_START) || normalSpec.startsWith(HSL_START);
		return starts || this.colorMap.containsKey(normalSpec);
	}
	
	/**
	 * <p>isRgbOrHsl.</p>
	 *
	 * @param colorSpec a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean isRgbOrHsl(final String colorSpec) {
		final String normalSpec = colorSpec.toLowerCase();
		return (normalSpec.startsWith(RGBA_START) ||
				normalSpec.startsWith(RGB_START) ||
				normalSpec.startsWith(HSLA_START) ||
				normalSpec.startsWith(HSL_START));
	}
	
	/**
	 * <p>getAdjustedColor.</p>
	 *
	 * @param c a {@link java.awt.Color} object.
	 * @param factor a double.
	 * @return a {@link java.awt.Color} object.
	 */
	public static Color getAdjustedColor(final Color c, final double factor) {
		final double f = 1 - Math.min(Math.abs(factor), 1);
		final double inc = (factor > 0 ? 255 * (1 - f) : 0);
		return new Color((int) (c.getRed() * f + inc), (int) (c.getGreen() * f + inc), (int) (c.getBlue() * f + inc));
	}

	private float parseAlpha(final String alpha) {
		if (alpha.endsWith("%")) {
			return parsePercent(alpha) / 100;
		}
		return Float.parseFloat(alpha);
	}

	private float parsePercent(final String perc) {
		return Float.parseFloat(perc.substring(0, perc.length() - 1));
	}

	private float parseValue(final String val, final int max) {
		if (val.endsWith("%")) {
			return (parsePercent(val) * max) / 100;
		}
		return Float.parseFloat(val);
	}

	private Color toRGB(final float h1, final float s1, final float l1, final float alpha) {
		float h = h1;
		float s = s1;
		float l = l1;
		h = h % 360.0f;
		h /= 360f;
		s /= 100f;
		l /= 100f;

		final float q;

		if (l < 0.5) {
			q = l * (1 + s);
		} else {
			q = l + s - s * l;
		}

		final float p = 2 * l - q;

		float r = Math.max(0, hueToRgb(p, q, h + 1.0f / 3.0f));
		float g = Math.max(0, hueToRgb(p, q, h));
		float b = Math.max(0, hueToRgb(p, q, h - 1.0f / 3.0f));

		r = Math.min(r, 1.0f);
		g = Math.min(g, 1.0f);
		b = Math.min(b, 1.0f);
		return new Color(r, g, b, alpha);
	}

	private Color HexToColor(final String hexColor) {
		final int red;
		final int green;
		final int blue;
		final int alpha;
		final String hex = hexColor.split("#")[1];
		switch (hex.length()) {
		case 6:
			red = Integer.valueOf(hex.substring(0, 2), 16);
			green = Integer.valueOf(hex.substring(2, 4), 16);
			blue = Integer.valueOf(hex.substring(4, 6), 16);
			return new Color(red, green, blue);
		case 8:
			red = Integer.valueOf(hex.substring(0, 2), 16);
			green = Integer.valueOf(hex.substring(2, 4), 16);
			blue = Integer.valueOf(hex.substring(4, 6), 16);
			alpha = Integer.valueOf(hex.substring(6, 8), 16);
			return new Color(red, green, blue, alpha);
		case 3:
			red = Integer.valueOf(hex.charAt(0)+ "" + hex.charAt(0), 16);
			green = Integer.valueOf(hex.charAt(1)+ "" + hex.charAt(1), 16);
			blue = Integer.valueOf(hex.charAt(2)+ "" + hex.charAt(2), 16);
			return new Color(red, green, blue);
		default:
			return null;
		}
	}

	private static int normalize(int colorComponent) {
		if (colorComponent > 255) {
			colorComponent = 255;
		} else if (colorComponent < 0) {
			colorComponent = 0;
		}
		return colorComponent;
	}

	private String getCommaValues (final String normalSpec, final String str){
		final int endIdx = normalSpec.lastIndexOf(')');
		final String commaValues;

		if (endIdx == -1) {
			commaValues = normalSpec.substring(str.length());
		} else {
			commaValues = normalSpec.substring(str.length(), endIdx);
		}
		return commaValues.replace("/", " ");
	}
}
