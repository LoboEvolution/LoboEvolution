package org.lobobrowser.html.w3c.events;

import org.w3c.dom.views.AbstractView;


/**
 * The Interface WheelEvent.
 */
public interface WheelEvent extends MouseEvent {
	// WheelEvent
	/** The Constant DOM_DELTA_PIXEL. */
	public static final int DOM_DELTA_PIXEL = 0x00;
	
	/** The Constant DOM_DELTA_LINE. */
	public static final int DOM_DELTA_LINE = 0x01;
	
	/** The Constant DOM_DELTA_PAGE. */
	public static final int DOM_DELTA_PAGE = 0x02;

	/**
	 * Gets the delta x.
	 *
	 * @return the delta x
	 */
	public int getDeltaX();

	/**
	 * Gets the delta y.
	 *
	 * @return the delta y
	 */
	public int getDeltaY();

	/**
	 * Gets the delta z.
	 *
	 * @return the delta z
	 */
	public int getDeltaZ();

	/**
	 * Gets the delta mode.
	 *
	 * @return the delta mode
	 */
	public int getDeltaMode();

	/**
	 * Inits the wheel event.
	 *
	 * @param typeArg the type arg
	 * @param canBubbleArg the can bubble arg
	 * @param cancelableArg the cancelable arg
	 * @param viewArg the view arg
	 * @param detailArg the detail arg
	 * @param screenXArg the screen x arg
	 * @param screenYArg the screen y arg
	 * @param clientXArg the client x arg
	 * @param clientYArg the client y arg
	 * @param buttonArg the button arg
	 * @param relatedTargetArg the related target arg
	 * @param modifiersListArg the modifiers list arg
	 * @param deltaXArg the delta x arg
	 * @param deltaYArg the delta y arg
	 * @param deltaZArg the delta z arg
	 * @param deltaMode the delta mode
	 */
	public void initWheelEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, int detailArg,
			int screenXArg, int screenYArg, int clientXArg, int clientYArg,
			short buttonArg, EventTarget relatedTargetArg,
			String modifiersListArg, int deltaXArg, int deltaYArg,
			int deltaZArg, int deltaMode);
}
