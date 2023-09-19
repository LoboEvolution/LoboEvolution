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

import org.loboevolution.html.node.Element;

/**
 * Any HTML element. Some elements directly implement this interface, while
 * others implement it via an interface that inherits it.
 */
public interface HTMLElement extends Element {

	/**
	 * <p>getAccessKey.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAccessKey();

	/**
	 * <p>getAccessKeyLabel.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAccessKeyLabel();

	/**
	 * <p>getAutocapitalize.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAutocapitalize();

	/**
	 * <p>getDir.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getDir();

	/**
	 * <p>getContentEditable.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getContentEditable();

	/**
	 * <p>getInnerText.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getInnerText();

	/**
	 * <p>getLang.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getLang();

	/**
	 * <p>getTitle.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getTitle();

	/**
	 * <p>getOffsetHeight.</p>
	 *
	 * @return a double.
	 */
	double getOffsetHeight();

	/**
	 * <p>getOffsetLeft.</p>
	 *
	 * @return a double.
	 */
	double getOffsetLeft();

	/**
	 * <p>getOffsetParent.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Element} object.
	 */
	Element getOffsetParent();

	/**
	 * <p>getOffsetTop.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	int getOffsetTop();

	/**
     * <p>getOffsetWidth.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
	Integer getOffsetWidth();

	/**
	 * <p>isSpellcheck.</p>
	 *
	 * @return a boolean.
	 */
	boolean isSpellcheck();

	/**
	 * <p>isDraggable.</p>
	 *
	 * @return a boolean.
	 */
	boolean isDraggable();

	/**
	 * <p>isHidden.</p>
	 *
	 * @return a boolean.
	 */
	boolean isHidden();

	/**
	 * <p>isTranslate.</p>
	 *
	 * @return a boolean.
	 */
	boolean isTranslate();

	/**
	 * <p>setAccessKey.</p>
	 *
	 * @param accessKey a {@link java.lang.String} object.
	 */
	void setAccessKey(String accessKey);

	/**
	 * <p>setAutocapitalize.</p>
	 *
	 * @param autocapitalize a {@link java.lang.String} object.
	 */
	void setAutocapitalize(String autocapitalize);

	/**
	 * <p>setDir.</p>
	 *
	 * @param dir a {@link java.lang.String} object.
	 */
	void setDir(String dir);

	/**
	 * <p>setDraggable.</p>
	 *
	 * @param draggable a boolean.
	 */
	void setDraggable(boolean draggable);

	/**
	 * <p>setHidden.</p>
	 *
	 * @param hidden a boolean.
	 */
	void setHidden(boolean hidden);

	/**
	 * <p>setInnerText.</p>
	 *
	 * @param innerText a {@link java.lang.String} object.
	 */
	void setInnerText(String innerText);

	/**
	 * <p>setLang.</p>
	 *
	 * @param lang a {@link java.lang.String} object.
	 */
	void setLang(String lang);

	/**
	 * <p>setSpellcheck.</p>
	 *
	 * @param spellcheck a boolean.
	 */
	void setSpellcheck(boolean spellcheck);

	/**
	 * <p>setContentEditable.</p>
	 *
	 * @param contenteditable a {@link java.lang.String} object.
	 */
	void setContentEditable(String contenteditable);

	/**
	 * <p>setTranslate.</p>
	 *
	 * @param translate a boolean.
	 */
	void setTranslate(boolean translate);

	/**
	 * <p>setTitle.</p>
	 *
	 * @param title a {@link java.lang.String} object.
	 */
	void setTitle(String title);

	/**
	 * <p>click.</p>
	 */
	void click();
}
