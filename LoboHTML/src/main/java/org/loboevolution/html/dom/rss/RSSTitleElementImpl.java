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

package org.loboevolution.html.dom.rss;

import org.loboevolution.html.CSSValues;
import org.loboevolution.html.style.FontValues;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;
import org.loboevolution.laf.LAFType;

import java.awt.*;

/**
 * <p>RSSTitleElementImpl class.</p>
 */
public class RSSTitleElementImpl extends RSSElement implements RSSDrawable {

	/**
	 * <p>Constructor for RSSTitleElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public RSSTitleElementImpl(final String name) {
		super(name);
	}
	
	/** {@inheritDoc} */
	@Override
	public void draw(final Graphics2D graphics, final int y) {
		final String text = getText();
		final FontKey key = FontValues.getDefaultFontKey(getHtmlRendererConfig());
		key.setFontStyle(LAFType.ITALIC.getValue());
		key.setFontVariant(CSSValues.SMALL_CAPS.getValue());
		key.setFontWeight(LAFType.BOLD.getValue());
		final Font font = FontFactory.getInstance().getFont(key);
		graphics.setFont(font);
		graphics.drawString(text, 10, y);
	}
}
