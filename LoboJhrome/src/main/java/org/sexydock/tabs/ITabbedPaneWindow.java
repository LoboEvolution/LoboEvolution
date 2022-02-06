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
import java.awt.*;

/**
 * The interface for a window containing a {@link TabbedPane}. {@code JhromeTabbedPane} uses this interface to move a jhromeTab that has been torn away into a
 * new window. This allows you to control the layout and attributes of the window besides its tabbed pane.
 *
 * @author andy.edwards
 * @see ITabbedPaneWindowFactory
 */
public interface ITabbedPaneWindow {
    /**
     * @return the {@code JhromeTabbedPane} in the window.
     */
    JTabbedPane getTabbedPane();

    /**
     * @return the {@code Window} containing the tabbed pane.
     */
    Window getWindow();
}
