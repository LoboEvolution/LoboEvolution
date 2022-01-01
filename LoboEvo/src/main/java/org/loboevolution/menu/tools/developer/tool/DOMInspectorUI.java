/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
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
    public DOMInspectorUI(BrowserFrame frame) {
        final ToolBar toolbar = frame.getToolbar();
        doc = NavigationManager.getDocument(toolbar.getAddressBar().getText());

        this.setLayout(new BorderLayout());

        this.tree = new JTree();
        this.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        JScrollPane scroll = new JScrollPane(tree);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);

        this.add(splitPane, "Center");
        splitPane.setLeftComponent(scroll);

        initForCurrentDocument();
    }

    private void initForCurrentDocument() {
        TreeModel model = new DOMTreeModel(doc);
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
