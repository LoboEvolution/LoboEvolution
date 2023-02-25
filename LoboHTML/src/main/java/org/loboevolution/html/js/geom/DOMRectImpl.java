/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
     * @param right  a {@link java.lang.Integer} object.
     * @param bottom a {@link java.lang.Integer} object.
     * @param left   a {@link java.lang.Integer} object.
     */
    public DOMRectImpl(final int width, final int height,
                       final int top, final int right, final int bottom, final int left) {

        this.width = width;
        this.height = height;
        this.top = top;
        this.right = right;
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
