/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.menu.tools.developer.tool.inspector;

import org.loboevolution.html.node.Node;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

/**
 * <p>DOMSelectionListener class.</p>
 */
public class DOMSelectionListener implements TreeSelectionListener {

    private JTree tree;

    private ElementPropertiesPanel elemPropPanel;

    /**
     * <p>Constructor for DOMSelectionListener.</p>
     *
     * @param tree a {@link javax.swing.JTree} object.
     * @param panel a {@link ElementPropertiesPanel} object.
     */
    public DOMSelectionListener(JTree tree, ElementPropertiesPanel panel) {
        this.tree = tree;
        this.elemPropPanel = panel;
    }

    /**
     * valueChanged
     *
     * @param e a {@link javax.swing.event.TreeSelectionEvent} object.
     */
    public void valueChanged(TreeSelectionEvent e) {
        Node node = (Node) this.tree.getLastSelectedPathComponent();

        if (node == null) {
            return;
        }

        this.elemPropPanel.setForElement(node);
    }
}
