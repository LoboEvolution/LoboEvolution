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

import org.loboevolution.html.dom.svg.SVGRect;


/**
 * <p>SVGRectImpl class.</p>
 *
 *
 *
 */
public class SVGRectImpl implements SVGRect {

	private float x;

	private float y;

	private float width;

	private float height;

	/**
	 * <p>Constructor for SVGRectImpl.</p>
	 *
	 * @param x a float.
	 * @param y a float.
	 * @param width a float.
	 * @param height a float.
	 */
	public SVGRectImpl(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
	public float getWidth() {
		return width;
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(float width) {
		this.width = width;
	}

	/** {@inheritDoc} */
	@Override
	public float getHeight() {
		return height;
	}

	/** {@inheritDoc} */
	@Override
	public void setHeight(float height) {
		this.height = height;
	}

}
