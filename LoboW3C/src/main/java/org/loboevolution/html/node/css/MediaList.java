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
 * Media list interface
 */
public interface MediaList {

    /**
     * <p> getMediaText. </p>
     * @return a {@link java.lang.String} object.
     */
    String getMediaText();

    /**
     * <p> getLength. </p>
     * @return a {@link java.lang.Integer} object.
     */
    int getLength();

    /**
     * <p> item. </p>
     * @param index a {@link java.lang.Integer} object.
     * @return a {@link java.lang.String} object.
     */
    String item(int index);

    /**
     * <p> appendMedium. </p>
     * @param medium a {@link java.lang.String} object.
     */
    void appendMedium(String medium);

    /**
     * <p> deleteMedium. </p>
     * @param medium a {@link java.lang.String} object.
     */
    void deleteMedium(String medium);
}
