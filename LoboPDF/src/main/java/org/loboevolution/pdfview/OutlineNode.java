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
package org.loboevolution.pdfview;

import org.loboevolution.pdfview.action.PDFAction;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * <p>OutlineNode class.</p>
 */
public class OutlineNode extends DefaultMutableTreeNode {

    private static final long serialVersionUID = 1L;

    private final String title;

    /**
     * Create a new outline node
     *
     * @param title the node's visible name in the tree
     */
    public OutlineNode(final String title) {
        this.title = title;
    }

    /**
     * Get the PDF action associated with this node
     *
     * @return a {@link org.loboevolution.pdfview.action.PDFAction} object.
     */
    public PDFAction getAction() {
        return (PDFAction) getUserObject();
    }

    /**
     * Set the PDF action associated with this node
     *
     * @param action a {@link org.loboevolution.pdfview.action.PDFAction} object.
     */
    public void setAction(final PDFAction action) {
        setUserObject(action);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Return the node's visible name in the tree
     */
    @Override
    public String toString() {
        return this.title;
    }
}
