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
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.info.SVGInfo;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.util.gui.ColorFactory;
import org.lobobrowser.w3c.svg.SVGPoint;
import org.lobobrowser.w3c.svg.SVGPointList;

public class SVGBasicControl extends BaseControl {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger(SVGBasicControl.class.getName());

	public SVGBasicControl(HTMLElementImpl modelNode) {
		super(modelNode);
	}

	public void circle(Graphics2D g2d, SVGInfo svgi) {
		Shape circle = new Ellipse2D.Double(svgi.getX() - svgi.getR(), svgi.getY() - svgi.getR(), 2 * svgi.getR(), 2 * svgi.getR());
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
			rect = new RoundRectangle2D.Float(svgi.getX(), svgi.getY(), svgi.getWidth(), svgi.getHeight(), svgi.getRx() * 2, svgi.getRy() * 2);
		} else {
			rect = new Rectangle2D.Float(svgi.getX(), svgi.getY(), svgi.getWidth(), svgi.getHeight());
		}
		drawFillAndStroke(g2d, rect, svgi);
	}

	public void ellipse(Graphics2D g2d, SVGInfo svgi) {
		Point2D.Float center = convertCoordinate(new Point2D.Float(svgi.getX(), svgi.getY()));
		Point2D.Float corner = convertCoordinate(new Point2D.Float(svgi.getX() - svgi.getRx(), svgi.getY() - svgi.getRy()));
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
	        path.lineTo(x,y);
	      }
	    }
	    
	    path.closePath();
	    drawFillAndStroke(g2d, path, svgi);
		
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
			
			logger.error("fill: " + svgi.getStyle().getFill());
			Color color = ColorFactory.getInstance().getColor(svgi.getStyle().getFill());
			fillColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(255 * fillOpacity));
		}

		if (fillColor != null && strokeColor != null) {
			g2d.setPaint(strokeColor);
			g2d.draw(basicStroke.createStrokedShape(shape2d));
			g2d.setPaint(fillColor);
			g2d.fill(shape2d);
		} else if (fillColor != null) {
			g2d.setPaint(fillColor);
			g2d.fill(shape2d);
		} else if (strokeColor != null) {
			g2d.setPaint(strokeColor);
			g2d.draw(basicStroke.createStrokedShape(shape2d));
		} else {
			g2d.draw(shape2d);
		}

		g2d.setPaint(prevPaint);
		g2d.setStroke(prevStroke);
	}

	protected BasicStroke getStroking(Graphics2D g2d, SVGInfo svgi) {

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
}
