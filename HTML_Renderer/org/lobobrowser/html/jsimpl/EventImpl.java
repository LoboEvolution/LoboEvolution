/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.Event;
import org.lobobrowser.html.w3c.events.EventTarget;
import org.lobobrowser.js.AbstractScriptableDelegate;
import org.w3c.dom.views.AbstractView;

public class EventImpl extends AbstractScriptableDelegate implements Event {
	
	
	private boolean ctrlKey; 
	private boolean altKey;
	private boolean shiftKey;
	private boolean metaKey; 
	private boolean cancelBubble;
	private boolean returnValue;
	private boolean bubbles;
	private boolean cancelable;
	private boolean defaultPrevented;
	private boolean isTrusted;
	private boolean cancelled;
	private int detail;
	private int screenX; 
	private int screenY; 
	private int clientX; 
	private int clientY;
	private int button;
	private int leafX;
	private int leafY;
	private short eventPhase;
	private long timeStamp;
	private HTMLElement fromElement;
	private HTMLElement toElement;
	private HTMLElement srcElement;
	private AbstractView view;
	private InputEvent inputEvent= null;
	private EventTarget target;
	private EventTarget currentTarget;
	private String type;
	
	public EventImpl(){}

	public EventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		this.type = type;
		this.srcElement = srcElement;
		this.leafX = leafX;
		this.leafY = leafY;
		this.inputEvent = mouseEvent;
		initEvent(type, false, false);
	}

	public EventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		this.type = type;
		this.srcElement = srcElement;
		this.inputEvent = keyEvent;
		initEvent(type, false, false);
	}

	public EventImpl(String type, HTMLElement srcElement) {
		this.type = type;
		this.srcElement = srcElement;
		this.inputEvent = null;
		initEvent(type, false, false);
	}

	@Override
	public void initEvent(String type, boolean bubbles, boolean cancelable) {
		this.type = type;
		this.bubbles = bubbles;
		this.cancelable = cancelable;
		this.timeStamp = System.currentTimeMillis();
		this.eventPhase = Event.AT_TARGET;
	}

	public boolean getAltKey() {
		System.out.println("alt: " + this.inputEvent.isAltDown());
		
		InputEvent ie = this.inputEvent;
		return ie == null ? false : ie.isAltGraphDown();
	}

	public boolean getShiftKey() {
		InputEvent ie = this.inputEvent;
		return ie == null ? false : ie.isShiftDown();
	}

	public boolean getCtrlKey() {
		InputEvent ie = this.inputEvent;
		return ie == null ? false : ie.isControlDown();
	}

	public int getButton() {
		InputEvent ie = this.inputEvent;
		if (ie instanceof MouseEvent) {
			return ((MouseEvent) ie).getButton();
		} else {
			return button;
		}
	}

	public boolean isCancelBubble() {
		return cancelBubble;
	}

	public void setCancelBubble(boolean cancelBubble) {
		this.cancelBubble = cancelBubble;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getClientX() {
		InputEvent ie = this.inputEvent;
		if (ie instanceof MouseEvent) {
			return ((MouseEvent) ie).getX();
		} else {
			return clientX;
		}
	}

	public int getClientY() {
		InputEvent ie = this.inputEvent;
		if (ie instanceof MouseEvent) {
			return ((MouseEvent) ie).getY();
		} else {
			return clientY;
		}
	}

	public int getKeyCode() {
		InputEvent ie = this.inputEvent;
		if (ie instanceof KeyEvent) {
			return ((KeyEvent) ie).getKeyCode();
		} else {
			return 0;
		}
	}

	public boolean isReturnValue() {
		return returnValue;
	}

	public void setReturnValue(boolean returnValue) {
		this.returnValue = returnValue;
	}

	public HTMLElement getSrcElement() {
		return srcElement;
	}

	public void setSrcElement(HTMLElement srcElement) {
		this.srcElement = srcElement;
	}

	public HTMLElement getFromElement() {
		return fromElement;
	}

	public void setFromElement(HTMLElement fromElement) {
		this.fromElement = fromElement;
	}

	public HTMLElement getToElement() {
		return toElement;
	}

	public void setToElement(HTMLElement toElement) {
		this.toElement = toElement;
	}

	public int getLeafX() {
		return leafX;
	}

	public void setLeafX(int leafX) {
		this.leafX = leafX;
	}

	public int getLeafY() {
		return leafY;
	}

	public void setLeafY(int leafY) {
		this.leafY = leafY;
	}

	public void preventDefault() {
		if (this.cancelable) {
			this.defaultPrevented = true;
		}
	}

	public void stopPropagation() {
		if (this.bubbles) {
			this.cancelBubble = true;
		}
	}

	public void stopImmediatePropagation() {
		if (this.bubbles) {
			this.cancelBubble = true;
		}
		this.cancelled = true;
	}

	public EventTarget getTarget() {
		return target;
	}

	public EventTarget getCurrentTarget() {
		return currentTarget;
	}

	public short getEventPhase() {
		return eventPhase;
	}

	public boolean getBubbles() {
		return bubbles;
	}

	public boolean getCancelable() {
		return cancelable;
	}
	
	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public boolean isDefaultPrevented() {
		return defaultPrevented;
	}

	public boolean isIsTrusted() {
		return isTrusted;
	}

	public void setTarget(EventTarget target) {
		this.target = target;
	}

	public void setCurrentTarget(EventTarget currentTarget) {
		this.currentTarget = currentTarget;
	}

	public void setEventPhase(short eventPhase) {
		this.eventPhase = eventPhase;
	}

	public boolean cancelBubble() {
		return cancelBubble;
	}

	public boolean cancelled() {
		return cancelled;
	}
	
	public boolean isMetaKey() {
		return metaKey;
	}

	public void setMetaKey(boolean metaKey) {
		this.metaKey = metaKey;
	}

	public int getDetail() {
		return detail;
	}

	public void setDetail(int detail) {
		this.detail = detail;
	}

	public int getScreenX() {
		return screenX;
	}

	public void setScreenX(int screenX) {
		this.screenX = screenX;
	}

	public int getScreenY() {
		return screenY;
	}

	public void setScreenY(int screenY) {
		this.screenY = screenY;
	}

	public AbstractView getView() {
		return view;
	}

	public void setView(AbstractView view) {
		this.view = view;
	}

	public void setCtrlKey(boolean ctrlKey) {
		this.ctrlKey = ctrlKey;
	}

	public void setAltKey(boolean altKey) {
		this.altKey = altKey;
	}

	public void setShiftKey(boolean shiftKey) {
		this.shiftKey = shiftKey;
	}

	public void setClientX(int clientX) {
		this.clientX = clientX;
	}

	public void setClientY(int clientY) {
		this.clientY = clientY;
	}

	public void setButton(int button) {
		this.button = button;
	}

	@Override
	public boolean getDefaultPrevented() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getTrusted() {
		// TODO Auto-generated method stub
		return false;
	}
}
