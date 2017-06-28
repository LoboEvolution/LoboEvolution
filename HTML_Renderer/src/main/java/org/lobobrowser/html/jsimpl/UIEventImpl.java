/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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

import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.w3c.events.UIEvent;
import org.lobobrowser.w3c.html.HTMLElement;
import org.w3c.dom.views.AbstractView;

/**
 * The Class UIEventImpl.
 */
public class UIEventImpl extends EventImpl implements UIEvent {

	/** The detail. */
	private int detail;

	/**
	 * Instantiates a new UI event impl.
	 */
	public UIEventImpl() {
	}

	/**
	 * Instantiates a new UI event impl.
	 *
	 * @param type
	 *            the type
	 * @param srcElement
	 *            the src element
	 */
	public UIEventImpl(String type, HTMLElement srcElement) {
		super(type, srcElement);
	}

	/**
	 * Instantiates a new UI event impl.
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
	public UIEventImpl(String type, HTMLElement srcElement, InputEvent mouseEvent, int leafX, int leafY) {
		super(type, srcElement, mouseEvent, leafX, leafY);
	}

	/**
	 * Instantiates a new UI event impl.
	 *
	 * @param type
	 *            the type
	 * @param srcElement
	 *            the src element
	 * @param keyEvent
	 *            the key event
	 */
	public UIEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
		super(type, srcElement, keyEvent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.events.UIEvent#initUIEvent(java.lang.String,
	 * boolean, boolean, org.w3c.dom.views.AbstractView, int)
	 */
	@Override
	public void initUIEvent(String type, boolean canBubble, boolean cancelable, AbstractView view, int detail) {

		setType(type);
		setCanBubble(canBubble);
		setCancelable(cancelable);
		this.detail = detail;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.jsimpl.EventImpl#getView()
	 */
	@Override
	public AbstractView getView() {
		HTMLElementImpl el = (HTMLElementImpl) this.getSrcElement();
		HTMLDocumentImpl doc = (HTMLDocumentImpl) el.getOwnerDocument();
		return doc.getDefaultView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.jsimpl.EventImpl#getDetail()
	 */
	@Override
	public int getDetail() {
		return detail;
	}
}
