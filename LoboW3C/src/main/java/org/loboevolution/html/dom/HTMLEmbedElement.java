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

package org.loboevolution.html.dom;

import org.loboevolution.html.node.Document;

/**
 * Provides special properties (beyond the regular HTMLElement interface it also has available to it by inheritance) for manipulating &lt;embed&gt; elements.
 *
 *
 *
 */
public interface HTMLEmbedElement extends HTMLElement {


    /**
     * <p>getAlign.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getAlign();

    
    /**
     * <p>setAlign.</p>
     *
     * @param align a {@link java.lang.String} object.
     */
    void setAlign(String align);

    /**
     * Sets or retrieves the height of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getHeight();

    
    /**
     * <p>setHeight.</p>
     *
     * @param height a {@link java.lang.String} object.
     */
    void setHeight(String height);

    /**
     * Sets or retrieves the name of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getName();

    
    /**
     * <p>setName.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    void setName(String name);

    /**
     * Sets or retrieves a URL to be loaded by the object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getSrc();

    
    /**
     * <p>setSrc.</p>
     *
     * @param src a {@link java.lang.String} object.
     */
    void setSrc(String src);

    
    /**
     * <p>getType.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getType();

    
    /**
     * <p>setType.</p>
     *
     * @param type a {@link java.lang.String} object.
     */
    void setType(String type);

    /**
     * Sets or retrieves the width of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getWidth();

    
    /**
     * <p>setWidth.</p>
     *
     * @param width a {@link java.lang.String} object.
     */
    void setWidth(String width);

    
    /**
     * <p>getSVGDocument.</p>
     *
     * @return a {@link org.loboevolution.html.node.Document} object.
     */
    Document getSVGDocument();

}
