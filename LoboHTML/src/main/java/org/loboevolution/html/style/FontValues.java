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

package org.loboevolution.html.style;

import com.loboevolution.store.laf.LAFSettings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.css.CSS3Properties;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.laf.FontKey;

import java.awt.*;
import java.awt.font.TextAttribute;

/**
 * <p>FontValues class.</p>
 */
public class FontValues extends HtmlValues {
	
	private static final LAFSettings laf = new LAFSettings().getInstance();

	/**
	 *  <p>getDefaultFontKey.</p>
	 * @return a {@link org.loboevolution.laf.FontKey} object.
	 */
	public static FontKey getDefaultFontKey() {
		return FontKey.builder().
				font(laf.getFont()).
				fontSize(laf.getFontSize()).
				build();
	}

	public static FontKey getFontKey(FontKey key, HTMLElementImpl element, CSS3Properties style, RenderState prevRenderState) {
		HTMLDocumentImpl document = (HTMLDocumentImpl) element.getDocumentNode();

		if (style != null) {
			final String fontSize = style.getFontSize();
			final String fontFamiy = style.getFontFamily();
			final String fontStyle = style.getFontStyle();
			final String fontVariant = style.getFontVariant();
			final String fontWeight = style.getFontWeight();
			final String verticalAlign = style.getVerticalAlign();
			final String letterSpacing = style.getLetterSpacing();
			final String textDecoration = style.getTextDecoration();

			key.setFontFamily(FontValues.getFontFamily(fontFamiy, prevRenderState));
			key.setFontStyle(FontValues.getFontStyle(fontStyle, prevRenderState));
			key.setFontVariant(FontValues.getFontVariant(fontVariant));
			key.setLocales(document.getLocales());
			key.setSuperscript(FontValues.getFontSuperScript(verticalAlign, prevRenderState));
			key.setLetterSpacing(HtmlValues.getPixelSize(letterSpacing, prevRenderState, document.getDefaultView(), 0));
			key.setStrikethrough(FontValues.getFontStrikeThrough(textDecoration, prevRenderState));
			key.setUnderline(FontValues.getFontUnderline(textDecoration, prevRenderState));
			key.setFontWeight(FontValues.getFontWeight(fontWeight, prevRenderState));
			key.setFontSize(FontValues.getFontSize(fontSize, element.getDocumentNode().getDefaultView(), prevRenderState));
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

		final float defaultSize = laf.getFontSize();
		float parentFontSize = parentRenderState == null ? defaultSize : parentRenderState.getFont().getSize();

		if (parentFontSize != defaultSize) {
			return parentFontSize;
		}

		if (spec == null) {
			return defaultSize;
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
						parentFontSize = (int) laf.getFontSize();
						if (parentRenderState != null) {
							parentFontSize = parentRenderState.getFont().getSize();
						}
						return parentFontSize * 1.2f;
					case SMALLER:
						parentFontSize = (int) laf.getFontSize();
						if (parentRenderState != null) {
							parentFontSize = parentRenderState.getFont().getSize();
						}
						return parentFontSize / 1.2f;
					case INHERIT:
						parentFontSize = (int) laf.getFontSize();
						if (parentRenderState != null && parentRenderState.getPreviousRenderState() != null) {
							parentFontSize = parentRenderState.getPreviousRenderState().getFont().getSize();
						}
						return parentFontSize;
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
	 * @return a boolean.
	 */
	public static boolean getFontStrikeThrough(String strikethrough, RenderState parentRenderState) {
		if (strikethrough == null) {
			if (parentRenderState != null && parentRenderState.getFont() != null) {
				Boolean strikethroughon = (Boolean) parentRenderState.getFont().getAttributes().get(TextAttribute.STRIKETHROUGH_ON);
				return strikethroughon != null && strikethroughon;
			} else {
				if (laf.isStrikethrough())
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
	 * @return a {@link java.lang.String} object.
	 */
	public static String getFontStyle(String fontStyle, RenderState parentRenderState) {
		if (fontStyle == null) {
			if (parentRenderState != null) {
				if (parentRenderState.getFont().getStyle() == 1)
					return CSSValues.ITALIC.getValue();
				else
					return null;
			} else {
				if (laf.isItalic())
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
	 * @return a {@link java.lang.Integer} object.
	 */
	public static Integer getFontSuperScript(String verticalAlign, RenderState parentRenderState) {
		Integer superscript = null;

		final boolean isSuper = "super".equalsIgnoreCase(verticalAlign);
		final boolean isSub = "sub".equalsIgnoreCase(verticalAlign);

		if (isSuper || laf.isSuperscript()) {
			superscript = TextAttribute.SUPERSCRIPT_SUPER;
		} else if (isSub || laf.isSubscript()) {
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
	 * @return a {@link java.lang.Integer} object.
	 */
	public static Integer getFontUnderline(String underline, RenderState parentRenderState) {

		if (underline == null) {
			if (parentRenderState != null) {
				return (Integer) parentRenderState.getFont().getAttributes().get(TextAttribute.UNDERLINE_LOW_ONE_PIXEL);
			} else {
				if (laf.isStrikethrough())
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
	 * @return a {@link java.lang.String} object.
	 */
	public static String getFontWeight(String fontWeight, RenderState parentRenderState) {
		if (fontWeight == null) {
			if (parentRenderState != null) {
				return (String) parentRenderState.getFont().getAttributes().get(TextAttribute.WEIGHT);
			} else {
				if (laf.isBold())
					return CSSValues.BOLD.getValue();
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
