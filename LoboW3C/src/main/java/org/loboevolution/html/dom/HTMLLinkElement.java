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
 * Reference information for external resources and the relationship of those
 * resources to a document and vice-versa. This object inherits all of the
 * properties and methods of the HTMLElement interface.
 *
 *
 *
 */
public interface HTMLLinkElement extends HTMLElement {

	/**
	 * <p>getAs.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getAs();

	/**
	 * <p>setAs.</p>
	 *
	 * @param as a {@link java.lang.String} object.
	 */
	void setAs(String as);

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
	 * <p>isDisabled.</p>
	 *
	 * @return a boolean.
	 */
	boolean isDisabled();

	/**
	 * <p>setDisabled.</p>
	 *
	 * @param disabled a boolean.
	 */
	void setDisabled(boolean disabled);

	/**
	 * Sets or retrieves a destination URL or an anchor point.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getHref();

	/**
	 * <p>setHref.</p>
	 *
	 * @param href a {@link java.lang.String} object.
	 */
	void setHref(String href);

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
	 * <p>getImageSizes.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getImageSizes();

	/**
	 * <p>setImageSizes.</p>
	 *
	 * @param imageSizes a {@link java.lang.String} object.
	 */
	void setImageSizes(String imageSizes);

	/**
	 * <p>getImageSrcset.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getImageSrcset();

	/**
	 * <p>setImageSrcset.</p>
	 *
	 * @param imageSrcset a {@link java.lang.String} object.
	 */
	void setImageSrcset(String imageSrcset);

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
	 * Sets or retrieves the relationship between the object and the destination of
	 * the link.
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
	 * Sets or retrieves the relationship between the object and the destination of
	 * the link.
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
	void setRev(String rev);

	/**
	 * <p>getSizes.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.DOMTokenList} object.
	 */
	DOMTokenList getSizes();

	/**
	 * Sets or retrieves the window or frame at which to target content.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getTarget();

	/**
	 * <p>setTarget.</p>
	 *
	 * @param target a {@link java.lang.String} object.
	 */
	void setTarget(String target);

	/**
	 * Sets or retrieves the MIME type of the object.
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
