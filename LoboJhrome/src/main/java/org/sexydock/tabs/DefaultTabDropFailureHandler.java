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

import org.sexydock.tabs.jhrome.JhromeTabbedPaneUI;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DragSourceDropEvent;

public class DefaultTabDropFailureHandler implements ITabDropFailureHandler {
    final ITabbedPaneWindowFactory windowFactory;

    public DefaultTabDropFailureHandler(ITabbedPaneWindowFactory windowFactory) {
        this.windowFactory = windowFactory;
    }

    @Override
    public void onDropFailure(DragSourceDropEvent dsde, Tab draggedTab, Dimension dragSourceWindowSize) {
        ITabbedPaneWindow newJhromeWindow = windowFactory.createWindow();
        Window newWindow = newJhromeWindow.getWindow();
        JTabbedPane tabbedPane = newJhromeWindow.getTabbedPane();

        if (tabbedPane.getUI() instanceof JhromeTabbedPaneUI) {
            JhromeTabbedPaneUI ui = (JhromeTabbedPaneUI) tabbedPane.getUI();
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

        Point loc = newWindow.getLocation();
        Component renderer = draggedTab.getRenderer();
        Point tabPos = new Point(renderer.getWidth() / 2, renderer.getHeight() / 2);
        SwingUtilities.convertPointToScreen(tabPos, renderer);

        loc.x += dsde.getX() - tabPos.x;
        loc.y += dsde.getY() - tabPos.y;
        newWindow.setLocation(loc);
    }
}
