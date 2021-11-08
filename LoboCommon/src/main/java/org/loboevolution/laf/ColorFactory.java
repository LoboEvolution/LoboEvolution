/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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
import java.util.HashMap;
import java.util.Map;

/**
 * A factory for creating Color objects.
 */
public final class ColorFactory {

	/** Constant TRANSPARENT */
	public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

	/** The instance. */
	private static ColorFactory instance;

	/** Cache of all colors on the page, initialized with W3C colors. */
	private static final Map<String, Color> COLOR_MAP = new HashMap<>();

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

	static {
		COLOR_MAP.put("transparent", new Color(0, 0, 0, 0));
		COLOR_MAP.put("aliceblue", new Color(240, 248, 255));
		COLOR_MAP.put("antiquewhite", new Color(250, 235, 215));
		COLOR_MAP.put("aqua", new Color(0, 255, 255));
		COLOR_MAP.put("aquamarine", new Color(127, 255, 212));
		COLOR_MAP.put("azure", new Color(240, 255, 255));
		COLOR_MAP.put("beige", new Color(245, 245, 220));
		COLOR_MAP.put("bisque", new Color(255, 228, 196));
		COLOR_MAP.put("black", new Color(0, 0, 0));
		COLOR_MAP.put("blanchedalmond", new Color(255, 235, 205));
		COLOR_MAP.put("blue", new Color(0, 0, 255));
		COLOR_MAP.put("blueviolet", new Color(138, 43, 226));
		COLOR_MAP.put("brown", new Color(165, 42, 42));
		COLOR_MAP.put("burlywood", new Color(222, 184, 135));
		COLOR_MAP.put("cadetblue", new Color(95, 158, 160));
		COLOR_MAP.put("chartreuse", new Color(127, 255, 0));
		COLOR_MAP.put("chocolate", new Color(210, 105, 30));
		COLOR_MAP.put("coral", new Color(255, 127, 80));
		COLOR_MAP.put("cornflowerblue", new Color(100, 149, 237));
		COLOR_MAP.put("cornsilk", new Color(255, 248, 220));
		COLOR_MAP.put("crimson", new Color(220, 20, 60));
		COLOR_MAP.put("cyan", new Color(0, 255, 255));
		COLOR_MAP.put("darkblue", new Color(0, 0, 139));
		COLOR_MAP.put("darkcyan", new Color(0, 139, 139));
		COLOR_MAP.put("darkgoldenrod", new Color(184, 134, 11));
		COLOR_MAP.put("darkgray", new Color(169, 169, 169));
		COLOR_MAP.put("darkgreen", new Color(0, 100, 0));
		COLOR_MAP.put("darkgrey", new Color(169, 169, 169));
		COLOR_MAP.put("darkkhaki", new Color(189, 183, 107));
		COLOR_MAP.put("darkmagenta", new Color(139, 0, 139));
		COLOR_MAP.put("darkolivegreen", new Color(85, 107, 47));
		COLOR_MAP.put("darkorange", new Color(255, 140, 0));
		COLOR_MAP.put("darkorchid", new Color(153, 50, 204));
		COLOR_MAP.put("darkred", new Color(139, 0, 0));
		COLOR_MAP.put("darksalmon", new Color(233, 150, 122));
		COLOR_MAP.put("darkseagreen", new Color(143, 188, 143));
		COLOR_MAP.put("darkslateblue", new Color(72, 61, 139));
		COLOR_MAP.put("darkslategray", new Color(47, 79, 79));
		COLOR_MAP.put("darkslategrey", new Color(47, 79, 79));
		COLOR_MAP.put("darkturquoise", new Color(0, 206, 209));
		COLOR_MAP.put("darkviolet", new Color(148, 0, 211));
		COLOR_MAP.put("deeppink", new Color(255, 20, 147));
		COLOR_MAP.put("deepskyblue", new Color(0, 191, 255));
		COLOR_MAP.put("dimgray", new Color(105, 105, 105));
		COLOR_MAP.put("dimgrey", new Color(105, 105, 105));
		COLOR_MAP.put("dodgerblue", new Color(30, 144, 255));
		COLOR_MAP.put("firebrick", new Color(178, 34, 34));
		COLOR_MAP.put("floralwhite", new Color(255, 250, 240));
		COLOR_MAP.put("forestgreen", new Color(34, 139, 34));
		COLOR_MAP.put("fuchsia", new Color(255, 0, 255));
		COLOR_MAP.put("gainsboro", new Color(220, 220, 220));
		COLOR_MAP.put("ghostwhite", new Color(248, 248, 255));
		COLOR_MAP.put("gold", new Color(255, 215, 0));
		COLOR_MAP.put("goldenrod", new Color(218, 165, 32));
		COLOR_MAP.put("gray", new Color(128, 128, 128));
		COLOR_MAP.put("green", new Color(0, 128, 0));
		COLOR_MAP.put("greenyellow", new Color(173, 255, 47));
		COLOR_MAP.put("grey", new Color(128, 128, 128));
		COLOR_MAP.put("honeydew", new Color(240, 255, 240));
		COLOR_MAP.put("hotpink", new Color(255, 105, 180));
		COLOR_MAP.put("indianred", new Color(205, 92, 92));
		COLOR_MAP.put("indigo", new Color(75, 0, 130));
		COLOR_MAP.put("ivory", new Color(255, 255, 240));
		COLOR_MAP.put("khaki", new Color(240, 230, 140));
		COLOR_MAP.put("lavender", new Color(230, 230, 250));
		COLOR_MAP.put("lavenderblush", new Color(255, 240, 245));
		COLOR_MAP.put("lawngreen", new Color(124, 252, 0));
		COLOR_MAP.put("lemonchiffon", new Color(255, 250, 205));
		COLOR_MAP.put("lightblue", new Color(173, 216, 230));
		COLOR_MAP.put("lightcoral", new Color(240, 128, 128));
		COLOR_MAP.put("lightcyan", new Color(224, 255, 255));
		COLOR_MAP.put("lightgoldenrodyellow", new Color(250, 250, 210));
		COLOR_MAP.put("lightgray", new Color(211, 211, 211));
		COLOR_MAP.put("lightgreen", new Color(144, 238, 144));
		COLOR_MAP.put("lightgrey", new Color(211, 211, 211));
		COLOR_MAP.put("lightpink", new Color(255, 182, 193));
		COLOR_MAP.put("lightsalmon", new Color(255, 160, 122));
		COLOR_MAP.put("lightseagreen", new Color(32, 178, 170));
		COLOR_MAP.put("lightskyblue", new Color(135, 206, 250));
		COLOR_MAP.put("lightslategray", new Color(119, 136, 153));
		COLOR_MAP.put("lightslategrey", new Color(119, 136, 153));
		COLOR_MAP.put("lightsteelblue", new Color(176, 196, 222));
		COLOR_MAP.put("lightyellow", new Color(255, 255, 224));
		COLOR_MAP.put("lime", new Color(0, 255, 0));
		COLOR_MAP.put("limegreen", new Color(50, 205, 50));
		COLOR_MAP.put("linen", new Color(250, 240, 230));
		COLOR_MAP.put("magenta", new Color(255, 0, 255));
		COLOR_MAP.put("maroon", new Color(128, 0, 0));
		COLOR_MAP.put("mediumaquamarine", new Color(102, 205, 170));
		COLOR_MAP.put("mediumblue", new Color(0, 0, 205));
		COLOR_MAP.put("mediumorchid", new Color(186, 85, 211));
		COLOR_MAP.put("mediumpurple", new Color(147, 112, 219));
		COLOR_MAP.put("mediumseagreen", new Color(60, 179, 113));
		COLOR_MAP.put("mediumslateblue", new Color(123, 104, 238));
		COLOR_MAP.put("mediumspringgreen", new Color(0, 250, 154));
		COLOR_MAP.put("mediumturquoise", new Color(72, 209, 204));
		COLOR_MAP.put("mediumvioletred", new Color(199, 21, 133));
		COLOR_MAP.put("midnightblue", new Color(25, 25, 112));
		COLOR_MAP.put("mintcream", new Color(245, 255, 250));
		COLOR_MAP.put("mistyrose", new Color(255, 228, 225));
		COLOR_MAP.put("moccasin", new Color(255, 228, 181));
		COLOR_MAP.put("navajowhite", new Color(255, 222, 173));
		COLOR_MAP.put("navy", new Color(0, 0, 128));
		COLOR_MAP.put("oldlace", new Color(253, 245, 230));
		COLOR_MAP.put("olive", new Color(128, 128, 0));
		COLOR_MAP.put("olivedrab", new Color(107, 142, 35));
		COLOR_MAP.put("orange", new Color(255, 165, 0));
		COLOR_MAP.put("orangered", new Color(255, 69, 0));
		COLOR_MAP.put("orchid", new Color(218, 112, 214));
		COLOR_MAP.put("palegoldenrod", new Color(238, 232, 170));
		COLOR_MAP.put("palegreen", new Color(152, 251, 152));
		COLOR_MAP.put("paleturquoise", new Color(175, 238, 238));
		COLOR_MAP.put("palevioletred", new Color(219, 112, 147));
		COLOR_MAP.put("papayawhip", new Color(255, 239, 213));
		COLOR_MAP.put("peachpuff", new Color(255, 218, 185));
		COLOR_MAP.put("peru", new Color(205, 133, 63));
		COLOR_MAP.put("pink", new Color(255, 192, 203));
		COLOR_MAP.put("plum", new Color(221, 160, 221));
		COLOR_MAP.put("powderblue", new Color(176, 224, 230));
		COLOR_MAP.put("purple", new Color(128, 0, 128));
		COLOR_MAP.put("red", new Color(255, 0, 0));
		COLOR_MAP.put("rosybrown", new Color(188, 143, 143));
		COLOR_MAP.put("royalblue", new Color(65, 105, 225));
		COLOR_MAP.put("saddlebrown", new Color(139, 69, 19));
		COLOR_MAP.put("salmon", new Color(250, 128, 114));
		COLOR_MAP.put("sandybrown", new Color(244, 164, 96));
		COLOR_MAP.put("seagreen", new Color(46, 139, 87));
		COLOR_MAP.put("seashell", new Color(255, 245, 238));
		COLOR_MAP.put("sienna", new Color(160, 82, 45));
		COLOR_MAP.put("silver", new Color(192, 192, 192));
		COLOR_MAP.put("skyblue", new Color(135, 206, 235));
		COLOR_MAP.put("slateblue", new Color(106, 90, 205));
		COLOR_MAP.put("slategray", new Color(112, 128, 144));
		COLOR_MAP.put("slategrey", new Color(112, 128, 144));
		COLOR_MAP.put("snow", new Color(255, 250, 250));
		COLOR_MAP.put("springgreen", new Color(0, 255, 127));
		COLOR_MAP.put("steelblue", new Color(70, 130, 180));
		COLOR_MAP.put("tan", new Color(210, 180, 140));
		COLOR_MAP.put("teal", new Color(0, 128, 128));
		COLOR_MAP.put("thistle", new Color(216, 191, 216));
		COLOR_MAP.put("tomato", new Color(255, 99, 71));
		COLOR_MAP.put("turquoise", new Color(64, 224, 208));
		COLOR_MAP.put("violet", new Color(238, 130, 238));
		COLOR_MAP.put("wheat", new Color(245, 222, 179));
		COLOR_MAP.put("white", new Color(255, 255, 255));
		COLOR_MAP.put("whitesmoke", new Color(245, 245, 245));
		COLOR_MAP.put("yellow", new Color(255, 255, 0));
		COLOR_MAP.put("yellowgreen", new Color(154, 205, 50));
	}

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
			this.colorMap = new HashMap<>(COLOR_MAP);
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
		if (c == null && normalSpec.startsWith(colorStart)) {
			final String commaValues = getCommaValues(normalSpec,colorStart);
			final String[] splitComma = Strings.splitUsingTokenizer(commaValues, ",");
			final int red = (int) parseValue(splitComma[0].trim(), 255);
			final int green =  (int) parseValue(splitComma[1].trim(), 255);
			final int blue =  (int) parseValue(splitComma[2].trim(), 255);
			if (splitComma.length > 3) {
				float alpha = parseAlpha(splitComma[splitComma.length - 1].trim());
				color = new Color(normalize(red), normalize(green), normalize(blue), alpha);
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
		final String[] list = {"#", RGBA_START, RGB_START, HSLA_START, HSL_START};
		return Arrays.asList(list).contains(normalSpec) || this.colorMap.containsKey(normalSpec);
	}

	/**
	 * <p>isRgbOrHsl.</p>
	 *
	 * @param colorSpec a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean isRgbOrHsl(String colorSpec) {
		final String normalSpec = colorSpec.toLowerCase();
		final String[] list = {RGBA_START, RGB_START, HSLA_START, HSL_START};
		return Arrays.asList(list).contains(normalSpec);
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
		hex = hex.replace("#", "");
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
