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

import org.w3c.dom.DOMException;

/**
 * <p>SVGPathSegArcAbs interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGPathSegArcAbs extends SVGPathSeg {

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
	 * <p>getR1.</p>
	 *
	 * @return a float.
	 */
	float getR1();

	/**
	 * <p>setR1.</p>
	 *
	 * @param r1 a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setR1(float r1) throws DOMException;

	/**
	 * <p>getR2.</p>
	 *
	 * @return a float.
	 */
	float getR2();

	/**
	 * <p>setR2.</p>
	 *
	 * @param r2 a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setR2(float r2) throws DOMException;

	/**
	 * <p>getAngle.</p>
	 *
	 * @return a float.
	 */
	float getAngle();

	/**
	 * <p>setAngle.</p>
	 *
	 * @param angle a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setAngle(float angle) throws DOMException;

	/**
	 * <p>getLargeArcFlag.</p>
	 *
	 * @return a boolean.
	 */
	boolean getLargeArcFlag();

	/**
	 * <p>setLargeArcFlag.</p>
	 *
	 * @param largeArcFlag a boolean.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setLargeArcFlag(boolean largeArcFlag) throws DOMException;

	/**
	 * <p>getSweepFlag.</p>
	 *
	 * @return a boolean.
	 */
	boolean getSweepFlag();

	/**
	 * <p>setSweepFlag.</p>
	 *
	 * @param sweepFlag a boolean.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setSweepFlag(boolean sweepFlag) throws DOMException;
}
