package org.lobobrowser.html.jsimpl;

import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.KeyboardEvent;
import org.w3c.dom.views.AbstractView;

public class KeyboardEventImpl extends UIEventImpl implements KeyboardEvent {

	private KeyEvent keyEvent;
	private String key;
	private String modifiersList;
	private boolean repeat;
	private int location;

	public KeyboardEventImpl() {
	}

	public KeyboardEventImpl(String type, HTMLElement srcElement,
			KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
		this.keyEvent = keyEvent;
	}

	@Override
	public void initKeyboardEvent(String type, boolean canBubble,
			boolean cancelable, AbstractView view, String key,
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

	@Override
	public String getKey() {
		if (key != null)
			return key;
		else
			return Character.toString(this.keyEvent.getKeyChar());
	}

	@Override
	public int getLocation() {
		if (location > 0)
			return location;
		else
			return this.keyEvent.getKeyLocation();
	}

	@Override
	public boolean getMetaKey() {
		return this.keyEvent.getKeyCode() == 524;
	}

	@Override
	public boolean getRepeat() {
		return repeat;
	}

	@Override
	public boolean getModifierState(String key) {
		String keyText = KeyEvent.getKeyText(this.keyEvent.getKeyCode());
		return keyText.equalsIgnoreCase(key);
	}
	
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getModifiersList() {
		return modifiersList;
	}

	public void setModifiersList(String modifiersList) {
		this.modifiersList = modifiersList;
	}

	public void setLocation(int location) {
		this.location = location;
	}
}
