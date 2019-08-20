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
package org.lobobrowser.html.dom.svg;

public interface SVGFEConvolveMatrixElement extends SVGElement, SVGFilterPrimitiveStandardAttributes {
	// Edge Mode Values
	public static final short SVG_EDGEMODE_UNKNOWN = 0;
	public static final short SVG_EDGEMODE_DUPLICATE = 1;
	public static final short SVG_EDGEMODE_WRAP = 2;
	public static final short SVG_EDGEMODE_NONE = 3;

	public SVGAnimatedInteger getOrderX();

	public SVGAnimatedInteger getOrderY();

	public SVGAnimatedNumberList getKernelMatrix();

	public SVGAnimatedNumber getDivisor();

	public SVGAnimatedNumber getBias();

	public SVGAnimatedInteger getTargetX();

	public SVGAnimatedInteger getTargetY();

	public SVGAnimatedEnumeration getEdgeMode();

	public SVGAnimatedNumber getKernelUnitLengthX();

	public SVGAnimatedNumber getKernelUnitLengthY();

	public SVGAnimatedBoolean getPreserveAlpha();
}
