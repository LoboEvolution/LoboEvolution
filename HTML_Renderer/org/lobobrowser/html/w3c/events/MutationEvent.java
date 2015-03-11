package org.lobobrowser.html.w3c.events;

import org.w3c.dom.Node;


/**
 * The Interface MutationEvent.
 */
public interface MutationEvent extends Event {
	// MutationEvent
	/** The Constant MODIFICATION. */
	public static final short MODIFICATION = 1;
	
	/** The Constant ADDITION. */
	public static final short ADDITION = 2;
	
	/** The Constant REMOVAL. */
	public static final short REMOVAL = 3;

	/**
	 * Gets the related node.
	 *
	 * @return the related node
	 */
	public Node getRelatedNode();

	/**
	 * Gets the prev value.
	 *
	 * @return the prev value
	 */
	public String getPrevValue();

	/**
	 * Gets the new value.
	 *
	 * @return the new value
	 */
	public String getNewValue();

	/**
	 * Gets the attr name.
	 *
	 * @return the attr name
	 */
	public String getAttrName();

	/**
	 * Gets the attr change.
	 *
	 * @return the attr change
	 */
	public short getAttrChange();

	/**
	 * Inits the mutation event.
	 *
	 * @param typeArg the type arg
	 * @param canBubbleArg the can bubble arg
	 * @param cancelableArg the cancelable arg
	 * @param relatedNodeArg the related node arg
	 * @param prevValueArg the prev value arg
	 * @param newValueArg the new value arg
	 * @param attrNameArg the attr name arg
	 * @param attrChangeArg the attr change arg
	 */
	public void initMutationEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, Node relatedNodeArg, String prevValueArg,
			String newValueArg, String attrNameArg, short attrChangeArg);
}
