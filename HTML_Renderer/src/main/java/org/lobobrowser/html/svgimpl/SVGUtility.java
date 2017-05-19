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
package org.lobobrowser.html.svgimpl;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.StringTokenizer;

import org.lobobrowser.html.info.SVGInfo;
import org.lobobrowser.html.style.CSSValuesProperties;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.util.gui.FontFactory;
import org.lobobrowser.util.gui.LAFSettings;
import org.lobobrowser.w3c.svg.SVGLength;
import org.lobobrowser.w3c.svg.SVGLengthList;
import org.lobobrowser.w3c.svg.SVGPoint;
import org.lobobrowser.w3c.svg.SVGPointList;
import org.lobobrowser.w3c.svg.SVGTransformList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SVGUtility {
	
	public static String SCALE = "scale";
	public static String TRANSLATE = "translate";
	public static String MATRIX = "matrix";
	public static String ROTATE = "rotate";
	public static String SKEW_X = "skewX";
	public static String SKEW_Y= "skewY";
	
	
	public static SVGPointList constructPointList(String pointString) {
		SVGPointListImpl points = new SVGPointListImpl();
		StringTokenizer st = new StringTokenizer(pointString, " ,", false);
		while (st.hasMoreTokens()) {
			float x = Float.parseFloat(st.nextToken());
			float y = Float.parseFloat(st.nextToken());
			SVGPoint point = new SVGPointImpl(x, y);
			points.appendItem(point);
		}
		return points;
	}
	
	public static SVGLengthList constructLengthList(String lengthString) {
		
		SVGLengthListImpl lengthList = new SVGLengthListImpl();
		
		if(lengthString == null)
			return lengthList;
			
		StringTokenizer st = new StringTokenizer(lengthString, " ", false);
		while (st.hasMoreTokens()) {
			int length = Integer.parseInt(st.nextToken());
			SVGLength lnt = new SVGLengthImpl(length);
			lengthList.appendItem(lnt);
		}
		return lengthList;
	}	
	
	public static SVGTransformList createTransformList(String transformString) {

		if (transformString == null)
			return null;

		transformString = transformString.trim();
		SVGTransformListImpl transformList = new SVGTransformListImpl();
		StringTokenizer st = new StringTokenizer(transformString, "()", false);

		while (st.hasMoreTokens()) {

			String transformType = st.nextToken().trim();
			if (!st.hasMoreTokens()){
				break;
			}
			
			String transformArgs = st.nextToken().trim();
			if (transformType.equals(MATRIX)) {
				StringTokenizer st1 = new StringTokenizer(transformArgs, ", ", false);
				int numArgs = st1.countTokens();
				if (numArgs == 6) {
					float a = Float.parseFloat(st1.nextToken());
					float b = Float.parseFloat(st1.nextToken());
					float c = Float.parseFloat(st1.nextToken());
					float d = Float.parseFloat(st1.nextToken());
					float e = Float.parseFloat(st1.nextToken());
					float f = Float.parseFloat(st1.nextToken());
					SVGTransformImpl transform = new SVGTransformImpl(SVGTransformImpl.SVG_TRANSFORM_MATRIX);
					 SVGMatrixImpl matrix = new SVGMatrixImpl(a,b,c,d,e,f);
					 transform.setMatrix(matrix);
					 transformList.appendItem(transform);
				}
			} else if (transformType.equals(TRANSLATE)) {
				StringTokenizer st1 = new StringTokenizer(transformArgs, ", ", false);
				int numArgs = st1.countTokens();
				float tx = 0;
				float ty = 0;
				if (numArgs == 1) {
					tx = Float.parseFloat(st1.nextToken());
				} else if (numArgs == 2) {
					tx = Float.parseFloat(st1.nextToken());
					ty = Float.parseFloat(st1.nextToken());
				} else {
					if (numArgs > 2) {
						tx = Float.parseFloat(st1.nextToken());
						ty = Float.parseFloat(st1.nextToken());
					}
				}
				SVGTransformImpl transform = new SVGTransformImpl(SVGTransformImpl.SVG_TRANSFORM_TRANSLATE);
				transform.setTranslate(tx, ty);
				transformList.appendItem(transform);
			} else if (transformType.equals(SCALE)) {
				StringTokenizer st1 = new StringTokenizer(transformArgs, ", ", false);
				int numArgs = st1.countTokens();
				float sx = 0;
				float sy = 0;
				if (numArgs == 1) {
					sx = Float.parseFloat(st1.nextToken());
					sy = sx;
				} else if (numArgs == 2) {
					sx = Float.parseFloat(st1.nextToken());
					sy = Float.parseFloat(st1.nextToken());
				} else {
					if (numArgs > 2) {
						sx = Float.parseFloat(st1.nextToken());
						sy = Float.parseFloat(st1.nextToken());
					}
				}
				SVGTransformImpl transform = new SVGTransformImpl(SVGTransformImpl.SVG_TRANSFORM_SCALE);
				transform.setScale(sx, sy);
				transformList.appendItem(transform);
			} else if (transformType.equals(ROTATE)) {
				StringTokenizer st1 = new StringTokenizer(transformArgs, ", ", false);
				int numArgs = st1.countTokens();
				float angle = 0;
				float cx = 0;
				float cy = 0;
				if (numArgs == 1) {
					angle = Float.parseFloat(st1.nextToken());
				} else if (numArgs == 3) {
					angle = Float.parseFloat(st1.nextToken());
					cx = Float.parseFloat(st1.nextToken());
					cy = Float.parseFloat(st1.nextToken());
				} else {
					if (numArgs == 2) {
						angle = Float.parseFloat(st1.nextToken());
					} else if (numArgs > 3) {
						angle = Float.parseFloat(st1.nextToken());
						cx = Float.parseFloat(st1.nextToken());
						cy = Float.parseFloat(st1.nextToken());
					}
				}
				SVGTransformImpl transform = new SVGTransformImpl(SVGTransformImpl.SVG_TRANSFORM_ROTATE);
				transform.setRotate(angle, cx, cy);
				transformList.appendItem(transform);
			} else if (transformType.equals(SKEW_X)) {
				float skewAngle = Float.parseFloat(transformArgs);
				SVGTransformImpl transform = new SVGTransformImpl(SVGTransformImpl.SVG_TRANSFORM_SKEWX);
				transform.setSkewX(skewAngle);
				transformList.appendItem(transform);
			} else if (transformType.equals(SKEW_Y)) {
				float skewAngle = Float.parseFloat(transformArgs);
				SVGTransformImpl transform = new SVGTransformImpl(SVGTransformImpl.SVG_TRANSFORM_SKEWY);
				transform.setSkewY(skewAngle);
				transformList.appendItem(transform);
			}
		}
		return transformList;
	}

	public static Font getFontValue(String ff, String fs) {

		float fontSize = LAFSettings.getInstance().getFontSize();
		String fontStyle = CSSValuesProperties.ITALIC;
		String fontVariant = CSSValuesProperties.SMALL_CAPS;
		String fontFamily = Font.SANS_SERIF;
		String fontWeight = CSSValuesProperties.BOLD;
		
		if (fs != null) {
			fontSize = HtmlValues.getFontSize(fs, null);
		}

		if (ff != null) {
			fontFamily = ff;
		}
		return FontFactory.getInstance().getFont(fontFamily, fontStyle, fontVariant, fontWeight, fontSize, null, null,0,false,0);
	}

	public static String getText(Node n) {
		String text = "";
		NodeList children = n.getChildNodes();
		int numChildren = children.getLength();
		for (int i = 0; i < numChildren; i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.TEXT_NODE) { // it is #PCDATA
				String nodeValue = child.getNodeValue();
				nodeValue = nodeValue.replace('\n', ' ');
				nodeValue = nodeValue.replace('\r', ' ');
				nodeValue = nodeValue.replace('\t', ' ');
				text = nodeValue;;
			}
		}
		return text.trim();
	}

	public static Point2D.Float calcTextPos(SVGInfo svgi, Graphics2D graphics) {
		FontMetrics metrics = graphics.getFontMetrics();
		float x = svgi.getX();
		float y = svgi.getY();
		y = y + metrics.getLeading() + metrics.getAscent();
		return new Point2D.Float(x, y);
	}

	public static void getTextAnchor(String textAnchor, GeneralPath path) {
		if (textAnchor != null) {
			if (textAnchor.equals("middle")) {
				double swidth = path.getBounds2D().getWidth();
				path.transform(AffineTransform.getTranslateInstance(-swidth / 2.0, 0));
			} else if (textAnchor.equals("end")) {
				double swidth = path.getBounds2D().getWidth();
				path.transform(AffineTransform.getTranslateInstance(-swidth, 0));
			}
		}
	}
}
