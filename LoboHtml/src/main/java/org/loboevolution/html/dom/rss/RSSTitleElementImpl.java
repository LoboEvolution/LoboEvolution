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

package org.loboevolution.html.dom.rss;

import java.awt.Font;
import java.awt.Graphics2D;

import org.loboevolution.html.CSSValues;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;
import org.loboevolution.laf.LAFType;

/**
 * <p>RSSTitleElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class RSSTitleElementImpl extends RSSElement implements RSSDrawable {

	/**
	 * <p>Constructor for RSSTitleElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public RSSTitleElementImpl(String name) {
		super(name);
	}
	
	/** {@inheritDoc} */
	@Override
	public void draw(Graphics2D graphics, int y) {
		String text = getText();
		FontKey key = new FontKey();
		key.setFontFamily(Font.SANS_SERIF);
		key.setFontStyle(LAFType.ITALIC.getValue());
		key.setFontVariant(CSSValues.SMALL_CAPS.getValue());
		key.setFontWeight(LAFType.BOLD.getValue());
		Font font = FontFactory.getInstance().getFont(key);
		graphics.setFont(font);
		graphics.drawString(text, 10, y);
	}
}
