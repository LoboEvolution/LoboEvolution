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
package org.lobobrowser.html.renderer;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.lobobrowser.html.dom.svgimpl.SVGAnimateColorElementImpl;
import org.lobobrowser.html.dom.svgimpl.SVGAnimateElementImpl;
import org.lobobrowser.html.dom.svgimpl.SVGAnimateImpl;
import org.lobobrowser.html.dom.svgimpl.SVGAnimateTransformElementImpl;
import org.lobobrowser.html.dom.svgimpl.SVGAnimationImpl;
import org.lobobrowser.html.dom.svgimpl.SVGCircleElementImpl;
import org.lobobrowser.html.dom.svgimpl.SVGDefsElementImpl;
import org.lobobrowser.html.dom.svgimpl.SVGEllipseElementImpl;
import org.lobobrowser.html.dom.svgimpl.SVGGElementImpl;
import org.lobobrowser.html.dom.svgimpl.SVGLineElementImpl;
import org.lobobrowser.html.dom.svgimpl.SVGMatrixImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathElementImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegArcAbsImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegArcRelImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegCurvetoCubicAbsImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegCurvetoCubicRelImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegCurvetoCubicSmoothAbsImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegCurvetoCubicSmoothRelImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegCurvetoQuadraticAbsImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegCurvetoQuadraticRelImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegCurvetoQuadraticSmoothAbsImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegCurvetoQuadraticSmoothRelImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegLinetoAbsImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegLinetoHorizontalAbsImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegLinetoHorizontalRelImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegLinetoRelImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegLinetoVerticalAbsImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegLinetoVerticalRelImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegMovetoAbsImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPathSegMovetoRelImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPointImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPolygonElementImpl;
import org.lobobrowser.html.dom.svgimpl.SVGPolylineElementImpl;
import org.lobobrowser.html.dom.svgimpl.SVGRadialGradientElementImpl;
import org.lobobrowser.html.dom.svgimpl.SVGRectElementImpl;
import org.lobobrowser.html.dom.svgimpl.SVGSVGElementImpl;
import org.lobobrowser.html.dom.svgimpl.SVGStyle;
import org.lobobrowser.html.dom.svgimpl.SVGUseElementImpl;
import org.lobobrowser.html.dom.svgimpl.SVGUtility;
import org.lobobrowser.html.dom.svgimpl.SVGViewBoxImpl;
import org.lobobrowser.html.style.AbstractCSSProperties;
import org.lobo.common.Nodes;
import org.lobo.info.SVGInfo;
import org.lobobrowser.html.dom.svg.SVGElement;
import org.lobobrowser.html.dom.svg.SVGLengthList;
import org.lobobrowser.html.dom.svg.SVGPathSeg;
import org.lobobrowser.html.dom.svg.SVGPathSegList;
import org.lobobrowser.html.dom.svg.SVGPoint;
import org.lobobrowser.html.dom.svg.SVGPointList;
import org.lobobrowser.html.dom.svg.SVGTransform;
import org.lobobrowser.html.dom.svg.SVGTransformList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;;

public class SVGBasicControl extends SVGStyle {

	private static final long serialVersionUID = 1L;
	
	/** The animate. */
	private transient SVGAnimationImpl animate;
	
	/** The circle. */
	public static final int CIRCLE = 1;

	/** The rect. */
	public static final int RECT = 2;

	/** The ellipse. */
	public static final int ELLIPSE = 3;

	/** The line. */
	public static final int LINE = 4;

	/** The line. */
	public static final int POLYGON = 5;

	/** The line. */
	public static final int POLYLINE = 6;

	/** The path. */
	public static final int PATH = 7;

	/** The use. */
	public static final int USE = 8;

	/** The text. */
	public static final int TEXT = 9;

	/** The radial. */
	public static final int RADIAL = 10;

	/** The modelN. */
	private SVGSVGElementImpl modelN;
	
	private int count = -1;

	public SVGBasicControl(SVGSVGElementImpl modelNode) {
		super(modelNode);
		this.modelN = modelNode;
	}

