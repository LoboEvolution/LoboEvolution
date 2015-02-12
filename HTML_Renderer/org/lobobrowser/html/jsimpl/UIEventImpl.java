package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.events.UIEvent;
import org.w3c.dom.views.AbstractView;

public class UIEventImpl extends EventImpl implements UIEvent {

	private int detail;

	public UIEventImpl() {
	}

	public UIEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	public UIEventImpl(String type, HTMLElement srcElement,
			InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	public UIEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	@Override
	public void initUIEvent(String type, boolean canBubble, boolean cancelable,
			AbstractView view, int detail) {

		setType(type);
		setCanBubble(canBubble);
		setCancelable(cancelable);
		this.detail = detail;

	}

	@Override
	public AbstractView getView() {
		HTMLElementImpl el = (HTMLElementImpl) this.getSrcElement();
		HTMLDocumentImpl doc = (HTMLDocumentImpl) el.getOwnerDocument();
		return doc.getDefaultView();
	}

	@Override
	public int getDetail() {
		return detail;
	}
}
