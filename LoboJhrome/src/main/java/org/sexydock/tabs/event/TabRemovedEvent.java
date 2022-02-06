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

import org.sexydock.tabs.Tab;

import javax.swing.*;

public class TabRemovedEvent extends TabbedPaneEvent {
    public final Tab removedTab;
    public final int removedIndex;
    public TabRemovedEvent(JTabbedPane tabbedPane, long timestamp, Tab removedTab, int removedIndex) {
        super(tabbedPane, timestamp);
        this.removedTab = removedTab;
        this.removedIndex = removedIndex;
    }

    public Tab getRemovedTab() {
        return removedTab;
    }

    public int getRemovedIndex() {
        return removedIndex;
    }

    @Override
    public String toString() {
        return String.format("%s[tabbedPane: %s, timestamp: %d, removedTab: %s, removedIndex: %d]", getClass().getName(), tabbedPane, timestamp, removedTab, removedIndex);
    }
}
