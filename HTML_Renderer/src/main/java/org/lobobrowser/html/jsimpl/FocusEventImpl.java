/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.w3c.events.EventTarget;
import org.lobobrowser.w3c.events.FocusEvent;
import org.lobobrowser.w3c.html.HTMLElement;
import org.w3c.dom.views.AbstractView;

/**
 * The Class FocusEventImpl.
 */
public class FocusEventImpl extends UIEventImpl implements FocusEvent {

	/** The related target. */
	private EventTarget relatedTarget;

	/**
	 * Instantiates a new focus event impl.
	 */
	public FocusEventImpl() {
	}

	/**
	 * Instantiates a new focus event impl.
	 *
	 * @param type
	 *            the type
	 * @param srcElement
	 *            the src element
	 */
	public FocusEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	/**
	 * Instantiates a new focus event impl.
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
	public FocusEventImpl(String type, HTMLElement srcElement, InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	/**
	 * Instantiates a new focus event impl.
	 *
	 * @param type
	 *            the type
	 * @param srcElement
	 *            the src element
	 * @param keyEvent
	 *            the key event
	 */
	public FocusEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.events.FocusEvent#initFocusEvent(java.lang.String,
	 * boolean, boolean, org.w3c.dom.views.AbstractView, int,
	 * org.lobobrowser.w3c.events.EventTarget)
	 */
	@Override
	public void initFocusEvent(String type, boolean canBubble, boolean cancelable, AbstractView view, int detail,
			EventTarget relatedTarget) {
		setType(type);
		setCanBubble(canBubble);
		setCancelable(cancelable);
		setView(view);
		setDetail(detail);
		this.relatedTarget = relatedTarget;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.events.FocusEvent#getRelatedTarget()
	 */
	@Override
	public EventTarget getRelatedTarget() {
		return relatedTarget;
	}

}
