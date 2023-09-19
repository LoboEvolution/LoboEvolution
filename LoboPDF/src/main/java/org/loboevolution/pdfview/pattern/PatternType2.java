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

package org.loboevolution.pdfview.pattern;

import java.io.IOException;
import java.util.Map;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFPaint;

/**
 * A type 1 (tiling) pattern
 *
  *
  *
 */
public class PatternType2 extends PDFPattern {
        
    /** the shader */
    private PDFShader shader;
        
    /**
     * Creates a new instance of PatternType1
     */
    public PatternType2() {
        super(2);
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Parse the pattern from the PDFObject
	 *
	 * Note the resources passed in are ignored...
	 */
    @Override
	protected void parse(PDFObject patternObj, Map rsrc) throws IOException
    {
        this.shader = PDFShader.getShader(patternObj.getDictRef("Shading"), rsrc);        
    }
    
	/**
	 * {@inheritDoc}
	 *
	 * Create a PDFPaint from this pattern and set of components.
	 * This creates a buffered image of this pattern using
	 * the given paint, then uses that image to create the correct
	 * TexturePaint to use in the PDFPaint.
	 */
    @Override
	public PDFPaint getPaint(PDFPaint basePaint) {
    	return shader.getPaint();
    }    
}
