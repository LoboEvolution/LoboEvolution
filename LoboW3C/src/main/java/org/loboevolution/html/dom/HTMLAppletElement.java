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
