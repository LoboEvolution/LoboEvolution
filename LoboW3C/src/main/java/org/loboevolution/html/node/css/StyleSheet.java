/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

package org.loboevolution.html.node.css;

import org.loboevolution.html.node.Element;

/**
 * <p>Style Sheet interface.</p>
 */
public interface StyleSheet {

    /**
     * <p> getType. </p>
     * @return a {@link java.lang.String} object.
     */
    String getType();

    /**
     * <p> getHref. </p>
     * @return a {@link java.lang.String} object.
     */
    String getHref();

    /**
     * <p> getOwnerNode. </p>
     * @return a {@link org.loboevolution.html.node.Element} object.
     */
    Element getOwnerNode();

    /**
     * <p> parentStyleSheet. </p>
     * @return a {@link org.loboevolution.html.node.css.CSSStyleSheet} object.
     */
    CSSStyleSheet parentStyleSheet();

    /**
     * <p> getMedia. </p>
     * @return a {@link org.loboevolution.html.node.css.MediaList} object.
     */
    MediaList getMedia();

    /**
     * <p> getDisabled. </p>
     * @return a {@link java.lang.Boolean} object.
     */
    boolean getDisabled();


}
