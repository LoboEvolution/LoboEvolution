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

package org.sexydock.tabs.event;

import javax.swing.*;

public class TabbedPaneEvent {
    public final JTabbedPane tabbedPane;
    public final long timestamp;
    public TabbedPaneEvent(JTabbedPane tabbedPane, long timestamp) {
        this.timestamp = timestamp;
        this.tabbedPane = tabbedPane;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
