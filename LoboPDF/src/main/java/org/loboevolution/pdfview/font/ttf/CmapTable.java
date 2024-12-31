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

import lombok.Getter;
import lombok.Setter;
import org.loboevolution.pdfview.PDFDebugger;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * Represents the TTF "cmap" table
 * <p>
 * Author  jkaplan
 */
public class CmapTable extends TrueTypeTable {

    /**
     * Holds the CMap subtables, sorted properly
     */
    private final SortedMap<CmapSubtable, CMap> subtables;
    /**
     * Holds value of property version.
     */
    @Getter
    @Setter
    private short version;

    /**
     * Creates a new instance of CmapTable
     */
    protected CmapTable() {
        super(TrueTypeTable.CMAP_TABLE);

        setVersion((short) 0x0);

        this.subtables = Collections.synchronizedSortedMap(new TreeMap<>());
    }

    /**
     * Add a CMap
     *
     * @param platformID         a short.
     * @param platformSpecificID a short.
     * @param cMap               a {@link org.loboevolution.pdfview.font.ttf.CMap} object.
     */
    public void addCMap(final short platformID, final short platformSpecificID,
                        final CMap cMap) {
        final CmapSubtable key = new CmapSubtable(platformID, platformSpecificID);
        this.subtables.put(key, cMap);
    }

    /**
     * Get a CMap by platform and specific ID
     *
     * @param platformID         a short.
     * @param platformSpecificID a short.
     * @return a {@link org.loboevolution.pdfview.font.ttf.CMap} object.
     */
    public CMap getCMap(final short platformID, final short platformSpecificID) {
        final CmapSubtable key = new CmapSubtable(platformID, platformSpecificID);
        return this.subtables.get(key);
    }

    /**
     * Get all CMaps
     *
     * @return an array of {@link org.loboevolution.pdfview.font.ttf.CMap} objects.
     */
    public CMap[] getCMaps() {
        final Collection<CMap> c = new ArrayList<>();

        final CMap cmap_3_1 = this.getCMap((short) 3, (short) 1);
        if (cmap_3_1 != null) {
            c.add(cmap_3_1);
        }
        final CMap cmap_1_0 = this.getCMap((short) 1, (short) 0);
        if (cmap_1_0 != null) {
            c.add(cmap_1_0);
        }

        for (final CMap cmap : this.subtables.values()) {
            if (!c.contains(cmap)) {
                c.add(cmap);
            }
        }
        final CMap[] maps = new CMap[c.size()];

        c.toArray(maps);

        return maps;
    }

    /**
     * Remove a CMap
     *
     * @param platformID         a short.
     * @param platformSpecificID a short.
     */
    public void removeCMap(final short platformID, final short platformSpecificID) {
        final CmapSubtable key = new CmapSubtable(platformID, platformSpecificID);
        this.subtables.remove(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ByteBuffer getData() {
        final ByteBuffer buf = ByteBuffer.allocate(getLength());

        // write the table header
        buf.putShort(getVersion());
        buf.putShort((short) this.subtables.size());

        // the current offset to write to, starts at the end of the
        // subtables
        int curOffset = 4 + (this.subtables.size() * 8);

        // write the subtables
        for (final Map.Entry<CmapSubtable, CMap> entry : this.subtables.entrySet()) {
            final CmapSubtable cms = entry.getKey();
            final CMap map = this.subtables.get(cms);

            buf.putShort(cms.platformID);
            buf.putShort(cms.platformSpecificID);
            buf.putInt(curOffset);

            curOffset += map.getLength();
        }

        // write the tables
        for (final CMap map : this.subtables.values()) {
            buf.put(map.getData());
        }

        // reset the position to the start of the buffer
        buf.flip();

        return buf;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setData(final ByteBuffer data) {
        setVersion(data.getShort());

        final short numberSubtables = data.getShort();

        for (int i = 0; i < numberSubtables; i++) {
            final short platformID = data.getShort();
            final short platformSpecificID = data.getShort();
            final int offset = data.getInt();

            data.mark();

            // get the position from the start of this buffer
            data.position(offset);

            final ByteBuffer mapData = data.slice();

            data.reset();

            try {
                final CMap cMap = CMap.getMap(mapData);
                if (cMap != null) {
                    addCMap(platformID, platformSpecificID, cMap);
                }
            } catch (final Exception ex) {
                PDFDebugger.debug("Error reading map.  PlatformID=" +
                        platformID + ", PlatformSpecificID=" +
                        platformSpecificID);
                PDFDebugger.debug("Reason: " + ex);
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the size of the table, in bytes
     */
    @Override
    public int getLength() {
        // start with the size of the fixed data
        int length = 4;

        // add the size of the subtables 
        length += this.subtables.size() * 8;

        // add the size of the dynamic data
        for (final CMap map : this.subtables.values()) {
            // add the size of the subtable data
            length += map.getLength();
        }

        return length;
    }

    /**
     * Get the number of tables
     *
     * @return a short.
     */
    public short getNumberSubtables() {
        return (short) this.subtables.size();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Print a pretty string
     */
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        final String indent = "    ";

        buf.append(indent).append("Version: ").append(this.getVersion()).append("\n");
        buf.append(indent).append("NumMaps: ").append(this.getNumberSubtables()).append("\n");

        for (final Map.Entry<CmapSubtable, CMap> entry : this.subtables.entrySet()) {
            final CmapSubtable key = entry.getKey();
            buf.append(indent).append("Map: platformID: ").append(key.platformID).append(" PlatformSpecificID: ").append(key.platformSpecificID).append("\n");

            final CMap map = this.subtables.get(key);

            buf.append(map.toString());
        }

        return buf.toString();
    }

    /**
     * @param platformID         The platformID for this subtable
     * @param platformSpecificID The platform-specific id
     */
    record CmapSubtable(short platformID, short platformSpecificID) implements Comparable {
        /**
         * Create a Cmap subtable
         */
        CmapSubtable {
        }

            /**
             * Compare two subtables
             */
            @Override
            public boolean equals(final Object obj) {
                return (compareTo(obj) == 0);
            }

            /**
             * Sort ascending by platform ID and then specific ID
             */
            @Override
            public int compareTo(final Object obj) {
                if (!(obj instanceof CmapSubtable cms)) {
                    return -1;
                }

                if (this.platformID < cms.platformID) {
                    return -1;
                } else if (this.platformID > cms.platformID) {
                    return 1;
                } else {
                    return Short.compare(this.platformSpecificID, cms.platformSpecificID);
                }
            }
        }

}
