/*
 * GNU LESSER GENERAL LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Copyright (c) 2003 World Wide Web Consortium, (Massachusetts Institute of
 * Technology, Institut National de Recherche en Informatique et en Automatique,
 * Keio University). All Rights Reserved. This program is distributed under the
 * W3C's Software Intellectual Property License. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * W3C License http://www.w3.org/Consortium/Legal/ for more details.
 */

package org.lobobrowser.html.w3c;

/**
 * Client-side image map. See the MAP element definition in HTML 4.01.
 * <p>
 * See also the Object Model (DOM) Level 2 HTML Specification
 * </p>
 * .
 */
public interface HTMLMapElement extends HTMLElement {

    /**
     * The list of areas defined for the image map.
     *
     * @return the areas
     */
    HTMLCollection getAreas();

    /**
     * Names the map (for use with <code>usemap</code>). See the name attribute
     * definition in HTML 4.01.
     *
     * @return the name
     */
    String getName();

    /**
     * Names the map (for use with <code>usemap</code>). See the name attribute
     * definition in HTML 4.01.
     *
     * @param name
     *            the new name
     */
    void setName(String name);

    /**
     * Gets the images.
     *
     * @return the images
     */
    HTMLCollection getImages();
}
