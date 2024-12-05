/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of che, to any person obtaining a copy
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
import org.loboevolution.events.EventTarget;
import org.loboevolution.events.MouseEvent;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.js.Window;
import org.mozilla.javascript.NativeObject;

/**
 * MouseEventImpl class.
 */
@Getter
@NoArgsConstructor
public class MouseEventImpl extends UIEventImpl implements MouseEvent {

    private Long buttons = 0L;
    private Long button = 0L;
    private Long x = 0L;
    private Long y = 0L;
    private Long screenX = 0L;
    private Long screenY = 0L;
    private Long clientX = 0L;
    private Long clientY = 0L;
    private Long offsetX = 0L;
    private Long offsetY = 0L;
    private Long pageX = 0L;
    private Long pageY = 0L;
    private Long movementX = 0L;
    private Long movementY = 0L;
    private Boolean ctrlKey = false;
    private Boolean shiftKey = false;
    private Boolean altKey = false;
    private Boolean metaKey = false;

    private EventTarget relatedTarget;
    private java.awt.event.MouseEvent mouseEvent;

    public MouseEventImpl(java.awt.event.MouseEvent mouseEvent) {
        super(mouseEvent);
        this.mouseEvent = mouseEvent;
    }

    public MouseEventImpl(final Object[] params) {
        setParams(params);
        if (params.length > 1) {
            if (params[1] != null && params[1] instanceof NativeObject obj) {
                this.x = getLongVal(obj, "x");
                this.y = getLongVal(obj, "y");
                this.screenX = getLongVal(obj, "screenX");
                this.screenY = getLongVal(obj, "screenY");
                this.clientX = getLongVal(obj, "clientX");
                this.clientY = getLongVal(obj, "clientY");
                this.offsetX = getLongVal(obj, "offsetX");
                this.offsetY = getLongVal(obj, "offsetY");
                this.pageX = getLongVal(obj, "pageX");
                this.pageY = getLongVal(obj, "pageY");
                this.movementX = getLongVal(obj, "movementX");
                this.movementY = getLongVal(obj, "movementY");
                this.button = getLongVal(obj, "button");
                this.buttons = getLongVal(obj, "buttons");
                this.ctrlKey = getBoolVal(obj, "ctrlKey");
                this.shiftKey = getBoolVal(obj, "shiftKey");
                this.altKey = getBoolVal(obj, "altKey");
                this.metaKey = getBoolVal(obj, "metaKey");
            }
        }
    }

    @Override
    public void initMouseEvent(String type, Boolean bubbles, Boolean cancelable, Window view,
                               Double detail, Double screenX, Double screenY, Double clientX, Double clientY,
                               Boolean ctrlKey, Boolean altKey, Boolean shiftKey, Boolean metaKey,
                               Double button, EventTarget relatedTarget) {
        initMouseEvent(type, bubbles, cancelable, view, detail, screenX, screenY, clientX, clientY,
                ctrlKey, altKey, shiftKey, metaKey, button);
        this.relatedTarget = relatedTarget;
        this.setTarget((HTMLElementImpl) relatedTarget);
    }

    @Override
    public void initMouseEvent(String type, Boolean bubbles, Boolean cancelable, Window view,
                               Double detail, Double screenX, Double screenY, Double clientX, Double clientY,
                               Boolean ctrlKey, Boolean altKey, Boolean shiftKey, Boolean metaKey, Double button) {
        initMouseEvent(type, bubbles, cancelable, view, detail, screenX, screenY, clientX, clientY,
                ctrlKey, altKey, shiftKey, metaKey);
        this.button = button != null ? button.longValue() : 0;
    }

    @Override
    public void initMouseEvent(String type, Boolean bubbles, Boolean cancelable, Window view,
                               Double detail, Double screenX, Double screenY, Double clientX, Double clientY, Boolean ctrlKey, Boolean altKey, Boolean shiftKey, Boolean metaKey) {
        initMouseEvent(type, bubbles, cancelable, view, detail, screenX, screenY, clientX, clientY,
                ctrlKey, altKey, shiftKey);
        this.metaKey = metaKey;
    }

