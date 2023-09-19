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

/**
 * Provides special properties (beyond the regular HTMLElement object interface it also has available to it by inheritance) for manipulating &lt;source&gt; elements.
 *
 *
 *
 */
public interface HTMLSourceElement extends HTMLElement {

    /**
     * Gets or sets the intended media type of the media source.
     *
     * @return a {@link java.lang.String} object.
     */
    String getMedia();

    
    /**
     * <p>setMedia.</p>
     *
     * @param media a {@link java.lang.String} object.
     */
    void setMedia(String media);

    
    /**
     * <p>getSizes.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getSizes();

    
    /**
     * <p>setSizes.</p>
     *
     * @param sizes a {@link java.lang.String} object.
     */
    void setSizes(String sizes);

    /**
     * The address or URL of the a media resource that is to be considered.
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
     * <p>getSrcset.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getSrcset();

    
    /**
     * <p>setSrcset.</p>
     *
     * @param srcset a {@link java.lang.String} object.
     */
    void setSrcset(String srcset);

    /**
     * Gets or sets the MIME type of a media resource.
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

}
