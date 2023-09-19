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
 * <p>SVGFETurbulenceElement interface.</p>
 *
 *
 *
 */
public interface SVGFETurbulenceElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Turbulence Types
	/** Constant SVG_TURBULENCE_TYPE_UNKNOWN=0 */
    short SVG_TURBULENCE_TYPE_UNKNOWN = 0;
	/** Constant SVG_TURBULENCE_TYPE_FRACTALNOISE=1 */
    short SVG_TURBULENCE_TYPE_FRACTALNOISE = 1;
	/** Constant SVG_TURBULENCE_TYPE_TURBULENCE=2 */
    short SVG_TURBULENCE_TYPE_TURBULENCE = 2;
	// Stitch Options
	/** Constant SVG_STITCHTYPE_UNKNOWN=0 */
    short SVG_STITCHTYPE_UNKNOWN = 0;
	/** Constant SVG_STITCHTYPE_STITCH=1 */
    short SVG_STITCHTYPE_STITCH = 1;
	/** Constant SVG_STITCHTYPE_NOSTITCH=2 */
    short SVG_STITCHTYPE_NOSTITCH = 2;

	/**
	 * <p>getBaseFrequencyX.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getBaseFrequencyX();

	/**
	 * <p>getBaseFrequencyY.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getBaseFrequencyY();

	/**
	 * <p>getNumOctaves.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedInteger} object.
	 */
	SVGAnimatedInteger getNumOctaves();

	/**
	 * <p>getSeed.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getSeed();

	/**
	 * <p>getStitchTiles.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getStitchTiles();

	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getType();
}
