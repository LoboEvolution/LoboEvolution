/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.menu.tools.developer.tool.inspector;

import org.loboevolution.html.node.Node;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

/**
 * <p>DOMSelectionListener class.</p>
 */
public class DOMSelectionListener implements TreeSelectionListener {

    private final JTree tree;

    private final ElementPropertiesPanel elemPropPanel;

    /**
     * <p>Constructor for DOMSelectionListener.</p>
     *
     * @param tree a {@link javax.swing.JTree} object.
     * @param panel a {@link ElementPropertiesPanel} object.
     */
    public DOMSelectionListener(final JTree tree, final ElementPropertiesPanel panel) {
        this.tree = tree;
        this.elemPropPanel = panel;
    }

    /**
     * valueChanged
     *
     * @param e a {@link javax.swing.event.TreeSelectionEvent} object.
     */
    public void valueChanged(final TreeSelectionEvent e) {
        final Node node = (Node) this.tree.getLastSelectedPathComponent();

        if (node == null) {
            return;
        }

        this.elemPropPanel.setForElement(node);
    }
}
