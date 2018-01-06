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
package org.loboevolution.html.svgimpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.loboevolution.w3c.svg.SVGAnimatedNumber;
import org.loboevolution.w3c.svg.SVGAnimatedPathData;
import org.loboevolution.w3c.svg.SVGPathElement;
import org.loboevolution.w3c.svg.SVGPathSegArcAbs;
import org.loboevolution.w3c.svg.SVGPathSegArcRel;
import org.loboevolution.w3c.svg.SVGPathSegClosePath;
import org.loboevolution.w3c.svg.SVGPathSegCurvetoCubicAbs;
import org.loboevolution.w3c.svg.SVGPathSegCurvetoCubicRel;
import org.loboevolution.w3c.svg.SVGPathSegCurvetoCubicSmoothAbs;
import org.loboevolution.w3c.svg.SVGPathSegCurvetoCubicSmoothRel;
import org.loboevolution.w3c.svg.SVGPathSegCurvetoQuadraticAbs;
import org.loboevolution.w3c.svg.SVGPathSegCurvetoQuadraticRel;
import org.loboevolution.w3c.svg.SVGPathSegCurvetoQuadraticSmoothAbs;
import org.loboevolution.w3c.svg.SVGPathSegCurvetoQuadraticSmoothRel;
import org.loboevolution.w3c.svg.SVGPathSegLinetoAbs;
import org.loboevolution.w3c.svg.SVGPathSegLinetoHorizontalAbs;
import org.loboevolution.w3c.svg.SVGPathSegLinetoHorizontalRel;
import org.loboevolution.w3c.svg.SVGPathSegLinetoRel;
import org.loboevolution.w3c.svg.SVGPathSegLinetoVerticalAbs;
import org.loboevolution.w3c.svg.SVGPathSegLinetoVerticalRel;
import org.loboevolution.w3c.svg.SVGPathSegList;
import org.loboevolution.w3c.svg.SVGPathSegMovetoAbs;
import org.loboevolution.w3c.svg.SVGPathSegMovetoRel;
import org.loboevolution.w3c.svg.SVGPoint;

/**
 * <p>
 * Paths represent the outline of a shape which can be filled, stroked, used as
 * a clipping path, or any combination of the three. (See Filling, Stroking and
 * Paint Servers and Clipping, Masking and Compositing.)
 * </p>
 * <p>
 * A path is described using the concept of a current point. In an analogy with
 * drawing on paper, the current point can be thought of as the location of the
 * pen. The position of the pen can be changed, and the outline of a shape (open
 * or closed) can be traced by dragging the pen in either straight lines or
 * curves.
 * </p>
 * <p>
 * Paths represent the geometry of the outline of an object, defined in terms of
 * moveto (set a new current point), lineto (draw a straight line), curveto
 * (draw a curve using a cubic Bézier), arc (elliptical or circular arc) and
 * closepath (close the current shape by drawing a line to the last moveto)
 * elements. Compound paths (i.e., a path with multiple subpaths) are possible
 * to allow effects such as "donut holes" in objects.
 * </p>
 * 
 */
public class SVGPathElementImpl extends SVGSVGElementImpl implements SVGPathElement {

	private SVGAnimatedPathData pathData = new SVGAnimatedPathDataImpl();

	public SVGPathElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGPathSegList getPathSegList() {
		ArrayList<String> parts = parts();
		for (int a = 0; a < parts.size(); a++) {
			a = interpretDValue(parts, a);
		}
		return pathData.getPathSegList();
	}

	@Override
	public SVGPathSegList getNormalizedPathSegList() {
		ArrayList<String> parts = parts();
		for (int a = 0; a < parts.size(); a++) {
			a = interpretDValue(parts, a);
		}
		return pathData.getPathSegList();
	}

	@Override
	public SVGPathSegList getAnimatedPathSegList() {
		ArrayList<String> parts = parts();
		for (int a = 0; a < parts.size(); a++) {
			a = interpretDValue(parts, a);
		}
		return pathData.getPathSegList();
	}

	@Override
	public SVGPathSegList getAnimatedNormalizedPathSegList() {
		ArrayList<String> parts = parts();
		for (int a = 0; a < parts.size(); a++) {
			a = interpretDValue(parts, a);
		}
		return pathData.getPathSegList();
	}

	@Override
	public SVGAnimatedNumber getPathLength() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getTotalLength() {
		return pathData.getPathSegList().getNumberOfItems();
	}

