/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.w3c.events.CustomEvent;
import org.lobobrowser.w3c.html.HTMLElement;

/**
 * The Class CustomEventImpl.
 */
public class CustomEventImpl extends EventImpl implements CustomEvent {

	/**
	 * Instantiates a new custom event impl.
	 */
	public CustomEventImpl() {
	}

	/**
	 * Instantiates a new custom event impl.
	 *
	 * @param type
	 *            the type
	 * @param srcElement
	 *            the src element
	 */
	public CustomEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	/**
	 * Instantiates a new custom event impl.
	 *
	 * @param type
	 *            the type
	 * @param srcElement
	 *            the src element
	 * @param mouseEvent
	 *            the mouse event
	 * @param leafX
	 *            the leaf x
	 * @param leafY
	 *            the leaf y
	 */
	public CustomEventImpl(String type, HTMLElement srcElement, InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	/**
	 * Instantiates a new custom event impl.
	 *
	 * @param type
	 *            the type
	 * @param srcElement
	 *            the src element
	 * @param keyEvent
	 *            the key event
	 */
	public CustomEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.events.CustomEvent#initCustomEvent(java.lang.String,
	 * boolean, boolean, int)
	 */
	@Override
	public void initCustomEvent(String type, boolean canBubble, boolean cancelable, int detail) {
		setType(type);
		setCanBubble(canBubble);
		setCancelable(cancelable);
		setDetail(detail);
	}
}
