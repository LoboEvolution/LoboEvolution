/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.svg;

/**
 * <p>SVGFECompositeElement interface.</p>
 *
 *
 *
 */
public interface SVGFECompositeElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Composite Operators
	/** Constant SVG_FECOMPOSITE_OPERATOR_UNKNOWN=0 */
    short SVG_FECOMPOSITE_OPERATOR_UNKNOWN = 0;
	/** Constant SVG_FECOMPOSITE_OPERATOR_OVER=1 */
    short SVG_FECOMPOSITE_OPERATOR_OVER = 1;
	/** Constant SVG_FECOMPOSITE_OPERATOR_IN=2 */
    short SVG_FECOMPOSITE_OPERATOR_IN = 2;
	/** Constant SVG_FECOMPOSITE_OPERATOR_OUT=3 */
    short SVG_FECOMPOSITE_OPERATOR_OUT = 3;
	/** Constant SVG_FECOMPOSITE_OPERATOR_ATOP=4 */
    short SVG_FECOMPOSITE_OPERATOR_ATOP = 4;
	/** Constant SVG_FECOMPOSITE_OPERATOR_XOR=5 */
    short SVG_FECOMPOSITE_OPERATOR_XOR = 5;
	/** Constant SVG_FECOMPOSITE_OPERATOR_ARITHMETIC=6 */
    short SVG_FECOMPOSITE_OPERATOR_ARITHMETIC = 6;

	/**
	 * <p>getIn1.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedString} object.
	 */
	SVGAnimatedString getIn1();

	/**
	 * <p>getIn2.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedString} object.
	 */
	SVGAnimatedString getIn2();

	/**
	 * <p>getOperator.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getOperator();

	/**
	 * <p>getK1.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getK1();

	/**
	 * <p>getK2.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getK2();

	/**
	 * <p>getK3.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getK3();

	/**
	 * <p>getK4.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getK4();
}
