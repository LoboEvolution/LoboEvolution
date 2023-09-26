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
 * A single simple glyph in a pdf font.
 *
  *
  *
 */
public class GlyfSimple extends Glyf {
    /** the end points of the various contours */
    private short[] contourEndPts;
    
    /** the instructions */
    private byte[] instructions;
    
    /** the flags */
    private byte[] flags;
    
    /** the x coordinates */
    private short[] xCoords;
    
    /** the y coordinates */
    private short[] yCoords;
    
    /**
     * Creates a new instance of a simple glyf
     */
    protected GlyfSimple() {
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Set the data for this glyf.
	 */
    @Override
	public void setData(final ByteBuffer data) {
        // int pos = data.position();
        // byte[] prdata = new byte[data.remaining()];
        // data.get(prdata);
        // HexDump.printData(prdata);
        // data.position(pos);
        
        
        // read the contour end points
        final short[] contourEndPts = new short[getNumContours()];
        for (int i = 0; i < contourEndPts.length; i++) {
            contourEndPts[i] = data.getShort();
        }
        setContourEndPoints(contourEndPts);
        
        // the number of points in the glyf is the number of the end
        // point in the last contour
        final int numPoints = getContourEndPoint(getNumContours() - 1) + 1;
        
        // read the instructions
        final short numInstructions = data.getShort();
        final byte[] instructions = new byte[numInstructions];
        for (int i = 0; i < instructions.length; i++) {
            instructions[i] = data.get();
        }
        setInstructions(instructions);
        
        // read the flags
        final byte[] flags = new byte[numPoints];
        for (int i = 0; i < flags.length; i++) {
            flags[i] = data.get();
            
            // check for repeats
            if ((flags[i] & 0x8) != 0) {
                final byte f = flags[i];
                final int n = (data.get() & 0xff);
                for (int c = 0; c < n; c++) {
                    flags[++i] =  f;
                }
            }
        }
        setFlags(flags);
        
        // read the x coordinates
        final short[] xCoords = new short[numPoints];
        for (int i = 0; i < xCoords.length; i++) {
             if (i > 0) {
                 xCoords[i] = xCoords[i - 1];
             }

             // read this value
            if (xIsByte(i)) {
                int val = (data.get() & 0xff);
                if (!xIsSame(i)) {
                    // the xIsSame bit controls the sign
                    val = -val;
                }
                xCoords[i] += (short)val;
            } else if (!xIsSame(i)) {
                xCoords[i] += data.getShort();
            }
        }
        setXCoords(xCoords);
        
        // read the y coordinates
        final short[] yCoords = new short[numPoints];
        for (int i = 0; i < yCoords.length; i++) {
            if (i > 0) {
                yCoords[i] = yCoords[i - 1];
            } 
            // read this value
            if (yIsByte(i)) {   
                int val = (data.get() & 0xff);
                if (!yIsSame(i)) {
                    // the xIsSame bit controls the sign
                    val = -val;
                }
                yCoords[i] += (short)val;
            } else if (!yIsSame(i)) {
                yCoords[i] += data.getShort();
            }
        }
        setYCoords(yCoords);
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Get the data in this glyf as a byte buffer.  Return the basic
	 * glyf data only, since there is no specific data.  This method returns
	 * the data un-flipped, so subclasses can simply append to the allocated
	 * buffer.
	 */
    @Override
	public ByteBuffer getData() {
        final ByteBuffer buf = super.getData();
        
        // write the contour end points
        for (int i = 0; i < getNumContours(); i++) {
            buf.putShort(getContourEndPoint(i));
        }
        
        // write the instructions
        buf.putShort(getNumInstructions());
        for (int i = 0; i < getNumInstructions(); i++) {
            buf.put(getInstruction(i));
        }
        
        // write the flags
        for (int i = 0; i < getNumPoints(); i++) {
            // check for repeats
            byte r = 0;
            while (i > 0 && (getFlag(i) == getFlag(i - 1))) {
                r++;
                i++;
            }
            if (r > 0) {
                buf.put(r);
            } else {
                buf.put(getFlag(i));
            }
        }
        
        // write the x coordinates
        for (int i = 0; i < getNumPoints(); i++) {
            if (xIsByte(i)) {
                buf.put((byte) getXCoord(i));
            } else if (!xIsSame(i)) {
                buf.putShort(getXCoord(i));
            }
        }
        
        // write the y coordinates
        for (int i = 0; i < getNumPoints(); i++) {
            if (yIsByte(i)) {
                buf.put((byte) getYCoord(i));
            } else if (!yIsSame(i)) {
                buf.putShort(getYCoord(i));
            }
        }
        
        // don't flip the buffer, since it may be used by subclasses
        return buf;
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Get the length of this glyf.
	 */
    @Override
	public short getLength() {
        // start with the length of the superclass
        short length = super.getLength();
        
        // add the length of the end points
        length =  (short)(length + (getNumContours() * 2));
        
        // add the length of the instructions
        length =  (short)(length + (2 + getNumInstructions()));
        
        // add the length of the flags, avoiding repeats
        for (int i = 0; i < getNumPoints(); i++) {
            // check for repeats
            while (i > 0 && (getFlag(i) == getFlag(i - 1)));
            length++;
        }
        
        // add the length of the xCoordinates
        for (int i = 0; i < getNumPoints(); i++) {
            if (xIsByte(i)) {
                length++;
            } else if (!xIsSame(i)) {
                length  = (short)(length +2);
            }
            
            if (yIsByte(i)) {
                length++;
            } else if (!yIsSame(i)) {
                length  = (short)(length +2);
            }
        }
         
        return length;
    }
    
    /**
     * Get the end point of a given contour
     *
     * @param index a int.
     * @return a short.
     */
    public short getContourEndPoint(final int index) {
        return this.contourEndPts[index];
    }
    
    /**
     * Set the number of contours in this glyf
     *
     * @param contourEndPts an array of {@link short} objects.
     */
    protected void setContourEndPoints(final short[] contourEndPts) {
        this.contourEndPts = contourEndPts;
    }
    
    /**
     * Get the number of instructions
     *
     * @return a short.
     */
    public short getNumInstructions() {
        return (short) this.instructions.length;
    }
    
    /**
     * Get a given instruction
     *
     * @param index a int.
     * @return a byte.
     */
    public byte getInstruction(final int index) {
        return this.instructions[index];
    }
    
    /**
     * Set the instructions
     *
     * @param instructions an array of {@link byte} objects.
     */
    protected void setInstructions(final byte[] instructions) {
        this.instructions = instructions;
    }
    
    /**
     * Get the number of points in the glyf
     *
     * @return a short.
     */
    public short getNumPoints() {
        return (short) this.flags.length;
    }
    
    /**
     * Get a given flag
     *
     * @param pointIndex a int.
     * @return a byte.
     */
    public byte getFlag(final int pointIndex) {
        return this.flags[pointIndex];
    }
    
    /**
     * Determine whether the given point is on the curve
     *
     * @param pointIndex a int.
     * @return a boolean.
     */
    public boolean onCurve(final int pointIndex) {
        return ((getFlag(pointIndex) & 0x1) != 0);
    }
    
    /**
     * Determine whether the x value for the given point is byte or short.
     * If true, it is a byte, if false it is a short
     *
     * @param pointIndex a int.
     * @return a boolean.
     */
    protected boolean xIsByte(final int pointIndex) {
        return ((getFlag(pointIndex) & 0x2) != 0);
    }
    
    /**
     * Determine whether the x value for the given point is byte or short.
     * If true, it is a byte, if false it is a short
     *
     * @param pointIndex a int.
     * @return a boolean.
     */
    protected boolean yIsByte(final int pointIndex) {
        return ((getFlag(pointIndex) & 0x4) != 0);
    }
    
    /**
     * Determine whether this flag repeats
     *
     * @param pointIndex a int.
     * @return a boolean.
     */
    protected boolean repeat(final int pointIndex) {
        return ((getFlag(pointIndex) & 0x8) != 0);
    }
    
    /**
     * Determine whether the x value for the given point is the same as
     * the previous value.
     *
     * @param pointIndex a int.
     * @return a boolean.
     */
    protected boolean xIsSame(final int pointIndex) {
        return ((getFlag(pointIndex) & 0x10) != 0);
    }
    
    /**
     * Determine whether the y value for the given point is the same as
     * the previous value.
     *
     * @param pointIndex a int.
     * @return a boolean.
     */
    protected boolean yIsSame(final int pointIndex) {
        return ((getFlag(pointIndex) & 0x20) != 0);
    }
    
    /**
     * Set the flags
     *
     * @param flags an array of {@link byte} objects.
     */
    protected void setFlags(final byte[] flags) {
        this.flags = flags;
    }
    
    /**
     * Get a given x coordinate
     *
     * @param pointIndex a int.
     * @return a short.
     */
    public short getXCoord(final int pointIndex) {
        return this.xCoords[pointIndex];
    }
    
    /**
     * Set the x coordinates
     *
     * @param xCoords an array of {@link short} objects.
     */
    protected void setXCoords(final short[] xCoords) {
        this.xCoords = xCoords;
    }
    
    /**
     * Get a given y coordinate
     *
     * @param pointIndex a int.
     * @return a short.
     */
    public short getYCoord(final int pointIndex) {
        return this.yCoords[pointIndex];
    }
    
    /**
     * Set the x coordinates
     *
     * @param yCoords an array of {@link short} objects.
     */
    protected void setYCoords(final short[] yCoords) {
        this.yCoords = yCoords;
    }
}
