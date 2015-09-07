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
import org.lobobrowser.w3c.html.HTMLSourceElement;

/**
 * The Class HTMLSourceElementImpl.
 */
public class HTMLSourceElementImpl extends HTMLElementImpl implements
HTMLSourceElement {

    /**
     * Instantiates a new HTML source element impl.
     *
     * @param name
     *            the name
     */
    public HTMLSourceElementImpl(String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLSourceElement#getSrc()
     */
    @Override
    public String getSrc() {
        return this.getAttribute(HtmlAttributeProperties.SRC);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLSourceElement#setSrc(java.lang.String)
     */
    @Override
    public void setSrc(String src) {
        this.setAttribute(HtmlAttributeProperties.SRC, src);

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLSourceElement#getType()
     */
    @Override
    public String getType() {
        return this.getAttribute(HtmlAttributeProperties.TYPE);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLSourceElement#setType(java.lang.String)
     */
    @Override
    public void setType(String type) {
        this.setAttribute(HtmlAttributeProperties.TYPE, type);

    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLSourceElement#getMedia()
     */
    @Override
    public String getMedia() {
        return this.getAttribute(HtmlAttributeProperties.MEDIA);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLSourceElement#setMedia(java.lang.String)
     */
    @Override
    public void setMedia(String media) {
        this.setAttribute(HtmlAttributeProperties.MEDIA, media);

    }

}
