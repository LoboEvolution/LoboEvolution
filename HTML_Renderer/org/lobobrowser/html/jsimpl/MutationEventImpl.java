package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.MutationEvent;
import org.w3c.dom.Node;

public class MutationEventImpl extends EventImpl implements MutationEvent {
	
	public MutationEventImpl(){}
	
	public MutationEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	public MutationEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	public MutationEventImpl(String type, HTMLElement srcElement,
			KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	@Override
	public void initMutationEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, Node relatedNodeArg, String prevValueArg,
			String newValueArg, String attrNameArg, short attrChangeArg) {
		// TODO Auto-generated method stub

	}

	@Override
	public Node getRelatedNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrevValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNewValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAttrName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getAttrChange() {
		// TODO Auto-generated method stub
		return 0;
	}

}
