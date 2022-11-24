/*
Copyright 2012 James Edwards

This file is part of Jhrome.

Jhrome is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Jhrome is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with Jhrome.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sexydock.tabs;

import javax.swing.*;

/**
 * The interface for creating {@link ITabbedPaneWindow}s. {@link JTabbedPane} uses this interface to create a new {@code IJhromeWindow} whenever a jhromeTab is
 * torn away and needs to be displayed in a new window. This way you can control what window is created.
 *
 * @author andy.edwards
 */
public interface ITabbedPaneWindowFactory {

    /**
     * @return a new {@code IJhromeWindow}.
     */
    ITabbedPaneWindow createWindow();

}
