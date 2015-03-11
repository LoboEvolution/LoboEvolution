package org.lobobrowser.html.w3c.events;


/**
 * The Interface ProgressEvent.
 */
public interface ProgressEvent extends Event {
	// ProgressEvent
	/**
	 * Gets the length computable.
	 *
	 * @return the length computable
	 */
	public boolean getLengthComputable();

	/**
	 * Gets the loaded.
	 *
	 * @return the loaded
	 */
	public int getLoaded();

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public int getTotal();

	/**
	 * Inits the progress event.
	 *
	 * @param typeArg the type arg
	 * @param canBubbleArg the can bubble arg
	 * @param cancelableArg the cancelable arg
	 * @param lengthComputableArg the length computable arg
	 * @param loadedArg the loaded arg
	 * @param totalArg the total arg
	 */
	public void initProgressEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, boolean lengthComputableArg, int loadedArg,
			int totalArg);
}
