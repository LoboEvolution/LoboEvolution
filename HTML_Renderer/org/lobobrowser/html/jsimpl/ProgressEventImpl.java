package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.ProgressEvent;

public class ProgressEventImpl extends EventImpl implements ProgressEvent {
	
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
	public void initProgressEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, boolean lengthComputableArg, int loadedArg,
			int totalArg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initProgressEventNS(String namespaceURI, String typeArg,
			boolean canBubbleArg, boolean cancelableArg,
			boolean lengthComputableArg, int loadedArg, int totalArg) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getLengthComputable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getLoaded() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}
}