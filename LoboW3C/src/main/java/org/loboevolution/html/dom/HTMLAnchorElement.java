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


import org.loboevolution.html.node.DOMTokenList;

/**
 * Hyperlink elements and provides special properties and methods (beyond those of the regular HTMLElement object interface that they inherit from) for manipulating the layout and presentation of such elements.
 *
 *
 *
 */
public interface HTMLAnchorElement extends HTMLElement, HTMLHyperlinkElementUtils {


    /**
     * Sets or retrieves the character set used to encode the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getCharset();

    /**
     * <p>setCharset.</p>
     *
     * @param charset a {@link java.lang.String} object.
     */
    @Deprecated
    void setCharset(String charset);

    /**
     * Sets or retrieves the coordinates of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getCoords();

    /**
     * <p>setCoords.</p>
     *
     * @param coords a {@link java.lang.String} object.
     */
    @Deprecated
    void setCoords(String coords);

    
    /**
     * <p>getDownload.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getDownload();

    
    /**
     * <p>setDownload.</p>
     *
     * @param download a {@link java.lang.String} object.
     */
    void setDownload(String download);

    /**
     * Sets or retrieves the language code of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getHreflang();

    
    /**
     * <p>setHreflang.</p>
     *
     * @param hreflang a {@link java.lang.String} object.
     */
    void setHreflang(String hreflang);

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
    @Deprecated
    void setName(String name);

    
    /**
     * <p>getPing.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getPing();

    
    /**
     * <p>setPing.</p>
     *
     * @param ping a {@link java.lang.String} object.
     */
    void setPing(String ping);

    
    /**
     * <p>getReferrerPolicy.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getReferrerPolicy();

    
    /**
     * <p>setReferrerPolicy.</p>
     *
     * @param referrerPolicy a {@link java.lang.String} object.
     */
    void setReferrerPolicy(String referrerPolicy);

    /**
     * Sets or retrieves the relationship between the object and the destination of the link.
     *
     * @return a {@link java.lang.String} object.
     */
    String getRel();

    
    /**
     * <p>setRel.</p>
     *
     * @param rel a {@link java.lang.String} object.
     */
    void setRel(String rel);

    
    /**
     * <p>getRelList.</p>
     *
     * @return a {@link org.loboevolution.html.node.DOMTokenList} object.
     */
    DOMTokenList getRelList();

    /**
     * Sets or retrieves the relationship between the object and the destination of the link.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getRev();

    /**
     * <p>setRev.</p>
     *
     * @param rev a {@link java.lang.String} object.
     */
    @Deprecated
    void setRev(String rev);

    /**
     * Sets or retrieves the shape of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getShape();

    /**
     * <p>setShape.</p>
     *
     * @param shape a {@link java.lang.String} object.
     */
    @Deprecated
    void setShape(String shape);

    /**
     * Sets or retrieves the window or frame at which to target content.
     *
     * @return a {@link java.lang.String} object.
     */
    String getTarget();

    
    /**
     * <p>setTarget.</p>
     *
     * @param target a {@link java.lang.String} object.
     */
    void setTarget(String target);

    /**
     * Retrieves or sets the text of the object as a string.
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

}
