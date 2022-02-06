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

import java.awt.*;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.HashSet;

/**
 * A tool that allows you listen to a {@link Component} and all of its children. Even when children or added or removed, the listeners will automatically be
 * added/removed as necessary.
 *
 * @author andy.edwards
 */
public abstract class RecursiveListener {
    private final ContainerListener containerListener = new HierarchyChangeListener();

    private final HashSet<Component> excluded = new HashSet<>();

    public void addExcludedComponent(Component c) {
        excluded.add(c);
    }

    public void removeExcludedComponent(Component c) {
        excluded.remove(c);
    }

    public void installRecursively(Component c) {
        if (excluded.contains(c)) {
            return;
        }
        install(c);
        if (c instanceof Container) {
            Container cont = (Container) c;
            cont.addContainerListener(containerListener);
            installRecursively(cont);
        }
    }

    public void uninstallRecursively(Component c) {
        uninstall(c);
        if (c instanceof Container) {
            Container cont = (Container) c;
            cont.removeContainerListener(containerListener);
            uninstallRecursively(cont);
        }
    }

    protected abstract void install(Component c);

    protected abstract void uninstall(Component c);

    private class HierarchyChangeListener implements ContainerListener {
        @Override
        public void componentAdded(ContainerEvent e) {
            installRecursively(e.getChild());
        }

        @Override
        public void componentRemoved(ContainerEvent e) {
            uninstallRecursively(e.getChild());
        }
    }
}
