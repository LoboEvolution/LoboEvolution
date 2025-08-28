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
import org.loboevolution.common.Strings;
import org.loboevolution.events.InputEvent;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

/**
 * InputEventImpl class.
 */
@Getter
@Setter
@NoArgsConstructor
public class InputEventImpl extends UIEventImpl implements InputEvent {

    private String data = "";
    private String inputType = "";
    private Boolean isComposing = false;

    public InputEventImpl(final Object[] params) throws RuntimeException {
        try {
            setParams(params);
        } catch (DOMException e) {
            throw new RuntimeException("Failed to initialize Event", e);
        }

        if (params.length > 1) {
            if (params[1] != null) {
                NativeObject obj = (NativeObject) params[1];
                Object isComposing = obj.get("isComposing");
                Object inputType = obj.get("inputType");
                if (isComposing != null) setIsComposing((Boolean) isComposing);
                if (inputType != null) setInputType((String) inputType);
                if (obj.get("data") instanceof NativeArray nativeArray) {
                    if (nativeArray.getLength() > 0) {
                        for (int i = 0; i < nativeArray.getLength(); i++) {
                            if (Strings.isNotBlank(getData())) {
                                setData(getData() + " " + nativeArray.get(i) + ",");
                            } else {
                                setData(nativeArray.get(i) + ",");
                            }
                        }
                        setData(getData().substring(0, getData().length() - 1));
                    }
                }

                if (obj.get("data") instanceof NativeObject) {
                    NativeObject data = (NativeObject) obj.get("data");
                    setData((String) data.get(0));
                }
            }
        }
    }

    @Override
    public String toString() {
        return "[object InputEvent]";
    }
}
