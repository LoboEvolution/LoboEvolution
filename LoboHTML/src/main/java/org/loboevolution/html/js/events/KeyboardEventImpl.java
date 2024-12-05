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
import lombok.Setter;
import org.loboevolution.events.KeyboardEvent;
import org.loboevolution.js.Window;
import org.mozilla.javascript.NativeObject;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

@NoArgsConstructor
@Getter
@Setter
public class KeyboardEventImpl extends UIEventImpl implements KeyboardEvent {

    private String key = "";
    private Double location = 0d;
    private Double charCode = 0d;
    private Boolean ctrlKey = false;
    private Boolean shiftKey = false;
    private Boolean altKey = false;
    private Boolean metaKey = false;
    private Boolean isComposing = false;
    private Boolean repeat = false;
    private String code = "";

    /**
     * <p>Constructor for KeyboardEventImpl.</p>
     *
     * @param params event constructor parameters
     */
    public KeyboardEventImpl(Object[] params) {
        setParams(params);
        if (params.length > 1) {
            if (params[1] != null && params[1] instanceof NativeObject obj) {
                setUIEventParams(obj);
                this.key = getStringVal(obj,"key");
                this.location = getDoubleVal(obj,"location");
                this.charCode = getDoubleVal(obj,"charCode");
                this.code = getStringVal(obj,"code");
                this.repeat = getBoolVal(obj, "repeat");
                this.isComposing = getBoolVal(obj, "isComposing");
                this.ctrlKey = getBoolVal(obj, "ctrlKey");
                this.shiftKey = getBoolVal(obj, "shiftKey");
                this.altKey = getBoolVal(obj, "altKey");
                this.metaKey = getBoolVal(obj, "metaKey");
            }
        }
    }

    @Override
    public Boolean getModifierState(String modifierState) {
        return null;
    }

    @Override
    public int getKeyCode() {
       /* InputEvent ie = this.inputEvent;
        if (ie instanceof KeyEvent ke) {
            return ke.getKeyCode();
        } else {
            return 0;
        }*/

        return 0;
    }

    @Override
    public void initKeyboardEvent(String type, Boolean bubbles, boolean cancelable, Window view, String key, Double location,
                                  boolean ctrlKey, boolean altKey, boolean shiftKey, boolean metaKey) {
        initKeyboardEvent(type, bubbles, cancelable, view, key, location, ctrlKey, altKey, shiftKey);
        this.metaKey = metaKey;
    }

    @Override
    public void initKeyboardEvent(String type, Boolean bubbles, boolean cancelable, Window view, String key, Double location,
                                  boolean ctrlKey, boolean altKey, boolean shiftKey) {
        initKeyboardEvent(type, bubbles, cancelable, view, key, location, ctrlKey, altKey);
        this.shiftKey = shiftKey;
    }

    @Override
    public void initKeyboardEvent(String type, Boolean bubbles, boolean cancelable, Window view, String key, Double location, boolean ctrlKey, boolean altKey) {
        initKeyboardEvent(type, bubbles, cancelable, view, key, location, ctrlKey);
        this.altKey = altKey;
    }

    @Override
    public void initKeyboardEvent(String type, Boolean bubbles, boolean cancelable, Window view, String key, Double location, boolean ctrlKey) {
        initKeyboardEvent(type, bubbles, cancelable, view, key, location);
        this.ctrlKey = ctrlKey;
    }

    @Override
    public void initKeyboardEvent(String type, Boolean bubbles, boolean cancelable, Window view, String key, Double location) {
        initKeyboardEvent(type, bubbles, cancelable, view, key);
        this.location = location;
    }

    @Override
    public void initKeyboardEvent(String type, Boolean bubbles, boolean cancelable, Window view, String key) {
        initKeyboardEvent(type, bubbles, cancelable, view);
        this.key = key;
    }

    @Override
    public void initKeyboardEvent(String type, Boolean bubbles, boolean cancelable, Window view) {
        initUIEvent(type, bubbles, cancelable, view);
    }

    @Override
    public void initKeyboardEvent(String type, Boolean bubbles, boolean cancelable) {
        initUIEvent(type, bubbles, cancelable);
    }

    @Override
    public void initKeyboardEvent(String type, Boolean bubbles) {
        initUIEvent(type, bubbles);
    }

    @Override
    public void initKeyboardEvent(String type) {
        initUIEvent(type);
    }

    @Override
    public String toString() {
        return "[object KeyboardEvent]";
    }
}
