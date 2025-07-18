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

package org.loboevolution.html.style;

import org.htmlunit.cssparser.dom.CSSValueImpl;
import org.htmlunit.cssparser.dom.CSSValueImpl.CSSPrimitiveValueType;
import org.loboevolution.common.Strings;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.ListValues;
import org.loboevolution.html.dom.domimpl.HTMLImageElementImpl;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.html.js.WindowImpl;
import org.loboevolution.js.Window;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.info.TimingInfo;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.net.HttpNetwork;

import java.awt.*;
import java.util.ArrayList;

/**
 * <p>HtmlValues class.</p>
 */
public class HtmlValues {

	/**
	 * <p>getListStyle.</p>
	 *
	 * @param listStyleText a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.style.ListStyle} object.
	 */
	public static ListStyle getListStyle(final String listStyleText) {
		final ListStyle listStyle = new ListStyle();
		final String[] tokens = HtmlValues.splitCssValue(listStyleText);
		for (final String token : tokens) {
			final ListValues listStyleType = HtmlValues.getListStyleType(token);
			if (listStyleType != ListValues.TYPE_UNSET && listStyleType != ListValues.NONE && listStyle.getType() < 1) {
				listStyle.setType(listStyleType.getValue());
			} else if (HtmlValues.isUrl(token)) {
				listStyle.setType(ListValues.TYPE_URL.getValue());
				listStyle.setImage(getListStyleImage(token));
			} else {
				final ListValues listStylePosition = HtmlValues.getListStylePosition(token);
				if (listStylePosition != ListValues.POSITION_UNSET && listStylePosition != ListValues.NONE) {
					listStyle.setPosition(listStylePosition.getValue());
				}
			}
		}
		return listStyle;
	}
	
	
	/**
	 * <p>getListStyleImage.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a {@link java.awt.Image} object.
	 */
	public static Image getListStyleImage(final String token) {
		Image image;
		final String start = "url(";
		final int startIdx = start.length();
		final int closingIdx = token.lastIndexOf(')');
		final String quotedUri = token.substring(startIdx, closingIdx);
		final String[] items = { "http", "https", "file" };
		final TimingInfo info = new TimingInfo();
		final HTMLImageElementImpl img = new HTMLImageElementImpl();
		if (Strings.containsWords(quotedUri, items)) {
			try {
				img.setSrc(quotedUri);
				image = HttpNetwork.getImage(img, info, false);
			} catch (final Exception e) {
				image = null;
			}
		} else {
			try {
				img.setSrc(quotedUri);
				image = HttpNetwork.getImage(img, info, true);
			} catch (final Exception e) {
				image = null;
			}
		}

		if(image != null) {
			final HtmlRendererContext htmlRendererContext = img.getHtmlRendererContext();
			final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
			htmlPanel.getBrowserPanel().getTimingList.add(info);
		}

		return image;
	}

	/**
	 * <p>getListStylePosition.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.ListValues} object.
	 */
	public static ListValues getListStylePosition(final String token) {
		final String tokenTL = token.toLowerCase();
		final CSSValues tkn = CSSValues.get(tokenTL);
        return switch (tkn) {
            case INSIDE -> ListValues.POSITION_INSIDE;
            case OUTSIDE -> ListValues.POSITION_OUTSIDE;
            default -> ListValues.POSITION_UNSET;
        };
	}

	/**
	 * <p>getListStyleType.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.ListValues} object.
	 */
	public static ListValues getListStyleType(final String token) {
		final String tokenTL = token.toLowerCase();
		final CSSValues tkn = CSSValues.get(tokenTL);
        return switch (tkn) {
            case NONE -> ListValues.TYPE_NONE;
            case DISC -> ListValues.TYPE_DISC;
            case CIRCLE -> ListValues.TYPE_CIRCLE;
            case SQUARE -> ListValues.TYPE_SQUARE;
            case DECIMAL -> ListValues.TYPE_DECIMAL;
            case DECIMAL_LEADING_ZERO -> ListValues.TYPE_DECIMAL_LEADING_ZERO;
            case LOWER_ALPHA, LOWER_LATIN -> ListValues.TYPE_LOWER_ALPHA;
            case UPPER_ALPHA, UPPER_LATIN -> ListValues.TYPE_UPPER_ALPHA;
            case LOWER_ROMAN -> ListValues.TYPE_LOWER_ROMAN;
            case UPPER_ROMAN -> ListValues.TYPE_UPPER_ROMAN;
            default -> ListValues.TYPE_UNSET;
        };
	}

