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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.StringTokenizer;

import org.lobobrowser.util.Strings;
import org.lobobrowser.html.info.SVGInfo;
import org.lobobrowser.html.style.CSSValuesProperties;
import org.lobobrowser.html.style.FontValues;
import org.lobobrowser.util.gui.ColorFactory;
import org.lobobrowser.util.gui.FontFactory;
import org.lobobrowser.util.gui.LAFSettings;
import org.lobobrowser.w3c.smil.ElementTargetAttributes;
import org.lobobrowser.w3c.smil.Time;
import org.lobobrowser.w3c.smil.TimeList;
import org.lobobrowser.w3c.svg.SVGLength;
import org.lobobrowser.w3c.svg.SVGLengthList;
import org.lobobrowser.w3c.svg.SVGPoint;
import org.lobobrowser.w3c.svg.SVGPointList;
import org.lobobrowser.w3c.svg.SVGTransform;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SVGUtility {

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

		if (lengthString == null) {
			return lengthList;
		}

		StringTokenizer st = new StringTokenizer(lengthString, " ", false);
		while (st.hasMoreTokens()) {
			int length = Integer.parseInt(st.nextToken());
			SVGLength lnt = new SVGLengthImpl(length);
			lengthList.appendItem(lnt);
		}
		return lengthList;
	}

	public static Font getFontValue(String ff, String fs) {

		float fontSize = LAFSettings.getInstance().getFontSize();
		String fontStyle = CSSValuesProperties.ITALIC;
		String fontVariant = CSSValuesProperties.SMALL_CAPS;
		String fontFamily = Font.SANS_SERIF;
		String fontWeight = CSSValuesProperties.BOLD;

		if (fs != null) {
			fontSize = FontValues.getFontSize(fs, null);
		}

		if (ff != null) {
			fontFamily = ff;
		}
		return FontFactory.getInstance().getFont(fontFamily, fontStyle, fontVariant, fontWeight, fontSize, null, null,
				0, false, 0);
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
				text = nodeValue;
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
		switch (Strings.isBlank(textAnchor) ? "" : textAnchor) {
		case "middle":
			double swidth = path.getBounds2D().getWidth();
			path.transform(AffineTransform.getTranslateInstance(-swidth / 2.0, 0));
			break;
		case "end":
			swidth = path.getBounds2D().getWidth();
			path.transform(AffineTransform.getTranslateInstance(-swidth, 0));
			break;
		default:
			break;
		}
	}

	public static float getClockMilliSecs(String clockVal) {
		try {
			if (clockVal.indexOf(":") != -1) {
				StringTokenizer st = new StringTokenizer(clockVal, ":");
				int numTokens = st.countTokens();
				if (numTokens == 3) { // is a full clock value
					int hours = Integer.parseInt(st.nextToken());
					int minutes = Integer.parseInt(st.nextToken());
					float seconds = Float.parseFloat(st.nextToken());
					return (hours * 3600 + minutes * 60 + seconds) * 1000;
				} else if (numTokens == 2) { // is a partial clock value
					int minutes = Integer.parseInt(st.nextToken());
					float seconds = Float.parseFloat(st.nextToken());
					return (minutes * 60 + seconds) * 1000;
				} else {
					return 0;
				}
			} else {
				if (clockVal.indexOf("h") != -1) {
					float hour = Float.parseFloat(clockVal.substring(0, clockVal.indexOf("h")));
					return (hour * 3600) * 1000;
				} else if (clockVal.indexOf("min") != -1) {
					float min = Float.parseFloat(clockVal.substring(0, clockVal.indexOf("min")));
					return (min * 60) * 1000;
				} else if (clockVal.indexOf("ms") != -1) {
					return Float.parseFloat(clockVal.substring(0, clockVal.indexOf("ms")));
				} else if (clockVal.indexOf("s") != -1) {
					float secs = Float.parseFloat(clockVal.substring(0, clockVal.indexOf("s")));
					return secs * 1000;
				} else {
					float secs = Float.parseFloat(clockVal);
					return secs * 1000;
				}
			}
		} catch (Exception e) {
			return 0;
		}
	}

	public static int timerDelay(SVGAnimationImpl animate) {
		if (animate.getDur() == 0)
			return 5;

		if ("transform".equalsIgnoreCase(animate.getAttributeName())) {
			return timeDelayTransform(animate);
		} else if (ElementTargetAttributes.ATTRIBUTE_TYPE_XML == animate.getAttributeType()) {
			return timerDelayFloat(animate);
		} else {
			return timerDelayColor(animate);
		}
	}

	private static int timerDelayFloat(SVGAnimationImpl animate) {
		float dur = animate.getDur();
		float from = Float.parseFloat(animate.getFrom());
		float to = Float.parseFloat(animate.getTo());
		float range = to - from;
		return Math.round(dur / range);
	}

	private static int timerDelayColor(SVGAnimationImpl animate) {
		float dur = animate.getDur();
		Color from = ColorFactory.getInstance().getColor(animate.getFrom());
		Color to = ColorFactory.getInstance().getColor(animate.getTo());
		float range = (to.getRed() - from.getRed()) + (to.getBlue() - from.getBlue()) + (to.getGreen() - from.getGreen());
		if(range == 0) return 255;
		return Math.round(dur / range);
	}

	private static int timeDelayTransform(SVGAnimationImpl animate) {
		float dur = animate.getDur();
		float range = 0;
		String from_trans = animate.getFrom();
		String to_trans = animate.getTo();
		StringTokenizer stFrom = new StringTokenizer(from_trans, " ,");

		if (animate.getType() == SVGTransform.SVG_TRANSFORM_TRANSLATE) {

			float txFrom = 0;
			float tyFrom = 0;
			if (stFrom.countTokens() == 1) {
				txFrom = Float.parseFloat(stFrom.nextToken());
			} else if (stFrom.countTokens() == 2) {
				txFrom = Float.parseFloat(stFrom.nextToken());
				tyFrom = Float.parseFloat(stFrom.nextToken());
			}

			StringTokenizer stTo = new StringTokenizer(to_trans, " ,");
			float txTo = 0;
			float tyTo = 0;
			if (stTo.countTokens() == 1) {
				txTo = Float.parseFloat(stTo.nextToken());
			} else if (stTo.countTokens() == 2) {
				txTo = Float.parseFloat(stTo.nextToken());
				tyTo = Float.parseFloat(stTo.nextToken());
			}

			range = (txTo - txFrom) + (tyTo - tyFrom);
			return Math.round(dur / range);

		} else if (animate.getType() == SVGTransform.SVG_TRANSFORM_SCALE) {

			float sxFrom = 0;
			float syFrom = 0;
			if (stFrom.countTokens() == 1) {
				sxFrom = Float.parseFloat(stFrom.nextToken());
			} else if (stFrom.countTokens() == 2) {
				sxFrom = Float.parseFloat(stFrom.nextToken());
				syFrom = Float.parseFloat(stFrom.nextToken());
			}

			StringTokenizer stTo = new StringTokenizer(to_trans, " ,");
			float sxTo = 0;
			float syTo = 0;
			if (stTo.countTokens() == 1) {
				sxTo = Float.parseFloat(stTo.nextToken());
			} else if (stTo.countTokens() == 2) {
				sxTo = Float.parseFloat(stTo.nextToken());
				syTo = Float.parseFloat(stTo.nextToken());
			}

			range = (sxTo - sxFrom) + (syTo - syFrom);
			return Math.round(dur / range);

		} else if (animate.getType() == SVGTransform.SVG_TRANSFORM_ROTATE) {

			float cxFrom = 0;
			float cyFrom = 0;

			if (stFrom.countTokens() == 3) {
				cxFrom = Float.parseFloat(stFrom.nextToken());
				cyFrom = Float.parseFloat(stFrom.nextToken());
			}

			StringTokenizer stTo = new StringTokenizer(to_trans, " ,");
			float cxTo = 0;
			float cyTo = 0;

			if (stTo.countTokens() == 3) {
				cxTo = Float.parseFloat(stTo.nextToken());
				cyTo = Float.parseFloat(stTo.nextToken());
			}

			range = (cxTo - cxFrom) + (cyTo - cyFrom);
			return Math.round(dur / range);

		} else if (animate.getType() == SVGTransform.SVG_TRANSFORM_SKEWX) {

			float sxFrom = Float.parseFloat(from_trans);
			float sxTo = Float.parseFloat(to_trans);
			range = sxTo - sxFrom;
			return Math.round(dur / range);

		} else if (animate.getType() == SVGTransform.SVG_TRANSFORM_SKEWY) {

			float sxFrom = Float.parseFloat(from_trans);
			float sxTo = Float.parseFloat(to_trans);
			range = sxTo - sxFrom;
			return Math.round(dur / range);
		}
		return 0;
	}
	
	public static int begin(SVGAnimationImpl animate){
		TimeList begin = animate.getBegin();
		Time time = begin.item(0);
		return new Double(time.getResolvedOffset()).intValue();
	}
	
	public static Shape createArc(float x1, float y1, float x2, float y2, float rx, float ry, float angle, boolean fA, boolean fS) {

		double cosAngle = Math.cos(angle);
		double sinAngle = Math.sin(angle);
		double x1prime = cosAngle * (x1 - x2) / 2 + sinAngle * (y1 - y2) / 2;
		double y1prime = -sinAngle * (x1 - x2) / 2 + cosAngle * (y1 - y2) / 2;
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

		if (ux * vy - uy * vx < 0) {
			startAngle = -startAngle;
		}

		ux = (x1prime - cXprime) / rx;
		uy = (y1prime - cYprime) / ry;
		vx = (-x1prime - cXprime) / rx;
		vy = (-y1prime - cYprime) / ry;

		double angleExtent = Math.acos((ux * vx + uy * vy) / (Math.sqrt(ux * ux + uy * uy) * Math.sqrt(vx * vx + vy * vy)));

		if (ux * vy - uy * vx < 0) {
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
}
