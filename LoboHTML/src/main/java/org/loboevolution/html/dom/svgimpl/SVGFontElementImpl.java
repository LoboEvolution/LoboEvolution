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

import org.loboevolution.html.dom.svg.SVGFontElement;

import java.awt.geom.Rectangle2D;

/**
 * <p>SVGFontElementImpl class.</p>
 */
public class SVGFontElementImpl extends SVGStylableImpl implements SVGFontElement {

	/**
	 * <p>Constructor for SVGFontElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGFontElementImpl(final String name) {
		super(name);
	}

	public Rectangle2D getBounds(String text, float x, float y, float fontSize) {
		float fontAscent = getFontAscent();
		float fontDescent = getFontDescent();
		if (fontDescent < 0) {
			fontDescent = -fontDescent;
		}
		float horizAdvX = getHorizAdvX();
		float scaleFactor = fontSize / getFontUnitsPerEm();

		Rectangle2D bounds = new Rectangle2D.Float();
		float bottom = y + fontDescent * scaleFactor;
		float width = horizAdvX * scaleFactor;
		float top = y - fontAscent * scaleFactor;
		float left = x;
		bounds.setRect(left, top, width, bottom - top);
		return bounds;
	}
}
