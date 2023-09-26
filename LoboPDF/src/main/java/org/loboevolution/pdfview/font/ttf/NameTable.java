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
import java.nio.charset.Charset;
import java.util.*;

/**
 * <p>NameTable class.</p>
 *
 * Author  jon
  *
 */
public class NameTable extends TrueTypeTable {
    /**
     * Values for platformID
     */
    public static final short PLATFORMID_UNICODE    = 0;
    /** Constant <code>PLATFORMID_MACINTOSH=1</code> */
    public static final short PLATFORMID_MACINTOSH  = 1;
    /** Constant <code>PLATFORMID_MICROSOFT=3</code> */
    public static final short PLATFORMID_MICROSOFT  = 3;
    
    /**
     * Values for platformSpecificID if platform is Mac
     */
    public static final short ENCODINGID_MAC_ROMAN = 0;
    
    /**
     * Values for platformSpecificID if platform is Unicode
     */
    public static final short ENCODINGID_UNICODE_DEFAULT = 0;
    /** Constant <code>ENCODINGID_UNICODE_V11=1</code> */
    public static final short ENCODINGID_UNICODE_V11     = 1;
    /** Constant <code>ENCODINGID_UNICODE_V2=3</code> */
    public static final short ENCODINGID_UNICODE_V2      = 3;
    
    /**
     * Values for language ID if platform is Mac
     */
    public static final short LANGUAGEID_MAC_ENGLISH     = 0;
    
    /**
     * Values for nameID
     */
    public static final short NAMEID_COPYRIGHT        = 0;
    /** Constant <code>NAMEID_FAMILY=1</code> */
    public static final short NAMEID_FAMILY           = 1;
    /** Constant <code>NAMEID_SUBFAMILY=2</code> */
    public static final short NAMEID_SUBFAMILY        = 2;
    /** Constant <code>NAMEID_SUBFAMILY_UNIQUE=3</code> */
    public static final short NAMEID_SUBFAMILY_UNIQUE = 3;
    /** Constant <code>NAMEID_FULL_NAME=4</code> */
    public static final short NAMEID_FULL_NAME        = 4;
    /** Constant <code>NAMEID_VERSION=5</code> */
    public static final short NAMEID_VERSION          = 5;
    /** Constant <code>NAMEID_POSTSCRIPT_NAME=6</code> */
    public static final short NAMEID_POSTSCRIPT_NAME  = 6;
    /** Constant <code>NAMEID_TRADEMARK=7</code> */
    public static final short NAMEID_TRADEMARK        = 7;
    /**
     * The format of this table
     */
    private short format;
    
    /**
     * The actual name records
     */
    private final SortedMap<NameRecord,String> records;
    
    
    /**
     * Creates a new instance of NameTable
     */
    protected NameTable() {
        super (TrueTypeTable.NAME_TABLE);
        
        this.records = Collections.synchronizedSortedMap(new TreeMap<>());
    }
    
    /**
     * Add a record to the table
     *
     * @param platformID a short.
     * @param platformSpecificID a short.
     * @param languageID a short.
     * @param nameID a short.
     * @param value a {@link java.lang.String} object.
     */
    public void addRecord(final short platformID, final short platformSpecificID,
                          final short languageID, final short nameID,
                          final String value) {
        final NameRecord rec = new NameRecord(platformID, platformSpecificID,
                                        languageID, nameID);
        this.records.put(rec, value);
    }
    
    /**
     * Get a record from the table
     *
     * @param platformID a short.
     * @param platformSpecificID a short.
     * @param languageID a short.
     * @param nameID a short.
     * @return a {@link java.lang.String} object.
     */
    public String getRecord(final short platformID, final short platformSpecificID,
                            final short languageID, final short nameID) {
    
        final NameRecord rec = new NameRecord(platformID, platformSpecificID,
                                        languageID, nameID);
        return this.records.get(rec);
    }
    
