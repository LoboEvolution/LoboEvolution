/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.html.style;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.util.Urls;
import org.lobobrowser.util.gui.ColorFactory;
import org.lobobrowser.util.gui.LAFSettings;

/**
 * The Class HtmlValues.
 */
public class HtmlValues implements CSSValuesProperties {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(HtmlValues.class.getName());

	/**
	 * Gets the pixel size.
	 *
	 * @param spec
	 *            the spec
	 * @param renderState
	 *            the render state
	 * @param errorValue
	 *            the error value
	 * @param availSize
	 *            the avail size
	 * @return the pixel size
	 */
	public static final int getPixelSize(String spec, RenderState renderState, int errorValue, int availSize) {
		if (spec.endsWith("%")) {
			String perText = spec.substring(0, spec.length() - 1);
			try {
				double val = Double.parseDouble(perText);
				return (int) Math.round(availSize * val / 100.0);
			} catch (NumberFormatException nfe) {
				return errorValue;
			}
		} else {
			return getPixelSize(spec, renderState, errorValue);
		}
	}

	/**
	 * Gets the pixel size.
	 *
	 * @param spec
	 *            the spec
	 * @param renderState
	 *            the render state
	 * @param errorValue
	 *            the error value
	 * @return the pixel size
	 */
	public static final int getPixelSize(String spec, RenderState renderState, int errorValue) {

		if (spec == null) {
			return 0;
		}

		String lcSpec = spec.toLowerCase();
		if (lcSpec.endsWith("px")) {
			String pxText = lcSpec.substring(0, lcSpec.length() - 2);
			try {
				return (int) Math.round(Double.parseDouble(pxText));
			} catch (NumberFormatException nfe) {
				return errorValue;
			}
		} else if (lcSpec.endsWith("em")) {
			float fontSize = LAFSettings.getInstance().getFontSize();
			
			if(renderState != null) {
				Font f = renderState.getFont();
				fontSize = f.getSize();
			}
						
			String valText = lcSpec.substring(0, lcSpec.length() - 2);
			double val;
			try {
				val = Double.parseDouble(valText);
			} catch (NumberFormatException nfe) {
				return errorValue;
			}
			
			int dpi = GraphicsEnvironment.isHeadless() ? 72 : Toolkit.getDefaultToolkit().getScreenResolution();
			double pixelSize = fontSize * dpi / 96;
			return (int) Math.round(pixelSize * val);
		} else if (lcSpec.endsWith("pt")) {
			String valText = lcSpec.substring(0, lcSpec.length() - 2);
			double val;
			try {
				val = Double.parseDouble(valText);
			} catch (NumberFormatException nfe) {
				return errorValue;
			}
			int dpi = GraphicsEnvironment.isHeadless() ? 72 : Toolkit.getDefaultToolkit().getScreenResolution();
			double inches = val / 72;
			return (int) Math.round(dpi * inches);
		} else if (lcSpec.endsWith("pc")) {
			String valText = lcSpec.substring(0, lcSpec.length() - 2);
			double val;
			try {
				val = Double.parseDouble(valText);
			} catch (NumberFormatException nfe) {
				return errorValue;
			}
			int dpi = GraphicsEnvironment.isHeadless() ? 72 : Toolkit.getDefaultToolkit().getScreenResolution();
			double inches = val / 6;
			return (int) Math.round(dpi * inches);
		} else if (lcSpec.endsWith("cm")) {
			String valText = lcSpec.substring(0, lcSpec.length() - 2);
			double val;
			try {
				val = Double.parseDouble(valText);
			} catch (NumberFormatException nfe) {
				return errorValue;
			}
			int dpi = GraphicsEnvironment.isHeadless() ? 72 : Toolkit.getDefaultToolkit().getScreenResolution();		
			double inches = val / 2.54;
			return (int) Math.round(dpi * inches);
		} else if (lcSpec.endsWith("mm")) {
			String valText = lcSpec.substring(0, lcSpec.length() - 2);
			double val;
			try {
				val = Double.parseDouble(valText);
			} catch (NumberFormatException nfe) {
				return errorValue;
			}
			int dpi = GraphicsEnvironment.isHeadless() ? 72 : Toolkit.getDefaultToolkit().getScreenResolution();
			double inches = val / 25.4;
			return (int) Math.round(dpi * inches);
		} else if (lcSpec.endsWith("ex") && renderState != null) {
			double xHeight = renderState.getFontMetrics().getAscent() * 0.47;
			String valText = lcSpec.substring(0, lcSpec.length() - 2);
			double val;
			try {
				val = Double.parseDouble(valText);
			} catch (NumberFormatException nfe) {
				return errorValue;
			}
			return (int) Math.round(xHeight * val);
		} else if (lcSpec.endsWith("in")) {
			String valText = lcSpec.substring(0, lcSpec.length() - 2);
			double val;
			try {
				val = Double.parseDouble(valText);
			} catch (NumberFormatException nfe) {
				return errorValue;
			}
			
			return (int) Math.round(val * 96);
		} else {
			String pxText = lcSpec;
			try {
				return (int) Math.round(Double.parseDouble(pxText));
			} catch (NumberFormatException nfe) {
				return errorValue;
			}
		}
	}

