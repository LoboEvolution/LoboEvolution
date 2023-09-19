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
 * <p>HTMLAppletElement interface.</p>
 *
 *
 *
 */
public interface HTMLAppletElement extends HTMLElement {

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
     * Sets or retrieves a text alternative to the graphic.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getAlt();

    
    /**
     * <p>setAlt.</p>
     *
     * @param alt a {@link java.lang.String} object.
     */
    void setAlt(String alt);

    /**
     * Sets or retrieves a character string that can be used to implement your own archive functionality for the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getArchive();

    
    /**
     * <p>setArchive.</p>
     *
     * @param archive a {@link java.lang.String} object.
     */
    void setArchive(String archive);

    /**
     * <p>getCode.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getCode();

    
    /**
     * <p>setCode.</p>
     *
     * @param code a {@link java.lang.String} object.
     */
    void setCode(String code);

    /**
     * Sets or retrieves the URL of the component.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getCodeBase();

    
    /**
     * <p>setCodeBase.</p>
     *
     * @param codeBase a {@link java.lang.String} object.
     */
    void setCodeBase(String codeBase);

    
    
    /**
     * <p>getForm.</p>
     *
     * @return a {@link org.loboevolution.html.dom.HTMLFormElement} object.
     */
    HTMLFormElement getForm();

    /**
     * Sets or retrieves the height of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getHeight();

    
    /**
     * <p>setHeight.</p>
     *
     * @param height a {@link java.lang.String} object.
     */
    void setHeight(String height);

    /**
     * <p>getHspace.</p>
     *
     * @return a double.
     */
    @Deprecated
    double getHspace();

    
    /**
     * <p>setHspace.</p>
     *
     * @param hspace a double.
     */
    void setHspace(double hspace);

    /**
     * Sets or retrieves the shape of the object.
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
     * <p>getObject.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getObject();

    
    /**
     * <p>setObject.</p>
     *
     * @param object a {@link java.lang.String} object.
     */
    void setObject(String object);

    /**
     * <p>getVspace.</p>
     *
     * @return a double.
     */
    @Deprecated
    double getVspace();

    
    /**
     * <p>setVspace.</p>
     *
     * @param vspace a double.
     */
    void setVspace(double vspace);

    /**
     * <p>getWidth.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getWidth();

    
    /**
     * <p>setWidth.</p>
     *
     * @param width a {@link java.lang.String} object.
     */
    void setWidth(String width);

}
