/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.html.dom.smil;

import org.htmlunit.cssparser.dom.DOMException;

/**
 * This interface is used by SMIL elements root-layout, top-layout and region.
 */
public interface ElementLayout {
    /**
     * <p>getTitle.</p>
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getTitle();

    /**
     * <p>setTitle.</p>
     *
     * @param title a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setTitle(String title) throws DOMException;

    /**
     * <p>getBackgroundColor.</p>
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getBackgroundColor();

    /**
     * <p>setBackgroundColor.</p>
     *
     * @param backgroundColor a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setBackgroundColor(String backgroundColor) throws DOMException;

    /**
     * <p>getHeight.</p>
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.Integer} object.
     */
    int getHeight();

    /**
     * <p>setHeight.</p>
     *
     * @param height a {@link java.lang.Integer} object.
     * @throws DOMException if any.
     */
    void setHeight(int height) throws DOMException;

    /**
     * <p>getWidth.</p>
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.Integer} object.
     */
    int getWidth();

    /**
     * <p>setWidth.</p>
     *
     * @param width a {@link java.lang.Integer} object.
     * @throws DOMException if any.
     */
    void setWidth(final int width) throws DOMException;

}
