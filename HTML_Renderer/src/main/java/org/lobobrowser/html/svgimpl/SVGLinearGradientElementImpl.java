/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution
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

import org.lobobrowser.w3c.svg.SVGAnimatedEnumeration;
import org.lobobrowser.w3c.svg.SVGAnimatedLength;
import org.lobobrowser.w3c.svg.SVGAnimatedString;
import org.lobobrowser.w3c.svg.SVGAnimatedTransformList;
import org.lobobrowser.w3c.svg.SVGLinearGradientElement;

public class SVGLinearGradientElementImpl extends SVGSVGElementImpl implements SVGLinearGradientElement {

	public SVGLinearGradientElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedEnumeration getGradientUnits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGAnimatedTransformList getGradientTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGAnimatedEnumeration getSpreadMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGAnimatedString getHref() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGAnimatedLength getX1() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute(X1)));
	}

	@Override
	public SVGAnimatedLength getY1() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute(Y1)));
	}

	@Override
	public SVGAnimatedLength getX2() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute(X2)));
	}

	@Override
	public SVGAnimatedLength getY2() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute(Y2)));
	}

}
