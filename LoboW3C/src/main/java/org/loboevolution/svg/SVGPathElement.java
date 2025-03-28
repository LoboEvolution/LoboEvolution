/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
package org.loboevolution.svg;

/**
 * <p>SVGPathElement interface.</p>
 */
public interface SVGPathElement extends SVGElement, SVGTests, SVGLangSpace, SVGExternalResourcesRequired, SVGStylable,
		SVGTransformable, SVGAnimatedPathData, Drawable {
	/**
	 * <p>getPathLength.</p>
	 *
	 * @return a {@link SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getPathLength();

	/**
	 * <p>getTotalLength.</p>
	 *
	 * @return a float.
	 */
	float getTotalLength();

	/**
	 * <p>getPointAtLength.</p>
	 *
	 * @param distance a float.
	 * @return a {@link SVGPoint} object.
	 */
	SVGPoint getPointAtLength(float distance);

	/**
	 * <p>getPathSegAtLength.</p>
	 *
	 * @param distance a float.
	 * @return a {@link java.lang.Integer} object.
	 */
	int getPathSegAtLength(float distance);

	/**
	 * <p>createSVGPathSegClosePath.</p>
	 *
	 * @return a {@link SVGPathSegClosePath} object.
	 */
	SVGPathSegClosePath createSVGPathSegClosePath();

	/**
	 * <p>createSVGPathSegMovetoAbs.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @return a {@link SVGPathSegMovetoAbs} object.
	 */
	SVGPathSegMovetoAbs createSVGPathSegMovetoAbs(float x, float y);

	/**
	 * <p>createSVGPathSegMovetoRel.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @return a {@link SVGPathSegMovetoRel} object.
	 */
	SVGPathSegMovetoRel createSVGPathSegMovetoRel(float x, float y);

	/**
	 * <p>createSVGPathSegLinetoAbs.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @return a {@link SVGPathSegLinetoAbs} object.
	 */
	SVGPathSegLinetoAbs createSVGPathSegLinetoAbs(float x, float y);

	/**
	 * <p>createSVGPathSegLinetoRel.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @return a {@link SVGPathSegLinetoRel} object.
	 */
	SVGPathSegLinetoRel createSVGPathSegLinetoRel(float x, float y);

	/**
	 * <p>createSVGPathSegCurvetoCubicAbs.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param x1 a float.
	 * @param y1 a float.
	 * @param x2 a float.
	 * @param y2 a float.
	 * @return a {@link SVGPathSegCurvetoCubicAbs} object.
	 */
	SVGPathSegCurvetoCubicAbs createSVGPathSegCurvetoCubicAbs(float x, float y, float x1, float y1, float x2,
			float y2);

	/**
	 * <p>createSVGPathSegCurvetoCubicRel.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param x1 a float.
	 * @param y1 a float.
	 * @param x2 a float.
	 * @param y2 a float.
	 * @return a {@link SVGPathSegCurvetoCubicRel} object.
	 */
	SVGPathSegCurvetoCubicRel createSVGPathSegCurvetoCubicRel(float x, float y, float x1, float y1, float x2,
			float y2);

	/**
	 * <p>createSVGPathSegCurvetoQuadraticAbs.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param x1 a float.
	 * @param y1 a float.
	 * @return a {@link SVGPathSegCurvetoQuadraticAbs} object.
	 */
	SVGPathSegCurvetoQuadraticAbs createSVGPathSegCurvetoQuadraticAbs(float x, float y, float x1, float y1);

	/**
	 * <p>createSVGPathSegCurvetoQuadraticRel.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param x1 a float.
	 * @param y1 a float.
	 * @return a {@link SVGPathSegCurvetoQuadraticRel} object.
	 */
	SVGPathSegCurvetoQuadraticRel createSVGPathSegCurvetoQuadraticRel(float x, float y, float x1, float y1);

	/**
	 * <p>createSVGPathSegArcAbs.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param r1 a float.
	 * @param r2 a float.
	 * @param angle a float.
	 * @param largeArcFlag a boolean.
	 * @param sweepFlag a boolean.
	 * @return a {@link SVGPathSegArcAbs} object.
	 */
	SVGPathSegArcAbs createSVGPathSegArcAbs(float x, float y, float r1, float r2, float angle,
			boolean largeArcFlag, boolean sweepFlag);

	/**
	 * <p>createSVGPathSegArcRel.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param r1 a float.
	 * @param r2 a float.
	 * @param angle a float.
	 * @param largeArcFlag a boolean.
	 * @param sweepFlag a boolean.
	 * @return a {@link SVGPathSegArcRel} object.
	 */
	SVGPathSegArcRel createSVGPathSegArcRel(float x, float y, float r1, float r2, float angle,
			boolean largeArcFlag, boolean sweepFlag);

	/**
	 * <p>createSVGPathSegLinetoHorizontalAbs.</p>
	 *
	 * @param x a float.
	 * @return a {@link SVGPathSegLinetoHorizontalAbs} object.
	 */
	SVGPathSegLinetoHorizontalAbs createSVGPathSegLinetoHorizontalAbs(float x);

	/**
	 * <p>createSVGPathSegLinetoHorizontalRel.</p>
	 *
	 * @param x a float.
	 * @return a {@link SVGPathSegLinetoHorizontalRel} object.
	 */
	SVGPathSegLinetoHorizontalRel createSVGPathSegLinetoHorizontalRel(float x);

	/**
	 * <p>createSVGPathSegLinetoVerticalAbs.</p>
	 *
	 * @param y a float.
	 * @return a {@link SVGPathSegLinetoVerticalAbs} object.
	 */
	SVGPathSegLinetoVerticalAbs createSVGPathSegLinetoVerticalAbs(float y);

	/**
	 * <p>createSVGPathSegLinetoVerticalRel.</p>
	 *
	 * @param y a float.
	 * @return a {@link SVGPathSegLinetoVerticalRel} object.
	 */
	SVGPathSegLinetoVerticalRel createSVGPathSegLinetoVerticalRel(float y);

	/**
	 * <p>createSVGPathSegCurvetoCubicSmoothAbs.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param x2 a float.
	 * @param y2 a float.
	 * @return a {@link SVGPathSegCurvetoCubicSmoothAbs} object.
	 */
	SVGPathSegCurvetoCubicSmoothAbs createSVGPathSegCurvetoCubicSmoothAbs(float x, float y, float x2, float y2);

	/**
	 * <p>createSVGPathSegCurvetoCubicSmoothRel.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param x2 a float.
	 * @param y2 a float.
	 * @return a {@link SVGPathSegCurvetoCubicSmoothRel} object.
	 */
	SVGPathSegCurvetoCubicSmoothRel createSVGPathSegCurvetoCubicSmoothRel(float x, float y, float x2, float y2);

	/**
	 * <p>createSVGPathSegCurvetoQuadraticSmoothAbs.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @return a {@link SVGPathSegCurvetoQuadraticSmoothAbs} object.
	 */
	SVGPathSegCurvetoQuadraticSmoothAbs createSVGPathSegCurvetoQuadraticSmoothAbs(float x, float y);

	/**
	 * <p>createSVGPathSegCurvetoQuadraticSmoothRel.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @return a {@link SVGPathSegCurvetoQuadraticSmoothRel} object.
	 */
	SVGPathSegCurvetoQuadraticSmoothRel createSVGPathSegCurvetoQuadraticSmoothRel(float x, float y);
}
