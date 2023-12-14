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
package org.loboevolution.html.js.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.events.EventTarget;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.type.EventPhase;

import java.util.List;

/**
 * <p>EventImpl class.</p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EventImpl extends AbstractScriptableDelegate implements Event {

    private final long mTimeStamp = System.currentTimeMillis();
    private EventTarget target;
    private EventTarget currentTarget;
    private short eventPhase;
    private int mSeekTo;
    private String type;

    private boolean bubbles;
    private boolean cancelBubble;
    private boolean cancelable;
    private boolean composed;
    private boolean defaultPrevented;
    private boolean initialized;
    private boolean stopPropagation;
    private boolean preventDefault;


    /**
     * <p>Constructor for EventImpl.</p>
     *
     * @param type a {@link java.lang.String} object.
     * @param bubbles a boolean.
     * @param cancelable a boolean.
     */
    public EventImpl(final String type, final boolean bubbles, final boolean cancelable) {
        this.type = type;
        this.bubbles = bubbles;
        this.cancelable = cancelable;
        this.initialized = true;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void initEvent(final String type, final boolean bubbles, final boolean cancelable) {
        this.type = type;
        this.bubbles = bubbles;
        this.cancelable = cancelable;
        this.initialized = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initEvent(final String type, final boolean bubbles) {
        initEvent(type, bubbles, false);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initEvent(final String type) {
        initEvent(type, false, false);
    }

    @Override
    public boolean isIsTrusted() {
        return false;
    }

    @Override
    public boolean isReturnValue() {
        return false;
    }

    @Override
    public void setReturnValue(final boolean returnValue) {
		// TODO Auto-generated method stub
    }

    @Override
    public EventTarget getSrcElement() {
        return null;
    }

    @Override
    public double getTimeStamp() {
        return mTimeStamp;
    }

    @Override
    public List<EventTarget> composedPath() {
        return null;
    }

    @Override
    public void preventDefault() {
        preventDefault = true;
    }

    @Override
    public void stopImmediatePropagation() {
        // TODO Auto-generated method stub
    }

    @Override
    public void stopPropagation() {
        stopPropagation = true;
    }

	public boolean isPropogationStopped() {
		return stopPropagation;
	}

    @Override
    public String toString() {
        return "[object Event]";
    }
}
