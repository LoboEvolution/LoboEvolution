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
 * Provides special properties (beyond those of the regular HTMLElement object interface it inherits) for manipulating &lt;param&gt; elements, representing a pair of a key and a value that acts as a parameter for an &lt;object&gt; element.
 *
 *
 *
 */
public interface HTMLParamElement extends HTMLElement {

    /**
     * Sets or retrieves the name of an input parameter for an element.
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
     * Sets or retrieves the content type of the resource designated by the value attribute.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getType();

    
    /**
     * <p>setType.</p>
     *
     * @param type a {@link java.lang.String} object.
     */
    void setType(String type);

    /**
     * Sets or retrieves the value of an input parameter for an element.
     *
     * @return a {@link java.lang.String} object.
     */
    String getValue();

    
    /**
     * <p>setValue.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    void setValue(String value);

    /**
     * Sets or retrieves the data type of the value attribute.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getValueType();

    
    /**
     * <p>setValueType.</p>
     *
     * @param valueType a {@link java.lang.String} object.
     */
    void setValueType(String valueType);

}