	public void svgCircle(Graphics2D g2d, SVGInfo svgi, int numObject) {
		animate(svgi, numObject);
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(modelN, svgi.getX(), svgi.getY(), svgi.getWidth(), svgi.getHeight(), svgi.getR());
		Shape circle = new Ellipse2D.Double(viewbox.getX() - viewbox.getR(), viewbox.getY() - viewbox.getR(), 2 * viewbox.getR(), 2 * viewbox.getR());
		svgTransform(g2d, svgi, new SVGInfo());
		drawFillAndStroke(g2d, circle, svgi);
		svgClip(svgi, g2d);
	}

	public void svgRectangle(Graphics2D g2d, SVGInfo svgi, int numObject) {
		animate(svgi, numObject);
		Shape rect;
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(modelN, svgi.getX(), svgi.getY(), svgi.getWidth(), svgi.getHeight(), 0);
		if (svgi.getRx() > 0 || svgi.getRy() > 0) {
			if (svgi.getRx() > 0 && svgi.getRy() == 0) {
				svgi.setRy(svgi.getRx());
			} else if (svgi.getRx() == 0 && svgi.getRy() > 0) {
				svgi.setRx(svgi.getRy());
			}
			rect = new RoundRectangle2D.Float(viewbox.getX(), viewbox.getY(), viewbox.getWidth(), viewbox.getHeight(),
					svgi.getRx() * 2, svgi.getRy() * 2);
		} else {
			rect = new Rectangle2D.Float(viewbox.getX(), viewbox.getY(), viewbox.getWidth(), viewbox.getHeight());
		}

		svgTransform(g2d, svgi, new SVGInfo());
		drawFillAndStroke(g2d, rect, svgi);
		svgClip(svgi, g2d);

	}

	public void svgEllipse(Graphics2D g2d, SVGInfo svgi, int numObject) {
		animate(svgi, numObject);
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(modelN, svgi.getX(), svgi.getY(), svgi.getWidth(), svgi.getHeight(), svgi.getR());
		Point2D.Float center = new Point2D.Float(viewbox.getX(), viewbox.getY());
		Point2D.Float corner = new Point2D.Float(viewbox.getX() - svgi.getRx(), viewbox.getY() - svgi.getRy());
		Ellipse2D.Float ellipse2d = new Ellipse2D.Float();
		ellipse2d.setFrameFromCenter(center, corner);
		svgTransform(g2d, svgi, new SVGInfo());
		drawFillAndStroke(g2d, ellipse2d, svgi);
		svgClip(svgi, g2d);
	}

	public void svgLine(Graphics2D g2d, SVGInfo svgi, int numObject) {
		animate(svgi, numObject);
		Point2D.Float p = new Point2D.Float(svgi.getX1(), svgi.getY1());
		Point2D.Float p1 = p;
		p = new Point2D.Float(svgi.getX2(), svgi.getY2());
		Point2D.Float p2 = p;
		Line2D line2d = new Line2D.Float(p1, p2);
		svgTransform(g2d, svgi, new SVGInfo());
		drawFillAndStroke(g2d, line2d, svgi);
		svgClip(svgi, g2d);
	}

	public void svgPolyline(Graphics2D g2d, SVGInfo svgi, int numObject) {
		animate(svgi, numObject);
		GeneralPath path = new GeneralPath();
		SVGPointList points = (SVGPointList)svgi.getPoilist();
		int numPoints = points.getNumberOfItems();
		for (int i = 0; i < numPoints; i++) {
			SVGPoint point = points.getItem(i);
			SVGViewBoxImpl viewbox = new SVGViewBoxImpl(modelN, point.getX(), point.getY(), 0, 0, 0);
			float x = viewbox.getX();
			float y = viewbox.getY();
			if (i == 0) {
				path.moveTo(x, y);
			} else {
				path.lineTo(x, y);
			}
		}
		path.closePath();
		svgTransform(g2d, svgi, new SVGInfo());
		drawFillAndStroke(g2d, path, svgi);
		svgClip(svgi, g2d);

	}

