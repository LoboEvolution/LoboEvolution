/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.js.events;

import java.awt.event.InputEvent;

import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MouseEvent;
import org.w3c.dom.views.AbstractView;

/**
 * <p>MouseEventImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class MouseEventImpl extends UIEventImpl implements MouseEvent {

	private int screenX;

	private int screenY;

	private int clientX;

	private int clientY;

	private boolean ctrlKey;

	private boolean shiftKey;

	private boolean altKey;

	private boolean metaKey;

	private short button;

	private EventTarget relatedTarget;

	private InputEvent ie;

	/**
	 * <p>Constructor for MouseEventImpl.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param shiftKey a boolean.
	 * @param ctrlKey a boolean.
	 * @param altKey a boolean.
	 * @param button a int.
	 */
	public MouseEventImpl(final String type, final boolean shiftKey, final boolean ctrlKey,
			final boolean altKey, final int button) {
		super(type, ("dblclick".equals(type) ? 2 :1), null);
		this.shiftKey = shiftKey;
		this.ctrlKey = ctrlKey;
		this.altKey = altKey;
		this.button = (short)button;
	}

	/**
	 * <p>Constructor for MouseEventImpl.</p>
	 */
	public MouseEventImpl() {
		this.shiftKey = false;
		this.ctrlKey = false;
		this.altKey = false;
		this.button = 0;
	}

	/** {@inheritDoc} */
	@Override
	public int getScreenX() {
		return screenX;
	}

	/** {@inheritDoc} */
	@Override
	public int getScreenY() {
		return screenY;
	}

	/** {@inheritDoc} */
	@Override
	public int getClientX() {
		return clientX;
	}

	/** {@inheritDoc} */
	@Override
	public int getClientY() {
		return clientY;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getCtrlKey() {
		return ctrlKey && ie.isControlDown();
	}

	/** {@inheritDoc} */
	@Override
	public boolean getShiftKey() {
		return shiftKey && ie.isShiftDown();
	}

	/** {@inheritDoc} */
	@Override
	public boolean getAltKey() {
		return altKey && ie.isAltDown();
	}

	/** {@inheritDoc} */
	@Override
	public boolean getMetaKey() {
		return metaKey;
	}

	/** {@inheritDoc} */
	@Override
	public short getButton() {
		if (ie instanceof MouseEvent) {
			return (short) (((MouseEvent) ie).getButton() - 1);
		} else {
			return button;
		}
	}

	/** {@inheritDoc} */
	@Override
	public EventTarget getRelatedTarget() {
		return this.relatedTarget;
	}

	/**
	 * <p>Getter for the field ie.</p>
	 *
	 * @return a {@link java.awt.event.InputEvent} object.
	 */
	public InputEvent getIe() {
		return ie;
	}

	/**
	 * <p>Setter for the field ie.</p>
	 *
	 * @param ie a {@link java.awt.event.InputEvent} object.
	 */
	public void setIe(InputEvent ie) {
		this.ie = ie;
	}

	/** {@inheritDoc} */
	@Override
	public void initMouseEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, AbstractView viewArg,
			int detailArg, int screenXArg, int screenYArg, int clientXArg, int clientYArg, boolean ctrlKeyArg,
			boolean altKeyArg, boolean shiftKeyArg, boolean metaKeyArg, short buttonArg, EventTarget relatedTargetArg) {
		super.initUIEvent(typeArg, canBubbleArg, cancelableArg, viewArg, detailArg);
		screenX = screenXArg;
		screenY = screenYArg;
		clientX = clientXArg;
		clientY = clientYArg;
		ctrlKey = ctrlKeyArg;
		altKey = altKeyArg;
		shiftKey = shiftKeyArg;
		metaKey = metaKeyArg;
		button = buttonArg;
		relatedTarget = relatedTargetArg;
	}
}
