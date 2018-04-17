package org.loboevolution.html.style.setter;

import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.FontValues;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.util.Strings;
import org.w3c.dom.css.CSSStyleDeclaration;

import com.steadystate.css.util.CSSProperties;

/**
 * The Class FontSetter.
 */
public class FontSetter implements SubPropertySetter,CSSProperties {

	/**
	 * Change value.
	 *
	 * @param properties
	 *            the properties
	 * @param newValue
	 *            the new value
	 * @param declaration
	 *            the declaration
	 */
	public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclaration declaration) {
		this.changeValue(properties, newValue, declaration, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.style.AbstractCSSProperties.SubPropertySetter#
	 * changeValue (org.loboevolution.html.style.AbstractCSSProperties,
	 * java.lang.String, org.w3c.dom.css.CSSStyleDeclaration, boolean)
	 */
	@Override
	public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclaration declaration,
			boolean important) {
		properties.setPropertyValueLCAlt(FONT, newValue, important);
		if (!Strings.isBlank(newValue)) {
			String fontSpecTL = newValue.toLowerCase();
			String[] tokens = HtmlValues.splitCssValue(fontSpecTL);
			String token = null;
			int length = tokens.length;
			int i;
			for (i = 0; i < length; i++) {
				token = tokens[i];
				if (FontValues.isFontStyle(token)) {
					properties.setPropertyValueLCAlt(FONT_STYLE, token, important);
					continue;
				}
				if (FontValues.isFontVariant(token)) {
					properties.setPropertyValueLCAlt(FONT_VARIANT, token, important);
					continue;
				}
				if (FontValues.isFontWeight(token)) {
					properties.setPropertyValueLCAlt(FONT_WEIGHT, token, important);
					continue;
				}
				// Otherwise exit loop
				break;
			}
			if (token != null) {
				int slashIdx = token.indexOf('/');
				String fontSizeText = slashIdx == -1 ? token : token.substring(0, slashIdx);
				properties.setPropertyValueLCAlt(FONT_SIZE, fontSizeText, important);
				String lineHeightText = slashIdx == -1 ? null : token.substring(slashIdx + 1);
				if (lineHeightText != null) {
					properties.setPropertyValueLCAlt(LINE_HEIGHT, lineHeightText, important);
				}
				if (++i < length) {
					StringBuilder fontFamilyBuff = new StringBuilder();
					do {
						token = tokens[i];
						fontFamilyBuff.append(token);
						fontFamilyBuff.append(' ');
					} while (++i < length);
					properties.setPropertyValueLCAlt(FONT_FAMILY, fontFamilyBuff.toString(), important);
				}
			}
		}
	}
}
