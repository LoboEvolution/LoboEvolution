/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.svg;

public interface SVGPathElement extends SVGElement, SVGTests, SVGLangSpace, SVGExternalResourcesRequired, SVGStylable,
		SVGTransformable, SVGAnimatedPathData, Drawable {
	SVGAnimatedNumber getPathLength();

	float getTotalLength();

	SVGPoint getPointAtLength(float distance);

	int getPathSegAtLength(float distance);

	SVGPathSegClosePath createSVGPathSegClosePath();

	SVGPathSegMovetoAbs createSVGPathSegMovetoAbs(float x, float y);

	SVGPathSegMovetoRel createSVGPathSegMovetoRel(float x, float y);

	SVGPathSegLinetoAbs createSVGPathSegLinetoAbs(float x, float y);

	SVGPathSegLinetoRel createSVGPathSegLinetoRel(float x, float y);

	SVGPathSegCurvetoCubicAbs createSVGPathSegCurvetoCubicAbs(float x, float y, float x1, float y1, float x2,
			float y2);

	SVGPathSegCurvetoCubicRel createSVGPathSegCurvetoCubicRel(float x, float y, float x1, float y1, float x2,
			float y2);

	SVGPathSegCurvetoQuadraticAbs createSVGPathSegCurvetoQuadraticAbs(float x, float y, float x1, float y1);

	SVGPathSegCurvetoQuadraticRel createSVGPathSegCurvetoQuadraticRel(float x, float y, float x1, float y1);

	SVGPathSegArcAbs createSVGPathSegArcAbs(float x, float y, float r1, float r2, float angle,
			boolean largeArcFlag, boolean sweepFlag);

	SVGPathSegArcRel createSVGPathSegArcRel(float x, float y, float r1, float r2, float angle,
			boolean largeArcFlag, boolean sweepFlag);

	SVGPathSegLinetoHorizontalAbs createSVGPathSegLinetoHorizontalAbs(float x);

	SVGPathSegLinetoHorizontalRel createSVGPathSegLinetoHorizontalRel(float x);

	SVGPathSegLinetoVerticalAbs createSVGPathSegLinetoVerticalAbs(float y);

	SVGPathSegLinetoVerticalRel createSVGPathSegLinetoVerticalRel(float y);

	SVGPathSegCurvetoCubicSmoothAbs createSVGPathSegCurvetoCubicSmoothAbs(float x, float y, float x2, float y2);

	SVGPathSegCurvetoCubicSmoothRel createSVGPathSegCurvetoCubicSmoothRel(float x, float y, float x2, float y2);

	SVGPathSegCurvetoQuadraticSmoothAbs createSVGPathSegCurvetoQuadraticSmoothAbs(float x, float y);

	SVGPathSegCurvetoQuadraticSmoothRel createSVGPathSegCurvetoQuadraticSmoothRel(float x, float y);
}
