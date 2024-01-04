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

package org.loboevolution.menu.tools.developer.tool;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.ToolBar;
import org.loboevolution.html.node.Document;
import org.loboevolution.http.NavigationManager;
import org.loboevolution.menu.tools.AbstractToolsUI;
import org.loboevolution.menu.tools.developer.tool.inspector.DOMSelectionListener;
import org.loboevolution.menu.tools.developer.tool.inspector.DOMTreeCellRenderer;
import org.loboevolution.menu.tools.developer.tool.inspector.DOMTreeModel;
import org.loboevolution.menu.tools.developer.tool.inspector.ElementPropertiesPanel;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

/**
 * <p>DOMInspectorPane class.</p>
 */
public class DOMInspectorUI extends AbstractToolsUI {

    private final JTree tree;

    private final JSplitPane splitPane;

    private ElementPropertiesPanel elementPropPanel;

    private DOMSelectionListener nodeSelectionListener;

    private final Document doc;

    /**
     * <p>Constructor for DOMInspectorPane.</p>
     *
     * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
     */
    public DOMInspectorUI(final BrowserFrame frame) {
        final ToolBar toolbar = frame.getToolbar();
        doc = NavigationManager.getDocument(toolbar.getAddressBar().getText());

        this.setLayout(new BorderLayout());

        this.tree = new JTree();
        this.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        final JScrollPane scroll = new JScrollPane(tree);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);

        this.add(splitPane, "Center");
        splitPane.setLeftComponent(scroll);

        initForCurrentDocument();
    }

    private void initForCurrentDocument() {
        final TreeModel model = new DOMTreeModel(doc);
        tree.setModel(model);
        if (!(tree.getCellRenderer() instanceof DOMTreeCellRenderer)) {
            tree.setCellRenderer(new DOMTreeCellRenderer());
        }

        if (elementPropPanel != null) {
            splitPane.remove(elementPropPanel);
        }
        elementPropPanel = new ElementPropertiesPanel();
        splitPane.setRightComponent(elementPropPanel);

        tree.removeTreeSelectionListener(nodeSelectionListener);
        nodeSelectionListener = new DOMSelectionListener(tree, elementPropPanel);
        tree.addTreeSelectionListener(nodeSelectionListener);
    }
}
