/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import org.loboevolution.common.Strings;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.js.WindowImpl;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.laf.FontKey;

import java.awt.*;
import java.awt.font.TextAttribute;

/**
 * <p>FontValues class.</p>
 */
public class FontValues extends HtmlValues {

	/**
	 * <p>getDefaultFontKey.</p>
	 * @param config a {@link HtmlRendererConfig} object.
	 * @return a {@link org.loboevolution.laf.FontKey} object.
	 */
	public static FontKey getDefaultFontKey(HtmlRendererConfig config) {
		return FontKey.builder().
				font(config.getFont()).
				fontSize(16).
				build();
	}

	/**
	 * Gets the font key.
	 *
	 * @param key a {@link FontKey} object.
	 * @param element a {@link HTMLElementImpl} object.
	 * @param style a {@link CSSStyleDeclaration} object.
	 * @param prevRenderState a {@link RenderState} object.
	 * @return a {@link org.loboevolution.laf.FontKey} object.
	 */
	public static FontKey getFontKey(FontKey key, HTMLElementImpl element, CSSStyleDeclaration style, RenderState prevRenderState) {
		HTMLDocumentImpl document = (HTMLDocumentImpl) element.getDocumentNode();
		HtmlRendererConfig config = document.getConfig();

		String fontSize = style.getFontSize();
		final String fontFamiy = style.getFontFamily();
		final String fontStyle = style.getFontStyle();
		final String fontVariant = style.getFontVariant();
		final String fontWeight = style.getFontWeight();
		final String verticalAlign = style.getVerticalAlign();
		final String letterSpacing = style.getLetterSpacing();
		final String textDecoration = style.getTextDecoration();

		if (key.getFontSize() > 0 ||
				Strings.isNotBlank(fontSize) ||
				Strings.isNotBlank(fontFamiy) ||
				Strings.isNotBlank(fontVariant) ||
				Strings.isNotBlank(fontWeight) ||
				Strings.isNotBlank(verticalAlign) ||
				Strings.isNotBlank(letterSpacing) ||
				Strings.isNotBlank(textDecoration)) {

			if (Strings.isBlank(fontSize) && key.getFontSize() == 0) fontSize = null;

			key.setFontFamily(FontValues.getFontFamily(fontFamiy, prevRenderState));
			key.setFontStyle(FontValues.getFontStyle(fontStyle, prevRenderState, config.isItalic()));
			key.setFontVariant(FontValues.getFontVariant(fontVariant));
			key.setLocales(document.getLocales());
			key.setSuperscript(getFontSuperScript(verticalAlign, prevRenderState, config.isSuperscript(), config.isSubscript()));
			key.setLetterSpacing(HtmlValues.getPixelSize(letterSpacing, prevRenderState, document.getDefaultView(), 0));
			key.setStrikethrough(getFontStrikeThrough(textDecoration, prevRenderState, config.isStrikethrough()));
			key.setUnderline(FontValues.getFontUnderline(textDecoration, prevRenderState, config.isStrikethrough()));
			key.setFontWeight(FontValues.getFontWeight(fontWeight, prevRenderState, config.isBold()));


			if(key.getFontSize() == 0 || Strings.isNotBlank(fontSize))
				key.setFontSize(FontValues.getFontSize(fontSize, element.getDocumentNode().getDefaultView(), prevRenderState));

		} else {

			key.setFontFamily(FontValues.getFontFamily(null, prevRenderState));
			key.setFontStyle(getFontStyle(null, prevRenderState, config.isItalic()));
			key.setFontVariant(FontValues.getFontVariant(null));
			key.setLocales(document.getLocales());
			key.setSuperscript(getFontSuperScript(null, prevRenderState, config.isSuperscript(), config.isSubscript()));
			key.setLetterSpacing(HtmlValues.getPixelSize(null, prevRenderState, document.getDefaultView(), 0));
			key.setStrikethrough(getFontStrikeThrough(null, prevRenderState, config.isStrikethrough()));
			key.setUnderline(FontValues.getFontUnderline(null, prevRenderState, config.isStrikethrough()));
			key.setFontWeight(FontValues.getFontWeight(null, prevRenderState, config.isBold()));
			key.setFontSize(FontValues.getFontSize(null, element.getDocumentNode().getDefaultView(), prevRenderState));
		}

		return key;
	}


