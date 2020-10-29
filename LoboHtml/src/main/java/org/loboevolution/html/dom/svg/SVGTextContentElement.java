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

/**
 * <p>SVGTextContentElement interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGTextContentElement
		extends SVGElement, SVGTests, SVGLangSpace, SVGExternalResourcesRequired, SVGStylable {
	// lengthAdjust Types
	/** Constant LENGTHADJUST_UNKNOWN=0 */
    short LENGTHADJUST_UNKNOWN = 0;
	/** Constant LENGTHADJUST_SPACING=1 */
    short LENGTHADJUST_SPACING = 1;
	/** Constant LENGTHADJUST_SPACINGANDGLYPHS=2 */
    short LENGTHADJUST_SPACINGANDGLYPHS = 2;

	/**
	 * <p>getTextLength.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getTextLength();

	/**
	 * <p>getLengthAdjust.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getLengthAdjust();

	/**
	 * <p>getNumberOfChars.</p>
	 *
	 * @return a int.
	 */
	int getNumberOfChars();

	/**
	 * <p>getComputedTextLength.</p>
	 *
	 * @return a float.
	 */
	float getComputedTextLength();

	/**
	 * <p>getSubStringLength.</p>
	 *
	 * @param charnum a int.
	 * @param nchars a int.
	 * @return a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	float getSubStringLength(int charnum, int nchars) throws DOMException;

	/**
	 * <p>getStartPositionOfChar.</p>
	 *
	 * @param charnum a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	SVGPoint getStartPositionOfChar(int charnum) throws DOMException;

	/**
	 * <p>getEndPositionOfChar.</p>
	 *
	 * @param charnum a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	SVGPoint getEndPositionOfChar(int charnum) throws DOMException;

	/**
	 * <p>getExtentOfChar.</p>
	 *
	 * @param charnum a int.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGRect} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	SVGRect getExtentOfChar(int charnum) throws DOMException;

	/**
	 * <p>getRotationOfChar.</p>
	 *
	 * @param charnum a int.
	 * @return a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	float getRotationOfChar(int charnum) throws DOMException;

	/**
	 * <p>getCharNumAtPosition.</p>
	 *
	 * @param point a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @return a int.
	 */
	int getCharNumAtPosition(SVGPoint point);

	/**
	 * <p>selectSubString.</p>
	 *
	 * @param charnum a int.
	 * @param nchars a int.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void selectSubString(int charnum, int nchars) throws DOMException;
}
