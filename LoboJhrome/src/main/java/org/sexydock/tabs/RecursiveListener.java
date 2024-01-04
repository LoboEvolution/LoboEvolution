/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

    public void addExcludedComponent(final Component c) {
        excluded.add(c);
    }

    public void removeExcludedComponent(final Component c) {
        excluded.remove(c);
    }

    public void installRecursively(final Component c) {
        if (excluded.contains(c)) {
            return;
        }
        install(c);
        if (c instanceof Container) {
            final Container cont = (Container) c;
            cont.addContainerListener(containerListener);
            installRecursively(cont);
        }
    }

    public void uninstallRecursively(final Component c) {
        uninstall(c);
        if (c instanceof Container) {
            final Container cont = (Container) c;
            cont.removeContainerListener(containerListener);
            uninstallRecursively(cont);
        }
    }

    protected abstract void install(final Component c);

    protected abstract void uninstall(final Component c);

    private final class HierarchyChangeListener implements ContainerListener {
        @Override
        public void componentAdded(final ContainerEvent e) {
            installRecursively(e.getChild());
        }

        @Override
        public void componentRemoved(final ContainerEvent e) {
            uninstallRecursively(e.getChild());
        }
    }
}
