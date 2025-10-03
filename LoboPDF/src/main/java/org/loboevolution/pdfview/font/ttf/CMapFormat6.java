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
import java.util.HashMap;
import java.util.Map;

/**
 * <p>CMapFormat6 class.</p>
 * <p>
 * Author  jkaplan
 */
public class CMapFormat6 extends CMap {
    /**
     * a reverse lookup from glyph id to index.
     */
    private final Map<Short, Short> glyphLookup = new HashMap<>();
    /**
     * First character code of subrange.
     */
    private short firstCode;
    /**
     * Number of character codes in subrange.
     */
    private short entryCount;
    /**
     * Array of glyph index values for character codes in the range.
     */
    private short[] glyphIndexArray;

    /**
     * Creates a new instance of CMapFormat0
     *
     * @param language a short.
     */
    protected CMapFormat6(final short language) {
        super((short) 6, language);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the length of this table
     */
    @Override
    public short getLength() {
        // start with the size of the fixed header
        short size = 5 * 2;

        // add the size of each segment header
        size = (short) (size + (this.entryCount * 2));
        return size;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Cannot map from a byte
     */
    @Override
    public byte map(final byte src) {
        final char c = map((char) src);
        if (c > Byte.MAX_VALUE) {
            return 0;
        }
        return (byte) c;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Map from char
     */
    @Override
    public char map(final char src) {

        // find first segment with endcode > src
        if (src < this.firstCode || src > (this.firstCode + this.entryCount)) {
            // Codes outside of the range are assumed to be missing and are
            // mapped to the glyph with index 0
            return '\000';
        }
        return (char) this.glyphIndexArray[src - this.firstCode];
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the src code which maps to the given glyphID
     */
    @Override
    public char reverseMap(final short glyphID) {
        final Short result = this.glyphLookup.get(glyphID);
        if (result == null) {
            return '\000';
        }
        return (char) result.shortValue();
    }


    /**
     * {@inheritDoc}
     * <p>
     * Get the data in this map as a ByteBuffer
     */
    @Override
    public void setData(final int length, final ByteBuffer data) {
        // read the table size values
        this.firstCode = data.getShort();
        this.entryCount = data.getShort();

        this.glyphIndexArray = new short[this.entryCount];
        for (int i = 0; i < this.glyphIndexArray.length; i++) {
            this.glyphIndexArray[i] = data.getShort();
            this.glyphLookup.put(this.glyphIndexArray[i],
                    (short) (i + this.firstCode));
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the data in the map as a byte buffer
     */
    @Override
    public ByteBuffer getData() {
        final ByteBuffer buf = ByteBuffer.allocate(getLength());

        // write the header
        buf.putShort(getFormat());
        buf.putShort(getLength());
        buf.putShort(getLanguage());

        // write the various values
        buf.putShort(this.firstCode);
        buf.putShort(this.entryCount);

        // write the endCodes
        for (final short value : this.glyphIndexArray) {
            buf.putShort(value);
        }
        // reset the data pointer
        buf.flip();

        return buf;
    }
}
