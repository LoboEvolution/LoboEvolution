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

public interface SVGFECompositeElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Composite Operators
	static final short SVG_FECOMPOSITE_OPERATOR_UNKNOWN = 0;
	static final short SVG_FECOMPOSITE_OPERATOR_OVER = 1;
	static final short SVG_FECOMPOSITE_OPERATOR_IN = 2;
	static final short SVG_FECOMPOSITE_OPERATOR_OUT = 3;
	static final short SVG_FECOMPOSITE_OPERATOR_ATOP = 4;
	static final short SVG_FECOMPOSITE_OPERATOR_XOR = 5;
	static final short SVG_FECOMPOSITE_OPERATOR_ARITHMETIC = 6;

	SVGAnimatedString getIn1();

	SVGAnimatedString getIn2();

	SVGAnimatedEnumeration getOperator();

	SVGAnimatedNumber getK1();

	SVGAnimatedNumber getK2();

	SVGAnimatedNumber getK3();

	SVGAnimatedNumber getK4();
}
