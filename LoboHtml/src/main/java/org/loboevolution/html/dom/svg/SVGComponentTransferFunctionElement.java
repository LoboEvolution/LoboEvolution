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

/**
 * <p>SVGComponentTransferFunctionElement interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGComponentTransferFunctionElement extends SVGElement {
	// Component Transfer Types
	/** Constant SVG_FECOMPONENTTRANSFER_TYPE_UNKNOWN=0 */
	static final short SVG_FECOMPONENTTRANSFER_TYPE_UNKNOWN = 0;
	/** Constant SVG_FECOMPONENTTRANSFER_TYPE_IDENTITY=1 */
	static final short SVG_FECOMPONENTTRANSFER_TYPE_IDENTITY = 1;
	/** Constant SVG_FECOMPONENTTRANSFER_TYPE_TABLE=2 */
	static final short SVG_FECOMPONENTTRANSFER_TYPE_TABLE = 2;
	/** Constant SVG_FECOMPONENTTRANSFER_TYPE_DISCRETE=3 */
	static final short SVG_FECOMPONENTTRANSFER_TYPE_DISCRETE = 3;
	/** Constant SVG_FECOMPONENTTRANSFER_TYPE_LINEAR=4 */
	static final short SVG_FECOMPONENTTRANSFER_TYPE_LINEAR = 4;
	/** Constant SVG_FECOMPONENTTRANSFER_TYPE_GAMMA=5 */
	static final short SVG_FECOMPONENTTRANSFER_TYPE_GAMMA = 5;

	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getType();

	/**
	 * <p>getTableValues.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumberList} object.
	 */
	SVGAnimatedNumberList getTableValues();

	/**
	 * <p>getSlope.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getSlope();

	/**
	 * <p>getIntercept.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getIntercept();

	/**
	 * <p>getAmplitude.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getAmplitude();

	/**
	 * <p>getExponent.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getExponent();

	/**
	 * <p>getOffset.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getOffset();
}