	/**
	 * <p>getPixelSize.</p>
	 *
	 * @param spec a {@link java.lang.String} object.
	 * @param renderState a {@link RenderState} object.
	 * @param errorValue a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public static int getPixelSize(final String spec, final RenderState renderState, final Window window, final int errorValue) {
		try {
			final int dpi = GraphicsEnvironment.isHeadless() ? 72 : Toolkit.getDefaultToolkit().getScreenResolution();
			final String lcSpec = spec.toLowerCase();
			String units = "";
			String text = "";
			if (isUnits(spec)) {
				if (spec.endsWith("q")) {
					units = lcSpec.substring(lcSpec.length() - 1);
					text = lcSpec.substring(0, lcSpec.length() - 1);
				} else if (spec.endsWith("rem")) {
					units = lcSpec.substring(lcSpec.length() - 3);
					text = lcSpec.substring(0, lcSpec.length() - 3);
				} else {
					units = lcSpec.substring(lcSpec.length() - 2);
					text = lcSpec.substring(0, lcSpec.length() - 2);
				}
			}

			switch (units) {
			case "px":
                final double val = Double.parseDouble(text);
                final double inches = val / 96;
                return (int) Math.round(dpi * inches);
			case "em":
				final FontFactory FONT_FACTORY = FontFactory.getInstance();
				final WindowImpl win = (WindowImpl) window;
				if (win == null || win.getConfig() == null) {
					return (int) Math.round(16.0f * Double.parseDouble(text));
				}

				final Font DEFAULT_FONT = FONT_FACTORY.getFont(FontValues.getDefaultFontKey(win.getConfig()));
				final Font f = (renderState == null) ? DEFAULT_FONT : renderState.getFont();
				final int fontSize = f.getSize();
				return (int) Math.round(fontSize * Double.parseDouble(text));

			case "rem":
				final WindowImpl win2 = (WindowImpl) window;
				final float fs = win2.getConfig() != null ? win2.getConfig().getFontSize() : 16.0f;
				return (int) Math.round(fs * Double.parseDouble(text));
			case "pt":
				return inches(72, dpi, text);
			case "pc":
				return inches(6, dpi, text);
			case "cm":
				return inches(2.54, dpi, text);
			case "mm":
				return inches(25.4, dpi, text);
			case "q":
				return inches(1016, dpi, text);
			case "ex":
				final double xHeight = renderState.getFontMetrics().getAscent() * 0.47;
				return (int) Math.round(xHeight * Double.parseDouble(text));
			case "in":
				return (int) Math.round(dpi * Double.parseDouble(text));
			case "vh":
				if (window != null) {
					final double ih = window.getInnerHeight();
					return (int) Math.round(ih * Double.parseDouble(text) / 100.0);
				} else {
					return (int) Math.round(Double.parseDouble(lcSpec));
				}
			case "vw":
				if (window != null) {
					final double iw = window.getInnerWidth();
					return (int) Math.round(iw * Double.parseDouble(text) / 100.0);
				} else {
					return (int) Math.round(Double.parseDouble(lcSpec));
				}
			default:
				return (int) Math.round(Double.parseDouble(lcSpec));
			}
		} catch (final Exception ex) {
			return errorValue;
		}
	}
	
	/**
	 * <p>getPixelSize.</p>
	 *
	 * @param spec a {@link java.lang.String} object.
	 * @param renderState a {@link RenderState} object.
	 * @param errorValue a {@link java.lang.Integer} object.
	 * @param availSize a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public static Integer getPixelSize(final String spec, final RenderState renderState, final Window window, final Integer errorValue, final Integer availSize) {
		try {
			if (spec.endsWith("%")) {
				final String perText = spec.substring(0, spec.length() - 1);
				final double val = Double.parseDouble(perText);
				return (int) Math.round(availSize * val / 100.0);
			} else {
				return getPixelSize(spec, renderState, window, errorValue);
			}
		} catch (final Exception nfe) {
			return errorValue;
		}
	}
	
	/**
	 * <p>resolutionValue.</p>
	 *
	 * @param cssValue a {@link CSSValueImpl} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public static int resolutionValue(final CSSValueImpl cssValue) {
        if (cssValue == null) {
            return -1;
        }
        
        if (cssValue.getPrimitiveType() == CSSPrimitiveValueType.CSS_DIMENSION) {
        	final String units = cssValue.getCssText().substring(cssValue.getCssText().length() - 3);
            return switch (units) {
                case "dpi" -> (int) cssValue.getDoubleValue();
                case "dpcm" -> (int) (2.54f * cssValue.getDoubleValue());
                case "dppx" -> (int) (96 * cssValue.getDoubleValue());
                default -> {
                    if (HtmlValues.isUnits(cssValue.getCssText())) {
                        yield HtmlValues.getPixelSize(cssValue.getCssText(), null, null, -1);
                    }
                    yield -1;
                }
            };
       }
        return -1;
    }

	/**
	 * <p>isBackgroundPosition.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isBackgroundPosition(final String token) {
		return isLength(token) || token.endsWith("%") || token.equalsIgnoreCase("top")
				|| token.equalsIgnoreCase("center") || token.equalsIgnoreCase("bottom")
				|| token.equalsIgnoreCase("left") || token.equalsIgnoreCase("right");
	}

	/**
	 * <p>isBackgroundPosition.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isBackgroundAttachment(final String token) {
		return token.equalsIgnoreCase("scroll") || token.equalsIgnoreCase("fixed")
				|| token.equalsIgnoreCase("local");
	}

	/**
	 * <p>isBackgroundRepeat.</p>
	 *
	 * @param repeat a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isBackgroundRepeat(final String repeat) {
		final String repeatTL = repeat.toLowerCase();
		return repeatTL.contains("repeat");
	}

	/**
	 * <p>isBorderStyle.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isBorderStyle(final String token) {
		final String tokenTL = token.toLowerCase();
		return tokenTL.equals("solid") || tokenTL.equals("dashed") || tokenTL.equals("dotted")
				|| tokenTL.equals("double") || tokenTL.equals("none") || tokenTL.equals("hidden")
				|| tokenTL.equals("groove") || tokenTL.equals("ridge") || tokenTL.equals("inset")
				|| tokenTL.equals("outset");
	}

	/**
	 * <p>isUrl.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isUrl(final String token) {
		return token.toLowerCase().startsWith("url(");
	}
	
	/**
	 * <p>isGradient.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isGradient(final String token) {
		return token.toLowerCase().contains("gradient");
	}
	
	/**
	 * <p>quoteAndEscape.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String quoteAndEscape(final String text) {
		final StringBuilder result = new StringBuilder();
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

	/**
	 * <p>splitCssValue.</p>
	 *
	 * @param cssValue a {@link java.lang.String} object.
	 * @return an array of {@link java.lang.String} objects.
	 */
	public static String[] splitCssValue(final String cssValue) {
		final ArrayList<String> tokens = new ArrayList<>(4);
		final int len = cssValue.length();
		int parenCount = 0;
		StringBuilder currentWord = null;
		for (int i = 0; i < len; i++) {
			final char ch = cssValue.charAt(i);
			switch (ch) {
			case '(':
				parenCount++;
				if (currentWord == null) {
					currentWord = new StringBuilder();
				}
				currentWord.append(ch);
				break;
			case ')':
				parenCount--;
				if (currentWord == null) {
					currentWord = new StringBuilder();
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
					currentWord = new StringBuilder();
				}
				currentWord.append(ch);
				break;
			}
		}
		if (currentWord != null) {
			tokens.add(currentWord.toString());
		}
		return tokens.toArray(new String[0]);
	}

	/**
	 * <p>unquoteAndUnescape.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String unquoteAndUnescape(final String text) {
		final StringBuilder result = new StringBuilder();
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

	public static boolean isUnits(final String token) {
		return token.endsWith("px") ||
				token.endsWith("pt") ||
				token.endsWith("pc") ||
				token.endsWith("cm") ||
				token.endsWith("mm") ||
				token.endsWith("ex") ||
				token.endsWith("em") ||
				(token.endsWith("in")  && !token.startsWith("zoom") && !token.equals("thin")) ||
				token.endsWith("q") ||
				token.endsWith("vh") ||
				token.endsWith("vw") ||
				token.endsWith("rem");
	}

	private static int inches(final double value, final int dpi, final String text) {
		final double val = Double.parseDouble(text);
		final double inches = val / value;
		return (int) Math.round(dpi * inches);
	}

	private static boolean isLength(final String token) {
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
}
