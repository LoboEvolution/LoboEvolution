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
 * Gives access to properties specific to &lt;label&gt; elements. It inherits methods and properties from the base HTMLElement interface.
 *
 *
 *
 */
public interface HTMLLabelElement extends HTMLElement {
  
    
    
    /**
     * <p>getControl.</p>
     *
     * @return a {@link org.loboevolution.html.dom.HTMLElement} object.
     */
    HTMLElement getControl();

    /**
     * Retrieves a reference to the form that the object is embedded in.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLFormElement} object.
     */
    HTMLFormElement getForm();

    /**
     * Sets or retrieves the object to which the given label object is assigned.
     *
     * @return a {@link java.lang.String} object.
     */
    String getHtmlFor();

    
    /**
     * <p>setHtmlFor.</p>
     *
     * @param htmlFor a {@link java.lang.String} object.
     */
    void setHtmlFor(String htmlFor);

}
