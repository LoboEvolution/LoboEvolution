/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Apr 17, 2005
 */
package org.loboevolution.util.gui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.loboevolution.util.ColorCommon;
import org.loboevolution.util.Strings;

/**
 * A factory for creating Color objects.
 *
 * @author J. H. S.
 */
public class ColorFactory {
		
	/** The instance. */
	private static ColorFactory instance;
	
	/** The Constant TRANSPARENT. */
	public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
	
	/** The color map. */
	private Map<String, Color> colorMap = new HashMap<String, Color>(510);
	
	/** The Constant RGB_START. */
	private String RGB_START = "rgb(";
	
	/** The Constant RGBA_START. */
	private String RGBA_START = "rgba(";
	
	/** The Constant HSLA_START. */
	private String HSLA_START = "hsla(";
	
	/** The Constant HSL_START. */
	private String HSL_START = "hsl(";

	/**
	 * Instantiates a new color factory.
	 */
	private ColorFactory() {
		synchronized (this) {
			this.colorMap = ColorCommon.mapColor();
		}
	}

	/**
	 * Gets the instance.
	 *
	 * @return the instance
	 */
	public static final ColorFactory getInstance() {
		if (instance == null) {
			synchronized (ColorFactory.class) {
				if (instance == null) {
					instance = new ColorFactory();
				}
			}
		}
		return instance;
	}

	/**
	 * Checks if is color.
	 *
	 * @param colorSpec
	 *            the color spec
	 * @return true, if is color
	 */
	public boolean isColor(String colorSpec) {
		String normalSpec = colorSpec.toLowerCase();

		if (colorSpec.startsWith("#")) {
			return true;
		} else if (normalSpec.startsWith(RGBA_START)) {
			return true;
		} else if (normalSpec.startsWith(RGB_START)) {
			return true;
		} else if (normalSpec.startsWith(HSLA_START)) {
			return true;
		} else if (normalSpec.startsWith(HSL_START)) {
			return true;
		}
		synchronized (this) {
			return colorMap.containsKey(normalSpec);
		}
	}

	/**
	 * Gets the color.
	 *
	 * @param colorSpec
	 *            the color spec
	 * @return the color
	 */
	public Color getColor(String colorSpec) {

		if (colorSpec == null) {
			return null;
		}

		String normalSpec = colorSpec.toLowerCase().trim();
		synchronized (this) {
			Color color = colorMap.get(normalSpec);
			if (color == null) {
				color = getRGBA(color, normalSpec);
				color = getRGB(color, normalSpec);
				color = getHex(color, normalSpec);
				color = getHSLA(color, normalSpec);
				color = getHSL(color, normalSpec);
			}
			return color;
		}
	}

	/**
	 * Get RGBA color.
	 *
	 * @param c
	 *            the color
	 * @param normalSpec
	 *            the color spec
	 * @return the color
	 */
	private Color getRGBA(Color c, String normalSpec) {
		Color color = null;
		if (c == null && normalSpec.startsWith(RGBA_START)) {
			int endIdx = normalSpec.lastIndexOf(')');
			String commaValues = "";

			if (endIdx == -1) {
				commaValues = normalSpec.substring(RGBA_START.length());
			} else {
				commaValues = normalSpec.substring(RGBA_START.length(), endIdx);
			}

			String[] strs = Strings.splitUsingTokenizer(commaValues, ",");
			int red = Integer.parseInt(strs[0].trim());
			int green = Integer.parseInt(strs[1].trim());
			int blue = Integer.parseInt(strs[2].trim());
			int alpha = new Float(255 * Float.parseFloat(strs[3].trim())).intValue();
			color = new Color(normalize(red), normalize(green), normalize(blue), alpha);
			colorMap.put(normalSpec, color);
		} else {
			color = c;
		}
		return color;
	}

	/**
	 * Get RGB color.
	 *
	 * @param c
	 *            the color
	 * @param normalSpec
	 *            the color spec
	 * @return the color
	 */
	private Color getRGB(Color c, String normalSpec) {
		Color color = null;
		if (c == null && normalSpec.startsWith(RGB_START)) {
			int endIdx = normalSpec.lastIndexOf(')');
			String commaValues = "";

			if (endIdx == -1) {
				commaValues = normalSpec.substring(RGB_START.length());
			} else {
				commaValues = normalSpec.substring(RGB_START.length(), endIdx);
			}
			
			String[] strs = Strings.splitUsingTokenizer(commaValues, ",");
			int red = Integer.parseInt(strs[0].trim());
			int green = Integer.parseInt(strs[1].trim());
			int blue = Integer.parseInt(strs[2].trim());
			color = new Color(normalize(red), normalize(green), normalize(blue));
			colorMap.put(normalSpec, color);
		} else {
			color = c;
		}
		return color;
	}
	
