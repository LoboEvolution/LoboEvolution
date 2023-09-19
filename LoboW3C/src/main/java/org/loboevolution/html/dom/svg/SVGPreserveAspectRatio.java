
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


import org.htmlunit.cssparser.dom.DOMException;

/**
 * <p>SVGPreserveAspectRatio interface.</p>
 *
 *
 *
 */
public interface SVGPreserveAspectRatio {
	// Alignment Types
	/** Constant SVG_PRESERVEASPECTRATIO_UNKNOWN=0 */
    short SVG_PRESERVEASPECTRATIO_UNKNOWN = 0;
	/** Constant SVG_PRESERVEASPECTRATIO_NONE=1 */
    short SVG_PRESERVEASPECTRATIO_NONE = 1;
	/** Constant SVG_PRESERVEASPECTRATIO_XMINYMIN=2 */
    short SVG_PRESERVEASPECTRATIO_XMINYMIN = 2;
	/** Constant SVG_PRESERVEASPECTRATIO_XMIDYMIN=3 */
    short SVG_PRESERVEASPECTRATIO_XMIDYMIN = 3;
	/** Constant SVG_PRESERVEASPECTRATIO_XMAXYMIN=4 */
    short SVG_PRESERVEASPECTRATIO_XMAXYMIN = 4;
	/** Constant SVG_PRESERVEASPECTRATIO_XMINYMID=5 */
    short SVG_PRESERVEASPECTRATIO_XMINYMID = 5;
	/** Constant SVG_PRESERVEASPECTRATIO_XMIDYMID=6 */
    short SVG_PRESERVEASPECTRATIO_XMIDYMID = 6;
	/** Constant SVG_PRESERVEASPECTRATIO_XMAXYMID=7 */
    short SVG_PRESERVEASPECTRATIO_XMAXYMID = 7;
	/** Constant SVG_PRESERVEASPECTRATIO_XMINYMAX=8 */
    short SVG_PRESERVEASPECTRATIO_XMINYMAX = 8;
	/** Constant SVG_PRESERVEASPECTRATIO_XMIDYMAX=9 */
    short SVG_PRESERVEASPECTRATIO_XMIDYMAX = 9;
	/** Constant SVG_PRESERVEASPECTRATIO_XMAXYMAX=10 */
    short SVG_PRESERVEASPECTRATIO_XMAXYMAX = 10;
	// Meet-or-slice Types
	/** Constant SVG_MEETORSLICE_UNKNOWN=0 */
    short SVG_MEETORSLICE_UNKNOWN = 0;
	/** Constant SVG_MEETORSLICE_MEET=1 */
    short SVG_MEETORSLICE_MEET = 1;
	/** Constant SVG_MEETORSLICE_SLICE=2 */
    short SVG_MEETORSLICE_SLICE = 2;

	/**
	 * <p>getAlign.</p>
	 *
	 * @return a short.
	 */
	short getAlign();

	/**
	 * <p>setAlign.</p>
	 *
	 * @param align a short.
	 * @throws DOMException if any.
	 */
	void setAlign(short align);

	/**
	 * <p>getMeetOrSlice.</p>
	 *
	 * @return a short.
	 */
	short getMeetOrSlice();

	/**
	 * <p>setMeetOrSlice.</p>
	 *
	 * @param meetOrSlice a short.
	 * @throws DOMException if any.
	 */
	void setMeetOrSlice(short meetOrSlice);
}
