/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.html.parser;

import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.style.FontValues;
import org.loboevolution.html.style.HtmlValues;

/**
 * <p>FontParser class.</p>
 */
public class FontParser {

    public final static int FONT_STYLE_INDEX = 0;
    public final static int FONT_VARIANT_INDEX = 1;
    public final static int FONT_WEIGHT_INDEX = 2;
    public final static int FONT_SIZE_INDEX = 3;
    public final static int LINE_HEIGHT_INDEX = 4;
    public final static int FONT_FAMILY_INDEX = 5;
    public final static int FONT_STRETCH_INDEX = 6;

    public String[] fontParser(final String font) {
        final String[] tokens = font.replace(" / ", "/").split(" ");
        if (tokens.length > 1) {
            final String[] details = new String[7];
            fontParser(tokens, details);
            if (details[FONT_SIZE_INDEX] != null && details[FONT_FAMILY_INDEX] == null) {
                details[FONT_FAMILY_INDEX] = font.indexOf('"') == -1 ? tokens[tokens.length - 1] : font.substring(font.indexOf('"'));
                return details;
            }
        }
        return null;
    }

    private void fontParser(final  String[] tokens, String[] details) {
        for (String token : tokens) {
            if (FontValues.isFontStyle(token)) {
                details[FONT_STYLE_INDEX] = token;
            }

            if (FontValues.isFontVariant(token)) {
                details[FONT_VARIANT_INDEX] = token;
            }

            if (FontValues.isFontWeight(token)) {
                details[FONT_WEIGHT_INDEX] = token;
            }

            if (FontValues.isFontStretch(token)) {
                details[FONT_STRETCH_INDEX] = token;
            }

            final String[] fontSize = fontSizeParser(token);
            if (fontSize != null) {
                details[FONT_SIZE_INDEX] = fontSize[0];
                details[LINE_HEIGHT_INDEX] = fontSize[1];
            }
        }
    }


    private String[] fontSizeParser(final String fontSize) {
        final int slash = fontSize.indexOf('/');
        final String actualFontSize = slash == -1 ? fontSize : fontSize.substring(0, slash);
        if (!HtmlValues.isUnits(actualFontSize) && !actualFontSize.endsWith("%")) {
            return null;
        }

        String actualLineHeight = slash == -1 ? "" : fontSize.substring(slash + 1);
        if (Strings.isBlank(actualLineHeight)) {
            actualLineHeight = null;
        } else if (!isValidLineHeight(actualLineHeight)) {
            return null;
        }
        return new String[]{actualFontSize, actualLineHeight};
    }

    private boolean isValidLineHeight(final String lineHeight) {
        return HtmlValues.isUnits(lineHeight) || CSSValues.NORMAL.isEqual(lineHeight) || lineHeight.endsWith("%");
    }
}
