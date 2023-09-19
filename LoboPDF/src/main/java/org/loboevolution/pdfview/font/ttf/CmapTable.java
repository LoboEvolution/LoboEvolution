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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import org.loboevolution.pdfview.PDFDebugger;

/**
 * Represents the TTF "cmap" table
 *
 * Author  jkaplan
  *
 */
public class CmapTable extends TrueTypeTable {
    
    /** Holds value of property version. */
    private short version;
    
    /**
     * Holds the CMap subtables, sorted properly
     */
    private final SortedMap<CmapSubtable,CMap> subtables;
    
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
     * @param platformID a short.
     * @param platformSpecificID a short.
     * @param cMap a {@link org.loboevolution.pdfview.font.ttf.CMap} object.
     */
    public void addCMap(short platformID, short platformSpecificID,
                        CMap cMap) {
        CmapSubtable key = new CmapSubtable(platformID, platformSpecificID);
        this.subtables.put(key, cMap);
    }
    
    /**
     * Get a CMap by platform and specific ID
     *
     * @param platformID a short.
     * @param platformSpecificID a short.
     * @return a {@link org.loboevolution.pdfview.font.ttf.CMap} object.
     */
    public CMap getCMap(short platformID, short platformSpecificID) {
        CmapSubtable key = new CmapSubtable(platformID, platformSpecificID);
        return this.subtables.get(key);
    }
    
    /**
     * Get all CMaps
     *
     * @return an array of {@link org.loboevolution.pdfview.font.ttf.CMap} objects.
     */
    public CMap[] getCMaps() {
        Collection<CMap> c = new ArrayList<>();
        
        CMap cmap_3_1 = this.getCMap((short)3, (short)1);
        if (cmap_3_1 != null) {
        	c.add(cmap_3_1);
        }
        CMap cmap_1_0 = this.getCMap((short)1, (short)0);
        if (cmap_1_0 != null) {
        	c.add(cmap_1_0);
        }

        for (CMap cmap : this.subtables.values()) {
        	if (!c.contains(cmap)) {
        		c.add(cmap);
        	}
        }
        CMap[] maps = new CMap[c.size()];
        
        c.toArray(maps);
        
        return maps;
    }
    
    /**
     * Remove a CMap
     *
     * @param platformID a short.
     * @param platformSpecificID a short.
     */
    public void removeCMap(short platformID, short platformSpecificID) {
        CmapSubtable key = new CmapSubtable(platformID, platformSpecificID);
        this.subtables.remove(key);
    }
    
    /** {@inheritDoc} */
    @Override public void setData(ByteBuffer data) {
        setVersion(data.getShort());
        
        short numberSubtables = data.getShort();
        
        for (int i = 0; i < numberSubtables; i++) {
            short platformID = data.getShort();
            short platformSpecificID = data.getShort();
            int offset = data.getInt();
            
            data.mark();
            
            // get the position from the start of this buffer 
            data.position(offset);
            
            ByteBuffer mapData = data.slice();
            
            data.reset();
            
            try {
                CMap cMap = CMap.getMap(mapData);
                if (cMap != null) {
                    addCMap(platformID, platformSpecificID, cMap);
                }
            } catch (Exception ex) {
                PDFDebugger.debug("Error reading map.  PlatformID=" +
                                    platformID + ", PlatformSpecificID=" + 
                                    platformSpecificID);
                PDFDebugger.debug("Reason: " + ex);
            }
        }
    }
    
    /** {@inheritDoc} */
    @Override public ByteBuffer getData() {
        ByteBuffer buf = ByteBuffer.allocate(getLength());
    
        // write the table header
        buf.putShort(getVersion());
        buf.putShort((short) this.subtables.size());
        
        // the current offset to write to, starts at the end of the
        // subtables
        int curOffset = 4 + (this.subtables.size() * 8);
        
        // write the subtables
        for (CmapSubtable cms : this.subtables.keySet()) {
            CMap map = this.subtables.get(cms);

            buf.putShort(cms.platformID);
            buf.putShort(cms.platformSpecificID);
            buf.putInt(curOffset);

            curOffset += map.getLength();
        }
        
        // write the tables
        for (CMap map : this.subtables.values()) {
            buf.put(map.getData());
        }
        
        // reset the position to the start of the buffer
        buf.flip();
        
        return buf;
    }
    
    /**
     * {@inheritDoc}
     *
     * Get the size of the table, in bytes
     */
    @Override public int getLength() {
        // start with the size of the fixed data
        int length = 4;
       
        // add the size of the subtables 
        length += this.subtables.size() * 8;
        
        // add the size of the dynamic data
        for (CMap map : this.subtables.values()) {
            // add the size of the subtable data
            length += map.getLength();
        }
    
        return length;
    }
    
    
    /**
     * Getter for property version.
     *
     * @return Value of property version.
     */
    public short getVersion() {
        return this.version;
    }
    
    /**
     * Setter for property version.
     *
     * @param version New value of property version.
     */
    public void setVersion(short version) {
        this.version = version;
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
     *
     * Print a pretty string
     */
    @Override public String toString() {
        StringBuilder buf = new StringBuilder();
        String indent = "    ";
    
        buf.append(indent).append("Version: ").append(this.getVersion()).append("\n");
        buf.append(indent).append("NumMaps: ").append(this.getNumberSubtables()).append("\n");

        for (CmapSubtable key : this.subtables.keySet()) {
            buf.append(indent).append("Map: platformID: ").append(key.platformID).append(" PlatformSpecificID: ").append(key.platformSpecificID).append("\n");

            CMap map = this.subtables.get(key);

            buf.append(map.toString());
        }
        
        return buf.toString();
    }
    
    static class CmapSubtable implements Comparable {
        /**
         * The platformID for this subtable
         */
        final short platformID;
        
        /**
         * The platform-specific id
         */
        final short platformSpecificID;
        
        /** 
         * Create a Cmap subtable
         */
        protected CmapSubtable(short platformID, short platformSpecificID) {
            this.platformID = platformID;
            this.platformSpecificID = platformSpecificID;
        }
            
        /**
         * Compare two subtables
         */
        @Override public boolean equals(final Object obj) {
            return (compareTo(obj) == 0);
        }
        
        /**
         * Sort ascending by platform ID and then specific ID
         */
        @Override
		public int compareTo(Object obj) {
            if (!(obj instanceof CmapSubtable)) {
                return -1;
            }
            
            CmapSubtable cms = (CmapSubtable) obj;
            if (this.platformID < cms.platformID) {
                return -1;
            } else if (this.platformID > cms.platformID) {
                return 1;
            } else {
                if (this.platformSpecificID < cms.platformSpecificID) {
                    return -1;
                } else if (this.platformSpecificID > cms.platformSpecificID) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }
    
}
