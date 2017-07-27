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

import org.lobobrowser.w3c.svg.SVGAnimatedLengthList;
import org.lobobrowser.w3c.svg.SVGLengthList;

public class SVGAnimatedLengthListImpl implements SVGAnimatedLengthList {

	private SVGLengthList lengths;

	public SVGAnimatedLengthListImpl(SVGLengthList lengths) {
		this.lengths = lengths;
	}

	@Override
	public SVGLengthList getBaseVal() {
		return this.lengths;
	}

	@Override
	public SVGLengthList getAnimVal() {
		return this.lengths;
	}
}