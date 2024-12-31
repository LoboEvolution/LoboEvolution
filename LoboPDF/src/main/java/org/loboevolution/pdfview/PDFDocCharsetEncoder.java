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

package org.loboevolution.pdfview;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.HashMap;
import java.util.Map;

/**
 * Encodes into a PDFDocEncoding representation. Note that only 256 characters
 * (if that) are represented in the PDFDocEncoding, so users should be
 * prepared to deal with unmappable character exceptions.
 * <p>
 * see "PDF Reference version 1.7, Appendix D"
 * Author Luke Kirby
 */
public class PDFDocCharsetEncoder extends CharsetEncoder {

    /**
     * For each character that exists in PDFDocEncoding, identifies whether
     * the byte value in UTF-16BE is the same as it is in PDFDocEncoding
     */
    static final boolean[] IDENT_PDF_DOC_ENCODING_MAP = new boolean[256];
    /**
     * For non-identity encoded characters, maps from the character to
     * the byte value in PDFDocEncoding. If an entry for a non-identity
     * coded character is absent from this map, that character is unmappable
     * in the PDFDocEncoding.
     */
    static final Map<Character, Byte> EXTENDED_TO_PDF_DOC_ENCODING_MAP =
            new HashMap<>();

    static {
        for (byte i = 0; i < PDFStringUtil.PDF_DOC_ENCODING_MAP.length; ++i) {
            final char c = PDFStringUtil.PDF_DOC_ENCODING_MAP[i];
            final boolean identical = (c == i);
            IDENT_PDF_DOC_ENCODING_MAP[i] = identical;
            if (!identical) {
                EXTENDED_TO_PDF_DOC_ENCODING_MAP.put(c, i);
            }
        }
    }

    /**
     * <p>Constructor for PDFDocCharsetEncoder.</p>
     */
    public PDFDocCharsetEncoder() {
        super(null, 1, 1);
    }

    /**
     * Identify whether a particular character preserves the same byte value
     * upon encoding in PDFDocEncoding
     *
     * @param ch the character
     * @return whether the character is identity encoded
     */
    public static boolean isIdentityEncoding(final char ch) {
        return ch >= 0 && ch <= 255 && IDENT_PDF_DOC_ENCODING_MAP[ch];

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CoderResult encodeLoop(final CharBuffer in, final ByteBuffer out) {
        while (in.remaining() > 0) {
            if (out.remaining() < 1) {
                return CoderResult.OVERFLOW;
            }
            final char c = in.get();
            if (c >= 0 && c < 256 && IDENT_PDF_DOC_ENCODING_MAP[c]) {
                out.put((byte) c);
            } else {
                final Byte mapped = EXTENDED_TO_PDF_DOC_ENCODING_MAP.get(c);
                if (mapped != null) {
                    out.put(mapped);
                } else {
                    return CoderResult.unmappableForLength(1);
                }
            }
        }
        return CoderResult.UNDERFLOW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLegalReplacement(final byte[] repl) {
        // avoid referencing the non-existent character set
        return true;
    }
}
