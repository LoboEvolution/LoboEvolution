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
			fontSize = HtmlValues.getFontSize(fs, null);
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
				;
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

	public static float getClockSecs(String clockVal) {
		try {
			if (clockVal.indexOf(":") != -1) {
				StringTokenizer st = new StringTokenizer(clockVal, ":");
				int numTokens = st.countTokens();
				if (numTokens == 3) { // is a full clock value
					int hours = Integer.parseInt(st.nextToken());
					int minutes = Integer.parseInt(st.nextToken());
					float seconds = Float.parseFloat(st.nextToken());
					return hours * 3600 + minutes * 60 + seconds;
				} else if (numTokens == 2) { // is a partial clock value
					int minutes = Integer.parseInt(st.nextToken());
					float seconds = Float.parseFloat(st.nextToken());
					return minutes * 60 + seconds;
				} else {
					return 0;
				}
			} else {
				if (clockVal.indexOf("h") != -1) {
					float hour = Float.parseFloat(clockVal.substring(0, clockVal.indexOf("h")));
					return hour * 3600;
				} else if (clockVal.indexOf("min") != -1) {
					float min = Float.parseFloat(clockVal.substring(0, clockVal.indexOf("min")));
					return min * 60;
				} else if (clockVal.indexOf("ms") != -1) {
					float ms = Float.parseFloat(clockVal.substring(0, clockVal.indexOf("ms")));
					return (float) (ms / 1000.0);
				} else if (clockVal.indexOf("s") != -1) {
					float secs = Float.parseFloat(clockVal.substring(0, clockVal.indexOf("s")));
					return secs;
				} else {
					float secs = Float.parseFloat(clockVal);
					return secs;
				}
			}
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