	/**
	 * Gets the URI from style value.
	 *
	 * @param fullURLStyleValue
	 *            the full url style value
	 * @return the URI from style value
	 */
	public static URL getURIFromStyleValue(String fullURLStyleValue) {
		String start = "url(";
		if (!fullURLStyleValue.toLowerCase().startsWith(start)) {
			return null;
		}
		int startIdx = start.length();
		int closingIdx = fullURLStyleValue.lastIndexOf(')');
		if (closingIdx == -1) {
			return null;
		}
		String quotedUri = fullURLStyleValue.substring(startIdx, closingIdx);
		String tentativeUri = unquoteAndUnescape(quotedUri);
		try {
			return Urls.createURL(null, tentativeUri);
		} catch (MalformedURLException | UnsupportedEncodingException mfu) {
			logger.error("Unable to create URL for URI=[" + tentativeUri + "].", mfu);
			return null;
		}
	}

	/**
	 * Unquote and unescape.
	 *
	 * @param text
	 *            the text
	 * @return the string
	 */
	public static String unquoteAndUnescape(String text) {
		StringBuffer result = new StringBuffer();
		int index = 0;
		int length = text.length();
		boolean escape = false;
		boolean single = false;
		if (index < length) {
			char ch = text.charAt(index);
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
			char ch = text.charAt(index);
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

	/**
	 * Quote and escape.
	 *
	 * @param text
	 *            the text
	 * @return the string
	 */
	public static String quoteAndEscape(String text) {
		StringBuffer result = new StringBuffer();
		result.append("'");
		int index = 0;
		int length = text.length();
		while (index < length) {
			char ch = text.charAt(index);
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

	/**
	 * Gets the color from background.
	 *
	 * @param background
	 *            the background
	 * @return the color from background
	 */
	public static String getColorFromBackground(String background) {
		String[] backgroundParts = HtmlValues.splitCssValue(background);
		for (String token : backgroundParts) {
			if (ColorFactory.getInstance().isColor(token)) {
				return token;
			}
		}
		return null;
	}

	/**
	 * Split css value.
	 *
	 * @param cssValue
	 *            the css value
	 * @return the string[]
	 */
	public static String[] splitCssValue(String cssValue) {
		ArrayList<String> tokens = new ArrayList<String>(4);
		int len = cssValue.length();
		int parenCount = 0;
		StringBuffer currentWord = null;
		for (int i = 0; i < len; i++) {
			char ch = cssValue.charAt(i);
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

	/**
	 * Checks if is url.
	 *
	 * @param token
	 *            the token
	 * @return true, if is url
	 */
	public static boolean isUrl(String token) {
		return token.toLowerCase().startsWith("url(");
	}

	/**
	 * Gets the list style type.
	 *
	 * @param token
	 *            the token
	 * @return the list style type
	 */
	public static int getListStyleType(String token) {
		String tokenTL = token.toLowerCase();

		switch (tokenTL) {
		case NONE:
			return ListStyle.TYPE_NONE;
		case DISC:
			return ListStyle.TYPE_DISC;
		case CIRCLE:
			return ListStyle.TYPE_CIRCLE;
		case SQUARE:
			return ListStyle.TYPE_SQUARE;
		case DECIMAL:
			return ListStyle.TYPE_DECIMAL;
		case DECIMAL_LEADING_ZERO:
			return ListStyle.TYPE_DECIMAL_LEADING_ZERO;
		case LOWER_ALPHA:
			return ListStyle.TYPE_LOWER_ALPHA;
		case LOWER_LATIN:
			return ListStyle.TYPE_LOWER_ALPHA;
		case UPPER_ALPHA:
			return ListStyle.TYPE_UPPER_ALPHA;
		case UPPER_LATIN:
			return ListStyle.TYPE_UPPER_ALPHA;
		case LOWER_ROMAN:
			return ListStyle.TYPE_LOWER_ROMAN;
		case UPPER_ROMAN:
			return ListStyle.TYPE_UPPER_ROMAN;
		case INITIAL:
			return ListStyle.TYPE_UPPER_ROMAN;
		default:
			return ListStyle.TYPE_UNSET;
		}
	}

	/**
	 * Gets the list style position.
	 *
	 * @param token
	 *            the token
	 * @return the list style position
	 */
	public static int getListStylePosition(String token) {
		String tokenTL = token.toLowerCase();
		if (INSIDE.equals(tokenTL)) {
			return ListStyle.POSITION_INSIDE;
		} else if (OUTSIDE.equals(tokenTL)) {
			return ListStyle.POSITION_OUTSIDE;
		} else {
			return ListStyle.POSITION_UNSET;
		}
	}

	/**
	 * Gets the list style.
	 *
	 * @param listStyleText
	 *            the list style text
	 * @return the list style
	 */
	public static ListStyle getListStyle(String listStyleText) {
		ListStyle listStyle = new ListStyle();
		String[] tokens = HtmlValues.splitCssValue(listStyleText);
		for (String token : tokens) {
			int listStyleType = HtmlValues.getListStyleType(token);
			if (listStyleType != ListStyle.TYPE_UNSET) {
				listStyle.type = listStyleType;
			} else if (HtmlValues.isUrl(token)) {
				// TODO: listStyle.image
			} else {
				int listStylePosition = HtmlValues.getListStylePosition(token);
				if (listStylePosition != ListStyle.POSITION_UNSET) {
					listStyle.position = listStylePosition;
				}
			}
		}
		return listStyle;
	}
}
