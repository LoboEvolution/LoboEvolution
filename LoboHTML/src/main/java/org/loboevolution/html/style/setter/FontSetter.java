/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
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

package org.loboevolution.html.style.setter;

import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;
import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.FontValues;
import org.loboevolution.html.style.HtmlValues;

import java.util.Arrays;

/**
 * <p>FontSetter class.</p>
 */
public class FontSetter implements SubPropertySetter {

    /**
     * <p>changeValue.</p>
     *
     * @param properties  a {@link org.loboevolution.html.style.AbstractCSSProperties} object.
     * @param newValue    a {@link java.lang.String} object.
     * @param declaration a {@link com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl} object.
     */
    public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration) {
        this.changeValue(properties, newValue, declaration, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration, boolean important) {
        if (Strings.isNotBlank(newValue)) {
            final String fontSpecTL = newValue.toLowerCase();
            final String[] tokens = fontSpecTL.split(" ");
            String token = null;
            boolean isSlash = false;
            final int length = tokens.length;
            int i;

            if (length > 0) {
                properties.setPropertyValueLCAlt(FONT, newValue, important);
                setDfaultFontValus(properties, important);
            }

            for (i = 0; i < length; i++) {
                token = tokens[i].trim().replace(",", "");

                if (Strings.isBlank(token)) {
                    continue;
                }

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

                if ("/".equals(token)) {
                    isSlash = true;
                    continue;
                }

                if ((token.contains("/") || isSlash) && properties.getPropertyValue(FONT_SIZE) != null) {
                    final int slashIdx = token.indexOf('/');
                    final String lineHeightText = slashIdx == -1 ? null : token.substring(slashIdx + 1);


                    if(lineHeightText == null) {
                        properties.setPropertyValueLCAlt(LINE_HEIGHT, token, important);
                    } else{
                        properties.setPropertyValueLCAlt(LINE_HEIGHT, lineHeightText, important);
                    }

                    isSlash = false;
                    continue;
                }

                if (token.contains("/") && properties.getPropertyValue(FONT_SIZE) == null) {

                    final int slashIdx = token.indexOf('/');
                    final String fontSizeText = slashIdx == -1 ? token : token.substring(0, slashIdx);
                    final String lineHeightText = slashIdx == -1 ? null : token.substring(slashIdx + 1);

                    if (HtmlValues.isUnits(fontSizeText)) {
                        properties.setPropertyValueLCAlt(FONT_SIZE, fontSizeText, important);
                    }

                  if (lineHeightText != null && HtmlValues.isUnits(lineHeightText)) {
                        properties.setPropertyValueLCAlt(LINE_HEIGHT, lineHeightText, important);
                    } else {
                        isSlash = true;
                    }

                    continue;
                }

                if (HtmlValues.isUnits(token)) {
                    properties.setPropertyValueLCAlt(FONT_SIZE, token, important);
                    continue;
                }

                String fontFamily = properties.getPropertyValue(FONT_FAMILY);
                if(fontFamily == null) {
                    properties.setPropertyValueLCAlt(FONT_FAMILY, token.trim(), important);
                } else {
                    properties.setPropertyValueLCAlt(FONT_FAMILY, fontFamily + ", " + token.trim(), important);
                }
                continue;
            }
        }
    }

    private void setDfaultFontValus(AbstractCSSProperties properties, boolean important) {
        properties.setPropertyValueLCAlt(FONT_STYLE, CSSValues.NORMAL.getValue(), important);
        properties.setPropertyValueLCAlt(FONT_VARIANT, CSSValues.NORMAL.getValue(), important);
        properties.setPropertyValueLCAlt(FONT_WEIGHT, CSSValues.BOLD400.getValue(), important);
    }
}
