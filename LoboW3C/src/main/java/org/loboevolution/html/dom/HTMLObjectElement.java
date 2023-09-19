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
import org.loboevolution.html.node.ValidityState;

/**
 * Provides special properties and methods (beyond those on the HTMLElement interface it also has available to it by inheritance) for manipulating the layout and presentation of &lt;object&gt; element, representing external resources.
 *
 *
 *
 */
public interface HTMLObjectElement extends HTMLElement {

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
     * <p>getBorder.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getBorder();

    
    /**
     * <p>setBorder.</p>
     *
     * @param border a {@link java.lang.String} object.
     */
    void setBorder(String border);

    /**
     * Sets or retrieves the URL of the file containing the compiled Java class.
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
     * Sets or retrieves the Internet media type for the code associated with the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getCodeType();

    
    /**
     * <p>setCodeType.</p>
     *
     * @param codeType a {@link java.lang.String} object.
     */
    void setCodeType(String codeType);

    /**
     * Retrieves the document object of the page or frame.
     *
     * @return a {@link org.loboevolution.html.node.Document} object.
     */
    Document getContentDocument();

        
  //  WindowProxy getContentWindow();

    /**
     * Sets or retrieves the URL that references the data of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getData();

    
    /**
     * <p>setData.</p>
     *
     * @param data a {@link java.lang.String} object.
     */
    void setData(String data);

    /**
     * <p>isDeclare.</p>
     *
     * @return a boolean.
     */
    @Deprecated
    boolean isDeclare();

    
    /**
     * <p>setDeclare.</p>
     *
     * @param declare a boolean.
     */
    void setDeclare(boolean declare);

    /**
     * Retrieves a reference to the form that the object is embedded in.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLFormElement} object.
     */
    HTMLFormElement getForm();

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
     * Sets or retrieves the name of the object.
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
     * Sets or retrieves a message to be displayed while an object is loading.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getStandby();

    
    /**
     * <p>setStandby.</p>
     *
     * @param standby a {@link java.lang.String} object.
     */
    void setStandby(String standby);

    /**
     * Sets or retrieves the MIME type of the object.
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
     * Sets or retrieves the URL, often with a bookmark extension (#name), to use as a client-side image map.
     *
     * @return a {@link java.lang.String} object.
     */
    String getUseMap();

    
    /**
     * <p>setUseMap.</p>
     *
     * @param useMap a {@link java.lang.String} object.
     */
    void setUseMap(String useMap);

    /**
     * Returns the error message that would be displayed if the user submits the form, or an empty string if no error message. It also triggers the standard error message, such as "this is a required field". The result is that the user sees validation messages without actually submitting.
     *
     * @return a {@link java.lang.String} object.
     */
    String getValidationMessage();

    /**
     * Returns a  ValidityState object that represents the validity states of an element.
     *
     * @return a {@link org.loboevolution.html.node.ValidityState} object.
     */
    ValidityState getValidity();

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
     * Returns whether an element will successfully validate based on forms validation rules and constraints.
     *
     * @return a boolean.
     */
    boolean isWillValidate();

    /**
     * Returns whether a form will validate when it is submitted, without having to submit it.
     *
     * @return a boolean.
     */
    boolean checkValidity();

    
    /**
     * <p>getSVGDocument.</p>
     *
     * @return a {@link org.loboevolution.html.node.Document} object.
     */
    Document getSVGDocument();

    /**
     * <p>reportValidity.</p>
     *
     * @return a boolean.
     */
    boolean reportValidity();

    /**
     * Sets a custom error message that is displayed when a form is submitted.
     *
     * @param error Sets a custom error message that is displayed when a form is submitted.
     */
    void setCustomValidity(String error);

}
