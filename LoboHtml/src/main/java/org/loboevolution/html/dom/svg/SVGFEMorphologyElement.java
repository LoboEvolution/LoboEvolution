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
package org.loboevolution.html.dom.svg;

public interface SVGFEMorphologyElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Morphology Operators
	static final short SVG_MORPHOLOGY_OPERATOR_UNKNOWN = 0;
	static final short SVG_MORPHOLOGY_OPERATOR_ERODE = 1;
	static final short SVG_MORPHOLOGY_OPERATOR_DILATE = 2;

	SVGAnimatedString getIn1();

	SVGAnimatedEnumeration getOperator();

	SVGAnimatedNumber getRadiusX();

	SVGAnimatedNumber getRadiusY();
}
