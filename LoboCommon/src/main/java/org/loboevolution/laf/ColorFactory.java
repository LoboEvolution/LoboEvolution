package org.loboevolution.laf;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.common.Strings;
import org.loboevolution.store.SQLiteCommon;

/**
 * A factory for creating Color objects.
 *
 * @author J. H. S.
 * @version $Id: $Id
 */
public class ColorFactory {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ColorFactory.class.getName());

	/** The instance. */
	private static ColorFactory instance;

	/** Constant TRANSPARENT */
	public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
	
	/** The color map. */
	private Map<String, Color> colorMap = new HashMap<String, Color>(510);

	private final String COLORS = "SELECT DISTINCT name, value FROM COLOR";

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
	public static synchronized final ColorFactory getInstance() {
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
			this.colorMap = mapColor();
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
	 * Get HEX color.
	 *
	 * @param c          the color
	 * @param normalSpec the color spec
	 * @return the color
	 */
	private Color getHex(Color c, String normalSpec) {
		Color color = null;
		if (normalSpec.startsWith("#")) {
			color = HexToColor(normalSpec);
			this.colorMap.put(normalSpec, color);
		} else {
			color = c;
		}
		return color;
	}

	private Color getHSL(Color c, String normalSpec) {
		Color color = null;
		if (c == null && normalSpec.startsWith(this.HSL_START)) {
			final int endIdx = normalSpec.lastIndexOf(')');
			String commaValues = "";

			if (endIdx == -1) {
				commaValues = normalSpec.substring(this.HSL_START.length());
			} else {
				commaValues = normalSpec.substring(this.HSL_START.length(), endIdx);
			}

			final String[] strs = Strings.splitUsingTokenizer(commaValues, ",");
			final float h = parseValue(strs[0].trim(), 360);
			final float s = parsePercent(strs[1].trim());
			final float l = parsePercent(strs[2].trim());

			color = toRGB(h, s, l, 1);
			this.colorMap.put(normalSpec, color);
		} else {
			color = c;
		}
		return color;
	}

	private Color getHSLA(Color c, String normalSpec) {
		Color color = null;
		if (c == null && normalSpec.startsWith(this.HSLA_START)) {
			final int endIdx = normalSpec.lastIndexOf(')');
			String commaValues = "";

			if (endIdx == -1) {
				commaValues = normalSpec.substring(this.HSLA_START.length());
			} else {
				commaValues = normalSpec.substring(this.HSLA_START.length(), endIdx);
			}

			final String[] strs = Strings.splitUsingTokenizer(commaValues, ",");

			final float h = parseValue(strs[0].trim(), 360);
			final float s = parsePercent(strs[1].trim());
			final float l = parsePercent(strs[2].trim());
			final float alpha = parseAlpha(strs[3].trim());
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
	private Color getRGB(Color c, String normalSpec) {
		Color color = null;
		if (c == null && normalSpec.startsWith(this.RGB_START)) {
			final int endIdx = normalSpec.indexOf(')');
			String commaValues = "";

			if (endIdx == -1) {
				commaValues = normalSpec.substring(this.RGB_START.length());
			} else {
				commaValues = normalSpec.substring(this.RGB_START.length(), endIdx);
			}

			final String[] strs = Strings.splitUsingTokenizer(commaValues, ",");
			final int red = (int) parseValue(strs[0].trim(), 255);
			final int green =  (int) parseValue(strs[1].trim(), 255);
			final int blue =  (int) parseValue(strs[2].trim(), 255);
			color = new Color(normalize(red), normalize(green), normalize(blue));
			this.colorMap.put(normalSpec, color);
		} else {
			color = c;
		}
		return color;
	}

	/**
	 * Get RGBA color.
	 *
	 * @param c          the color
	 * @param normalSpec the color spec
	 * @return the color
	 */
	private Color getRGBA(Color c, String normalSpec) {
		Color color = null;
		if (c == null && normalSpec.startsWith(this.RGBA_START)) {
			final int endIdx = normalSpec.lastIndexOf(')');
			String commaValues = "";

			if (endIdx == -1) {
				commaValues = normalSpec.substring(this.RGBA_START.length());
			} else {
				commaValues = normalSpec.substring(this.RGBA_START.length(), endIdx);
			}

			final String[] strs = Strings.splitUsingTokenizer(commaValues, ",");
			final int red = (int) parseValue(strs[0].trim(), 255);
			final int green =  (int) parseValue(strs[1].trim(), 255);
			final int blue =  (int) parseValue(strs[2].trim(), 255);
			final int alpha = Float.valueOf(255 * Float.parseFloat(strs[3].trim())).intValue();
			color = new Color(normalize(red), normalize(green), normalize(blue), alpha);
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

		if (colorSpec.startsWith("#")) {
			return true;
		} else if (normalSpec.startsWith(this.RGBA_START)) {
			return true;
		} else if (normalSpec.startsWith(this.RGB_START)) {
			return true;
		} else if (normalSpec.startsWith(this.HSLA_START)) {
			return true;
		} else if (normalSpec.startsWith(this.HSL_START)) {
			return true;
		}
		synchronized (this) {
			return this.colorMap.containsKey(normalSpec);
		}
	}
	
	/**
	 * <p>isRgbOrHsl.</p>
	 *
	 * @param colorSpec a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean isRgbOrHsl(String colorSpec) {
		final String normalSpec = colorSpec.toLowerCase();

		if (normalSpec.startsWith(this.RGBA_START)) {
			return true;
		} else if (normalSpec.startsWith(this.RGB_START)) {
			return true;
		} else if (normalSpec.startsWith(this.HSLA_START)) {
			return true;
		} else if (normalSpec.startsWith(this.HSL_START)) {
			return true;
		}
		return false;
	}
	
	public static Color getAdjustedColor(Color c, double factor) {
		double f = 1 - Math.min(Math.abs(factor), 1);
		double inc = (factor > 0 ? 255 * (1 - f) : 0);
		return new Color((int) (c.getRed() * f + inc), (int) (c.getGreen() * f + inc), (int) (c.getBlue() * f + inc));
	}

	private Map<String, Color> mapColor() {
		final Map<String, Color> colorMap = new HashMap<String, Color>();
		colorMap.put("transparent", new Color(0, 0, 0, 0));
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(this.COLORS)) {
				while (rs != null && rs.next()) {
					int color = Integer.parseInt(rs.getString(2).replace("0x",""), 16);
					colorMap.put(rs.getString(1), new Color(color));
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return colorMap;
	}

	private float parseAlpha(String alpha) {
		return Float.parseFloat(alpha);
	}

	private float parsePercent(String perc) {
		return Float.parseFloat(perc.substring(0, perc.length() - 1));
	}

	private float parseValue(String val, int max) {
		if (val.endsWith("%")) {
			return (int) (parsePercent(val) * max);
		}
		return Float.parseFloat(val);
	}

	private Color toRGB(float h, float s, float l, float alpha) {
		h = h % 360.0f;
		h /= 360f;
		s /= 100f;
		l /= 100f;

		float q = 0;

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
		Integer red = -1;
		Integer green = -1;
		Integer blue = -1;
		Integer alpha = -1;
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
	
	/**
	 * Normalize color component within allow range 0..255
	 *
	 * @param colorComponent any value
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
