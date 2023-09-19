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

package org.sexydock.tabs;

import javax.swing.*;

/**
 * Controls the drag and drop policy of a {@link JTabbedPane}. This includes whether tabs may be torn away from the tabbed pane or snapped in.
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
