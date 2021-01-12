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

import org.loboevolution.common.Strings;
import org.w3c.dom.DOMException;
import org.w3c.dom.events.Event;

/**
 * <p>EventFactory class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class EventFactory {

	/**
	 * <p>createEvent.</p>
	 *
	 * @param eventType a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.events.Event} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public static Event createEvent(String eventType) throws DOMException {
		Event theEvent = null;
		String event = Strings.isNotBlank(eventType) ? eventType.toLowerCase() : "";
		switch (event) {
		case "mouseevent":
			theEvent = new MouseEventImpl();
			break;
		case "uievent":
			theEvent = new UIEventImpl();
			break;
		case "event":
		default:
			theEvent = new EventImpl();
			break;
		}
		return theEvent;
	}
}
