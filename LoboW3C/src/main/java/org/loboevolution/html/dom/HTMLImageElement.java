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

import org.loboevolution.jsenum.Decoding;

/**
 * Provides special properties and methods for manipulating &lt;img&gt; elements.
 *
 *
 *
 */
public interface HTMLImageElement extends HTMLElement {
   
    /**
     * Sets or retrieves how the object is aligned with adjacent text.
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
    @Deprecated
    void setAlign(String align);

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
     * Specifies the properties of a border drawn around an object.
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
     * Retrieves whether the object is fully loaded.
     *
     * @return a boolean.
     */
    boolean isComplete();

    
    
    /**
     * <p>getCrossOrigin.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getCrossOrigin();

    
    /**
     * <p>setCrossOrigin.</p>
     *
     * @param crossOrigin a {@link java.lang.String} object.
     */
    void setCrossOrigin(String crossOrigin);

    
    /**
     * <p>getCurrentSrc.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getCurrentSrc();

    
    /**
     * <p>getDecoding.</p>
     *
     * @return a {@link org.loboevolution.jsenum.Decoding} object.
     */
    Decoding getDecoding();

    
    /**
     * <p>setDecoding.</p>
     *
     * @param decoding a {@link org.loboevolution.jsenum.Decoding} object.
     */
    void setDecoding(Decoding decoding);

    /**
     * Sets or retrieves the height of the object.
     *
     * @return a double.
     */
    double getHeight();

    
    /**
     * <p>setHeight.</p>
     *
     * @param height a double.
     */
    void setHeight(double height);

    /**
     * Sets or retrieves the width of the border to draw around the object.
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
     * Sets or retrieves whether the image is a server-side image map.
     *
     * @return a boolean.
     */
    boolean isIsMap();

    
    /**
     * <p>setIsMap.</p>
     *
     * @param isMap a boolean.
     */
    void setIsMap(boolean isMap);

    /**
     * Sets or retrieves a Uniform Resource Identifier (URI) to a long description of the object.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getLongDesc();

    
    /**
     * <p>setLongDesc.</p>
     *
     * @param longDesc a {@link java.lang.String} object.
     */
    void setLongDesc(String longDesc);

    /**
     * <p>getLowsrc.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getLowsrc();

    
    /**
     * <p>setLowsrc.</p>
     *
     * @param lowsrc a {@link java.lang.String} object.
     */
    void setLowsrc(String lowsrc);

    /**
     * Sets or retrieves the name of the object.
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
     * The original height of the image resource before sizing.
     *
     * @return a double.
     */
    double getNaturalHeight();

    /**
     * The original width of the image resource before sizing.
     *
     * @return a double.
     */
    double getNaturalWidth();

    
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
     * <p>getSizes.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getSizes();

    
    /**
     * <p>setSizes.</p>
     *
     * @param sizes a {@link java.lang.String} object.
     */
    void setSizes(String sizes);

    /**
     * The address or URL of the a media resource that is to be considered.
     *
     * @return a {@link java.lang.String} object.
     */
    String getSrc();

    
    /**
     * <p>setSrc.</p>
     *
     * @param src a {@link java.lang.String} object.
     */
    void setSrc(String src);

    
    /**
     * <p>getSrcset.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getSrcset();

    
    /**
     * <p>setSrcset.</p>
     *
     * @param srcset a {@link java.lang.String} object.
     */
    void setSrcset(String srcset);

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
     * Sets or retrieves the vertical margin for the object.
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
     * @return a double.
     */
    double getWidth();

    
    /**
     * <p>setWidth.</p>
     *
     * @param width a double.
     */
    void setWidth(double width);

    
    /**
     * <p>getX.</p>
     *
     * @return a double.
     */
    double getX();

    
    /**
     * <p>getY.</p>
     *
     * @return a double.
     */
    double getY();

}
