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
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.pdfview.PDFDebugger;

/**
 * <p>TrueTypeFont class.</p>
 *
 * Author  jkaplan
  *
 */
@Slf4j
public class TrueTypeFont {
    private final int type;
    // could be a ByteBuffer or a TrueTypeTable

    private final SortedMap<String, Object> tables;

    /**
     * Creates a new instance of TrueTypeParser
     *
     * @param type a int.
     */
    public TrueTypeFont(final int type) {
        this.type = type;

        this.tables = Collections.synchronizedSortedMap(
                new TreeMap<>());
    }

    /**
     * Parses a TrueType font from a byte array
     *
     * @param orig an array of {@link byte} objects.
     * @return a {@link org.loboevolution.pdfview.font.ttf.TrueTypeFont} object.
     */
    public static TrueTypeFont parseFont (final byte[] orig) {
        final ByteBuffer inBuf = ByteBuffer.wrap (orig);
        return parseFont (inBuf);
    }

    /**
     * Parses a TrueType font from a byte buffer
     *
     * @param inBuf a {@link java.nio.ByteBuffer} object.
     * @return a {@link org.loboevolution.pdfview.font.ttf.TrueTypeFont} object.
     */
    public static TrueTypeFont parseFont(final ByteBuffer inBuf) {
        final int type = inBuf.getInt();
        final short numTables = inBuf.getShort();
        @SuppressWarnings("unused") final short searchRange = inBuf.getShort();
        @SuppressWarnings("unused") final short entrySelector = inBuf.getShort();
        @SuppressWarnings("unused") final short rangeShift = inBuf.getShort();

        final TrueTypeFont font = new TrueTypeFont(type);
        parseDirectories(inBuf, numTables, font);

        return font;
    }

    /**
     * Get the type of this font
     *
     * @return a int.
     */
    public int getType () {
        return this.type;
    }

    /**
     * Add a table to the font
     *
     * @param tagString the name of this table, as a 4 character string
     *        (i.e. cmap or head)
     * @param data the data for this table, as a byte buffer
     */
    public void addTable (final String tagString, final ByteBuffer data) {
        this.tables.put (tagString, data);
    }

    /**
     * Add a table to the font
     *
     * @param tagString the name of this table, as a 4 character string
     *        (i.e. cmap or head)
     * @param table the table
     */
    public void addTable (final String tagString, final TrueTypeTable table) {
        this.tables.put (tagString, table);
    }

    /**
     * Get a table by name.  This command causes the table in question
     * to be parsed, if it has not already been parsed.
     *
     * @param tagString the name of this table, as a 4 character string
     *        (i.e. cmap or head)
     * @return a {@link org.loboevolution.pdfview.font.ttf.TrueTypeTable} object.
     */
    public TrueTypeTable getTable (final String tagString) {
        final Object tableObj = this.tables.get (tagString);

        TrueTypeTable table = null;

        if (tableObj instanceof ByteBuffer) {
            // the table has not yet been parsed.  Parse it, and add the
            // parsed version to the map of tables.
            final ByteBuffer data = (ByteBuffer) tableObj;

            table = TrueTypeTable.createTable (this, tagString, data);
            addTable (tagString, table);
        } else {
            table = (TrueTypeTable) tableObj;
        }

        return table;
    }

    /**
     * Remove a table by name
     *
     * @param tagString the name of this table, as a 4 character string
     *        (i.e. cmap or head)
     */
    public void removeTable (final String tagString) {
        this.tables.remove (tagString);
    }

    /**
     * Get the number of tables
     *
     * @return a short.
     */
    public short getNumTables () {
        return (short) this.tables.size ();
    }

    /**
     * Get the search range
     *
     * @return a short.
     */
    public short getSearchRange () {
        final double pow2 = Math.floor (Math.log (getNumTables ()) / Math.log (2));
        final double maxPower = Math.pow (2, pow2);

        return (short) (16 * maxPower);
    }

    /**
     * Get the entry selector
     *
     * @return a short.
     */
    public short getEntrySelector () {
        final double pow2 = Math.floor (Math.log (getNumTables ()) / Math.log (2));
        final double maxPower = Math.pow (2, pow2);

        return (short) (Math.log (maxPower) / Math.log (2));
    }

    /**
     * Get the range shift
     *
     * @return a short.
     */
    public short getRangeShift () {
        final double pow2 = Math.floor (Math.log (getNumTables ()) / Math.log (2));
        final double maxPower = Math.pow (2, pow2);

        return (short) ((maxPower * 16) - getSearchRange ());
    }

    /**
     * Write a font given the type and an array of Table Directory Entries
     *
     * @return an array of {@link byte} objects.
     */
    public byte[] writeFont () {
        // allocate a buffer to hold the font
        final ByteBuffer buf = ByteBuffer.allocate (getLength ());
        
        // write the font header
        buf.putInt (getType ());
        buf.putShort (getNumTables ());
        buf.putShort (getSearchRange ());
        buf.putShort (getEntrySelector ());
        buf.putShort (getRangeShift ());

        // first offset is the end of the table directory entries
        int curOffset = 12 + (getNumTables () * 16);

        // write the tables
        for (final Map.Entry<String,Object> entry : this.tables.entrySet()) {
            final String tagString = entry.getKey();
            final int tag = TrueTypeTable.stringToTag(tagString);

            ByteBuffer data = null;

            final Object tableObj = this.tables.get(tagString);
            if (tableObj instanceof TrueTypeTable) {
                data = ((TrueTypeTable) tableObj).getData();
            } else {
                data = (ByteBuffer) tableObj;
            }

            final int dataLen = data.remaining();

            // write the table directory entry
            buf.putInt(tag);
            buf.putInt(calculateChecksum(tagString, data));
            buf.putInt(curOffset);
            buf.putInt(dataLen);

            // save the current position
            buf.mark();

            // move to the current offset and write the data
            buf.position(curOffset);
            buf.put(data);

            // reset the data start pointer
            data.flip();

            // return to the table directory entry
            buf.reset();

            // udate the offset
            curOffset += dataLen;

            // don't forget the padding
            while ((curOffset % 4) > 0) {
                curOffset++;
            }
        }

        buf.position (curOffset);
        buf.flip ();

        // adjust the checksum
        updateChecksumAdj (buf);

        return buf.array ();
    }

