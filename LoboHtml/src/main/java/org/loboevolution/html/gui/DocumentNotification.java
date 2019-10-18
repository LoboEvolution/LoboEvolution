package org.loboevolution.html.gui;

import org.loboevolution.html.dom.domimpl.NodeImpl;

class DocumentNotification {
	public static final int GENERIC = 3;
	public static final int LOOK = 0;
	public static final int POSITION = 1;
	public static final int SIZE = 2;

	public final NodeImpl node;
	public final int type;

	public DocumentNotification(int type, NodeImpl node) {
		this.type = type;
		this.node = node;
	}

	@Override
	public String toString() {
		return "DocumentNotification[type=" + this.type + ",node=" + this.node + "]";
	}
}
