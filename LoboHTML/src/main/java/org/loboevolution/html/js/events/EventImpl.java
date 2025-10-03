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

import lombok.*;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.events.Event;
import org.loboevolution.events.EventTarget;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.mozilla.javascript.NativeObject;

import java.awt.event.InputEvent;
import java.util.List;

/**
 * <p>EventImpl class.</p>
 */
@Getter
@Setter
@NoArgsConstructor
public class EventImpl extends AbstractScriptableDelegate implements Event {

    private final long mTimeStamp = System.currentTimeMillis();
    private HTMLElementImpl target;
    private HTMLElementImpl currentTarget;
    private short eventPhase;
    private int mSeekTo;
    private Object type;

    private Boolean bubbles = false;
    private Boolean cancelable = false;
    private Boolean composed = false;
    private Boolean stopPropagation = false;
    private Boolean trusted = false;
    private Boolean defaultPrevented = false;
    private InputEvent inputEvent;


    /**
     * <p>Constructor for EventImpl.</p>
     */
    public EventImpl(InputEvent inputEvent){
        this.inputEvent = inputEvent;
    }

    /**
     * <p>Constructor for EventImpl.</p>
     */
    public EventImpl(final Object[] params) {
        try {
            setParams(params);
        } catch (DOMException e) {
            throw new RuntimeException("Failed to initialize Event", e);
        }
    }

    protected void setParams(final Object[] params) throws DOMException {
        if (params != null && params.length > 0) {
            this.type = params[0];
            if (params.length > 1) {
                if (params[1] != null && params[1] instanceof NativeObject obj) {
                    this.bubbles = obj.get("bubbles") != null;
                    this.cancelable = obj.get("cancelable") != null ? (Boolean) obj.get("cancelable") : false;
                    this.composed = obj.get("composed") != null ? (Boolean) obj.get("composed") : false;
                }
            }
        } else {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Failed : 1 argument required, but only 0 present.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initEvent(final String type, final Boolean bubbles, final boolean cancelable) {
        this.type = type;
        this.bubbles = bubbles;
        this.cancelable = cancelable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initEvent(final String type, final Boolean bubbles) {
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
    public void preventDefault() {
        if (cancelable) {
            defaultPrevented = true;
        }
    }

    @Override
    public Boolean getDefaultPrevented() {
        return cancelable && defaultPrevented;
    }

    @Override
    public Boolean getReturnValue() {
        return !defaultPrevented;
    }

    @Override
    public EventTarget getSrcElement() {
        return null;
    }

    @Override
    public double getTimeStamp() {
        return System.currentTimeMillis();
    }

    @Override
    public List<EventTarget> getComposedPath() {
        return List.of();
    }

    @Override
    public void getStopImmediatePropagation() {
        getStopPropagation();
    }

    @Override
    public void getStopPropagation() {
        stopPropagation = true;
    }

    public boolean isPropogationStopped() {
        return stopPropagation;
    }

    public void setReturnValue(final Object newValue) {
        if (newValue instanceof Boolean) {
            if (cancelable) {
                defaultPrevented = (Boolean) newValue;
            }
        }
    }

    protected String getStringVal(NativeObject obj, String key) {
        return obj.get(key) != null ? (String) obj.get(key) : "";
    }

    protected Double getDoubleVal(NativeObject obj, String key) {
        return obj.get(key) != null ? ((Double) obj.get(key)) : 0d;
    }

    protected Long getLongVal(NativeObject obj, String key) {
        return obj.get(key) != null ? ((Double) obj.get(key)).longValue() : 0;
    }

    protected Boolean getBoolVal(NativeObject obj, String key) {
        if (obj.get(key) instanceof String) {
            return "true".equals(obj.get(key)) || "1.0".equals(obj.get(key));
        }

        if (obj.get(key) instanceof Double) {
            return ((Double) obj.get(key) == 1d);
        }

        if (obj.get(key) instanceof Boolean) {
            return (Boolean) obj.get(key);
        }

        return obj.get(key) != null;
    }

    @Override
    public String toString() {
        return "[object Event]";
    }
}
