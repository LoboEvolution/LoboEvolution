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
 * <p>SVGFETurbulenceElement interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGFETurbulenceElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Turbulence Types
	/** Constant SVG_TURBULENCE_TYPE_UNKNOWN=0 */
    short SVG_TURBULENCE_TYPE_UNKNOWN = 0;
	/** Constant SVG_TURBULENCE_TYPE_FRACTALNOISE=1 */
    short SVG_TURBULENCE_TYPE_FRACTALNOISE = 1;
	/** Constant SVG_TURBULENCE_TYPE_TURBULENCE=2 */
    short SVG_TURBULENCE_TYPE_TURBULENCE = 2;
	// Stitch Options
	/** Constant SVG_STITCHTYPE_UNKNOWN=0 */
    short SVG_STITCHTYPE_UNKNOWN = 0;
	/** Constant SVG_STITCHTYPE_STITCH=1 */
    short SVG_STITCHTYPE_STITCH = 1;
	/** Constant SVG_STITCHTYPE_NOSTITCH=2 */
    short SVG_STITCHTYPE_NOSTITCH = 2;

	/**
	 * <p>getBaseFrequencyX.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getBaseFrequencyX();

	/**
	 * <p>getBaseFrequencyY.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getBaseFrequencyY();

	/**
	 * <p>getNumOctaves.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedInteger} object.
	 */
	SVGAnimatedInteger getNumOctaves();

	/**
	 * <p>getSeed.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedNumber} object.
	 */
	SVGAnimatedNumber getSeed();

	/**
	 * <p>getStitchTiles.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getStitchTiles();

	/**
	 * <p>getType.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getType();
}
