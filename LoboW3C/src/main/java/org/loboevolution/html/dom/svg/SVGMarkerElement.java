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
 * <p>SVGMarkerElement interface.</p>
 *
 *
 *
 */
public interface SVGMarkerElement
		extends SVGElement, SVGLangSpace, SVGExternalResourcesRequired, SVGStylable, SVGFitToViewBox {
	// Marker Unit Types
	/** Constant SVG_MARKERUNITS_UNKNOWN=0 */
    short SVG_MARKERUNITS_UNKNOWN = 0;
	/** Constant SVG_MARKERUNITS_USERSPACEONUSE=1 */
    short SVG_MARKERUNITS_USERSPACEONUSE = 1;
	/** Constant SVG_MARKERUNITS_STROKEWIDTH=2 */
    short SVG_MARKERUNITS_STROKEWIDTH = 2;
	// Marker Orientation Types
	/** Constant SVG_MARKER_ORIENT_UNKNOWN=0 */
    short SVG_MARKER_ORIENT_UNKNOWN = 0;
	/** Constant SVG_MARKER_ORIENT_AUTO=1 */
    short SVG_MARKER_ORIENT_AUTO = 1;
	/** Constant SVG_MARKER_ORIENT_ANGLE=2 */
    short SVG_MARKER_ORIENT_ANGLE = 2;

	/**
	 * <p>getRefX.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getRefX();

	/**
	 * <p>getRefY.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getRefY();

	/**
	 * <p>getMarkerUnits.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getMarkerUnits();

	/**
	 * <p>getMarkerWidth.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getMarkerWidth();

	/**
	 * <p>getMarkerHeight.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getMarkerHeight();

	/**
	 * <p>getOrientType.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getOrientType();

	/**
	 * <p>getOrientAngle.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedAngle} object.
	 */
	SVGAnimatedAngle getOrientAngle();

	/**
	 * <p>setOrientToAuto.</p>
	 */
	void setOrientToAuto();

	/**
	 * <p>setOrientToAngle.</p>
	 *
	 * @param angle a {@link org.loboevolution.html.dom.svg.SVGAngle} object.
	 */
	void setOrientToAngle(SVGAngle angle);
}
