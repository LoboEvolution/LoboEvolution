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
package org.jpedal.jbig2.pdf;

import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <p>PDFSegment class.</p>
 */
@Data
public class PDFSegment {

    private final ByteArrayOutputStream header = new ByteArrayOutputStream();
    private final ByteArrayOutputStream data = new ByteArrayOutputStream();
    private int segmentDataLength;

    /**
     * <p>writeToHeader.</p>
     *
     * @param bite a short.
     */
    public void writeToHeader(final short bite) {
        header.write(bite);
    }

    /**
     * <p>writeToHeader.</p>
     *
     * @param bites an array of {@link short} objects.
     * @throws java.io.IOException if any.
     */
    public void writeToHeader(final short[] bites) throws IOException {
        for (final short bite : bites) {
            header.write(bite);
        }
    }

    /**
     * <p>writeToData.</p>
     *
     * @param bite a short.
     */
    public void writeToData(final short bite) {
        data.write(bite);
    }
}
