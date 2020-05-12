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
