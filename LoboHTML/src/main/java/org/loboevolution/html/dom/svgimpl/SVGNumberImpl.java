/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

import org.loboevolution.html.dom.svg.SVGNumber;

/**
 * <p>SVGNumberImpl class.</p>
 */
public class SVGNumberImpl implements SVGNumber {

	private float value;

	/**
	 * <p>Constructor for SVGNumberImpl.</p>
	 */
	public SVGNumberImpl() {
		this.value= 0;
	}

	/**
	 * <p>Constructor for SVGNumberImpl.</p>
	 *
	 * @param value a {@link java.lang.Float} object.
	 */
	public SVGNumberImpl(float value) {
		this.value = value;
	}

	/**
	 * <p>Constructor for SVGNumberImpl.</p>
	 *
	 * @param strValue a {@link java.lang.String} object.
	 */
	public SVGNumberImpl(String strValue) {
		this.value = Float.parseFloat(strValue);
	}

	/** {@inheritDoc} */
	@Override
	public float getValue() {
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(float value) {
		this.value = value;
	}
}
