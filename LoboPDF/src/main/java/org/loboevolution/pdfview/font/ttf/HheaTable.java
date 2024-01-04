/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

/**
 * <p>HheaTable class.</p>
 * <p>
 * Author  jkaplan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HheaTable extends TrueTypeTable {

    /**
     * Holds value of property version.
     */
    private int version;

    /**
     * Holds value of property ascent.
     */
    private short ascent;

    /**
     * Holds value of property descent.
     */
    private short descent;

    /**
     * Holds value of property lineGap.
     */
    private short lineGap;

    /**
     * Holds value of property advanceWidthMax.
     */
    private short advanceWidthMax;

    /**
     * Holds value of property minLeftSideBearing.
     */
    private short minLeftSideBearing;

    /**
     * Holds value of property minRightSideBearing.
     */
    private short minRightSideBearing;

    /**
     * Holds value of property xMaxExtent.
     */
    private short xMaxExtent;

    /**
     * Holds value of property caretSlopeRise.
     */
    private short caretSlopeRise;

    /**
     * Holds value of property caretSlopeRun.
     */
    private short caretSlopeRun;

    /**
     * Holds value of property caretOffset.
     */
    private short caretOffset;

    /**
     * Holds value of property metricDataFormat.
     */
    private short metricDataFormat;

    /**
     * Holds value of property numOfLongHorMetrics.
     */
    private short numOfLongHorMetrics;

    /**
     * Creates a new instance of HeadTable
     * Makes up reasonable(?) defaults for all values
     */
    protected HheaTable() {
        super(TrueTypeTable.HEAD_TABLE);

        setVersion(0x10000);
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
        buf.putShort(getAscent());
        buf.putShort(getDescent());
        buf.putShort(getLineGap());
        buf.putShort(getAdvanceWidthMax());
        buf.putShort(getMinLeftSideBearing());
        buf.putShort(getMinRightSideBearing());
        buf.putShort(getXMaxExtent());
        buf.putShort(getCaretSlopeRise());
        buf.putShort(getCaretSlopeRun());
        buf.putShort(getCaretOffset());

        // padding
        buf.putShort((short) 0);
        buf.putShort((short) 0);
        buf.putShort((short) 0);
        buf.putShort((short) 0);

        buf.putShort(getMetricDataFormat());
        buf.putShort(getNumOfLongHorMetrics());

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
        if (data.remaining() != 36) {
            throw new IllegalArgumentException("Bad Head table size");
        }
        setVersion(data.getInt());
        setAscent(data.getShort());
        setDescent(data.getShort());
        setLineGap(data.getShort());
        setAdvanceWidthMax(data.getShort());
        setMinLeftSideBearing(data.getShort());
        setMinRightSideBearing(data.getShort());
        setXMaxExtent(data.getShort());
        setCaretSlopeRise(data.getShort());
        setCaretSlopeRun(data.getShort());
        setCaretOffset(data.getShort());

        // padding
        data.getShort();
        data.getShort();
        data.getShort();
        data.getShort();

        setMetricDataFormat(data.getShort());
        setNumOfLongHorMetrics(data.getShort());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the length of this table
     */
    @Override
    public int getLength() {
        return 36;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Create a pretty string
     */
    @Override
    public String toString() {
        final String indent = "    ";

        return indent + "Version             : " + Integer.toHexString(getVersion()) + "\n" +
                indent + "Ascent              : " + getAscent() + "\n" +
                indent + "Descent             : " + getDescent() + "\n" +
                indent + "LineGap             : " + getLineGap() + "\n" +
                indent + "AdvanceWidthMax     : " + getAdvanceWidthMax() + "\n" +
                indent + "MinLSB              : " + getMinLeftSideBearing() + "\n" +
                indent + "MinRSB              : " + getMinRightSideBearing() + "\n" +
                indent + "MaxExtent           : " + getXMaxExtent() + "\n" +
                indent + "CaretSlopeRise      : " + getCaretSlopeRise() + "\n" +
                indent + "CaretSlopeRun       : " + getCaretSlopeRun() + "\n" +
                indent + "CaretOffset         : " + getCaretOffset() + "\n" +
                indent + "MetricDataFormat    : " + getMetricDataFormat() + "\n" +
                indent + "NumOfLongHorMetrics : " + getNumOfLongHorMetrics() + "\n";
    }
}
