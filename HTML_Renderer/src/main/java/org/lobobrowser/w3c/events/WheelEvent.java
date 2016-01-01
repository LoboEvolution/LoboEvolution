/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.w3c.events;

import org.w3c.dom.views.AbstractView;


/**
 * The public interface WheelEvent.
 */
public interface WheelEvent extends MouseEvent {
	// WheelEvent
	/** The Constant DOM_DELTA_PIXEL. */
	int DOM_DELTA_PIXEL = 0x00;

	/** The Constant DOM_DELTA_LINE. */
	int DOM_DELTA_LINE = 0x01;

	/** The Constant DOM_DELTA_PAGE. */
	int DOM_DELTA_PAGE = 0x02;

	/**
	 * Gets the delta x.
	 *
	 * @return the delta x
	 */
	int getDeltaX();

	/**
	 * Gets the delta y.
	 *
	 * @return the delta y
	 */
	int getDeltaY();

	/**
	 * Gets the delta z.
	 *
	 * @return the delta z
	 */
	int getDeltaZ();

	/**
	 * Gets the delta mode.
	 *
	 * @return the delta mode
	 */
	int getDeltaMode();

	/**
	 * Inits the wheel event.
	 *
	 * @param typeArg
	 *            the type arg
	 * @param canBubbleArg
	 *            the can bubble arg
	 * @param cancelableArg
	 *            the cancelable arg
	 * @param viewArg
	 *            the view arg
	 * @param detailArg
	 *            the detail arg
	 * @param screenXArg
	 *            the screen x arg
	 * @param screenYArg
	 *            the screen y arg
	 * @param clientXArg
	 *            the client x arg
	 * @param clientYArg
	 *            the client y arg
	 * @param buttonArg
	 *            the button arg
	 * @param relatedTargetArg
	 *            the related target arg
	 * @param modifiersListArg
	 *            the modifiers list arg
	 * @param deltaXArg
	 *            the delta x arg
	 * @param deltaYArg
	 *            the delta y arg
	 * @param deltaZArg
	 *            the delta z arg
	 * @param deltaMode
	 *            the delta mode
	 */
	void initWheelEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, AbstractView viewArg,
			int detailArg, int screenXArg, int screenYArg, int clientXArg, int clientYArg, short buttonArg,
			EventTarget relatedTargetArg, String modifiersListArg, int deltaXArg, int deltaYArg, int deltaZArg,
			int deltaMode);
}
