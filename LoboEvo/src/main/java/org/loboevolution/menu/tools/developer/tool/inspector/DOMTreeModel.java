/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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

import org.loboevolution.common.Strings;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DOMTreeModel implements TreeModel {

    /**
     * Description of the Field
     */
    private final Document doc;

    /**
     * Our root for display
     */
    private Node root;

    /**
     * Description of the Field
     */
    private final Map<Node, List<Node>> displayableNodes;

    /**
     * Description of the Field
     */
    private final List<TreeModelListener> listeners = new ArrayList<>();

    /**
     * Constructor for the DOMTreeModel object
     *
     * @param doc PARAM
     */
    public DOMTreeModel(final Document doc) {
        this.displayableNodes = new HashMap<>();
        this.doc = doc;
        setRoot("body");
    }

    private void setRoot(final String rootNodeName) {
        final Node tempRoot = doc.getDocumentElement();
        final NodeList nl = tempRoot.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeName().toLowerCase().equals(rootNodeName)) {
                this.root = nl.item(i);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void addTreeModelListener(final TreeModelListener l) {
        this.listeners.add(l);
    }

    /** {@inheritDoc} */
    @Override
    public void removeTreeModelListener(final TreeModelListener l) {
        this.listeners.remove(l);
    }

    /** {@inheritDoc} */
    @Override
    public Object getChild(final Object parent, final int index) {
        final Node node = (Node) parent;
        List<Node> children = this.displayableNodes.get(parent);
        if (children == null) {
            children = addDisplayable(node);
        }
        return children.get(index);
    }

    /** {@inheritDoc} */
    @Override
    public int getChildCount(final Object parent) {

        final Node node = (Node) parent;
        List<Node> children = this.displayableNodes.get(parent);
        if (children == null) {
            children = addDisplayable(node);
        }
        return children.size();
    }

    /** {@inheritDoc} */
    @Override
    public int getIndexOfChild(final Object parent, final Object child) {

        final Node node = (Node) parent;
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

    /** {@inheritDoc} */
    @Override
    public Object getRoot() {
        return this.root;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isLeaf(final Object nd) {
        final Node node = (Node) nd;
        return !node.hasChildNodes();
    }

    /** {@inheritDoc} */
    @Override
    public void valueForPathChanged(final TreePath path, final Object newValue) {

    }

    /**
     * Adds a feature to the Displayable attribute of the DOMTreeModel object
     *
     * @param parent The feature to be added to the Displayable attribute
     * @return Returns
     */
    private List<Node> addDisplayable(final Node parent) {
        List<Node> children = this.displayableNodes.get(parent);
        if (children == null) {
            children = new ArrayList<>();
            this.displayableNodes.put(parent, children);
            final NodeList nl = parent.getChildNodes();
            for (int i = 0, len = nl.getLength(); i < len; i++) {
                final Node child = nl.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE ||
                        child.getNodeType() == Node.COMMENT_NODE ||
                        (child.getNodeType() == Node.TEXT_NODE && Strings.isNotBlank(child.getNodeValue().trim()))) {
                    children.add(child);
                }
            }
            return children;
        } else {
            return new ArrayList<>();
        }
    }

}