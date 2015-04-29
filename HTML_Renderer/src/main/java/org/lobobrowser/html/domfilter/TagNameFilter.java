/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The XAMJ Project This
 * library is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with this library; if not, write
 * to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston,
 * MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.html.domfilter;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * The Class TagNameFilter.
 */
public class TagNameFilter implements NodeFilter {

    /** The name. */
    private final String name;

    /**
     * Instantiates a new tag name filter.
     *
     * @param name
     *            the name
     */
    public TagNameFilter(String name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.domfilter.NodeFilter#accept(org.w3c.dom.Node)
     */
    @Override
    public boolean accept(Node node) {
        if (!(node instanceof Element)) {
            return false;
        }
        String n = this.name;
        return n.equalsIgnoreCase(((Element) node).getTagName());
    }
}
