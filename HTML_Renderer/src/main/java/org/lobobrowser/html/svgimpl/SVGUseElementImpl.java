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
import org.lobobrowser.w3c.svg.SVGAnimatedString;
import org.lobobrowser.w3c.svg.SVGAnimatedTransformList;
import org.lobobrowser.w3c.svg.SVGElementInstance;
import org.lobobrowser.w3c.svg.SVGUseElement;

public class SVGUseElementImpl extends SVGSVGElementImpl implements SVGUseElement {

	public SVGUseElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedString getHref() {
		return new SVGAnimatedStringImpl(this.getAttribute(HtmlAttributeProperties.XLINK_HREF));
	}

	@Override
	public SVGAnimatedTransformList getTransform() {
		return new SVGAnimatedTransformListImpl(this.getAttribute(HtmlAttributeProperties.TRANSFORM));
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
}