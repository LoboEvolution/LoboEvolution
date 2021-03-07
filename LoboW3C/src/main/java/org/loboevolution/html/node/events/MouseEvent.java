package org.loboevolution.html.node.events;

import org.loboevolution.html.node.js.Window;

/**
 * Events that occur due to the user interacting with a pointing device (such as
 * a mouse). Common events using this interface include click, dblclick,
 * mouseup, mousedown.
 */
public interface MouseEvent extends UIEvent {

	boolean isAltKey();

	int getButton();

	int getButtons();

	double getClientX();

	double getClientY();

	boolean isCtrlKey();

	boolean isMetaKey();

	double getMovementX();

	double getMovementY();

	double getOffsetX();

	double getOffsetY();

	double getPageX();

	double getPageY();

	EventTarget getRelatedTarget();

	double getScreenX();

	double getScreenY();

	boolean isShiftKey();

	double getX();

	double getY();

	boolean getModifierState(String keyArg);

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
