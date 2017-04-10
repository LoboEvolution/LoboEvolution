package org.lobobrowser.html.svgimpl;

import java.util.StringTokenizer;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.w3c.svg.SVGAnimatedTransformList;
import org.lobobrowser.w3c.svg.SVGPoint;
import org.lobobrowser.w3c.svg.SVGPointList;
import org.lobobrowser.w3c.svg.SVGPolygonElement;

public class SVGPolygonElementImpl extends SVGSVGElementImpl implements SVGPolygonElement {

	public SVGPolygonElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedTransformList getTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGPointList getPoints() {
		return constructPointList(this.getAttribute(HtmlAttributeProperties.POINTS));
	}

	@Override
	public SVGPointList getAnimatedPoints() {
		return constructPointList(this.getAttribute(HtmlAttributeProperties.POINTS));
	}

	private SVGPointList constructPointList(String pointString) {
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
}
