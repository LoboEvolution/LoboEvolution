/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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

/**
 * The public interface Event.
 */
public interface Event {
	// Event
	/** The Constant CAPTURING_PHASE. */
	short CAPTURING_PHASE = 1;

	/** The Constant AT_TARGET. */
	short AT_TARGET = 2;

	/** The Constant BUBBLING_PHASE. */
	short BUBBLING_PHASE = 3;

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	String getType();

	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	EventTarget getTarget();

	/**
	 * Gets the current target.
	 *
	 * @return the current target
	 */
	EventTarget getCurrentTarget();

	/**
	 * Gets the event phase.
	 *
	 * @return the event phase
	 */
	short getEventPhase();

	/**
	 * Gets the bubbles.
	 *
	 * @return the bubbles
	 */
	boolean getBubbles();

	/**
	 * Gets the cancelable.
	 *
	 * @return the cancelable
	 */
	boolean getCancelable();

	/**
	 * Gets the time stamp.
	 *
	 * @return the time stamp
	 */
	long getTimeStamp();

	/**
	 * Stop propagation.
	 */
	void stopPropagation();

	/**
	 * Prevent default.
	 */
	void preventDefault();

	/**
	 * Inits the event.
	 *
	 * @param eventTypeArg
	 *            the event type arg
	 * @param canBubbleArg
	 *            the can bubble arg
	 * @param cancelableArg
	 *            the cancelable arg
	 */
	void initEvent(String eventTypeArg, boolean canBubbleArg, boolean cancelableArg);

	/**
	 * Inits the event ns.
	 *
	 * @param namespaceURIArg
	 *            the namespace uri arg
	 * @param eventTypeArg
	 *            the event type arg
	 * @param canBubbleArg
	 *            the can bubble arg
	 * @param cancelableArg
	 *            the cancelable arg
	 */
	void initEventNS(String namespaceURIArg, String eventTypeArg, boolean canBubbleArg, boolean cancelableArg);

	/**
	 * Stop immediate propagation.
	 */
	void stopImmediatePropagation();

	/**
	 * Gets the default prevented.
	 *
	 * @return the default prevented
	 */
	boolean getDefaultPrevented();

	/**
	 * Gets the trusted.
	 *
	 * @return the trusted
	 */
	boolean getTrusted();
}
