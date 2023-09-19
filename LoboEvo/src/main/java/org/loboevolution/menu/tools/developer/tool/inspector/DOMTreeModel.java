/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DOMTreeModel implements TreeModel {

    /**
     * Description of the Field
     */
    private Document doc;

    /**
     * Our root for display
     */
    private Node root;

    /**
     * Description of the Field
     */
    private HashMap<Node, List<Node>> displayableNodes;

    /**
     * Description of the Field
     */
    private List listeners = new ArrayList<>();

    /**
     * Constructor for the DOMTreeModel object
     *
     * @param doc PARAM
     */
    public DOMTreeModel(Document doc) {
        this.displayableNodes = new HashMap<>();
        this.doc = doc;
        setRoot("body");
    }

    private void setRoot(String rootNodeName) {
        Node tempRoot = doc.getDocumentElement();
        NodeList nl = tempRoot.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeName().toLowerCase().equals(rootNodeName)) {
                this.root = nl.item(i);
            }
        }
    }

    /**
     * Adds the specified TreeModel listener to receive TreeModel events from
     * this component. If listener l is null, no exception is thrown and no
     * action is performed.
     *
     * @param l Contains the TreeModelListener for TreeModelEvent data.
     */
    public void addTreeModelListener(TreeModelListener l) {
        this.listeners.add(l);
    }

    /**
     * Removes the specified TreeModel listener so that it no longer receives
     * TreeModel events from this component. This method performs no function,
     * nor does it throw an exception, if the listener specified by the argument
     * was not previously added to this component. If listener l is null, no
     * exception is thrown and no action is performed.
     *
     * @param l Contains the TreeModelListener for TreeModelEvent data.
     */
    public void removeTreeModelListener(TreeModelListener l) {
        this.listeners.remove(l);
    }

    /**
     * Gets the child attribute of the DOMTreeModel object
     *
     * @param parent PARAM
     * @param index  PARAM
     * @return The child value
     */
    public Object getChild(Object parent, int index) {
        Node node = (Node) parent;
        List<Node> children = this.displayableNodes.get(parent);
        if (children == null) {
            children = addDisplayable(node);
        }
        return children.get(index);
    }

    /**
     * Gets the childCount attribute of the DOMTreeModel object
     *
     * @param parent PARAM
     * @return The childCount value
     */
    public int getChildCount(Object parent) {

        Node node = (Node) parent;
        List<Node> children = this.displayableNodes.get(parent);
        if (children == null) {
            children = addDisplayable(node);
        }
        return children.size();
    }

    /**
     * Gets the indexOfChild attribute of the DOMTreeModel object
     *
     * @param parent PARAM
     * @param child  PARAM
     * @return The indexOfChild value
     */
    public int getIndexOfChild(Object parent, Object child) {

        Node node = (Node) parent;
        List<Node> children = this.displayableNodes.get(parent);
        if (children == null) {
            children = addDisplayable(node);
        }
        if (children.contains(child)) {
            return children.indexOf(child);
        } else {
            return -1;
        }
    }

    /**
     * Gets the root attribute of the DOMTreeModel object
     *
     * @return The root value
     */
    public Object getRoot() {
        return this.root;
    }

    /**
     * Gets the leaf attribute of the DOMTreeModel object
     *
     * @param nd PARAM
     * @return The leaf value
     */
    public boolean isLeaf(Object nd) {
        Node node = (Node) nd;
        return !node.hasChildNodes();
    }

    /**
     * Messaged when the user has altered the value for the item identified
     * by <code>path</code> to <code>newValue</code>.
     * If <code>newValue</code> signifies a truly new value
     * the model should post a <code>treeNodesChanged</code> event.
     *
     * @param path     path to the node that the user has altered
     * @param newValue the new value from the TreeCellEditor
     */
    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    /**
     * Adds a feature to the Displayable attribute of the DOMTreeModel object
     *
     * @param parent The feature to be added to the Displayable attribute
     * @return Returns
     */
    private List<Node> addDisplayable(Node parent) {
        List<Node> children = this.displayableNodes.get(parent);
        if (children == null) {
            children = new ArrayList<>();
            this.displayableNodes.put(parent, children);
            NodeList nl = parent.getChildNodes();
            for (int i = 0, len = nl.getLength(); i < len; i++) {
                Node child = nl.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE ||
                        child.getNodeType() == Node.COMMENT_NODE ||
                        (child.getNodeType() == Node.TEXT_NODE && (child.getNodeValue().trim().length() > 0))) {
                    children.add(child);
                }
            }
            return children;
        } else {
            return new ArrayList<>();
        }
    }

}