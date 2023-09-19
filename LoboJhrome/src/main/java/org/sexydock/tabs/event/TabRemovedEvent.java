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
