package org.loboevolution.html.dom.rss;

import java.awt.Graphics2D;

import org.loboevolution.common.Nodes;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>RSSElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class RSSElementImpl extends RSSElement {

	/**
	 * <p>Constructor for RSSElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public RSSElementImpl(String name) {
		super(name);
	}

	/**
	 * <p>draw.</p>
	 *
	 * @param graphics a {@link java.awt.Graphics2D} object.
	 */
	public void draw(Graphics2D graphics) {
		if (hasChildNodes()) {
			NodeList children = getChildNodes();
			for (Node child : Nodes.iterable(children)) {
				if (child instanceof RSSChanelElementImpl) {
					RSSChanelElementImpl channel = (RSSChanelElementImpl) child;
					channel.draw(graphics);
				}
			}
		}
	}
}
