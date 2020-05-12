
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
 * <p>SVGLocatable interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGLocatable {
	/**
	 * <p>getNearestViewportElement.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGElement} object.
	 */
	SVGElement getNearestViewportElement();

	/**
	 * <p>getFarthestViewportElement.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGElement} object.
	 */
	SVGElement getFarthestViewportElement();

	/**
	 * <p>getBBox.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGRect} object.
	 */
	SVGRect getBBox();

	/**
	 * <p>getCTM.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 */
	SVGMatrix getCTM();

	/**
	 * <p>getScreenCTM.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 */
	SVGMatrix getScreenCTM();

	/**
	 * <p>getTransformToElement.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.svg.SVGElement} object.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 * @throws org.loboevolution.html.dom.svg.SVGException if any.
	 */
	SVGMatrix getTransformToElement(SVGElement element) throws SVGException;
}
