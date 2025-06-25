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

package org.loboevolution.pdfview.decode;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Undo prediction based on the PNG algorithm.
 */
public class PNGPredictor extends Predictor {
    /**
     * Creates a new instance of PNGPredictor
     */
    public PNGPredictor() {
        super(PNG);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Undo data based on the png algorithm
     */
    @Override
    public ByteBuffer unpredict(final ByteBuffer imageData)
            throws IOException {
        final List<byte[]> rows = new ArrayList<>();

        byte[] curLine;
        byte[] prevLine = null;

        // get the number of bytes per row
        int rowSize = getColumns() * getColors() * getBitsPerComponent();
        rowSize = (int) Math.ceil(rowSize / 8.0);

        while (imageData.remaining() >= rowSize + 1) {
            // the first byte determines the algorithm
            final int algorithm = (imageData.get() & 0xff);

            // read the rest of the line
            curLine = new byte[rowSize];
            imageData.get(curLine);

            // use the algorithm, Luke
            switch (algorithm) {
                case 0:
                    // none
                    break;
                case 1:
                    doSubLine(curLine);
                    break;
                case 2:
                    doUpLine(curLine, prevLine);
                    break;
                case 3:
                    doAverageLine(curLine, prevLine);
                    break;
                case 4:
                    doPaethLine(curLine, prevLine);
                    break;
                default:
                    break;
            }

            rows.add(curLine);
            prevLine = curLine;
        }

        // turn into byte array
        final ByteBuffer outBuf = ByteBuffer.allocate(rows.size() * rowSize);
        for (final byte[] b : rows) {
            outBuf.put(b);
        }

        // reset start pointer
        outBuf.flip();

        // return
        return outBuf;

    }

    /**
     * Return the value of the Sub algorithm on the line (compare bytes to
     * the previous byte of the same color on this line).
     *
     * @param curLine an array of {@link byte} objects.
     */
    protected void doSubLine(final byte[] curLine) {
        // get the number of bytes per sample
        final int sub = (int) Math.ceil((getBitsPerComponent() * getColors()) / 8.0);

        for (int i = 0; i < curLine.length; i++) {
            final int prevIdx = i - sub;
            if (prevIdx >= 0) {
                curLine[i] += curLine[prevIdx];
            }
        }
    }

    /**
     * Return the value of the up algorithm on the line (compare bytes to
     * the same byte in the previous line)
     *
     * @param curLine  an array of {@link byte} objects.
     * @param prevLine an array of {@link byte} objects.
     */
    protected void doUpLine(final byte[] curLine, final byte[] prevLine) {
        if (prevLine == null) {
            // do nothing if this is the first line
            return;
        }

        for (int i = 0; i < curLine.length; i++) {
            curLine[i] += prevLine[i];
        }
    }

    /**
     * Return the value of the average algorithm on the line (compare
     * bytes to the average of the previous byte of the same color and
     * the same byte on the previous line)
     *
     * @param curLine  an array of {@link byte} objects.
     * @param prevLine an array of {@link byte} objects.
     */
    protected void doAverageLine(final byte[] curLine, final byte[] prevLine) {
        // get the number of bytes per sample
        final int sub = (int) Math.ceil((getBitsPerComponent() * getColors()) / 8.0);

        for (int i = 0; i < curLine.length; i++) {
            int raw = 0;
            int prior = 0;

            // get the last value of this color
            final int prevIdx = i - sub;
            if (prevIdx >= 0) {
                raw = curLine[prevIdx] & 0xff;
            }

            // get the value on the previous line
            if (prevLine != null) {
                prior = prevLine[i] & 0xff;
            }

            // add the average
            curLine[i] += (byte) Math.floor((raw + prior) / 2.0);
        }
    }

    /**
     * Return the value of the average algorithm on the line (compare
     * bytes to the average of the previous byte of the same color and
     * the same byte on the previous line)
     *
     * @param curLine  an array of {@link byte} objects.
     * @param prevLine an array of {@link byte} objects.
     */
    protected void doPaethLine(final byte[] curLine, final byte[] prevLine) {
        // get the number of bytes per sample
        final int sub = (int) Math.ceil((getBitsPerComponent() * getColors()) / 8.0);

        for (int i = 0; i < curLine.length; i++) {
            int left = 0;
            int up = 0;
            int upLeft = 0;

            // get the last value of this color
            final int prevIdx = i - sub;
            if (prevIdx >= 0) {
                left = curLine[prevIdx] & 0xff;
            }

            // get the value on the previous line
            if (prevLine != null) {
                up = prevLine[i] & 0xff;
            }

            if (prevIdx >= 0 && prevLine != null) {
                upLeft = prevLine[prevIdx] & 0xff;
            }

            // add the average
            curLine[i] += (byte) paeth(left, up, upLeft);
        }
    }

    /**
     * The paeth algorithm
     *
     * @param left   a {@link java.lang.Integer} object.
     * @param up     a {@link java.lang.Integer} object.
     * @param upLeft a {@link java.lang.Integer} object.
     * @return a {@link java.lang.Integer} object.
     */
    protected int paeth(final int left, final int up, final int upLeft) {
        final int p = left + up - upLeft;
        final int pa = Math.abs(p - left);
        final int pb = Math.abs(p - up);
        final int pc = Math.abs(p - upLeft);

        if ((pa <= pb) && (pa <= pc)) {
            return left;
        } else if (pb <= pc) {
            return up;
        } else {
            return upLeft;
        }
    }

}
