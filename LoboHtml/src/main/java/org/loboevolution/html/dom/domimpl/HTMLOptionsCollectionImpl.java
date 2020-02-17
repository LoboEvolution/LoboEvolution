package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLOptionsCollection;
import org.loboevolution.html.dom.filter.OptionFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class HTMLOptionsCollectionImpl implements HTMLOptionsCollection {

	private NodeImpl rootNode;

	private NodeListImpl rootList = null;

	public HTMLOptionsCollectionImpl(NodeImpl rootNode) {
		this.rootNode = rootNode;
		rootList = (NodeListImpl) rootNode.getNodeList(new OptionFilter());
	}

	@Override
	public int getLength() {
		if (rootList == null) {
			return this.rootNode.getChildCount();
		} else {
			return this.rootList.getLength();
		}
	}

	@Override
	public Node item(int index) {
		if (rootList == null) {
			return this.rootNode.getChildAtIndex(index);
		} else {
			return this.rootList.get(index);
		}
	}

	@Override
	public Node namedItem(String name) {
		final Document doc = this.rootNode.getOwnerDocument();
		if (doc == null) {
			return null;
		}
		final Node node = doc.getElementById(name);
		if (node != null && node.getParentNode() == this.rootNode) {
			return node;
		}
		return null;
	}
	
	public int indexOf(Node node) {
		if (rootList == null) {
			return this.rootNode.getChildIndex(node);
		} else {
			return this.rootList.indexOf(node);
		}
	}
	
	protected NodeListImpl getRootList() {
		return rootList;
	}
}