    @Override
    public void initMouseEvent(String type, Boolean bubbles, Boolean cancelable, Window view,
                               Double detail, Double screenX, Double screenY, Double clientX, Double clientY,
                               Boolean ctrlKey, Boolean altKey, Boolean shiftKey) {
        initMouseEvent(type, bubbles, cancelable, view, detail, screenX, screenY, clientX, clientY, ctrlKey, altKey);
        this.shiftKey = shiftKey;
    }

    @Override
    public void initMouseEvent(String type, Boolean bubbles, Boolean cancelable, Window view,
                               Double detail, Double screenX, Double screenY, Double clientX, Double clientY,
                               Boolean ctrlKey, Boolean altKey) {
        initMouseEvent(type, bubbles, cancelable, view, detail, screenX, screenY, clientX, clientY, ctrlKey);
        this.altKey = altKey;
    }

    @Override
    public void initMouseEvent(String type, Boolean bubbles, Boolean cancelable, Window view,
                               Double detail, Double screenX, Double screenY, Double clientX, Double clientY, Boolean ctrlKey) {
        initMouseEvent(type, bubbles, cancelable, view, detail, screenX, screenY, clientX, clientY);
        this.ctrlKey = ctrlKey;
    }

    @Override
    public void initMouseEvent(String type, Boolean bubbles, Boolean cancelable, Window view,
                               Double detail, Double screenX, Double screenY, Double clientX, Double clientY) {
        initMouseEvent(type, bubbles, cancelable, view, detail, screenX, screenY, clientX);
        this.clientY = clientY != null ? clientY.longValue() : 0;
    }

    @Override
    public void initMouseEvent(String type, Boolean bubbles, Boolean cancelable, Window view,
                               Double detail, Double screenX, Double screenY, Double clientX) {
        initMouseEvent(type, bubbles, cancelable, view, detail, screenX, screenY);
        this.clientX = clientX != null ? clientX.longValue() : 0;
    }

    @Override
    public void initMouseEvent(String type, Boolean bubbles, Boolean cancelable, Window view,
                               Double detail, Double screenX, Double screenY) {
        initMouseEvent(type, bubbles, cancelable, view, detail, screenX);
        this.screenY = screenY != null ? screenY.longValue() : 0;
    }

    @Override
    public void initMouseEvent(String type, Boolean bubbles, Boolean cancelable, Window view, Double detail, Double screenX) {
        initUIEvent(type, bubbles, cancelable, view, detail);
        this.screenX = screenX != null ? screenX.longValue() : 0;
    }

    @Override
    public void initMouseEvent(String type, Boolean bubbles, Boolean cancelable, Window view, Double detail) {
        initUIEvent(type, bubbles, cancelable, view, detail);
    }

    @Override
    public void initMouseEvent(String type, Boolean bubbles, Boolean cancelable, Window view) {
        initUIEvent(type, bubbles, cancelable, view);
    }

    @Override
    public void initMouseEvent(String type, Boolean bubbles, Boolean cancelable) {
        super.initUIEvent(type, bubbles, cancelable);
    }

    @Override
    public void initMouseEvent(String type, Boolean bubbles) {
        super.initUIEvent(type, bubbles);
    }

    @Override
    public void initMouseEvent(String type) {
        super.initUIEvent(type);
    }

    @Override
    public EventTarget relatedTarget() {
        return relatedTarget;
    }

    @Override
    public Boolean getModifierState(String key) {
        return false;
    }

    @Override
    public Double getWhich() {
        if (mouseEvent != null) {
            return (double) (mouseEvent.getButton() - 1);
        }
        return (double) (buttons > 0 ? buttons - 1 : 1);
    }

    @Override
    public Long getButton() {
        if (mouseEvent != null) {
            return (long) mouseEvent.getButton();
        }
        return button;
    }

    @Override
    public String toString() {
        return "[object MouseEvent]";
    }
}
