package org.lobobrowser.html.gui;

import org.lobobrowser.html.domimpl.DOMNodeImpl;


/**
 * The Class DocumentNotification.
 */
class DocumentNotification {
	
	/** The Constant LOOK. */
	public static final int LOOK = 0;
	
	/** The Constant POSITION. */
	public static final int POSITION = 1;
	
	/** The Constant SIZE. */
	public static final int SIZE = 2;
	
	/** The Constant GENERIC. */
	public static final int GENERIC = 3;

	/** The type. */
	public final int type;
	
	/** The node. */
	public final DOMNodeImpl node;

	/**
	 * Instantiates a new document notification.
	 *
	 * @param type the type
	 * @param node the node
	 */
	public DocumentNotification(int type, DOMNodeImpl node) {
		this.type = type;
		this.node = node;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "DocumentNotification[type=" + this.type + ",node=" + this.node
				+ "]";
	}
}
