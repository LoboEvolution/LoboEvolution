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
import org.loboevolution.html.dom.svg.SVGRadialGradientElement;

/**
 * <p>SVGRadialGradientElementImpl class.</p>
 *
 *
 *
 */
public class SVGRadialGradientElementImpl extends SVGGradientElementImpl implements SVGRadialGradientElement {

	/**
	 * <p>Constructor for SVGRadialGradientElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGRadialGradientElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getCx() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("cx")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getCy() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("cy")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getR() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("r")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getFx() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("fx")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getFy() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("fy")));
	}
}
