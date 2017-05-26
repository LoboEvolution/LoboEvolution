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

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.w3c.svg.SVGAnimatedTransformList;
import org.lobobrowser.w3c.svg.SVGPointList;
import org.lobobrowser.w3c.svg.SVGPolygonElement;

public class SVGPolygonElementImpl extends SVGSVGElementImpl implements SVGPolygonElement {

	public SVGPolygonElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedTransformList getTransform() {
		return new SVGAnimatedTransformListImpl(this.getAttribute(HtmlAttributeProperties.TRANSFORM));
	}

	@Override
	public SVGPointList getPoints() {
		return SVGUtility.constructPointList(this.getAttribute(HtmlAttributeProperties.POINTS));
	}

	@Override
	public SVGPointList getAnimatedPoints() {
		return SVGUtility.constructPointList(this.getAttribute(HtmlAttributeProperties.POINTS));
	}

	@Override
	public AbstractCSS2Properties getSVGStyle() {
		AbstractCSS2Properties style = this.getStyle();
		style.setFill(this.getFill());
		style.setStroke(this.getStroke());
		style.setStrokeDashArray(this.getStrokeDashArray());
		style.setStrokeLineCap(this.getStrokeLineCap());
		style.setStrokeMiterLimit(this.getStrokeMiterLimit());
		style.setStrokeOpacity(this.getStrokeOpacity());
		style.setStrokeWidth(this.getStrokeWidth());
		return style;
	}
}
