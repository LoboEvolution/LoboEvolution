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

import java.nio.ByteBuffer;

/**
 * Model the TrueType Loca table
 */
public class LocaTable extends TrueTypeTable {
    /**
     * if true, the table stores glyphs in long format
     */
    private final boolean isLong;

    /**
     * the offsets themselves
     */
    private final int[] offsets;

    /**
     * Creates a new instance of HmtxTable
     *
     * @param ttf a {@link org.loboevolution.pdfview.font.ttf.TrueTypeFont} object.
     */
    protected LocaTable(final TrueTypeFont ttf) {
        super(TrueTypeTable.LOCA_TABLE);

        final MaxpTable maxp = (MaxpTable) ttf.getTable("maxp");
        final int numGlyphs = maxp.getNumGlyphs();

        final HeadTable head = (HeadTable) ttf.getTable("head");
        final short format = head.getIndexToLocFormat();
        this.isLong = (format == 1);

        this.offsets = new int[numGlyphs + 1];
    }

    /**
     * get the offset, in bytes, of a given glyph from the start of
     * the glyph table
     *
     * @param glyphID a {@link java.lang.Integer} object.
     * @return a {@link java.lang.Integer} object.
     */
    public int getOffset(final int glyphID) {
        return this.offsets[glyphID];
    }

    /**
     * get the size, in bytes, of the given glyph
     *
     * @param glyphID a {@link java.lang.Integer} object.
     * @return a {@link java.lang.Integer} object.
     */
    public int getSize(final int glyphID) {
        return this.offsets[glyphID + 1] - this.offsets[glyphID];
    }

    /**
     * Return true if the glyphs arte in long (int) format, or
     * false if they are in short (short) format
     *
     * @return a boolean.
     */
    public boolean isLongFormat() {
        return this.isLong;
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

        // write the offsets
        for (final int offset : this.offsets) {
            if (isLongFormat()) {
                buf.putInt(offset);
            } else {
                buf.putShort((short) (offset / 2));
            }
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
        for (int i = 0; i < this.offsets.length; i++) {
            if (isLongFormat()) {
                this.offsets[i] = data.getInt();
            } else {
                this.offsets[i] = 2 * (0xFFFF & data.getShort());
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the length of this table
     */
    @Override
    public int getLength() {
        if (isLongFormat()) {
            return this.offsets.length * 4;
        } else {
            return this.offsets.length * 2;
        }
    }
}
