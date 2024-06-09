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

package org.loboevolution.menu.tools.developer.tool.inspector;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.nodeimpl.ElementImpl;
import org.loboevolution.html.node.Node;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.Serial;

/**
 * <p>DOMTreeCellRenderer class.</p>
 */
public class DOMTreeCellRenderer extends DefaultTreeCellRenderer {

    @Serial
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
    public Component getTreeCellRendererComponent(final JTree tree, Object value,
                                                  final boolean selected, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {

        final Node node = (Node) value;

        if (node.getNodeType() == Node.ELEMENT_NODE) {

            String cls = "";
            final ElementImpl element = (ElementImpl) node;
            if (element.hasAttributes()) {
                final Node cn = element.getAttributes().getNamedItem("class");
                if (cn != null) {
                    cls = " class='" + cn.getNodeValue() + "'";
                }
            }
            value = "<" + node.getNodeName() + cls + ">";

        }

        if (node.getNodeType() == Node.TEXT_NODE) {

            if (Strings.isNotBlank(node.getNodeValue().trim())) {
                value = "\"" + node.getNodeValue() + "\"";
            }
        }

        if (node.getNodeType() == Node.COMMENT_NODE) {
            value = "<!-- " + node.getNodeValue() + " -->";
        }

        final DefaultTreeCellRenderer tcr = (DefaultTreeCellRenderer) super.getTreeCellRendererComponent(tree, value, selected,
                                                                                            expanded, leaf, row, hasFocus);
        tcr.setOpenIcon(null);
        tcr.setClosedIcon(null);

        return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
    }
}