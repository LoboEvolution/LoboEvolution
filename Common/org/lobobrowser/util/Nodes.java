/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.util;

import org.w3c.dom.Node;

/**
 * The Class Nodes.
 */
public class Nodes {
    /**
     * Gets the common ancestor.
     *
     * @param node1
     *            the node1
     * @param node2
     *            the node2
     * @return the common ancestor
     */
    public static Node getCommonAncestor(Node node1, Node node2) {
        if ((node1 == null) || (node2 == null)) {
            return null;
        }
        Node checkNode = node1;
        while (!isSameOrAncestorOf(checkNode, node2)) {
            checkNode = checkNode.getParentNode();
            if (checkNode == null) {
                return null;
            }
        }
        return checkNode;
    }

    /**
     * Checks if is same or ancestor of.
     *
     * @param node
     *            the node
     * @param child
     *            the child
     * @return true, if is same or ancestor of
     */
    public static boolean isSameOrAncestorOf(Node node, Node child) {
        if (child.isSameNode(node)) {
            return true;
        }
        Node parent = child.getParentNode();
        if (parent == null) {
            return false;
        }
        return isSameOrAncestorOf(node, parent);
    }
}
