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

package org.loboevolution.pdfview.font.cid;

import org.loboevolution.pdfview.PDFObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * **************************************************************************
 * Parses a CMAP and builds a lookup table to map CMAP based codes to unicode.
 * This is not a fully functional CMAP parser but a stripped down parser
 * that should be able to parse some limited variants of CMAPs that are
 * used for the ToUnicode mapping found for some Type0 fonts.
 * <p>
 * Author  Bernd Rosstauscher
 *
 * @since 03.08.2011
 * **************************************************************************
 */
public class ToUnicodeMap extends PDFCMap {

    private final Map<Character, Character> singleCharMappings;
    private final List<CharRangeMapping> charRangeMappings;
    private final List<CodeRangeMapping> codeRangeMappings;
    /**
     * **********************************************************************
     * Constructor
     *
     * @param map a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    public ToUnicodeMap(final PDFObject map) throws IOException {
        super();
        this.singleCharMappings = new HashMap<>();
        this.charRangeMappings = new ArrayList<>();
        this.codeRangeMappings = new ArrayList<>();
        parseMappings(map);
    }

    /*************************************************************************
     * @param map a {@link PDFObject} object.
     * @throws {@link IOException} object.
     ************************************************************************/

    private void parseMappings(final PDFObject map) throws IOException {
        try {
            final StringReader reader = new StringReader(new String(map.getStream(), "ASCII"));
            final BufferedReader bf = new BufferedReader(reader);
            String line = bf.readLine();
            while (line != null) {
                if (line.contains("beginbfchar") || line.contains("begincidchar")) {
                    parseSingleCharMappingSection(bf, line.contains("begincidchar"));
                }
                if (line.contains("beginbfrange") || line.contains("begincidrange")) {
                    parseCharRangeMappingSection(bf, line.contains("begincidrange"));
                }
                if (line.contains("begincodespacerange")) {
                    parseCodeRangeMappingSection(bf, line);
                }
                line = bf.readLine();
            }
        } catch (final UnsupportedEncodingException e) {
            throw new IOException(e);
        }
    }

    /*************************************************************************
     * @param bf a {@link BufferedReader} object.
     * @throws {@link IOException} object.
     ************************************************************************/

    private void parseCharRangeMappingSection(final BufferedReader bf, final boolean isCid) throws IOException {
        String line = bf.readLine();
        while (line != null) {
            if (line.contains("endbfrange") || line.contains("endcidrange")) {
                break;
            }
            parseRangeLine(line, isCid);
            line = bf.readLine();
        }
    }

    private void parseCodeRangeMappingSection(final BufferedReader bf, final String ln) throws IOException {
        String line = ln;
        if (line.contains("endcodespacerange")) {
            int indexOf = line.indexOf("endcodespacerange");
            line = line.substring(0, indexOf);
            indexOf = line.indexOf("begincodespacerange");
            line = line.substring(indexOf + "begincodespacerange".length());
            line = line.trim();

            parseCodeRangeLine(line);
        } else {
            String rline = bf.readLine();
            while (rline != null) {
                if (rline.contains("endcodespacerange")) {
                    break;
                }
                parseCodeRangeLine(rline);
                rline = bf.readLine();
            }
        }
    }

    /*************************************************************************
     * @param line a {@link String} object.
     * @param isCID a {@link Boolean} object.
     ************************************************************************/

    private void parseRangeLine(final String line, final boolean isCid) {
        final String[] mapping = line.split(" ");
        if (mapping.length == 3) {
            final Character srcStart = parseChar(mapping[0]);
            final Character srcEnd = parseChar(mapping[1]);
            final Character destStart;
            if (isCid) {
                destStart = (char) Integer.parseInt(mapping[2]);
            } else {
                destStart = parseChar(mapping[2]);
            }
            this.charRangeMappings.add(new CharRangeMapping(srcStart, srcEnd, destStart));
        } else {
            final int indexOf1 = line.indexOf(">");
            final String substring1 = line.substring(0, indexOf1 + 1);

            final int indexOf2 = line.indexOf("<", indexOf1);
            final int indexOf3 = line.indexOf(">", indexOf2);
            final String substring2 = line.substring(indexOf2, indexOf3 + 1);

            final int indexOf4 = line.indexOf("<", indexOf3);
            final String substring3 = line.substring(indexOf4);

            if (!substring1.isEmpty() && !substring2.isEmpty() && !substring3.isEmpty()) {
                final Character srcStart = parseChar(substring1);
                final Character srcEnd = parseChar(substring2);
                final Character destStart = parseChar(substring3);
                this.charRangeMappings.add(new CharRangeMapping(srcStart, srcEnd, destStart));
            }
        }
    }

