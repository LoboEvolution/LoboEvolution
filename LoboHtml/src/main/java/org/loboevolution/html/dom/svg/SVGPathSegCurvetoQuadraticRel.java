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
 * <p>SVGPathSegCurvetoQuadraticRel interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGPathSegCurvetoQuadraticRel extends SVGPathSeg {

	/**
	 * <p>getX.</p>
	 *
	 * @return a float.
	 */
	float getX();

	/**
	 * <p>setX.</p>
	 *
	 * @param x a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setX(float x) throws DOMException;

	/**
	 * <p>getY.</p>
	 *
	 * @return a float.
	 */
	float getY();

	/**
	 * <p>setY.</p>
	 *
	 * @param y a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setY(float y) throws DOMException;

	/**
	 * <p>getX1.</p>
	 *
	 * @return a float.
	 */
	float getX1();

	/**
	 * <p>setX1.</p>
	 *
	 * @param x1 a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setX1(float x1) throws DOMException;

	/**
	 * <p>getY1.</p>
	 *
	 * @return a float.
	 */
	float getY1();

	/**
	 * <p>setY1.</p>
	 *
	 * @param y1 a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setY1(float y1) throws DOMException;
}
