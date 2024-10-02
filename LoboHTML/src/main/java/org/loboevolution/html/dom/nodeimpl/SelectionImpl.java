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

package org.loboevolution.html.dom.nodeimpl;

import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.ranges.Range;
import org.loboevolution.html.node.Selection;
import org.loboevolution.js.AbstractScriptableDelegate;

/**
 * <p>SelectionImpl class.</p>
 */
public class SelectionImpl extends AbstractScriptableDelegate implements Selection {

    @Override
    public Node getAnchorNode() {
        return null;
    }

    @Override
    public int getAnchorOffset() {
        return 0;
    }

    @Override
    public Node getFocusNode() {
        return null;
    }

    @Override
    public int getFocusOffset() {
        return 0;
    }

    @Override
    public boolean isIsCollapsed() {
        return false;
    }

    @Override
    public int getRangeCount() {
        return 0;
    }

    @Override
    public String getType() {
        return "";
    }

    @Override
    public void addRange(Range range) {

    }

    @Override
    public void collapse(Node node, int offset) {

    }

    @Override
    public void collapse(Node node) {

    }

    @Override
    public void collapseToEnd() {

    }

    @Override
    public void collapseToStart() {

    }

    @Override
    public boolean containsNode(Node node, boolean allowPartialContainment) {
        return false;
    }

    @Override
    public boolean containsNode(Node node) {
        return false;
    }

    @Override
    public void deleteFromDocument() {

    }

    @Override
    public void empty() {

    }

    @Override
    public void extend(Node node, int offset) {

    }

    @Override
    public void extend(Node node) {

    }

    @Override
    public Range getRangeAt(int index) {
        return null;
    }

    @Override
    public void removeAllRanges() {

    }

    @Override
    public void removeRange(Range range) {

    }

    @Override
    public void selectAllChildren(Node node) {

    }

    @Override
    public void setBaseAndExtent(Node anchorNode, int anchorOffset, Node focusNode, int focusOffset) {

    }

    @Override
    public void setPosition(Node node, int offset) {

    }

    @Override
    public void setPosition(Node node) {

    }
}
