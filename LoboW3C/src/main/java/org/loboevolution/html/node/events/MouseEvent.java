/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.node.events;

import org.loboevolution.html.node.js.Window;

/**
 * Events that occur due to the user interacting with a pointing device (such as
 * a mouse). Common events using this interface include click, dblclick,
 * mouseup, mousedown.
 *
 *
 *
 */
public interface MouseEvent extends UIEvent {

	/**
	 * <p>isAltKey.</p>
	 *
	 * @return a boolean.
	 */
	boolean isAltKey();

	/**
	 * <p>getButton.</p>
	 *
	 * @return a int.
	 */
	int getButton();

	/**
	 * <p>getButtons.</p>
	 *
	 * @return a int.
	 */
	int getButtons();

	/**
	 * <p>getClientX.</p>
	 *
	 * @return a double.
	 */
	double getClientX();

	/**
	 * <p>getClientY.</p>
	 *
	 * @return a double.
	 */
	double getClientY();

	/**
	 * <p>isCtrlKey.</p>
	 *
	 * @return a boolean.
	 */
	boolean isCtrlKey();

	/**
	 * <p>isMetaKey.</p>
	 *
	 * @return a boolean.
	 */
	boolean isMetaKey();

	/**
	 * <p>getMovementX.</p>
	 *
	 * @return a double.
	 */
	double getMovementX();

	/**
	 * <p>getMovementY.</p>
	 *
	 * @return a double.
	 */
	double getMovementY();

	/**
	 * <p>getOffsetX.</p>
	 *
	 * @return a double.
	 */
	double getOffsetX();

	/**
	 * <p>getOffsetY.</p>
	 *
	 * @return a double.
	 */
	double getOffsetY();

	/**
	 * <p>getPageX.</p>
	 *
	 * @return a double.
	 */
	double getPageX();

	/**
	 * <p>getPageY.</p>
	 *
	 * @return a double.
	 */
	double getPageY();

	/**
	 * <p>getRelatedTarget.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.events.EventTarget} object.
	 */
	EventTarget getRelatedTarget();

	/**
	 * <p>getScreenX.</p>
	 *
	 * @return a double.
	 */
	double getScreenX();

	/**
	 * <p>getScreenY.</p>
	 *
	 * @return a double.
	 */
	double getScreenY();

	/**
	 * <p>isShiftKey.</p>
	 *
	 * @return a boolean.
	 */
	boolean isShiftKey();

	/**
	 * <p>getX.</p>
	 *
	 * @return a double.
	 */
	double getX();

	/**
	 * <p>getY.</p>
	 *
	 * @return a double.
	 */
	double getY();

	/**
	 * <p>getModifierState.</p>
	 *
	 * @param keyArg a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	boolean getModifierState(String keyArg);

	/**
	 * <p>initMouseEvent.</p>
	 *
	 * @param typeArg a {@link java.lang.String} object.
	 * @param canBubbleArg a boolean.
	 * @param cancelableArg a boolean.
	 * @param viewArg a {@link org.loboevolution.html.node.js.Window} object.
	 * @param detailArg a double.
	 * @param screenXArg a double.
	 * @param screenYArg a double.
	 * @param clientXArg a double.
	 * @param clientYArg a double.
	 * @param ctrlKeyArg a boolean.
	 * @param altKeyArg a boolean.
	 * @param shiftKeyArg a boolean.
	 * @param metaKeyArg a boolean.
	 * @param buttonArg a int.
	 * @param relatedTargetArg a {@link org.loboevolution.html.node.events.EventTarget} object.
	 */
	void initMouseEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, Window viewArg, double detailArg,
			double screenXArg, double screenYArg, double clientXArg, double clientYArg, boolean ctrlKeyArg,
			boolean altKeyArg, boolean shiftKeyArg, boolean metaKeyArg, int buttonArg, EventTarget relatedTargetArg);

	interface MouseEventInit extends EventModifierInit {

		int getButton();

		void setButton(int button);

		int getButtons();

		void setButtons(int buttons);

		double getClientX();

		void setClientX(double clientX);

		double getClientY();

		void setClientY(double clientY);

		double getMovementX();

		void setMovementX(double movementX);

		double getMovementY();

		void setMovementY(double movementY);

		EventTarget getRelatedTarget();

		void setRelatedTarget(EventTarget relatedTarget);

		double getScreenX();

		void setScreenX(double screenX);

		double getScreenY();

		void setScreenY(double screenY);

	}
}
