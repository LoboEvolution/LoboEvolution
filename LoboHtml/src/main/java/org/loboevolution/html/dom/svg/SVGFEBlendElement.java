
/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

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
package org.loboevolution.html.dom.svg;

public interface SVGFEBlendElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Blend Mode Types
	static final short SVG_FEBLEND_MODE_UNKNOWN = 0;
	static final short SVG_FEBLEND_MODE_NORMAL = 1;
	static final short SVG_FEBLEND_MODE_MULTIPLY = 2;
	static final short SVG_FEBLEND_MODE_SCREEN = 3;
	static final short SVG_FEBLEND_MODE_DARKEN = 4;
	static final short SVG_FEBLEND_MODE_LIGHTEN = 5;

	SVGAnimatedString getIn1();

	SVGAnimatedString getIn2();

	SVGAnimatedEnumeration getMode();
}
