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
 * The public interface HTMLOptionElement_Constructor.
 */
public interface HTMLOptionElement_Constructor {
    // Constructor
    /**
     * Creates the instance.
     *
     * @return the HTML option element
     */
    HTMLOptionElement createInstance();

    /**
     * Creates the instance.
     *
     * @param text
     *            the text
     * @return the HTML option element
     */
    HTMLOptionElement createInstance(String text);

    /**
     * Creates the instance.
     *
     * @param text
     *            the text
     * @param value
     *            the value
     * @return the HTML option element
     */
    HTMLOptionElement createInstance(String text, String value);

    /**
     * Creates the instance.
     *
     * @param text
     *            the text
     * @param value
     *            the value
     * @param defaultSelected
     *            the default selected
     * @return the HTML option element
     */
    HTMLOptionElement createInstance(String text, String value,
            boolean defaultSelected);

    /**
     * Creates the instance.
     *
     * @param text
     *            the text
     * @param value
     *            the value
     * @param defaultSelected
     *            the default selected
     * @param selected
     *            the selected
     * @return the HTML option element
     */
    HTMLOptionElement createInstance(String text, String value,
            boolean defaultSelected, boolean selected);
}
