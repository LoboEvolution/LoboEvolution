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

package org.loboevolution.menu.tools.developer;

import com.jtattoo.plaf.lobo.LoboTree;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.menu.tools.AbstractToolsUI;
import org.loboevolution.menu.tools.ToolsInfo;
import org.loboevolution.menu.tools.developer.tool.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * <p>DeveloperToolTree class.</p>
 */
public class DeveloperToolTree extends LoboTree {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant frame. */
	private static BrowserFrame frame;

	/**
	 * Instantiates a new preferences tree.
	 * @param frame a {@link BrowserFrame} object.
	 */
	public DeveloperToolTree(BrowserFrame frame) {
		this.frame = frame;
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		final TreeNode rootNode = createRootNode();
		setModel(new DefaultTreeModel(rootNode));
		setRootVisible(false);
	}

	private TreeNode createRootNode() {
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode();

		final DefaultMutableTreeNode moduleDebug = new DefaultMutableTreeNode("Debug");
		moduleDebug.add(new DefaultMutableTreeNode(getConsole()));
		moduleDebug.add(new DefaultMutableTreeNode(getSourceCode()));
		moduleDebug.add(new DefaultMutableTreeNode(getDomTree()));
		root.add(moduleDebug);

		final DefaultMutableTreeNode moduleNet = new DefaultMutableTreeNode("Network");
		moduleNet.add(new DefaultMutableTreeNode(getNetworkHeaders()));
		moduleNet.add(new DefaultMutableTreeNode(getNetworkTiming()));

		root.add(moduleNet);

		final DefaultMutableTreeNode mediaNode = new DefaultMutableTreeNode("Media");
		mediaNode.add(new DefaultMutableTreeNode(getCss()));
		mediaNode.add(new DefaultMutableTreeNode(getJS()));
		mediaNode.add(new DefaultMutableTreeNode(getImage()));
		root.add(mediaNode);

		final DefaultMutableTreeNode storeeNode = new DefaultMutableTreeNode("Store");
		storeeNode.add(new DefaultMutableTreeNode(getCookie()));
		storeeNode.add(new DefaultMutableTreeNode(getLocalStorage()));
		storeeNode.add(new DefaultMutableTreeNode(getSessionStorage()));
		root.add(storeeNode);

		final DefaultMutableTreeNode infoNode = new DefaultMutableTreeNode("Info");
		infoNode.add(new DefaultMutableTreeNode(getInfo()));
		root.add(infoNode);

		return root;
	}

	private ToolsInfo getConsole() {
		return new ToolsInfo() {
			@Override
			public AbstractToolsUI createSettingsUI() {
				return new ConsoleUI();
			}

			@Override
			public String getDescription() {
				return "";
			}

			@Override
			public String getName() {
				return "Console";
			}

			@Override
			public String toString() {
				return getName();
			}
		};
	}

	private ToolsInfo getSourceCode() {
		return new ToolsInfo() {
			@Override
			public AbstractToolsUI createSettingsUI() {
				return new SourceViewerUI(DeveloperToolTree.frame);
			}

			@Override
			public String getDescription() {
				return "";
			}

			@Override
			public String getName() {
				return "Source Code";
			}

			@Override
			public String toString() {
				return getName();
			}
		};
	}

	private ToolsInfo getDomTree() {
		return new ToolsInfo() {
			@Override
			public AbstractToolsUI createSettingsUI() {
				return new DOMInspectorUI(DeveloperToolTree.frame);
			}

			@Override
			public String getDescription() {
				return "";
			}

			@Override
			public String getName() {
				return "Dom Tree";
			}

			@Override
			public String toString() {
				return getName();
			}
		};
	}

	private ToolsInfo getNetworkHeaders() {
		return new ToolsInfo() {
			@Override
			public AbstractToolsUI createSettingsUI() {
				return new HeadersPageUI(DeveloperToolTree.frame);
			}

			@Override
			public String getDescription() {
				return "";
			}

			@Override
			public String getName() {
				return "Headers";
			}

			@Override
			public String toString() {
				return getName();
			}
		};
	}

	private ToolsInfo getNetworkTiming() {
		return new ToolsInfo() {
			@Override
			public AbstractToolsUI createSettingsUI() {
				return new TimingPageUI(DeveloperToolTree.frame);
			}

			@Override
			public String getDescription() {
				return "";
			}

			@Override
			public String getName() {
				return "Timing";
			}

			@Override
			public String toString() {
				return getName();
			}
		};
	}

	private ToolsInfo getCss() {
		return new ToolsInfo() {
			@Override
			public AbstractToolsUI createSettingsUI() {
				return new ContentPageUI(DeveloperToolTree.frame, SyntaxConstants.SYNTAX_STYLE_CSS);
			}

			@Override
			public String getDescription() {
				return "";
			}

			@Override
			public String getName() {
				return "Css";
			}

			@Override
			public String toString() {
				return getName();
			}
		};
	}

	private ToolsInfo getJS() {
		return new ToolsInfo() {
			@Override
			public AbstractToolsUI createSettingsUI() {
				return new ContentPageUI(DeveloperToolTree.frame, SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
			}

			@Override
			public String getDescription() {
				return "";
			}

			@Override
			public String getName() {
				return "Js";
			}

			@Override
			public String toString() {
				return getName();
			}
		};
	}

	private ToolsInfo getImage() {
		return new ToolsInfo() {
			@Override
			public AbstractToolsUI createSettingsUI() {
				return new ImagePageUI(DeveloperToolTree.frame);
			}

			@Override
			public String getDescription() {
				return "";
			}

			@Override
			public String getName() {
				return "Images";
			}

			@Override
			public String toString() {
				return getName();
			}
		};
	}

	private ToolsInfo getCookie() {
		return new ToolsInfo() {
			@Override
			public AbstractToolsUI createSettingsUI() {
				return new CookieUI(DeveloperToolTree.frame);
			}

			@Override
			public String getDescription() {
				return "";
			}

			@Override
			public String getName() {
				return "Cookies";
			}

			@Override
			public String toString() {
				return getName();
			}
		};
	}

	private ToolsInfo getLocalStorage() {
		return new ToolsInfo() {
			@Override
			public AbstractToolsUI createSettingsUI() {
				return new StorageUI(DeveloperToolTree.frame, 0);
			}

			@Override
			public String getDescription() {
				return "";
			}

			@Override
			public String getName() {
				return "Local";
			}

			@Override
			public String toString() {
				return getName();
			}
		};
	}

	private ToolsInfo getSessionStorage() {
		return new ToolsInfo() {
			@Override
			public AbstractToolsUI createSettingsUI() {
				return new StorageUI(DeveloperToolTree.frame, 1);
			}

			@Override
			public String getDescription() {
				return "";
			}

			@Override
			public String getName() {
				return "Session";
			}

			@Override
			public String toString() {
				return getName();
			}
		};
	}

	private ToolsInfo getInfo() {
		return new ToolsInfo() {
			@Override
			public AbstractToolsUI createSettingsUI() {
				return new InfoPageUI(DeveloperToolTree.frame);
			}

			@Override
			public String getDescription() {
				return "";
			}

			@Override
			public String getName() {
				return "Info Page";
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