	private Color getHSL(Color c, String normalSpec) {
		Color color = null;
		if (c == null && normalSpec.startsWith(HSL_START)) {
			int endIdx = normalSpec.lastIndexOf(')');
			String commaValues = "";

			if (endIdx == -1) {
				commaValues = normalSpec.substring(HSL_START.length());
			} else {
				commaValues = normalSpec.substring(HSL_START.length(), endIdx);
			}

			String[] strs = Strings.splitUsingTokenizer(commaValues, ",");
			float h = parseValue(strs[0].trim(), 360);
			float s = parsePercent(strs[1].trim());
			float l = parsePercent(strs[2].trim());

			color = toRGB(h, s, l, 1);
			colorMap.put(normalSpec, color);
		} else {
			color = c;
		}
		return color;
	}

	private Color getHSLA(Color c, String normalSpec) {
		Color color = null;
		if (c == null && normalSpec.startsWith(HSLA_START)) {
			int endIdx = normalSpec.lastIndexOf(')');
			String commaValues = "";
			
			if (endIdx == -1) {
				commaValues = normalSpec.substring(HSLA_START.length());
			} else {
				commaValues = normalSpec.substring(HSLA_START.length(), endIdx);
			}
			
			String[] strs = Strings.splitUsingTokenizer(commaValues, ",");

			float h = parseValue(strs[0].trim(), 360);
			float s = parsePercent(strs[1].trim());
			float l = parsePercent(strs[2].trim());
			float alpha = parseAlpha(strs[3].trim());
			color = toRGB(h, s, l, alpha);
			colorMap.put(normalSpec, color);
		} else {
			color = c;
		}
		return color;
	}
	
	/**
	 * Get HEX color.
	 *
	 * @param c
	 *            the color
	 * @param normalSpec
	 *            the color spec
	 * @return the color
	 */
	private Color getHex(Color c, String normalSpec) {
		Color color = null;
		if (normalSpec.startsWith("#")) {
			color = Color.decode(normalSpec);
			colorMap.put(normalSpec, color);
		} else {
			color = c;
		}
		return color;
	}

	private float parseValue(String val, int max) {
		if (val.endsWith("%")) {
			return (int) (parsePercent(val) * max);
		}
		return Float.parseFloat(val);
	}

	private float parsePercent(String perc) {
		return Float.parseFloat(perc.substring(0, perc.length() - 1));
	}

	private float parseAlpha(String alpha) {
		return Float.parseFloat(alpha);
	}

	private Color toRGB(float h, float s, float l, float alpha) {
		h = h % 360.0f;
		h /= 360f;
		s /= 100f;
		l /= 100f;

		float q = 0;

		if (l < 0.5)
			q = l * (1 + s);
		else
			q = (l + s) - (s * l);

		float p = 2 * l - q;

		float r = Math.max(0, hueToRgb(p, q, h + (1.0f / 3.0f)));
		float g = Math.max(0, hueToRgb(p, q, h));
		float b = Math.max(0, hueToRgb(p, q, h - (1.0f / 3.0f)));

		r = Math.min(r, 1.0f);
		g = Math.min(g, 1.0f);
		b = Math.min(b, 1.0f);

		return new Color(r, g, b, alpha);
	}

	/** Helper method that converts hue to rgb */
	private float hueToRgb(float p, float q, float h) {
		if (h < 0)
			h += 1;

		if (h > 1)
			h -= 1;

		if (6 * h < 1) {
			return p + ((q - p) * 6 * h);
		}

		if (2 * h < 1) {
			return q;
		}

		if (3 * h < 2) {
			return p + ((q - p) * 6 * ((2.0f / 3.0f) - h));
		}
		return p;
	}

	/**
	 * Normalize color component within allow range 0..255
	 *
	 * @param colorComponent
	 *            any value
	 * @return 0 <= value <= 255
	 */
	private static int normalize(int colorComponent) {
		if (colorComponent > 255) {
			colorComponent = 255;
		} else if (colorComponent < 0) {
			colorComponent = 0;
		}
		return colorComponent;
	}
}
