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
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

import org.lobobrowser.html.info.SVGInfo;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.html.svgimpl.SVGCircleElementImpl;
import org.lobobrowser.html.svgimpl.SVGEllipseElementImpl;
import org.lobobrowser.html.svgimpl.SVGGElementImpl;
import org.lobobrowser.html.svgimpl.SVGLineElementImpl;
import org.lobobrowser.html.svgimpl.SVGLinearGradientElementImpl;
import org.lobobrowser.html.svgimpl.SVGMatrixImpl;
import org.lobobrowser.html.svgimpl.SVGPathElementImpl;
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
import org.lobobrowser.html.svgimpl.SVGPolygonElementImpl;
import org.lobobrowser.html.svgimpl.SVGPolylineElementImpl;
import org.lobobrowser.html.svgimpl.SVGRadialGradientElementImpl;
import org.lobobrowser.html.svgimpl.SVGRectElementImpl;
import org.lobobrowser.html.svgimpl.SVGSVGElementImpl;
import org.lobobrowser.html.svgimpl.SVGStopElementImpl;
import org.lobobrowser.html.svgimpl.SVGUseElementImpl;
import org.lobobrowser.html.svgimpl.SVGViewBoxImpl;
import org.lobobrowser.util.gui.ColorFactory;
import org.lobobrowser.w3c.svg.SVGLengthList;
import org.lobobrowser.w3c.svg.SVGPathSegList;
import org.lobobrowser.w3c.svg.SVGPoint;
import org.lobobrowser.w3c.svg.SVGPointList;
import org.lobobrowser.w3c.svg.SVGTransform;
import org.lobobrowser.w3c.svg.SVGTransformList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGPathSeg;

public class SVGBasicControl extends BaseControl {
	
	private static final long serialVersionUID = 1L;
	
	private SVGInfo svgiGroup = new SVGInfo();
	
	/** The circle. */
	public final int CIRCLE = 1;

	/** The rect. */
	public final int RECT = 2;

	/** The ellipse. */
	public final int ELLIPSE = 3;
	
	/** The line. */
	public final int LINE = 4;
	
	/** The line. */
	public final int POLYGON = 5;
	
	/** The line. */
	public final int POLYLINE = 6;
	
	/** The path. */
	public final int PATH = 7;
	
	/** The use. */
	public final int USE = 8;
	
	/** The text. */
	public final int TEXT = 9;
	
	/** The radial. */
	public final int RADIAL = 10;
	
	/** The modelN. */
	private SVGSVGElementImpl modelN;
	
		
	public SVGBasicControl(SVGSVGElementImpl modelNode) {
		super(modelNode);
		this.modelN = modelNode;
	}

