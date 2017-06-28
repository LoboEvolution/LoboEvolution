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
import org.lobobrowser.w3c.svg.SVGAnimatedEnumeration;
import org.lobobrowser.w3c.svg.SVGAnimatedLength;
import org.lobobrowser.w3c.svg.SVGAnimatedString;
import org.lobobrowser.w3c.svg.SVGAnimatedTransformList;
import org.lobobrowser.w3c.svg.SVGRadialGradientElement;

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
		return new SVGAnimatedTransformListImpl(this.getAttribute(HtmlAttributeProperties.TRANSFORM));
	}

	@Override
	public SVGAnimatedEnumeration getSpreadMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGAnimatedString getHref() {
		String href = this.getAttribute(HtmlAttributeProperties.XLINK_HREF);
		if (href == null) {
			href = this.getAttribute(HtmlAttributeProperties.HREF);
		}
		return new SVGAnimatedStringImpl(href);
	}

	@Override
	public SVGAnimatedLength getCx() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute(HtmlAttributeProperties.CX)));
	}

	@Override
	public SVGAnimatedLength getCy() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute(HtmlAttributeProperties.CY)));
	}

	@Override
	public SVGAnimatedLength getR() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute(HtmlAttributeProperties.R)));
	}

	@Override
	public SVGAnimatedLength getFx() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute(HtmlAttributeProperties.FX)));
	}

	@Override
	public SVGAnimatedLength getFy() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute(HtmlAttributeProperties.FY)));
	}
}
