package org.lobobrowser.html.w3c.events;

import org.w3c.dom.views.AbstractView;


/**
 * The Interface KeyboardEvent.
 */
public interface KeyboardEvent extends UIEvent {
	// KeyboardEvent
	/** The Constant DOM_KEY_LOCATION_STANDARD. */
	public static final int DOM_KEY_LOCATION_STANDARD = 0x00;
	
	/** The Constant DOM_KEY_LOCATION_LEFT. */
	public static final int DOM_KEY_LOCATION_LEFT = 0x01;
	
	/** The Constant DOM_KEY_LOCATION_RIGHT. */
	public static final int DOM_KEY_LOCATION_RIGHT = 0x02;
	
	/** The Constant DOM_KEY_LOCATION_NUMPAD. */
	public static final int DOM_KEY_LOCATION_NUMPAD = 0x03;
	
	/** The Constant DOM_KEY_LOCATION_MOBILE. */
	public static final int DOM_KEY_LOCATION_MOBILE = 0x04;
	
	/** The Constant DOM_KEY_LOCATION_JOYSTICK. */
	public static final int DOM_KEY_LOCATION_JOYSTICK = 0x05;

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey();

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public int getLocation();

	/**
	 * Gets the ctrl key.
	 *
	 * @return the ctrl key
	 */
	public boolean getCtrlKey();

	/**
	 * Gets the shift key.
	 *
	 * @return the shift key
	 */
	public boolean getShiftKey();

	/**
	 * Gets the alt key.
	 *
	 * @return the alt key
	 */
	public boolean getAltKey();

	/**
	 * Gets the meta key.
	 *
	 * @return the meta key
	 */
	public boolean getMetaKey();

	/**
	 * Gets the repeat.
	 *
	 * @return the repeat
	 */
	public boolean getRepeat();

	/**
	 * Gets the modifier state.
	 *
	 * @param keyArg the key arg
	 * @return the modifier state
	 */
	public boolean getModifierState(String keyArg);

	/**
	 * Inits the keyboard event.
	 *
	 * @param typeArg the type arg
	 * @param canBubbleArg the can bubble arg
	 * @param cancelableArg the cancelable arg
	 * @param viewArg the view arg
	 * @param keyArg the key arg
	 * @param locationArg the location arg
	 * @param modifiersListArg the modifiers list arg
	 * @param repeat the repeat
	 */
	public void initKeyboardEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, String keyArg,
			int locationArg, String modifiersListArg, boolean repeat);
}
