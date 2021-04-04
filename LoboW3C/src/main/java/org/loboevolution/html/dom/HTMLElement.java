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

import org.loboevolution.html.node.Element;

/**
 * Any HTML element. Some elements directly implement this interface, while
 * others implement it via an interface that inherits it.
 *
 *
 *
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
	 * @return a int.
	 */
	int getOffsetTop();

	/**
	 * <p>getOffsetWidth.</p>
	 *
	 * @return a int.
	 */
	int getOffsetWidth();

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
