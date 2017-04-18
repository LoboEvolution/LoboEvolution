/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.control;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.info.SVGInfo;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.html.svgimpl.SVGMatrixImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegArcAbsImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegArcRelImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegCurvetoCubicAbsImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegCurvetoCubicRelImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegCurvetoCubicSmoothAbsImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegCurvetoCubicSmoothRelImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegCurvetoQuadraticAbsImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegCurvetoQuadraticRelImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegCurvetoQuadraticSmoothAbsImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegCurvetoQuadraticSmoothRelImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegLinetoAbsImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegLinetoHorizontalAbsImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegLinetoHorizontalRelImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegLinetoRelImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegLinetoVerticalAbsImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegLinetoVerticalRelImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegMovetoAbsImpl;
import org.lobobrowser.html.svgimpl.SVGPathSegMovetoRelImpl;
import org.lobobrowser.html.svgimpl.SVGPointImpl;
import org.lobobrowser.util.gui.ColorFactory;
import org.lobobrowser.w3c.svg.SVGPathSegList;
import org.lobobrowser.w3c.svg.SVGPoint;
import org.lobobrowser.w3c.svg.SVGPointList;
import org.lobobrowser.w3c.svg.SVGTransform;
import org.lobobrowser.w3c.svg.SVGTransformList;
import org.w3c.dom.svg.SVGPathSeg;

public class SVGBasicControl extends BaseControl {
	
	private static final long serialVersionUID = 1L;

	public SVGBasicControl(HTMLElementImpl modelNode) {
		super(modelNode);
	}

	public void circle(Graphics2D g2d, SVGInfo svgi) {
		Shape circle = new Ellipse2D.Double(svgi.getX() - svgi.getR(), svgi.getY() - svgi.getR(), 2 * svgi.getR(),
				2 * svgi.getR());
		drawFillAndStroke(g2d, circle, svgi);
	}

	public void rectangle(Graphics2D g2d, SVGInfo svgi) {
		Shape rect;
		if (svgi.getRx() > 0 || svgi.getRy() > 0) {
			if (svgi.getRx() > 0 && svgi.getRy() == 0) {
				svgi.setRy(svgi.getRx());
			} else if (svgi.getRx() == 0 && svgi.getRy() > 0) {
				svgi.setRx(svgi.getRy());
			}
			rect = new RoundRectangle2D.Float(svgi.getX(), svgi.getY(), svgi.getWidth(), svgi.getHeight(),
					svgi.getRx() * 2, svgi.getRy() * 2);
		} else {
			rect = new Rectangle2D.Float(svgi.getX(), svgi.getY(), svgi.getWidth(), svgi.getHeight());
		}
		drawFillAndStroke(g2d, rect, svgi);
	}

	public void ellipse(Graphics2D g2d, SVGInfo svgi) {
		Point2D.Float center = convertCoordinate(new Point2D.Float(svgi.getX(), svgi.getY()));
		Point2D.Float corner = convertCoordinate(
				new Point2D.Float(svgi.getX() - svgi.getRx(), svgi.getY() - svgi.getRy()));
		Ellipse2D.Float ellipse2d = new Ellipse2D.Float();
		ellipse2d.setFrameFromCenter(center, corner);
		drawFillAndStroke(g2d, ellipse2d, svgi);
	}

	public void line(Graphics2D g2d, SVGInfo svgi) {
		Point2D.Float p = new Point2D.Float(svgi.getX1(), svgi.getY1());
		Point2D.Float p1 = convertCoordinate(p);
		p = new Point2D.Float(svgi.getX2(), svgi.getY2());
		Point2D.Float p2 = convertCoordinate(p);
		Line2D line2d = new Line2D.Float(p1, p2);
		drawFillAndStroke(g2d, line2d, svgi);
	}

