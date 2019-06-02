package org.lobobrowser.html.style;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.font.TextAttribute;

import org.lobo.laf.LAFSettings;

public class FontValues extends HtmlValues {
	public static final String getFontFamily(String spec, RenderState parentRenderState) {
		final String fontFamily = spec;
		if (fontFamily == null) {
			if (parentRenderState != null) {
				return parentRenderState.getFont().getFamily();
			} else {
				return Font.SANS_SERIF;
			}
		}
		return fontFamily;
	}

	/**
	 * Gets a number for 1 to 7.
	 *
	 * @param oldHtmlSpec A number from 1 to 7 or +1, etc.
	 * @param renderState the render state
	 * @return the font number old style
	 */
	public static final int getFontNumberOldStyle(String oldHtmlSpec, RenderState renderState) {
		oldHtmlSpec = oldHtmlSpec.trim();
		int tentative;
		try {
			if (oldHtmlSpec.startsWith("+")) {
				tentative = renderState.getFontBase() + Integer.parseInt(oldHtmlSpec.substring(1));
			} else if (oldHtmlSpec.startsWith("-")) {
				tentative = renderState.getFontBase() + Integer.parseInt(oldHtmlSpec);
			} else {
				tentative = Integer.parseInt(oldHtmlSpec);
			}
			if (tentative < 1) {
				tentative = 1;
			} else if (tentative > 7) {
				tentative = 7;
			}
		} catch (final NumberFormatException nfe) {
			tentative = 3;
		}
		return tentative;
	}

	/**
	 * Gets the font size.
	 *
	 * @param fontNumber the font number
	 * @return the font size
	 */
	public static float getFontSize(int fontNumber) {
		switch (fontNumber) {
		case 1:
			return 10.0f;
		case 2:
			return 11.0f;
		case 3:
			return 13.0f;
		case 4:
			return 16.0f;
		case 5:
			return 21.0f;
		case 6:
			return 29.0f;
		case 7:
			return 42.0f;
		default:
			return 63.0f;
		}
	}

	/**
	 * Gets the font size.
	 *
	 * @param spec              the spec
	 * @param parentRenderState the parent render state
	 * @return the font size
	 */
	public static final float getFontSize(String spec, RenderState parentRenderState) {

		if (spec == null) {
			return new LAFSettings().getIstance().getFontSize();
		}

		final String specTL = spec.toLowerCase();

		if (specTL.endsWith("em")) {
			if (parentRenderState == null) {
				return new LAFSettings().getIstance().getFontSize();
			}
			final Font font = parentRenderState.getFont();
			final String pxText = specTL.substring(0, specTL.length() - 2);
			double value;
			try {
				value = Double.parseDouble(pxText);
			} catch (final NumberFormatException nfe) {
				return new LAFSettings().getIstance().getFontSize();
			}
			return (int) Math.round(font.getSize() * value);
		} else if (specTL.endsWith("px") || specTL.endsWith("pt") || specTL.endsWith("em") || specTL.endsWith("pc")
				|| specTL.endsWith("em") || specTL.endsWith("mm") || specTL.endsWith("ex")) {
			final int pixelSize = getPixelSize(spec, parentRenderState,
					(int) new LAFSettings().getIstance().getFontSize());
			final int dpi = GraphicsEnvironment.isHeadless() ? 72 : Toolkit.getDefaultToolkit().getScreenResolution();
			// Normally the factor below should be 72, but
			// the font-size concept in HTML is handled differently.
			return pixelSize * 96 / dpi;
		} else if (specTL.endsWith("%")) {
			final String value = specTL.substring(0, specTL.length() - 1);
			try {
				final double valued = Double.parseDouble(value);
				final double parentFontSize = parentRenderState == null ? 14.0 : parentRenderState.getFont().getSize();
				return (float) (parentFontSize * valued / 100.0);
			} catch (final NumberFormatException nfe) {
				return new LAFSettings().getIstance().getFontSize();
			}
		} else {

			int parentFontSize = 0;

			switch (specTL) {
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
				parentFontSize = (int) new LAFSettings().getIstance().getFontSize();
				if (parentRenderState != null) {
					parentFontSize = parentRenderState.getFont().getSize();
				}
				return parentFontSize * 1.2f;
			case SMALLER:
				parentFontSize = (int) new LAFSettings().getIstance().getFontSize();
				if (parentRenderState != null) {
					parentFontSize = parentRenderState.getFont().getSize();
				}
				return parentFontSize / 1.2f;
			case INHERIT:
				parentFontSize = (int) new LAFSettings().getIstance().getFontSize();
				if (parentRenderState != null) {
					parentRenderState.getPreviousRenderState().getFont().getSize();
				}
				return parentFontSize;
			case INITIAL:
			default:
				return getPixelSize(spec, parentRenderState, (int) new LAFSettings().getIstance().getFontSize());
			}
		}
	}

