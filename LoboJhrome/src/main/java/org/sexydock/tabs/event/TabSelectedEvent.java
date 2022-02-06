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

public class TabSelectedEvent extends TabbedPaneEvent {
    public final Tab prevSelected;
    public final int prevSelectedIndex;
    public final Tab newSelected;
    public final int newSelectedIndex;
    public TabSelectedEvent(JTabbedPane tabbedPane, long timestamp, Tab prevSelected, int prevSelectedIndex, Tab newSelected, int newSelectedIndex) {
        super(tabbedPane, timestamp);
        this.prevSelected = prevSelected;
        this.prevSelectedIndex = prevSelectedIndex;
        this.newSelected = newSelected;
        this.newSelectedIndex = newSelectedIndex;
    }

    public Tab getPrevSelected() {
        return prevSelected;
    }

    public Tab getNewSelected() {
        return newSelected;
    }

    public int getPrevSelectedIndex() {
        return prevSelectedIndex;
    }

    public int getNewSelectedIndex() {
        return newSelectedIndex;
    }

    @Override
    public String toString() {
        return String.format("%s[tabbedPane: %s, timestamp: %d, prevSelected: %s, prevSelectedIndex: %d, newIndex: %d]", getClass().getName(), tabbedPane, timestamp, prevSelected, prevSelectedIndex, newSelected, newSelectedIndex);
    }
}
