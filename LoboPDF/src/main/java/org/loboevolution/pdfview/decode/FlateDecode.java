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

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * decode a deFlated byte array
 * <p>
 * Author Mike Wessler
 */
public class FlateDecode {

    /**
     * decode a byte buffer in Flate format.
     * <p>
     * Flate is a built-in Java algorithm.  It's part of the java.util.zip
     * package.
     *
     * @param buf    the deflated input buffer
     * @param params parameters to the decoder (unused)
     * @param dict   a {@link org.loboevolution.pdfview.PDFObject} object.
     * @return the decoded (inflated) bytes
     * @throws java.io.IOException if any.
     */
    public static ByteBuffer decode(final PDFObject dict, final ByteBuffer buf,
                                    final PDFObject params) throws IOException {
        final Inflater inf = new Inflater(false);

        final int bufSize = buf.remaining();

        // copy the data, since the array() method is not supported
        // on raf-based ByteBuffers
        final byte[] data = new byte[bufSize];
        buf.get(data);

        // set the input to the inflater
        inf.setInput(data);

        // output to a byte-array output stream, since we don't
        // know how big the output will be
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final byte[] decomp = new byte[bufSize];
        int read = 0;

        try {
            while (!inf.finished()) {
                read = inf.inflate(decomp);
                if (read <= 0) {
                    if (inf.needsDictionary()) {
                        throw new PDFParseException("Don't know how to ask for a dictionary in FlateDecode");
                    } else {
                        // just return the data which is already read
                        break;
                    }
                }
                baos.write(decomp, 0, read);
            }
        } catch (final DataFormatException dfe) {
            throw new PDFParseException("Data format exception:" + dfe.getMessage());
        }

        // return the output as a byte buffer
        ByteBuffer outBytes = ByteBuffer.wrap(baos.toByteArray());

        // undo a predictor algorithm, if any was used
        if (params != null && params.getDictionary().containsKey("Predictor")) {
            final Predictor predictor = Predictor.getPredictor(params);
            if (predictor != null) {
                outBytes = predictor.unpredict(outBytes);
            }
        }

        return outBytes;
    }
}
