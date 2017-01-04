/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.w3c.events;

import org.w3c.dom.views.AbstractView;


/**
 * The public interface KeyboardEvent.
 */
public interface KeyboardEvent extends UIEvent {
	// KeyboardEvent
	/** The Constant DOM_KEY_LOCATION_STANDARD. */
	int DOM_KEY_LOCATION_STANDARD = 0x00;

	/** The Constant DOM_KEY_LOCATION_LEFT. */
	int DOM_KEY_LOCATION_LEFT = 0x01;

	/** The Constant DOM_KEY_LOCATION_RIGHT. */
	int DOM_KEY_LOCATION_RIGHT = 0x02;

	/** The Constant DOM_KEY_LOCATION_NUMPAD. */
	int DOM_KEY_LOCATION_NUMPAD = 0x03;

	/** The Constant DOM_KEY_LOCATION_MOBILE. */
	int DOM_KEY_LOCATION_MOBILE = 0x04;

	/** The Constant DOM_KEY_LOCATION_JOYSTICK. */
	int DOM_KEY_LOCATION_JOYSTICK = 0x05;

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	String getKey();

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	int getLocation();

	/**
	 * Gets the ctrl key.
	 *
	 * @return the ctrl key
	 */
	boolean getCtrlKey();

	/**
	 * Gets the shift key.
	 *
	 * @return the shift key
	 */
	boolean getShiftKey();

	/**
	 * Gets the alt key.
	 *
	 * @return the alt key
	 */
	boolean getAltKey();

	/**
	 * Gets the meta key.
	 *
	 * @return the meta key
	 */
	boolean getMetaKey();

	/**
	 * Gets the repeat.
	 *
	 * @return the repeat
	 */
	boolean getRepeat();

	/**
	 * Gets the modifier state.
	 *
	 * @param keyArg
	 *            the key arg
	 * @return the modifier state
	 */
	boolean getModifierState(String keyArg);

	/**
	 * Inits the keyboard event.
	 *
	 * @param typeArg
	 *            the type arg
	 * @param canBubbleArg
	 *            the can bubble arg
	 * @param cancelableArg
	 *            the cancelable arg
	 * @param viewArg
	 *            the view arg
	 * @param keyArg
	 *            the key arg
	 * @param locationArg
	 *            the location arg
	 * @param modifiersListArg
	 *            the modifiers list arg
	 * @param repeat
	 *            the repeat
	 */
	void initKeyboardEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, AbstractView viewArg,
			String keyArg, int locationArg, String modifiersListArg, boolean repeat);
}
