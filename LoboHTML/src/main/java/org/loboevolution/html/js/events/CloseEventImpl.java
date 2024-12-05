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

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.loboevolution.events.CloseEvent;
import org.mozilla.javascript.NativeObject;

@NoArgsConstructor
@Getter
public class CloseEventImpl extends EventImpl implements CloseEvent {

    private String reason;
    private Double code = 0d;
    private Boolean wasClean = false;

    /**
     * <p>Constructor for CloseEventImpl.</p>
     *
     * @param params event constructor parameters
     */
    public CloseEventImpl(Object[] params) {
        setParams(params);
        if (params != null && params.length > 0) {
            if (params.length > 1) {
                if (params[1] != null && params[1] instanceof NativeObject obj) {
                    this.wasClean = obj.get("wasClean") != null;
                    this.reason = (String) obj.get("reason");
                    this.code = (Double) obj.get("code");
                }
            }
        }
    }

    @Override
    public String toString() {
        return "[object CloseEvent]";
    }
}
