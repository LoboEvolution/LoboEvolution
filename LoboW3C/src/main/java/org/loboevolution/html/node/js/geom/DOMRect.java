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

package org.loboevolution.html.node.js.geom;

/**
 * <p>DOMRect interface.</p>
 */
public interface DOMRect extends DOMRectReadOnly {

    /**
     * <p>setBottom.</p>
     *
     * @param bottom a {@link java.lang.Integer} object.
     */
    void setBottom(int bottom);

    /**
     * <p>setHeight.</p>
     *
     * @param height a {@link java.lang.Integer} object.
     */
    void setHeight(int height);

    /**
     * <p>setLeft.</p>
     *
     * @param left a {@link java.lang.Integer} object.
     */
    void setLeft(int left);

    /**
     * <p>setRight.</p>
     *
     * @param right a {@link java.lang.Integer} object.
     */
    void setRight(int right);

    /**
     * <p>setTop.</p>
     *
     * @param top a {@link java.lang.Integer} object.
     */
    void setTop(int top);

    /**
     * <p>setWidth.</p>
     *
     * @param width a {@link java.lang.Integer} object.
     */
    void setWidth(int width);

    /**
     * <p>setX.</p>
     *
     * @param x a {@link java.lang.Integer} object.
     */
    void setX(int x);

    /**
     * <p>setY.</p>
     *
     * @param y a {@link java.lang.Integer} object.
     */
    void setY(int y);
}
