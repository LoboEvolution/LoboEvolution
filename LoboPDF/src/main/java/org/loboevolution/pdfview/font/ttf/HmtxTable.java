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

package org.loboevolution.pdfview.font.ttf;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Model the TrueType Post table
 * <p>
 * Author  jkaplan
 */
public class HmtxTable extends TrueTypeTable {

    /**
     * advance widths for any glyphs that have one
     */
    final short[] advanceWidths;

    /**
     * left side bearings for each glyph
     */
    final short[] leftSideBearings;

    /**
     * Creates a new instance of HmtxTable
     *
     * @param ttf a {@link org.loboevolution.pdfview.font.ttf.TrueTypeFont} object.
     */
    protected HmtxTable(final TrueTypeFont ttf) {
        super(TrueTypeTable.HMTX_TABLE);

        // the number of glyphs stored in the maxp table may be incorrect
        // in the case of subsetted fonts produced by some pdf generators
        final MaxpTable maxp = (MaxpTable) ttf.getTable("maxp");
        final int numGlyphs = maxp.getNumGlyphs();

        final HheaTable hhea = (HheaTable) ttf.getTable("hhea");
        final int numOfLongHorMetrics = hhea.getNumOfLongHorMetrics();

        this.advanceWidths = new short[numOfLongHorMetrics];
        this.leftSideBearings = new short[numGlyphs];
    }

    /**
     * get the advance of a given glyph
     *
     * @param glyphID a int.
     * @return a short.
     */
    public short getAdvance(final int glyphID) {
        if (glyphID < this.advanceWidths.length) {
            return this.advanceWidths[glyphID];
        } else {
            return this.advanceWidths[this.advanceWidths.length - 1];
        }
    }

    /**
     * get the left side bearing of a given glyph
     *
     * @param glyphID a int.
     * @return a short.
     */
    public short getLeftSideBearing(final int glyphID) {
        return this.leftSideBearings[glyphID];
    }

    /**
     * {@inheritDoc}
     * <p>
     * get the data in this map as a ByteBuffer
     */
    @Override
    public ByteBuffer getData() {
        final int size = getLength();

        final ByteBuffer buf = ByteBuffer.allocate(size);

        // write the metrics
        for (int i = 0; i < this.leftSideBearings.length; i++) {
            if (i < this.advanceWidths.length) {
                buf.putShort(this.advanceWidths[i]);
            }

            buf.putShort(this.leftSideBearings[i]);
        }

        // reset the start pointer
        buf.flip();

        return buf;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Initialize this structure from a ByteBuffer
     */
    @Override
    public void setData(final ByteBuffer data) {
        // some PDF writers subset the font but don't update the number of glyphs in the maxp table,
        // this would appear to break the TTF spec.
        // A better solution might be to try and override the numGlyphs in the maxp table based
        // on the number of entries in the cmap table or by parsing the glyf table, but this
        // appears to be the only place that gets affected by the discrepancy... so far!...
        // so updating this allows it to work.
        int i;
        // only read as much data as is available
        for (i = 0; i < this.leftSideBearings.length && data.hasRemaining(); i++) {
            if (i < this.advanceWidths.length) {
                this.advanceWidths[i] = data.getShort();
            }

            this.leftSideBearings[i] = data.getShort();
        }
        // initialise the remaining advanceWidths and leftSideBearings to 0
        if (i < this.advanceWidths.length) {
            Arrays.fill(this.advanceWidths, i, this.advanceWidths.length - 1, (short) 0);
        }
        if (i < this.leftSideBearings.length) {
            Arrays.fill(this.leftSideBearings, i, this.leftSideBearings.length - 1, (short) 0);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the length of this table
     */
    @Override
    public int getLength() {
        return (this.advanceWidths.length * 2) + (this.leftSideBearings.length * 2);
    }
}
