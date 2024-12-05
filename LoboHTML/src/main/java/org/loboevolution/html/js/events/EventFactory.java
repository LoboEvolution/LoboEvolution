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
 */
public class EventFactory {

	/**
	 * <p>createEvent.</p>
	 *
	 * @param eventType a {@link java.lang.String} object.
	 * @return a {@link Event} object.
	 * @throws DOMException if any.
	 */
	public static Event createEvent(final String eventType) throws DOMException {
		Event theEvent;
		final String event = Strings.isNotBlank(eventType) ? eventType.toLowerCase() : "";
        theEvent = switch (event) {
            case "mouseevent" -> new MouseEventImpl();
			case "uievents", "uievent" -> new UIEventImpl();
            case "inputvent" -> new InputEventImpl();
			case "submitevent" -> new SubmitEventImpl();
			case "animationevent" -> new AnimationEventImpl();
			case "htmlevents", "event", "events", "domcontentloaded" -> new EventImpl();
            case "keyboardevent"  -> new KeyboardEventImpl();
			case "messageevent"  -> new MessageEventImpl();
			case "mutationevent"  -> new MutationEventImpl();
			case "customevent"  -> new CustomEventImpl();
			case "closeevent"  -> new CloseEventImpl();
			case "compositionevent"  -> new CompositionEventImpl();
			case "popstateevent" -> new PopStateEventImpl();
			case "focusevent" -> new FocusEventImpl();
			case "wheelevent" -> new WheelEventImpl();
			case "blobevent" -> new BlobEventImpl();
			case "dragevent" -> new DragEventImpl();
			case "gamepadevent" -> new GamepadEventimpl();
			case "transitionevent" -> new TransitionEventImpl();
			case "beforeunloadevent" -> new BeforeUnloadEventImpl();
			case "hashchangeevent" -> new HashChangeEventImpl();
            default -> throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Failed to execute 'createEvent' on 'Document': " +
					"The provided event type " + eventType + " is invalid.");
        };
		return theEvent;
	}
}
