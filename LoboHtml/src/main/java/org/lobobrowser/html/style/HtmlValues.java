/*
 GNU LESSER GENERAL PUBLIC LICENSE
 Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution. Copyright (C) 2014 Lobo Evolution

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.html.style;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobo.common.Urls;
import org.lobo.laf.ColorFactory;
import org.lobo.laf.FontFactory;
import org.lobo.laf.FontKey;
import org.lobobrowser.html.CSSValuesProperties;
import org.w3c.dom.css.CSS2Properties;

public class HtmlValues implements CSSValuesProperties {
	public static final int BORDER_STYLE_DASHED = 3;
	public static final int BORDER_STYLE_DOTTED = 2;
	public static final int BORDER_STYLE_DOUBLE = 5;

	public static final int BORDER_STYLE_GROOVE = 6;
	public static final int BORDER_STYLE_HIDDEN = 1;
	public static final int BORDER_STYLE_INSET = 8;
	public static final int BORDER_STYLE_NONE = 0;
	public static final int BORDER_STYLE_OUTSET = 9;
	public static final int BORDER_STYLE_RIDGE = 7;
	public static final int BORDER_STYLE_SOLID = 4;
	public static final int DEFAULT_BORDER_WIDTH = 2;
	private static final Logger logger = Logger.getLogger(HtmlValues.class.getName());
	public static final Map<String, FontInfo> SYSTEM_FONTS = new HashMap<String, FontInfo>();

	static {
		final FontInfo systemFont = new FontInfo();
		SYSTEM_FONTS.put("caption", systemFont);
		SYSTEM_FONTS.put("icon", systemFont);
		SYSTEM_FONTS.put("menu", systemFont);
		SYSTEM_FONTS.put("message-box", systemFont);
		SYSTEM_FONTS.put("small-caption", systemFont);
		SYSTEM_FONTS.put("status-bar", systemFont);
	}

	public static BorderInfo getBorderInfo(CSS2Properties properties, RenderState renderState) {
		final BorderInfo binfo = new BorderInfo();

		binfo.topStyle = getBorderStyle(properties.getBorderTopStyle());
		binfo.rightStyle = getBorderStyle(properties.getBorderRightStyle());
		binfo.bottomStyle = getBorderStyle(properties.getBorderBottomStyle());
		binfo.leftStyle = getBorderStyle(properties.getBorderLeftStyle());

		final ColorFactory cf = ColorFactory.getInstance();
		final String topColorSpec = properties.getBorderTopColor();
		if (topColorSpec != null) {
			binfo.topColor = cf.getColor(topColorSpec);
		}
		final String rightColorSpec = properties.getBorderRightColor();
		if (rightColorSpec != null) {
			binfo.rightColor = cf.getColor(rightColorSpec);
		}
		final String bottomColorSpec = properties.getBorderBottomColor();
		if (bottomColorSpec != null) {
			binfo.bottomColor = cf.getColor(bottomColorSpec);
		}
		final String leftColorSpec = properties.getBorderLeftColor();
		if (leftColorSpec != null) {
			binfo.leftColor = cf.getColor(leftColorSpec);
		}

		HtmlValues.populateBorderInsets(binfo, properties, renderState);

		return binfo;
	}

	public static HtmlInsets getBorderInsets(Insets borderStyles, CSS2Properties cssProperties,
			RenderState renderState) {
		HtmlInsets insets = null;
		if (borderStyles.top != HtmlValues.BORDER_STYLE_NONE) {
			final String topText = cssProperties.getBorderTopWidth();
			insets = updateTopInset(insets, topText, renderState);
		}
		if (borderStyles.left != HtmlValues.BORDER_STYLE_NONE) {
			final String leftText = cssProperties.getBorderLeftWidth();
			insets = updateLeftInset(insets, leftText, renderState);
		}
		if (borderStyles.bottom != HtmlValues.BORDER_STYLE_NONE) {
			final String bottomText = cssProperties.getBorderBottomWidth();
			insets = updateBottomInset(insets, bottomText, renderState);
		}
		if (borderStyles.right != HtmlValues.BORDER_STYLE_NONE) {
			final String rightText = cssProperties.getBorderRightWidth();
			insets = updateRightInset(insets, rightText, renderState);
		}
		return insets;
	}

	public static Insets getInsets(String insetsSpec, RenderState renderState, boolean negativeOK) {
		final int[] insetsArray = new int[4];
		int size = 0;
		final StringTokenizer tok = new StringTokenizer(insetsSpec);
		if (tok.hasMoreTokens()) {
			String token = tok.nextToken();
			insetsArray[0] = getPixelSize(token, renderState, 0);
			if (negativeOK || insetsArray[0] >= 0) {
				size = 1;
				if (tok.hasMoreTokens()) {
					token = tok.nextToken();
					insetsArray[1] = getPixelSize(token, renderState, 0);
					if (negativeOK || insetsArray[1] >= 0) {
						size = 2;
						if (tok.hasMoreTokens()) {
							token = tok.nextToken();
							insetsArray[2] = getPixelSize(token, renderState, 0);
							if (negativeOK || insetsArray[2] >= 0) {
								size = 3;
								if (tok.hasMoreTokens()) {
									token = tok.nextToken();
									insetsArray[3] = getPixelSize(token, renderState, 0);
									size = 4;
									if (negativeOK || insetsArray[3] >= 0) {
										// nop
									} else {
										insetsArray[3] = 0;
									}
								}
							} else {
								size = 4;
								insetsArray[2] = 0;
							}
						}
					} else {
						size = 4;
						insetsArray[1] = 0;
					}
				}
			} else {
				size = 1;
				insetsArray[0] = 0;
			}
		}
		if (size == 4) {
			return new Insets(insetsArray[0], insetsArray[3], insetsArray[2], insetsArray[1]);
		} else if (size == 1) {
			final int val = insetsArray[0];
			return new Insets(val, val, val, val);
		} else if (size == 2) {
			return new Insets(insetsArray[0], insetsArray[1], insetsArray[0], insetsArray[1]);
		} else if (size == 3) {
			return new Insets(insetsArray[0], insetsArray[1], insetsArray[2], insetsArray[1]);
		} else {
			return null;
		}
	}

	public static ListStyle getListStyle(String listStyleText) {
		final ListStyle listStyle = new ListStyle();
		final String[] tokens = HtmlValues.splitCssValue(listStyleText);
		for (final String token : tokens) {
			final int listStyleType = HtmlValues.getListStyleType(token);
			if (listStyleType != ListStyle.TYPE_UNSET) {
				listStyle.type = listStyleType;
			} else if (HtmlValues.isUrl(token)) {
				// TODO: listStyle.image
			} else {
				final int listStylePosition = HtmlValues.getListStylePosition(token);
				if (listStylePosition != ListStyle.POSITION_UNSET) {
					listStyle.position = listStylePosition;
				}
			}
		}
		return listStyle;
	}

	public static int getListStylePosition(String token) {
		final String tokenTL = token.toLowerCase();
		if ("inside".equals(tokenTL)) {
			return ListStyle.POSITION_INSIDE;
		} else if ("outside".equals(tokenTL)) {
			return ListStyle.POSITION_OUTSIDE;
		} else {
			return ListStyle.POSITION_UNSET;
		}
	}

	public static int getListStyleType(String token) {
		final String tokenTL = token.toLowerCase();
		if ("none".equals(tokenTL)) {
			return ListStyle.TYPE_NONE;
		} else if ("disc".equals(tokenTL)) {
			return ListStyle.TYPE_DISC;
		} else if ("circle".equals(tokenTL)) {
			return ListStyle.TYPE_CIRCLE;
		} else if ("square".equals(tokenTL)) {
			return ListStyle.TYPE_SQUARE;
		} else if ("decimal".equals(tokenTL)) {
			return ListStyle.TYPE_DECIMAL;
		} else if ("lower-alpha".equals(tokenTL) || "lower-latin".equals(tokenTL)) {
			return ListStyle.TYPE_LOWER_ALPHA;
		} else if ("upper-alpha".equals(tokenTL) || "upper-latin".equals(tokenTL)) {
			return ListStyle.TYPE_UPPER_ALPHA;
		} else {
			// TODO: Many types missing here
			return ListStyle.TYPE_UNSET;
		}
	}

	public static int getListStyleTypeDeprecated(String token) {
		final String tokenTL = token.toLowerCase();
		if ("disc".equals(tokenTL)) {
			return ListStyle.TYPE_DISC;
		} else if ("circle".equals(tokenTL)) {
			return ListStyle.TYPE_CIRCLE;
		} else if ("square".equals(tokenTL)) {
			return ListStyle.TYPE_SQUARE;
		} else if ("1".equals(tokenTL)) {
			return ListStyle.TYPE_DECIMAL;
		} else if ("a".equals(tokenTL)) {
			return ListStyle.TYPE_LOWER_ALPHA;
		} else if ("A".equals(tokenTL)) {
			return ListStyle.TYPE_UPPER_ALPHA;
		} else {
			// TODO: Missing i, I.
			return ListStyle.TYPE_UNSET;
		}
	}

	public static HtmlInsets getMarginInsets(CSS2Properties cssProperties, RenderState renderState) {
		HtmlInsets insets = null;
		final String topText = cssProperties.getMarginTop();
		insets = updateTopInset(insets, topText, renderState);
		final String leftText = cssProperties.getMarginLeft();
		insets = updateLeftInset(insets, leftText, renderState);
		final String bottomText = cssProperties.getMarginBottom();
		insets = updateBottomInset(insets, bottomText, renderState);
		final String rightText = cssProperties.getMarginRight();
		insets = updateRightInset(insets, rightText, renderState);
		return insets;
	}

	public static HtmlInsets getPaddingInsets(CSS2Properties cssProperties, RenderState renderState) {
		HtmlInsets insets = null;
		final String topText = cssProperties.getPaddingTop();
		insets = updateTopInset(insets, topText, renderState);
		final String leftText = cssProperties.getPaddingLeft();
		insets = updateLeftInset(insets, leftText, renderState);
		final String bottomText = cssProperties.getPaddingBottom();
		insets = updateBottomInset(insets, bottomText, renderState);
		final String rightText = cssProperties.getPaddingRight();
		insets = updateRightInset(insets, rightText, renderState);
		return insets;
	}
	
	public static final int getPixelSize(String spec, RenderState renderState, int errorValue) {
		try {
			final int dpi = GraphicsEnvironment.isHeadless() ? 72 : Toolkit.getDefaultToolkit().getScreenResolution();
			final String lcSpec = spec.toLowerCase();
			String units = "";
			String text = "";
			if(isUnits(spec)) {
				units = lcSpec.substring(lcSpec.length() - 2, lcSpec.length());
				text = lcSpec.substring(0, lcSpec.length() - 2);
			}
					
			switch (units) {
			case "px":
				return (int) Math.round(Double.parseDouble(text));
			case "em":
				final FontFactory FONT_FACTORY = FontFactory.getInstance();
				final Font DEFAULT_FONT = FONT_FACTORY.getFont(new FontKey());
				final Font f = (renderState == null) ? DEFAULT_FONT : renderState.getFont();
				final int fontSize = f.getSize();
				final double pixelSize = fontSize * dpi / 96;
				return (int) Math.round(pixelSize * Double.parseDouble(text));
			case "pt":
				return inches(72, dpi, text);
			case "pc":
				return inches(6, dpi, text);
			case "cm":
				return inches(2.54, dpi, text);
			case "mm":
				return inches(25.4, dpi, text);
			case "ex":
				final double xHeight = renderState.getFontMetrics().getAscent() * 0.47;
				return (int) Math.round(xHeight * Double.parseDouble(text));
			default:
				return (int) Math.round(Double.parseDouble(lcSpec));
			}
		} catch (final Exception ex) {
			return errorValue;
		}
	}
	
	public static final int getPixelSize(String spec, RenderState renderState, int errorValue, int availSize) {
		try {
			if (spec.endsWith("%")) {
				final String perText = spec.substring(0, spec.length() - 1);
				final double val = Double.parseDouble(perText);
				return (int) Math.round(availSize * val / 100.0);
			} else {
				return getPixelSize(spec, renderState, errorValue);
			}
		} catch (final Exception nfe) {
			return errorValue;
		}
	}

	public static URL getURIFromStyleValue(String fullURLStyleValue) {
		final String start = "url(";
		if (!fullURLStyleValue.toLowerCase().startsWith(start)) {
			return null;
		}
		final int startIdx = start.length();
		final int closingIdx = fullURLStyleValue.lastIndexOf(')');
		if (closingIdx == -1) {
			return null;
		}
		final String quotedUri = fullURLStyleValue.substring(startIdx, closingIdx);
		final String tentativeUri = unquoteAndUnescape(quotedUri);
		try {
			return Urls.createURL(null, tentativeUri);
		} catch (final Exception mfu) {
			logger.log(Level.WARNING, "Unable to create URL for URI=[" + tentativeUri + "].", mfu);
			return null;
		}
	}

	public static boolean isBackgroundPosition(String token) {
		return isLength(token) || token.endsWith("%") || token.equalsIgnoreCase("top")
				|| token.equalsIgnoreCase("center") || token.equalsIgnoreCase("bottom")
				|| token.equalsIgnoreCase("left") || token.equalsIgnoreCase("right");
	}

	public static boolean isBackgroundRepeat(String repeat) {
		final String repeatTL = repeat.toLowerCase();
		return repeatTL.indexOf("repeat") != -1;
	}

	public static boolean isBorderStyle(String token) {
		final String tokenTL = token.toLowerCase();
		return tokenTL.equals("solid") || tokenTL.equals("dashed") || tokenTL.equals("dotted")
				|| tokenTL.equals("double") || tokenTL.equals("none") || tokenTL.equals("hidden")
				|| tokenTL.equals("groove") || tokenTL.equals("ridge") || tokenTL.equals("inset")
				|| tokenTL.equals("outset");
	}

	public static boolean isFontStyle(String token) {
		return "italic".equals(token) || "normal".equals(token) || "oblique".equals(token);
	}

	public static boolean isFontVariant(String token) {
		return "small-caps".equals(token) || "normal".equals(token);
	}

	public static boolean isFontWeight(String token) {
		if ("bold".equals(token) || "bolder".equals(token) || "lighter".equals(token)) {
			return true;
		}
		try {
			final int value = Integer.parseInt(token);
			return value % 100 == 0 && value >= 100 && value <= 900;
		} catch (final Exception nfe) {
			return false;
		}
	}
	
	public static boolean isUrl(String token) {
		return token.toLowerCase().startsWith("url(");
	}

	public static void populateBorderInsets(BorderInfo binfo, CSS2Properties cssProperties, RenderState renderState) {
		HtmlInsets insets = null;
		if (binfo.topStyle != HtmlValues.BORDER_STYLE_NONE) {
			final String topText = cssProperties.getBorderTopWidth();
			insets = updateTopInset(insets, topText, renderState);
		}
		if (binfo.leftStyle != HtmlValues.BORDER_STYLE_NONE) {
			final String leftText = cssProperties.getBorderLeftWidth();
			insets = updateLeftInset(insets, leftText, renderState);
		}
		if (binfo.bottomStyle != HtmlValues.BORDER_STYLE_NONE) {
			final String bottomText = cssProperties.getBorderBottomWidth();
			insets = updateBottomInset(insets, bottomText, renderState);
		}
		if (binfo.rightStyle != HtmlValues.BORDER_STYLE_NONE) {
			final String rightText = cssProperties.getBorderRightWidth();
			insets = updateRightInset(insets, rightText, renderState);
		}
		binfo.insets = insets;
	}

	public static String quoteAndEscape(String text) {
		final StringBuffer result = new StringBuffer();
		result.append("'");
		int index = 0;
		final int length = text.length();
		while (index < length) {
			final char ch = text.charAt(index);
			switch (ch) {
			case '\'':
				result.append("\\'");
				break;
			case '\\':
				result.append("\\\\");
				break;
			default:
				result.append(ch);
			}
			index++;
		}
		result.append("'");
		return result.toString();
	}

	public static String[] splitCssValue(String cssValue) {
		final ArrayList<String> tokens = new ArrayList<String>(4);
		final int len = cssValue.length();
		int parenCount = 0;
		StringBuffer currentWord = null;
		for (int i = 0; i < len; i++) {
			final char ch = cssValue.charAt(i);
			switch (ch) {
			case '(':
				parenCount++;
				if (currentWord == null) {
					currentWord = new StringBuffer();
				}
				currentWord.append(ch);
				break;
			case ')':
				parenCount--;
				if (currentWord == null) {
					currentWord = new StringBuffer();
				}
				currentWord.append(ch);
				break;
			case ' ':
			case '\t':
			case '\n':
			case '\r':
				if (parenCount == 0) {
					tokens.add(currentWord.toString());
					currentWord = null;
					break;
				} else {
					// Fall through - no break
				}
			default:
				if (currentWord == null) {
					currentWord = new StringBuffer();
				}
				currentWord.append(ch);
				break;
			}
		}
		if (currentWord != null) {
			tokens.add(currentWord.toString());
		}
		return tokens.toArray(new String[tokens.size()]);
	}

	public static String unquoteAndUnescape(String text) {
		final StringBuffer result = new StringBuffer();
		int index = 0;
		final int length = text.length();
		boolean escape = false;
		boolean single = false;
		if (index < length) {
			final char ch = text.charAt(index);
			switch (ch) {
			case '\'':
				single = true;
				break;
			case '"':
				break;
			case '\\':
				escape = true;
				break;
			default:
				result.append(ch);
			}
			index++;
		}
		OUTER: for (; index < length; index++) {
			final char ch = text.charAt(index);
			switch (ch) {
			case '\'':
				if (escape || !single) {
					escape = false;
					result.append(ch);
				} else {
					break OUTER;
				}
				break;
			case '"':
				if (escape || single) {
					escape = false;
					result.append(ch);
				} else {
					break OUTER;
				}
				break;
			case '\\':
				if (escape) {
					escape = false;
					result.append(ch);
				} else {
					escape = true;
				}
				break;
			default:
				if (escape) {
					escape = false;
					result.append('\\');
				}
				result.append(ch);
			}
		}
		return result.toString();
	}

	private static int getBorderStyle(String styleText) {
		if (styleText == null || styleText.length() == 0) {
			return HtmlValues.BORDER_STYLE_NONE;
		}
		final String stl = styleText.toLowerCase();
		if ("solid".equals(stl)) {
			return BORDER_STYLE_SOLID;
		} else if ("dashed".equals(stl)) {
			return BORDER_STYLE_DASHED;
		} else if ("dotted".equals(stl)) {
			return BORDER_STYLE_DOTTED;
		} else if ("none".equals(stl)) {
			return BORDER_STYLE_NONE;
		} else if ("hidden".equals(stl)) {
			return BORDER_STYLE_HIDDEN;
		} else if ("double".equals(stl)) {
			return BORDER_STYLE_DOUBLE;
		} else if ("groove".equals(stl)) {
			return BORDER_STYLE_GROOVE;
		} else if ("ridge".equals(stl)) {
			return BORDER_STYLE_RIDGE;
		} else if ("inset".equals(stl)) {
			return BORDER_STYLE_INSET;
		} else if ("outset".equals(stl)) {
			return BORDER_STYLE_OUTSET;
		} else {
			return BORDER_STYLE_NONE;
		}
	}

	private static int inches(double value, int dpi, String text) {
		double val = Double.parseDouble(text);
		final double inches = val / value;
		return (int) Math.round(dpi * inches);
	}

	private static boolean isLength(String token) {
		if (isUnits(token)) {
			return true;
		} else {
			try {
				Double.parseDouble(token);
				return true;
			} catch (final Exception nfe) {
				return false;
			}
		}
	}
	
	private static boolean isUnits(String token) {
		if (token.endsWith("px") || token.endsWith("pt") || token.endsWith("pc") || token.endsWith("cm")
				|| token.endsWith("mm") || token.endsWith("ex") || token.endsWith("em")) {
			return true;
		}
		return false;
	}
	
	private static HtmlInsets updateBottomInset(HtmlInsets insets, String sizeText, RenderState renderState) {
		if (sizeText == null) {
			return insets;
		}
		sizeText = sizeText.trim();
		if (sizeText.length() == 0) {
			return insets;
		}
		if (insets == null) {
			insets = new HtmlInsets();
		}
		if ("auto".equalsIgnoreCase(sizeText)) {
			insets.bottomType = HtmlInsets.TYPE_AUTO;
		} else if (sizeText.endsWith("%")) {
			insets.bottomType = HtmlInsets.TYPE_PERCENT;
			try {
				insets.bottom = Integer.parseInt(sizeText.substring(0, sizeText.length() - 1));
			} catch (final Exception nfe) {
				insets.bottom = 0;
			}
		} else {
			insets.bottomType = HtmlInsets.TYPE_PIXELS;
			insets.bottom = HtmlValues.getPixelSize(sizeText, renderState, 0);
		}
		return insets;
	}

	private static HtmlInsets updateLeftInset(HtmlInsets insets, String sizeText, RenderState renderState) {
		if (sizeText == null) {
			return insets;
		}
		sizeText = sizeText.trim();
		if (sizeText.length() == 0) {
			return insets;
		}
		if (insets == null) {
			insets = new HtmlInsets();
		}
		if ("auto".equalsIgnoreCase(sizeText)) {
			insets.leftType = HtmlInsets.TYPE_AUTO;
		} else if (sizeText.endsWith("%")) {
			insets.leftType = HtmlInsets.TYPE_PERCENT;
			try {
				insets.left = Integer.parseInt(sizeText.substring(0, sizeText.length() - 1));
			} catch (final Exception nfe) {
				insets.left = 0;
			}
		} else {
			insets.leftType = HtmlInsets.TYPE_PIXELS;
			insets.left = HtmlValues.getPixelSize(sizeText, renderState, 0);
		}
		return insets;
	}

	private static HtmlInsets updateRightInset(HtmlInsets insets, String sizeText, RenderState renderState) {
		if (sizeText == null) {
			return insets;
		}
		sizeText = sizeText.trim();
		if (sizeText.length() == 0) {
			return insets;
		}
		if (insets == null) {
			insets = new HtmlInsets();
		}
		if ("auto".equalsIgnoreCase(sizeText)) {
			insets.rightType = HtmlInsets.TYPE_AUTO;
		} else if (sizeText.endsWith("%")) {
			insets.rightType = HtmlInsets.TYPE_PERCENT;
			try {
				insets.right = Integer.parseInt(sizeText.substring(0, sizeText.length() - 1));
			} catch (final Exception nfe) {
				insets.right = 0;
			}
		} else {
			insets.rightType = HtmlInsets.TYPE_PIXELS;
			insets.right = HtmlValues.getPixelSize(sizeText, renderState, 0);
		}
		return insets;
	}

	private static HtmlInsets updateTopInset(HtmlInsets insets, String sizeText, RenderState renderState) {
		if (sizeText == null) {
			return insets;
		}
		sizeText = sizeText.trim();
		if (sizeText.length() == 0) {
			return insets;
		}
		if (insets == null) {
			insets = new HtmlInsets();
		}
		if ("auto".equalsIgnoreCase(sizeText)) {
			insets.topType = HtmlInsets.TYPE_AUTO;
		} else if (sizeText.endsWith("%")) {
			insets.topType = HtmlInsets.TYPE_PERCENT;
			try {
				insets.top = Integer.parseInt(sizeText.substring(0, sizeText.length() - 1));
			} catch (final Exception nfe) {
				insets.top = 0;
			}
		} else {
			insets.topType = HtmlInsets.TYPE_PIXELS;
			insets.top = HtmlValues.getPixelSize(sizeText, renderState, 0);
		}
		return insets;
	}
}
