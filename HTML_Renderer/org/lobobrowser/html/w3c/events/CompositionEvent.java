package org.lobobrowser.html.w3c.events;

import org.w3c.dom.views.AbstractView;


/**
 * The Interface CompositionEvent.
 */
public interface CompositionEvent extends UIEvent {
	// CompositionEvent
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String getData();

	/**
	 * Inits the composition event.
	 *
	 * @param typeArg the type arg
	 * @param canBubbleArg the can bubble arg
	 * @param cancelableArg the cancelable arg
	 * @param viewArg the view arg
	 * @param dataArg the data arg
	 */
	public void initCompositionEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, String dataArg);
}
