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

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.loboevolution.w3c.events.EventTarget;
import org.loboevolution.w3c.events.MouseEvent;
import org.loboevolution.w3c.html.HTMLElement;
import org.w3c.dom.views.AbstractView;

/**
 * The Class MouseEventImpl.
 */
public class MouseEventImpl extends UIEventImpl implements MouseEvent {

	/**
	 * Instantiates a new mouse event impl.
	 */
	public MouseEventImpl() {
	}

	/**
	 * Instantiates a new mouse event impl.
	 *
	 * @param type
	 *            the type
	 * @param srcElement
	 *            the src element
	 */
	public MouseEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	/**
	 * Instantiates a new mouse event impl.
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
	public MouseEventImpl(String type, HTMLElement srcElement, InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	/**
	 * Instantiates a new mouse event impl.
	 *
	 * @param type
	 *            the type
	 * @param srcElement
	 *            the src element
	 * @param keyEvent
	 *            the key event
	 */
	public MouseEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.events.MouseEvent#initMouseEvent(java.lang.String,
	 * boolean, boolean, org.w3c.dom.views.AbstractView, int, int, int, int,
	 * int, boolean, boolean, boolean, boolean, int,
	 * org.loboevolution.w3c.events.EventTarget)
	 */
	@Override
	public void initMouseEvent(String type, boolean canBubble, boolean cancelable, AbstractView view, int detail,
			int screenX, int screenY, int clientX, int clientY, boolean ctrlKey, boolean altKey, boolean shiftKey,
			boolean metaKey, int button, EventTarget relatedTarget) {

		initMouseEventNS(null, type, canBubble, cancelable, view, detail, screenX, screenY, clientX, clientY, ctrlKey,
				altKey, shiftKey, metaKey, button, relatedTarget);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.events.MouseEvent#initMouseEventNS(java.lang.String,
	 * java.lang.String, boolean, boolean, org.w3c.dom.views.AbstractView, int,
	 * int, int, int, int, boolean, boolean, boolean, boolean, int,
	 * org.loboevolution.w3c.events.EventTarget)
	 */
	@Override
	public void initMouseEventNS(String namespaceURI, String type, boolean canBubble, boolean cancelable,
			AbstractView view, int detail, int screenX, int screenY, int clientX, int clientY, boolean ctrlKey,
			boolean altKey, boolean shiftKey, boolean metaKey, int button, EventTarget relatedTarget) {

		setType(type);
		setCanBubble(canBubble);
		setCancelable(cancelable);
		setView(view);
		setDetail(detail);
		setScreenX(screenX);
		setScreenY(screenY);
		setClientX(clientX);
		setClientY(clientY);
		setCtrlKey(ctrlKey);
		setAltKey(altKey);
		setShiftKey(shiftKey);
		setMetaKey(metaKey);
		setButton(button);
		setCurrentTarget(relatedTarget);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.events.MouseEvent#getRelatedTarget()
	 */
	@Override
	public HTMLElement getRelatedTarget() {
		return this.getSrcElement();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.events.MouseEvent#getModifierState(java.lang.String)
	 */
	@Override
	public boolean getModifierState(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.events.MouseEvent#getPageX()
	 */
	@Override
	public int getPageX() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.events.MouseEvent#getPageY()
	 */
	@Override
	public int getPageY() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.events.MouseEvent#getX()
	 */
	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.events.MouseEvent#getY()
	 */
	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.events.MouseEvent#getOffsetX()
	 */
	@Override
	public int getOffsetX() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.events.MouseEvent#getOffsetY()
	 */
	@Override
	public int getOffsetY() {
		// TODO Auto-generated method stub
		return 0;
	}
}
