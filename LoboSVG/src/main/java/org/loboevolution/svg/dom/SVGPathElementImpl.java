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

package org.loboevolution.svg.dom;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.svg.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>SVGPathElementImpl class.</p>
 */
@Slf4j
public class SVGPathElementImpl extends SVGGraphic implements SVGPathElement {

	private SVGPathSegList pathSegList;

	/**
	 * <p>Constructor for SVGPathElementImpl.</p>
	 *
	 * @param element a {@link HTMLElement} object.
	 */
	public SVGPathElementImpl(final HTMLElement element) {
		super(element);
	}

	@Override
	public SVGRect getBBox() {
		final Shape shape = createShape(null);
		return new SVGRectImpl(shape.getBounds2D());
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegList getPathSegList() {
		return pathSegList;
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegList getNormalizedPathSegList() {
		return pathSegList;
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegList getAnimatedPathSegList() {
		return pathSegList;
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegList getAnimatedNormalizedPathSegList() {
		return pathSegList;
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedNumber getPathLength() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public float getTotalLength() {
		return pathSegList.getNumberOfItems();
	}

	/** {@inheritDoc} */
	@Override
	public SVGPoint getPointAtLength(final float distance) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public int getPathSegAtLength(final float distance) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegClosePath createSVGPathSegClosePath() {
		return new SVGPathSegClosePathImpl();
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegMovetoAbs createSVGPathSegMovetoAbs(final float x, final float y) {
		return new SVGPathSegMovetoAbsImpl(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegMovetoRel createSVGPathSegMovetoRel(final float x, final float y) {
		return new SVGPathSegMovetoRelImpl(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegLinetoAbs createSVGPathSegLinetoAbs(final float x, final float y) {
		return new SVGPathSegLinetoAbsImpl(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegLinetoRel createSVGPathSegLinetoRel(final float x, final float y) {
		return new SVGPathSegLinetoRelImpl(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegCurvetoCubicAbs createSVGPathSegCurvetoCubicAbs(final float x, final float y, final float x1, final float y1, final float x2, final float y2) {
		return new SVGPathSegCurvetoCubicAbsImpl(x, y, x1, y1, x2, y2);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegCurvetoCubicRel createSVGPathSegCurvetoCubicRel(final float x, final float y, final float x1, final float y1, final float x2, final float y2) {
		return new SVGPathSegCurvetoCubicRelImpl(x, y, x1, y1, x2, y2);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegCurvetoQuadraticAbs createSVGPathSegCurvetoQuadraticAbs(final float x, final float y, final float x1, final float y1) {
		return new SVGPathSegCurvetoQuadraticAbsImpl(x, y, x1, y1);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegCurvetoQuadraticRel createSVGPathSegCurvetoQuadraticRel(final float x, final float y, final float x1, final float y1) {
		return new SVGPathSegCurvetoQuadraticRelImpl(x, y, x1, y1);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegArcAbs createSVGPathSegArcAbs(final float x, final float y, final float r1, final float r2, final float angle,
                                                   final boolean largeArcFlag, final boolean sweepFlag) {
		return new SVGPathSegArcAbsImpl(x, y, r1, r2, angle, largeArcFlag, sweepFlag);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegArcRel createSVGPathSegArcRel(final float x, final float y, final float r1, final float r2, final float angle,
                                                   final boolean largeArcFlag, final boolean sweepFlag) {
		return new SVGPathSegArcRelImpl(x, y, r1, r2, angle, largeArcFlag, sweepFlag);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegLinetoHorizontalAbs createSVGPathSegLinetoHorizontalAbs(final float x) {
		return new SVGPathSegLinetoHorizontalAbsImpl(x);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegLinetoHorizontalRel createSVGPathSegLinetoHorizontalRel(final float x) {
		return new SVGPathSegLinetoHorizontalRelImpl(x);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegLinetoVerticalAbs createSVGPathSegLinetoVerticalAbs(final float y) {
		return new SVGPathSegLinetoVerticalAbsImpl(y);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegLinetoVerticalRel createSVGPathSegLinetoVerticalRel(final float y) {
		return new SVGPathSegLinetoVerticalRelImpl(y);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegCurvetoCubicSmoothAbs createSVGPathSegCurvetoCubicSmoothAbs(final float x, final float y, final float x2, final float y2) {
		return new SVGPathSegCurvetoCubicSmoothAbsImpl(x, y, x2, y2);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegCurvetoCubicSmoothRel createSVGPathSegCurvetoCubicSmoothRel(final float x, final float y, final float x2, final float y2) {
		return new SVGPathSegCurvetoCubicSmoothRelImpl(x, y, x2, y2);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegCurvetoQuadraticSmoothAbs createSVGPathSegCurvetoQuadraticSmoothAbs(final float x, final float y) {
		return new SVGPathSegCurvetoQuadraticSmoothAbsImpl(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public SVGPathSegCurvetoQuadraticSmoothRel createSVGPathSegCurvetoQuadraticSmoothRel(final float x, final float y) {
		return new SVGPathSegCurvetoQuadraticSmoothRelImpl(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public void draw(final Graphics2D graphics) {
		final String attribute = getAttribute("d");
		if(Strings.isNotBlank(attribute)) {
			constructPathSegList(attribute);
			final Shape shape = createShape(null);
			animate(this);
			drawable(graphics, shape);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Shape createShape(final AffineTransform transform) {
		final GeneralPath path = new GeneralPath();
		float lastX = 0;
		float lastY = 0;
		Point2D lastControlPoint = null;
		final int numPathSegs = pathSegList.getNumberOfItems();
		boolean startOfSubPath = true;
		SVGPoint subPathStartPoint = null;
		final SVGPointList points = new SVGPointListImpl();

		for (int i = 0; i < numPathSegs; i++) {
			SVGPathSeg seg = pathSegList.getItem(i);

			if (startOfSubPath) {
				final boolean isMoved  = "M".equalsIgnoreCase(seg.getPathSegTypeAsLetter());
				while (!isMoved && i < numPathSegs) {
					i++;
					seg = pathSegList.getItem(i);
				}

				if (isMoved) {
					if (seg.getPathSegType() == SVGPathSeg.PATHSEG_MOVETO_REL) {
						final float x = ((SVGPathSegMovetoRel) seg).getX();
						final float y = ((SVGPathSegMovetoRel) seg).getY();
						path.moveTo(x + lastX, y + lastY);
						subPathStartPoint = new SVGPointImpl(x + lastX, y + lastY);
						points.appendItem(subPathStartPoint);
						lastX += x;
						lastY += y;
					} else if (seg.getPathSegType() == SVGPathSeg.PATHSEG_MOVETO_ABS) {
						final float x = ((SVGPathSegMovetoAbs) seg).getX();
						final float y = ((SVGPathSegMovetoAbs) seg).getY();
						path.moveTo(x, y);
						subPathStartPoint = new SVGPointImpl(x, y);
						points.appendItem(subPathStartPoint);
						lastX = x;
						lastY = y;
					}
					startOfSubPath = false;
				}

			} else {
				switch (seg.getPathSegType()) {
					case SVGPathSeg.PATHSEG_CLOSEPATH: {
						path.closePath();
						lastControlPoint = null;
						startOfSubPath = true;
						if (subPathStartPoint != null) {
							points.appendItem(subPathStartPoint);
							lastX = subPathStartPoint.getX();
							lastY = subPathStartPoint.getY();
							subPathStartPoint = null;
						}
						break;
					}

					case SVGPathSeg.PATHSEG_MOVETO_ABS: {
						final float x = ((SVGPathSegMovetoAbs) seg).getX();
						final float y = ((SVGPathSegMovetoAbs) seg).getY();
						path.moveTo(x, y);
						lastX = x;
						lastY = y;
						lastControlPoint = null;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}

					case SVGPathSeg.PATHSEG_MOVETO_REL: {
						final float x = ((SVGPathSegMovetoRel) seg).getX();
						final float y = ((SVGPathSegMovetoRel) seg).getY();
						path.moveTo(x + lastX, y + lastY);
						lastX += x;
						lastY += y;
						lastControlPoint = null;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}

					case SVGPathSeg.PATHSEG_LINETO_ABS: {
						final float x = ((SVGPathSegLinetoAbs) seg).getX();
						final float y = ((SVGPathSegLinetoAbs) seg).getY();
						path.lineTo(x, y);
						lastX = x;
						lastY = y;
						lastControlPoint = null;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}

					case SVGPathSeg.PATHSEG_LINETO_REL: {
						final float x = ((SVGPathSegLinetoRel) seg).getX();
						final float y = ((SVGPathSegLinetoRel) seg).getY();
						path.lineTo(x + lastX, y + lastY);
						lastX += x;
						lastY += y;
						lastControlPoint = null;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}

					case SVGPathSeg.PATHSEG_LINETO_HORIZONTAL_ABS: {
						final float x = ((SVGPathSegLinetoHorizontalAbs) seg).getX();
						path.lineTo(x, lastY);
						lastX = x;
						lastControlPoint = null;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}

					case SVGPathSeg.PATHSEG_LINETO_HORIZONTAL_REL: {
						final float x = ((SVGPathSegLinetoHorizontalRel) seg).getX();
						path.lineTo(x + lastX, lastY);
						lastX += x;
						lastControlPoint = null;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}

					case SVGPathSeg.PATHSEG_LINETO_VERTICAL_ABS: {
						final float y = ((SVGPathSegLinetoVerticalAbs) seg).getY();
						path.lineTo(lastX, y);
						lastY = y;
						lastControlPoint = null;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}

					case SVGPathSeg.PATHSEG_LINETO_VERTICAL_REL: {
						final float y = ((SVGPathSegLinetoVerticalRel) seg).getY();
						path.lineTo(lastX, y + lastY);
						lastY += y;
						lastControlPoint = null;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}

					case SVGPathSeg.PATHSEG_CURVETO_CUBIC_ABS: {
						final float x = ((SVGPathSegCurvetoCubicAbs) seg).getX();
						final float y = ((SVGPathSegCurvetoCubicAbs) seg).getY();
						final float x1 = ((SVGPathSegCurvetoCubicAbs) seg).getX1();
						final float y1 = ((SVGPathSegCurvetoCubicAbs) seg).getY1();
						final float x2 = ((SVGPathSegCurvetoCubicAbs) seg).getX2();
						final float y2 = ((SVGPathSegCurvetoCubicAbs) seg).getY2();
						path.curveTo(x1, y1, x2, y2, x, y);
						lastControlPoint = new Point2D.Float(x2, y2);
						lastX = x;
						lastY = y;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}

					case SVGPathSeg.PATHSEG_CURVETO_CUBIC_REL: {
						final float x = ((SVGPathSegCurvetoCubicRel) seg).getX();
						final float y = ((SVGPathSegCurvetoCubicRel) seg).getY();
						final float x1 = ((SVGPathSegCurvetoCubicRel) seg).getX1();
						final float y1 = ((SVGPathSegCurvetoCubicRel) seg).getY1();
						final float x2 = ((SVGPathSegCurvetoCubicRel) seg).getX2();
						final float y2 = ((SVGPathSegCurvetoCubicRel) seg).getY2();
						path.curveTo(lastX + x1, lastY + y1, lastX + x2, lastY + y2, lastX + x, lastY + y);
						lastControlPoint = new Point2D.Float(lastX + x2, lastY + y2);
						lastX += x;
						lastY += y;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}

					case SVGPathSeg.PATHSEG_CURVETO_CUBIC_SMOOTH_ABS: {
						final float x = ((SVGPathSegCurvetoCubicSmoothAbs) seg).getX();
						final float y = ((SVGPathSegCurvetoCubicSmoothAbs) seg).getY();
						final float x2 = ((SVGPathSegCurvetoCubicSmoothAbs) seg).getX2();
						final float y2 = ((SVGPathSegCurvetoCubicSmoothAbs) seg).getY2();
						if (lastControlPoint == null) {
							lastControlPoint = new Point2D.Float(lastX, lastY);
						}
						path.curveTo(2 * lastX - (float) lastControlPoint.getX(),
								2 * lastY - (float) lastControlPoint.getY(), x2, y2, x, y);
						lastControlPoint = new Point2D.Float(x2, y2);
						lastX = x;
						lastY = y;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}

					case SVGPathSeg.PATHSEG_CURVETO_CUBIC_SMOOTH_REL: {
						final float x = ((SVGPathSegCurvetoCubicSmoothRel) seg).getX();
						final float y = ((SVGPathSegCurvetoCubicSmoothRel) seg).getY();
						final float x2 = ((SVGPathSegCurvetoCubicSmoothRel) seg).getX2();
						final float y2 = ((SVGPathSegCurvetoCubicSmoothRel) seg).getY2();
						if (lastControlPoint == null) {
							lastControlPoint = new Point2D.Float(lastX, lastY);
						}
						path.curveTo(2 * lastX - (float) lastControlPoint.getX(),
								2 * lastY - (float) lastControlPoint.getY(), lastX + x2, lastY + y2, lastX + x, lastY + y);
						lastControlPoint = new Point2D.Float(lastX + x2, lastY + y2);
						lastX += x;
						lastY += y;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}

					case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_ABS: {
						final float x = ((SVGPathSegCurvetoQuadraticAbs) seg).getX();
						final float y = ((SVGPathSegCurvetoQuadraticAbs) seg).getY();
						final float x1 = ((SVGPathSegCurvetoQuadraticAbs) seg).getX1();
						final float y1 = ((SVGPathSegCurvetoQuadraticAbs) seg).getY1();
						path.quadTo(x1, y1, x, y);
						lastControlPoint = new Point2D.Float(x1, y1);
						lastX = x;
						lastY = y;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}

					case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_REL: {
						final float x = ((SVGPathSegCurvetoQuadraticRel) seg).getX();
						final float y = ((SVGPathSegCurvetoQuadraticRel) seg).getY();
						final float x1 = ((SVGPathSegCurvetoQuadraticRel) seg).getX1();
						final float y1 = ((SVGPathSegCurvetoQuadraticRel) seg).getY1();
						path.quadTo(lastX + x1, lastY + y1, lastX + x, lastY + y);
						lastControlPoint = new Point2D.Float(lastX + x1, lastY + y1);
						lastX += x;
						lastY += y;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}

					case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_SMOOTH_ABS: {
						final float x = ((SVGPathSegCurvetoQuadraticSmoothAbs) seg).getX();
						final float y = ((SVGPathSegCurvetoQuadraticSmoothAbs) seg).getY();
						if (lastControlPoint == null) {
							lastControlPoint = new Point2D.Float(lastX, lastY);
						}
						final Point2D nextControlPoint = new Point2D.Float(2 * lastX - (float) lastControlPoint.getX(), 2 * lastY - (float) lastControlPoint.getY());

						path.quadTo((float) nextControlPoint.getX(), (float) nextControlPoint.getY(), x, y);
						lastControlPoint = nextControlPoint;
						lastX = x;
						lastY = y;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}

					case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_SMOOTH_REL: {
						final float x = ((SVGPathSegCurvetoQuadraticSmoothRel) seg).getX();
						final float y = ((SVGPathSegCurvetoQuadraticSmoothRel) seg).getY();
						if (lastControlPoint == null) {
							lastControlPoint = new Point2D.Float(lastX, lastY);
						}
						final Point2D nextControlPoint = new Point2D.Float(2 * lastX - (float) lastControlPoint.getX(), 2 * lastY - (float) lastControlPoint.getY());

						path.quadTo((float) nextControlPoint.getX(), (float) nextControlPoint.getY(), lastX + x, lastY + y);
						lastControlPoint = nextControlPoint;
						lastX += x;
						lastY += y;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}

					case SVGPathSeg.PATHSEG_ARC_ABS: {

						final float x1 = lastX;
						final float y1 = lastY;
						final float x2 = ((SVGPathSegArcAbs) seg).getX();
						final float y2 = ((SVGPathSegArcAbs) seg).getY();
						final float rx = Math.abs(((SVGPathSegArcAbs) seg).getR1());
						final float ry = Math.abs(((SVGPathSegArcAbs) seg).getR2());
						final float angle = (float) Math.toRadians(((SVGPathSegArcAbs) seg).getAngle());
						final boolean fA = ((SVGPathSegArcAbs) seg).getLargeArcFlag();
						final boolean fS = ((SVGPathSegArcAbs) seg).getSweepFlag();

						if (rx == 0 || ry == 0) {
							path.lineTo(x2, y2);
						} else {
							final Shape arc = createArc(x1, y1, x2, y2, rx, ry, angle, fA, fS);
							path.append(arc, true);
						}
						lastX = x2;
						lastY = y2;
						lastControlPoint = null;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}
					case SVGPathSeg.PATHSEG_ARC_REL: {

						final float x1 = lastX;
						final float y1 = lastY;
						final float x2 = lastX + ((SVGPathSegArcRel) seg).getX();
						final float y2 = lastY + ((SVGPathSegArcRel) seg).getY();
						final float rx = Math.abs(((SVGPathSegArcRel) seg).getR1());
						final float ry = Math.abs(((SVGPathSegArcRel) seg).getR2());
						final float angle = (float) Math.toRadians(((SVGPathSegArcRel) seg).getAngle());
						final boolean fA = ((SVGPathSegArcRel) seg).getLargeArcFlag();
						final boolean fS = ((SVGPathSegArcRel) seg).getSweepFlag();

						if (rx == 0 || ry == 0) {
							path.lineTo(x2, y2);
						} else {
							final Shape arc = createArc(x1, y1, x2, y2, rx, ry, angle, fA, fS);
							path.append(arc, true);
						}
						lastX = x2;
						lastY = y2;
						lastControlPoint = null;
						points.appendItem(new SVGPointImpl(lastX, lastY));
						break;
					}
					default:
						break;

				}
			}
		}
		return path;
	}

	private Shape createArc(final float x1, final float y1, final float x2, final float y2, final float rxArc, final float ryArc, final float angle, final boolean fA, final boolean fS) {
		float rx = rxArc;
		float ry = ryArc;
		final double cosAngle = Math.cos(angle);
		final double sinAngle = Math.sin(angle);
		final double x1prime = cosAngle * (x1 - x2) / 2 + sinAngle * (y1 - y2) / 2;
		final double y1prime = -sinAngle * (x1 - x2) / 2 + cosAngle * (y1 - y2) / 2;
		double rx2 = rx * rx;
		double ry2 = ry * ry;
		final double x1prime2 = x1prime * x1prime;
		final double y1prime2 = y1prime * y1prime;

		final double radiiCheck = x1prime2 / rx2 + y1prime2 / ry2;
		if (radiiCheck > 1) {
			rx = (float) Math.sqrt(radiiCheck) * rx;
			ry = (float) Math.sqrt(radiiCheck) * ry;
			rx2 = rx * rx;
			ry2 = ry * ry;
		}

		double squaredThing = (rx2 * ry2 - rx2 * y1prime2 - ry2 * x1prime2) / (rx2 * y1prime2 + ry2 * x1prime2);
		if (squaredThing < 0) { // this may happen due to lack of precision
			squaredThing = 0;
		}
		squaredThing = Math.sqrt(squaredThing);
		if (fA == fS) {
			squaredThing = -squaredThing;
		}
		final double cXprime = squaredThing * rx * y1prime / ry;
		final double cYprime = squaredThing * -(ry * x1prime / rx);
		final double cx = cosAngle * cXprime - sinAngle * cYprime + (x1 + x2) / 2;
		final double cy = sinAngle * cXprime + cosAngle * cYprime + (y1 + y2) / 2;
		double ux = 1;
		double uy = 0;
		double vx = (x1prime - cXprime) / rx;
		double vy = (y1prime - cYprime) / ry;
		double startAngle = Math
				.acos((ux * vx + uy * vy) / (Math.sqrt(ux * ux + uy * uy) * Math.sqrt(vx * vx + vy * vy)));

		if (ux * vy - uy * vx < 0) {
			startAngle = -startAngle;
		}
		ux = (x1prime - cXprime) / rx;
		uy = (y1prime - cYprime) / ry;
		vx = (-x1prime - cXprime) / rx;
		vy = (-y1prime - cYprime) / ry;

		double angleExtent = Math
				.acos((ux * vx + uy * vy) / (Math.sqrt(ux * ux + uy * uy) * Math.sqrt(vx * vx + vy * vy)));

		if (ux * vy - uy * vx < 0) {
			angleExtent = -angleExtent;
		}

		double angleExtentDegrees = Math.toDegrees(angleExtent);
		final double numCircles = Math.abs(angleExtentDegrees / 360.0);
		if (numCircles > 1) {
			if (angleExtentDegrees > 0) {
				angleExtentDegrees -= 360 * Math.floor(numCircles);
			} else {
				angleExtentDegrees += 360 * Math.floor(numCircles);
			}
			angleExtent = Math.toRadians(angleExtentDegrees);
		}
		if (fS && angleExtent < 0) {
			angleExtent += Math.toRadians(360.0);
		} else if (!fS && angleExtent > 0) {
			angleExtent -= Math.toRadians(360.0);
		}

		Shape arc = new Arc2D.Double(cx - rx, cy - ry, rx * 2, ry * 2, -Math.toDegrees(startAngle),
				-Math.toDegrees(angleExtent), Arc2D.OPEN);
		arc = AffineTransform.getRotateInstance(angle, cx, cy).createTransformedShape(arc);
		return arc;
	}

	private void constructPathSegList(final String d) {
		pathSegList = new SVGPathSegListImpl();
		final Pattern pattern = Pattern.compile("([MmLlHhVvCcSsQqTtAaZz])|(-?\\d*\\.?\\d+(?:[eE][-+]?\\d+)?)");
		final Matcher matcher = pattern.matcher(d);
		String currentCommand = null;
		List<Float> params = new ArrayList<>();
		while (matcher.find()) {
			String command = matcher.group(1);
			String number = matcher.group(2);
			if (command != null) {
				if (currentCommand != null) {
					addCommand(currentCommand, params);
					params.clear();
				}
				currentCommand = command;
				if (command.equalsIgnoreCase("Z")) {
					addCommand(currentCommand, null);
					currentCommand = null;
				}
			} else if (number != null) {
				params.add(Float.parseFloat(number));
			}
		}
		if (currentCommand != null) {
			addCommand(currentCommand, params);
		}
	}

	private void addCommand(final String command, final List<Float> params) {
		if (params == null) {
			pathSegList.appendItem(new SVGPathSegClosePathImpl());
			return;
		}
		switch (command) {
			case "M":
			case "m":
				for (int i = 0; i < params.size(); i += 2) {
					float x = params.get(i);
					float y = params.get(i + 1);
					if (i == 0) {
						pathSegList.appendItem(command.equals("M")
								? new SVGPathSegMovetoAbsImpl(x, y)
								: new SVGPathSegMovetoRelImpl(x, y));
					} else {
						pathSegList.appendItem(command.equals("M")
								? new SVGPathSegLinetoAbsImpl(x, y)
								: new SVGPathSegLinetoRelImpl(x, y));
					}
				}
				break;
			case "L":
			case "l":
				for (int i = 0; i < params.size(); i += 2) {
					float x = params.get(i);
					float y = params.get(i + 1);
					pathSegList.appendItem(command.equals("L")
							? new SVGPathSegLinetoAbsImpl(x, y)
							: new SVGPathSegLinetoRelImpl(x, y));
				}
				break;
			case "H":
			case "h":
				for (float x : params) {
					pathSegList.appendItem(command.equals("H")
							? new SVGPathSegLinetoHorizontalAbsImpl(x)
							: new SVGPathSegLinetoHorizontalRelImpl(x));
				}
				break;
			case "V":
			case "v":
				for (float y : params) {
					pathSegList.appendItem(command.equals("V")
							? new SVGPathSegLinetoVerticalAbsImpl(y)
							: new SVGPathSegLinetoVerticalRelImpl(y));
				}
				break;
			case "C":
			case "c":
				for (int i = 0; i < params.size(); i += 6) {
					float x1 = params.get(i);
					float y1 = params.get(i + 1);
					float x2 = params.get(i + 2);
					float y2 = params.get(i + 3);
					float x = params.get(i + 4);
					float y = params.get(i + 5);
					pathSegList.appendItem(command.equals("C")
							? new SVGPathSegCurvetoCubicAbsImpl(x, y, x1, y1, x2, y2)
							: new SVGPathSegCurvetoCubicRelImpl(x, y, x1, y1, x2, y2));
				}
				break;
			case "S":
			case "s":
				for (int i = 0; i < params.size(); i += 4) {
					float x2 = params.get(i);
					float y2 = params.get(i + 1);
					float x = params.get(i + 2);
					float y = params.get(i + 3);
					pathSegList.appendItem(command.equals("S")
							? new SVGPathSegCurvetoCubicSmoothAbsImpl(x, y, x2, y2)
							: new SVGPathSegCurvetoCubicSmoothRelImpl(x, y, x2, y2));
				}
				break;
			case "Q":
			case "q":
				for (int i = 0; i < params.size(); i += 4) {
					float x1 = params.get(i);
					float y1 = params.get(i + 1);
					float x = params.get(i + 2);
					float y = params.get(i + 3);
					pathSegList.appendItem(command.equals("Q")
							? new SVGPathSegCurvetoQuadraticAbsImpl(x, y, x1, y1)
							: new SVGPathSegCurvetoQuadraticRelImpl(x, y, x1, y1));
				}
				break;
			case "T":
			case "t":
				for (int i = 0; i < params.size(); i += 2) {
					float x = params.get(i);
					float y = params.get(i + 1);
					pathSegList.appendItem(command.equals("T")
							? new SVGPathSegCurvetoQuadraticSmoothAbsImpl(x, y)
							: new SVGPathSegCurvetoQuadraticSmoothRelImpl(x, y));
				}
				break;
			case "A":
			case "a":
				for (int i = 0; i < params.size(); i += 7) {
					float r1 = params.get(i);
					float r2 = params.get(i + 1);
					float angle = params.get(i + 2);
					boolean largeArcFlag = params.get(i + 3) != 0;
					boolean sweepFlag = params.get(i + 4) != 0;
					float x = params.get(i + 5);
					float y = params.get(i + 6);
					pathSegList.appendItem(command.equals("A")
							? new SVGPathSegArcAbsImpl(x, y, r1, r2, angle, largeArcFlag, sweepFlag)
							: new SVGPathSegArcRelImpl(x, y, r1, r2, angle, largeArcFlag, sweepFlag));
				}
				break;
			case "Z":
			case "z":
				pathSegList.appendItem(new SVGPathSegClosePathImpl());
				break;
			default:
				log.error("Unsupported command: {} ", command);
				break;
		}
	}
}