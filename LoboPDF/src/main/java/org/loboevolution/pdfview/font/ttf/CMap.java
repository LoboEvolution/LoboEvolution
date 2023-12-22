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
package org.loboevolution.pdfview.font.ttf;

import lombok.Getter;
import org.loboevolution.pdfview.PDFDebugger;

import java.nio.ByteBuffer;

/**
 * <p>Abstract CMap class.</p>
 * <p>
 * Author  jkaplan
 */
@Getter
public abstract class CMap {

    /**
     * The format of this map
     */
    private final short format;

    /**
     * The language of this map, or 0 for language-independent
     */
    private final short language;

    /**
     * Creates a new instance of CMap
     * Don't use this directly, use <code>CMap.createMap()</code>
     *
     * @param format   a short.
     * @param language a short.
     */
    protected CMap(final short format, final short language) {
        this.format = format;
        this.language = language;
    }

    /**
     * Create a map for the given format and language
     *
     * <p>The Macintosh standard character to glyph mapping is supported
     * by format 0.</p>
     *
     * <p>Format 2 supports a mixed 8/16 bit mapping useful for Japanese,
     * Chinese and Korean. </p>
     *
     * <p>Format 4 is used for 16 bit mappings.</p>
     *
     * <p>Format 6 is used for dense 16 bit mappings.</p>
     *
     * <p>Formats 8, 10, and 12 (properly 8.0, 10.0, and 12.0) are used
     * for mixed 16/32-bit and pure 32-bit mappings.<br>
     * This supports text encoded with surrogates in Unicode 2.0 and later.</p>
     *
     * <p>Reference:<br>
     * <a href="http://developer.apple.com/textfonts/TTRefMan/RM06/Chap6cmap.html">...</a> </p>
     *
     * @param format   a short.
     * @param language a short.
     * @return a {@link CMap} object.
     */
    public static CMap createMap(final short format, final short language) {
        CMap outMap;

        switch (format) {
            case 0: // CMap format 0 - single byte codes
                outMap = new CMapFormat0(language);
                break;
            case 4: // CMap format 4 - two byte encoding
                outMap = new CMapFormat4(language);
                break;
            case 6: // CMap format 6 - 16-bit, two byte encoding
                outMap = new CMapFormat6(language);
                break;
            default:
                PDFDebugger.debug("Unsupport CMap format: " + format);
                return null;
        }

        return outMap;
    }

    /**
     * Get a map from the given data
     * <p>
     * This method reads the format, data and length variables of
     * the map.
     *
     * @param data a {@link java.nio.ByteBuffer} object.
     * @return a {@link org.loboevolution.pdfview.font.ttf.CMap} object.
     */
    public static CMap getMap(final ByteBuffer data) {
        final short format = data.getShort();
        final short lengthShort = data.getShort();
        final int length = 0xFFFF & lengthShort;
        PDFDebugger.debug("CMAP, length: " + length + ", short: " + lengthShort, 100);

        // make sure our slice of the data only contains up to the length
        // of this table
        data.limit(Math.min(length, data.limit()));

        final short language = data.getShort();

        final CMap outMap = createMap(format, language);
        if (outMap == null) {
            return null;
        }

        outMap.setData(data.limit(), data);

        return outMap;
    }

    /**
     * Set the data for this map
     *
     * @param length a {@link java.lang.Integer} object.
     * @param data   a {@link java.nio.ByteBuffer} object.
     */
    public abstract void setData(int length, ByteBuffer data);

    /**
     * Get the data in this map as a byte buffer
     *
     * @return a {@link java.nio.ByteBuffer} object.
     */
    public abstract ByteBuffer getData();

    /**
     * Get the length of this map
     *
     * @return a short.
     */
    public abstract short getLength();

    /**
     * Map an 8 bit value to another 8 bit value
     *
     * @param src a byte.
     * @return a byte.
     */
    public abstract byte map(byte src);

    /**
     * Map a 16 bit value to another 16 but value
     *
     * @param src a char.
     * @return a char.
     */
    public abstract char map(char src);

    /**
     * Get the src code which maps to the given glyphID
     *
     * @param glyphID a short.
     * @return a char.
     */
    public abstract char reverseMap(final short glyphID);

    /**
     * {@inheritDoc}
     * <p>
     * Print a pretty string
     */
    @Override
    public String toString() {
        final String indent = "        ";

        return indent + " format: " + getFormat() + " length: " +
                getLength() + " language: " + getLanguage() + "\n";
    }
}
