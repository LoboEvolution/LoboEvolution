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
import lombok.EqualsAndHashCode;

import java.nio.ByteBuffer;
import java.util.Date;

/**
 * <p>HeadTable class.</p>
 * <p>
 * Author  jkaplan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HeadTable extends TrueTypeTable {

    /**
     * Holds value of property version.
     */
    private int version;

    /**
     * Holds value of property fontRevision.
     */
    private int fontRevision;

    /**
     * Holds value of property checksumAdjustment.
     */
    private int checksumAdjustment;

    /**
     * Holds value of property magicNumber.
     */
    private int magicNumber;

    /**
     * Holds value of property flags.
     */
    private short flags;

    /**
     * Holds value of property unitsPerEm.
     */
    private short unitsPerEm;

    /**
     * Holds value of property created.
     */
    private long created;

    /**
     * Holds value of property modified.
     */
    private long modified;

    /**
     * Holds value of property xMin.
     */
    private short xMin;

    /**
     * Holds value of property yMin.
     */
    private short yMin;

    /**
     * Holds value of property xMax.
     */
    private short xMax;

    /**
     * Holds value of property yMax.
     */
    private short yMax;

    /**
     * Holds value of property macStyle.
     */
    private short macStyle;

    /**
     * Holds value of property lowestRecPPem.
     */
    private short lowestRecPPem;

    /**
     * Holds value of property fontDirectionHint.
     */
    private short fontDirectionHint;

    /**
     * Holds value of property indexToLocFormat.
     */
    private short indexToLocFormat;

    /**
     * Holds value of property glyphDataFormat.
     */
    private short glyphDataFormat;

    /**
     * Creates a new instance of HeadTable
     * Makes up reasonable(?) defaults for all values
     */
    public HeadTable() {
        super(TrueTypeTable.HEAD_TABLE);

        setVersion(0x10000);
        setFontRevision(0x10000);
        setChecksumAdjustment(0);
        setMagicNumber(0x5f0f3cf5);
        setFlags((short) 0x0);
        setUnitsPerEm((short) 64);
        setCreated(System.currentTimeMillis());
        setModified(System.currentTimeMillis());
        setXMin((short) 0);
        setXMax(Short.MAX_VALUE);
        setYMin((short) 0);
        setYMax(Short.MAX_VALUE);
        setMacStyle((short) 0x0);
        setLowestRecPPem((short) 0);
        setFontDirectionHint((short) 0);
        setIndexToLocFormat((short) 0);
        setGlyphDataFormat((short) 0);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the data we have stored
     */
    @Override
    public ByteBuffer getData() {
        final ByteBuffer buf = ByteBuffer.allocate(getLength());

        buf.putInt(getVersion());
        buf.putInt(getFontRevision());
        buf.putInt(getChecksumAdjustment());
        buf.putInt(getMagicNumber());
        buf.putShort(getFlags());
        buf.putShort(getUnitsPerEm());
        buf.putLong(getCreated());
        buf.putLong(getModified());
        buf.putShort(getXMin());
        buf.putShort(getXMax());
        buf.putShort(getYMin());
        buf.putShort(getYMax());
        buf.putShort(getMacStyle());
        buf.putShort(getLowestRecPPem());
        buf.putShort(getFontDirectionHint());
        buf.putShort(getIndexToLocFormat());
        buf.putShort(getGlyphDataFormat());

        // reset the position to the start of the buffer
        buf.flip();

        return buf;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Parse the data before it is set
     */
    @Override
    public void setData(final ByteBuffer data) {
        if (data.remaining() < 54) {
            throw new IllegalArgumentException("Bad Head table size " + data.remaining());
        }
        setVersion(data.getInt());
        setFontRevision(data.getInt());
        setChecksumAdjustment(data.getInt());
        setMagicNumber(data.getInt());
        setFlags(data.getShort());
        setUnitsPerEm(data.getShort());
        setCreated(data.getLong());
        setModified(data.getLong());
        setXMin(data.getShort());
        setXMax(data.getShort());
        setYMin(data.getShort());
        setYMax(data.getShort());
        setMacStyle(data.getShort());
        setLowestRecPPem(data.getShort());
        setFontDirectionHint(data.getShort());
        setIndexToLocFormat(data.getShort());
        setGlyphDataFormat(data.getShort());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the length of this table
     */
    @Override
    public int getLength() {
        return 54;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Create a pretty string
     */
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        final String indent = "    ";

        buf.append(indent).append("Version          : ").append(Integer.toHexString(getVersion())).append("\n");
        buf.append(indent).append("Revision         : ").append(Integer.toHexString(getFontRevision())).append("\n");
        buf.append(indent).append("ChecksumAdj      : ").append(Integer.toHexString(getChecksumAdjustment())).append("\n");
        buf.append(indent).append("MagicNumber      : ").append(Integer.toHexString(getMagicNumber())).append("\n");
        buf.append(indent).append("Flags            : ").append(Integer.toBinaryString(getFlags())).append("\n");
        buf.append(indent).append("UnitsPerEm       : ").append(getUnitsPerEm()).append("\n");
        buf.append(indent).append("Created          : ").append(new Date(getCreated())).append("\n");
        buf.append(indent).append("Modified         : ").append(new Date(getModified())).append("\n");
        buf.append(indent).append("XMin             : ").append(getXMin()).append("\n");
        buf.append(indent).append("XMax             : ").append(getXMax()).append("\n");
        buf.append(indent).append("YMin             : ").append(getYMin()).append("\n");
        buf.append(indent).append("YMax             : ").append(getYMax()).append("\n");
        buf.append(indent).append("MacStyle         : ").append(Integer.toBinaryString(getMacStyle())).append("\n");
        buf.append(indent).append("LowestPPem       : ").append(getLowestRecPPem()).append("\n");
        buf.append(indent).append("FontDirectionHint: ").append(getFontDirectionHint()).append("\n");
        buf.append(indent).append("IndexToLocFormat : ").append(getIndexToLocFormat()).append("\n");
        buf.append(indent).append("GlyphDataFormat  : ").append(getGlyphDataFormat()).append("\n");

        return buf.toString();
    }
}
