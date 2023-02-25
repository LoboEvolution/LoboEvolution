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
 * CSS style declaration interface
 */
public interface CSSStyleDeclaration extends CSS3Properties {

    /**
     * <p> getCssText. </p>
     * @return a {@link java.lang.String} object.
     */
    String getCssText();


    /**
     * <p> getPropertyValue. </p>
     * @param property a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    String getPropertyValue(String property);

    /**
     * <p> getPropertyPriority. </p>
     * @param property a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    String getPropertyPriority(String property);

    /**
     * <p> getCssFloat. </p>
     * @return a {@link java.lang.String} object.
     */
    String getCssFloat();

    /**
     * <p> item. </p>
     * @param index a {@link java.lang.Integer} object.
     * @return a {@link java.lang.String} object.
     */
    String item(int index);

    /**
     * <p> removeProperty. </p>
     * @param property a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    String removeProperty(String property);

    /**
     * <p> getLength. </p>
     * @return a {@link java.lang.Integer} object.
     */
    int getLength();

    /**
     * <p> getParentRule. </p>
     * @return a {@link org.loboevolution.html.node.css.CSSRule} object.
     */
    CSSRule getParentRule();

    /**
     * <p> setProperty. </p>
     * @param propertyName a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     * @param priority a {@link java.lang.String} object.
     */
    void setProperty(String propertyName, String value, String priority);

    /**
     * <p> setProperty. </p>
     * @param propertyName a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     */
    void setProperty(String propertyName, String value);


    /**
     * <p> setCssText. </p>
     * @param text a {@link java.lang.String} object.
     */
    void setCssText(String text);

    /**
     * <p>Setter for the field overlayColor.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    void setOverlayColor(String value);

    /**
     * <p>Getter for the field overlayColor.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getOverlayColor();
}
