package org.lobobrowser.html.w3c.events;

import org.w3c.dom.views.AbstractView;


/**
 * The Interface FocusEvent.
 */
public interface FocusEvent extends UIEvent {
	// FocusEvent
	/**
	 * Gets the related target.
	 *
	 * @return the related target
	 */
	public EventTarget getRelatedTarget();

	/**
	 * Inits the focus event.
	 *
	 * @param typeArg the type arg
	 * @param canBubbleArg the can bubble arg
	 * @param cancelableArg the cancelable arg
	 * @param viewArg the view arg
	 * @param detailArg the detail arg
	 * @param relatedTargetArg the related target arg
	 */
	public void initFocusEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, int detailArg,
			EventTarget relatedTargetArg);
}
