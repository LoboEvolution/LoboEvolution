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
import org.lobobrowser.html.w3c.HTMLTimeElement;

/**
 * The Class HTMLTimeElementImpl.
 */
public class HTMLTimeElementImpl extends HTMLElementImpl implements
HTMLTimeElement {

    /**
     * Instantiates a new HTML time element impl.
     *
     * @param name
     *            the name
     */
    public HTMLTimeElementImpl(String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTimeElement#getDateTime()
     */
    @Override
    public String getDateTime() {
        return this.getAttribute(HtmlAttributeProperties.DATETIME);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTimeElement#setDateTime(java.lang.String)
     */
    @Override
    public void setDateTime(String dateTime) {
        this.setAttribute(HtmlAttributeProperties.DATETIME, dateTime);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTimeElement#getPubDate()
     */
    @Override
    public boolean getPubDate() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTimeElement#setPubDate(boolean)
     */
    @Override
    public void setPubDate(boolean pubDate) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLTimeElement#getValueAsDate()
     */
    @Override
    public long getValueAsDate() {
        // TODO Auto-generated method stub
        return 0;
    }
}
