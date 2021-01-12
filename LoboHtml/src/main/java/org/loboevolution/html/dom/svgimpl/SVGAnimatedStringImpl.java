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

import org.loboevolution.html.dom.svg.SVGAnimatedString;
import org.w3c.dom.DOMException;

/**
 * <p>SVGAnimatedStringImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGAnimatedStringImpl implements SVGAnimatedString {

	private String baseVal;

	/**
	 * <p>Constructor for SVGAnimatedStringImpl.</p>
	 *
	 * @param baseVal a {@link java.lang.String} object.
	 */
	public SVGAnimatedStringImpl(String baseVal) {
		this.baseVal = baseVal;
	}

	/** {@inheritDoc} */
	@Override
	public String getBaseVal() {
		return baseVal;
	}

	/** {@inheritDoc} */
	@Override
	public void setBaseVal(String baseVal) throws DOMException {
		this.baseVal = baseVal;
	}

	/** {@inheritDoc} */
	@Override
	public String getAnimVal() {
		return baseVal;
	}
}
