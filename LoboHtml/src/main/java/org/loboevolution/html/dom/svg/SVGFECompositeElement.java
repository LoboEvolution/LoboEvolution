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

public interface SVGFECompositeElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Composite Operators
	public static final short SVG_FECOMPOSITE_OPERATOR_UNKNOWN = 0;
	public static final short SVG_FECOMPOSITE_OPERATOR_OVER = 1;
	public static final short SVG_FECOMPOSITE_OPERATOR_IN = 2;
	public static final short SVG_FECOMPOSITE_OPERATOR_OUT = 3;
	public static final short SVG_FECOMPOSITE_OPERATOR_ATOP = 4;
	public static final short SVG_FECOMPOSITE_OPERATOR_XOR = 5;
	public static final short SVG_FECOMPOSITE_OPERATOR_ARITHMETIC = 6;

	public SVGAnimatedString getIn1();

	public SVGAnimatedString getIn2();

	public SVGAnimatedEnumeration getOperator();

	public SVGAnimatedNumber getK1();

	public SVGAnimatedNumber getK2();

	public SVGAnimatedNumber getK3();

	public SVGAnimatedNumber getK4();
}