    private void parseCodeRangeLine(final String line) {
        final String[] mapping = line.split(" ");
        if (mapping.length == 2) {
            final Character srcStart = parseChar(mapping[0]);
            final Character srcEnd = parseChar(mapping[1]);
            this.codeRangeMappings.add(new CodeRangeMapping(srcStart, srcEnd));
        } else {
            final int indexOf1 = line.indexOf(">");
            final String substring1 = line.substring(0, indexOf1 + 1);

            final int indexOf2 = line.indexOf("<", indexOf1);
            final String substring2 = line.substring(indexOf2);

            if (!substring1.isEmpty() && !substring2.isEmpty()) {
                final Character srcStart = parseChar(substring1);
                final Character srcEnd = parseChar(substring2);
                this.codeRangeMappings.add(new CodeRangeMapping(srcStart, srcEnd));
            }
        }
    }

    /*************************************************************************
     * @param bf a {@link BufferedReader} object.
     * @param isCID a {@link Boolean} object.
     * @throws {@link IOException} object.
     ************************************************************************/

    private void parseSingleCharMappingSection(final BufferedReader bf, final boolean isCID) throws IOException {
        String line = bf.readLine();
        while (line != null) {
            if (line.contains("endbfchar") || line.contains("endcidchar")) {
                break;
            }
            parseSingleCharMappingLine(line, isCID);
            line = bf.readLine();
        }
    }

    /*************************************************************************
     * @param line a {@link String} object.
     * @param isCID a {@link Boolean} object.
     ************************************************************************/

    private void parseSingleCharMappingLine(final String line, final boolean isCID) {
        final String[] mapping = line.split(" ");
        if (mapping.length == 2) {
            if (isCID) {
                this.singleCharMappings.put(parseChar(mapping[0]), (char) Integer.parseInt(mapping[1]));
            } else {
                this.singleCharMappings.put(parseChar(mapping[0]), parseChar(mapping[1]));
            }
        }
    }

    /*************************************************************************
     * Parse a string of the format <0F3A> to a char.
     * @param chardef a {@link String} object.
     * @return a {@link Character} object.
     ************************************************************************/

    private Character parseChar(final String chardef) {
        String charDef = chardef;
        if (charDef.startsWith("<")) {
            charDef = charDef.substring(1);
        }
        if (charDef.endsWith(">")) {
            charDef = charDef.substring(0, charDef.length() - 1);
        }
        try {
            final long result = Long.decode("0x" + charDef);
            return (char) result;
        } catch (final NumberFormatException e) {
            return ' ';
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * **********************************************************************
     * map
     *
     * @see org.loboevolution.pdfview.font.cid.PDFCMap#map(char)
     * **********************************************************************
     */
    @Override
    public char map(final char src) {
        Character mappedChar = null;
        for (final CodeRangeMapping codeRange : this.codeRangeMappings) {
            if (codeRange.contains(src)) {
                mappedChar = this.singleCharMappings.get(src);
                if (mappedChar == null) {
                    mappedChar = lookupInRanges(src);
                }
                break;
            }
        }
        if (mappedChar == null) {
            // TODO XOND 27.03.2012: PDF Spec. "9.7.6.3Handling Undefined Characters"
            mappedChar = 0;
        }
        return mappedChar;
    }

    /*************************************************************************
     * @param src a {@link Character} object.
     * @return a {@link Character} object.
     ************************************************************************/

    private Character lookupInRanges(final char src) {
        Character mappedChar = null;
        for (final CharRangeMapping rangeMapping : this.charRangeMappings) {
            if (rangeMapping.contains(src)) {
                mappedChar = rangeMapping.map(src);
                break;
            }
        }
        return mappedChar;
    }

    /*****************************************************************************
     * Small helper class to define a code range.
     ****************************************************************************/

    private static class CodeRangeMapping {
        final char srcStart;
        final char srcEnd;

        CodeRangeMapping(final char srcStart, final char srcEnd) {
            this.srcStart = srcStart;
            this.srcEnd = srcEnd;
        }

        boolean contains(final char c) {
            return this.srcStart <= c
                    && c <= this.srcEnd;
        }

    }

    /*****************************************************************************
     * Small helper class to define a char range.
     ****************************************************************************/

    private static class CharRangeMapping {
        final char srcStart;
        final char srcEnd;
        final char destStart;

        CharRangeMapping(final char srcStart, final char srcEnd, final char destStart) {
            this.srcStart = srcStart;
            this.srcEnd = srcEnd;
            this.destStart = destStart;
        }

        boolean contains(final char c) {
            return this.srcStart <= c
                    && c <= this.srcEnd;
        }

        char map(final char src) {
            return (char) (this.destStart + (src - this.srcStart));
        }

    }

}
