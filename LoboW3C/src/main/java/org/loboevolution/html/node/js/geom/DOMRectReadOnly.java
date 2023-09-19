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

package org.loboevolution.html.node.js.geom;

/**
 * <p>DOMRectReadOnly interface.</p>
 */
public interface DOMRectReadOnly {

    /**
     * <p>getBottom.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getBottom();

    /**
     * <p>getHeight.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getHeight();

    /**
     * <p>getLeft.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getLeft();

    /**
     * <p>getRight.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getRight();

    /**
     * <p>getTop.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getTop();

    /**
     * <p>getWidth.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getWidth();

    /**
     * <p>getX.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getX();

    /**
     * <p>getY.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getY();


    /**
     * <p>fromRect.</p>
     *
     * @return a DOMRectReadOnly.
     */
    DOMRectReadOnly fromRect();

    /**
     * <p>fromRect.</p>
     *
     * @return a DOMRectReadOnly.
     */
    DOMRectReadOnly fromRect(DOMRectInit other);
}