/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.dom.svg;

/**
 * <p>SVGTextPathElement interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGTextPathElement extends SVGTextContentElement, SVGURIReference {
	// textPath Method Types
	/** Constant TEXTPATH_METHODTYPE_UNKNOWN=0 */
    short TEXTPATH_METHODTYPE_UNKNOWN = 0;
	/** Constant TEXTPATH_METHODTYPE_ALIGN=1 */
    short TEXTPATH_METHODTYPE_ALIGN = 1;
	/** Constant TEXTPATH_METHODTYPE_STRETCH=2 */
    short TEXTPATH_METHODTYPE_STRETCH = 2;
	// textPath Spacing Types
	/** Constant TEXTPATH_SPACINGTYPE_UNKNOWN=0 */
    short TEXTPATH_SPACINGTYPE_UNKNOWN = 0;
	/** Constant TEXTPATH_SPACINGTYPE_AUTO=1 */
    short TEXTPATH_SPACINGTYPE_AUTO = 1;
	/** Constant TEXTPATH_SPACINGTYPE_EXACT=2 */
    short TEXTPATH_SPACINGTYPE_EXACT = 2;

	/**
	 * <p>getStartOffset.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getStartOffset();

	/**
	 * <p>getMethod.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getMethod();

	/**
	 * <p>getSpacing.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedEnumeration} object.
	 */
	SVGAnimatedEnumeration getSpacing();
}
