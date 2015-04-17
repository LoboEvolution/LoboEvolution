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
 * The public interface HTMLEmbedElement.
 */
public interface HTMLEmbedElement extends HTMLElement {
    // HTMLEmbedElement
    /**
     * Gets the src.
     *
     * @return the src
     */
    String getSrc();

    /**
     * Sets the src.
     *
     * @param src
     *            the new src
     */
    void setSrc(String src);

    /**
     * Gets the type.
     *
     * @return the type
     */
    String getType();

    /**
     * Sets the type.
     *
     * @param type
     *            the new type
     */
    void setType(String type);

    /**
     * Gets the width.
     *
     * @return the width
     */
    String getWidth();

    /**
     * Sets the width.
     *
     * @param width
     *            the new width
     */
    void setWidth(String width);

    /**
     * Gets the height.
     *
     * @return the height
     */
    String getHeight();

    /**
     * Sets the height.
     *
     * @param height
     *            the new height
     */
    void setHeight(String height);

    // HTMLEmbedElement-12
    /**
     * Gets the align.
     *
     * @return the align
     */
    String getAlign();

    /**
     * Sets the align.
     *
     * @param align
     *            the new align
     */
    void setAlign(String align);

    /**
     * Gets the name.
     *
     * @return the name
     */
    String getName();

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    void setName(String name);
}