    /**
     * Remove a record from the table
     *
     * @param platformID a short.
     * @param platformSpecificID a short.
     * @param languageID a short.
     * @param nameID a short.
     */
    public void removeRecord(final short platformID, final short platformSpecificID,
                             final short languageID, final short nameID) {
        final NameRecord rec = new NameRecord(platformID, platformSpecificID,
                                        languageID, nameID);
        this.records.remove(rec);
    }
    
    /**
     * Determine if we have any records with a given platform ID
     *
     * @param platformID a short.
     * @return a boolean.
     */
    public boolean hasRecords(final short platformID) {
        for (final Map.Entry<NameRecord, String> entry : this.records.entrySet()) {
            final NameRecord rec = entry.getKey();
            if (rec.platformID == platformID) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Determine if we have any records with a given platform ID and
     * platform-specific ID
     *
     * @param platformID a short.
     * @param platformSpecificID a short.
     * @return a boolean.
     */
    public boolean hasRecords(final short platformID, final short platformSpecificID) {
        for (final Map.Entry<NameRecord, String> entry : this.records.entrySet()) {
            final NameRecord rec = entry.getKey();
            if (rec.platformID == platformID &&
                    rec.platformSpecificID == platformSpecificID) {
                return true;
            }
        }
        
        return false;
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Read the table from data
	 */
    @Override
	public void setData(final ByteBuffer data) {
        //read table header
        setFormat(data.getShort());
        final int count = data.getShort();
        final int stringOffset = data.getShort();
        
        // read the records
        for (int i = 0; i < count; i++) {
            final short platformID = data.getShort();
            final short platformSpecificID = data.getShort();
            final short languageID = data.getShort();
            final short nameID = data.getShort();
            
            final int length = data.getShort() & 0xFFFF;
            final int offset = data.getShort() & 0xFFFF;
            
            // read the String data
            data.mark();
            data.position(stringOffset + offset);
            
            final ByteBuffer stringBuf = data.slice();
            stringBuf.limit(length);
            
            data.reset();
            
            // choose the character set
            final String charsetName = getCharsetName(platformID, platformSpecificID);
            final Charset charset = Charset.forName(charsetName);
            
            // parse the data as a string
            final String value = charset.decode(stringBuf).toString();
        
            // add to the mix
            addRecord(platformID, platformSpecificID, languageID, nameID, value);
        }
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Get the data in this table as a buffer
	 */
    @Override
	public ByteBuffer getData() {
        // alocate the output buffer
        final ByteBuffer buf = ByteBuffer.allocate(getLength());
        
        // the start of string data
        final short headerLength = (short) (6 + (12 * getCount()));
        
        // write the header
        buf.putShort(getFormat());
        buf.putShort(getCount());
        buf.putShort(headerLength);
        
        // the offset from the start of the strings table
        short curOffset = 0;
        
        // add the size of each record
        for (final Map.Entry<NameRecord, String> entry : this.records.entrySet()) {
            final NameRecord rec = entry.getKey();
            final String value = this.records.get(rec);

            // choose the charset
            final String charsetName = getCharsetName(rec.platformID,
                    rec.platformSpecificID);
            final Charset charset = Charset.forName(charsetName);

            // encode
            final ByteBuffer strBuf = charset.encode(value);
            final short strLen = (short) (strBuf.remaining() & 0xFFFF);

            // write the IDs
            buf.putShort(rec.platformID);
            buf.putShort(rec.platformSpecificID);
            buf.putShort(rec.languageID);
            buf.putShort(rec.nameID);

            // write the size and offset
            buf.putShort(strLen);
            buf.putShort(curOffset);

            // remember or current position
            buf.mark();

            // move to the current offset and write the data
            buf.position(headerLength + curOffset);
            buf.put(strBuf);

            // reset stuff
            buf.reset();

            // increment offset
            curOffset += strLen;
        }
        
        // reset the pointer on the buffer
        buf.position(headerLength + curOffset);
        buf.flip();
        
        return buf;
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Get the length of this table
	 */
    @Override
	public int getLength() {
        // start with the size of the fixed header plus the size of the
        // records
        int length = 6 + (12 * getCount());
        
        // add the size of each record
        for (final Map.Entry<NameRecord, String> entry : this.records.entrySet()) {
            final NameRecord rec = entry.getKey();
            final String value = this.records.get(rec);

            // choose the charset
            final String charsetName = getCharsetName(rec.platformID,
                    rec.platformSpecificID);
            final Charset charset = Charset.forName(charsetName);

            // encode
            final ByteBuffer buf = charset.encode(value);

            // add the size of the coded buffer
            length += buf.remaining();
        }
        
        return length;
    }
    
    /**
     * Get the format of this table
     *
     * @return a short.
     */
    public short getFormat() {
        return this.format;
    }
    
    /**
     * Set the format of this table
     *
     * @param format a short.
     */
    public void setFormat(final short format) {
        this.format = format;
    }
    
    /**
     * Get the number of records in the table
     *
     * @return a short.
     */
    public short getCount() {
        return (short) this.records.size();
    }
    
    /**
     * Get the charset name for a given platform, encoding and language
     *
     * @param platformID a int.
     * @param encodingID a int.
     * @return a {@link java.lang.String} object.
     */
    public static String getCharsetName(final int platformID, final int encodingID) {
        String charset = "";   
            
        switch (platformID) {
            case PLATFORMID_UNICODE:
            case PLATFORMID_MICROSOFT:
                charset = "UTF-16";
                break;
            default:
            	charset = "US-ASCII";
                break;
        }
        
        return charset;
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Get a pretty string
	 */
    @Override
	public String toString() {
        final StringBuilder buf = new StringBuilder();
        final String indent = "    ";
        
        buf.append(indent).append("Format: ").append(getFormat()).append("\n");
        buf.append(indent).append("Count : ").append(getCount()).append("\n");

        for (final Map.Entry<NameRecord, String> entry : this.records.entrySet()) {
            final NameRecord rec = entry.getKey();
            buf.append(indent).append(" platformID: ").append(rec.platformID);
            buf.append(" platformSpecificID: ").append(rec.platformSpecificID);
            buf.append(" languageID: ").append(rec.languageID);
            buf.append(" nameID: ").append(rec.nameID).append("\n");
            buf.append(indent).append("  ").append(this.records.get(rec)).append("\n");
        }
        
        return buf.toString();
    }
    
    /**
     * <p>getNames.</p>
     *
     * @return a {@link java.util.Collection} object.
     */
    public Collection<String> getNames()
    {
    	return Collections.unmodifiableCollection(records.values());
    }
    
    /**
     * A class to hold the data associated with each record
     */
    static class NameRecord implements Comparable {
        /**
         * Platform ID
         */
        final short platformID;
        
        /**
         * Platform Specific ID (Encoding)
         */
        final short platformSpecificID;
        
        /**
         * Language ID
         */
        final short languageID;
        
        /**
         * Name ID
         */
        final short nameID;
        
        /**
         * Create a new record
         */
        NameRecord(final short platformID, final short platformSpecificID,
                   final short languageID, final short nameID) {
            this.platformID = platformID;
            this.platformSpecificID = platformSpecificID;
            this.languageID = languageID;
            this.nameID = nameID;
        }
        
        
        /**
         * Compare two records
         */
        @Override
		public boolean equals(final Object o) {
            return (compareTo(o) == 0);
        }
        
        /**
         * Compare two records
         */
        @Override
		public int compareTo(final Object obj) {
            if (!(obj instanceof NameRecord)) {
                return -1;
            }
            
            final NameRecord rec = (NameRecord) obj;
            
            if (this.platformID > rec.platformID) {
                return 1;
            } else if (this.platformID < rec.platformID) {
                return -1;
            } else if (this.platformSpecificID > rec.platformSpecificID) {
                return 1;
            } else if (this.platformSpecificID < rec.platformSpecificID) {
                return -1;
            } else if (this.languageID > rec.languageID) {
                return 1;
            } else if (this.languageID < rec.languageID) {
                return -1;
            } else return Short.compare(this.nameID, rec.nameID);
        }
        
        
    }
}
