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


import org.loboevolution.html.node.css.CSSStyleSheet;

/**
 * A &lt;style&gt; element. It inherits properties and methods from its parent, HTMLElement, and from LinkStyle.
 */
public interface HTMLStyleElement extends HTMLElement {


    /**
     * Sets or retrieves the media type.
     *
     * @return a {@link java.lang.String} object.
     */
    String getMedia();

    
    /**
     * <p>setMedia.</p>
     *
     * @param media a {@link java.lang.String} object.
     */
    void setMedia(String media);

    /**
     * Retrieves the CSS language in which the style sheet is written.
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getType();

    
    /**
     * <p>setType.</p>
     *
     * @param type a {@link java.lang.String} object.
     */
    void setType(String type);


	/**
	 * <p>setDisabled.</p>
	 *
	 * @param disabled a {@link java.lang.Boolean} object.
	 */
	void setDisabled(boolean disabled);


	/**
	 * <p>isDisabled.</p>
	 *
	 * @return a {@link java.lang.Boolean} object.
	 */
	boolean isDisabled();

	/**
	 * <p>isDisabled.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.css.CSSStyleSheet} object.
	 */
	CSSStyleSheet getStyleSheet();


}
