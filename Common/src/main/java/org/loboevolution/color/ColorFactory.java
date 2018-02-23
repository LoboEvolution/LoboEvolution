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
package org.loboevolution.color;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.loboevolution.util.Strings;

/**
 * A factory for creating Color objects.
 *
 * @author J. H. S.
 */
public class ColorFactory implements ColorCommon{
		
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
			this.colorMap = mapColor();
		}
	}

	/**
	 * Gets the instance.
	 *
	 * @return the instance
	 */
	public static synchronized final ColorFactory getInstance() {
		if (instance == null) {
			instance = new ColorFactory();
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
			int alpha = Float.valueOf(255 * Float.parseFloat(strs[3].trim())).intValue();
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
	
	private Map<String, Color> mapColor() {
		Map<String, Color> colorMap = new HashMap<String, Color>();
		colorMap.put("transparent", new Color(0, 0, 0, 0));
		colorMap.put("aliceblue", new Color(ALICEBLUE));
		colorMap.put("antiquewhite", new Color(ANTIQUEWHITE));
		colorMap.put("antiquewhite1", new Color(ANTIQUEWHITE1));
		colorMap.put("antiquewhite2", new Color(ANTIQUEWHITE2));
		colorMap.put("antiquewhite3", new Color(ANTIQUEWHITE3));
		colorMap.put("antiquewhite4", new Color(ANTIQUEWHITE4));
		colorMap.put("aqua", new Color(AQUA));
		colorMap.put("aquamarine", new Color(AQUAMARINE));
		colorMap.put("aquamarine1", new Color(AQUAMARINE1));
		colorMap.put("aquamarine2", new Color(AQUAMARINE2));
		colorMap.put("azure", new Color(AZURE));
		colorMap.put("azure1", new Color(AZURE1));
		colorMap.put("azure2", new Color(AZURE2));
		colorMap.put("azure3", new Color(AZURE3));
		colorMap.put("beige", new Color(BEIGE));
		colorMap.put("bisque", new Color(BISQUE));
		colorMap.put("bisque1", new Color(BISQUE1));
		colorMap.put("bisque2", new Color(BISQUE2));
		colorMap.put("bisque3", new Color(BISQUE3));
		colorMap.put("black", new Color(BLACK));
		colorMap.put("blanchedalmond", new Color(BLANCHEDALMOND));
		colorMap.put("blue", new Color(BLUE));
		colorMap.put("blue1", new Color(BLUE1));
		colorMap.put("blueviolet", new Color(BLUEVIOLET));
		colorMap.put("brown", new Color(BROWN));
		colorMap.put("brown1", new Color(BROWN1));
		colorMap.put("brown2", new Color(BROWN2));
		colorMap.put("brown3", new Color(BROWN3));
		colorMap.put("brown4", new Color(BROWN4));
		colorMap.put("burlywood", new Color(BURLYWOOD));
		colorMap.put("burlywood1", new Color(BURLYWOOD1));
		colorMap.put("burlywood2", new Color(BURLYWOOD2));
		colorMap.put("burlywood3", new Color(BURLYWOOD3));
		colorMap.put("burlywood4", new Color(BURLYWOOD4));
		colorMap.put("cadetblue", new Color(CADETBLUE));
		colorMap.put("cadetblue1", new Color(CADETBLUE1));
		colorMap.put("cadetblue2", new Color(CADETBLUE2));
		colorMap.put("cadetblue3", new Color(CADETBLUE3));
		colorMap.put("cadetblue4", new Color(CADETBLUE4));
		colorMap.put("chartreuse", new Color(CHARTREUSE));
		colorMap.put("chartreuse1", new Color(CHARTREUSE1));
		colorMap.put("chartreuse2", new Color(CHARTREUSE2));
		colorMap.put("chartreuse3", new Color(CHARTREUSE3));
		colorMap.put("chocolate", new Color(CHOCOLATE));
		colorMap.put("chocolate1", new Color(CHOCOLATE1));
		colorMap.put("chocolate2", new Color(CHOCOLATE2));
		colorMap.put("chocolate3", new Color(CHOCOLATE3));
		colorMap.put("coral", new Color(CORAL));
		colorMap.put("coral1", new Color(CORAL1));
		colorMap.put("coral2", new Color(CORAL2));
		colorMap.put("coral3", new Color(CORAL3));
		colorMap.put("coral4", new Color(CORAL4));
		colorMap.put("cornflowerblue", new Color(CORNFLOWERBLUE));
		colorMap.put("cornsilk", new Color(CORNSILK));
		colorMap.put("cornsilk1", new Color(CORNSILK1));
		colorMap.put("cornsilk2", new Color(CORNSILK2));
		colorMap.put("cornsilk3", new Color(CORNSILK3));
		colorMap.put("crimson", new Color(CRIMSON));
		colorMap.put("cyan", new Color(CYAN));
		colorMap.put("cyan1", new Color(CYAN1));
		colorMap.put("cyan2", new Color(CYAN2));
		colorMap.put("darkblue", new Color(DARKBLUE));
		colorMap.put("darkcyan", new Color(DARKCYAN));
		colorMap.put("darkgoldenrod", new Color(DARKGOLDENROD));
		colorMap.put("darkgoldenrod1", new Color(DARKGOLDENROD1));
		colorMap.put("darkgoldenrod2", new Color(DARKGOLDENROD2));
		colorMap.put("darkgoldenrod3", new Color(DARKGOLDENROD3));
		colorMap.put("darkgoldenrod4", new Color(DARKGOLDENROD4));
		colorMap.put("darkgray", new Color(DARKGRAY));
		colorMap.put("darkgreen", new Color(DARKGREEN));
		colorMap.put("darkkhaki", new Color(DARKKHAKI));
		colorMap.put("darkmagenta", new Color(DARKMAGENTA));
		colorMap.put("darkolivegreen", new Color(DARKOLIVEGREEN));
		colorMap.put("darkolivegreen1", new Color(DARKOLIVEGREEN1));
		colorMap.put("darkolivegreen2", new Color(DARKOLIVEGREEN2));
		colorMap.put("darkolivegreen3", new Color(DARKOLIVEGREEN3));
		colorMap.put("darkolivegreen4", new Color(DARKOLIVEGREEN4));
		colorMap.put("darkorange", new Color(DARKORANGE));
		colorMap.put("darkorange1", new Color(DARKORANGE1));
		colorMap.put("darkorange2", new Color(DARKORANGE2));
		colorMap.put("darkorange3", new Color(DARKORANGE3));
		colorMap.put("darkorange4", new Color(DARKORANGE4));
		colorMap.put("darkorchid", new Color(DARKORCHID));
		colorMap.put("darkorchid1", new Color(DARKORCHID1));
		colorMap.put("darkorchid2", new Color(DARKORCHID2));
		colorMap.put("darkorchid3", new Color(DARKORCHID3));
		colorMap.put("darkorchid4", new Color(DARKORCHID4));
		colorMap.put("darkred", new Color(DARKRED));
		colorMap.put("darksalmon", new Color(DARKSALMON));
		colorMap.put("darkseagreen", new Color(DARKSEAGREEN));
		colorMap.put("darkseagreen1", new Color(DARKSEAGREEN1));
		colorMap.put("darkseagreen2", new Color(DARKSEAGREEN2));
		colorMap.put("darkseagreen3", new Color(DARKSEAGREEN3));
		colorMap.put("darkseagreen4", new Color(DARKSEAGREEN4));
		colorMap.put("darkslateblue", new Color(DARKSLATEBLUE));
		colorMap.put("darkslategray", new Color(DARKSLATEGRAY));
		colorMap.put("darkslategray1", new Color(DARKSLATEGRAY1));
		colorMap.put("darkslategray2", new Color(DARKSLATEGRAY2));
		colorMap.put("darkslategray3", new Color(DARKSLATEGRAY3));
		colorMap.put("darkslategray4", new Color(DARKSLATEGRAY4));
		colorMap.put("darkturquoise", new Color(DARKTURQUOISE));
		colorMap.put("darkviolet", new Color(DARKVIOLET));
		colorMap.put("deeppink", new Color(DEEPPINK));
		colorMap.put("deeppink1", new Color(DEEPPINK1));
		colorMap.put("deeppink2", new Color(DEEPPINK2));
		colorMap.put("deeppink3", new Color(DEEPPINK3));
		colorMap.put("deepskyblue", new Color(DEEPSKYBLUE));
		colorMap.put("deepskyblue1", new Color(DEEPSKYBLUE1));
		colorMap.put("deepskyblue2", new Color(DEEPSKYBLUE2));
		colorMap.put("deepskyblue3", new Color(DEEPSKYBLUE3));
		colorMap.put("dimgray", new Color(DIMGRAY));
		colorMap.put("dodgerblue", new Color(DODGERBLUE));
		colorMap.put("dodgerblue1", new Color(DODGERBLUE1));
		colorMap.put("dodgerblue2", new Color(DODGERBLUE2));
		colorMap.put("dodgerblue3", new Color(DODGERBLUE3));
		colorMap.put("firebrick", new Color(FIREBRICK));
		colorMap.put("firebrick1", new Color(FIREBRICK1));
		colorMap.put("firebrick2", new Color(FIREBRICK2));
		colorMap.put("firebrick3", new Color(FIREBRICK3));
		colorMap.put("firebrick4", new Color(FIREBRICK4));
		colorMap.put("floralwhite", new Color(FLORALWHITE));
		colorMap.put("forestgreen", new Color(FORESTGREEN));
		colorMap.put("fractal", new Color(FRACTAL));
		colorMap.put("gainsboro", new Color(GAINSBORO));
		colorMap.put("ghostwhite", new Color(GHOSTWHITE));
		colorMap.put("gold", new Color(GOLD));
		colorMap.put("gold1", new Color(GOLD1));
		colorMap.put("gold2", new Color(GOLD2));
		colorMap.put("gold3", new Color(GOLD3));
		colorMap.put("goldenrod", new Color(GOLDENROD));
		colorMap.put("goldenrod1", new Color(GOLDENROD1));
		colorMap.put("goldenrod2", new Color(GOLDENROD2));
		colorMap.put("goldenrod3", new Color(GOLDENROD3));
		colorMap.put("goldenrod4", new Color(GOLDENROD4));
		colorMap.put("gray1", new Color(GRAY1));
		colorMap.put("gray10", new Color(GRAY10));
		colorMap.put("gray11", new Color(GRAY11));
		colorMap.put("gray12", new Color(GRAY12));
		colorMap.put("gray13", new Color(GRAY13));
		colorMap.put("gray14", new Color(GRAY14));
		colorMap.put("gray15", new Color(GRAY15));
		colorMap.put("gray16", new Color(GRAY16));
		colorMap.put("gray17", new Color(GRAY17));
		colorMap.put("gray18", new Color(GRAY18));
		colorMap.put("gray19", new Color(GRAY19));
		colorMap.put("gray2", new Color(GRAY2));
		colorMap.put("gray20", new Color(GRAY20));
		colorMap.put("gray21", new Color(GRAY21));
		colorMap.put("gray22", new Color(GRAY22));
		colorMap.put("gray23", new Color(GRAY23));
		colorMap.put("gray24", new Color(GRAY24));
		colorMap.put("gray25", new Color(GRAY25));
		colorMap.put("gray26", new Color(GRAY26));
		colorMap.put("gray27", new Color(GRAY27));
		colorMap.put("gray28", new Color(GRAY28));
		colorMap.put("gray29", new Color(GRAY29));
		colorMap.put("gray3", new Color(GRAY3));
		colorMap.put("gray30", new Color(GRAY30));
		colorMap.put("gray31", new Color(GRAY31));
		colorMap.put("gray32", new Color(GRAY32));
		colorMap.put("gray33", new Color(GRAY33));
		colorMap.put("gray34", new Color(GRAY34));
		colorMap.put("gray35", new Color(GRAY35));
		colorMap.put("gray36", new Color(GRAY36));
		colorMap.put("gray37", new Color(GRAY37));
		colorMap.put("gray38", new Color(GRAY38));
		colorMap.put("gray", new Color(GRAY));
		colorMap.put("gray4", new Color(GRAY4));
		colorMap.put("gray40", new Color(GRAY40));
		colorMap.put("gray41", new Color(GRAY41));
		colorMap.put("gray42", new Color(GRAY42));
		colorMap.put("gray43", new Color(GRAY43));
		colorMap.put("gray44", new Color(GRAY44));
		colorMap.put("gray45", new Color(GRAY45));
		colorMap.put("gray46", new Color(GRAY46));
		colorMap.put("gray47", new Color(GRAY47));
		colorMap.put("gray48", new Color(GRAY48));
		colorMap.put("gray49", new Color(GRAY49));
		colorMap.put("gray5", new Color(GRAY5));
		colorMap.put("gray50", new Color(GRAY50));
		colorMap.put("gray51", new Color(GRAY51));
		colorMap.put("gray52", new Color(GRAY52));
		colorMap.put("gray53", new Color(GRAY53));
		colorMap.put("gray54", new Color(GRAY54));
		colorMap.put("gray55", new Color(GRAY55));
		colorMap.put("gray56", new Color(GRAY56));
		colorMap.put("gray57", new Color(GRAY57));
		colorMap.put("gray58", new Color(GRAY58));
		colorMap.put("gray59", new Color(GRAY59));
		colorMap.put("gray6", new Color(GRAY6));
		colorMap.put("gray60", new Color(GRAY60));
		colorMap.put("gray61", new Color(GRAY61));
		colorMap.put("gray62", new Color(GRAY62));
		colorMap.put("gray63", new Color(GRAY63));
		colorMap.put("gray64", new Color(GRAY64));
		colorMap.put("gray65", new Color(GRAY65));
		colorMap.put("gray66", new Color(GRAY66));
		colorMap.put("gray67", new Color(GRAY67));
		colorMap.put("gray68", new Color(GRAY68));
		colorMap.put("gray69", new Color(GRAY69));
		colorMap.put("gray7", new Color(GRAY7));
		colorMap.put("gray70", new Color(GRAY70));
		colorMap.put("gray71", new Color(GRAY71));
		colorMap.put("gray72", new Color(GRAY72));
		colorMap.put("gray73", new Color(GRAY73));
		colorMap.put("gray74", new Color(GRAY74));
		colorMap.put("gray75", new Color(GRAY75));
		colorMap.put("gray76", new Color(GRAY76));
		colorMap.put("gray77", new Color(GRAY77));
		colorMap.put("gray78", new Color(GRAY78));
		colorMap.put("gray79", new Color(GRAY79));
		colorMap.put("gray8", new Color(GRAY8));
		colorMap.put("gray80", new Color(GRAY80));
		colorMap.put("gray81", new Color(GRAY81));
		colorMap.put("gray82", new Color(GRAY82));
		colorMap.put("gray83", new Color(GRAY83));
		colorMap.put("gray84", new Color(GRAY84));
		colorMap.put("gray85", new Color(GRAY85));
		colorMap.put("gray86", new Color(GRAY86));
		colorMap.put("gray87", new Color(GRAY87));
		colorMap.put("gray88", new Color(GRAY88));
		colorMap.put("gray89", new Color(GRAY89));
		colorMap.put("gray9", new Color(GRAY9));
		colorMap.put("gray90", new Color(GRAY90));
		colorMap.put("gray91", new Color(GRAY91));
		colorMap.put("gray92", new Color(GRAY92));
		colorMap.put("gray93", new Color(GRAY93));
		colorMap.put("gray94", new Color(GRAY94));
		colorMap.put("gray95", new Color(GRAY95));
		colorMap.put("gray96", new Color(GRAY96));
		colorMap.put("gray97", new Color(GRAY97));
		colorMap.put("green", new Color(GREEN));
		colorMap.put("green1", new Color(GREEN1));
		colorMap.put("green2", new Color(GREEN2));
		colorMap.put("green3", new Color(GREEN3));
		colorMap.put("greenyellow", new Color(GREENYELLOW));
		colorMap.put("honeydew", new Color(HONEYDEW));
		colorMap.put("honeydew1", new Color(HONEYDEW1));
		colorMap.put("honeydew2", new Color(HONEYDEW2));
		colorMap.put("honeydew3", new Color(HONEYDEW3));
		colorMap.put("hotpink", new Color(HOTPINK));
		colorMap.put("hotpink1", new Color(HOTPINK1));
		colorMap.put("hotpink2", new Color(HOTPINK2));
		colorMap.put("hotpink3", new Color(HOTPINK3));
		colorMap.put("hotpink4", new Color(HOTPINK4));
		colorMap.put("indianred", new Color(INDIANRED));
		colorMap.put("indianred1", new Color(INDIANRED1));
		colorMap.put("indianred2", new Color(INDIANRED2));
		colorMap.put("indianred3", new Color(INDIANRED3));
		colorMap.put("indianred4", new Color(INDIANRED4));
		colorMap.put("indigo", new Color(INDIGO));
		colorMap.put("ivory", new Color(IVORY));
		colorMap.put("ivory1", new Color(IVORY1));
		colorMap.put("ivory2", new Color(IVORY2));
		colorMap.put("ivory3", new Color(IVORY3));
		colorMap.put("khaki", new Color(KHAKI));
		colorMap.put("khaki1", new Color(KHAKI1));
		colorMap.put("khaki2", new Color(KHAKI2));
		colorMap.put("khaki3", new Color(KHAKI3));
		colorMap.put("khaki4", new Color(KHAKI4));
		colorMap.put("lavender", new Color(LAVENDER));
		colorMap.put("lavenderblush", new Color(LAVENDERBLUSH));
		colorMap.put("lavenderblush1", new Color(LAVENDERBLUSH1));
		colorMap.put("lavenderblush2", new Color(LAVENDERBLUSH2));
		colorMap.put("lavenderblush3", new Color(LAVENDERBLUSH3));
		colorMap.put("lawngreen", new Color(LAWNGREEN));
		colorMap.put("lemonchiffon", new Color(LEMONCHIFFON));
		colorMap.put("lemonchiffon1", new Color(LEMONCHIFFON1));
		colorMap.put("lemonchiffon2", new Color(LEMONCHIFFON2));
		colorMap.put("lemonchiffon3", new Color(LEMONCHIFFON3));
		colorMap.put("lightblue", new Color(LIGHTBLUE));
		colorMap.put("lightblue1", new Color(LIGHTBLUE1));
		colorMap.put("lightblue2", new Color(LIGHTBLUE2));
		colorMap.put("lightblue3", new Color(LIGHTBLUE3));
		colorMap.put("lightblue4", new Color(LIGHTBLUE4));
		colorMap.put("lightcoral", new Color(LIGHTCORAL));
		colorMap.put("lightcyan", new Color(LIGHTCYAN));
		colorMap.put("lightcyan1", new Color(LIGHTCYAN1));
		colorMap.put("lightcyan2", new Color(LIGHTCYAN2));
		colorMap.put("lightcyan3", new Color(LIGHTCYAN3));
		colorMap.put("lightgoldenrod", new Color(LIGHTGOLDENROD));
		colorMap.put("lightgoldenrod1", new Color(LIGHTGOLDENROD1));
		colorMap.put("lightgoldenrod2", new Color(LIGHTGOLDENROD2));
		colorMap.put("lightgoldenrod3", new Color(LIGHTGOLDENROD3));
		colorMap.put("lightgoldenrod4", new Color(LIGHTGOLDENROD4));
		colorMap.put("lightgoldenrodyellow", new Color(LIGHTGOLDENRODYELLOW));
		colorMap.put("lightgray", new Color(LIGHTGRAY));
		colorMap.put("lightgrey", new Color(LIGHTGRAY));
		colorMap.put("lightgreen", new Color(LIGHTGREEN));
		colorMap.put("lightpink", new Color(LIGHTPINK));
		colorMap.put("lightpink1", new Color(LIGHTPINK1));
		colorMap.put("lightpink2", new Color(LIGHTPINK2));
		colorMap.put("lightpink3", new Color(LIGHTPINK3));
		colorMap.put("lightpink4", new Color(LIGHTPINK4));
		colorMap.put("lightsalmon", new Color(LIGHTSALMON));
		colorMap.put("lightsalmon1", new Color(LIGHTSALMON1));
		colorMap.put("lightsalmon2", new Color(LIGHTSALMON2));
		colorMap.put("lightsalmon3", new Color(LIGHTSALMON3));
		colorMap.put("lightseagreen", new Color(LIGHTSEAGREEN));
		colorMap.put("lightskyblue", new Color(LIGHTSKYBLUE));
		colorMap.put("lightskyblue1", new Color(LIGHTSKYBLUE1));
		colorMap.put("lightskyblue2", new Color(LIGHTSKYBLUE2));
		colorMap.put("lightskyblue3", new Color(LIGHTSKYBLUE3));
		colorMap.put("lightskyblue4", new Color(LIGHTSKYBLUE4));
		colorMap.put("lightslateblue", new Color(LIGHTSLATEBLUE));
		colorMap.put("lightslategray", new Color(LIGHTSLATEGRAY));
		colorMap.put("lightsteelblue", new Color(LIGHTSTEELBLUE));
		colorMap.put("lightsteelblue1", new Color(LIGHTSTEELBLUE1));
		colorMap.put("lightsteelblue2", new Color(LIGHTSTEELBLUE2));
		colorMap.put("lightsteelblue3", new Color(LIGHTSTEELBLUE3));
		colorMap.put("lightsteelblue4", new Color(LIGHTSTEELBLUE4));
		colorMap.put("lightyellow", new Color(LIGHTYELLOW));
		colorMap.put("lightyellow1", new Color(LIGHTYELLOW1));
		colorMap.put("lightyellow2", new Color(LIGHTYELLOW2));
		colorMap.put("lightyellow3", new Color(LIGHTYELLOW3));
		colorMap.put("limegreen", new Color(LIMEGREEN));
		colorMap.put("linen", new Color(LINEN));
		colorMap.put("magenta", new Color(MAGENTA));
		colorMap.put("magenta1", new Color(MAGENTA1));
		colorMap.put("magenta2", new Color(MAGENTA2));
		colorMap.put("maroon", new Color(MAROON));
		colorMap.put("maroon1", new Color(MAROON1));
		colorMap.put("maroon2", new Color(MAROON2));
		colorMap.put("maroon3", new Color(MAROON3));
		colorMap.put("maroon4", new Color(MAROON4));
		colorMap.put("mediumaquamarine", new Color(MEDIUMAQUAMARINE));
		colorMap.put("mediumblue", new Color(MEDIUMBLUE));
		colorMap.put("mediumforestgreen", new Color(MEDIUMFORESTGREEN));
		colorMap.put("mediumgoldenrod", new Color(MEDIUMGOLDENROD));
		colorMap.put("mediumorchid", new Color(MEDIUMORCHID));
		colorMap.put("mediumorchid1", new Color(MEDIUMORCHID1));
		colorMap.put("mediumorchid2", new Color(MEDIUMORCHID2));
		colorMap.put("mediumorchid3", new Color(MEDIUMORCHID3));
		colorMap.put("mediumorchid4", new Color(MEDIUMORCHID4));
		colorMap.put("mediumpurple", new Color(MEDIUMPURPLE));
		colorMap.put("mediumpurple1", new Color(MEDIUMPURPLE1));
		colorMap.put("mediumpurple2", new Color(MEDIUMPURPLE2));
		colorMap.put("mediumpurple3", new Color(MEDIUMPURPLE3));
		colorMap.put("mediumpurple4", new Color(MEDIUMPURPLE4));
		colorMap.put("mediumseagreen", new Color(MEDIUMSEAGREEN));
		colorMap.put("mediumslateblue", new Color(MEDIUMSLATEBLUE));
		colorMap.put("mediumspringgreen", new Color(MEDIUMSPRINGGREEN));
		colorMap.put("mediumturquoisenew", new Color(MEDIUMTURQUOISE));
		colorMap.put("mediumvioletred", new Color(MEDIUMVIOLETRED));
		colorMap.put("midnightblue", new Color(MIDNIGHTBLUE));
		colorMap.put("mintcream", new Color(MINTCREAM));
		colorMap.put("mistyrose", new Color(MISTYROSE));
		colorMap.put("mistyrose1", new Color(MISTYROSE1));
		colorMap.put("mistyrose2", new Color(MISTYROSE2));
		colorMap.put("mistyrose3", new Color(MISTYROSE3));
		colorMap.put("moccasin", new Color(MOCCASIN));
		colorMap.put("navajowhite", new Color(NAVAJOWHITE));
		colorMap.put("navajowhite1", new Color(NAVAJOWHITE1));
		colorMap.put("navajowhite2", new Color(NAVAJOWHITE2));
		colorMap.put("navajowhite3", new Color(NAVAJOWHITE3));
		colorMap.put("navy", new Color(NAVYBLUE));
		colorMap.put("navyblue", new Color(NAVYBLUE));
		colorMap.put("oldlace", new Color(OLDLACE));
		colorMap.put("olive", new Color(OLIVE));
		colorMap.put("olivedrab", new Color(OLIVEDRAB));
		colorMap.put("olivedrab1", new Color(OLIVEDRAB1));
		colorMap.put("olivedrab2", new Color(OLIVEDRAB2));
		colorMap.put("olivedrab3", new Color(OLIVEDRAB3));
		colorMap.put("orange", new Color(ORANGE));
		colorMap.put("orange1", new Color(ORANGE1));
		colorMap.put("orange2", new Color(ORANGE2));
		colorMap.put("orange3", new Color(ORANGE3));
		colorMap.put("orangered", new Color(ORANGERED));
		colorMap.put("orangered1", new Color(ORANGERED1));
		colorMap.put("orangered2", new Color(ORANGERED2));
		colorMap.put("orangered3", new Color(ORANGERED3));
		colorMap.put("orchid", new Color(ORCHID));
		colorMap.put("orchid1", new Color(ORCHID1));
		colorMap.put("orchid2", new Color(ORCHID2));
		colorMap.put("orchid3", new Color(ORCHID3));
		colorMap.put("orchid4", new Color(ORCHID4));
		colorMap.put("palegoldenrod", new Color(PALEGOLDENROD));
		colorMap.put("palegreen", new Color(PALEGREEN));
		colorMap.put("palegreen1", new Color(PALEGREEN1));
		colorMap.put("palegreen2", new Color(PALEGREEN2));
		colorMap.put("palegreen3", new Color(PALEGREEN3));
		colorMap.put("paleturquoise", new Color(PALETURQUOISE));
		colorMap.put("paleturquoise1", new Color(PALETURQUOISE1));
		colorMap.put("paleturquoise2", new Color(PALETURQUOISE2));
		colorMap.put("paleturquoise3", new Color(PALETURQUOISE3));
		colorMap.put("paleturquoise4", new Color(PALETURQUOISE4));
		colorMap.put("palevioletred", new Color(PALEVIOLETRED));
		colorMap.put("palevioletred1", new Color(PALEVIOLETRED1));
		colorMap.put("palevioletred2", new Color(PALEVIOLETRED2));
		colorMap.put("palevioletred3", new Color(PALEVIOLETRED3));
		colorMap.put("palevioletred4", new Color(PALEVIOLETRED4));
		colorMap.put("papayawhip", new Color(PAPAYAWHIP));
		colorMap.put("peachpuff", new Color(PEACHPUFF));
		colorMap.put("peachpuff1", new Color(PEACHPUFF1));
		colorMap.put("peachpuff2", new Color(PEACHPUFF2));
		colorMap.put("peachpuff3", new Color(PEACHPUFF3));
		colorMap.put("pink", new Color(PINK));
		colorMap.put("pink1", new Color(PINK1));
		colorMap.put("pink2", new Color(PINK2));
		colorMap.put("pink3", new Color(PINK3));
		colorMap.put("pink4", new Color(PINK4));
		colorMap.put("plum", new Color(PLUM));
		colorMap.put("plum1", new Color(PLUM1));
		colorMap.put("plum2", new Color(PLUM2));
		colorMap.put("plum3", new Color(PLUM3));
		colorMap.put("plum4", new Color(PLUM4));
		colorMap.put("powderblue", new Color(POWDERBLUE));
		colorMap.put("purple", new Color(PURPLE));
		colorMap.put("purple1", new Color(PURPLE1));
		colorMap.put("purple2", new Color(PURPLE2));
		colorMap.put("purple3", new Color(PURPLE3));
		colorMap.put("purple4", new Color(PURPLE4));
		colorMap.put("red", new Color(RED));
		colorMap.put("red1", new Color(RED1));
		colorMap.put("red2", new Color(RED2));
		colorMap.put("rosybrown", new Color(ROSYBROWN));
		colorMap.put("rosybrown1", new Color(ROSYBROWN1));
		colorMap.put("rosybrown2", new Color(ROSYBROWN2));
		colorMap.put("rosybrown3", new Color(ROSYBROWN3));
		colorMap.put("rosybrown4", new Color(ROSYBROWN4));
		colorMap.put("royalblue", new Color(ROYALBLUE));
		colorMap.put("royalblue1", new Color(ROYALBLUE1));
		colorMap.put("royalblue2", new Color(ROYALBLUE2));
		colorMap.put("royalblue3", new Color(ROYALBLUE3));
		colorMap.put("royalblue4", new Color(ROYALBLUE4));
		colorMap.put("saddlebrown", new Color(SADDLEBROWN));
		colorMap.put("salmon", new Color(SALMON));
		colorMap.put("salmon1", new Color(SALMON1));
		colorMap.put("salmon2", new Color(SALMON2));
		colorMap.put("salmon3", new Color(SALMON3));
		colorMap.put("salmon4", new Color(SALMON4));
		colorMap.put("sandybrown", new Color(SANDYBROWN));
		colorMap.put("seagreen", new Color(SEAGREEN));
		colorMap.put("seagreen1", new Color(SEAGREEN1));
		colorMap.put("seagreen2", new Color(SEAGREEN2));
		colorMap.put("seagreen3", new Color(SEAGREEN3));
		colorMap.put("seashell", new Color(SEASHELL));
		colorMap.put("seashell1", new Color(SEASHELL1));
		colorMap.put("seashell2", new Color(SEASHELL2));
		colorMap.put("seashell3", new Color(SEASHELL3));
		colorMap.put("sienna", new Color(SIENNA));
		colorMap.put("sienna1", new Color(SIENNA1));
		colorMap.put("sienna2", new Color(SIENNA2));
		colorMap.put("sienna3", new Color(SIENNA3));
		colorMap.put("sienna4", new Color(SIENNA4));
		colorMap.put("silver", new Color(SILVER));
		colorMap.put("skyblue", new Color(SKYBLUE));
		colorMap.put("skyblue1", new Color(SKYBLUE1));
		colorMap.put("skyblue2", new Color(SKYBLUE2));
		colorMap.put("skyblue3", new Color(SKYBLUE3));
		colorMap.put("skyblue4", new Color(SKYBLUE4));
		colorMap.put("slateblue", new Color(SLATEBLUE));
		colorMap.put("slateblue1", new Color(SLATEBLUE1));
		colorMap.put("slateblue2", new Color(SLATEBLUE2));
		colorMap.put("slateblue3", new Color(SLATEBLUE3));
		colorMap.put("slateblue4", new Color(SLATEBLUE4));
		colorMap.put("slategray", new Color(SLATEGRAY));
		colorMap.put("slategray1", new Color(SLATEGRAY1));
		colorMap.put("slategray2", new Color(SLATEGRAY2));
		colorMap.put("slategray3", new Color(SLATEGRAY3));
		colorMap.put("slategray4", new Color(SLATEGRAY4));
		colorMap.put("snow", new Color(SNOW));
		colorMap.put("snow1", new Color(SNOW1));
		colorMap.put("snow2", new Color(SNOW2));
		colorMap.put("snow3", new Color(SNOW3));
		colorMap.put("springgreen", new Color(SPRINGGREEN));
		colorMap.put("springgreen1", new Color(SPRINGGREEN1));
		colorMap.put("springgreen2", new Color(SPRINGGREEN2));
		colorMap.put("springgreen3", new Color(SPRINGGREEN3));
		colorMap.put("steelblue", new Color(STEELBLUE));
		colorMap.put("steelblue1", new Color(STEELBLUE1));
		colorMap.put("steelblue2", new Color(STEELBLUE2));
		colorMap.put("steelblue3", new Color(STEELBLUE3));
		colorMap.put("steelblue4", new Color(STEELBLUE4));
		colorMap.put("tan", new Color(TAN));
		colorMap.put("tan1", new Color(TAN1));
		colorMap.put("tan2", new Color(TAN2));
		colorMap.put("tan3", new Color(TAN3));
		colorMap.put("tan4", new Color(TAN4));
		colorMap.put("teal", new Color(TEAL));
		colorMap.put("thistle", new Color(THISTLE));
		colorMap.put("thistle1", new Color(THISTLE1));
		colorMap.put("thistle2", new Color(THISTLE2));
		colorMap.put("thistle3", new Color(THISTLE3));
		colorMap.put("thistle4", new Color(THISTLE4));
		colorMap.put("tomato", new Color(TOMATO));
		colorMap.put("tomato1", new Color(TOMATO1));
		colorMap.put("tomato2", new Color(TOMATO2));
		colorMap.put("tomato3", new Color(TOMATO3));
		colorMap.put("turquoise", new Color(TURQUOISE));
		colorMap.put("turquoise1", new Color(TURQUOISE1));
		colorMap.put("turquoise2", new Color(TURQUOISE2));
		colorMap.put("turquoise3", new Color(TURQUOISE3));
		colorMap.put("turquoise4", new Color(TURQUOISE4));
		colorMap.put("violet", new Color(VIOLET));
		colorMap.put("violetred", new Color(VIOLETRED));
		colorMap.put("violetred1", new Color(VIOLETRED1));
		colorMap.put("violetred2", new Color(VIOLETRED2));
		colorMap.put("violetred3", new Color(VIOLETRED3));
		colorMap.put("violetred4", new Color(VIOLETRED4));
		colorMap.put("wheat", new Color(WHEAT));
		colorMap.put("wheat1", new Color(WHEAT1));
		colorMap.put("wheat2", new Color(WHEAT2));
		colorMap.put("wheat3", new Color(WHEAT3));
		colorMap.put("wheat4", new Color(WHEAT4));
		colorMap.put("white", new Color(WHITE));
		colorMap.put("whitesmoke", new Color(WHITESMOKE));
		colorMap.put("yellow", new Color(YELLOW));
		colorMap.put("yellow1", new Color(YELLOW1));
		colorMap.put("yellow2", new Color(YELLOW2));
		colorMap.put("yellow3", new Color(YELLOW3));
		colorMap.put("yellowgreen", new Color(YELLOWGREEN));
		return colorMap;
	}
}
