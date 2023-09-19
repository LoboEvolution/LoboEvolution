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
package org.loboevolution.html.dom.nodeimpl;

import lombok.Data;
import org.loboevolution.html.dom.DOMError;
import org.loboevolution.html.dom.DOMErrorHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a utility implementation of EventListener
 * that captures all events and provides access
 * to lists of all events by mode
 */

@Data
public class DOMErrorMonitor implements DOMErrorHandler {

    private List<DOMError> errors;

    public DOMErrorMonitor(){
        errors = new ArrayList<>();
    }

    /**
     * Implementation of DOMErrorHandler.handleError that
     * adds copy of error to list for later retrieval.
     */
    @Override
    public boolean handleError(DOMError error) {
        errors.add(new DOMErrorImpl(error));
        return true;
    }

    public boolean assertLowerSeverity(int severity) {
        for (DOMError error : errors) {
            if (error.getSeverity() >= severity) {
                return false;
            }
        }
        return true;
    }
}