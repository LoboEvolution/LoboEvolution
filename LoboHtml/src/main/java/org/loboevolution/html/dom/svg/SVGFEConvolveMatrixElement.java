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
 * <p>SVGFEConvolveMatrixElement interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGFEConvolveMatrixElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Edge Mode Values
	/** Constant SVG_EDGEMODE_UNKNOWN=0 */
    short SVG_EDGEMODE_UNKNOWN = 0;
	/** Constant SVG_EDGEMODE_DUPLICATE=1 */
    short SVG_EDGEMODE_DUPLICATE = 1;
	/** Constant SVG_EDGEMODE_WRAP=2 */
    short SVG_EDGEMODE_WRAP = 2;
	/** Constant SVG_EDGEMODE_NONE=3 */
    short SVG_EDGEMODE_NONE = 3;

	/**
	 * <p>getOrderX.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedInteger} object.
	 */
	SVGAnimatedInteger getOrderX();

	/**
	 * <p>getOrderY.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedInteger} object.
	 */
	SVGAnimatedInteger getOrderY();

	/**
	 * <p>getKernelMatrix.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumberList} object.
	 */
	SVGAnimatedNumberList getKernelMatrix();

	/**
	 * <p>getDivisor.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getDivisor();

	/**
	 * <p>getBias.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getBias();

	/**
	 * <p>getTargetX.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedInteger} object.
	 */
	SVGAnimatedInteger getTargetX();

	/**
	 * <p>getTargetY.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedInteger} object.
	 */
	SVGAnimatedInteger getTargetY();

	/**
	 * <p>getEdgeMode.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getEdgeMode();

	/**
	 * <p>getKernelUnitLengthX.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getKernelUnitLengthX();

	/**
	 * <p>getKernelUnitLengthY.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getKernelUnitLengthY();

	/**
	 * <p>getPreserveAlpha.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedBoolean} object.
	 */
	SVGAnimatedBoolean getPreserveAlpha();
}
