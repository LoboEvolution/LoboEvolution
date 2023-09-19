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

package org.loboevolution.html.js.geom;

import org.loboevolution.html.node.js.geom.DOMRect;
import org.loboevolution.html.node.js.geom.DOMRectInit;
import org.loboevolution.html.node.js.geom.DOMRectReadOnly;

/**
 * <p>DOMRectImpl class.</p>
 */
public class DOMRectImpl implements DOMRect {

    /** The x. */
    private int x;

    /** The y. */
    private int y;

    /** The width. */
    private int width;

    /** The height. */
    private int height;

    /** The bottom. */
    private int bottom;

    /** The top. */
    private int top;

    /** The left. */
    private int left;

    /** The right. */
    private int right;

    /**
     * <p>Constructor for DOMRectImpl.</p>
     * @param width  a {@link java.lang.Integer} object.
     * @param height a {@link java.lang.Integer} object.
     * @param top    a {@link java.lang.Integer} object.
     * @param bottom a {@link java.lang.Integer} object.
     * @param left   a {@link java.lang.Integer} object.
     */
    public DOMRectImpl(final int width, final int height,
                       final int top, final int bottom, final int left) {

        this.width = width;
        this.height = height;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DOMRectReadOnly fromRect() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DOMRectReadOnly fromRect(DOMRectInit other) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getX() {
        if(x == 0) return getLeft();
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setX(int x) {
        this.x = x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getY() {
        if(y == 0) return getTop();
        return y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setY(int y) {
        this.y = y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * <p>getBottom.</p>
     *
     * @return a {@link Integer} object.
     */
    @Override
    public int getBottom() {
        if(bottom == 0) return getHeight() + getTop();
        return bottom;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * <p>getLeft.</p>
     *
     * @return a {@link Integer} object.
     */
    @Override
    public int getLeft() {
        return left;
    }

    /**
     * <p>getRight.</p>
     *
     * @return a {@link Integer} object.
     */
    @Override
    public int getRight() {
        if(right == 0) return getWidth() + getLeft();
        return right;
    }

    /**
     * <p>getTop.</p>
     *
     * @return a {@link Integer} object.
     */
    @Override
    public int getTop() {
        return this.top;
    }

    /**
     * <p>setBottom.</p>
     *
     * @param bottom a {@link Integer} object.
     */
    @Override
    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * <p>setLeft.</p>
     *
     * @param left a {@link Integer} object.
     */
    @Override
    public void setLeft(int left) {
        this.left = left;
    }

    /**
     * <p>setRight.</p>
     *
     * @param right a {@link Integer} object.
     */
    @Override
    public void setRight(int right) {
        this.right = right;
    }

    /**
     * <p>setTop.</p>
     *
     * @param top a {@link Integer} object.
     */
    @Override
    public void setTop(int top) {
        this.top = top;
    }

    @Override
    public String toString() {
        return "[object DOMRect]";
    }
}
