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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.w3c.html.HTMLDetailsElement;

/**
 * The Class HTMLDetailsElementImpl.
 */
public class HTMLDetailsElementImpl extends HTMLElementImpl implements
HTMLDetailsElement {

    /**
     * Instantiates a new HTML details element impl.
     *
     * @param name
     *            the name
     */
    public HTMLDetailsElementImpl(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLDetailsElement#getOpen()
     */
    @Override
    public boolean getOpen() {
        String open = this.getAttribute(HtmlAttributeProperties.OPEN);
        return HtmlAttributeProperties.OPEN.equalsIgnoreCase(open);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLDetailsElement#setOpen(boolean)
     */
    @Override
    public void setOpen(boolean open) {
        this.setAttribute(HtmlAttributeProperties.OPEN,
                open ? HtmlAttributeProperties.OPEN : null);

    }

}
