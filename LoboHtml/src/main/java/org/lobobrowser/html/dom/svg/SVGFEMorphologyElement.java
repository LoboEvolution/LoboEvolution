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
package org.lobobrowser.html.dom.svg;

public interface SVGFEMorphologyElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Morphology Operators
	public static final short SVG_MORPHOLOGY_OPERATOR_UNKNOWN = 0;
	public static final short SVG_MORPHOLOGY_OPERATOR_ERODE = 1;
	public static final short SVG_MORPHOLOGY_OPERATOR_DILATE = 2;

	public SVGAnimatedString getIn1();

	public SVGAnimatedEnumeration getOperator();

	public SVGAnimatedNumber getRadiusX();

	public SVGAnimatedNumber getRadiusY();
}
