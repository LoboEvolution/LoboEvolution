
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
 * <p>SVGGradientElement interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGGradientElement
		extends SVGElement, SVGURIReference, SVGExternalResourcesRequired, SVGStylable, SVGUnitTypes {
	// Spread Method Types
	/** Constant SVG_SPREADMETHOD_UNKNOWN=0 */
	static final short SVG_SPREADMETHOD_UNKNOWN = 0;
	/** Constant SVG_SPREADMETHOD_PAD=1 */
	static final short SVG_SPREADMETHOD_PAD = 1;
	/** Constant SVG_SPREADMETHOD_REFLECT=2 */
	static final short SVG_SPREADMETHOD_REFLECT = 2;
	/** Constant SVG_SPREADMETHOD_REPEAT=3 */
	static final short SVG_SPREADMETHOD_REPEAT = 3;

	/**
	 * <p>getGradientUnits.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getGradientUnits();

	/**
	 * <p>getGradientTransform.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedTransformList} object.
	 */
	SVGAnimatedTransformList getGradientTransform();

	/**
	 * <p>getSpreadMethod.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getSpreadMethod();
}
