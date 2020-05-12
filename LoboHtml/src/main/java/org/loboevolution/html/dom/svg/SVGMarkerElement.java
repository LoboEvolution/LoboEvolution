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
 * <p>SVGMarkerElement interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGMarkerElement
		extends SVGElement, SVGLangSpace, SVGExternalResourcesRequired, SVGStylable, SVGFitToViewBox {
	// Marker Unit Types
	/** Constant SVG_MARKERUNITS_UNKNOWN=0 */
	static final short SVG_MARKERUNITS_UNKNOWN = 0;
	/** Constant SVG_MARKERUNITS_USERSPACEONUSE=1 */
	static final short SVG_MARKERUNITS_USERSPACEONUSE = 1;
	/** Constant SVG_MARKERUNITS_STROKEWIDTH=2 */
	static final short SVG_MARKERUNITS_STROKEWIDTH = 2;
	// Marker Orientation Types
	/** Constant SVG_MARKER_ORIENT_UNKNOWN=0 */
	static final short SVG_MARKER_ORIENT_UNKNOWN = 0;
	/** Constant SVG_MARKER_ORIENT_AUTO=1 */
	static final short SVG_MARKER_ORIENT_AUTO = 1;
	/** Constant SVG_MARKER_ORIENT_ANGLE=2 */
	static final short SVG_MARKER_ORIENT_ANGLE = 2;

	/**
	 * <p>getRefX.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getRefX();

	/**
	 * <p>getRefY.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getRefY();

	/**
	 * <p>getMarkerUnits.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getMarkerUnits();

	/**
	 * <p>getMarkerWidth.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getMarkerWidth();

	/**
	 * <p>getMarkerHeight.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getMarkerHeight();

	/**
	 * <p>getOrientType.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getOrientType();

	/**
	 * <p>getOrientAngle.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedAngle} object.
	 */
	SVGAnimatedAngle getOrientAngle();

	/**
	 * <p>setOrientToAuto.</p>
	 */
	void setOrientToAuto();

	/**
	 * <p>setOrientToAngle.</p>
	 *
	 * @param angle a {@link org.loboevolution.html.dom.svg.SVGAngle} object.
	 */
	void setOrientToAngle(SVGAngle angle);
}
