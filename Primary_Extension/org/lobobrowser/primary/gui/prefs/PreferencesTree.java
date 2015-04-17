/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.gui.prefs;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * The Class PreferencesTree.
 */
public class PreferencesTree extends JTree {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new preferences tree.
     */
    public PreferencesTree() {
        TreeNode rootNode = this.createRootNode();
        this.setModel(new DefaultTreeModel(rootNode));
        this.setRootVisible(false);
    }

    /**
     * Inits the selection.
     */
    public void initSelection() {
        this.addSelectionRow(0);
    }

    /**
     * Creates the root node.
     *
     * @return the tree node
     */
    private TreeNode createRootNode() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        root.add(new DefaultMutableTreeNode(this.getGeneralSettingsInfo()));
        root.add(new DefaultMutableTreeNode(this.getConnectionSettingsInfo()));
        root.add(new DefaultMutableTreeNode(this.getToolsSettingsInfo()));
        return root;
    }

    /**
     * Gets the general settings info.
     *
     * @return the general settings info
     */
    private SettingsInfo getGeneralSettingsInfo() {
        return new SettingsInfo() {
            @Override
            public AbstractSettingsUI createSettingsUI() {
                return new GeneralSettingsUI();
            }

            @Override
            public String getDescription() {
                return "General browser settings.";
            }

            @Override
            public String getName() {
                return "General";
            }

            @Override
            public String toString() {
                return this.getName();
            }
        };
    }

    /**
     * Gets the connection settings info.
     *
     * @return the connection settings info
     */
    private SettingsInfo getConnectionSettingsInfo() {
        return new SettingsInfo() {
            @Override
            public AbstractSettingsUI createSettingsUI() {
                return new ConnectionSettingsUI();
            }

            @Override
            public String getDescription() {
                return "Network connection settings.";
            }

            @Override
            public String getName() {
                return "Connection";
            }

            @Override
            public String toString() {
                return this.getName();
            }
        };
    }

    /**
     * Gets the tools settings info.
     *
     * @return the tools settings info
     */
    private SettingsInfo getToolsSettingsInfo() {
        return new SettingsInfo() {
            @Override
            public AbstractSettingsUI createSettingsUI() {
                return new ToolsSettingsUI();
            }

            @Override
            public String getDescription() {
                return "Tools settings.";
            }

            @Override
            public String getName() {
                return "Tools";
            }

            @Override
            public String toString() {
                return this.getName();
            }
        };
    }
}
