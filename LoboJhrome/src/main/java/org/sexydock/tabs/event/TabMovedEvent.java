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

public class TabMovedEvent extends TabbedPaneEvent {
    public final Tab movedTab;
    public final int prevIndex;
    public final int newIndex;
    public TabMovedEvent(JTabbedPane tabbedPane, long timestamp, Tab movedTab, int prevIndex, int newIndex) {
        super(tabbedPane, timestamp);
        this.movedTab = movedTab;
        this.prevIndex = prevIndex;
        this.newIndex = newIndex;
    }

    public Tab getMovedTab() {
        return movedTab;
    }

    public int getPrevIndex() {
        return prevIndex;
    }

    public int getNewIndex() {
        return newIndex;
    }

    @Override
    public String toString() {
        return String.format("%s[tabbedPane: %s, timestamp: %d, movedTab: %s, prevIndex: %d, newIndex: %d]", getClass().getName(), tabbedPane, timestamp, movedTab, prevIndex, newIndex);
    }
}
