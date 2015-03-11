package org.lobobrowser.html.w3c.events;


/**
 * The Interface CustomEvent.
 */
public interface CustomEvent extends Event {
	// CustomEvent
	/**
	 * Gets the detail.
	 *
	 * @return the detail
	 */
	public int getDetail();

	/**
	 * Inits the custom event.
	 *
	 * @param typeArg the type arg
	 * @param canBubbleArg the can bubble arg
	 * @param cancelableArg the cancelable arg
	 * @param detailArg the detail arg
	 */
	public void initCustomEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, int detailArg);
}
