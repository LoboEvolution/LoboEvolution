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

/**
 * A CSS rule interface
 */
public interface CSSRule {

    /**
     * <p> getCssText. </p>
     * @return a {@link java.lang.String} object.
     */
    String getCssText();

    /**
     * <p> getParentRule. </p>
     * @return a {@link org.loboevolution.html.node.css.CSSRule} object.
     */
    CSSRule getParentRule();


    /**
     * <p> parentStyleSheet. </p>
     * @return a {@link org.loboevolution.html.node.css.CSSStyleSheet} object.
     */
    CSSStyleSheet getParentStyleSheet();

    /**
     * <p> getType. </p>
     * @return a {@link java.lang.Integer} object.
     */
    int getType();
}
