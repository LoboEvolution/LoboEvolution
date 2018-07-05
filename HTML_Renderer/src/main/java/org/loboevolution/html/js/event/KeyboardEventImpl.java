/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.js.event;

import java.awt.event.KeyEvent;

import org.loboevolution.w3c.events.KeyboardEvent;
import org.loboevolution.w3c.html.HTMLElement;
import org.w3c.dom.views.AbstractView;

/**
 * The Class KeyboardEventImpl.
 */
public class KeyboardEventImpl extends UIEventImpl implements KeyboardEvent {

	/** The key event. */
	private KeyEvent keyEvent;

	/** The key. */
	private String key;

	/** The modifiers list. */
	private String modifiersList;

	/** The repeat. */
	private boolean repeat;

	/** The location. */
	private int location;

	/**
	 * Instantiates a new keyboard event impl.
	 */
	public KeyboardEventImpl() {
	}

	/**
	 * Instantiates a new keyboard event impl.
	 *
	 * @param type
	 *            the type
	 * @param srcElement
	 *            the src element
	 * @param keyEvent
	 *            the key event
	 */
	public KeyboardEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
		this.keyEvent = keyEvent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.events.KeyboardEvent#initKeyboardEvent(java.lang
	 * .String, boolean, boolean, org.w3c.dom.views.AbstractView,
	 * java.lang.String, int, java.lang.String, boolean)
	 */
	@Override
	public void initKeyboardEvent(String type, boolean canBubble, boolean cancelable, AbstractView view, String key,
			int location, String modifiersList, boolean repeat) {
		setType(type);
		setCanBubble(canBubble);
		setCancelable(cancelable);
		setView(view);
		setKey(key);
		setLocation(location);
		setModifiersList(modifiersList);
		setRepeat(repeat);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.events.KeyboardEvent#getKey()
	 */
	@Override
	public String getKey() {
		if (key != null) {
			return key;
		} else {
			return Character.toString(this.keyEvent.getKeyChar());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.events.KeyboardEvent#getLocation()
	 */
	@Override
	public int getLocation() {
		if (location > 0) {
			return location;
		} else {
			return this.keyEvent.getKeyLocation();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.js.event.EventImpl#getMetaKey()
	 */
	@Override
	public boolean getMetaKey() {
		return this.keyEvent.getKeyCode() == 524;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.events.KeyboardEvent#getRepeat()
	 */
	@Override
	public boolean getRepeat() {
		return repeat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.events.KeyboardEvent#getModifierState(java.lang.
	 * String)
	 */
	@Override
	public boolean getModifierState(String key) {
		String keyText = KeyEvent.getKeyText(this.keyEvent.getKeyCode());
		return keyText.equalsIgnoreCase(key);
	}

	/**
	 * Sets the repeat.
	 *
	 * @param repeat
	 *            the new repeat
	 */
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	/**
	 * Sets the key.
	 *
	 * @param key
	 *            the new key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets the modifiers list.
	 *
	 * @return the modifiers list
	 */
	public String getModifiersList() {
		return modifiersList;
	}

	/**
	 * Sets the modifiers list.
	 *
	 * @param modifiersList
	 *            the new modifiers list
	 */
	public void setModifiersList(String modifiersList) {
		this.modifiersList = modifiersList;
	}

	/**
	 * Sets the location.
	 *
	 * @param location
	 *            the new location
	 */
	public void setLocation(int location) {
		this.location = location;
	}
}
