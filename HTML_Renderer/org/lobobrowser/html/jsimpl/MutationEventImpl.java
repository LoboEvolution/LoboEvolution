package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.MutationEvent;
import org.w3c.dom.Node;

public class MutationEventImpl extends EventImpl implements MutationEvent {

	private Node related;
	private String pvalue;
	private String nvalue;
	private String attname;
	private short attchid;

	public MutationEventImpl() {
	}

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
	public void initMutationEvent(String type, boolean canBubble,
			boolean cancelable, Node relatedNode, String prevValue,
			String newValue, String attrName, short attrChange) {

		setType(type);
		setCanBubble(canBubble);
		setCancelable(cancelable);		
		related = relatedNode;
		pvalue = prevValue;
		nvalue = newValue;
		attname = attrName;
		attchid = attrChange;

	}

	@Override
	public Node getRelatedNode() {
		return related;
	}

	@Override
	public String getPrevValue() {
		return pvalue;
	}

	@Override
	public String getNewValue() {
		return nvalue;
	}

	@Override
	public String getAttrName() {
		return attname;
	}

	@Override
	public short getAttrChange() {
		return attchid;
	}
}