	public void polygon(Graphics2D g2d, SVGInfo svgi) {
		GeneralPath path = new GeneralPath();
		SVGPointList points = svgi.getPoilist();
		int numPoints = points.getNumberOfItems();
		for (int i = 0; i < numPoints; i++) {
			SVGPoint point = points.getItem(i);
			float x = point.getX();
			float y = point.getY();
			if (i == 0) {
				path.moveTo(x, y);
			} else {
				path.lineTo(x, y);
			}
		}

		path.closePath();
		drawFillAndStroke(g2d, path, svgi);

	}

	public void polyline(Graphics2D g2d, SVGInfo svgi) {
		GeneralPath path = new GeneralPath();
		SVGPointList points = svgi.getPoilist();
		int numPoints = points.getNumberOfItems();
		for (int i = 0; i < numPoints; i++) {
			SVGPoint point = points.getItem(i);
			float x = point.getX();
			float y = point.getY();
			if (i == 0) {
				path.moveTo(x, y);
			} else {
				path.lineTo(x, y);
			}
		}
		path.closePath();
		drawFillAndStroke(g2d, path, svgi);

	}

	public void path(Graphics2D g2d, SVGInfo svgi) {

		GeneralPath path = new GeneralPath();
		Point2D lastControlPoint = null;
		SVGPoint subPathStartPoint = null;
		SVGPathSegList list = svgi.getPathSegList();
		boolean startOfSubPath = true;
		int numPathSegs = list.getNumberOfItems();
		float lastX = 0;
		float lastY = 0;

		for (int i = 0; i < numPathSegs; i++) {
			SVGPathSegImpl seg = (SVGPathSegImpl) list.getItem(i);
			
			if (startOfSubPath) {
				while (!seg.getPathSegTypeAsLetter().equalsIgnoreCase("m") && i < numPathSegs) {
					i++;
					seg = (SVGPathSegImpl) list.getItem(i);
				}
				if (seg.getPathSegTypeAsLetter().equalsIgnoreCase("m")) {
					if (seg.getPathSegType() == SVGPathSeg.PATHSEG_MOVETO_REL) {
						float x = ((SVGPathSegMovetoRelImpl) seg).getX();
						float y = ((SVGPathSegMovetoRelImpl) seg).getY();
						path.moveTo(x + lastX, y + lastY);
						subPathStartPoint = new SVGPointImpl(x + lastX, y + lastY);
						lastX += x;
						lastY += y;
					} else if (seg.getPathSegType() == SVGPathSeg.PATHSEG_MOVETO_ABS) {
						
						float x = ((SVGPathSegMovetoAbsImpl) seg).getX();
						float y = ((SVGPathSegMovetoAbsImpl) seg).getY();
						path.moveTo(x, y);
						subPathStartPoint = new SVGPointImpl(x, y);
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
							lastX = subPathStartPoint.getX();
							lastY = subPathStartPoint.getY();
							subPathStartPoint = null;
						}
						break;
					}
	
					case SVGPathSeg.PATHSEG_MOVETO_ABS: {
						float x = ((SVGPathSegMovetoAbsImpl) seg).getX();
						float y = ((SVGPathSegMovetoAbsImpl) seg).getY();
						path.moveTo(x, y);
						lastX = x;
						lastY = y;
						lastControlPoint = null;
						break;
					}
	
					case SVGPathSeg.PATHSEG_MOVETO_REL: {
						float x = ((SVGPathSegMovetoRelImpl) seg).getX();
						float y = ((SVGPathSegMovetoRelImpl) seg).getY();
						path.moveTo(x + lastX, y + lastY);
						lastX += x;
						lastY += y;
						lastControlPoint = null;
						break;
					}
	
					case SVGPathSeg.PATHSEG_LINETO_ABS: {
						float x = ((SVGPathSegLinetoAbsImpl) seg).getX();
						float y = ((SVGPathSegLinetoAbsImpl) seg).getY();
						path.lineTo(x, y);
						lastX = x;
						lastY = y;
						lastControlPoint = null;
						break;
					}
	
					case SVGPathSeg.PATHSEG_LINETO_REL: {
						float x = ((SVGPathSegLinetoRelImpl) seg).getX();
						float y = ((SVGPathSegLinetoRelImpl) seg).getY();
						path.lineTo(x + lastX, y + lastY);
						lastX += x;
						lastY += y;
						lastControlPoint = null;
						break;
					}
	
					case SVGPathSeg.PATHSEG_LINETO_HORIZONTAL_ABS: {
						float x = ((SVGPathSegLinetoHorizontalAbsImpl) seg).getX();
						path.lineTo(x, lastY);
						lastX = x;
						lastControlPoint = null;
						break;
					}
	
					case SVGPathSeg.PATHSEG_LINETO_HORIZONTAL_REL: {
						float x = ((SVGPathSegLinetoHorizontalRelImpl) seg).getX();
						path.lineTo(x + lastX, lastY);
						lastX += x;
						lastControlPoint = null;
						break;
					}
	
					case SVGPathSeg.PATHSEG_LINETO_VERTICAL_ABS: {
						float y = ((SVGPathSegLinetoVerticalAbsImpl) seg).getY();
						path.lineTo(lastX, y);
						lastY = y;
						lastControlPoint = null;
						break;
					}
	
					case SVGPathSeg.PATHSEG_LINETO_VERTICAL_REL: {
						float y = ((SVGPathSegLinetoVerticalRelImpl) seg).getY();
						path.lineTo(lastX, y + lastY);
						lastY += y;
						lastControlPoint = null;
	
						break;
					}
	
					case SVGPathSeg.PATHSEG_CURVETO_CUBIC_ABS: {
						float x = ((SVGPathSegCurvetoCubicAbsImpl) seg).getX();
						float y = ((SVGPathSegCurvetoCubicAbsImpl) seg).getY();
						float x1 = ((SVGPathSegCurvetoCubicAbsImpl) seg).getX1();
						float y1 = ((SVGPathSegCurvetoCubicAbsImpl) seg).getY1();
						float x2 = ((SVGPathSegCurvetoCubicAbsImpl) seg).getX2();
						float y2 = ((SVGPathSegCurvetoCubicAbsImpl) seg).getY2();
						path.curveTo(x1, y1, x2, y2, x, y);
						lastControlPoint = new Point2D.Float(x2, y2);
						lastX = x;
						lastY = y;
						break;
					}
	
					case SVGPathSeg.PATHSEG_CURVETO_CUBIC_REL: {
						float x = ((SVGPathSegCurvetoCubicRelImpl) seg).getX();
						float y = ((SVGPathSegCurvetoCubicRelImpl) seg).getY();
						float x1 = ((SVGPathSegCurvetoCubicRelImpl) seg).getX1();
						float y1 = ((SVGPathSegCurvetoCubicRelImpl) seg).getY1();
						float x2 = ((SVGPathSegCurvetoCubicRelImpl) seg).getX2();
						float y2 = ((SVGPathSegCurvetoCubicRelImpl) seg).getY2();
						path.curveTo(lastX + x1, lastY + y1, lastX + x2, lastY + y2, lastX + x, lastY + y);
						lastControlPoint = new Point2D.Float(lastX + x2, lastY + y2);
						lastX += x;
						lastY += y;
						break;
					}
	
					case SVGPathSeg.PATHSEG_CURVETO_CUBIC_SMOOTH_ABS: {
						float x = ((SVGPathSegCurvetoCubicSmoothAbsImpl) seg).getX();
						float y = ((SVGPathSegCurvetoCubicSmoothAbsImpl) seg).getY();
						float x2 = ((SVGPathSegCurvetoCubicSmoothAbsImpl) seg).getX2();
						float y2 = ((SVGPathSegCurvetoCubicSmoothAbsImpl) seg).getY2();
						if (lastControlPoint == null)
							lastControlPoint = new Point2D.Float(lastX, lastY);
						path.curveTo(2 * lastX - (float) lastControlPoint.getX(), 2 * lastY - (float) lastControlPoint.getY(), x2, y2, x, y);
						lastControlPoint = new Point2D.Float(x2, y2);
						lastX = x;
						lastY = y;
						break;
					}
	
					case SVGPathSeg.PATHSEG_CURVETO_CUBIC_SMOOTH_REL: {
						float x = ((SVGPathSegCurvetoCubicSmoothRelImpl) seg).getX();
						float y = ((SVGPathSegCurvetoCubicSmoothRelImpl) seg).getY();
						float x2 = ((SVGPathSegCurvetoCubicSmoothRelImpl) seg).getX2();
						float y2 = ((SVGPathSegCurvetoCubicSmoothRelImpl) seg).getY2();
						if (lastControlPoint == null)
							lastControlPoint = new Point2D.Float(lastX, lastY);
						path.curveTo(2 * lastX - (float) lastControlPoint.getX(), 2 * lastY - (float) lastControlPoint.getY(), lastX + x2, lastY + y2, lastX + x, lastY + y);
						lastControlPoint = new Point2D.Float(lastX + x2, lastY + y2);
						lastX += x;
						lastY += y;
						break;
					}
	
					case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_ABS: {
						float x = ((SVGPathSegCurvetoQuadraticAbsImpl) seg).getX();
						float y = ((SVGPathSegCurvetoQuadraticAbsImpl) seg).getY();
						float x1 = ((SVGPathSegCurvetoQuadraticAbsImpl) seg).getX1();
						float y1 = ((SVGPathSegCurvetoQuadraticAbsImpl) seg).getY1();
						path.quadTo(x1, y1, x, y);
						lastControlPoint = new Point2D.Float(x1, y1);
						lastX = x;
						lastY = y;
						break;
					}
	
					case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_REL: {
						float x = ((SVGPathSegCurvetoQuadraticRelImpl) seg).getX();
						float y = ((SVGPathSegCurvetoQuadraticRelImpl) seg).getY();
						float x1 = ((SVGPathSegCurvetoQuadraticRelImpl) seg).getX1();
						float y1 = ((SVGPathSegCurvetoQuadraticRelImpl) seg).getY1();
						path.quadTo(lastX + x1, lastY + y1, lastX + x, lastY + y);
						lastControlPoint = new Point2D.Float(lastX + x1, lastY + y1);
						lastX += x;
						lastY += y;
						break;
					}
	
					case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_SMOOTH_ABS: {
						float x = ((SVGPathSegCurvetoQuadraticSmoothAbsImpl) seg).getX();
						float y = ((SVGPathSegCurvetoQuadraticSmoothAbsImpl) seg).getY();
						
						if (lastControlPoint == null)
							lastControlPoint = new Point2D.Float(lastX, lastY);
						
						Point2D nextControlPoint = new Point2D.Float(2 * lastX - (float) lastControlPoint.getX(), 2 * lastY - (float) lastControlPoint.getY());
						path.quadTo((float) nextControlPoint.getX(), (float) nextControlPoint.getY(), x, y);
						lastControlPoint = nextControlPoint;
						lastX = x;
						lastY = y;
						break;
					}
	
					case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_SMOOTH_REL: {
						float x = ((SVGPathSegCurvetoQuadraticSmoothRelImpl) seg).getX();
						float y = ((SVGPathSegCurvetoQuadraticSmoothRelImpl) seg).getY();
						
						if (lastControlPoint == null)
							lastControlPoint = new Point2D.Float(lastX, lastY);
						
						Point2D nextControlPoint = new Point2D.Float(2 * lastX - (float) lastControlPoint.getX(), 2 * lastY - (float) lastControlPoint.getY());
						path.quadTo((float) nextControlPoint.getX(), (float) nextControlPoint.getY(), lastX + x, lastY + y);
						lastControlPoint = nextControlPoint;
						lastX += x;
						lastY += y;
						break;
					}
	
					case SVGPathSeg.PATHSEG_ARC_ABS: {
	
						float x1 = lastX;
						float y1 = lastY;
						float x2 = ((SVGPathSegArcAbsImpl) seg).getX();
						float y2 = ((SVGPathSegArcAbsImpl) seg).getY();
						float rx = Math.abs(((SVGPathSegArcAbsImpl) seg).getR1());
						float ry = Math.abs(((SVGPathSegArcAbsImpl) seg).getR2());
						float angle = (float) Math.toRadians(((SVGPathSegArcAbsImpl) seg).getAngle());
						boolean fA = ((SVGPathSegArcAbsImpl) seg).getLargeArcFlag();
						boolean fS = ((SVGPathSegArcAbsImpl) seg).getSweepFlag();	
						
						if (rx == 0 || ry == 0) {
							// radii 0, just do a lineTo
							path.lineTo(x2, y2);
							lastX = x2;
							lastY = y2;
							lastControlPoint = null;
	
						} else {
	
							Shape arc = createArc(x1, y1, x2, y2, rx, ry, angle, fA, fS);
							path.append(arc, true);
							lastX = x2;
							lastY = y2;
							lastControlPoint = null;
						}
						break;
					}
					
					case SVGPathSeg.PATHSEG_ARC_REL: {
	
						float x1 = lastX;
						float y1 = lastY;
						float x2 = lastX + ((SVGPathSegArcRelImpl) seg).getX();
						float y2 = lastY + ((SVGPathSegArcRelImpl) seg).getY();
						float rx = Math.abs(((SVGPathSegArcRelImpl) seg).getR1());
						float ry = Math.abs(((SVGPathSegArcRelImpl) seg).getR2());
						float angle = (float) Math.toRadians(((SVGPathSegArcRelImpl) seg).getAngle());
						boolean fA = ((SVGPathSegArcRelImpl) seg).getLargeArcFlag();
						boolean fS = ((SVGPathSegArcRelImpl) seg).getSweepFlag();
	
						if (rx == 0 || ry == 0) {
							// radii 0, just do a lineTo
							path.lineTo(x2, y2);
							lastX = x2;
							lastY = y2;
							lastControlPoint = null;
						} else {
							Shape arc = createArc(x1, y1, x2, y2, rx, ry, angle, fA, fS);
							path.append(arc, true);
							lastX = x2;
							lastY = y2;
						}
						break;
					}
				}
			}
		}

