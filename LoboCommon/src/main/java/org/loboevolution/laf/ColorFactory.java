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

package org.loboevolution.laf;

import org.loboevolution.common.Strings;

import java.awt.*;
import java.util.Arrays;
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
	public Color getColor(LAFColorType colorSpec) {
		return getColor(colorSpec.getValue());

	}

	/**
	 * Gets the color.
	 *
	 * @param colorSpec the color spec
	 * @return the color
	 */
	public Color getColor(String colorSpec) {

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
	 * Get HEX color.
	 *
	 * @param c          the color
	 * @param normalSpec the color spec
	 * @return the color
	 */
	private Color getHex(Color c, String normalSpec) {
		Color color;
		if (normalSpec.startsWith("#")) {
			color = HexToColor(normalSpec);
			this.colorMap.put(normalSpec, color);
		} else {
			color = c;
		}
		return color;
	}

	private Color getHSL(Color c, String normalSpec, String colorStart) {
		Color color;
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
	private Color getRGB(Color c, String normalSpec, String colorStart) {
		Color color;
		if (c == null && normalSpec.startsWith(colorStart) && normalSpec.endsWith(")")) {
			final String commaValues = getCommaValues(normalSpec,colorStart);
			final String[] splitComma = Strings.splitUsingTokenizer(commaValues, ",");
			final int red = (int) parseValue(splitComma[0].trim(), 255);
			final int green =  (int) parseValue(splitComma[1].trim(), 255);
			final int blue =  (int) parseValue(splitComma[2].trim(), 255);
			if (splitComma.length > 3) {
				float alpha = parseAlpha(splitComma[splitComma.length - 1].trim());
				color = new Color(normalize(red), normalize(green), normalize(blue), Math.round(alpha));
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
	private float hueToRgb(float p, float q, float h) {
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
	public boolean isColor(String colorSpec) {
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
	public boolean isRgbOrHsl(String colorSpec) {
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
	public static Color getAdjustedColor(Color c, double factor) {
		double f = 1 - Math.min(Math.abs(factor), 1);
		double inc = (factor > 0 ? 255 * (1 - f) : 0);
		return new Color((int) (c.getRed() * f + inc), (int) (c.getGreen() * f + inc), (int) (c.getBlue() * f + inc));
	}

	private float parseAlpha(String alpha) {
		if (alpha.endsWith("%")) {
			return parsePercent(alpha) / 100;
		}
		return Float.parseFloat(alpha);
	}

	private float parsePercent(String perc) {
		return Float.parseFloat(perc.substring(0, perc.length() - 1));
	}

	private float parseValue(String val, int max) {
		if (val.endsWith("%")) {
			return (parsePercent(val) * max) / 100;
		}
		return Float.parseFloat(val);
	}

	private Color toRGB(float h, float s, float l, float alpha) {
		h = h % 360.0f;
		h /= 360f;
		s /= 100f;
		l /= 100f;

		float q;

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

	private Color HexToColor(String hex) {
		int red;
		int green;
		int blue;
		int alpha;
		hex = hex.split("#")[1];
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

	private String getCommaValues (String normalSpec, String str){
		final int endIdx = normalSpec.lastIndexOf(')');
		String commaValues;

		if (endIdx == -1) {
			commaValues = normalSpec.substring(str.length());
		} else {
			commaValues = normalSpec.substring(str.length(), endIdx);
		}
		return commaValues.replace("/", " ");
	}
}
