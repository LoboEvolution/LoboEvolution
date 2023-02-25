/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
