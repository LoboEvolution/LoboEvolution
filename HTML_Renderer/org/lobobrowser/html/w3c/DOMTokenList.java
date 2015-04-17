/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.w3c;

/**
 * The public interface DOMTokenList.
 */
public interface DOMTokenList {
    // DOMTokenList
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
    @Override
    String toString();
}
