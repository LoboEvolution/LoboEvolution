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
package org.loboevolution.html.dom.svg;

/**
 * <p>SVGPaint interface.</p>
 *
 *
 *
 */
public interface SVGPaint extends SVGColor {
	// Paint Types
	/** Constant SVG_PAINTTYPE_UNKNOWN=0 */
    short SVG_PAINTTYPE_UNKNOWN = 0;
	/** Constant SVG_PAINTTYPE_RGBCOLOR=1 */
    short SVG_PAINTTYPE_RGBCOLOR = 1;
	/** Constant SVG_PAINTTYPE_RGBCOLOR_ICCCOLOR=2 */
    short SVG_PAINTTYPE_RGBCOLOR_ICCCOLOR = 2;
	/** Constant SVG_PAINTTYPE_NONE=101 */
    short SVG_PAINTTYPE_NONE = 101;
	/** Constant SVG_PAINTTYPE_CURRENTCOLOR=102 */
    short SVG_PAINTTYPE_CURRENTCOLOR = 102;
	/** Constant SVG_PAINTTYPE_URI_NONE=103 */
    short SVG_PAINTTYPE_URI_NONE = 103;
	/** Constant SVG_PAINTTYPE_URI_CURRENTCOLOR=104 */
    short SVG_PAINTTYPE_URI_CURRENTCOLOR = 104;
	/** Constant SVG_PAINTTYPE_URI_RGBCOLOR=105 */
    short SVG_PAINTTYPE_URI_RGBCOLOR = 105;
	/** Constant SVG_PAINTTYPE_URI_RGBCOLOR_ICCCOLOR=106 */
    short SVG_PAINTTYPE_URI_RGBCOLOR_ICCCOLOR = 106;
	/** Constant SVG_PAINTTYPE_URI=107 */
    short SVG_PAINTTYPE_URI = 107;

	/**
	 * <p>getPaintType.</p>
	 *
	 * @return a short.
	 */
	short getPaintType();

	/**
	 * <p>getUri.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getUri();

	/**
	 * <p>setUri.</p>
	 *
	 * @param uri a {@link java.lang.String} object.
	 */
	void setUri(String uri);

	/**
	 * <p>setPaint.</p>
	 *
	 * @param paintType a short.
	 * @param uri a {@link java.lang.String} object.
	 * @param rgbColor a {@link java.lang.String} object.
	 * @param iccColor a {@link java.lang.String} object.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	void setPaint(short paintType, String uri, String rgbColor, String iccColor) throws SVGException;
}
