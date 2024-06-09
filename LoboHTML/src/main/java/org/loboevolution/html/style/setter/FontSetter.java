/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.html.parser.FontParser;

/**
 * <p>FontSetter class.</p>
 */
public class FontSetter implements SubPropertySetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeValue(final CSSStyleDeclaration declaration, final String newValue) {
        final CSSStyleDeclarationImpl properties = (CSSStyleDeclarationImpl) declaration;
        setDefaultFontValues(properties);
        if (Strings.isNotBlank(newValue)) {
            FontParser fontParser = new FontParser();
            String[] tokens = fontParser.fontParser(newValue);
            if (tokens != null) {
                properties.setProperty(FONT_FAMILY, tokens[FontParser.FONT_FAMILY_INDEX]);
                properties.setProperty(FONT_SIZE, tokens[FontParser.FONT_SIZE_INDEX]);

                if (tokens[FontParser.FONT_STYLE_INDEX] != null) {
                    properties.setProperty(FONT_STYLE, tokens[FontParser.FONT_STYLE_INDEX]);
                }

                if (tokens[FontParser.FONT_VARIANT_INDEX] != null) {
                    properties.setProperty(FONT_VARIANT, tokens[FontParser.FONT_VARIANT_INDEX]);
                }

                if (tokens[FontParser.LINE_HEIGHT_INDEX] != null) {
                    properties.setProperty(LINE_HEIGHT, tokens[FontParser.LINE_HEIGHT_INDEX]);
                }

                if (tokens[FontParser.FONT_WEIGHT_INDEX] != null) {
                    properties.setProperty(FONT_WEIGHT, tokens[FontParser.FONT_WEIGHT_INDEX]);
                }

                if (tokens[FontParser.FONT_STRETCH_INDEX] != null) {
                    properties.setProperty(FONT_STRETCH, tokens[FontParser.FONT_STRETCH_INDEX]);
                }

            } else {
                setNullFontValues(properties);
            }
        }
    }

    private void setDefaultFontValues(final CSSStyleDeclarationImpl properties) {
        properties.setProperty(FONT_STYLE, CSSValues.NORMAL.getValue());
        properties.setProperty(FONT_VARIANT, CSSValues.NORMAL.getValue());
        properties.setProperty(FONT_STRETCH, CSSValues.NORMAL.getValue());
        properties.setProperty(LINE_HEIGHT, CSSValues.NORMAL.getValue());
        properties.setProperty(FONT_WEIGHT, CSSValues.NORMAL.getValue());
        properties.setProperty(FONT_SIZE, CSSValues.NORMAL.getValue());
    }

    private void setNullFontValues(final CSSStyleDeclarationImpl properties) {
        properties.setProperty(FONT_STYLE, null);
        properties.setProperty(FONT_VARIANT, null);
        properties.setProperty(FONT_STRETCH, null);
        properties.setProperty(LINE_HEIGHT, null);
        properties.setProperty(FONT_WEIGHT, null);
        properties.setProperty(FONT_SIZE, null);
    }
}
