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

package org.loboevolution.component;

import javax.swing.*;
import java.awt.*;

public interface ITabbedPane {
    /**
     * Indicates the relative location of the tab pane within a series of
     * tabbed panes.
     *
     * @return the index, starting from the left-most pane
     */
    int getIndex();

    /**
     * Associates a pop-up menu with the tabbed pane using the given panel
     * as the pop-up menu's parent.
     *
     * @param panel The parent to the pop-up menu to create.
     */
    void setComponentPopupMenu(IBrowserPanel panel);

    /**
     * Returns the actively selected tab in the list of tabs.
     *
     * @return A zero-based index.
     */
    int getSelectedIndex();

    /**
     * Changes the selected tab.
     *
     * @param index A zero-based index.
     */
    void setSelectedIndex(int index);

    /**
     * Removes the tab at the given index from the tabbed panel.
     *
     * @param index Zero-based index.
     */
    void remove(int index);

    /**
     * Inserts a new tab into the panel at the given index.
     *
     * @param title     Descriptive tab title.
     * @param icon      Small identifying icon.
     * @param component Parent component.
     * @param tip       Tooltip.
     * @param index     Zero-based index.
     */
    void insertTab(String title, Icon icon, Component component, String tip, int index);
}
