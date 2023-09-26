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
package org.loboevolution.pdfview.decode;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import org.loboevolution.pdfview.PDFFile;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

/**
 * decode ASCII85 text into a byte array.
 *
 * Author Mike Wessler
  *
 */
public final class ASCII85Decode {

    private final ByteBuffer buf;

    /**
     * initialize the decoder with byte buffer in ASCII85 format
     */
    private ASCII85Decode(final ByteBuffer buf) {
        this.buf = buf;
    }

    /**
     * get the next character from the input.
     * @return the next character, or -1 if at end of stream
     */
    private int nextChar() {
        // skip whitespace
        // returns next character, or -1 if end of stream
        while (this.buf.remaining() > 0) {
            final char c = (char) this.buf.get();

            if (!PDFFile.isWhiteSpace(c)) {
                return c;
            }
        }

        // EOF reached
        return -1;
    }

    /**
     * decode the next five ASCII85 characters into up to four decoded
     * bytes.  Return false when finished, or true otherwise.
     *
     * @param baos the ByteArrayOutputStream to write output to, set to the
     *        correct position
     * @return false when finished, or true otherwise.
     */
    private boolean decode5(final ByteArrayOutputStream baos)
            throws PDFParseException {
        // stream ends in ~>
        final int[] five = new int[5];
        int i;
        for (i = 0; i < 5; i++) {
            five[i] = nextChar();
            if (five[i] == '~') {
                if (nextChar() == '>') {
                    break;
                } else {
                    throw new PDFParseException("Bad character in ASCII85Decode: not ~>");
                }
            } else if (five[i] >= '!' && five[i] <= 'u') {
                five[i] -= '!';
            } else if (five[i] == 'z') {
                if (i == 0) {
                    five[i] = 0;
                    i = 4;
                } else {
                    throw new PDFParseException("Inappropriate 'z' in ASCII85Decode");
                }
            } else {
                throw new PDFParseException("Bad character in ASCII85Decode: " + five[i] + " (" + (char) five[i] + ")");
            }
        }

        if (i > 0) {
            i -= 1;
        }

        final int value =
                five[0] * 85 * 85 * 85 * 85 +
                five[1] * 85 * 85 * 85 +
                five[2] * 85 * 85 +
                five[3] * 85 +
                five[4];

        for (int j = 0; j < i; j++) {
            final int shift = 8 * (3 - j);
            baos.write((byte) ((value >> shift) & 0xff));
        }

        return (i == 4);
    }

    /**
     * decode the bytes
     * @return the decoded bytes
     */
    private ByteBuffer decode() throws PDFParseException {
        // start from the beginning of the data
        this.buf.rewind();

        // allocate the output buffer
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // decode the bytes 
        while (decode5(baos)) {
        }

        return ByteBuffer.wrap(baos.toByteArray());
    }

    /**
     * decode an array of bytes in ASCII85 format.
     * <p>
     * In ASCII85 format, every 5 characters represents 4 decoded
     * bytes in base 85.  The entire stream can contain whitespace,
     * and ends in the characters '~&gt;'.
     *
     * @param buf the encoded ASCII85 characters in a byte buffer
     * @param params parameters to the decoder (ignored)
     * @return the decoded bytes
     * @throws org.loboevolution.pdfview.PDFParseException if any.
     */
    public static ByteBuffer decode(final ByteBuffer buf, final PDFObject params)
            throws PDFParseException {
        final ASCII85Decode me = new ASCII85Decode(buf);
        return me.decode();
    }
}
