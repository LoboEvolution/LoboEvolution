package org.lobobrowser.html.w3c.events;

import org.w3c.dom.Node;

public interface MutationEvent extends Event {
	// MutationEvent
	public static final short MODIFICATION = 1;
	public static final short ADDITION = 2;
	public static final short REMOVAL = 3;

	public Node getRelatedNode();

	public String getPrevValue();

	public String getNewValue();

	public String getAttrName();

	public short getAttrChange();

	public void initMutationEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, Node relatedNodeArg, String prevValueArg,
			String newValueArg, String attrNameArg, short attrChangeArg);
}
