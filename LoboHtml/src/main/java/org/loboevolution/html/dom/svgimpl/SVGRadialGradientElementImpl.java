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

import org.loboevolution.html.dom.svg.SVGAnimatedEnumeration;
import org.loboevolution.html.dom.svg.SVGAnimatedLength;
import org.loboevolution.html.dom.svg.SVGAnimatedString;
import org.loboevolution.html.dom.svg.SVGAnimatedTransformList;
import org.loboevolution.html.dom.svg.SVGRadialGradientElement;

public class SVGRadialGradientElementImpl extends SVGSVGElementImpl implements SVGRadialGradientElement {

	public SVGRadialGradientElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedEnumeration getGradientUnits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGAnimatedTransformList getGradientTransform() {
		return new SVGAnimatedTransformListImpl(this.getAttribute("transform"));
	}

	@Override
	public SVGAnimatedEnumeration getSpreadMethod() {
		// TODO Auto-generated method stub
		return null;
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
	public SVGAnimatedLength getCx() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("cx")));
	}

	@Override
	public SVGAnimatedLength getCy() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("cy")));
	}

	@Override
	public SVGAnimatedLength getR() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("r")));
	}

	@Override
	public SVGAnimatedLength getFx() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("fx")));
	}

	@Override
	public SVGAnimatedLength getFy() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("fy")));
	}
}