	public void svgPath(Graphics2D g2d, SVGInfo svgi, int numObject) {
		animate(svgi, numObject);
		GeneralPath path = new GeneralPath();
		Point2D lastControlPoint = null;
		SVGPoint subPathStartPoint = null;
		SVGPathSegList list = (SVGPathSegList)svgi.getPathSegList();
		boolean startOfSubPath = true;
		int numPathSegs = list == null ? 0 : list.getNumberOfItems();
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
						if (lastControlPoint == null) {
							lastControlPoint = new Point2D.Float(lastX, lastY);
						}
						path.curveTo(2 * lastX - (float) lastControlPoint.getX(),
								2 * lastY - (float) lastControlPoint.getY(), x2, y2, x, y);
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
						if (lastControlPoint == null) {
							lastControlPoint = new Point2D.Float(lastX, lastY);
						}
						path.curveTo(2 * lastX - (float) lastControlPoint.getX(),
								2 * lastY - (float) lastControlPoint.getY(), lastX + x2, lastY + y2, lastX + x, lastY + y);
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
	
						if (lastControlPoint == null) {
							lastControlPoint = new Point2D.Float(lastX, lastY);
						}
	
						Point2D nextControlPoint = new Point2D.Float(2 * lastX - (float) lastControlPoint.getX(),
								2 * lastY - (float) lastControlPoint.getY());
						path.quadTo((float) nextControlPoint.getX(), (float) nextControlPoint.getY(), x, y);
						lastControlPoint = nextControlPoint;
						lastX = x;
						lastY = y;
						break;
					}
	
