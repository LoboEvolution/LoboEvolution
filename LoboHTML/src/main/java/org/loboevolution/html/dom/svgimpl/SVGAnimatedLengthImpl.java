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

import org.loboevolution.html.dom.svg.SVGAnimatedLength;
import org.loboevolution.html.dom.svg.SVGLength;

/**
 * <p>SVGAnimatedLengthImpl class.</p>
 *
 *
 *
 */
public class SVGAnimatedLengthImpl implements SVGAnimatedLength {

	private final SVGLength baseValue;
	private final SVGLength animValue;

	/**
	 * <p>Constructor for SVGAnimatedLengthImpl.</p>
	 *
	 * @param baseValue a {@link org.loboevolution.html.dom.svg.SVGLength} object.
	 */
	public SVGAnimatedLengthImpl(SVGLength baseValue) {
		this.baseValue = baseValue;
		this.animValue = baseValue;
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength getBaseVal() {
		return this.baseValue;
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength getAnimVal() {
		return this.animValue;
	}

}