	/**
	 * Gets the font size spec.
	 *
	 * @param fontNumber the font number
	 * @return the font size spec
	 */
	public static final String getFontSizeSpec(int fontNumber) {
		switch (fontNumber) {
		case 1:
			return "10px";
		case 2:
			return "11px";
		case 3:
			return "13px";
		case 4:
			return "16px";
		case 5:
			return "21px";
		case 6:
			return "29px";
		case 7:
			return "42px";
		default:
			return "63px";
		}
	}

	public static boolean getFontStrikeThrough(String spec) {
		final String strikethrough = spec;

		if (LINE_THROUGH.equals(strikethrough)) {
			return TextAttribute.STRIKETHROUGH_ON;
		}

		if (strikethrough == null && new LAFSettings().getIstance().isUnderline()) {
			return TextAttribute.STRIKETHROUGH_ON;
		}
		return false;
	}

	public static final String getFontStyle(String spec) {
		final String fontStyle = spec;
		if (fontStyle == null && new LAFSettings().getIstance().isItalic()) {
			return ITALIC;
		}
		return fontStyle;
	}

	public static Integer getFontSuperScript(String spec, RenderState parentRenderState) {
		final String verticalAlign = spec;
		Integer superscript = null;

		final boolean isSuper = verticalAlign != null && "super".equalsIgnoreCase(verticalAlign);
		final boolean isSub = verticalAlign != null && "sub".equalsIgnoreCase(verticalAlign);

		if (isSuper || new LAFSettings().getIstance().isSuperscript()) {
			superscript = TextAttribute.SUPERSCRIPT_SUPER;
		} else if (isSub || new LAFSettings().getIstance().isSubscript()) {
			superscript = TextAttribute.SUPERSCRIPT_SUB;
		}

		if (superscript == null && parentRenderState != null) {
			superscript = (Integer) parentRenderState.getFont().getAttributes().get(TextAttribute.SUPERSCRIPT);
		}
		return superscript;
	}

	public static Integer getFontUnderline(String spec) {
		final String underline = spec;

		if (UNDERLINE.equals(underline)) {
			return TextAttribute.UNDERLINE_LOW_ONE_PIXEL;
		}

		if (underline == null && new LAFSettings().getIstance().isUnderline()) {
			return TextAttribute.UNDERLINE_LOW_ONE_PIXEL;
		}
		return null;
	}

	public static String getFontWeight(String spec) {
		final String fontWeight = spec;
		if (fontWeight == null && new LAFSettings().getIstance().isBold()) {
			return BOLD;
		}
		return fontWeight;
	}

	/**
	 * Checks if is font style.
	 *
	 * @param token the token
	 * @return true, if is font style
	 */
	public static boolean isFontStyle(String token) {
		return ITALIC.equals(token) || NORMAL.equals(token) || OBLIQUE.equals(token);
	}

	/**
	 * Checks if is font variant.
	 *
	 * @param token the token
	 * @return true, if is font variant
	 */
	public static boolean isFontVariant(String token) {
		return SMALL_CAPS.equals(token) || NORMAL.equals(token);
	}

	/**
	 * Checks if is font weight.
	 *
	 * @param token the token
	 * @return true, if is font weight
	 */
	public static boolean isFontWeight(String token) {
		if (BOLD.equals(token) || BOLDER.equals(token) || LIGHTER.equals(token)) {
			return true;
		}
		try {
			final int value = Integer.parseInt(token);
			return value % 100 == 0 && value >= 100 && value <= 900;
		} catch (final NumberFormatException nfe) {
			return false;
		}
	}
}
