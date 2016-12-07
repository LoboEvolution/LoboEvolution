/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
import java.awt.event.MouseEvent;

import org.lobobrowser.js.AbstractScriptableDelegate;
import org.lobobrowser.w3c.events.Event;
import org.lobobrowser.w3c.events.EventTarget;
import org.lobobrowser.w3c.html.HTMLElement;
import org.w3c.dom.views.AbstractView;

/**
 * The Class EventImpl.
 */
public class EventImpl extends AbstractScriptableDelegate implements Event {

	/** The ctrl key. */
	private Boolean ctrlKey = null;

	/** The alt key. */
	private Boolean altKey = null;;

	/** The shift key. */
	private Boolean shiftKey = null;;

	/** The meta key. */
	private boolean metaKey;

	/** The can bubble. */
	private boolean canBubble;

	/** The return value. */
	private boolean returnValue;

	/** The bubbles. */
	private boolean bubbles;

	/** The cancelable. */
	private boolean cancelable;

	/** The cancelled. */
	private boolean cancelled;

	/** The detail. */
	private int detail;

	/** The screen x. */
	private int screenX;

	/** The screen y. */
	private int screenY;

	/** The client x. */
	private int clientX;

	/** The client y. */
	private int clientY;

	/** The button. */
	private int button;

	/** The leaf x. */
	private int leafX;

	/** The leaf y. */
	private int leafY;

	/** The event phase. */
	private short eventPhase;

	/** The time stamp. */
	private long timeStamp;

	/** The type. */
	private String type;

	/** The namespace uri. */
	private String namespaceURI;

	/** The from element. */
	private HTMLElement fromElement;

	/** The to element. */
	private HTMLElement toElement;

	/** The src element. */
	private HTMLElement srcElement;

	/** The view. */
	private AbstractView view;

	/** The input event. */
	private InputEvent inputEvent;

	/** The target. */
	private EventTarget target;

	/** The current target. */
	private EventTarget currentTarget;

	/**
	 * Instantiates a new event impl.
	 */
	public EventImpl() {
	}

