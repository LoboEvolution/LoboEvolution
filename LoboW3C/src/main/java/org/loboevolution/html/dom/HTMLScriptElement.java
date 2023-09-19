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
