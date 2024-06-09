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
package org.loboevolution.pdfview.colorspace;

import lombok.Getter;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFPaint;

import java.awt.*;
import java.io.IOException;

/**
 * A PDFColorSpace for an IndexedColor model
 * <p>
 * Author Mike Wessler
 */
@Getter
public class IndexedColor extends PDFColorSpace {

    /**
     * size of the color table
     */
    final int count;
    /**
     * r,g,and b components of the color table as a single array, for
     * Java's IndexColorModel
     */
    protected byte[] colorComponents;
    /**
     * the color table
     */
    Color[] table;
    /**
     * number of channels in the base Color Space (unused)
     */
    int nchannels;

    /**
     * create a new IndexColor PDFColorSpace based on another PDFColorSpace,
     * a count of colors, and a stream of values.  Every consecutive n bytes
     * of the stream is interpreted as a color in the base ColorSpace, where
     * n is the number of components in that color space.
     *
     * @param base   the color space in which the data is interpreted
     * @param cnt    the number of colors in the table
     * @param stream a stream of bytes.  The number of bytes must be count*n,
     *               where n is the number of components in the base colorspace.
     * @throws java.io.IOException if any.
     */
    public IndexedColor(final PDFColorSpace base, final int cnt, final PDFObject stream) throws IOException {
        super(null);
        int count = cnt;
        count++;
        this.count = count;
        final byte[] data = stream.getStream();
        this.nchannels = base.getNumComponents();
        final boolean offSized = (data.length / this.nchannels) < count;
        this.colorComponents = new byte[3 * count];
        this.table = new Color[count];
        final float[] comps = new float[this.nchannels];
        int loc = 0;
        int finalloc = 0;
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < comps.length; j++) {
                if (loc < data.length) {
                    comps[j] = ((data[loc++]) & 0xff) / 255f;
                } else {
                    comps[j] = 1.0f;
                }
            }
            this.table[i] = (Color) base.getPaint(comps).getPaint();
            this.colorComponents[finalloc++] = (byte) this.table[i].getRed();
            this.colorComponents[finalloc++] = (byte) this.table[i].getGreen();
            this.colorComponents[finalloc++] = (byte) this.table[i].getBlue();
        }
    }

    /**
     * create a new IndexColor PDFColorSpace based on a table of colors.
     *
     * @param table an array of colors
     * @throws java.io.IOException if any.
     */
    public IndexedColor(final Color[] table) throws IOException {
        super(null);

        this.count = table.length;
        this.table = table;

        this.colorComponents = new byte[3 * this.count];
        this.nchannels = 3;

        int loc = 0;

        for (int i = 0; i < this.count; i++) {
            this.colorComponents[loc++] = (byte) table[i].getRed();
            this.colorComponents[loc++] = (byte) table[i].getGreen();
            this.colorComponents[loc++] = (byte) table[i].getBlue();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * get the number of components of this colorspace (1)
     */
    @Override
    public int getNumComponents() {
        return 1;
    }

    /**
     * {@inheritDoc}
     * <p>
     * get the color represented by the index.
     */
    @Override
    public PDFPaint getPaint(final float[] components) {
        return PDFPaint.getPaint(this.table[(int) components[0]]);
    }
}
