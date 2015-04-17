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
 * The public interface DOMStringMap.
 */
public interface DOMStringMap {
    // DOMStringMap
    /**
     * Gets the element.
     *
     * @param name
     *            the name
     * @return the element
     */
    String getElement(String name);

    /**
     * Sets the element.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    void setElement(String name, String value);

    /**
     * Creates the element.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    void createElement(String name, String value);

    /**
     * Delete element.
     *
     * @param name
     *            the name
     */
    void deleteElement(String name);
}
