package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.MutationNameEvent;
import org.w3c.dom.Node;


/**
 * The Class MutationNameEventImpl.
 */
public class MutationNameEventImpl extends MutationEventImpl implements
		MutationNameEvent {

	/** The prev namespace uri. */
	private String prevNamespaceURI;
	
	/** The prev node name. */
	private String prevNodeName;

	/**
	 * Instantiates a new mutation name event impl.
	 */
	public MutationNameEventImpl() {
	}

	/**
	 * Instantiates a new mutation name event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 */
	public MutationNameEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	/**
	 * Instantiates a new mutation name event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 * @param mouseEvent the mouse event
	 * @param leafX the leaf x
	 * @param leafY the leaf y
	 */
	public MutationNameEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	/**
	 * Instantiates a new mutation name event impl.
	 *
	 * @param type the type
	 * @param srcElement the src element
	 * @param keyEvent the key event
	 */
	public MutationNameEventImpl(String type, HTMLElement srcElement,
			KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.MutationNameEvent#initMutationNameEvent(java.lang.String, boolean, boolean, org.w3c.dom.Node, java.lang.String, java.lang.String)
	 */
	@Override
	public void initMutationNameEvent(String type, boolean canBubble,
			boolean cancelable, Node relatedNode, String prevNamespaceURI,
			String prevNodeName) {

		setType(type);
		setCanBubble(canBubble);
		setCancelable(cancelable);
		this.prevNamespaceURI = prevNamespaceURI;
		this.prevNodeName = prevNodeName;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.MutationNameEvent#getPrevNamespaceURI()
	 */
	@Override
	public String getPrevNamespaceURI() {
		return prevNamespaceURI;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.events.MutationNameEvent#getPrevNodeName()
	 */
	@Override
	public String getPrevNodeName() {
		return prevNodeName;
	}

}
