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
