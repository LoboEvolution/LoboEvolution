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

package org.loboevolution.menu.tools.developer;

import com.jtattoo.plaf.lobo.LoboTree;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.menu.tools.AbstractToolsUI;
import org.loboevolution.menu.tools.ToolsInfo;
import org.loboevolution.menu.tools.developer.tool.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.io.Serial;

/**
 * <p>DeveloperToolTree class.</p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeveloperToolTree extends LoboTree {

	/** The Constant serialVersionUID. */
	@Serial
	private static final long serialVersionUID = 1L;

	/** The Constant frame. */
	private BrowserFrame frame;

	/**
	 * Instantiates a new preferences tree.
	 * @param frame a {@link BrowserFrame} object.
	 */
	public DeveloperToolTree(final BrowserFrame frame) {
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
				return new SourceViewerUI(DeveloperToolTree.this.getFrame());
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
				return new DOMInspectorUI(DeveloperToolTree.this.getFrame());
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
				return new HeadersPageUI(DeveloperToolTree.this.getFrame());
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
				return new TimingPageUI(DeveloperToolTree.this.getFrame());
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
				return new ContentPageUI(DeveloperToolTree.this.getFrame(), SyntaxConstants.SYNTAX_STYLE_CSS);
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
				return new ContentPageUI(DeveloperToolTree.this.getFrame(), SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
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
				return new ImagePageUI(DeveloperToolTree.this.getFrame());
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
				return new CookieUI(DeveloperToolTree.this.getFrame());
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
				return new StorageUI(DeveloperToolTree.this.getFrame(), 0);
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
				return new StorageUI(DeveloperToolTree.this.getFrame(), 1);
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
				return new InfoPageUI(DeveloperToolTree.this.getFrame());
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
