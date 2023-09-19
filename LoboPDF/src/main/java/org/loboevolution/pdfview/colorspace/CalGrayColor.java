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

package org.loboevolution.pdfview.colorspace;

import java.awt.color.ColorSpace;
import java.io.IOException;

import org.loboevolution.pdfview.PDFObject;

/**
 * A ColorSpace for calibrated gray
 *
 * Author Mike Wessler
  *
 */
public class CalGrayColor extends ColorSpace {
	final float[] white = {1f, 1f, 1f};
    final float[] black = {0, 0, 0};
    float gamma= 1;
    static final ColorSpace cie= ColorSpace.getInstance(ColorSpace.CS_sRGB);

    /**
     * Create a new Calibrated Gray color space object, given
     * the description in a PDF dictionary.
     *
     * @param obj a dictionary that contains an Array of 3 Numbers
     * for "WhitePoint" and "BlackPoint", and a Number for "Gamma"
     * @throws java.io.IOException if any.
     */
    public CalGrayColor(PDFObject obj) throws IOException {
	// obj is a dictionary that has the following parts:
	// WhitePoint [a b c]
	// BlackPoint [a b c]
	// Gamma a
	super(TYPE_GRAY, 1);
	PDFObject ary= obj.getDictRef("WhitePoint");
	if (ary!=null) {
	    for(int i=0; i<3; i++) {
		this.white[i]= ary.getAt(i).getFloatValue();
	    }
	}
	ary= obj.getDictRef("BlackPoint");
	if (ary!=null) {
	    for(int i=0; i<3; i++) {
		this.black[i]= ary.getAt(i).getFloatValue();
	    }
	}
	PDFObject g= obj.getDictRef("Gamma");
	if (g!=null) {
	    this.gamma= g.getFloatValue();
	}
    }

    /**
     * Create a new calibrated gray color space object, with the
     * default values for black point, white point and gamma
     */
    public CalGrayColor() {
        super(TYPE_GRAY, 1);
    }
    
    /**
     * {@inheritDoc}
     *
     * get the number of components (1).
     */
    @Override public int getNumComponents() {
	return 1;
    }

	/**
	 * {@inheritDoc}
	 *
	 * convert from Calibrated Gray to RGB.
	 */
    @Override
	public float[] toRGB(float[] comp) {
	if (comp.length==1) {
	    float mul= (float)Math.pow(comp[0], this.gamma);
	    float[] xyz = {
		this.white[0]*mul,
		0,
		0};
	    float[] rgb = cie.fromCIEXYZ(xyz);
	    return rgb;
	} else {
	    return this.black;
	}
    }

	/**
	 * {@inheritDoc}
	 *
	 * convert from RGB to Calibrated Gray.  NOT IMPLEMENTED
	 */
    @Override
	public float[] fromRGB(float[] rgbvalue) {
	return new float[1];
    }

	/**
	 * {@inheritDoc}
	 *
	 * convert from CIEXYZ to Calibrated Gray.  NOT IMPLEMENTED
	 */
    @Override
	public float[] fromCIEXYZ(float[] colorvalue) {
	return new float[1];
    }

    /**
     * {@inheritDoc}
     *
     * get the type of this ColorSpace (TYPE_GRAY)
     */
    @Override public int getType() {
	return TYPE_GRAY;
    }

	/**
	 * {@inheritDoc}
	 *
	 * convert from Calibrated Gray to CIEXYZ.  NOT IMPLEMENTED
	 */
    @Override
	public float[] toCIEXYZ(float[] colorvalue) {
	return new float[3];
    }
    
}
