/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

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
package org.lobobrowser.html.js.events;

import java.awt.event.InputEvent;

import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MouseEvent;
import org.w3c.dom.views.AbstractView;

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

	public MouseEventImpl(final String type, final boolean shiftKey, final boolean ctrlKey,
			final boolean altKey, final int button) {
		super(type, ("dblclick".equals(type) ? 2 :1), null);
		this.shiftKey = shiftKey;
		this.ctrlKey = ctrlKey;
		this.altKey = altKey;
		this.button = (short)button;
	}

	public MouseEventImpl() {
		this.shiftKey = false;
		this.ctrlKey = false;
		this.altKey = false;
		this.button = 0;
	}

	@Override
	public int getScreenX() {
		return screenX;
	}

	@Override
	public int getScreenY() {
		return screenY;
	}

	@Override
	public int getClientX() {
		return clientX;
	}

	@Override
	public int getClientY() {
		return clientY;
	}

	@Override
	public boolean getCtrlKey() {
		return ctrlKey ? ie.isControlDown() : false;
	}

	@Override
	public boolean getShiftKey() {
		return shiftKey ? ie.isShiftDown() : false;
	}

	@Override
	public boolean getAltKey() {
		return altKey ? ie.isAltDown() : false;
	}

	@Override
	public boolean getMetaKey() {
		return metaKey;
	}

	@Override
	public short getButton() {
		if (ie instanceof MouseEvent) {
			return (short) (((MouseEvent) ie).getButton() - 1);
		} else {
			return button;
		}
	}

	@Override
	public EventTarget getRelatedTarget() {
		return this.relatedTarget;
	}

	public InputEvent getIe() {
		return ie;
	}

	public void setIe(InputEvent ie) {
		this.ie = ie;
	}

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
