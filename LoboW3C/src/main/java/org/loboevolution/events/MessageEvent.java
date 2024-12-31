/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.events;

import org.loboevolution.js.MessageEventSource;
import org.loboevolution.js.MessagePort;

import java.util.List;

/**
 * The MessageEvent interface represents a message received by a target object.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MessageEvent">MessageEvent - MDN</a>
 * @see <a href="https://html.spec.whatwg.org/multipage/comms.html#the-messageevent-interface"># the-messageevent-interface</a>
 */
public interface MessageEvent extends Event {

    /**
     * The data read-only property of the MessageEvent interface represents the data sent by the message emitter.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MessageEvent/data">MessageEvent.data - MDN</a>
     * @see <a href="https://html.spec.whatwg.org/multipage/#dom-messageevent-data">MessageEvent: data - HTML Living Standard</a>
     */
    Object getData();

    /**
     * The lastEventId read-only property of the MessageEvent interface is a DOMString representing a unique ID for the event.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MessageEvent/lastEventId">MessageEvent.lastEventId - MDN</a>
     * @see <a href="https://html.spec.whatwg.org/multipage/#dom-messageevent-lasteventid">MessageEvent: lastEventId - HTML Living Standard</a>
     */
    String getLastEventId();

    /**
     * The origin read-only property of the MessageEvent interface is a USVString representing the origin of the message emitter.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MessageEvent/origin">MessageEvent.origin - MDN</a>
     * @see <a href="https://html.spec.whatwg.org/multipage/#dom-messageevent-origin">MessageEvent: origin - HTML Living Standard</a>
     */
    String getOrigin();

    /**
     * The ports read-only property of the MessageEvent interface is an array of MessagePort objects representing the ports associated with the channel the message is being sent through (where appropriate, e.g. in channel messaging or when sending a message to a shared worker).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MessageEvent/ports">MessageEvent.ports - MDN</a>
     * @see <a href="https://html.spec.whatwg.org/multipage/#dom-messageevent-ports">ports - HTML Living Standard</a>
     */
    List<MessagePort> getPorts();

    /**
     * The source read-only property of the MessageEvent interface is a MessageEventSource (which can be a WindowProxy, MessagePort, or ServiceWorker object) representing the message emitter.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MessageEvent/source">MessageEvent.source - MDN</a>
     * @see <a href="https://html.spec.whatwg.org/multipage/#dom-messageevent-source"> MessageEvent: source - HTML Living Standard</a>
     */
    MessageEventSource getSource();

    void initMessageEvent(String type, Boolean bubbles, boolean cancelable,
                          Object data, String origin, String lastEventId,
                          MessageEventSource source, List<MessagePort> ports);


    void initMessageEvent(String type, Boolean bubbles, boolean cancelable,
                          Object data, String origin, String lastEventId,
                          MessageEventSource source);

    void initMessageEvent(String type, Boolean bubbles, boolean cancelable,
                          Object data, String origin, String lastEventId);

    void initMessageEvent(String type, Boolean bubbles, boolean cancelable,
                          Object data, String origin);

    void initMessageEvent(String type, Boolean bubbles, boolean cancelable,
                          Object data);

    void initMessageEvent(String type, Boolean bubbles, boolean cancelable);

    void initMessageEvent(String type, Boolean bubbles);

    void initMessageEvent(String type);
}
