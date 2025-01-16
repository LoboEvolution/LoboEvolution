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

package org.loboevolution.svg;

import org.loboevolution.common.Strings;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * <p>SVGPathElementImpl class.</p>
 */
public class SVGPathElementImpl extends SVGGraphic implements SVGPathElement {

	private SVGPathSegList pathSegList;

	/**
	 * <p>Constructor for SVGPathElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGPathElementImpl(final String name) {
		super(name);
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
		final String commands = "ZzMmLlCcQqAaHhVvSsTt";
		final StringTokenizer st = new StringTokenizer(d, commands, true);
		while (st.hasMoreTokens()) {
			String command = st.nextToken();
			while (!commands.contains(command) && st.hasMoreTokens()) {
				command = st.nextToken();
			}
			if (commands.contains(command)) {
				if (command.equals("Z") || command.equals("z")) {
					addCommand(command, null);
				} else {
					if (st.hasMoreTokens()) {
						final String parameters = st.nextToken();
						addCommand(command, parameters);
					}
				}
			}
		}
	}

	private void addCommand(final String command, final String parameters) {
		switch (command) {
			case "Z":
			case "z":
				final SVGPathSeg seg = new SVGPathSegClosePathImpl();
				pathSegList.appendItem(seg);
				break;

			case "M":
			case "m":
				addMoveTo(command, parameters);
				break;

			case "L":
			case "l":
				addLineTo(command, parameters);
				break;

			case "C":
			case "c":
				addCurveTo(command, parameters);
				break;

			case "S":
			case "s":
				addSmoothCurveTo(command, parameters);
				break;

			case "H":
			case "h":
				addHorizontalLineTo(command, parameters);
				break;

			case "V":
			case "v":
				addVerticalLineTo(command, parameters);
				break;

			case "Q":
			case "q":
				addQuadraticBezierCurveTo(command, parameters);
				break;

			case "T":
			case "t":
				addTruetypeQuadraticBezierCurveTo(command, parameters);
				break;

			case "A":
			case "a":
				addEllipticArc(command, parameters);
				break;

			default:
				break;
		}
	}

	private void addMoveTo(final String command, final String param) {
		final boolean absolute = !command.equals("m");
		boolean firstPoint = true;
		final String parameters = trimCommas(param);
		final String delims = " ,-\n\t\r";
		final StringTokenizer st = new StringTokenizer(parameters, delims, true);
		while (st.hasMoreTokens()) {
			String token = getNextToken(st, delims);
			final float x = Float.parseFloat(token);
			token = getNextToken(st, delims);
			final float y = Float.parseFloat(token);
			if (firstPoint) {
				if (absolute) {
					pathSegList.appendItem(new SVGPathSegMovetoAbsImpl(x, y));
				} else {
					pathSegList.appendItem(new SVGPathSegMovetoRelImpl(x, y));
				}
				firstPoint = false;
			} else {
				if (absolute) {
					pathSegList.appendItem(new SVGPathSegLinetoAbsImpl(x, y));
				} else {
					pathSegList.appendItem(new SVGPathSegLinetoRelImpl(x, y));
				}
			}
		}
	}

	private void addLineTo(final String command, final String param) {
		final boolean absolute = !command.equals("l");
		final String delims = " ,-\n\t\r";
		final String parameters = trimCommas(param);
		final StringTokenizer st = new StringTokenizer(parameters, delims, true);
		while (st.hasMoreTokens()) {

			// get x coordinate
			String token = getNextToken(st, delims);
			final float x = Float.parseFloat(token);

			// get y coordinate
			token = getNextToken(st, delims);
			final float y = Float.parseFloat(token);

			// add new seg to path seg list
			if (absolute) {
				pathSegList.appendItem(new SVGPathSegLinetoAbsImpl(x, y));
			} else {
				pathSegList.appendItem(new SVGPathSegLinetoRelImpl(x, y));
			}
		}
	}

	private void addCurveTo(final String command, final String param) {
		final boolean absolute = !command.equals("c");
		final String delims = " ,-\n\t\r";
		final String parameters = trimCommas(param);
		final StringTokenizer st = new StringTokenizer(parameters, delims, true);
		while (st.hasMoreTokens()) {
			// get x1 coordinate
			String token = getNextToken(st, delims);
			final float x1 = Float.parseFloat(token);

			// get y1 coordinate
			token = getNextToken(st, delims);
			final float y1 = Float.parseFloat(token);

			// get x2 coordinate
			token = getNextToken(st, delims);
			final float x2 = Float.parseFloat(token);

			// get y2 coordinate
			token = getNextToken(st, delims);
			final float y2 = Float.parseFloat(token);

			// get x coordinate
			token = getNextToken(st, delims);
			final float x = Float.parseFloat(token);

			// get y coordinate
			token = getNextToken(st, delims);
			final float y = Float.parseFloat(token);

			// add new seg to path seg list
			if (absolute) {
				pathSegList.appendItem(new SVGPathSegCurvetoCubicAbsImpl(x, y, x1, y1, x2, y2));
			} else {
				pathSegList.appendItem(new SVGPathSegCurvetoCubicRelImpl(x, y, x1, y1, x2, y2));
			}
		}
	}

	private void addSmoothCurveTo(final String command, final String param) {
		final boolean absolute = !command.equals("s");
		final String delims = " ,-\n\t\r";
		final String parameters = trimCommas(param);
		final StringTokenizer st = new StringTokenizer(parameters, delims, true);

		while (st.hasMoreTokens()) {

			// get x2 coordinate
			String token = getNextToken(st, delims);
			final float x2 = Float.parseFloat(token);

			// get y2 coordinate
			token = getNextToken(st, delims);
			final float y2 = Float.parseFloat(token);

			// get x coordinate
			token = getNextToken(st, delims);
			final float x = Float.parseFloat(token);

			// get y coordinate
			token = getNextToken(st, delims);
			final float y = Float.parseFloat(token);

			// add new seg to path seg list
			if (absolute) {
				pathSegList.appendItem(new SVGPathSegCurvetoCubicSmoothAbsImpl(x, y, x2, y2));
			} else {
				pathSegList.appendItem(new SVGPathSegCurvetoCubicSmoothRelImpl(x, y, x2, y2));
			}
		}
	}

	private void addHorizontalLineTo(final String command, final String param) {
		final boolean absolute = !command.equals("h");
		final String delims = " ,-\n\t\r";
		final String parameters = trimCommas(param);
		final StringTokenizer st = new StringTokenizer(parameters, delims, true);

		while (st.hasMoreTokens()) {

			// get x coordinate
			final String token = getNextToken(st, delims);
			final float x = Float.parseFloat(token);

			// add new seg to path seg list
			if (absolute) {
				pathSegList.appendItem(new SVGPathSegLinetoHorizontalAbsImpl(x));
			} else {
				pathSegList.appendItem(new SVGPathSegLinetoHorizontalRelImpl(x));
			}
		}
	}

	private void addVerticalLineTo(final String command, final String param) {
		final boolean absolute = !command.equals("v");
		final String delims = " ,-\n\t\r";
		final String parameters = trimCommas(param);
		final StringTokenizer st = new StringTokenizer(parameters, delims, true);

		while (st.hasMoreTokens()) {

			// get y coordinate
			final String token = getNextToken(st, delims);
			final float y = Float.parseFloat(token);

			// add new seg to path seg list
			if (absolute) {
				pathSegList.appendItem(new SVGPathSegLinetoVerticalAbsImpl(y));
			} else {
				pathSegList.appendItem(new SVGPathSegLinetoVerticalRelImpl(y));
			}
		}
	}

	private void addEllipticArc(final String command, final String param) {
		final boolean absolute = !command.equals("a");
		final String delims = " ,-\n\t\r";
		final String parameters = trimCommas(param);
		final StringTokenizer st = new StringTokenizer(parameters, delims, true);
		while (st.hasMoreTokens()) {

			// get rx coordinate
			String token = getNextToken(st, delims);
			final float r1 = Float.parseFloat(token);

			// get ry coordinate
			token = getNextToken(st, delims);
			final float r2 = Float.parseFloat(token);

			// get x-axis-rotation
			token = getNextToken(st, delims);
			final float angle = Float.parseFloat(token);

			// get large-arc-flag
			token = getNextToken(st, delims);
			final float largeArc = Float.parseFloat(token);

			// get sweep-flag
			token = getNextToken(st, delims);
			final float sweepFlag = Float.parseFloat(token);

			// get x coordinate
			token = getNextToken(st, delims);
			final float x = Float.parseFloat(token);

			// get y coordinate
			token = getNextToken(st, delims);
			final float y = Float.parseFloat(token);

			// add new seg to path seg list
			if (absolute) {
				pathSegList.appendItem(new SVGPathSegArcAbsImpl(x, y, r1, r2, angle, largeArc == 1, sweepFlag == 1));
			} else {
				pathSegList.appendItem(new SVGPathSegArcRelImpl(x, y, r1, r2, angle, largeArc == 1, sweepFlag == 1));
			}
		}
	}

	private void addQuadraticBezierCurveTo(final String command, final String param) {
		final boolean absolute = !command.equals("q");
		final String delims = " ,-\n\t\r";
		final String parameters = trimCommas(param);
		final StringTokenizer st = new StringTokenizer(parameters, delims, true);
		while (st.hasMoreTokens()) {

			// get x1 coordinate
			String token = getNextToken(st, delims);
			final float x1 = Float.parseFloat(token);

			// get y1 coordinate
			token = getNextToken(st, delims);
			final float y1 = Float.parseFloat(token);

			// get x coordinate
			token = getNextToken(st, delims);
			final float x = Float.parseFloat(token);

			// get y coordinate
			token = getNextToken(st, delims);
			final float y = Float.parseFloat(token);

			// add new seg to path seg list
			if (absolute) {
				pathSegList.appendItem(new SVGPathSegCurvetoQuadraticAbsImpl(x, y, x1, y1));
			} else {
				pathSegList.appendItem(new SVGPathSegCurvetoQuadraticRelImpl(x, y, x1, y1));
			}
		}
	}

	private void addTruetypeQuadraticBezierCurveTo(final String command, final String param) {
		final boolean absolute = !command.equals("t");
		final String delims = " ,-\n\t\r";
		final String parameters = trimCommas(param);

		final StringTokenizer st = new StringTokenizer(parameters, delims, true);

		while (st.hasMoreTokens()) {

			// get x coordinate
			String token = getNextToken(st, delims);
			final float x = Float.parseFloat(token);

			// get y coordinate
			token = getNextToken(st, delims);
			final float y = Float.parseFloat(token);

			// add new seg to path seg list
			if (absolute) {
				pathSegList.appendItem(new SVGPathSegCurvetoQuadraticSmoothAbsImpl(x, y));
			} else {
				pathSegList.appendItem(new SVGPathSegCurvetoQuadraticSmoothRelImpl(x, y));
			}
		}
	}

	private String getNextToken(final StringTokenizer st, final String delims) {

		String token;
		boolean neg = false;
		try {
			token = st.nextToken();
			while (st.hasMoreTokens() && delims.contains(token)) {
				neg = token.equals("-");
				token = st.nextToken();
			}
			if (delims.contains(token)) {
				token = "0";
			}
		} catch (final NoSuchElementException e) {
			token = "0";
		}

		if (neg) {
			token = "-" + token;
		}

		if (token.endsWith("e") || token.endsWith("E")) {
			// is an exponential number, need to read the exponent too
			try {
				String exponent = st.nextToken(); // get the '-'
				exponent += st.nextToken(); // get the exponent digits
				token += exponent;
			} catch (final NoSuchElementException e) {
				token += '0';
			}
		}
		return token;
	}

	private String trimCommas(final String params) {
		String result = params;
		while (result.startsWith(",")) {
			result = result.substring(1);
		}
		while (result.endsWith(",")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
}