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

package org.loboevolution.html.node.css;

/**
 * CSS style declaration interface
 */
public interface CSSStyleDeclaration extends CSS3Properties {

    /**
     * <p> getCssText. </p>
     * @return a {@link java.lang.String} object.
     */
    String getCssText();


    /**
     * <p> getPropertyValue. </p>
     * @param property a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    String getPropertyValue(String property);

    /**
     * <p> getPropertyPriority. </p>
     * @param property a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    String getPropertyPriority(String property);

    /**
     * <p> getCssFloat. </p>
     * @return a {@link java.lang.String} object.
     */
    String getCssFloat();

    /**
     * <p> item. </p>
     * @param index a {@link java.lang.Integer} object.
     * @return a {@link java.lang.String} object.
     */
    String item(int index);

    /**
     * <p> removeProperty. </p>
     * @param property a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    String removeProperty(String property);

    /**
     * <p> getLength. </p>
     * @return a {@link java.lang.Integer} object.
     */
    int getLength();

    /**
     * <p> getParentRule. </p>
     * @return a {@link org.loboevolution.html.node.css.CSSRule} object.
     */
    CSSRule getParentRule();

    /**
     * <p> setProperty. </p>
     * @param propertyName a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     * @param priority a {@link java.lang.String} object.
     */
    void setProperty(String propertyName, String value, String priority);

    /**
     * <p> setProperty. </p>
     * @param propertyName a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     */
    void setProperty(String propertyName, String value);


    /**
     * <p> setCssText. </p>
     * @param text a {@link java.lang.String} object.
     */
    void setCssText(String text);

    /**
     * <p>Setter for the field overlayColor.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    void setOverlayColor(String value);

    /**
     * <p>Getter for the field overlayColor.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getOverlayColor();
}
