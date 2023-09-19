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
 * <p>SVGColor interface.</p>
 *
 *
 *
 */
public interface SVGColor {
	// Color Types
	/** Constant SVG_COLORTYPE_UNKNOWN=0 */
    short SVG_COLORTYPE_UNKNOWN = 0;
	/** Constant SVG_COLORTYPE_RGBCOLOR=1 */
    short SVG_COLORTYPE_RGBCOLOR = 1;
	/** Constant SVG_COLORTYPE_RGBCOLOR_ICCCOLOR=2 */
    short SVG_COLORTYPE_RGBCOLOR_ICCCOLOR = 2;
	/** Constant SVG_COLORTYPE_CURRENTCOLOR=3 */
    short SVG_COLORTYPE_CURRENTCOLOR = 3;

	/**
	 * <p>getColorType.</p>
	 *
	 * @return a short.
	 */
	short getColorType();

	/**
	 * <p>getICCColor.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGICCColor} object.
	 */
	SVGICCColor getICCColor();

	/**
	 * <p>setRGBColor.</p>
	 *
	 * @param rgbColor a {@link java.lang.String} object.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	void setRGBColor(String rgbColor) throws SVGException;

	/**
	 * <p>setRGBColorICCColor.</p>
	 *
	 * @param rgbColor a {@link java.lang.String} object.
	 * @param iccColor a {@link java.lang.String} object.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	void setRGBColorICCColor(String rgbColor, String iccColor) throws SVGException;

	/**
	 * <p>setColor.</p>
	 *
	 * @param colorType a short.
	 * @param rgbColor a {@link java.lang.String} object.
	 * @param iccColor a {@link java.lang.String} object.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	void setColor(short colorType, String rgbColor, String iccColor) throws SVGException;
}
