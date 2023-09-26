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

import org.sexydock.tabs.jhrome.JhromeTabbedPaneUI;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DragSourceDropEvent;

public class DefaultTabDropFailureHandler implements ITabDropFailureHandler {
    final ITabbedPaneWindowFactory windowFactory;

    public DefaultTabDropFailureHandler(final ITabbedPaneWindowFactory windowFactory) {
        this.windowFactory = windowFactory;
    }

    @Override
    public void onDropFailure(final DragSourceDropEvent dsde, final Tab draggedTab, final Dimension dragSourceWindowSize) {
        final ITabbedPaneWindow newJhromeWindow = windowFactory.createWindow();
        final Window newWindow = newJhromeWindow.getWindow();
        final JTabbedPane tabbedPane = newJhromeWindow.getTabbedPane();

        if (tabbedPane.getUI() instanceof JhromeTabbedPaneUI) {
            final JhromeTabbedPaneUI ui = (JhromeTabbedPaneUI) tabbedPane.getUI();
            ui.addTab(tabbedPane.getTabCount(), draggedTab, false);
            ui.finishAnimation();
        } else {
            JhromeTabbedPaneUI.insertTab(tabbedPane, tabbedPane.getTabCount(), draggedTab);
        }
        if (draggedTab.isEnabled()) {
            tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
        } else {
            tabbedPane.setSelectedIndex(-1);
        }

        if (dragSourceWindowSize != null) {
            newWindow.setSize(dragSourceWindowSize);
        } else {
            newWindow.pack();
        }

        newWindow.setLocation(dsde.getLocation());
        newWindow.setVisible(true);

        newWindow.toFront();
        newWindow.requestFocus();

        final Point loc = newWindow.getLocation();
        final Component renderer = draggedTab.getRenderer();
        final Point tabPos = new Point(renderer.getWidth() / 2, renderer.getHeight() / 2);
        SwingUtilities.convertPointToScreen(tabPos, renderer);

        loc.x += dsde.getX() - tabPos.x;
        loc.y += dsde.getY() - tabPos.y;
        newWindow.setLocation(loc);
    }
}
