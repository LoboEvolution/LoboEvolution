package org.loboevolution.html.gui;

import org.loboevolution.html.dom.domimpl.NodeImpl;

class DocumentNotification {
	/** Constant GENERIC=3 */
	public static final int GENERIC = 3;
	/** Constant LOOK=0 */
	public static final int LOOK = 0;
	/** Constant POSITION=1 */
	public static final int POSITION = 1;
	/** Constant SIZE=2 */
	public static final int SIZE = 2;

	public final NodeImpl node;
	public final int type;

	/**
	 * <p>Constructor for DocumentNotification.</p>
	 *
	 * @param type a int.
	 * @param node a {@link org.loboevolution.html.dom.domimpl.NodeImpl} object.
	 */
	public DocumentNotification(int type, NodeImpl node) {
		this.type = type;
		this.node = node;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "DocumentNotification[type=" + this.type + ",node=" + this.node + "]";
	}
}
