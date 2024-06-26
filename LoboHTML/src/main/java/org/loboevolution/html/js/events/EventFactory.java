/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
package org.loboevolution.html.js.events;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.Strings;
import org.loboevolution.events.Event;

/**
 * <p>EventFactory class.</p>
 *
 *
 *
 */
public class EventFactory {

	/**
	 * <p>createEvent.</p>
	 *
	 * @param eventType a {@link java.lang.String} object.
	 * @return a {@link Event} object.
	 * @throws DOMException if any.
	 */
	public static Event createEvent(final String eventType) {
		Event theEvent;
		final String event = Strings.isNotBlank(eventType) ? eventType.toLowerCase() : "";
        theEvent = switch (event) {
            case "mouseevent" -> new MouseEventImpl();
            case "uievent" -> new UIEventImpl();
            default -> new EventImpl();
        };
		return theEvent;
	}
}
