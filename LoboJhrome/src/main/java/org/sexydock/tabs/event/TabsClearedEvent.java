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
import java.util.List;

public class TabsClearedEvent extends TabbedPaneEvent {
    public final List<Tab> removedTabs;

    public TabsClearedEvent(final JTabbedPane tabbedPane, final long timestamp, final List<Tab> removedTabs) {
        super(tabbedPane, timestamp);
        this.removedTabs = removedTabs;
    }

    public List<Tab> getRemovedTabs() {
        return removedTabs;
    }

    @Override
    public String toString() {
        return String.format("%s[tabbedPane: %s, timestamp: %d]", getClass().getName(), tabbedPane, timestamp);
    }
}
