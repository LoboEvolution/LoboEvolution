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
import lombok.Setter;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.events.PointerEvent;
import org.mozilla.javascript.NativeObject;

@NoArgsConstructor
@Getter
@Setter
public class PointerEventImpl extends MouseEventImpl implements PointerEvent {

    private Double pointerId = 0d;
    private Double width = 1d;
    private Double height = 1d;
    private Double pressure = 0d;
    private Double altitudeAngle = 1.5707963267948966;
    private Double azimuthAngle = 0d;
    private Boolean isPrimary = false;
    private String pointerType;
    private Integer tiltX = 0;
    private Integer tiltY = 0;

    /**
     * <p>Constructor for PointerEventImpl.</p>
     *
     * @param params event constructor parameters
     */
    public PointerEventImpl(Object[] params) {
        try {
            setParams(params);
        } catch (DOMException e) {
            throw new RuntimeException("Failed to initialize Event", e);
        }

        if (params.length > 1) {
            if (params[1] != null && params[1] instanceof NativeObject obj) {
                this.pointerId = (Double) obj.get("pointerId");
                this.pointerType = (String) obj.get("pointerType");
                this.tiltX = obj.get("tiltX") != null ? (Integer)obj.get("tiltX") : 0;
                this.tiltY = obj.get("tiltY") != null ? (Integer)obj.get("tiltY") : 0;
            }
        }
    }

    @Override
    public float getTangentialPressure() {
        return 0;
    }

    @Override
    public int getTwist() {
        return 0;
    }

    @Override
    public String toString() {
        return "[object PointerEvent]";
    }
}
