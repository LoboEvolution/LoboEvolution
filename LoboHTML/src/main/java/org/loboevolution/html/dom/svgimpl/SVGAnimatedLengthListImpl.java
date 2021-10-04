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

import org.loboevolution.html.dom.svg.SVGAnimatedLengthList;
import org.loboevolution.html.dom.svg.SVGLengthList;

/**
 * <p>SVGAnimatedLengthListImpl class.</p>
 *
 *
 *
 */
public class SVGAnimatedLengthListImpl implements SVGAnimatedLengthList {

	private final SVGLengthList lenght;

	/**
	 * <p>Constructor for SVGAnimatedLengthListImpl.</p>
	 *
	 * @param lenght a {@link org.loboevolution.html.dom.svg.SVGLengthList} object.
	 */
	public SVGAnimatedLengthListImpl(SVGLengthList lenght) {
		this.lenght = lenght;
	}

	/** {@inheritDoc} */
	@Override
	public SVGLengthList getBaseVal() {
		return lenght;
	}

	/** {@inheritDoc} */
	@Override
	public SVGLengthList getAnimVal() {
		return lenght;
	}
}
