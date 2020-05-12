package org.loboevolution.html.dom.rss;

import java.awt.Graphics2D;

import org.loboevolution.common.Nodes;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>RSSChanelElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class RSSChanelElementImpl extends HTMLElementImpl {
	
	/**
	 * <p>Constructor for RSSChanelElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public RSSChanelElementImpl(String name) {
		super(name);
	}

	/**
	 * <p>draw.</p>
	 *
	 * @param graphics a {@link java.awt.Graphics2D} object.
	 */
	public void draw(Graphics2D graphics) {
		if (hasChildNodes()) {
			int y = 10;
			NodeList children = getChildNodes();
			for (Node child : Nodes.iterable(children)) {
				if (child instanceof RSSDrawable) {
					RSSDrawable channel = (RSSDrawable) child;
					channel.draw(graphics, y);
					y = y + 20; 
				}
				
				if (child instanceof RSSItemElementImpl) {
					RSSItemElementImpl channel = (RSSItemElementImpl) child;
					channel.draw(graphics, y);
					y = y + 60;
				}
			}
		}
	}
}
