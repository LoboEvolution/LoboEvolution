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

import org.w3c.dom.DOMException;

public interface SVGTextContentElement
		extends SVGElement, SVGTests, SVGLangSpace, SVGExternalResourcesRequired, SVGStylable {
	// lengthAdjust Types
	public static final short LENGTHADJUST_UNKNOWN = 0;
	public static final short LENGTHADJUST_SPACING = 1;
	public static final short LENGTHADJUST_SPACINGANDGLYPHS = 2;

	public SVGAnimatedLength getTextLength();

	public SVGAnimatedEnumeration getLengthAdjust();

	public int getNumberOfChars();

	public float getComputedTextLength();

	public float getSubStringLength(int charnum, int nchars) throws DOMException;

	public SVGPoint getStartPositionOfChar(int charnum) throws DOMException;

	public SVGPoint getEndPositionOfChar(int charnum) throws DOMException;

	public SVGRect getExtentOfChar(int charnum) throws DOMException;

	public float getRotationOfChar(int charnum) throws DOMException;

	public int getCharNumAtPosition(SVGPoint point);

	public void selectSubString(int charnum, int nchars) throws DOMException;
}
