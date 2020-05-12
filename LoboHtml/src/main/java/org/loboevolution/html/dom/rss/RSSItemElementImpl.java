package org.loboevolution.html.dom.rss;

import java.awt.Graphics2D;

import org.loboevolution.common.Nodes;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>RSSItemElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class RSSItemElementImpl extends HTMLElementImpl {
	
	/**
	 * <p>Constructor for RSSItemElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public RSSItemElementImpl(String name) {
		super(name);
	}
	
	/**
	 * <p>draw.</p>
	 *
	 * @param graphics a {@link java.awt.Graphics2D} object.
	 * @param y a int.
	 */
	public void draw(Graphics2D graphics, int y) {
		if (hasChildNodes()) {
			NodeList children = getChildNodes();
			for (Node child : Nodes.iterable(children)) {
				if (child instanceof RSSDrawable) {
					RSSDrawable channel = (RSSDrawable) child;
					channel.draw(graphics, y);
					y = y + 20;
				}
			}
		}
	}
}
