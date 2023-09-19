/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.node.events;

import org.loboevolution.html.node.js.Window;

/**
 * Events that occur due to the user interacting with a pointing device (such as
 * a mouse). Common events using this interface include click, dblclick,
 * mouseup, mousedown.
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
