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
package org.lobobrowser.w3c.events;

import org.lobobrowser.w3c.html.HTMLElement;
import org.w3c.dom.views.AbstractView;

/**
 * The public interface MouseEvent.
 */
public interface MouseEvent extends UIEvent {
	// MouseEvent
	/**
	 * Gets the screen x.
	 *
	 * @return the screen x
	 */
	int getScreenX();

	/**
	 * Gets the screen y.
	 *
	 * @return the screen y
	 */
	int getScreenY();

	/**
	 * Gets the client x.
	 *
	 * @return the client x
	 */
	int getClientX();

	/**
	 * Gets the client y.
	 *
	 * @return the client y
	 */
	int getClientY();

	/**
	 * Gets the ctrl key.
	 *
	 * @return the ctrl key
	 */
	boolean getCtrlKey();

	/**
	 * Gets the shift key.
	 *
	 * @return the shift key
	 */
	boolean getShiftKey();

	/**
	 * Gets the alt key.
	 *
	 * @return the alt key
	 */
	boolean getAltKey();

	/**
	 * Gets the meta key.
	 *
	 * @return the meta key
	 */
	boolean getMetaKey();

	/**
	 * Gets the related target.
	 *
	 * @return the related target
	 */
	HTMLElement getRelatedTarget();

	/**
	 * Inits the mouse event.
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
	 * @param ctrlKeyArg
	 *            the ctrl key arg
	 * @param altKeyArg
	 *            the alt key arg
	 * @param shiftKeyArg
	 *            the shift key arg
	 * @param metaKeyArg
	 *            the meta key arg
	 * @param buttonArg
	 *            the button arg
	 * @param relatedTargetArg
	 *            the related target arg
	 */
	void initMouseEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, AbstractView viewArg,
			int detailArg, int screenXArg, int screenYArg, int clientXArg, int clientYArg, boolean ctrlKeyArg,
			boolean altKeyArg, boolean shiftKeyArg, boolean metaKeyArg, int buttonArg, EventTarget relatedTargetArg);

	/**
	 * Inits the mouse event ns.
	 *
	 * @param namespaceURIArg
	 *            the namespace uri arg
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
	 * @param ctrlKeyArg
	 *            the ctrl key arg
	 * @param altKeyArg
	 *            the alt key arg
	 * @param shiftKeyArg
	 *            the shift key arg
	 * @param metaKeyArg
	 *            the meta key arg
	 * @param buttonArg
	 *            the button arg
	 * @param relatedTargetArg
	 *            the related target arg
	 */
	void initMouseEventNS(String namespaceURIArg, String typeArg, boolean canBubbleArg, boolean cancelableArg,
			AbstractView viewArg, int detailArg, int screenXArg, int screenYArg, int clientXArg, int clientYArg,
			boolean ctrlKeyArg, boolean altKeyArg, boolean shiftKeyArg, boolean metaKeyArg, int buttonArg,
			EventTarget relatedTargetArg);

	/**
	 * Gets the modifier state.
	 *
	 * @param keyArg
	 *            the key arg
	 * @return the modifier state
	 */
	boolean getModifierState(String keyArg);

	// MouseEvent-43
	/**
	 * Gets the page x.
	 *
	 * @return the page x
	 */
	int getPageX();

	/**
	 * Gets the page y.
	 *
	 * @return the page y
	 */
	int getPageY();

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	int getX();

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	int getY();

	/**
	 * Gets the offset x.
	 *
	 * @return the offset x
	 */
	int getOffsetX();

	/**
	 * Gets the offset y.
	 *
	 * @return the offset y
	 */
	int getOffsetY();
}
