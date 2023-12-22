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

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.nio.ByteBuffer;

/**
 * <p>MaxpTable class.</p>
 * <p>
 * Author  jkaplan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MaxpTable extends TrueTypeTable {

    /**
     * Holds value of property version.
     */
    private int version;

    // the following are supposed to be USHORT, but will be Int to enclose the sign
    // (http://www.microsoft.com/typography/OTSpec/maxp.htm)
    /**
     * Holds value of property numGlyphs.
     */
    private int numGlyphs;

    /**
     * Holds value of property maxPoints.
     */
    private int maxPoints;

    /**
     * Holds value of property maxContours.
     */
    private int maxContours;

    /**
     * Holds value of property maxComponentPoints.
     */
    private int maxComponentPoints;

    /**
     * Holds value of property maxComponentContours.
     */
    private int maxComponentContours;

    /**
     * Holds value of property maxZones.
     */
    private int maxZones;

    /**
     * Holds value of property maxTwilightPoints.
     */
    private int maxTwilightPoints;

    /**
     * Holds value of property maxStorage.
     */
    private int maxStorage;

    /**
     * Holds value of property maxFunctionDefs.
     */
    private int maxFunctionDefs;

    /**
     * Holds value of property maxInstructionDefs.
     */
    private int maxInstructionDefs;

    /**
     * Holds value of property maxStackElements.
     */
    private int maxStackElements;

    /**
     * Holds value of property maxSizeOfInstructions.
     */
    private int maxSizeOfInstructions;

    /**
     * Holds value of property maxComponentElements.
     */
    private int maxComponentElements;

    /**
     * Holds value of property maxComponentDepth.
     */
    private int maxComponentDepth;

    /**
     * Creates a new instance of MaxpTable
     */
    protected MaxpTable() {
        super(TrueTypeTable.MAXP_TABLE);

        setVersion(0x10000);
        setNumGlyphs(0);
        setMaxPoints(0);
        setMaxContours(0);
        setMaxComponentPoints(0);
        setMaxComponentContours(0);
        setMaxZones(2);
        setMaxTwilightPoints(0);
        setMaxStorage(0);
        setMaxFunctionDefs(0);
        setMaxInstructionDefs(0);
        setMaxStackElements(0);
        setMaxSizeOfInstructions(0);
        setMaxComponentElements(0);
        setMaxComponentDepth(0);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get a buffer from the data
     */
    @Override
    public ByteBuffer getData() {
        final ByteBuffer buf = ByteBuffer.allocate(getLength());

        buf.putInt(getVersion());
        buf.putShort((short) getNumGlyphs());
        buf.putShort((short) getMaxPoints());
        buf.putShort((short) getMaxContours());
        buf.putShort((short) getMaxComponentPoints());
        buf.putShort((short) getMaxComponentContours());
        buf.putShort((short) getMaxZones());
        buf.putShort((short) getMaxTwilightPoints());
        buf.putShort((short) getMaxStorage());
        buf.putShort((short) getMaxFunctionDefs());
        buf.putShort((short) getMaxInstructionDefs());
        buf.putShort((short) getMaxStackElements());
        buf.putShort((short) getMaxSizeOfInstructions());
        buf.putShort((short) getMaxComponentElements());
        buf.putShort((short) getMaxComponentDepth());

        // reset the position to the beginning of the buffer
        buf.flip();

        return buf;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Set the values from data
     */
    @Override
    public void setData(final ByteBuffer data) {
        if (data.remaining() != 32) {
            throw new IllegalArgumentException("Bad size for Maxp table");
        }

        setVersion(data.getInt());
        setNumGlyphs(data.getShort());
        setMaxPoints(data.getShort());
        setMaxContours(data.getShort());
        setMaxComponentPoints(data.getShort());
        setMaxComponentContours(data.getShort());
        setMaxZones(data.getShort());
        setMaxTwilightPoints(data.getShort());
        setMaxStorage(data.getShort());
        setMaxFunctionDefs(data.getShort());
        setMaxInstructionDefs(data.getShort());
        setMaxStackElements(data.getShort());
        setMaxSizeOfInstructions(data.getShort());
        setMaxComponentElements(data.getShort());
        setMaxComponentDepth(data.getShort());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the length of this table
     */
    @Override
    public int getLength() {
        return 32;
    }

    /**
     * Getter for property numGlyphs.
     *
     * @return Value of property numGlyphs.
     */
    public int getNumGlyphs() {
        return this.numGlyphs & 0xFFFF;
    }

    /**
     * Getter for property maxPoints.
     *
     * @return Value of property maxPoints.
     */
    public int getMaxPoints() {
        return this.maxPoints & 0xFFFF;
    }

    /**
     * Getter for property maxContours.
     *
     * @return Value of property maxContours.
     */
    public int getMaxContours() {
        return this.maxContours & 0xFFFF;
    }

    /**
     * Getter for property maxComponentPoints.
     *
     * @return Value of property maxComponentPoints.
     */
    public int getMaxComponentPoints() {
        return this.maxComponentPoints & 0xFFFF;
    }

    /**
     * Getter for property maxComponentContours.
     *
     * @return Value of property maxComponentContours.
     */
    public int getMaxComponentContours() {
        return this.maxComponentContours & 0xFFFF;
    }

    /**
     * Getter for property maxZones.
     *
     * @return Value of property maxZones.
     */
    public int getMaxZones() {
        return this.maxZones & 0xFFFF;
    }

    /**
     * Getter for property maxTwilightPoints.
     *
     * @return Value of property maxTwilightPoints.
     */
    public int getMaxTwilightPoints() {
        return this.maxTwilightPoints & 0xFFFF;
    }

    /**
     * Getter for property maxStorage.
     *
     * @return Value of property maxStorage.
     */
    public int getMaxStorage() {
        return this.maxStorage & 0xFFFF;
    }

    /**
     * Getter for property maxFunctionDefs.
     *
     * @return Value of property maxFunctionDefs.
     */
    public int getMaxFunctionDefs() {
        return this.maxFunctionDefs & 0xFFFF;
    }

    /**
     * Getter for property maxInstructionDefs.
     *
     * @return Value of property maxInstructionDefs.
     */
    public int getMaxInstructionDefs() {
        return this.maxInstructionDefs & 0xFFFF;
    }

    /**
     * Getter for property maxStackElements.
     *
     * @return Value of property maxStackElements.
     */
    public int getMaxStackElements() {
        return this.maxStackElements & 0xFFFF;
    }

    /**
     * Getter for property maxSizeOfInstructions.
     *
     * @return Value of property maxSizeOfInstructions.
     */
    public int getMaxSizeOfInstructions() {
        return this.maxSizeOfInstructions & 0xFFFF;
    }

    /**
     * Getter for property maxComponentElements.
     *
     * @return Value of property maxComponentElements.
     */
    public int getMaxComponentElements() {
        return this.maxComponentElements & 0xFFFF;
    }

    /**
     * Getter for property maxComponentDepth.
     *
     * @return Value of property maxComponentDepth.
     */
    public int getMaxComponentDepth() {
        return this.maxComponentDepth & 0xFFFF;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Create a pretty String
     */
    @Override
    public String toString() {
        final String indent = "    ";

        return indent + "Version          : " + Integer.toHexString(getVersion()) + "\n" +
                indent + "NumGlyphs        : " + getNumGlyphs() + "\n" +
                indent + "MaxPoints        : " + getMaxPoints() + "\n" +
                indent + "MaxContours      : " + getMaxContours() + "\n" +
                indent + "MaxCompPoints    : " + getMaxComponentPoints() + "\n" +
                indent + "MaxCompContours  : " + getMaxComponentContours() + "\n" +
                indent + "MaxZones         : " + getMaxZones() + "\n" +
                indent + "MaxTwilightPoints: " + getMaxTwilightPoints() + "\n" +
                indent + "MaxStorage       : " + getMaxStorage() + "\n" +
                indent + "MaxFuncDefs      : " + getMaxFunctionDefs() + "\n" +
                indent + "MaxInstDefs      : " + getMaxInstructionDefs() + "\n" +
                indent + "MaxStackElements : " + getMaxStackElements() + "\n" +
                indent + "MaxSizeInst      : " + getMaxSizeOfInstructions() + "\n" +
                indent + "MaxCompElements  : " + getMaxComponentElements() + "\n" +
                indent + "MaxCompDepth     : " + getMaxComponentDepth() + "\n";
    }
}
