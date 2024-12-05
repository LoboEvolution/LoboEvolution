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

import lombok.*;
import org.loboevolution.events.ProgressEvent;
import org.mozilla.javascript.NativeObject;

/**
 * ProgressEventImpl class.
 */
@Getter
@Setter
@NoArgsConstructor
public class ProgressEventImpl extends EventImpl implements ProgressEvent {

    private Double loaded = 0d;
    private Double total = 0d;

    /**
     * <p>Constructor for EventImpl.</p>
     *
     * @param params event constructor parameters
     */
    public ProgressEventImpl(Object[] params) {
        setParams(params);
        if (params != null && params.length > 0) {
            if (params.length > 1) {
                if (params[1] != null && params[1] instanceof NativeObject obj) {
                    this.loaded = (Double) obj.get("loaded");
                    this.total = (Double) obj.get("total");
                }
            }
        }
    }

    @Override
    public boolean getLengthComputable() {
        return (total > 0);
    }

    @Override
    public String toString() {
        return "[object ProgressEvent]";
    }
}