	@Override
	public SVGPoint getPointAtLength(float distance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPathSegAtLength(float distance) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SVGPathSegClosePath createSVGPathSegClosePath() {
		SVGPathSegClosePath pathSeg = new SVGPathSegClosePathImpl();
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegMovetoAbs createSVGPathSegMovetoAbs(float x, float y) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), x, y, 0, 0, 0);
		SVGPathSegMovetoAbs pathSeg = new SVGPathSegMovetoAbsImpl(viewbox.getX(), viewbox.getY());
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegMovetoRel createSVGPathSegMovetoRel(float x, float y) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), x, y, 0, 0, 0);
		SVGPathSegMovetoRel pathSeg = new SVGPathSegMovetoRelImpl(viewbox.getX(), viewbox.getY());
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegLinetoAbs createSVGPathSegLinetoAbs(float x, float y) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), x, y, 0, 0, 0);
		SVGPathSegLinetoAbs pathSeg = new SVGPathSegLinetoAbsImpl(viewbox.getX(), viewbox.getY());
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegLinetoRel createSVGPathSegLinetoRel(float x, float y) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), x, y, 0, 0, 0);
		SVGPathSegLinetoRel pathSeg = new SVGPathSegLinetoRelImpl(viewbox.getX(), viewbox.getY());
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegCurvetoCubicAbs createSVGPathSegCurvetoCubicAbs(float x1, float y1, float x2, float y2, float x,
			float y) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), x, y, 0, 0, 0);
		SVGPathSegCurvetoCubicAbs pathSeg = new SVGPathSegCurvetoCubicAbsImpl(x1, y1, x2, y2, viewbox.getX(),
				viewbox.getY());
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegCurvetoCubicRel createSVGPathSegCurvetoCubicRel(float x, float y, float x1, float y1, float x2,
			float y2) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), x, y, 0, 0, 0);
		SVGPathSegCurvetoCubicRel pathSeg = new SVGPathSegCurvetoCubicRelImpl(viewbox.getX(), viewbox.getY(), x1, y1,
				x2, y2);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegCurvetoQuadraticAbs createSVGPathSegCurvetoQuadraticAbs(float x, float y, float x1, float y1) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), x, y, 0, 0, 0);
		SVGPathSegCurvetoQuadraticAbs pathSeg = new SVGPathSegCurvetoQuadraticAbsImpl(viewbox.getX(), viewbox.getY(),
				x1, y1);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegCurvetoQuadraticRel createSVGPathSegCurvetoQuadraticRel(float x, float y, float x1, float y1) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), x, y, 0, 0, 0);
		SVGPathSegCurvetoQuadraticRel pathSeg = new SVGPathSegCurvetoQuadraticRelImpl(viewbox.getX(), viewbox.getY(),
				x1, y1);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegArcAbs createSVGPathSegArcAbs(float x, float y, float r1, float r2, float angle,
			boolean largeArcFlag, boolean sweepFlag) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), x, y, 0, 0, 0);
		SVGPathSegArcAbs pathSeg = new SVGPathSegArcAbsImpl(viewbox.getX(), viewbox.getY(), r1, r2, angle, largeArcFlag,
				sweepFlag);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegArcRel createSVGPathSegArcRel(float x, float y, float r1, float r2, float angle,
			boolean largeArcFlag, boolean sweepFlag) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), x, y, 0, 0, 0);
		SVGPathSegArcRel pathSeg = new SVGPathSegArcRelImpl(viewbox.getX(), viewbox.getY(), r1, r2, angle, largeArcFlag,
				sweepFlag);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegLinetoHorizontalAbs createSVGPathSegLinetoHorizontalAbs(float x) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), x, 0, 0, 0, 0);
		SVGPathSegLinetoHorizontalAbs pathSeg = new SVGPathSegLinetoHorizontalAbsImpl(viewbox.getX());
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegLinetoHorizontalRel createSVGPathSegLinetoHorizontalRel(float x) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), x, 0, 0, 0, 0);
		SVGPathSegLinetoHorizontalRel pathSeg = new SVGPathSegLinetoHorizontalRelImpl(viewbox.getX());
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegLinetoVerticalAbs createSVGPathSegLinetoVerticalAbs(float y) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), 0, y, 0, 0, 0);
		SVGPathSegLinetoVerticalAbs pathSeg = new SVGPathSegLinetoVerticalAbsImpl(viewbox.getY());
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegLinetoVerticalRel createSVGPathSegLinetoVerticalRel(float y) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), 0, y, 0, 0, 0);
		SVGPathSegLinetoVerticalRel pathSeg = new SVGPathSegLinetoVerticalRelImpl(viewbox.getY());
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegCurvetoCubicSmoothAbs createSVGPathSegCurvetoCubicSmoothAbs(float x2, float y2, float x, float y) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), x, y, 0, 0, 0);
		SVGPathSegCurvetoCubicSmoothAbs pathSeg = new SVGPathSegCurvetoCubicSmoothAbsImpl(x2, y2, viewbox.getX(),
				viewbox.getY());
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegCurvetoCubicSmoothRel createSVGPathSegCurvetoCubicSmoothRel(float x2, float y2, float x, float y) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), x, y, 0, 0, 0);
		SVGPathSegCurvetoCubicSmoothRel pathSeg = new SVGPathSegCurvetoCubicSmoothRelImpl(x2, y2, viewbox.getX(),
				viewbox.getY());
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegCurvetoQuadraticSmoothAbs createSVGPathSegCurvetoQuadraticSmoothAbs(float x, float y) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), x, y, 0, 0, 0);
		SVGPathSegCurvetoQuadraticSmoothAbs pathSeg = new SVGPathSegCurvetoQuadraticSmoothAbsImpl(viewbox.getX(),
				viewbox.getY());
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	@Override
	public SVGPathSegCurvetoQuadraticSmoothRel createSVGPathSegCurvetoQuadraticSmoothRel(float x, float y) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(this.getOwnerSVGElement(), x, y, 0, 0, 0);
		SVGPathSegCurvetoQuadraticSmoothRel pathSeg = new SVGPathSegCurvetoQuadraticSmoothRelImpl(viewbox.getX(),
				viewbox.getY());
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	private int interpretDValue(ArrayList<String> parts, int i) {

		String part = parts.get(i);
		float x;
		float y;
		float x1;
		float y1;
		float x2;
		float y2;
		float angle;
		boolean f1;
		boolean f2;

		switch (part) {
		case "A":
			x1 = convertToFloat(parts.get(i + 1));
			y1 = convertToFloat(parts.get(i + 2));
			angle = convertToFloat(parts.get(i + 3));
			f1 = convertToBoolean(parts.get(i + 4));
			f2 = convertToBoolean(parts.get(i + 5));
			x = convertToFloat(parts.get(i + 6));
			y = convertToFloat(parts.get(i + 7));
			createSVGPathSegArcAbs(x, y, x1, y1, angle, f1, f2);
			i += 7;
			break;
		case "a":
			x1 = convertToFloat(parts.get(i + 1));
			y1 = convertToFloat(parts.get(i + 2));
			angle = convertToFloat(parts.get(i + 3));
			f1 = convertToBoolean(parts.get(i + 4));
			f2 = convertToBoolean(parts.get(i + 5));
			x = convertToFloat(parts.get(i + 6));
			y = convertToFloat(parts.get(i + 7));
			createSVGPathSegArcRel(x, y, x1, y1, angle, f1, f2);
			i += 7;
			break;
		case "C":
			x1 = convertToFloat(parts.get(i + 1));
			y1 = convertToFloat(parts.get(i + 2));
			x2 = convertToFloat(parts.get(i + 3));
			y2 = convertToFloat(parts.get(i + 4));
			x = convertToFloat(parts.get(i + 5));
			y = convertToFloat(parts.get(i + 6));
			createSVGPathSegCurvetoCubicAbs(x1, y1, x2, y2, x, y);
			i += 6;
			break;
		case "c":
			x1 = convertToFloat(parts.get(i + 1));
			y1 = convertToFloat(parts.get(i + 2));
			x2 = convertToFloat(parts.get(i + 3));
			y2 = convertToFloat(parts.get(i + 4));
			x = convertToFloat(parts.get(i + 5));
			y = convertToFloat(parts.get(i + 6));
			createSVGPathSegCurvetoCubicRel(x1, y1, x2, y2, x, y);
			i += 6;
			break;
		case "H":
			x = convertToFloat(parts.get(i + 1));
			createSVGPathSegLinetoHorizontalAbs(x);
			i += 1;
			break;
		case "h":
			x = convertToFloat(parts.get(i + 1));
			createSVGPathSegLinetoHorizontalRel(x);
			i += 1;
			break;
		case "L":
			x = convertToFloat(parts.get(i + 1));
			y = convertToFloat(parts.get(i + 2));
			createSVGPathSegLinetoAbs(x, y);
			i += 2;
			break;
		case "l":
			x = convertToFloat(parts.get(i + 1));
			y = convertToFloat(parts.get(i + 2));
			createSVGPathSegLinetoRel(x, y);
			i += 2;
			break;
		case "M":
			x = convertToFloat(parts.get(i + 1));
			y = convertToFloat(parts.get(i + 2));
			createSVGPathSegMovetoAbs(x, y);
			i += 2;
			break;
		case "m":
			x = convertToFloat(parts.get(i + 1));
			y = convertToFloat(parts.get(i + 2));
			createSVGPathSegMovetoRel(x, y);
			i += 2;
			break;
		case "Q":
			x = convertToFloat(parts.get(i + 1));
			y = convertToFloat(parts.get(i + 2));
			x1 = convertToFloat(parts.get(i + 3));
			y1 = convertToFloat(parts.get(i + 4));
			createSVGPathSegCurvetoQuadraticAbs(x, y, x1, y1);
			i += 4;
			break;
		case "q":
			x = convertToFloat(parts.get(i + 1));
			y = convertToFloat(parts.get(i + 2));
			x1 = convertToFloat(parts.get(i + 3));
			y1 = convertToFloat(parts.get(i + 4));
			createSVGPathSegCurvetoQuadraticRel(x, y, x1, y1);
			i += 4;
			break;
		case "S":
			x2 = convertToFloat(parts.get(i + 1));
			y2 = convertToFloat(parts.get(i + 2));
			x = convertToFloat(parts.get(i + 3));
			y = convertToFloat(parts.get(i + 4));
			createSVGPathSegCurvetoCubicSmoothAbs(x2, y2, x, y);
			i += 4;
			break;
		case "s":
			x2 = convertToFloat(parts.get(i + 1));
			y2 = convertToFloat(parts.get(i + 2));
			x = convertToFloat(parts.get(i + 3));
			y = convertToFloat(parts.get(i + 4));
			createSVGPathSegCurvetoCubicSmoothRel(x2, y2, x, y);
			i += 4;
			break;
		case "T":
			x = convertToFloat(parts.get(i + 1));
			y = convertToFloat(parts.get(i + 2));
			createSVGPathSegCurvetoQuadraticSmoothAbs(x, y);
			i += 4;
			break;
		case "t":
			x = convertToFloat(parts.get(i + 1));
			y = convertToFloat(parts.get(i + 2));
			createSVGPathSegCurvetoQuadraticSmoothRel(x, y);
			i += 4;
			break;
		case "V":
			y = convertToFloat(parts.get(i + 1));
			createSVGPathSegLinetoVerticalAbs(y);
			i += 1;
			break;
		case "v":
			y = convertToFloat(parts.get(i + 1));
			createSVGPathSegLinetoVerticalRel(y);
			i += 1;
			break;
		case "z":
			createSVGPathSegClosePath();
			break;
		default:
			break;
		}
		return i;
	}

	private float convertToFloat(String value) {
		try {
			float f = Float.parseFloat(value);
			return f;
		} catch (NumberFormatException e) {
			return 0.0f;
		}
	}

	private boolean convertToBoolean(String value) {
		try {
			float flt = Float.parseFloat(value);
			return (flt == 1);
		} catch (Exception e) {
			return false;
		}
	}

	private ArrayList<String> parts() {

		final Matcher matchPathCmd = Pattern
				.compile("([MmLlHhVvAaQqTtCcSsZz])|([-+]?((\\d*\\.\\d+)|(\\d+))([eE][-+]?\\d+)?)").matcher(getD());
		LinkedList<String> tokens = new LinkedList<String>();
		while (matchPathCmd.find()) {
			tokens.addLast(matchPathCmd.group());
		}

		ArrayList<String> list = new ArrayList<String>();

		char charCmd = 'Z';
		while (!tokens.isEmpty()) {
			String curToken = tokens.removeFirst();
			char initChar = curToken.charAt(0);
			if (initChar >= 'A' && initChar <= 'Z' || initChar >= 'a' && initChar <= 'z') {
				charCmd = initChar;
			} else {
				tokens.addFirst(curToken);
			}

			String curCmd = String.valueOf(charCmd);

			switch (curCmd) {
			case "M":
				list.add(curCmd);
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				curCmd = "L";
				break;
			case "m":
				list.add(curCmd);
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				curCmd = "l";
				break;
			case "L":
				list.add(curCmd);
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				break;
			case "l":
				list.add(curCmd);
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				break;
			case "H":
				list.add(curCmd);
				list.add(nextString(tokens));
				break;
			case "h":
				list.add(curCmd);
				list.add(nextString(tokens));
				break;
			case "V":
				list.add(curCmd);
				list.add(nextString(tokens));
				break;
			case "v":
				list.add(curCmd);
				list.add(nextString(tokens));
				break;
			case "A":
			case "a":
				list.add(curCmd);
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				break;
			case "Q":
				list.add(curCmd);
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				break;
			case "q":
				list.add(curCmd);
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				break;
			case "T":
				list.add(curCmd);
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				break;
			case "t":
				list.add(curCmd);
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				break;
			case "C":
				list.add(curCmd);
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				break;
			case "c":
				list.add(curCmd);
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				break;
			case "S":
				list.add(curCmd);
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				break;
			case "s":
				list.add(curCmd);
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				list.add(nextString(tokens));
				break;
			case "Z":
			case "z":
				list.add(curCmd);

				break;
			default:
				throw new RuntimeException("Invalid path element");
			}
		}
		return list;
	}

	static protected String nextString(LinkedList<String> l) {
		return l.removeFirst();
	}
}