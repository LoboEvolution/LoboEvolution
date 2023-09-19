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
 * Provides special properties and methods (beyond those of the regular object HTMLElement interface it also has available to it by inheritance) for manipulating the layout and presentation of &lt;area&gt; elements.
 *
 *
 *
 */
public interface HTMLAreaElement extends HTMLElement, HTMLHyperlinkElementUtils {


    /**
     * Sets or retrieves a text alternative to the graphic.
     *
     * @return a {@link java.lang.String} object.
     */
    String getAlt();

    
    /**
     * <p>setAlt.</p>
     *
     * @param alt a {@link java.lang.String} object.
     */
    void setAlt(String alt);

    /**
     * Sets or retrieves the coordinates of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getCoords();

    
    /**
     * <p>setCoords.</p>
     *
     * @param coords a {@link java.lang.String} object.
     */
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
     * Sets or gets whether clicks in this region cause action.
     *
     * @return a boolean.
     */
    @Deprecated
    boolean isNoHref();

    
    /**
     * <p>setNoHref.</p>
     *
     * @param noHref a boolean.
     */
    void setNoHref(boolean noHref);

    
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
     * <p>getRel.</p>
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
     * Sets or retrieves the shape of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    String getShape();

    
    /**
     * <p>setShape.</p>
     *
     * @param shape a {@link java.lang.String} object.
     */
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

}
