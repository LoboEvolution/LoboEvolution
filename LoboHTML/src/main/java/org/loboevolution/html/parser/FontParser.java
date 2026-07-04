/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>FontParser class.</p>
 */
public class FontParser {

    public final static int FONT_STYLE_INDEX = 0;    // font-style (optional)
    public final static int FONT_VARIANT_INDEX = 1;  // font-variant (optional)
    public final static int FONT_WEIGHT_INDEX = 2;   // font-weight (optional)
    public final static int FONT_STRETCH_INDEX = 3;  // font-stretch (optional, CSS3)
    public final static int FONT_SIZE_INDEX = 4;     // font-size (required)
    public final static int LINE_HEIGHT_INDEX = 5;   // line-height (optional)
    public final static int FONT_FAMILY_INDEX = 6;   // font-family (required, LAST)

    public static final Map<String, String> FONT_STRETCH_VALUES;

    static {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("ultra-condensed", "50%");
        map.put("extra-condensed", "62.5%");
        map.put("condensed", "75%");
        map.put("semi-condensed", "87.5%");
        map.put("normal", "100%");
        map.put("semi-expanded", "112.5%");
        map.put("expanded", "125%");
        map.put("extra-expanded", "150%");
        map.put("ultra-expanded", "200%");
        FONT_STRETCH_VALUES = Collections.unmodifiableMap(map);
    }

    private static final Set<String> GENERIC_FONT_FAMILIES = Set.of(
            "serif", "sans-serif", "monospace", "cursive", "fantasy", "system-ui"
    );

    public static String getPercentage(String stretchValue) {
        return FONT_STRETCH_VALUES.getOrDefault(stretchValue, "100%");
    }

    public String[] fontParser(final String font) {
        if (font == null || font.trim().isEmpty()) {
            return null;
        }

        final String[] details = new String[7];
        String remaining = parseFontProperties(font, details);

        // The remaining part should be the font family
        if (Strings.isNotBlank(remaining)) {
            details[FONT_FAMILY_INDEX] = formatFontFamily(remaining.trim());
        }

        // At minimum, we need font-size and font-family
        if (details[FONT_SIZE_INDEX] == null || details[FONT_FAMILY_INDEX] == null) {
            return null;
        }

        return details;
    }

    private String parseFontProperties(String font, String[] details) {
        // Normalize spaces around slash: "1px/ 2px" -> "1px/2px"
        String normalized = font.replaceAll("\\s*/\\s*", "/");
        String[] tokens = normalized.split("\\s+");
        int i = 0;

        // Parse style, variant, weight, stretch
        while (i < tokens.length) {
            String token = tokens[i];
            boolean matched = false;

            if (details[FONT_STYLE_INDEX] == null && FontValues.isFontStyle(token)) {
                // Check if this is oblique with an angle (e.g., "oblique 10deg")
                String tokenLower = token.toLowerCase();
                if (tokenLower.startsWith("oblique") && i + 1 < tokens.length) {
                    String nextToken = tokens[i + 1];
                    String nextTokenLower = nextToken.toLowerCase();
                    // Check if next token is an angle (deg, rad, grad, turn)
                    if (nextTokenLower.matches("\\d+(\\.\\d+)?(deg|rad|grad|turn)")) {
                        // Preserve original case for angle
                        details[FONT_STYLE_INDEX] = tokenLower + " " + nextToken;
                        i++; // Skip the angle token
                        matched = true;
                    } else {
                        details[FONT_STYLE_INDEX] = tokenLower;
                        matched = true;
                    }
                } else {
                    details[FONT_STYLE_INDEX] = tokenLower;
                    matched = true;
                }
            } else if (details[FONT_VARIANT_INDEX] == null && FontValues.isFontVariant(token)) {
                details[FONT_VARIANT_INDEX] = token;
                matched = true;
            } else if (details[FONT_WEIGHT_INDEX] == null && FontValues.isFontWeight(token)) {
                details[FONT_WEIGHT_INDEX] = token;
                matched = true;
            } else if (details[FONT_STRETCH_INDEX] == null && FontValues.isFontStretch(token)) {
                details[FONT_STRETCH_INDEX] = token;
                matched = true;
            } else {
                // Try to parse as font-size/line-height
                String[] fontSize = fontSizeParser(token);
                if (fontSize != null) {
                    details[FONT_SIZE_INDEX] = fontSize[0];
                    details[LINE_HEIGHT_INDEX] = fontSize[1];
                    matched = true;
                    i++;
                    break; // After size, the rest is family
                }
            }

            if (!matched) {
                break; // No more properties to parse
            }
            i++;
        }

        // Collect remaining tokens as font family
        if (i < tokens.length) {
            StringBuilder family = new StringBuilder();
            for (; i < tokens.length; i++) {
                if (family.length() > 0) family.append(" ");
                family.append(tokens[i]);
            }
            return family.toString();
        }

        return "";
    }

    private String[] fontSizeParser(final String fontSize) {
        final int slash = fontSize.indexOf('/');
        final String actualFontSize = slash == -1 ? fontSize : fontSize.substring(0, slash).trim();
        
        // Check if font size has valid units
        boolean hasValidUnits = HtmlValues.isUnits(actualFontSize) || actualFontSize.endsWith("%");
        if (!hasValidUnits) {
            return null;
        }

        String actualLineHeight = slash == -1 ? "" : fontSize.substring(slash + 1).trim();
        if (Strings.isBlank(actualLineHeight)) {
            actualLineHeight = null;
        } else if (!isValidLineHeight(actualLineHeight)) {
            return null;
        }
        return new String[]{actualFontSize, actualLineHeight};
    }

    private boolean isValidLineHeight(final String lineHeight) {
        return HtmlValues.isUnits(lineHeight) || Strings.isNumeric(lineHeight) || CSSValues.NORMAL.isEqual(lineHeight) || lineHeight.endsWith("%");
    }

    private String formatFontFamily(String fontFamily) {
        // Handle multiple font families separated by commas
        if (fontFamily.contains(",")) {
            String[] families = fontFamily.split(",");
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < families.length; i++) {
                if (i > 0) result.append(", ");
                result.append(formatSingleFontFamily(families[i].trim()));
            }
            return result.toString();
        }
        
        return formatSingleFontFamily(fontFamily);
    }

    private String formatSingleFontFamily(String fontFamily) {
        String cleaned = fontFamily.replace("\"", "").trim();

        if (GENERIC_FONT_FAMILIES.contains(cleaned.toLowerCase())) {
            return cleaned.toLowerCase();
        }

        // Don't capitalize - preserve original casing
        return cleaned.contains(" ") ? "\"" + cleaned + "\"" : cleaned;
    }

    private String capitalizeFontName(String name) {
        return Arrays.stream(name.split("\\s+"))
                .map(word -> word.isEmpty() ? word :
                        Character.toUpperCase(word.charAt(0)) +
                                (word.length() > 1 ? word.substring(1).toLowerCase() : ""))
                .collect(Collectors.joining(" "));
    }
}
