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

import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.svg.SVGFontElement;

import java.awt.geom.Rectangle2D;

/**
 * <p>SVGFontElementImpl class.</p>
 */
public class SVGFontElementImpl extends SVGStylableImpl implements SVGFontElement {

	/**
	 * <p>Constructor for SVGFontElementImpl.</p>
	 *
	 * @param element a {@link HTMLElement} object.
	 */
	public SVGFontElementImpl(final HTMLElement element) {
		super(element);
	}

	public Rectangle2D getBounds(final String text, final float x, final float y, final float fontSize) {
		final float fontAscent = getFontAscent();
		float fontDescent = getFontDescent();
		if (fontDescent < 0) {
			fontDescent = -fontDescent;
		}
		final float horizAdvX = getHorizAdvX();
		final float scaleFactor = fontSize / getFontUnitsPerEm();

		final Rectangle2D bounds = new Rectangle2D.Float();
		final float bottom = y + fontDescent * scaleFactor;
		final float width = horizAdvX * scaleFactor;
		final float top = y - fontAscent * scaleFactor;
		final float left = x;
		bounds.setRect(left, top, width, bottom - top);
		return bounds;
	}
}
