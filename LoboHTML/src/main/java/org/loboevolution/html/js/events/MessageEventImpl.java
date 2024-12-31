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

package org.loboevolution.html.js.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.loboevolution.events.MessageEvent;
import org.loboevolution.js.MessageEventSource;
import org.loboevolution.js.MessagePort;

import java.util.List;

@NoArgsConstructor
@Getter
public class MessageEventImpl extends EventImpl implements MessageEvent {

    private Object data;
    private String origin;
    private String lastEventId;
    private MessageEventSource source;
    private List<MessagePort> ports;


    /**
     * <p>Constructor for MessageEventImpl.</p>
     *
     * @param params event constructor parameters
     */
    public MessageEventImpl(Object[] params) {
        setParams(params);
    }

    @Override
    public void initMessageEvent(String type, Boolean bubbles, boolean cancelable, Object data, String origin,
                                 String lastEventId, MessageEventSource source, List<MessagePort> ports) {
        initMessageEvent(type, bubbles, cancelable, data, origin, lastEventId, source);
        this.ports = ports;
    }

    @Override
    public void initMessageEvent(String type, Boolean bubbles, boolean cancelable, Object data, String origin,
                                 String lastEventId, MessageEventSource source) {
        initMessageEvent(type, bubbles, cancelable, data, origin, lastEventId);
        this.source = source;
    }

    @Override
    public void initMessageEvent(String type, Boolean bubbles, boolean cancelable, Object data, String origin,
                                 String lastEventId) {
        initMessageEvent(type, bubbles, cancelable, data, origin);
        this.lastEventId = lastEventId;
    }

    @Override
    public void initMessageEvent(String type, Boolean bubbles, boolean cancelable, Object data, String origin) {
        initMessageEvent(type, bubbles, cancelable, data);
        this.origin = origin;
    }

    @Override
    public void initMessageEvent(String type, Boolean bubbles, boolean cancelable, Object data) {
        super.initEvent(type, bubbles, cancelable);
        this.data = data;
    }

    @Override
    public void initMessageEvent(String type, Boolean bubbles, boolean cancelable) {
        super.initEvent(type, bubbles, cancelable);
    }

    @Override
    public void initMessageEvent(String type, Boolean bubbles) {
        super.initEvent(type, bubbles);
    }

    @Override
    public void initMessageEvent(String type) {
        super.initEvent(type);
    }

    @Override
    public String toString() {
        return "[object MessageEvent]";
    }
}
