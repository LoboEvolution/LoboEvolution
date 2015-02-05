package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.MutationNameEvent;
import org.w3c.dom.Node;

public class MutationNameEventImpl extends MutationEventImpl implements MutationNameEvent {
	
	public MutationNameEventImpl(){}
	
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
	public void initMutationNameEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, Node relatedNodeArg,
			String prevNamespaceURIArg, String prevNodeNameArg) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPrevNamespaceURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrevNodeName() {
		// TODO Auto-generated method stub
		return null;
	}

}
