/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.ext;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.loboevolution.gui.DefaultWindowFactory;
import org.loboevolution.util.gui.WrapperLayout;
import org.loboevolution.w3c.html.HTMLDocument;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class DomViewerWindow.
 */
public class DomViewerWindow extends JFrame implements TreeSelectionListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The dom tree. */
	private JTree domTree;

	/** The text area. */
	private JTextArea textArea;

	/**
	 * Instantiates a new dom viewer window.
	 */
	public DomViewerWindow() {
		super("Lobo DOM Viewer");
		this.setIconImage(DefaultWindowFactory.getInstance().getDefaultImageIcon().getImage());
		Container contentPane = this.getContentPane();
		this.domTree = new JTree();
		this.domTree.setRootVisible(false);
		this.domTree.setShowsRootHandles(true);
		this.domTree.addTreeSelectionListener(this);
		JTextArea textArea = this.createTextArea();
		this.textArea = textArea;
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(domTree),
				new JScrollPane(textArea));
		contentPane.setLayout(WrapperLayout.getInstance());
		contentPane.add(splitPane);
	}

	/**
	 * Creates the text area.
	 *
	 * @return the j text area
	 */
	private JTextArea createTextArea() {
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		return textArea;
	}

	/**
	 * Sets the document.
	 *
	 * @param document
	 *            the new document
	 */
	public void setDocument(HTMLDocument document) {
		this.domTree.setModel(new DefaultTreeModel(new DomTreeNode(document)));
	}

	/**
	 * The Class DomTreeNode.
	 */
	private class DomTreeNode extends DefaultMutableTreeNode {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new dom tree node.
		 *
		 * @param node
		 *            the node
		 */
		public DomTreeNode(Node node) {
			super(node);
			NodeList childNodes = node.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node child = childNodes.item(i);
				if (child.getNodeType() == Node.TEXT_NODE) {
					if (child.getNodeValue().trim().length() > 0) {
						this.add(new DomTreeNode(child));
					}
				} else {
					this.add(new DomTreeNode(child));
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
		 */
		@Override
		public String toString() {
			return getNode().getNodeName();
		}

		/**
		 * Gets the node.
		 *
		 * @return the node
		 */
		public Node getNode() {
			return (Node) getUserObject();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.
	 * TreeSelectionEvent)
	 */
	@Override
	public void valueChanged(TreeSelectionEvent treeselectionevent) {
		TreePath path = treeselectionevent.getNewLeadSelectionPath();
		if (path != null) {
			DomTreeNode domNode = (DomTreeNode) path.getLastPathComponent();
			Node node = domNode.getNode();
			if (node.getNodeType() == Node.TEXT_NODE || node.getNodeType() == Node.COMMENT_NODE) {
				this.textArea.setText(node.getNodeValue());
			} else {
				this.textArea.setText("");
				this.appendNode(0, node);
			}
			this.textArea.setCaretPosition(0);
		}
	}

	/**
	 * Append node.
	 *
	 * @param indent
	 *            the indent
	 * @param node
	 *            the node
	 */
	private void appendNode(int indent, Node node) {
		if (node.getNodeType() == Node.TEXT_NODE) {
			this.textArea.append(node.getNodeValue());
		} else if (node.getNodeType() == Node.COMMENT_NODE) {
			this.textArea.append("\n/* " + node.getNodeValue() + " */");
		} else {
			this.textArea.setText(this.textArea.getText().trim());
			this.textArea.append("\n");
			addIndent(indent);
			this.textArea.append("<" + node.getNodeName());
			this.addAttributes(node);
			NodeList childNodes = node.getChildNodes();
			if (childNodes.getLength() == 0) {
				this.textArea.append("/");
			}
			this.textArea.append(">");
			for (int i = 0; i < childNodes.getLength(); i++) {
				appendNode(indent + 1, childNodes.item(i));
			}
			if (childNodes.getLength() > 0) {
				this.textArea.append("\n");
				addIndent(indent);
				this.textArea.append("</" + node.getNodeName() + ">");
			}
		}
	}

	/**
	 * Adds the attributes.
	 *
	 * @param node
	 *            the node
	 */
	private void addAttributes(Node node) {
		NamedNodeMap attributes = node.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Node attr = attributes.item(i);
			textArea.append(" ");
			textArea.append(attr.getNodeName());
			textArea.append("=\"");
			textArea.append(attr.getNodeValue());
			textArea.append("\"");
		}
	}

	/**
	 * Adds the indent.
	 *
	 * @param indent
	 *            the indent
	 */
	private void addIndent(int indent) {
		for (int i = 0; i < indent; i++) {
			this.textArea.append("   ");
		}
	}
}
