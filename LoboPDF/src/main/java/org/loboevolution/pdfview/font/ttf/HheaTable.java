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

/**
 * <p>HheaTable class.</p>
 *
 * Author  jkaplan
  *
 */
public class HheaTable extends TrueTypeTable {
    
    /** Holds value of property version. */
    private int version;
    
    /** Holds value of property ascent. */
    private short ascent;
    
    /** Holds value of property descent. */
    private short descent;
    
    /** Holds value of property lineGap. */
    private short lineGap;
    
    /** Holds value of property advanceWidthMax. */
    private short advanceWidthMax;
    
    /** Holds value of property minLeftSideBearing. */
    private short minLeftSideBearing;
    
    /** Holds value of property minRightSideBearing. */
    private short minRightSideBearing;
    
    /** Holds value of property xMaxExtent. */
    private short xMaxExtent;
    
    /** Holds value of property caretSlopeRise. */
    private short caretSlopeRise;
    
    /** Holds value of property caretSlopeRun. */
    private short caretSlopeRun;
    
    /** Holds value of property caretOffset. */
    private short caretOffset;
    
    /** Holds value of property metricDataFormat. */
    private short metricDataFormat;
    
    /** Holds value of property numOfLongHorMetrics. */
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
	 *
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
	 *
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
        buf.putShort((short) getNumOfLongHorMetrics());
    
