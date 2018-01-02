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
package org.loboevolution.w3c.svg;

public interface SVGFilterElement
		extends SVGElement, SVGURIReference, SVGLangSpace, SVGExternalResourcesRequired, SVGStylable, SVGUnitTypes {
	public SVGAnimatedEnumeration getFilterUnits();

	public SVGAnimatedEnumeration getPrimitiveUnits();

	public SVGAnimatedLength getX();

	public SVGAnimatedLength getY();

	public SVGAnimatedLength getWidth();

	public SVGAnimatedLength getHeight();

	public SVGAnimatedInteger getFilterResX();

	public SVGAnimatedInteger getFilterResY();

	public void setFilterRes(int filterResX, int filterResY);
}
