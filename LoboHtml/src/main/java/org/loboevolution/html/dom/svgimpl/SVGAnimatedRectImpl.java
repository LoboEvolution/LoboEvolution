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

import org.loboevolution.html.dom.svg.SVGAnimatedRect;
import org.loboevolution.html.dom.svg.SVGRect;
import org.w3c.dom.DOMException;

/**
 * <p>SVGAnimatedRectImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGAnimatedRectImpl implements SVGAnimatedRect {

	private SVGRect baseVal;

	/**
	 * <p>Constructor for SVGAnimatedRectImpl.</p>
	 *
	 * @param baseVal a {@link org.loboevolution.html.dom.svg.SVGRect} object.
	 */
	public SVGAnimatedRectImpl(SVGRect baseVal) {
		this.baseVal = baseVal;
	}

	/** {@inheritDoc} */
	@Override
	public SVGRect getBaseVal() {
		return baseVal;
	}

	void setBaseVal(SVGRect baseVal) throws DOMException {
		this.baseVal = baseVal;
	}

	/** {@inheritDoc} */
	@Override
	public SVGRect getAnimVal() {
		return baseVal;
	}

}
