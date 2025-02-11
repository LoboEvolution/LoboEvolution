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

import lombok.NoArgsConstructor;
import org.loboevolution.events.TouchEvent;
import org.loboevolution.events.TouchList;

@NoArgsConstructor
public class TouchEventImpl extends UIEventImpl implements TouchEvent {

    /**
     * <p>Constructor for TouchEventImpl.</p>
     *
     * @param params event constructor parameters
     */
    public TouchEventImpl(Object[] params) {
        setParams(params);
    }

    @Override
    public TouchList getTouches() {
        return null;
    }

    @Override
    public TouchList getTargetTouches() {
        return null;
    }

    @Override
    public TouchList getChangedTouches() {
        return null;
    }

    @Override
    public boolean getAltKey() {
        return false;
    }

    @Override
    public boolean getMetaKey() {
        return false;
    }

    @Override
    public boolean getCtrlKey() {
        return false;
    }

    @Override
    public boolean getShiftKey() {
        return false;
    }

    @Override
    public boolean getModifierState(String keyArg) {
        return false;
    }

    @Override
    public String toString() {
        return "[object TouchEvent]";
    }
}
