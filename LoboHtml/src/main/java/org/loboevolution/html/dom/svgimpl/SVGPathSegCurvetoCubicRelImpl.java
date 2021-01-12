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

package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegCurvetoCubicRel;

/**
 * <p>SVGPathSegCurvetoCubicRelImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGPathSegCurvetoCubicRelImpl implements SVGPathSegCurvetoCubicRel {

	private float x;

	private float y;

	private float x1;

	private float y1;

	private float x2;

	private float y2;

	/**
	 * <p>Constructor for SVGPathSegCurvetoCubicRelImpl.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param x1 a float.
	 * @param y1 a float.
	 * @param x2 a float.
	 * @param y2 a float.
	 */
	public SVGPathSegCurvetoCubicRelImpl(float x, float y, float x1, float y1, float x2, float y2) {
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	/** {@inheritDoc} */
	@Override
	public short getPathSegType() {
		return PATHSEG_CURVETO_CUBIC_REL;
	}

	/** {@inheritDoc} */
	@Override
	public String getPathSegTypeAsLetter() {
		return "c";
	}

	/** {@inheritDoc} */
	@Override
	public float getX() {
		return x;
	}

	/** {@inheritDoc} */
	@Override
	public void setX(float x) {
		this.x = x;
	}

	/** {@inheritDoc} */
	@Override
	public float getY() {
		return y;
	}

	/** {@inheritDoc} */
	@Override
	public void setY(float y) {
		this.y = y;
	}

	/** {@inheritDoc} */
	@Override
	public float getX1() {
		return x1;
	}

	/** {@inheritDoc} */
	@Override
	public void setX1(float x1) {
		this.x1 = x1;
	}

	/** {@inheritDoc} */
	@Override
	public float getY1() {
		return y1;
	}

	/** {@inheritDoc} */
	@Override
	public void setY1(float y1) {
		this.y1 = y1;
	}

	/** {@inheritDoc} */
	@Override
	public float getX2() {
		return x2;
	}

	/** {@inheritDoc} */
	@Override
	public void setX2(float x2) {
		this.x2 = x2;
	}

	/** {@inheritDoc} */
	@Override
	public float getY2() {
		return y2;
	}

	/** {@inheritDoc} */
	@Override
	public void setY2(float y2) {
		this.y2 = y2;
	}
}
