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
 * <p>SVGPathSeg interface.</p>
 *
 *
 *
 */
public interface SVGPathSeg {

	/** Constant PATHSEG_UNKNOWN=0 */
    short PATHSEG_UNKNOWN = 0;

	/** Constant PATHSEG_CLOSEPATH=1 */
    short PATHSEG_CLOSEPATH = 1;

	/** Constant PATHSEG_MOVETO_ABS=2 */
    short PATHSEG_MOVETO_ABS = 2;

	/** Constant PATHSEG_MOVETO_REL=3 */
    short PATHSEG_MOVETO_REL = 3;

	/** Constant PATHSEG_LINETO_ABS=4 */
    short PATHSEG_LINETO_ABS = 4;

	/** Constant PATHSEG_LINETO_REL=5 */
    short PATHSEG_LINETO_REL = 5;

	/** Constant PATHSEG_CURVETO_CUBIC_ABS=6 */
    short PATHSEG_CURVETO_CUBIC_ABS = 6;

	/** Constant PATHSEG_CURVETO_CUBIC_REL=7 */
    short PATHSEG_CURVETO_CUBIC_REL = 7;

	/** Constant PATHSEG_CURVETO_QUADRATIC_ABS=8 */
    short PATHSEG_CURVETO_QUADRATIC_ABS = 8;

	/** Constant PATHSEG_CURVETO_QUADRATIC_REL=9 */
    short PATHSEG_CURVETO_QUADRATIC_REL = 9;

	/** Constant PATHSEG_ARC_ABS=10 */
    short PATHSEG_ARC_ABS = 10;

	/** Constant PATHSEG_ARC_REL=11 */
    short PATHSEG_ARC_REL = 11;

	/** Constant PATHSEG_LINETO_HORIZONTAL_ABS=12 */
    short PATHSEG_LINETO_HORIZONTAL_ABS = 12;

	/** Constant PATHSEG_LINETO_HORIZONTAL_REL=13 */
    short PATHSEG_LINETO_HORIZONTAL_REL = 13;

	/** Constant PATHSEG_LINETO_VERTICAL_ABS=14 */
    short PATHSEG_LINETO_VERTICAL_ABS = 14;

	/** Constant PATHSEG_LINETO_VERTICAL_REL=15 */
    short PATHSEG_LINETO_VERTICAL_REL = 15;

	/** Constant PATHSEG_CURVETO_CUBIC_SMOOTH_ABS=16 */
    short PATHSEG_CURVETO_CUBIC_SMOOTH_ABS = 16;

	/** Constant PATHSEG_CURVETO_CUBIC_SMOOTH_REL=17 */
    short PATHSEG_CURVETO_CUBIC_SMOOTH_REL = 17;

	/** Constant PATHSEG_CURVETO_QUADRATIC_SMOOTH_ABS=18 */
    short PATHSEG_CURVETO_QUADRATIC_SMOOTH_ABS = 18;

	/** Constant PATHSEG_CURVETO_QUADRATIC_SMOOTH_REL=19 */
    short PATHSEG_CURVETO_QUADRATIC_SMOOTH_REL = 19;

	/**
	 * <p>getPathSegType.</p>
	 *
	 * @return a short.
	 */
	short getPathSegType();

	/**
	 * <p>getPathSegTypeAsLetter.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getPathSegTypeAsLetter();
}
