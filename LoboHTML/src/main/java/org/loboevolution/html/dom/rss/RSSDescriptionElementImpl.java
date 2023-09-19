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

package org.loboevolution.html.dom.rss;

import org.loboevolution.html.CSSValues;
import org.loboevolution.html.style.FontValues;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;

import java.awt.*;

/**
 * <p>RSSDescriptionElementImpl class.</p>
 */
public class RSSDescriptionElementImpl extends RSSElement implements RSSDrawable {

	/**
	 * <p>Constructor for RSSDescriptionElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public RSSDescriptionElementImpl(final String name) {
		super(name);
	}
	
	/** {@inheritDoc} */
	@Override
	public void draw(Graphics2D graphics, int y) {
		String text = getText();
		FontKey key = FontValues.getDefaultFontKey(getHtmlRendererConfig());
		key.setFontVariant(CSSValues.SMALL_CAPS.getValue());
		Font font = FontFactory.getInstance().getFont(key);
		graphics.setFont(font);
		graphics.drawString(text, 10, y);
	
		String firstPart = text;
		
		while (graphics.getFontMetrics().stringWidth(firstPart) > 600) {
		    firstPart = firstPart.substring(0, firstPart.length() - 1);
		}
		String secondPart = text.substring(firstPart.length());
		
		graphics.drawString(firstPart, 10, y);
		graphics.drawString(secondPart, 10, y+20);
	
	}
}
