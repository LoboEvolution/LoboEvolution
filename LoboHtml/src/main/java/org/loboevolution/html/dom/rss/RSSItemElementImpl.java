package org.loboevolution.html.dom.rss;

import java.awt.Graphics2D;

import org.loboevolution.common.Nodes;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RSSItemElementImpl extends HTMLElementImpl {
	
	public RSSItemElementImpl(String name) {
		super(name);
	}
	
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
