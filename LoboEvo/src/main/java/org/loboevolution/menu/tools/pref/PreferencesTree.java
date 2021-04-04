/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.menu.tools.pref;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import com.jtattoo.plaf.lobo.LoboTree;

/**
 * <p>PreferencesTree class.</p>
 *
 *
 *
 */
public class PreferencesTree extends LoboTree {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new preferences tree.
	 */
	public PreferencesTree() {
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		final TreeNode rootNode = createRootNode();
		setModel(new DefaultTreeModel(rootNode));
		setRootVisible(false);
	}

	/**
	 * Creates the root node.
	 *
	 * @return the tree node
	 */
	private TreeNode createRootNode() {
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		root.add(new DefaultMutableTreeNode(getConnectionSettingsInfo()));
		root.add(new DefaultMutableTreeNode(getGeneralSettingsInfo()));
		root.add(new DefaultMutableTreeNode(getLookAndFeelsSettingsInfo()));
		root.add(new DefaultMutableTreeNode(getToolsSettingsInfo()));
		return root;
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
				return getName();
			}
		};
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
				return getName();
			}
		};
	}

	/**
	 * Gets the look and feels settings info.
	 *
	 * @return the look and feels settings info
	 */
	private SettingsInfo getLookAndFeelsSettingsInfo() {
		return new SettingsInfo() {
			@Override
			public AbstractSettingsUI createSettingsUI() {
				return new LookAndFeelsSettingsUI();
			}

			@Override
			public String getDescription() {
				return "Choice Look and Feels.";
			}

			@Override
			public String getName() {
				return "Look and Feels";
			}

			@Override
			public String toString() {
				return getName();
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
				return getName();
			}
		};
	}

	/**
	 * Inits the selection.
	 */
	public void initSelection() {
		addSelectionRow(0);
	}
}
