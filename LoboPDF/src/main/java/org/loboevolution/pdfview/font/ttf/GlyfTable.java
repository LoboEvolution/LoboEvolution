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
 * Model the TrueType Glyf table
 *
  *
  *
 */
public class GlyfTable extends TrueTypeTable {
    /** 
     * the glyph data, as either a byte buffer (unparsed) or a 
     * glyph object (parsed)
     */
    private final Object[] glyphs;
    
    /**
     * The glyph location table
     */
    private final LocaTable loca;
    
    /**
     * Creates a new instance of HmtxTable
     *
     * @param ttf a {@link org.loboevolution.pdfview.font.ttf.TrueTypeFont} object.
     */
    protected GlyfTable(TrueTypeFont ttf) {
        super (TrueTypeTable.GLYF_TABLE);
    
        this.loca = (LocaTable) ttf.getTable("loca");
        
        MaxpTable maxp = (MaxpTable) ttf.getTable("maxp");
        int numGlyphs = maxp.getNumGlyphs();
        
        this.glyphs = new Object[numGlyphs]; 
    }
  
    /**
     * Get the glyph at a given index, parsing it as needed
     *
     * @param index a int.
     * @return a {@link org.loboevolution.pdfview.font.ttf.Glyf} object.
     */
    public Glyf getGlyph(int index) {
        Object o = this.glyphs[index];
        if (o == null) {
            return null;
        }
        
        if (o instanceof ByteBuffer) {
            Glyf g = Glyf.getGlyf((ByteBuffer) o);
            this.glyphs[index] = g;
            
            return g;
        } else {
            return (Glyf) o;
        }
    }
  
	/**
	 * {@inheritDoc}
	 *
	 * get the data in this map as a ByteBuffer
	 */
    @Override
	public ByteBuffer getData() {
        int size = getLength();
        
        ByteBuffer buf = ByteBuffer.allocate(size);
        
        // write the offsets
        for (Object o : this.glyphs) {
            if (o == null) {
                continue;
            }

            ByteBuffer glyfData = null;
            if (o instanceof ByteBuffer) {
                glyfData = (ByteBuffer) o;
            } else {
                glyfData = ((Glyf) o).getData();
            }

            glyfData.rewind();
            buf.put(glyfData);
            glyfData.flip();
        }
        
        // reset the start pointer
        buf.flip();
        
        return buf;
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Initialize this structure from a ByteBuffer
	 */
    @Override
	public void setData(ByteBuffer data) {
        for (int i = 0; i < this.glyphs.length; i++) {
            int location = this.loca.getOffset(i);
            int length = this.loca.getSize(i);
            
            if (length == 0) {
                // undefined glyph
                continue;
            }
            
            data.position(location);
            ByteBuffer glyfData = data.slice();
            glyfData.limit(length);
            
            this.glyphs[i] = glyfData;
        }
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Get the length of this table
	 */
    @Override
	public int getLength() {
        int length = 0;

        for (Object o : this.glyphs) {
            if (o == null) {
                continue;
            }

            if (o instanceof ByteBuffer) {
                length += ((ByteBuffer) o).remaining();
            } else {
                length += ((Glyf) o).getLength();
            }
        }
        
        return length;
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Create a pretty String
	 */
    @Override
	public String toString() {
        StringBuilder buf = new StringBuilder();
        String indent = "    ";
     
        buf.append(indent).append("Glyf Table: (").append(this.glyphs.length).append(" glyphs)\n");
        buf.append(indent).append("  Glyf 0: ").append(getGlyph(0));
        
        return buf.toString();
    }
}
