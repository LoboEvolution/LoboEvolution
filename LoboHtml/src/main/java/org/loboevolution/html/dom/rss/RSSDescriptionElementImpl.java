/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.html.dom.rss;

import java.awt.Font;
import java.awt.Graphics2D;

import org.loboevolution.html.CSSValues;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;

/**
 * <p>RSSDescriptionElementImpl class.</p>
 *
 *
 *
 */
public class RSSDescriptionElementImpl extends RSSElement implements RSSDrawable {

	/**
	 * <p>Constructor for RSSDescriptionElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public RSSDescriptionElementImpl(String name) {
		super(name);
	}
	
	/** {@inheritDoc} */
	@Override
	public void draw(Graphics2D graphics, int y) {
		String text = getText();
		FontKey key = new FontKey();
		key.setFontFamily(Font.SANS_SERIF);
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