		drawFillAndStroke(g2d, path, svgi);

	}
	
	private Shape createArc(float x1, float y1, float x2, float y2, float rx, float ry, float angle, boolean fA, boolean fS) {

		double cosAngle = Math.cos(angle);
		double sinAngle = Math.sin(angle);
		double x1prime = (cosAngle * (x1 - x2) / 2) + (sinAngle * (y1 - y2) / 2);
		double y1prime = (-sinAngle * (x1 - x2) / 2) + (cosAngle * (y1 - y2) / 2);
		double rx2 = rx * rx;
		double ry2 = ry * ry;
		double x1prime2 = x1prime * x1prime;
		double y1prime2 = y1prime * y1prime;

		double radiiCheck = x1prime2 / rx2 + y1prime2 / ry2;
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

		double cXprime = squaredThing * rx * y1prime / ry;
		double cYprime = squaredThing * -(ry * x1prime / rx);
		double cx = cosAngle * cXprime - sinAngle * cYprime + (x1 + x2) / 2;
		double cy = sinAngle * cXprime + cosAngle * cYprime + (y1 + y2) / 2;
		double ux = 1;
		double uy = 0;
		double vx = (x1prime - cXprime) / rx;
		double vy = (y1prime - cYprime) / ry;
		double startAngle = Math.acos((ux * vx + uy * vy) / (Math.sqrt(ux * ux + uy * uy) * Math.sqrt(vx * vx + vy * vy)));

		if ((ux * vy - uy * vx) < 0) {
			startAngle = -startAngle;
		}

		ux = (x1prime - cXprime) / rx;
		uy = (y1prime - cYprime) / ry;
		vx = (-x1prime - cXprime) / rx;
		vy = (-y1prime - cYprime) / ry;

		double angleExtent = Math.acos((ux * vx + uy * vy) / (Math.sqrt(ux * ux + uy * uy) * Math.sqrt(vx * vx + vy * vy)));

		if ((ux * vy - uy * vx) < 0) {
			angleExtent = -angleExtent;
		}
		double angleExtentDegrees = Math.toDegrees(angleExtent);
		double numCircles = Math.abs(angleExtentDegrees / 360.0);
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
		Shape arc = new Arc2D.Double(cx - rx, cy - ry, rx * 2, ry * 2, -Math.toDegrees(startAngle), -Math.toDegrees(angleExtent), Arc2D.OPEN);
		arc = AffineTransform.getRotateInstance(angle, cx, cy).createTransformedShape(arc);
		return arc;
	}

	private void drawFillAndStroke(Graphics2D g2d, Shape shape2d, SVGInfo svgi) {

		Stroke prevStroke = g2d.getStroke();
		Paint prevPaint = g2d.getPaint();
		BasicStroke basicStroke = null;
		Color strokeColor = null;
		Color fillColor = null;
		float fillOpacity = 1.0F;
		float strokeOpacity = 1.0F;

		if (svgi.getStyle().getFillOpacity() != null) {
			fillOpacity = Float.parseFloat(svgi.getStyle().getFillOpacity());
		}

		if (svgi.getStyle().getStrokeOpacity() != null) {
			strokeOpacity = Float.parseFloat(svgi.getStyle().getStrokeOpacity());
		}

		if (svgi.getStyle().getOpacity() != null) {
			fillOpacity = Float.parseFloat(svgi.getStyle().getOpacity());
			strokeOpacity = Float.parseFloat(svgi.getStyle().getOpacity());
		}

		if (svgi.getStyle().getStroke() != null && !"none".equalsIgnoreCase(svgi.getStyle().getStroke())) {
			Color color = ColorFactory.getInstance().getColor(svgi.getStyle().getStroke());
			strokeColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(255 * strokeOpacity));
			basicStroke = getStroking(g2d, svgi);
		}

		if (svgi.getStyle().getFill() != null && !"none".equalsIgnoreCase(svgi.getStyle().getFill())) {
			Color color = ColorFactory.getInstance().getColor(svgi.getStyle().getFill());
			fillColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(255 * fillOpacity));
		}
		
		if (fillColor != null && strokeColor != null) {
			g2d.setPaint(strokeColor);
			g2d.draw(basicStroke.createStrokedShape(shape2d));
			g2d.setPaint(fillColor);
			transform(g2d, svgi);
			g2d.fill(shape2d);
		} else if (fillColor != null) {
			g2d.setPaint(fillColor);
			//AffineTransform t = new AffineTransform();
		    //t.rotate(Math.toRadians(45));
		    //g2d.transform(t);
			transform(g2d, svgi);
			g2d.fill(shape2d);
		} else if (strokeColor != null) {
			g2d.setPaint(strokeColor);
			transform(g2d, svgi);
			g2d.draw(basicStroke.createStrokedShape(shape2d));
		} else {
			g2d.setStroke(prevStroke);
			g2d.setPaint(prevPaint);
			transform(g2d, svgi);
			g2d.draw(shape2d);
		}
	}

	private BasicStroke getStroking(Graphics2D g2d, SVGInfo svgi) {

		BasicStroke basicStroke;
		int strokeWidth = 1;
		int intLineCap = BasicStroke.CAP_BUTT;
		int intlineJoin = BasicStroke.JOIN_BEVEL;
		int miterlimit = 4;

		if ("round".equals(svgi.getStyle().getStrokeLineCap())) {
			intLineCap = BasicStroke.CAP_ROUND;

		} else if ("square".equals(svgi.getStyle().getStrokeLineCap())) {
			intLineCap = BasicStroke.CAP_SQUARE;
		}
		if ("round".equals(svgi.getStyle().getStrokeLineCap())) {
			intlineJoin = BasicStroke.JOIN_ROUND;

		} else if ("miter".equals(svgi.getStyle().getStrokeLineCap())) {
			intlineJoin = BasicStroke.JOIN_MITER;
		}

		if (svgi.getStyle().getStrokeWidth() != null) {
			strokeWidth = HtmlValues.getPixelSize(svgi.getStyle().getStrokeWidth(), null, 1);
		}

		if (svgi.getStyle().getStrokeMiterLimit() != null) {
			miterlimit = HtmlValues.getPixelSize(svgi.getStyle().getStrokeMiterLimit(), null, 4);
		}

		if (svgi.getStyle().getStrokeDashArray() == null) {
			basicStroke = new BasicStroke(strokeWidth, intLineCap, intlineJoin, miterlimit);
		} else {
			String parts[] = svgi.getStyle().getStrokeDashArray().split("\\s*,\\s*|\\s+");

			float[] dashArray = new float[parts.length];
			int i = 0;
			for (String str : parts) {
				dashArray[i] = Float.parseFloat(str);
				i++;
			}

			basicStroke = new BasicStroke(strokeWidth, intLineCap, intlineJoin, miterlimit, dashArray, 0.0F);
		}

		if (basicStroke != null) {
			g2d.setStroke(basicStroke);
		}
		return basicStroke;
	}

	private Point2D.Float convertCoordinate(Point2D.Float p1) {
		Point2D.Float p = p1;
		return p;
	}

	private void transform(Graphics2D g2d, SVGInfo svgi) {
		SVGTransformList transformList = svgi.getTransformList();
		
		if(transformList == null)
			return;
		
		int numPoints = transformList.getNumberOfItems();
		for (int i = 0; i < numPoints; i++) {
			SVGTransform point = transformList.getItem(i);
			SVGMatrixImpl mtrx = (SVGMatrixImpl) point.getMatrix();
			AffineTransform affine = new AffineTransform();
			
			switch (point.getType()) {
			case SVGTransform.SVG_TRANSFORM_MATRIX:
				affine.concatenate(new AffineTransform(mtrx.getA(), mtrx.getB(), mtrx.getC(), mtrx.getD(), mtrx.getE(), mtrx.getF()));
				break;
			case SVGTransform.SVG_TRANSFORM_TRANSLATE:
				affine.translate(mtrx.getE(), mtrx.getF());
				break;
			case SVGTransform.SVG_TRANSFORM_SCALE:
				affine.scale(mtrx.getA(), mtrx.getD());
				break;
			case SVGTransform.SVG_TRANSFORM_ROTATE:
				affine.rotate(Math.toRadians(mtrx.getA()), mtrx.getB(), mtrx.getC());
				break;
			case SVGTransform.SVG_TRANSFORM_SKEWX:
				affine.concatenate(new AffineTransform(mtrx.getA(), mtrx.getB(), mtrx.getC(), mtrx.getD(), mtrx.getE(), mtrx.getF()));
				break;
			case SVGTransform.SVG_TRANSFORM_SKEWY:
				affine.concatenate(new AffineTransform(mtrx.getA(), mtrx.getB(), mtrx.getC(), mtrx.getD(), mtrx.getE(), mtrx.getF()));
				break;
			}
			g2d.transform(affine);
		}
	}
}