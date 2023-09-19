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

import org.loboevolution.pdfview.PDFFile;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/**
 * decode an array of hex nybbles into a byte array
 *
 * Author Mike Wessler
  *
 */
public class ASCIIHexDecode {
    private final ByteBuffer buf;
    
    /**
     * initialize the decoder with an array of bytes in ASCIIHex format
     */
    private ASCIIHexDecode(ByteBuffer buf) {
	this.buf = buf;
    }

    /**
     * get the next character from the input
     * @return a number from 0-15, or -1 for the end character
     */
    private int readHexDigit() throws PDFParseException {    
        // read until we hit a non-whitespace character or the
        // end of the stream
        while (this.buf.remaining() > 0) {
            int c = this.buf.get();
        
            // see if we found a useful character
            if (!PDFFile.isWhiteSpace((char) c)) {
                if (c >= '0' && c <= '9') {
                    c -= '0';
                } else if (c >= 'a' && c <= 'f') {
                    c -= 'a' - 10;
                } else if (c >= 'A' && c <= 'F') {
                    c -= 'A' - 10;
                } else if (c == '>') {
                    c = -1;
                } else {
                    // unknown character
                    throw new PDFParseException("Bad character " + c + 
                                                "in ASCIIHex decode");
                }
                
                // return the useful character
                return c;
            }
        }
        
        // end of stream reached
	throw new PDFParseException("Short stream in ASCIIHex decode");
    }

    /**
     * decode the array
     * @return the decoded bytes
     */
    private ByteBuffer decode() throws PDFParseException {
        // start at the beginning of the buffer
        buf.rewind();

        // allocate the output buffer
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        while (true) {
            int first = readHexDigit();
            if (first == -1) {
                break;
            }
            int second = readHexDigit();

            if (second == -1) {
                baos.write((byte) (first << 4));
                break;
            } else {
                baos.write((byte) ((first << 4) + second));
            }
        }

        return ByteBuffer.wrap(baos.toByteArray());
    }

    /**
     * decode an array of bytes in ASCIIHex format.
     * <p>
     * ASCIIHex format consists of a sequence of Hexidecimal
     * digits, with possible whitespace, ending with the
     * '&gt;' character.
     *
     * @param buf the encoded ASCII85 characters in a byte
     *        buffer
     * @param params parameters to the decoder (ignored)
     * @return the decoded bytes
     * @throws org.loboevolution.pdfview.PDFParseException if any.
     */
    public static ByteBuffer decode(ByteBuffer buf, PDFObject params)
	throws PDFParseException 
    {
	ASCIIHexDecode me = new ASCIIHexDecode(buf);
	return me.decode();
    }
}
