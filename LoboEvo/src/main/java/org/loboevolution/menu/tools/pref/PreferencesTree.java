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

package org.loboevolution.menu.tools.pref;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import com.jtattoo.plaf.lobo.LoboTree;
import org.loboevolution.menu.tools.AbstractToolsUI;
import org.loboevolution.menu.tools.ToolsInfo;

import java.io.Serial;

/**
 * <p>PreferencesTree class.</p>
 */
public class PreferencesTree extends LoboTree {

	/** The Constant serialVersionUID. */
	@Serial
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
		root.add(new DefaultMutableTreeNode(getSettingsInfo()));
		return root;
	}

	/**
	 * Gets the connection settings info.
	 *
	 * @return the connection settings info
	 */
	private ToolsInfo getConnectionSettingsInfo() {
		return new ToolsInfo() {
			@Override
			public AbstractToolsUI createSettingsUI() {
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
	private ToolsInfo getGeneralSettingsInfo() {
		return new ToolsInfo() {
			@Override
			public AbstractToolsUI createSettingsUI() {
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
	private ToolsInfo getLookAndFeelsSettingsInfo() {
		return new ToolsInfo() {
			@Override
			public AbstractToolsUI createSettingsUI() {
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
	private ToolsInfo getSettingsInfo() {
		return new ToolsInfo() {
			@Override
			public AbstractToolsUI createSettingsUI() {
				return new SettingsUI();
			}

			@Override
			public String getDescription() {
				return "Settings.";
			}

			@Override
			public String getName() {
				return "Settings";
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
