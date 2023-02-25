/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.js.css.CSSStyleDeclarationImpl;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.style.FontValues;
import org.loboevolution.html.style.HtmlValues;

/**
 * <p>FontSetter class.</p>
 */
public class FontSetter implements SubPropertySetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeValue(CSSStyleDeclaration declaration, String newValue) {
        CSSStyleDeclarationImpl properties = (CSSStyleDeclarationImpl) declaration;
        if (Strings.isNotBlank(newValue)) {
            final String fontSpecTL = newValue.toLowerCase();
            final String[] tokens = fontSpecTL.split(" ");
            String token;
            boolean isSlash = false;
            final int length = tokens.length;
            int i;

            if (length > 0) {
                properties.setProperty(FONT, newValue);
                setDefaultFontValus(properties);
            }

            for (i = 0; i < length; i++) {
                token = tokens[i].trim().replace(",", "");

                if (Strings.isBlank(token)) {
                    continue;
                }

                if (FontValues.isFontStyle(token)) {
                    properties.setProperty(FONT_STYLE, token);
                    continue;
                }

                if (FontValues.isFontVariant(token)) {
                    properties.setProperty(FONT_VARIANT, token);
                    continue;
                }

                if (FontValues.isFontWeight(token)) {
                    properties.setProperty(FONT_WEIGHT, token);
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
                        properties.setProperty(LINE_HEIGHT, token);
                    } else{
                        properties.setProperty(LINE_HEIGHT, lineHeightText);
                    }

                    isSlash = false;
                    continue;
                }

                if (token.contains("/") && properties.getPropertyValue(FONT_SIZE) == null) {

                    final int slashIdx = token.indexOf('/');
                    final String fontSizeText = slashIdx == -1 ? token : token.substring(0, slashIdx);
                    final String lineHeightText = slashIdx == -1 ? null : token.substring(slashIdx + 1);

                    if (HtmlValues.isUnits(fontSizeText)) {
                        properties.setProperty(FONT_SIZE, fontSizeText);
                    }

                  if (lineHeightText != null && HtmlValues.isUnits(lineHeightText)) {
                        properties.setProperty(LINE_HEIGHT, lineHeightText);
                    } else {
                        isSlash = true;
                    }

                    continue;
                }

                if (HtmlValues.isUnits(token)) {
                    properties.setProperty(FONT_SIZE, token);
                    continue;
                }

                String fontFamily = properties.getPropertyValue(FONT_FAMILY);
                if(Strings.isCssBlank(fontFamily)) {
                    properties.setProperty(FONT_FAMILY, token.trim());
                } else {
                    properties.setProperty(FONT_FAMILY, fontFamily + ", " + token.trim());
                }
                continue;
            }
        }
    }

    private void setDefaultFontValus(CSSStyleDeclarationImpl properties) {
        properties.setProperty(FONT_STYLE, CSSValues.NORMAL.getValue());
        properties.setProperty(FONT_VARIANT, CSSValues.NORMAL.getValue());
    }
}
