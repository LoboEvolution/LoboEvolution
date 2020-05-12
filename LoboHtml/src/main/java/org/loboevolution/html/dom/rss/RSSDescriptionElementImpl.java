package org.loboevolution.html.dom.rss;

import java.awt.Font;
import java.awt.Graphics2D;

import org.loboevolution.html.CSSValues;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;

/**
 * <p>RSSDescriptionElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
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
		String secondPart = text.substring(firstPart.length(), text.length());
		
		graphics.drawString(firstPart, 10, y);
		graphics.drawString(secondPart, 10, y+20);
	
	}
}
