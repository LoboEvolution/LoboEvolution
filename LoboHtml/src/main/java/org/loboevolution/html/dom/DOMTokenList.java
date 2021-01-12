/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.dom;

/**
 * <p>DOMTokenList interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface DOMTokenList {

    /**
     * Gets the length.
     *
     * @return the length
     */
    int getLength();

    /**
     * Item.
     *
     * @param index
     *            the index
     * @return the string
     */
    String item(int index);

    /**
     * Contains.
     *
     * @param token
     *            the token
     * @return true, if successful
     */
    boolean contains(String token);

    /**
     * Adds the.
     *
     * @param token
     *            the token
     */
    void add(String token);

    /**
     * Removes the.
     *
     * @param token
     *            the token
     */
    void remove(String token);

    /**
     * Toggle.
     *
     * @param token
     *            the token
     * @return true, if successful
     */
    boolean toggle(String token);

    /**
     * Toggle.
     *
     * @param token
     *            the token
     * @param force
     *            the force
     * @return true, if successful
     */
    boolean toggle(String token, boolean force);

    /**
     * To string.
     *
     * @return the string
     */
    String toString();
}
