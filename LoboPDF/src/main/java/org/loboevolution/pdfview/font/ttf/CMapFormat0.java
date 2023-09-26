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
 * <p>CMapFormat0 class.</p>
 *
 * Author  jkaplan
  *
 */
public class CMapFormat0 extends CMap {
    
    /**
     * The glyph index array
     */
    private byte[] glyphIndex;
    
    /**
     * Creates a new instance of CMapFormat0
     *
     * @param language a short.
     */
    protected CMapFormat0(final short language) {
        super((short) 0, language);
    
        final byte[] initialIndex = new byte[256];
        for (int i = 0; i < initialIndex.length; i++) {
            initialIndex[i] = (byte) i;
        }
        setMap(initialIndex);
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Get the length of this table
	 */
    @Override
	public short getLength() {
        return (short) 262;
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Map from a byte
	 */
    @Override
	public byte map(final byte src) {
        final int i = 0xff & src;
        
        return this.glyphIndex[i];
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Cannot map from short
	 */
    @Override
	public char map(final char src) {
        if (src  < 0 || src > 255) {
            // out of range
            return (char) 0;
        }
    
        return (char) (map((byte) src) & 0xff);
    }
        
    
	/**
	 * {@inheritDoc}
	 *
	 * Get the src code which maps to the given glyphID
	 */
    @Override
	public char reverseMap(final short glyphID) {
        for (int i = 0; i < this.glyphIndex.length; i++) {
            if ((this.glyphIndex[i] & 0xff) == glyphID) {
                return (char) i;
            }
        }
        
        return (char) 0;
    }
    
    /**
     * Set the entire map
     *
     * @param glyphIndex an array of {@link byte} objects.
     */
    public void setMap(final byte[] glyphIndex) {
        if (glyphIndex.length != 256) {
            throw new IllegalArgumentException("Glyph map must be size 256!");
        }
        
        this.glyphIndex = glyphIndex;
    }
    
    /**
     * Set a single mapping entry
     *
     * @param src a byte.
     * @param dest a byte.
     */
    public void setMap(final byte src, final byte dest) {
        final int i = 0xff & src;
        
        this.glyphIndex[i] = dest;
    }
    
    /**
     * Get the whole map
     *
     * @return an array of {@link byte} objects.
     */
    protected byte[] getMap() {
        return this.glyphIndex;
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Get the data in this map as a ByteBuffer
	 */
    @Override
	public ByteBuffer getData() {
        final ByteBuffer buf = ByteBuffer.allocate(262);
        
        buf.putShort(getFormat());
        buf.putShort(getLength());
        buf.putShort(getLanguage());
        buf.put(getMap());
        
        // reset the position to the beginning of the buffer
        buf.flip();
        
        return buf;
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Read the map in from a byte buffer
	 */
    @Override
	public void setData(final int length, final ByteBuffer data) {
        if (length != 262) {
            throw new IllegalArgumentException("Bad length for CMap format 0");
        }
        
        if (data.remaining() != 256) {
            throw new IllegalArgumentException("Wrong amount of data for CMap format 0");
        }
        
        final byte[] map = new byte[256];
        data.get(map);
        
        setMap(map);
    }
}
