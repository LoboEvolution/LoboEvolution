/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.svgimpl;

import org.loboevolution.html.dom.svg.SVGAnimatedNumber;
import org.loboevolution.html.dom.svg.SVGStopElement;

/**
 * <p>SVGStopElementImpl class.</p>
 */
public class SVGStopElementImpl extends SVGStylableImpl implements SVGStopElement {

	/**
	 * <p>Constructor for SVGStopElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGStopElementImpl(final String name) {
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
