/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
		SVGTransformable, SVGAnimatedPathData {
	public SVGAnimatedNumber getPathLength();

	public float getTotalLength();

	public SVGPoint getPointAtLength(float distance);

	public int getPathSegAtLength(float distance);

	public SVGPathSegClosePath createSVGPathSegClosePath();

	public SVGPathSegMovetoAbs createSVGPathSegMovetoAbs(float x, float y);

	public SVGPathSegMovetoRel createSVGPathSegMovetoRel(float x, float y);

	public SVGPathSegLinetoAbs createSVGPathSegLinetoAbs(float x, float y);

	public SVGPathSegLinetoRel createSVGPathSegLinetoRel(float x, float y);

	public SVGPathSegCurvetoCubicAbs createSVGPathSegCurvetoCubicAbs(float x, float y, float x1, float y1, float x2,
			float y2);

	public SVGPathSegCurvetoCubicRel createSVGPathSegCurvetoCubicRel(float x, float y, float x1, float y1, float x2,
			float y2);

	public SVGPathSegCurvetoQuadraticAbs createSVGPathSegCurvetoQuadraticAbs(float x, float y, float x1, float y1);

	public SVGPathSegCurvetoQuadraticRel createSVGPathSegCurvetoQuadraticRel(float x, float y, float x1, float y1);

	public SVGPathSegArcAbs createSVGPathSegArcAbs(float x, float y, float r1, float r2, float angle,
			boolean largeArcFlag, boolean sweepFlag);

	public SVGPathSegArcRel createSVGPathSegArcRel(float x, float y, float r1, float r2, float angle,
			boolean largeArcFlag, boolean sweepFlag);

	public SVGPathSegLinetoHorizontalAbs createSVGPathSegLinetoHorizontalAbs(float x);

	public SVGPathSegLinetoHorizontalRel createSVGPathSegLinetoHorizontalRel(float x);

	public SVGPathSegLinetoVerticalAbs createSVGPathSegLinetoVerticalAbs(float y);

	public SVGPathSegLinetoVerticalRel createSVGPathSegLinetoVerticalRel(float y);

	public SVGPathSegCurvetoCubicSmoothAbs createSVGPathSegCurvetoCubicSmoothAbs(float x, float y, float x2, float y2);

	public SVGPathSegCurvetoCubicSmoothRel createSVGPathSegCurvetoCubicSmoothRel(float x, float y, float x2, float y2);

	public SVGPathSegCurvetoQuadraticSmoothAbs createSVGPathSegCurvetoQuadraticSmoothAbs(float x, float y);

	public SVGPathSegCurvetoQuadraticSmoothRel createSVGPathSegCurvetoQuadraticSmoothRel(float x, float y);
}
