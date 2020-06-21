package org.loboevolution.html.style;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.font.TextAttribute;

import org.loboevolution.html.CSSValues;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.laf.LAFSettings;

/**
 * <p>FontValues class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class FontValues extends HtmlValues {
	/**
	 * <p>getFontFamily.</p>
	 *
	 * @param spec a {@link java.lang.String} object.
	 * @param parentRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link java.lang.String} object.
	 */
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
	 * Gets the font size.
	 *
	 * @param spec              the spec
	 * @param parentRenderState the parent render state
	 * @return the font size
	 */
	public static final float getFontSize(String spec, RenderState parentRenderState) {

		if (spec == null) {
			return new LAFSettings().getInstance().getFontSize();
		}

		final String specTL = spec.toLowerCase();

		if (specTL.endsWith("em")) {
			if (parentRenderState == null) {
				return new LAFSettings().getInstance().getFontSize();
			}
			final Font font = parentRenderState.getFont();
			final String pxText = specTL.substring(0, specTL.length() - 2);
			double value;
			try {
				value = Double.parseDouble(pxText);
			} catch (final NumberFormatException nfe) {
				return new LAFSettings().getInstance().getFontSize();
			}
			return (int) Math.round(font.getSize() * value);
		} else if (specTL.endsWith("px") || specTL.endsWith("pt") || specTL.endsWith("em") || specTL.endsWith("pc")
				|| specTL.endsWith("em") || specTL.endsWith("mm") || specTL.endsWith("ex")) {
			final int pixelSize = getPixelSize(spec, parentRenderState,
					(int) new LAFSettings().getInstance().getFontSize());
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
				return new LAFSettings().getInstance().getFontSize();
			}
		} else {

			int parentFontSize = 0;
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
				parentFontSize = (int) new LAFSettings().getInstance().getFontSize();
				if (parentRenderState != null) {
					parentFontSize = parentRenderState.getFont().getSize();
				}
				return parentFontSize * 1.2f;
			case SMALLER:
				parentFontSize = (int) new LAFSettings().getInstance().getFontSize();
				if (parentRenderState != null) {
					parentFontSize = parentRenderState.getFont().getSize();
				}
				return parentFontSize / 1.2f;
			case INHERIT:
				parentFontSize = (int) new LAFSettings().getInstance().getFontSize();
				if (parentRenderState != null && parentRenderState.getPreviousRenderState() != null) {
					parentRenderState.getPreviousRenderState().getFont().getSize();
				}
				return parentFontSize;
			case INITIAL:
			default:
				return getPixelSize(spec, parentRenderState, (int) new LAFSettings().getInstance().getFontSize());
			}
		}
	}

	/**
	 * <p>getFontStrikeThrough.</p>
	 *
	 * @param spec a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean getFontStrikeThrough(String spec) {
		final String strikethrough = spec;
		if (CSSValues.get(strikethrough).equals(CSSValues.LINE_THROUGH)) {
			return TextAttribute.STRIKETHROUGH_ON;
		}

		if (strikethrough == null && new LAFSettings().getInstance().isStrikethrough()) {
			return TextAttribute.STRIKETHROUGH_ON;
		}
		return false;
	}

	/**
	 * <p>getFontStyle.</p>
	 *
	 * @param spec a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static final String getFontStyle(String spec) {
		final String fontStyle = spec;
		if (fontStyle == null && new LAFSettings().getInstance().isItalic()) {
			return CSSValues.ITALIC.getValue();
		}
		return fontStyle;
	}

	/**
	 * <p>getFontSuperScript.</p>
	 *
	 * @param spec a {@link java.lang.String} object.
	 * @param parentRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public static Integer getFontSuperScript(String spec, RenderState parentRenderState) {
		final String verticalAlign = spec;
		Integer superscript = null;

		final boolean isSuper = verticalAlign != null && "super".equalsIgnoreCase(verticalAlign);
		final boolean isSub = verticalAlign != null && "sub".equalsIgnoreCase(verticalAlign);

		if (isSuper || new LAFSettings().getInstance().isSuperscript()) {
			superscript = TextAttribute.SUPERSCRIPT_SUPER;
		} else if (isSub || new LAFSettings().getInstance().isSubscript()) {
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
	 * @param spec a {@link java.lang.String} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public static Integer getFontUnderline(String spec) {
		final String underline = spec;

		if (CSSValues.get(underline).equals(CSSValues.UNDERLINE)) {
			return TextAttribute.UNDERLINE_LOW_TWO_PIXEL;
		}

		if (underline == null && new LAFSettings().getInstance().isUnderline()) {
			return TextAttribute.UNDERLINE_LOW_ONE_PIXEL;
		}
		return null;
	}

	/**
	 * <p>getFontWeight.</p>
	 *
	 * @param spec a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getFontWeight(String spec) {
		final String fontWeight = spec;
		if (fontWeight == null && new LAFSettings().getInstance().isBold()) {
			return CSSValues.BOLD.getValue();
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
