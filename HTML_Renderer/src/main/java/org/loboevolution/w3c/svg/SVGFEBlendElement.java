
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
package org.loboevolution.w3c.svg;

public interface SVGFEBlendElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Blend Mode Types
	public static final short SVG_FEBLEND_MODE_UNKNOWN = 0;
	public static final short SVG_FEBLEND_MODE_NORMAL = 1;
	public static final short SVG_FEBLEND_MODE_MULTIPLY = 2;
	public static final short SVG_FEBLEND_MODE_SCREEN = 3;
	public static final short SVG_FEBLEND_MODE_DARKEN = 4;
	public static final short SVG_FEBLEND_MODE_LIGHTEN = 5;

	public SVGAnimatedString getIn1();

	public SVGAnimatedString getIn2();

	public SVGAnimatedEnumeration getMode();
}
