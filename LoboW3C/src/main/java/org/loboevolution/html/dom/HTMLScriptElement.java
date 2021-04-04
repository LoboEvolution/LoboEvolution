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
 * HTML &lt;script&gt; elements expose the HTMLScriptElement interface, which provides special properties and methods for manipulating the behavior and execution of &lt;script&gt; elements (beyond the inherited HTMLElement interface).
 *
 *
 *
 */
public interface HTMLScriptElement extends HTMLElement {

    /**
     * <p>isAsync.</p>
     *
     * @return a boolean.
     */
    boolean isAsync();

    /**
     * <p>setAsync.</p>
     *
     * @param async a boolean.
     */
    void setAsync(boolean async);

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
    void setCharset(String charset);

    
    
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
     * Sets or retrieves the status of the script.
     *
     * @return a boolean.
     */
    boolean isDefer();

    
    /**
     * <p>setDefer.</p>
     *
     * @param defer a boolean.
     */
    void setDefer(boolean defer);

    /**
     * Sets or retrieves the event for which the script is written.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getEvent();

    
    /**
     * <p>setEvent.</p>
     *
     * @param event a {@link java.lang.String} object.
     */
    void setEvent(String event);

    /**
     * Sets or retrieves the object that is bound to the event script.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getHtmlFor();

    
    /**
     * <p>setHtmlFor.</p>
     *
     * @param htmlFor a {@link java.lang.String} object.
     */
    void setHtmlFor(String htmlFor);

    
    /**
     * <p>getIntegrity.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getIntegrity();

    
    /**
     * <p>setIntegrity.</p>
     *
     * @param integrity a {@link java.lang.String} object.
     */
    void setIntegrity(String integrity);

    
    /**
     * <p>isNoModule.</p>
     *
     * @return a boolean.
     */
    boolean isNoModule();

    
    /**
     * <p>setNoModule.</p>
     *
     * @param noModule a boolean.
     */
    void setNoModule(boolean noModule);

    
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
     * Retrieves the URL to an external file that contains the source code or data.
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
     * Sets or retrieves the MIME type for the associated scripting engine.
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
