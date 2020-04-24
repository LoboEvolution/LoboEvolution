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

public interface SVGFETurbulenceElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Turbulence Types
	static final short SVG_TURBULENCE_TYPE_UNKNOWN = 0;
	static final short SVG_TURBULENCE_TYPE_FRACTALNOISE = 1;
	static final short SVG_TURBULENCE_TYPE_TURBULENCE = 2;
	// Stitch Options
	static final short SVG_STITCHTYPE_UNKNOWN = 0;
	static final short SVG_STITCHTYPE_STITCH = 1;
	static final short SVG_STITCHTYPE_NOSTITCH = 2;

	SVGAnimatedNumber getBaseFrequencyX();

	SVGAnimatedNumber getBaseFrequencyY();

	SVGAnimatedInteger getNumOctaves();

	SVGAnimatedNumber getSeed();

	SVGAnimatedEnumeration getStitchTiles();

	SVGAnimatedEnumeration getType();
}
