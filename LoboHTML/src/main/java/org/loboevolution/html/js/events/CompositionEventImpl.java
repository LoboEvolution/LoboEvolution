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
import org.loboevolution.events.CompositionEvent;
import org.loboevolution.js.Window;
import org.mozilla.javascript.NativeObject;

@NoArgsConstructor
@Getter
public class CompositionEventImpl extends UIEventImpl implements CompositionEvent {

    private String data;

    /**
     * <p>Constructor for CompositionEventImpl.</p>
     *
     * @param params event constructor parameters
     */
    public CompositionEventImpl(Object[] params) {
        setParams(params);
        if (params.length > 1) {
            if (params[1] != null && params[1] instanceof NativeObject obj) {
                this.data = (String) obj.get("data");
            }
        }
    }

    @Override
    public void initCompositionEvent(String type, Boolean bubbles, boolean cancelable, Window view, String data) {
        initCompositionEvent(type, bubbles, cancelable, view);
        this.data = data;
    }

    @Override
    public void initCompositionEvent(String type, Boolean bubbles, boolean cancelable, Window view) {
        initUIEvent(type, bubbles, cancelable, view);
    }

    @Override
    public void initCompositionEvent(String type, Boolean bubbles, boolean cancelable) {
        initUIEvent(type, bubbles, cancelable);
    }

    @Override
    public void initCompositionEvent(String type, Boolean bubbles) {
        initUIEvent(type, bubbles);
    }

    @Override
    public void initCompositionEvent(String type) {
        initUIEvent(type);
    }

    @Override
    public String toString() {
        return "[object CompositionEvent]";
    }
}
