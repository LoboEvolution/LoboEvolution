/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGPathSegCurvetoQuadraticRel;

/**
 * <p>SVGPathSegCurvetoQuadraticRelImpl class.</p>
 *
 *
 *
 */
public class SVGPathSegCurvetoQuadraticRelImpl implements SVGPathSegCurvetoQuadraticRel {

	private float x;

	private float y;

	private float x1;

	private float y1;

	/**
	 * <p>Constructor for SVGPathSegCurvetoQuadraticRelImpl.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param x1 a float.
	 * @param y1 a float.
	 */
	public SVGPathSegCurvetoQuadraticRelImpl(float x, float y, float x1, float y1) {
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
	}

	/** {@inheritDoc} */
	@Override
	public short getPathSegType() {
		return PATHSEG_CURVETO_QUADRATIC_REL;
	}

	/** {@inheritDoc} */
	@Override
	public String getPathSegTypeAsLetter() {
		return "q";
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
	public void setY(final float y) {
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
}
