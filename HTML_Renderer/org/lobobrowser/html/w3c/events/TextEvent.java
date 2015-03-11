package org.lobobrowser.html.w3c.events;

import org.w3c.dom.views.AbstractView;


/**
 * The Interface TextEvent.
 */
public interface TextEvent extends UIEvent {
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String getData();

	/**
	 * Inits the text event.
	 *
	 * @param typeArg the type arg
	 * @param canBubbleArg the can bubble arg
	 * @param cancelableArg the cancelable arg
	 * @param viewArg the view arg
	 * @param dataArg the data arg
	 */
	public void initTextEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, String dataArg);
}
