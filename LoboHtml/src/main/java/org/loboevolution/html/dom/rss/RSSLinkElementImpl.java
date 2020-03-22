package org.loboevolution.html.dom.rss;

import java.awt.Graphics2D;

public class RSSLinkElementImpl extends RSSElement implements RSSDrawable {

	public RSSLinkElementImpl(String name) {
		super(name);
	}
	
	@Override
	public void draw(Graphics2D graphics, int y) {
		String text = getText();
		graphics.drawString(text, 10, y);
	}
}