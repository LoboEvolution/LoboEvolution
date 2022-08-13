/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
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

package org.loboevolution.menu.tools.developer.tool.inspector;

import org.loboevolution.html.dom.nodeimpl.ElementImpl;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeType;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * <p>DOMTreeCellRenderer class.</p>
 */
public class DOMTreeCellRenderer extends DefaultTreeCellRenderer {

    private static final long serialVersionUID = 1L;

    /**
     * Gets the treeCellRendererComponent attribute of the DOMTreeCellRenderer
     * object
     *
     * @param tree a {@link javax.swing.JTree} object.
     * @param value a {@link java.lang.Object} object.
     * @param selected a {@link java.lang.Boolean} object.
     * @param expanded a {@link java.lang.Boolean} object.
     * @param leaf     a {@link java.lang.Boolean} object.
     * @param row      a {@link java.lang.Integer} object
     * @param hasFocus a {@link java.lang.Boolean} object.
     * @return The treeCellRendererComponent value
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        Node node = (Node) value;

        if (node.getNodeType() == NodeType.ELEMENT_NODE) {

            String cls = "";
            ElementImpl element = (ElementImpl) node;
            if (element.hasAttributes()) {
                Node cn = element.getAttributes().getNamedItem("class");
                if (cn != null) {
                    cls = " class='" + cn.getNodeValue() + "'";
                }
            }
            value = "<" + node.getNodeName() + cls + ">";

        }

        if (node.getNodeType() == NodeType.TEXT_NODE) {

            if (node.getNodeValue().trim().length() > 0) {
                value = "\"" + node.getNodeValue() + "\"";
            }
        }

        if (node.getNodeType() == NodeType.COMMENT_NODE) {
            value = "<!-- " + node.getNodeValue() + " -->";
        }

        DefaultTreeCellRenderer tcr = (DefaultTreeCellRenderer) super.getTreeCellRendererComponent(tree, value, selected,
                                                                                            expanded, leaf, row, hasFocus);
        tcr.setOpenIcon(null);
        tcr.setClosedIcon(null);

        return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
    }
}