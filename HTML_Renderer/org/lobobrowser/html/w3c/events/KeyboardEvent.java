package org.lobobrowser.html.w3c.events;

import org.w3c.dom.views.AbstractView;

public interface KeyboardEvent extends UIEvent {
	// KeyboardEvent
	public static final int DOM_KEY_LOCATION_STANDARD = 0x00;
	public static final int DOM_KEY_LOCATION_LEFT = 0x01;
	public static final int DOM_KEY_LOCATION_RIGHT = 0x02;
	public static final int DOM_KEY_LOCATION_NUMPAD = 0x03;
	public static final int DOM_KEY_LOCATION_MOBILE = 0x04;
	public static final int DOM_KEY_LOCATION_JOYSTICK = 0x05;

	public String getKey();

	public int getLocation();

	public boolean getCtrlKey();

	public boolean getShiftKey();

	public boolean getAltKey();

	public boolean getMetaKey();

	public boolean getRepeat();

	public boolean getModifierState(String keyArg);

	public void initKeyboardEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, AbstractView viewArg, String keyArg,
			int locationArg, String modifiersListArg, boolean repeat);
}
