package org.lobobrowser.html.w3c.events;

import org.w3c.dom.views.AbstractView;


/**
 * The Interface UIEvent.
 */
public interface UIEvent extends Event {
	// UIEvent
	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	public AbstractView getView();

	/**
	 * Gets the detail.
	 *
	 * @return the detail
	 */
	public int getDetail();

	/**
	 * Inits the ui event.
	 *
	 * @param typeArg the type arg
	 * @param canBubbleArg the can bubble arg
	 * @param cancelableArg the cancelable arg
	 * @param viewArg the view arg
	 * @param detailArg the detail arg
	 */
	public void initUIEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, int detailArg);
}
