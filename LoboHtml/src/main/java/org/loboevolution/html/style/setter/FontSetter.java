package org.loboevolution.html.style.setter;

import org.loboevolution.common.Strings;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.info.FontInfo;

import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;

public class FontSetter implements SubPropertySetter {

	public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration) {
		this.changeValue(properties, newValue, declaration, true);
	}

	@Override
	public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration,
			boolean important) {
		properties.setPropertyValueLCAlt(FONT, newValue, important);
		if (Strings.isNotBlank(newValue)) {
			final String fontSpecTL = newValue.toLowerCase();
			final FontInfo fontInfo = (FontInfo) HtmlValues.SYSTEM_FONTS.get(fontSpecTL);
			if (fontInfo != null) {
				if (fontInfo.getFontFamily() != null) {
					properties.setPropertyValueLCAlt(FONT_FAMILY, fontInfo.getFontFamily(), important);
				}
				if (fontInfo.getFontSize() != null) {
					properties.setPropertyValueLCAlt(FONT_SIZE, fontInfo.getFontSize(), important);
				}
				if (fontInfo.getFontStyle() != null) {
					properties.setPropertyValueLCAlt(FONT_STYLE, fontInfo.getFontStyle(), important);
				}
				if (fontInfo.getFontVariant() != null) {
					properties.setPropertyValueLCAlt(FONT_VARIANT, fontInfo.getFontVariant(), important);
				}
				if (fontInfo.getFontWeight() != null) {
					properties.setPropertyValueLCAlt(FONT_WEIGHT, fontInfo.getFontWeight(), important);
				}
				return;
			}
			final String[] tokens = HtmlValues.splitCssValue(fontSpecTL);
			String token = null;
			final int length = tokens.length;
			int i;
			for (i = 0; i < length; i++) {
				token = tokens[i];
				if (HtmlValues.isFontStyle(token)) {
					properties.setPropertyValueLCAlt(FONT_STYLE, token, important);
					continue;
				}
				if (HtmlValues.isFontVariant(token)) {
					properties.setPropertyValueLCAlt(FONT_VARIANT, token, important);
					continue;
				}
				if (HtmlValues.isFontWeight(token)) {
					properties.setPropertyValueLCAlt(FONT_WEIGHT, token, important);
					continue;
				}
				// Otherwise exit loop
				break;
			}
			if (token != null) {
				final int slashIdx = token.indexOf('/');
				final String fontSizeText = slashIdx == -1 ? token : token.substring(0, slashIdx);
				properties.setPropertyValueLCAlt(FONT_SIZE, fontSizeText, important);
				final String lineHeightText = slashIdx == -1 ? null : token.substring(slashIdx + 1);
				if (lineHeightText != null) {
					properties.setPropertyValueLCAlt(LINE_HEIGHT, lineHeightText, important);
				}
				if (++i < length) {
					final StringBuilder fontFamilyBuff = new StringBuilder();
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