/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.gui.prefs;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class PreferencesTree extends JTree {

	private static final long serialVersionUID = 1L;

	public PreferencesTree() {
		TreeNode rootNode = this.createRootNode();
		this.setModel(new DefaultTreeModel(rootNode));
		this.setRootVisible(false);
	}

	public void initSelection() {
		this.addSelectionRow(0);
	}

	private TreeNode createRootNode() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		root.add(new DefaultMutableTreeNode(this.getGeneralSettingsInfo()));
		root.add(new DefaultMutableTreeNode(this.getConnectionSettingsInfo()));
		root.add(new DefaultMutableTreeNode(this.getToolsSettingsInfo()));
		return root;
	}

	private SettingsInfo getGeneralSettingsInfo() {
		return new SettingsInfo() {
			public AbstractSettingsUI createSettingsUI() {
				return new GeneralSettingsUI();
			}

			public String getDescription() {
				return "General browser settings.";
			}

			public String getName() {
				return "General";
			}

			public String toString() {
				return this.getName();
			}
		};
	}

	private SettingsInfo getConnectionSettingsInfo() {
		return new SettingsInfo() {
			public AbstractSettingsUI createSettingsUI() {
				return new ConnectionSettingsUI();
			}

			public String getDescription() {
				return "Network connection settings.";
			}

			public String getName() {
				return "Connection";
			}

			public String toString() {
				return this.getName();
			}
		};
	}

	private SettingsInfo getToolsSettingsInfo() {
		return new SettingsInfo() {
			public AbstractSettingsUI createSettingsUI() {
				return new ToolsSettingsUI();
			}

			public String getDescription() {
				return "Tools settings.";
			}

			public String getName() {
				return "Tools";
			}

			public String toString() {
				return this.getName();
			}
		};
	}
}
