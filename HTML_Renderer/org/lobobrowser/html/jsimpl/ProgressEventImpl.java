package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.ProgressEvent;

public class ProgressEventImpl extends EventImpl implements ProgressEvent {
	
	private int loaded;
	private int total;

	public ProgressEventImpl(){}
	
	public ProgressEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	public ProgressEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	public ProgressEventImpl(String type, HTMLElement srcElement,
			KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	@Override
	public void initProgressEvent(String type, boolean canBubble,
			boolean cancelable, boolean lengthComputable, int loaded,
			int total) {

		setType(type);
		setCanBubble(canBubble);
		setCancelable(cancelable);		
		total = (lengthComputable)? total: -1;
		loaded = (loaded >= 0)? loaded: 0;

	}

	@Override
	public boolean getLengthComputable() {
		return (total >= 0);
	}

	@Override
	public int getLoaded() {
		return loaded;
	}

	@Override
	public int getTotal() {
		return (total >= 0)? total: 0;
	}
}