/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.svg.dom;

import org.loboevolution.common.Strings;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.svg.SVGAnimatedTransformList;
import org.loboevolution.svg.SVGTransformList;
import org.loboevolution.svg.SVGTransformable;

/**
 * <p>SVGTransformableImpl class.</p>
 */
public abstract class SVGTransformableImpl extends SVGLocatableImpl implements SVGTransformable {

	/**
	 * <p>Constructor for SVGTransformableImpl.</p>
	 *
	 * @param element a {@link HTMLElement} object.
	 */
	public SVGTransformableImpl(HTMLElement element) {
		super(element);
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedTransformList getTransform() {
		CSSStyleDeclaration style = getStyle();
		String transformString = Strings.isNotBlank(style.getTransform()) ? style.getTransform() : this.getAttribute("transform");
		SVGTransformList createTransformList = SVGTransformListImpl.createTransformList(transformString);
		return new SVGAnimatedTransformListImpl((SVGTransformListImpl) createTransformList);
	}
}
