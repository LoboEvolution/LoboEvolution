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
import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>CMapFormat4 class.</p>
 * <p>
 * Author  jkaplan
 */
public class CMapFormat4 extends CMap {

    /**
     * The segments and associated data can be a char[] or an Integer
     */
    public final SortedMap<Segment, Object> segments;

    /**
     * Creates a new instance of CMapFormat0
     *
     * @param language a short.
     */
    protected CMapFormat4(final short language) {
        super((short) 4, language);

        this.segments = Collections.synchronizedSortedMap(new TreeMap<>());

        final char[] map = new char[1];
        map[0] = (char) 0;
        addSegment((short) 0xffff, (short) 0xffff, map);
    }

    /**
     * Add a segment with a map
     *
     * @param startCode a short.
     * @param endCode   a short.
     * @param map       an array of {@link char} objects.
     */
    public void addSegment(final short startCode, final short endCode, final char[] map) {
        if (map.length != (endCode - startCode) + 1) {
            throw new IllegalArgumentException("Wrong number of entries in map");
        }

        final Segment s = new Segment(startCode, endCode, true);
        // make sure we remove any old entries
        this.segments.remove(s);
        this.segments.put(s, map);
    }

    /**
     * Add a segment with an idDelta
     *
     * @param startCode a short.
     * @param endCode   a short.
     * @param idDelta   a short.
     */
    public void addSegment(final short startCode, final short endCode, final short idDelta) {
        final Segment s = new Segment(startCode, endCode, false);
        // make sure we remove any old entries
        this.segments.remove(s);
        this.segments.put(s, (int) idDelta);
    }