    /**
     * Calculate the checksum for a given table
     * 
     * @param tagString the name of the data
     * @param data the data in the table
     */
    private static int calculateChecksum (final String tagString, final ByteBuffer data) {
        int sum = 0;

        data.mark ();

        // special adjustment for head table: always treat the 4-bytes
        // starting at byte 8 as 0x0000. This the checkSumAdjustment so
        // must be ignored here (see the TTF spec)
        if (tagString.equals ("head")) {
        	if (!data.isReadOnly()) {
            	data.putInt (8, 0);
        	}
        	sum += data.getInt();
        	sum += data.getInt();
        	// consume the uncounted checkSumAdjustment int
        	data.getInt();
        }

        int nlongs = (data.remaining () + 3) / 4;

        while (nlongs-- > 0) {
            if (data.remaining () > 3) {
                sum += data.getInt ();
            } else {
                final byte b0 = (data.remaining () > 0) ? data.get () : 0;
                final byte b1 = (data.remaining () > 0) ? data.get () : 0;
                final byte b2 = (data.remaining () > 0) ? data.get () : 0;

                sum += ((0xff & b0) << 24) | ((0xff & b1) << 16) |
                        ((0xff & b2) << 8);
            }
        }

        data.reset ();

        return sum;
    }

    /**
     * Get directory entries from a font
     */
    private static void parseDirectories (final ByteBuffer data, final int numTables,
                                          final TrueTypeFont ttf) {
        for (int i = 0; i < numTables; i++) {
            final int tag = data.getInt ();
            final String tagString = TrueTypeTable.tagToString (tag);
            PDFDebugger.debug("TTFFont.parseDirectories: " + tagString, 100);
            final int checksum = data.getInt ();
            final int offset = data.getInt ();
            final int length = data.getInt ();

            // read the data
            PDFDebugger.debug("TTFFont.parseDirectories: checksum: " +
                    checksum + ", offset: " + offset + ", length: " + length, 100);
            data.mark ();
            data.position (offset);

            final ByteBuffer tableData = data.slice ();
            tableData.limit (length);

            final int calcChecksum = calculateChecksum (tagString, tableData);

            if (calcChecksum == checksum) {
                ttf.addTable (tagString, tableData);
            } else {
                PDFDebugger.debug("Mismatched checksums on table " + tagString + ": " + calcChecksum + " != " + checksum, 200);

                ttf.addTable (tagString, tableData);

            }
            data.reset ();
        }
    }

    /**
     * Get the length of the font
     *
     * @return the length of the entire font, in bytes
     */
    private int getLength () {
        // the size of all the table directory entries
        int length = 12 + (getNumTables () * 16);

        // for each directory entry, get the size,
        // and don't forget the padding!
        for (final Object tableObj : this.tables.values()) {
            // add the length of the entry
            if (tableObj instanceof TrueTypeTable) {
                length += ((TrueTypeTable) tableObj).getLength();
            } else {
                length += ((ByteBuffer) tableObj).remaining();
            }

            // pad
            if ((length % 4) != 0) {
                length += (4 - (length % 4));
            }
        }

        return length;
    }

    /**
     * Update the checksumAdj field in the head table
     */
    private void updateChecksumAdj (final ByteBuffer fontData) {
        final int checksum = calculateChecksum ("", fontData);
        final int checksumAdj = 0xb1b0afba - checksum;

        // find the head table
        int offset = 12 + (getNumTables () * 16);

        // find the head table
        for (final Map.Entry<String,Object> entry : this.tables.entrySet()) {
            final String tagString = entry.getKey();
            if (tagString.equals("head")) {
                fontData.putInt(offset + 8, checksumAdj);
                return;
            }

            // add the length of the entry 
            final Object tableObj = this.tables.get(tagString);
            if (tableObj instanceof TrueTypeTable) {
                offset += ((TrueTypeTable) tableObj).getLength();
            } else {
                offset += ((ByteBuffer) tableObj).remaining();
            }

            // pad
            if ((offset % 4) != 0) {
                offset += (4 - (offset % 4));
            }
        }
    }

	/**
	 * {@inheritDoc}
	 *
	 * Write the font to a pretty string
	 */
    @Override
	public String toString () {
        final StringBuilder buf = new StringBuilder ();

        log.info ("Type         : {} ", getType ());
        log.info ("NumTables    : {} ", getNumTables ());
        log.info ("SearchRange  : {} ", getSearchRange ());
        log.info ("EntrySelector: {} ", getEntrySelector ());
        log.info ("RangeShift   : {} ", getRangeShift ());

        for (final Map.Entry<String, Object> e : this.tables.entrySet()) {
            TrueTypeTable table = null;
            if (e.getValue() instanceof ByteBuffer) {
                table = getTable(e.getKey());
            } else {
                table = (TrueTypeTable) e.getValue();
            }

            log.info("table: {} ",table);
        }

        return buf.toString ();
    }

    /**
     * <p>getNames.</p>
     *
     * @return a {@link java.util.Collection} object.
     */
    public Collection<String> getNames() {
        final NameTable table = (NameTable) getTable("name");
        if (table != null) {
            return table.getNames();
        } else {
            return Collections.emptyList();
        }
    }
}
