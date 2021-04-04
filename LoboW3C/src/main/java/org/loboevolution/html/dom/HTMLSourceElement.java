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
 * Provides special properties (beyond the regular HTMLElement object interface it also has available to it by inheritance) for manipulating &lt;source&gt; elements.
 *
 *
 *
 */
public interface HTMLSourceElement extends HTMLElement {

    /**
     * Gets or sets the intended media type of the media source.
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
     * Gets or sets the MIME type of a media resource.
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