					case SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_SMOOTH_REL: {
						float x = ((SVGPathSegCurvetoQuadraticSmoothRelImpl) seg).getX();
						float y = ((SVGPathSegCurvetoQuadraticSmoothRelImpl) seg).getY();
	
						if (lastControlPoint == null) {
							lastControlPoint = new Point2D.Float(lastX, lastY);
						}
	
						Point2D nextControlPoint = new Point2D.Float(2 * lastX - (float) lastControlPoint.getX(),
								2 * lastY - (float) lastControlPoint.getY());
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
	
							Shape arc = SVGUtility.createArc(x1, y1, x2, y2, rx, ry, angle, fA, fS);
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
							Shape arc = SVGUtility.createArc(x1, y1, x2, y2, rx, ry, angle, fA, fS);
							path.append(arc, true);
							lastX = x2;
							lastY = y2;
						}
						break;
					}
					default:
						break;
				}
			}
		}

		svgTransform(g2d, svgi, new SVGInfo());
		drawFillAndStroke(g2d, path, svgi);
		svgClip(svgi, g2d);

	}

	public void svgUse(Graphics2D g2d, SVGInfo svgi, SVGSVGElementImpl svg) {

		String href = svgi.getHref();
		float x = svgi.getX();
		float y = svgi.getY();
		AbstractCSSProperties style = (AbstractCSSProperties)svgi.getStyle();
		SVGTransformList transformList = (SVGTransformList)svgi.getTransformList();
		Element elementById = svg.getOwnerDocument().getElementById(href.split("#")[1]);
		List<SVGInfo> useList = child(elementById);
		
		for (int i = 0; i < useList.size(); i++) {
			SVGInfo info = useList.get(i);

			if (x > 0) {
				info.setX(x);
			}

			if (y > 0) {
				info.setY(y);
			}

			if (style != null) {
				info.setStyle(style);
			}

			if (transformList != null && transformList.getNumberOfItems() > 0) {
				info.setTransformList(transformList);
			}

			draw(g2d, svgi, modelN, i);
		}
	}

	public void svgTransform(Graphics2D g2d, SVGInfo svgi, SVGInfo group) {

		SVGTransformList transformList = null;

		if (group.getTransformList() != null) {
			transformList = (SVGTransformList)group.getTransformList();
		} else {
			transformList = (SVGTransformList)svgi.getTransformList();
		}

		if (transformList == null) {
			return;
		}

		int numPoints = transformList.getNumberOfItems();
		for (int i = 0; i < numPoints; i++) {
			SVGTransform point = transformList.getItem(i);
			SVGMatrixImpl mtrx = (SVGMatrixImpl) point.getMatrix();
			AffineTransform affine = new AffineTransform();

			switch (point.getType()) {
			case SVGTransform.SVG_TRANSFORM_MATRIX:
				affine.concatenate(new AffineTransform(mtrx.getA(), mtrx.getB(), mtrx.getC(), mtrx.getD(), mtrx.getE(),
						mtrx.getF()));
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
				affine.concatenate(new AffineTransform(mtrx.getA(), mtrx.getB(), mtrx.getC(), mtrx.getD(), mtrx.getE(),
						mtrx.getF()));
				break;
			case SVGTransform.SVG_TRANSFORM_SKEWY:
				affine.concatenate(new AffineTransform(mtrx.getA(), mtrx.getB(), mtrx.getC(), mtrx.getD(), mtrx.getE(),
						mtrx.getF()));
				break;
			default:
				break;
			}
			g2d.transform(affine);
		}
	}

	public void svgText(Graphics2D g2d, SVGInfo svgi, int numObject) {
		animate(svgi, numObject);
		GeneralPath path = new GeneralPath();
		path.setWindingRule(Path2D.WIND_NON_ZERO);
		FontRenderContext frc = new FontRenderContext(null, false, false);

		SVGLengthList dxList = (SVGLengthList)svgi.getDxList();
		SVGLengthList dyList = (SVGLengthList)svgi.getDyList();
		char[] cr = svgi.getText().toCharArray();
		float x = svgi.getX();
		float y = svgi.getY();

		for (int i = 0; i < cr.length; i++) {
			if (dxList != null && dxList.getNumberOfItems() > 0) {
				try {

					if (i == 0) {
						x = i + dxList.getItem(i).getValue();
					} else {
						x = dxList.getItem(i).getValue() + x;
					}
				} catch (Exception e) {
					x = 8 + x;
				}
			} else if (dyList != null && dyList.getNumberOfItems() > 0) {
				try {
					if (i == 0) {
						y = i + dyList.getItem(i).getValue();
					} else {
						y = dyList.getItem(i).getValue() + y;
					}
				} catch (Exception e) {
					y = 20 + y;
				}
			} else {
				if (i > 0) {
					x = i * 8;
				}
			}

			TextLayout tl = new TextLayout(String.valueOf(cr[i]), svgi.getFont(), frc);
			Point2D.Float pos = new Point2D.Float(x, y);
			AffineTransform textAt = AffineTransform.getTranslateInstance(pos.x, pos.y);
			textAt.translate(x, y);
			Shape textShape = tl.getOutline(textAt);
			path.append(textShape, false);
		}
		path.closePath();
		svgTransform(g2d, svgi, new SVGInfo());
		drawFillAndStroke(g2d, path, svgi);
		svgClip(svgi, g2d);
	}

	public List<SVGInfo> childNodes(Node element) {

		List<SVGInfo> useList = new ArrayList<SVGInfo>();
		NodeList childNodes = element.getChildNodes();
			for (Node n : Nodes.iterable(childNodes)) {		
				if (n instanceof SVGGElementImpl) {
					SVGGElementImpl svgGroup = (SVGGElementImpl) n;
					SVGTransformList tl = svgGroup.getTransform().getBaseVal();
					AbstractCSSProperties style = svgGroup.getSVGStyle();
					setSvgiGroup(new SVGInfo(style, tl));
					svgGroup.setSvg(modelN);
					NodeList gChildNodes = n.getChildNodes();
					for (Node n1 : Nodes.iterable(gChildNodes)) {
						useList.addAll(child(n1));
					}
				} else if (n instanceof SVGDefsElementImpl) {
					SVGDefsElementImpl defs = (SVGDefsElementImpl) n;
					SVGTransformList tl = defs.getTransform().getBaseVal();
					setSvgiGroup(new SVGInfo(null, tl));
				} else if (n instanceof SVGAnimateElementImpl) {
					setAnimate((SVGAnimateElementImpl) n);
				} else if (n instanceof SVGAnimateColorElementImpl) {
					setAnimate((SVGAnimateColorElementImpl) n);
				} else if (n instanceof SVGAnimateTransformElementImpl) {
					setAnimate((SVGAnimateTransformElementImpl) n);
				} else {
					useList.addAll(child(n));
				}
			}
		return useList;
	}
	
	public List<SVGInfo> child(Node n){
		List<SVGInfo> useList = new ArrayList<SVGInfo>();
		if (n instanceof SVGCircleElementImpl) {
			SVGCircleElementImpl svgcircle = (SVGCircleElementImpl) n;
			String id = svgcircle.getId();
			float x = svgcircle.getCx().getBaseVal().getValue();
			float y = svgcircle.getCy().getBaseVal().getValue();
			float r = svgcircle.getR().getBaseVal().getValue();
			String clippath = svgcircle.getClipPath();
			SVGTransformList tl = svgcircle.getTransform().getBaseVal();
			AbstractCSSProperties style = svgcircle.getSVGStyle();
			SVGAnimationImpl animateElement = svgcircle.getAnimateElement();
			useList.add(new SVGInfo(id, CIRCLE, x, y, r, style, false, clippath, tl, animateElement));
		}

		if (n instanceof SVGRectElementImpl) {
			SVGRectElementImpl svgrect = (SVGRectElementImpl) n;
			String id = svgrect.getId();
			float x = svgrect.getX().getBaseVal().getValue();
			float y = svgrect.getY().getBaseVal().getValue();
			float height = svgrect.getHeight().getBaseVal().getValue();
			float width = svgrect.getWidth().getBaseVal().getValue();
			float rx = svgrect.getRx().getBaseVal().getValue();
			float ry = svgrect.getRy().getBaseVal().getValue();
			String clippath = svgrect.getClipPath();
			SVGTransformList tl = svgrect.getTransform().getBaseVal();
			AbstractCSSProperties style = svgrect.getSVGStyle();
			SVGAnimationImpl animateElement = svgrect.getAnimateElement();
			useList.add(new SVGInfo(id, RECT, x, y, height, width, rx, ry, style, false, clippath, tl,
					animateElement));
		}

		if (n instanceof SVGEllipseElementImpl) {
			SVGEllipseElementImpl svgellipse = (SVGEllipseElementImpl) n;
			String id = svgellipse.getId();
			float x = svgellipse.getCx().getBaseVal().getValue();
			float y = svgellipse.getCy().getBaseVal().getValue();
			float rx = svgellipse.getRx().getBaseVal().getValue();
			float ry = svgellipse.getRy().getBaseVal().getValue();
			String clippath = svgellipse.getClipPath();
			SVGTransformList tl = svgellipse.getTransform().getBaseVal();
			AbstractCSSProperties style = svgellipse.getSVGStyle();
			SVGAnimationImpl animateElement = svgellipse.getAnimateElement();
			useList.add(new SVGInfo(id, ELLIPSE, x, y, rx, ry, style, false, clippath, tl, animateElement));
		}

		if (n instanceof SVGLineElementImpl) {
			SVGLineElementImpl svgline = (SVGLineElementImpl) n;
			String id = svgline.getId();
			float x1 = svgline.getX1().getBaseVal().getValue();
			float x2 = svgline.getX2().getBaseVal().getValue();
			float y1 = svgline.getY1().getBaseVal().getValue();
			float y2 = svgline.getY2().getBaseVal().getValue();
			String clippath = svgline.getClipPath();
			SVGTransformList tl = svgline.getTransform().getBaseVal();
			AbstractCSSProperties style = svgline.getSVGStyle();
			SVGAnimationImpl animateElement = svgline.getAnimateElement();
			useList.add(new SVGInfo(id, LINE, x1, y1, x2, y2, style, clippath, false, tl, animateElement));
		}

		if (n instanceof SVGPolylineElementImpl) {
			SVGPolylineElementImpl svgpolyline = (SVGPolylineElementImpl) n;
			String id = svgpolyline.getId();
			SVGPointList points = svgpolyline.getPoints();
			String clippath = svgpolyline.getClipPath();
			SVGTransformList tl = svgpolyline.getTransform().getBaseVal();
			AbstractCSSProperties style = svgpolyline.getSVGStyle();
			SVGAnimationImpl animateElement = svgpolyline.getAnimateElement();
			useList.add(new SVGInfo(id, POLYLINE, points, style, false, clippath, tl, animateElement));
		}

		if (n instanceof SVGPolygonElementImpl) {
			SVGPolygonElementImpl svgpolygon = (SVGPolygonElementImpl) n;
			String id = svgpolygon.getId();
			SVGPointList points = svgpolygon.getPoints();
			String clippath = svgpolygon.getClipPath();
			SVGTransformList tl = svgpolygon.getTransform().getBaseVal();
			AbstractCSSProperties style = svgpolygon.getSVGStyle();
			SVGAnimationImpl animateElement = svgpolygon.getAnimateElement();
			useList.add(new SVGInfo(id, POLYGON, points, style, false, clippath, tl, animateElement));
		}

		if (n instanceof SVGPathElementImpl) {
			SVGPathElementImpl svgpath = (SVGPathElementImpl) n;
			String id = svgpath.getId();
			SVGPathSegList points = svgpath.getPathSegList();
			String clippath = svgpath.getClipPath();
			SVGTransformList tl = svgpath.getTransform().getBaseVal();
			AbstractCSSProperties style = svgpath.getSVGStyle();
			SVGAnimationImpl animateElement = svgpath.getAnimateElement();
			useList.add(new SVGInfo(id, PATH, points, style, false, clippath, tl, animateElement));
		}

		if (n instanceof SVGGElementImpl) {
			SVGGElementImpl svgGroup = (SVGGElementImpl) n;
			SVGTransformList tl = svgGroup.getTransform().getBaseVal();
			AbstractCSSProperties style = svgGroup.getSVGStyle();
			setSvgiGroup(new SVGInfo(style, tl));
			svgGroup.setSvg(modelN);

			NodeList gChildNodes = svgGroup.getChildNodes();
			for (Node n1 : Nodes.iterable(gChildNodes)) {
				useList.addAll(child(n1));
			}
		}

		if (n instanceof SVGUseElementImpl) {
			SVGUseElementImpl use = (SVGUseElementImpl) n;
			SVGInfo svgi = new SVGInfo();
			svgi.setMethod(USE);
			svgi.setHref(use.getHref().getBaseVal());
			svgi.setX(use.getX().getBaseVal().getValue());
			svgi.setY(use.getY().getBaseVal().getValue());
			svgi.setTransformList(use.getTransform().getBaseVal());
			useList.add(svgi);
		}

		if (n instanceof SVGRadialGradientElementImpl) {
			SVGRadialGradientElementImpl radial = (SVGRadialGradientElementImpl) n;
			SVGInfo svgi = new SVGInfo();
			svgi.setMethod(RADIAL);
			svgi.setHref(radial.getHref().getBaseVal());
			svgi.setX(radial.getCx().getBaseVal().getValue());
			svgi.setY(radial.getCy().getBaseVal().getValue());
			svgi.setR(radial.getR().getBaseVal().getValue());
			svgi.setFx(radial.getFx().getBaseVal().getValue());
			svgi.setFy(radial.getCy().getBaseVal().getValue());
			svgi.setTransformList(radial.getGradientTransform().getBaseVal());
			useList.add(svgi);
		}

		if (n instanceof SVGDefsElementImpl) {
			SVGDefsElementImpl defs = (SVGDefsElementImpl) n;
			SVGTransformList tl = defs.getTransform().getBaseVal();
			setSvgiGroup(new SVGInfo(null, tl));
		}
		return useList;
	}
	
	public void svgClip(SVGInfo svginfo, Graphics2D g2d) {
		if (svginfo.getClipPath() != null && svginfo.getClipPath().contains("url")) {
			String clipElemId = svginfo.getClipPath().split("#")[1].replace(")", "").trim();
			Element elementById = modelN.getOwnerDocument().getElementById(clipElemId);
			List<SVGInfo> useList = childNodes(elementById);

			for (int i = 0; i < useList.size(); i++) {
				SVGInfo svgi = useList.get(i);
				draw(g2d, svgi, modelN, i);
			}
		}
	}
	
	public void draw(Graphics2D g2d, SVGInfo svgi, SVGSVGElementImpl modelNode, int index){
		switch (svgi.getMethod()) {
		case CIRCLE:
			svgCircle(g2d, svgi, index);
			break;
		case RECT:
			svgRectangle(g2d, svgi, index);
			break;
		case ELLIPSE:
			svgEllipse(g2d, svgi, index);
			break;
		case LINE:
			svgLine(g2d, svgi, index);
			break;
		case POLYLINE:
		case POLYGON:
			svgPolyline(g2d, svgi, index);
			break;
		case PATH:
			svgPath(g2d, svgi, index);
			break;
		case USE:
			svgUse(g2d, svgi, modelNode);
			break;
		case TEXT:
			svgText(g2d, svgi, index);
			break;
		default:
			break;
		}
	}
	
	private void animate(SVGInfo svgi, int numObject) {
		if (getAnimate() != null && getAnimate().getTargetElement() != null) {
			SVGElement elem = getAnimate().getTargetElement();
			if (count < numObject && elem.getId().equals(svgi.getId())){
				svgi.setAnimate(getAnimate());
				new SVGAnimateImpl(svgi, ruicontrol);
				count++;
			}
		} else if (svgi.getAnimate() != null && count < numObject) {
			new SVGAnimateImpl(svgi, ruicontrol);
			count++;
		}
	}

	/**
	 * @return the animate
	 */
	public SVGAnimationImpl getAnimate() {
		return animate;
	}

	/**
	 * @param animate the animate to set
	 */
	public void setAnimate(SVGAnimationImpl animate) {
		this.animate = animate;
	}
}