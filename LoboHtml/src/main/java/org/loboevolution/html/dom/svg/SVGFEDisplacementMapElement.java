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

public interface SVGFEDisplacementMapElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Channel Selectors
	static final short SVG_CHANNEL_UNKNOWN = 0;
	static final short SVG_CHANNEL_R = 1;
	static final short SVG_CHANNEL_G = 2;
	static final short SVG_CHANNEL_B = 3;
	static final short SVG_CHANNEL_A = 4;

	SVGAnimatedString getIn1();

	SVGAnimatedString getIn2();

	SVGAnimatedNumber getScale();

	SVGAnimatedEnumeration getXChannelSelector();

	SVGAnimatedEnumeration getYChannelSelector();
}