	public void circle(Graphics2D g2d, SVGInfo svgi) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(modelN, svgi.getX(), svgi.getY(), svgi.getWidth(), svgi.getHeight(), svgi.getR());
		Shape circle = new Ellipse2D.Double(viewbox.getX() - viewbox.getR(), viewbox.getY() - viewbox.getR(), 2 * viewbox.getR(), 2 * viewbox.getR());
		transform(g2d, svgi, new SVGInfo());
		drawFillAndStroke(g2d, circle, svgi);
	}

	public void rectangle(Graphics2D g2d, SVGInfo svgi) {
		Shape rect;
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(modelN, svgi.getX(), svgi.getY(), svgi.getWidth(), svgi.getHeight(), 0);
		if (svgi.getRx() > 0 || svgi.getRy() > 0) {
			if (svgi.getRx() > 0 && svgi.getRy() == 0) {
				svgi.setRy(svgi.getRx());
			} else if (svgi.getRx() == 0 && svgi.getRy() > 0) {
				svgi.setRx(svgi.getRy());
			}
			rect = new RoundRectangle2D.Float(viewbox.getX(), viewbox.getY(), viewbox.getWidth(), viewbox.getHeight(), svgi.getRx() * 2, svgi.getRy() * 2);
		} else {
			rect = new Rectangle2D.Float(viewbox.getX(), viewbox.getY(), viewbox.getWidth(), viewbox.getHeight());
		}
		
		transform(g2d, svgi, new SVGInfo());
		drawFillAndStroke(g2d, rect, svgi);
		
	}

	public void ellipse(Graphics2D g2d, SVGInfo svgi) {
		SVGViewBoxImpl viewbox = new SVGViewBoxImpl(modelN, svgi.getX(), svgi.getY(), svgi.getWidth(), svgi.getHeight(), svgi.getR());
		Point2D.Float center = new Point2D.Float(viewbox.getX(), viewbox.getY());
		Point2D.Float corner = new Point2D.Float(viewbox.getX() - svgi.getRx(), viewbox.getY() - svgi.getRy());
		Ellipse2D.Float ellipse2d = new Ellipse2D.Float();
		ellipse2d.setFrameFromCenter(center, corner);
		transform(g2d, svgi, new SVGInfo());
		drawFillAndStroke(g2d, ellipse2d, svgi);
	}

	public void line(Graphics2D g2d, SVGInfo svgi) {
		Point2D.Float p = new Point2D.Float(svgi.getX1(), svgi.getY1());
		Point2D.Float p1 = p;
		p = new Point2D.Float(svgi.getX2(), svgi.getY2());
		Point2D.Float p2 = p;
		Line2D line2d = new Line2D.Float(p1, p2);
		transform(g2d, svgi, new SVGInfo());
		drawFillAndStroke(g2d, line2d, svgi);
	}

	public void polygon(Graphics2D g2d, SVGInfo svgi) {
		GeneralPath path = new GeneralPath();
		SVGPointList points = svgi.getPoilist();
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
		transform(g2d, svgi, new SVGInfo());
		drawFillAndStroke(g2d, path, svgi);

	}

	public void polyline(Graphics2D g2d, SVGInfo svgi) {
		GeneralPath path = new GeneralPath();
		SVGPointList points = svgi.getPoilist();
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
		transform(g2d, svgi, new SVGInfo());
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

		transform(g2d, svgi, new SVGInfo());
		drawFillAndStroke(g2d, path, svgi);

	}
		
	public void use(Graphics2D g2d, SVGInfo svgi,SVGSVGElementImpl svg) {
		
		String href = svgi.getHref();
		float x = svgi.getX();
		float y = svgi.getY();
		AbstractCSS2Properties style = svgi.getStyle();
		SVGTransformList transformList = svgi.getTransformList();		
		Element elementById = svg.getOwnerDocument().getElementById(href.split("#")[1]);
		ArrayList<SVGInfo> useList = useChildren(elementById);
		
		for (int i = 0; i < useList.size(); i++) {
			SVGInfo info = useList.get(i);
			
			if (x > 0) {
				info.setX(x);
			}

			if (y > 0) {
				info.setY(y);
			}
			
			if(style != null){
				info.setStyle(style);
			}
			
			if(transformList!= null && transformList.getNumberOfItems() > 0){
				info.setTransformList(transformList);
			}
			
			switch (info.getMethod()) {
			case CIRCLE:
				circle(g2d, info);
				break;
			case RECT:
				rectangle(g2d, info);
				break;
			case ELLIPSE:
				ellipse(g2d, info);
				break;
			case LINE:
				line(g2d, info);
				break;
			case POLYGON:
				polygon(g2d, info);
				break;
			case POLYLINE:
				polyline(g2d, info);
				break;
			case PATH:
				path(g2d, info);
				break;
			default:
				break;
			}
		}		
	}

    public void transform(Graphics2D g2d, SVGInfo svgi, SVGInfo group) {
		
		SVGTransformList transformList = null;
		
		if (group.getTransformList() != null) {
			transformList = group.getTransformList();
		} else {
			transformList = svgi.getTransformList();
		}
		
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

	public void text(Graphics2D g2d, SVGInfo svgi) {
		GeneralPath path = new GeneralPath();
		path.setWindingRule(Path2D.WIND_NON_ZERO);
		FontRenderContext frc = new FontRenderContext(null, false, false);

		SVGLengthList dxList = svgi.getDxList();
		SVGLengthList dyList = svgi.getDyList();
		char[] cr = svgi.getText().toCharArray();
		float x = svgi.getX();
		float y = svgi.getY();
		
		for (int i = 0; i < cr.length; i++) {
			if (dxList != null && dxList.getNumberOfItems() > 0) {
				try {
					
					if(i == 0){
						x = i + (dxList.getItem(i).getValue());
					} else {
						x = dxList.getItem(i).getValue() + x;
					}
				} catch (Exception e) {
					x = 8 + x;
				}
			} else if (dyList != null && dyList.getNumberOfItems() > 0) {
				try {
					if(i == 0){
						y = i + (dyList.getItem(i).getValue());
					} else {
						y = dyList.getItem(i).getValue() + y;
					}
				} catch (Exception e) {
					y = 20 + y;
				}
			} else {
				if (i > 0)
					x = i * 8;
			}
			
			TextLayout tl = new TextLayout(String.valueOf(cr[i]), svgi.getFont(), frc);
			Point2D.Float pos = new Point2D.Float(x, y);
			AffineTransform textAt = AffineTransform.getTranslateInstance(pos.x, pos.y);
			textAt.translate(x, y);
			Shape textShape = tl.getOutline(textAt);
			path.append(textShape, false);
		}
		path.closePath();
		transform(g2d, svgi, new SVGInfo());
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

		BasicStroke basicStroke = null;
		Paint strokeColor = null;
		Paint fillColor = Color.BLACK;
		float fillOpacity = 1.0F;
		float strokeOpacity = 1.0F;
		
		SVGInfo group = getSvgiGroup();
		
		if (group.getStyle() != null && group.getStyle().getFillOpacity() != null) {
			fillOpacity = Float.parseFloat(group.getStyle().getFillOpacity());
		} else if (svgi.getStyle().getFillOpacity() != null) {
			fillOpacity = Float.parseFloat(svgi.getStyle().getFillOpacity());
		}

		if (group.getStyle() != null && group.getStyle().getStrokeOpacity() != null) {
			strokeOpacity = Float.parseFloat(group.getStyle().getStrokeOpacity());
		} else if (svgi.getStyle().getStrokeOpacity() != null) {
			strokeOpacity = Float.parseFloat(svgi.getStyle().getStrokeOpacity());
		}

		if (group.getStyle() != null && group.getStyle().getOpacity() != null) {
			fillOpacity = Float.parseFloat(group.getStyle().getOpacity());
			strokeOpacity = Float.parseFloat(group.getStyle().getOpacity());
		} else if (svgi.getStyle().getOpacity() != null) {
			fillOpacity = Float.parseFloat(svgi.getStyle().getOpacity());
			strokeOpacity = Float.parseFloat(svgi.getStyle().getOpacity());
		}

		if (group.getStyle() != null && group.getStyle().getStroke() != null && !"none".equalsIgnoreCase(group.getStyle().getStroke())) {
			Color color = ColorFactory.getInstance().getColor(group.getStyle().getStroke());
			strokeColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(255 * strokeOpacity));
			basicStroke = getStroking(g2d, group);
		} else if (svgi.getStyle().getStroke() != null && !"none".equalsIgnoreCase(svgi.getStyle().getStroke())) {
			String stroke = svgi.getStyle().getStroke();
			if(stroke.contains("url")){
				String idElement = stroke.split("#")[1].replace(")","").trim();
				Element elementById = modelN.getOwnerDocument().getElementById(idElement);
				if (elementById instanceof SVGRadialGradientElementImpl || elementById instanceof SVGLinearGradientElementImpl) {
					strokeColor = gradient(elementById, shape2d);
				}
			} else {
				Color color = ColorFactory.getInstance().getColor(svgi.getStyle().getStroke());
				strokeColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(255 * strokeOpacity));
			}
			basicStroke = getStroking(g2d, svgi);
		}

		if (group.getStyle() != null && group.getStyle().getFill() != null && !"none".equalsIgnoreCase(group.getStyle().getFill())) {
			Color color = ColorFactory.getInstance().getColor(group.getStyle().getFill());
			fillColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(255 * fillOpacity));
		} else if (svgi.getStyle().getFill() != null && !"none".equalsIgnoreCase(svgi.getStyle().getFill())) {
			String fill = svgi.getStyle().getFill();
			if(fill.contains("url")){
				String idElement = fill.split("#")[1].replace(")","").trim();
				Element elementById = modelN.getOwnerDocument().getElementById(idElement);
				if (elementById instanceof SVGRadialGradientElementImpl || elementById instanceof SVGLinearGradientElementImpl) {
					fillColor = gradient(elementById, shape2d);
				}				
			} else  {
				Color color = ColorFactory.getInstance().getColor(svgi.getStyle().getFill());
				fillColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(255 * fillOpacity));
			}
		}
		
		if (svgi.isClip()) {
			g2d.clip(shape2d);
			
			if (fillColor != null) {
				g2d.setPaint(fillColor);
				g2d.fill(shape2d);
			}
			
			if (strokeColor != null) {
				g2d.setPaint(strokeColor);
				g2d.draw(basicStroke.createStrokedShape(shape2d));
			}
		} else if (svgi.getClipPath() != null && svgi.getClipPath().length() > 0) {
			g2d.setClip(shape2d);
			g2d.setPaint(Color.WHITE);
			g2d.draw(shape2d);
			g2d.fill(shape2d);
			clip(svgi,g2d);
		} else {
			if (fillColor != null) {
				g2d.setPaint(fillColor);
				g2d.fill(shape2d);
			}
			
			if (strokeColor != null) {
				g2d.setPaint(strokeColor);
				g2d.draw(basicStroke.createStrokedShape(shape2d));
			}
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
	
	private ArrayList<SVGInfo> useChildren(Node element) {
		
		ArrayList<SVGInfo> useList = new ArrayList<SVGInfo>();
		
		NodeList childNodes = element.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = (Node) childNodes.item(i);

			if (n instanceof SVGCircleElementImpl) {
				SVGCircleElementImpl svgcircle = (SVGCircleElementImpl) n;
				float x = svgcircle.getCx().getBaseVal().getValue();
				float y= svgcircle.getCy().getBaseVal().getValue();
				float r = svgcircle.getR().getBaseVal().getValue();
				String clippath = svgcircle.getClipPath();
				SVGTransformList tl = svgcircle.getTransform().getBaseVal();
				AbstractCSS2Properties style = svgcircle.getSVGStyle();
				useList.add(new SVGInfo(CIRCLE, x, y, r, style, false, clippath, tl));
			}

			if (n instanceof SVGRectElementImpl) {
				SVGRectElementImpl svgrect = (SVGRectElementImpl) n;
				float x = svgrect.getX().getBaseVal().getValue();
				float y= svgrect.getY().getBaseVal().getValue();
				float height =svgrect.getHeight().getBaseVal().getValue();
				float width = svgrect.getWidth().getBaseVal().getValue();
				float rx = svgrect.getRx().getBaseVal().getValue();
				float ry= svgrect.getRy().getBaseVal().getValue();
				String clippath = svgrect.getClipPath();
				SVGTransformList tl = svgrect.getTransform().getBaseVal();
				AbstractCSS2Properties style = svgrect.getSVGStyle();
				useList.add(new SVGInfo(RECT, x, y, height, width, rx, ry, style, false, clippath, tl));
			}

			if (n instanceof SVGEllipseElementImpl) {
				SVGEllipseElementImpl svgellipse = (SVGEllipseElementImpl) n;
				float x = svgellipse.getCx().getBaseVal().getValue();
				float y= svgellipse.getCy().getBaseVal().getValue();
				float rx = svgellipse.getRx().getBaseVal().getValue();
				float ry= svgellipse.getRy().getBaseVal().getValue();
				String clippath = svgellipse.getClipPath();
				SVGTransformList tl = svgellipse.getTransform().getBaseVal();
				AbstractCSS2Properties style = svgellipse.getSVGStyle();
				useList.add(new SVGInfo(ELLIPSE, x, y, rx, ry, style, false, clippath, tl));
			}

			if (n instanceof SVGLineElementImpl) {
				SVGLineElementImpl svgline = (SVGLineElementImpl) n;
				float x1 = svgline.getX1().getBaseVal().getValue();
				float x2= svgline.getX2().getBaseVal().getValue();
				float y1 = svgline.getY1().getBaseVal().getValue();
				float y2 = svgline.getY2().getBaseVal().getValue();
				String clippath = svgline.getClipPath();
				SVGTransformList tl = svgline.getTransform().getBaseVal();
				AbstractCSS2Properties style = svgline.getSVGStyle();
				useList.add(new SVGInfo(LINE, x1, y1, x2, y2, style, clippath, false, tl));
			}

			if (n instanceof SVGPolylineElementImpl) {
				SVGPolylineElementImpl svgpolyline = (SVGPolylineElementImpl) n;
				SVGPointList points = svgpolyline.getPoints();
				String clippath = svgpolyline.getClipPath();
				SVGTransformList tl = svgpolyline.getTransform().getBaseVal();
				AbstractCSS2Properties style = svgpolyline.getSVGStyle();
				useList.add(new SVGInfo(POLYLINE, points, style, false, clippath, tl));
			}

			if (n instanceof SVGPolygonElementImpl) {
				SVGPolygonElementImpl svgpolygon = (SVGPolygonElementImpl) n;
				SVGPointList points = svgpolygon.getPoints();
				String clippath = svgpolygon.getClipPath();
				SVGTransformList tl = svgpolygon.getTransform().getBaseVal();
				AbstractCSS2Properties style = svgpolygon.getSVGStyle();
				useList.add(new SVGInfo(POLYGON, points, style, false, clippath, tl));
			}

			if (n instanceof SVGPathElementImpl) {
				SVGPathElementImpl svgpath = (SVGPathElementImpl)n;
				SVGPathSegList points = svgpath.getPathSegList();
				String clippath = svgpath.getClipPath();
				SVGTransformList tl = svgpath.getTransform().getBaseVal();
				AbstractCSS2Properties style = svgpath.getSVGStyle();
				useList.add(new SVGInfo(PATH, points, style, false, clippath, tl));
			}

			if (n instanceof SVGGElementImpl) {
				SVGGElementImpl svgGroup = (SVGGElementImpl) n;
				SVGTransformList tl = svgGroup.getTransform().getBaseVal();
				AbstractCSS2Properties style = svgGroup.getSVGStyle();
				setSvgiGroup(new SVGInfo(style, tl));

				NodeList gChildNodes = svgGroup.getChildNodes();
				for (int g = 0; g < gChildNodes.getLength(); g++) {
					Node n1 = (Node) gChildNodes.item(g);
					useChildren(n1);
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
		}
		return useList;
	}
	
	private Paint gradient(Element gradient, Shape shape2d) {
		if (gradient instanceof SVGRadialGradientElementImpl) {
			SVGRadialGradientElementImpl radial = (SVGRadialGradientElementImpl)gradient;
			return radial(shape2d, radial, fractions(radial), colors(radial));
		}
		
		if(gradient instanceof SVGLinearGradientElementImpl){
			SVGLinearGradientElementImpl linear = (SVGLinearGradientElementImpl)gradient;
			return new LinearGradientPaint(linear.getX1().getBaseVal().getValue(),
										   linear.getY1().getBaseVal().getValue(), 
										   linear.getX2().getBaseVal().getValue(),
										   linear.getY2().getBaseVal().getValue(), 
										   fractions(linear), colors(linear));
		}
		return null;
	}
	
	private Paint radial(Shape shape, SVGRadialGradientElementImpl radial, float[] fractions, Color[] colors) {
		
		float x = radial.getCx().getBaseVal().getValue();
		float y = radial.getCy().getBaseVal().getValue(); 
		float radius = radial.getR().getBaseVal().getValue();
		double w = shape.getBounds2D().getWidth();
		double h = shape.getBounds2D().getHeight();
		Point2D.Float center = new Point2D.Float(x/100, y/100);		
		double cx = w * center.getX() + shape.getBounds2D().getX();
		double cy = h * center.getY() + shape.getBounds2D().getY();
		final Point2D newCenter = new Point2D.Double(cx, cy);
		double delta = newCenter.distance(new Point2D.Double(shape.getBounds2D().getCenterX(), shape.getBounds2D().getCenterY()));
		final double r = Math.sqrt(w * w + h * h) / 2;
		final float newRadius = (float) (delta + r * (radius/100));
		
		return new RadialGradientPaint(newCenter, newRadius, newCenter,
                                 fractions, colors,
                                 MultipleGradientPaint.CycleMethod.REFLECT,
                                 MultipleGradientPaint.ColorSpaceType.SRGB,
                                 new AffineTransform());
		
	}
	
	private float[] fractions(Element elem) {
		ArrayList<Float> fractions = new ArrayList<Float>();
		NodeList childNodes = elem.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = (Node) childNodes.item(i);
			if (n instanceof SVGStopElementImpl) {
				SVGStopElementImpl stop = (SVGStopElementImpl) n;
				logger.error("offset: " + stop.getOffset().getBaseVal());
				fractions.add(stop.getOffset().getBaseVal());
			}
		}
		float[] floatArray = new float[fractions.size()];
		int i = 0;

		for (Float f : fractions) {
			floatArray[i++] = (f != null ? f : Float.NaN);
		}
		Arrays.sort(floatArray);
		return floatArray;
	}
	
	private Color[] colors(Element elem) {
		ArrayList<Color> colors = new ArrayList<Color>();
		NodeList childNodes = elem.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = (Node) childNodes.item(i);
			if (n instanceof SVGStopElementImpl) {
				SVGStopElementImpl stop = (SVGStopElementImpl) n;
				colors.add(stop.getStopColor());
			}
		}
		Color[] colorArray = new Color[colors.size()];
		int i = 0;
		for (Color c : colors) {
			colorArray[i++] = c;
		}
		return colorArray;
	}
	
	private void clip(SVGInfo svginfo, Graphics2D g2d) {
		
		if(svginfo.getClipPath()!= null && svginfo.getClipPath().contains("url")){
			String clipElemId = svginfo.getClipPath().split("#")[1].replace(")","").trim();
			Element elementById = modelN.getOwnerDocument().getElementById(clipElemId);
			NodeList gChildNodes = elementById.getChildNodes();
			for (int g = 0; g < gChildNodes.getLength(); g++) {
				Node n1 = (Node) gChildNodes.item(g);
				if (n1 instanceof SVGRectElementImpl) {
					SVGRectElementImpl svgrect = (SVGRectElementImpl) n1;
					float x = svgrect.getX().getBaseVal().getValue();
					float y= svgrect.getY().getBaseVal().getValue();
					float height =svgrect.getHeight().getBaseVal().getValue();
					float width = svgrect.getWidth().getBaseVal().getValue();
					float rx = svgrect.getRx().getBaseVal().getValue();
					float ry= svgrect.getRy().getBaseVal().getValue();
					AbstractCSS2Properties style = svginfo.getStyle();
					SVGInfo svgi = new SVGInfo(RECT, x, y, height, width, rx, ry, style, true, null, null);
					rectangle(g2d, svgi);
				}
				
				if (n1 instanceof SVGCircleElementImpl) {
					SVGCircleElementImpl svgcircle = (SVGCircleElementImpl) n1;
					float x = svgcircle.getCx().getBaseVal().getValue();
					float y= svgcircle.getCy().getBaseVal().getValue();
					float r = svgcircle.getR().getBaseVal().getValue();
					AbstractCSS2Properties style = svgcircle.getSVGStyle();
					SVGInfo svgi = new SVGInfo(CIRCLE, x, y, r, style, true, null, null);
					circle(g2d, svgi);
				}

				if (n1 instanceof SVGEllipseElementImpl) {
					SVGEllipseElementImpl svgellipse = (SVGEllipseElementImpl) n1;
					float x = svgellipse.getCx().getBaseVal().getValue();
					float y= svgellipse.getCy().getBaseVal().getValue();
					float rx = svgellipse.getRx().getBaseVal().getValue();
					float ry= svgellipse.getRy().getBaseVal().getValue();
					AbstractCSS2Properties style = svgellipse.getSVGStyle();
					SVGInfo svgi = new SVGInfo(ELLIPSE, x, y, rx, ry, style, true, null, null);
					ellipse(g2d, svgi);
				}

				if (n1 instanceof SVGLineElementImpl) {
					SVGLineElementImpl svgline = (SVGLineElementImpl) n1;
					float x1 = svgline.getX1().getBaseVal().getValue();
					float x2= svgline.getX2().getBaseVal().getValue();
					float y1 = svgline.getY1().getBaseVal().getValue();
					float y2 = svgline.getY2().getBaseVal().getValue();
					AbstractCSS2Properties style = svgline.getSVGStyle();
					SVGInfo svgi = new SVGInfo(LINE, x1, y1, x2, y2, style, null, true, null);
					line(g2d, svgi);
				}

				if (n1 instanceof SVGPolylineElementImpl) {
					SVGPolylineElementImpl svgpolyline = (SVGPolylineElementImpl) n1;
					SVGPointList points = svgpolyline.getPoints();
					AbstractCSS2Properties style = svgpolyline.getSVGStyle();
					SVGInfo svgi = new SVGInfo(POLYLINE, points, style, true, null, null);
					polygon(g2d, svgi);
				}

				if (n1 instanceof SVGPolygonElementImpl) {
					SVGPolygonElementImpl svgpolygon = (SVGPolygonElementImpl) n1;
					SVGPointList points = svgpolygon.getPoints();
					AbstractCSS2Properties style = svgpolygon.getSVGStyle();
					SVGInfo svgi = new SVGInfo(POLYGON, points, style, true, null, null);
					polygon(g2d, svgi);
				}

				if (n1 instanceof SVGPathElementImpl) {
					SVGPathElementImpl svgpath = (SVGPathElementImpl)n1;
					SVGPathSegList points = svgpath.getPathSegList();
					AbstractCSS2Properties style = svgpath.getSVGStyle();
					SVGInfo svgi = new SVGInfo(PATH, points, style, true, null, null);
					path(g2d, svgi);
				}
			}
		}
	}
	
	/**
	 * @return the svgiGroup
	 */
	public SVGInfo getSvgiGroup() {
		return svgiGroup;
	}

	/**
	 * @param svgiGroup the svgiGroup to set
	 */
	public void setSvgiGroup(SVGInfo svgiGroup) {
		this.svgiGroup = svgiGroup;
	}

}