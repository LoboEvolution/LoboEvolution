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

import org.loboevolution.html.dom.svg.SVGAnimatedNumber;
import org.loboevolution.html.dom.svg.SVGStopElement;

/**
 * <p>SVGStopElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGStopElementImpl extends SVGGraphic implements SVGStopElement {

	/**
	 * <p>Constructor for SVGStopElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGStopElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedNumber getOffset() {
		final String value = this.getAttribute("offset");
		float number = 0;
		int index = value.indexOf('%');
		if (index != -1) {
			number = Float.parseFloat(value.substring(0, index)) / 100;
		} else {
			number = Float.parseFloat(value);
		}

		if (number < 0) {
			number = 0;
		} else if (number > 1) {
			number = 1;
		}
		return new SVGAnimatedNumberImpl(new SVGNumberImpl(number));
	}
}
