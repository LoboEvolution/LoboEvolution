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
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;

/**
 * The base class for TrueType tables.  Specific tables can extend this
 * to add more functionality
 */
@NoArgsConstructor(force = true)
@Data
public class TrueTypeTable {

    /**
     * Well known tables
     */
    public static final int CMAP_TABLE = 0x636d6170;
    /**
     * Constant <code>GLYF_TABLE=0x676c7966</code>
     */
    public static final int GLYF_TABLE = 0x676c7966;
    /**
     * Constant <code>HEAD_TABLE=0x68656164</code>
     */
    public static final int HEAD_TABLE = 0x68656164;
    /**
     * Constant <code>HHEA_TABLE=0x68686561</code>
     */
    public static final int HHEA_TABLE = 0x68686561;
    /**
     * Constant <code>HMTX_TABLE=0x686d7478</code>
     */
    public static final int HMTX_TABLE = 0x686d7478;
    /**
     * Constant <code>MAXP_TABLE=0x6d617870</code>
     */
    public static final int MAXP_TABLE = 0x6d617870;
    /**
     * Constant <code>NAME_TABLE=0x6e616d65</code>
     */
    public static final int NAME_TABLE = 0x6e616d65;
    /**
     * Constant <code>POST_TABLE=0x706f7374</code>
     */
    public static final int POST_TABLE = 0x706f7374;
    /**
     * Constant <code>LOCA_TABLE=0x6c6f6361</code>
     */
    public static final int LOCA_TABLE = 0x6c6f6361;
    /**
     * This table's tag
     */
    private final int tag;
    /**
     * The data in this table, in ByteBuffer form
     */
    private ByteBuffer data;

    /**
     * Creates a new instance of TrueTypeTable.
     * <p>
     * This method is protected.  Use the <code>getTable()</code> methods
     * to get new instances.
     *
     * @param tag the tag for this table
     */
    protected TrueTypeTable(final int tag) {
        this.tag = tag;
    }

    /**
     * Get a new instance of an empty table by tag string
     *
     * @param ttf       the font that contains this table
     * @param tagString the tag for this table, as a 4 character string
     *                  (e.g. head or cmap)
     * @return a {@link org.loboevolution.pdfview.font.ttf.TrueTypeTable} object.
     */
    public static TrueTypeTable createTable(final TrueTypeFont ttf,
                                            final String tagString) {
        return createTable(ttf, tagString, null);
    }

    /**
     * Get a new instance of a table with provided data
     *
     * @param ttf       the font that contains this table
     * @param tagString the tag for this table, as a 4 character string
     *                  (e.g. head or cmap)
     * @param data      the table data
     * @return a {@link org.loboevolution.pdfview.font.ttf.TrueTypeTable} object.
     */
    public static TrueTypeTable createTable(final TrueTypeFont ttf,
                                            final String tagString, final ByteBuffer data) {
        TrueTypeTable outTable;

        final int tag = stringToTag(tagString);

        outTable = switch (tag) {
            case CMAP_TABLE -> // cmap table
                    new CmapTable();
            case GLYF_TABLE -> new GlyfTable(ttf);
            case HEAD_TABLE -> // head table
                    new HeadTable();
            case HHEA_TABLE ->  // hhea table
                    new HheaTable();
            case HMTX_TABLE -> new HmtxTable(ttf);
            case LOCA_TABLE -> new LocaTable(ttf);
            case MAXP_TABLE ->  // maxp table
                    new MaxpTable();
            case NAME_TABLE -> // name table
                    new NameTable();
            case POST_TABLE -> // post table
                    new PostTable();
            default -> new TrueTypeTable(tag);
        };

        if (data != null) {
            outTable.setData(data);
        }

        return outTable;
    }

    /**
     * Get the tag as a string
     *
     * @param tag a {@link java.lang.Integer} object.
     * @return a {@link java.lang.String} object.
     */
    public static String tagToString(final int tag) {
        final char[] c = new char[4];
        c[0] = (char) (0xff & (tag >> 24));
        c[1] = (char) (0xff & (tag >> 16));
        c[2] = (char) (0xff & (tag >> 8));
        c[3] = (char) (0xff & (tag));

        return new String(c);
    }

    /**
     * Turn a string into a tag
     *
     * @param tag a {@link java.lang.String} object.
     * @return a {@link java.lang.Integer} object.
     */
    public static int stringToTag(final String tag) {
        final char[] c = tag.toCharArray();

        if (c.length != 4) {
            throw new IllegalArgumentException("Bad tag length: " + tag);
        }

        return c[0] << 24 | c[1] << 16 | c[2] << 8 | c[3];
    }

    /**
     * Get the size of the table, in bytes
     *
     * @return a {@link java.lang.Integer} object.
     */
    public int getLength() {
        return getData().remaining();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Put into a nice string
     */
    @Override
    public String toString() {
        String out = "    " + tagToString(getTag()) + " Table.  Data is: ";
        if (getData() == null) {
            out += "not set";
        } else {
            out += "set";
        }
        return out;
    }
}
