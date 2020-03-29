package org.loboevolution.html.dom.rss;

import java.awt.Graphics2D;

import org.loboevolution.common.Nodes;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RSSElementImpl extends RSSElement {

	public RSSElementImpl(String name) {
		super(name);
	}

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