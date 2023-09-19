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
package org.loboevolution.pdfview.colorspace;

import java.awt.Color;
import java.io.IOException;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFPaint;

/**
 * A PDFColorSpace for an IndexedColor model
 *
 * Author Mike Wessler
  *
 */
public class IndexedColor extends PDFColorSpace {

    /**
     * r,g,and b components of the color table as a single array, for
     * Java's IndexColorModel */
    protected byte[] finalcolors;
    /** the color table */
    Color[] table;
    /** size of the color table */
    final int count;
    /** number of channels in the base Color Space (unused) */
    int nchannels = 1;

    /**
     * create a new IndexColor PDFColorSpace based on another PDFColorSpace,
     * a count of colors, and a stream of values.  Every consecutive n bytes
     * of the stream is interpreted as a color in the base ColorSpace, where
     * n is the number of components in that color space.
     *
     * @param base the color space in which the data is interpreted
     * @param count the number of colors in the table
     * @param stream a stream of bytes.  The number of bytes must be count*n,
     * where n is the number of components in the base colorspace.
     * @throws java.io.IOException if any.
     */
    public IndexedColor(PDFColorSpace base, int count, PDFObject stream) throws IOException {
        super(null);
        count++;
        this.count = count;
        byte[] data = stream.getStream();
        this.nchannels = base.getNumComponents();
        boolean offSized = (data.length / this.nchannels) < count;
        this.finalcolors = new byte[3 * count];
        this.table = new Color[count];
        float[] comps = new float[this.nchannels];
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
            this.finalcolors[finalloc++] = (byte) this.table[i].getRed();
            this.finalcolors[finalloc++] = (byte) this.table[i].getGreen();
            this.finalcolors[finalloc++] = (byte) this.table[i].getBlue();
        }
    }

    /**
     * create a new IndexColor PDFColorSpace based on a table of colors.
     *
     * @param table an array of colors
     * @throws java.io.IOException if any.
     */
    public IndexedColor(Color[] table) throws IOException {
        super(null);

        this.count = table.length;
        this.table = table;

        this.finalcolors = new byte[3 * this.count];
        this.nchannels = 3;

        int loc = 0;

        for (int i = 0; i < this.count; i++) {
            this.finalcolors[loc++] = (byte) table[i].getRed();
            this.finalcolors[loc++] = (byte) table[i].getGreen();
            this.finalcolors[loc++] = (byte) table[i].getBlue();
        }
    }

    /**
     * Get the number of indices
     *
     * @return a int.
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Get the table of color components
     *
     * @return an array of {@link byte} objects.
     */
    public byte[] getColorComponents() {
        return this.finalcolors;
    }

    /**
     * {@inheritDoc}
     *
     * get the number of components of this colorspace (1)
     */
    @Override
    public int getNumComponents() {
        return 1;
    }

    /**
     * {@inheritDoc}
     *
     * get the color represented by the index.
     */
    @Override
    public PDFPaint getPaint(float[] components) {
        return PDFPaint.getPaint(this.table[(int) components[0]]);
    }
}
