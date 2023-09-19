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
 * Contains descriptive metadata about a document. It inherits all of the properties and methods described in the HTMLElement interface.
 *
 *
 *
 */
public interface HTMLMetaElement extends HTMLElement {
  
    /**
     * Gets or sets meta-information to associate with httpEquiv or name.
     *
     * @return a {@link java.lang.String} object.
     */
    String getContent();

    
    /**
     * <p>setContent.</p>
     *
     * @param content a {@link java.lang.String} object.
     */
    void setContent(String content);

    /**
     * Gets or sets information used to bind the value of a content attribute of a meta element to an HTTP response header.
     *
     * @return a {@link java.lang.String} object.
     */
    String getHttpEquiv();

    
    /**
     * <p>setHttpEquiv.</p>
     *
     * @param httpEquiv a {@link java.lang.String} object.
     */
    void setHttpEquiv(String httpEquiv);

    /**
     * Sets or retrieves the value specified in the content attribute of the meta object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getName();

    
    /**
     * <p>setName.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    void setName(String name);

    /**
     * Sets or retrieves a scheme to be used in interpreting the value of a property specified for the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getScheme();

    
    /**
     * <p>setScheme.</p>
     *
     * @param scheme a {@link java.lang.String} object.
     */
    void setScheme(String scheme);

}
