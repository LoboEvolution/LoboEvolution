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

import org.loboevolution.html.dom.svg.SVGPathSegCurvetoQuadraticSmoothRel;

/**
 * <p>SVGPathSegCurvetoQuadraticSmoothRelImpl class.</p>
 *
 *
 *
 */
public class SVGPathSegCurvetoQuadraticSmoothRelImpl implements SVGPathSegCurvetoQuadraticSmoothRel {
	
	private float x;

	private float y;

	/**
	 * <p>Constructor for SVGPathSegCurvetoQuadraticSmoothRelImpl.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 */
	public SVGPathSegCurvetoQuadraticSmoothRelImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/** {@inheritDoc} */
	@Override
	public short getPathSegType() {
		return PATHSEG_CURVETO_QUADRATIC_SMOOTH_REL;
	}

	/** {@inheritDoc} */
	@Override
	public String getPathSegTypeAsLetter() {
		return "T";
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

}
