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

package org.loboevolution.pdfview.decode;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Undo prediction based on the TIFF Predictor 2 algorithm
 */
public class TIFFPredictor extends Predictor {

    /**
     * <p>Constructor for TIFFPredictor.</p>
     */
    public TIFFPredictor() {
        super(TIFF);
    }

    private static byte getbits(final byte[] data, final int bitIndex, final int shiftWhenByteAligned, final int mask) {
        final int b = data[(bitIndex >> 3)];
        final int bitIndexInB = bitIndex & 7;
        final int shift = shiftWhenByteAligned - bitIndexInB;
        return (byte) ((b >>> shift) & mask);
    }

    private static void setbits(final byte[] data, final int bitIndex, final int shiftWhenByteAligned, final int mask, final byte bits) {
        final int b = data[(bitIndex >> 3)];
        final int bitIndexInB = bitIndex & 7;
        final int shift = shiftWhenByteAligned - bitIndexInB;
        data[bitIndex >> 3] = (byte) ((b & ~(mask << shift)) | (bits << shift));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Undo data based on the png algorithm
     */
    public ByteBuffer unpredict(final ByteBuffer imageData)
            throws IOException {
        final ByteBuffer out = ByteBuffer.allocate(imageData.limit());

        final int numComponents = getColors();
        final int pixelBits = numComponents * getBitsPerComponent();

        final int bytePerRow = (getColumns() * pixelBits + 7) / 8;

        final byte[] row = new byte[bytePerRow];

        while (imageData.remaining() > 0) {
            imageData.get(row);
            if (getBitsPerComponent() == 8) {
                for (int i = numComponents; i < row.length; i += numComponents) {
                    for (int c = 0; c < numComponents; ++c) {
                        final int pos = i + c;
                        row[pos] += row[pos - numComponents];
                    }
                }
            } else if (getBitsPerComponent() == 16) {
                final short[] prev = new short[numComponents];
                for (int c = 0; c < numComponents; c += 1) {
                    final int pos = c * 2;
                    prev[c] = (short) ((row[pos] << 8 | (row[pos + 1]) & 0xFFFF));
                }
                for (int i = numComponents * 2; i < row.length; i += numComponents * 2) {
                    for (int c = 0; c < numComponents; c += 1) {
                        final int pos = i + c * 2;
                        short cur = (short) ((row[pos] << 8 | (row[pos + 1]) & 0xFFFF));
                        cur += prev[c];
                        row[pos] = (byte) (cur >>> 8 & 0xFF);
                        row[pos + 1] = (byte) (cur & 0xFF);
                        prev[c] = cur;
                    }
                }
            } else {
                assert getBitsPerComponent() == 1 || getBitsPerComponent() == 2 || getBitsPerComponent() == 4 : "we don't want to grab components across pixel boundaries";
                final int bitsOnRow = pixelBits * getColumns(); // may be less than bytesOnRow * 8
                final byte[] prev = new byte[numComponents];
                final int shiftWhenAligned = 8 - getBitsPerComponent();
                final int mask = (1 << getBitsPerComponent()) - 1;
                for (int c = 0; c < numComponents; ++c) {
                    prev[c] = getbits(row, c * getBitsPerComponent(), shiftWhenAligned, mask);
                }
                for (int i = pixelBits; i < bitsOnRow; i += pixelBits) {
                    for (int c = 0; c < numComponents; ++c) {
                        byte cur = getbits(row, i + c * getBitsPerComponent(), shiftWhenAligned, mask);
                        cur += prev[c];
                        prev[c] = cur;
                        setbits(row, i + c * getBitsPerComponent(), shiftWhenAligned, mask, cur);
                    }
                }
            }
            out.put(row);
        }


        // reset start pointer
        out.flip();

        // return
        return out;

    }


}
