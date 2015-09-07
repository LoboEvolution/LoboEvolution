/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.jsimpl;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.lobobrowser.w3c.events.EventTarget;
import org.lobobrowser.w3c.events.WheelEvent;
import org.lobobrowser.w3c.html.HTMLElement;
import org.w3c.dom.views.AbstractView;

/**
 * The Class WheelEventImpl.
 */
public class WheelEventImpl extends MouseEventImpl implements WheelEvent {

    /** The delta x. */
    private int deltaX;

    /** The delta y. */
    private int deltaY;

    /** The delta z. */
    private int deltaZ;

    /** The delta mode. */
    private int deltaMode;

    /**
     * Instantiates a new wheel event impl.
     */
    public WheelEventImpl() {
    }

    /**
     * Instantiates a new wheel event impl.
     *
     * @param type
     *            the type
     * @param srcElement
     *            the src element
     */
    public WheelEventImpl(String type, HTMLElement srcElement) {
        super(type, srcElement);
    }

    /**
     * Instantiates a new wheel event impl.
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
    public WheelEventImpl(String type, HTMLElement srcElement,
            InputEvent mouseEvent, int leafX, int leafY) {
        super(type, srcElement, mouseEvent, leafX, leafY);
    }

    /**
     * Instantiates a new wheel event impl.
     *
     * @param type
     *            the type
     * @param srcElement
     *            the src element
     * @param keyEvent
     *            the key event
     */
    public WheelEventImpl(String type, HTMLElement srcElement, KeyEvent keyEvent) {
        super(type, srcElement, keyEvent);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.w3c.events.WheelEvent#initWheelEvent(java.lang.String,
     * boolean, boolean, org.w3c.dom.views.AbstractView, int, int, int, int, int,
     * short, org.lobobrowser.w3c.events.EventTarget, java.lang.String, int,
     * int, int, int)
     */
    @Override
    public void initWheelEvent(String type, boolean canBubble,
            boolean cancelable, AbstractView view, int detail, int screenX,
            int screenY, int clientX, int clientY, short button,
            EventTarget relatedTarget, String modifiersList, int deltaX,
            int deltaY, int deltaZ, int deltaMode) {
        setType(type);
        setCanBubble(canBubble);
        setCancelable(cancelable);
        setView(view);
        setDetail(detail);
        setScreenX(screenX);
        setScreenY(screenY);
        setClientX(clientX);
        setClientY(clientY);
        setButton(button);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.deltaMode = deltaMode;

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.events.WheelEvent#getDeltaX()
     */
    @Override
    public int getDeltaX() {
        return deltaX;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.events.WheelEvent#getDeltaY()
     */
    @Override
    public int getDeltaY() {
        return deltaY;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.events.WheelEvent#getDeltaZ()
     */
    @Override
    public int getDeltaZ() {
        return deltaZ;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.events.WheelEvent#getDeltaMode()
     */
    @Override
    public int getDeltaMode() {
        return deltaMode;
    }
}
