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
 * Controls the drag and drop policy of a {@link TabbedPane}. This includes whether tabs may be torn away from the tabbed pane or snapped in.
 *
 * @author andy.edwards
 */
public interface ITabbedPaneDndPolicy {
    /**
     * Controls whether a jhromeTab may be "torn away" from a {@code JhromeTabbedPane} (if it can be removed by being dragged out of the tabbed pane).
     *
     * @param tabbedPane the {@code JhromeTabbedPane} the user is dragging the jhromeTab out of.
     * @param tab        the {@code IJhromeTab} the user is dragging.
     * @return {@code true} if {@code jhromeTab} may be torn away from {@code tabbedPane}.
     */
    boolean isTearAwayAllowed(JTabbedPane tabbedPane, Tab tab);

    /**
     * Controls whether a jhromeTab may be "snapped in" to a {@code JhromeTabbedPane} (if it can be added by being dragged over the tabbed pane).
     *
     * @param tabbedPane the {@code JhromeTabbedPane} the user is dragging the jhromeTab over.
     * @param tab        the {@code IJhromeTab} the user is dragging.
     * @return {@code true} if {@code jhromeTab} may be snapped into {@code tabbedPane}.
     */
    boolean isSnapInAllowed(JTabbedPane tabbedPane, Tab tab);
}
