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
package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedString;
import org.loboevolution.html.dom.svg.SVGAnimatedTransformList;
import org.loboevolution.html.dom.svg.SVGElementInstance;
import org.loboevolution.html.dom.svg.SVGUseElement;
import org.loboevolution.html.style.AbstractCSSProperties;

public class SVGUseElementImpl extends SVGSVGElementImpl implements SVGUseElement {

	public SVGUseElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedString getHref() {
		String href = this.getAttribute("xlink:href");
		if (href == null) {
			href = this.getAttribute("href");
		}
		return new SVGAnimatedStringImpl(href);
	}

	@Override
	public SVGAnimatedTransformList getTransform() {
		return new SVGAnimatedTransformListImpl(this.getAttribute("transform"));
	}

	@Override
	public SVGElementInstance getInstanceRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGElementInstance getAnimatedInstanceRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCSSProperties getSVGStyle() {
		AbstractCSSProperties style = this.getStyle();
		boolean isStyle = false;
		if (this.getFill() != null) {
			style.setFill(this.getFill());
			isStyle = true;
		}

		if (this.getStroke() != null) {
			style.setStroke(this.getStroke());
		}

		if (this.getStrokeDashArray() != null) {
			style.setStrokeDashArray(this.getStrokeDashArray());
			isStyle = true;
		}

		if (this.getStrokeLineCap() != null) {
			style.setStrokeLineCap(this.getStrokeLineCap());
			isStyle = true;
		}

		if (this.getStrokeMiterLimit() != null) {
			style.setStrokeMiterLimit(this.getStrokeMiterLimit());
			isStyle = true;
		}

		if (this.getStrokeOpacity() != null) {
			style.setStrokeOpacity(this.getStrokeOpacity());
			isStyle = true;
		}

		if (this.getStrokeWidth() != null) {
			style.setStrokeWidth(this.getStrokeWidth());
			isStyle = true;
		}

		if (!isStyle) {
			style = null;
		}

		return style;
	}
}