        // reset the position to the start of the buffer
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
        return 36;
    }
    
    /**
     * Getter for property version.
     *
     * @return Value of property version.
     */
    public int getVersion() {
        return this.version;
    }
   
    /**
     * Setter for property version.
     *
     * @param version New value of property version.
     */
    public void setVersion(final int version) {
        this.version = version;
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Create a pretty string
	 */
    @Override
	public String toString() {
        final StringBuilder buf = new StringBuilder();
        final String indent = "    ";
        
        buf.append(indent).append("Version             : ").append(Integer.toHexString(getVersion())).append("\n");
        buf.append(indent).append("Ascent              : ").append(getAscent()).append("\n");
        buf.append(indent).append("Descent             : ").append(getDescent()).append("\n");
        buf.append(indent).append("LineGap             : ").append(getLineGap()).append("\n");
        buf.append(indent).append("AdvanceWidthMax     : ").append(getAdvanceWidthMax()).append("\n");
        buf.append(indent).append("MinLSB              : ").append(getMinLeftSideBearing()).append("\n");
        buf.append(indent).append("MinRSB              : ").append(getMinRightSideBearing()).append("\n");
        buf.append(indent).append("MaxExtent           : ").append(getXMaxExtent()).append("\n");
        buf.append(indent).append("CaretSlopeRise      : ").append(getCaretSlopeRise()).append("\n");
        buf.append(indent).append("CaretSlopeRun       : ").append(getCaretSlopeRun()).append("\n");
        buf.append(indent).append("CaretOffset         : ").append(getCaretOffset()).append("\n");
        buf.append(indent).append("MetricDataFormat    : ").append(getMetricDataFormat()).append("\n");
        buf.append(indent).append("NumOfLongHorMetrics : ").append(getNumOfLongHorMetrics()).append("\n");
        return buf.toString();
    }
    
    /**
     * Getter for property ascent.
     *
     * @return Value of property ascent.
     */
    public short getAscent() {
        return this.ascent;
    }
    
    /**
     * Setter for property ascent.
     *
     * @param ascent New value of property ascent.
     */
    public void setAscent(final short ascent) {
        this.ascent = ascent;
    }
    
    /**
     * Getter for property descent.
     *
     * @return Value of property descent.
     */
    public short getDescent() {
        return this.descent;
    }
    
    /**
     * Setter for property descent.
     *
     * @param descent New value of property descent.
     */
    public void setDescent(final short descent) {
        this.descent = descent;
    }
    
    /**
     * Getter for property lineGap.
     *
     * @return Value of property lineGap.
     */
    public short getLineGap() {
        return this.lineGap;
    }
    
    /**
     * Setter for property lineGap.
     *
     * @param lineGap New value of property lineGap.
     */
    public void setLineGap(final short lineGap) {
        this.lineGap = lineGap;
    }
    
    /**
     * Getter for property advanceWidthMax.
     *
     * @return Value of property advanceWidthMax.
     */
    public short getAdvanceWidthMax() {
        return this.advanceWidthMax;
    }
    
    /**
     * Setter for property advanceWidthMax.
     *
     * @param advanceWidthMax New value of property advanceWidthMax.
     */
    public void setAdvanceWidthMax(final short advanceWidthMax) {
        this.advanceWidthMax = advanceWidthMax;
    }
    
    /**
     * Getter for property minLeftSideBearing.
     *
     * @return Value of property minLeftSideBearing.
     */
    public short getMinLeftSideBearing() {
        return this.minLeftSideBearing;
    }
    
    /**
     * Setter for property minLeftSideBearing.
     *
     * @param minLeftSideBearing New value of property minLeftSideBearing.
     */
    public void setMinLeftSideBearing(final short minLeftSideBearing) {
        this.minLeftSideBearing = minLeftSideBearing;
    }
    
    /**
     * Getter for property minRIghtSideBearing.
     *
     * @return Value of property minRIghtSideBearing.
     */
    public short getMinRightSideBearing() {
        return this.minRightSideBearing;
    }
    
    /**
     * Setter for property minRIghtSideBearing.
     *
     * @param minRightSideBearing New value of property minRIghtSideBearing.
     */
    public void setMinRightSideBearing(final short minRightSideBearing) {
        this.minRightSideBearing = minRightSideBearing;
    }
    
    /**
     * Getter for property xMaxExtent.
     *
     * @return Value of property xMaxExtent.
     */
    public short getXMaxExtent() {
        return this.xMaxExtent;
    }
    
    /**
     * Setter for property xMaxExtent.
     *
     * @param xMaxExtent New value of property xMaxExtent.
     */
    public void setXMaxExtent(final short xMaxExtent) {
        this.xMaxExtent = xMaxExtent;
    }
    
    /**
     * Getter for property caretSlopeRise.
     *
     * @return Value of property caretSlopeRise.
     */
    public short getCaretSlopeRise() {
        return this.caretSlopeRise;
    }
    
    /**
     * Setter for property caretSlopeRise.
     *
     * @param caretSlopeRise New value of property caretSlopeRise.
     */
    public void setCaretSlopeRise(final short caretSlopeRise) {
        this.caretSlopeRise = caretSlopeRise;
    }
    
    /**
     * Getter for property caretSlopeRun.
     *
     * @return Value of property caretSlopeRun.
     */
    public short getCaretSlopeRun() {
        return this.caretSlopeRun;
    }
    
    /**
     * Setter for property caretSlopeRun.
     *
     * @param caretSlopeRun New value of property caretSlopeRun.
     */
    public void setCaretSlopeRun(final short caretSlopeRun) {
        this.caretSlopeRun = caretSlopeRun;
    }
    
    /**
     * Getter for property caretOffset.
     *
     * @return Value of property caretOffset.
     */
    public short getCaretOffset() {
        return this.caretOffset;
    }
    
    /**
     * Setter for property caretOffset.
     *
     * @param caretOffset New value of property caretOffset.
     */
    public void setCaretOffset(final short caretOffset) {
        this.caretOffset = caretOffset;
    }
    
    /**
     * Getter for property metricDataFormat.
     *
     * @return Value of property metricDataFormat.
     */
    public short getMetricDataFormat() {
        return this.metricDataFormat;
    }
    
    /**
     * Setter for property metricDataFormat.
     *
     * @param metricDataFormat New value of property metricDataFormat.
     */
    public void setMetricDataFormat(final short metricDataFormat) {
        this.metricDataFormat = metricDataFormat;
    }
    
    /**
     * Getter for property numOfLongHorMetrics.
     *
     * @return Value of property numOfLongHorMetrics.
     */
    public int getNumOfLongHorMetrics() {
        return this.numOfLongHorMetrics & 0xFFFF;
    }
    
    /**
     * Setter for property numOfLongHorMetrics.
     *
     * @param numOfLongHorMetrics New value of property numOfLongHorMetrics.
     */
    public void setNumOfLongHorMetrics(final short numOfLongHorMetrics) {
        this.numOfLongHorMetrics = numOfLongHorMetrics;
    }
    
}
