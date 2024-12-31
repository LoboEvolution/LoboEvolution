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

package org.loboevolution.pdfview.font.ttf;

import lombok.Data;

import java.nio.ByteBuffer;

/**
 * A single glyph in a pdf font.  May be simple or compound via subclasses
 */
@Data
public class Glyf {
    /**
     * If true, the glyf is compound
     */
    private boolean compound;

    /**
     * the number of contours
     */
    private short numContours;

    /**
     * the minimum x value
     */
    private short minX;

    /**
     * the minimum y value
     */
    private short minY;

    /**
     * the maximum x value
     */
    private short maxX;

    /**
     * the maximum y value
     */
    private short maxY;

    /**
     * Creates a new instance of glyf
     * Don't use this directly, use <code>Glyf.getGlyf()</code>
     */
    protected Glyf() {
    }

    /**
     * Get a map from the given data
     * <p>
     * This method reads the format, data and length variables of
     * the map.
     *
     * @param data a {@link java.nio.ByteBuffer} object.
     * @return a {@link org.loboevolution.pdfview.font.ttf.Glyf} object.
     */
    public static Glyf getGlyf(final ByteBuffer data) {
        final short numContours = data.getShort();

        Glyf g;
        if (numContours == 0) {
            // no glyph data
            g = new Glyf();
        } else if (numContours == -1) {
            // compound glyf
            g = new GlyfCompound();
        } else if (numContours > 0) {
            // simple glyf
            g = new GlyfSimple();
        } else {
            throw new IllegalArgumentException("Unknown glyf type: " +
                    numContours);
        }

        g.setNumContours(numContours);
        g.setMinX(data.getShort());
        g.setMinY(data.getShort());
        g.setMaxX(data.getShort());
        g.setMaxY(data.getShort());

        // do glyphtype-specific parsing
        g.setData(data);

        return g;
    }

    /**
     * Get the data in this glyf as a byte buffer.  Return the basic
     * glyf data only, since there is no specific data.  This method returns
     * the data un-flipped, so subclasses can simply append to the allocated
     * buffer.
     *
     * @return a {@link java.nio.ByteBuffer} object.
     */
    public ByteBuffer getData() {
        final ByteBuffer buf = ByteBuffer.allocate(getLength());

        buf.putShort(getNumContours());
        buf.putShort(getMinX());
        buf.putShort(getMinY());
        buf.putShort(getMaxX());
        buf.putShort(getMaxY());

        // don't flip the buffer, since it may be used by subclasses
        return buf;
    }

    /**
     * Set the data for this glyf.  Do nothing, since a glyf with
     * no contours has no glyf data.
     *
     * @param data a {@link java.nio.ByteBuffer} object.
     */
    public void setData(final ByteBuffer data) {
        //No data
    }

    /**
     * Get the length of this glyf.  A glyf with no data has a length
     * of 10 (2 bytes each for 5 short values)
     *
     * @return a short.
     */
    public short getLength() {
        return 10;
    }
}
