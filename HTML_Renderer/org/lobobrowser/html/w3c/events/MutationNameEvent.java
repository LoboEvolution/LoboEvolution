package org.lobobrowser.html.w3c.events;

import org.w3c.dom.Node;


/**
 * The Interface MutationNameEvent.
 */
public interface MutationNameEvent extends MutationEvent {
	// MutationNameEvent
	/**
	 * Gets the prev namespace uri.
	 *
	 * @return the prev namespace uri
	 */
	public String getPrevNamespaceURI();

	/**
	 * Gets the prev node name.
	 *
	 * @return the prev node name
	 */
	public String getPrevNodeName();

	/**
	 * Inits the mutation name event.
	 *
	 * @param typeArg the type arg
	 * @param canBubbleArg the can bubble arg
	 * @param cancelableArg the cancelable arg
	 * @param relatedNodeArg the related node arg
	 * @param prevNamespaceURIArg the prev namespace uri arg
	 * @param prevNodeNameArg the prev node name arg
	 */
	public void initMutationNameEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, Node relatedNodeArg,
			String prevNamespaceURIArg, String prevNodeNameArg);
}
