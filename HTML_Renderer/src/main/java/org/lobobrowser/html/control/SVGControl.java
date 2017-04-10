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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import org.lobobrowser.html.info.SVGInfo;
import org.lobobrowser.html.svgimpl.SVGCircleElementImpl;
import org.lobobrowser.html.svgimpl.SVGEllipseElementImpl;
import org.lobobrowser.html.svgimpl.SVGLineElementImpl;
import org.lobobrowser.html.svgimpl.SVGPolygonElementImpl;
import org.lobobrowser.html.svgimpl.SVGRectElementImpl;
import org.lobobrowser.html.svgimpl.SVGSVGElementImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SVGControl extends SVGBasicControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private ArrayList<SVGInfo> svgList = new ArrayList<SVGInfo>();
	
	/** The circle. */
	private final int CIRCLE = 1;

	/** The rect. */
	private final int RECT = 2;

	/** The ellipse. */
	private final int ELLIPSE = 3;
	
	/** The line. */
	private final int LINE = 4;
	
	/** The line. */
	private final int POLYGON = 5;
	
	public SVGControl(SVGSVGElementImpl modelNode) {
		super(modelNode);
		NodeList childNodes = modelNode.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = (Node) childNodes.item(i);

			if (n instanceof SVGCircleElementImpl) {
				SVGCircleElementImpl svgcircle = (SVGCircleElementImpl) n;
				SVGInfo svgi = new SVGInfo();
				svgi.setMethod(CIRCLE);
				svgi.setX(svgcircle.getCx().getBaseVal().getValue());
				svgi.setY(svgcircle.getCy().getBaseVal().getValue());
				svgi.setR(svgcircle.getR().getBaseVal().getValue());
				svgi.setStyle(svgcircle.getStyle());
				svgList.add(svgi);
			}

			if (n instanceof SVGRectElementImpl) {
				SVGRectElementImpl svgrect = (SVGRectElementImpl) n;
				SVGInfo svgi = new SVGInfo();
				svgi.setMethod(RECT);
				svgi.setX(svgrect.getX().getBaseVal().getValue());
				svgi.setY(svgrect.getY().getBaseVal().getValue());
				svgi.setWidth(svgrect.getWidth().getBaseVal().getValue());
				svgi.setHeight(svgrect.getHeight().getBaseVal().getValue());
				svgi.setRx(svgrect.getRx().getBaseVal().getValue());
				svgi.setRy(svgrect.getRy().getBaseVal().getValue());
				svgi.setStyle(svgrect.getStyle());
				svgList.add(svgi);
			}

			if (n instanceof SVGEllipseElementImpl) {
				SVGEllipseElementImpl svgellipse = (SVGEllipseElementImpl) n;
				SVGInfo svgi = new SVGInfo();
				svgi.setMethod(ELLIPSE);
				svgi.setX(svgellipse.getCx().getBaseVal().getValue());
				svgi.setY(svgellipse.getCy().getBaseVal().getValue());
				svgi.setRx(svgellipse.getRx().getBaseVal().getValue());
				svgi.setRy(svgellipse.getRy().getBaseVal().getValue());
				svgi.setStyle(svgellipse.getStyle());
				svgList.add(svgi);
			}
			
			if (n instanceof SVGLineElementImpl) {
				SVGLineElementImpl svgline = (SVGLineElementImpl) n;
				SVGInfo svgi = new SVGInfo();
				svgi.setMethod(LINE);
				svgi.setX1(svgline.getX1().getBaseVal().getValue());
				svgi.setY1(svgline.getY1().getBaseVal().getValue());
				svgi.setX2(svgline.getX2().getBaseVal().getValue());
				svgi.setY2(svgline.getY2().getBaseVal().getValue());
				svgi.setStyle(svgline.getStyle());
				svgList.add(svgi);
			}
			
			if (n instanceof SVGPolygonElementImpl) {
				SVGPolygonElementImpl svgline = (SVGPolygonElementImpl) n;
				SVGInfo svgi = new SVGInfo();
				svgi.setMethod(POLYGON);
				svgi.setPoilist(svgline.getPoints());
				svgi.setStyle(svgline.getStyle());
				svgList.add(svgi);
			}
			
			
		}
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (int i = 0; i < svgList.size(); i++) {
			SVGInfo svgi = svgList.get(i);
			switch (svgi.getMethod()) {
			case CIRCLE:
				circle(g2d, svgi);
				break;
			case RECT:
				rectangle(g2d, svgi);
				break;
			case ELLIPSE:
				ellipse(g2d, svgi);
				break;
			case LINE:
				line(g2d, svgi);
				break;
			case POLYGON:
				polygon(g2d, svgi);
				break;
			default:
				break;
			}
		}
	}
}
