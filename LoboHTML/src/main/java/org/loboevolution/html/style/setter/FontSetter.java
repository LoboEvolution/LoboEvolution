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