	/**
	 * Instantiates a new event impl.
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
	public EventImpl(String type, HTMLElement srcElement, InputEvent mouseEvent, int leafX, int leafY) {
		this.type = type;
		this.srcElement = srcElement;
		this.leafX = leafX;
		this.leafY = leafY;
		this.inputEvent = mouseEvent;
		initEvent(type, false, false);
	}

	/**
	 * Instantiates a new event impl.
	 *
	 * @param type
	 *            the type
	 * @param srcElement
	 *            the src element
	 * @param keyEvent
	 *            the key event
	 */
	public EventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		this.type = type;
		this.srcElement = srcElement;
		this.inputEvent = keyEvent;
		initEvent(type, false, false);
	}

	/**
	 * Instantiates a new event impl.
	 *
	 * @param type
	 *            the type
	 * @param srcElement
	 *            the src element
	 */
	public EventImpl(String type, HTMLElement srcElement) {
		this.type = type;
		this.srcElement = srcElement;
		this.inputEvent = null;
		initEvent(type, false, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.events.Event#initEvent(java.lang.String,
	 * boolean, boolean)
	 */
	@Override
	public void initEvent(String type, boolean bubbles, boolean cancelable) {
		this.type = type;
		this.bubbles = bubbles;
		this.cancelable = cancelable;
		this.timeStamp = System.currentTimeMillis();
		this.eventPhase = Event.AT_TARGET;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.events.Event#initEventNS(java.lang.String,
	 * java.lang.String, boolean, boolean)
	 */
	@Override
	public void initEventNS(String namespaceURI, String type, boolean bubbles, boolean cancelable) {
		this.namespaceURI = namespaceURI;
		this.type = type;
		this.bubbles = bubbles;
		this.cancelable = cancelable;
	}

	/**
	 * Gets the alt key.
	 *
	 * @return the alt key
	 */
	public boolean getAltKey() {
		if (altKey != null) {
			return altKey;
		}

		InputEvent ie = this.inputEvent;
		return ie == null ? false : ie.isAltGraphDown();
	}

	/**
	 * Gets the shift key.
	 *
	 * @return the shift key
	 */
	public boolean getShiftKey() {
		if (shiftKey != null) {
			return shiftKey;
		}

		InputEvent ie = this.inputEvent;
		return ie == null ? false : ie.isShiftDown();
	}

	/**
	 * Gets the ctrl key.
	 *
	 * @return the ctrl key
	 */
	public boolean getCtrlKey() {
		if (ctrlKey != null) {
			return ctrlKey;
		}

		InputEvent ie = this.inputEvent;
		return ie == null ? false : ie.isControlDown();
	}

	/**
	 * Gets the button.
	 *
	 * @return the button
	 */
	public int getButton() {
		InputEvent ie = this.inputEvent;
		if (ie instanceof MouseEvent) {
			return ((MouseEvent) ie).getButton();
		} else {
			return button;
		}
	}

	/**
	 * Checks if is can bubble.
	 *
	 * @return the can bubble
	 */
	public boolean isCanBubble() {
		return canBubble;
	}

	/**
	 * Sets the can bubble.
	 *
	 * @param canBubble
	 *            the new can bubble
	 */
	public void setCanBubble(boolean canBubble) {
		this.canBubble = canBubble;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.events.Event#getType()
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the client x.
	 *
	 * @return the client x
	 */
	public int getClientX() {
		InputEvent ie = this.inputEvent;
		if (ie instanceof MouseEvent) {
			return ((MouseEvent) ie).getX();
		} else {
			return clientX;
		}
	}

	/**
	 * Gets the client y.
	 *
	 * @return the client y
	 */
	public int getClientY() {
		InputEvent ie = this.inputEvent;
		if (ie instanceof MouseEvent) {
			return ((MouseEvent) ie).getY();
		} else {
			return clientY;
		}
	}

	/**
	 * Gets the key code.
	 *
	 * @return the key code
	 */
	public int getKeyCode() {
		InputEvent ie = this.inputEvent;
		if (ie instanceof KeyEvent) {
			return ((KeyEvent) ie).getKeyCode();
		} else {
			return 0;
		}
	}

	/**
	 * Checks if is return value.
	 *
	 * @return the return value
	 */
	public boolean isReturnValue() {
		return returnValue;
	}

	/**
	 * Sets the return value.
	 *
	 * @param returnValue
	 *            the new return value
	 */
	public void setReturnValue(boolean returnValue) {
		this.returnValue = returnValue;
	}

	/**
	 * Gets the src element.
	 *
	 * @return the src element
	 */
	public HTMLElement getSrcElement() {
		return srcElement;
	}

	/**
	 * Sets the src element.
	 *
	 * @param srcElement
	 *            the new src element
	 */
	public void setSrcElement(HTMLElement srcElement) {
		this.srcElement = srcElement;
	}

	/**
	 * Gets the from element.
	 *
	 * @return the from element
	 */
	public HTMLElement getFromElement() {
		return fromElement;
	}

	/**
	 * Sets the from element.
	 *
	 * @param fromElement
	 *            the new from element
	 */
	public void setFromElement(HTMLElement fromElement) {
		this.fromElement = fromElement;
	}

	/**
	 * Gets the to element.
	 *
	 * @return the to element
	 */
	public HTMLElement getToElement() {
		return toElement;
	}

	/**
	 * Sets the to element.
	 *
	 * @param toElement
	 *            the new to element
	 */
	public void setToElement(HTMLElement toElement) {
		this.toElement = toElement;
	}

	/**
	 * Gets the leaf x.
	 *
	 * @return the leaf x
	 */
	public int getLeafX() {
		return leafX;
	}

	/**
	 * Sets the leaf x.
	 *
	 * @param leafX
	 *            the new leaf x
	 */
	public void setLeafX(int leafX) {
		this.leafX = leafX;
	}

	/**
	 * Gets the leaf y.
	 *
	 * @return the leaf y
	 */
	public int getLeafY() {
		return leafY;
	}

	/**
	 * Sets the leaf y.
	 *
	 * @param leafY
	 *            the new leaf y
	 */
	public void setLeafY(int leafY) {
		this.leafY = leafY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.events.Event#preventDefault()
	 */
	@Override
	public void preventDefault() {
		if (this.cancelable) {
			this.cancelable = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.events.Event#stopPropagation()
	 */
	@Override
	public void stopPropagation() {
		if (this.bubbles) {
			this.canBubble = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.events.Event#stopImmediatePropagation()
	 */
	@Override
	public void stopImmediatePropagation() {
		if (this.bubbles) {
			this.canBubble = true;
		}
		this.cancelled = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.events.Event#getTarget()
	 */
	@Override
	public EventTarget getTarget() {
		return target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.events.Event#getCurrentTarget()
	 */
	@Override
	public EventTarget getCurrentTarget() {
		return currentTarget;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.events.Event#getEventPhase()
	 */
	@Override
	public short getEventPhase() {
		return eventPhase;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.events.Event#getBubbles()
	 */
	@Override
	public boolean getBubbles() {
		return bubbles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.events.Event#getCancelable()
	 */
	@Override
	public boolean getCancelable() {
		return cancelable;
	}

	/**
	 * Sets the cancelable.
	 *
	 * @param cancelable
	 *            the new cancelable
	 */
	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.events.Event#getTimeStamp()
	 */
	@Override
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Sets the target.
	 *
	 * @param target
	 *            the new target
	 */
	public void setTarget(EventTarget target) {
		this.target = target;
	}

	/**
	 * Sets the current target.
	 *
	 * @param currentTarget
	 *            the new current target
	 */
	public void setCurrentTarget(EventTarget currentTarget) {
		this.currentTarget = currentTarget;
	}

	/**
	 * Sets the event phase.
	 *
	 * @param eventPhase
	 *            the new event phase
	 */
	public void setEventPhase(short eventPhase) {
		this.eventPhase = eventPhase;
	}

	/**
	 * Cancelled.
	 *
	 * @return true, if successful
	 */
	public boolean cancelled() {
		return cancelled;
	}

	/**
	 * Gets the meta key.
	 *
	 * @return the meta key
	 */
	public boolean getMetaKey() {
		return metaKey;
	}

	/**
	 * Sets the meta key.
	 *
	 * @param metaKey
	 *            the new meta key
	 */
	public void setMetaKey(boolean metaKey) {
		this.metaKey = metaKey;
	}

	/**
	 * Gets the detail.
	 *
	 * @return the detail
	 */
	public int getDetail() {
		return detail;
	}

	/**
	 * Sets the detail.
	 *
	 * @param detail
	 *            the new detail
	 */
	public void setDetail(int detail) {
		this.detail = detail;
	}

	/**
	 * Gets the screen x.
	 *
	 * @return the screen x
	 */
	public int getScreenX() {
		return screenX;
	}

	/**
	 * Sets the screen x.
	 *
	 * @param screenX
	 *            the new screen x
	 */
	public void setScreenX(int screenX) {
		this.screenX = screenX;
	}

	/**
	 * Gets the screen y.
	 *
	 * @return the screen y
	 */
	public int getScreenY() {
		return screenY;
	}

	/**
	 * Sets the screen y.
	 *
	 * @param screenY
	 *            the new screen y
	 */
	public void setScreenY(int screenY) {
		this.screenY = screenY;
	}

	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	public AbstractView getView() {
		return view;
	}

	/**
	 * Sets the view.
	 *
	 * @param view
	 *            the new view
	 */
	public void setView(AbstractView view) {
		this.view = view;
	}

	/**
	 * Sets the ctrl key.
	 *
	 * @param ctrlKey
	 *            the new ctrl key
	 */
	public void setCtrlKey(boolean ctrlKey) {
		this.ctrlKey = ctrlKey;
	}

	/**
	 * Sets the alt key.
	 *
	 * @param altKey
	 *            the new alt key
	 */
	public void setAltKey(boolean altKey) {
		this.altKey = altKey;
	}

	/**
	 * Sets the shift key.
	 *
	 * @param shiftKey
	 *            the new shift key
	 */
	public void setShiftKey(boolean shiftKey) {
		this.shiftKey = shiftKey;
	}

	/**
	 * Sets the client x.
	 *
	 * @param clientX
	 *            the new client x
	 */
	public void setClientX(int clientX) {
		this.clientX = clientX;
	}

	/**
	 * Sets the client y.
	 *
	 * @param clientY
	 *            the new client y
	 */
	public void setClientY(int clientY) {
		this.clientY = clientY;
	}

	/**
	 * Sets the button.
	 *
	 * @param button
	 *            the new button
	 */
	public void setButton(int button) {
		this.button = button;
	}

	/**
	 * Gets the namespace uri.
	 *
	 * @return the namespace uri
	 */
	public String getNamespaceURI() {
		return namespaceURI;
	}

	/**
	 * Sets the namespace uri.
	 *
	 * @param namespaceURI
	 *            the new namespace uri
	 */
	public void setNamespaceURI(String namespaceURI) {
		this.namespaceURI = namespaceURI;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.events.Event#getDefaultPrevented()
	 */
	@Override
	public boolean getDefaultPrevented() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.events.Event#getTrusted()
	 */
	@Override
	public boolean getTrusted() {
		// TODO Auto-generated method stub
		return false;
	}
}
