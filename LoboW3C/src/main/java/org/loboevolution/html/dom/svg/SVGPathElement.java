/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.svg;

/**
 * <p>SVGPathElement interface.</p>
 *
 *
 *
 */
public interface SVGPathElement extends SVGElement, SVGTests, SVGLangSpace, SVGExternalResourcesRequired, SVGStylable,
		SVGTransformable, SVGAnimatedPathData, Drawable {
	/**
	 * <p>getPathLength.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
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
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 */
	SVGPoint getPointAtLength(float distance);

	/**
	 * <p>getPathSegAtLength.</p>
	 *
	 * @param distance a float.
	 * @return a int.
	 */
	int getPathSegAtLength(float distance);

	/**
	 * <p>createSVGPathSegClosePath.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegClosePath} object.
	 */
	SVGPathSegClosePath createSVGPathSegClosePath();

	/**
	 * <p>createSVGPathSegMovetoAbs.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegMovetoAbs} object.
	 */
	SVGPathSegMovetoAbs createSVGPathSegMovetoAbs(float x, float y);

	/**
	 * <p>createSVGPathSegMovetoRel.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegMovetoRel} object.
	 */
	SVGPathSegMovetoRel createSVGPathSegMovetoRel(float x, float y);

	/**
	 * <p>createSVGPathSegLinetoAbs.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegLinetoAbs} object.
	 */
	SVGPathSegLinetoAbs createSVGPathSegLinetoAbs(float x, float y);

	/**
	 * <p>createSVGPathSegLinetoRel.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegLinetoRel} object.
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
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegCurvetoCubicAbs} object.
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
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegCurvetoCubicRel} object.
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
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegCurvetoQuadraticAbs} object.
	 */
	SVGPathSegCurvetoQuadraticAbs createSVGPathSegCurvetoQuadraticAbs(float x, float y, float x1, float y1);

	/**
	 * <p>createSVGPathSegCurvetoQuadraticRel.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param x1 a float.
	 * @param y1 a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegCurvetoQuadraticRel} object.
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
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegArcAbs} object.
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
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegArcRel} object.
	 */
	SVGPathSegArcRel createSVGPathSegArcRel(float x, float y, float r1, float r2, float angle,
			boolean largeArcFlag, boolean sweepFlag);

	/**
	 * <p>createSVGPathSegLinetoHorizontalAbs.</p>
	 *
	 * @param x a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegLinetoHorizontalAbs} object.
	 */
	SVGPathSegLinetoHorizontalAbs createSVGPathSegLinetoHorizontalAbs(float x);

	/**
	 * <p>createSVGPathSegLinetoHorizontalRel.</p>
	 *
	 * @param x a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegLinetoHorizontalRel} object.
	 */
	SVGPathSegLinetoHorizontalRel createSVGPathSegLinetoHorizontalRel(float x);

	/**
	 * <p>createSVGPathSegLinetoVerticalAbs.</p>
	 *
	 * @param y a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegLinetoVerticalAbs} object.
	 */
	SVGPathSegLinetoVerticalAbs createSVGPathSegLinetoVerticalAbs(float y);

	/**
	 * <p>createSVGPathSegLinetoVerticalRel.</p>
	 *
	 * @param y a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegLinetoVerticalRel} object.
	 */
	SVGPathSegLinetoVerticalRel createSVGPathSegLinetoVerticalRel(float y);

	/**
	 * <p>createSVGPathSegCurvetoCubicSmoothAbs.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param x2 a float.
	 * @param y2 a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegCurvetoCubicSmoothAbs} object.
	 */
	SVGPathSegCurvetoCubicSmoothAbs createSVGPathSegCurvetoCubicSmoothAbs(float x, float y, float x2, float y2);

	/**
	 * <p>createSVGPathSegCurvetoCubicSmoothRel.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param x2 a float.
	 * @param y2 a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegCurvetoCubicSmoothRel} object.
	 */
	SVGPathSegCurvetoCubicSmoothRel createSVGPathSegCurvetoCubicSmoothRel(float x, float y, float x2, float y2);

	/**
	 * <p>createSVGPathSegCurvetoQuadraticSmoothAbs.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegCurvetoQuadraticSmoothAbs} object.
	 */
	SVGPathSegCurvetoQuadraticSmoothAbs createSVGPathSegCurvetoQuadraticSmoothAbs(float x, float y);

	/**
	 * <p>createSVGPathSegCurvetoQuadraticSmoothRel.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPathSegCurvetoQuadraticSmoothRel} object.
	 */
	SVGPathSegCurvetoQuadraticSmoothRel createSVGPathSegCurvetoQuadraticSmoothRel(float x, float y);
}
