/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.ua;

/**
 * A navigator event object.
 */
public class NavigatorEvent extends java.util.EventObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The clientlet frame. */
	private final NavigatorFrame clientletFrame;

	/** The event type. */
	private final NavigatorEventType eventType;

	/**
	 * Instantiates a new navigator event.
	 *
	 * @param source
	 *            the source
	 * @param eventType
	 *            the event type
	 * @param clientletFrame
	 *            the clientlet frame
	 */
	public NavigatorEvent(Object source, final NavigatorEventType eventType, final NavigatorFrame clientletFrame) {
		super(source);
		this.clientletFrame = clientletFrame;
		this.eventType = eventType;
	}

	/**
	 * Gets the navigator frame.
	 *
	 * @return the navigator frame
	 */
	public NavigatorFrame getNavigatorFrame() {
		return clientletFrame;
	}

	/**
	 * Gets the event type.
	 *
	 * @return the event type
	 */
	public NavigatorEventType getEventType() {
		return eventType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.EventObject#toString()
	 */
	@Override
	public String toString() {
		return "NavigatorWindowEvent[type=" + this.getEventType() + "]";
	}
}