	/**
	 * Gets the font size.
	 *
	 * @param spec              the spec
	 * @param parentRenderState the parent render state
	 * @return the font size
	 */
	public static float getFontSize(String spec, Window window, RenderState parentRenderState) {

		final WindowImpl win = (WindowImpl) window;
		final float defaultSize = win.getConfig() != null ? win.getConfig().getFontSize() : 16.0f;

		if (Strings.isBlank(spec)) {
			float parentFontSize = parentRenderState == null ? defaultSize : parentRenderState.getFont().getSize();
			if (parentFontSize > 0 && parentFontSize != defaultSize) {
				return parentFontSize;
			} else {
				return defaultSize;
			}
		}

		String units;
		String text;
		final String specTL = spec.toLowerCase();

		if (specTL.endsWith("q") || (specTL.endsWith("%"))) {
			units = specTL.substring(specTL.length() - 1);
			text = specTL.substring(0, specTL.length() - 1);
		} else if (specTL.endsWith("rem")) {
			units = specTL.substring(specTL.length() - 3);
			text = specTL.substring(0, specTL.length() - 3);
		} else {
			units = specTL.substring(specTL.length() - 2);
			text = specTL.substring(0, specTL.length() - 2);
		}

		switch (units) {
			case "rem":
				try {
					return (int) Math.round(defaultSize * Double.parseDouble(text));
				} catch (final NumberFormatException nfe) {
					nfe.printStackTrace();
					return defaultSize;
				}
			case "em":
				try {
					float parentFontSize = parentRenderState == null ? defaultSize : parentRenderState.getFont().getSize();
					return (int) Math.round(parentFontSize * Double.parseDouble(text));
				} catch (final NumberFormatException nfe) {
					return defaultSize;
				}
			case "px":
			case "pt":
			case "pc":
			case "mm":
			case "ex":
				final int pixelSize = getPixelSize(spec, parentRenderState, window, (int) defaultSize);
				final int dpi = GraphicsEnvironment.isHeadless() ? 72 : Toolkit.getDefaultToolkit().getScreenResolution();
				return pixelSize * 96 / dpi;
			case "%":
				final String value = specTL.substring(0, specTL.length() - 1);
				try {
					float parentFontSize = parentRenderState == null ? defaultSize : parentRenderState.getFont().getSize();
					final double valued = Double.parseDouble(value);
					return (float) (parentFontSize * valued / 100.0);
				} catch (final NumberFormatException nfe) {
					return defaultSize;
				}
			default:
				CSSValues fontZize = CSSValues.get(specTL);
				switch (fontZize) {
					case SMALL:
						return 12.0f;
					case MEDIUM:
						return 14.0f;
					case LARGE:
						return 20.0f;
					case X_SMALL:
						return 11.0f;
					case XX_SMALL:
						return 10.0f;
					case X_LARGE:
						return 26.0f;
					case XX_LARGE:
						return 40.0f;
					case LARGER:
						float lager = parentRenderState == null ? defaultSize : parentRenderState.getFont().getSize();
						return lager * 1.2f;
					case SMALLER:
						float smaller = parentRenderState == null ? defaultSize : parentRenderState.getFont().getSize();
						return smaller / 1.2f;
					case INHERIT:
						float inherit = defaultSize;
						if (parentRenderState != null && parentRenderState.getPreviousRenderState() != null) {
							inherit = parentRenderState.getPreviousRenderState().getFont().getSize();
						}
						return inherit;
					case INITIAL:
					default:
						return getPixelSize(spec, parentRenderState, window, (int) defaultSize);
				}
		}

	}

	/**
	 * <p>getFontFamily.</p>
	 *
	 * @param fontFamily a {@link java.lang.String} object.
	 * @param parentRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getFontFamily(String fontFamily, RenderState parentRenderState) {
		if (fontFamily == null) {
			if (parentRenderState != null) {
				return parentRenderState.getFont().getFamily();
			} else {
				return FontKey.builder().build().getFontFamily();
			}
		}
		return fontFamily;
	}

	/**
	 * <p>getFontStrikeThrough.</p>
	 *
	 * @param strikethrough a {@link java.lang.String} object.
	 * @param parentRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param isStrikethrough a {@link Boolean} object.
	 * @return a boolean.
	 */
	private static boolean getFontStrikeThrough(String strikethrough, RenderState parentRenderState, boolean isStrikethrough) {
		if (strikethrough == null) {
			if (parentRenderState != null && parentRenderState.getFont() != null) {
				Boolean strikethroughon = (Boolean) parentRenderState.getFont().getAttributes().get(TextAttribute.STRIKETHROUGH_ON);
				return strikethroughon != null && strikethroughon;
			} else {
				if (isStrikethrough)
					return TextAttribute.STRIKETHROUGH_ON;
			}
		}

		if (CSSValues.get(strikethrough).equals(CSSValues.LINE_THROUGH)) {
			return TextAttribute.STRIKETHROUGH_ON;
		}

		return false;
	}

