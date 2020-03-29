package org.loboevolution.html.dom.rss;

import org.loboevolution.common.Nodes;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RSSElement extends HTMLElementImpl {

	public RSSElement(String name) {
		super(name);

	}

	public String getText() {
		String text = "";
		if (hasChildNodes()) {
			NodeList children = getChildNodes();
			for (Node child : Nodes.iterable(children)) {
				if (child.getNodeType() == Node.TEXT_NODE) {
					String nodeValue = child.getNodeValue();
					String childText = "";
					nodeValue = nodeValue.replace('\n', ' ');
					nodeValue = nodeValue.replace('\r', ' ');
					nodeValue = nodeValue.replace('\t', ' ');
					childText = nodeValue;
					text += childText + " ";
				}
			}
		}
		if (text.length() > 0) {
			return text.substring(0, text.length() - 1);
		} else {
			return text;
		}
	}

}
