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
 * &lt;option&gt; elements and inherits all classes and methods of the HTMLElement interface.
 */
public interface HTMLOptionElement extends HTMLElement {

    /**
     * Sets or retrieves the status of an option.
     *
     * @return a boolean.
     */
    boolean isDefaultSelected();


    /**
     * <p>setDefaultSelected.</p>
     *
     * @param defaultSelected a boolean.
     */
    void setDefaultSelected(boolean defaultSelected);


    /**
     * <p>isDisabled.</p>
     *
     * @return a boolean.
     */
    boolean isDisabled();


    /**
     * <p>setDisabled.</p>
     *
     * @param disabled a boolean.
     */
    void setDisabled(boolean disabled);

    /**
     * Retrieves a reference to the form that the object is embedded in.
     *
     * @return a {@link org.loboevolution.html.dom.HTMLFormElement} object.
     */
    HTMLFormElement getForm();

    /**
     * Sets or retrieves the ordinal position of an option in a list box.
     *
     * @return a int.
     */
    int getIndex();

    /**
     * Sets or retrieves a value that you can use to implement your own label functionality for the object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getLabel();


    /**
     * <p>setLabel.</p>
     *
     * @param label a {@link java.lang.String} object.
     */
    void setLabel(String label);

    /**
     * Sets or retrieves whether the option in the list box is the default item.
     *
     * @return a boolean.
     */
    Boolean isSelected();


    /**
     * <p>setSelected.</p>
     *
     * @param selected a boolean.
     */
    void setSelected(Object selected);

    /**
     * Sets or retrieves the text string specified by the option tag.
     *
     * @return a {@link java.lang.String} object.
     */
    String getText();


    /**
     * <p>setText.</p>
     *
     * @param text a {@link java.lang.String} object.
     */
    void setText(String text);

    /**
     * Sets or retrieves the value which is returned to the server when the form control is submitted.
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

}