    /**
     * Remove a segment
     *
     * @param startCode a short.
     * @param endCode   a short.
     */
    public void removeSegment(final short startCode, final short endCode) {
        final Segment s = new Segment(startCode, endCode, true);
        this.segments.remove(s);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the length of this table
     */
    @Override
    public short getLength() {
        // start with the size of the fixed header
        short size = 16;

        // add the size of each segment header
        size = (short) (size + this.segments.size() * 8);

        // add the total number of mappings times the size of a mapping
        for (final Map.Entry<Segment, Object> entry : this.segments.entrySet()) {
            final Segment s = entry.getKey();
            // see if there's a map
            if (s.hasMap) {
                // if there is, add its size
                final char[] map = (char[]) this.segments.get(s);
                size = (short) (size + map.length * 2);
            }
        }

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
        if (c < Byte.MIN_VALUE || c > Byte.MAX_VALUE) {
            // out of range
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
        for (final Map.Entry<Segment, Object> entry : this.segments.entrySet()) {
            final Segment s = entry.getKey();
            if (s.endCode >= src) {
                // are we within range?
                if (s.startCode <= src) {
                    if (s.hasMap) {
                        // return the index of this character in 
                        // the segment's map
                        final char[] map = (char[]) this.segments.get(s);
                        return map[src - s.startCode];
                    } else {
                        // return the character code + idDelta
                        final Integer idDelta = (Integer) this.segments.get(s);
                        return (char) (src + idDelta);
                    }
                } else {
                    // undefined character
                    return (char) 0;
                }
            }
        }

        // shouldn't get here!
        return (char) 0;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the src code which maps to the given glyphID
     */
    @Override
    public char reverseMap(final short glyphID) {
        // look at each segment
        for (final Map.Entry<Segment, Object> entry : this.segments.entrySet()) {
            final Segment s = entry.getKey();
            // see if we have a map or a delta
            if (s.hasMap) {
                final char[] map = (char[]) this.segments.get(s);

                // if we have a map, we have to iterate through it
                for (final char c : map) {
                    if (map[c] == glyphID) {
                        return (char) (s.startCode + c);
                    }
                }
            } else {
                final Integer idDelta = (Integer) this.segments.get(s);

                // we can do the math to see if we're in range
                final int start = s.startCode + idDelta;
                final int end = s.endCode + idDelta;

                if (glyphID >= start && glyphID <= end) {
                    // we're in the range
                    return (char) (glyphID - idDelta);
                }
            }
        }

        // not found!
        return (char) 0;
    }


    /**
     * {@inheritDoc}
     * <p>
     * Get the data in this map as a ByteBuffer
     */
    @Override
    public void setData(final int length, final ByteBuffer data) {
        // read the table size values
        final short segCount = (short) (data.getShort() / 2);
        // create arrays to store segment info
        final short[] endCodes = new short[segCount];
        final short[] startCodes = new short[segCount];
        final short[] idDeltas = new short[segCount];
        final short[] idRangeOffsets = new short[segCount];

        // the start of the glyph array
        final int glyphArrayPos = 16 + (8 * segCount);

        // read the endCodes
        for (int i = 0; i < segCount; i++) {
            endCodes[i] = data.getShort();
        }

        // read the pad
        data.getShort();

        // read the start codes
        for (int i = 0; i < segCount; i++) {
            startCodes[i] = data.getShort();
        }

        // read the idDeltas
        for (int i = 0; i < segCount; i++) {
            idDeltas[i] = data.getShort();
        }

        // read the id range offsets
        for (int i = 0; i < segCount; i++) {
            idRangeOffsets[i] = data.getShort();

            // calculate the actual offset
            if (idRangeOffsets[i] <= 0) {
                // the easy way
                addSegment(startCodes[i], endCodes[i], idDeltas[i]);
            } else {
                // find the start of the data segment
                final int offset = (data.position() - 2) + idRangeOffsets[i];

                // get the number of entries in the map
                final int size = (endCodes[i] - startCodes[i]) + 1;

                // allocate the actual map
                final char[] map = new char[size];

                // remember our offset
                data.mark();

                // read the mappings    
                for (int c = 0; c < size; c++) {
                    data.position(offset + (c * 2));
                    map[c] = data.getChar();
                }

                // reset the position
                data.reset();

                addSegment(startCodes[i], endCodes[i], map);
            }
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
        buf.putShort((short) (getSegmentCount() * 2));
        buf.putShort(getSearchRange());
        buf.putShort(getEntrySelector());
        buf.putShort(getRangeShift());

        // write the endCodes
        for (final Map.Entry<Segment, Object> entry : this.segments.entrySet()) {
            final Segment s = entry.getKey();
            buf.putShort((short) s.endCode);
        }

        // write the pad
        buf.putShort((short) 0);

        // write the startCodes
        for (final Map.Entry<Segment, Object> entry : this.segments.entrySet()) {
            final Segment s = entry.getKey();
            buf.putShort((short) s.startCode);
        }

        // write the idDeltas for segments using deltas
        for (final Map.Entry<Segment, Object> entry : this.segments.entrySet()) {
            final Segment s = entry.getKey();
            if (!s.hasMap) {
                final Integer idDelta = (Integer) this.segments.get(s);
                buf.putShort(idDelta.shortValue());
            } else {
                buf.putShort((short) 0);
            }
        }

        // the start of the glyph array
        int glyphArrayOffset = 16 + (8 * getSegmentCount());

        // write the idRangeOffsets and maps for segments using maps
        for (final Map.Entry<Segment, Object> entry : this.segments.entrySet()) {
            final Segment s = entry.getKey();
            if (s.hasMap) {
                // first set the offset, which is the number of bytes from the
                // current position to the current offset
                buf.putShort((short) (glyphArrayOffset - buf.position()));

                // remember the current position
                buf.mark();

                // move the position to the offset
                buf.position(glyphArrayOffset);

                // now write the map
                final char[] map = (char[]) this.segments.get(s);
                for (final char value : map) {
                    buf.putChar(value);
                }

                // reset the data pointer
                buf.reset();

                // update the offset
                glyphArrayOffset += map.length * 2;
            } else {
                buf.putShort((short) 0);
            }
        }

        // make sure we are at the end of the buffer before we flip
        buf.position(glyphArrayOffset);

        // reset the data pointer
        buf.flip();

        return buf;
    }

    /**
     * Get the segment count
     *
     * @return a short.
     */
    public short getSegmentCount() {
        return (short) this.segments.size();
    }

    /**
     * Get the search range
     *
     * @return a short.
     */
    public short getSearchRange() {
        final double pow = Math.floor(Math.log(getSegmentCount()) / Math.log(2));
        final double pow2 = Math.pow(2, pow);

        return (short) (2 * pow2);
    }

    /**
     * Get the entry selector
     *
     * @return a short.
     */
    public short getEntrySelector() {
        final int sr2 = getSearchRange() / 2;
        return (short) (Math.log(sr2) / Math.log(2));
    }

    /**
     * Get the rangeShift()
     *
     * @return a short.
     */
    public short getRangeShift() {
        return (short) ((2 * getSegmentCount()) - getSearchRange());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get a pretty string
     */
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        final String indent = "        ";

        buf.append(super.toString());
        buf.append(indent).append("SegmentCount : ").append(getSegmentCount()).append("\n");
        buf.append(indent).append("SearchRange  : ").append(getSearchRange()).append("\n");
        buf.append(indent).append("EntrySelector: ").append(getEntrySelector()).append("\n");
        buf.append(indent).append("RangeShift   : ").append(getRangeShift()).append("\n");

        for (final Map.Entry<Segment, Object> entry : this.segments.entrySet()) {
            final Segment s = entry.getKey();
            buf.append(indent);
            buf.append("Segment: ").append(Integer.toHexString(s.startCode));
            buf.append("-").append(Integer.toHexString(s.endCode)).append(" ");
            buf.append("hasMap: ").append(s.hasMap).append(" ");

            if (!s.hasMap) {
                buf.append("delta: ").append(this.segments.get(s));
            }

            buf.append("\n");
        }

        return buf.toString();
    }

    static class Segment implements Comparable {
        /**
         * the end code (highest code in this segment)
         */
        final int endCode;

        /**
         * the start code (lowest code in this segment)
         */
        final int startCode;

        /**
         * whether it is a map or a delta
         */
        final boolean hasMap;

        /**
         * Create a new segment
         */
        public Segment(final short startCode, final short endCode, final boolean hasMap) {
            // convert from unsigned short
            this.endCode = (0xffff & endCode);
            this.startCode = (0xffff & startCode);

            this.hasMap = hasMap;
        }

        /**
         * Equals based on compareTo (only compares endCode)
         */
        @Override
        public boolean equals(final Object o) {
            return (compareTo(o) == 0);
        }

        /**
         * Segments sort by increasing endCode
         */
        @Override
        public int compareTo(final Object o) {
            if (!(o instanceof Segment)) {
                return -1;
            }

            final Segment s = (Segment) o;

            // if regions overlap at all, declare the segments equal,
            // to avoid overlap in the segment list
            if (((s.endCode >= this.startCode) && (s.endCode <= this.endCode)) ||
                    ((s.startCode >= this.startCode) && (s.startCode <= this.endCode))) {
                return 0;
            }
            return Integer.compare(this.endCode, s.endCode);
        }
    }
}
