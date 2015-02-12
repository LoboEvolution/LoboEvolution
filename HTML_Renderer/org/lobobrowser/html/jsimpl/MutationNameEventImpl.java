package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.MutationNameEvent;
import org.w3c.dom.Node;

public class MutationNameEventImpl extends MutationEventImpl implements
		MutationNameEvent {

	private String prevNamespaceURI;
	private String prevNodeName;

	public MutationNameEventImpl() {
	}

	public MutationNameEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	public MutationNameEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	public MutationNameEventImpl(String type, HTMLElement srcElement,
			KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

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

	@Override
	public String getPrevNamespaceURI() {
		return prevNamespaceURI;
	}

	@Override
	public String getPrevNodeName() {
		return prevNodeName;
	}

}