	/**
	 * <p>getFontStyle.</p>
	 *
	 * @param fontStyle a {@link java.lang.String} object.
	 * @param parentRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param isItalic a {@link Boolean} object.
	 * @return a {@link java.lang.String} object.
	 */
	private static String getFontStyle(String fontStyle, RenderState parentRenderState, boolean isItalic) {
		if (fontStyle == null) {
			if (parentRenderState != null) {
				if (parentRenderState.getFont().getStyle() == Font.BOLD)
					return CSSValues.ITALIC.getValue();
				else
					return null;
			} else {
				if (isItalic)
					return CSSValues.ITALIC.getValue();
			}
		}
		return fontStyle;
	}

	/**
	 * <p>getFontVariant.</p>
	 *
	 * @param fontVariant a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getFontVariant(String fontVariant) {
		if (fontVariant == null){
			return CSSValues.NORMAL.getValue();
		}
		return fontVariant;
	}

	/**
	 * <p>getFontSuperScript.</p>
	 *
	 * @param verticalAlign a {@link java.lang.String} object.
	 * @param parentRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param isSuperscript a {@link Boolean} object.
	 * @param isSubscript a {@link Boolean} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public static Integer getFontSuperScript(String verticalAlign, RenderState parentRenderState, boolean isSuperscript, boolean isSubscript) {
		Integer superscript = null;

		final boolean isSuper = "super".equalsIgnoreCase(verticalAlign);
		final boolean isSub = "sub".equalsIgnoreCase(verticalAlign);

		if (isSuper || isSuperscript) {
			superscript = TextAttribute.SUPERSCRIPT_SUPER;
		} else if (isSub || isSubscript) {
			superscript = TextAttribute.SUPERSCRIPT_SUB;
		}

		if (superscript == null && parentRenderState != null) {
			superscript = (Integer) parentRenderState.getFont().getAttributes().get(TextAttribute.SUPERSCRIPT);
		}
		return superscript;
	}

	/**
	 * <p>getFontUnderline.</p>
	 *
	 * @param underline a {@link java.lang.String} object.
	 * @param parentRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param isStrikethrough a {@link Boolean} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	private static Integer getFontUnderline(String underline, RenderState parentRenderState, boolean isStrikethrough) {

		if (underline == null) {
			if (parentRenderState != null) {
				return (Integer) parentRenderState.getFont().getAttributes().get(TextAttribute.UNDERLINE_LOW_ONE_PIXEL);
			} else {
				if (isStrikethrough)
					return TextAttribute.UNDERLINE_LOW_ONE_PIXEL;
			}
		}

		if (CSSValues.get(underline).equals(CSSValues.UNDERLINE)) {
			return TextAttribute.UNDERLINE_LOW_TWO_PIXEL;
		}

		return null;
	}

	/**
	 * <p>getFontWeight.</p>
	 *
	 * @param fontWeight a {@link java.lang.String} object.
	 * @param parentRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param isBold a {@link Boolean} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getFontWeight(String fontWeight, RenderState parentRenderState, boolean isBold) {
		if (fontWeight == null) {
			if (parentRenderState != null) {
				if(parentRenderState.getFont().getAttributes().get(TextAttribute.WEIGHT) == null){
					return CSSValues.BOLD400.getValue();
				}

				return parentRenderState.getFont().getAttributes().get(TextAttribute.WEIGHT).toString();
			} else {
				if (isBold)
					return CSSValues.BOLD400.getValue();
			}
		}

		return fontWeight;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Checks if is font style.
	 */
	public static boolean isFontStyle(String token) {
		CSSValues tok = CSSValues.get(token);
		switch (tok) {
		case ITALIC:
		case NORMAL:
		case OBLIQUE:
			return true;
		default:
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Checks if is font variant.
	 */
	public static boolean isFontVariant(String token) {
		CSSValues tok = CSSValues.get(token);
		switch (tok) {
		case SMALL_CAPS:
		case NORMAL:
			return true;
		default:
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Checks if is font weight.
	 */
	public static boolean isFontWeight(String token) {
		CSSValues tok = CSSValues.get(token);
		switch (tok) {
		case BOLD:
		case NORMAL:
		case LIGHTER:
			return true;
		default:
			try {
				final int value = Integer.parseInt(token);
				return value % 100 == 0 && value >= 100 && value <= 900;
			} catch (final NumberFormatException nfe) {
				return false;
			}
		}
	}
}
