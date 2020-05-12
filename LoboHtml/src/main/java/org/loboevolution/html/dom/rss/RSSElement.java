package org.loboevolution.html.dom.rss;

import org.loboevolution.common.Nodes;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>RSSElement class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class RSSElement extends HTMLElementImpl {

	/**
	 * <p>Constructor for RSSElement.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public RSSElement(String name) {
		super(name);

	}

	/**
	 * <p>getText.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
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
