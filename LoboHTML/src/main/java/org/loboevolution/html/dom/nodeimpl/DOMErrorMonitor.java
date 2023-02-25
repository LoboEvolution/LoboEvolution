/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.nodeimpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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