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
 * A &lt;style&gt; element. It inherits properties and methods from its parent, HTMLElement, and from LinkStyle.
 *
 *
 *
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
	 * @param disabled a boolean.
	 */
	void setDisabled(boolean disabled);


	/**
	 * <p>isDisabled.</p>
	 *
	 * @return a boolean.
	 */
	boolean isDisabled();

}
