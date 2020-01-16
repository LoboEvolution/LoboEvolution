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

public interface SVGMarkerElement
		extends SVGElement, SVGLangSpace, SVGExternalResourcesRequired, SVGStylable, SVGFitToViewBox {
	// Marker Unit Types
	static final short SVG_MARKERUNITS_UNKNOWN = 0;
	static final short SVG_MARKERUNITS_USERSPACEONUSE = 1;
	static final short SVG_MARKERUNITS_STROKEWIDTH = 2;
	// Marker Orientation Types
	static final short SVG_MARKER_ORIENT_UNKNOWN = 0;
	static final short SVG_MARKER_ORIENT_AUTO = 1;
	static final short SVG_MARKER_ORIENT_ANGLE = 2;

	SVGAnimatedLength getRefX();

	SVGAnimatedLength getRefY();

	SVGAnimatedEnumeration getMarkerUnits();

	SVGAnimatedLength getMarkerWidth();

	SVGAnimatedLength getMarkerHeight();

	SVGAnimatedEnumeration getOrientType();

	SVGAnimatedAngle getOrientAngle();

	void setOrientToAuto();

	void setOrientToAngle(SVGAngle angle);
}
