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

import org.w3c.dom.DOMException;

public interface SVGTextContentElement
		extends SVGElement, SVGTests, SVGLangSpace, SVGExternalResourcesRequired, SVGStylable {
	// lengthAdjust Types
	static final short LENGTHADJUST_UNKNOWN = 0;
	static final short LENGTHADJUST_SPACING = 1;
	static final short LENGTHADJUST_SPACINGANDGLYPHS = 2;

	SVGAnimatedLength getTextLength();

	SVGAnimatedEnumeration getLengthAdjust();

	int getNumberOfChars();

	float getComputedTextLength();

	float getSubStringLength(int charnum, int nchars) throws DOMException;

	SVGPoint getStartPositionOfChar(int charnum) throws DOMException;

	SVGPoint getEndPositionOfChar(int charnum) throws DOMException;

	SVGRect getExtentOfChar(int charnum) throws DOMException;

	float getRotationOfChar(int charnum) throws DOMException;

	int getCharNumAtPosition(SVGPoint point);

	void selectSubString(int charnum, int nchars) throws DOMException;
}
