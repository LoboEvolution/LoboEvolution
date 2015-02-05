package org.lobobrowser.html.w3c.events;

import org.w3c.dom.Node;

public interface MutationNameEvent extends MutationEvent {
	// MutationNameEvent
	public String getPrevNamespaceURI();

	public String getPrevNodeName();

	public void initMutationNameEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, Node relatedNodeArg,
			String prevNamespaceURIArg, String prevNodeNameArg);
}
