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

import org.loboevolution.pdfview.PDFDebugger;
import org.loboevolution.pdfview.PDFObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A CMap maps from a character in a composite font to a font/glyph number
 * pair in a CID font.
 * <p>
 * Author  jkaplan
 */
public abstract class PDFCMap {
    /**
     * A cache of known CMaps by name
     */
    private static Map<String, PDFCMap> cache;

    /**
     * Creates a new instance of CMap
     */
    protected PDFCMap() {
    }

    /**
     * Get a CMap, given a PDF object containing one of the following:
     * a string name of a known CMap
     * a stream containing a CMap definition
     *
     * @param map a {@link org.loboevolution.pdfview.PDFObject} object.
     * @return a {@link org.loboevolution.pdfview.font.cid.PDFCMap} object.
     * @throws java.io.IOException if any.
     */
    public static PDFCMap getCMap(final PDFObject map) throws IOException {
        if (map.getType() == PDFObject.NAME) {
            return getCMap(map.getStringValue());
        } else if (map.getType() == PDFObject.STREAM) {
            return parseCMap(map);
        } else {
            throw new IOException("CMap type not Name or Stream!");
        }
    }

    /**
     * Get a CMap, given a string name
     *
     * @param mapName a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.pdfview.font.cid.PDFCMap} object.
     * @throws java.io.IOException if any.
     */
    public static PDFCMap getCMap(final String mapName) throws IOException {
        if (cache == null) {
            populateCache();
        }

        if (!cache.containsKey(mapName)) {
            //throw new IOException("Unknown CMap: " + mapName);
            PDFDebugger.debug("Unknown CMap: '" + mapName + "' procced with 'Identity-H'");
            return cache.get("Identity-H");
        }

        return cache.get(mapName);
    }

    /**
     * Populate the cache with well-known types
     */
    protected static void populateCache() {
        cache = new HashMap<>();

        // add the Identity-H map
        cache.put("Identity-H", new PDFCMap() {
            @Override
            public char map(final char src) {
                return src;
            }
        });
    }

    /**
     * Parse a CMap from a CMap stream
     *
     * @param map a {@link org.loboevolution.pdfview.PDFObject} object.
     * @return a {@link org.loboevolution.pdfview.font.cid.PDFCMap} object.
     * @throws java.io.IOException if any.
     */
    protected static PDFCMap parseCMap(final PDFObject map) throws IOException {
        return new ToUnicodeMap(map);
    }

    /**
     * Map a given source character to a destination character
     *
     * @param src a char.
     * @return a char.
     */
    public abstract char map(char src);

    /**
     * Get the font number assoicated with a given source character
     *
     * @param src a char.
     * @return a {@link java.lang.Integer} object.
     */
    public int getFontID(final char src) {
        return 0;
    }

}
