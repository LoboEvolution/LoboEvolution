/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
